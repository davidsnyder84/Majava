package majava.summary.entity;

import majava.RoundTracker;
import majava.Wall;
import majava.tiles.GameTile;

public class RoundEntities {
	
	public final PlayerTracker[] playerTrackers;
	public final GameTile[] wallTiles;
	public final Wall wall;
	
	public final RoundTracker roundTracker;
	
	
	
	public RoundEntities(RoundTracker rTracker, PlayerTracker[] ptrackers, Wall reveivedWall, GameTile[] tilesW){
		roundTracker = rTracker;
		playerTrackers = ptrackers;
		wall = reveivedWall;
		wallTiles = tilesW;
	}
}
