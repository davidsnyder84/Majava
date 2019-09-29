package majava;

import java.util.ArrayList;
import java.util.List;

import majava.enums.Wind;
import majava.tiles.PondTile;
import majava.tiles.GameTile;
import majava.util.GTL;


//represents a player's pond (êÏ) of discarded tiles
public class Pond implements Cloneable{
	private static final int SIZE_MAX = 30;
	private static final int NOT_FOUND = -82;
	
	private final GTL tiles;
	
	public Pond(GTL addTheseTiles){;
		GTL pondTiles = new GTL();
		
		//PondTileList might be useful here
		for (GameTile t: addTheseTiles)
			if (t instanceof PondTile)
				pondTiles = pondTiles.add(t);
			else
				pondTiles = pondTiles.add(new PondTile(t));
		
		tiles = pondTiles;
	}
	public Pond(){this(new GTL());}
	public Pond(Pond other){this(other.tiles);}
	public Pond clone(){return new Pond(this);}
	
	private Pond withTiles(GTL newTiles){return new Pond(newTiles);}
	
	
	
	//accessors
	public int indexOfRiichiTile(){
		for (int index = 0; index < size(); index++)
			if (getTile(index).isRiichiTile())
				return index;		
		return NOT_FOUND;
	}
	public PondTile getRiichiTile(){return getTile(indexOfRiichiTile());} //NPE if there is no riichi tile
	public boolean containsRiichiTile(){return (indexOfRiichiTile() != NOT_FOUND);}
	
	public PondTile getMostRecentTile(){return getLast();}
	public int size(){return tiles.size();}
	
	
	public PondTile getTile(int index){return (PondTile) tiles.get(index);}
	public PondTile getFirst(){return getTile(0);}
	public PondTile getLast(){return getTile(size() - 1);}
	public List<PondTile> getTilesAsList(){
		ArrayList<PondTile> pTiles = new ArrayList<PondTile>(size());
		for (GameTile t: tiles.toList())
			pTiles.add((PondTile) t);
		
		return pTiles;
	}
	
	//conditions for nagashi mangan: composed of only TYC, and no tiles have been called
	public boolean isElligibleForNagashiMangan(){
		if (!isUntouched()) return false;
		
		for (GameTile t: tiles) if (!t.isYaochuu()) return false;
		return true;
	}
	//untouched = no tiles have been called
	public boolean isUntouched(){
		for (GameTile t : tiles) if (((PondTile)t).wasCalled()) return false;
		return true;
	}
	
	public boolean isEmpty(){return tiles.isEmpty();}
	
	
	//mutators
	public Pond addTile(GameTile t){return this.withTiles(tiles.add(new PondTile(t)));}
	public Pond addTile(int tileID){return addTile(new GameTile(tileID));}
	public Pond addAll(GTL addThese){
		Pond thisWithAdded = this;
		for (GameTile t : addThese)
			thisWithAdded = thisWithAdded.addTile(t);
		return thisWithAdded;
	}
	
	//the removed tile will remain in the pond, but it will be marked as "called"
	public Pond removeMostRecentTile(Wind caller){
		PondTile calledTile = getMostRecentTile().setCalled(caller);
		GTL tilesWithLastSet = tiles.setLast(calledTile);
		
		return this.withTiles(tilesWithLastSet);
	}
	
	
	
	@Override
	public String toString(){
		String pondString = "";
		
		final int TILES_PER_LINE = 6;
		for (int i = 0; i < (size() / TILES_PER_LINE) + 1; i++){
			
			pondString += "\t";
			for (int j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < size()); j++){
				PondTile tile = getTile(TILES_PER_LINE*i + j);
				String separator = tile.wasCalled() ? "@ " : "  ";
				pondString += tile + separator;
			}
			
			if (TILES_PER_LINE*i < size()) pondString += "\n";
		}
		return pondString;
	}
}
