package majava.tiles;

import majava.enums.Wind;


//represents a single game tile (”v), has information for who originally owned the tile
public class GameTile implements Cloneable, TileInterface {
	
	private final Janpai baseTile;
	private final Wind originalOwnerWind;
	
//	private final int gameTileID;
	
	
	public GameTile(Janpai tilebase, Wind owner){
		baseTile = tilebase;
		originalOwnerWind = owner;
	}
	public GameTile(Janpai tilebase){
		this(tilebase, Wind.UNKNOWN);
	}
	public GameTile(int id, boolean wantRedDora){this(Janpai.retrieveTile(id, wantRedDora));}
	public GameTile(int id){this(Janpai.retrieveTile(id));}
	public GameTile(String suitfaceString){this(Janpai.valueOf(suitfaceString));}
	
	//copy constructor
	public GameTile(GameTile other){
		baseTile = other.baseTile;
		originalOwnerWind = other.originalOwnerWind;
	}
	public GameTile clone(){return new GameTile(this);}
	
	//Owner methods (the player who drew the tile from the wall)
	final public Wind getOrignalOwner(){return originalOwnerWind;}
	final public GameTile withOwnerWind(Wind owner){return new GameTile(baseTile, owner);}
	
	
	
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
