package majava.tiles;


public class PondTile extends Tile{
	
	
	private boolean mIsRiichiTile;
	private boolean mWasCalled;
	
	
	
	
//	public PondTile(int id) {super(id);}
	public PondTile(Tile other){
		super(other);
		mIsRiichiTile = false;
		mWasCalled = false;
	}
	

	
	public boolean isRiichiTile(){return mIsRiichiTile;}
	public boolean wasCalled(){return mWasCalled;}
	
	public void setRiichiTile(){mIsRiichiTile = true;}
	public void setCalled(){mWasCalled = true;}

}
