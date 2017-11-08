package majava.summary;

import java.util.List;

import majava.hand.Meld;
import majava.enums.Exclamation;
import majava.enums.Wind;
import majava.player.Player;
import majava.summary.PlayerSummary;
import majava.tiles.GameTile;


//immutable representation of a player
public class ImmutablePlayer{
	
	
	
	private Player player;
	
	
	public ImmutablePlayer(Player p){
		player = p;
	}
	
	
	
	
	
	
	
	
	
	//Player accessor methods
	
	public GameTile getLastDiscard(){return player.getLastDiscard();}
	
	
	public boolean turnActionMadeKan(){return player.turnActionMadeKan();}
	public boolean turnActionMadeAnkan(){return player.turnActionMadeAnkan();}
	public boolean turnActionMadeMinkan(){return player.turnActionMadeMinkan();}
	public boolean turnActionCalledTsumo(){return player.turnActionCalledTsumo();}
	public boolean turnActionChoseDiscard(){return player.turnActionChoseDiscard();}
	public boolean turnActionRiichi(){return player.turnActionRiichi();}
	
	
	//turn actions
	public boolean ableToAnkan(){return player.ableToAnkan();}
	public boolean ableToMinkan(){return player.ableToMinkan();}
	public boolean ableToRiichi(){return player.ableToRiichi();}
	public boolean ableToTsumo(){return player.ableToTsumo();}
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean ableToCallChiL(){return player.ableToCallChiL();}
	public boolean ableToCallChiM(){return player.ableToCallChiM();}
	public boolean ableToCallChiH(){return player.ableToCallChiH();}
	public boolean ableToCallPon(){return player.ableToCallPon();}
	public boolean ableToCallKan(){return player.ableToCallKan();}
	public boolean ableToCallRon(){return player.ableToCallRon();}
	
	
	
	
	
	
	
	
	//accessors
	public int getPlayerNumber(){return player.getPlayerNumber();}
	public Wind getSeatWind(){return player.getSeatWind();}
	public boolean isDealer(){return player.isDealer();}
	
	public String getPlayerName(){return player.getPlayerName();}
	public int getPlayerID(){return player.getPlayerID();}
	

	public int handSize(){return player.handSize();}
	public boolean isInRiichi(){return player.isInRiichi();}
	public boolean isInFuriten(){return player.isInFuriten();}
	public boolean checkTenpai(){return player.checkTenpai();}
	
	
	
	
	
	
	
	//returns call status as an exclamation
	public Exclamation getCallStatusExclamation(){return player.getCallStatusExclamation();}
	
	//returns true if the player called a tile
	public boolean called(){return player.called();}
	//individual call statuses
	public boolean calledChi(){return player.calledChi();}
	public boolean calledChiL(){return player.calledChiL();}
	public boolean calledChiM(){return player.calledChiM();}
	public boolean calledChiH(){return player.calledChiH();}
	public boolean calledPon(){return player.calledPon();}
	public boolean calledKan(){return player.calledKan();}
	public boolean calledRon(){return player.calledRon();}
	
	
	//check if the players needs to draw a tile, and what type of draw (normal vs rinshan)
	public boolean needsDraw(){return player.needsDraw();}
	public boolean needsDrawNormal(){return player.needsDrawNormal();}
	public boolean needsDrawRinshan(){return player.needsDrawRinshan();}
	
	

	
	
	
	
	public boolean holdingRinshan(){return player.holdingRinshan();}
	public GameTile getTsumoTile(){return player.getTsumoTile();}
	
	
	public boolean handIsFullyConcealed(){return player.handIsFullyConcealed();}
	
	//returns the number of melds the player has made (open melds and ankans)
	public int getNumMeldsMade(){return player.getNumMeldsMade();}
	
	//returns a list of the melds that have been made (copy of actual melds), returns an empty list if no melds made
	public List<Meld> getMelds(){return player.getMelds();}
	
	
	public int getNumKansMade(){return player.getNumKansMade();}
	public boolean hasMadeAKan(){return player.hasMadeAKan();}
	
	
	
	
	
	public String getControllerAsString(){return player.getControllerAsString();}
	public boolean controllerIsHuman(){return player.controllerIsHuman();}
	public boolean controllerIsComputer(){return player.controllerIsComputer();}
	
	
	
	
	
	
	
	//accessors for points
	public int getPoints(){return player.getPoints();}
	
	
	
	
	
	
	
	
	
	
	
	//get pond as string
	public String getAsStringPond(){return player.getAsStringPond();}
	public String getAsStringHand(){return player.getAsStringHand();}
	public String getAsStringHandCompact(){return player.getAsStringHandCompact();}
	
	
	
	
	public PlayerSummary getPlayerSummary(){return PlayerSummary.getSummaryFor(player);}
	
	
	
	
	
	public boolean equals(Object other){return player.equals(other);}
	
	public String toString(){return player.toString();}
	
	
	

}
