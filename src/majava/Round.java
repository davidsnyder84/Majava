package majava;

import java.util.HashMap;
import java.util.Map;

import utility.Pauser;
import majava.graphics.userinterface.GameUI;
import majava.summary.RoundResultSummary;
import majava.tiles.Tile;
import majava.util.TileList;
import majava.enums.GameplayEvent;
import majava.enums.Wind;
import majava.enums.Exclamation;




/*
Class: Round

data:
	p1, p2, p3, p4 - the four players who will play the round
	mPlayerArray - an array containing the four players
	
	mWall - wall of tiles, includes the dead wall
	
	mRoundTracker - tracks and manages information on the round
	mTviewer - TableGUI to display the game and get input from the human player
	mPauser - used to pause (wait) after player actions
	
	mCurrentRoundWind, mCurrentRoundNum, mCurrentRoundBonusNum - information for the round
	
	mDoFastGameplay - option, will do fast gameplay if true
	sleepTime, sleepTimeExclamation, sleepTimeRoundEnd - determine how long to pause during gameplay
	
methods:
	constructor:
	2-arg: requires TableViewer, array of four players
	4-arg: requires TableViewer, array of four players, round wind, round num
	5-arg: requires TableViewer, array of four players, round wind, round num, round bonus num
	
	public:
		mutators:
	 	play - play the round
	 	setOptionFastGameplay - set option to do fast gameplay or not
	 	
	 	accessors:
	 	getRoundWind, getRoundNum, getRoundBonusNum - returns round information
	 	
	 	roundIsOver - returns true if the round has ended
	 	endedWithVictory - returns true if the round ended with a win (not a draw)
	 	getWinningHandString - return a string repesentation of the round's winning hand
	 	displayRoundResult - displays the round's end result
*/
public class Round{
	
	private static final Wind DEFAULT_ROUND_WIND = Wind.EAST;
	private static final int DEFAULT_ROUND_NUM = 1;
	private static final int DEFAULT_ROUND_BONUS_NUM = 0;
	
	//for debug use
	private static final boolean DEBUG_LOAD_DEBUG_WALL = false;
	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	
	
	
	
	private final Player p1, p2, p3, p4;
	private final Player[] mPlayerArray;
	
	private final Wall mWall;
	
