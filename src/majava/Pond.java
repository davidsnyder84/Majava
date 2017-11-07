package majava;

import java.util.ArrayList;
import java.util.List;

import majava.tiles.PondTile;
import majava.tiles.GameTile;

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
	
	private static final int SIZE_MAX = 30;
	
	
	
	private final List<PondTile> mTiles;
	private int mRiichiTileIndex;
	
	
	
	
	public Pond(){
		mTiles = new ArrayList<PondTile>(SIZE_MAX);
		mRiichiTileIndex = -1;
	}
	
	
	public void addTile(GameTile t){
		mTiles.add(new PondTile(t));
	}
	
	
	
	
	public boolean isNagashiMangan(){
		//nagashi mangan not yet implemented
		return false;
	}
	
	
	
	//returns which tile the player used for riichi
	public PondTile getRiichiTile(){return mTiles.get(mRiichiTileIndex);}
	//returns the most recently discarded tile in the pond
	public PondTile getMostRecentTile(){return mTiles.get(size()-1);}
	public int size(){return mTiles.size();}
	
	
	
	//marks the most recent tile as missing (because it was callled)
	public PondTile removeMostRecentTile(){
		getMostRecentTile().setCalled();
		return getMostRecentTile();
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public String toString(){
		String pondString = "";
		
		final int TILES_PER_LINE = 6;
		for (int i = 0; i < (size() / TILES_PER_LINE) + 1; i++){
			
			pondString += "\t";
			for (int j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < size()); j++)
				pondString += mTiles.get(TILES_PER_LINE*i + j) + " ";
			
			if (TILES_PER_LINE*i < size()) pondString += "\n";
		}
		
		return pondString;
	}
	
	
	
	
	
	//sync pond tilelist with tracker
	public void syncWithRoundTracker(RoundTracker tracker){
		tracker.syncPond(mTiles);
	}
	
	
	
}
