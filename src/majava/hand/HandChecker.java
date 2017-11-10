package majava.hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.tiles.HandCheckerTile;
import majava.tiles.GameTile;
import majava.tiles.ImmutableTile;
import majava.tiles.TileInterface;
import majava.util.GameTileList;
import majava.enums.MeldType;
import majava.enums.Wind;



//runs various checks on a player's hand
public class HandChecker {
	private static final int NUM_PARTNERS_NEEDED_TO_CHI = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
	private static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	
	private static final int OFFSET_CHI_L1 = 1, OFFSET_CHI_L2 = 2;
	private static final int OFFSET_CHI_M1 = -1,  OFFSET_CHI_M2 = 1;
	private static final int OFFSET_CHI_H1 = -2, OFFSET_CHI_H2 = -1;
	
	private static final int MAX_HAND_SIZE = 14;
	
	private static final Integer[] YAOCHUU_TILE_IDS = ImmutableTile.retrieveYaochuuTileIDs();
	private static final int NUMBER_OF_YAOCHUU_TILES = YAOCHUU_TILE_IDS.length;
	
	
	
	
	private final Hand myHand;
	private final GameTileList myHandTiles;
	private final List<Meld> handMelds;
	
	//call flags and partner index lists
	private boolean flagCanChiL, flagCanChiM, flagCanChiH, flagCanPon, flagCanKan, flagCanRon, flagCanPair;
	private final List<Integer> partnerIndicesChiL, partnerIndicesChiM, partnerIndicesChiH, partnerIndicesPon, partnerIndicesKan, partnerIndicesPair;
	
	private boolean flagCanAnkan, flagCanMinkan, flagCanRiichi, flagCanTsumo;
//	private Tile mTurnAnkanCandidate;
//	private Tile mTurnMinkanCandidate;
	private int indexTurnAnkanCandidate, indexTurnMinkanCandidate;
	
	
	private boolean pairHasBeenChosen = false;
	private final List<Meld> finishingMelds;
	