	private final RoundTracker mRoundTracker;
	private final RoundResult mRoundResult;
	private final GameUI mUI;
//	private final TableGUI mTviewer;
//	private final TextualUI mTextinterface;
	
	
	private final Wind mRoundWind;
	private final int mRoundNum, mRoundBonusNum;
	
	
	//options
	private boolean mDoFastGameplay;
	private int sleepTime, sleepTimeExclamation, sleepTimeRoundEnd;
	private Pauser mPauser;
	
	
	/*
	Constructor
	initializes a new round to make it ready for playing
	
	creates the wall
	initializes round and game info
	*/
	//constructor
	public Round(GameUI ui, Player[] playerArray, Wind roundWind, int roundNum, int roundBonusNum){
		
		mPlayerArray = playerArray;
		p1 = mPlayerArray[0]; p2 = mPlayerArray[1]; p3 = mPlayerArray[2]; p4 = mPlayerArray[3];
		
		//prepare players for new round
		for (Player p: mPlayerArray)
			p.prepareForNewRound();
		
		
		//creates the wall
		mWall = new Wall();
		
		//initializes round info
		mRoundWind = roundWind;
		mRoundNum = roundNum;
		mRoundBonusNum = roundBonusNum;
		
		mUI = ui;
//		mTviewer = tviewer;
//		mTextinterface = textinterface;
		
		//initialize Round Tracker
//		mTviewer = null; mTextinterface = null;
		mRoundResult = new RoundResult();
		mRoundTracker = new RoundTracker(mUI, mRoundResult, mRoundWind,mRoundNum,mRoundBonusNum,  mWall,  p1,p2,p3,p4);
//		mRoundTracker = new RoundTracker(mTextinterface, mTviewer, mRoundResult, mRoundWind,mRoundNum,mRoundBonusNum,  mWall,  p1,p2,p3,p4);
		
		setOptionFastGameplay(DEFAULT_DO_FAST_GAMEPLAY);
	}
	//no bonus round info
	public Round(GameUI ui, Player[] playerArray, Wind roundWind, int roundNum){this(ui, playerArray, roundWind, roundNum, DEFAULT_ROUND_BONUS_NUM);}
	//no round info
	public Round(GameUI ui, Player[] playerArray){this(ui, playerArray, DEFAULT_ROUND_WIND, DEFAULT_ROUND_NUM);}
	
	
	
	
	/*
	method: play
	plays a single round of mahjong with the round's players
	
	if (round is already over): return early
	
	deal hands
	loop while (game is not over)
		handle current player's turn
		if (round is not over, and a call was made on the current player's discard)
			handle the reaction to the player's discard
		end if
	end loop
	
	display end of round result
	*/
	public void play(){
		
		if (roundIsOver()){mUI.printErrorRoundAlreadyOver();return;}
		
		//deal starting hands
		__dealHands();
		
		
		//game loop, loop until the round is over
		while (!roundIsOver()){

			//handle player turns
			__doPlayerTurn(mRoundTracker.currentPlayer());
			

			//handle reactions here
			if (!roundIsOver())
				if (mRoundTracker.callWasMadeOnDiscard())
					__handleReaction();
		}
		
		//handle end of round
		__handleRoundEnd();
	}
	
	
	private void __handleRoundEnd(){
		
		__doPointPayments();
		
		
		//display end of round result
		displayRoundResult();
	}
	
	
	
	private void __doPointPayments(){
		
		final int PAYMENT_DUE = 8000;
		int paymentDue = PAYMENT_DUE;
		
		Map<Player, Integer> playerPaymentMap = null;
		

		//find who has to pay what
		if (mRoundResult.isDraw()) playerPaymentMap = __mapPaymentsDraw();
		else playerPaymentMap = __mapPaymentsWin(paymentDue);
		
		//carry out payments
		for (Player p: mPlayerArray) p.pointsIncrease(playerPaymentMap.get(p));
		
		//record payments in the result
		mRoundResult.recordPayments(playerPaymentMap);
	}

	
	/*
	private method: __mapPaymentsWin
	maps payments to players, in the case of a win
	*/
	private Map<Player, Integer> __mapPaymentsWin(int handValue){
		
		Map<Player, Integer> playerPaymentMap = new HashMap<Player, Integer>();
		
		final double DEALER_WIN_MULTIPLIER = 1.5, DEALER_TSUMO_MULTIPLIER = 2;
		
		int paymentDue = handValue;
		int tsumoPointsNonDealer = paymentDue / 4;
		int tsumoPointsDealer = (int) (DEALER_TSUMO_MULTIPLIER * tsumoPointsNonDealer);
		
		
		//find who the winner is
		Player winner = mRoundResult.getWinningPlayer();
		Player[] losers = {mRoundTracker.neighborShimochaOf(winner), mRoundTracker.neighborToimenOf(winner), mRoundTracker.neighborKamichaOf(winner)};
		Player furikonda = null;
		
		if (winner.isDealer()) paymentDue *= DEALER_WIN_MULTIPLIER;
		
		///////add in honba here
		
		playerPaymentMap.put(winner, paymentDue);
		
		
		//find who has to pay
		if (mRoundResult.isVictoryRon()){
			furikonda = mRoundResult.getFurikondaPlayer();
			for (Player p: losers)
				if (p == furikonda) playerPaymentMap.put(p, -paymentDue);
				else playerPaymentMap.put(p, 0);
		}
		else{//tsumo
			for (Player p: losers){
				if (p.isDealer() || winner.isDealer()) playerPaymentMap.put(p, -tsumoPointsDealer);
				else  playerPaymentMap.put(p, -tsumoPointsNonDealer);
			}
		}
		///////add in riichi sticks here
		return playerPaymentMap;
	}
	
