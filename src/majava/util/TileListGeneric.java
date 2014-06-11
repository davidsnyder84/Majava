package majava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import utility.ConviniList;

import majava.tiles.HandCheckerTile;
import majava.tiles.GameTile;
import majava.tiles.TileInterface;


/*
Class: TileListGeneric
a wrapper class for an List of Tiles. adds extra functionalities

data:
	mTiles - a list that holds the list of tiles
	mSorter - used to sort the list
	
methods:
	
	constructors:
	Takes List / Takes initial capacity / Takes Array or Var args
	
	public:
		mutators:
		removeFirst, removeLast - removes and returns a tile in the list, returns null if the list is empty
		removeMultiple - removes multiple indices from the list
		sort, sortAscending, sortDescending, shuffle - sort the list in specified order
	 	
	 	accessors:
		getFirst, getLast - returns a tile in the list, returns null if the list is empty
		subList - returns a sublist, as a TileListGeneric from fromIndex (inclusive) to toIndex (exclusive)
		
		findAllIndicesOf - searches the list for all occurences of Tile t, returns a List of integer indices of where that tile occurred
		makeCopy - returns a copy of the list
		makeCopyNoDuplicates - returns a copy of the list with no duplicate tiles
		makeCopyWithCheckers - makes a copy of the list with checkers
		
		
		methods from Lsist:
		add, remove, size, get, contains, isEmpty, indexOf, lastIndexOf, set, clear, trimToSize, ensureCapacity, iterator
*/
public class TileListGeneric<TileType> extends ConviniList<TileInterface>{
	private static final long serialVersionUID = -3919481636758323535L;


	//creates a new list with the given capacity
	public TileListGeneric(int capacity){super(capacity);}
	//takes a List
	public TileListGeneric(List<TileInterface> tiles){
		this(tiles.size());
		for (TileInterface t: tiles) add(t);
	}
	//can take an array, or a var args
	public TileListGeneric(TileInterface... tiles){this(Arrays.asList(tiles));}
	
	//overloaded for a list of integer ids, makes a list of tiles out of them
	public TileListGeneric(int... ids){
		this(ids.length);
		for (int id: ids) add(new GameTile(id));
	}
	public TileListGeneric(){this(10);}
	
	
	
	//return a new TileListGeneric with a COPY (independent) of each tile in the list
	public TileListGeneric makeCopy(){
		return new TileListGeneric(this);
	}
	//return a new TileListGeneric with a COPY (independent) of each tile in the list
	public TileListGeneric makeCopyNoDuplicates(){
		TileListGeneric copy = new TileListGeneric(size());
		
		for (TileInterface t: this)
			if (!copy.contains(t))
				copy.add(t.clone());
//				copy.add(new GameTile(t));
		return copy;
	}
	
	
	public TileListGeneric makeCopyWithCheckers(){
		TileListGeneric copy = new TileListGeneric(size());
//		for (Tile t: this) copy.add(new HandCheckerTile(t));
		for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile((GameTile)get(i)));
//		for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile(get(i)));
		return copy;
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
	//add, overloaded for tileID
	public boolean add(int id){return add(new GameTile(id));}
	
	//indexOf, overloaded for Tile ID
	public int indexOf(int tileID){return indexOf(new GameTile(tileID));}
	public int indexOf(Integer tileID){return indexOf(tileID.intValue());}
	
	//contains, overloaded for tileID
	public boolean contains(int id){return contains(new GameTile(id));}
	public boolean contains(Integer id){return contains(id.intValue());}
	
	
	//overloaded for tileID
	public int findHowManyOf(int id){return findHowManyOf(new GameTile(id));}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public String toString(){
		String tilesString = "";
		//add the tiles to the string
		for (TileInterface t: this) tilesString += t.toString() + " ";
		if (tilesString != "") tilesString = tilesString.substring(0, tilesString.length() - 1);
		
		return tilesString;
	}
	
	
	@Override
	public boolean equals(Object other){
		if (other == null || (other instanceof TileListGeneric) == false) return false;
		if (((TileListGeneric)other).size() != this.size()) return false;
		
		int size = this.size(); 
		for(int i = 0; i < size; i++)
			if (!get(i).equals(((TileListGeneric)other).get(i))) return false;
		
		return true;
	}
	
	
}

