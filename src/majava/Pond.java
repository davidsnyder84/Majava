package majava;

import java.util.ArrayList;
import java.util.List;

import majava.tiles.PondTile;
import majava.tiles.GameTile;
import majava.util.GTL;


//represents a player's pond (êÏ) of discarded tiles
public class Pond implements Cloneable{
	private static final int SIZE_MAX = 30;
	
	private final GTL tiles;
	
	public Pond(GTL pondTiles){
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
		return -1;
	}
	public PondTile getRiichiTile(){return getTile(indexOfRiichiTile());}
	public PondTile getMostRecentTile(){return getTile(size() - 1);}
	public int size(){return tiles.size();}
	
	public PondTile getTile(int index){return (PondTile) tiles.get(index);}
	public List<PondTile> getTilesAsList(){
		ArrayList<PondTile> pTiles = new ArrayList<PondTile>(size());
		for (GameTile t: tiles.toList())
			pTiles.add((PondTile) t);
		
		return pTiles;
	}
	
	//conditions for nagashi mangan: composed of only TYC, and no tiles have been called
	public boolean isElligibleForNagashiMangan(){
		for (GameTile t: tiles)
			if (!t.isYaochuu() || ((PondTile)t).wasCalled())
				return false;
		return true;
	}
	
	
	//mutators
	public Pond addTile(GameTile t){return this.withTiles(tiles.add(new PondTile(t)));}
	
	//the removed tile will remain in the pond, but it will be marked as "called"
	public Pond removeMostRecentTile(){
		return this.withTiles(tiles.setLast(getMostRecentTile().withCalled()));
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
}
