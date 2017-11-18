package majava;

import java.util.ArrayList;
import java.util.List;

import majava.userinterface.GameUI;
import majava.yaku.YakuAnalyzer;
import majava.player.Player;
import majava.summary.PaymentMap;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.enums.GameplayEvent;
import majava.enums.Wind;
import majava.enums.Exclamation;
import majava.hand.AgariHand;



//represents a single round (ˆê‹Ç) of mahjong
public class Round{

	private static final int NUM_PLAYERS = 4;
	private static final Wind DEFAULT_ROUND_WIND = Wind.EAST;
	private static final int DEFAULT_ROUND_NUM = 1;
	private static final int DEFAULT_ROUND_BONUS_NUM = 0;
	
	//for debug use
//	private static final boolean DEBUG_LOAD_DEBUG_WALL = true;
	private static final boolean DEBUG_LOAD_DEBUG_WALL = false;
	
//	private static final boolean DEBUG_EXHAUSTED_WALL = true;
	private static final boolean DEBUG_EXHAUSTED_WALL = false;
	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	
	
	
	
	private final Player[] players;	
	
	private final RoundTracker roundTracker;
	private final RoundResult roundResult;
	private final GameUI userInterface;	
	
	private final Wall wall;
	private final Wind roundWind;
	private final int roundNumber, roundBonusNumber;
	
	
	//options
	private boolean optionDoFastGameplay;
	private int sleepTime, sleepTimeExclamation, sleepTimeRoundEnd;
	
	
	
	//constructor
	public Round(GameUI ui, Player[] playerArray, Wind roundWindToSet, int roundNum, int roundBonusNum){
		
		players = playerArray;
		for (Player p: players)
			p.prepareForNewRound();
		
		wall = new Wall();
		
		//initializes round info
		roundWind = roundWindToSet;
		roundNumber = roundNum;
		roundBonusNumber = roundBonusNum;
		
		userInterface = ui;
		
		//initialize Round Tracker
		roundResult = new RoundResult();
		
		/////PLAYERS must be prepared before this line
		/////suggestion: can we refactor to do players.prepareForNewRound() and initialize roundTracker in the same line?
		roundTracker = new RoundTracker(userInterface, roundResult, roundWind,roundNumber,roundBonusNumber, wall, players);
		
		setOptionFastGameplay(DEFAULT_DO_FAST_GAMEPLAY);
	}
	//no bonus round info
	public Round(GameUI ui, Player[] playerArray, Wind roundWindToSet, int roundNum){this(ui, playerArray, roundWindToSet, roundNum, DEFAULT_ROUND_BONUS_NUM);}
	//no round info
	public Round(GameUI ui, Player[] playerArray){this(ui, playerArray, DEFAULT_ROUND_WIND, DEFAULT_ROUND_NUM);}
	
	
	
	
	
