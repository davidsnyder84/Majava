package majava.summary.entity;

import majava.RoundTracker;
import majava.Wall;
import majava.tiles.Tile;

public class RoundEntities {
	
	public final PlayerTracker[] mPTrackers;
	public final Tile[] mTilesW;
	public final Wall mWall;
	
	public final RoundTracker mRoundTracker;
	
	
	
	public RoundEntities(RoundTracker rTracker, PlayerTracker[] ptrackers, Wall wall, Tile[] tilesW){
		mRoundTracker = rTracker;
		mPTrackers = ptrackers;
		mWall = wall;mTilesW = tilesW;
	}
}
