package majava.player;


public abstract class RobotBrain extends PlayerBrain {
	
	
	
	
	
	
	
	public RobotBrain(Player p) {
		super(p);
	}
	
	
	
	
	
	@Override
	public void chooseTurnAction() {
	}
	
	
	
	public final boolean isHuman(){return false;}
	
	
	@Override
	public String toString(){return "RobotBrain";}
}
