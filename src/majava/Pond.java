package majava;

import java.util.ArrayList;
import java.util.List;

import majava.tiles.PondTile;
import majava.tiles.GameTile;


//represents a player's pond (êÏ) of discarded tiles
public class Pond {
	private static final int SIZE_MAX = 30;
	private static final int NO_RIICHI_INDEX = -1;
	
	
	private final List<PondTile> tiles;
	private int indexOfRiichiTile;
	
	
	
	public Pond(){
		tiles = new ArrayList<PondTile>(SIZE_MAX);
		indexOfRiichiTile = NO_RIICHI_INDEX;
	}
	
	public void addTile(GameTile t){tiles.add(new PondTile(t));}
	
	public PondTile getRiichiTile(){return getTile(indexOfRiichiTile);}
	public PondTile getMostRecentTile(){return getTile(size() - 1);}
	public int size(){return tiles.size();}
	
	private PondTile getTile(int index){return tiles.get(index);}
	
	
	//marks the most recent tile as missing (because it was callled)
	public PondTile removeMostRecentTile(){
		getMostRecentTile().setCalled();
		return getMostRecentTile();
	}
	
	
	
	
	public boolean isNagashiMangan(){
		/////implement nagashi mangan here
		return false;
	}
	
	
	
	@Override
	public String toString(){
		String pondString = "";
		
		final int TILES_PER_LINE = 6;
		for (int i = 0; i < (size() / TILES_PER_LINE) + 1; i++){
			
			pondString += "\t";
			for (int j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < size()); j++)
				pondString += getTile(TILES_PER_LINE*i + j) + " ";
			
			if (TILES_PER_LINE*i < size()) pondString += "\n";
		}
		
		return pondString;
	}
	
	
	
	//sync pond tilelist with tracker
	public void syncWithRoundTracker(RoundTracker tracker){
		tracker.syncPond(tiles);
	}
	
}
