package majava.tiles;

import majava.enums.Wind;


//represents a single game tile (”v), has information for who originally owned the tile
public class GameTile implements Cloneable, TileInterface {
	public static final GameTile DUMMY_TILE = new GameTile(Janpai.DUMMY_TILE);
	private static final Wind DEFAULT_OWNER_WIND = Wind.UNKNOWN;
	private static final int DEFAULT_WALL_ID = 9999;
	
	
	private final Janpai baseTile;
	
	private final Wind originalOwnerWind;
	private final int wallID;
	
	
	
	public GameTile(Janpai tilebase, Wind owner, int wallid){
		baseTile = tilebase;
		
		originalOwnerWind = owner;
		wallID = wallid;
	}
	public GameTile(Janpai tilebase, Wind owner){this(tilebase, owner, DEFAULT_WALL_ID);}
	public GameTile(Janpai tilebase, int wallid){this(tilebase, DEFAULT_OWNER_WIND, wallid);}
	
	public GameTile(Janpai tilebase){this(tilebase, DEFAULT_OWNER_WIND);}
	public GameTile(int tileID, boolean wantRedDora){this(Janpai.retrieveTile(tileID, wantRedDora));}
	public GameTile(int tileID){this(Janpai.retrieveTile(tileID));}
	public GameTile(String suitfaceString){this(Janpai.valueOf(suitfaceString));}
	
	//copy constructor
	public GameTile(GameTile other){
		this(other.baseTile, other.originalOwnerWind, other.wallID);
	}
	public GameTile clone(){return new GameTile(this);}
	
	//builders
	final public GameTile withOwnerWind(Wind owner){return new GameTile(baseTile, owner, wallID);}
	final public GameTile withWallID(int wallid){return new GameTile(baseTile, originalOwnerWind, wallid);}
	
	
	
	
	//the wind of the player who originally drew the tile from the wall (example: first M1=1, second M1=2, etc)
	final public Wind getOrignalOwner(){return originalOwnerWind;}
	
	
	//uniquely identifies each of the 136 tiles from a round's wall (is -> equals, but equals -/> is)
	//a game tile "is" another game tile if it has the same wallID
	final public boolean is(GameTile other){return (this.wallID == other.wallID);}
	final public boolean wallIDhasBeenSet(){return wallID != DEFAULT_WALL_ID;} //this isn't needed
	final public int getWallID(){return wallID;} //this isn't needed
	
	
	
	//------------Tile methods
	final public int getId(){return baseTile.getId();}
	final public char getSuit(){return baseTile.getSuit();}
	final public char getFace(){return baseTile.getFace();}
	final public boolean isRedDora(){return baseTile.isRedDora();}
	
	final public boolean isTanyao(){return !isYaochuu();}
	final public boolean isYaochuu(){return baseTile.isYaochuu();}
	final public boolean isHonor(){return baseTile.isHonor();}
	final public boolean isWind(){return baseTile.isWind();}
	final public boolean isDragon(){return baseTile.isDragon();}
	final public boolean isTerminal(){return baseTile.isTerminal();}

	final public Janpai getTileBase(){return baseTile;}
	
	final public GameTile nextTile(){return new GameTile(baseTile.nextTile());}

	//returns true if it's possible for Tile T to finish a Chi of the given type
	final public boolean canCompleteChiL(){return baseTile.canCompleteChiL();}
	final public boolean canCompleteChiM(){return baseTile.canCompleteChiM(); }
	final public boolean canCompleteChiH(){return baseTile.canCompleteChiH();}
	
	@Override
	final public int compareTo(TileInterface other){return baseTile.compareTo(other.getTileBase());}
	@Override
	final public boolean equals(Object other){return baseTile.equals(other);}
	@Override
	public String toString(){return baseTile.toString();}
}
