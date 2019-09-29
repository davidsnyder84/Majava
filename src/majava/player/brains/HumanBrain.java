package majava.player.brains;

import java.util.List;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;
import majava.userinterface.humaninput.HumanAskerText;
import majava.userinterface.humaninput.HumanInputAsker;

public class HumanBrain extends PlayerBrain {
	
	private final HumanInputAsker hasker;
	
	
	public HumanBrain(HumanInputAsker ha){
		hasker = ha;
	}
	public HumanBrain(){
		this(new HumanAskerText());
	}
	
	
	
	
	
	@Override
	protected TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions){
		return hasker.selectTurnAction(player, hand, listOfPossibleTurnActions);
	}
	
	@Override
	protected int selectDiscardIndex(Hand hand){
		return hasker.selectDiscardIndex(hand);
	}
	
	
	
	@Override
	public CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions){
		return hasker.chooseReaction(player, hand, tileToReactTo, listOfPossibleReactions);
	}
	
	
	
	
	
	@Override
	public boolean isHuman(){return true;}
	
	@Override
	public String toString(){return "HumanBrain";}
	
}
