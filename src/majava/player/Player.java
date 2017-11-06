package majava.player;

import java.util.List;
import java.util.Random;

import majava.Hand;
import majava.Meld;
import majava.Pond;
import majava.RoundTracker;
import majava.enums.Exclamation;
import majava.enums.GameplayEvent;
import majava.enums.Wind;
import majava.userinterface.GameUI;
import majava.summary.PlayerSummary;
import majava.tiles.GameTile;

/*
Class: Player
represents a player in the game
*/
public class Player {
	

	//used to indicate who is controlling the player
	private static enum Controller{
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

	//used to indicate what call a player wants to make on another player's discard
	private static enum CallType{
		NONE, CHI_L, CHI_M, CHI_H, PON, KAN, RON, CHI, UNDECIDED;
		
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
		public Exclamation toExclamation(){
			switch (this){
			case CHI_L: case CHI_M: case CHI_H: return Exclamation.CHI;
			case PON: return Exclamation.PON;
			case KAN: return Exclamation.KAN;
			case RON: return Exclamation.RON;
			case NONE: return Exclamation.NONE;
			default: return Exclamation.UNKNOWN;
			}
		}
	}
	
	//used to indicate what type of draw a player needs
	private static enum DrawType{
		NONE, NORMAL, RINSHAN;
	}

	//used to indicate what action the player wants to do on their turn
	private static enum ActionType{
		DISCARD, ANKAN, MINKAN, RIICHI, TSUMO, UNDECIDED;
	}
	
	//used to dictate how the com chooses its discards
	private static enum ComBehavior{DISCARD_LAST, DISCARD_FIRST, DISCARD_RANDOM}
	private static final ComBehavior COM_BEHAVIOR_DEFAULT = ComBehavior.DISCARD_LAST;
	
	

	

	
	private static final boolean DEBUG_ALLOW_PLAYER_CALLS = true;
	private static final boolean DEBUG_COMPUTERS_MAKE_CALLS = true;
	private static final boolean DEBUG_COMPUTERS_MAKE_ACTIONS = true;
	
	private static final Wind SEAT_DEFAULT = Wind.UNKNOWN;
	private static final Controller CONTROLLER_DEFAULT = Controller.UNDECIDED;
	private static final String DEFAULT_PLAYERNAME = "Kyoutarou";
	private static final int DEFAULT_PLAYERNUM = 0;
	private static final ComBehavior COM_BEHAVIOR = COM_BEHAVIOR_DEFAULT;
	
	private static final int POINTS_STARTING_AMOUNT_DEFAULT = 25000;
	
	private static final int NO_DISCARD_CHOSEN = -94564;
	
	
	
	
	
	
	
	private Hand mHand;
	private Pond mPond;
	private int mPoints;
	
	private Wind mSeatWind;
	private int mPlayerNum;
	private Controller mController;
	private PlayerBrain brain = null;
	private String mPlayerName;
	private int mPlayerID;
	
//	mCallStatus - the player's call (reaction) to the most recent disacrd (chi, pon, kan, ron, or none)
//	mDrawNeeded - the type of draw the player needs for their next turn (normal draw, rinshan draw, or no draw)
//	mTurnAction - the player's choice of action on their turn (discard, ankan, tsumo, etc)
//	private CallType mCallStatus;
	private DrawType mDrawNeeded;
	private ActionType mTurnAction;
	private int mChosenDiscardIndex;
	
	
	private GameTile mLastDiscard;
	
//	mHoldingRinshanTile - is true if the player is holding a rinshan tile that they drew this turn, false otherwise
//	mRiichiStatus - is true if the player has declared riichi, false if not
//	mFuritenStatus - is true if the player is in furiten status, false if not
	private boolean mHoldingRinshanTile;
	private boolean mRiichiStatus;
	private boolean mFuritenStatus;
	
	
	private RoundTracker mRoundTracker;
	private GameUI mUI;
	
	
	
	
	
	
	
