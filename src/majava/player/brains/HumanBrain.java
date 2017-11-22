package majava.player.brains;

import java.util.List;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;
import majava.userinterface.GameUI;

public class HumanBrain extends PlayerBrain {
	
	private GameUI userInterface;
	private CallType callChosenByHuman;
	private TurnActionType turnActionChosenByHuman;
	private int discardIndexChosenByHuman;
	
	
	//constructor requires a UI to be able to talk to the user
	public HumanBrain(Player p, GameUI ui) {
		super(p);
		userInterface = ui;
	}
	
	
	public void setCallChosenByHuman(CallType call) {callChosenByHuman = call;}
	public void setTurnActionChosenByHuman(TurnActionType turnaction) {turnActionChosenByHuman = turnaction;}
	public void setDiscardIndexChosenByHuman(int discardindex) {discardIndexChosenByHuman = discardindex;}
	
	
	@Override
	protected TurnActionType selectTurnAction(Hand hand, List<TurnActionType> listOfPossibleTurnActions){
		//get the player's desired action through the UI
		userInterface.askUserInputTurnAction(
				hand.size(),
				listOfPossibleTurnActions.contains(TurnActionType.RIICHI),
				listOfPossibleTurnActions.contains(TurnActionType.ANKAN),
				listOfPossibleTurnActions.contains(TurnActionType.MINKAN),
				listOfPossibleTurnActions.contains(TurnActionType.TSUMO)
				);
		
		return turnActionChosenByHuman;
	}
	
	@Override
	protected int selectDiscardIndex(Hand hand){
		//the human has already chosen the discard index through the user interface, just need to return that
//		return userInterface.resultChosenDiscardIndex() - 1;
		return discardIndexChosenByHuman;
	}
	
	
	
	
	
	@Override
	public CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions){
		userInterface.movePromptPanelToSeat(player.getPlayerNumber());
		
		//get user's choice through UI
		boolean called = userInterface.askUserInputCall(
				listOfPossibleReactions.contains(CallType.CHI_L),
				listOfPossibleReactions.contains(CallType.CHI_M),
				listOfPossibleReactions.contains(CallType.CHI_H),
				listOfPossibleReactions.contains(CallType.PON),
				listOfPossibleReactions.contains(CallType.KAN),
				listOfPossibleReactions.contains(CallType.RON)
				);
		
		return callChosenByHuman;
	}
	
	
	
	@Override
	public boolean isHuman(){return true;}
	
	public void setUI(GameUI ui){
		userInterface = ui;
	}
	
	@Override
	public String toString(){return "HumanBrain";}
	
}
