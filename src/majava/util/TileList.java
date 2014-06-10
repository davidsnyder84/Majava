package majava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.tiles.HandCheckerTile;
import majava.tiles.Tile;

import utility.GenSort;


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
public class TileList extends ArrayList<Tile>{
	private static final long serialVersionUID = -6296356765155653731L;
	
	
	private final GenSort<Tile> mSorter;
	

	//creates a new list with the given capacity
	public TileList(int capacity){
		super(capacity);
		mSorter = new GenSort<Tile>(this);
	}
	//takes a List
	public TileList(List<Tile> tiles){
		this(tiles.size());
		for (Tile t: tiles) add(t);
	}
	//can take an array, or a var args
	public TileList(Tile... tiles){this(Arrays.asList(tiles));}
	
	//overloaded for a list of integer ids, makes a list of tiles out of them
	public TileList(int... ids){
		this(ids.length);
		for (int id: ids) add(new Tile(id));
	}
	public TileList(){this(10);}
	
	
	
	//return a new TileList with a COPY (independent) of each tile in the list
	public TileList makeCopy(){
		return new TileList(this);
	}
	//return a new TileList with a COPY (independent) of each tile in the list
	public TileList makeCopyNoDuplicates(){
		TileList copy = new TileList(size());
		
		for (Tile t: this)
			if (!copy.contains(t))
				copy.add(new Tile(t));
		return copy;
	}
	
	
	public TileList makeCopyWithCheckers(){
		TileList copy = new TileList(size());
//		for (Tile t: this) copy.add(new HandCheckerTile(t));
		for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile(get(i)));
		return copy;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns a tile in the list, returns null if the list is empty
	public Tile getFirst(){if (isEmpty()) return null; return get(0);}
	public Tile getLast(){if (isEmpty()) return null; return get(size() - 1);}
	
	//removes and returns a tile in the list, returns null if the list is empty
	public Tile removeFirst(){if (isEmpty()) return null; return remove(0);}
	public Tile removeLast(){if (isEmpty()) return null; return remove(size() - 1);}
	

	
	//returns a sublist, as a TileList from fromIndex (inclusive) to toIndex (exclusive)
	public TileList subList(int fromIndex, int toIndex){return new TileList(super.subList(fromIndex, toIndex));}
	
	//returns a sublist of the entire list, minus the last tile
	public TileList getAllExceptLast(){return subList(0, size() - 1);}
	
	
	
	
	//add, overloaded to accept tileID
	public boolean add(int id){return add(new Tile(id));}
	
	//indexOf, overloaded for Tile ID
	public int indexOf(int tileID){return indexOf(new Tile(tileID));}
	public int indexOf(Integer tileID){return indexOf(tileID.intValue());}
	
	//contains, overloaded to accept tileID
	public boolean contains(int id){return contains(new Tile(id));}
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
		GenSort<Integer> sorter = new GenSort<Integer>(seenSoFar); sorter.sortDescending();
		
		//remove the indices
		for (Integer i: seenSoFar) remove((int)i);
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	//finds all indices where a tile occurs in the list, returns the indices as a list of integers
	public List<Integer> findAllIndicesOf(Tile t, boolean allowCountingItself){
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
	public List<Integer> findAllIndicesOf(Tile tile){return findAllIndicesOf(tile, false);}
	
	
	//allow counting itself
	public int findHowManyOf(Tile tile){return findAllIndicesOf(tile, true).size();}
	public int findHowManyOf(int id){return findHowManyOf(new Tile(id));}
	
	
	
	
	
	//sorts
	public void sort(){mSorter.sort();}
//	public void sortAscending(){mSorter.sort();}
//	public void sortDescending(){mSorter.sortDescending();}
//	public void shuffle(){mSorter.shuffle();}
	
	
	
	
	
	
	
	
	@Override
	public String toString(){
		String tilesString = "";
		//add the tiles to the string
		for (Tile t: this) tilesString += t.toString() + " ";
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

