package majava.summary.entity;

import java.util.List;

import majava.Hand;
import majava.Meld;
import majava.Player;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.tiles.Tile;
import majava.util.TileList;

public class RoundEntities {
	
	private static final int NUM_PLAYERS = 4;
	
	
	public final PlayerTracker[] mPTrackers;
	public final Tile[] mTilesW;
	public final Wall mWall;
	
	public final RoundTracker mRoundTracker;
	
	
	
	
	public RoundEntities(RoundTracker rTracker, PlayerTracker[] ptrackers, Wall wall, Tile[] tilesW){
		mRoundTracker = rTracker;
		mPTrackers = ptrackers;
		mWall = wall;mTilesW = tilesW;
	}
	public RoundEntities(RoundTracker rTracker, Player[] pPlayers, Hand[] pHands, TileList[] pHandTiles, Pond[] pPonds, TileList[] pPondTiles, List<List<Meld>> pMelds, Wall wall, Tile[] tilesW){
		mRoundTracker = rTracker;
		
		mPTrackers = new PlayerTracker[NUM_PLAYERS];
		for (int i = 0; i < NUM_PLAYERS; i++) mPTrackers[i] = new PlayerTracker(pPlayers[i], pHands[i], pHandTiles[i], pPonds[i], pPondTiles[i], pMelds.get(i));
		
		mWall = wall;mTilesW = tilesW;
	}
	
}
