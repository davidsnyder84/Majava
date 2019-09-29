package majava.player;

import java.util.List;
import java.util.Scanner;

import majava.hand.Hand;
import majava.hand.Meld;
import majava.Pond;
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
import majava.tiles.TileInterface;
import majava.util.GTL;


//represents a player in the game
//immutable class
public class Player implements Comparable<Player>{
	public static final Player NOBODY = new Player();
	private static final boolean PLEASE_I_NEED_RINSHAN_DRAW = true, RINSHAN_DRAW_NOT_NEEDED = false;
	
	private final PlayerBrain brain;
	private final PlayerProfile profile;
	private final PointsBox pointsBox;
	
	
	private final Hand hand;
	private final Pond pond;
	private final Wind seatWind;
	
	private final int playerNum;///////////////////get rid of this, maybe move it to profile?
	
	
	//these can all disappear (reset) during builder calls
	private final DecisionCall decisionCall;
	private final DecisionTurnAction decisionTurnAction;
	private final boolean needsRinshanDraw;
	
	private final boolean isHoldingRinshanTile;
	private final boolean isRiichi;
	
	
	private Player(PlayerBrain brn, PlayerProfile prof, PointsBox pts, int pnum, Hand h, Pond p, Wind w, boolean rinshanNeeded, DecisionCall decC, DecisionTurnAction decTA){
		if (brn == null) brain = new NullPlayerBrain();
		else brain = brn;
		
		profile = prof;
		pointsBox = pts;
		playerNum = pnum;
		
		seatWind = w;
		pond = p;
		hand = h.setOwnerSeatWind(seatWind);
		
		needsRinshanDraw = rinshanNeeded;
		decisionCall = decC;
		decisionTurnAction = decTA;
		
		//not implemented yet
		isHoldingRinshanTile = false; isRiichi = false;
	}
	private Player(PlayerBrain brn, PlayerProfile prof, PointsBox pts, int pnum, Hand h, Pond p, Wind w){
		this(brn, prof, pts, pnum, h, p, w,
				RINSHAN_DRAW_NOT_NEEDED,
				DecisionCall.DUMMY,
				DecisionTurnAction.DUMMY
		);
	}
	
	private Player(PlayerProfile prof, PointsBox pts, int pnum, Hand h, Pond p, Wind w){
		this(null, prof, pts, pnum, h, p, w);
	}
	public Player(PlayerProfile prof, PointsBox pts, int pnum){
		this(prof, pts, pnum, new Hand(), new Pond(), Wind.UNKNOWN);
	}
	public Player(PlayerProfile newProfile){
		this(newProfile, new PointsBox(), 0);
	}
	public Player(String name){this(new PlayerProfile(name));}
	public Player(){this( ((String)null) );}
	
	
	
	private Player withBrain(PlayerBrain newBrain){return new Player(newBrain, profile, pointsBox, playerNum, hand, pond, seatWind);}
	private Player withPlayerNum(int newPlayerNum){return new Player(brain, profile, pointsBox, newPlayerNum, hand, pond, seatWind);}
	
	private Player withPoints(PointsBox pts){return new Player(brain, profile, pts, playerNum, hand, pond, seatWind);}
	
	private Player withHand(Hand h){return new Player(brain, profile, pointsBox, playerNum, h, pond, seatWind);}
	private Player withPond(Pond p){return new Player(brain, profile, pointsBox, playerNum, hand, p, seatWind);}
	private Player withSeatWind(Wind w){return new Player(brain, profile, pointsBox, playerNum, hand, pond, w);}
	
	private Player withDrawNeededRinshan(){return new Player(brain, profile, pointsBox, playerNum, hand, pond, seatWind, PLEASE_I_NEED_RINSHAN_DRAW, DecisionCall.DUMMY, DecisionTurnAction.DUMMY);}
	private Player withDecisionTurnAction(DecisionTurnAction decTA){return new Player(brain, profile, pointsBox, playerNum, hand, pond, seatWind, RINSHAN_DRAW_NOT_NEEDED, DecisionCall.DUMMY, decTA);}
	private Player withDecisionCall(DecisionCall decC){return new Player(brain, profile, pointsBox, playerNum, hand, pond, seatWind, RINSHAN_DRAW_NOT_NEEDED, decC, DecisionTurnAction.DUMMY);}
	
	
	
	public Player decideTurnAction(){
		
		DecisionTurnAction decision = brain.chooseTurnAction(this);
		return this.withDecisionTurnAction(decision);
	}
	public Player doTurnAction(){
		
		if (turnActionChoseDiscard()){
			return discardChosenTile();
		}
		
		
		if (turnActionMadeKan()){
			Hand handWithKan = hand;
			
			if (turnActionMadeAnkan())
				handWithKan = hand.makeMeldTurnAnkan();
			else if (turnActionMadeMinkan())
				handWithKan = hand.makeMeldTurnMinkan();
			
			return this.withHand(handWithKan).withDrawNeededRinshan();
		}
		
		if (turnActionCalledTsumo()){
			////////////////////////this should never be reached
//			return null;
			////
		}
		
		if (turnActionRiichi()){
//			return null;
			////
		}
		
		
		System.out.println("HOWDIDYOUGETHEREdoTurnAction: " + decisionTurnAction); return null; //not sure if anything can ever reach here
	}
	
	
	private Player discardChosenTile(){
		int discardIndex = decisionTurnAction.getChosenDiscardIndex();
		GameTile discardedTile = hand.getTile(discardIndex);
		
		Hand handWithRemovedTile = hand.removeTile(discardIndex);
		Pond pondWithDiscardedTile = pond.addTile(discardedTile);
		
		//set needed draw to normal, since we just discarded a tile
		return this.withHand(handWithRemovedTile).withPond(pondWithDiscardedTile);
	}
	
