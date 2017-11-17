package majava.yaku;

import java.util.ArrayList;
import java.util.List;

import majava.hand.Hand;
import majava.util.YakuList;

public class YakuAnalyzer {
	
	
	private Hand hand;
	
	List<AbstractYakuCheck> checks;
	
	public YakuAnalyzer(Hand receivedHand){
		hand = receivedHand;
		
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
