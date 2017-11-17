package majava.tiles;

import majava.enums.Wind;


//represents a single game tile (”v), has information for red dora and for who originally owned the tile
public class GameTile implements Cloneable, TileInterface {
	private static final String FACE_FOR_RED_DORA = "%";
	
	private final Janpai baseTile;
	
	private final boolean isRed;
	private Wind originalOwnerWind;
	
	

	public GameTile(Janpai tilebase, boolean wantRedDora){
		baseTile = tilebase;
		originalOwnerWind = Wind.UNKNOWN;
		isRed = wantRedDora && (baseTile.getFace() == '5');
	}
	public GameTile(Janpai tilebase){this(tilebase, false);}
	public GameTile(int id, boolean wantRedDora){this(Janpai.retrieveTile(id), wantRedDora);}
	public GameTile(int id){this(id, false);}
	public GameTile(String suitfaceString){this(Janpai.valueOf(suitfaceString), false);}
	
	//copy constructor
	public GameTile(GameTile other){
		baseTile = other.baseTile;
		originalOwnerWind = other.originalOwnerWind;
		isRed = other.isRed;
	}
	public GameTile clone(){return new GameTile(this);}
	
	
	//Owner methods (the player who drew the tile from the wall)
	final public Wind getOrignalOwner(){return originalOwnerWind;}
	final public void setOwner(Wind owner){originalOwnerWind = owner;}
	
	//dora method
	final public boolean isRedDora(){return isRed;}
	
	
	
	
	//------------Tile methods
	final public int getId(){return baseTile.getId();}
	final public char getSuit(){return baseTile.getSuit();}
	final public char getFace(){return baseTile.getFace();}
	
	final public boolean isYaochuu(){return baseTile.isYaochuu();}
	final public boolean isHonor(){return baseTile.isHonor();}
	final public boolean isTerminal(){return baseTile.isTerminal();}

	final public Janpai getTileBase(){return baseTile;}
	
	final public GameTile nextTile(){return new GameTile(baseTile.nextTile());}
	

	//returns true if it's possible for Tile T to finish a Chi of the given type
	final public boolean canCompleteChiL(){return baseTile.canCompleteChiL();}
	final public boolean canCompleteChiM(){return baseTile.canCompleteChiM(); }
	final public boolean canCompleteChiH(){return baseTile.canCompleteChiH();}
	
	
	@Override
	final public int compareTo(TileInterface other){
		if (getId() != other.getId())
			return (getId() - other.getId());
		
		if (getFace() == '5')
			if (isRedDora() && !other.isRedDora()) return 1;
			else return -1;
		
		return 0;
	}
	@Override
	final public boolean equals(Object other){
		if (other == null || !(other instanceof TileInterface)) return false;
		return getId() == ((TileInterface)other).getId();
	}
	
	
	@Override
	public String toString(){
		if (isRedDora()) return redDoraToString();
		return baseTile.toString();
	}
	private String redDoraToString(){return getSuit() + FACE_FOR_RED_DORA;}
}
