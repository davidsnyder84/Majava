package majava.player.brains;

import java.util.List;
import java.util.Random;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;



public abstract class RobotBrain extends PlayerBrain {
	protected static final int INDEX_NOT_FOUND = -1;
	
	
	
	
	@Override
	protected CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions) {
		//win by ron if possible
		if (listOfPossibleReactions.contains(CallType.RON)) return CallType.RON;
		return CallType.NONE;
	}
	@Override
	protected TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions) {
		//win by tsumo if possible
		if (listOfPossibleTurnActions.contains(TurnActionType.TSUMO)) return TurnActionType.TSUMO;
		return TurnActionType.DISCARD;
	}
	
	
	
	
	protected static int pickRandomlyFrom(List<Integer> choices){
		if (choices.isEmpty()) return INDEX_NOT_FOUND;
		return choices.get((new Random()).nextInt(choices.size()));
	}
	protected static int pickLastFrom(List<Integer> choices){
		if (choices.isEmpty()) return INDEX_NOT_FOUND;
		return choices.get(choices.size()-1);
	}
	protected static int pickFirstFrom(List<Integer> choices){
		if (choices.isEmpty()) return INDEX_NOT_FOUND;
		return choices.get(0);
	}
	
	
	
	
	
	@Override
	public final boolean isHuman(){return false;}
	
	
	@Override
	public String toString(){return "UnspecifiedRobotBrain";}
}
