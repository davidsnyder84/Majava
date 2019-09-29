package majava.player.brains;

import java.util.ArrayList;
import java.util.List;

import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

//a bot that tries to form a seven pairs hand (chiitoitsu/Žµ‘ÎŽq)
public class SevenTwinBot extends RobotBrain {
	
	@Override
	protected int selectDiscardIndex(Hand hand) {
		return indexOfPairlessTile(hand);
	}
	
	private int indexOfPairlessTile(Hand hand){
		List<Integer> nonPairs = new ArrayList<Integer>();		
		for (int index = 0; index < hand.size(); index++)
			if (hand.findHowManyOf(hand.getTile(index)) != 2)
				nonPairs.add(index);
		
		return pickOneFrom(nonPairs);
	}
	
	private static int pickOneFrom(List<Integer> choices){
//		return pickRandomlyFrom(choices);
		return pickLastFrom(choices);
	}
	
	@Override
	public String toString(){return "SevenTwinBot";}
}
