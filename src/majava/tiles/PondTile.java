package majava.tiles;

import majava.enums.Wind;



//represents a tile in a pond, has extra information (riichi tile, tile that has been called, etc)
public class PondTile extends GameTile {
	public static final PondTile DUMMY_TILE = new PondTile(GameTile.DUMMY_TILE);
	
	private final boolean flagIsRiichiTile;
	private final Wind caller;
	
	
	private PondTile(GameTile tile, boolean isRiichi, Wind callerWind){
		super(tile);
		flagIsRiichiTile = isRiichi;
		caller = callerWind;
	}
	public PondTile(GameTile tile){
		this(tile, false, Wind.UNKNOWN);
	}
	public PondTile clone(){return new PondTile(this, flagIsRiichiTile, caller);}
	
	
	public boolean isRiichiTile(){return flagIsRiichiTile;}
	
	public boolean wasCalled(){return (caller != Wind.UNKNOWN);}
	public Wind getCaller(){return caller;}
	
	
	
	//mutators
	public PondTile withRiichi(){return new PondTile(this, true, caller);}
	public PondTile withCalled(Wind callerWind){return new PondTile(this, flagIsRiichiTile, callerWind);}
	
	public PondTile setCalled(Wind callerWind){return this.withCalled(callerWind);}
}
