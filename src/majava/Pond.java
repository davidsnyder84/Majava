package majava;

import majava.tiles.PondTile;
import majava.tiles.GameTile;
import majava.tiles.TileInterface;
import majava.util.TileList;

/*
Class: Pond
represents a player's pond of discarded tiles

data:
	mTiles - list of tiles in the pond
	mRiichiTileIndex - the index of the tile used to riichi
	
methods:
	
	public:
		mutators:
		addTile - adds a tile to the pond
	 	removeMostRecentTile - marks the most recent tile as missing (because it was callled)
	 	
	 	accessors:
	 	isNagashiMangan - returns true if the player has made a nagashi mangan in their pond
		getRiichiTile - returns which tile the player used for riichi
		getMostRecentTile - returns the most recently discarded tile in the pond
	
	other:
		syncWithRoundTracker - associates this pond with the round tracker
*/
public class Pond {
	
	
	private static final int SIZE_DEFAULT = 30;
//	private static final int SIZE_MAX = 30;
	
	
	
	
	private final TileList mTiles;
	
	private int mRiichiTileIndex;
	
	
	
	
	public Pond(){
		mTiles = new TileList(SIZE_DEFAULT);
		mRiichiTileIndex = -1;
	}
	
	
	public void addTile(GameTile t){
		mTiles.add(new PondTile(t));
	}
	
	
	
	
	//returns true if the player has made a nagashi mangan in their pond
	public boolean isNagashiMangan(){
		return false;
	}
	
	
	
	//returns which tile the player used for riichi
	public GameTile getRiichiTile(){return (GameTile)mTiles.get(mRiichiTileIndex);}
	
	//returns the most recently discarded tile in the pond
	public GameTile getMostRecentTile(){return (GameTile)mTiles.getLast();}
	
	
	
	//marks the most recent tile as missing (because it was callled)
	public GameTile removeMostRecentTile(){
		((PondTile) mTiles.getLast()).setCalled();
		return getMostRecentTile();
	}
	
	
	
	
	
	
	
	
	
	@Override
	public String toString(){
		
		int i, j;
		String pondString = "";
		
		final int TILES_PER_LINE = 6;
		for (i = 0; i < (mTiles.size() / TILES_PER_LINE) + 1; i++){
			
			pondString += "\t";
			for (j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < mTiles.size()); j++)
				pondString += mTiles.get(TILES_PER_LINE*i + j) + " ";
			
			if (TILES_PER_LINE*i < mTiles.size()) pondString += "\n";
		}
		
		return pondString;
	}
	
	
	
	
	
	//sync pond tilelist with tracker
	public void syncWithRoundTracker(RoundTracker tracker){
		tracker.syncPond(mTiles);
	}
	
	
	
}
