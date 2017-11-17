package majava.yaku;

import majava.hand.Hand;

public abstract class AbstractYakuCheck {
	protected final Hand hand;
	
	public AbstractYakuCheck(Hand aHand){
		hand = aHand;
	}
	
	
	protected Hand getHand(){return hand;}
	protected int handSize(){return hand.size();}
	protected int numberOfMeldsMade(){return hand.numberOfMeldsMade();}
}
