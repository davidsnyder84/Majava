package majava.yaku;

import java.util.List;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class ToitoiCheck extends AbstractYakuCheck {
	
	public ToitoiCheck(AgariHand h){super(h);}
	
	public YakuList calculateElligibleYaku() {
		YakuList elligibleYakus = new YakuList();
		
		if (handIsToitoi())
			elligibleYakus.add(Yaku.TOITOI);
		
		return elligibleYakus;
	}
	
	public boolean handIsToitoi(){
		List<Meld> melds = hand.getFinishingMelds();
		/////need meld form here, not just finishing melds
		
		if (melds.size() != 5) return false;
		for (Meld m: melds)
			if (!m.isMulti()) return false;
		
		return true;
	}
	
	
}
