package majava.player;

import java.util.List;

import majava.hand.Hand;
import majava.hand.Meld;
import majava.Pond;
import majava.RoundTracker;
import majava.enums.Exclamation;
import majava.enums.Wind;
import majava.player.brains.BeaverBot;
import majava.player.brains.HumanBrain;
import majava.player.brains.NullPlayerBrain;
import majava.player.brains.PlayerBrain;
import majava.player.brains.PonMonsterBot;
import majava.player.brains.RandomRobotGenerator;
import majava.player.brains.RobotBrain;
import majava.player.brains.SevenTwinBot;
import majava.player.brains.SimpleRobot;
import majava.player.brains.TseIiMenBot;
import majava.summary.PointsBox;
import majava.tiles.GameTile;
import majava.util.GTL;


//represents a player in the game
public class Player {
	
	private PlayerBrain brain;
	private PlayerProfile profile;
	private PointsBox pointsBox;
	
	private RoundTracker roundTracker;
	
	//data that changes between rounds
	private final Hand hand;
	private final Pond pond;
	private final Wind seatWind;
	
	private int playerNum;
	
	
	private final DrawingNeed drawNeeded;
	private final GameTile lastDiscard;	
	private final boolean isHoldingRinshanTile;
	private final boolean isRiichi;
	private final boolean isFuriten;
	
	
	private Player(Hand h, Pond p, Wind w, DrawingNeed dn, GameTile lasd){
		hand = h;
		pond = p;
		seatWind = w;
		drawNeeded = dn;
		lastDiscard = lasd;
		
		isHoldingRinshanTile = false; isRiichi = false; isFuriten = false;
	}
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
/////////////////////////////////////////////////////////////////////////////////mutate
		
		hand = new Hand();
		hand.setOwnerSeatWind(seatWind);
		
		pond = new Pond();
		
		//just in case, don't know if it's needed
		brain.clearCallStatus();
		brain.clearTurnActionStatus();
		
