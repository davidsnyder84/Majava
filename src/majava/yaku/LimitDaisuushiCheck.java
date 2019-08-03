package majava.yaku;

import majava.enums.Suit;
import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class LimitDaisuushiCheck extends AbstractYakuCheck {
	
	public LimitDaisuushiCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		if(handIsDaisuushi())
			putElligibleYakuHere.add(Yaku.YKM_DAISHUUSHII);
		
		if(handIsShousuushi())
			putElligibleYakuHere.add(Yaku.YKM_SHOUSHUUSHII);
		

		//note: Daisuushi and Shousuushi will never overlap with eachother
	}
	
	
	
	
	public boolean handIsDaisuushi(){
		return (numberOfWindPons() == 4);
	}
	public boolean handIsShousuushi(){
		return (numberOfWindPons() == 3) && pairIsWind();
	}
	
	
	
	
	private int numberOfWindPons(){
		int numWindPons = 0;
		for (Meld m: handInMeldForm())
			if (m.isWindMeld() && m.isPonKan()) numWindPons++;
		return numWindPons;
	}
	private boolean pairIsWind(){return hand.getPair().isWindMeld();}
	
}
