package majava.tiles;



//represents a tile in a pond, has extra information (riichi tile, tile that has been called, etc)
public class PondTile extends GameTile {
	
	private final boolean flagIsRiichiTile;
	private final boolean flagWasCalled;
	
	
	private PondTile(GameTile tile, boolean isRiichi, boolean isCalled){
		super(tile);
		flagIsRiichiTile = isRiichi;
		flagWasCalled = isCalled;
	}
	public PondTile(GameTile tile){
		this(tile, false, false);
	}
	public PondTile clone(){return new PondTile(this, flagIsRiichiTile, flagWasCalled);}
	
	
	public boolean isRiichiTile(){return flagIsRiichiTile;}
	public boolean wasCalled(){return flagWasCalled;}
	
	
	//mutators
	public PondTile withRiichi(){return new PondTile(this, true, flagWasCalled);}
	public PondTile withCalled(){return new PondTile(this, flagIsRiichiTile, true);}
	
}
