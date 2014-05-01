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
	

methods:
	constructors:
	2-arg - takes seat and controller, initializes wall and pond, and status info
	1-arg - takes seat, sets default controller
	no-arg - sets default seat and controller
	
	
	mutators:
	setController - sets the player's controller
 	setSeatWind - sets the player's seat wind
	pointsIncrease, pointsDecrease - increase/decrease the player's points by an integer amount
 	
 	accessors:
 	getHandSize - returns hand size
	getSeatWind - return seat wind
	getPlayerNumber - returns (1,2,3,4) corresponding to (E,S,W,N)
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
*/
public class Player {
	
	
	
	private enum Controller{
		HUMAN, COM, UNDECIDED;
		
		@Override
		public String toString(){
			switch (this){
			case HUMAN: return "Human";
			case COM: return "Computer";
			default: return "Undecided";
			}
		}
		
		public boolean isComputer(){return (this == COM);}
		public boolean isHuman(){return (this == HUMAN);}
	}
	

	
	private static final Wind SEAT_DEFAULT = Wind.UNKNOWN;
	public static final Controller CONTROLLER_DEFAULT = Controller.UNDECIDED;
	
	
	private static final int COM_BEHAVIOR_DISCARD_LAST = 1;
	private static final int COM_BEHAVIOR_DISCARD_FIRST = 2;
	private static final int COM_BEHAVIOR_DISCARD_RANDOM = 3;
	private static final int COM_BEHAVIOR_DISCARD_DEFAULT = COM_BEHAVIOR_DISCARD_LAST;
	private static final int COM_BEHAVIOR = COM_BEHAVIOR_DISCARD_LAST;
	
	
	
	public static final int POINTS_STARTING_AMOUNT = 25000;
	
	
	private enum CallType{
		NONE, CHI_L, CHI_M, CHI_H, PON, KAN, RON, CHI, UNDECIDED;
		

		public boolean isChi(){return (this == CHI_L || this == CHI_M || this == CHI_H);}
		public boolean isPon(){return (this == PON);}
		
		
		@Override
		public String toString(){
			switch (this){
			case CHI_L: case CHI_M: case CHI_H: return "Chi";
			case PON: return "Pon";
			case KAN: return "Kan";
			case RON: return "Ron";
			default: return "None";
			}
		}
	}
	
	
	private enum DrawType{
		NONE, NORMAL, RINSHAN;
	}
	
	private enum ActionType{
		DISCARD, ANKAN, MINKAN, RIICHI, TSUMO, UNDECIDED;
	}
	

	private static final int CALLED_NONE = 0;
	private static final int CALLED_CHI_L = 1;
	private static final int CALLED_CHI_M = 2;
	private static final int CALLED_CHI_H = 3;
	private static final int CALLED_PON = 4;
	private static final int CALLED_KAN = 5;
	private static final int CALLED_RON = 6;
	private static final int CALLED_CHI = 123;
	
	
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
	private static final boolean DEBUG_COMPUTERS_MAKE_ACTIONS = true;
	
	
	
	
	
	private Hand mHand;
	private Pond mPond;
	private int mPoints;
	
	private Wind mSeatWind;
//	private int mSeatNumber;
	private Controller mController;
	private String mPlayerName;
	
	private CallType mCallStatus;
	private DrawType mDrawNeeded;
	private int mTurnAction;
	private int mChosenDiscardIndex;
	
	private Tile mLastDiscard;
	
	private boolean mHoldingRinshanTile;
	private boolean mRiichiStatus;
	private boolean mFuritenStatus;
	
	private boolean mWon;
	
	private RoundTracker mRoundTracker;
	
	
	
	
	
	
	
	
	public Player(Wind seat, Controller controller, String pName){
		
		mSeatWind = seat;
		mController = controller;
		
		if (pName == null) pName = PLAYERNAME_DEFAULT;
		setPlayerName(pName);
		
		mPoints = POINTS_STARTING_AMOUNT;
		
		prepareForNewRound();
	}
	public Player(Wind seat, Controller controller){this(seat, controller, PLAYERNAME_DEFAULT);}
	public Player(Wind seat){this(seat, CONTROLLER_DEFAULT);}
	public Player(){this(SEAT_DEFAULT);}
	
	
	
	
	
