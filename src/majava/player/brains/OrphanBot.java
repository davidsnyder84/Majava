package majava.player.brains;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

//a bot that tries to form thirteen orphans (çëémñ≥ëo)
public class OrphanBot extends RobotBrain {
	
	public OrphanBot(Player p) {super(p);}
	
	@Override
	protected int selectDiscardIndex(Hand hand) {
		return Math.max(indexOfNonTYC(hand), Math.max(indexOfTriplicateTYC(hand), indexOfUnneededPairTYC(hand)));
//		return tsumoTileIndex(hand);
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
