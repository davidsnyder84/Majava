package majava.yaku;

import java.util.ArrayList;
import java.util.List;

import majava.RoundResult;
import majava.hand.Hand;
import majava.util.YakuList;

public class YakuAnalyzer {
	
	
	private Hand hand;
	private RoundResult roundResult;
	
	private List<AbstractYakuCheck> checks;
	
	
	public YakuAnalyzer(Hand receivedHand, RoundResult result){
		hand = receivedHand;
		roundResult = result;
		
		
		
		checks = new ArrayList<AbstractYakuCheck>();
		makeCheckers();
	}
	
	private void makeCheckers(){
		checks.add(new ToitoiCheck(hand));
	}
	
	
	public YakuList getYakuList(){
		return null;
	}
	
}