	//removes the most recent tile from the player's pond (because another player called it)
	public Player removeTileFromPond(Wind caller){return this.withPond(pond.removeMostRecentTile(caller));}
	public Pond getPond(){return pond;}
	public boolean pondIsEmpty(){return pond.isEmpty();}
	
	public GameTile getLastDiscard(){return pond.getMostRecentTile();}
	public Hand getHand(){return hand;}
	
	
	public boolean turnActionMadeKan(){return (turnActionMadeAnkan() || turnActionMadeMinkan());}
	public boolean turnActionMadeAnkan(){return decisionTurnAction.choseAnkan();}
	public boolean turnActionMadeMinkan(){return decisionTurnAction.choseMinkan();}
	public boolean turnActionCalledTsumo(){return decisionTurnAction.choseTsumo();}
	public boolean turnActionChoseDiscard(){return decisionTurnAction.choseDiscard();}
	public boolean turnActionRiichi(){return decisionTurnAction.choseRiichi();}
	public int chosenDicardIndex(){return decisionTurnAction.getChosenDiscardIndex();}
	
	public boolean hasChosenTurnAction(){
		return turnActionMadeKan() || 
				turnActionMadeAnkan() || 
				turnActionMadeMinkan() || 
				turnActionCalledTsumo() || 
				turnActionChoseDiscard() || 
				turnActionRiichi()
				;
	}
	
	
	//turn actions
	public boolean ableToAnkan(){return hand.ableToAnkan();}
	public boolean ableToMinkan(){return hand.ableToMinkan();}
	public boolean ableToRiichi(){return !isInRiichi() && hand.ableToRiichi();}
	public boolean ableToTsumo(){return hand.ableToTsumo();}
	
	
	
	
	
	public Player addTileToHand(final GameTile t){
		Hand handWithAddedTile = hand.addTile(t.withOwnerWind(seatWind));
		
		//no longer need to draw (because the player has just drawn)
		return this.withHand(handWithAddedTile);
	}
	public Player giveStartingHand(GTL startingTiles){
		GTL startingTilesWithWind = startingTiles.withWind(seatWind);
		Hand startingHand = hand.addAll(startingTilesWithWind).sort();
		
		return this.withHand(startingHand);
	}
	
	
	
	
	
	
	
	
	//ask brain for reaction
	public Player reactToDiscard(GameTile candidate){
		if (alreadyReactedTo(candidate))
			return this; //don't ask twice for the same tile
		
		DecisionCall chosenCall = DecisionCall.None(candidate);
		
		if (ableToCallTile(candidate))
			chosenCall = brain.reactToDiscard(this, candidate);
		
		return this.withDecisionCall(chosenCall);
	}
	
	//checks if the player is able to make a call on Tile t (actual checks performed)
	public boolean ableToCallTile(GameTile candidate){
		
		//if riichi, only allow ron
		if (isInRiichi())
			return ableToCallRon(candidate);
		
		return hand.canCallTile(candidate);
	}
	
	public boolean ableToCallChiL(GameTile candidate){return !isInRiichi() && hand.ableToChiL(candidate);}
	public boolean ableToCallChiM(GameTile candidate){return !isInRiichi() && hand.ableToChiM(candidate);}
	public boolean ableToCallChiH(GameTile candidate){return !isInRiichi() && hand.ableToChiH(candidate);}
	public boolean ableToCallPon(GameTile candidate){return !isInRiichi() && hand.ableToPon(candidate);}
	public boolean ableToCallKan(GameTile candidate){return !isInRiichi() && hand.ableToKan(candidate);}
	public boolean ableToCallRon(GameTile candidate){return hand.ableToRon(candidate);}
	
	
	
	
	
	
	
	//forms a meld using the given tile
	public Player makeMeld(GameTile claimedTile){
//		prevent an error case that will probably never happen if (!called()){System.out.println("-----Error: No meld to make (the player didn't make a call!!)"); return;}
		
		//make the meld
		Hand handWithNewMeld = null;
		if (calledChiL(claimedTile)) handWithNewMeld = hand.makeMeldChiL(claimedTile);
		else if (calledChiM(claimedTile)) handWithNewMeld = hand.makeMeldChiM(claimedTile);
		else if (calledChiH(claimedTile)) handWithNewMeld = hand.makeMeldChiH(claimedTile);
		else if (calledPon(claimedTile)) handWithNewMeld = hand.makeMeldPon(claimedTile);
		else if (calledKan(claimedTile)) handWithNewMeld = hand.makeMeldKan(claimedTile);
		
		
		if (calledKan(claimedTile))
			return this.withHand(handWithNewMeld).withDrawNeededRinshan();
		
		return this.withHand(handWithNewMeld);
	}
	
	
	
	
	
	
	//accessors
	public int handSize(){return hand.size();}
	public boolean isInRiichi(){return isRiichi;}
	public boolean checkTenpai(){return hand.isInTenpai();}
	
	
	
