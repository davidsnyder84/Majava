package majava;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import majava.control.testcode.WallDemoer;
import majava.tiles.GameTile;
import majava.util.GameTileList;



//represents the wall (ŽR) of tiles used in the game
public class Wall {
	private static final int NUMBER_OF_DIFFERENT_TILES = 34;
	private static final int MAX_SIZE_WALL = NUMBER_OF_DIFFERENT_TILES * 4;	//136
	private static final int POS_LAST_NORMAL_WALL_TILE = 121;
//	private static final int POS_LAST_DEAD_WALL_TILE = 135;
//	private static final int FIRST_TILE_IN_WALL = 0;
	
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~BEGIN DEAD WALL CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private static final int OFFSET_DEAD_WALL = POS_LAST_NORMAL_WALL_TILE + 1;
	private static final int MAX_SIZE_DEAD_WALL = 14;
	
	//even numbers are top row, odd are bottom row
	private static final int POS_KANDRAW_1 = 12;
	private static final int POS_KANDRAW_2 = 13;
	private static final int POS_KANDRAW_3 = 10;
	private static final int POS_KANDRAW_4 = 11;
	private static final Integer[] POS_KANDRAWS = {POS_KANDRAW_1, POS_KANDRAW_2, POS_KANDRAW_3, POS_KANDRAW_4};
	
	//dora indicators
	private static final int POS_DORA_1 = 8;
	private static final int POS_URADORA_1 = 9;
	
	private static final int POS_DORA_2 = 6;
	private static final int POS_URADORA_2 = 7;
	
	private static final int POS_DORA_3 = 4;
	private static final int POS_URADORA_3 = 5;
	
	private static final int POS_DORA_4 = 2;
	private static final int POS_URADORA_4 = 3;
	
	//these are only used if a player makes 4 kans (making 5 dora indicators)
	private static final int POS_DORA_5 = 0;
	private static final int POS_URADORA_5 = 1;

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~END DEAD WALL CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	
	
	
	
	private GameTile[] wallTiles;
	private int currentWallPosition;
	
	private RoundTracker roundTracker;
	
	
	public Wall(){
		//fill and shuffle the wall
		wallTiles = new GameTile[MAX_SIZE_WALL];
		fillWall();
		shuffle();
	}
	
	private void fillWall(){
		currentWallPosition = 0;
				
		//fill the wall with 4 of each tile, in sequential order
		//make red doras accordingly for fives (1 in man, 2 in pin, 1 in sou)
		final int IDM5 = 5, IDP5 = 14, IDS5 = 23;
		int i = 0;
		for (int id = 1; id <= NUMBER_OF_DIFFERENT_TILES; id++){
			wallTiles[i++] = new GameTile(id);
			wallTiles[i++] = new GameTile(id);
			
			if (id == IDP5) wallTiles[i++] = new GameTile(id, true);
			else            wallTiles[i++] = new GameTile(id);
			
			if (id == IDM5 || id == IDP5 || id == IDS5) wallTiles[i++] = new GameTile(id, true);
			else                                        wallTiles[i++] = new GameTile(id);
		}
	}
	private void shuffle(){Collections.shuffle(Arrays.asList(wallTiles));}
	
	
	
	
	//fills the received TileLists with each player's starting hands
	public void takeStartingHands(List<GameTile> tilesE, List<GameTile> tilesS, List<GameTile> tilesW, List<GameTile> tilesN){
		
		//[East takes 4, South takes 4, West takes 4, North takes 4] x3
		for (int i = 0; i < 3; i++){
			tilesE.addAll(takeTiles(4));
			tilesS.addAll(takeTiles(4));
			tilesW.addAll(takeTiles(4));
			tilesN.addAll(takeTiles(4));
		}
		//East takes 2, South takes 1, West takes 1, North takes 1 x1
		tilesE.add(takeTile()); tilesE.add(takeTile());
		tilesS.add(takeTile());
		tilesW.add(takeTile());
		tilesN.add(takeTile());
	}
	
	
	//returns a list of the dora indicators tiles. if wantUraDora is true, the list will also contain the ura dora indicators
	private GameTileList getDoraIndicators(boolean wantUraDora){
		int indicatorsNeeded = getNumKansMade() + 1;
		GameTileList indicators = new GameTileList(indicatorsNeeded); 
		
		for (int currentIndicator = 1; currentIndicator <= indicatorsNeeded; currentIndicator++)
			indicators.add(getDoraIndicator(currentIndicator));
		
		if (wantUraDora)
			for (int currentIndicator = 1; currentIndicator <= indicatorsNeeded; currentIndicator++)
				indicators.add(getUraDoraIndicator(currentIndicator));
		
		return indicators;
	}
	public GameTileList getDoraIndicators(){return getDoraIndicators(false);}
	public GameTileList getDoraIndicatorsWithUra(){return getDoraIndicators(true);}
	
