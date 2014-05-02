package majava.tiles;

import java.util.ArrayList;

import majava.TileList;
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
	stringRepr - string representation of the suit and face. stored for convenience.
	
methods:
	
	constructors:
	2-arg, takes tile ID and boolean flag for Red Dora
	1-arg, takes tile ID, sets red dora flag to false
	2-arg, takes string representation and boolean flag for Red Dora
	1-arg, takes string representation, sets red dora flag to false
	2-arg, takes char values of suit and face
	
	mutators:
 	setOwner - sets the original owner attribute of the tile (the player who drew the tile from the wall)
	setRedDora - marks the tile as a red dora (only works for a 5 tile)
 	
 	accessors:
	getId - returns the integer ID of the tile
	getSuit - returns the suit of the tile as a character
	getFace - returns the face of the tile as a character
	isRedDora - returns true if the tile is a red dora 5, false if not
	getOrignalOwner - returns the wind of the original owner of the tile
	isYaochuu - returns true if the tile is either a terminal or an honor, false otherwise
	isHonor - returns true if the tile is an honor tile, false otherwise
	isTerminal - returns true if the tile is a terminal, false otherwise
	
	nextTile - returns what the dora would be if this tile was a dora indicator
	findHotTiles - returns a list of IDs of "hot tiles" (all tiles that could be in a meld with this tile)
	
	
	other:
	compareTo - compares the IDs of two tiles
	equals - returns true if both tiles have the same ID, false otherwise
	toString - returns string representation of a tile's suit/face
	toStringAllInfo - returns all info about a tile as a string (debug use)
	getImageFilename - returns the filename of the image that represents the tile
	
	
	static:
	stringReprOfId - takes an ID, returns the string representation of the suit/face of that ID
	idOfStringRepr - takes a string representation of the suit/face of a tile, returns the ID that tile would have
*/
public class Tile implements Comparable<Tile> {
	
	public static final int NUMBER_OF_DIFFERENT_TILES = 34;
	public static final int NUMBER_OF_YAOCHUU_TILES = 13;
	private static final int ID_FIRST_HONOR_TILE = 28;
	
//	private static final int DEFAULT_ID = 0;
	private static final String CHAR_FOR_RED_DORA = "%";

//	private static final char SUIT_MAN = 'M';
//	private static final char SUIT_PIN = 'C';
//	private static final char SUIT_SOU = 'B';
//	private static final char SUIT_WIND = 'W';
//	private static final char SUIT_DRAGON = 'D';

	
	private static final String STR_REPS_BY_ID = "M1M2M3M4M5M6M7M8M9P1P2P3P4P5P6P7P8P9S1S2S3S4S5S6S7S8S9WEWSWWWNDWDGDR";
	private static final TileList LIST_OF_YAOCHUU_TILES = new TileList(1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34);
	
	
	
	//instance variables
	
	private int mID;
	private char mSuit;
	private char mFace;
	private boolean mRedDora;
	
	private Wind mOriginalOwner;
	
	
	
	//2-arg Constructor, takes tile ID and boolean value for if it's a Red Dora
	//Takes ID of tile (and optional red dora flag)
	public Tile(int id, boolean isRed){
		
		mID = id;
		mSuit = repr_suitOfId(id);
		mFace = repr_faceOfId(id);

		mOriginalOwner = Wind.UNKNOWN;
		mRedDora = false;
		if (isRed) __setRedDora();
	}
	//1-arg Constructor, takes tile ID
	public Tile(int id){this(id, false);}
	
	
	//Takes string representation of tile (and optional red dora flag)
	public Tile(String suitfaceString, boolean isRed){
		this((STR_REPS_BY_ID.indexOf(suitfaceString.toUpperCase()) / 2 + 1), isRed);
	}
	public Tile(String suitfaceString){this(suitfaceString, false);}
	
	
//	//2-arg, takes id and sets an onwer's seat wind
//	public Tile(int id, Wind ownerWind){
//		this(id);
//		mOriginalOwner = ownerWind;
//	}
	public Tile(Tile other){
		mID = other.mID;
		mSuit = other.mSuit;
		mFace = other.mFace;
		mOriginalOwner = other.mOriginalOwner;
		mRedDora = other.mRedDora;
	}
	
//	public Object clone(){return null;} 
	

