package majava.wall;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import majava.tiles.GameTile;
import majava.util.GTL;



//represents the wall (ŽR/•Ç) of tiles used in the game
public class Wall {
	
	private static final int NUMBER_OF_DIFFERENT_TILES = 34;
	private static final int MAX_SIZE_WALL = NUMBER_OF_DIFFERENT_TILES * 4;	//136
	private static final int POS_START = 0;
	private static final int POS_LAST_NORMAL_WALL_TILE = 121;
//	private static final int POS_LAST_DEAD_WALL_TILE = 135;
	
	//dead wall counstants
	private static final int OFFSET_DEAD_WALL = POS_LAST_NORMAL_WALL_TILE + 1;
	private static final int MAX_SIZE_DEAD_WALL = 14;
	private static final GameTile REMOVED = null;
	
	
	
	private final GameTile[] wallTiles;
	private final int currentWallPosition;
	
	
	public Wall(){this(standardSetOf134Tiles());}
	public Wall(GameTile[] newTiles){this(newTiles, POS_START);}
	
	public Wall(GameTile[] newTiles, int pos){
		wallTiles = newTiles.clone();
		currentWallPosition = pos;
	}
	public Wall withTiles(GameTile[] newTiles){return new Wall(newTiles, currentWallPosition);}
	public Wall withPosition(int newPos){return new Wall(wallTiles, newPos);}
	
	
	
	private GameTile[] tilesClone(){return wallTiles.clone();}
	
	
	
	
	
	public GameTile nextTile(){
		if (currentWallPosition > POS_LAST_NORMAL_WALL_TILE) return null;
		return getTileAt(currentWallPosition);
	}
	
	public GameTile nextDeadWallTile(){return getTileAt(indexOfNextDeadwallTile());}
	private int indexOfNextDeadwallTile(){
		//even numbers are top row, odd are bottom row
		final int POS_KANDRAW_1 = 12, POS_KANDRAW_2 = 13, POS_KANDRAW_3 = 10, POS_KANDRAW_4 = 11;
		final Integer[] POS_KANDRAWS = {POS_KANDRAW_1, POS_KANDRAW_2, POS_KANDRAW_3, POS_KANDRAW_4};
		
		return OFFSET_DEAD_WALL + POS_KANDRAWS[numKansMade()];
	}
	
	public List<GameTile> getNextMultiple(int numberOfTilesToTake){
		ArrayList<GameTile> nextTiles = new ArrayList<GameTile>();
		for (int i = 0; i < numberOfTilesToTake; i++)
			nextTiles.add(getTileAt(currentWallPosition + i));
		return nextTiles;
	}
	
	public GameTile getTileAt(int index){return wallTiles[index];}
	public GameTile[] getTiles(){return tilesClone();}
	private GameTile getDeadWallTile(int index){return getTileAt(OFFSET_DEAD_WALL + index);}
	
	
	
	
	
	public int currentPosition(){return currentWallPosition;}
	public boolean isEmpty(){return (numTilesLeftInWall() == 0);}
	//returns the number of tiles left in the wall (not including dead wall)
	public int numTilesLeftInWall(){return MAX_SIZE_WALL - currentWallPosition - MAX_SIZE_DEAD_WALL;}
	public int numTilesLeftInDeadWall(){return MAX_SIZE_DEAD_WALL - numKansMade();}
	
	
	private int numKansMade(){
		int numberOfTilesMissingFromDeadWall = 0;
		for (int index = 0; index < MAX_SIZE_DEAD_WALL; index++)
			if (getDeadWallTile(index) == REMOVED) numberOfTilesMissingFromDeadWall++;
//		for (GameTile t: deadWallTiles()) if (t == null) numberOfTilesMissingFromDeadWall++;
		return numberOfTilesMissingFromDeadWall;
	}
	private GameTile[] deadWallTiles(){
		GameTile[] deadWallSection = new GameTile[MAX_SIZE_DEAD_WALL];
		for (int index = 0; index < MAX_SIZE_DEAD_WALL; index++)
			deadWallSection[index] = getDeadWallTile(index);
		return deadWallSection;
	}
	
	
	
	//returns a list of the dora indicators tiles. if wantUraDora is true, the list will also contain the ura dora indicators
	private GTL getDoraIndicators(boolean wantUraDora){
		int indicatorsNeeded = numKansMade() + 1;
		GTL indicators = new GTL(); 
		
		for (int currentIndicator = 1; currentIndicator <= indicatorsNeeded; currentIndicator++)
			indicators = indicators.add(getDoraIndicator(currentIndicator));
		
		if (wantUraDora)
			for (int currentIndicator = 1; currentIndicator <= indicatorsNeeded; currentIndicator++)
				indicators = indicators.add(getUraDoraIndicator(currentIndicator));
		
		return indicators;
	}
	public GTL getDoraIndicators(){return getDoraIndicators(false);}
	public GTL getDoraIndicatorsWithUra(){return getDoraIndicators(true);}
	
