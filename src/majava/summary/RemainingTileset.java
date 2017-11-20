package majava.summary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import majava.Wall;
import majava.tiles.GameTile;
import majava.tiles.TileInterface;
import majava.util.GameTileList;

public class RemainingTileset {
	
	private final GameTileList tilesRemaining;
	
	public RemainingTileset() {
		tilesRemaining = new GameTileList();
		tilesRemaining.addAll(Arrays.asList(Wall.generateStandardSetOf134Tiles()));
	}
	
	
	public void remove(GameTile t){
	}
	public void removeAll(Collection<? extends TileInterface> removeThese){
	}
	
}
