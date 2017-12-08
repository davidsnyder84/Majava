package majava.player;

import java.util.List;

import majava.hand.Hand;
import majava.hand.Meld;
import majava.Pond;
import majava.RoundTracker;
import majava.enums.Exclamation;
import majava.enums.Wind;
import majava.player.brains.HumanBrain;
import majava.player.brains.NullPlayerBrain;
import majava.player.brains.PlayerBrain;
import majava.player.brains.PonMonsterBot;
import majava.player.brains.RobotBrain;
import majava.player.brains.SevenTwinBot;
import majava.player.brains.SimpleRobot;
import majava.player.brains.TseIiMenBot;
import majava.summary.PointsBox;
import majava.tiles.GameTile;


//represents a player in the game
public class Player {
	
	private PlayerBrain brain;
	private PlayerProfile profile;
	private PointsBox pointsBox;
	
	private RoundTracker roundTracker;
	
	//data that changes between rounds
	private Hand hand;
	private Pond pond;
	private Wind seatWind;
	private int playerNum;
	
	
	private DrawingNeed drawNeeded;
	private GameTile lastDiscard;	
	private boolean isHoldingRinshanTile;
	private boolean isRiichi;
	private boolean isFuriten;
	
	
	
	public Player(PlayerProfile newProfile){
		//always generate a default null brain
		brain = new NullPlayerBrain(this);
		profile = newProfile;
		pointsBox = new PointsBox();
		
		setSeatWind(Wind.UNKNOWN);
		playerNum = 0;
		drawNeeded = new DrawingNeed();
		prepareForNewRound();	/////////////////////refactor later
	}
	public Player(String name){this(new PlayerProfile(name));}
	public Player(){this((String)null);}
	
	//initializes a player's resources for a new round
	public void prepareForNewRound(){
		
		hand = new Hand();
		pond = new Pond();
		
		//just in case, don't know if it's needed
		brain.clearCallStatus();
		brain.clearTurnActionStatus();
		
		setDrawNeededNormal();
		isRiichi = false;
		isFuriten = false;
		isHoldingRinshanTile = false;
		lastDiscard = null;
	}
	
	
	
	
	
	
	
	//lets a player take their turn
	//returns the tile discarded by the player, returns null if the player did not discard (they made a kan or tsumo)
	public GameTile takeTurn(){
		lastDiscard = null;
		
		brain.chooseTurnAction(hand);
		if (turnActionChoseDiscard()){
			
			//discard and return the chosen tile
			lastDiscard = discardChosenTile();
			return lastDiscard;
		}
		
		if (turnActionMadeKan()){
			//make the kan
			if (turnActionMadeAnkan())
				hand.makeMeldTurnAnkan();
			else if (turnActionMadeMinkan())
				hand.makeMeldTurnMinkan();
			
			setDrawNeededRinshan();
		}
		//riichi, tsumo falls here
		return null;
	}
	
	private GameTile discardChosenTile(){
		GameTile discardedTile = hand.removeTile(brain.getChosenDiscardIndex());
		putTileInPond(discardedTile);
		
		//set needed draw to normal, since we just discarded a tile
		setDrawNeededNormal();

		return discardedTile;
	}
	private void putTileInPond(GameTile t){pond.addTile(t);}
	
	//removes the most recent tile from the player's pond (because another player called it)
	public GameTile removeTileFromPond(){return pond.removeMostRecentTile();}
	public Pond getPond(){return pond.clone();}
	
	public GameTile getLastDiscard(){return lastDiscard;}
	
	
	public boolean turnActionMadeKan(){return (turnActionMadeAnkan() || turnActionMadeMinkan());}
	public boolean turnActionMadeAnkan(){return brain.turnActionMadeAnkan();}
	public boolean turnActionMadeMinkan(){return brain.turnActionMadeMinkan();}
	public boolean turnActionCalledTsumo(){return brain.turnActionCalledTsumo();}
	public boolean turnActionChoseDiscard(){return brain.turnActionChoseDiscard();}
	public boolean turnActionRiichi(){return brain.turnActionRiichi();}
	
	
	//turn actions
	public boolean ableToAnkan(){return hand.ableToAnkan();}
	public boolean ableToMinkan(){return hand.ableToMinkan();}
	public boolean ableToRiichi(){return !isInRiichi() && hand.ableToRiichi();}
	public boolean ableToTsumo(){return hand.ableToTsumo();}
	
	
	
	
	
	
	
