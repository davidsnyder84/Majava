package majava.player.brains;

import java.util.List;
import java.util.Random;

import majava.player.Player;



public abstract class RobotBrain extends PlayerBrain {
	
	
	public RobotBrain(Player p) {
		super(p);
	}
	
	
	protected int pickRandomlyFrom(List<Integer> choices){return choices.get((new Random()).nextInt(choices.size()));}
	
	@Override
	public final boolean isHuman(){return false;}
	
	
	@Override
	public String toString(){return "RobotBrain";}
}
