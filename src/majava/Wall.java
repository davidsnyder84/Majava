package majava;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
	
	private static final int OFFSET_DEAD_WALL = 122;
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
		
		int numKansMade = getNumKansMade();
		int size = numKansMade + 1;
		if (wantUraDora) size *= 2;
		GameTileList indicators = new GameTileList(size);
		
		//add the first dora indicator
		indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_DORA_1]);
		
		//add additional indicators if kans have been made
		if (numKansMade >= 1) indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_DORA_2]);
		if (numKansMade >= 2) indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_DORA_3]);
		if (numKansMade >= 3) indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_DORA_4]);
		if (numKansMade == 4) indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_DORA_5]);
		
		
		//add ura dora indicators, if specified
		if (wantUraDora){
			//add the first ura dora indicator
			indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_URADORA_1]);

			//add additional ura indicators if kans have been made
			if (numKansMade >= 1) indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_URADORA_2]);
			if (numKansMade >= 2) indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_URADORA_3]);
			if (numKansMade >= 3) indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_URADORA_4]);
			if (numKansMade == 4) indicators.add(wallTiles[OFFSET_DEAD_WALL + POS_URADORA_5]);
		}
		
		return indicators;
	}
	//methods to get a list of dora tiles, or a list of both dora and ura dora tiles 
	public GameTileList getDoraIndicators(){return getDoraIndicators(false);}
	public GameTileList getDoraIndicatorsWithUra(){return getDoraIndicators(true);}
	
	
	
	
	
	
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
	
	
	
	
	
	public boolean isEmpty(){return (getNumTilesLeftInWall() == 0);}
	//returns the number of tiles left in the wall (not including dead wall)
	public int getNumTilesLeftInWall(){return MAX_SIZE_WALL - currentWallPosition - MAX_SIZE_DEAD_WALL;}
	public int getNumTilesLeftInDeadWall(){return MAX_SIZE_DEAD_WALL - getNumKansMade();}
	
	public int getNumKansMade(){return roundTracker.getNumKansMade();}
	
	
	
	
	
	
	
	
	

	////////////////////////////////////////////////////////////////////////////////////
	//////BEGIN DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	private final int[] debugHandSizes = {14,13,13,13};
	
	public void DEMOmyDEMOWALL(){
		
	}
	
	public void DEMOloadDebugWall(){
		
		int tsumo2, tsumo3, tsumo4, tsumo5, tsumo6, tsumo7, tsumo8, tsumo9;
		tsumo2 = tsumo3 = tsumo4 = tsumo5 = tsumo6 = tsumo7 = tsumo8 = tsumo9 = 0;
//		int[] h1 = {2,2,4,4,6,6,8,8,10,10,12,12,15,15};
		
		
//		//multiple minkans
//		int[] h1 = {2,2,2,14,14,14,14,22,22,29,29,29,33,33};
//		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
//		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
//		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		

//		int[] h1 = {3,6,8,9,9,10,12,13,15,23,23,32,32,32};
//		int[] h2 = {4,6,12,13,14,14,18,19,19,21,25,29,34};
//		int[] h3 = {2,5,11,13,16,17,21,23,26,27,28,29,32};
//		int[] h4 = {1,2,6,7,9,10,11,15,18,20,22,24,25};
//	tsumo2 = 9 + 18;
//	tsumo3 = 1 + 18;
//	tsumo4 = 0;
		
		
		//specific error case
//		Integer[] h1 = {1,2,5,8,9,10,11,14,15,20,21,29,30,34};
//		Integer[] h2 = {2,2,4,7,9,15,16,17,18,19,19,22,23};
//		Integer[] h3 = {1,3,7,16,16,17,25,25,28,30,30,31,33};
//		Integer[] h4 = {3,5,9,10,11,11,21,22,24,28,29,29,34};
//		tsumo2 = 24;
		
		//specific error case
//		Integer[] h1 = {3,4,11,11,17};
//		Integer[] h2 = {1,6,7,20};
//		Integer[] h3 = {2,5,5,6};
//		Integer[] h4 = {4,8,12,17};
//		tsumo2 = 2;
//		debugHandSizes = new int[]{5,4,4,4};
		
		
		//kokushi
//		int[] h1 = {1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 34, 34, 7};
		int[] h1 = {1,1,1,2,3,4,5,6,7,8,9,9,9,15};
//		int[] h1 = {1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34, 7};
		int[] h2 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
		int[] h3 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
		for (int i = 0; i < h2.length; i++){h2[i] += 3;	h3[i] -= 1;	h4[i] += 2;}
		tsumo2 = tsumo3 = tsumo4 = tsumo5 = tsumo6 = tsumo7 = tsumo8 = 28;
		tsumo9 = 1;
		
		
		
		//desired hands (ENTER HERE)
//		int[] h1 = {1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34, 7};
//		int[] h1 = {2,2,2,2,2,2,2,2,2,2,2,2,2,7};
//		int[] h1 = {1,1,1,2,3,4,5,6,7,8,9,9,9,15};
		
//		//chiitoi
//		int[] h1 = {2,2,11,11,25,25,28,28,29,32,33,33,34,34};
//		int[] h2 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h3 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		for (int i = 0; i < h2.length; i++){h2[i] += 3;	h3[i] -= 1;	h4[i] += 2;}
		
//		int[] h1 = {2,2,4,4,6,6,8,8,10,10,12,12,15,15};
//		int[] h2 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h3 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
		

		
		
		//kan
//		int[] h1 = {15,2,10,6,4,6,8,2,4,2,12,12,2,10};
//		int[] h2 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h3 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
		
		
//		//priority calling
////		int[] h1 = {2,2,5,5,6,6,8,8,10,10,12,12,15,15};
//		int[] h1 = {1,1,1,2,3,4,5,6,7,8,9,9,9,20};
////		int[] h2 = {1,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h2 = {5,5,5,5,5,5,5,5,5,5,5,5,5};
//		int[] h3 = {1,1,1,2,3,4,5,6,7,8,9,9,9};
////		int[] h3 = {5,5,5,5,5,5,5,5,5,5,5,5,5};
////		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h4 = {1,1,1,1,1,1,1,1,1,1,1,1,1};
//		
//		for (int i = 0; i < h1.length; i++){
//			h1[i] += 9;
//			if (i < h2.length){
//				h4[i] += 9 + 6;
//				h3[i] += 9;
//			}
//		}
////		tsumo2 = 0;
//		tsumo3 = 1 + 9;
////		tsumo4 = 0;
		
		
//		int[] h1 = {2,2,2,2,2,2,2,2,2,2,2,2,2,7};
//		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
//		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
//		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		
		

//		int[] h1 = {1,1,1,2,3,4,5,6,7,8,9,9,9,15};
//		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
//		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
//		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		
		
		
		
		/*
		int[] h1 = {1,2,2,2,3,4,5,6,7,8,9,9,9,3};
		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		*/
		
		
		/*
		int[] h1 = {1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		for(int i = 0; i < 13; i++){
			h1[i] += 18;
			h2[i] += 18;
			h3[i] += 18;
			h4[i] += 18;
		}
		h1[13] += 18;
		*/
		
		Integer h1i[] = new Integer[h1.length], h2i[] = new Integer[h2.length], h3i[] = new Integer[h3.length], h4i[] = new Integer[h4.length];
		for (int i = 0; i < h1.length; i++) h1i[i] = h1[i];
		for (int i = 0; i < h2.length; i++) h2i[i] = h2[i];
		for (int i = 0; i < h3.length; i++) h3i[i] = h3[i];
		for (int i = 0; i < h4.length; i++) h4i[i] = h4[i];
		
		GameTileList tilesE = new GameTileList(h1i);
		GameTileList tilesS = new GameTileList(h2i);
		GameTileList tilesW = new GameTileList(h3i);
		GameTileList tilesN = new GameTileList(h4i);
		
		while (tilesE.size() < 14) tilesE.add(new GameTile(0));
		while (tilesS.size() < 13) tilesS.add(new GameTile(0));
		while (tilesW.size() < 13) tilesW.add(new GameTile(0));
		while (tilesN.size() < 13) tilesN.add(new GameTile(0));
		
		if (debugHandSizes[0] > 14) debugHandSizes[0] = 14;
		for (int i = 1; i < debugHandSizes.length; i++) if (debugHandSizes[i] > 13) debugHandSizes[i] = 13;
		
		
		final int TAKEN_PER_ROUND = 16;
		final int TAKEN_PER_PLAYER = 4;
		
		//put desired hands in the wall
		int i, j;
		//each player takes 4, 3 times
		for (i = 0; i < 3; i++){
			//east takes 4
			for (j = 0; j < 4; j++)
				wallTiles[TAKEN_PER_ROUND*i + j + 0*TAKEN_PER_PLAYER] = tilesE.remove(0);
			//south takes 4
			for (j = 0; j < 4; j++)
				wallTiles[TAKEN_PER_ROUND*i + j + 1*TAKEN_PER_PLAYER] = tilesS.remove(0);
			//west takes 4
			for (j = 0; j < 4; j++)
				wallTiles[TAKEN_PER_ROUND*i + j + 2*TAKEN_PER_PLAYER] = tilesW.remove(0);
			//north takes 4
			for (j = 0; j < 4; j++)
				wallTiles[TAKEN_PER_ROUND*i + j + 3*TAKEN_PER_PLAYER] = tilesN.remove(0);
		}
		//east takes 2
		wallTiles[3*TAKEN_PER_ROUND + 0] = tilesE.remove(0);
		wallTiles[3*TAKEN_PER_ROUND + 1] = tilesE.remove(0);

		//south takes 1
		wallTiles[3*TAKEN_PER_ROUND + 2] = tilesS.remove(0);

		//west takes 1
		wallTiles[3*TAKEN_PER_ROUND + 3] = tilesW.remove(0);

		//north takes 1
		wallTiles[3*TAKEN_PER_ROUND + 4] = tilesN.remove(0);
		
		
		if (tsumo2 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 1] = new GameTile(tsumo2);
		if (tsumo3 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 2] = new GameTile(tsumo3);
		if (tsumo4 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 3] = new GameTile(tsumo4);
		
		
		if (tsumo5 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 4] = new GameTile(tsumo5);
		if (tsumo6 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 5] = new GameTile(tsumo6);
		if (tsumo7 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 6] = new GameTile(tsumo7);
		if (tsumo8 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 7] = new GameTile(tsumo8);
		
		if (tsumo9 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 8] = new GameTile(tsumo9);
		
	}
	public int[] DEMOgetDebugPlayerHandSizes(){return debugHandSizes;}
	////////////////////////////////////////////////////////////////////////////////////
	//////END DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
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
	
	
}
