package majava.summary.entity;

import majava.RoundTracker;
import majava.Wall;
import majava.tiles.GameTile;

public class RoundEntities {
	
	public final PlayerTracker[] playerTrackers;
	private final GameTile[] wallTiles;
	private final Wall wall;
	
	public final RoundTracker roundTracker;
	
////////////////////
	public final GameTile[] getWallTiles(){return wallTiles.clone();}
	public final String wallToString(){return wall.toString();}
	public final String deadWallToString(){return wall.toStringDeadWall();}
/////////////////////
	
	
	public RoundEntities(RoundTracker rTracker, PlayerTracker[] ptrackers, Wall reveivedWall, GameTile[] tilesW){
		roundTracker = rTracker;
		playerTrackers = ptrackers;
		wall = reveivedWall;
		wallTiles = tilesW;
	}
}
