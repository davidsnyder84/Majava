package majava.yaku;


import majava.hand.AgariHand;
import majava.tiles.GameTile;
import majava.util.GTL;
import majava.util.YakuList;

public class LimitRyuuiisouCheck extends AbstractYakuCheck {
	private static final GTL GREEN_TILES = new GTL(18+2, 18+3, 18+4, 18+6, 18+8, 33);
	
	public LimitRyuuiisouCheck(AgariHand h){super(h);}
	
	
	@Override
	public void findElligibleYaku(final YakuList putElligibleYakuHere){
		if(handIsRyuuiisou())
			putElligibleYakuHere.add(Yaku.YKM_RYUUIISOU);
	}
	
	
	
	public boolean handIsRyuuiisou(){
		return handContainsOnlyGreenTiles();
	}
	
	private boolean handContainsOnlyGreenTiles(){
		for (GameTile t: handInTilesForm())
			if (!GREEN_TILES.contains(t))
				return false;	//return false if a non-green tile is found
		
		return true;
	}
	
	
	public static GTL listOfGreenTiles(){return GREEN_TILES.clone();}
}
