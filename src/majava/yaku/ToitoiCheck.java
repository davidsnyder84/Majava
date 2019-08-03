package majava.yaku;

import java.util.List;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class ToitoiCheck extends AbstractYakuCheck {
	
	public ToitoiCheck(AgariHand h){super(h);}
	
	@Override
	public YakuList getElligibleYaku() {
		YakuList elligibleYakus = new YakuList();
		
		if (handIsToitoi())
			elligibleYakus.add(Yaku.TOITOI);
		
		return elligibleYakus;
	}
	
	
	
	
	//conditions: 5 melds (1 is pair), all are pon/kan
	public boolean handIsToitoi(){
		List<Meld> melds = hand.getMeldForm();
		
		if (melds.size() != 5) return false;
		for (Meld m: melds)
			if (!m.isMulti()) return false;
		
		return true;
	}
	
}