	public void addTileToHand(final GameTile t){
		t.setOwner(seatWind);
		hand.addTile(t);
		
		//no longer need to draw (because the player has just drawn)
		setDrawNeededNone();
	}
	public void giveStartingHand(List<GameTile> startingTiles){
		for (GameTile t: startingTiles) addTileToHand(t);
		hand.sort();
		
		//if the player isn't east, they will need to draw
		if (hand.size() < Hand.MAX_HAND_SIZE)
			setDrawNeededNormal();
	}
	
	
	
	
	
	
	
	
	//ask brain for reaction
	public void reactToDiscard(GameTile tileToReactTo){
		brain.clearCallStatus();
		brain.reactToDiscard(hand, tileToReactTo);
//		return brain.called();
	}
	
	//checks if the player is able to make a call on Tile t (actual checks performed)
	public boolean ableToCallTile(GameTile tileToReactTo){
		brain.clearCallStatus();
		
		//check if t can be called to make a meld
		boolean ableToCall = hand.canCallTile(tileToReactTo);
		
		//only allow ron if riichi
		if (isInRiichi() && !hand.ableToRon(tileToReactTo)) ableToCall = false;
		
		return ableToCall;
	}
	
	public boolean ableToCallChiL(GameTile tileToReactTo){return !isInRiichi() && hand.ableToChiL(tileToReactTo);}
	public boolean ableToCallChiM(GameTile tileToReactTo){return !isInRiichi() && hand.ableToChiM(tileToReactTo);}
	public boolean ableToCallChiH(GameTile tileToReactTo){return !isInRiichi() && hand.ableToChiH(tileToReactTo);}
	public boolean ableToCallPon(GameTile tileToReactTo){return !isInRiichi() && hand.ableToPon(tileToReactTo);}
	public boolean ableToCallKan(GameTile tileToReactTo){return !isInRiichi() && hand.ableToKan(tileToReactTo);}
	public boolean ableToCallRon(GameTile tileToReactTo){return hand.ableToRon(tileToReactTo);}
	
	
	
	
	
	
	
	//forms a meld using the given tile
	public void makeMeld(GameTile claimedTile){
		//prevent an error case that will probably never happen
		if (!called()){System.out.println("-----Error: No meld to make (the player didn't make a call!!)"); return;}
		
		//make the right type of meld, based on call status
		if (calledChiL()) hand.makeMeldChiL(claimedTile);
		else if (calledChiM()) hand.makeMeldChiM(claimedTile);
		else if (calledChiH()) hand.makeMeldChiH(claimedTile);
		else if (calledPon()) hand.makeMeldPon(claimedTile);
		else if (calledKan()) hand.makeMeldKan(claimedTile);
		
		//update what the player will need to draw next turn (draw nothing if called chi/pon, rinshan draw if called kan)
		if (calledChi() || calledPon())
			setDrawNeededNone();
		if (calledKan())
			setDrawNeededRinshan();
		
		//clear call status because the call has been completed
		brain.clearCallStatus();	//this is needed to make sure a player can't call their own discard
	}
	
	
	
	
	
	
	//accessors
	public int handSize(){return hand.size();}
	public boolean isInRiichi(){return isRiichi;}
	public boolean isInFuriten(){return isFuriten;}
	public boolean checkTenpai(){return hand.isInTenpai();}
	
	
	
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
	public boolean needsDraw(){return drawNeeded.needsToDraw();}
	public boolean needsDrawNormal(){return drawNeeded.needsNormal();}
	public boolean needsDrawRinshan(){return drawNeeded.needsRinshan();}
	private void setDrawNeededRinshan(){drawNeeded.setRinshan();}
	private void setDrawNeededNormal(){drawNeeded.setNormal();}
	private void setDrawNeededNone(){drawNeeded.setNone();}
	
	

	
	
	
	
	public boolean holdingRinshan(){return isHoldingRinshanTile;}
	public GameTile getTsumoTile(){return hand.getTile(hand.size() - 1).clone();}
	
	
	public boolean handIsFullyConcealed(){return hand.isClosed();}
	
