package majava;

import java.util.ArrayList;
import java.util.Scanner;

import majava.graphics.TableViewer;
import majava.tiles.Tile;

/*
Class: Player
represents a single player in the game

data:
	mHand - the player's hand (melds are also in here)
	mPond - the player's pond of discards
	mPoints - how many points the player has
	
	mSeatWind - the player's seat wind (ESWN)
	mController - who is controlling the player (human or computer)
	
	mCallStatus - the player's call (reaction) to the most recent disacrd (chi, pon, kan, ron, or none)
	mDrawNeeded - the type of draw the player needs for their next turn (normal draw, kan draw, or no draw) 
	
	mHoldingRinshanTile - is true if the player is holding a rinshan tile that they drew this turn, false otherwise
	mRiichiStatus - is true if the player has declared riichi, false if not
	mFuritenStatus - is true if the player is in furiten status, false if not
	mTenpaiStatus = is true if the player's hand is in tenpai
	
	linkShimocha - a link to the player's shimocha (player to the right)
	linkToimen - a link to the player's toimen (player directly across)
	linkKamicha - a link to the player's kamicha (player to the left)
	

methods:
	constructors:
	2-arg - takes seat and controller, initializes wall and pond, and status info
	1-arg - takes seat, sets default controller
	no-arg - sets default seat and controller
	
	
	mutators:
	setController - sets the player's controller
 	setSeatWind - sets the player's seat wind
	setShimocha, setToimen, setKamicha, setNeighbors - set links to the player's neighbors
	pointsIncrease, pointsDecrease - increase/decrease the player's points by an integer amount
 	
 	accessors:
 	getHandSize - returns hand size
	getSeatWind - return seat wind
	getPlayerNumber - returns (1,2,3,4) corresponding to (E,S,W,N)
	getKamicha, getToimen, getShimocha - returns links to the player's neighbors
	getPoints - returns how many points the player has
	
	getRiichiStatus - returns true if the player is in riichi status
	checkFuriten - returns true if the player is in furiten status
	checkRinshan - returns true if the player is holding a rinshan tile that they drew this turn
	checkTenpai - returns true if the player is in tenpai
	getNumMeldsMade - returns the number of melds the player has made (open melds and ankans)
	
	called - returns true if the player has called a discarded tile
	checkCallStatus - returns the specific type of call the player has made
	checkCallStatusString - returns the specific type of call the player has made, as a string
	checkDrawNeeded - returns the type of draw the player needs (normal draw, kan draw, or none)

	
	
	private:
	__discardTile - gets a player's discard choice, discards it, and returns the tile
	__askSelfForDiscard- asks a player which tile they want to discard, returns their choice
	__askDiscardHuman - asks a human player which tile they want to discard, returns their choice
	__askDiscardCom - asks a computer player which tile they want to discard, returns their choice
	
	__askSelfForReaction - asks the player how they want to react to the discarded tile
	__askReactionHuman - asks a human player how they want to react to the discarded tile
	__askReactionCom - asks a computer player how they want to react to the discarded tile
	
	__putTileInPond - adds a tile to the player's pond
	__ableToCallTile - checks if the player is able to make a call on Tile t
	
	
	other:
	addTileToHand - add a tile to the player's hand
	takeTurn - walks the player through their discard turn, returns their discard
	reactToDiscard - shows a player a discarded tile, and gets their reaction (call or no call) to it
	
	showHand - display the player's hand
	showPond - display the player's pond
	getPondAsString - get the player's pond as a string
	
	
	static:
	findKamichaOf - receives a seat wind (as a character), returns the seat wind of the received wind's kamicha
*/
public class Player {
	
	public static final char SEAT_UNDECIDED = 'U';
	public static final char SEAT_EAST = 'E';
	public static final char SEAT_SOUTH = 'S';
	public static final char SEAT_WEST = 'W';
	public static final char SEAT_NORTH = 'N';
	public static final char SEAT_DEFAULT = SEAT_UNDECIDED;
	public static final String SEAT_WINDS = "ESWN";