	//plays a single round of mahjong with the round's players
	public void play(){
		if (roundIsOver()){userInterface.printErrorRoundAlreadyOver();return;}
		
		dealHands();
		while (!roundIsOver()){
			doPlayerTurn(currentPlayer());
			
			if (roundIsOver())
				break;
			
			if (callWasMadeOnDiscard())
				handleReaction();
			else
				goToNextTurn();
		}
		handleRoundEnd();
	}
	private void goToNextTurn(){		
		if (wallIsEmpty())
			return;
		roundTracker.nextTurn();
	}
	private Player currentPlayer(){return roundTracker.currentPlayer();}
	
	
	private void handleRoundEnd(){
		doPointPayments();
		
		displayRoundResult();
	}
	private void doPointPayments(){
		Scorer scorer = new Scorer(roundResult, roundTracker);
		PaymentMap payments = scorer.getPaymentMap();
		
		//carry out payments
		for (Player p: players)
			p.pointsIncrease(payments.get(p));
		
		roundResult.recordPayments(payments);
		
		scorer.printWinningYaku();/////demo
	}
	
	
	
	
	//deals players their starting hands
	private void dealHands(){
		if (DEBUG_EXHAUSTED_WALL) wall.DEMOexhaustWall(); if (DEBUG_LOAD_DEBUG_WALL) wall.DEMOloadDebugWall();	//DEBUG
		
		//get starting hands from the wall
		List<GameTile> tilesE = new ArrayList<GameTile>(), tilesS = new ArrayList<GameTile>(), tilesW = new ArrayList<GameTile>(), tilesN = new ArrayList<GameTile>();
		wall.takeStartingHands(tilesE, tilesS, tilesW, tilesN);
		
		Player eastPlayer = currentPlayer();
		eastPlayer.giveStartingHand(tilesE);
		roundTracker.neighborShimochaOf(eastPlayer).giveStartingHand(tilesS);
		roundTracker.neighborToimenOf(eastPlayer).giveStartingHand(tilesW);
		roundTracker.neighborKamichaOf(eastPlayer).giveStartingHand(tilesN);
		
		__updateUI(GameplayEvent.START_OF_ROUND);
	}
	
	
	
	
	//handles player p's turn, and gets the other players' reactions to the p's turn
	private void doPlayerTurn(Player p){
		if (p.needsDraw())
			letPlayerDraw(p);
		else
			__updateUI(GameplayEvent.PLACEHOLDER);
		
		//return early if the round is over (4kan or washout)
		if (roundIsOver()) return;
		
		//~~~~~~get player's discard (kans and riichi are handled inside here)
		//loop until the player has chosen a discard (loop until the player stops making kans)
		GameTile discardedTile = null;
		do{
			discardedTile = p.takeTurn();
			roundTracker.setMostRecentDiscard(discardedTile);
			
			if (madeKan(p)){
				GameplayEvent kanEvent = GameplayEvent.DECLARED_OWN_KAN;
				kanEvent.setExclamation(Exclamation.OWN_KAN, p.getPlayerNumber());
				__updateUI(kanEvent);
				__updateUI(GameplayEvent.MADE_OWN_KAN);
				
				//give player a rinshan draw
				letPlayerDraw(p);
			}
			
			if (p.turnActionCalledTsumo()){
				GameplayEvent tsumoEvent = GameplayEvent.DECLARED_TSUMO;
				tsumoEvent.setExclamation(Exclamation.TSUMO, p.getPlayerNumber());
				__updateUI(tsumoEvent);
				
				setResultVictory(p);
			}
			
			//return early if the round is over (tsumo or 4kan or 4riichi or kyuushu)
			if (roundIsOver()) return;
		}
		while (!p.turnActionChoseDiscard());
		
		
		//show the human player their hand, show the discarded tile and the discarder's pond
		__updateUI(GameplayEvent.DISCARDED_TILE);
		
		//~~~~~~get reactions from the other players
		roundTracker.neighborShimochaOf(p).reactToDiscard(discardedTile);
		roundTracker.neighborToimenOf(p).reactToDiscard(discardedTile);
		roundTracker.neighborKamichaOf(p).reactToDiscard(discardedTile);
	}
	private boolean madeKan(Player p){return p.needsDrawRinshan();}
	private boolean callWasMadeOnDiscard(){return roundTracker.callWasMadeOnDiscard();}
	
	
	
	
	//gives a player a tile from the wall or dead wall
	private void letPlayerDraw(Player p){
		if (!p.needsDraw()) return;
		
		GameTile drawnTile = null;
		if (p.needsDrawNormal()){
			if (wallIsEmpty()){
				setResultRyuukyokuWashout();
				return;
			}
			drawnTile = wall.takeTile();
		}
		else if (p.needsDrawRinshan()){
			if (tooManyKans()){
				setResultRyuukyoku4Kan();
				return;
			}
			drawnTile = wall.takeTileFromDeadWall();
			__updateUI(GameplayEvent.NEW_DORA_INDICATOR);
		}
		
		p.addTileToHand(drawnTile);
		__updateUI(GameplayEvent.DREW_TILE);
	}
	
	private boolean tooManyKans(){return roundTracker.tooManyKans();}
	private void setResultRyuukyoku4Kan(){roundResult.setResultRyuukyoku4Kan();}
	
	private void setResultVictory(Player winner){roundTracker.setResultVictory(winner);}
	
	private boolean wallIsEmpty(){return wall.isEmpty();}
	private void setResultRyuukyokuWashout(){roundResult.setResultRyuukyokuWashout();}
	
	/////these aren't implemented yet in gameplay
	private void setResultRyuukyokuKyuushu(){roundResult.setResultRyuukyokuKyuushu();}
	private void setResultRyuukyoku4Riichi(){roundResult.setResultRyuukyoku4Riichi();}
	private void setResultRyuukyoku4Wind(){roundResult.setResultRyuukyoku4Wind();}
	
	
	
