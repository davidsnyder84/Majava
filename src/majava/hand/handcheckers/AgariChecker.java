package majava.hand.handcheckers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import majava.enums.MeldType;
import majava.hand.Hand;
import majava.hand.Meld;
import majava.tiles.GameTile;
import majava.tiles.HandCheckerTile;
import majava.tiles.Janpai;
import majava.util.GTL;
import majava.util.TileKnowledge;


//checks if a hand is complete (agari) or nearly complete (tenpai)
public class AgariChecker {
	private static final int MAX_HAND_SIZE = 14;
	
	private final Hand myHand;
	
	
	
	public AgariChecker(Hand handToCheck){
		myHand = handToCheck;
	}
	
	
	
	//returns true if a hand is complete (it is a winning hand)
	public boolean isComplete(){
		return (isCompleteKokushi() || isCompleteChiitoitsu() || isCompleteNormal());
	}
	
	
	//returns the list of tenpai waits (will be empty if the hand is not in tenpai)
	public GTL getTenpaiWaits(){
		GTL waits = new GTL(getNormalTenpaiWaits());
		if (waits.isEmpty()) waits.addAll(getKokushiWaits());
		if (waits.isEmpty()) waits.addAll(getChiitoiWait());
		
		return waits;
	}
	public boolean isInTenpai(){return !getTenpaiWaits().isEmpty();}
	
	
	
	public boolean isTenpaiKokushi(){return kokushiChecker().isTenpaiKokushi();}
	public boolean isCompleteKokushi(){return kokushiChecker().isCompleteKokushi();}
	public GTL getKokushiWaits(){return kokushiChecker().getKokushiWaits();}
	
	public boolean isTenpaiChiitoitsu(){return chiitoiChecker().isTenpaiChiitoitsu();}
	public boolean isCompleteChiitoitsu(){return chiitoiChecker().isCompleteChiitoitsu();}
	public GTL getChiitoiWait(){return chiitoiChecker().getChiitoiWait();}
	
	public boolean isCompleteNormal(){return normalAgariChecker().isCompleteNormal();}
	public GTL getNormalTenpaiWaits(){return normalAgariChecker().getNormalTenpaiWaits();}
	public List<Meld> getFinishingMelds(){return normalAgariChecker().getFinishingMelds();}
	
	
	
	private ChiitoiChecker chiitoiChecker(){return new ChiitoiChecker(myHand);}
	private KokushiChecker kokushiChecker(){return new KokushiChecker(myHand);}
	private NormalAgariChecker normalAgariChecker(){return new NormalAgariChecker(myHand);}
	
	
	
	
	
	private static class KokushiChecker{
		private static final Integer[] YAOCHUU_TILE_IDS = Janpai.retrieveYaochuuTileIDs();
		private static final int NUMBER_OF_YAOCHUU_TILES = YAOCHUU_TILE_IDS.length;
		
		private final Hand myHand;
		private final GTL handTiles;
		
		public KokushiChecker(Hand handToCheck){
			myHand = handToCheck;
			handTiles = myHand.getTiles();
		}
		
		
		public boolean isTenpaiKokushi(){
			//conditions for kokushi tenpai: handsize >= 13, and hand has at least 12 different TYC tiles
			
			//if any melds have been made, kokushi musou is impossible, return false
			if (handTiles.size() < MAX_HAND_SIZE-1) return false;
			//if the hand contains even one non-honor tile, return false
			for (GameTile t: handTiles) if (!t.isYaochuu()) return false;
			
			
			//check if the hand contains at least 12 different TYC tiles
			int countTYC = 0;
			for (int id: YAOCHUU_TILE_IDS)
				if (handTiles.contains(id))
					countTYC++;
			
			//return false if the hand doesn't contain at least 12 different TYC tiles
			if (countTYC < NUMBER_OF_YAOCHUU_TILES - 1) return false;
			
			return true;
		}
		
		//returns true if a 14-tile hand is a complete kokushi musou
		public boolean isCompleteKokushi(){
			if ((handTiles.size() == MAX_HAND_SIZE) &&
				(isTenpaiKokushi()) &&
				(getKokushiWaits().size() == NUMBER_OF_YAOCHUU_TILES))
				return true;
			
			return false;
		}

		//returns a list of the hand's waits, if it is in tenpai for kokushi musou
		//returns an empty list if not in kokushi musou tenpai
		private GTL getKokushiWaits(){
			GTL waits = new GTL();
			GameTile missingTYC = null;
			
			if (isTenpaiKokushi()){
				//look for a Yaochuu tile that the hand doesn't contain
				for (Integer id: YAOCHUU_TILE_IDS)
					if (!handTiles.contains(id))
						missingTYC = new GameTile(id);
				
				//if the hand contains exactly one of every Yaochuu tile, then it is a 13-sided wait for all Yaochuu tiles
				if (missingTYC == null)
					waits = new GTL(YAOCHUU_TILE_IDS);
				else
					//else, if the hand is missing a Yaochuu tile, that missing tile is the hand's wait
					waits = waits.add(missingTYC);
			}
			return waits;
		}
	}
	
	
	
	
	
	
	
	
	private static class ChiitoiChecker{
		
