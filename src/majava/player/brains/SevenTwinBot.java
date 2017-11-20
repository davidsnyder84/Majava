package majava.player.brains;

import java.util.ArrayList;
import java.util.List;

import majava.hand.Hand;
import majava.player.Player;
import majava.player.brains.PlayerBrain.CallType;
import majava.tiles.GameTile;

public class SevenTwinBot extends RobotBrain {
	
	public SevenTwinBot(Player p) {super(p);}
	
	@Override
	protected ActionType selectTurnAction(Hand hand, List<ActionType> listOfPossibleTurnActions) {
		//don't kan, only tsumo
		if (listOfPossibleTurnActions.contains(ActionType.TSUMO))
			return ActionType.TSUMO;
		return ActionType.DISCARD;
	}
	
	@Override
	protected int selectDiscardIndex(Hand hand) {
		return indexOfPairlessTile(hand);
	}
	
	private int indexOfPairlessTile(Hand hand){
		List<Integer> nonPairs = new ArrayList<Integer>();		
		for (int index = 0; index < hand.size(); index++)
			if (hand.findHowManyOf(hand.getTile(index)) != 2)
				nonPairs.add(index);
		
//		return nonPairs.get(nonPairs.size() - 1);
		return pickRandomlyFrom(nonPairs);
	}
	
	
	
	@Override
	protected CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions) {
		//only call ron
		if (listOfPossibleReactions.contains(CallType.RON))
			return CallType.RON;
		return CallType.NONE;
	}
	
}
