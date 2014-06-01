package majava.summary;

import java.util.List;

import majava.Meld;
import majava.Player;
import majava.enums.Exclamation;
import majava.enums.Wind;
import majava.summary.PlayerSummary;
import majava.tiles.Tile;

/*
Class: ImmutablePlayer
immutable representation of a player

methods:
	constructors: requires a Player
*/
public class ImmutablePlayer{
	
	
	
	private Player mPlayer;
	
	
	public ImmutablePlayer(Player p){
		mPlayer = p;
	}
	
	
	
	
	
	
	
	
	
	//Player accessor methods
	
	public Tile getLastDiscard(){return mPlayer.getLastDiscard();}
	
	
	public boolean turnActionMadeKan(){return mPlayer.turnActionMadeKan();}
	public boolean turnActionMadeAnkan(){return mPlayer.turnActionMadeAnkan();}
	public boolean turnActionMadeMinkan(){return mPlayer.turnActionMadeMinkan();}
	public boolean turnActionCalledTsumo(){return mPlayer.turnActionCalledTsumo();}
	public boolean turnActionChoseDiscard(){return mPlayer.turnActionChoseDiscard();}
	public boolean turnActionRiichi(){return mPlayer.turnActionRiichi();}
	
	
	//turn actions
	public boolean ableToAnkan(){return mPlayer.ableToAnkan();}
	public boolean ableToMinkan(){return mPlayer.ableToMinkan();}
	public boolean ableToRiichi(){return mPlayer.ableToRiichi();}
	public boolean ableToTsumo(){return mPlayer.ableToTsumo();}
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean ableToCallChiL(){return mPlayer.ableToCallChiL();}
	public boolean ableToCallChiM(){return mPlayer.ableToCallChiM();}
	public boolean ableToCallChiH(){return mPlayer.ableToCallChiH();}
	public boolean ableToCallPon(){return mPlayer.ableToCallPon();}
	public boolean ableToCallKan(){return mPlayer.ableToCallKan();}
	public boolean ableToCallRon(){return mPlayer.ableToCallRon();}
	
	
	
	
	
	
	
	
	//accessors
	public int getPlayerNumber(){return mPlayer.getPlayerNumber();}
	public Wind getSeatWind(){return mPlayer.getSeatWind();}
	public boolean isDealer(){return mPlayer.isDealer();}
	
	public String getPlayerName(){return mPlayer.getPlayerName();}
	public int getPlayerID(){return mPlayer.getPlayerID();}
	

	public int getHandSize(){return mPlayer.getHandSize();}
	public boolean getRiichiStatus(){return mPlayer.getRiichiStatus();}
	public boolean checkFuriten(){return mPlayer.checkFuriten();}
	public boolean checkTenpai(){return mPlayer.checkTenpai();}
	
	
	
	
	
	
	
	//returns call status as an exclamation
	public Exclamation getCallStatusExclamation(){return mPlayer.getCallStatusExclamation();}
	
	//returns true if the player called a tile
	public boolean called(){return mPlayer.called();}
	//individual call statuses
	public boolean calledChi(){return mPlayer.calledChi();}
	public boolean calledChiL(){return mPlayer.calledChiL();}
	public boolean calledChiM(){return mPlayer.calledChiM();}
	public boolean calledChiH(){return mPlayer.calledChiH();}
	public boolean calledPon(){return mPlayer.calledPon();}
	public boolean calledKan(){return mPlayer.calledKan();}
	public boolean calledRon(){return mPlayer.calledRon();}
	
	
	//check if the players needs to draw a tile, and what type of draw (normal vs rinshan)
	public boolean needsDraw(){return mPlayer.needsDraw();}
	public boolean needsDrawNormal(){return mPlayer.needsDrawNormal();}
	public boolean needsDrawRinshan(){return mPlayer.needsDrawRinshan();}
	
	

	
	
	
	
	public boolean holdingRinshan(){return mPlayer.holdingRinshan();}
	public Tile getTsumoTile(){return mPlayer.getTsumoTile();}
	
	
	public boolean handIsFullyConcealed(){return mPlayer.handIsFullyConcealed();}
	
	//returns the number of melds the player has made (open melds and ankans)
	public int getNumMeldsMade(){return mPlayer.getNumMeldsMade();}
	
	//returns a list of the melds that have been made (copy of actual melds), returns an empty list if no melds made
	public List<Meld> getMelds(){return mPlayer.getMelds();}
	
	
	public int getNumKansMade(){return mPlayer.getNumKansMade();}
	public boolean hasMadeAKan(){return mPlayer.hasMadeAKan();}
	
	
	
	
	
	public String getControllerAsString(){return mPlayer.getAsStringController();}
	public boolean controllerIsHuman(){return mPlayer.controllerIsHuman();}
	public boolean controllerIsComputer(){return mPlayer.controllerIsComputer();}
	
	
	
	
	
	
	
	//accessors for points
	public int getPoints(){return mPlayer.getPoints();}
	
	
	
	
	
	
	
	
	
	
	
	//get pond as string
	public String getAsStringPond(){return mPlayer.getAsStringPond();}
	public String getAsStringHand(){return mPlayer.getAsStringHand();}
	public String getAsStringHandCompact(){return mPlayer.getAsStringHandCompact();}
	
	
	
	
	public PlayerSummary getPlayerSummary(){return mPlayer.getPlayerSummary();}
	
	
	
	
	
	public boolean equals(Object other){return mPlayer.equals(other);}
	
	public String toString(){return mPlayer.toString();}
	
	
	

}
