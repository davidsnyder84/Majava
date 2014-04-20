package majava;

import java.util.ArrayList;

import utility.GenSort;
import utility.MahList;
import utility.MahStack;


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
	mCanChiL, mCanChiM, mCanChiH, mCanPon, mCanKan, mCanRon - flags, set to true if a call can be made on mCallCandidate
	mPartnerIndicesChiL, etc - hold the hand indices of meld partners for mCallCandidate, if it can be called
	
	mTenpaiWaits - if the hand is in tenpai, this holds a list of the hand's waits 

methods:
	
	constructors:
	takes a hand, a list of the hand's tiles, and a list of the hand's melds (creates a LINK between this and the hand's tiles/melds)
 	
 	accessors:
	getClosedStatus - returns true if the hand is fully concealed, false if an open meld has been made
	getTenpaiStatus - returns true if the hand is in tenpai
	getTenpaiWaits - returns the list of tenpai waits
	
	findAllHotTiles - returns a list of hot tile IDs for ALL tiles in the hand
	findAllCallableTiles - returns a list of callable tile IDs for ALL tiles in the hand
	ableToChiL, ableToChiM, ableToChiH, ableToPon, ableToKan, ableToRon - return true if a call can be made on mCallCandidate
	numberOfCallsPossible - returns the number of different calls possible for mCallCandidate
	getCallCandidate - returns mCallCandidate
	getPartnerIndices - returns the partner indices list for a given meld type
	
	
	private:
	__resetCallableFlags - resets call flags to false, creates new empty partner index lists
	__storePartnerIndices - takes a partner list and a list integer indices, stores the indices in the partner list
	__canRon - returns true if the player can call ron on the candidate tile
	
	__canChiType - checks if a candidate can make a chiL/M/H. populates chiL/M/H partner list, returns true if chiL/M/H is possible
	__canChiL - checks if a candidate can make a Chi-L
	__canChiM - checks if a candidate can make a Chi-M
	__canChiH - checks if a candidate can make a Chi-H
	
	__canMultiType - checks if a candidate can make a pair/pon/kan. populates pair/pon/kan partner list and returns true if a pair/pon/kan possible
	__canPair - checks if a candidate can make a pair
	__canPon - checks if a candidate can make a pon
	__canKan - checks if a candidate can make a kan
	__howManyOfThisTileInHand - returns how many copies of a tile are in the hand, can fill a list with the indices where the tile is found
	
	__checkIfTenpai - checks if the hand is in tenpai, sets mTenpaiStaus flag if it is, and returns true
	kokushiMusouInTenpai - returns true if the hand is in tenpai for kokushi musou
	kokushiMusouIsComplete - returns true if a 14-tile hand is a complete kokushi musou
	kokushiMusouWaits - returns a list of the hand's waits, if it is in tenpai for kokushi musou
	
	
	public:
	checkCallableTile - checks if a tile is callable. if it is callable, sets flag and populates partner index lists for that call
	updateClosedStatus - checks if the hand is closed or open, sets flag accordingly
	updateTenpaiStatus - checks if the hand is in tenpai, sets flag accordingly
*/
public class HandChecker {
	
	
	//private static final int NOT_FOUND = -1;
	public static final boolean DEFAULT_CLOSED_STATUS = true;
	
	public static final int NUM_PARTNERS_NEEDED_TO_CHI = 2;
	public static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	public static final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
	public static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	
	private static final int OFFSET_CHI_L1 = 1;
	private static final int OFFSET_CHI_L2 = 2;
	private static final int OFFSET_CHI_M1 = -1;
	private static final int OFFSET_CHI_M2 = 1;
	private static final int OFFSET_CHI_H1 = -2;
	private static final int OFFSET_CHI_H2 = -1;
	
	
	
	
	
	
	private Hand mHand;
	private TileList mHandTiles;
	private ArrayList<Meld> mHandMelds;
	
	private boolean mTenpaiStatus;
	private boolean mClosed;
	
	
	//call flags and partner index lists
	private boolean mCanChiL;
	private boolean mCanChiM;
	private boolean mCanChiH;
	private boolean mCanPon;
	private boolean mCanKan;
	private boolean mCanRon;
	private boolean mCanPair;
	private ArrayList<Integer> mPartnerIndicesChiL;
	private ArrayList<Integer> mPartnerIndicesChiM;
	private ArrayList<Integer> mPartnerIndicesChiH;
	private ArrayList<Integer> mPartnerIndicesPon;
	private ArrayList<Integer> mPartnerIndicesKan;
	private ArrayList<Integer> mPartnerIndicesPair;
	private Tile mCallCandidate;
	
	private boolean mCanAnkan;
	private boolean mCanMinkan;
	private boolean mCanTsumo;
	
	
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
	//copy constructor, makes another checker for the hand
	//creates a COPY OF the other checker tiles/melds lists
	public HandChecker(HandChecker other){
		
		mHandTiles = new TileList(Hand.MAX_HAND_SIZE);
		for (Tile t: other.mHandTiles) mHandTiles.add(t);
		mHandMelds = new ArrayList<Meld>(Hand.MAX_NUM_MELDS);
		for (Meld m: other.mHandMelds) mHandMelds.add(new Meld(m));

		this.__resetCallableFlags();
		mCallCandidate = other.mCallCandidate;
		mCanChiL = other.mCanChiL;
		mCanChiM = other.mCanChiM;
		mCanChiH = other.mCanChiH;
		mCanPon = other.mCanPon;
		mCanKan = other.mCanKan;
		mCanRon = other.mCanRon;
		mCanPair = other.mCanPair;
		for (Integer i: other.mPartnerIndicesChiL) mPartnerIndicesChiL.add(i);
		for (Integer i: other.mPartnerIndicesChiM) mPartnerIndicesChiM.add(i);
		for (Integer i: other.mPartnerIndicesChiH) mPartnerIndicesChiH.add(i);
		for (Integer i: other.mPartnerIndicesPon) mPartnerIndicesPon.add(i);
		for (Integer i: other.mPartnerIndicesKan) mPartnerIndicesKan.add(i);
		for (Integer i: other.mPartnerIndicesPair) mPartnerIndicesPair.add(i);
		
		mCanAnkan = other.mCanAnkan;
		mCanMinkan = other.mCanMinkan;
		mCanTsumo = other.mCanTsumo;
		
		mTenpaiStatus = other.mTenpaiStatus;
		mClosed = other.mClosed;
	}
	/*
	//creates a COPY of the hand's tiles/melds to check (don't use this unless you NEED a copy)
	public HandChecker(Hand hand){
		mHand = hand;
		mHandTiles = new ArrayList<Tile>(mHand.getSize());
		for (Tile t: mHand) mHandTiles.add(t);	//copy tiles into this checker's lsit
		//copy melds (not done this yet)
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	private boolean __canChiType(Tile candidate, ArrayList<Integer> storePartnersHere, int offset1, int offset2){
		
		//decide who the chi partners should be (offset is decided based on chi type)
		//search the hand for the desired chi partners (get the indices)
		MahList<Integer> tempPartnerIndices = new MahList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		tempPartnerIndices.add(mHandTiles.indexOf(new Tile(candidate.getId() + offset1)));
		tempPartnerIndices.add(mHandTiles.indexOf(new Tile(candidate.getId() + offset2)));
		
		//if both parters were found in the hand
		if (tempPartnerIndices.getFirst() != MahList.NOT_FOUND && tempPartnerIndices.getLast() != MahList.NOT_FOUND){
			
			//sore the indices of the partners in a partner list
			__storePartnerIndices(storePartnersHere, tempPartnerIndices);
			
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
	private boolean __canMultiType(Tile candidate, ArrayList<Integer> storePartnersHere, int numPartnersNeeded){
		
		//count how many occurences of the tile, and store the indices of the occurences in tempPartnerIndices
		MahList<Integer> tempPartnerIndices = new MahList<Integer>(numPartnersNeeded);
		
		//meld is possible if there are enough partners in the hand to form the meld
		if (howManyOfThisTileInHand(candidate, tempPartnerIndices.getArrayList()) >= numPartnersNeeded){

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
	
	
	
	/*
	private method: howManyOfThisTileInHand
	returns how many copies of tile t are in the hand
	
	input: t is the tile to look for. if a list is provided, storeIndicesHere will be populated with the indices where t occurs 
	
	foundIndices = find all indices of t in the hand
	if a (list was provided): store the indices in that list
	return (number of indices found)
	*/
	public int howManyOfThisTileInHand(Tile t, ArrayList<Integer> storeIndicesHere){
		
		//find all the indices of t in the hand, then store the indices if a list was provided
		MahList<Integer> foundIndices = mHandTiles.findAllIndicesOf(t);
		if (storeIndicesHere != null) for (Integer i: foundIndices) storeIndicesHere.add(i);
		
		//return the number of indices found
		return foundIndices.size();
	}
	//overloaded, omitting list argument simply returns the count, and doesn't populate any list
	public int howManyOfThisTileInHand(Tile t){return howManyOfThisTileInHand(t, null);}
	
	
	
	
	
	//returns true if the player can call ron on the candidate tile
	public boolean __canRon(){
		
		
		
		
		return false;
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
		
		//~~~~reset flags
		__resetCallableFlags();
		//set mCallCandidate = candidate
		mCallCandidate = candidate;
		
		//~~~~runs checks, set flags to the check results
		//only allow chis from the player's kamicha, or from the player's own tiles. don't check chi if candidate is an honor tile
		if (!candidate.isHonor() && (
			(candidate.getOrignalOwner() == mHand.getOwnerSeatWind()) || 
			(candidate.getOrignalOwner() == Player.findKamichaOf(mHand.getOwnerSeatWind()))) ){
			mCanChiL = __canChiL();
			mCanChiM = __canChiM();
			mCanChiH = __canChiH();
		}

		//check pair. if can't pair, don't bother checking pon. check pon. if can't pon, don't bother checking kan.
		if (mCanPair = __canPair())
			if (mCanPon = __canPon())
				mCanKan = __canKan();
		
		//if in tenpai, check ron
		if (mTenpaiStatus == true)
			mCanRon = __canRon();
		
		//~~~~return true if a call (any call) can be made
		return (mCanChiL || mCanChiM || mCanChiH || mCanPon || mCanKan || mCanRon);
	}
	
	
	
	
	//takes a list and several integer indices, stores the indices in the list
	private void __storePartnerIndices(ArrayList<Integer> storeHere, MahList<Integer> partnerIndices){
		for (int i: partnerIndices)
			storeHere.add(i);
	}
	
	//resets call flags to false, creates new empty partner index lists
	private void __resetCallableFlags(){
		mCanChiL = mCanChiM = mCanChiH = mCanPon = mCanKan = mCanRon = mCanPair = false;
		mCanAnkan = mCanMinkan = mCanTsumo = false;
		mPartnerIndicesChiL = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		mPartnerIndicesChiM = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		mPartnerIndicesChiH = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_CHI);
		mPartnerIndicesPon = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_PON);
		mPartnerIndicesKan = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_KAN);
		mPartnerIndicesPair = new ArrayList<Integer>(NUM_PARTNERS_NEEDED_TO_PAIR);
	}
	
	
	//returns the number of different calls possible for mCallCandidate
	public int numberOfCallsPossible(){
		int count = 0;
		if (mCanChiL) count++;
		if (mCanChiM) count++;
		if (mCanChiH) count++;
		if (mCanPon) count++;
		if (mCanKan) count++;
		if (mCanRon) count++;
		return count;
	}
	
	public int numberOfPlayerTurnCallsPossible(){
		int count = 0;
		if (mCanAnkan) count++;
		if (mCanMinkan) count++;
		if (mCanTsumo) count++;
		return count;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~END MELD CHEKCERS
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------
	//----BEGIN FINDERS
	//---------------------------------------------------------------------------------------------------
	
	//returns a list of hot tile IDs for ALL tiles in the hand
	public ArrayList<Integer> findAllHotTiles(){

		ArrayList<Integer> allHotTileIds = new ArrayList<Integer>(16);
		ArrayList<Integer> singleTileHotTiles = null;
		
		//get hot tiles for each tile in the hand
		for (Tile t: mHandTiles)
		{
			singleTileHotTiles = t.findHotTiles();
			for (Integer i: singleTileHotTiles)
				if (allHotTileIds.contains(i) == false)
					allHotTileIds.add(i);
		}
		
		//return list of integer IDs
		return allHotTileIds;
	}

	//returns a list of callable tile IDs for ALL tiles in the hand
	public ArrayList<Integer> findAllCallableTiles(){
		
		
		ArrayList<Integer> allCallableTileIds = new  ArrayList<Integer>(4);
		ArrayList<Integer> hotTileIds;
		
		
		final boolean TEST_ALL_TILES = false;
		
		if (TEST_ALL_TILES){
			hotTileIds = new ArrayList<Integer>(34);
			for (int i = 1; i <= 34; i++)
				hotTileIds.add(i);
		}
		else
			hotTileIds = findAllHotTiles();
		
		
		//examine all hot tiles
		for (Integer i: hotTileIds)
			//if tile i is callable
			if (checkCallableTile(new Tile(i)))
				//add its id to the list of callable tiles
				allCallableTileIds.add(i);
		
		//return list of callable tile ids
		return allCallableTileIds;
	}
	
	
	public void findAllMachis(){
		
	}
	
	
	
	//---------------------------------------------------------------------------------------------------
	//----END FINDERS
	//---------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//***************************************************************************************************
	//***************************************************************************************************
	//****BEGIN TENPAI CHECKERS
	//***************************************************************************************************
	//***************************************************************************************************
	
	
	
	
	
	
	
	
	
	//checks if the hand is in tenpai
	//sets mTenpaiStaus flag if it is, and returns true
	private boolean __checkIfTenpai(){
		
		boolean isKokushiTenpai = false, isChiitoitsuTenpai = false, isNormalTenpai = false;
		
		mTenpaiWaits = new TileList();
		
		//check for kokushi musou tenpai, waits are also found here
		isKokushiTenpai = kokushiMusouInTenpai();
		if (isKokushiTenpai)
				mTenpaiWaits = kokushiMusouWaits();
		else{
			
			//check if the hand is in normal tenpai, waits are also found here (don't check if in already kokushi tenpai)
			isNormalTenpai = (!findTenpaiWaits().isEmpty());
			
			//check for chiitoitsu tenpai, wait is also found here (only check if no kokushi tenpai and no normal tenpai)
			if (!isNormalTenpai)
				isChiitoitsuTenpai = chiitoitsuInTenpai(mHandTiles);			
		}		
		
		mTenpaiStatus = (isKokushiTenpai || isChiitoitsuTenpai || isNormalTenpai);
		return mTenpaiStatus;
	}
	public boolean updateTenpaiStatus(){return __checkIfTenpai();}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns true if the hand is in tenpai for kokushi musou
	//if this is true, it means that: handsize >= 13, hand has at least 12 different TYC tiles
	public boolean kokushiMusouInTenpai(){
		
		//if any melds have been made, kokushi musou is impossible, return false
		if (mHand.getNumMeldsMade() > 0) return false;
		//if the hand contains even one non-honor tile, return false
		for (Tile t: mHandTiles) if (!t.isYaochuu()) return false;
		
		
		//check if the hand contains at least 12 different TYC tiles
		TileList listTYC = Tile.listOfYaochuuTiles();
		int countTYC = 0;
		for (int i = 0; i < Tile.NUMBER_OF_YAOCHUU_TILES; i++)
			if (mHandTiles.contains(listTYC.get(i)))
				countTYC++;

		//return false if the hand doesn't contain at least 12 different TYC tiles
		if (countTYC < Tile.NUMBER_OF_YAOCHUU_TILES - 1) return false;
		
		return true;
	}
	
	//returns true if a 14-tile hand is a complete kokushi musou
	public boolean kokushiMusouIsComplete(){
		
		if ((mHandTiles.size() == Hand.MAX_HAND_SIZE) &&
			(kokushiMusouInTenpai() == true) &&
			(kokushiMusouWaits().size() == Tile.NUMBER_OF_YAOCHUU_TILES))
			return true;
		
		return false;
	}

	//returns a list of the hand's waits, if it is in tenpai for kokushi musou
	//returns an empty list if not in kokushi musou tenpai
	public TileList kokushiMusouWaits(){
		
		TileList waits = new TileList(1);
		
		Tile missingTYC = null;
		if (kokushiMusouInTenpai() == true){
			//look for a Yaochuu tile that the hand doesn't contain
			TileList listTYC = Tile.listOfYaochuuTiles();
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
	
	
	
	
	
	
	
	
	
	
	
	/*
	method: chiitoitsuInTenpai
	checks if the hand is in tenpai for chiitoitsu, and modifies the list of waits if so 
	
	input: handTiles is the list of Tiles to check for chiitoi tenpai
	
	returns true if the hand is in tenpai for chiitoitsu 
	return false if either of them are missing
	*/
	public boolean chiitoitsuInTenpai(TileList handTiles){
		
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
	
	
	//returns true if a 14-tile hand is a complete chiitoitsu
	public boolean chiitoitsuIsComplete(){
		
		//chiitoitsu is impossible if a meld has been made
		if (mHandTiles.size() < Hand.MAX_HAND_SIZE) return false;
		
		//even tiles should equal odd tiles, if chiitoitsu
		TileList evenTiles = mHandTiles.getMultiple(0,2,4,6,8,10,12);
		TileList oddTiles = mHandTiles.getMultiple(1,3,5,7,9,11,13);
		return evenTiles.equals(oddTiles);
	}
	
	public boolean DEMOchiitoitsuInTenpai(){return chiitoitsuInTenpai(mHandTiles);}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	@SuppressWarnings("unused")
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
	public boolean checkMeldableTile(Tile candidate, TileList handTiles, MahStack<MeldType> meldStack){
		
		//check pon. if can pon, push both pon and pair. if can't pon, check pair.
		if (__canClosedPon(candidate, handTiles)){
			meldStack.push(MeldType.PAIR);
			meldStack.push(MeldType.PON);
		}
		else if (__canClosedPair(candidate, handTiles)) meldStack.push(MeldType.PAIR);
		
		//don't check chi if candidate is an honor tile
		if (!candidate.isHonor()){
			if (__canClosedChiH(candidate, handTiles)) meldStack.push(MeldType.CHI_H);
			if (__canClosedChiM(candidate, handTiles)) meldStack.push(MeldType.CHI_M);
			if (__canClosedChiL(candidate, handTiles)) meldStack.push(MeldType.CHI_L);
		}
		
		//~~~~return true if a call (any call) can be made
		return (!meldStack.isEmpty());
	}
	public boolean checkMeldableTile(Tile candidate, MahStack<MeldType> meldStack){return checkMeldableTile(candidate, mHandTiles, meldStack);}
	
	//============================================================================
	//====END CAN MELDS
	//============================================================================
	
	
	
	
	
	
	
	
	//demo completed melds
	public boolean demoComplete(){
		boolean complete;
		if (complete = isNormalComplete(mHandTiles)){
			for (Meld m: mFinishingMelds) mHandMelds.add(m);
			GenSort<Meld> meldSorter = new GenSort<Meld>(mHandMelds);
			meldSorter.sort();
		}
		
		return complete;
	}
	
	
	

	
	
	
	public TileList findTenpaiWaits(){
		//TODO this is find tenpai waits
		
		TileList waits = new TileList();
		
		TileList handTilesCopy;
		ArrayList<Integer> hotTileIDs = findAllHotTiles();
		Tile currentHotTile;
		
		for (Integer id: hotTileIDs){
			//get a hot tile (and mark it with the hand's seat wind, so chi is valid)
			currentHotTile = new Tile(id, mHand.getOwnerSeatWind());
			
			//make a copy of the hand, add the current hot tile to that copy, sort the copy
			handTilesCopy = mHandTiles.makeCopy();
			handTilesCopy.add(currentHotTile); handTilesCopy.sort();
			
			//check if the copy, with the added tile, is complete
			if (isNormalComplete(handTilesCopy)) waits.add(currentHotTile);
		}
		
		//store the waits in mTenpaiWaits, if there are any
		if (!waits.isEmpty()) mTenpaiWaits = waits;
		return waits;
	}
	
	
	
	
	
	/*
	method: isNormalComplete
	returns true if list of handTiles is complete (is a winning hand)
	*/
	public boolean isNormalComplete(TileList handTiles){
		
		//populate stacks
		MeldTypeStackList listMTSL = new MeldTypeStackList(handTiles.size());
		if (populateMeldStacks(handTiles, listMTSL) == false) return false;
		
		pairHasBeenChosen = false;
		mFinishingMelds = new ArrayList<Meld>(5);
		return isCompleteHand(handTiles, listMTSL);
	}
	//overloaded, checks mHandTiles by default
	public boolean isNormalComplete(){return isNormalComplete(mHandTiles);}
	
	/*
	method: populateMeldStacks
	populates the meld type stacks for all of the hand tiles
	returns true if all tiles can make a meld, returns false if a tile cannot make a meld
	
	input: handTiles is the list of tiles to check
		   listMTSL is a list of meld type stacks, will be populated corresponding to the tiles in handTiles
	*/
	public boolean populateMeldStacks(TileList handTiles, MeldTypeStackList listMTSL){
		//check to see if every tile can make at least one meld
		for (int i = 0; i < handTiles.size(); i++)
			if (checkMeldableTile(handTiles.get(i), handTiles, listMTSL.get(i)) == false) return false;
		
		return true;
	}
	
	
	
	/*
	method: isCompleteHand
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
	public boolean isCompleteHand(TileList handTiles, MeldTypeStackList listMTSL){
		
		//if the hand is empty, it is complete
		if (handTiles.isEmpty()) return true;
		
		
		
		TileList toMeldTiles = null;
		TileList handTilesMinusThisMeld = null;
		MeldTypeStackList listMTSLMinusThisMeld = null;
		

		Tile currentTile = null;
		MeldType currentTileMeldType;
		ArrayList<Integer> currentTileParterIDs = null;
		boolean currentTilePartersAreStillHere = true;
		MahList<Integer> partnerIndices = null;
		
		
		
		
		//currrentTile = first tile in the hand
		currentTile = handTiles.getFirst();
		
		
		//loop until every possible meld type has been tried for the current tile
		while(listMTSL.firstIsEmpty() == false){

			
			//~~~~Verify that currentTile's partners are still in the hand
			//currentTileParterIDs = list of IDs of partners for currentTile's top MeldType
			currentTileParterIDs = listMTSL.firstTopPartnerIDs(currentTile.getId());

			//get the top meldType from currentTile's stack
			currentTileMeldType = listMTSL.firstPop();	//(remove it)


			//check if currentTile's partners are still in the hand
			currentTilePartersAreStillHere = true;
			if (currentTileMeldType.isChi()){
				if (!handTiles.contains(currentTileParterIDs.get(0)) || !handTiles.contains(currentTileParterIDs.get(1)))
					currentTilePartersAreStillHere = false;
			}
			else{
				if (currentTileMeldType == MeldType.PAIR && handTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PAIR + 1) currentTilePartersAreStillHere = false;
				if (currentTileMeldType == MeldType.PON && handTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PON + 1) currentTilePartersAreStillHere = false;
			}
			
			
			
			//~~~~Separate the tiles if the meld is possible
			//if (currentTile's partners for the meld are still in the hand) AND (if pair has already been chosen, currentTileMeldType must not be a pair)
			if (currentTilePartersAreStillHere && !(pairHasBeenChosen && currentTileMeldType == MeldType.PAIR))
			{
				//take the pair privelige
				if (currentTileMeldType == MeldType.PAIR) pairHasBeenChosen = true;
				
				
				//~~~~Find the inidces of currentTile's partners
				partnerIndices = new MahList<Integer>();
				
				//if chi, just find the partners
				if (currentTileMeldType.isChi()){
					partnerIndices.add(handTiles.indexOf(currentTileParterIDs.get(0)));
					partnerIndices.add(handTiles.indexOf(currentTileParterIDs.get(1)));
				}
				else{
					//else if pon/pair, make sure you don't count the tile itsef
					partnerIndices = handTiles.findAllIndicesOf(currentTile);
					
					//trim the lists down to size to fit the meld type
					if (currentTileMeldType == MeldType.PAIR) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PAIR) partnerIndices.removeLast();
					if (currentTileMeldType == MeldType.PON) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PON) partnerIndices.removeLast();
				}
				
				

				//~~~~Add the tiles to a meld tile list
				//add the currentTile to the meldlist, add the hand tiles to the meldlist
				toMeldTiles = new TileList();
				toMeldTiles.add(currentTile);
				for (Integer index: partnerIndices) toMeldTiles.add(handTiles.get(index));
				

				//make a copy of the hand and stacklist, and remove the meld tiles from the copies
				handTilesMinusThisMeld = handTiles.makeCopy();
				listMTSLMinusThisMeld = listMTSL.makeCopy();
				
				while (partnerIndices.isEmpty() == false){
					handTilesMinusThisMeld.remove(partnerIndices.get(partnerIndices.size() - 1).intValue());
					listMTSLMinusThisMeld.remove(partnerIndices.get(partnerIndices.size() - 1).intValue());
					partnerIndices.remove(partnerIndices.size() - 1);
				}
				handTilesMinusThisMeld.remove(0);
				listMTSLMinusThisMeld.remove(0);
				
				//~~~~Recursive call, check if the hand is still complete without the removed meld tiles
				if (isCompleteHand(handTilesMinusThisMeld, listMTSLMinusThisMeld)){
					mFinishingMelds.add(new Meld(toMeldTiles, currentTileMeldType));	//add the meld tiles to the finishing melds stack
					return true;
				}
				else{
					//relinquish the pair privelege, if it was taken
					if (currentTileMeldType == MeldType.PAIR) pairHasBeenChosen = false;
				}
				
				
			}
			
		}
		//TODO
		
		return false;
	}
	
	

	
	//returns the list of tenpai waits
	public TileList getTenpaiWaits(){return mTenpaiWaits;}
	
	
	//***************************************************************************************************
	//***************************************************************************************************
	//****END TENPAI CHECKERS
	//***************************************************************************************************
	//***************************************************************************************************
	
	
	
	
	
	
	
	
	
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
	
	
	//player turn calls
	public boolean ableToAnkan(){return mCanAnkan;}
	public boolean ableToMinkan(){return mCanMinkan;}
	public boolean ableToTsumo(){return mCanTsumo;}
	
	
	//returns true if the hand is in tenpai
	public boolean getTenpaiStatus(){return mTenpaiStatus;}
	
	
	//returns true if the hand is fully concealed, false if an open meld has been made
	public boolean getClosedStatus(){return mClosed;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		
		Hand h = new Hand(MajaPlay.ownerSeat);
		
		/*
		h.addTile(new Tile(1));
		h.addTile(new Tile(1));
		h.addTile(new Tile(2));
		h.addTile(new Tile(2));
		h.addTile(new Tile(2));
		h.addTile(new Tile(3));
		h.addTile(new Tile(3));
		h.addTile(new Tile(4));
		h.addTile(new Tile(4));
		*/
//		h.addTile(new Tile(3));
		h.addTile(new Tile(9));
		h.addTile(new Tile(9));
		h.addTile(new Tile(9));
		
		h.addTile(new Tile(11));
		h.addTile(new Tile(12));
		h.addTile(new Tile(24));
		h.addTile(new Tile(24));
		
		h.sortHand();
		
		
		boolean DO_TENPAI = true;
		
		System.out.println(h.toString());
		
		if (DO_TENPAI){
			TileList waits = h.mChecker.findTenpaiWaits();
			System.out.print("Waits: ");
			String waitString = "";
			for (Tile t: waits) waitString += t.toString() + ", ";
			System.out.println(waitString);
		}
		else{
			System.out.println("\nHand is complete normal?: " + h.mChecker.isNormalComplete());
		}
		
		
		DemoHandGen.main(null);
	}

	//satisfy compiler whining
	public void lookAtMelds(){mHandMelds.size();}//yep, i'm looking at them
	
	
	
	
}
