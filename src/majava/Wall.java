package majava;

import java.util.Scanner;

import majava.tiles.Tile;

import utility.GenSort;



/*
Class: Wall
represents the wall of tiles used in the game

data:
	mTiles - list of tiles in the wall (includes dead wall)
	mNumKansMade - the number of kans made (affects kan draws and dora indicators)
	mStartOfDeadWall - marks the index of the first dead wall tile
	
	
methods:
	constructors:
	no-arg - builds and initializes the wall

	mutators:
	dealHands - deals starting hands for each player
	takeTile - removes a tile from the beginning of the wall and returns it
	takeTileFromDeadWall - removes a tile from the end of the dead wall and returns it
 	
 	accessors:
	isEmpty - returns true if the main wall is empty (has no tiles left)
	getNumTilesLeftInWall - returns the number of tiles left in the wall (not including dead wall)
	
	getNumTilesLeftInDeadWall - returns the number of tiles left in the dead wall
 	getNumKansMade - returns the number of kans made
	getDoraIndicators - returns a list of dora indicator tiles
	getDoraIndicatorsWithUra - returns a list of dora indicator tiles, including ura dora
	
	other:
	toString
	
	private:
	__initialize - builds, initializes, and shuffles the wall
	__getDoraIndicators - returns a list of dora indicator tiles. can also return ura dora depending on the flag.
*/
public class Wall {
	

	public static final int MAX_SIZE_WALL = 136;
	public static final int POS_LAST_NORMAL_WALL_TILE = 121;
	public static final int POS_LAST_DEAD_WALL_TILE = 135;
	public static final int POS_START_OF_DEAD_WALL = 122;
	public static final int FIRST_TILE_IN_WALL = 0;
	
	public static final boolean DEGBUG_SHUFFLE_WALL = Round.DEBUG_LOAD_DEBUG_WALL;
	
	
	
	
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~BEGIN DEAD WALL CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	public static final int OFFSET_DEAD_WALL = 122;
	public static final int MAX_SIZE_DEAD_WALL = 14;
	
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
	
	
	
	
	
	
	
	
//	private TileList mTiles;
	private Tile[] mTiles;
	
	private int mCurrentWallPosition;
//	private int mNumKansMade;
	
	private RoundTracker mRoundTracker;
	
	
	
	
	public Wall(){
		//fill and shuffle the wall
		mTiles = new Tile[MAX_SIZE_WALL];
		__initialize();
	}
	
	
	
	
	/*
	method: dealHands
	deals a starting hand for each player
	
	East takes 4, South takes 4, West takes 4, North takes 4
    East takes 4, South takes 4, West takes 4, North takes 4
    East takes 4, South takes 4, West takes 4, North takes 4
    East takes 2, South takes 1, West takes 1, North takes 1
	*/
	public void dealHands(Player p1, Player p2, Player p3, Player p4){
		TileList tilesE = new TileList(Hand.MAX_HAND_SIZE);
		TileList tilesS = new TileList(Hand.MAX_HAND_SIZE - 1);
		TileList tilesW = new TileList(Hand.MAX_HAND_SIZE - 1);
		TileList tilesN = new TileList(Hand.MAX_HAND_SIZE - 1);
		
		int i, j;
		//each player takes 4, 3 times
		for (i = 0; i < 3; i++){
			//east takes 4
			for (j = 0; j < 4; j++) tilesE.add(takeTile());
			//south takes 4
			for (j = 0; j < 4; j++) tilesS.add(takeTile());
			//west takes 4
			for (j = 0; j < 4; j++) tilesW.add(takeTile());
			//north takes 4
			for (j = 0; j < 4; j++) tilesN.add(takeTile());
		}
		
		
		//east takes 2
		for (j = 0; j < 2; j++) tilesE.add(takeTile());
		//south takes 1
		tilesS.add(takeTile());
		//west takes 1
		tilesW.add(takeTile());
		//north takes 1
		tilesN.add(takeTile());
		
		
		//add the tiles to the hands
		for(Tile t: tilesE) p1.addTileToHand(t);
		for(Tile t: tilesS) p2.addTileToHand(t);
		for(Tile t: tilesW) p3.addTileToHand(t);
		for(Tile t: tilesN) p4.addTileToHand(t);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	private method: __initialize
	fills and shuffles the wall
	
	fill the wall with 4 of each tile
	mark the red dora 5 tiles (1 in man, 2 in pin, 1 in sou)
	shuffle the wall
	*/
	private void __initialize(){
		
		mCurrentWallPosition = 0;
		int i = mCurrentWallPosition;
		
		
		//fill the wall with 4 of each tile, in sequential order
		for (int id = 1; id <= Tile.NUMBER_OF_DIFFERENT_TILES; id++){
			mTiles[i++] = new Tile(id);
			mTiles[i++] = new Tile(id);
			mTiles[i++] = new Tile(id);
			mTiles[i++] = new Tile(id);
		}
		
		//mark the red dora fives (1 in man, 2 in pin, 1 in sou)
		mTiles[(5-1) * 4].setRedDora();
		mTiles[((5-1) + 9) * 4].setRedDora();
		mTiles[((5-1) + 9) * 4 + 1].setRedDora();
		mTiles[((5-1) + 9*2) * 4].setRedDora();
		
		//shuffle the wall
		GenSort<Tile> shuffler = new GenSort<Tile>(mTiles);
		shuffler.shuffleArray();
	}
	
	
	
	
	
	
	
	/*
	 private method: __getDoraIndicators
	 returns the dora indicators, as a list of Tiles
	 input: if getUraDora is true, the list will also contain the ura dora indicators
	 
	 
	 decide the exact size of the list
	 add first dora indicator
	 if kans have been made,  add more indicators to the list
	 if (getUraDora)
	 	add first ura dora indicator
	 	if kans have been made,  add more ura indicators to the list
	 return the list
	*/
	private TileList __getDoraIndicators(boolean getUraDora){
		
		int numKansMade = mRoundTracker.getNumKansMade();
		int size = numKansMade + 1;
		if (getUraDora) size *= 2;
		TileList indicators = new TileList(size);
		
		//add the first dora indicator
		indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_DORA_1]);
		
