package majava.yaku;

import majava.hand.AgariHand;
import majava.tiles.GameTile;
import majava.util.YakuList;

public class TanyaoCheck extends AbstractYakuCheck {
	private static boolean allowKuitan = true;
	
	public TanyaoCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		if (handIsTanyao())
			putElligibleYakuHere.add(Yaku.TANYAO);
	}
	
	
	
	
	//conditions: 5 melds (1 is pair), all are pon/kan
	public boolean handIsTanyao(){
		for (GameTile t: handInTilesForm())
			if (t.isTanyao()) return false;
		
		return true;
	}
	
}
