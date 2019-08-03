package majava.yaku;

import majava.enums.Suit;
import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class LimitDaisangenCheck extends AbstractYakuCheck {
	
	public LimitDaisangenCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		if(handIsDaisangen())
			putElligibleYakuHere.add(Yaku.YKM_DAISANGEN);
		
		if(handIsShousangen())
			putElligibleYakuHere.add(Yaku.SHOUSANGEN);
		
		//note: Daisangen and Shousangen will never overlap with eachother
	}
	
	
	public boolean handIsDaisangen(){
		return (numberOfDragonPons() == 3);
	}
	public boolean handIsShousangen(){
		return (numberOfDragonPons() == 2) && pairIsDragon();
	}
	
	
	
	private int numberOfDragonPons(){
		int numDragonPons = 0;
		for (Meld m: handInMeldForm())
			if (m.isDragonMeld() && m.isPonkon()) numDragonPons++;
		return numDragonPons;
	}
	private boolean pairIsDragon(){return meldIsDragon(hand.getPair());}
	
	private boolean meldIsDragon(Meld m){
		return (m.getFirstTile().getSuit() == Suit.DRAGON.toChar());
	}
	
}
