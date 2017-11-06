package majava.player;

import java.util.List;

import majava.player.PlayerBrain.ActionType;


public abstract class RobotBrain extends PlayerBrain {
	
	
	
	
	
	
	
	public RobotBrain(Player p) {
		super(p);
	}
	
	
	
	
	
	
	public final boolean isHuman(){return false;}
	
	
	@Override
	public String toString(){return "RobotBrain";}
}