	private final GameTileList tenpaiWaits;
	
	
	
	
	//creates a LINK between this and the hand's tiles/melds
	public HandChecker(Hand handToCheck, GameTileList reveivedHandTiles, List<Meld> reveivedHandMelds){
		myHand = handToCheck;
		myHandTiles = reveivedHandTiles;
		handMelds = reveivedHandMelds;
		
		tenpaiWaits = new GameTileList();
		

		partnerIndicesChiL = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		partnerIndicesChiM = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		partnerIndicesChiH = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		partnerIndicesPon = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_PON);
		partnerIndicesKan = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_KAN);
		partnerIndicesPair = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_PAIR);
		finishingMelds = new ArrayList<Meld>(5);
		
		resetCallableFlags();
	}
	
	private int handSize(){return myHand.size();}
	private Wind ownerSeatWind(){return myHand.getOwnerSeatWind();}
	
	
	
	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~BEGIN MELD CHECKERS
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//checks if a candidate tile can make a chi with other tiles in the hand
	private boolean __canChiType(GameTile candidate, List<Integer> storePartnersHere, int offset1, int offset2){
		
		//return false if the hand does not contain the partners
		if (!myHandTiles.contains(candidate.getId() + offset1) || !myHandTiles.contains(candidate.getId() + offset2))
			return false;
		
		//if both parters were found in the hand, store the indices of the partners in a partner list
		storePartnersHere.addAll(Arrays.asList(myHandTiles.indexOf(candidate.getId() + offset1), myHandTiles.indexOf(candidate.getId() + offset2)));
		return true;
		
	}
	private boolean __canChiL(GameTile candidate){
		if (candidate.getFace() == '8' || candidate.getFace() == '9') return false;
		return __canChiType(candidate, partnerIndicesChiL, OFFSET_CHI_L1, OFFSET_CHI_L2);
	}
	private boolean __canChiM(GameTile candidate){
		if (candidate.getFace() == '1' || candidate.getFace() == '9') return false;
		return __canChiType(candidate, partnerIndicesChiM, OFFSET_CHI_M1, OFFSET_CHI_M2);
	}
	private boolean __canChiH(GameTile candidate){
		if (candidate.getFace() == '1' || candidate.getFace() == '2') return false;
		return __canChiType(candidate, partnerIndicesChiH, OFFSET_CHI_H1, OFFSET_CHI_H2);
	}
	
	
	//checks if a multi (pair/pon/kan) can be made with the new tile
	private boolean __canMultiType(GameTile candidate, List<Integer> storePartnersHere, int numPartnersNeeded){
		//count how many occurences of the tile, and store the indices of the occurences in tempPartnerIndices
		List<Integer> tempPartnerIndices = myHandTiles.findAllIndicesOf(candidate);
		
		//meld is possible if there are enough partners in the hand to form the meld
		if (tempPartnerIndices.size() >= numPartnersNeeded){
			
			//store the partner indices in the pon partner index list
			storePartnersHere.addAll(tempPartnerIndices.subList(0, numPartnersNeeded));
			
			return true;
		}
		return false;
	}
	private boolean __canPair(GameTile candidate){return __canMultiType(candidate, partnerIndicesPair, NUM_PARTNERS_NEEDED_TO_PAIR);}
	private boolean __canPon(GameTile candidate){return __canMultiType(candidate, partnerIndicesPon, NUM_PARTNERS_NEEDED_TO_PON);}
	private boolean __canKan(GameTile candidate){return __canMultiType(candidate, partnerIndicesKan, NUM_PARTNERS_NEEDED_TO_KAN);}
	
	
	
	//returns true if the player can call ron on the candidate tile
	private boolean __canRon(GameTile candidate){
		return tenpaiWaits.contains(candidate);
	}
	
	
	
	
	
	
	//checks if a tile is callable. returns true if a call (any call) can be made for the tile
	//if a call is possible, sets flag and populates partner index lists for that call.
	//TODO checkCallableTile
	public boolean tileIsCallable(GameTile candidate){
		boolean handIsInTenpai = isInTenpai();	//use temporary variable to avoid having to calculate twice
		
		//if candidate is not a hot tile, return false
		if (!findAllHotTiles().contains(candidate.getId()) && !handIsInTenpai) return false;
		
		
		//check which melds candidate can be called for, if any
		
		//~~~~reset flags
		resetCallableFlags();
		
		//~~~~runs checks, set flags to the check results (flags are set and lists are populated here)
		//only allow chis from the player's kamicha, or from the player's own tiles. don't check chi if candidate is an honor tile
		if (!candidate.isHonor() && (
			(candidate.getOrignalOwner() == ownerSeatWind()) || 
			(candidate.getOrignalOwner() == ownerSeatWind().kamichaWind())) ){
			flagCanChiL = __canChiL(candidate);
			flagCanChiM = __canChiM(candidate);
			flagCanChiH = __canChiH(candidate);
		}

		//check pair. if can't pair, don't bother checking pon. check pon. if can't pon, don't bother checking kan.
		if (flagCanPair = __canPair(candidate))
			if (flagCanPon = __canPon(candidate))
				flagCanKan = __canKan(candidate);
		
		//if in tenpai, check ron
		if (handIsInTenpai)
			flagCanRon = __canRon(candidate);
		
		//~~~~return true if a call (any call) can be made
		return (flagCanChiL || flagCanChiM || flagCanChiH || flagCanPon || flagCanKan || flagCanRon);
	}
	
	
	
	
	//resets call flags to false, creates new empty partner index lists
	private void resetCallableFlags(){
		flagCanChiL = flagCanChiM = flagCanChiH = flagCanPon = flagCanKan = flagCanRon = flagCanPair = false;
		flagCanAnkan = flagCanMinkan = flagCanRiichi = flagCanTsumo = false;
		
		partnerIndicesChiL.clear();
		partnerIndicesChiM.clear();
		partnerIndicesChiH.clear();
		partnerIndicesPon.clear();
		partnerIndicesKan.clear();
		partnerIndicesPair.clear();
		indexTurnAnkanCandidate = indexTurnMinkanCandidate = -1; 
	}
	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~END MELD CHECKERS
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
	
	//returns the partner indices list for a given meld type
	public List<Integer> getPartnerIndices(MeldType meldType){
		switch (meldType){
		case CHI_L: return getPartnerIndicesChiL();
		case CHI_M: return getPartnerIndicesChiM();
		case CHI_H: return getPartnerIndicesChiH();
		case PON: return getPartnerIndicesPon();
		case KAN: return getPartnerIndicesKan();
		case PAIR: return getPartnerIndicesPair();
		default: return null;
		}
	}
	public List<Integer> getPartnerIndicesChiL(){return partnerIndicesChiL;}
	public List<Integer> getPartnerIndicesChiM(){return partnerIndicesChiM;}
	public List<Integer> getPartnerIndicesChiH(){return partnerIndicesChiH;}
	public List<Integer> getPartnerIndicesPon(){return partnerIndicesPon;}
	public List<Integer> getPartnerIndicesKan(){return partnerIndicesKan;}
	public List<Integer> getPartnerIndicesPair(){return partnerIndicesPair;}
	
	
	public boolean ableToChiL(){return flagCanChiL;}
	public boolean ableToChiM(){return flagCanChiM;}
	public boolean ableToChiH(){return flagCanChiH;}
	public boolean ableToPon(){return flagCanPon;}
	public boolean ableToKan(){return flagCanKan;}
	public boolean ableToRon(){return flagCanRon;}
