package majava.userinterface.humaninput;

import java.util.List;

import majava.enums.CallType;
import majava.enums.TurnActionType;
import majava.hand.Hand;
import majava.tiles.GameTile;

public interface HumanInputAsker {
	
	
	public TurnActionType selectTurnAction(Hand hand, List<TurnActionType> listOfPossibleTurnActions);
	public int selectDiscardIndex(Hand hand);
	
	
	public CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions);
	
}
