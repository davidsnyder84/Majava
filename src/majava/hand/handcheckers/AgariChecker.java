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
		if (waits.isEmpty()) waits = waits.addAll(getKokushiWaits());
		if (waits.isEmpty()){waits = waits.addAll(getChiitoiWait());}
		
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
			GTL waits = emptyWaitsList();
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
		
		private final Hand myHand;
		private final GTL handTiles;
		
		public NormalAgariChecker(Hand handToCheck){
			myHand = handToCheck;
			handTiles = myHand.getTiles();
		}
		
		
		private GTL getNormalTenpaiWaits(){
			GTL waits = emptyWaitsList();
			
			final List<Integer> hotTileIDs = TileKnowledge.findAllHotTiles(handTiles);
			for (Integer id: hotTileIDs){
				//get a hot tile (and mark it with the hand's seat wind, so chi is valid)
				GameTile currentHotTile = new GameTile(id).withOwnerWind(myHand.getOwnerSeatWind());
				
				//check if adding the tile causes the hand to be complete
				if (isCompleteNormal(handTiles.add(currentHotTile)))
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
			//^^^make a HandCheckerTilesList class for this
			
			CompleteNormalHander normalHandChecker = new CompleteNormalHander(populatedCheckTiles);
			if (finishingMelds != null) finishingMelds.addAll(normalHandChecker.getFinishingMelds());
			return normalHandChecker.isCompleteNormal();
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
			return finishingMelds;
		}
		
		
	}
	
	private static final GTL emptyWaitsList(){return new GTL();}
}