	//returns call status as an exclamation
	public Exclamation getCallStatusExclamation(){return decisionCall.getCallStatusExclamation();}	//do I need isFor(t) here?
	
	//returns true if the player called a tile
	public boolean called(GameTile t){return decisionCall.isFor(t) && decisionCall.called();}
	//individual call statuses
	public boolean calledChi(GameTile t){return decisionCall.isFor(t) && decisionCall.calledChi();}
	public boolean calledChiL(GameTile t){return decisionCall.isFor(t) && decisionCall.calledChiL();}
	public boolean calledChiM(GameTile t){return decisionCall.isFor(t) && decisionCall.calledChiM();}
	public boolean calledChiH(GameTile t){return decisionCall.isFor(t) && decisionCall.calledChiH();}
	public boolean calledPon(GameTile t){return decisionCall.isFor(t) && decisionCall.calledPon();}
	public boolean calledKan(GameTile t){return decisionCall.isFor(t) && decisionCall.calledKan();}
	public boolean calledRon(GameTile t){return decisionCall.isFor(t) && decisionCall.calledRon();}
	
	public boolean alreadyReactedTo(GameTile t){return decisionCall.isFor(t);}
	
	//check if the players needs to draw a tile, and what type of draw (normal vs rinshan)
	public boolean needsDraw(){return !handIsFull();}
	public boolean needsDrawRinshan(){return needsRinshanDraw;}
	
	

	
	
	
	public boolean handIsFull(){return hand.isFull();}
	public boolean needsToDiscard(){return handIsFull();}
	
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
	public Player setPlayerNumber(int newNum){
		if (newNum < 0 || newNum >= 4)
			return this;
		return this.withPlayerNum(newNum);
	}
/////////////////////////////////////////////////////////////////////////////////mutate
	public int getPlayerNumber(){return playerNum;}
	
	
	//controller methods
	public Player setController(PlayerBrain desiredBrain){return this.withBrain(desiredBrain);}
	public Player setControllerHuman(){return setController(new HumanBrain());}
	public Player setControllerComputer(){
//		RobotBrain robot = new SimpleRobot();
//		RobotBrain robot = new SevenTwinBot();
//		RobotBrain robot = new TseIiMenBot();
//		RobotBrain robot = new PonMonsterBot();
		RobotBrain robot = new BeaverBot();
//		RobotBrain robot = RandomRobotGenerator.generateRandomComputerPlayer();
		
		return setController(robot);
	}
	public String getControllerAsString(){return brain.toString();}
	public boolean controllerIsHuman(){return brain.isHuman();}
	public boolean controllerIsComputer(){return brain.isComputer();}
	public PlayerBrain getController(){return brain;}
	
	
	//point methods
	public int getPoints(){return pointsBox.getPoints();}
	public boolean pointsIsHakoshita(){return pointsBox.isHakoshita();}
	public Player pointsIncrease(int amount){return this.withPoints(pointsBox.add(amount));}
	public Player pointsDecrease(int amount){return this.withPoints(pointsBox.subtract(amount));}
	

	//profile methods
	public String getPlayerName(){return profile.getPlayerName();}
	public int getPlayerID(){return profile.getPlayerID();}
/////////////////////////////////////////////////////////////////////////////////mutate
//	public void setPlayerName(String newName){profile.setPlayerName(newName);}
	
	
	
	
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
	public boolean equals(Object other){return (this == other);} /////////////do I need a better definition for equals?
	@Override
	public String toString(){return (getPlayerName() + " (" + seatWind.toChar() +" player) ");}
	
	
	@Override
	final public int compareTo(Player other){return this.seatWind.compareTo(other.seatWind);}
	
	
	

	////xxxxxxxxxxxxxBEGIN DEMO METHODS
	//fill hand with demo values
	public void DEMOfillHandChuuren(){hand.DEMOfillChuuren();}
	public void DEMOfillHand(){hand.DEMOfillScattered();}
	public void DEMOfillHandNoTsumo(){DEMOfillHand(); hand.removeTile(hand.size()-1);}

	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand (for debug use)
	public Player addTileToHand(int tileID){return addTileToHand(new GameTile(tileID));}
/////////////////////////////////////////////////////////////////////////////////mutate
	////xxxxxxxxxxxxxEND DEMO METHODS
	
	
	//will implement these for chankan later
//	public boolean reactToAnkan(GameTile t){return false;}
//	public boolean reactToMinkan(GameTile t){return false;}
	
}
