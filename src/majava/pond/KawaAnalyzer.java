package majava.pond;

import majava.Pond;
import majava.enums.Wind;
import majava.tiles.PondTile;
import majava.util.PlayerList;


public class KawaAnalyzer {
	private static final int E=0, S=1, W=2, N=3;
	
	private final Pond e, s, w, n;
//	private final KawaRotator rotator;
	
	
	public KawaAnalyzer(Pond pe, Pond ps, Pond pw, Pond pn){
		e = pe; s = ps; w = pw; n = pn;
//		rotator = new KawaRotator(e, s, w, n);
	}
	public KawaAnalyzer(PlayerList plyrs){this(plyrs.seat(Wind.EAST).getPond(), plyrs.seat(Wind.SOUTH).getPond(), plyrs.seat(Wind.WEST).getPond(), plyrs.seat(Wind.NORTH).getPond());}
	
	
	
	
	public PondTile lastDiscard(){
		PondTile latestTile = null;
		KawaRotator rotator = new KawaRotator(e, s, w, n);
		
		//first discard is always the first tile in east's pond
		latestTile = currentPond.getCurrentTile();
		
		
		
		if (latestTile.wasCalled()){
			
		}
		
		return latestTile;
	}
	
	
	
	
}
