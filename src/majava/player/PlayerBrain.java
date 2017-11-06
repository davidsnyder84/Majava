package majava.player;

import majava.tiles.GameTile;

public abstract class PlayerBrain {
	
	
	
	
	public abstract void chooseTurnAction();
	
	
	public abstract Object reactToDiscard(GameTile T);
	
	
	
	
	public abstract boolean isHuman(); 
	
	@Override
	public String toString(){return "UnknownBrain";}
	
}