		setDrawNeededNormal();
	}
	
	private Player withHand(Hand h){return new Player();}
	private Player withPond(Pond p){return new Player();}
	private Player withSeatWind(Wind w){return new Player();}
	
	private Player withDrawingNeed(DrawingNeed dn){return new Player();}
	private Player withLastDiscard(GameTile d){return new Player();}
	
	private Player withDrawNeededRinshan(){return new Player(drawNeeded.rinshan());}
	private Player withDrawNeededNormal(){return new Player(drawNeeded.normal();}
	private Player withDrawNeededNone(){return new Player(drawNeeded.none());}
	
	
	
	
	
	//lets a player take their turn
	//returns the tile discarded by the player, returns null if the player did not discard (they made a kan or tsumo)
	public GameTile takeTurn(){
/////////////////////////////////////////////////////////////////////////////////mutate
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
	
	private Player discardChosenTile(){
		
		int discardIndex = brain.getChosenDiscardIndex();
		
		GameTile discardedTile = hand.getTile(discardIndex);
		Hand handWithRemovedTile = hand.removeTile(discardIndex);
		Pond pondWithDiscardedTile = pond.addTile(discardedTile);
		
		//set needed draw to normal, since we just discarded a tile
		return this.withHand(handWithRemovedTile).withPond(pondWithDiscardedTile).withLastDiscard(discardedTile).withDrawNeededNormal();
//		return discardedTile;
	}
	
	//removes the most recent tile from the player's pond (because another player called it)
	public Player removeTileFromPond(){return this.withPond(pond.removeMostRecentTile());}
/////////////////////////////////////////////////////////////////////////////////mutate
	public Pond getPond(){return pond;}
	
	public GameTile getLastDiscard(){return lastDiscard;}
	public Hand getHand(){return hand;}
	
	
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
	
	
	
	
	
	
	
	public Player addTileToHand(final GameTile t){
		Hand handWithAddedTile = hand.addTile(t.withOwnerWind(seatWind));
		
		//no longer need to draw (because the player has just drawn)
		return this.withHand(handWithAddedTile).withDrawNeededNone();
	}
	public Player giveStartingHand(GTL startingTiles){
		Hand startingHand = hand.addAll(startingTiles.withWind(seatWind)).sort();
			
		//if the player isn't east, they will need to draw
		if (hand.size() < Hand.MAX_HAND_SIZE)
			return this.withHand(startingHand).withDrawNeededNormal();
		else
			return this.withHand(startingHand).withDrawNeededNone();
	}
	
	
	
	
	
	
	
	
	//ask brain for reaction
	public void reactToDiscard(GameTile tileToReactTo){
/////////////////////////////////////////////////////////////////////////////////mutate
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
/////////////////////////////////////////////////////////////////////////////////mutate
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
/////////////////////////////////////////////////////////////////////////////////mutate
	
	

	
	
	
	
	public boolean holdingRinshan(){return isHoldingRinshanTile;}
	public GameTile getTsumoTile(){return hand.getLastTile();}
	
	
	public boolean handIsFullyConcealed(){return hand.isClosed();}
	
	//returns the number of melds the player has made (open melds and ankans)
	public int getNumMeldsMade(){return hand.numberOfMeldsMade();}
	public List<Meld> getMelds(){return hand.getMelds();}
	
	
	public int getNumKansMade(){return hand.numberOfKansMade();}
	public boolean hasMadeAKan(){return (getNumKansMade() != 0);}
	
	
	
	
	
	
	//seat wind methods
	public Player setSeatWind(Wind wind){return this.withSeatWind(wind);}
	public Player rotateSeat(){return setSeatWind(seatWind.prev());}
	public Player setSeatWindEast(){return setSeatWind(Wind.EAST);}
	public Player setSeatWindSouth(){return setSeatWind(Wind.SOUTH);}
	public Player setSeatWindWest(){return setSeatWind(Wind.WEST);}
	public Player setSeatWindNorth(){return setSeatWind(Wind.NORTH);}
	
	public Wind getSeatWind(){return seatWind;}
	public boolean isDealer(){return getSeatWind().isDealerWind();}
	
	//player number methods
	public void setPlayerNumber(int newNum){
		if (newNum >= 0 && newNum < 4) playerNum = newNum;
	}
/////////////////////////////////////////////////////////////////////////////////mutate
	public int getPlayerNumber(){return playerNum;}
	
	
	//controller methods
/////////////////////////////////////////////////////////////////////////////////mutate
	public void setController(PlayerBrain desiredBrain){brain = desiredBrain;}
	public void setControllerHuman(){setController(new HumanBrain(this));}
	public void setControllerComputer(){
//		RobotBrain robot = new SimpleRobot(this);
//		RobotBrain robot = new SevenTwinBot(this);
//		RobotBrain robot = new TseIiMenBot(this);
//		RobotBrain robot = new PonMonsterBot(this);
		RobotBrain robot = new BeaverBot(this);
//		RobotBrain robot = RandomRobotGenerator.generateRandomComputerPlayer(this);
		
		setController(robot);
	}
	public String getControllerAsString(){return brain.toString();}
	public boolean controllerIsHuman(){return brain.isHuman();}
	public boolean controllerIsComputer(){return brain.isComputer();}
	public PlayerBrain getController(){return brain;}
	
	
	//point methods
	public int getPoints(){return pointsBox.getPoints();}
/////////////////////////////////////////////////////////////////////////////////mutate
	public void pointsIncrease(int amount){pointsBox.add(amount);}
	public void pointsDecrease(int amount){pointsBox.subtract(amount);}
	public boolean pointsIsHakoshita(){return pointsBox.isHakoshita();}

	//profile methods
	public String getPlayerName(){return profile.getPlayerName();}
	public int getPlayerID(){return profile.getPlayerID();}
/////////////////////////////////////////////////////////////////////////////////mutate
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
/////////////////////////////////////////////////////////////////////////////////mutate
		roundTracker = tracker;
	}
	
	
	
	
	
	

	////xxxxxxxxxxxxxBEGIN DEMO METHODS
	//fill hand with demo values
	public void DEMOfillHandChuuren(){hand.DEMOfillChuuren();}
	public void DEMOfillHand(){hand.DEMOfillScattered();}
	public void DEMOfillHandNoTsumo(){DEMOfillHand(); hand.removeTile(hand.size()-1);}

	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand (for debug use)
	public void addTileToHand(int tileID){addTileToHand(new GameTile(tileID));}
/////////////////////////////////////////////////////////////////////////////////mutate
	////xxxxxxxxxxxxxEND DEMO METHODS
	
	
	//will implement these for chankan later
//	public boolean reactToAnkan(GameTile t){return false;}
//	public boolean reactToMinkan(GameTile t){return false;}
	
}