	//returns the specified dora indicator tile
	private GameTile getDoraIndicator(int doraNumber){return getTileAt(indexOfDora(doraNumber));}
	private GameTile getUraDoraIndicator(int uraDoraNumber){return getTileAt(indexOfUraDora(uraDoraNumber));}
	private int indexOfDora(int doraNumber){return OFFSET_DEAD_WALL + 10 - 2*doraNumber;}
	private int indexOfUraDora(int uraDoraNumber){return indexOfDora(uraDoraNumber) + 1;}
	
	
	
	
	
	
	
	
	//-----------------------------------------------------------------------mutators
	
	//remove
	public Wall removeNextTile(){
		if (currentWallPosition > POS_LAST_NORMAL_WALL_TILE) return null;
		return this.removeTileAt(currentWallPosition).withPosition(currentWallPosition + 1);
	}
	public Wall removeTileAt(int index){
		GameTile[] tilesWithRemoved = tilesClone();
		tilesWithRemoved[index] = REMOVED;
		
		return this.withTiles(tilesWithRemoved);
	}
	public Wall removeNextDeadWallTile(){return removeTileAt(indexOfNextDeadwallTile());}
	
	public Wall removeNextMultiple(int numberOfTilesToTake){
		Wall thisWithRemovedMultiple = this;
		for (int i = 0; i < numberOfTilesToTake; i++)
			thisWithRemovedMultiple = thisWithRemovedMultiple.removeNextTile();
		return thisWithRemovedMultiple;
	}
	
	
	//set
	public Wall setTileAt(int index, GameTile tile){
		GameTile[] tilesWithSet = tilesClone();
		tilesWithSet[index] = tile;
		return this.withTiles(tilesWithSet);
	}
	
	
	//shuffle
	public Wall shuffle(){return this.withTiles(shuffledTiles());}
	private GameTile[] shuffledTiles(){
		GameTile[] shuffledTiles = tilesClone();
		Collections.shuffle(Arrays.asList(shuffledTiles));
		return shuffledTiles;
	}
	//-----------------------------------------------------------------------mutators
	
	
	
	
	
	
	
	
	
	//tostring
	@Override
	public String toString(){
		String wallString = "";
		
		final int TILES_PER_LINE = 17;
		for (int i = 0; i < numTilesLeftInWall() / TILES_PER_LINE + 1; i++){
			for (int j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < numTilesLeftInWall()); j++)
				wallString += getTileAt(currentWallPosition + TILES_PER_LINE*i + j) + " ";
			if (TILES_PER_LINE*i < numTilesLeftInWall())
				wallString += "\n";
		}
		
		String deadWallString = toStringDeadWall();
		return ("Wall: " + numTilesLeftInWall() + "\n" + wallString + "\n\n" + deadWallString);
	}
	
	//string representation of deadwall
	public String toStringDeadWall(){
		String dwString = "";
		String topRow = "", bottomRow = "";
		
		for (int tile = 0; tile < MAX_SIZE_DEAD_WALL / 2; tile++){
			topRow += deadWallTileToString(2*tile) + " ";
			bottomRow += deadWallTileToString(2*tile + 1) + " ";
		}
		
		dwString = "DeadWall: " + numTilesLeftInDeadWall() + "\n" + topRow + "\n" + bottomRow;
		return dwString;
	}
	
	
	private String singleTileToString(int index){
		if (getTileAt(index) == REMOVED) return "  ";
		else return getTileAt(index).toString();
	}
	private String deadWallTileToString(int index){return singleTileToString(OFFSET_DEAD_WALL + index);}
	
	
	
	
	
	
	
	public static GameTile[] standardSetOf134Tiles(){
		final GameTile[] tiles = new GameTile[MAX_SIZE_WALL];
		final int IDM5 = 5, IDP5 = 14, IDS5 = 23;
		int index = 0;
		//fill the set with 4 of each tile, in sequential order
		//make red doras accordingly for fives (1 in man, 2 in pin, 1 in sou)
		for (int id = 1; id <= NUMBER_OF_DIFFERENT_TILES; id++){
			tiles[index++] = new GameTile(id);
			tiles[index++] = new GameTile(id);
			
			if (id == IDP5) tiles[index++] = new GameTile(id, true);
			else            tiles[index++] = new GameTile(id);
			
			if (id == IDM5 || id == IDP5 || id == IDS5) tiles[index++] = new GameTile(id, true);
			else                                        tiles[index++] = new GameTile(id);
		}
		return tiles;
	}
}
