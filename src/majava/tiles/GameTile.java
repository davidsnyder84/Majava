package majava.tiles;

import majava.enums.Wind;




/*
Class: GameTile
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
		nextGameTile - returns what the dora would be if this tile was a dora indicator
		
		other:
		equals - returns true if both tiles have the same ID, false otherwise
		toString - returns string representation of a tile's suit/face
*/
public class GameTile implements Cloneable, TileInterface {
	
	private final ImmutableTile baseTile;
	private Wind originalOwnerWind;
	
	

	public GameTile(ImmutableTile tilebase){
		baseTile = tilebase;
		originalOwnerWind = Wind.UNKNOWN;
	}
	public GameTile(TileInterface tileInterface){this(tileInterface.getTileBase());}
	//Takes ID of tile (and optional red dora flag)
	public GameTile(int id, boolean isRed){this(ImmutableTile.retrieveTileRed(id));}
	//1-arg Constructor, takes tile ID
	public GameTile(int id){this(ImmutableTile.retrieveTile(id));}
	//Takes string representation of tile
	public GameTile(String suitfaceString){this(ImmutableTile.retrieveTile(suitfaceString));}
	
	
	
	
	
	//copy constructor
	public GameTile(GameTile other){
		baseTile = other.baseTile;
		originalOwnerWind = other.originalOwnerWind;
	}
	public GameTile clone(){return new GameTile(this);}
	
	
	
	
	
	
	
	
	
	
	
	
	//Owner methods (the player who drew the tile from the wall)
	final public Wind getOrignalOwner(){return originalOwnerWind;}
	final public void setOwner(Wind owner){originalOwnerWind = owner;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//------------Tile methods
	
	
	//accessors for tile
	final public int getId(){return baseTile.getId();}
	final public char getSuit(){return baseTile.getSuit();}
	final public char getFace(){return baseTile.getFace();}
	final public boolean isRedDora(){return baseTile.isRedDora();}
	
	final public boolean isYaochuu(){return baseTile.isYaochuu();}
	final public boolean isHonor(){return baseTile.isHonor();}
	final public boolean isTerminal(){return baseTile.isTerminal();}

	final public ImmutableTile getTileBase(){return baseTile;}
	
	
	
	//returns the tile that follows this one (used to find a dora from a dora indicator)
	final public GameTile nextTile(){return new GameTile(baseTile.nextTile());}
	
	
	
	
	@Override
	final public int compareTo(TileInterface other){return baseTile.compareTo(other.getTileBase());}
	
	//returns true if the tiles have the same ID
	@Override
	final public boolean equals(Object other){return baseTile.equals(other);}
	
	@Override
	public String toString(){return baseTile.toString();}
	
}
