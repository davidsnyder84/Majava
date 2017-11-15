package majava.player.brains;

import majava.player.Player;



public abstract class RobotBrain extends PlayerBrain {
	
	
	public RobotBrain(Player p) {
		super(p);
	}
	
	
	
	@Override
	public final boolean isHuman(){return false;}
	
	
	@Override
	public String toString(){return "RobotBrain";}
}
