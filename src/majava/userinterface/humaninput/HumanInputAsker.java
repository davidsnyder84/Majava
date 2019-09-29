package majava.userinterface.humaninput;

import java.util.List;

import majava.enums.CallType;
import majava.enums.TurnActionType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

public interface HumanInputAsker {
	
	
	public TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions);
	public int selectDiscardIndex(Hand hand);
	
	
	public CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions);
	
}