		private final Hand myHand;
		private final GTL handTiles; //not necessary since you can get this from myHand.getTiles(), just for convenience (IDE highlighting)
		
		public ChiitoiChecker(Hand handToCheck){
			myHand = handToCheck;
			handTiles = myHand.getTiles();
		}
		
		public GTL getChiitoiWait(){
			//conditions for chiitoi tenpai:
				//hand must be 13 tiles (no melds made)
				//hand must have exactly 7 different types of tiles
				//hand must have no more than 2 of each tile
			
			//if any melds have been made, chiitoitsu is impossible, return false
			if (handTiles.size() != MAX_HAND_SIZE-1 || myHand.numberOfMeldsMade() > 0) return emptyWaitsList();
			
			//the hand should have exactly 7 different types of tiles (4 of a kind != 2 pairs)
			if (handTiles.makeCopyNoDuplicates().size() != 7) return emptyWaitsList();

			//the hand must have no more than 2 of each tile
			GameTile missingTile = null;
			for(GameTile t: handTiles){
				switch(handTiles.findHowManyOf(t)){
				case 1: missingTile = t;
				case 2: break;//intentionally blank
				default: return emptyWaitsList();
				}
			}
			
			//at this point, we know that the hand is in chiitoitsu tenpai
			
			//add the missing tile to the wait list (it will be the only wait)
			//if NPO happens here, look at old code
			return new GTL(missingTile);
		}
		public boolean isTenpaiChiitoitsu(){return !getChiitoiWait().isEmpty();}
		
