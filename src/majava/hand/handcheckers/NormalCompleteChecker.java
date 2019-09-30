package majava.hand.handcheckers;

import java.util.ArrayList;
import java.util.List;

import majava.enums.MeldType;
import majava.hand.Meld;
import majava.tiles.HandCheckerTile;
import majava.util.GTL;

//class implementation of AgariChecker's old isCompleteNormal recursive method
public class NormalCompleteChecker{
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	private static final boolean IS_COMPLETE = true, NOT_COMPLETE = false;
	
	
	private final GTL handTiles;
	private final List<Meld> finishingMelds;
	private final PairPrivelege pairPrivelege;
	
	private NormalCompleteChecker(GTL checkTiles, List<Meld> fm, PairPrivelege pairpriv){
		handTiles = checkTiles;
		finishingMelds = fm;
		pairPrivelege = pairpriv;
		
		if (!handTiles.isEmpty())
			currentTile = new HandCheckerTile(handTiles.getFirst());
	}
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~start public methods~~~~~~~~~~~~~~~~~~~~
	
	//public constructor
	public NormalCompleteChecker(GTL checkTiles){
		this(
			checkTiles.sort(),
			emptyMeldList(),
			new PairPrivelege()
			);
	}
	
	public boolean isCompleteNormal(){
		finishingMelds.clear();
		pairPrivelege.reset();
		if (invalidHandsize()) return false;
		
		return isComplete();
	}
	
	public List<Meld> getFinishingMelds(){
		if (isCompleteNormal())
			return new ArrayList<Meld>(finishingMelds);
		
		return emptyMeldList();
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~end public methods~~~~~~~~~~~~~~~~~~~~~
	
	
	
	
	private boolean isComplete(){
		if (handTiles.isEmpty()) return IS_COMPLETE;	//if the hand is empty, it is complete (base case)
		
		while(currentTileStillHasPossibleMeldtypesRemaining()){
			
			moveToNextMeldtypeForCurrentTile();
			
			if (currentMeldtypeIsImpossibleForCurrentTile())
				continue;	//exit whileloop early and move to the next meldType
			
			
			takePairPrivelegeIfMeldtypeIsPair();
			
			if (handMinusThisMeld().isComplete()){ //recursive call, for handtiles minus the tiles used for this meld
				addThisMeldToFinishingMelds();
				return IS_COMPLETE;
			}
			else{
				relinquishPairPrivelegeIfMeldtypeIsPair();
			}
		}
		return NOT_COMPLETE;	//currentTile could not make any meld, so the hand must not be complete
	}
	
	
	//----------------------------------------------------------------------------below are helper methods
	
	
	//these are member variables only for convenince, just so I don't have to pass 3 arguments for every method call
	private HandCheckerTile currentTile;
	private MeldType currentTileMeldType;
	private int[] currentTilePartnerIDs;
	
	
	private boolean currentTileStillHasPossibleMeldtypesRemaining(){
		return !currentTile.mstackIsEmpty();
	}
	
	private void moveToNextMeldtypeForCurrentTile(){
		//~~~~Verify that currentTile's partners are still in the hand
		currentTilePartnerIDs = currentTile.mstackTopPartnerIDs();
		currentTileMeldType = currentTile.mstackTop();
		currentTile = currentTile.mstackPop();	//(remove it)
	}
	
	private boolean currentMeldtypeIsImpossibleForCurrentTile(){
		return partnersForCurrentTileAreGone() ||
				(currentTileMeldType.isPair() && pairPrivelege.isTaken());
	}
	
	private boolean partnersForCurrentTileAreGone(){return !currentTilePartnersAreStillHere();}
	private boolean currentTilePartnersAreStillHere(){
		if (currentTileMeldType.isChi())
			if (!handTiles.contains(currentTilePartnerIDs[0]) || !handTiles.contains(currentTilePartnerIDs[1]))
				return false;
		if (currentTileMeldType.isPair() && handTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PAIR + 1)
			return false;
		if (currentTileMeldType.isPon() && handTiles.findHowManyOf(currentTile) < NUM_PARTNERS_NEEDED_TO_PON + 1)
			return false;
		
		return true;
	}
	
	
	
	private void takePairPrivelegeIfMeldtypeIsPair(){
		if (currentTileMeldType.isPair())
			pairPrivelege.take();
	}
	private void relinquishPairPrivelegeIfMeldtypeIsPair(){
		if (currentTileMeldType.isPair())
			pairPrivelege.relinquish();
	}
	
	
	
	private NormalCompleteChecker handMinusThisMeld(){
		return new NormalCompleteChecker(handTilesMinusThisMeld(), finishingMelds, pairPrivelege);
	}
	
	private void addThisMeldToFinishingMelds(){
		finishingMelds.add(new Meld(toMeldTiles(), currentTileMeldType));
	}
	
	private GTL handTilesMinusThisMeld(){
		return handTiles.removeMultiple(partnerIndices()).removeFirst();
	}
	private GTL toMeldTiles(){
		return handTiles.getMultiple(partnerIndices()).add(handTiles.getFirst());
	}
	
	private List<Integer> partnerIndices(){
		List<Integer> partnerIndices = new ArrayList<Integer>();
		//if chi, just find the partners
		if (currentTileMeldType.isChi()){
			partnerIndices.add(handTiles.indexOf(currentTilePartnerIDs[0]));
			partnerIndices.add(handTiles.indexOf(currentTilePartnerIDs[1]));
			return partnerIndices;
		}
		//else if pon/pair, make sure you don't count the tile itsef
		partnerIndices = handTiles.findAllIndicesOf(currentTile);
		
		//trim the lists down to size to fit the meld type
		if (currentTileMeldType.isPair()) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PAIR) partnerIndices.remove(partnerIndices.size() - 1);
		if (currentTileMeldType.isPon()) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PON) partnerIndices.remove(partnerIndices.size() - 1);
		return partnerIndices;
	}
	
	
	private boolean invalidHandsize(){return (handTiles.size() % 3) != 2;}
	
	
	
	
	
	
	private static class PairPrivelege{
		private boolean taken;
		
		private PairPrivelege(){
			taken = false;
		}
		
		public boolean isTaken(){return taken;}
		public void take(){taken = true;}
		public void relinquish(){taken = false;}
		public void reset(){relinquish();}
	}
	
	private static List<Meld> emptyMeldList(){return new ArrayList<Meld>();}
}

