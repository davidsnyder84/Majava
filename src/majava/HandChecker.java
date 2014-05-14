package majava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.tiles.HandCheckerTile;
import majava.tiles.Tile;


/*
Class: HandChecker
runs various checks on a player's hand

data:
	mHand - the hand that this object will be checking
	mHandTiles - list of mHand's tiles
	mHandMelds - list of mHand's melds
	
	mClosed - is true if the hand is fully concealed (no calls made on other discards)
	mTenpaiStatus - is true if the hand is in tenpai (one tile away from winning)
	
	mCallCandidate - the most recently discarded tile. checks are run on it to see if it can be called.
	mCanChiL, etc - flags, set to true if a call can be made on mCallCandidate
	mCanAnkan, etc - flags, set to true if an action can be made
	mPartnerIndicesChiL, etc - hold the hand indices of meld partners for mCallCandidate, if it can be called
	
	mTenpaiWaits - if the hand is in tenpai, this holds a list of the hand's waits 

methods:
	constructors:
	3-arg: requires a hand, a list of the hand's tiles, and a list of the hand's melds (creates a LINK between this and the hand's tiles/melds)
 	
 	public:
 	
	 	mutators:
	 	checkCallableTile - checks if a tile is callable. if it is callable, sets flag and populates partner index lists for that call
		updateClosedStatus - checks if the hand is closed or open, sets flag accordingly
		updateTenpaiStatus - checks if the hand is in tenpai, sets flag accordingly
		updateTurnActions - updates what turn actions are possible, sets flags accordingly
	 	
	 	isComplete - returns true if a hand is complete (modifies wait lists)
	 	isTenpaiKokushi, isCompleteKokushi - checks completeness, tenpai for kokushi (modifies wait lists)
	 	isTenpaiChiitoitsu, isCompleteChiitoitsu - checks completeness, tenpai for chiitoi (modifies wait lists)
	 	
	 	
	 	accessors:
		getClosedStatus - returns true if the hand is fully concealed, false if an open meld has been made
		getTenpaiStatus - returns true if the hand is in tenpai
		getTenpaiWaits - returns the list of tenpai waits
		
		ableToChiL, etc - returns true if the corresponding call can be made on mCallCandidate
		ableToAnkan, etc - returns true if the corresponding action is possible
		getCallCandidate - returns mCallCandidate
		getPartnerIndices - returns the partner indices list for a given meld type
		getCandidateAn/MinkanIndex - get the index of the tile that can make a minkan/ankan
*/
public class HandChecker {
	
	
	private static final boolean DEFAULT_CLOSED_STATUS = true;
	
	private static final int NUM_PARTNERS_NEEDED_TO_CHI = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
	private static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	
	private static final int OFFSET_CHI_L1 = 1;
	private static final int OFFSET_CHI_L2 = 2;
	private static final int OFFSET_CHI_M1 = -1;
	private static final int OFFSET_CHI_M2 = 1;
	private static final int OFFSET_CHI_H1 = -2;
	private static final int OFFSET_CHI_H2 = -1;
	
	private static final int MAX_HAND_SIZE = 14;
	
	private int NOT_FOUND = -1;	//list

	private static final int NUMBER_OF_YAOCHUU_TILES = 13;
	private static final TileList LIST_OF_YAOCHUU_TILES = new TileList(1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34);
	
	
	
	
	
	private Hand mHand;
	private TileList mHandTiles;
	private ArrayList<Meld> mHandMelds;
	
	private boolean mClosed;
	private boolean mTenpaiStatus;
	
	
	//call flags and partner index lists
	private boolean mCanChiL, mCanChiM, mCanChiH, mCanPon, mCanKan, mCanRon, mCanPair;
	private ArrayList<Integer> mPartnerIndicesChiL, mPartnerIndicesChiM, mPartnerIndicesChiH, mPartnerIndicesPon, mPartnerIndicesKan, mPartnerIndicesPair;
	private Tile mCallCandidate;
	
	private boolean mCanAnkan, mCanMinkan, mCanRiichi, mCanTsumo;
//	private Tile mTurnAnkanCandidate;
//	private Tile mTurnMinkanCandidate;
	private int mTurnAnkanCandidateIndex, mTurnMinkanCandidateIndex;
	
	
	private boolean pairHasBeenChosen = false;
	private ArrayList<Meld> mFinishingMelds;
	
	private TileList mTenpaiWaits;
	
	
	