	public static final char CONTROLLER_UNDECIDED = 'u';
	public static final char CONTROLLER_HUMAN = 'h';
	public static final char CONTROLLER_COM = 'c';
	public static final char CONTROLLER_DEFAULT = CONTROLLER_UNDECIDED;

	private static final int COM_BEHAVIOR_DISCARD_LAST = 1;
	private static final int COM_BEHAVIOR_DISCARD_FIRST = 2;
	private static final int COM_BEHAVIOR_DISCARD_RANDOM = 3;
	private static final int COM_BEHAVIOR_DISCARD_DEFAULT = COM_BEHAVIOR_DISCARD_LAST;
	
	public static final int POINTS_STARTING_AMOUNT = 25000;

	private static final int CALLED_NONE = 0;
	private static final int CALLED_CHI_L = 1;
	private static final int CALLED_CHI_M = 2;
	private static final int CALLED_CHI_H = 3;
	private static final int CALLED_PON = 4;
	private static final int CALLED_KAN = 5;
	private static final int CALLED_RON = 6;
	private static final int CALLED_CHI = 123;
	
	private static final int DRAW_NONE = 0;
	private static final int DRAW_NORMAL = 1;
	private static final int DRAW_RINSHAN = 3;
	
	
	private static final int TURN_ACTION_DISCARD = -10;
	private static final int TURN_ACTION_ANKAN = -20;
	private static final int TURN_ACTION_MINKAN = -30;
	private static final int TURN_ACTION_RIICHI = -40;
	private static final int TURN_ACTION_TSUMO = -50;
	private static final int NO_ACTION_CHOSEN = -1;
	private static final int NO_DISCARD_CHOSEN = -94564;
	
	
	public static final String PLAYERNAME_DEFAULT = "Kyoutarou";
	
	
	private static final boolean DEBUG_SKIP_PLAYER_CALL = false;
	private static final boolean DEBUG_COMPUTERS_MAKE_CALLS = true;
	private static final boolean DEBUG_COMPUTERS_MAKE_ACTIONS = false;
	
	
	
	
	
	private Hand mHand;
	private Pond mPond;
	private int mPoints;
	
	private char mSeatWind;
	private int mSeatNumber;
	private char mController;
	private String mPlayerName;
	
	private int mCallStatus;
	private int mDrawNeeded;
	private int mChosenDiscardIndex;
	private int mTurnAction;
	
	private Tile mLastDiscard;
	
	private boolean mHoldingRinshanTile;
	private boolean mRiichiStatus;
	private boolean mFuritenStatus;
	
	//private ArrayList<Tile> mWaits;
	
