package majava.yaku;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.util.YakuList;

public class KotsuYakuCheck extends AbstractYakuCheck {
	
	public KotsuYakuCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){

		if (handIsToitoi())
			putElligibleYakuHere.add(Yaku.TOITOI);
		
		
		if(handIsSuuankou())
			putElligibleYakuHere.add(Yaku.YKM_SUUANKOU);
		if(handIsSanankou())
			putElligibleYakuHere.add(Yaku.SANANKOU);
		//will not overlap
		
		
		if(handIsSuukantsu())
			putElligibleYakuHere.add(Yaku.YKM_SUUKANTSU);
		if(handIsSankantsu())
			putElligibleYakuHere.add(Yaku.SANKANTSU);
		//will not overlap
	}
	
	
	
	
	//no melds are chi (all are pon/kan)
	public boolean handIsToitoi(){
		if (handIsKokushiOrChiitoi()) return false;
		
		for (Meld m: handInMeldForm())
			if (m.isChi())
				return false;
		
		return true;
	}
	
	
	
	
	//4ankou
	public boolean handIsSuuankou(){return numberOfAnkou() == 4;}
	//3ankou
	public boolean handIsSanankou(){return numberOfAnkou() == 3;}
	
	private int numberOfAnkou(){
		int numAnkou = 0;
		for (Meld m: handInMeldForm())
			if (m.isPonKan() && m.isClosed())
				numAnkou++;
		return numAnkou;
	}
	
	
	
	
	//4kans
	public boolean handIsSuukantsu(){return hand.numberOfKansMade() == 4;}
	//3kans
	public boolean handIsSankantsu(){return hand.numberOfKansMade() == 3;}
	
}