		//add additional indicators if kans have been made
		if (numKansMade >= 1) indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_DORA_2]);
		if (numKansMade >= 2) indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_DORA_3]);
		if (numKansMade >= 3) indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_DORA_4]);
		if (numKansMade == 4) indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_DORA_5]);
		
		
		//add ura dora indicators, if specified
		if (getUraDora){
			//add the first ura dora indicator
			indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_URADORA_1]);

			//add additional ura indicators if kans have been made
			if (numKansMade >= 1) indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_URADORA_2]);
			if (numKansMade >= 2) indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_URADORA_3]);
			if (numKansMade >= 3) indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_URADORA_4]);
			if (numKansMade == 4) indicators.add(mTiles[POS_START_OF_DEAD_WALL + POS_URADORA_5]);
		}
		
		return indicators;
	}
	//methods to get a list of dora tiles, or a list of both dora and ura dora tiles 
	public TileList getDoraIndicators(){return __getDoraIndicators(false);}
	public TileList getDoraIndicatorsWithUra(){return __getDoraIndicators(true);}
	
	
	
	
	
	
	/*
	method: takeTile
	removes a tile from the current wall position and returns it
	returns the tile, or returns null if the wall was empty
	*/
	public Tile takeTile(){
		Tile takenTile = null;
		if (mCurrentWallPosition <= POS_LAST_NORMAL_WALL_TILE){
			takenTile = mTiles[mCurrentWallPosition];
			mTiles[mCurrentWallPosition] = null;
			mCurrentWallPosition++;
		}
		
		return takenTile;
	}
	
	
	
	//removes a tile from the end of the dead wall and returns it (for a rinshan draw)
	public Tile takeTileFromDeadWall(){
		Tile takenTile = null;

		takenTile = mTiles[POS_START_OF_DEAD_WALL + POS_KANDRAWS[mRoundTracker.getNumKansMade() - 1]];
		mTiles[POS_START_OF_DEAD_WALL + POS_KANDRAWS[mRoundTracker.getNumKansMade() - 1]] = null;
		
		return takenTile;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns true if the wall is empty (has no tiles left)
	public boolean isEmpty(){return (getNumTilesLeftInWall() == 0);}
	//returns the number of tiles left in the wall (not including dead wall)
	public int getNumTilesLeftInWall(){return MAX_SIZE_WALL - mCurrentWallPosition - MAX_SIZE_DEAD_WALL;}
	//returns the number of tiles left in the dead wall
	public int getNumTilesLeftInDeadWall(){return MAX_SIZE_DEAD_WALL - mRoundTracker.getNumKansMade();}
	
	
	//returns the number of kans made
	public int getNumKansMade(){return mRoundTracker.getNumKansMade();}
	
	
	
	
	
	
	
	
	

	////////////////////////////////////////////////////////////////////////////////////
	//////BEGIN DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	public void loadDebugWall(){
		
		//desired hands (ENTER HERE)
		int[] h1 = {2,2,2,2,2,2,2,2,2,2,2,2,2,7};
		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		
		

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
		

		TileList tilesE = new TileList(14);
		TileList tilesS = new TileList(13);
		TileList tilesW = new TileList(13);
		TileList tilesN = new TileList(13);
		for(int i: h1)
			tilesE.add(new Tile(i));
		for(int i: h2)
			tilesS.add(new Tile(i));
		for(int i: h3)
			tilesW.add(new Tile(i));
		for(int i: h4)
			tilesN.add(new Tile(i));
		
		final int TAKEN_PER_ROUND = 16;
		final int TAKEN_PER_PLAYER = 4;
		
		//put desired hands in the wall
		int i, j;
		//each player takes 4, 3 times
		for (i = 0; i < 3; i++){
			//east takes 4
			for (j = 0; j < 4; j++)
				mTiles[TAKEN_PER_ROUND*i + j + 0*TAKEN_PER_PLAYER] = tilesE.removeFirst();
			//south takes 4
			for (j = 0; j < 4; j++)
				mTiles[TAKEN_PER_ROUND*i + j + 1*TAKEN_PER_PLAYER] = tilesS.removeFirst();
			//west takes 4
			for (j = 0; j < 4; j++)
				mTiles[TAKEN_PER_ROUND*i + j + 2*TAKEN_PER_PLAYER] = tilesW.removeFirst();
			//north takes 4
			for (j = 0; j < 4; j++)
				mTiles[TAKEN_PER_ROUND*i + j + 3*TAKEN_PER_PLAYER] = tilesN.removeFirst();
		}
		//east takes 2
		mTiles[3*TAKEN_PER_ROUND + 0] = tilesE.removeFirst();
		mTiles[3*TAKEN_PER_ROUND + 1] = tilesE.removeFirst();

		//south takes 1
		mTiles[3*TAKEN_PER_ROUND + 2] = tilesS.removeFirst();

		//west takes 1
		mTiles[3*TAKEN_PER_ROUND + 3] = tilesW.removeFirst();

		//north takes 1
		mTiles[3*TAKEN_PER_ROUND + 4] = tilesN.removeFirst();
		
	}
	////////////////////////////////////////////////////////////////////////////////////
	//////END DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public void printDoraIndicators(){
		TileList t = getDoraIndicators();
		System.out.println("Dora Indicators: " + t.toString() + "\n\n");
	}
	
	

	public void printWall(){System.out.println(toString());}
	public void printDeadWall(){System.out.println(toStringDeadWall());}
	
	//tostring
	@Override
	public String toString(){
		
		int i, j;
		String wallString = "";
		
		final int TILES_PER_LINE = 17;
		for (i = 0; i < getNumTilesLeftInWall() / TILES_PER_LINE + 1; i++){
			for (j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < getNumTilesLeftInWall()); j++){
				wallString += mTiles[mCurrentWallPosition + TILES_PER_LINE*i + j].toString() + " ";
			}
			if (TILES_PER_LINE*i < getNumTilesLeftInWall())
				wallString += "\n";
		}
		
		String dWallString = toStringDeadWall();
		return ("Wall: " + getNumTilesLeftInWall() + "\n" + wallString + "\n\n" + dWallString);
	}
	
	//string representation of deadwall
	public String toStringDeadWall(){

		String dwString = "";
		String topRow = "";
		String bottomRow = "";
		
		int tile;
		for (tile = 0; tile < MAX_SIZE_DEAD_WALL / 2; tile++){
			topRow += __deadWallTileToString(2*tile) + " ";
			bottomRow += __deadWallTileToString(2*tile + 1) + " ";
		}
		
		dwString = "DeadWall: " + getNumTilesLeftInDeadWall() + "\n" + topRow + "\n" + bottomRow;
		return dwString;
	}
	
	
	private String __wallTileToString(int index){
		if (mTiles[index] == null) return "  ";
		else return mTiles[index].toString();
	}
	private String __deadWallTileToString(int index){return __wallTileToString(POS_START_OF_DEAD_WALL + index);}
	
	
	
	public void syncWithTracker(RoundTracker tracker){
		mRoundTracker = tracker; 
		mRoundTracker.syncWall(mTiles);
	}
	
	
}
