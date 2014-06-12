package majava.util;

import java.util.ArrayList;

import majava.tiles.GameTile;
import majava.tiles.HandCheckerTile;
import majava.tiles.ImmutableTile;
import majava.tiles.PondTile;
import majava.tiles.TileInterface;


public class ToolList<TileType extends TileInterface> extends ArrayList<TileType>{
	private static final long serialVersionUID = 8412193322599946476L;
	
	
	
	public static void main(String f[]){
		
		
		ToolList<TileInterface> list = null;
		ToolList<GameTile> list2 = null;
		
		
		
		
		System.out.println(list);
	}
}
