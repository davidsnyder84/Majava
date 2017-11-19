package majava.util;

import java.util.Arrays;
import java.util.List;

import majava.tiles.GameTile;
import utility.ConviniList;

//a list of tiles with extra methods for convenience
public class GameTileList extends ConviniList<GameTile>{
	private static final long serialVersionUID = 5627551401426889451L;
	
	private static final int DEFAULT_SIZE = 15;
	
	
	
	//takes a List
	public GameTileList(int capacity){super(capacity);}
	public GameTileList(List<? extends GameTile> tiles){
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
	}
	
	
	@Override public GameTileList getMultiple(Integer... indices){return new GameTileList(super.getMultiple(indices));}
	@Override public GameTileList getMultiple(List<Integer> indices){return new GameTileList(super.getMultiple(indices));}
	
	
	
	@Override
	public GameTileList subList(int fromIndex, int toIndex){
		return new GameTileList(super.subList(fromIndex, toIndex));
	}
	
//	@Override
//	public String toString(){
//		String listString = "";
//		for (GameTile t: this) listString += t + ", ";
//		return listString;
//	}
	
	
	//overloaded methods for tile id
	public int indexOf(Integer id){return indexOf(new GameTile(id));}
	public boolean contains(Integer id){return contains(new GameTile(id));}
	public boolean add(int id){return add(new GameTile(id));}
	
}
