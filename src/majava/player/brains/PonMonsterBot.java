package majava.player.brains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

//a bot that likes making pon-related yaku (toitoi hands)
public class PonMonsterBot extends RobotBrain {
	
	private boolean hatesChi;

	public PonMonsterBot(Player p) {
		super(p);
		hatesChi = true;
	}
	

	@Override
	protected TurnActionType selectTurnAction(Hand hand, List<TurnActionType> listOfPossibleTurnActions) {return biggestTurnAction(listOfPossibleTurnActions);}
	
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
	
	
	public void setChiBehavior(boolean willCallChi){hatesChi = !willCallChi;}
	
	
	@Override
	protected CallType chooseReaction(Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions) {
		//don't call chi
		if (hatesChi)
			listOfPossibleReactions.removeAll(Arrays.asList(CallType.CHI_L, CallType.CHI_M, CallType.CHI_H));
		
		return biggestReaction(listOfPossibleReactions);
	}
	
	@Override
	public String toString(){return "PonMonsterBot";}
}