	//makes the tile a red dora tile
	final private void __setRedDora(){if (mFace == '5') mRedDora = true;}
	
	
	
	
	//accessors
	final public int getId(){return mID;}
	final public char getSuit(){return mSuit;}
	final public char getFace(){return mFace;}
	final public boolean isRedDora(){return mRedDora;}
	final public Wind getOrignalOwner(){return mOriginalOwner;}
	
	
	
	
	//sets the original owner attribute of the tile (the player who drew the tile from the wall)
	public void setOwner(Wind owner){
		mOriginalOwner = owner;
	}
	
	
	
	
	
	
	
	
	/*
	method: findHotTiles
	returns a list of integer IDs of hot tiles, for this tile
	
	add itself to the list (because pon)
	if (not honor suit): add all possible chi partners to the list
	return list
	*/
	public ArrayList<Integer> findHotTiles(){
		
		ArrayList<Integer> hotTileIds = new ArrayList<Integer>(1); 
		
		//a tile is always its own hot tile (pon/kan/pair)
		hotTileIds.add(mID);
		
		//add possible chi partners, if tile is not an honor tile
		if (!isHonor()){
			if (mFace != '1' && mFace != '2') hotTileIds.add(mID - 2);
			if (mFace != '1') hotTileIds.add(mID - 1);
			if (mFace != '9') hotTileIds.add(mID + 1);
			if (mFace != '8' && mFace != '9') hotTileIds.add(mID + 2);
		}
		
		//return list of integer IDs
		return hotTileIds;
	}
	
	
	
	/*
	method: nextTile
	
	returns the tile that follows this one
	used to find a dora from a dora indicator
	*/
	final public Tile nextTile(){
		int nextID = 1;
		
		//determine the ID of the next tile
		if(mFace != '9' && mFace != 'N' && mFace != 'R')
			nextID = mID + 1;
		else
			if (mFace == '9')
				nextID = mID - 8;
			else if (mFace == 'N')
				nextID = mID - 3;
			else if (mFace == 'R')
				nextID = mID - 2;
		
		return new Tile(nextID);
	}
	
	
	//returns true if the tile is a terminal or honor, false otherwise
	final public boolean isYaochuu(){return (isHonor() || isTerminal());}
	//returns true if the tile is an honor tile, false if not
	final public boolean isHonor(){return (mID >= ID_FIRST_HONOR_TILE);}
	//returns true if the tile is a terminal tile, false if not
	final public boolean isTerminal(){return (mFace == '1' || mFace == '9');}

	
	
	
	
	

	//compares the IDs of two tiles
	//if they are both 5's, and one is a red dora, the red dora will "come after" the non-red tile
	@Override
	final public int compareTo(Tile other){
		
		//if the tiles have different id's, return the difference
		if (mID != other.mID)
			return (mID - other.mID);
		
		//at this point, both tiles have the same ID
		//if both 5's, check if one is red dora
		if (mFace == '5' && other.mFace == '5')
			if (mRedDora && !other.mRedDora) return 1;
			else if (!mRedDora && other.mRedDora) return -1;
		
		//if the tiles are not 5's, or if both are red doras, return 0
		return 0;
	}
	
	//returns true if the tiles have the same ID
	@Override
	final public boolean equals(Object other){
		if (other == null || !(other instanceof Tile))
			return false;
		
		return (mID == ((Tile)other).mID);
	}
	
	//string representaiton of tile's suit/face
	@Override
	public String toString(){
		if (mRedDora)
			return (Character.toString(mSuit) + CHAR_FOR_RED_DORA); 
		else
			return repr_stringReprOfId(mID);
	}
	
	
	
	
	
	
	
	//takes a tile ID, returns the string representation of that ID
	private static String repr_stringReprOfId(int id){
		return STR_REPS_BY_ID.substring(2*(id-1), 2*(id-1) + 2);
	}
	//takes a tile ID, returns the suit or face of that ID
	private static char repr_suitOfId(int id){return STR_REPS_BY_ID.charAt(2*(id-1));}
	private static char repr_faceOfId(int id){return STR_REPS_BY_ID.charAt(2*(id-1) + 1);}
	
	
	
	//returns a list of all Yaochuu tiles (terminal or honor)
	public static TileList listOfYaochuuTiles(){return (new TileList(LIST_OF_YAOCHUU_TILES));}
	
	
	

}
