package majava.yaku;

import majava.hand.AgariHand;
import majava.util.YakuList;

public class ChantaCheck extends AbstractYakuCheck {
	
	public ChantaCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		if(handIs())
			putElligibleYakuHere.add(Yaku.NAGASHI_MANGAN);
		
		
	}
	
	
	
	
	
	public boolean handIs(){
		
		
		
		return false;
	}
	
}
