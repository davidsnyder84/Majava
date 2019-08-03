package majava.yaku;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class ChantaCheck extends AbstractYakuCheck {
	
	public ChantaCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		if(handIsChanta())
			putElligibleYakuHere.add(Yaku.CHANTA);
		
		//will overlap with honrouto
		//will overlap with honrouto
		//will overlap with honrouto
		//will overlap with honrouto
	}
	
	
	
	
	
	public boolean handIsChanta(){
		for (Meld m: handInMeldForm())
			if (!(m.containsTerminal() || m.isHonorMeld()))
				return false;
		
		return true;
	}
	
}
