package majava.yaku;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.tiles.GameTile;
import majava.util.YakuList;

public class TerminalHonorYakuCheck extends AbstractYakuCheck {
	private boolean allowKuitan = true;
	
	
	public TerminalHonorYakuCheck(AgariHand h){super(h);}
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
//		boolean isChanta = handIsChanta(), isJunchan = handIsJunchan(), isHonrouto = handIsHonrouto(), isChinrouto = handIsChinrouto();
		
		if (handIsTanyao())
			putElligibleYakuHere.add(Yaku.TANYAO);
		
		if(handIsChanta())
			putElligibleYakuHere.add(Yaku.CHANTA);
		
		if(handIsJunchan()){
			putElligibleYakuHere.add(Yaku.JUNCHAN);			
			putElligibleYakuHere.remove(Yaku.CHANTA);
			//chanta will always overlap with junchan ("every meld contains terminal or honor" VS "every meld contains terminal")
		}
		
		if(handIsHonrouto()){
			putElligibleYakuHere.add(Yaku.HONROUTO);
			putElligibleYakuHere.remove(Yaku.CHANTA);
			//chanta will always overlap with honrouto ("every meld contains terminal or honor" VS "every tile is terminal or honor")
		}
		
		if(handIsChinrouto())
			putElligibleYakuHere.add(Yaku.YKM_CHINROUTO);
		
		if(handIsTsuuiisou())
			putElligibleYakuHere.add(Yaku.YKM_TSUUIISOU);
	}
	
	
	
	
	
	//hand contains no terminals
	public boolean handIsTanyao(){
		for (GameTile t: handInTilesForm())
			if (t.isTanyao()) return false;
		
		return true;
	}
	
	
	//contains terminal or honor in each meld
	public boolean handIsChanta(){
		for (Meld m: handInMeldForm())
			if (!(m.containsTerminal() || m.isHonorMeld()))
				return false;
		
		return true;
	}
	
	
	
	//contains terminal in each meld
	public boolean handIsJunchan(){
		for (Meld m: handInMeldForm())
			if (!m.containsTerminal())
				return false;
		
		return true;
	}
	
	
	
	//hand is only terminals and honors
	//(catches 7pairs version of honrouto too)
	public boolean handIsHonrouto(){
		for (GameTile t: handInTilesForm())
			if (!(t.isHonor() || t.isTerminal()))
				return false;
		
		return true;
	}
	
	
	
	//hand is all terminals (yakuman)
	public boolean handIsChinrouto(){		
		for (GameTile t: handInTilesForm())
			if (!t.isTerminal())
				return false;
		
		return true;
	}
	
	
	//hand is all honors (yakuman)
	public boolean handIsTsuuiisou(){
		for (GameTile t: handInTilesForm())
			if (!t.isHonor())
				return false;
		
		return true;
	}
}
