package majava.summary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import majava.wall.Wall;
import majava.tiles.GameTile;
import majava.tiles.TileInterface;
import majava.util.GTL;

public class RemainingTileset {
	
	private final GTL tilesRemaining;
	
	public RemainingTileset() {
		tilesRemaining = new GTL(Wall.standardSetOf134Tiles());
	}
	
	
	public void remove(GameTile t){
	}
	public void removeAll(Collection<? extends TileInterface> removeThese){
	}
	
}
