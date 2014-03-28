import java.util.ArrayList;




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
	
	public static final int DEFAULT_ID = 0;
	public static final char OWNER_NONE = 'N';
	public static final String CHAR_FOR_RED_DORA = "%";

	public static final char SUIT_MAN = 'M';
	public static final char SUIT_PIN = 'C';
	public static final char SUIT_SOU = 'B';
	public static final char SUIT_WIND = 'W';
	public static final char SUIT_DRAGON = 'D';

	public static final int ID_FIRST_HONOR_TILE = 28;
	public static final int NUMBER_OF_YAOCHUU_TILES = 13;
	
	private static final String STR_REPS_BY_ID = "M1M2M3M4M5M6M7M8M9P1P2P3P4P5P6P7P8P9S1S2S3S4S5S6S7S8S9WEWSWWWNDWDGDR";
	private static final TileList LIST_OF_YAOCHUU_TILES = new TileList(1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34);
	
	
	
	//instance variables
	
	private int mID;
	private char mSuit;
	private char mFace;
	
	private char mOriginalOwner;
	private boolean mRedDora;
	
	
	
	//2-arg Constructor, takes tile ID and boolean value for if it's a Red Dora 
	public Tile(int id, boolean isRed){
		
		mID = id;
		mSuit = repr_suitOfId(id);
		mFace = repr_faceOfId(id);

		mOriginalOwner = OWNER_NONE;
		mRedDora = isRed;
	}
	//1-arg Constructor, takes tile ID
	public Tile(int id){
		this(id, false);
	}
	//2-arg, takes string representation, and boolean red dora flag
	public Tile(String suitfaceString, boolean isRed){
		this(repr_idOfStringRepr(suitfaceString), isRed);
	}
	//1-arg, takes string representation of tile
	public Tile(String suitfaceString){
		this(repr_idOfStringRepr(suitfaceString.toUpperCase()));
	}
	//2-arg, takes id and sets an onwer's seat wind
	public Tile(int id, char ownerWind){
		this(id);
		mOriginalOwner = ownerWind;
	}
	
	
	public Tile(Tile other){
		mID = other.mID;
		mSuit = other.mSuit;
		mFace = other.mFace;
		mOriginalOwner = other.mOriginalOwner;
		mRedDora = other.mRedDora;
		
//		mMeldTypeStack = new MahStack<MeldType>(other.mMeldTypeStack);
	}
	
	/*
	public boolean mstackPush(MeldType meldType){return mMeldTypeStack.push(meldType);}
	public MeldType mstackPop(){return mMeldTypeStack.pop();}
	public MeldType mstackTop(){return mMeldTypeStack.top();}
	public boolean mstackIsEmpty(){return mMeldTypeStack.isEmpty();}
	
	//returns a list of the partner IDs for the top meldType on the stack
	public ArrayList<Integer> mstackTopParterIDs(){
		
		ArrayList<Integer> partnerIDs = new ArrayList<Integer>(2);
		
		switch(mMeldTypeStack.top()){
		case CHI_L: partnerIDs.add(mID + 1); partnerIDs.add(mID + 2); break;
		case CHI_M: partnerIDs.add(mID - 1); partnerIDs.add(mID + 1); break;
		case CHI_H: partnerIDs.add(mID - 2); partnerIDs.add(mID - 1); break;
		case PON: partnerIDs.add(mID); partnerIDs.add(mID); break;
		case KAN: partnerIDs.add(mID); partnerIDs.add(mID); partnerIDs.add(mID); break;
		case PAIR: partnerIDs.add(mID); break;
		default: break;
		}
		return partnerIDs;
	}
	
	*/
	
	
	//accessors
	public int getId(){return mID;}
	public char getSuit(){return mSuit;}
	public char getFace(){return mFace;}
	public boolean isRedDora(){return mRedDora;}
	public char getOrignalOwner(){return mOriginalOwner;}
	
	
	
	
	//sets the original owner attribute of the tile (the player who drew the tile from the wall)
	public void setOwner(char owner){
		mOriginalOwner = owner;
	}
	
	//makes the tile a red dora tile
	public void setRedDora(){if (mFace == '5') mRedDora = true;}
	
	
	
	
	
	
	
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
	public Tile nextTile(){
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
	public boolean isYaochuu(){return (isHonor() || isTerminal());}
	//returns true if the tile is an honor tile, false if not
	public boolean isHonor(){return (mID >= ID_FIRST_HONOR_TILE);}
	//returns true if the tile is a terminal tile, false if not
	public boolean isTerminal(){return (mFace == '1' || mFace == '9');}

	
	
	
	
	

	//compares the IDs of two tiles
	//if they are both 5's, and one is a red dora, the red dora will "come after" the non-red tile
	@Override
	public int compareTo(Tile other){
		
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
	public boolean equals(Object other){
		if (other == null || (other instanceof Tile) == false)
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
	
	
	
	
	
	
	
	
	
	
	
	//returns all of the tile's info as a string (for debug use)
	public String toStringAllInfo(){
		
		String tileString = "";
		tileString += "Tile: " + mSuit + mFace + '\n';
		tileString += "\tID: " + mID + ", Suit: " + mSuit + ", Face: " + mFace + '\n';
		tileString += "\tOriginal Owner: " + mOriginalOwner + '\n';
		tileString += "\tRed Dora?: " + mRedDora + ", Yaochuu?: " + isYaochuu() + '\n';
		tileString += "\tNext tile: " + nextTile() + '\n';

		tileString += "\tHot tiles: ";
		ArrayList<Integer> hotTiles = findHotTiles();
		for (Integer i: hotTiles)
			tileString += repr_stringReprOfId(i) + ", ";
		
		tileString = tileString.substring(0, tileString.length() - 2);
		return tileString;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getImageFilename(){
		
		String filename = "img\\tiles\\" + mID;
		if (mRedDora) filename += "r";
		filename += ".gif";
		
		return filename;
	}
	
	
	
	
	//takes a tile ID, returns the string representation of that ID
	public static String repr_stringReprOfId(int id){
		return STR_REPS_BY_ID.substring(2*(id-1), 2*(id-1) + 2);
	}
	//takes a tile ID, returns the suit or face of that ID
	public static char repr_suitOfId(int id){return STR_REPS_BY_ID.charAt(2*(id-1));}
	public static char repr_faceOfId(int id){return STR_REPS_BY_ID.charAt(2*(id-1) + 1);}
	
	
	//takes a string representation of a tile, returns the ID that tile would have
	public static int repr_idOfStringRepr(String strRep){
		int id = STR_REPS_BY_ID.indexOf(strRep);
		if (id < 0) return -1;
		return (id / 2 + 1);
	}
	//overloaded to accept 2 characters instead of a string
	public static int repr_idOfStringRepr(char suit, char face){return repr_idOfStringRepr(Character.toString(suit) + Character.toString(face));}
	
	//returns a list of all Yaochuu tiles (terminal or honor)
	public static TileList listOfYaochuuTiles(){return (new TileList(LIST_OF_YAOCHUU_TILES));}
	
	
	

}
