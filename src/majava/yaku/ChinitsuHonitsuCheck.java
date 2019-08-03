package majava.yaku;

import majava.enums.Suit;
import majava.hand.AgariHand;
import majava.tiles.GameTile;
import majava.util.YakuList;

public class ChinitsuHonitsuCheck extends AbstractYakuCheck {
	
	public ChinitsuHonitsuCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		if (handIsChinitsu())
			putElligibleYakuHere.add(Yaku.CHINITSU);
		
		if (handIsHonitsu())
			putElligibleYakuHere.add(Yaku.HONITSU);
		
		//note: Chinitsu and Honitsu will never overlap with eachother
	}
	
	
	
	//one suit, no honors
	public boolean handIsChinitsu(){
		return handContainsOnlyOneSuit() && !handContainsHonors();
	}
	
	//one suit, plus some honors
	public boolean handIsHonitsu(){		
		return handContainsOnlyOneSuit() && handContainsHonors();
	}
	
	
	private boolean handContainsOnlyOneSuit(){
		boolean manInHand = false, pinInHand = false, souInHand = false;		
		for (GameTile t: handInTilesForm()){
			manInHand |= t.getSuit() == Suit.MANZU.toChar();
			pinInHand |= t.getSuit() == Suit.PINZU.toChar();
			souInHand |= t.getSuit() == Suit.SOUZU.toChar();
		}
		
		//3-way XOR
		return ((manInHand^pinInHand^souInHand)^(manInHand && pinInHand && souInHand));
		
	}
	
	private boolean handContainsHonors(){
		for (GameTile t: handInTilesForm())
			if (t.isHonor())
				return true;
		
		return false;
	}
	
}
