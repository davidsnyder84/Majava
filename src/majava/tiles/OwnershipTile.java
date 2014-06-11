package majava.tiles;

import majava.enums.Wind;

public class OwnershipTile extends GameTileOnly{
	

	private Wind mOriginalOwner = Wind.UNKNOWN;
	
	
	public OwnershipTile(ImmutableTile tilebase){super(tilebase);}
	public OwnershipTile(int id){super(id);}
//	public OwnershipTile(int id, boolean isRed){super(id, isRed);}
//	public OwnershipTile(String suitfaceString){super(suitfaceString);}
	
	
	

	
	//Owner methods
	final public Wind getOrignalOwner(){return mOriginalOwner;}
	//sets the original owner attribute of the tile (the player who drew the tile from the wall)
	final public void setOwner(Wind owner){mOriginalOwner = owner;}
	
}
