package majava.player.brains;

import java.util.ArrayList;
import java.util.List;

import majava.enums.CallType;
import majava.enums.Exclamation;
import majava.enums.TurnActionType;
import majava.hand.Hand;
import majava.player.DecisionCall;
import majava.player.DecisionTurnAction;
import majava.player.Player;
import majava.tiles.GameTile;


//makes decisions for a player
public abstract class PlayerBrain {
	
	
	
	public PlayerBrain(){
	}
	
	
	
	
	
	
	public final DecisionTurnAction chooseTurnAction(Player player){
		
		//force auto discard if in riichi and unable to tsumo/kan
		if (player.isInRiichi() && (!player.ableToTsumo() && !player.ableToAnkan()))
			return DecisionTurnAction.Discard(tsumoTileIndex(player.getHand()));
		
		
		//choose your action
		TurnActionType chosenAction = selectTurnAction(player, player.getHand(), listOfPossibleTurnActions(player));
		
		
		//actions other than discard
		if (chosenAction.isNotDiscard())
			return new DecisionTurnAction(chosenAction);
		
		
		//choose discard index
		int discardIndex = selectDiscardIndex(player.getHand());
		return DecisionTurnAction.Discard(discardIndex);
	}
	
	private final List<TurnActionType> listOfPossibleTurnActions(Player player) {
		List<TurnActionType> listOfPossibleTurnActions = new ArrayList<TurnActionType>();
		
		//discard is always possible, don't add it to the list
		if (player.ableToAnkan()) listOfPossibleTurnActions.add(TurnActionType.ANKAN);
		if (player.ableToMinkan()) listOfPossibleTurnActions.add(TurnActionType.MINKAN);
		if (player.ableToRiichi()) listOfPossibleTurnActions.add(TurnActionType.RIICHI);
		if (player.ableToTsumo()) listOfPossibleTurnActions.add(TurnActionType.TSUMO);
		
		return listOfPossibleTurnActions;
	}
	//how the turn action is chosen left abstract and must be defined by the subclass
	protected abstract TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions);
	protected abstract int selectDiscardIndex(Hand hand);
	
	
	
	
	
	
	
	public final DecisionCall reactToDiscard(Player player, GameTile tileToReactTo) {
		
		List<CallType> listOfPossibleReactions = getListOfPossibleReactions(player, tileToReactTo);
		
		if (listOfPossibleReactions.isEmpty())
			return DecisionCall.None(tileToReactTo);
		
		
		CallType chosenCallType = chooseReaction(player, player.getHand(), tileToReactTo, listOfPossibleReactions);
		DecisionCall decision = new DecisionCall(chosenCallType, tileToReactTo);
		return decision;
	}
	
	private final List<CallType> getListOfPossibleReactions(Player player, GameTile tileToReactTo) {
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
	protected abstract CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions);
	
	
	
	
	
	public abstract boolean isHuman();
	public final boolean isComputer(){return !isHuman();}
	
	
	
	
	
	
	
	//convenience methods for subclasses to use
	protected final TurnActionType biggestTurnAction(List<TurnActionType> actions){
		if (actions.isEmpty()) return TurnActionType.DISCARD;
		return actions.get(actions.size()-1);
	}
	protected final CallType biggestReaction(List<CallType> calls){
		if (calls.isEmpty()) return CallType.NONE;
		return calls.get(calls.size()-1);
	}
	
	protected static final int tsumoTileIndex(Hand hand){return hand.size() - 1;}
	protected static final int firstTileIndex(Hand hand){return 0;}
	
	
	
	@Override
	public String toString(){return "UnknownBrain";}
}
