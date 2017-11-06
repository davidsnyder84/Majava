package majava.player;

import java.util.ArrayList;
import java.util.List;

import majava.tiles.GameTile;

public abstract class RobotBrain extends PlayerBrain {
	
	
	
	
	
	
	
	public RobotBrain(Player p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void chooseTurnAction() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	//template method pattern, final
	@Override
	public final void reactToDiscard(GameTile tileToReactTo) {
		
		List<CallType> listOfPossibleReactions = getListOfPossibleReactions();
		
		//return early if no call is possible
		if (listOfPossibleReactions.isEmpty()){
			callStatus = CallType.NONE;
			return;
		}

		callStatus = chooseReaction(listOfPossibleReactions);
	}

	//get list of possible options
	private final List<CallType> getListOfPossibleReactions() {
		List<CallType> listOfPossibleReactions = new ArrayList<CallType>();
		
		if (player.ableToCallChiL()) listOfPossibleReactions.add(CallType.CHI_L);
		if (player.ableToCallChiM()) listOfPossibleReactions.add(CallType.CHI_M);
		if (player.ableToCallChiH()) listOfPossibleReactions.add(CallType.CHI_H);
		if (player.ableToCallPon()) listOfPossibleReactions.add(CallType.PON);
		if (player.ableToCallKan()) listOfPossibleReactions.add(CallType.KAN);
		if (player.ableToCallRon()) listOfPossibleReactions.add(CallType.RON);
		
		return listOfPossibleReactions;
	}
	//how the reaction is chosen left abstract and must be defined by the subclass
	protected abstract CallType chooseReaction(List<CallType> listOfPossibleReactions);
	
	
	
	
	public boolean isHuman(){return false;}
	
	
	@Override
	public String toString(){return "RobotBrain";}
}