	//creates a LINK between this and the hand's tiles/melds
	public HandChecker(Hand hand, TileList handTiles, ArrayList<Meld> handMelds){
		mHand = hand;
		mHandTiles = handTiles;
		mHandMelds = handMelds;

		mTenpaiStatus = false;
		mClosed = DEFAULT_CLOSED_STATUS;
		
		//reset callable flags
		__resetCallableFlags();
		mCallCandidate = null;
	}
	//copy constructor, makes another checker for the hand (creates a COPY OF the other checker tiles/melds lists)
//	public HandChecker(HandChecker other){
//		
//		mHandTiles = new TileList();
//		for (Tile t: other.mHandTiles) mHandTiles.add(t);
//		mHandMelds = new ArrayList<Meld>();
//		for (Meld m: other.mHandMelds) mHandMelds.add(new Meld(m));
//
//		this.__resetCallableFlags();
//		mCallCandidate = other.mCallCandidate;
//		mCanChiL = other.mCanChiL; mCanChiM = other.mCanChiM; mCanChiH = other.mCanChiH; mCanPon = other.mCanPon; mCanKan = other.mCanKan; mCanRon = other.mCanRon; mCanPair = other.mCanPair; 
//		for (Integer i: other.mPartnerIndicesChiL) mPartnerIndicesChiL.add(i);
//		for (Integer i: other.mPartnerIndicesChiM) mPartnerIndicesChiM.add(i);
//		for (Integer i: other.mPartnerIndicesChiH) mPartnerIndicesChiH.add(i);
//		for (Integer i: other.mPartnerIndicesPon) mPartnerIndicesPon.add(i);
//		for (Integer i: other.mPartnerIndicesKan) mPartnerIndicesKan.add(i);
//		for (Integer i: other.mPartnerIndicesPair) mPartnerIndicesPair.add(i);
//		
//		mCanAnkan = other.mCanAnkan; mCanMinkan = other.mCanMinkan; mCanTsumo = other.mCanTsumo; mCanRiichi = other.mCanRiichi;
//		mTurnAnkanCandidate = other.mTurnAnkanCandidate; mTurnMinkanCandidate = other.mTurnMinkanCandidate;
//		mTurnAnkanCandidateIndex = other.mTurnAnkanCandidateIndex; mTurnMinkanCandidateIndex = other.mTurnMinkanCandidateIndex;
//		
//		mTenpaiStatus = other.mTenpaiStatus;
//		mClosed = other.mClosed;
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~BEGIN MELD CHEKCERS
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/*
	private method: __canChiType
	checks if a candidate tile can make a chi with other tiles in the hand
	
	input: candidate is the tile to search for chi partners for
		   storePartnersHere is the list that will hold the partner indices, if chi is possible
	 	   offset1 and offset2 are the offsets of mCallCandidate's ID to look for
	
	if chi type is possible: populates the meld partner list and returns true
	
	
	tempPartnerIndices = search hand for (candidate's ID + offset1/2), take first occurence of each
	if (indexes were found for both partners)
		store partner indices in the received list
		return true
	end if
	return false
	*/
	private boolean __canChiType(Tile candidate, List<Integer> storePartnersHere, int offset1, int offset2){
		
		//decide who the chi partners should be (offset is decided based on chi type)
		//search the hand for the desired chi partners (get the indices)
		Integer[] tempPartnerIndices = {mHandTiles.indexOf(new Tile(candidate.getId() + offset1)), mHandTiles.indexOf(new Tile(candidate.getId() + offset2))};
		
		//if both parters were found in the hand
		if (tempPartnerIndices[0] != NOT_FOUND && tempPartnerIndices[1] != NOT_FOUND){
			
			//sore the indices of the partners in a partner list
			__storePartnerIndices(storePartnersHere, Arrays.asList(tempPartnerIndices));
			
			return true;
		}
		return false;
		
	}
	private boolean __canChiL(Tile candidate){
		if (candidate.getFace() == '8' || candidate.getFace() == '9') return false;
		return __canChiType(candidate, mPartnerIndicesChiL, OFFSET_CHI_L1, OFFSET_CHI_L2);
	}
	private boolean __canChiM(Tile candidate){
		if (candidate.getFace() == '1' || candidate.getFace() == '9') return false;
		return __canChiType(candidate, mPartnerIndicesChiM, OFFSET_CHI_M1, OFFSET_CHI_M2);
	}
	private boolean __canChiH(Tile candidate){
		if (candidate.getFace() == '1' || candidate.getFace() == '2') return false;
		return __canChiType(candidate, mPartnerIndicesChiH, OFFSET_CHI_H1, OFFSET_CHI_H2);
	}
	//overloaded. if no tile argument given, candidate = mCallCandidate is passsed
	private boolean __canChiL(){return __canChiL(mCallCandidate);}
	private boolean __canChiM(){return __canChiM(mCallCandidate);}
	private boolean __canChiH(){return __canChiH(mCallCandidate);}
	
	
	
