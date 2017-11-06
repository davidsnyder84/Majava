package majava.player;

import majava.tiles.GameTile;

public class HumanBrain extends PlayerBrain {

	public HumanBrain(Player p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void chooseTurnAction() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void reactToDiscard(GameTile tileToReactTo){
	}
	
	public boolean isHuman(){return true;}
	
	@Override
	public String toString(){return "HumanBrain";}
}