	private RoundTracker mRoundTracker;
	
	
	
	
	
	
	
	
	public Player(char seat, char controller, String pName){
		
		mSeatWind = seat;
		mController = controller;
		mPlayerName = pName;
		
		mHand = new Hand(mSeatWind);
		mPond = new Pond();
		mPoints = POINTS_STARTING_AMOUNT;
		
		mCallStatus = CALLED_NONE;
		mDrawNeeded = DRAW_NORMAL;
		
		mRiichiStatus = false;
		mFuritenStatus = false;
		mHoldingRinshanTile = false;
		//mTenpaiStatus = false;
		
		mLastDiscard = null;
	}
	public Player(char seat, char controller){
		this(seat, controller, PLAYERNAME_DEFAULT);
	}
	public Player(char seat){
		this(seat, CONTROLLER_DEFAULT);
	}
	public Player(){
		this(SEAT_DEFAULT);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	method: takeTurn
	walks the player through their discard turn
	
	returns the discarded tile if they chose a discard
	returns null if the player did not discard (they made a kan or tsumo)
	
	discardedTile = discard a tile
	
	if (discard was chosen)
		set drawNeeded = normal draw for next turn
		put discardedTile in the pond
		return discardedTile
	else
		return null (no discard chosen)
	end if
	*/
	public Tile takeTurn(TableViewer tviewer){
		
		mLastDiscard = null;
		mChosenDiscardIndex = NO_DISCARD_CHOSEN;
		
		//discard a tile
		__chooseTurnAction(tviewer);
		
		
		
		if (turnActionChoseDiscard()){
			
			//discard and return the chosen tile
			mLastDiscard = __discardChosenTile();
			return mLastDiscard;
		}
		else{
			
			if (turnActionMadeAnkan()) System.out.println("\n\n!!!!!OOOOOHBOY ANKAN\n!!!!");
			if (turnActionMadeMinkan()) System.out.println("\n\n!!!!!OOOOOHBOY MINKAN\n!!!!");
			if (turnActionRiichi()) System.out.println("\n\n!!!!!OOOOOHBOY RIICHI\n!!!!");
			if (turnActionCalledTsumo()) System.out.println("\n\n!!!!!OOOOOHBOY TSUMO\n!!!!");
			
			
			
			//ankan
			//minkan
			if (turnActionMadeKan()){
				
				//make the meld
				
				if (turnActionMadeAnkan()){
					mHand.makeMeldTurnAnkan();
				}
				else if (turnActionMadeMinkan()){
					mHand.makeMeldTurnMinkan();
				}
				
				
				mDrawNeeded = DRAW_RINSHAN;
			}
			
			
			
			//riichi
			
			
			
			//tsumo
			
			
			
			
			return null;
		}
	}
	
	
	
	//adds a tile to the pond
	private void __putTileInPond(Tile t){
		mPond.addTile(t);
	}
	
	
	//removes the most recent tile from the player's pond (because another player called it)
	public Tile removeTileFromPond(){return mPond.removeMostRecentTile();}
	
	
	
	
	private Tile __discardChosenTile(){
		
		Tile discardedTile = null;
		
		//remove the chosen discard tile from hand
		discardedTile = mHand.getTile(mChosenDiscardIndex);
		mHand.removeTile(mChosenDiscardIndex);
		
		//put the tile in the pond
		__putTileInPond(discardedTile);
		
		//set needed draw to normal, since we just discarded a tile
		mDrawNeeded = DRAW_NORMAL;

		//return the discarded tile
		return discardedTile;
	}
	
	
	
	
	/*
	private method: __turnAction
	gets the player's desired action for their turn (discard, kan, tsumo)
	
	returns the index of the player's chosen discard
	returns a negative value if the player did something other than discard
	
	
	chosenDiscardIndex = ask self which tile to discard
	return chosenDiscardIndex
	*/
	private int __chooseTurnAction(TableViewer tviewer){
		
		//ask player for which tile to discard
		int chosenAction = __askSelfForTurnAction(tviewer);
		
		//return the chosen tile index
		return chosenAction;
		
	}
	
	
	
	/*
	private method: __askSelfForTurnAction
	asks a player what they want to do on their turn
	(only asks and gets their choice, doesn't carry out the action)
	
	returns the index of the tile to discard, if the player chose a discard
	returns something negative otherwise
	
	if (controller is human) ask human and return choice
	else (controller is computer) ask computer and return choice
	*/
	private int __askSelfForTurnAction(TableViewer tviewer){
		if (controllerIsHuman()) return __askTurnActionHuman(tviewer);
		else return __askTurnActionCom();
	}
	
	
	
	/*
	private method: __askDiscardHuman
	asks a human player which tile they want to discard, returns their choice
	
	returns the index of the tile the player wants to discard
	
	
	chosenDiscard = ask user through keyboard
	chosenDiscard = user clicks on tile through GUI
	return chosenDiscard
	*/
	private int __askTurnActionHuman(TableViewer tviewer){
		
		int chosenAction = NO_ACTION_CHOSEN;
		int chosenDiscardIndex = NO_DISCARD_CHOSEN;
		
		//show hand
		showHand();
		tviewer.updateEverything();

		//get the player's desired action
		tviewer.getClickTurnAction();
		
		if (tviewer.resultClickTurnActionWasDiscard()){
			mTurnAction = TURN_ACTION_DISCARD;
			chosenDiscardIndex = tviewer.getResultClickedDiscard();
			mChosenDiscardIndex = chosenDiscardIndex - 1;	//adjust for index
			return mChosenDiscardIndex;
		}
		else{
			if (tviewer.resultClickTurnActionWasAnkan()) chosenAction = TURN_ACTION_ANKAN;
			if (tviewer.resultClickTurnActionWasMinkan()) chosenAction = TURN_ACTION_MINKAN;
			if (tviewer.resultClickTurnActionWasRiichi()) chosenAction = TURN_ACTION_RIICHI;
			if (tviewer.resultClickTurnActionWasTsumo()) chosenAction = TURN_ACTION_TSUMO;
			mTurnAction = chosenAction;
			return NO_DISCARD_CHOSEN;
		}
	}
	
	
	
	/*
	private method: __askDiscardCom
	asks a computer player which tile they want to discard, returns their choice
	
	returns the index of the tile the player wants to discard
	
	
	chosenDiscard = the last tile in the player's hand
	return chosenDiscard
	*/
	@SuppressWarnings("unused")
	private int __askTurnActionCom(){
		
		int chosenDiscardIndex;

		//always choose the last tile in the hand (most recently drawn one)
		chosenDiscardIndex = mHand.getSize() - 1;
		
		//always choose the first tile in the hand
		chosenDiscardIndex = 0;
		
		
		if (mSeatWind == 'N')chosenDiscardIndex = mHand.getSize() - 1;
		if (mSeatWind == 'E')chosenDiscardIndex = mHand.getSize() - 1;
		
		
		if (DEBUG_COMPUTERS_MAKE_ACTIONS && ableToTsumo()){
			mTurnAction = TURN_ACTION_TSUMO;
			return NO_DISCARD_CHOSEN;
		}
		else{
			mTurnAction = TURN_ACTION_DISCARD;
			mChosenDiscardIndex = chosenDiscardIndex;
			return mChosenDiscardIndex;
		}
	}
	
	
	public Tile getLastDiscard(){return mLastDiscard;}
	
	
	public boolean turnActionMadeKan(){return (turnActionMadeAnkan() || turnActionMadeMinkan());}
	public boolean turnActionMadeAnkan(){return (mTurnAction == TURN_ACTION_ANKAN);}
	public boolean turnActionMadeMinkan(){return (mTurnAction == TURN_ACTION_MINKAN);}
	public boolean turnActionCalledTsumo(){return (mTurnAction == TURN_ACTION_TSUMO);}
	public boolean turnActionChoseDiscard(){return (mTurnAction == TURN_ACTION_DISCARD);}
//	public boolean turnActionChoseDiscard(){return (mTurnAction == TURN_ACTION_DISCARD || mTurnAction == TURN_ACTION_RIICHI);}
	public boolean turnActionRiichi(){return (mTurnAction == TURN_ACTION_DISCARD || mTurnAction == TURN_ACTION_RIICHI);}
	
	
	//turn actions
	public boolean ableToAnkan(){return mHand.ableToAnkan();}
	public boolean ableToMinkan(){return mHand.ableToMinkan();}
	public boolean ableToRiichi(){return mHand.ableToRiichi();}
	public boolean ableToTsumo(){return mHand.ableToTsumo();}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	method: addTileToHand
	receives a tile, adds the tile to the player's hand
	
	add the tile to the player's hand
	set drawNeeded = none (since the player has just drawn)
	*/
	public void addTileToHand(Tile t){
		
		//set the tile's owner to be the player
		t.setOwner(mSeatWind);
		
		//add the tile to the hand
		mHand.addTile(t);
		
		//no longer need to draw
		mDrawNeeded = DRAW_NONE;
	}
	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand (for debug use)
	public void addTileToHand(int tileID){addTileToHand(new Tile(tileID));}
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	/*
	method: reactToDiscard
	shows a player a tile, and gets their reaction (call or no call) for it
	
	input: t is the tile that was just discarded, and the player has a chance to react to it
		   tviewer is the TableViewer GUI the player will click on
	
	returns the type of call the player wants to make on the tile (none, chi, pon, kan, ron)
	
	
	call status = none
	if (the player is able to call the tile)
		call status = ask self for a reaction to the tile
	end if
	return call status
	*/
	public boolean reactToDiscard(Tile t, TableViewer tviewer){
		mCallStatus = CALLED_NONE;
		
		//if able to call the tile, ask self for reaction
		if (__ableToCallTile(t)){
			
			//ask self for reaction
			//update call status
			mCallStatus = __askSelfForReaction(t, tviewer);
		}
		
		////////////////////WATCH THIS MOTHERFUCKER
		//////////////////I DONT KNOW WHAT THIS WILL DO
		//draw normally if no call
		if (mCallStatus == CALLED_NONE)
			mDrawNeeded = DRAW_NORMAL;
		
		return (mCallStatus != CALLED_NONE);
	}
	
	
	
	
	/*
	private method: __askSelfForReaction
	asks the player how they want to react to the discarded tile
	
	input: t is the tile that was just discarded, and the player has a chance to react to it
		   tviewer is the TableViewer GUI the player will click on
	
	returns the type of call the player wants to make on the tile (none, chi, pon, kan, ron)
	
	
	if (controller == human)
		call = ask human
	else if (controller == computer)
		call = ask computer
	end if
	return call
	*/
	private int __askSelfForReaction(Tile t, TableViewer tviewer){
		int call = CALLED_NONE;
		
		if (mController == CONTROLLER_HUMAN) call = __askReactionHuman(t, tviewer);
		else call = __askReactionCom(t);
		
		return call;
	}
	
	
	/*
	private method: __askReactionHuman
	asks a human player how they want to react to the discarded tile
	
	input: t is the tile that was just discarded, and the player has a chance to react to it
		   tviewer is the TableViewer GUI the player will click on
	returns the type of call the player wants to make on the tile (none, chi, pon, kan, ron)
	
	figure out which calls are possible
	choice = get player's choice through GUI
	call = decide based on player's choice
	return call
	*/
	private int __askReactionHuman(Tile t, TableViewer tviewer){
		
		int call = CALLED_NONE;
		boolean called = false;
		if (DEBUG_SKIP_PLAYER_CALL) return call;
		
		//get user's choice through GUI
		called = tviewer.getClickCall(mHand.ableToChiL(), mHand.ableToChiM(), mHand.ableToChiH(),
									mHand.ableToPon(), mHand.ableToKan(), 
									mHand.ableToRon());

		//decide call based on player's choice
		if (called){
			if (tviewer.resultClickCallWasChiL()) call = Player.CALLED_CHI_L;
			else if (tviewer.resultClickCallWasChiM()) call = Player.CALLED_CHI_M;
			else if (tviewer.resultClickCallWasChiH()) call = Player.CALLED_CHI_H;
			else if (tviewer.resultClickCallWasPon()) call = Player.CALLED_PON;
			else if (tviewer.resultClickCallWasKan()) call = Player.CALLED_KAN;
			else if (tviewer.resultClickCallWasRon()) call = Player.CALLED_RON;
		}
		
		return call;
	}
	
	
	
	/*
	private method: __askReactionCom
	asks a computer player how they want to react to the discarded tile
	
	input: t is the tile that was just discarded, and the player has a chance to react to it
	
	returns the type of call the player wants to make on the tile (none, chi, pon, kan, ron)
	
	
	call = NONE
	return call
	*/
	private int __askReactionCom(Tile t){
		/*
		I'm a computer. What do I want to call?
		Ron > Kan = pon > Chi-L = Chi-M = Chi-H > none
		*/
		
		int call = CALLED_NONE;
		if (!DEBUG_COMPUTERS_MAKE_CALLS) return call;
		
		//computer will always call if possible
		if (mHand.ableToChiL()) call = Player.CALLED_CHI_L;
		if (mHand.ableToChiM()) call = Player.CALLED_CHI_M;
		if (mHand.ableToChiH()) call = Player.CALLED_CHI_H;
		if (mHand.ableToPon()) call = Player.CALLED_PON;
		if (mHand.ableToKan()) call = Player.CALLED_KAN;
		if (mHand.ableToRon()) call = Player.CALLED_RON;
		
		return call;
	}
	
	
	
	/*
	private method: __ableToCallTile
	checks if the player is able to make a call on Tile t
	
	input: t is the tile to check if the player can call
	
	returns true if the player can call the tile, false if not
	
	
	get list of hot tiles for the hand
	check if t is a hot tile
	if (t is not a hot tile): return false
	
	check if t is callable
	if (t is callable): return true
	if (t not callable): return false
	*/
	private boolean __ableToCallTile(Tile t){
		
		boolean ableToCall = false;
		
		//check if tile t is a hot tile. if t is not a hot tile, return false
		ArrayList<Integer> hotList = mHand.findAllHotTiles();
		if (hotList.contains(t.getId()) == false) return false;
		
		//~~~~At this point, we know t is a hot tile
		//we need to check which melds it can be called for, if any
		//check if t can be called to make a meld
		ableToCall = mHand.checkCallableTile(t);
		
		//return true if t is callable, false if not
		return ableToCall;
	}
	
	
	
	
	
	/*
	method: makeMeld
	forms a meld using the given tile
	
	input: t is the "new" tile to form the meld with
	
	
	decide the type of meld to form, based on call status
	tell hand to make the meld
	update what the player will need to draw next turn
	*/
	public void makeMeld(Tile t){
		
		if (mCallStatus != CALLED_NONE){
			
			//make the right type of meld, based on call status
			if (mCallStatus == CALLED_CHI_L) mHand.makeMeldChiL();
			else if (mCallStatus == CALLED_CHI_M) mHand.makeMeldChiM();
			else if (mCallStatus == CALLED_CHI_H) mHand.makeMeldChiH();
			else if (mCallStatus == CALLED_PON) mHand.makeMeldPon();
			else if (mCallStatus == CALLED_KAN) mHand.makeMeldKan();
			
			
			//update what the player will need to draw next turn (draw nothing if called chi/pon, rinshan draw if called kan)
			if (mCallStatus == CALLED_CHI_L || mCallStatus == CALLED_CHI_M || mCallStatus == CALLED_CHI_H || mCallStatus == CALLED_PON)
				mDrawNeeded = DRAW_NONE;
			if (mCallStatus == CALLED_KAN)
				mDrawNeeded = DRAW_RINSHAN;
			
			//clear call status because the call has been completed
			mCallStatus = CALLED_NONE;
		}
		else
			System.out.println("-----Error: No meld to make (the player didn't make a call!!)");
	}
	
	
	
	
	
	//will use these for chankan later
	public int reactToAnkan(Tile t){return CALLED_NONE;}
	public int reactToMinkan(Tile t){return CALLED_NONE;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//accessors
	public int getHandSize(){return mHand.getSize();}
	public char getSeatWind(){return mSeatWind;}
	//returns 1,2,3,4, corresponding to seat wind E,S,W,N
	public int getPlayerNumber(){
		if (mSeatWind == SEAT_EAST) return 1;
		else if (mSeatWind == SEAT_SOUTH) return 2;
		else if (mSeatWind == SEAT_WEST) return 3;
		else if (mSeatWind == SEAT_NORTH) return 4;
		else return 0;
	}
	public String getPlayerName(){return mPlayerName;}
	
	

	
	public boolean getRiichiStatus(){return mRiichiStatus;}
	public boolean checkFuriten(){return mFuritenStatus;}
	public boolean checkTenpai(){return mHand.getTenpaiStatus();}
	
	
	
	//returns call status as a string
	public String getCallStatusString(){
		switch (mCallStatus){
		case CALLED_CHI_L: case CALLED_CHI_M: case CALLED_CHI_H: return "Chi";
		case CALLED_PON: return "Pon";
		case CALLED_KAN: return "Kan";
		case CALLED_RON: return "Ron";
		default: return "None";
		}
	}
	
	//returns true if the player called a tile
	public boolean called(){return (mCallStatus != CALLED_NONE);}
	//individual call statuses
	public boolean calledChi(){return (calledChiL() || calledChiM() || calledChiH());}
	public boolean calledChiL(){return (mCallStatus == CALLED_CHI_L);}
	public boolean calledChiM(){return (mCallStatus == CALLED_CHI_M);}
	public boolean calledChiH(){return (mCallStatus == CALLED_CHI_H);}
	public boolean calledPon(){return (mCallStatus == CALLED_PON);}
	public boolean calledKan(){return (mCallStatus == CALLED_KAN);}
	public boolean calledRon(){return (mCallStatus == CALLED_RON);}
	
	
	//check if the players needs to draw a tile, and what type of draw (normal vs rinshan)
	public boolean needsDraw(){return (needsDrawNormal() || needsDrawRinshan());}
	public boolean needsDrawNormal(){return (mDrawNeeded == DRAW_NORMAL);}
	public boolean needsDrawRinshan(){return (mDrawNeeded == DRAW_RINSHAN);}
	
	

	
	
	
	
	public boolean holdingRinshan(){return mHoldingRinshanTile;}
	
	
	public boolean handIsFullyConcealed(){return mHand.isClosed();}
	
	//returns the number of melds the player has made (open melds and ankans)
	public int getNumMeldsMade(){return mHand.getNumMeldsMade();}
	
	//returns a list of the melds that have been made (copy of actual melds), returns an empty list if no melds made
	public ArrayList<Meld> getMelds(){return mHand.getMelds();}
	
	
	public int getNumKansMade(){return mHand.getNumKansMade();}
	public boolean hasMadeAKan(){return (getNumKansMade() != 0);}
	
	
	
	
	
	
	//mutator for seat wind
	public boolean setSeatWind(char wind){
		if (wind ==  SEAT_EAST || wind ==  SEAT_SOUTH || wind ==  SEAT_WEST || wind ==  SEAT_NORTH)
			mSeatWind = wind;
		else
			return false;
		
		return true;
	}
	
	//used to set the controller of the player after its creation
	public boolean setController(char newController){
		if (mController == CONTROLLER_UNDECIDED)
			if (newController == CONTROLLER_HUMAN || newController == CONTROLLER_COM){
				mController = newController;
				return true;
			}
			else
				System.out.println("-----Error: controller must be human or computer\n");
		else
			System.out.println("-----Error: controller has already been set\n");
		
		return false;
	}
	public boolean setControllerHuman(){return setController(CONTROLLER_HUMAN);}
	public boolean setControllerComputer(){return setController(CONTROLLER_COM);}
	
	
	public String getControllerAsString(){
		if (mController == CONTROLLER_HUMAN) return "Human";
		else return "Computer";
	}
	
	public boolean controllerIsHuman(){return mController == CONTROLLER_HUMAN;}
	public boolean controllerIsComputer(){return mController == CONTROLLER_COM;}
	
	
	
	public void setPlayerName(String newName){
		if (newName != null) mPlayerName = newName;
	}
	
	
	
	
	
	//accessors for points
	public int getPoints(){return mPoints;}
	//mutators for points, increase or decrease
	public void pointsIncrease(int amount){
		mPoints += amount;
	}
	public void pointsDecrease(int amount){
		mPoints -= amount;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	////////////////////////////////////////////////////////////////////////////////////
	//////BEGIN DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	//fill hand with demo values
	public void fillHand(){
		mHand.fill();
	}
	////////////////////////////////////////////////////////////////////////////////////
	//////END DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	//sort the player's hand in ascending order
	public void sortHand(){mHand.sortHand();}
	
	
	

	//show the player's hand
	public void showHand(){
		System.out.print("\n" + mSeatWind + " Player's hand (controller: " + getControllerAsString() + ", " + mPlayerName + "):");
		if (mHand.getTenpaiStatus() == true) System.out.print("     $$$$!Tenpai!$$$$");
		System.out.println("\n" + mHand.toString());
	}
	//show player's melds
	public void showMelds(){mHand.showMelds();}
	
	//show pond
	public void showPond(){
		System.out.println("\t" + mSeatWind + " Player's pond " + mController + ":\n" + mPond.toString());
	}
	public String getPondAsString(){
		return (mSeatWind + " Player's pond:\n" + mPond.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//sync player's hand and pond with tracker
	public void syncWithRoundTracker(RoundTracker tracker){
		mRoundTracker = tracker;
		mRoundTracker.syncPlayer(mHand, mPond);
	}
	
	
	
	
	
	public static char findKamichaOf(char seat){
		if (SEAT_WINDS.indexOf(seat) == -1) return SEAT_UNDECIDED;
		
		if (seat == SEAT_EAST) return SEAT_NORTH;
		else return SEAT_WINDS.charAt(SEAT_WINDS.indexOf(seat) - 1);
	}
	
	

}
