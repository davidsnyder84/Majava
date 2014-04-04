import java.util.ArrayList;
import java.util.Iterator;

import utility.GenSort;
import utility.MahList;


/*
Class: MahList
a wrapper class for an ArrayList of Tiles. adds extra functionalities

data:
	mTiles - an arraylist that holds the elements of the MahList
	mSorter - used to sort the list
	
methods:
	
	constructors:
	Takes ArrayList
	Takes initial capacity
	Takes Array or Var args
	Takes another MahList, duplicates entries
	Default - creates empty list with default capacity
	
	
	mutators:
	addMultiple - adds multiple tiles to the end of a list. takes a list, an array, or var arguments
	addMultipleToBeginning -  adds multiple tiles to the beginning of a list. takes a list, an array, or var arguments
	
	removeFirst - removes and returns the first tile in the list, returns null if the list is empty
	removeLast - removes and returns the last tile in the list, returns null if the list is empty
	
	removeMultiple... - removes specified number of tiles from a specified position
	removeMultipleFromEnd - remove multiple tiles from end
	removeMultipleFromBeginning - remove multiple tiles from beginning
	removeMultipleFromPosition - remove multiple tiles from a given position
	
	sort - sort the list in ascending order
	sortAscending - sort ascending
	sortDescending - sort descending
	shuffle - shuffle the tiles of the list in a random order
 	
 	
 	accessors:
	getFirst - returns the fist tile in the list, returns null if the list is empty
	getLast - returns the last tile in the list, returns null if the list is empty
	subList - returns a sublist, as a MahList from fromIndex (inclusive) to toIndex (exclusive)
	
	findAllIndicesOf - searches the list for all occurences of Tile t, returns a MahList of integer indices of where that tile occurred
	makeCopy - 
	makeCopyNoDuplicates - 
	
	
	other:
	iterator - the ArrayList's iterator
	
	methods from ArrayList:
	add, remove, size, get, contains, isEmpty, indexOf, lastIndexOf, 
	set, clear, trimToSize, ensureCapacity, iterator
	
*/
public class TileList implements Iterable<Tile>{
	
	
	private static final int DEFAULT_CAPACITY = 10;
	public static final int NOT_FOUND = -1;
	
	
	private ArrayList<Tile> mTiles;
	private final GenSort<Tile> mSorter;
	
	
	//takes an arrayList
	public TileList(ArrayList<Tile> tiles){
		mTiles = tiles;
		mSorter = new GenSort<Tile>(mTiles);
	}	
	//takes a MahList
	public TileList(MahList<Tile> tiles){
		this(tiles.getArrayList());
	}
	//creates a new lsit with the given capacity
	public TileList(int capacity){
		this(new ArrayList<Tile>(capacity));
	}
	//can take an array, or a variable number or arguments of type T 
	public TileList(Tile... tiles){
		this(tiles.length);
		for (Tile t: tiles) mTiles.add(t);
	}
	public TileList(){
		this(DEFAULT_CAPACITY);
	}
	
	//takes a list of integer ids, makes a list of tiles out of them
	public TileList(int... ids){
		this(ids.length);
		for (int id: ids) mTiles.add(new Tile(id));
	}
	//copy constructor, duplicates another MahList
	public TileList(TileList other){		
		this(other.size());
		for (Tile t: other) mTiles.add(t);
	}
	
	
	/*
	//make a list with a COPY (independent) of each tile in the other list
	public TileList makeCopy(TileList other){
		TileList copy = new TileList(other.size());
		for (Tile t: other) copy.add(new Tile(t));
		return copy;
	}
	*/
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//contains, overloaded to accept tileID
	public boolean contains(int id){return contains(new Tile(id));}
	
	//add, overloaded to accept tileID
	public boolean add(int id){return mTiles.add(new Tile(id));}
	
	
	
	
	
	/*
	private method: subList
	returns a sublist, as a TileList type list from fromIndex (inclusive) to toIndex (exclusive)
	Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive. (If fromIndex and toIndex are equal, the returned list is empty)
	
	input: fromIndex, inclusive
		   toIndex, exclusive
	
	creates a new arraylist out of a sublist of mTiles
	returns a new MahList object with the sublist as its list 
	*/
	public TileList subList(int fromIndex, int toIndex){
		return new TileList(new ArrayList<Tile>(mTiles.subList(fromIndex, toIndex)));
	}
	//returns a sublist of the entire list, minus the last tile
	public TileList getAllExceptLast(){return subList(0, mTiles.size() - 1);}
	
	
	//returns the first tile in the list, returns null if the list is empty
	public Tile getFirst(){
		if (mTiles.isEmpty()) return null;
		return mTiles.get(0);
	}
	//returns the last tile in the list, returns null if the list is empty
	public Tile getLast(){
		if (mTiles.isEmpty()) return null;
		return mTiles.get(mTiles.size() - 1);
	}
	
