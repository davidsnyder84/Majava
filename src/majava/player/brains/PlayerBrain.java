package majava.player.brains;

import java.util.ArrayList;
import java.util.List;

import majava.enums.CallType;
import majava.enums.Exclamation;
import majava.enums.TurnActionType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;


//makes decisions for a player
public abstract class PlayerBrain {
	
	private static final int NO_DISCARD_CHOSEN = -94564;
	
	
	protected final Player player;
	
	private CallType callStatus;
	private TurnActionType turnAction;
	private int chosenDiscardIndex;
	
	
	
	public PlayerBrain(Player p){
		player = p;	//if (player == null) System.out.println("null in PlayerBrain constructor, shouldn't be here");
		
		callStatus = CallType.NONE;
	}

//	private Hand hand;
//	public void setHand(Hand newHand){hand = newHand;}
	
	
	
	
	//template method pattern, final
	public final void chooseTurnAction(Hand hand){
		TurnActionType chosenAction = TurnActionType.DISCARD;
		int discardIndex = NO_DISCARD_CHOSEN;
		
		//force auto discard if in riichi and unable to tsumo/kan
		if (player.isInRiichi() && (!player.ableToTsumo() && !player.ableToAnkan())){
			turnAction = TurnActionType.DISCARD;
			discardIndex = tsumoTileIndex(hand);
			return;
		}
		
		//get list of possible turn actions
		List<TurnActionType> listOfPossibleTurnActions = listOfPossibleTurnActions();
		
		//ask to pick a turn action (abstract)
		chosenAction = selectTurnAction(hand, listOfPossibleTurnActions);
		
		//if discard was chosen, ask to pick a discard (abstract)
		if (chosenAction == TurnActionType.DISCARD){
			discardIndex = selectDiscardIndex(hand);
			chosenDiscardIndex = discardIndex;
		}
		
		turnAction = chosenAction;
	}
	//get list of possible options
	private final List<TurnActionType> listOfPossibleTurnActions() {
		List<TurnActionType> listOfPossibleTurnActions = new ArrayList<TurnActionType>();
		
		//discard is always possible, don't add it to the list
		if (player.ableToAnkan()) listOfPossibleTurnActions.add(TurnActionType.ANKAN);
		if (player.ableToMinkan()) listOfPossibleTurnActions.add(TurnActionType.MINKAN);
		if (player.ableToRiichi()) listOfPossibleTurnActions.add(TurnActionType.RIICHI);
		if (player.ableToTsumo()) listOfPossibleTurnActions.add(TurnActionType.TSUMO);
		
		return listOfPossibleTurnActions;
	}
	//how the turn action is chosen left abstract and must be defined by the subclass
	protected abstract TurnActionType selectTurnAction(Hand hand, List<TurnActionType> listOfPossibleTurnActions);
	protected abstract int selectDiscardIndex(Hand hand);
	
	
	public boolean turnActionMadeKan(){return (turnActionMadeAnkan() || turnActionMadeMinkan());}
	public boolean turnActionMadeAnkan(){return (turnAction == TurnActionType.ANKAN);}
	public boolean turnActionMadeMinkan(){return (turnAction == TurnActionType.MINKAN);}
	public boolean turnActionCalledTsumo(){return (turnAction == TurnActionType.TSUMO);}
	public boolean turnActionChoseDiscard(){return (turnAction == TurnActionType.DISCARD);}
	public boolean turnActionRiichi(){return (turnAction == TurnActionType.RIICHI);}
	
	public int getChosenDiscardIndex(){return chosenDiscardIndex;}
	
	
	
	
	
	
	
	//template method pattern, final
	public final void reactToDiscard(Hand hand, GameTile tileToReactTo) {
		
		List<CallType> listOfPossibleReactions = getListOfPossibleReactions(tileToReactTo);
		
		//return early if no call is possible
		if (listOfPossibleReactions.isEmpty()){
			callStatus = CallType.NONE;
			return;
		}

		callStatus = chooseReaction(hand, tileToReactTo, listOfPossibleReactions);
//		return callStatus != CallType.NONE;
	}

	//get list of possible options
	private final List<CallType> getListOfPossibleReactions(GameTile tileToReactTo) {
		List<CallType> listOfPossibleReactions = new ArrayList<CallType>();
		
		if (player.ableToCallChiL(tileToReactTo)) listOfPossibleReactions.add(CallType.CHI_L);
		if (player.ableToCallChiM(tileToReactTo)) listOfPossibleReactions.add(CallType.CHI_M);
		if (player.ableToCallChiH(tileToReactTo)) listOfPossibleReactions.add(CallType.CHI_H);
		if (player.ableToCallPon(tileToReactTo)) listOfPossibleReactions.add(CallType.PON);
		if (player.ableToCallKan(tileToReactTo)) listOfPossibleReactions.add(CallType.KAN);
		if (player.ableToCallRon(tileToReactTo)) listOfPossibleReactions.add(CallType.RON);
		
		return listOfPossibleReactions;
	}
	//how the reaction is chosen left abstract and must be defined by the subclass
	protected abstract CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions);
	
	protected final TurnActionType biggestTurnAction(List<TurnActionType> actions){
		if (actions.isEmpty()) return TurnActionType.DISCARD;
		return actions.get(actions.size()-1);
	}
	protected final CallType biggestReaction(List<CallType> calls){
		if (calls.isEmpty()) return CallType.NONE;
		return calls.get(calls.size()-1);
	}
	
	
	
	
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
		turnAction = TurnActionType.UNDECIDED;
		chosenDiscardIndex = NO_DISCARD_CHOSEN;
	}
	
	
	
	
	
	public abstract boolean isHuman();
	public final boolean isComputer(){return !isHuman();}
	
	protected static final int tsumoTileIndex(Hand hand){return hand.size() - 1;}
	protected static final int firstTileIndex(Hand hand){return 0;}
	
	
	
	
	@Override
	public String toString(){return "UnknownBrain";}
}
