package majava.player;

import majava.tiles.GameTile;

public class RobotBrain extends PlayerBrain {

	@Override
	public void chooseTurnAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object reactToDiscard(GameTile T) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public boolean isHuman(){return false;}

	@Override
	public String toString(){return "Robot brain";}
}
