package majava.player.brains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

//a bot that likes making pon-related yaku (toitoi hands)
public class ShuntsuMonsterBot extends RobotBrain{

	public ShuntsuMonsterBot(Player p) {
		super(p);
	}
	

	@Override
	protected TurnActionType selectTurnAction(Hand hand, List<TurnActionType> listOfPossibleTurnActions){
		//don't ankan
		listOfPossibleTurnActions.removeAll(Arrays.asList(TurnActionType.ANKAN, TurnActionType.MINKAN));
		return biggestTurnAction(listOfPossibleTurnActions);
	}
	
	@Override
	protected int selectDiscardIndex(Hand hand){
		return 8888888;
	}
	
	
	
	
	private static int pickOneFrom(List<Integer> choices){
//		return pickRandomlyFrom(choices);
		return pickLastFrom(choices);
	}
	
	
	@Override
	protected CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions) {
		//don't call pon/kan
		listOfPossibleReactions.removeAll(Arrays.asList(CallType.PON, CallType.KAN));		
		return biggestReaction(listOfPossibleReactions);
	}
	
	@Override
	public String toString(){return "PonMonsterBot";}
}
