package majava.yaku;

import java.util.ArrayList;
import java.util.List;

import majava.RoundResult;
import majava.hand.AgariHand;
import majava.hand.Hand;
import majava.util.YakuList;

public class YakuAnalyzer {
	
	
	private final AgariHand hand;
	private final RoundResult roundResult;
	
	private final List<AbstractYakuCheck> checks;
	
	
	public YakuAnalyzer(Hand receivedHand, RoundResult result){
		roundResult = result;
		hand = new AgariHand(receivedHand, roundResult.getWinningTile());		
		
		checks = new ArrayList<AbstractYakuCheck>();
		makeCheckers();
	}
	
	private void makeCheckers(){
		checks.add(new ToitoiCheck(hand));
	}
	
	
	public YakuList getAllElligibleYaku(){
		YakuList elligibleYakus = new YakuList();
		for (AbstractYakuCheck checker: checks)
			elligibleYakus.addAll(checker.getElligibleYaku());
		
		return elligibleYakus;
	}
	
}
