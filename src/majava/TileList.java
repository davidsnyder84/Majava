package majava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import majava.tiles.HandCheckerTile;
import majava.tiles.Tile;

import utility.GenSort;


/*
Class: TileList
a wrapper class for an ArrayList of Tiles. adds extra functionalities

data:
	mTiles - an arraylist that holds the list of tiles
	mSorter - used to sort the list
	
methods:
	
	constructors:
	ArrayList / Takes initial capacity / Takes Array or Var args
	
	public:
		mutators:
		removeFirst, removeLast - removes and returns a tile in the list, returns null if the list is empty
		sort, sortAscending, sortDescending, shuffle - sort the list in specified order
	 	
	 	accessors:
		getFirst, getLast - returns a tile in the list, returns null if the list is empty
		subList - returns a sublist, as a TileList from fromIndex (inclusive) to toIndex (exclusive)
		
		findAllIndicesOf - searches the list for all occurences of Tile t, returns an ArrayList of integer indices of where that tile occurred
		makeCopy - returns a copy of the list
		makeCopyNoDuplicates - returns a copy of the list with no duplicate tiles
		makeCopyWithCheckers - makes a copy of the list with checkers
		
		
		methods from ArrayList:
		add, remove, size, get, contains, isEmpty, indexOf, lastIndexOf, set, clear, trimToSize, ensureCapacity, iterator
*/
public class TileList implements Iterable<Tile>{
	
	
	private static final int DEFAULT_CAPACITY = 10;
	
	
	private final ArrayList<Tile> mTiles;
	private final GenSort<Tile> mSorter;
	
	
	//takes an arrayList
	public TileList(ArrayList<Tile> tiles){
		mTiles = tiles;
		mSorter = new GenSort<Tile>(mTiles);
	}
	//creates a new list with the given capacity
	public TileList(int capacity){this(new ArrayList<Tile>(capacity));}
	public TileList(){this(DEFAULT_CAPACITY);}
	
	//can take an array, or a variable number or arguments of type T 
	public TileList(Tile... tiles){
		this(tiles.length);
		for (Tile t: tiles) mTiles.add(t);
	}
	
	//takes a list of integer ids, makes a list of tiles out of them
	public TileList(int... ids){
		this(ids.length);
		for (int id: ids) mTiles.add(new Tile(id));
	}
	
	
	
	//return a new TileList with a COPY (independent) of each tile in the list
	public TileList makeCopy(){
		TileList copy = new TileList(mTiles.size());
		for (Tile t: mTiles) copy.add(new Tile(t));
		return copy;
	}
	//return a new TileList with a COPY (independent) of each tile in the list
	public TileList makeCopyNoDuplicates(){
		TileList copy = new TileList(mTiles.size());
		
		for (Tile t: mTiles)
			if (!copy.contains(t))
				copy.add(new Tile(t));
		return copy;
	}
	
	
	public TileList makeCopyWithCheckers(){
		TileList copy = new TileList(mTiles.size());
//		for (Tile t: mTiles) copy.add(new HandCheckerTile(t));
		for (int i = 0; i < mTiles.size(); i++) copy.add(new HandCheckerTile(mTiles.get(i)));
		return copy;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//contains, overloaded to accept tileID
	public boolean contains(int id){return contains(new Tile(id));}
	
	//add, overloaded to accept tileID
	public boolean add(int id){return mTiles.add(new Tile(id));}
	
	
	
	
	
	//returns a sublist, as a TileList from fromIndex (inclusive) to toIndex (exclusive)
	public TileList subList(int fromIndex, int toIndex){return new TileList(new ArrayList<Tile>(mTiles.subList(fromIndex, toIndex)));}
	
	//returns a sublist of the entire list, minus the last tile
	public TileList getAllExceptLast(){return subList(0, mTiles.size() - 1);}
	
	
	
	
	//returns a tile in the list, returns null if the list is empty
	public Tile getFirst(){if (mTiles.isEmpty()) return null; return mTiles.get(0);}
	public Tile getLast(){if (mTiles.isEmpty()) return null; return mTiles.get(mTiles.size() - 1);}
	
	//removes and returns a tile in the list, returns null if the list is empty
	public Tile removeFirst(){if (mTiles.isEmpty()) return null; return mTiles.remove(0);}
	public Tile removeLast(){if (mTiles.isEmpty()) return null; return mTiles.remove(mTiles.size() - 1);}
	
	//indexOf
	public int indexOf(Tile t){return mTiles.indexOf(t);}
	public int indexOf(int tileID){return mTiles.indexOf(new Tile(tileID));}	//overloaded for Tile ID
	
	
	
	
	
	
	//returns multiple tiles in the list, at the given indices
	public TileList getMultiple(List<Integer> indices){
		TileList holder = new TileList();
		for (Integer index: indices)
			if (index < mTiles.size() && index >= 0)
				holder.add(mTiles.get(index));
		
		return holder;
	}
	public TileList getMultiple(Integer... indices){return getMultiple(Arrays.asList(indices));}
	
	

	
	
	//finds all indices where a tile occurs in the list, returns the indices as an arraylist of integers
	public ArrayList<Integer> findAllIndicesOf(Tile t, boolean allowCountingItself){
		ArrayList<Integer> indices = new ArrayList<Integer>(2);
		for (int i = 0; i < mTiles.size(); i++)
			if (mTiles.get(i).equals(t)){
				if (mTiles.get(i) != t)
					indices.add(i);
				else if (allowCountingItself)
					indices.add(i);
			}
		return indices;
	}
	//overloaded, omitting allowCountingItself will default to false (do not count itself)
	public ArrayList<Integer> findAllIndicesOf(Tile tile){return findAllIndicesOf(tile, false);}
	
	
	//allow counting itself
	public int findHowManyOf(Tile tile){return findAllIndicesOf(tile, true).size();}
	public int findHowManyOf(int id){return findHowManyOf(new Tile(id));}
	
	
	
	
	
	//sorts
	public void sort(){sortAscending();}
	public void sortAscending(){mSorter.sort();}
	public void sortDescending(){mSorter.sortDescending();}
	public void shuffle(){mSorter.shuffle();}
	
	
	
	
	
	
	
	
	
	//***************************************************************************************************
	//****BEGIN ARRAYLIST FUNCS
	//***************************************************************************************************
	//add
	public boolean add(Tile t){return mTiles.add(t);}
	public void add(int index, Tile t){mTiles.add(index, t);}
	
	//remove
	public boolean remove(Tile t){return mTiles.remove(t);}
	public Tile remove(int index){return mTiles.remove(index);}
	
	//size
	public int size(){return mTiles.size();}
	
	//get
	public Tile get(int index){return mTiles.get(index);}
	public boolean contains(Tile t){return mTiles.contains(t);}
	public boolean isEmpty(){return mTiles.isEmpty();}
	public int lastIndexOf(Tile t){return mTiles.lastIndexOf(t);}
	public Tile set(int index, Tile t){return mTiles.set(index, t);}
	
	
//	public void clear(){mTiles.clear();}
//	public void trimToSize(){mTiles.trimToSize();}
//	public void ensureCapacity(int minCapacity){mTiles.ensureCapacity(minCapacity);}
	
	@Override
	public Iterator<Tile> iterator(){return mTiles.iterator();}	//returns the arrayList's iterator
	//***************************************************************************************************
	//****END ARRAYLIST FUNCS
	//***************************************************************************************************
	
	
	
	
	@Override
	public String toString(){
		String tilesString = "";
		//add the tiles to the string
		for (Tile t: mTiles) tilesString += t.toString() + " ";
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