	public Player(int playerNum, String pName){
		
		mSeatWind = SEAT_DEFAULT;
		mController = CONTROLLER_DEFAULT;
		
		if (pName == null) pName = DEFAULT_PLAYERNAME;
		setPlayerName(pName);
		mPlayerID = (new Random()).nextInt();
		
		mPoints = POINTS_STARTING_AMOUNT_DEFAULT;
		
		mPlayerNum = 0;
		prepareForNewRound();
	}
	public Player(int playerNum){this(playerNum, DEFAULT_PLAYERNAME);}
	public Player(String pName){this(DEFAULT_PLAYERNUM, pName);}
	public Player(){this(DEFAULT_PLAYERNUM);}
	
	
	
	//initializes a player's resources for a new round
	public void prepareForNewRound(){
		
		mHand = new Hand(mSeatWind);
		mPond = new Pond();
		
		brain = null;
//		mCallStatus = CallType.NONE;
		mDrawNeeded = DrawType.NORMAL;
		
		mChosenDiscardIndex = NO_DISCARD_CHOSEN;
		mTurnAction = ActionType.UNDECIDED;
		
		mRiichiStatus = false;
		mFuritenStatus = false;
		mHoldingRinshanTile = false;
		
		mLastDiscard = null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	method: takeTurn
	walks the player through their turn
	
	returns the discarded tile if they chose a discard
	returns null if the player did not discard (they made a kan or tsumo)
	*/
	public GameTile takeTurn(){
		
		mLastDiscard = null;
		mChosenDiscardIndex = NO_DISCARD_CHOSEN;
		
		//discard a tile
		__chooseTurnAction();
		
		if (turnActionChoseDiscard()){
			
			//discard and return the chosen tile
			mLastDiscard = __discardChosenTile();
			return mLastDiscard;
		}
		else{
			
//			if (turnActionMadeAnkan()) System.out.println("\n\n!!!!!OOOOOHBOY ANKAN\n!!!!"); if (turnActionMadeMinkan()) System.out.println("\n\n!!!!!OOOOOHBOY MINKAN\n!!!!"); if (turnActionRiichi()) System.out.println("\n\n!!!!!OOOOOHBOY RIICHI\n!!!!"); if (turnActionCalledTsumo()) System.out.println("\n\n!!!!!OOOOOHBOY TSUMO\n!!!!");
			
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
	private void __putTileInPond(GameTile t){mPond.addTile(t);}
	
	
	//removes the most recent tile from the player's pond (because another player called it)
	public GameTile removeTileFromPond(){return mPond.removeMostRecentTile();}
	
	
	
	
	private GameTile __discardChosenTile(){
		
		GameTile discardedTile = null;
		
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
	------------------------------
	private method: __askSelfForTurnAction
	asks a player what they want to do on their turn
	(only asks and gets their choice, doesn't carry out the action)
	
	returns the index of the tile to discard, if the player chose a discard
	returns something negative otherwise
	
	if (controller is human) ask human and return choice
	else (controller is computer) ask computer and return choice
	*/
	private void __chooseTurnAction(){
		
		//auto discard if in riichi (if unable to tsumo/kan)
		if (mRiichiStatus && (!ableToTsumo() && !ableToAnkan())){
			mTurnAction = ActionType.DISCARD;
			mChosenDiscardIndex = mHand.size() - 1;
			return;
		}
		
		
		//ask self for turn action
//		brain.chooseTurnAction();
		if (controllerIsHuman()) __askTurnActionHuman();
		else __askTurnActionCom();
	}
	
	
	
	/*
	private method: __askDiscardHuman
	asks a human player which tile they want to discard, returns their choice
	
	returns the index of the tile the player wants to discard
	
	
	chosenDiscard = ask user through keyboard
	chosenDiscard = user clicks on tile through GUI
	return chosenDiscard
	*/
	private void __askTurnActionHuman(){
		
		ActionType chosenAction = ActionType.UNDECIDED;
		int chosenDiscardIndex = NO_DISCARD_CHOSEN;
		
		//show hand
		__updateUI(GameplayEvent.HUMAN_PLAYER_TURN_START);

		//get the player's desired action through the UI
		mUI.askUserInputTurnAction(getHandSize(), ableToRiichi(), ableToAnkan(), ableToMinkan(), ableToTsumo());
		
		
		
		if (mUI.resultChosenTurnActionWasDiscard()){
			mTurnAction = ActionType.DISCARD;
			chosenDiscardIndex = mUI.resultChosenDiscardIndex();
			mChosenDiscardIndex = chosenDiscardIndex - 1;	//adjust for index
		}
		else{
			if (mUI.resultChosenTurnActionWasAnkan()) chosenAction = ActionType.ANKAN;
			if (mUI.resultChosenTurnActionWasMinkan()) chosenAction = ActionType.MINKAN;
			if (mUI.resultChosenTurnActionWasRiichi()) chosenAction = ActionType.RIICHI;
			if (mUI.resultChosenTurnActionWasTsumo()) chosenAction = ActionType.TSUMO;
			mTurnAction = chosenAction;
			
			//riichi
			if (mTurnAction == ActionType.RIICHI){
				mRiichiStatus = true;
				
				mTurnAction = ActionType.DISCARD;
				mChosenDiscardIndex = getHandSize() - 1;
			}
		}
	}
	
	
	
	/*
	private method: __askDiscardCom
	asks a computer player which tile they want to discard, returns their choice
	*/
	private void __askTurnActionCom(){
		
		ActionType chosenAction = ActionType.UNDECIDED;
		int chosenDiscardIndex = NO_DISCARD_CHOSEN;
		
		//choose the first tile in the hand
		if (COM_BEHAVIOR == ComBehavior.DISCARD_FIRST) chosenDiscardIndex = 0;
		//choose the last tile in the hand (most recently drawn one)
		if (COM_BEHAVIOR == ComBehavior.DISCARD_LAST) chosenDiscardIndex = mHand.size() - 1;
		
		
//		if (mSeatWind == Wind.NORTH)chosenDiscardIndex = mHand.getSize() - 1;
//		if (mSeatWind == Wind.EAST)chosenDiscardIndex = mHand.getSize() - 1;
		
		if (DEBUG_COMPUTERS_MAKE_ACTIONS && ableToAnkan()) chosenAction = ActionType.ANKAN;
		if (DEBUG_COMPUTERS_MAKE_ACTIONS && ableToMinkan()) chosenAction = ActionType.MINKAN;
		if (DEBUG_COMPUTERS_MAKE_ACTIONS && ableToTsumo()) chosenAction = ActionType.TSUMO;
		
		if (chosenAction != ActionType.UNDECIDED){
			mTurnAction = chosenAction;
		}
		else{
			mTurnAction = ActionType.DISCARD;
			mChosenDiscardIndex = chosenDiscardIndex;
		}
	}
	
	
	public GameTile getLastDiscard(){return mLastDiscard;}
	
	
	public boolean turnActionMadeKan(){return (turnActionMadeAnkan() || turnActionMadeMinkan());}
	public boolean turnActionMadeAnkan(){return (mTurnAction == ActionType.ANKAN);}
	public boolean turnActionMadeMinkan(){return (mTurnAction == ActionType.MINKAN);}
	public boolean turnActionCalledTsumo(){return (mTurnAction == ActionType.TSUMO);}
	public boolean turnActionChoseDiscard(){return (mTurnAction == ActionType.DISCARD);}
//	public boolean turnActionChoseDiscard(){return (mTurnAction == TURN_ACTION_DISCARD || mTurnAction == TURN_ACTION_RIICHI);}
	public boolean turnActionRiichi(){return (mTurnAction == ActionType.RIICHI);}
	
	
	//turn actions
	public boolean ableToAnkan(){return mHand.ableToAnkan();}
	public boolean ableToMinkan(){return mHand.ableToMinkan();}
	public boolean ableToRiichi(){return !mRiichiStatus && mHand.ableToRiichi();}
	public boolean ableToTsumo(){return mHand.ableToTsumo();}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void addTileToHand(final GameTile t){
		
		//set the tile's owner to be the player
		t.setOwner(mSeatWind);
		
		//add the tile to the hand
		mHand.addTile(t);
		
		//no longer need to draw (because the player has just drawn)
		mDrawNeeded = DrawType.NONE;
	}
	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand (for debug use)
	public void addTileToHand(int tileID){addTileToHand(new GameTile(tileID));}
	
	//give a player a starting hand at the beginning of the round
	public void giveStartingHand(List<GameTile> startingTiles){
		for (GameTile t: startingTiles)
			addTileToHand(t);
		
		//if the player isn't east, they will need to draw
		if (mHand.size() < Hand.maxHandSize())
			mDrawNeeded = DrawType.NORMAL;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
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
	public boolean reactToDiscard(GameTile tileToReactTo){
		brain.clearCallStatus();
		
		//if able to call the tile, ask self for reaction
		if (__ableToCallTile(tileToReactTo)){
			
			//ask self for reaction, also update call status
//			mCallStatus = __askSelfForReaction(tileToReactTo);
			brain.reactToDiscard(tileToReactTo);
		}
		
		return brain.called();	//i think?
//		return (mCallStatus != CallType.NONE);
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
	private CallType __askSelfForReaction(GameTile t){
		CallType call = CallType.NONE;
		
		if (controllerIsHuman()) call = __askReactionHuman(t);
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
	private CallType __askReactionHuman(GameTile t){
		
		CallType call = CallType.NONE;
		boolean called = false;
		if (!DEBUG_ALLOW_PLAYER_CALLS) return call;
		
		//get user's choice through UI
		called = mUI.askUserInputCall(ableToCallChiL(), ableToCallChiM(), ableToCallChiH(), ableToCallPon(), ableToCallKan(), ableToCallRon());
		
		//decide call based on player's choice
		if (called){
			if (mUI.resultChosenCallWasChiL()) call = CallType.CHI_L;
			else if (mUI.resultChosenCallWasChiM()) call = CallType.CHI_M;
			else if (mUI.resultChosenCallWasChiH()) call = CallType.CHI_H;
			else if (mUI.resultChosenCallWasPon()) call = CallType.PON;
			else if (mUI.resultChosenCallWasKan()) call = CallType.KAN;
			else if (mUI.resultChosenCallWasRon()) call = CallType.RON;
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
	private CallType __askReactionCom(GameTile t){
		/*
		I'm a computer. What do I want to call?
		Ron > Kan = pon > Chi-L = Chi-M = Chi-H > none
		*/
		
		CallType call = CallType.NONE;
		if (!DEBUG_COMPUTERS_MAKE_CALLS) return call;
		
		//computer will always call if possible
		if (ableToCallChiL()) call = CallType.CHI_L;
		if (ableToCallChiM()) call = CallType.CHI_M;
		if (ableToCallChiH()) call = CallType.CHI_H;
		if (ableToCallPon()) call = CallType.PON;
		if (ableToCallKan()) call = CallType.KAN;
		if (ableToCallRon()) call = CallType.RON;
		
		return call;
	}
	
	
	
	/*
	private method: __ableToCallTile
	checks if the player is able to make a call on Tile t
	
	input: t is the tile to check if the player can call
	returns true if the player can call the tile, false if not
	*/
	private boolean __ableToCallTile(GameTile t){
		
		//check if t can be called to make a meld
		boolean ableToCall = mHand.checkCallableTile(t);
		
		//only allow ron if riichi
		if (mRiichiStatus && !mHand.ableToRon()) ableToCall = false;
		
		//return true if t is callable, false if not
		return ableToCall;
	}
	
	public boolean ableToCallChiL(){return !mRiichiStatus && mHand.ableToChiL();}
	public boolean ableToCallChiM(){return !mRiichiStatus && mHand.ableToChiM();}
	public boolean ableToCallChiH(){return !mRiichiStatus && mHand.ableToChiH();}
	public boolean ableToCallPon(){return !mRiichiStatus && mHand.ableToPon();}
	public boolean ableToCallKan(){return !mRiichiStatus && mHand.ableToKan();}
	public boolean ableToCallRon(){return mHand.ableToRon();}
	
	
	
	
	
	
	/*
	method: makeMeld
	forms a meld using the given tile
	
	input: t is the "new" tile to form the meld with
	
	
	decide the type of meld to form, based on call status
	tell hand to make the meld
	update what the player will need to draw next turn
	*/
	public void makeMeld(GameTile t){
		
		if (called()){
			
			//make the right type of meld, based on call status
			if (calledChiL()) mHand.makeMeldChiL();
			else if (calledChiM()) mHand.makeMeldChiM();
			else if (calledChiH()) mHand.makeMeldChiH();
			else if (calledPon()) mHand.makeMeldPon();
			else if (calledKan()) mHand.makeMeldKan();
			
			
			//update what the player will need to draw next turn (draw nothing if called chi/pon, rinshan draw if called kan)
			if (calledChi() || calledPon())
				mDrawNeeded = DrawType.NONE;
			if (calledKan())
				mDrawNeeded = DrawType.RINSHAN;
			
			//clear call status because the call has been completed
			//i don't really like this
			brain.clearCallStatus();
		}
		else
			System.out.println("-----Error: No meld to make (the player didn't make a call!!)");
	}
	
	
	
	
	
	//will use these for chankan later
	public boolean reactToAnkan(GameTile t){return false;}
	public boolean reactToMinkan(GameTile t){return false;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//accessors
	public int getPlayerNumber(){return mPlayerNum;}
	public Wind getSeatWind(){return mSeatWind;}
	public boolean isDealer(){return mSeatWind == Wind.EAST;}
	
	public String getPlayerName(){return mPlayerName;}
	public int getPlayerID(){return mPlayerID;}
	
	

	public int getHandSize(){return mHand.size();}
	public boolean getRiichiStatus(){return mRiichiStatus;}
	public boolean checkFuriten(){return mFuritenStatus;}
	public boolean checkTenpai(){return mHand.getTenpaiStatus();}
	
	
	
	
	
	
	//returns call status as an exclamation
	public Exclamation getCallStatusExclamation(){return brain.getCallStatusExclamation();}
	
	//returns true if the player called a tile
	public boolean called(){return brain.called();}
	//individual call statuses
	public boolean calledChi(){return (calledChiL() || calledChiM() || calledChiH());}
	public boolean calledChiL(){return brain.calledChiL();}
	public boolean calledChiM(){return brain.calledChiM();}
	public boolean calledChiH(){return brain.calledChiH();}
	public boolean calledPon(){return brain.calledPon();}
	public boolean calledKan(){return brain.calledKan();}
	public boolean calledRon(){return brain.calledRon();}
	
	
	//check if the players needs to draw a tile, and what type of draw (normal vs rinshan)
	public boolean needsDraw(){return (needsDrawNormal() || needsDrawRinshan());}
	public boolean needsDrawNormal(){return (mDrawNeeded == DrawType.NORMAL);}
	public boolean needsDrawRinshan(){return (mDrawNeeded == DrawType.RINSHAN);}
	
	

	
	
	
	
	public boolean holdingRinshan(){return mHoldingRinshanTile;}
	public GameTile getTsumoTile(){return mHand.getTile(mHand.size() - 1).clone();}
	
	
	public boolean handIsFullyConcealed(){return mHand.isClosed();}
	
	//returns the number of melds the player has made (open melds and ankans)
	public int getNumMeldsMade(){return mHand.getNumMeldsMade();}
	
	//returns a list of the melds that have been made (copy of actual melds), returns an empty list if no melds made
	public List<Meld> getMelds(){return mHand.getMelds();}
	
	
	public int getNumKansMade(){return mHand.getNumKansMade();}
	public boolean hasMadeAKan(){return (getNumKansMade() != 0);}
	
	
	
	
	
	
	//mutator for seat wind
	private void __setSeatWind(Wind wind){mSeatWind = wind;}
	public void rotateSeat(){__setSeatWind(mSeatWind.prev());}
	
	public void setSeatWindEast(){__setSeatWind(Wind.EAST);}
	public void setSeatWindSouth(){__setSeatWind(Wind.SOUTH);}
	public void setSeatWindWest(){__setSeatWind(Wind.WEST);}
	public void setSeatWindNorth(){__setSeatWind(Wind.NORTH);}
	
	public void setPlayerNumber(int playerNum){if (playerNum >= 0 && playerNum < 4) mPlayerNum = playerNum;}
	
	
	
	//used to set the controller of the player after its creation
	private void __setController(Controller newController){
		mController = newController;
	}
	public void setControllerHuman(){__setController(Controller.HUMAN);}
	public void setControllerComputer(){__setController(Controller.COM);}
	
	public String getAsStringController(){return mController.toString();}
	
	public boolean controllerIsHuman(){return mController.isHuman();}
	public boolean controllerIsComputer(){return mController.isComputer();}
	
	
	
	
	public void setPlayerName(String newName){if (newName != null) mPlayerName = newName;}
	
	
	
	
	
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
//	public void DEMOfillHand(){mHand.DEMOfillChuuren();}
	public void DEMOfillHand(){mHand.DEMOfillHelter();}
	public void DEMOfillHandNoTsumo(){DEMOfillHand(); mHand.removeTile(mHand.size()-1);}
	////////////////////////////////////////////////////////////////////////////////////
	//////END DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	//sort the player's hand in ascending order
	public void sortHand(){mHand.sortHand();}
	
	
	
	//get pond as string
	public String getAsStringPond(){
		return (mSeatWind + " Player's pond:\n" + mPond.toString());
	}
	//get hand as string
	public String getAsStringHand(){
		String hs = "";
		hs += mSeatWind + " Player's hand (controller: " + getAsStringController() + ", " + mPlayerName + "):";
		if (mHand.getTenpaiStatus()) hs += "     $$$$!Tenpai!$$$$";
		hs += "\n" + mHand.toString();
		return hs;
	}
	public String getAsStringHandCompact(){
		String hs = "";
		hs += mSeatWind.toChar() + " hand: ";
		for (GameTile t: mHand) hs += t + " ";
		return hs;
	}
	
	
	
	
	public PlayerSummary getPlayerSummary(){
		PlayerSummary summary = new PlayerSummary(mPlayerName, mPlayerID, mController.toString(), mPlayerNum, mSeatWind, mPoints);
		return summary;
	}
	
	
	
	

	
	@Override
	public boolean equals(Object other){return (this == other);}
	
	@Override
	public String toString(){return (mPlayerName + " (" + mSeatWind.toChar() +" player) ");}
	
	
	
	
	
	
	
	private void __updateUI(GameplayEvent event){
		if (mUI != null) mUI.displayEvent(event);
	}
	public void setUI(GameUI ui){mUI = ui;}
	
	
	//sync player's hand and pond with tracker
	public void syncWithRoundTracker(RoundTracker tracker){
		mRoundTracker = tracker;
		mRoundTracker.syncPlayer(mHand, mPond);
	}
	

}