//	public boolean ableToPair(){return flagCanPair;}
	
	
	//turn actions
	public boolean ableToAnkan(){return flagCanAnkan;}
	public boolean ableToMinkan(){return flagCanMinkan;}
//	public boolean ableToRiichi(){return mCanRiichi;}
	public boolean ableToRiichi(){return false;}
	public boolean ableToTsumo(){return flagCanTsumo;}
	
	
	
	
	
	
	
	
	
	
	public void updateTurnActions(){
		resetCallableFlags();
		
		int index = 0;
		for (GameTile t: myHandTiles){
			
			if (__canClosedKan(t)){
				indexTurnAnkanCandidate = index;
				flagCanAnkan = true;
			}
			
			if (__canMinkan(t)){
				indexTurnMinkanCandidate = index;
				flagCanMinkan = true;
			}
			
			index++;
		}
		
		if (__canTsumo()){
			flagCanTsumo = true;
		}
	}
	
	private boolean __canTsumo(){return isComplete();}
	
	private boolean __canMinkan(GameTile candidate){
		for (Meld m: handMelds)
			if (m.canMinkanWith(candidate))
				return true;
		return false;
	}
	
	public int getCandidateMinkanIndex(){return indexTurnMinkanCandidate;}
	public int getCandidateAnkanIndex(){return indexTurnAnkanCandidate;}
	
	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------
	//----BEGIN FINDERS
	//---------------------------------------------------------------------------------------------------
	
	//returns a list of hot tile IDs for ALL tiles in the hand
	private List<Integer> findAllHotTiles(List<GameTile> tiles){
		List<Integer> allHotTileIds = new ArrayList<Integer>(32);
		List<Integer> singleTileHotTiles = null;
		
		//get hot tiles for each tile in the hand
		for (GameTile t: tiles){
			singleTileHotTiles = __findHotTilesOfTile(t);
			for (Integer i: singleTileHotTiles) if (!allHotTileIds.contains(i)) allHotTileIds.add(i);
		}
		
		return allHotTileIds;
	}
	private List<Integer> findAllHotTiles(){return findAllHotTiles(myHandTiles);}
	
	/*
	private static method: __findHotTilesOfTile
	returns a list of integer IDs of hot tiles, for tile t
	
	add t to the list (because pon)
	if (t is not honor): add all possible chi partners to the list
	return list
	*/
	private static List<Integer> __findHotTilesOfTile(final TileInterface t){
		
		List<Integer> hotTileIds = new ArrayList<Integer>(5); 
		int id = t.getId(); char face = t.getFace();
		
		//a tile is always its own hot tile (pon/kan/pair)
		hotTileIds.add(id);
		
		//add possible chi partners, if tile is not an honor tile
		if (!t.isHonor()){
			if (face != '1' && face != '2') hotTileIds.add(id - 2);
			if (face != '1') hotTileIds.add(id - 1);
			if (face != '9') hotTileIds.add(id + 1);
			if (face != '8' && face != '9') hotTileIds.add(id + 2);
		}
		//return list of integer IDs
		return hotTileIds;
	}
	
	
	
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//xxxxBEGIN DEMO METHODS
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//returns a list of callable tile IDs for ALL tiles in the hand
	private List<Integer> __findAllCallableTiles(){
		
		List<Integer> allCallableTileIds = new  ArrayList<Integer>(4);
		List<Integer> hotTileIds = new ArrayList<Integer>(34);
		
		final boolean TEST_ALL_TILES = false;
		
		if (TEST_ALL_TILES) for (int i = 1; i <= 34; i++) hotTileIds.add(i);
		else hotTileIds = findAllHotTiles();
		
		//examine all hot tiles
		for (Integer i: hotTileIds)
			//if tile i is callable
			if (tileIsCallable(new GameTile(i)))
				//add its id to the list of callable tiles
				allCallableTileIds.add(i);
		
		return allCallableTileIds;
	}
	public List<Integer> DEMOfindAllCallableTiles(){return __findAllCallableTiles();}
	public List<Integer> DEMOfindAllHotTiles(){return findAllHotTiles();}
	public void findAllMachis(){}
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//xxxxEND DEMO METHODS
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	
	
	//---------------------------------------------------------------------------------------------------
	//----END FINDERS
	//---------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//***************************************************************************************************
	//***************************************************************************************************
	//****BEGIN TENPAI CHECKERS
	//***************************************************************************************************
	//***************************************************************************************************
	
	
	
	//returns true if a hand is complete (it is a winning hand)
	public boolean isComplete(){
		return (isCompleteKokushi() || isCompleteChiitoitsu() || isCompleteNormal());
	}
	
	
	
	
	
	
	
	
	//returns true if the hand is in tenpai
	public boolean isInTenpai(){
		
		boolean isKokushiTenpai = false, isChiitoitsuTenpai = false, isNormalTenpai = false;
		
//		mTenpaiWaits = new TileList();
		tenpaiWaits.clear();
		
		//check for kokushi musou tenpai, waits are also found here
		isKokushiTenpai = isTenpaiKokushi();
		if (isKokushiTenpai){
//			mTenpaiWaits.clear();
			tenpaiWaits.addAll(__getKokushiWaits());
		}
		else{

			//check if the hand is in normal tenpai, waits are also found here (don't check if in already kokushi tenpai)
			isNormalTenpai = (!__findNormalTenpaiWaits().isEmpty());
			
			//check for chiitoitsu tenpai, wait is also found here (only check if no kokushi tenpai and no normal tenpai)
			if (!isNormalTenpai)
				isChiitoitsuTenpai = isTenpaiChiitoitsu();			
		}		
		
		return (isKokushiTenpai || isChiitoitsuTenpai || isNormalTenpai);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns true if the hand is in tenpai for kokushi musou
	//if this is true, it means that: handsize >= 13, hand has at least 12 different TYC tiles
	public boolean isTenpaiKokushi(){
		
		//if any melds have been made, kokushi musou is impossible, return false
		if (handSize() < MAX_HAND_SIZE-1) return false;
		//if the hand contains even one non-honor tile, return false
		for (GameTile t: myHandTiles) if (!t.isYaochuu()) return false;
		
		
		//check if the hand contains at least 12 different TYC tiles
		int countTYC = 0;
		for (int id: YAOCHUU_TILE_IDS)
			if (myHandTiles.contains(id))
				countTYC++;

		//return false if the hand doesn't contain at least 12 different TYC tiles
		if (countTYC < NUMBER_OF_YAOCHUU_TILES - 1) return false;
		
		return true;
	}
	
	//returns true if a 14-tile hand is a complete kokushi musou
	public boolean isCompleteKokushi(){
		if ((handSize() == MAX_HAND_SIZE) &&
			(isTenpaiKokushi()) &&
			(__getKokushiWaits().size() == NUMBER_OF_YAOCHUU_TILES))
			return true;
		
		return false;
	}

	//returns a list of the hand's waits, if it is in tenpai for kokushi musou
	//returns an empty list if not in kokushi musou tenpai
	private GameTileList __getKokushiWaits(){
		
		GameTileList waits = new GameTileList(1);
		
		GameTile missingTYC = null;
		if (isTenpaiKokushi()){
			//look for a Yaochuu tile that the hand doesn't contain
			for (Integer id: YAOCHUU_TILE_IDS)
				if (!myHandTiles.contains(id))
					missingTYC = new GameTile(id);
			
			//if the hand contains exactly one of every Yaochuu tile, then it is a 13-sided wait for all Yaochuu tiles
			if (missingTYC == null)
				waits = new GameTileList(YAOCHUU_TILE_IDS);
			else
				//else, if the hand is missing a Yaochuu tile, that missing tile is the hand's wait
				waits.add(missingTYC);
		}
		
		return waits;
	}
	public GameTileList DEMOgetKokushiWaits(){return __getKokushiWaits();}
	
	
	
	
	
	
	
	
	
	//checks if the hand is in tenpai for chiitoitsu, and modifies the list of waits if so 
	public boolean isTenpaiChiitoitsu(final GameTileList handTiles){
		
		GameTile missingTile = null;
		//conditions:
		//hand must be 13 tiles (no melds made)
		//hand must have exactly 7 different types of tiles
		//hand must have no more than 2 of each tile
		
		//if any melds have been made, chiitoitsu is impossible, return false
		if (myHand.numberOfMeldsMade() > 0) return false;
		
		//the hand should have exactly 7 different types of tiles (4 of a kind != 2 pairs)
		if (handTiles.makeCopyNoDuplicates().size() != 7) return false;

		//the hand must have no more than 2 of each tile
		for(GameTile t: handTiles){
			switch(handTiles.findHowManyOf(t)){
			case 1: missingTile = t;
			case 2: break;//intentionally blank
			default: return false;
			}
		}
		
		//at this point, we know that the hand is in chiitoitsu tenpai
		
		
		//add the missing tile to the wait list (it will be the only wait)
		tenpaiWaits.clear();
		if (missingTile != null) tenpaiWaits.add(missingTile);
		return true;
	}
	public boolean isTenpaiChiitoitsu(){return isTenpaiChiitoitsu(myHandTiles);}
	
	
	
	
	
	//returns true if a 14-tile hand is a complete chiitoitsu
	public boolean isCompleteChiitoitsu(final GameTileList handTiles){
		
		handTiles.sort();
		
		//chiitoitsu is impossible if a meld has been made
		if (handTiles.size() < MAX_HAND_SIZE) return false;
		
		//even tiles should equal odd tiles, if chiitoitsu
		GameTileList evenTiles = handTiles.getMultiple(0,2,4,6,8,10,12);
		GameTileList oddTiles = handTiles.getMultiple(1,3,5,7,9,11,13);
		return evenTiles.equals(oddTiles);
	}
	public boolean isCompleteChiitoitsu(){return isCompleteChiitoitsu(myHandTiles.clone());}
	
	
	
	public boolean DEMOchiitoitsuInTenpai(){return isTenpaiChiitoitsu();}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//============================================================================
	//====BEGIN CAN MELDS
	//============================================================================
	
	/*
	private method: __canClosedChiType
	returns true if a candidate tile can make a chi with other tiles in the hand
	
	input: candidate is the tile to search for chi partners for
	 	   offset1 and offset2 are the offsets of candidate's ID to look for
	
	return true if hand contains both (candidate's ID + offset1) and (candidate's ID + offset2)
	return false if either of them are missing
	*/
	private boolean __canClosedChiType(GameTile candidate, GameTileList checkTiles, int offset1, int offset2){
		return (checkTiles.contains(candidate.getId() + offset1) && checkTiles.contains(candidate.getId() + offset2));
	}
	private boolean __canClosedChiL(GameTile candidate, GameTileList checkTiles){return ((candidate.getFace() != '8' && candidate.getFace() != '9') && __canClosedChiType(candidate, checkTiles, OFFSET_CHI_L1, OFFSET_CHI_L2));}
	private boolean __canClosedChiM(GameTile candidate, GameTileList checkTiles){return ((candidate.getFace() != '1' && candidate.getFace() != '9') && __canClosedChiType(candidate, checkTiles, OFFSET_CHI_M1, OFFSET_CHI_M2));}
	private boolean __canClosedChiH(GameTile candidate, GameTileList checkTiles){return ((candidate.getFace() != '1' && candidate.getFace() != '2') && __canClosedChiType(candidate, checkTiles, OFFSET_CHI_H1, OFFSET_CHI_H2));}
	
	
	/*
	private method: __canClosedMultiType
	returns true if the hand contains at least "numPartnersNeeded" copies of candidate
	
	input: candidate is the tile to search for copies of, numPartnersNeeded is the number of copies needed
	*/
	private boolean __canClosedMultiType(GameTile candidate, int numPartnersNeeded){
		//count how many occurences of the tile
		return myHandTiles.findHowManyOf(candidate) >= numPartnersNeeded;
	}
//	private boolean __canClosedPair(GameTile candidate){return __canClosedMultiType(candidate, NUM_PARTNERS_NEEDED_TO_PAIR + 1);}
//	private boolean __canClosedPon(GameTile candidate){return __canClosedMultiType(candidate, NUM_PARTNERS_NEEDED_TO_PON + 1);}
	private boolean __canClosedKan(GameTile candidate){return __canClosedMultiType(candidate, NUM_PARTNERS_NEEDED_TO_KAN + 1);}
	
	
		
	/*
	method: checkMeldableTile
	checks if a tile is meldable
	if a call is possible, pushes the meld type on meldStack and returns true
	
	input: candidate is the tile to check if callable
		   meldStack will receive the types of melds that are possible for candidate
		   
	returns true if the tile can make a ChiL, ChiM, ChiH, Pon, or Pair
	
	
	check pon/pair, check chi
	(order of stack should be top->L,M,H,Pon,Pair)
	return true if meldStack is not empty
	*/
	private boolean __checkMeldableTile(HandCheckerTile candidate, GameTileList checkTiles){
		
		
		//check pon. if can pon, push both pon and pair. if can't pon, check pair.
		switch(checkTiles.findHowManyOf(candidate)){
		case 2: candidate.mstackPush(MeldType.PAIR); break;
		case 4: case 3: candidate.mstackPush(MeldType.PAIR); candidate.mstackPush(MeldType.PON);
		default: break;
		}
		
		//don't check chi if candidate is an honor tile
		if (!candidate.isHonor()){
			if (__canClosedChiH(candidate, checkTiles)) candidate.mstackPush(MeldType.CHI_H);
			if (__canClosedChiM(candidate, checkTiles)) candidate.mstackPush(MeldType.CHI_M);
			if (__canClosedChiL(candidate, checkTiles)) candidate.mstackPush(MeldType.CHI_L);
		}
		
		//~~~~return true if a call (any call) can be made
		return (!candidate.mstackIsEmpty());
	}
	
	//============================================================================
	//====END CAN MELDS
	//============================================================================
	
	
	
	
	public boolean DEMOisComplete(){return isComplete();}
	
	
	

	
	
	
	private GameTileList __findNormalTenpaiWaits(){
		//TODO this is find tenpai waits
		
		final GameTileList waits = new GameTileList();
		
		final GameTileList handTilesCopy = myHandTiles.clone();
		final List<Integer> hotTileIDs = findAllHotTiles();
		GameTile currentHotTile = null;
		
		for (Integer id: hotTileIDs){
			//get a hot tile (and mark it with the hand's seat wind, so chi is valid)
			currentHotTile = new GameTile(id);
			currentHotTile.setOwner(ownerSeatWind());
			
			//make a copy of the hand, add the current hot tile to that copy
			handTilesCopy.add(currentHotTile);
			
			//check if the copy, with the added tile, is complete
			if (__isCompleteNormal(handTilesCopy)) waits.add(currentHotTile);
			
			handTilesCopy.remove(currentHotTile);
		}
		
		//store the waits in mTenpaiWaits, if there are any
		if (!waits.isEmpty()) tenpaiWaits.addAll(waits);
		return waits;
	}
	public GameTileList DEMOfindNormalTenpaiWaits(){return __findNormalTenpaiWaits();}
	
	
	
	
	
	
	
	/*
	private method: __isCompleteNormal
	returns true if list of handTiles is complete (is a winning hand)
	*/
	public boolean __isCompleteNormal(GameTileList handTiles){
		
		if ((handTiles.size() % 3) != 2) return false;
		
		//make a list of checkTiles out of the handTiles
		GameTileList checkTiles = handTiles.makeCopyWithCheckers();
		checkTiles.sort();
		
		
		//populate stacks
		if (!__populateMeldStacks(checkTiles)) return false;
		
		pairHasBeenChosen = false;
		finishingMelds.clear();
		
		return __isCompleteNormalHand(checkTiles);
	}
	//overloaded, checks mHandTiles by default
	public boolean isCompleteNormal(){return __isCompleteNormal(myHandTiles);}
	
	
	
	/*
	private method: __populateMeldStacks
	populates the meld type stacks for all of the tile in checkTiles
	returns true if all tiles can make a meld, returns false if a tile cannot make a meld
	*/
	private boolean __populateMeldStacks(GameTileList checkTiles){
		for (GameTile t: checkTiles)
			if (!__checkMeldableTile((HandCheckerTile)t, checkTiles)) return false;
		
		return true;
	}
	
	
	
	
	
	
	
	
	/*
	private method: isCompleteNormalHand
	checks if a hand is complete
	if a hand is complete, should populate meld lists and flags and stuff (it doesn't yet)
	
	input: handTiles is the list of hand tiles to check for completeness
		   listMTSL is a list of MeldType stacks corresponding to each tile in handTiles
		   
	returns true if the list of hand tiles is complete (is a winner)
	
	
	if (handTiles is empty): return true (an empty hand is complete)
	currentTile = first tile in handTiles
	
	while (currentTile's stack of valid meld types is not empty)
		
		currentTileMeldType = pop a meldtype off of currentTile's stack (this is the meld type we will try)
		
		if ((currentTile's partners for the meld are still in the hand) && (if pairHasBeenChosen, currentTileMeldType must not be a pair))
			
			if (currentTileMeldType is pair): pairHasBeenChosen = true (take the pair privelege)
			
			partnerIndices = find the indices of currentTile's partners for the currentTileMeldType
			toMeldTiles = list of tiles from the hand, includes currentTile and its partner tiles
			
			handTilesMinusThisMeld = copy of handTiles, but with the toMeldTiles removed
			listMTSLMinusThisMeld = copy of listMTSL, but with the stacks for toMeldTiles removed
			
			if (isCompleteHand(tiles/stacks minus this meld)) (recursive call)
				return true (the hand is complete)
			else
				if (currentTileMeldType is pair): pairHasBeenChosen = false (relinquish the pair privelege)
			end if
			
		end if
		
	end while
	return false (currentTile could not make any meld, so the hand cannot be complete)
	*/
	private boolean __isCompleteNormalHand(GameTileList checkTiles){
		
		//if the hand is empty, it is complete
		if (checkTiles.isEmpty()) return true;
		
		GameTileList toMeldTiles = null;
		GameTileList checkTilesMinusThisMeld = null;

		HandCheckerTile currentTile = null;
		MeldType currentTileMeldType;
		int[] currentTileParterIDs = null;
		boolean currentTilePartersAreStillHere = true;
		List<Integer> partnerIndices = null;
		
		//currrentTile = first tile in the hand
		currentTile = (HandCheckerTile)checkTiles.getFirst();
		
		//loop until every possible meld type has been tried for the current tile
		while(currentTile.mstackIsEmpty() == false){
			
			//~~~~Verify that currentTile's partners are still in the hand
			//currentTileParterIDs = list of IDs of partners for currentTile's top MeldType
			currentTileParterIDs = currentTile.mstackTopParterIDs();
			
			//get the top meldType from currentTile's stack
			currentTileMeldType = currentTile.mstackPop();	//(remove it)
			
			
			//check if currentTile's partners are still in the hand
			currentTilePartersAreStillHere = true;
			if (currentTileMeldType.isChi()){
				if (!checkTiles.contains(currentTileParterIDs[0]) || !checkTiles.contains(currentTileParterIDs[1]))
					currentTilePartersAreStillHere = false;
			}
			else{
				if (currentTileMeldType == MeldType.PAIR && checkTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PAIR + 1)
					currentTilePartersAreStillHere = false;
				if (currentTileMeldType == MeldType.PON && checkTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PON + 1)
					currentTilePartersAreStillHere = false;
			}
			
			
			
			//~~~~Separate the tiles if the meld is possible
			//if (currentTile's partners for the meld are still in the hand) AND (if pair has already been chosen, currentTileMeldType must not be a pair)
			if (currentTilePartersAreStillHere && !(pairHasBeenChosen && currentTileMeldType == MeldType.PAIR)){
				
				//take the pair privelige
				if (currentTileMeldType == MeldType.PAIR)
					pairHasBeenChosen = true;
				
				
				//~~~~Find the inidces of currentTile's partners
				partnerIndices = new ArrayList<Integer>();
				
				//if chi, just find the partners
				if (currentTileMeldType.isChi()){
					partnerIndices.add(checkTiles.indexOf(currentTileParterIDs[0]));
					partnerIndices.add(checkTiles.indexOf(currentTileParterIDs[1]));
				}
				else{
					//else if pon/pair, make sure you don't count the tile itsef
					partnerIndices = checkTiles.findAllIndicesOf(currentTile);
					
					//trim the lists down to size to fit the meld type
					if (currentTileMeldType == MeldType.PAIR) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PAIR) partnerIndices.remove(partnerIndices.size() - 1);
					if (currentTileMeldType == MeldType.PON) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PON) partnerIndices.remove(partnerIndices.size() - 1);
				}
				

				//~~~~Add the tiles to a meld tile list
				//make a copy of the hand, then remove the meld tiles from the copy and add them to the meld
//				checkTilesMinusThisMeld = HandCheckerTile.makeCopyOfListWithCheckers(checkTiles);
				checkTilesMinusThisMeld = checkTiles.makeCopyWithCheckers();
				toMeldTiles = new GameTileList();
				
				while (!partnerIndices.isEmpty())
					toMeldTiles.add(checkTilesMinusThisMeld.remove( partnerIndices.remove(partnerIndices.size() - 1).intValue()) );
				
				toMeldTiles.add(checkTilesMinusThisMeld.removeFirst());
				
				
				
				
				//~~~~Recursive call, check if the hand is still complete without the removed meld tiles
				if (__isCompleteNormalHand(checkTilesMinusThisMeld)){
					finishingMelds.add(new Meld(toMeldTiles.clone(), currentTileMeldType));	//add the meld tiles to the finishing melds stack
					return true;
				}
				else{
					//relinquish the pair privelege, if it was taken
					if (currentTileMeldType == MeldType.PAIR)
						pairHasBeenChosen = false;
				}
				
				
			}
			
		}
		
		return false;
	}
	

	
	//returns the list of tenpai waits
	public GameTileList getTenpaiWaits(){return tenpaiWaits;}
	
	
	//***************************************************************************************************
	//***************************************************************************************************
	//****END TENPAI CHECKERS
	//***************************************************************************************************
	//***************************************************************************************************
	
	
	
	
	public void DEMOprintFinishingMelds(){for (Meld m: finishingMelds) System.out.println(m.toString());}
	
	
	
	
	
	
	//returns true if the hand is fully concealed, false if an open meld has been made
	public boolean getClosedStatus(){
		for (Meld m: handMelds)
			if (m.isOpen())
				return false;
		return true;
	}
	
	
	
	//runs test code
//	public static void main(String[] args){majava.control.testcode.DemoHandGen.main(null);}
	
}