	//returns multiple tiles in the list, at the given indices
	public TileList getMultiple(ArrayList<Integer> indices){
		TileList holder = new TileList();
		for (Integer index: indices)
			if (index < mTiles.size() && index >= 0)
				holder.add(mTiles.get(index));
		
		return holder;
	}
	public TileList getMultiple(MahList<Integer> indices){return getMultiple(indices.getArrayList());}
	public TileList getMultiple(Integer... indices){return getMultiple(new MahList<Integer>(indices));}
	
	
	
	
	
	
	
	
	//removes and returns the first tile in the list, returns null if the list is empty
	public Tile removeFirst(){
		if (mTiles.isEmpty()) return null;
		return mTiles.remove(0);
	}
	//removes and returns the last tile in the list, returns null if the list is empty
	public Tile removeLast(){
		if (mTiles.isEmpty()) return null;
		return mTiles.remove(mTiles.size() - 1);
	}
	
	
	//remove multiple tiles
	//returns true if exactly the desired number of tiles were removed
	//returns false if the list was emptied before removing the desired number of tiles
	public boolean removeMultipleFromEnd(int howMany){
		int i = 0;
		while(i < howMany && !mTiles.isEmpty()){
			mTiles.remove(mTiles.size() - 1);
			i++;
		}
		return (i == howMany);
	}
	public boolean removeMultipleFromBeginning(int howMany){
		int i = 0;
		while(i < howMany && !mTiles.isEmpty()){
			mTiles.remove(0);
			i++;
		}
		return (i == howMany);
	}
	public boolean removeMultipleFromPosition(int howMany, int position){
		int i = 0;
		while(i < howMany && (position + i) < mTiles.size()){
			mTiles.remove(position);
			i++;
		}
		return (i == howMany);
	}
	
	
	//add multiple tiles to the end of a list
	//takes a list, an array, or var arguments
	public void addMultiple(TileList tiles){
		for (Tile t: tiles) mTiles.add(t);
	}
	public void addMultiple(Tile... tiles){addMultiple(new TileList(tiles));}
	public void addMultiple(ArrayList<Tile> tiles){addMultiple(new TileList(tiles));}
	
	
	
	//add multiple tiles to the beginning of a list
	//takes a list, an array, or var arguments
	public void addMultipleToBeginning(TileList tiles){
		for (Tile t: tiles) mTiles.add(0, t);
	}
	public void addMultipleToBeginning(Tile... tiles){addMultiple(new TileList(tiles));}
	public void addMultipleToBeginning(ArrayList<Tile> tiles){addMultiple(new TileList(tiles));}
	
	
	
	
	//finds all indices where a tile occurs in the list
	//returns the indices in a MahList<Integer>
	public MahList<Integer> findAllIndicesOf(Tile t, boolean allowCountingItself){
		MahList<Integer> indices = new MahList<Integer>(2);
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
	public MahList<Integer> findAllIndicesOf(Tile tile){return findAllIndicesOf(tile, false);}
	
	
	//allow counting itself
	public int findHowManyOf(Tile tile){return findAllIndicesOf(tile, true).size();}
	
	
	
	
	
	//sorts
	public void sort(){sortAscending();}
	public void sortAscending(){mSorter.sort();}
	public void sortDescending(){mSorter.sortDescending();}
	public void shuffle(){mSorter.shuffle();}
	
	
	
	
	
	
	public int indexOf(Tile t){return mTiles.indexOf(t);}
	//indexOf, overloaded for Tile ID
	public int indexOf(int tileID){return mTiles.indexOf(new Tile(tileID));}
	
	
	
	
	
	
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
	
	
	public void clear(){mTiles.clear();}
	public void trimToSize(){mTiles.trimToSize();}
	public void ensureCapacity(int minCapacity){mTiles.ensureCapacity(minCapacity);}
	
	
	//returns the arrayList's iterator
	@Override
	public Iterator<Tile> iterator(){return mTiles.iterator();}
	//***************************************************************************************************
	//****END ARRAYLIST FUNCS
	//***************************************************************************************************
	
	//returns a reference to the MahList's arrayList (parentheses this is a good idea)
	public ArrayList<Tile> getArrayList(){return mTiles;}
	
	
	
	
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

