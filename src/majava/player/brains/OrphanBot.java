package majava.player.brains;

import java.util.ArrayList;
import java.util.List;

import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

//a bot that tries to form thirteen orphans (çëémñ≥ëo)
///////////////NO LOGIC IMPLEMENTED YET
public class OrphanBot extends RobotBrain {
	
	
	@Override
	protected int selectDiscardIndex(Hand hand) {
		int bestIndex = Math.max(indexOfNonTYC(hand), Math.max(indexOfTriplicateTYC(hand), indexOfUnneededPairTYC(hand)));
		
		if (bestIndex == INDEX_NOT_FOUND)
			bestIndex = tsumoTileIndex(hand);
		
		return bestIndex;
	}
	
	private int indexOfNonTYC(Hand hand){
		List<Integer> nonTYCs = new ArrayList<Integer>();
		return INDEX_NOT_FOUND;
	}
	private int indexOfTriplicateTYC(Hand hand){
		List<Integer> triplicateTYCs = new ArrayList<Integer>();
		return INDEX_NOT_FOUND;
	}
	private int indexOfUnneededPairTYC(Hand hand){
		List<Integer> unneededPairTYCs = new ArrayList<Integer>();
		return INDEX_NOT_FOUND;
	}
	
	@Override
	public String toString(){return "OrphanBot";}	
}
