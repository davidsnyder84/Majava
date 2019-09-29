package majava.player.brains;

import java.util.List;
import java.util.Random;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;


public class SimpleRobot extends RobotBrain {
	
	//used to dictate how the com chooses its discards
	private static enum DiscardBehavior{DISCARD_LAST, DISCARD_FIRST, DISCARD_RANDOM}
	private static final DiscardBehavior DEFAULT_DISCARD_BEHAVIOR = DiscardBehavior.DISCARD_LAST;
	
	
	//behavior
	private boolean likesToMakeCalls;
	private boolean likesToMakeTurnActions;
	private DiscardBehavior myDiscardBehavior;
	
	
	public SimpleRobot(boolean willMakeCalls, boolean willMakeTurnActions){
		likesToMakeCalls = willMakeCalls;
		likesToMakeTurnActions = willMakeTurnActions;
		myDiscardBehavior = DEFAULT_DISCARD_BEHAVIOR;
	}
	public SimpleRobot(){this(true, true);}
	
	
	
	
	
	
	
	
	
	

	
	@Override
	protected TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions){
		if (likesToMakeTurnActions)
			return biggestTurnAction(listOfPossibleTurnActions);
		return TurnActionType.DISCARD;
	}
	
	@Override
	protected int selectDiscardIndex(Hand hand){
		return preferredDiscardIndex(hand);
	}
	
	private int preferredDiscardIndex(Hand hand){
		switch(myDiscardBehavior){
		case DISCARD_FIRST: return firstTileIndex(hand);
		case DISCARD_LAST: return tsumoTileIndex(hand);
		case DISCARD_RANDOM: return (new Random()).nextInt(hand.size());
		default: return 0;
		}
	}
	
	
	
	
	
	
	@Override
	protected CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions){
		//listOfPossibleReactions is guaranteed to be non-empty (see superclass's template method)	
		//choose the biggest call I possibly can
		if (likesToMakeCalls)
			return biggestReaction(listOfPossibleReactions);
		return CallType.NONE;
	}
	
	
	
	//setters for behavior
	public void setDiscardBehaviorRandom(){myDiscardBehavior = DiscardBehavior.DISCARD_RANDOM;}
	public void setDiscardBehaviorLast(){myDiscardBehavior = DiscardBehavior.DISCARD_LAST;}
	public void setDiscardBehaviorFirst(){myDiscardBehavior = DiscardBehavior.DISCARD_FIRST;}
	
	
	@Override
	public String toString(){return "SimpleRobot";}
	
	
}
