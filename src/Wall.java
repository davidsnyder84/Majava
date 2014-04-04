

/*
Class: Wall
represents the wall of tiles used in the game

data:
	mTiles - list of tiles in the wall
	mDeadWall - the dead wall is separated and stored here
	
	
methods:
	constructors:
	no-arg - builds and initializes the wall

	mutators:
	dealHands - deals starting hands for each player
	takeTile - removes a tile from the beginning of the wall and returns it
	takeTileFromDeadWall - removes a tile from the end of the dead wall and returns it
 	
 	accessors:
	isEmpty - returns true if the wall is empty (has no tiles left)
	getNumTilesLeftInWall - returns the number of tiles left in the wall (not including dead wall)
	getDoraIndicators - returns a list of dora indicators from the dead wall (as a list of tiles)
	
	other:
	toString
	
	private:
	__initialize - builds, initializes, and shuffles the wall
	__makeDeadWall - make the dead wall out of the wall's last 14 tiles
*/
public class Wall {
	

	public static final int MAX_SIZE_WALL = 136;
	public static final int FIRST_TILE_IN_WALL = 0;
	
	public static final boolean DEGBUG_SHUFFLE_WALL = Table.DEBUG_LOAD_DEBUG_WALL;
	
	
	
	private TileList mTiles;
	private DeadWall mDeadWall;
	
	
	
	
	public Wall(){
		
		mTiles = new TileList(MAX_SIZE_WALL);
		
		//fill and shuffle the wall
		__initialize();
		
		//split off the dead wall
		__makeDeadWall();
	}
	
	
	
	
	/*
	method: dealHands
	deals a starting hand for each player
	
	East takes 4, South takes 4, West takes 4, North takes 4
    East takes 4, South takes 4, West takes 4, North takes 4
    East takes 4, South takes 4, West takes 4, North takes 4
    East takes 2, South takes 1, West takes 1, North takes 1
	*/
	public void dealHands(Player p1, Player p2, Player p3, Player p4)
	{
		TileList tilesE = new TileList(Hand.MAX_HAND_SIZE);
		TileList tilesS = new TileList(Hand.MAX_HAND_SIZE - 1);
		TileList tilesW = new TileList(Hand.MAX_HAND_SIZE - 1);
		TileList tilesN = new TileList(Hand.MAX_HAND_SIZE - 1);
		
		int i, j;
		//each player takes 4, 3 times
		for (i = 0; i < 3; i++)
		{
			//east takes 4
			for (j = 0; j < 4; j++){
				tilesE.add(mTiles.getFirst());
				mTiles.removeFirst();
			}
			//south takes 4
			for (j = 0; j < 4; j++){
				tilesS.add(mTiles.getFirst());
				mTiles.removeFirst();
			}
			//west takes 4
			for (j = 0; j < 4; j++){
				tilesW.add(mTiles.getFirst());
				mTiles.removeFirst();
			}
			//north takes 4
			for (j = 0; j < 4; j++){
				tilesN.add(mTiles.getFirst());
				mTiles.removeFirst();
			}
		}
		
		
		//east takes 2
		for (j = 0; j < 2; j++){
			tilesE.add(mTiles.getFirst());
			mTiles.removeFirst();
		}
		
		//south takes 1
		tilesS.add(mTiles.getFirst());
		mTiles.removeFirst();
		
		//west takes 1
		tilesW.add(mTiles.getFirst());
		mTiles.removeFirst();
		
		//north takes 1
		tilesN.add(mTiles.getFirst());
		mTiles.removeFirst();
		
		
		//add the tiles to the hands
		for(Tile t: tilesE)
			p1.addTileToHand(t);
		for(Tile t: tilesS)
			p2.addTileToHand(t);
		for(Tile t: tilesW)
			p3.addTileToHand(t);
		for(Tile t: tilesN)
			p4.addTileToHand(t);
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
	private method: __makeDeadWall
	removes the last 14 tiles from the wall, and creates the dead wall with them
	*/
	private void __makeDeadWall()
	{
		TileList deadWallTiles = new TileList(DeadWall.MAX_SIZE_DEAD_WALL);
		int startingPos = MAX_SIZE_WALL - DeadWall.MAX_SIZE_DEAD_WALL;

		//remove the last 14 tiles in wall, create the dead wall with them
		int i;
		for (i = 0; i < DeadWall.MAX_SIZE_DEAD_WALL; i++){
			//add a tile to the dead wall
			deadWallTiles.add(mTiles.get(startingPos));
			
			//remove that tile from the wall
			mTiles.remove(startingPos);
		}
		
		//initialize the dead wall with the tiles
		mDeadWall = new DeadWall(deadWallTiles);
	}
	
	
	
	
	
	//returns the dora indicators, as a list of Tiles
	//if getUraDora is true, will also return ura dora indicators
	public TileList getDoraIndicators(boolean getUraDora){
		return mDeadWall.getDoraIndicators(getUraDora);
	}
	//Overloaded with no args, gets just normal dora (no ura)
	public TileList getDoraIndicators(){
		return getDoraIndicators(false);
	}
	
	
	
	
	
	
	/*
	method: takeTile
	removes a tile from the beginning of the wall and returns it
	returns the tile, or returns null if the wall was empty
	
	draw the first tile from the wall
	remove that tile from the wall
	return the tile
	*/
	public Tile takeTile(){
		
		//return null if the wall is empty
		if (isEmpty())
			return null;
		
		//draw the first tile from the wall
		Tile drawnTile = mTiles.get(FIRST_TILE_IN_WALL);
		
		//remove that tile from the wall
		mTiles.remove(FIRST_TILE_IN_WALL);
		
		return drawnTile;
	}
	
	//removes a tile from the end of the dead wall and returns it
	public Tile takeTileFromDeadWall(){
		return mDeadWall.takeTile();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns true if the wall is empty (has no tiles left)
	public boolean isEmpty(){
		return (mTiles.size() == 0);
	}
	
	//returns the number of tiles left in the wall (not including dead wall)
	public int getNumTilesLeftInWall(){
		return mTiles.size();
	}
	
	
	
	
	

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
	public String toString()
	{
		
		int i, j;
		String wallString = "";
		
		final int TILES_PER_LINE = 16;
		for (i = 0; i < (mTiles.size() / TILES_PER_LINE) + 1; i++)
		{
			for (j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < mTiles.size()); j++)
			{
				wallString += mTiles.get(TILES_PER_LINE*i + j).toString() + " ";
			}
			if (TILES_PER_LINE*i < mTiles.size())
				wallString += "\n";
		}
		
		
		String dWallString = "DeadWall: " + mDeadWall.getSize() + "\n" + mDeadWall.toString();
		
		return ("Wall: " + getNumTilesLeftInWall() + "\n" + wallString + "\n\n" + dWallString);
	}
	
	
	
}
