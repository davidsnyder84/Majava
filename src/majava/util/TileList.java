package majava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import majava.tiles.HandCheckerTile;
import majava.tiles.GameTile;


/*
Class: TileList
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
		subList - returns a sublist, as a TileList from fromIndex (inclusive) to toIndex (exclusive)
		
		findAllIndicesOf - searches the list for all occurences of Tile t, returns a List of integer indices of where that tile occurred
		makeCopy - returns a copy of the list
		makeCopyNoDuplicates - returns a copy of the list with no duplicate tiles
		makeCopyWithCheckers - makes a copy of the list with checkers
		
		
		methods from Lsist:
		add, remove, size, get, contains, isEmpty, indexOf, lastIndexOf, set, clear, trimToSize, ensureCapacity, iterator
*/
public class TileList extends ArrayList<GameTile>{
	private static final long serialVersionUID = -6296356765155653731L;
	

	//creates a new list with the given capacity
	public TileList(int capacity){super(capacity);}
	//takes a List
	public TileList(List<GameTile> tiles){
		this(tiles.size());
		for (GameTile t: tiles) add(t);
	}
	//can take an array, or a var args
	public TileList(GameTile... tiles){this(Arrays.asList(tiles));}
	
	//overloaded for a list of integer ids, makes a list of tiles out of them
	public TileList(int... ids){
		this(ids.length);
		for (int id: ids) add(new GameTile(id));
	}
	public TileList(){this(10);}
	
	
	
	//return a new TileList with a COPY (independent) of each tile in the list
	public TileList makeCopy(){
		return new TileList(this);
	}
	//return a new TileList with a COPY (independent) of each tile in the list
	public TileList makeCopyNoDuplicates(){
		TileList copy = new TileList(size());
		
		for (GameTile t: this)
			if (!copy.contains(t))
				copy.add(new GameTile(t));
		return copy;
	}
	
	
	public TileList makeCopyWithCheckers(){
		TileList copy = new TileList(size());
//		for (Tile t: this) copy.add(new HandCheckerTile(t));
		for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile(get(i)));
		return copy;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns a tile in the list, returns null if the list is empty
	public GameTile getFirst(){if (isEmpty()) return null; return get(0);}
	public GameTile getLast(){if (isEmpty()) return null; return get(size() - 1);}
	
	//removes and returns a tile in the list, returns null if the list is empty
	public GameTile removeFirst(){if (isEmpty()) return null; return remove(0);}
	public GameTile removeLast(){if (isEmpty()) return null; return remove(size() - 1);}
	

	
	//returns a sublist, as a TileList from fromIndex (inclusive) to toIndex (exclusive)
	public TileList subList(int fromIndex, int toIndex){return new TileList(super.subList(fromIndex, toIndex));}
	
	//returns a sublist of the entire list, minus the last tile
	public TileList getAllExceptLast(){return subList(0, size() - 1);}
	
	
	
	
	//add, overloaded to accept tileID
	public boolean add(int id){return add(new GameTile(id));}
	
	//indexOf, overloaded for Tile ID
	public int indexOf(int tileID){return indexOf(new GameTile(tileID));}
	public int indexOf(Integer tileID){return indexOf(tileID.intValue());}
	
	//contains, overloaded to accept tileID
	public boolean contains(int id){return contains(new GameTile(id));}
	public boolean contains(Integer id){return contains(id.intValue());}
	
	
	
	
	
	
	//returns multiple tiles in the list, at the given indices
	public TileList getMultiple(List<Integer> indices){
		TileList holder = new TileList();
		for (Integer index: indices)
			if (index < size() && index >= 0)
				holder.add(get(index));
		
		return holder;
	}
	public TileList getMultiple(Integer... indices){return getMultiple(Arrays.asList(indices));}
	
	
	
	//remove multiple indices from the list
	//returns true if the indices were removed, false if not
	public boolean removeMultiple(List<Integer> removeIndices){
		
		//disallow more indices than the list has
		if (removeIndices.size() > size()) return false;
		
		List<Integer> seenSoFar = new ArrayList<Integer>();
		for (Integer i: removeIndices){
			//disallow an index outside of the list's size, disallow duplicate indices
			if (i >= size() || i < 0 || seenSoFar.contains(i)) return false;
			else seenSoFar.add(i);
		}
		
		//sort the indices in descending order
		Collections.sort(seenSoFar, Collections.reverseOrder());
		
		//remove the indices
		for (Integer i: seenSoFar) remove((int)i);
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	//finds all indices where a tile occurs in the list, returns the indices as a list of integers
	public List<Integer> findAllIndicesOf(GameTile t, boolean allowCountingItself){
		List<Integer> indices = new ArrayList<Integer>(2);
		for (int i = 0; i < size(); i++)
			if (get(i).equals(t)){
				if (get(i) != t)
					indices.add(i);
				else if (allowCountingItself)
					indices.add(i);
			}
		return indices;
	}
	//overloaded, omitting allowCountingItself will default to false (do not count itself)
	public List<Integer> findAllIndicesOf(GameTile tile){return findAllIndicesOf(tile, false);}
	
	
	//allow counting itself
	public int findHowManyOf(GameTile tile){return findAllIndicesOf(tile, true).size();}
	public int findHowManyOf(int id){return findHowManyOf(new GameTile(id));}
	
	
	
	
	
	//sort
	public void sort(){Collections.sort(this);}
	
	
	
	
	
	
	
	
	@Override
	public String toString(){
		String tilesString = "";
		//add the tiles to the string
		for (GameTile t: this) tilesString += t.toString() + " ";
		if (tilesString != "") tilesString = tilesString.substring(0, tilesString.length() - 1);
		
		return tilesString;
	}
	
	
	@Override
	public boolean equals(Object other){
		if (other == null || (other instanceof TileList) == false) return false;
		if (((TileList)other).size() != this.size()) return false;
		
		int size = this.size(); 
		for(int i = 0; i < size; i++)
			if (!get(i).equals(((TileList)other).get(i))) return false;
		
		return true;
	}
	
	
}

