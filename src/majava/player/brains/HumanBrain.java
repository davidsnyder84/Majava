package majava.player.brains;

import java.util.List;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

public class HumanBrain extends PlayerBrain {
	
	private CallType callChosenByHuman;
	private TurnActionType turnActionChosenByHuman;
	private int discardIndexChosenByHuman;
	
	
	public HumanBrain(Player p) {
		super(p);
	}
	
	//a human sets these values through a UI
	public void setCallChosenByHuman(CallType call) {callChosenByHuman = call;}
	public void setTurnActionChosenByHuman(TurnActionType turnaction) {turnActionChosenByHuman = turnaction;}
	public void setDiscardIndexChosenByHuman(int discardindex) {discardIndexChosenByHuman = discardindex;}
	
	
	@Override
	protected TurnActionType selectTurnAction(Hand hand, List<TurnActionType> listOfPossibleTurnActions){
		return turnActionChosenByHuman;
	}
	
	@Override
	protected int selectDiscardIndex(Hand hand){
		return discardIndexChosenByHuman;
	}
	
	
	
	@Override
	public CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions){
		return callChosenByHuman;
	}
	
	
	
	@Override
	public boolean isHuman(){return true;}
	
	@Override
	public String toString(){return "HumanBrain";}
	
}
