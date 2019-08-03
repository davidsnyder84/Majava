package majava.yaku;


import majava.hand.AgariHand;
import majava.util.YakuList;

public class LimitKokushiCheck extends AbstractYakuCheck {
	
	public LimitKokushiCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		if (handIsKokushi())
			putElligibleYakuHere.add(Yaku.YKM_KOKUSHI);
	}
	
	
	
	
	public boolean handIsKokushi(){
		return hand.isCompleteKokushi();		
		//should we do double kokushi for 13-side wait?
	}
	
}
