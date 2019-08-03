package majava.yaku;

import majava.hand.AgariHand;
import majava.util.YakuList;

public class MenzenTsumoCheck extends AbstractYakuCheck {
	
	public MenzenTsumoCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		if(handIsClosed())
			putElligibleYakuHere.add(Yaku.MENZEN_TSUMO);
	}
	
	
	
	
	//hand is closed implies tsumo victory, so isClosed should be sufficient
	public boolean handIsClosed(){
		return hand.isClosed();
	}
	
}
