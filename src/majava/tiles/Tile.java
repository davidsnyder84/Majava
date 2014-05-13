package majava.tiles;

import java.util.Arrays;
import majava.Wind;




/*
Class: Tile
represents a single tile

data:
	mID - number ID of the tile, 1 to 34, same ID means same suit/face tile (ie, M2 has ID 2)
	mSuit - man, pin, sou, wind, dragon (M,P,S,W,D)
	mFace - 1-9, ESWN, white/green/red
	mRedDora - if true, indicates that the tile is a Red Dora 5 tile 
	
	mOriginalOwner - holds the wind of the player who originally drew the tile.
	mSuitfaceString - string representation of the suit and face
	
methods:
	
	constructors:
	Requires either integer ID or suit/face string representaiton. Red dora flag is optional.
	
	public:
		mutators:
	 	setOwner - sets the original owner attribute of the tile (the player who drew the tile from the wall)
	 	
	 	accessors:
		getId - returns the integer ID of the tile
		getSuit - returns the suit of the tile as a character
		getFace - returns the face of the tile as a character
		isRedDora - returns true if the tile is a red dora 5, false if not
		isYaochuu - returns true if the tile is either a terminal or an honor, false otherwise
		isHonor - returns true if the tile is an honor tile, false otherwise
		isTerminal - returns true if the tile is a terminal, false otherwise
		getOrignalOwner - returns the wind of the original owner of the tile
		nextTile - returns what the dora would be if this tile was a dora indicator
		
		other:
		equals - returns true if both tiles have the same ID, false otherwise
		toString - returns string representation of a tile's suit/face
*/
public class Tile implements Comparable<Tile> {
	
	private static final int NUMBER_OF_DIFFERENT_TILES = 34;
	private static final int ID_FIRST_HONOR_TILE = 28;
	
	private static final String FACE_FOR_RED_DORA = "%";

	
	private static final String[] STR_REPS = {"O0", 
											  "M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9",
											  "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9",
											  "S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9",
											  "WE", "WS", "WW", "WN",
											  "DW", "DG", "DR"};
	
	
	private int mID;
	private char mSuit;
	private char mFace;
	private String mSuitfaceString;
	
	private boolean mRedDora;
	private Wind mOriginalOwner;
	
	
	
	//Takes ID of tile (and optional red dora flag)
	public Tile(int id, boolean isRed){
		
		if (id < 1 || id > NUMBER_OF_DIFFERENT_TILES) id = 0;
		mID = id;
		
		mSuitfaceString = STR_REPS[mID];
		mSuit = mSuitfaceString.charAt(0);
		mFace = mSuitfaceString.charAt(1);

		mOriginalOwner = Wind.UNKNOWN;
		
		mRedDora = false;
		if (isRed) __setRedDora();
	}
	//1-arg Constructor, takes tile ID
	public Tile(int id){this(id, false);}
	//Takes string representation of tile (and optional red dora flag)
	public Tile(String suitfaceString, boolean isRed){this(Arrays.asList(STR_REPS).indexOf(suitfaceString.toUpperCase()), isRed);}
	public Tile(String suitfaceString){this(suitfaceString, false);}
	
	//copy constructor
	public Tile(Tile other){
		mID = other.mID;
		mSuit = other.mSuit;
		mFace = other.mFace;
		mSuitfaceString = other.mSuitfaceString;
		mOriginalOwner = other.mOriginalOwner;
		mRedDora = other.mRedDora;
	}

	//makes the tile a red dora tile
	private void __setRedDora(){if (mFace == '5'){mRedDora = true; mSuitfaceString = mSuit + FACE_FOR_RED_DORA;}}
	
	
	
	
	//accessors
	final public int getId(){return mID;}
	final public char getSuit(){return mSuit;}
	final public char getFace(){return mFace;}
	final public boolean isRedDora(){return mRedDora;}
	final public Wind getOrignalOwner(){return mOriginalOwner;}
	
	final public boolean isYaochuu(){return (isHonor() || isTerminal());}
	final public boolean isHonor(){return (mID >= ID_FIRST_HONOR_TILE);}
	final public boolean isTerminal(){return (mFace == '1' || mFace == '9');}
	
	
	
	//sets the original owner attribute of the tile (the player who drew the tile from the wall)
	public void setOwner(Wind owner){mOriginalOwner = owner;}
	
	
	
	
	
	/*
	method: nextTile
	returns the tile that follows this one (used to find a dora from a dora indicator)
	*/
	final public Tile nextTile(){
		if (mFace == '9') return new Tile(mID - 8);
		else if (mFace == 'N') return new Tile(mID - 3);
		else if (mFace == 'R') return new Tile(mID - 2);
		else return new Tile(mID + 1);
	}
	

	
	
	
	
	

	//compares the IDs of two tiles
	//if they are both 5's, and one is a red dora, the red dora will "come after" the non-red tile
	@Override
	final public int compareTo(Tile other){
		
		//if the tiles have different id's, return the difference
		if (mID != other.mID) return (mID - other.mID);
		
		//at this point, both tiles have the same ID
		//if both 5's, check if one is red dora
		if (mFace == '5')
			if (mRedDora && !other.mRedDora) return 1;
			else return -1;
		
		//if the tiles are not 5's, or if both or neither are red doras, return 0
		return 0;
	}
	
	//returns true if the tiles have the same ID
	@Override
	final public boolean equals(Object other){
		if (other == null || !(other instanceof Tile)) return false;
		return (mID == ((Tile)other).mID);
	}
	
	//string representaiton of tile's suit/face
	@Override
	public String toString(){return mSuitfaceString;}
	
}
