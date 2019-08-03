package majava.yaku;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class ToitoiCheck extends AbstractYakuCheck {
	
	public ToitoiCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		if (handIsToitoi())
			putElligibleYakuHere.add(Yaku.TOITOI);
	}
	
	
	
	//conditions: 5 melds (1 is pair), none are chi (all are pon/kan)
	public boolean handIsToitoi(){
//		if (melds.size() != 5) return false;
		for (Meld m: handInMeldForm())
			if (m.isChi()) return false;
		
		return true;
	}
	
}
