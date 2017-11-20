package majava.player.brains;

import java.util.List;
import java.util.Random;

import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;



public abstract class RobotBrain extends PlayerBrain {
	protected static final int INDEX_NOT_FOUND = -1;
	
	
	public RobotBrain(Player p) {
		super(p);
	}
	
	@Override
	protected CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions) {
		//win by ron if possible
		if (listOfPossibleReactions.contains(CallType.RON)) return CallType.RON;
		return CallType.NONE;
	}
	@Override
	protected ActionType selectTurnAction(Hand hand, List<ActionType> listOfPossibleTurnActions) {
		//win by tsumo if possible
		if (listOfPossibleTurnActions.contains(ActionType.TSUMO)) return ActionType.TSUMO;
		return ActionType.DISCARD;
	}
	
	protected static int pickRandomlyFrom(List<Integer> choices){return choices.get((new Random()).nextInt(choices.size()));}
	
	
	@Override
	public final boolean isHuman(){return false;}
	
	
	@Override
	public String toString(){return "UnspecifiedRobotBrain";}
}