	/*
	private method: __canMultiType
	checks if a multi (pair/pon/kan) can be made with the new tile
	
	input: candidate is the tile to search for chi partners for
		   storePartnersHere is the list that will hold the partner indices, if chi is possible
	 	   offset1 and offset2 are the offsets of mCallCandidate's ID to look for
	 	   
	if the multi type is possible: populates the meld partner list and returns true
	
	
	if (there are enough partner tile in the hand to form the multi)
		store the partner indices in the storePartnersHere list
		return true
	end if
	else return false
	*/
	private boolean __canMultiType(Tile candidate, List<Integer> storePartnersHere, int numPartnersNeeded){
		
		//count how many occurences of the tile, and store the indices of the occurences in tempPartnerIndices
		List<Integer> tempPartnerIndices = new ArrayList<Integer>(numPartnersNeeded);
		
		//meld is possible if there are enough partners in the hand to form the meld
		if (__howManyOfThisTileInHand(candidate, tempPartnerIndices) >= numPartnersNeeded){

			//store the partner indices in the pon partner index list
			__storePartnerIndices(storePartnersHere, tempPartnerIndices.subList(0, numPartnersNeeded));
			
			return true;
		}
		return false;
	}
	private boolean __canPair(Tile candidate){return __canMultiType(candidate, mPartnerIndicesPair, NUM_PARTNERS_NEEDED_TO_PAIR);}
	private boolean __canPon(Tile candidate){return __canMultiType(candidate, mPartnerIndicesPon, NUM_PARTNERS_NEEDED_TO_PON);}
	private boolean __canKan(Tile candidate){return __canMultiType(candidate, mPartnerIndicesKan, NUM_PARTNERS_NEEDED_TO_KAN);}
	//overloaded. if no tile argument given, candidate = mCallCandidate is passsed
	private boolean __canPair(){return __canPair(mCallCandidate);}
	private boolean __canPon(){return __canPon(mCallCandidate);}
	private boolean __canKan(){return __canKan(mCallCandidate);}
	
	
	
	//returns true if the player can call ron on the candidate tile
	private boolean __canRon(Tile candidate){
		return (mTenpaiWaits.contains(candidate));
	}
	//overloaded. if no tile argument given, candidate = mCallCandidate is passsed
	private boolean __canRon(){return __canRon(mCallCandidate);}
	
	
	
	/*
	private method: __howManyOfThisTileInHand
	returns how many copies of tile t are in the hand
	
	input: t is the tile to look for. if a list is provided, storeIndicesHere will be populated with the indices where t occurs 
	
	foundIndices = find all indices of t in the hand
	if a (list was provided): store the indices in that list
	return (number of indices found)
	*/
	private int __howManyOfThisTileInHand(Tile t, List<Integer> storeIndicesHere){
		
		//find all the indices of t in the hand, then store the indices if a list was provided
		List<Integer> foundIndices = mHandTiles.findAllIndicesOf(t);
		if (storeIndicesHere != null) for (Integer i: foundIndices) storeIndicesHere.add(i);
		
		//return the number of indices found
		return foundIndices.size();
	}
	
	
	
	
	
	
	/*
	method: checkCallableTile
	checks if a tile is callable
	if a call is possible, sets flag and populates partner index lists for that call
	
	input: candidate is the tile to check if callable
	returns true if a call (any call) can be made for the tile
	
	
	reset all call flags/lists
	mCallCandidate = candidate
	check if each type of call can be made (flags are set and lists are populated here)
	if any call can be made, return true. else, return false
	*/
	public boolean checkCallableTile(Tile candidate){
		
		//check if tile candidate is a hot tile. if candidate is not a hot tile, return false
		if (!__findAllHotTiles().contains(candidate.getId())) return false;
		
		//check which melds candidate can be called for, if any
		
		
		//~~~~reset flags
		__resetCallableFlags();
		mCallCandidate = candidate;
		
		//~~~~runs checks, set flags to the check results
		//only allow chis from the player's kamicha, or from the player's own tiles. don't check chi if candidate is an honor tile
		if (!candidate.isHonor() && (
			(candidate.getOrignalOwner() == mHand.getOwnerSeatWind()) || 
			(candidate.getOrignalOwner() == mHand.getOwnerSeatWind().kamichaWind())) ){
			mCanChiL = __canChiL();
			mCanChiM = __canChiM();
			mCanChiH = __canChiH();
		}

		//check pair. if can't pair, don't bother checking pon. check pon. if can't pon, don't bother checking kan.
		if (mCanPair = __canPair())
			if (mCanPon = __canPon())
				mCanKan = __canKan();
		
		//if in tenpai, check ron
		if (mTenpaiStatus)
			mCanRon = __canRon();
		
		//~~~~return true if a call (any call) can be made
		return (mCanChiL || mCanChiM || mCanChiH || mCanPon || mCanKan || mCanRon);
	}
	
	
	
	
	//takes a list and several integer indices, stores the indices in the list
	private void __storePartnerIndices(List<Integer> storeHere, List<Integer> partnerIndices){
		for (int i: partnerIndices) storeHere.add(i);
	}
	
