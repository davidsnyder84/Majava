package majava.tiles;

import majava.enums.Wind;


//represents a single game tile (”v), has information for who originally owned the tile
public class GameTile implements Cloneable, TileInterface {
	
	private final ImmutableTile baseTile;
	private Wind originalOwnerWind;
	
	

	public GameTile(ImmutableTile tilebase){
		baseTile = tilebase;
		originalOwnerWind = Wind.UNKNOWN;
	}
	public GameTile(TileInterface tileInterface){this(tileInterface.getTileBase());}
	public GameTile(int id, boolean isRed){this(ImmutableTile.retrieveTileRed(id));}
	public GameTile(int id){this(ImmutableTile.retrieveTile(id));}
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
	final public int getId(){return baseTile.getId();}
	final public char getSuit(){return baseTile.getSuit();}
	final public char getFace(){return baseTile.getFace();}
	final public boolean isRedDora(){return baseTile.isRedDora();}
	
	final public boolean isYaochuu(){return baseTile.isYaochuu();}
	final public boolean isHonor(){return baseTile.isHonor();}
	final public boolean isTerminal(){return baseTile.isTerminal();}

	final public ImmutableTile getTileBase(){return baseTile;}
	
	final public GameTile nextTile(){return new GameTile(baseTile.nextTile());}
	
	
	@Override
	final public int compareTo(TileInterface other){return baseTile.compareTo(other.getTileBase());}
	@Override
	final public boolean equals(Object other){return baseTile.equals(other);}
	@Override
	public String toString(){return baseTile.toString();}
}
