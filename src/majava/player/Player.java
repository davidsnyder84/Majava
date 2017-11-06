package majava.player;

import java.util.List;
import java.util.Random;

import majava.Hand;
import majava.Meld;
import majava.Pond;
import majava.RoundTracker;
import majava.enums.Exclamation;
import majava.enums.Wind;
import majava.userinterface.GameUI;
import majava.summary.PlayerSummary;
import majava.summary.PointsBox;
import majava.tiles.GameTile;

/*
Class: Player
represents a player in the game
*/
public class Player {
	
	private PlayerBrain myBrain;
	private String mPlayerName;
	private int mPlayerID;
	
	private Hand mHand;
	private Pond mPond;
	private PointsBox pointsBox;
	private Wind mSeatWind;
	private int mPlayerNum;
	
	
	//used to indicate what type of draw a player needs
	private DrawingNeed drawNeeded;
	private GameTile mLastDiscard;
	
	
	private boolean mHoldingRinshanTile;
	private boolean mRiichiStatus;
	private boolean mFuritenStatus;
	
	
	private RoundTracker mRoundTracker;
	private GameUI mUI;
	
	
	
	public Player(String pName){
		
		//always generate a default brain just in case. we don't want brainless players
		myBrain = generateDefaultBrain(this);
		setPlayerName(pName);
		mPlayerID = (new Random()).nextInt();
		
		pointsBox = new PointsBox();
		
		mSeatWind = Wind.UNKNOWN;
		mPlayerNum = 0;
		prepareForNewRound();
	}
	public Player(){this(null);}
	
	
	
	//initializes a player's resources for a new round
	public void prepareForNewRound(){
		
		mHand = new Hand(mSeatWind);
		mPond = new Pond();
		
		//just in case, don't know if it's needed
		myBrain.clearCallStatus();
		myBrain.clearTurnActionStatus();
		
		setDrawNeededNormal();
		mRiichiStatus = false;
		mFuritenStatus = false;
		mHoldingRinshanTile = false;
		
		mLastDiscard = null;
	}
	
	
	
	
	
	
	
	
	
	//lets a player take their turn
	//returns the tile discarded by the player, returns null if the player did not discard (they made a kan or tsumo)
	public GameTile takeTurn(){
		mLastDiscard = null;
		
		myBrain.chooseTurnAction();
		if (turnActionChoseDiscard()){
			
			//discard and return the chosen tile
			mLastDiscard = __discardChosenTile();
			return mLastDiscard;
		}
		else{
			if (turnActionMadeKan()){
				//make the meld
				if (turnActionMadeAnkan())
					mHand.makeMeldTurnAnkan();
				else if (turnActionMadeMinkan())
					mHand.makeMeldTurnMinkan();
				
				setDrawNeededRinshan();
			}
			//riichi, tsumo falls here
			return null;
		}
	}
	
	
	
	private void __putTileInPond(GameTile t){mPond.addTile(t);}
	//removes the most recent tile from the player's pond (because another player called it)
	public GameTile removeTileFromPond(){return mPond.removeMostRecentTile();}
	
	
	
	
	private GameTile __discardChosenTile(){
		//remove the chosen discard tile from hand
		GameTile discardedTile = mHand.removeTile(myBrain.getChosenDiscardIndex());
		
		__putTileInPond(discardedTile);
		
		//set needed draw to normal, since we just discarded a tile
		setDrawNeededNormal();

		return discardedTile;
	}
	
	public GameTile getLastDiscard(){return mLastDiscard;}
	
	
	public boolean turnActionMadeKan(){return (turnActionMadeAnkan() || turnActionMadeMinkan());}
	public boolean turnActionMadeAnkan(){return myBrain.turnActionMadeAnkan();}
	public boolean turnActionMadeMinkan(){return myBrain.turnActionMadeMinkan();}
	public boolean turnActionCalledTsumo(){return myBrain.turnActionCalledTsumo();}
	public boolean turnActionChoseDiscard(){return myBrain.turnActionChoseDiscard();}
	public boolean turnActionRiichi(){return myBrain.turnActionRiichi();}
	
	
	//turn actions
	public boolean ableToAnkan(){return mHand.ableToAnkan();}
	public boolean ableToMinkan(){return mHand.ableToMinkan();}
	public boolean ableToRiichi(){return !isInRiichi() && mHand.ableToRiichi();}
	public boolean ableToTsumo(){return mHand.ableToTsumo();}
	
	
	
	
	
	
	
