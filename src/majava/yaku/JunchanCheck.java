package majava.yaku;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class JunchanCheck extends AbstractYakuCheck {
	
	public JunchanCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		
		if(handIsJunchan())
			putElligibleYakuHere.add(Yaku.JUNCHAN);
		
		//overlap with yakuman, but it doesn't matter
	}
	
	
	
	
	//contains terminal in each meld
	public boolean handIsJunchan(){
		for (Meld m: handInMeldForm())
			if (!m.containsTerminal())
				return false;
		
		return true;
	}
	
}
