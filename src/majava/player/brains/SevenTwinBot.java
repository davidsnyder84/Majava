package majava.player.brains;

import java.util.ArrayList;
import java.util.List;

import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

//a bot that tries to form a seven pairs hand (chiitoitsu/Žµ‘ÎŽq)
public class SevenTwinBot extends RobotBrain {
	
	public SevenTwinBot(Player p) {super(p);}
	
	@Override
	protected int selectDiscardIndex(Hand hand) {
		return indexOfPairlessTile(hand);
	}
	
	private int indexOfPairlessTile(Hand hand){
		List<Integer> nonPairs = new ArrayList<Integer>();		
		for (int index = 0; index < hand.size(); index++)
			if (hand.findHowManyOf(hand.getTile(index)) != 2)
				nonPairs.add(index);
		
//		return nonPairs.get(nonPairs.size() - 1);
		return pickRandomlyFrom(nonPairs);
	}
	
	@Override
	public String toString(){return "SevenTwinBot";}
}
