package majava.player.brains;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

public class PonMonsterBot extends RobotBrain {

	public PonMonsterBot(Player p) {super(p);}
	

	@Override
	protected ActionType selectTurnAction(Hand hand, List<ActionType> listOfPossibleTurnActions) {return biggestTurnAction(listOfPossibleTurnActions);}
	
	@Override
	protected int selectDiscardIndex(Hand hand){
		return findLoneliestIndex(hand);
	}
	
	private int findLoneliestIndex(Hand hand){
		List<Integer> singlesIndices = new ArrayList<Integer>(), doublesIndices = new ArrayList<Integer>(), triplesIndices = new ArrayList<Integer>();		
		for (int index = 0; index < hand.size(); index++){
			switch (hand.findHowManyOf(hand.getTile(index))){
			case 1: singlesIndices.add(index); break; 
			case 2: doublesIndices.add(index); break;
			case 3: triplesIndices.add(index); break;
			default: break;
			}
		}
		if (!singlesIndices.isEmpty()) return pickRandomlyFrom(singlesIndices);
		if (!doublesIndices.isEmpty()) return pickRandomlyFrom(doublesIndices);
		if (!triplesIndices.isEmpty()) return pickRandomlyFrom(triplesIndices);
		
		return tsumoTileIndex(hand);
	}
	
	private int pickRandomlyFrom(List<Integer> choices){return choices.get((new Random()).nextInt(choices.size()));}
	
	
	@Override
	protected CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions) {return biggestReaction(listOfPossibleReactions);}
}
