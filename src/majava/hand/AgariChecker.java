package majava.hand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import majava.enums.MeldType;
import majava.enums.Wind;
import majava.tiles.GameTile;
import majava.tiles.HandCheckerTile;
import majava.tiles.ImmutableTile;
import majava.tiles.TileInterface;
import majava.util.GameTileList;

public class AgariChecker {
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	private static final int OFFSET_CHI_L1 = 1, OFFSET_CHI_L2 = 2;
	private static final int OFFSET_CHI_M1 = -1,  OFFSET_CHI_M2 = 1;
	private static final int OFFSET_CHI_H1 = -2, OFFSET_CHI_H2 = -1;
	
	private static final int MAX_HAND_SIZE = 14;
	
	private static final Integer[] YAOCHUU_TILE_IDS = ImmutableTile.retrieveYaochuuTileIDs();
	private static final int NUMBER_OF_YAOCHUU_TILES = YAOCHUU_TILE_IDS.length;
	
	private final Hand myHand;
	private final GameTileList myHandTiles;
	
	//creates a link between this and the hand's tiles/melds
	public AgariChecker(Hand handToCheck, GameTileList reveivedHandTiles, List<Meld> reveivedHandMelds){
		myHand = handToCheck;
		myHandTiles = reveivedHandTiles;
	}
	
	private Wind ownerSeatWind(){return myHand.getOwnerSeatWind();}
	

	//***************************************************************************************************
	//***************************************************************************************************
	//****BEGIN TENPAI CHECKERS
	//***************************************************************************************************
	//***************************************************************************************************
	
	
	
	
	
	
	
	
	
	
	
}
