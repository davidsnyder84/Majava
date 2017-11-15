package majava.player.brains;

import java.util.List;

import majava.player.Player;


public class NullPlayerBrain extends PlayerBrain {
	
	public NullPlayerBrain(Player p) {
		super(p);
	}

	@Override
	protected ActionType selectTurnAction(List<ActionType> listOfPossibleTurnActions) {
		return null;
	}
	
	@Override
	protected int selectDiscardIndex() {
		return 0;
	}
	
	@Override
	protected CallType chooseReaction(List<CallType> listOfPossibleReactions) {
		return null;
	}
	
	@Override
	public boolean isHuman(){return false;}
	
	
	@Override
	public String toString(){return "NullBrain";}
}