	//resets call flags to false, creates new empty partner index lists
	private void __resetCallableFlags(){
		mCanChiL = mCanChiM = mCanChiH = mCanPon = mCanKan = mCanRon = mCanPair = false;
		mCanAnkan = mCanMinkan = mCanRiichi = mCanTsumo = false;
		
		mPartnerIndicesChiL = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		mPartnerIndicesChiM = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		mPartnerIndicesChiH = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		mPartnerIndicesPon = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_PON);
		mPartnerIndicesKan = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_KAN);
		mPartnerIndicesPair = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_PAIR);
		mTurnAnkanCandidateIndex = mTurnMinkanCandidateIndex = -1; 
	}
	
	
	//returns the number of different calls possible for mCallCandidate
//	public int numberOfCallsPossible(){
//		int count = 0;
//		if (mCanChiL) count++;
//		if (mCanChiM) count++;
//		if (mCanChiH) count++;
//		if (mCanPon) count++;
//		if (mCanKan) count++;
//		if (mCanRon) count++;
//		return count;
//	}
	
	
	
	
	
	public void updateTurnActions(){
		
		__resetCallableFlags();
		
		for (int index = 0; index < mHandTiles.size(); index++){
			
			Tile t = mHandTiles.get(index);
			
			if (__canClosedKan(t, mHandTiles)){
//				mTurnAnkanCandidate = t;
				mTurnAnkanCandidateIndex = index;
				mCanAnkan = true;
			}
			
			if (__canMinkan(t)){
//				mTurnMinkanCandidate = t;
				mTurnMinkanCandidateIndex = index;
				mCanMinkan = true;
			}
			
			if (__canTsumo()){
				mCanTsumo = true;
			}
			
		}
	}
	
	private boolean __canTsumo(){return isComplete();}
	
	
	
	
	private boolean __canMinkan(Tile candidate){
		for (Meld m: mHandMelds){
			if (m.isPon() && m.getFirstTile().equals(candidate))
				return true;
		}
		return false;
	}
	
	
//	public Tile getCandidateMinkan(){return mTurnMinkanCandidate;}
//	public Tile getCandidateAnkan(){return mTurnAnkanCandidate;}
	public int getCandidateMinkanIndex(){return mTurnMinkanCandidateIndex;}
	public int getCandidateAnkanIndex(){return mTurnAnkanCandidateIndex;}
	
