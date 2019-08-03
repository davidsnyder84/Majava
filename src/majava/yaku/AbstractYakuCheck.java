package majava.yaku;

import majava.hand.AgariHand;
import majava.hand.Hand;
import majava.util.YakuList;

public abstract class AbstractYakuCheck {
	protected final AgariHand hand;
	
	public AbstractYakuCheck(AgariHand h){
		hand = h;
	}
	
	public abstract YakuList getElligibleYaku();
	
	
	
	
	protected Hand getHand(){return hand;}
	protected int handSize(){return hand.size();}
	protected int numberOfMeldsMade(){return hand.numberOfMeldsMade();}
}
