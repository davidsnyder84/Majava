package majava.yaku;

import majava.hand.AgariHand;
import majava.util.YakuList;

public class LimitNanKantsuCheck extends AbstractYakuCheck {
	
	public LimitNanKantsuCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){

		if(handIsSuukantsu())
			putElligibleYakuHere.add(Yaku.YKM_SUUKANTSU);
		
		if(handIsSankantsu())
			putElligibleYakuHere.add(Yaku.SANKANTSU);
		
		//will not overlap
	}
	
	
	public boolean handIsSuukantsu(){return hand.numberOfKansMade() == 4;}
	public boolean handIsSankantsu(){return hand.numberOfKansMade() == 3;}
}