	//handles a call made on a discarded tile
	private void handleReaction(){
		Player priorityCaller = whoCalled();
		displayCallFrom(priorityCaller);
		
		if (priorityCaller.calledRon()){
			setResultVictory(priorityCaller);
			return;
		}
		
		//remove tile from discarder's pond and make meld
		GameTile calledTile = currentPlayer().removeTileFromPond();
		priorityCaller.makeMeld(calledTile);
		__updateUI(GameplayEvent.MADE_OPEN_MELD);
		
		//it is now the calling player's turn
		roundTracker.setTurn(priorityCaller);
	}
	private void displayCallFrom(Player priorityCaller){
		GameplayEvent callEvent = GameplayEvent.CALLED_TILE;
		callEvent.setExclamation(priorityCaller.getCallStatusExclamation(), priorityCaller.getPlayerNumber());
		__updateUI(callEvent);
	}
	
	//decides who gets to call the tile
	private Player whoCalled(){
		Player callingPlayer = null;
		Player callerPon = null, callerRON = null;
		int numCallers = 0;
		
		for (Player p: players)
			if (p.called()){
				callingPlayer = p;
				numCallers++;
			}
		
		//if only one player called, return that player
		if (numCallers == 1) return callingPlayer;
		
		//if p called something other than a chi... if he called pon/kan, he is the pon caller (there can't be 2 pon callers, not enough tiles in the game). if he called ron, he is the ron caller (if there is already a ron caller, do nothing, because that caller has seat order priority)
		for (int i = players.length - 1; i >= 0 ; i--){
			if (players[i].called() && !players[i].calledChi())
				if (players[i].calledPon() || players[i].calledKan())
					callerPon = players[i];
				else
					callerRON = players[i];
		}
		
		//return the first ron caller, or return the pon caller if there was no ron caller
		if (callerRON != null) return callerRON;
		return callerPon;
	}
	
	
	
	
	
	//accessors
	public Wind getRoundWind(){return roundWind;}
	public int getRoundNum(){return roundNumber;}
	public int getRoundBonusNum(){return roundBonusNumber;}
	
	//round result methods
	public boolean roundIsOver(){return roundResult.isOver();}
	public boolean endedWithVictory(){return roundResult.isVictory();}
	public String getWinningHandString(){return roundResult.getAsStringWinningHand();}
	public RoundResultSummary getResultSummary(){return roundResult.getSummary();}
	
	public boolean qualifiesForRenchan(){return roundTracker.qualifiesForRenchan();}
	
	
	
	public void displayRoundResult(){
		if (userInterface != null) userInterface.setRoundResult(roundResult.getSummary());
		__updateUI(GameplayEvent.END_OF_ROUND);
	}
	
	
	private void __updateUI(GameplayEvent event){
		if (userInterface == null) return;
		
		userInterface.displayEvent(event);
	}
	
	
	public void setOptionFastGameplay(boolean doFastGameplay){
		optionDoFastGameplay = doFastGameplay;

		final int DEAFULT_SLEEPTIME = 400;
		final int DEAFULT_SLEEPTIME_EXCLAMATION = 1500;
		final int DEAFULT_SLEEPTIME_ROUND_END = 18000;
		final int FAST_SLEEPTIME = 0;
		final int FAST_SLEEPTIME_EXCLAMATION = 0;
		final int FAST_SLEEPTIME_ROUND_END = 0;
		
		if (optionDoFastGameplay){
			sleepTime = FAST_SLEEPTIME;
			sleepTimeExclamation = FAST_SLEEPTIME_EXCLAMATION;
			sleepTimeRoundEnd = FAST_SLEEPTIME_ROUND_END;
		}
		else{
			sleepTime = DEAFULT_SLEEPTIME;
			sleepTimeExclamation = DEAFULT_SLEEPTIME_EXCLAMATION;
			sleepTimeRoundEnd = DEAFULT_SLEEPTIME_ROUND_END;
		}
		if (userInterface != null) userInterface.setSleepTimes(sleepTime, sleepTimeExclamation, sleepTimeRoundEnd);
	}
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Round)!");
		
		System.out.println("Launching Table...");
		Table.main(null);
	}

}
