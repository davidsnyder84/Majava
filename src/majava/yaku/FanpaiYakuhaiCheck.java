package majava.yaku;

import majava.hand.AgariHand;
import majava.hand.Meld;
import majava.tiles.GameTile;
import majava.util.YakuList;

public class FanpaiYakuhaiCheck extends AbstractYakuCheck {
	private static final GameTile TON = new GameTile(28), NAN = new GameTile(29), SHA = new GameTile(30), PEI = new GameTile(31), HAKU = new GameTile(32), HATSU = new GameTile(33), CHUN = new GameTile(34);
	
	public FanpaiYakuhaiCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		if(handHasPonOf(HAKU)) putElligibleYakuHere.add(Yaku.YAKUHAI_DRAGON_HAKU);
		if(handHasPonOf(HATSU)) putElligibleYakuHere.add(Yaku.YAKUHAI_DRAGON_HATSU);
		if(handHasPonOf(CHUN)) putElligibleYakuHere.add(Yaku.YAKUHAI_DRAGON_CHUN);
		
		if(kazeIsValueable(TON) && handHasPonOf(TON)) putElligibleYakuHere.add(Yaku.YAKUHAI_WIND_EAST);
		if(kazeIsValueable(NAN) && handHasPonOf(NAN)) putElligibleYakuHere.add(Yaku.YAKUHAI_WIND_SOUTH);
		if(kazeIsValueable(SHA) && handHasPonOf(SHA)) putElligibleYakuHere.add(Yaku.YAKUHAI_WIND_WEST);
		if(kazeIsValueable(PEI) && handHasPonOf(PEI)) putElligibleYakuHere.add(Yaku.YAKUHAI_WIND_NORTH);
	}
	
	
	
	
	private boolean handHasPonOf(GameTile tile){
		for (Meld m: handInMeldForm())
			if (m.isPon() && m.contains(tile))
				return true;
		return false;
	}
	
	
	private boolean kazeIsValueable(GameTile kazeTile){
		return kazeIsBakaze(kazeTile) || kazeIsJikaze(kazeTile);
	}
	private boolean kazeIsBakaze(GameTile kazeTile){
		///////////////////////////NOT IMPLEMENTED
		///////////////need roundinfo for this
		return false;
	}
	private boolean kazeIsJikaze(GameTile kazeTile){
		return (kazeTile.getFace()) == (hand.getOwnerSeatWind().toChar());
	}
	
}
