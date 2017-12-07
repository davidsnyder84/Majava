package majava.hand.handcheckers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import majava.enums.MeldType;
import majava.enums.Wind;
import majava.hand.Hand;
import majava.hand.Meld;
import majava.tiles.GameTile;
import majava.tiles.HandCheckerTile;
import majava.tiles.Janpai;
import majava.util.GameTileList;
import majava.util.TileKnowledge;

public class AgariChecker {
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	private static final int OFFSET_CHI_L1 = 1, OFFSET_CHI_L2 = 2;
	private static final int OFFSET_CHI_M1 = -1,  OFFSET_CHI_M2 = 1;
	private static final int OFFSET_CHI_H1 = -2, OFFSET_CHI_H2 = -1;
	
	private static final int MAX_HAND_SIZE = 14;
	
	private static final Integer[] YAOCHUU_TILE_IDS = Janpai.retrieveYaochuuTileIDs();
	private static final int NUMBER_OF_YAOCHUU_TILES = YAOCHUU_TILE_IDS.length;
	
	
	
	private final Hand myHand;
	private final GameTileList myHandTiles;
	
	public AgariChecker(Hand handToCheck, GameTileList reveivedHandTiles){
		myHand = handToCheck;
		myHandTiles = reveivedHandTiles;
	}
	
	private Wind ownerSeatWind(){return myHand.getOwnerSeatWind();}
	
	
	
	
	//returns true if a hand is complete (it is a winning hand)
	public boolean isComplete(){
		return (isCompleteKokushi() || isCompleteChiitoitsu() || isCompleteNormal());
	}
	
	
	private static GameTileList emptyWaitsList(){return new GameTileList();}
	
	//returns the list of tenpai waits
	public GameTileList getTenpaiWaits(){
//		System.out.println("norm" + findNormalTenpaiWaits(myHandTiles).toString());System.out.println("koku" + getKokushiWaits(myHandTiles).toString());System.out.println("chit" + getChiitoiWait(myHandTiles).toString());
		GameTileList waits = new GameTileList();
		waits.addAll(getNormalTenpaiWaits());
		if (waits.isEmpty()) waits.addAll(getKokushiWaits());
		if (waits.isEmpty()) waits.addAll(getChiitoiWait());
		
		return waits;
	}
	//returns true if the hand is in tenpai
	public boolean isInTenpai(){return !getTenpaiWaits().isEmpty();}
	
	
	
	
	
	//returns true if the hand is in tenpai for kokushi musou
	//if this is true, it means that: handsize >= 13, hand has at least 12 different TYC tiles
	public boolean isTenpaiKokushi(){
		
		//if any melds have been made, kokushi musou is impossible, return false
		if (myHandTiles.size() < MAX_HAND_SIZE-1) return false;
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
		if ((myHandTiles.size() == MAX_HAND_SIZE) &&
			(isTenpaiKokushi()) &&
			(getKokushiWaits().size() == NUMBER_OF_YAOCHUU_TILES))
			return true;
		
		return false;
	}

