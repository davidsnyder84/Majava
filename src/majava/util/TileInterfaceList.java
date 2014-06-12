package majava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import utility.ConviniList;

import majava.tiles.HandCheckerTile;
import majava.tiles.GameTile;
import majava.tiles.ImmutableTile;
import majava.tiles.TileInterface;

public class TileInterfaceList extends ConviniList<TileInterface>{
	private static final long serialVersionUID = 114707090841523426L;
	
	
	//creates a new list with the given capacity
	public TileInterfaceList(int capacity){super(capacity);}
	
	
	//takes a List
	public TileInterfaceList(List<TileInterface> tiles){
		this(tiles.size());
		addAll(tiles);
	}
	//can take an array, or a var args
	public TileInterfaceList(TileInterface tiles){this(Arrays.asList(tiles));}
	public TileInterfaceList(){this(10);}
	
	
	
	public TileInterfaceList(Integer... ids){
		this(ImmutableTile.retrieveMultipleTiles(ids));
//		this(ids.length);
//		for (Integer id: ids) add(ImmutableTile.retrieveTile(id));
	}
	
	
	public TileInterfaceList clone(){
		return new TileInterfaceList(this);
//		TileInterfaceList copy = new TileInterfaceList(size());
//		for (TileInterface t: this) copy.add(t.clone());
//		return copy;
	}
	
	
	
	
	
	
	
	public ConviniList<GameTile> toGameTiles(){return toGameTiles(this);}
	
	
	
	
	
	
	
	
	
	
	
	
	public static final ConviniList<GameTile> toGameTiles(List<TileInterface> list){
		ConviniList<GameTile> listGT = new ConviniList<GameTile>();
		for (TileInterface t: list)
			if (t instanceof GameTile) listGT.add((GameTile)t);
			else listGT.add(new GameTile(t));
		return listGT;
	}
	
	
	
}

