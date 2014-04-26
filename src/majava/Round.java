package majava;

import utility.Pauser;
import majava.graphics.TableViewer;
import majava.tiles.Tile;




/*
Class: Round

data:
	p1, p2, p3, p4 - four players. p1 is always east, p2 is always south, etc. 
	mWall - wall of tiles, includes the dead wall
	
	mGameType - length of game being played (single, tonpuusen, or hanchan)
	
	mRoundWind - the prevailing wind of the current round ('E' or 'S')
	mWhoseTurn - whose turn it is (1,2,3,4 corresponds to E,S,W,N)
	mReaction - will be true if no calls were made during a turn, will be false if a call was made
	mGameIsOver - will be true if the game is over, false if not
	mGameResult - the specific result of the game (reason for a draw game, or who won), is UNDECIDED if game is not over
	
methods:
	mutators:
 	
 	accessors:
	
	other:
*/
public class Round {
	
	public static final int DEFAULT_NUM_PLAYERS = 4;
	public static final char DEFAULT_ROUND_WIND = 'E';
	public static final int DEFAULT_ROUND_NUM = 1;
	public static final int DEFAULT_ROUND_BONUS_NUM = 0;
	
	public static final int TIME_TO_SLEEP = 0;
	public static final int TIME_TO_SLEEP_EXCLAMATION = 0;
	public static final int TIME_TO_SLEEP_END_OF_ROUND = 0;
//	public static final int TIME_TO_SLEEP = 200;
//	public static final int TIME_TO_SLEEP_EXCLAMATION = 1500;
//	public static final int TIME_TO_SLEEP_END_OF_ROUND = 3000;
	
	
	//for debug use
	public static final boolean DEBUG_WAIT_AFTER_COMPUTER = true;
	public static final boolean DEBUG_LOAD_DEBUG_WALL = false;
	
	
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	private Wall mWall;
	
	private RoundTracker mRoundTracker;
	private TableViewer mTviewer;
	private Pauser mPauser;
	
	
	private char mRoundWind;
	private int mRoundNum;
	private int mRoundBonusNum;
	
	
	
	/*
	Constructor
	initializes a new round to make it ready for playing
	
	creates the wall
	initializes round and game info
	*/
	public Round(TableViewer tviewer, Player[] playerArray, char roundWind, int roundNum, int roundBonusNum){
		
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
		mPauser = new Pauser(TIME_TO_SLEEP);
	}
	public Round(TableViewer tviewer, Player[] playerArray, char roundWind, int roundNum){this(tviewer, playerArray, roundWind, roundNum, DEFAULT_ROUND_BONUS_NUM);}
	public Round(TableViewer tviewer, Player[] playerArray){this(tviewer, playerArray, DEFAULT_ROUND_WIND, DEFAULT_ROUND_NUM);}
	
	
	
	
	/*
	method: play
	plays a new game of mahjong with four new players
	 
	 
	before play is called:
		wall/deadwall is set up
		round wind = East
	 
	deal hands
	 
	whoseTurn = 1;
	while (game is not over)
		mReaction = no reaction;
		
		//handle player turns here
	 	if (it is p1's turn && game is not over): do p1's turn
	 	if (it is p2's turn && no calls were made && game is not over): do p2's turn
	 	if (it is p3's turn && no calls were made && game is not over): do p3's turn
	 	if (it is p4's turn && no calls were made && game is not over): do p4's turn


	 	//handle reactions here
	 	if (reaction): handleReaction
	end while
	
	display endgame result
	*/
	public void play(){
		
		//------------------------------------------------DEBUG INFO
		if (DEBUG_LOAD_DEBUG_WALL) mWall.loadDebugWall();
		System.out.println(mWall.toString() + "\n\n\n");mWall.printDoraIndicators();
		//------------------------------------------------DEBUG INFO
		
		
		
		
		//deal and sort hands
		mWall.dealHands(p1, p2, p3, p4);
		p1.sortHand(); p2.sortHand(); p3.sortHand(); p4.sortHand();
		updateWindow();
		

		//------------------------------------------------DEBUG INFO
		mWall.printWall();
		mWall.printDoraIndicators();
		
		p1.showHand();p2.showHand();p3.showHand();p4.showHand();
		System.out.println("\n\n\n");
		//------------------------------------------------DEBUG INFO
		
		
		
		//game loop, loop until the round is over
		while (!mRoundTracker.roundIsOver()){

			//handle player turns
			doPlayerTurn(mRoundTracker.currentPlayer());
			

			//handle reactions here
			if (!mRoundTracker.roundIsOver())
				if (mRoundTracker.callWasMadeOnDiscard())
					handleReaction();
			
		}
		
		//display end of round result
		displayRoundResult();
	}
	
	
	
	

	
	/*
	method: doPlayerTurn
	handles a player's turn, and the other players' reactions to the player's turn
	
	input: p is the player whose turn it is
	
	returns the tile that the player discarded
	
	
	if (player needs to draw)
		take tile from wall or dead wall depending on what player needs
		if (there were no tiles left in the wall to take)
			gameIsOver = true, result = washout
			return null
		else
			add the tile to the player's hand
		end if
	end if
	
 	discardedTile = player's chosen discard
 	display what the player discarded
 	get the other players' reactions to the discarded tile
 	(the players will "make a call", the call won't actually be handled yet)
 	
	whoseTurn++
	return discardedTile
	*/
	private void doPlayerTurn(Player p){
		
		//~~~~~~handle drawing a tile
		//if the player needs to draw a tile, draw a tile
		if (p.needsDraw()){
			givePlayerTile(p);
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
				
				showExclamation("Kan!", p);
				
				//give player a rinshan draw
				givePlayerTile(p);
				
			}
			
			if (p.turnActionCalledTsumo()){
				showExclamation("Tsumo", p);
				mRoundTracker.setResultVictory(p);
			}
			
		}
		while (p.turnActionChoseDiscard() == false && mRoundTracker.roundIsOver() == false);
//		while (p.turnActionMadeKan());
		
		
		
		
		//return early if the round is over (tsumo or 4kan or 4riichi or kyuushu)
		if (mRoundTracker.roundIsOver()) return;
		
		
		
		
		//show the human player their hand
		showHandsOfHumanPlayers();
		
