package majava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.userinterface.GameUI;
import majava.util.GameTileList;
import majava.player.Player;
import majava.summary.PaymentMap;
import majava.summary.RoundResultSummary;
import majava.summary.StateOfGame;
import majava.tiles.GameTile;
import majava.control.testcode.GameSimulation;
import majava.events.GameEventListener;
import majava.events.GameplayEvent;
import majava.enums.Wind;
import majava.enums.Exclamation;



//represents a single round (ˆê‹Ç) of mahjong
public class Round{
	private static final int NUM_PLAYERS = 4;
	private static final Wind DEFAULT_ROUND_WIND = Wind.EAST;
	private static final int DEFAULT_ROUND_NUM = 1 , DEFAULT_ROUND_BONUS_NUM = 0;
	
	//for debug use
	private static final boolean DEBUG_LOAD_DEBUG_WALL = true;
//	private static final boolean DEBUG_LOAD_DEBUG_WALL = false;
	
//	private static final boolean DEBUG_EXHAUSTED_WALL = true;
	private static final boolean DEBUG_EXHAUSTED_WALL = false;
	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	
	
	
	
	private final Player[] players;	
	private final Wind roundWind;
	private final int roundNumber, roundBonusNumber;
	
	private final StateOfGame gameState;
	private final GameEventListener gameEventListener;
	private final GameUI userInterface;
	
	private final Wall wall;
	
	private final RoundTracker roundTracker;
	private final TurnIndicator turnIndicator;
	private final RoundResult roundResult;
	
	
	
	//options
	private boolean optionDoFastGameplay;
	private int sleepTime, sleepTimeExclamation, sleepTimeRoundEnd;
	
	
	
	//constructor
	public Round(GameUI ui, GameEventListener eventListener, Player[] playerArray, Wind roundWindToSet, int roundNum, int roundBonusNum){
		players = playerArray;
		
		roundWind = roundWindToSet;
		roundNumber = roundNum;
		roundBonusNumber = roundBonusNum;
		
		userInterface = ui;
		gameEventListener = eventListener;
		
		
		//prepare for new round
		for (Player p: players)
			p.prepareForNewRound();
		
		wall = new Wall();
		roundResult = new RoundResult();
		turnIndicator = new TurnIndicator(players);	/////Does TurnIndicator really need players?
		
		/////PLAYERS must be prepared before this line
		roundTracker = new RoundTracker(this, wall, players);		
		gameState = new StateOfGame(roundTracker, players, wall);
		
		setOptionFastGameplay(DEFAULT_DO_FAST_GAMEPLAY);
	}
	public Round(GameUI ui, GameEventListener eventListener, Player[] playerArray, Wind roundWindToSet, int roundNum){this(ui, eventListener, playerArray, roundWindToSet, roundNum, DEFAULT_ROUND_BONUS_NUM);}
	public Round(GameUI ui, GameEventListener eventListener, Player[] playerArray){this(ui, eventListener, playerArray, DEFAULT_ROUND_WIND, DEFAULT_ROUND_NUM);}
	
	
	
	
	
	//plays a single round of mahjong with the round's players
	public void play(){
//		if (roundIsOver()){userInterfacprintErrorRoundAlreadyOver();return;}
		
		dealHands();
		while (!roundIsOver()){
			doPlayerTurn(currentPlayer());
			
			if (roundIsOver())
				break;
			
			letOtherPlayersReactToDiscard();
			if (callWasMadeOnDiscard()){
				handleReaction();
				setTurnToPriorityCaller();
			}
			else
				goToNextTurn();
		}
		handleRoundEnd();
	}
	
	private void goToNextTurn(){		
		if (wallIsEmpty())
			return;
		turnIndicator.nextTurn();
	}
	private void setTurnToPriorityCaller(){turnIndicator.setTurnToPriorityCaller();}
	
	public boolean callWasMadeOnDiscard(){return turnIndicator.callWasMadeOnDiscard();}
	public GameTile mostRecentDiscard(){return turnIndicator.getMostRecentDiscard();}
	public Player currentPlayer(){return turnIndicator.currentPlayer();}
	public int whoseTurnNumber(){return turnIndicator.whoseTurnNumber();}
	
