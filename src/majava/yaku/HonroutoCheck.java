package majava.yaku;

import majava.hand.AgariHand;
import majava.tiles.GameTile;
import majava.util.YakuList;

public class HonroutoCheck extends AbstractYakuCheck {
	
	public HonroutoCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		if(handIsHonrouto())
			putElligibleYakuHere.add(Yaku.HONROUTO);
		
		//will overlap with chinrouto and tsuuiisou, but both of those are yakuman so it doesn't matter
	}
	
	
	
	
	//hand is only terminals and honors
	//(catched 7pairs version of honrouto too)
	public boolean handIsHonrouto(){
		for (GameTile t: handInTilesForm())
			if (!(t.isHonor() || t.isTerminal()))
				return false;
		
		return true;
	}
	
}
