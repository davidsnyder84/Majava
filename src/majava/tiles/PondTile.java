package majava.tiles;



//represents a tile in a pond, has extra information (riichi tile, tile that has been called, etc)
public class PondTile extends GameTile {
	
	private boolean flagIsRiichiTile;
	private boolean flagWasCalled;
	
	
	public PondTile(GameTile other){
		super(other);
		
		flagIsRiichiTile = false;
		flagWasCalled = false;
	}
	public PondTile clone(){
		PondTile other = new PondTile(this);
		other.flagIsRiichiTile = flagIsRiichiTile;
		other.flagWasCalled = flagWasCalled;
		return other;
	}
	
	
	//getters
	public boolean isRiichiTile(){return flagIsRiichiTile;}
	public boolean wasCalled(){return flagWasCalled;}
	
	//setters
	public void setRiichiTile(){flagIsRiichiTile = true;}
	public void setCalled(){flagWasCalled = true;}
	
}
