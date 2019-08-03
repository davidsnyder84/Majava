package majava.yaku;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class LimitNanAnkouCheck extends AbstractYakuCheck {
	
	public LimitNanAnkouCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		if(handIsSuuankou())
			putElligibleYakuHere.add(Yaku.YKM_SUUANKOU);
		
		if(handIsSanankou())
			putElligibleYakuHere.add(Yaku.SANANKOU);
		
		//will not overlap
	}
	
	
	
	
	
	public boolean handIsSuuankou(){
		return numberOfAnkou() == 4;
	}
	public boolean handIsSanankou(){
		return numberOfAnkou() == 3;
	}
	
	private int numberOfAnkou(){
		int numAnkou = 0;
		for (Meld m: handInMeldForm())
			if (m.isMulti() && m.isClosed())
				numAnkou++;
		return numAnkou;
	}
}
