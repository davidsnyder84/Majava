package majava.yaku;

import majava.hand.AgariHand;
import majava.tiles.GameTile;
import majava.util.YakuList;

public class LimitTsuuiisouCheck extends AbstractYakuCheck {
	
	public LimitTsuuiisouCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		if(handIsTsuuiisou())
			putElligibleYakuHere.add(Yaku.YKM_TSUUIISOU);
	}
	
	
	
	
	//hand contains only honoes
	public boolean handIsTsuuiisou(){
		for (GameTile t: handInTilesForm())
			if (!t.isHonor())
				return false;
		
		return true;
	}
	
}
