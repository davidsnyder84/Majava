package majava.tiles;


/*
Class: PondTile
represents a tile in a pond

data:
	mIsRiichiTile - is true if the tile was used to riichi
	mWasCalled - is true if the tile was called by another player
	
methods:
	
	constructors:
	Requires tile ID, or another tile (copy constructor) 
	
	public:
		mutators:
	 	setRiichiTile, setCalled
	 	
	 	accessors:
		isRiichiTile, wasCalled
*/
public class PondTile extends Tile {
	
	
	private boolean mIsRiichiTile;
	private boolean mWasCalled;
	
	
	
	
	public PondTile(Tile other){
		super(other);
		mIsRiichiTile = false;
		mWasCalled = false;
	}
	public PondTile(int id) {this (new Tile(id));}
	
	
	
	
	public boolean isRiichiTile(){return mIsRiichiTile;}
	public boolean wasCalled(){return mWasCalled;}
	
	public void setRiichiTile(){mIsRiichiTile = true;}
	public void setCalled(){mWasCalled = true;}

}