//	public int numberOfTurnActionsPossible(){
//		int count = 0;
//		if (mCanAnkan) count++;
//		if (mCanMinkan) count++;
//		if (mCanTsumo) count++;
//		return count;
//	}
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~END MELD CHEKCERS
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------
	//----BEGIN FINDERS
	//---------------------------------------------------------------------------------------------------
	
	//returns a list of hot tile IDs for ALL tiles in the hand
	private ArrayList<Integer> __findAllHotTiles(){

		ArrayList<Integer> allHotTileIds = new ArrayList<Integer>(16);
		ArrayList<Integer> singleTileHotTiles = null;
		
		//get hot tiles for each tile in the hand
		for (Tile t: mHandTiles){
			singleTileHotTiles = __findHotTilesOfTile(t);
			for (Integer i: singleTileHotTiles) if (!allHotTileIds.contains(i)) allHotTileIds.add(i);
		}
		
		return allHotTileIds;
	}
	
	/*
	private static method: __findHotTilesOfTile
	returns a list of integer IDs of hot tiles, for tile t
	
	add t to the list (because pon)
	if (t is not honor): add all possible chi partners to the list
	return list
	*/
	private static ArrayList<Integer> __findHotTilesOfTile(Tile t){
		
		ArrayList<Integer> hotTileIds = new ArrayList<Integer>(1); 
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
	private ArrayList<Integer> __findAllCallableTiles(){
		
		ArrayList<Integer> allCallableTileIds = new  ArrayList<Integer>(4);
		ArrayList<Integer> hotTileIds = new ArrayList<Integer>(34);
		
		final boolean TEST_ALL_TILES = false;
		
		if (TEST_ALL_TILES) for (int i = 1; i <= 34; i++) hotTileIds.add(i);
		else hotTileIds = __findAllHotTiles();
		
		//examine all hot tiles
		for (Integer i: hotTileIds)
			//if tile i is callable
			if (checkCallableTile(new Tile(i)))
				//add its id to the list of callable tiles
				allCallableTileIds.add(i);
		
		return allCallableTileIds;
	}
	public ArrayList<Integer> DEMOfindAllCallableTiles(){return __findAllCallableTiles();}
	public ArrayList<Integer> DEMOfindAllHotTiles(){return __findAllHotTiles();}
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
	
	
	
	
	
	
	
	
	//checks if the hand is in tenpai
	//sets mTenpaiStaus flag if it is, and returns true
	private boolean __checkIfTenpai(){
		
		boolean isKokushiTenpai = false, isChiitoitsuTenpai = false, isNormalTenpai = false;
		
		mTenpaiWaits = new TileList();
		
		//check for kokushi musou tenpai, waits are also found here
		isKokushiTenpai = isTenpaiKokushi();
		if (isKokushiTenpai)
				mTenpaiWaits = __getKokushiWaits();
		else{
			
			//check if the hand is in normal tenpai, waits are also found here (don't check if in already kokushi tenpai)
			isNormalTenpai = (!__findTenpaiWaits().isEmpty());
			
			//check for chiitoitsu tenpai, wait is also found here (only check if no kokushi tenpai and no normal tenpai)
			if (!isNormalTenpai)
				isChiitoitsuTenpai = isTenpaiChiitoitsu();			
		}		
		
		mTenpaiStatus = (isKokushiTenpai || isChiitoitsuTenpai || isNormalTenpai);
		return mTenpaiStatus;
	}
	public boolean updateTenpaiStatus(){return __checkIfTenpai();}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns true if the hand is in tenpai for kokushi musou
	//if this is true, it means that: handsize >= 13, hand has at least 12 different TYC tiles
	public boolean isTenpaiKokushi(){
		
		//if any melds have been made, kokushi musou is impossible, return false
		if (mHand.getNumMeldsMade() > 0) return false;
		//if the hand contains even one non-honor tile, return false
		for (Tile t: mHandTiles) if (!t.isYaochuu()) return false;
		
		
		//check if the hand contains at least 12 different TYC tiles
		TileList listTYC = __listOfYaochuuTiles();
		int countTYC = 0;
		for (int i = 0; i < NUMBER_OF_YAOCHUU_TILES; i++)
			if (mHandTiles.contains(listTYC.get(i)))
				countTYC++;

		//return false if the hand doesn't contain at least 12 different TYC tiles
		if (countTYC < NUMBER_OF_YAOCHUU_TILES - 1) return false;
		
		return true;
	}
	
	//returns true if a 14-tile hand is a complete kokushi musou
	public boolean isCompleteKokushi(){
		if ((mHandTiles.size() == MAX_HAND_SIZE) &&
			(isTenpaiKokushi() == true) &&
			(__getKokushiWaits().size() == NUMBER_OF_YAOCHUU_TILES))
			return true;
		
		return false;
	}

	//returns a list of the hand's waits, if it is in tenpai for kokushi musou
	//returns an empty list if not in kokushi musou tenpai
	private TileList __getKokushiWaits(){
		
		TileList waits = new TileList(1);
		
		Tile missingTYC = null;
		if (isTenpaiKokushi() == true){
			//look for a Yaochuu tile that the hand doesn't contain
			TileList listTYC = __listOfYaochuuTiles();
			for (Tile t: listTYC)
				if (mHandTiles.contains(t) == false)
					missingTYC = t;
			
			//if the hand contains exactly one of every Yaochuu tile, then it is a 13-sided wait for all Yaochuu tiles
			if (missingTYC == null)
				waits = listTYC;
			else
				//else, if the hand is missing a Yaochuu tile, that missing tile is the hand's wait
				waits.add(missingTYC);
		}
		
		//store the waits in mTenpaiWaits if there are any
		if (!waits.isEmpty()) mTenpaiWaits = waits;
		return waits;
	}
	public TileList DEMOgetKokushiWaits(){return __getKokushiWaits();}
	
	
	
	
	
	
	
	
	
	/*
	method: isTenpaiChiitoitsu
	checks if the hand is in tenpai for chiitoitsu, and modifies the list of waits if so 
	
	input: handTiles is the list of Tiles to check for chiitoi tenpai
	
	returns true if the hand is in tenpai for chiitoitsu 
	return false if either of them are missing
	*/
	public boolean isTenpaiChiitoitsu(TileList handTiles){
		
		Tile missingTile = null;
		//conditions:
		//hand must be 13 tiles (no melds made)
		//hand must have exactly 7 different types of tiles
		//hand must have no more than 2 of each tile
		
		//if any melds have been made, chiitoitsu is impossible, return false
		if (mHand.getNumMeldsMade() > 0) return false;
		
		//the hand should have exactly 7 different types of tiles (4 of a kind != 2 pairs)
		if (handTiles.makeCopyNoDuplicates().size() != 7) return false;

		//the hand must have no more than 2 of each tile
		for(Tile t: handTiles){
			switch(handTiles.findHowManyOf(t)){
			case 1: missingTile = t; break;
			case 2: ;break;//intentionally blank
			default: return false;
			}
		}
		
		//at this point, we know that the hand is in chiitoitsu tenpai
		
		
		//add the missing tile to the wait list (it will be the only wait)
		mTenpaiWaits = new TileList(1);
		if (missingTile != null) mTenpaiWaits.add(missingTile);
		return true;
	}
	public boolean isTenpaiChiitoitsu(){return isTenpaiChiitoitsu(mHandTiles);}
	
	
	
	
	
	//returns true if a 14-tile hand is a complete chiitoitsu
	public boolean isCompleteChiitoitsu(TileList handTiles){
		
		handTiles.sort();
		
		//chiitoitsu is impossible if a meld has been made
		if (handTiles.size() < MAX_HAND_SIZE) return false;
		
		//even tiles should equal odd tiles, if chiitoitsu
		TileList evenTiles = handTiles.getMultiple(0,2,4,6,8,10,12);
		TileList oddTiles = handTiles.getMultiple(1,3,5,7,9,11,13);
		return evenTiles.equals(oddTiles);
	}
	public boolean isCompleteChiitoitsu(){return isCompleteChiitoitsu(mHandTiles.makeCopy());}
	
	
	
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
	private boolean __canClosedChiType(Tile candidate, TileList handTiles, int offset1, int offset2){
		return (handTiles.contains(candidate.getId() + offset1) && handTiles.contains(candidate.getId() + offset2));
	}
	private boolean __canClosedChiL(Tile candidate, TileList handTiles){return ((candidate.getFace() != '8' && candidate.getFace() != '9') && __canClosedChiType(candidate, handTiles, OFFSET_CHI_L1, OFFSET_CHI_L2));}
	private boolean __canClosedChiM(Tile candidate, TileList handTiles){return ((candidate.getFace() != '1' && candidate.getFace() != '9') && __canClosedChiType(candidate, handTiles, OFFSET_CHI_M1, OFFSET_CHI_M2));}
	private boolean __canClosedChiH(Tile candidate, TileList handTiles){return ((candidate.getFace() != '1' && candidate.getFace() != '2') && __canClosedChiType(candidate, handTiles, OFFSET_CHI_H1, OFFSET_CHI_H2));}
	
	
	/*
	private method: __canClosedMultiType
	returns true if the hand contains at least "numPartnersNeeded" copies of candidate
	
	input: candidate is the tile to search for copies of, numPartnersNeeded is the number of copies needed
	*/
	private boolean __canClosedMultiType(Tile candidate, TileList handTiles, int numPartnersNeeded){
		//count how many occurences of the tile
		int count = (handTiles.findHowManyOf(candidate) );
		return count >= numPartnersNeeded;
	}
	private boolean __canClosedPair(Tile candidate, TileList handTiles){return __canClosedMultiType(candidate, handTiles, NUM_PARTNERS_NEEDED_TO_PAIR + 1);}
	private boolean __canClosedPon(Tile candidate, TileList handTiles){return __canClosedMultiType(candidate, handTiles, NUM_PARTNERS_NEEDED_TO_PON + 1);}
	private boolean __canClosedKan(Tile candidate, TileList handTiles){return __canClosedMultiType(candidate, handTiles, NUM_PARTNERS_NEEDED_TO_KAN + 1);}
	
	
		
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
	private boolean __checkMeldableTile(HandCheckerTile candidate, TileList checkTiles){
		
		//check pon. if can pon, push both pon and pair. if can't pon, check pair.
		if (__canClosedPon(candidate, checkTiles)){
			candidate.mstackPush(MeldType.PAIR);
			candidate.mstackPush(MeldType.PON);
		}
		else if (__canClosedPair(candidate, checkTiles)) candidate.mstackPush(MeldType.PAIR);
		
		//don't check chi if candidate is an honor tile
		if (!candidate.isHonor()){
			if (__canClosedChiH(candidate, checkTiles)) candidate.mstackPush(MeldType.CHI_H);
			if (__canClosedChiM(candidate, checkTiles)) candidate.mstackPush(MeldType.CHI_M);
			if (__canClosedChiL(candidate, checkTiles)) candidate.mstackPush(MeldType.CHI_L);
		}
		
		//~~~~return true if a call (any call) can be made
		return (!candidate.mstackIsEmpty());
	}
//	private boolean __checkMeldableTile(HandCheckerTile candidate){return __checkMeldableTile(candidate, mHandTiles);}
	
	//============================================================================
	//====END CAN MELDS
	//============================================================================
	
	
	
	
	
	
	
	
	//demo completed melds
//	public boolean demoComplete(){
//		boolean complete;
//		if (complete = isCompleteNormal(mHandTiles)){
//			for (Meld m: mFinishingMelds) mHandMelds.add(m);
//			GenSort<Meld> meldSorter = new GenSort<Meld>(mHandMelds);
//			meldSorter.sort();
//		}
//		
//		return complete;
//	}
	public boolean DEMOisComplete(){return isComplete();}
	
	
	

	
	
	
	private TileList __findTenpaiWaits(){
		//TODO this is find tenpai waits
		
		TileList waits = new TileList();
		
		TileList handTilesCopy;
		List<Integer> hotTileIDs = __findAllHotTiles();
		Tile currentHotTile;
		
		for (Integer id: hotTileIDs){
			//get a hot tile (and mark it with the hand's seat wind, so chi is valid)
			currentHotTile = new Tile(id);
			currentHotTile.setOwner(mHand.getOwnerSeatWind());
			
			//make a copy of the hand, add the current hot tile to that copy, sort the copy
			handTilesCopy = mHandTiles.makeCopy();
			handTilesCopy.add(currentHotTile); handTilesCopy.sort();
			
			//check if the copy, with the added tile, is complete
			if (__isCompleteNormal(handTilesCopy)) waits.add(currentHotTile);
		}
		
		//store the waits in mTenpaiWaits, if there are any
		if (!waits.isEmpty()) mTenpaiWaits = waits;
		return waits;
	}
	public TileList DEMOfindTenpaiWaits(){return __findTenpaiWaits();}
	
	
	
	
	
	
	
	/*
	private method: __isCompleteNormal
	returns true if list of handTiles is complete (is a winning hand)
	*/
	public boolean __isCompleteNormal(TileList handTiles){
		
		if ((handTiles.size() % 3) != 2) return false;
		
		//make a list of checkTiles out of the handTiles
		TileList checkTiles = handTiles.makeCopyWithCheckers();
		checkTiles.sort();
		
		
		//populate stacks
		if (!__populateMeldStacks(checkTiles)) return false;
		
		pairHasBeenChosen = false;
		mFinishingMelds = new ArrayList<Meld>(5);
		
		return __isCompleteNormalHand(checkTiles);
	}
	//overloaded, checks mHandTiles by default
	public boolean isCompleteNormal(){return __isCompleteNormal(mHandTiles);}
	
	
	
	/*
	private method: __populateMeldStacks
	populates the meld type stacks for all of the tile in checkTiles
	returns true if all tiles can make a meld, returns false if a tile cannot make a meld
	*/
	private boolean __populateMeldStacks(TileList checkTiles){
		//check to see if every tile can make at least one meld
		for (int i = 0; i < checkTiles.size(); i++)
			if (!__checkMeldableTile(((HandCheckerTile)checkTiles.get(i)), checkTiles)) return false;
		
		return true;
	}
	
	
	
	
	
	
	
	
	/*
	private method: __isCompleteNormalHand
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
	private boolean __isCompleteNormalHand(TileList checkTiles){
		
		//if the hand is empty, it is complete
		if (checkTiles.isEmpty()) return true;
		
		
		
		TileList toMeldTiles = null;
		TileList checkTilesMinusThisMeld = null;
		

		HandCheckerTile currentTile = null;
		MeldType currentTileMeldType;
		List<Integer> currentTileParterIDs = null;
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
				if (!checkTiles.contains(currentTileParterIDs.get(0)) || !checkTiles.contains(currentTileParterIDs.get(1)))
					currentTilePartersAreStillHere = false;
			}
			else{
				if (currentTileMeldType == MeldType.PAIR && checkTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PAIR + 1) currentTilePartersAreStillHere = false;
				if (currentTileMeldType == MeldType.PON && checkTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PON + 1) currentTilePartersAreStillHere = false;
			}
			
			
			
			//~~~~Separate the tiles if the meld is possible
			//if (currentTile's partners for the meld are still in the hand) AND (if pair has already been chosen, currentTileMeldType must not be a pair)
			if (currentTilePartersAreStillHere && !(pairHasBeenChosen && currentTileMeldType == MeldType.PAIR)){
				
				//take the pair privelige
				if (currentTileMeldType == MeldType.PAIR) pairHasBeenChosen = true;
				
				
				//~~~~Find the inidces of currentTile's partners
				partnerIndices = new ArrayList<Integer>();
				
				//if chi, just find the partners
				if (currentTileMeldType.isChi()){
					partnerIndices.add(checkTiles.indexOf(currentTileParterIDs.get(0)));
					partnerIndices.add(checkTiles.indexOf(currentTileParterIDs.get(1)));
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
				checkTilesMinusThisMeld = checkTiles.makeCopyWithCheckers();
				toMeldTiles = new TileList();
				
				while (!partnerIndices.isEmpty()) toMeldTiles.add(checkTilesMinusThisMeld.remove(partnerIndices.remove(partnerIndices.size() - 1)));
				toMeldTiles.add(checkTilesMinusThisMeld.removeFirst());
				
				
				
				
				
				//~~~~Recursive call, check if the hand is still complete without the removed meld tiles
				if (__isCompleteNormalHand(checkTilesMinusThisMeld)){
					mFinishingMelds.add(new Meld(toMeldTiles, currentTileMeldType));	//add the meld tiles to the finishing melds stack
					return true;
				}
				else{
					//relinquish the pair privelege, if it was taken
					if (currentTileMeldType == MeldType.PAIR) pairHasBeenChosen = false;
				}
				
				
			}
			
		}
		
		return false;
	}
	

	
	//returns the list of tenpai waits
	public TileList getTenpaiWaits(){return mTenpaiWaits;}
	
	
	//***************************************************************************************************
	//***************************************************************************************************
	//****END TENPAI CHECKERS
	//***************************************************************************************************
	//***************************************************************************************************
	
	
	
	
	public void DEMOprintFinishingMelds(){for (Meld m: mFinishingMelds) System.out.println(m.toString());}
	
	
	
	
	
	
	
	
	
	
	
	//checks if the hand is closed or open, ajusts flag accordingly
	public boolean updateClosedStatus(){
		boolean meldsAreAllClosed = true;
		//if all the melds are closed, then the hand is closed
		for (Meld m: mHandMelds) meldsAreAllClosed = (meldsAreAllClosed && m.isClosed());
		
		return mClosed = meldsAreAllClosed;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns the partner indices list for a given meld type
	public ArrayList<Integer> getPartnerIndices(MeldType meldType){
		
		ArrayList<Integer> wantedIndices = null;
		switch (meldType){
		case CHI_L: wantedIndices = mPartnerIndicesChiL; break;
		case CHI_M: wantedIndices = mPartnerIndicesChiM; break;
		case CHI_H: wantedIndices = mPartnerIndicesChiH; break;
		case PON: wantedIndices = mPartnerIndicesPon; break;
		case KAN: wantedIndices = mPartnerIndicesKan; break;
		case PAIR: wantedIndices = mPartnerIndicesPair; break;
		default: break;
		}
		return wantedIndices;
	}
	
	//returns mCallCandidate
	public Tile getCallCandidate(){return mCallCandidate;}
	
	
	//returns true if a specific call can be made on mCallCandidate
	public boolean ableToChiL(){return mCanChiL;}
	public boolean ableToChiM(){return mCanChiM;}
	public boolean ableToChiH(){return mCanChiH;}
	public boolean ableToPon(){return mCanPon;}
	public boolean ableToKan(){return mCanKan;}
	public boolean ableToRon(){return mCanRon;}
	public boolean ableToPair(){return mCanPair;}
	
	
	//turn actions
	public boolean ableToAnkan(){return mCanAnkan;}
	public boolean ableToMinkan(){return mCanMinkan;}
	public boolean ableToRiichi(){return mCanRiichi;}
	public boolean ableToTsumo(){return mCanTsumo;}
	
	
	//returns true if the hand is in tenpai
	public boolean getTenpaiStatus(){return mTenpaiStatus;}
	
	
	//returns true if the hand is fully concealed, false if an open meld has been made
	public boolean getClosedStatus(){return mClosed;}
	
	
	
	
	
	private final static TileList __listOfYaochuuTiles(){return LIST_OF_YAOCHUU_TILES.makeCopy();}
	
	
	
	//runs test code
	public static void main(String[] args){majava.control.testcode.DemoHandGen.main(null);}
	
}
