package majava.yaku;

import java.util.List;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class IttsuuCheck extends AbstractYakuCheck {
	
	public IttsuuCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		
		
	}
	
	
	
	
	//conditions: 5 melds (1 is pair), all are pon/kan
	public boolean handIs(){
		List<Meld> melds = hand.getMeldForm();
		
		
		return false;
	}
	
}
