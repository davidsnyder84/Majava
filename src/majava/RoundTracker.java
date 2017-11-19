package majava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.userinterface.GameUI;
import majava.util.GameTileList;
import majava.player.Player;
import majava.summary.RoundResultSummary;
import majava.summary.entity.PlayerTracker;
import majava.summary.entity.RoundEntities;
import majava.tiles.GameTile;
import majava.tiles.PondTile;
import majava.enums.Wind;
import majava.hand.Hand;
import majava.hand.Meld;


//other objects can ask a RoundTracker for information about the round
public class RoundTracker {
	private static final int NUM_PLAYERS = 4;
	private static final int NUM_MELDS_TO_TRACK = 5;
	
	
	private final Round round;
	private final RoundEntities roundEntities;
	private final Wall wall;	//duplicate
	private final GameTile[] wallTiles;	//duplicate
	
	private final Player[] players;
	
	
	public RoundTracker(Round roundToTrack, Wall receivedWall, Player[] playerArray, GameUI ui){
		round = roundToTrack;	
		
		wall = receivedWall;
		wallTiles = wall.syncWithTracker(this);
		
		players = playerArray.clone();
		for (Player p: players)
			p.syncWithRoundTracker(this);
		
		roundEntities = new RoundEntities(this, makePlayerTrackers(), wall, wallTiles);
		
		__syncWithUI(ui);
	}
	//overloaded without UI
	public RoundTracker(Round roundToTrack, Wall receivedWall, Player[] playerArray){this(roundToTrack, receivedWall, playerArray, null);}

	private PlayerTracker[] makePlayerTrackers(){
		PlayerTracker[] trackers = {new PlayerTracker(players[0]), new PlayerTracker(players[1]), new PlayerTracker(players[2]), new PlayerTracker(players[3])};		
		return trackers;
	}
	
	private void __syncWithUI(GameUI ui){
		if (ui == null) return;
		ui.syncWithRoundTracker(roundEntities);
	}
	
	
	
	
	
	
	/////I want to get rid of these eventually, but they're used by the UIs
	public int whoseTurn(){return round.whoseTurnNumber();}
	public Player currentPlayer(){return round.currentPlayer();}	//used by Scorer
	public boolean callWasMadeOnDiscard(){return round.callWasMadeOnDiscard();}
	public GameTile getMostRecentDiscard(){return round.mostRecentDiscard();}
	//these too, maybe
	public RoundResultSummary getResultSummary(){return round.getResultSummary();}	
	public String getRoundResultString(){return round.getRoundResultString();}
	public boolean roundIsOver(){return round.roundIsOver();}
	
	
	private Player neighborOffsetOf(Player p, int offset){
		return players[(p.getPlayerNumber() + offset) % NUM_PLAYERS];
	}
	public Player neighborNextPlayer(Player p){return neighborShimochaOf(p);}
	public Player neighborShimochaOf(Player p){return neighborOffsetOf(p, 1);}
	public Player neighborToimenOf(Player p){return neighborOffsetOf(p, 2);}
	public Player neighborKamichaOf(Player p){return neighborOffsetOf(p, 3);}
	
	
	
	
	//returns true if multiple players have made kans, returns false if only one player or no players have made kans
	private boolean multiplePlayersHaveMadeKans(){
		//count the number of players who have made kans
		int count = 0;
		for (Player p: players){
			if (p.hasMadeAKan())
				count++;
		}
		return (count > 1);
	}
	//returns true if a round-ending number of kans have been made
	//returns true if 5 kans have been made, or if 4 kans have been made by multiple players
	public boolean tooManyKans(){
		final int KAN_LIMIT = 4;
		if (getNumKansMade() < KAN_LIMIT) return false;
		if (getNumKansMade() == KAN_LIMIT && !multiplePlayersHaveMadeKans()) return false;		
		return true;
	}
	
	public int getNumKansMade(){
		int count = 0;
		for (Player p: players) count += p.getNumKansMade();
		return count;
	}
	
	
	
	public int getNumTilesLeftInWall(){return wall.numTilesLeftInWall();}
	
	
	public Wind getRoundWind(){return round.getRoundWind();}
	public int getRoundNum(){return round.getRoundNum();}
	public int getRoundBonusNum(){return round.getRoundBonusNum();}
	
	
	public Wind getWindOfSeat(int seat){
		if (seat < 0 || seat >= NUM_PLAYERS) return null;
		return players[seat].getSeatWind();
	}
	
}


/*
Class: RoundTracker
	public:
	 	accessors:
	 	getRoundWind, getRoundNum, getRoundBonusNum - return the corresponding round info
	 	
	 	currentPlayer - returns the Player whose turn it is (reference to mutable object)
	 	neighborShimochaOf, etc - returns the corresponding neighbor Player of the given player (reference to mutable object)
	 	getSeatNumber - returns the seat number of the given player
	 	
	 	getMostRecentDiscard - returns the most recently discarded tile
	 	callWasMadeOnDiscard - returns true if any player made a call on the most recent discard
	 	
	 	getNumKansMade - returns the number of kans made in the round
	 	getNumTilesLeftInWall - returns the number of tiles left in the wall
	 	
	 	roundIsOver - returns true if the round has ended
	 	roundEndedWithDraw, roundEndedWithVictory - returns true if the round ended with the corresponding result
	 	printRoundResult - prints the round result
	 	getRoundResultString - returns the round result as a string
	 	getWinningHandString - returns a string representation of the winner's winning hand
	 	
	setup:
		syncWall
		setupPlayerTrackers - sets up player trackers (track players, their hands, and their ponds)
		syncWithWall - set up association with the wall
		syncWithViewer - set up association with the GUI
		
		syncPlayer - called by a player, associates that player with the tracker
		syncHand - called by a hand, associates that hand with the tracker
*/

