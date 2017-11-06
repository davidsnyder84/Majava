package majava.player;

import java.util.List;

public class SimpleRobot extends RobotBrain {
	
	
	
	private boolean likesToMakeCalls = true;
	
	
	public SimpleRobot(Player p, boolean likesCalls){
		super(p);
		likesToMakeCalls = likesCalls;
	}
	public SimpleRobot(Player p){this(p, true);}
	
	
	
	@Override
	protected CallType chooseReaction(List<CallType> listOfPossibleReactions) {
		//listOfPossibleReactions is guaranteed to be non-empty (see superclass's template method)
		
		//choose the biggest call I possibly can
		if (likesToMakeCalls)
			return listOfPossibleReactions.get(listOfPossibleReactions.size()-1);
		else
			return CallType.NONE;
	}
	
	
	@Override
	public String toString(){return "SimpleRobot";}
	
	
	
	
}
