package majava.player;

import java.util.ArrayList;
import java.util.List;

import majava.enums.Exclamation;
import majava.tiles.GameTile;


//makes decisions for a player
public abstract class PlayerBrain {
	
	private static final int NO_DISCARD_CHOSEN = -94564;
	
	
	protected Player player;
	
	private CallType callStatus;
	private ActionType turnAction;
	private int chosenDiscardIndex;
	
	
	
	public PlayerBrain(Player p){
		player = p;
		if (player == null) System.out.println("null player in PlayerBrain, shouldn't be here");
		
		callStatus = CallType.NONE;
	}
	
	
	
	
	//template method pattern, final
	public final void chooseTurnAction(){
		ActionType chosenAction = ActionType.DISCARD;
		int discardIndex = NO_DISCARD_CHOSEN;
		
		//force auto discard if in riichi and unable to tsumo/kan
		if (player.isInRiichi() && (!player.ableToTsumo() && !player.ableToAnkan())){
			turnAction = ActionType.DISCARD;
			discardIndex = player.handSize() - 1;
			return;
		}
		
		//get list of possible turn actions
		List<ActionType> listOfPossibleTurnActions = listOfPossibleTurnActions();
		
		//ask to pick a turn action (abstract)
		chosenAction = selectTurnAction(listOfPossibleTurnActions);
		
		//if discard was chosen, ask to pick a discard (abstract)
		if (chosenAction == ActionType.DISCARD){
			discardIndex = selectDiscardIndex();
			chosenDiscardIndex = discardIndex;
		}
		
		turnAction = chosenAction;
	}
	//get list of possible options
	private final List<ActionType> listOfPossibleTurnActions() {
		List<ActionType> listOfPossibleTurnActions = new ArrayList<ActionType>();
		
		//discard is always possible, don't add it to the list
		if (player.ableToAnkan()) listOfPossibleTurnActions.add(ActionType.ANKAN);
		if (player.ableToMinkan()) listOfPossibleTurnActions.add(ActionType.MINKAN);
		if (player.ableToRiichi()) listOfPossibleTurnActions.add(ActionType.RIICHI);
		if (player.ableToTsumo()) listOfPossibleTurnActions.add(ActionType.TSUMO);
		
		return listOfPossibleTurnActions;
	}
	//how the turn action is chosen left abstract and must be defined by the subclass
	protected abstract ActionType selectTurnAction(List<ActionType> listOfPossibleTurnActions);
	protected abstract int selectDiscardIndex();
	
	
	public boolean turnActionMadeKan(){return (turnActionMadeAnkan() || turnActionMadeMinkan());}
	public boolean turnActionMadeAnkan(){return (turnAction == ActionType.ANKAN);}
	public boolean turnActionMadeMinkan(){return (turnAction == ActionType.MINKAN);}
	public boolean turnActionCalledTsumo(){return (turnAction == ActionType.TSUMO);}
	public boolean turnActionChoseDiscard(){return (turnAction == ActionType.DISCARD);}
	public boolean turnActionRiichi(){return (turnAction == ActionType.RIICHI);}
	
	public int getChosenDiscardIndex(){return chosenDiscardIndex;}
	
	
	
	
	
	
	
	//template method pattern, final
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
	
	
	
	
	
	//returns call status as an exclamation
	public final Exclamation getCallStatusExclamation(){return callStatus.toExclamation();}
	
	//returns true if the player called a tile
	public final boolean called(){return (callStatus != CallType.NONE);}
	//individual call statuses
	public final boolean calledChi(){return (calledChiL() || calledChiM() || calledChiH());}
	public final boolean calledChiL(){return (callStatus == CallType.CHI_L);}
	public final boolean calledChiM(){return (callStatus == CallType.CHI_M);}
	public final boolean calledChiH(){return (callStatus == CallType.CHI_H);}
	public final boolean calledPon(){return (callStatus == CallType.PON);}
	public final boolean calledKan(){return (callStatus == CallType.KAN);}
	public final boolean calledRon(){return (callStatus == CallType.RON);}
	
	
	public void clearCallStatus(){callStatus = CallType.NONE;}
	public void clearTurnActionStatus(){
		turnAction = ActionType.UNDECIDED;
		chosenDiscardIndex = NO_DISCARD_CHOSEN;
	}
	
	
	
	
	
	public abstract boolean isHuman();
	public final boolean isComputer(){return !isHuman();}
	
	public static final PlayerBrain generateGenericBrain(Player p){return new SimpleRobot(p);}
	
	@Override
	public String toString(){return "UnknownBrain";}
	
	
	
	
	
	//enums
	//used to indicate what call a player wants to make on another player's discard
	protected static enum CallType{
		NONE, CHI_L, CHI_M, CHI_H, PON, KAN, RON, CHI, UNDECIDED;
		
		@Override
		public String toString(){
			switch (this){
			case CHI_L: case CHI_M: case CHI_H: return "Chi";
			case PON: return "Pon";
			case KAN: return "Kan";
			case RON: return "Ron";
			default: return "None";
			}
		}
		public Exclamation toExclamation(){
			switch (this){
			case CHI_L: case CHI_M: case CHI_H: return Exclamation.CHI;
			case PON: return Exclamation.PON;
			case KAN: return Exclamation.KAN;
			case RON: return Exclamation.RON;
			case NONE: return Exclamation.NONE;
			default: return Exclamation.UNKNOWN;
			}
		}
	}
	
	//used to indicate what action the player wants to do on their turn
	protected static enum ActionType{
		DISCARD, ANKAN, MINKAN, RIICHI, TSUMO, UNDECIDED;
	}
}
