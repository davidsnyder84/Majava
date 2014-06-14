package majava.summary.entity;

import majava.RoundTracker;
import majava.Wall;
import majava.tiles.GameTile;
import majava.tiles.TileInterface;

public class RoundEntities {
	
	public final PlayerTracker[] mPTrackers;
	public final GameTile[] mTilesW;
	public final Wall mWall;
	
	public final RoundTracker mRoundTracker;
	
	
	
	public RoundEntities(RoundTracker rTracker, PlayerTracker[] ptrackers, Wall wall, GameTile[] tilesW){
		mRoundTracker = rTracker;
		mPTrackers = ptrackers;
		mWall = wall;mTilesW = tilesW;
	}
}
