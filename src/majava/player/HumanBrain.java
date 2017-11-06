package majava.player;

import java.util.List;

import majava.userinterface.GameUI;

public class HumanBrain extends PlayerBrain {
	
	
	GameUI userInterface;
	
	
	//constructor requires a UI to be able to talk to the user
	public HumanBrain(Player p, GameUI ui) {
		super(p);
		userInterface = ui;
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
	
	
	
	
	

	
	

	@Override
	public void chooseTurnAction() {
		// TODO Auto-generated method stub
	}
	
	
	
	
	
	public boolean isHuman(){return true;}
	
	@Override
	public String toString(){return "HumanBrain";}
}
