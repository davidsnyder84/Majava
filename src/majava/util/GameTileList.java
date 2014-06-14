package majava.util;

import java.util.Arrays;
import java.util.List;

import majava.tiles.GameTile;
import majava.tiles.HandCheckerTile;
import majava.tiles.TileInterface;
import utility.ConviniList;


public class GameTileList extends ConviniList<GameTile>{
	private static final long serialVersionUID = 5627551401426889451L;
	
	private static final int DEFAULT_SIZE = 15;
	
	
	
	//creates a new list with the given capacity
	public GameTileList(int capacity){super(capacity);}
	
	//takes a List
	public GameTileList(List<GameTile> tiles){
		this(DEFAULT_SIZE);
		addAll(tiles);
	}
	//can take an array, or a var args
	public GameTileList(GameTile... tiles){this(Arrays.asList(tiles));}
	public GameTileList(){this(DEFAULT_SIZE);}
	
	
	
	public GameTileList(Integer... ids){
		this(DEFAULT_SIZE);
		for (Integer i: ids) add(new GameTile(i));
	}
//	public GameTileList(int... ids){this(DEFAULT_SIZE); for (Integer i: ids) add(new GameTile(i));}
	
	
	@Override
	public GameTileList clone(){
		return new GameTileList(this);
//		TileInterfaceList copy = new TileInterfaceList(size());
//		for (TileInterface t: this) copy.add(t.clone());
//		return copy;
	}
	
	
	
	
//	public boolean contains(TileInterface other){
//		for (GameTile t: this) if (t.equals(other)) return true;
//		return true;
//	}
	
	
	
	public GameTileList makeCopyWithCheckers(){
		GameTileList copy = new GameTileList(DEFAULT_SIZE);
		for (GameTile t: this) copy.add(new HandCheckerTile(t));
		return copy;
	}
	
	
	
	@Override public GameTileList getMultiple(Integer... indices){return new GameTileList(super.getMultiple(indices));}
	@Override public GameTileList getMultiple(List<Integer> indices){return new GameTileList(super.getMultiple(indices));}
	
	
	
	@Override
	public GameTileList subList(int fromIndex, int toIndex){
		return new GameTileList(super.subList(fromIndex, toIndex));
	}
	
	
	
	
	public int indexOf(Integer id){return indexOf(new GameTile(id));}
//	public int indexOf(Integer id){
//		for (GameTile t: this) if (t.getId() == id) return true;
//		return false;
//	}
	
	//contains, overloaded for tile id
	public boolean contains(Integer id){return contains(new GameTile(id));}
//	public boolean contains(Integer id){
//		for (GameTile t: this) if (t.getId() == id) return true;
//		return false;
//	}
//	public boolean containsAll(Integer... ids){
//		
//		return false;
//	}
	
	
	//add, overloaded for tile id
	public boolean add(int id){return add(new GameTile(id));}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	///*
	//Class: TileList
	//a wrapper class for an List of Tiles. adds extra functionalities
	//
	//data:
//		mTiles - a list that holds the list of tiles
//		mSorter - used to sort the list
	//	
	//methods:
	//	
//		constructors:
//		Takes List / Takes initial capacity / Takes Array or Var args
	//	
//		public:
//			mutators:
//			removeFirst, removeLast - removes and returns a tile in the list, returns null if the list is empty
//			removeMultiple - removes multiple indices from the list
//			sort, sortAscending, sortDescending, shuffle - sort the list in specified order
//		 	
//		 	accessors:
//			getFirst, getLast - returns a tile in the list, returns null if the list is empty
//			subList - returns a sublist, as a TileList from fromIndex (inclusive) to toIndex (exclusive)
//			
//			findAllIndicesOf - searches the list for all occurences of Tile t, returns a List of integer indices of where that tile occurred
//			makeCopy - returns a copy of the list
//			makeCopyNoDuplicates - returns a copy of the list with no duplicate tiles
//			makeCopyWithCheckers - makes a copy of the list with checkers
//			
//			
//			methods from Lsist:
//			add, remove, size, get, contains, isEmpty, indexOf, lastIndexOf, set, clear, trimToSize, ensureCapacity, iterator
	//*/
	//public class TileList extends ConviniList<TileInterface>{
//		private static final long serialVersionUID = -6296356765155653731L;
	//	
	//	
	//	
//		public TileList(int capacity){super(capacity);}
//		public TileList(List<TileInterface> tiles){super(tiles);}
//		public TileList(TileInterface... tiles){super(Arrays.asList(tiles));}
//		public TileList(){super();}
	//	
	//	
	//	
	//	
	//	
//		//(No it doesn't) return a new TileList with a COPY (independent) of each tile in the list
//		public TileList clone(){
//			return new TileList(super.clone());
//		}
	//	
	//	
//		public TileList makeCopyWithCheckers(){
//			TileList copy = new TileList(size());
////			for (Tile t: this) copy.add(new HandCheckerTile(t));
//			for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile((GameTile)get(i)));
////			for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile(get(i)));
//			return copy;
//		}
	//	
	//	
	//	
	//	
	//	
	//	
	//	
	//	
//		/*
//		 * --------------------------------------------------------TILELIST FUNCTIONS
//		*/
	//	
//		//overloaded for a list of integer ids, makes a list of tiles out of them
//		public TileList(int... ids){
//			this(ids.length);
//			for (int id: ids) add(new GameTile(id));
//		}
	//	
//		//add, overloaded for tileID
//		public boolean add(int id){return add(new GameTile(id));}
	//	
//		//indexOf, overloaded for Tile ID
//		public int indexOf(int tileID){return indexOf(new GameTile(tileID));}
//		public int indexOf(Integer tileID){return indexOf(tileID.intValue());}
	//	
//		//contains, overloaded for tileID
//		public boolean contains(int id){return contains(new GameTile(id));}
//		public boolean contains(Integer id){return contains(id.intValue());}
	//	
	//	
//		//overloaded for tileID
//		public int findHowManyOf(int id){return findHowManyOf(new GameTile(id));}
	//
//		/*
//		 * --------------------------------------------------------TILELIST FUNCTIONS
//		*/
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
//		public TileList subList(int fromIndex, int toIndex){return new TileList(super.subList(fromIndex, toIndex));}
//		public TileList getAllExceptLast(){return subList(0, size() - 1);}
	//	
	//	
	//	
	//	
	//	
	//	
//		//returns multiple items in the list, at the given indices
//		public TileList getMultiple(List<Integer> indices){return new TileList(super.getMultiple(indices));}
//		public TileList getMultiple(Integer... indices){return getMultiple(Arrays.asList(indices));}
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
//		@Override
//		public String toString(){
//			String tilesString = "";
//			//add the tiles to the string
//			for (TileInterface t: this) tilesString += t.toString() + " ";
//			if (tilesString != "") tilesString = tilesString.substring(0, tilesString.length() - 1);
//			
//			return tilesString;
//		}
	//	
	//	
//		@Override
//		public boolean equals(Object other){
//			if (other == null || (other instanceof TileList) == false) return false;
//			if (((TileList)other).size() != this.size()) return false;
//			
//			int size = this.size(); 
//			for(int i = 0; i < size; i++)
//				if (!get(i).equals(((TileList)other).get(i))) return false;
//			
//			return true;
//		}
	//	
	//	
	//}
	//

}