	/*
	private method: __mapPaymentsDraw
	maps payments to players, in the case of a draw
	*/
	private Map<Player, Integer> __mapPaymentsDraw(){
		Map<Player, Integer> playerPaymentMap = new HashMap<Player, Integer>();
		
		for (Player p: mPlayerArray) playerPaymentMap.put(p, 0);
		return playerPaymentMap;
	}
	
	
	
	
	
	/*
	private method: __dealHands
	deals players their starting hands
	*/
	private void __dealHands(){
		
		if (DEBUG_LOAD_DEBUG_WALL) mWall.DEMOloadDebugWall();	//DEBUG
		
		//get starting hands (as lists of tiles)
		TileList tilesE = new TileList(), tilesS = new TileList(), tilesW = new TileList(), tilesN = new TileList();
		mWall.getStartingHands(tilesE, tilesS, tilesW, tilesN);
		
		//add the tiles to the players' hands
		for(Tile t: tilesE) p1.addTileToHand(t);
		for(Tile t: tilesS) p2.addTileToHand(t);
		for(Tile t: tilesW) p3.addTileToHand(t);
		for(Tile t: tilesN) p4.addTileToHand(t);
		
		//sort the players' hands
		p1.sortHand(); p2.sortHand(); p3.sortHand(); p4.sortHand();
		__updateUI(GameplayEvent.START_OF_ROUND);
	}
	
	
	
	
	

	
	/*
	private method: __doPlayerTurn
	handles player p's turn, and gets the other players' reactions to the p's turn
	
	
	if (player needs to draw): give player a tile
	if (round is over): return
	
	loop while (the player has not chosen a discard)
		let the player do something
	end loop
	
 	display what the player discarded
 	get the other players' reactions to the discarded tile (the players will "make a call", the call won't actually be handled yet)
 	
	if the wall is not empty and no one made a call, move to the next player's turn
	*/
	private void __doPlayerTurn(Player p){
		
		//~~~~~~handle drawing a tile
		//if the player needs to draw a tile, draw a tile
		if (p.needsDraw()){
			__givePlayerTile(p);
		}
		else __updateUI(GameplayEvent.PLACEHOLDER);
		
		
		//return early if the round is over (4kan or washout)
		if (roundIsOver()) return;
		
		
		//~~~~~~get player's discard (ankans, riichi, and such are handled inside here)
		//loop until the player has chosen a discard
		//loop until the player stops making kans
		do{
			Tile discardedTile = p.takeTurn();
			mRoundTracker.setMostRecentDiscard(discardedTile);
			
			//if the player made an ankan or minkan, they need a rinshan draw
			if (p.needsDrawRinshan()){

				GameplayEvent kanEvent = GameplayEvent.DECLARED_OWN_KAN;
				kanEvent.setExclamation(Exclamation.OWN_KAN, mRoundTracker.getSeatNumber(p));
				__updateUI(kanEvent);
				__updateUI(GameplayEvent.MADE_OWN_KAN);
				
				//give player a rinshan draw
				__givePlayerTile(p);
				
			}
			
			if (p.turnActionCalledTsumo()){
				GameplayEvent tsumoEvent = GameplayEvent.DECLARED_TSUMO;
				tsumoEvent.setExclamation(Exclamation.TSUMO, mRoundTracker.getSeatNumber(p));
				__updateUI(tsumoEvent);
				mRoundTracker.setResultVictory(p);
			}
			
		}
		while (p.turnActionChoseDiscard() == false && mRoundResult.isOver() == false);
		
		
		
		//return early if the round is over (tsumo or 4kan or 4riichi or kyuushu)
		if (roundIsOver()) return;
		
		
		//show the human player their hand, show the discarded tile and the discarder's pond
		__updateUI(GameplayEvent.DISCARDED_TILE);
		
		
		//~~~~~~get reactions from the other players
		mRoundTracker.neighborShimochaOf(p).reactToDiscard(mRoundTracker.getMostRecentDiscard());
		mRoundTracker.neighborToimenOf(p).reactToDiscard(mRoundTracker.getMostRecentDiscard());
		mRoundTracker.neighborKamichaOf(p).reactToDiscard(mRoundTracker.getMostRecentDiscard());
		
		
		
		if (!mRoundTracker.callWasMadeOnDiscard()){
			
			//update turn indicator
			if (!mRoundTracker.checkIfWallIsEmpty())
				mRoundTracker.nextTurn();
		}
	}
	
	
	
	
	
	
	
	
	
