package majava.yaku;

import majava.hand.AgariHand;
import majava.tiles.GameTile;
import majava.util.YakuList;

public class LimitChinroutoCheck extends AbstractYakuCheck {
	
	public LimitChinroutoCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		if(handIsChinrouto())
			putElligibleYakuHere.add(Yaku.YKM_CHINROUTO);
	}
	
	
	
	
	
	//hand is all terminals
	public boolean handIsChinrouto(){		
		for (GameTile t: handInTilesForm())
			if (!t.isTerminal())
				return false;
		
		return true;
	}
	
}
