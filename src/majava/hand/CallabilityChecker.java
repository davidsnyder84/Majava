package majava.hand;

import java.util.List;

import majava.enums.Wind;
import majava.tiles.ImmutableTile;
import majava.util.GameTileList;

public class CallabilityChecker {
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
	private static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	private static final int OFFSET_CHI_L1 = 1, OFFSET_CHI_L2 = 2;
	private static final int OFFSET_CHI_M1 = -1,  OFFSET_CHI_M2 = 1;
	private static final int OFFSET_CHI_H1 = -2, OFFSET_CHI_H2 = -1;
	private static final int INDEX_NOT_FOUND = -1;
	
	private static final int MAX_HAND_SIZE = 14;
	
	private static final Integer[] YAOCHUU_TILE_IDS = ImmutableTile.retrieveYaochuuTileIDs();
	private static final int NUMBER_OF_YAOCHUU_TILES = YAOCHUU_TILE_IDS.length;
	
	
	
	
	private final Hand myHand;
	private final GameTileList myHandTiles;
	
	//creates a link between this and the hand's tiles/melds
	public CallabilityChecker(Hand handToCheck, GameTileList reveivedHandTiles, List<Meld> reveivedHandMelds){
		myHand = handToCheck;
		myHandTiles = reveivedHandTiles;
	}

	
	private Wind ownerSeatWind(){return myHand.getOwnerSeatWind();}
	
	
	//unused code from HandChecker, just for reference
	//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
//	private class Callability{
//	
//	private final List<Integer> mPartnerIndicesChiL, mPartnerIndicesChiM, mPartnerIndicesChiH, mPartnerIndicesPon, mPartnerIndicesKan, mPartnerIndicesPair;
//	private boolean mCanChiL, mCanChiM, mCanChiH, mCanPon, mCanKan, mCanRon, mCanPair;
//	private GameTile mCallCandidate;
//	
//
//	private boolean mCanAnkan, mCanMinkan, mCanRiichi, mCanTsumo;
////	private Tile mTurnAnkanCandidate;
////	private Tile mTurnMinkanCandidate;
//	private int mTurnAnkanCandidateIndex, mTurnMinkanCandidateIndex;
//	
//	public Callability(){
//		mPartnerIndicesChiL = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
//		mPartnerIndicesChiM = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
//		mPartnerIndicesChiH = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
//		mPartnerIndicesPon = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_PON);
//		mPartnerIndicesKan = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_KAN);
//		mPartnerIndicesPair = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_PAIR);
//		
//		resetCallableFlags();
//		mCallCandidate = null;
//	}
//	
//	public void resetCallableFlags(){
//		mCanChiL = mCanChiM = mCanChiH = mCanPon = mCanKan = mCanRon = mCanPair = false;
//		mCanAnkan = mCanMinkan = mCanRiichi = mCanTsumo = false;
//		
//		mPartnerIndicesChiL.clear();
//		mPartnerIndicesChiM.clear();
//		mPartnerIndicesChiH.clear();
//		mPartnerIndicesPon.clear();
//		mPartnerIndicesKan.clear();
//		mPartnerIndicesPair.clear();
//		
//		mTurnAnkanCandidateIndex = mTurnMinkanCandidateIndex = -1; 
//	}
//}
}
