

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
	public static final int FIRST_TILE_IN_WALL = 0;
	
	public static final boolean DEGBUG_SHUFFLE_WALL = Table.DEBUG_LOAD_DEBUG_WALL;
	
	
	
	
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~BEGIN DEAD WALL CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private static final int OFFSET_DEAD_WALL = 122;
	private static final int MAX_SIZE_DEAD_WALL = 14;
	
	//odd numbers are top row, even are bottom row
	private static final int POS_KANDRAW_1 = 13;
	private static final int POS_KANDRAW_2 = 12;
	private static final int POS_KANDRAW_3 = 11;
	private static final int POS_KANDRAW_4 = 10;
	
	//dora indicators
	private static final int POS_DORA_1 = 9;
	private static final int POS_URADORA_1 = 8;
	
	private static final int POS_DORA_2 = 7;
	private static final int POS_URADORA_2 = 6;
	
	private static final int POS_DORA_3 = 5;
	private static final int POS_URADORA_3 = 4;
	
	private static final int POS_DORA_4 = 3;
	private static final int POS_URADORA_4 = 2;
	
	//these are only used if a player makes 4 kans (making 5 dora indicators)
	private static final int POS_DORA_5 = 1;
	private static final int POS_URADORA_5 = 0;

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~END DEAD WALL CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	
	
	
	
	
	
	
	private TileList mTiles;

	private int mNumKansMade; 
	private int mStartOfDeadWall;
	
	
	
	
	public Wall(){
		//fill and shuffle the wall
		mTiles = new TileList(MAX_SIZE_WALL);
		__initialize();
		
		//mark the start of the dead wall
		mStartOfDeadWall = OFFSET_DEAD_WALL;
		mNumKansMade = 0;
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
	private void __initialize()
	{
		//fill the wall with 4 of each tile, in sequential order
		for (int id = 1; id <= Tile.NUMBER_OF_DIFFERENT_TILES; id++){
			mTiles.add(new Tile(id));
			mTiles.add(new Tile(id));
			mTiles.add(new Tile(id));
			mTiles.add(new Tile(id));
		}
		
		//mark the red dora fives (1 in man, 2 in pin, 1 in sou)
		mTiles.get((5-1) * 4).setRedDora();
		mTiles.get(((5-1) + 9) * 4).setRedDora();
		mTiles.get(((5-1) + 9) * 4 + 1).setRedDora();
		mTiles.get(((5-1) + 9*2) * 4).setRedDora();
		
		//shuffle the wall
		mTiles.shuffle();
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
		
		int size = mNumKansMade + 1;
		if (getUraDora) size *= 2;
		TileList indicators = new TileList(size);
		
		//add the first dora indicator
		indicators.add(mTiles.get(mStartOfDeadWall + POS_DORA_1));
		
		//add other indicators if kans have been made
		if (mNumKansMade >= 1) indicators.add(mTiles.get(mStartOfDeadWall + POS_DORA_2));
		if (mNumKansMade >= 2) indicators.add(mTiles.get(mStartOfDeadWall + POS_DORA_3));
		if (mNumKansMade >= 3) indicators.add(mTiles.get(mStartOfDeadWall + POS_DORA_4));
		if (mNumKansMade == 4) indicators.add(mTiles.get(mStartOfDeadWall + POS_DORA_5));
		
		
		//add ura dora indicators, if specified
		if (getUraDora){
			//add the first ura dora indicator
			indicators.add(mTiles.get(mStartOfDeadWall + POS_URADORA_1));

			//add other ura indicators if kans have been made
			if (mNumKansMade >= 1) indicators.add(mTiles.get(mStartOfDeadWall + POS_URADORA_2));
			if (mNumKansMade >= 2) indicators.add(mTiles.get(mStartOfDeadWall + POS_URADORA_3));
			if (mNumKansMade >= 3) indicators.add(mTiles.get(mStartOfDeadWall + POS_URADORA_4));
			if (mNumKansMade == 4) indicators.add(mTiles.get(mStartOfDeadWall + POS_URADORA_5));
		}
		
		return indicators;
	}
	//methods to get a list of dora tiles, or a list of both dora and ura dora tiles 
	public TileList getDoraIndicators(){return __getDoraIndicators(false);}
	public TileList getDoraIndicatorsWithUra(){return __getDoraIndicators(true);}
	
	
	
	
	
	
	/*
	method: takeTile
	removes a tile from the beginning of the wall and returns it
	returns the tile, or returns null if the wall was empty
	*/
	public Tile takeTile(){
		mStartOfDeadWall--;
		return mTiles.removeFirst();
	}
	
	
	
	//removes a tile from the end of the dead wall and returns it (for a rinshan draw)
	public Tile takeTileFromDeadWall(){
		mNumKansMade++;
		return mTiles.removeLast();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns true if the wall is empty (has no tiles left)
	public boolean isEmpty(){return (getNumTilesLeftInWall() == 0);}
	//returns the number of tiles left in the wall (not including dead wall)
	public int getNumTilesLeftInWall(){return mTiles.size() - getNumTilesLeftInDeadWall();}
	//returns the number of tiles left in the dead wall
	public int getNumTilesLeftInDeadWall(){return MAX_SIZE_DEAD_WALL - mNumKansMade;}
	
	
	//returns the number of kans made
	public int getNumKansMade(){return mNumKansMade;}
	
	
	
	
	
	
	
	
	

	////////////////////////////////////////////////////////////////////////////////////
	//////BEGIN DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	public void loadDebugWall(){
		
		//desired hands (ENTER HERE)
		int[] h1 = {2,2,2,2,2,2,2,2,2,2,2,2,2,7};
		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		
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
		for (i = 0; i < 3; i++)
		{
			//east takes 4
			for (j = 0; j < 4; j++)
			{
				mTiles.set(TAKEN_PER_ROUND*i + j + 0*TAKEN_PER_PLAYER, tilesE.get(0));
				tilesE.remove(0);
			}
			//south takes 4
			for (j = 0; j < 4; j++)
			{
				mTiles.set(TAKEN_PER_ROUND*i + j + 1*TAKEN_PER_PLAYER, tilesS.get(0));
				tilesS.remove(0);
			}
			//west takes 4
			for (j = 0; j < 4; j++)
			{
				mTiles.set(TAKEN_PER_ROUND*i + j + 2*TAKEN_PER_PLAYER, tilesW.get(0));
				tilesW.remove(0);
			}
			//north takes 4
			for (j = 0; j < 4; j++)
			{
				mTiles.set(TAKEN_PER_ROUND*i + j + 3*TAKEN_PER_PLAYER, tilesN.get(0));
				tilesN.remove(0);
			}
		}
		//east takes 2
		mTiles.set(3*TAKEN_PER_ROUND + 0, tilesE.get(0));
		tilesE.remove(0);
		mTiles.set(3*TAKEN_PER_ROUND + 1, tilesE.get(0));
		tilesE.remove(0);

		//south takes 1
		mTiles.set(3*TAKEN_PER_ROUND + 2, tilesS.get(0));
		tilesS.remove(0);

		//west takes 1
		mTiles.set(3*TAKEN_PER_ROUND + 3, tilesW.get(0));
		tilesW.remove(0);

		//north takes 1
		mTiles.set(3*TAKEN_PER_ROUND + 4, tilesN.get(0));
		tilesN.remove(0);
		
	}
	////////////////////////////////////////////////////////////////////////////////////
	//////END DEMO METHODS
	////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	//tostring
	@Override
	public String toString(){
		
		int i, j;
		String wallString = "";
		
		final int TILES_PER_LINE = 17;
		for (i = 0; i < getNumTilesLeftInWall() / TILES_PER_LINE + 1; i++){
			for (j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < getNumTilesLeftInWall()); j++){
				wallString += mTiles.get(TILES_PER_LINE*i + j).toString() + " ";
			}
			if (TILES_PER_LINE*i < getNumTilesLeftInWall())
				wallString += "\n";
		}
		
		
		String dWallString = "DeadWall: " + getNumTilesLeftInDeadWall() + "\n" + toStringDeadWall();
		
		return ("Wall: " + getNumTilesLeftInWall() + "\n" + wallString + "\n\n" + dWallString);
	}
	
	//string representation of deadwall
	public String toStringDeadWall(){
		
		int i, j;
		String wallString = "";
		
		final int TILES_PER_LINE = 2;
		for (i = 0; i < getNumTilesLeftInDeadWall() / TILES_PER_LINE + 1; i++){
			for (j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < getNumTilesLeftInDeadWall()); j++){
				wallString += mTiles.get(this.mStartOfDeadWall + TILES_PER_LINE*i + j).toString() + " ";
			}
			if (TILES_PER_LINE*i < getNumTilesLeftInDeadWall())
				wallString += "\n";
		}
		
		return wallString;
	}
	
	
	
	
	private RoundTracker mRTracker;
	public void syncWithTracker(RoundTracker tracker){
		mRTracker = tracker; 
		mRTracker.syncWall(mTiles);
	}
	
	
}