	//gives a player a tile from the wall or dead wall
	private void __givePlayerTile(Player p){
		
		Tile drawnTile = null;
		if (!p.needsDraw()) return;
		
		//draw from wall or dead wall, depending on what player needs
		if (p.needsDrawNormal()){
			
			if (mRoundTracker.checkIfWallIsEmpty()){
				//no tiles left in wall, round over
				return;
			}
			else{
				drawnTile = mWall.takeTile();
			}
		}
		else if (p.needsDrawRinshan()){
			
			//check if too many kans have been made before making a rinshan draw
			if (mRoundTracker.checkIfTooManyKans()){
				//too many kans, round over
				return;
			}
			else{
				drawnTile = mWall.takeTileFromDeadWall();
				__updateUI(GameplayEvent.NEW_DORA_INDICATOR);
			}			
		}
		
		
		//add the tile to the player's hand
		p.addTileToHand(drawnTile);
		__updateUI(GameplayEvent.DREW_TILE);
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	private method: __handleReaction
	handles a call made on a discarded tile
	
	input: t is the discarded tile
	
	
	priorityCaller = figure out who made the call (and decide priority if >1 calls)
	if (caller called ron)
		handle Ron
	else
		let the caller make the meld with the tile
		show who called the tile, and what they called 
	end if
	
	if (multiple players tried to call)
		display other callers who got bumped by priority
	end if
	
	whoseTurn = priorityCaller's turn
	reaction = NO_REACTION
	*/
	private void __handleReaction(){
		
		
		//get the discarded tile
		Tile discardedTile = mRoundTracker.currentPlayer().getLastDiscard();
		
		
		
		//figure out who called the tile, and if multiple players called, who gets priority
		Player priorityCaller = __whoCalled();
		
		//remove the tile from the discarder's pond (because it is being called), unless the call was Ron
		if (!priorityCaller.calledRon()) mRoundTracker.currentPlayer().removeTileFromPond();
		
		
		
		//show the call
		GameplayEvent callEvent = GameplayEvent.CALLED_TILE;
		callEvent.setExclamation(priorityCaller.getCallStatusExclamation(), mRoundTracker.getSeatNumber(priorityCaller));
		__updateUI(callEvent);
		
		
		//give the caller the discarded tile so they can make their meld
		//if the caller called Ron, handle that instead
		if (priorityCaller.calledRon()){
			
			mRoundTracker.setResultVictory(priorityCaller);
			
		}
		else{
			
			//make the meld
			priorityCaller.makeMeld(discardedTile);
			__updateUI(GameplayEvent.MADE_OPEN_MELD);
		}
		
		
		//it is now the calling player's turn (if the round isn't over)
		if (!roundIsOver())
			mRoundTracker.setTurn(priorityCaller);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	private method: __whoCalled
	decides who gets to call the tile
	
	
	check if only one player tried to call
	if (only one player made a call)
		return that player
	else (>1 player made a call)
		return the player with greater priority
		(ron > pon/kan > chi, break ron tie by seat order)
	end if
	
	callingPlayer = figure out who made the call (decide priority if >1 calls)
	handle the call
	show the result of the call
	
	whoseTurn = the player who made the call's turn
	reaction = NO_REACTION
	*/
	private Player __whoCalled(){
		
		Player callingPlayer = null;
		Player callerPon = null, callerRon = null;
		
		int numCallers = 0;
		for (Player p: mPlayerArray)
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
			for (int i = mPlayerArray.length - 1; i >= 0 ; i--){
				if (mPlayerArray[i].called() && !mPlayerArray[i].calledChi())
					if (mPlayerArray[i].calledPon() || mPlayerArray[i].calledKan())
						callerPon = mPlayerArray[i];
					else
						callerRon = mPlayerArray[i];
			}
			
			//return the first ron caller, or return the pon caller if there was no ron caller
			if (callerRon != null) return callerRon;
			return callerPon;
		}
	}
	
	
	
	
	
	
	//accessors
	public Wind getRoundWind(){return mRoundWind;}
	public int getRoundNum(){return mRoundNum;}
	public int getRoundBonusNum(){return mRoundBonusNum;}
	
	public boolean roundIsOver(){return mRoundResult.isOver();}
	public boolean endedWithVictory(){return mRoundResult.isVictory();}
	public String getWinningHandString(){return mRoundResult.getAsStringWinningHand();}
	public RoundResultSummary getResultSummary(){return mRoundResult.getSummary();}
	
	public boolean qualifiesForRenchan(){return mRoundTracker.qualifiesForRenchan();}
	
	
	
	/*
	method: displayRoundResult
	displays the result of the current round
	*/
	public void displayRoundResult(){
		
		__updateUI(GameplayEvent.END_OF_ROUND);
		
		Pauser.pauseFor(sleepTimeRoundEnd);
	}
	
	
	
	private void __updateUI(GameplayEvent event){
		if (mUI != null) mUI.displayEvent(event);
		
		if (!event.isExclamation() && event != GameplayEvent.PLACEHOLDER) mPauser.pauseWait();
	}
	
	
	
	public void setOptionFastGameplay(boolean doFastGameplay){
		
		mDoFastGameplay = doFastGameplay;

		final int DEAFULT_SLEEPTIME = 400;
		final int DEAFULT_SLEEPTIME_EXCLAMATION = 1500;
//		final int DEAFULT_SLEEPTIME_ROUND_END = 2000;
		final int DEAFULT_SLEEPTIME_ROUND_END = 5000;
		
		final int FAST_SLEEPTIME = 0;
		final int FAST_SLEEPTIME_EXCLAMATION = 0;
//		final int FAST_SLEEPTIME_EXCLAMATION = DEAFULT_SLEEPTIME_EXCLAMATION;
		final int FAST_SLEEPTIME_ROUND_END = 0;
//		final int FAST_SLEEPTIME_ROUND_END = DEAFULT_SLEEPTIME_ROUND_END;
		
		
		if (mDoFastGameplay){
			sleepTime = FAST_SLEEPTIME;
			sleepTimeExclamation = FAST_SLEEPTIME_EXCLAMATION;
			sleepTimeRoundEnd = FAST_SLEEPTIME_ROUND_END;
		}
		else{
			sleepTime = DEAFULT_SLEEPTIME;
			sleepTimeExclamation = DEAFULT_SLEEPTIME_EXCLAMATION;
			sleepTimeRoundEnd = DEAFULT_SLEEPTIME_ROUND_END;
		}
		
		mPauser = new Pauser(sleepTime);
		if (mUI != null) mUI.setSleepTimeExclamation(sleepTimeExclamation);
//		if (mTviewer != null) mTviewer.setSleepTimeExclamation(sleepTimeExclamation);
//		if (mTextinterface != null) mTextinterface.setSleepTimeExclamation(0);
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Round)!");
		
		System.out.println("Launching Table...");
		Table.main(null);
	}
	

}