		//returns true if a 14-tile hand is a complete chiitoitsu
		public boolean isCompleteChiitoitsu(){			
			//chiitoitsu is impossible if a meld has been made
			if (handTiles.size() < MAX_HAND_SIZE) return false;
						
			//even tiles should equal odd tiles, if chiitoitsu
			GTL sortedHandtiles = handTiles.sort();
			GTL evenTiles = sortedHandtiles.getMultiple(0,2,4,6,8,10,12);
			GTL oddTiles = sortedHandtiles.getMultiple(1,3,5,7,9,11,13);
			return evenTiles.equals(oddTiles);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	private static class NormalAgariChecker{
		private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
		private static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
		
		
		private final Hand myHand;
		private final GTL handTiles;
		
		public NormalAgariChecker(Hand handToCheck){
			myHand = handToCheck;
			handTiles = myHand.getTiles();
		}
		
		
		private GTL getNormalTenpaiWaits(){
			GTL waits = new GTL();
			
			final List<Integer> hotTileIDs = TileKnowledge.findAllHotTiles(handTiles);
			for (Integer id: hotTileIDs){
				//get a hot tile (and mark it with the hand's seat wind, so chi is valid)
				GameTile currentHotTile = new GameTile(id).withOwnerWind(myHand.getOwnerSeatWind());
				
				//make a copy of the hand, add the current hot tile to that copy
				GTL checkTiles = handTiles.add(currentHotTile);
				
				//check if the copy, with the added tile, is complete
				if (isCompleteNormal(checkTiles))
					waits = waits.add(currentHotTile);
			}
			
			return waits;
		}
		
		
		
		//returns true if list of checkTiles is complete (is a winning hand)
		public static boolean isCompleteNormal(GTL checkTiles, List<Meld> finishingMelds){
			if ((checkTiles.size() % 3) != 2) return false;
			
			GTL populatedCheckTiles = HandCheckerTile.populateStacksForEntireList(checkTiles).sort();
			if (!allTilesCanMeld(populatedCheckTiles))
				return false;
				
			
			return isCompleteNormalHand(populatedCheckTiles, finishingMelds);
		}
		//overloaded, checks mHandTiles by default
		public static boolean isCompleteNormal(GTL checkTiles){return isCompleteNormal(checkTiles, null);}
		public boolean isCompleteNormal(List<Meld> finishingMelds){return isCompleteNormal(handTiles, finishingMelds);}
		public boolean isCompleteNormal(){return isCompleteNormal(handTiles);}
		
		
		//populates the meld type stacks for all of the tile in checkTiles
		//returns true if all tiles can make a meld, returns false if a tile cannot make a meld
		private static boolean allTilesCanMeld(GTL checkTiles){
			for (GameTile t: checkTiles)
				if ( ((HandCheckerTile)t).mstackIsEmpty() )
					return false;
			
			return true;
		}
		
		public List<Meld> getFinishingMelds(){
			List<Meld> finishingMelds = new ArrayList<Meld>(5);
			isCompleteNormal(finishingMelds);
			System.out.println(isCompleteNormal(finishingMelds));
			return finishingMelds;
		}
		
		
		
		//checks if a hand is complete (one pair + n number of シュンツ/コーツ)
		//recursive method
		private static boolean isCompleteNormalHand(GTL checkTiles, List<Meld> finishingMelds, AtomicBoolean pairHasBeenChosen){
			if (checkTiles.isEmpty()) return true;	//if the hand is empty, it is complete (base case)
			
			HandCheckerTile currentTile = (HandCheckerTile)checkTiles.getFirst();
			
			//loop until every possible meld type has been tried for the current tile
			while(!currentTile.mstackIsEmpty()){
				
				//~~~~Verify that currentTile's partners are still in the hand
				int[] currentTileParterIDs = currentTile.mstackTopParterIDs();
				MeldType currentTileMeldType = currentTile.mstackTop();
				currentTile = currentTile.mstackPop();	//(remove it)
				
				boolean currentTilePartersAreStillHere = partnersAreStillHereFor(currentTileMeldType, checkTiles, currentTile, currentTileParterIDs);
				
				//if (currentTile's partners for the meld are no longer here) OR (currentTileMeldType is pair and pair has already been chosen)
				if (!currentTilePartersAreStillHere || (currentTileMeldType.isPair() && pairHasBeenChosen.get()))
					continue;	//exit loop early and continue to the next meldType
					
				//take the pair privelige
				if (currentTileMeldType.isPair()) pairHasBeenChosen.set(true);
				
				//~~~~Find the inidces of currentTile's partners for the current meldType					
				List<Integer> partnerIndices = findIndicesOfCurrentTilePartersForMeldType(currentTileMeldType, checkTiles, currentTile, currentTileParterIDs);
				
				
				
				//remove the meld tiles from the (copy of the) hand and add them to the meld
				GTL toMeldTiles = checkTiles.getMultiple(partnerIndices).add(checkTiles.getFirst());
				GTL checkTilesMinusThisMeld = checkTiles.removeMultiple(partnerIndices).removeFirst(); 
				
				
				
				//~~~~Recursive call, check if the hand is still complete without the removed meld tiles
				if (isCompleteNormalHand(checkTilesMinusThisMeld, finishingMelds, pairHasBeenChosen)){
					if (finishingMelds != null) finishingMelds.add(new Meld(toMeldTiles, currentTileMeldType));	//add the meld tiles to the finishing melds stack
					return true;	//hand is complete
				}
				else{
					if (currentTileMeldType.isPair()) pairHasBeenChosen.set(false);	//relinquish the pair privelege, if it was taken
				}
			}
			return false;	//currentTile could not make any meld, so the hand must not be complete
		}
		private static boolean isCompleteNormalHand(GTL checkTiles, List<Meld> finishingMelds){return isCompleteNormalHand(checkTiles, finishingMelds, new AtomicBoolean(false));}
		
		
		
		
		//extracted methods to make isCompleteNormalHand more readable
		private static boolean partnersAreStillHereFor(MeldType currentTileMeldType, GTL checkTiles, HandCheckerTile currentTile, int[] currentTileParterIDs){
			if (currentTileMeldType.isChi())
				if (!checkTiles.contains(currentTileParterIDs[0]) || !checkTiles.contains(currentTileParterIDs[1]))
					return false;
			if (currentTileMeldType.isPair() && checkTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PAIR + 1)
				return false;
			if (currentTileMeldType.isPon() && checkTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PON + 1)
				return false;
			
			return true;
		}
		
		private static List<Integer> findIndicesOfCurrentTilePartersForMeldType(MeldType currentTileMeldType, GTL checkTiles, HandCheckerTile currentTile, int[] currentTileParterIDs){
			List<Integer> partnerIndices = new ArrayList<Integer>();
			//if chi, just find the partners
			if (currentTileMeldType.isChi()){
				partnerIndices.add(checkTiles.indexOf(currentTileParterIDs[0]));
				partnerIndices.add(checkTiles.indexOf(currentTileParterIDs[1]));
				return partnerIndices;
			}
			//else if pon/pair, make sure you don't count the tile itsef
			partnerIndices = checkTiles.findAllIndicesOf(currentTile);
			
			//trim the lists down to size to fit the meld type
			if (currentTileMeldType.isPair()) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PAIR) partnerIndices.remove(partnerIndices.size() - 1);
			if (currentTileMeldType.isPon()) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PON) partnerIndices.remove(partnerIndices.size() - 1);
			return partnerIndices;
		}
		
		
	}
	
	private static final GTL emptyWaitsList(){return new GTL();}
}
