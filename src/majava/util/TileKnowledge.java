package majava.util;

import java.util.ArrayList;
import java.util.List;

import majava.hand.Hand;
import majava.tiles.GameTile;
import majava.tiles.TileInterface;


//inherent charactistic information about a tile
public class TileKnowledge {
	
	
	//returns a list of hot tile IDs for ALL tiles in the hand
	public static List<Integer> findAllHotTiles(GTL tiles){
		List<Integer> allHotTileIds = new ArrayList<Integer>(32);
		List<Integer> singleTileHotTiles = null;
		
		//get hot tiles for each tile in the hand
		for (GameTile t: tiles){
			singleTileHotTiles = TileKnowledge.findHotTilesOfTile(t);
			for (Integer i: singleTileHotTiles) if (!allHotTileIds.contains(i)) allHotTileIds.add(i);
		}
		
		return allHotTileIds;
	}
	//overloaded for Hand
	public static List<Integer> findAllHotTiles(Hand hand){return findAllHotTiles(hand.getTiles());}
	
	
	
	//returns a list of integer IDs of hot tiles, for tile t
	public static List<Integer> findHotTilesOfTile(final TileInterface t){
		
		List<Integer> hotTileIds = new ArrayList<Integer>(5); 
		int id = t.getId(); char face = t.getFace();
		
		//a tile is always its own hot tile (pon/kan/pair)
		hotTileIds.add(id);
		
		//add possible chi partners, if tile is not an honor tile
		if (!t.isHonor()){
			if (face != '1' && face != '2') hotTileIds.add(id - 2);
			if (face != '1') hotTileIds.add(id - 1);
			if (face != '9') hotTileIds.add(id + 1);
			if (face != '8' && face != '9') hotTileIds.add(id + 2);
		}
		//return list of integer IDs
		return hotTileIds;
	}
	
	
	
	
	//returns true if it's possible for Tile T to finish a Chi of the given type
	final static private boolean tileCanCompleteChiL(TileInterface t, char badface1, char badface2){
		return !t.isHonor() && t.getFace() != badface1 && t.getFace() != badface2;
	}
	//Txx
	final static public boolean tileCanCompleteChiL(TileInterface t){return tileCanCompleteChiL(t, '8', '9');}
	//xTx
	final static public boolean tileCanCompleteChiM(TileInterface t){return tileCanCompleteChiL(t, '1', '9');}
	//xxT
	final static public boolean tileCanCompleteChiH(TileInterface t){return tileCanCompleteChiL(t, '1', '2');}
	
	
	
	
	
}
