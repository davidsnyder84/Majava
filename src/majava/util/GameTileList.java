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
//		this(tiles.size());
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
//		for (int i = 0; i < size(); i++) copy.add(new HandCheckerTile(get(i)));
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public static ConviniList<HandCheckerTile> makeCopyOfListWithCheckers(final List<? extends TileInterface> orig){
//		
//		ConviniList<HandCheckerTile> copy = new ConviniList<HandCheckerTile>();
//		
//		for (TileInterface t: orig){
//			if (t instanceof GameTile) copy.add(new HandCheckerTile((GameTile)t));
//			else copy.add(new HandCheckerTile(t));
//		}
//		return copy;
//	}
	
	
	
	
	
	
}