	public void addTileToHand(final GameTile t){
		t.setOwner(mSeatWind);
		mHand.addTile(t);
		
		//no longer need to draw (because the player has just drawn)
		setDrawNeededNone();
	}
	
	public void giveStartingHand(List<GameTile> startingTiles){
		for (GameTile t: startingTiles) addTileToHand(t);
		
		//if the player isn't east, they will need to draw
		if (mHand.size() < Hand.maxHandSize())
			setDrawNeededNormal();
	}
	
	
	
	
	
	
	
	//shows a player a tile, and gets their reaction (call or no call) for it
	public boolean reactToDiscard(GameTile tileToReactTo){
		myBrain.clearCallStatus();
		
		//if able to call the tile, ask brain for reaction
		if (__ableToCallTile(tileToReactTo))
			myBrain.reactToDiscard(tileToReactTo);
		
		return myBrain.called();
	}
	
	
	
	
	//checks if the player is able to make a call on Tile t (actual checks performed)
	private boolean __ableToCallTile(GameTile t){
		
		//check if t can be called to make a meld
		boolean ableToCall = mHand.checkCallableTile(t);
		
		//only allow ron if riichi
		if (isInRiichi() && !mHand.ableToRon()) ableToCall = false;
		
		return ableToCall;
	}
	
	public boolean ableToCallChiL(){return !isInRiichi() && mHand.ableToChiL();}
	public boolean ableToCallChiM(){return !isInRiichi() && mHand.ableToChiM();}
	public boolean ableToCallChiH(){return !isInRiichi() && mHand.ableToChiH();}
	public boolean ableToCallPon(){return !isInRiichi() && mHand.ableToPon();}
	public boolean ableToCallKan(){return !isInRiichi() && mHand.ableToKan();}
	public boolean ableToCallRon(){return mHand.ableToRon();}
	
	
	
	
	
	
	
	//forms a meld using the given tile
	public void makeMeld(GameTile t){
		
		//prevent an error case that will probably never happen
		if (!called()){System.out.println("-----Error: No meld to make (the player didn't make a call!!)"); return;}
		
		//make the right type of meld, based on call status
		if (calledChiL()) mHand.makeMeldChiL();
		else if (calledChiM()) mHand.makeMeldChiM();
		else if (calledChiH()) mHand.makeMeldChiH();
		else if (calledPon()) mHand.makeMeldPon();
		else if (calledKan()) mHand.makeMeldKan();
		
		//update what the player will need to draw next turn (draw nothing if called chi/pon, rinshan draw if called kan)
		if (calledChi() || calledPon())
			setDrawNeededNone();
		if (calledKan())
			setDrawNeededRinshan();
		
		//clear call status because the call has been completed
		myBrain.clearCallStatus();	//don't know how needed this is
	}
	
	
	
	
	
