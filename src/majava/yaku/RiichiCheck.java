package majava.yaku;

import majava.hand.AgariHand;
import majava.util.YakuList;

public class RiichiCheck extends AbstractYakuCheck {
	
	public RiichiCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		if(handIs())
			putElligibleYakuHere.add(Yaku.NAGASHI_MANGAN);
		
		
	}
	
	
	
	
	
	public boolean handIs(){
		
		
		
		return false;
	}
	
}