		//show the discarded tile and the discarder's pond
		System.out.println("\n\n\tTiles left: " + mWall.getNumTilesLeftInWall());
		System.out.println("\t" + p.getSeatWind() + " Player's discard: ^^^^^" + mRoundTracker.getMostRecentDiscard().toString() + "^^^^^");
		p.showPond();
		updateWindow();
		
		
		
		
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
	private void givePlayerTile(Player p){
		
		Tile drawnTile = null;
		if (p.needsDraw() == false) return;
		
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
		updateWindow();
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	method: handleReaction
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
	private void handleReaction(){
		
		
		//remove the tile from the discarder's pond (because it is being called)
		Tile discardedTile = mRoundTracker.currentPlayer().removeTileFromPond();
		
		
		
		//figure out who called the tile, and if multiple players called, who gets priority
		Player priorityCaller = whoCalled();
		
		//show the call
		showExclamation(priorityCaller.getCallStatusString(), priorityCaller);
		
		
		//give the caller the discarded tile so they can make their meld
		//if the caller called Ron, handle that instead
		if (priorityCaller.calledRon()){
			
			System.out.println("\n*****RON! RON RON! RON! RON! ROOOOOOOOOOOOOOOOOOOOOON!");
			//handle here
			
			mRoundTracker.setResultVictory(priorityCaller);
			
			
		}
		else{
			
			//make the meld
			priorityCaller.makeMeld(discardedTile);
			updateWindow();
			//meld has been made
			

			//show who called the tile 
			System.out.println("\n*********************************************************" + 
								"\n**********" + priorityCaller.getSeatWind() + " Player called the tile (" + discardedTile.toString() + ")! " + priorityCaller.getCallStatusString() + "!!!**********" + 
								"\n*********************************************************");
		}
		
		

		//if multiple players called, show if someone got bumped by priority 
		for (Player p: mPlayerArray)
			if (p.called() && p != priorityCaller)
				System.out.println("~~~~~~~~~~" + p.getSeatWind() + " Player tried to call " + p.getCallStatusString() + ", but got bumped by " + priorityCaller.getSeatWind() + "!");
		System.out.println();
		
		
		//it is now the calling player's turn (if the round isn't over)
		if (!mRoundTracker.roundIsOver())
			mRoundTracker.setTurn(priorityCaller.getPlayerNumber());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	method: whoCalled
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
	private Player whoCalled(){
		
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
	method: showHandsOfHumanPlayers
	shows the hands of all human players in the game
	*/
	private void showHandsOfHumanPlayers(){for (Player p: mPlayerArray) if (p.controllerIsHuman()) p.showHand();}
	
	
	
	
	//accessors
	public char getRoundWind(){return mRoundWind;}
	public boolean roundIsOver(){return mRoundTracker.roundIsOver();}
	
	public String getWinningHandString(){return mRoundTracker.getWinningHandString();}
	public boolean endedWithVictory(){return mRoundTracker.roundEndedWithVictory();}
	
	
	
	/*
	method: displayRoundResult
	displays the result of the current round
	*/
	public void displayRoundResult(){
		
		mRoundTracker.printRoundResult();
		
		updateWindow();
		
		for (Player p: mPlayerArray) p.showHand();
		
		Pauser.pauseFor(TIME_TO_SLEEP_END_OF_ROUND);
	}
	
	
	
	
	
	private void updateWindow(){
		mTviewer.updateEverything();
		//pause for dramatic effect (like after a computer's turn)
		mPauser.pauseWait();
	}
	
	
	private void showExclamation(String exclamation, Player p){
		mTviewer.showExclamation(exclamation, mRoundTracker.getSeatNumber(p), TIME_TO_SLEEP_EXCLAMATION);
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Round)!");
		
		System.out.println("Launching Table...");
		Table.main(null);
	}
	

}
