package majava.player.brains;

import java.util.List;

import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

public class OrphanBot extends RobotBrain {
	
	public OrphanBot(Player p) {super(p);}
	
	@Override
	protected ActionType selectTurnAction(Hand hand, List<ActionType> listOfPossibleTurnActions) {
		return null;
	}
	
	@Override
	protected int selectDiscardIndex(Hand hand) {
		return 0;
	}
	
	@Override
	protected CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions) {
		return null;
	}
	
}