	//returns the specified dora indicator tile
	private GameTile getDoraIndicator(int doraNumber){return getTile(indexOfDora(doraNumber));}
	private GameTile getUraDoraIndicator(int uraDoraNumber){return getTile(indexOfUraDora(uraDoraNumber));}
	private int indexOfDora(int doraNumber){return OFFSET_DEAD_WALL + 10 - 2*doraNumber;}
	private int indexOfUraDora(int uraDoraNumber){return indexOfDora(uraDoraNumber) + 1;}
	
	
	
	
	
	//removes a tile from the current wall position and returns it. returns the tile, or returns null if the wall was empty
	public GameTile takeTile(){
		GameTile takenTile = null;
		if (currentWallPosition <= POS_LAST_NORMAL_WALL_TILE){
			takenTile = wallTiles[currentWallPosition];
			wallTiles[currentWallPosition] = null;
			currentWallPosition++;
		}
		return takenTile;
	}
	//take multiple tiles at once
	public List<GameTile> takeTiles(int numberOfTilesToTake){
		ArrayList<GameTile> takenTiles = new ArrayList<GameTile>(numberOfTilesToTake);
		for (int i = 0; i < numberOfTilesToTake; i++)
			takenTiles.add(takeTile());
		return takenTiles;
	}
	
	//removes a tile from the end of the dead wall and returns it (for a rinshan draw)
	public GameTile takeTileFromDeadWall(){
		GameTile takenTile = wallTiles[OFFSET_DEAD_WALL + POS_KANDRAWS[getNumKansMade() - 1]];
		wallTiles[OFFSET_DEAD_WALL + POS_KANDRAWS[getNumKansMade() - 1]] = null;
		
		return takenTile;
	}
	
	
	private GameTile getTile(int index){return wallTiles[index];}
	private GameTile getDeadWallTile(int index){return getTile(OFFSET_DEAD_WALL + index);}
	private void setTile(int index, GameTile tile){wallTiles[index] = tile;}
	private void removeTile(int index){wallTiles[index] = null;}
	
	
	
	
	public boolean isEmpty(){return (getNumTilesLeftInWall() == 0);}
	//returns the number of tiles left in the wall (not including dead wall)
	public int getNumTilesLeftInWall(){return MAX_SIZE_WALL - currentWallPosition - MAX_SIZE_DEAD_WALL;}
	public int getNumTilesLeftInDeadWall(){return MAX_SIZE_DEAD_WALL - getNumKansMade();}
	
	public int getNumKansMade(){return roundTracker.getNumKansMade();}
	
	
	
//	public void printWall(){System.out.println(toString());}
//	public void printDeadWall(){System.out.println(toStringDeadWall());}
	
	//tostring
	@Override
	public String toString(){
		String wallString = "";
		
		final int TILES_PER_LINE = 17;
		for (int i = 0; i < getNumTilesLeftInWall() / TILES_PER_LINE + 1; i++){
			for (int j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < getNumTilesLeftInWall()); j++)
				wallString += wallTiles[currentWallPosition + TILES_PER_LINE*i + j].toString() + " ";
			if (TILES_PER_LINE*i < getNumTilesLeftInWall())
				wallString += "\n";
		}
		
		String deadWallString = toStringDeadWall();
		return ("Wall: " + getNumTilesLeftInWall() + "\n" + wallString + "\n\n" + deadWallString);
	}
	
	//string representation of deadwall
	public String toStringDeadWall(){
		String dwString = "";
		String topRow = "", bottomRow = "";
		
		for (int tile = 0; tile < MAX_SIZE_DEAD_WALL / 2; tile++){
			topRow += deadWallTileToString(2*tile) + " ";
			bottomRow += deadWallTileToString(2*tile + 1) + " ";
		}
		
		dwString = "DeadWall: " + getNumTilesLeftInDeadWall() + "\n" + topRow + "\n" + bottomRow;
		return dwString;
	}
	
	
	private String singleTileToString(int index){
		if (wallTiles[index] == null) return "  ";
		else return wallTiles[index].toString();
	}
	private String deadWallTileToString(int index){return singleTileToString(OFFSET_DEAD_WALL + index);}
	
	
	
	public void syncWithTracker(RoundTracker tracker){
		roundTracker = tracker; 
		roundTracker.syncWall(wallTiles);
	}
	
	
	
	//DEMO METHODS
	public void DEMOloadDebugWall(){WallDemoer.loadDebugWall(wallTiles);}
}