	private void handleRoundEnd(){
		doPointPayments();
		
		displayRoundResult();
	}
	private void doPointPayments(){
		Scorer scorer = new Scorer(roundResult, players);
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
		turnIndicator.neighborShimochaOf(eastPlayer).giveStartingHand(tilesS);
		turnIndicator.neighborToimenOf(eastPlayer).giveStartingHand(tilesW);
		turnIndicator.neighborKamichaOf(eastPlayer).giveStartingHand(tilesN);
		
		__updateUI(GameplayEvent.startOfRoundEvent());
	}
	
	
	
	
	//handles player p's turn, and gets the other players' reactions to the p's turn
	private void doPlayerTurn(Player p){
		if (p.needsDraw())
			letPlayerDraw(p);
		else
			__updateUI(GameplayEvent.placeholderEvent());
		
		if (roundIsOver()) return;	//return early if (4kan or washout)
		
		//loop until the player has chosen a discard (loop until the player stops making kans) (kans and riichi are handled inside here)
		GameTile discardedTile = null;
		do{
			if (p.controllerIsHuman()) tellUiAboutHumanPlayerTurnStart(p);
			
			discardedTile = p.takeTurn();
			turnIndicator.setMostRecentDiscard(discardedTile);	//discardedTile will be null if the player made a kan/tsumo, but that's ok
			
			if (madeKan(p)){
				tellUiAboutSelfKan(p);
				letPlayerDraw(p);	//give player a rinshan draw
			}
			
			if (p.turnActionCalledTsumo()){
				tellUiAboutTsumo(p);
				setResultVictory(p);
			}
			
			if (roundIsOver()) return;	//return early if (tsumo or 4kan or 4riichi or kyuushu)
		}
		while (!p.turnActionChoseDiscard());
		
		//show the human player their hand, show the discarded tile and the discarder's pond
		__updateUI(GameplayEvent.discardedTileEvent());
	}
	private boolean madeKan(Player p){return p.needsDrawRinshan();}
//	private List<Player> playersOtherThan(Player p){return Arrays.asList(roundTracker.neighborShimochaOf(p), roundTracker.neighborToimenOf(p), roundTracker.neighborKamichaOf(p));}
	
	
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
			__updateUI(GameplayEvent.newDoraIndicatorEvent());
		}
		
		p.addTileToHand(drawnTile);
		__updateUI(GameplayEvent.drewTileEvent());
	}
	
	private boolean tooManyKans(){return roundTracker.tooManyKans();}
	private void setResultRyuukyoku4Kan(){roundResult.setResultRyuukyoku4Kan();}
	
	private void setResultVictory(Player winner){
		GameTile winningTile = null;
		GameTileList winningHandTiles = winner.DEMOgetHand().DEMOgetTilesAsList().clone();	/////Need this for now, will make more elegant later
		
		if (winner == currentPlayer()){
			roundResult.setVictoryTsumo(winner);			
			winningTile = winner.getTsumoTile();
			winningHandTiles.removeLast();
		}
		else{ 
			roundResult.setVictoryRon(winner, currentPlayer());			
			winningTile = mostRecentDiscard();
		}
		roundResult.setWinningHand(winningHandTiles, winner.DEMOgetHand().getMelds(), winningTile);
	}
	
	private boolean wallIsEmpty(){return wall.isEmpty();}
	private void setResultRyuukyokuWashout(){roundResult.setResultRyuukyokuWashout();}
	
	/////these aren't implemented yet in gameplay
	private void setResultRyuukyokuKyuushu(){roundResult.setResultRyuukyokuKyuushu();}
	private void setResultRyuukyoku4Riichi(){roundResult.setResultRyuukyoku4Riichi();}
	private void setResultRyuukyoku4Wind(){roundResult.setResultRyuukyoku4Wind();}
	
	
	
	
	
	
	
	
	private void letOtherPlayersReactToDiscard(){
		letReact(turnIndicator.neighborShimochaOf(currentPlayer()));
		letReact(turnIndicator.neighborToimenOf(currentPlayer()));
		letReact(turnIndicator.neighborKamichaOf(currentPlayer()));
	}
	private void letReact(Player p){
		if (!p.ableToCallTile(mostRecentDiscard())) return;
		if (p.controllerIsHuman())
			tellUiAboutHumanReactionChance(p);
		p.reactToDiscard(mostRecentDiscard());
	}
	
	
	
	//handles a call made on a discarded tile
	private void handleReaction(){
		Player priorityCaller = whoCalled();
		turnIndicator.setPriorityCaller(priorityCaller);
		
		displayCallFrom(priorityCaller);
		
		if (priorityCaller.calledRon()){
			setResultVictory(priorityCaller);
			return;
		}
		
		//remove tile from discarder's pond and make meld
		GameTile calledTile = currentPlayer().removeTileFromPond();
		priorityCaller.makeMeld(calledTile);
		
		__updateUI(GameplayEvent.madeOpenMeldEvent());
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
	public String getRoundResultString(){return roundResult.toString();}
	
	public boolean qualifiesForRenchan(){
		return roundEndedWithDealerVictory();
		//or if the dealer is in tenpai, or a certain ryuukyoku happens
	}
	public boolean roundEndedWithDealerVictory(){return roundResult.isDealerVictory();}	
	
	
	
	
	
	public void displayRoundResult(){
		GameplayEvent endOfRoundEvent = GameplayEvent.endOfRoundEvent();
		endOfRoundEvent.packInfo(roundResult.getSummary());
		__updateUI(endOfRoundEvent);
	}
	private void displayCallFrom(Player caller){
		GameplayEvent callEvent = GameplayEvent.calledTileEvent();
		callEvent.setExclamation(caller.getCallStatusExclamation());
		callEvent.packInfo(caller, mostRecentDiscard(), whoseTurnNumber());
		__updateUI(callEvent);
	}
	private void tellUiAboutSelfKan(Player fromPlayer){
		GameplayEvent kanEvent = GameplayEvent.declaredOwnKanEvent();
		kanEvent.setExclamation(Exclamation.OWN_KAN);
		kanEvent.packInfo(fromPlayer);
		__updateUI(kanEvent);
		__updateUI(GameplayEvent.madeOwnKanEvent());
	}
	private void tellUiAboutTsumo(Player fromPlayer){
		GameplayEvent tsumoEvent = GameplayEvent.declaredTsumoEvent();
		tsumoEvent.setExclamation(Exclamation.TSUMO);
		tsumoEvent.packInfo(fromPlayer);
		__updateUI(tsumoEvent);
	}
	private void __updateUI(GameplayEvent event){
		gameEventListener.postNewEvent(event, gameState);
	}
	private void tellUiAboutHumanPlayerTurnStart(Player p){
		GameplayEvent humanTurnStartEvent = GameplayEvent.humanPlayerTurnStartEvent();
		humanTurnStartEvent.packInfo(p);
		__updateUI(humanTurnStartEvent);
	}
	private void tellUiAboutHumanReactionChance(Player p){
		GameplayEvent humanReactionEvent = GameplayEvent.humanReactionEvent();
		humanReactionEvent.packInfo(p, mostRecentDiscard(), whoseTurnNumber());
		__updateUI(humanReactionEvent);
	}
	
	
	
	
	
	public void setOptionFastGameplay(boolean doFastGameplay){
		optionDoFastGameplay = doFastGameplay;

		final int DEAFULT_SLEEPTIME = 400;
		final int DEAFULT_SLEEPTIME_EXCLAMATION = 1500;
		final int DEAFULT_SLEEPTIME_ROUND_END = 18000;
		final int FAST_SLEEPTIME = 0, FAST_SLEEPTIME_EXCLAMATION = 0, FAST_SLEEPTIME_ROUND_END = 0;
		
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
		
//		GameSimulation.main(args);
//		if (args.equals(args)) return;
		
		System.out.println("Welcome to Majava (Round)!");
		
		System.out.println("Launching Table...");
		Table.main(null);
	}

}
