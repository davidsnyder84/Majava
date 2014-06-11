//package majava.util;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import utility.ConviniList;
//
//import majava.tiles.HandCheckerTile;
//import majava.tiles.GameTile;
//import majava.tiles.TileInterface;
//
//
///*
//Class: TileList
//a wrapper class for an List of Tiles. adds extra functionalities
//
//data:
//	mTiles - a list that holds the list of tiles
//	mSorter - used to sort the list
//	
//methods:
//	
//	constructors:
//	Takes List / Takes initial capacity / Takes Array or Var args
//	
//	public:
//		mutators:
//		removeFirst, removeLast - removes and returns a tile in the list, returns null if the list is empty
//		removeMultiple - removes multiple indices from the list
//		sort, sortAscending, sortDescending, shuffle - sort the list in specified order
//	 	
//	 	accessors:
//		getFirst, getLast - returns a tile in the list, returns null if the list is empty
//		subList - returns a sublist, as a TileList from fromIndex (inclusive) to toIndex (exclusive)
//		
//		findAllIndicesOf - searches the list for all occurences of Tile t, returns a List of integer indices of where that tile occurred
//		makeCopy - returns a copy of the list
//		makeCopyNoDuplicates - returns a copy of the list with no duplicate tiles
//		makeCopyWithCheckers - makes a copy of the list with checkers
//		
//		
//		methods from Lsist:
//		add, remove, size, get, contains, isEmpty, indexOf, lastIndexOf, set, clear, trimToSize, ensureCapacity, iterator
//*/
//public class TileList extends ConviniList<TileInterface>{
//	private static final long serialVersionUID = -6296356765155653731L;
//	
//	
//	
//	public TileList(int capacity){super(capacity);}
//	public TileList(List<TileInterface> tiles){super(tiles);}
//	public TileList(TileInterface... tiles){super(Arrays.asList(tiles));}
//	public TileList(){super();}
//	
//	
//	
//	
//	
//	//(No it doesn't) return a new TileList with a COPY (independent) of each tile in the list
//	public TileList clone(){
//		return new TileList(super.clone());
//	}
//	
//	
//	public TileList makeCopyWithCheckers(){
//		TileList copy = new TileList(size());
////		for (Tile t: this) copy.add(new HandCheckerTile(t));
//		for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile((GameTile)get(i)));
////		for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile(get(i)));
//		return copy;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	/*
//	 * --------------------------------------------------------TILELIST FUNCTIONS
//	*/
//	
//	//overloaded for a list of integer ids, makes a list of tiles out of them
//	public TileList(int... ids){
//		this(ids.length);
//		for (int id: ids) add(new GameTile(id));
//	}
//	
//	//add, overloaded for tileID
//	public boolean add(int id){return add(new GameTile(id));}
//	
//	//indexOf, overloaded for Tile ID
//	public int indexOf(int tileID){return indexOf(new GameTile(tileID));}
//	public int indexOf(Integer tileID){return indexOf(tileID.intValue());}
//	
//	//contains, overloaded for tileID
//	public boolean contains(int id){return contains(new GameTile(id));}
//	public boolean contains(Integer id){return contains(id.intValue());}
//	
//	
//	//overloaded for tileID
//	public int findHowManyOf(int id){return findHowManyOf(new GameTile(id));}
//
//	/*
//	 * --------------------------------------------------------TILELIST FUNCTIONS
//	*/
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	public TileList subList(int fromIndex, int toIndex){return new TileList(super.subList(fromIndex, toIndex));}
//	public TileList getAllExceptLast(){return subList(0, size() - 1);}
//	
//	
//	
//	
//	
//	
//	//returns multiple items in the list, at the given indices
//	public TileList getMultiple(List<Integer> indices){return new TileList(super.getMultiple(indices));}
//	public TileList getMultiple(Integer... indices){return getMultiple(Arrays.asList(indices));}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	@Override
//	public String toString(){
//		String tilesString = "";
//		//add the tiles to the string
//		for (TileInterface t: this) tilesString += t.toString() + " ";
//		if (tilesString != "") tilesString = tilesString.substring(0, tilesString.length() - 1);
//		
//		return tilesString;
//	}
//	
//	
//	@Override
//	public boolean equals(Object other){
//		if (other == null || (other instanceof TileList) == false) return false;
//		if (((TileList)other).size() != this.size()) return false;
//		
//		int size = this.size(); 
//		for(int i = 0; i < size; i++)
//			if (!get(i).equals(((TileList)other).get(i))) return false;
//		
//		return true;
//	}
//	
//	
//}
//
