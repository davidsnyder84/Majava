package majava.player;

import java.util.List;


public class SimpleRobot extends RobotBrain {
	
	//used to dictate how the com chooses its discards
	private static enum DiscardBehavior{DISCARD_LAST, DISCARD_FIRST, DISCARD_RANDOM}
	private static final DiscardBehavior DEFAULT_DISCARD_BEHAVIOR = DiscardBehavior.DISCARD_LAST;
	
	
	//behavior
	private boolean likesToMakeCalls;
	private boolean likesToMakeTurnActions;
	private DiscardBehavior myDiscardBehavior;
	
	
	public SimpleRobot(Player p, boolean willMakeCalls, boolean willMakeTurnActions){
		super(p);
		likesToMakeCalls = willMakeCalls;
		likesToMakeTurnActions = willMakeTurnActions;
		myDiscardBehavior = DEFAULT_DISCARD_BEHAVIOR;
	}
	public SimpleRobot(Player p){this(p, true, true);}
	
	
	
	
	
	
	
	
	
	

	
	@Override
	protected ActionType selectTurnAction(List<ActionType> listOfPossibleTurnActions){
		if (likesToMakeTurnActions && !listOfPossibleTurnActions.isEmpty())
			return listOfPossibleTurnActions.get(listOfPossibleTurnActions.size()-1);
		else
			return ActionType.DISCARD;
	}
	
	@Override
	protected int selectDiscardIndex(){
		return preferredDiscardIndex();
	}
	
	
	
	
	@Override
	protected CallType chooseReaction(List<CallType> listOfPossibleReactions){
		//listOfPossibleReactions is guaranteed to be non-empty (see superclass's template method)
		
		//choose the biggest call I possibly can
		if (likesToMakeCalls)
			return listOfPossibleReactions.get(listOfPossibleReactions.size()-1);
		
		return CallType.NONE;
	}
	
	
	
	
	
	
	
	private int preferredDiscardIndex(){
		switch(myDiscardBehavior){
		case DISCARD_FIRST: return 0;
		case DISCARD_LAST: return player.handSize()-1;
		case DISCARD_RANDOM: return 0;	//implement random
		default: return 0;
		}
	}
	
	
	//setters for behavior
	public void setDiscardBehaviorRandom(){myDiscardBehavior = DiscardBehavior.DISCARD_RANDOM;}
	public void setDiscardBehaviorLast(){myDiscardBehavior = DiscardBehavior.DISCARD_LAST;}
	public void setDiscardBehaviorFirst(){myDiscardBehavior = DiscardBehavior.DISCARD_FIRST;}
	
	
	@Override
	public String toString(){return "SimpleRobot";}
	
	
}
