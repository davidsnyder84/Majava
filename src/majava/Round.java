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
		
		///////////////////////////////
		if (roundResult.isVictory()) yakuDriveby();
		///////////////////////////////
		
		
		if (userInterface != null) userInterface.setRoundResult(roundResult.getSummary());
		
		//display end of round result
		displayRoundResult();
	}
	
	private void yakuDriveby(){
		AgariHand agariHand = new AgariHand(roundResult.getWinningPlayer().DEMOgetHand(), roundResult.getWinningTile());
		System.out.println(agariHand.toString());
		
		YakuAnalyzer yakuana = new YakuAnalyzer(roundResult.getWinningPlayer().DEMOgetHand(), roundResult);
		System.out.println("YAKUANALYZERSAYS: "  + yakuana.getYakuList());
	}
	
	
	private void doPointPayments(){
		final int PAYMENT_DUE = 8000;
		int paymentDue = PAYMENT_DUE;
		
		PaymentMap payments = null;

		//find who has to pay what
		if (roundResult.isDraw())
			payments = mapPaymentsForDraw();
		else
			payments = mapPaymentsForWin(paymentDue);
		
		//carry out payments
		for (Player p: players)
			p.pointsIncrease(payments.get(p));
		
		roundResult.recordPayments(payments);
	}

	
	
	//maps payments to players, for the case of a win
	private PaymentMap mapPaymentsForWin(int handValue){
		PaymentMap payments = new PaymentMap();
		
		final double DEALER_WIN_MULTIPLIER = 1.5, DEALER_TSUMO_MULTIPLIER = 2;
		
		int paymentDue = handValue;
		int tsumoPointsNonDealer = paymentDue / 4;
		int tsumoPointsDealer = (int) (DEALER_TSUMO_MULTIPLIER * tsumoPointsNonDealer);
		
		
		//find who the winner is
		Player winner = roundResult.getWinningPlayer();
		Player[] losers = {roundTracker.neighborShimochaOf(winner), roundTracker.neighborToimenOf(winner), roundTracker.neighborKamichaOf(winner)};
		Player furikonda = null;
		
		if (winner.isDealer()) paymentDue *= DEALER_WIN_MULTIPLIER;
		
		///////add in honba here
		
		payments.put(winner, paymentDue);
		
		
		//find who has to pay
		if (roundResult.isVictoryRon()){
			furikonda = roundResult.getFurikondaPlayer();
			for (Player p: losers)
				if (p == furikonda) payments.put(p, -paymentDue);
				else payments.put(p, 0);
		}
		else{//tsumo
			for (Player p: losers){
				if (p.isDealer() || winner.isDealer()) payments.put(p, -tsumoPointsDealer);
				else  payments.put(p, -tsumoPointsNonDealer);
			}
		}
		///////add in riichi sticks here
		return payments;
	}
	
	//maps payments to players, for the case of a draw
	private PaymentMap mapPaymentsForDraw(){
		PaymentMap payments = new PaymentMap();
		/////implement no-ten bappu here 
		
		for (Player p: players)
			payments.put(p, 0);
		return payments;
	}
	
	
	
	
	
	
	//deals players their starting hands
	private void dealHands(){
		if (DEBUG_EXHAUSTED_WALL) wall.DEMOexhaustWall();
		if (DEBUG_LOAD_DEBUG_WALL) wall.DEMOloadDebugWall();	//DEBUG
		
		//get starting hands from the wall
		List<GameTile> tilesE = new ArrayList<GameTile>(), tilesS = new ArrayList<GameTile>(), tilesW = new ArrayList<GameTile>(), tilesN = new ArrayList<GameTile>();
		wall.takeStartingHands(tilesE, tilesS, tilesW, tilesN);
		
		
		Player eastPlayer = currentPlayer();
		//give dealer their tiles
		eastPlayer.giveStartingHand(tilesE);
		roundTracker.neighborShimochaOf(eastPlayer).giveStartingHand(tilesS);
		roundTracker.neighborToimenOf(eastPlayer).giveStartingHand(tilesW);
		roundTracker.neighborKamichaOf(eastPlayer).giveStartingHand(tilesN);
		
		__updateUI(GameplayEvent.START_OF_ROUND);
	}
	
	
	
	
	//handles player p's turn, and gets the other players' reactions to the p's turn
	private void doPlayerTurn(Player p){
		
		if (p.needsDraw()){
			givePlayerTile(p);
		}
		else __updateUI(GameplayEvent.PLACEHOLDER);
		
		//return early if the round is over (4kan or washout)
		if (roundIsOver()) return;
		
		//~~~~~~get player's discard (kans and riichi are handled inside here)
		//loop until the player has chosen a discard
		//loop until the player stops making kans
		GameTile discardedTile = null;
		do{
			discardedTile = p.takeTurn();
			roundTracker.setMostRecentDiscard(discardedTile);
			
			//if the player made an ankan or minkan, they need a rinshan draw
			if (p.needsDrawRinshan()){
				
				GameplayEvent kanEvent = GameplayEvent.DECLARED_OWN_KAN;
				kanEvent.setExclamation(Exclamation.OWN_KAN, p.getPlayerNumber());
				__updateUI(kanEvent);
				__updateUI(GameplayEvent.MADE_OWN_KAN);
				
				//give player a rinshan draw
				givePlayerTile(p);
				
			}
			
			if (p.turnActionCalledTsumo()){
				GameplayEvent tsumoEvent = GameplayEvent.DECLARED_TSUMO;
				tsumoEvent.setExclamation(Exclamation.TSUMO, p.getPlayerNumber());
				__updateUI(tsumoEvent);
				setResultVictory(p);
			}
			
		}
		while (!p.turnActionChoseDiscard() && !roundIsOver());
		
		//return early if the round is over (tsumo or 4kan or 4riichi or kyuushu)
		if (roundIsOver()) return;
		
		//show the human player their hand, show the discarded tile and the discarder's pond
		__updateUI(GameplayEvent.DISCARDED_TILE);
		
		//~~~~~~get reactions from the other players
		roundTracker.neighborShimochaOf(p).reactToDiscard(discardedTile);
		roundTracker.neighborToimenOf(p).reactToDiscard(discardedTile);
		roundTracker.neighborKamichaOf(p).reactToDiscard(discardedTile);
	}
	
	
	private boolean callWasMadeOnDiscard(){return roundTracker.callWasMadeOnDiscard();}
	
	
	
	
	//gives a player a tile from the wall or dead wall
	private void givePlayerTile(Player p){
		if (!p.needsDraw()) return;
		GameTile drawnTile = null;
		
		//draw from wall or dead wall, depending on what player needs
		if (p.needsDrawNormal()){
			
			if (wallIsEmpty()){
				setResultRyuukyokuWashout();
				return;
			}
			else{
				drawnTile = wall.takeTile();
			}
		}
		else if (p.needsDrawRinshan()){
			
			//check if too many kans have been made before making a rinshan draw
			if (tooManyKans()){
				setResultRyuukyoku4Kan();
				return;
			}
			else{
				drawnTile = wall.takeTileFromDeadWall();
				__updateUI(GameplayEvent.NEW_DORA_INDICATOR);
			}			
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
		//get the discarded tile
		GameTile discardedTile = currentPlayer().getLastDiscard();
		
		//figure out who called the tile, and if multiple players called, who gets priority
		Player priorityCaller = whoCalled();
		
		//remove the tile from the discarder's pond (because it is being called), unless the call was Ron
		if (!priorityCaller.calledRon())
			currentPlayer().removeTileFromPond();
		
		//show the call
		GameplayEvent callEvent = GameplayEvent.CALLED_TILE;
		callEvent.setExclamation(priorityCaller.getCallStatusExclamation(), priorityCaller.getPlayerNumber());
		__updateUI(callEvent);
		
		//give the caller the discarded tile so they can make their meld
		//if the caller called Ron, handle that instead
		if (priorityCaller.calledRon()){
			setResultVictory(priorityCaller);
		}
		else{
			//make the meld
			priorityCaller.makeMeld(discardedTile);
			__updateUI(GameplayEvent.MADE_OPEN_MELD);
		}
		
		//it is now the calling player's turn (if the round isn't over)
		if (!roundIsOver())
			roundTracker.setTurn(priorityCaller);
	}
	
	//decides who gets to call the tile
	private Player whoCalled(){
		
		Player callingPlayer = null;
		Player callerPon = null, callerRon = null;
		
		int numCallers = 0;
		for (Player p: players)
			if (p.called()){
				callingPlayer = p;
				numCallers++;
			}
		
		//if only one player called, return that player
		if (numCallers == 1){
			return callingPlayer;
		}
		else {
			//else, if more than one player called, figure out who has more priority
			//if p called something other than a chi...
				//if he called pon/kan, he is the pon caller (there can't be 2 pon callers, not enough tiles in the game)
				//if he called ron, he is the ron caller (if there is already a ron caller, do nothing, because that caller has seat order priority)
			for (int i = players.length - 1; i >= 0 ; i--){
				if (players[i].called() && !players[i].calledChi())
					if (players[i].calledPon() || players[i].calledKan())
						callerPon = players[i];
					else
						callerRon = players[i];
			}
			
			//return the first ron caller, or return the pon caller if there was no ron caller
			if (callerRon != null) return callerRon;
			return callerPon;
		}
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
//		final int DEAFULT_SLEEPTIME_ROUND_END = 2000;
//		final int DEAFULT_SLEEPTIME_ROUND_END = 7000;
		final int DEAFULT_SLEEPTIME_ROUND_END = 18000;
		
		final int FAST_SLEEPTIME = 0;
		final int FAST_SLEEPTIME_EXCLAMATION = 0;
//		final int FAST_SLEEPTIME_EXCLAMATION = DEAFULT_SLEEPTIME_EXCLAMATION;
		final int FAST_SLEEPTIME_ROUND_END = 0;
//		final int FAST_SLEEPTIME_ROUND_END = DEAFULT_SLEEPTIME_ROUND_END;
		
		
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
