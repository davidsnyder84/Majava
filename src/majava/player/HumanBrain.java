package majava.player;

import java.util.List;

import majava.enums.GameplayEvent;
import majava.player.PlayerBrain.ActionType;
import majava.userinterface.GameUI;

public class HumanBrain extends PlayerBrain {
	
	
	GameUI userInterface;
	
	
	//constructor requires a UI to be able to talk to the user
	public HumanBrain(Player p, GameUI ui) {
		super(p);
		userInterface = ui;
	}
	
	
	
	
	
	
	
	
	@Override
	protected ActionType selectTurnAction(List<ActionType> listOfPossibleTurnActions){
		ActionType chosenAction = ActionType.UNDECIDED;
		
		//show hand
		userInterface.displayEvent(GameplayEvent.HUMAN_PLAYER_TURN_START);
		
		//get the player's desired action through the UI
		userInterface.askUserInputTurnAction(
				player.handSize(),
				listOfPossibleTurnActions.contains(ActionType.RIICHI),
				listOfPossibleTurnActions.contains(ActionType.ANKAN),
				listOfPossibleTurnActions.contains(ActionType.MINKAN),
				listOfPossibleTurnActions.contains(ActionType.TSUMO)
				);
		
		//decide action based on player's choice
		if (userInterface.resultChosenTurnActionWasDiscard()) chosenAction = ActionType.DISCARD;
		else if (userInterface.resultChosenTurnActionWasAnkan()) chosenAction = ActionType.ANKAN;
		else if (userInterface.resultChosenTurnActionWasMinkan()) chosenAction = ActionType.MINKAN;
		else if (userInterface.resultChosenTurnActionWasRiichi()) chosenAction = ActionType.RIICHI;
		else if (userInterface.resultChosenTurnActionWasTsumo()) chosenAction = ActionType.TSUMO;
		
		return chosenAction;
	}
	
	@Override
	protected int selectDiscardIndex(){
		//the human has already chosen the discard index through the user interface, just need to return that
		return userInterface.resultChosenDiscardIndex() - 1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public CallType chooseReaction(List<CallType> listOfPossibleReactions){
		
		//get user's choice through UI
		boolean called = userInterface.askUserInputCall(
				listOfPossibleReactions.contains(CallType.CHI_L),
				listOfPossibleReactions.contains(CallType.CHI_M),
				listOfPossibleReactions.contains(CallType.CHI_H),
				listOfPossibleReactions.contains(CallType.PON),
				listOfPossibleReactions.contains(CallType.KAN),
				listOfPossibleReactions.contains(CallType.RON)
				);
		
		//decide call based on player's choice
		CallType chosenCallType = CallType.NONE;
		if (called){
			if (userInterface.resultChosenCallWasChiL()) chosenCallType = CallType.CHI_L;
			else if (userInterface.resultChosenCallWasChiM()) chosenCallType = CallType.CHI_M;
			else if (userInterface.resultChosenCallWasChiH()) chosenCallType = CallType.CHI_H;
			else if (userInterface.resultChosenCallWasPon()) chosenCallType = CallType.PON;
			else if (userInterface.resultChosenCallWasKan()) chosenCallType = CallType.KAN;
			else if (userInterface.resultChosenCallWasRon()) chosenCallType = CallType.RON;
		}
		
		return chosenCallType;
	}
	
	
	
	
	

	
	
	
	
	
	
	public boolean isHuman(){return true;}
	
	public void setUI(GameUI ui){
		userInterface = ui;
	}
	
	@Override
	public String toString(){return "HumanBrain";}
	
}
