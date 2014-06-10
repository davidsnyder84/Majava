package majava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.tiles.HandCheckerTile;
import majava.tiles.Tile;

import utility.GenSort;


/*
Class: TileListX
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
		subList - returns a sublist, as a TileListX from fromIndex (inclusive) to toIndex (exclusive)
		
		findAllIndicesOf - searches the list for all occurences of Tile t, returns a List of integer indices of where that tile occurred
		makeCopy - returns a copy of the list
		makeCopyNoDuplicates - returns a copy of the list with no duplicate tiles
		makeCopyWithCheckers - makes a copy of the list with checkers
		
		
		methods from List:
		add, remove, size, get, contains, isEmpty, indexOf, lastIndexOf, set, clear, trimToSize, ensureCapacity, iterator
*/
public class TileListX extends ArrayList<Tile>{
	private static final long serialVersionUID = -6296356765155653731L;
	
	
	private final GenSort<Tile> mSorter;
	

	//creates a new list with the given capacity
	public TileListX(int capacity){
		super(capacity);
		mSorter = new GenSort<Tile>(this);
	}
	//takes a List
	public TileListX(List<Tile> tiles){
		this(tiles.size());
		for (Tile t: tiles) add(t);
	}
	//can take an array, or a var args
	public TileListX(Tile... tiles){this(Arrays.asList(tiles));}
	
	//overloaded for a list of integer ids, makes a list of tiles out of them
	public TileListX(int... ids){
		this(ids.length);
		for (int id: ids) add(new Tile(id));
	}
	public TileListX(){this(10);}
	
	
	
	//return a new TileListX with a COPY (independent) of each tile in the list
	public TileListX makeCopy(){
		return new TileListX(this);
	}
	//return a new TileListX with a COPY (independent) of each tile in the list
	public TileListX makeCopyNoDuplicates(){
		TileListX copy = new TileListX(size());
		
		for (Tile t: this)
			if (!copy.contains(t))
				copy.add(new Tile(t));
		return copy;
	}
	
	
	public TileListX makeCopyWithCheckers(){
		TileListX copy = new TileListX(size());
//		for (Tile t: this) copy.add(new HandCheckerTile(t));
		for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile(get(i)));
		return copy;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//contains, overloaded to accept tileID
	public boolean contains(int id){return contains(new Tile(id));}
	
	//add, overloaded to accept tileID
	public boolean add(int id){return add(new Tile(id));}
	
	
	
	
	
	//returns a sublist, as a TileListX from fromIndex (inclusive) to toIndex (exclusive)
	public TileListX subList(int fromIndex, int toIndex){return new TileListX(subList(fromIndex, toIndex));}
	
	//returns a sublist of the entire list, minus the last tile
	public TileListX getAllExceptLast(){return subList(0, size() - 1);}
	
	
	
	
	//returns a tile in the list, returns null if the list is empty
	public Tile getFirst(){if (isEmpty()) return null; return get(0);}
	public Tile getLast(){if (isEmpty()) return null; return get(size() - 1);}
	
	//removes and returns a tile in the list, returns null if the list is empty
	public Tile removeFirst(){if (isEmpty()) return null; return remove(0);}
	public Tile removeLast(){if (isEmpty()) return null; return remove(size() - 1);}
	
	//indexOf, overloaded for Tile ID
	public int indexOf(int tileID){return indexOf(new Tile(tileID));}
	
	
	
	
	
	
	//returns multiple tiles in the list, at the given indices
	public TileListX getMultiple(List<Integer> indices){
		TileListX holder = new TileListX();
		for (Integer index: indices)
			if (index < size() && index >= 0)
				holder.add(get(index));
		
		return holder;
	}
	public TileListX getMultiple(Integer... indices){return getMultiple(Arrays.asList(indices));}
	
	
	
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
	public void sort(){sortAscending();}
	public void sortAscending(){mSorter.sort();}
	public void sortDescending(){mSorter.sortDescending();}
	public void shuffle(){mSorter.shuffle();}
	
	
	
	
	
	
	
	
	
	//***************************************************************************************************
	//****BEGIN LIST FUNCS
	//***************************************************************************************************
	//add
//	public boolean add(Tile t){return mTiles.add(t);}
//	public void add(int index, Tile t){mTiles.add(index, t);}
//	
//	//remove
//	public boolean remove(Tile t){return mTiles.remove(t);}
//	public Tile remove(int index){return mTiles.remove(index);}
//	
//	//size
//	public int size(){return mTiles.size();}
	
	//get
//	public Tile get(int index){return mTiles.get(index);}
//	public boolean contains(Tile t){return mTiles.contains(t);}
//	public boolean isEmpty(){return mTiles.isEmpty();}
//	public int lastIndexOf(Tile t){return mTiles.lastIndexOf(t);}
//	public Tile set(int index, Tile t){return mTiles.set(index, t);}
	
	
//	public void clear(){mTiles.clear();}
//	public void trimToSize(){mTiles.trimToSize();}
//	public void ensureCapacity(int minCapacity){mTiles.ensureCapacity(minCapacity);}
	
//	public Iterator<Tile> iterator(){return mTiles.iterator();}	//returns the List's iterator
	//***************************************************************************************************
	//****END LIST FUNCS
	//***************************************************************************************************
	
	
	
	
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
		if (other == null || (other instanceof TileListX) == false) return false;
		if (((TileListX)other).size() != this.size()) return false;
		
		int size = this.size(); 
		for(int i = 0; i < size; i++)
			if (!get(i).equals(((TileListX)other).get(i))) return false;
		
		return true;
	}
	
	
}

