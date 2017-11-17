package majava.hand;

import java.util.List;

import majava.enums.Wind;
import majava.tiles.Janpai;
import majava.util.GameTileList;

public class TurnActionabilityChecker {
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
	private static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	private static final int OFFSET_CHI_L1 = 1, OFFSET_CHI_L2 = 2;
	private static final int OFFSET_CHI_M1 = -1,  OFFSET_CHI_M2 = 1;
	private static final int OFFSET_CHI_H1 = -2, OFFSET_CHI_H2 = -1;
	private static final int INDEX_NOT_FOUND = -1;
	
	private static final int MAX_HAND_SIZE = 14;
	
	private static final Integer[] YAOCHUU_TILE_IDS = Janpai.retrieveYaochuuTileIDs();
	private static final int NUMBER_OF_YAOCHUU_TILES = YAOCHUU_TILE_IDS.length;
	
	
	
	
	private final Hand myHand;
	private final GameTileList myHandTiles;
	
	//creates a link between this and the hand's tiles/melds
	public TurnActionabilityChecker(Hand handToCheck, GameTileList reveivedHandTiles, List<Meld> reveivedHandMelds){
		myHand = handToCheck;
		myHandTiles = reveivedHandTiles;
	}
	
	private Wind ownerSeatWind(){return myHand.getOwnerSeatWind();}
	
}
