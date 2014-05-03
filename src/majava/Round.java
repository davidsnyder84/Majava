package majava;

import utility.Pauser;
import majava.graphics.TableViewer;
import majava.tiles.Tile;




/*
Class: Round

data:
	p1, p2, p3, p4 - the four players who will play the round
	mPlayerArray - an array containing the four players
	
	mWall - wall of tiles, includes the dead wall
	
	mRoundTracker - tracks and manages information on the round
	mTviewer - TableViewer GUI to display the game and get input from the human player
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
public class Round {
	
//	private static final int DEFAULT_NUM_PLAYERS = 4;
	
	private static final Wind DEFAULT_ROUND_WIND = Wind.EAST;
	private static final int DEFAULT_ROUND_NUM = 1;
	private static final int DEFAULT_ROUND_BONUS_NUM = 0;
	
	//for debug use
	private static final boolean DEBUG_LOAD_DEBUG_WALL = false;
	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	
	
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	private Wall mWall;
	
	private RoundTracker mRoundTracker;
	private TableViewer mTviewer;
	private Pauser mPauser;
	
	
	private Wind mRoundWind;
	private int mRoundNum;
	private int mRoundBonusNum;
	
	
	//options
	private boolean mDoFastGameplay;
	private int sleepTime, sleepTimeExclamation, sleepTimeRoundEnd;
	
	
	/*
	Constructor
	initializes a new round to make it ready for playing
	
	creates the wall
	initializes round and game info
	*/
	public Round(TableViewer tviewer, Player[] playerArray, Wind roundWind, int roundNum, int roundBonusNum){
		
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
		
		
		mTviewer = tviewer;
		
		//initialize Round Tracker
		mRoundTracker = new RoundTracker(tviewer, mRoundWind,mRoundNum,mRoundBonusNum,  mWall,  p1,p2,p3,p4);
		
		setOptionFastGameplay(DEFAULT_DO_FAST_GAMEPLAY);
	}
	public Round(TableViewer tviewer, Player[] playerArray, Wind roundWind, int roundNum){this(tviewer, playerArray, roundWind, roundNum, DEFAULT_ROUND_BONUS_NUM);}
	public Round(TableViewer tviewer, Player[] playerArray){this(tviewer, playerArray, DEFAULT_ROUND_WIND, DEFAULT_ROUND_NUM);}
	
	
	
	
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
		
		if (mRoundTracker.roundIsOver()){
			System.out.println("----Error: Round is already over, cannot play");
			return;
		}
		
		
		//------------------------------------------------DEBUG INFO
		if (DEBUG_LOAD_DEBUG_WALL) mWall.loadDebugWall();
		System.out.println(mWall.toString() + "\n\n\n");mWall.printDoraIndicators();
		//------------------------------------------------DEBUG INFO
		
		
		
		
		//deal starting hands
		__dealHands();
		

		//------------------------------------------------DEBUG INFO
		mWall.printWall();
		mWall.printDoraIndicators();
		
		p1.showHand();p2.showHand();p3.showHand();p4.showHand();
		System.out.println("\n\n\n");
		//------------------------------------------------DEBUG INFO
		
		
		
		//game loop, loop until the round is over
		while (!mRoundTracker.roundIsOver()){

			//handle player turns
			__doPlayerTurn(mRoundTracker.currentPlayer());
			

			//handle reactions here
			if (!mRoundTracker.roundIsOver())
				if (mRoundTracker.callWasMadeOnDiscard())
					__handleReaction();
			
		}
		
		//display end of round result
		displayRoundResult();
	}
	
	
	
	
	/*
	private method: __dealHands
	deals players their starting hands
	*/
	private void __dealHands(){

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
		__updateWindow();
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
		
		
		//return early if the round is over (4kan or washout)
		if (mRoundTracker.roundIsOver()) return;
		
		
		//~~~~~~get player's discard (ankans, riichi, and such are handled inside here)
		//loop until the player has chosen a discard
		//loop until the player stops making kans
		do{
			Tile discardedTile = p.takeTurn(mTviewer);
			mRoundTracker.setMostRecentDiscard(discardedTile);
			
			//if the player made an ankan or minkan, they need a rinshan draw
			if (p.needsDrawRinshan()){
				
				__showExclamation("Kan!", p);
				
				//give player a rinshan draw
				__givePlayerTile(p);
				
			}
			
			if (p.turnActionCalledTsumo()){
				__showExclamation("Tsumo", p);
				mRoundTracker.setResultVictory(p);
			}
			
		}
		while (p.turnActionChoseDiscard() == false && mRoundTracker.roundIsOver() == false);
//		while (p.turnActionMadeKan());
		
		
		
		
		//return early if the round is over (tsumo or 4kan or 4riichi or kyuushu)
		if (mRoundTracker.roundIsOver()) return;
		
		
		
		
		//show the human player their hand
		__showHandsOfHumanPlayers();
		
		//show the discarded tile and the discarder's pond
		System.out.println("\n\n\tTiles left: " + mWall.getNumTilesLeftInWall());
		System.out.println("\t" + p.getSeatWind() + " Player's discard: ^^^^^" + mRoundTracker.getMostRecentDiscard().toString() + "^^^^^");
		p.showPond();
//		__showDiscard();
		__updateWindow();
		
		
		
		
		//~~~~~~get reactions from the other players
		mRoundTracker.neighborShimochaOf(p).reactToDiscard(mRoundTracker.getMostRecentDiscard(), mTviewer);
		mRoundTracker.neighborToimenOf(p).reactToDiscard(mRoundTracker.getMostRecentDiscard(), mTviewer);
		mRoundTracker.neighborKamichaOf(p).reactToDiscard(mRoundTracker.getMostRecentDiscard(), mTviewer);
		
		
		
		if (mRoundTracker.callWasMadeOnDiscard() == false){
			
			//update turn indicator
			if (mRoundTracker.checkIfWallIsEmpty() == false)
				mRoundTracker.nextTurn();
		}
	}
	
	
	
	
	
	
	
	
	
	//gives a player a tile from the wall or dead wall
	private void __givePlayerTile(Player p){
		
		Tile drawnTile = null;
		if (!p.needsDraw()) return;
		
		mWall.printWall(); //debug
		
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
				mWall.printDeadWall();
				mWall.printDoraIndicators();	//debug
			}			
		}
		
		
		//add the tile to the player's hand
		p.addTileToHand(drawnTile);
		__updateWindow();
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
		
		
		
		//show who called the tile 
		System.out.println("\n*********************************************************" + 
							"\n**********" + priorityCaller.getSeatWind() + " Player called the tile (" + discardedTile.toString() + ")! " + priorityCaller.getCallStatusString() + "!!!**********" + 
							"\n*********************************************************");
		//show the call
		__showExclamation(priorityCaller.getCallStatusString(), priorityCaller);
		
		
		
		
		//give the caller the discarded tile so they can make their meld
		//if the caller called Ron, handle that instead
		if (priorityCaller.calledRon()){
			
			System.out.println("\n*****RON! RON RON! RON! RON! ROOOOOOOOOOOOOOOOOOOOOON!");
			mRoundTracker.setResultVictory(priorityCaller);
			
			
		}
		else{
			
			//make the meld
			priorityCaller.makeMeld(discardedTile);
			__updateWindow();
			//meld has been made
		}
		
		

		//if multiple players called, show if someone got bumped by priority 
		for (Player p: mPlayerArray)
			if (p.called() && p != priorityCaller)
				System.out.println("~~~~~~~~~~" + p.getSeatWind() + " Player tried to call " + p.getCallStatusString() + ", but got bumped by " + priorityCaller.getSeatWind() + "!");
		System.out.println();
		
		
		//it is now the calling player's turn (if the round isn't over)
		if (!mRoundTracker.roundIsOver())
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
		
		boolean onlyOnePlayerCalled = true;	//this is set to true by default, because we know at LEAST one player called
		boolean alreadyFoundACall = false;	//this is false until a call is found
		
		
		for (Player p: mPlayerArray){
			if (p.called()){
				if (alreadyFoundACall) onlyOnePlayerCalled = false;
				
				callingPlayer = p;
				alreadyFoundACall = true;
			}
		}
		
		
		//if only one player called, return that player
		if (onlyOnePlayerCalled){
			return callingPlayer;
		}
		else {
			
			//else, if more than one player called, figure out who has more priority
			/*
			can 2 players call pon?: no, not enough tiles
			can 2 players call kan?: no, not enough tiles
			can 2 players call chi?: no, only shimocha can chi
			1 chi, 1 pon?: yes
			1 chi, 1 kan?: yes
			
			can 2 players call ron?: yes
			can 3 players call ron?: yes
			1 chi, 1 ron?: yes
			1 chi, 2 ron?: yes
			1 chi, 1 pon, 1 ron?: yes
			
			ron > pon/kan > chi
			pon/kan > chi
			
			2 rons: decide by closest seat order
			
			if >1 players called, and one of them called chi, the chi caller will NEVER have higher priority
			//if 1 chi and 1 pon/kan, or 1 chi and 1 ron, the pon/kan/ron always gets higher priority
			//so the chi is not even considered
			*/

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
			else return callerPon;
		}
	}
	
	
	
	
	
	
	/*
	private method: __showHandsOfHumanPlayers
	shows the hands of all human players in the game
	*/
	private void __showHandsOfHumanPlayers(){for (Player p: mPlayerArray) if (p.controllerIsHuman()) p.showHand();}
	
	
	
	
	
	
	
	//accessors
	public Wind getRoundWind(){return mRoundWind;}
	public int getRoundNum(){return mRoundNum;}
	public int getRoundBonusNum(){return mRoundBonusNum;}
	
	public boolean roundIsOver(){return mRoundTracker.roundIsOver();}
	public boolean endedWithVictory(){return mRoundTracker.roundEndedWithVictory();}
	public String getWinningHandString(){return mRoundTracker.getWinningHandString();}
	
	
	
	/*
	method: displayRoundResult
	displays the result of the current round
	*/
	public void displayRoundResult(){
		
		for (Player p: mPlayerArray) p.showHand();

		mRoundTracker.printRoundResult();
		
		__updateWindow();
		
		Pauser.pauseFor(sleepTimeRoundEnd);
	}
	
	
	
	
	
	private void __updateWindow(){
		mTviewer.updateEverything();
		//pause for dramatic effect
		mPauser.pauseWait();
	}
	
	
	private void __showExclamation(String exclamation, Player p){
		mTviewer.showExclamation(exclamation, mRoundTracker.getSeatNumber(p), sleepTimeExclamation);
	}
	
	
	
	
	
	public void setOptionFastGameplay(boolean doFastGameplay){
		
		mDoFastGameplay = doFastGameplay;

		final int DEAFULT_SLEEPTIME = 400;
		final int DEAFULT_SLEEPTIME_EXCLAMATION = 1500;
		final int DEAFULT_SLEEPTIME_ROUND_END = 5000;
		
		final int FAST_SLEEPTIME = 0;
		final int FAST_SLEEPTIME_EXCLAMATION = 0;
		final int FAST_SLEEPTIME_ROUND_END = 0;
		
		
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
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Round)!");
		
		System.out.println("Launching Table...");
		Table.main(null);
	}
	

}