	//returns a list of the hand's waits, if it is in tenpai for kokushi musou
	//returns an empty list if not in kokushi musou tenpai
	private GameTileList getKokushiWaits(){
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
	
	
	
	
	
	public GameTileList getChiitoiWait(){
		GameTile missingTile = null;
		//conditions:
		//hand must be 13 tiles (no melds made)
		//hand must have exactly 7 different types of tiles
		//hand must have no more than 2 of each tile
		
		//if any melds have been made, chiitoitsu is impossible, return false
		if (myHandTiles.size() != MAX_HAND_SIZE-1 || myHand.numberOfMeldsMade() > 0) return emptyWaitsList();
		
		//the hand should have exactly 7 different types of tiles (4 of a kind != 2 pairs)
		if (myHandTiles.makeCopyNoDuplicates().size() != 7) return emptyWaitsList();

		//the hand must have no more than 2 of each tile
		for(GameTile t: myHandTiles){
			switch(myHandTiles.findHowManyOf(t)){
			case 1: missingTile = t;
			case 2: break;//intentionally blank
			default: return emptyWaitsList();
			}
		}
		
		//at this point, we know that the hand is in chiitoitsu tenpai
		
		//add the missing tile to the wait list (it will be the only wait)
		//if NPO happens here, look at old code
		return new GameTileList(missingTile);
	}
	public boolean isTenpaiChiitoitsu(){return !getChiitoiWait().isEmpty();}
	
	
	//returns true if a 14-tile hand is a complete chiitoitsu
	public boolean isCompleteChiitoitsu(){
		GameTileList checkTilesCopy = myHandTiles.clone();
		checkTilesCopy.sort();
		
		//chiitoitsu is impossible if a meld has been made
		if (checkTilesCopy.size() < MAX_HAND_SIZE) return false;
		
		//even tiles should equal odd tiles, if chiitoitsu
		GameTileList evenTiles = checkTilesCopy.getMultiple(0,2,4,6,8,10,12);
		GameTileList oddTiles = checkTilesCopy.getMultiple(1,3,5,7,9,11,13);
		return evenTiles.equals(oddTiles);
	}
	
	
	
	
	
	
	
	
	//シュンツ
	private static boolean canClosedChiType(GameTileList checkTiles, GameTile candidate, int offset1, int offset2){
		return (checkTiles.contains(candidate.getId() + offset1) && checkTiles.contains(candidate.getId() + offset2));
	}
	private static boolean canClosedChiL(GameTileList checkTiles, GameTile candidate){return candidate.canCompleteChiL() && canClosedChiType(checkTiles, candidate, OFFSET_CHI_L1, OFFSET_CHI_L2);}
	private static boolean canClosedChiM(GameTileList checkTiles, GameTile candidate){return candidate.canCompleteChiM() && canClosedChiType(checkTiles, candidate, OFFSET_CHI_M1, OFFSET_CHI_M2);}
	private static boolean canClosedChiH(GameTileList checkTiles, GameTile candidate){return candidate.canCompleteChiH() && canClosedChiType(checkTiles, candidate, OFFSET_CHI_H1, OFFSET_CHI_H2);}
	//コーツ
	private static boolean canClosedMultiType(GameTileList checkTiles, GameTile candidate, int numPartnersNeeded){
		return checkTiles.findHowManyOf(candidate) >= numPartnersNeeded;
	}
	private static boolean canClosedPair(GameTileList checkTiles, GameTile candidate){return canClosedMultiType(checkTiles, candidate, NUM_PARTNERS_NEEDED_TO_PAIR + 1);}
	private static boolean canClosedPon(GameTileList checkTiles, GameTile candidate){return canClosedMultiType(checkTiles, candidate, NUM_PARTNERS_NEEDED_TO_PON + 1);}
	
	
	//checks if a tile is meldable, populates the meldStack for candidate. returns true if a meld (any meld) can be made
	private static boolean checkMeldableTile(HandCheckerTile candidate, GameTileList checkTiles){
		//order of stack should be top->L,M,H,Pon,Pair
		if (canClosedPair(checkTiles, candidate)) candidate.mstackPush(MeldType.PAIR);
		if (canClosedPon(checkTiles, candidate)) candidate.mstackPush(MeldType.PON);
		if (canClosedChiH(checkTiles, candidate)) candidate.mstackPush(MeldType.CHI_H);
		if (canClosedChiM(checkTiles, candidate)) candidate.mstackPush(MeldType.CHI_M);
		if (canClosedChiL(checkTiles, candidate)) candidate.mstackPush(MeldType.CHI_L);
		
		return (!candidate.mstackIsEmpty());
	}

	
	
	//TODO this is find tenpai waits
	private GameTileList getNormalTenpaiWaits(){
		final GameTileList waits = new GameTileList();
		final GameTileList checkTilesCopy = myHandTiles.clone();
		
		final List<Integer> hotTileIDs = TileKnowledge.findAllHotTiles(myHandTiles);
		for (Integer id: hotTileIDs){
			//get a hot tile (and mark it with the hand's seat wind, so chi is valid)
			GameTile currentHotTile = new GameTile(id);
			currentHotTile.setOwner(ownerSeatWind());
			
			//make a copy of the hand, add the current hot tile to that copy
			checkTilesCopy.add(currentHotTile);
			
			//check if the copy, with the added tile, is complete
			if (isCompleteNormal(checkTilesCopy)) waits.add(currentHotTile);
			
			checkTilesCopy.remove(currentHotTile);
		}
		
		return waits;
	}
	
	
	
	
	
	
	
	//returns true if list of handTiles is complete (is a winning hand)
	public static boolean isCompleteNormal(GameTileList checkTiles, List<Meld> finishingMelds){
		if ((checkTiles.size() % 3) != 2) return false;
		
		//make a list of checkTiles out of the handTiles
		GameTileList copyOfCheckTiles = HandCheckerTile.makeCopyOfListWithCheckers(checkTiles);
		copyOfCheckTiles.sort();
		
		//populate stacks
		if (!populateMeldStacks(copyOfCheckTiles)) return false;
		return isCompleteNormalHand(copyOfCheckTiles, finishingMelds);
	}
	//overloaded, checks mHandTiles by default
	public static boolean isCompleteNormal(GameTileList checkTiles){return isCompleteNormal(checkTiles, null);}
	public boolean isCompleteNormal(List<Meld> finishingMelds){return isCompleteNormal(myHandTiles, finishingMelds);}
	public boolean isCompleteNormal(){return isCompleteNormal(myHandTiles);}
	
	
	//populates the meld type stacks for all of the tile in checkTiles
	//returns true if all tiles can make a meld, returns false if a tile cannot make a meld
	private static boolean populateMeldStacks(GameTileList checkTiles){
		for (GameTile t: checkTiles)
			if (!checkMeldableTile((HandCheckerTile)t, checkTiles)) return false;
		
		return true;
	}
	
	public List<Meld> getFinishingMelds(){
		List<Meld> finishingMelds = new ArrayList<Meld>(5);
		isCompleteNormal(finishingMelds);
		return finishingMelds;
	}
	
	
	/*
	checks if a hand is complete
	
	
	if (handtiles is empty): return true (an empty hand is complete)
	currentTile = first tile in handTiles
	
	while (currentTile's stack of valid meld types is not empty)
		
		currentTileMeldType = pop a meldtype off of currentTile's stack (this is the meld type we will try)
		
		if ((currentTile's partners for the meld are still in the hand) && (if pairChosen, currentTileMeldType must not be a pair))
			
			if (currentTileMeldType is pair): pairChosen = true (take the pair privelege)
			
			partnerIndices = find the indices of currentTile's partners for the currentTileMeldType
			toMeldTiles = list of tiles from the hand, includes currentTile and its partner tiles
			
			handtilessMinusThisMeld = copy of handtiles, but with the toMeldTiles removed
			listMTSLMinusThisMeld = copy of listMTSL, but with the stacks for toMeldTiles removed
			
			if (isCompleteHand(tiles/stacks minus this meld)) (recursive call)
				return true (the hand is complete)
			else
				if (currentTileMeldType is pair): pairChosen = false (relinquish the pair privelege)
			end if
			
		end if
		
	end while
	return false (currentTile could not make any meld, so the hand cannot be complete)
	*/
	private static boolean isCompleteNormalHand(GameTileList checkTiles, List<Meld> finishingMelds, AtomicBoolean pairHasBeenChosen){
		
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
			if (currentTilePartersAreStillHere && !(pairHasBeenChosen.get() && currentTileMeldType == MeldType.PAIR)){
				
				//take the pair privelige
				if (currentTileMeldType == MeldType.PAIR)
					pairHasBeenChosen.set(true);
				
				
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
				checkTilesMinusThisMeld = HandCheckerTile.makeCopyOfListWithCheckers(checkTiles);
				toMeldTiles = new GameTileList();
				
				while (!partnerIndices.isEmpty())
					toMeldTiles.add(checkTilesMinusThisMeld.remove( partnerIndices.remove(partnerIndices.size() - 1).intValue()) );
				
				toMeldTiles.add(checkTilesMinusThisMeld.removeFirst());
				
				
				
				
				//~~~~Recursive call, check if the hand is still complete without the removed meld tiles
				if (isCompleteNormalHand(checkTilesMinusThisMeld, finishingMelds, pairHasBeenChosen)){
					if (finishingMelds != null)
						finishingMelds.add(new Meld(toMeldTiles.clone(), currentTileMeldType));	//add the meld tiles to the finishing melds stack
					return true;
				}
				else{
					//relinquish the pair privelege, if it was taken
					if (currentTileMeldType == MeldType.PAIR)
						pairHasBeenChosen.set(false);
				}
				
			}
		}
		return false;
	}
	private static boolean isCompleteNormalHand(GameTileList checkTiles, List<Meld> finishingMelds){return isCompleteNormalHand(HandCheckerTile.makeCopyOfListWithCheckers(checkTiles), finishingMelds, new AtomicBoolean(false));}
	
}