	//returns the number of melds the player has made (open melds and ankans)
	public int getNumMeldsMade(){return hand.numberOfMeldsMade();}
	public List<Meld> getMelds(){return hand.getMelds();}
	
	
	public int getNumKansMade(){return hand.getNumKansMade();}
	public boolean hasMadeAKan(){return (getNumKansMade() != 0);}
	
	
	
	
	
	
	//seat wind methods
	public void setSeatWind(Wind wind){seatWind = wind;}
	public void rotateSeat(){setSeatWind(seatWind.prev());}
	public void setSeatWindEast(){setSeatWind(Wind.EAST);}
	public void setSeatWindSouth(){setSeatWind(Wind.SOUTH);}
	public void setSeatWindWest(){setSeatWind(Wind.WEST);}
	public void setSeatWindNorth(){setSeatWind(Wind.NORTH);}
	public Wind getSeatWind(){return seatWind;}
	public boolean isDealer(){return getSeatWind().isDealerWind();}
	
	//player number methods
	public void setPlayerNumber(int newNum){if (newNum >= 0 && newNum < 4) playerNum = newNum;}	
	public int getPlayerNumber(){return playerNum;}
	
	
	//controller methods
	private void setController(PlayerBrain desiredBrain){brain = desiredBrain;}
	public void setControllerHuman(){setController(new HumanBrain(this));}
	public void setControllerComputer(){
		RobotBrain robot = new SimpleRobot(this);
//		RobotBrain robot = new SevenTwinBot(this);
//		RobotBrain robot = new TseIiMenBot(this);
//		RobotBrain robot = new PonMonsterBot(this);
		setController(robot);
	}
	public String getControllerAsString(){return brain.toString();}
	public boolean controllerIsHuman(){return brain.isHuman();}
	public boolean controllerIsComputer(){return brain.isComputer();}
	public PlayerBrain getController(){return brain;}
	
	
	//point methods
	public int getPoints(){return pointsBox.getPoints();}
	public void pointsIncrease(int amount){pointsBox.add(amount);}
	public void pointsDecrease(int amount){pointsBox.subtract(amount);}
	public boolean pointsIsHakoshita(){return pointsBox.isHakoshita();}

	//profile methods
	public String getPlayerName(){return profile.getPlayerName();}
	public int getPlayerID(){return profile.getPlayerID();}
	public void setPlayerName(String newName){profile.setPlayerName(newName);}
	
	
	
	
	//get pond as string
	public String getAsStringPond(){
		return getSeatWind() + " Player's pond:\n" + pond;
	}
	//get hand as string
	public String getAsStringHand(){
		String hs = "";
		hs += seatWind + " Player's hand (controller: " + brain + ", " + getPlayerName() + "):";
		if (hand.isInTenpai()) hs += "     $$$$!Tenpai!$$$$";
		hs += "\n" + hand;
		return hs;
	}
	public String getAsStringHandCompact(){
		String hs = "";
		hs += seatWind.toChar() + " hand: ";
		for (GameTile t: hand) hs += t + " ";
		return hs;
	}

	
	@Override
	public boolean equals(Object other){return (this == other);}
	@Override
	public String toString(){return (getPlayerName() + " (" + seatWind.toChar() +" player) ");}
	
	
	
	public void syncWithRoundTracker(RoundTracker tracker){
		roundTracker = tracker;
	}
	
	
	
	
	
	

	////xxxxxxxxxxxxxBEGIN DEMO METHODS
	//fill hand with demo values
	public void DEMOfillHandChuuren(){hand.DEMOfillChuuren();}
	public void DEMOfillHand(){hand.DEMOfillScattered();}
	public void DEMOfillHandNoTsumo(){DEMOfillHand(); hand.removeTile(hand.size()-1);}

	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand (for debug use)
	public void addTileToHand(int tileID){addTileToHand(new GameTile(tileID));}
	
	public Hand DEMOgetHand(){return hand;}
	////xxxxxxxxxxxxxEND DEMO METHODS
	
	
	//will implement these for chankan later
//	public boolean reactToAnkan(GameTile t){return false;}
//	public boolean reactToMinkan(GameTile t){return false;}
	
}
