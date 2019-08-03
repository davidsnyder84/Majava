package majava.yaku;

import majava.hand.AgariHand;
import majava.hand.Hand;
import majava.util.YakuList;

public abstract class AbstractYakuCheck {
	
	protected final AgariHand hand;
	
	public AbstractYakuCheck(AgariHand h){
		hand = h;
	}
	
	
	
	final public YakuList getElligibleYaku(){
		final YakuList elligibleYakus = new YakuList();
		
		findElligibleYaku(elligibleYakus);
		
		return elligibleYakus;
	}
	
	//the subclass should find elligible yaku, and store them in the list putElligibleYakuHere
	public abstract void findElligibleYaku(final YakuList putElligibleYakuHere);
	
	
	
	
	protected Hand getHand(){return hand;}
	protected int handSize(){return hand.size();}
	protected int numberOfMeldsMade(){return hand.numberOfMeldsMade();}
}
