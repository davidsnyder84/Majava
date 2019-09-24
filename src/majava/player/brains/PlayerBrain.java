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
	
	protected Player player;
	
	
	
	public PlayerBrain(Player p){
		player = p;
	}
	
	
	
	
	//template method pattern, final
	public final DecisionTurnAction chooseTurnAction(Player thisPlayer, Hand hand){
		this.player = thisPlayer;
		
		//this logic should be good, but I haven't implemented turn actions yet
		
//		//force auto discard if in riichi and unable to tsumo/kan
//		if (player.isInRiichi() && (!player.ableToTsumo() && !player.ableToAnkan()))
//			return DecisionTurnAction.Discard(tsumoTileIndex(hand));
//		
//		
//		//choose your action
//		TurnActionType chosenAction = selectTurnAction(hand, listOfPossibleTurnActions());
//		
//		
//		//actions other than discard
//		if (chosenAction.isNotDiscard())
//			return new DecisionTurnAction(chosenAction);
		
		//discard
		int discardIndex = selectDiscardIndex(hand);
		return DecisionTurnAction.Discard(discardIndex);
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
	
	
	
	
	
	
	
	//template method pattern, final
	public final DecisionCall reactToDiscard(Player thisPlayer, Hand hand, GameTile tileToReactTo) {
		this.player = thisPlayer;
		
		List<CallType> listOfPossibleReactions = getListOfPossibleReactions(tileToReactTo);
		
		if (listOfPossibleReactions.isEmpty())
			return DecisionCall.None(tileToReactTo);
		
		
		CallType chosenCallType = chooseReaction(hand, tileToReactTo, listOfPossibleReactions);
		DecisionCall decision = new DecisionCall(chosenCallType, tileToReactTo);
		return decision;
	}

	//get list of possible options
	private final List<CallType> getListOfPossibleReactions(GameTile tileToReactTo) {
		List<CallType> listOfPossibleReactions = new ArrayList<CallType>();
		
		if (player.ableToCallChiL(tileToReactTo)) listOfPossibleReactions.add(CallType.CHI_L);
		if (player.ableToCallChiM(tileToReactTo)) listOfPossibleReactions.add(CallType.CHI_M);
		if (player.ableToCallChiH(tileToReactTo)) listOfPossibleReactions.add(CallType.CHI_H);
		if (player.ableToCallPon(tileToReactTo)) listOfPossibleReactions.add(CallType.PON);
		if (player.ableToCallKan(tileToReactTo)) listOfPossibleReactions.add(CallType.KAN);
//		if (player.ableToCallRon(tileToReactTo)) listOfPossibleReactions.add(CallType.RON);
		
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
	
	
	
	
	
	public abstract boolean isHuman();
	public final boolean isComputer(){return !isHuman();}
	
	protected static final int tsumoTileIndex(Hand hand){return hand.size() - 1;}
	protected static final int firstTileIndex(Hand hand){return 0;}
	
	
	@Override
	public String toString(){return "UnknownBrain";}
}