	//will implement these for chankan later
	public boolean reactToAnkan(GameTile t){return false;}
	public boolean reactToMinkan(GameTile t){return false;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//accessors
	public int getPlayerNumber(){return mPlayerNum;}
	public Wind getSeatWind(){return mSeatWind;}
	public boolean isDealer(){return mSeatWind == Wind.EAST;}
	
	public String getPlayerName(){return mPlayerName;}
	public int getPlayerID(){return mPlayerID;}

	public int handSize(){return mHand.size();}
	public boolean isInRiichi(){return mRiichiStatus;}
	public boolean isInFuriten(){return mFuritenStatus;}
	public boolean checkTenpai(){return mHand.getTenpaiStatus();}
	
	
	
	
	
	
	//returns call status as an exclamation
	public Exclamation getCallStatusExclamation(){return myBrain.getCallStatusExclamation();}
	
	//returns true if the player called a tile
	public boolean called(){return myBrain.called();}
	//individual call statuses
	public boolean calledChi(){return (calledChiL() || calledChiM() || calledChiH());}
	public boolean calledChiL(){return myBrain.calledChiL();}
	public boolean calledChiM(){return myBrain.calledChiM();}
	public boolean calledChiH(){return myBrain.calledChiH();}
	public boolean calledPon(){return myBrain.calledPon();}
	public boolean calledKan(){return myBrain.calledKan();}
	public boolean calledRon(){return myBrain.calledRon();}
	
	
	//check if the players needs to draw a tile, and what type of draw (normal vs rinshan)
	public boolean needsDraw(){return drawNeeded.needsToDraw();}
	public boolean needsDrawNormal(){return drawNeeded.needsNormal();}
	public boolean needsDrawRinshan(){return drawNeeded.needsRinshan();}
	private void setDrawNeededRinshan(){drawNeeded.setRinshan();}
	private void setDrawNeededNormal(){drawNeeded.setNormal();}
	private void setDrawNeededNone(){drawNeeded.setNone();}
	
	

	
	
	
	
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
	private void __setController(PlayerBrain desiredBrain){
		myBrain = desiredBrain;
	}
	public void setControllerHuman(){__setController(new HumanBrain(this, mUI));}
	public void setControllerComputer(){
		SimpleRobot robot = new SimpleRobot(this);
		__setController(robot);
	}
	
	public String getAsStringController(){return myBrain.toString();}
	public boolean controllerIsHuman(){return myBrain.isHuman();}
	public boolean controllerIsComputer(){return myBrain.isComputer();}
	private static PlayerBrain generateDefaultBrain(Player p){return new SimpleRobot(p);}
	
	

	private static final String DEFAULT_PLAYERNAME = "Saki";
	public void setPlayerName(String newName){
		if (newName == null)
			if (mPlayerName == null) mPlayerName = DEFAULT_PLAYERNAME;
			else return;
		mPlayerName = newName;
	}
	
	
	
	
	
	//points
	public int getPoints(){return pointsBox.getPoints();}
	public void pointsIncrease(int amount){pointsBox.add(amount);}
	public void pointsDecrease(int amount){pointsBox.subtract(amount);}	
	
	
	
	
	
	
	
	//sort the player's hand in ascending order
	public void sortHand(){mHand.sortHand();}
	
	
	
	//get pond as string
	public String getAsStringPond(){
		return mSeatWind + " Player's pond:\n" + mPond;
	}
	//get hand as string
	public String getAsStringHand(){
		String hs = "";
		hs += mSeatWind + " Player's hand (controller: " + myBrain + ", " + mPlayerName + "):";
		if (mHand.getTenpaiStatus()) hs += "     $$$$!Tenpai!$$$$";
		hs += "\n" + mHand;
		return hs;
	}
	public String getAsStringHandCompact(){
		String hs = "";
		hs += mSeatWind.toChar() + " hand: ";
		for (GameTile t: mHand) hs += t + " ";
		return hs;
	}
	
	
	
	
	public PlayerSummary getPlayerSummary(){
		PlayerSummary summary = new PlayerSummary(mPlayerName, mPlayerID, myBrain.toString(), mPlayerNum, mSeatWind, pointsBox.getPoints());
		return summary;
	}
	
	
	
	

	
	@Override
	public boolean equals(Object other){return (this == other);}
	
	@Override
	public String toString(){return (mPlayerName + " (" + mSeatWind.toChar() +" player) ");}
	
	
	
	
	
	
	public void setUI(GameUI ui){
		mUI = ui;
		if (controllerIsHuman())
			((HumanBrain) myBrain).setUI(mUI);
	}
	
	
	//sync player's hand and pond with tracker
	public void syncWithRoundTracker(RoundTracker tracker){
		mRoundTracker = tracker;
		mRoundTracker.syncPlayer(mHand, mPond);
	}
	
	

	
	
	

	////////////////////////////////////////////////////////////////////////////////////
	//////BEGIN DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	//fill hand with demo values
//	public void DEMOfillHand(){mHand.DEMOfillChuuren();}
	public void DEMOfillHand(){mHand.DEMOfillHelter();}
	public void DEMOfillHandNoTsumo(){DEMOfillHand(); mHand.removeTile(mHand.size()-1);}

	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand (for debug use)
	public void addTileToHand(int tileID){addTileToHand(new GameTile(tileID));}
	////////////////////////////////////////////////////////////////////////////////////
	//////END DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////

}
