package majava.yaku;

import majava.hand.AgariHand;
import majava.util.YakuList;

public class ChiitoitsuCheck extends AbstractYakuCheck {
	
	public ChiitoitsuCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		if (handIsChiitoi())
			putElligibleYakuHere.add(Yaku.CHIITOITSU);
	}
	
	
	
	public boolean handIsChiitoi(){
		return hand.isCompleteChiitoitsu();
	}
	
}