	//initializes a player's resources for a new round
	public void prepareForNewRound(){
		
		mHand = new Hand(mSeatWind);
		mPond = new Pond();
		
		mCallStatus = CallType.NONE;
		mDrawNeeded = DrawType.NORMAL;
		
		mChosenDiscardIndex = NO_DISCARD_CHOSEN;
		mTurnAction = NO_ACTION_CHOSEN;
		
		mRiichiStatus = false;
		mFuritenStatus = false;
		mHoldingRinshanTile = false;
		
		mWon = false;
		
		mLastDiscard = null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	method: takeTurn
	walks the player through their turn
	
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
				
				
				mDrawNeeded = DrawType.RINSHAN;
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
		mDrawNeeded = DrawType.NORMAL;

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
	*/
	@SuppressWarnings("unused")
	private int __askTurnActionCom(){
		
		int chosenAction = NO_ACTION_CHOSEN;
		int chosenDiscardIndex = NO_DISCARD_CHOSEN;
		
		//choose the first tile in the hand
		if (COM_BEHAVIOR == COM_BEHAVIOR_DISCARD_FIRST) chosenDiscardIndex = 0;
		//choose the last tile in the hand (most recently drawn one)
		if (COM_BEHAVIOR == COM_BEHAVIOR_DISCARD_LAST) chosenDiscardIndex = mHand.getSize() - 1;
		
		
//		if (mSeatWind == 'N')chosenDiscardIndex = mHand.getSize() - 1;
//		if (mSeatWind == 'E')chosenDiscardIndex = mHand.getSize() - 1;
		
		if (DEBUG_COMPUTERS_MAKE_ACTIONS && ableToAnkan()) chosenAction = TURN_ACTION_ANKAN;
		if (DEBUG_COMPUTERS_MAKE_ACTIONS && ableToMinkan()) chosenAction = TURN_ACTION_MINKAN;
		if (DEBUG_COMPUTERS_MAKE_ACTIONS && ableToTsumo()) chosenAction = TURN_ACTION_TSUMO;
		
		if (chosenAction != NO_ACTION_CHOSEN){
			mTurnAction = chosenAction;
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
		mDrawNeeded = DrawType.NONE;
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
		mCallStatus = CallType.NONE;
		
		//if able to call the tile, ask self for reaction
		if (__ableToCallTile(t)){
			
			//ask self for reaction
			//update call status
			mCallStatus = __askSelfForReaction(t, tviewer);
		}
		
		////////////////////WATCH THIS MOTHERFUCKER
		//////////////////I DONT KNOW WHAT THIS WILL DO
		//draw normally if no call
		if (mCallStatus == CallType.NONE)
			mDrawNeeded = DrawType.NORMAL;
		
		return (mCallStatus != CallType.NONE);
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
	private CallType __askSelfForReaction(Tile t, TableViewer tviewer){
		CallType call = CallType.NONE;
		
		if (controllerIsHuman()) call = __askReactionHuman(t, tviewer);
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
	private CallType __askReactionHuman(Tile t, TableViewer tviewer){
		
		CallType call = CallType.NONE;
		boolean called = false;
		if (DEBUG_SKIP_PLAYER_CALL) return call;
		
		//get user's choice through GUI
		called = tviewer.getClickCall(mHand.ableToChiL(), mHand.ableToChiM(), mHand.ableToChiH(),
									mHand.ableToPon(), mHand.ableToKan(), 
									mHand.ableToRon());

		//decide call based on player's choice
		if (called){
			if (tviewer.resultClickCallWasChiL()) call = CallType.CHI_L;
			else if (tviewer.resultClickCallWasChiM()) call = CallType.CHI_M;
			else if (tviewer.resultClickCallWasChiH()) call = CallType.CHI_H;
			else if (tviewer.resultClickCallWasPon()) call = CallType.PON;
			else if (tviewer.resultClickCallWasKan()) call = CallType.KAN;
			else if (tviewer.resultClickCallWasRon()) call = CallType.RON;
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
	private CallType __askReactionCom(Tile t){
		/*
		I'm a computer. What do I want to call?
		Ron > Kan = pon > Chi-L = Chi-M = Chi-H > none
		*/
		
		CallType call = CallType.NONE;
		if (!DEBUG_COMPUTERS_MAKE_CALLS) return call;
		
		//computer will always call if possible
		if (mHand.ableToChiL()) call = CallType.CHI_L;
		if (mHand.ableToChiM()) call = CallType.CHI_M;
		if (mHand.ableToChiH()) call = CallType.CHI_H;
		if (mHand.ableToPon()) call = CallType.PON;
		if (mHand.ableToKan()) call = CallType.KAN;
		if (mHand.ableToRon()) call = CallType.RON;
		
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
		
		if (mCallStatus != CallType.NONE){
			
			//make the right type of meld, based on call status
			if (mCallStatus == CallType.CHI_L) mHand.makeMeldChiL();
			else if (mCallStatus == CallType.CHI_M) mHand.makeMeldChiM();
			else if (mCallStatus == CallType.CHI_H) mHand.makeMeldChiH();
			else if (mCallStatus == CallType.PON) mHand.makeMeldPon();
			else if (mCallStatus == CallType.KAN) mHand.makeMeldKan();
			
			
			//update what the player will need to draw next turn (draw nothing if called chi/pon, rinshan draw if called kan)
			if (mCallStatus == CallType.CHI_L || mCallStatus == CallType.CHI_M || mCallStatus == CallType.CHI_H || mCallStatus == CallType.PON)
				mDrawNeeded = DrawType.NONE;
			if (mCallStatus == CallType.KAN)
				mDrawNeeded = DrawType.RINSHAN;
			
			//clear call status because the call has been completed
			mCallStatus = CallType.NONE;
		}
		else
			System.out.println("-----Error: No meld to make (the player didn't make a call!!)");
	}
	
	
	
	
	
	//will use these for chankan later
	public int reactToAnkan(Tile t){return CALLED_NONE;}
	public int reactToMinkan(Tile t){return CALLED_NONE;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//accessors
	public int getHandSize(){return mHand.getSize();}
	public Wind getSeatWind(){return mSeatWind;}
	//returns 1,2,3,4, corresponding to seat wind E,S,W,N
	public int getPlayerNumber(){
		if (mSeatWind == Wind.EAST) return 1;
		else if (mSeatWind == Wind.SOUTH) return 2;
		else if (mSeatWind == Wind.WEST) return 3;
		else if (mSeatWind == Wind.NORTH) return 4;
		else return 0;
	}
	public String getPlayerName(){return mPlayerName;}
	
	

	
	public boolean getRiichiStatus(){return mRiichiStatus;}
	public boolean checkFuriten(){return mFuritenStatus;}
	public boolean checkTenpai(){return mHand.getTenpaiStatus();}
	
	
	
	//returns call status as a string
	public String getCallStatusString(){return mCallStatus.toString();}
	
	//returns true if the player called a tile
	public boolean called(){return (mCallStatus != CallType.NONE);}
	//individual call statuses
	public boolean calledChi(){return (calledChiL() || calledChiM() || calledChiH());}
	public boolean calledChiL(){return (mCallStatus == CallType.CHI_L);}
	public boolean calledChiM(){return (mCallStatus == CallType.CHI_M);}
	public boolean calledChiH(){return (mCallStatus == CallType.CHI_H);}
	public boolean calledPon(){return (mCallStatus == CallType.PON);}
	public boolean calledKan(){return (mCallStatus == CallType.KAN);}
	public boolean calledRon(){return (mCallStatus == CallType.RON);}
	
	
	//check if the players needs to draw a tile, and what type of draw (normal vs rinshan)
	public boolean needsDraw(){return (needsDrawNormal() || needsDrawRinshan());}
	public boolean needsDrawNormal(){return (mDrawNeeded == DrawType.NORMAL);}
	public boolean needsDrawRinshan(){return (mDrawNeeded == DrawType.RINSHAN);}
	
	

	
	
	
	
	public boolean holdingRinshan(){return mHoldingRinshanTile;}
	
	
	public boolean handIsFullyConcealed(){return mHand.isClosed();}
	
	//returns the number of melds the player has made (open melds and ankans)
	public int getNumMeldsMade(){return mHand.getNumMeldsMade();}
	
	//returns a list of the melds that have been made (copy of actual melds), returns an empty list if no melds made
	public ArrayList<Meld> getMelds(){return mHand.getMelds();}
	
	
	public int getNumKansMade(){return mHand.getNumKansMade();}
	public boolean hasMadeAKan(){return (getNumKansMade() != 0);}
	
	
	
	
	
	
	//mutator for seat wind
	private void __setSeatWind(Wind wind){mSeatWind = wind;}
	public void setSeatWindEast(){__setSeatWind(Wind.EAST);}
	public void setSeatWindSouth(){__setSeatWind(Wind.SOUTH);}
	public void setSeatWindWest(){__setSeatWind(Wind.WEST);}
	public void setSeatWindNorth(){__setSeatWind(Wind.NORTH);}
	
	
	
	//used to set the controller of the player after its creation
	private void __setController(Controller newController){
		mController = newController;
	}
	public void setControllerHuman(){__setController(Controller.HUMAN);}
	public void setControllerComputer(){__setController(Controller.COM);}
	
	public String getControllerAsString(){return mController.toString();}
	
	public boolean controllerIsHuman(){return mController.isHuman();}
	public boolean controllerIsComputer(){return mController.isComputer();}
	
	
	
	
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
		mHand.fillChuuren();
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
	

}
