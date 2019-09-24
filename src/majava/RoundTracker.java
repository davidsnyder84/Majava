package majava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.userinterface.GameUI;
import majava.util.GTL;
import majava.player.Player;
import majava.summary.StateOfGame;
import majava.summary.PlayerSummary;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.enums.Wind;
import majava.wall.Wall;


//other objects can ask a RoundTracker for universally available information about the round
//(analogous to what you can see with your own eyes)
public class RoundTracker {
	private static final int NUM_PLAYERS = 4;
	
	
	private final Round round;
	private final Wall wall;	//duplicate
	
	
	
	public RoundTracker(Round roundToTrack, Wall receivedWall, Player[] playerArray){
		round = roundToTrack;	
		
		wall = receivedWall;
		
//		for (Player p: players)
//			p.syncWithRoundTracker(this);
	}
	
	
	
	
	
	/////I kind of want to get rid of these eventually, but they're used by the UIs
	public int whoseTurn(){return round.whoseTurnNumber();}
	
	
//	public RoundResultSummary getResultSummary(){return round.getResultSummary();}	
	

	
}

