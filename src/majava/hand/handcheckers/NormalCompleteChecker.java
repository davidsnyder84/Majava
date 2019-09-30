package majava.hand.handcheckers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.enums.MeldType;
import majava.hand.Meld;
import majava.tiles.GameTile;
import majava.util.GTL;
import static majava.enums.MeldType.*;

//class implementation of AgariChecker's old isCompleteNormal recursive method
public class NormalCompleteChecker{
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2, NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	private static final int DEFAULT_MELD_ORDER = 1;
	private static final boolean IS_COMPLETE = true, NOT_COMPLETE = false;
	
	
	private final GTL handTiles;
	private final List<Meld> finishingMelds;
	
	private final PairPrivelege pairPrivelege;
	private final List<MeldType> remainingMeldTypesToTryForCurrentTile;
	private int permutationOrder;
	
	private NormalCompleteChecker(GTL checkTiles, List<Meld> fm, PairPrivelege pairpriv){
		handTiles = checkTiles;
		finishingMelds = fm;
		pairPrivelege = pairpriv;
		
		permutationOrder = DEFAULT_MELD_ORDER;
		remainingMeldTypesToTryForCurrentTile = allMeldTypes();
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
		resetVariables();
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
			
			if (currentMeldtypeIsImpossibleForCurrentTile()){
				moveToNextMeldtypeForCurrentTile();
				continue;	//exit whileloop early and move to the next meldType
			}
			
			
			takePairPrivelegeIfMeldtypeIsPair();
			
			if (handMinusThisMeld().isComplete()){ //recursive call, for handtiles minus the tiles used for this meld
				addThisMeldToFinishingMelds();
				return IS_COMPLETE;
			}
			
			relinquishPairPrivelegeIfMeldtypeIsPair();
			moveToNextMeldtypeForCurrentTile();
		}
		return NOT_COMPLETE;	//currentTile could not make any meld, so the hand must not be complete
	}
	
	
	//------------------------------below are helper methods
	
	private GameTile currentTile(){
		return handTiles.getFirst();
	}
	private MeldType currentTileMeldType(){
		return remainingMeldTypesToTryForCurrentTile.get(0);
	}
	private boolean currentTileStillHasPossibleMeldtypesRemaining(){
		return !remainingMeldTypesToTryForCurrentTile.isEmpty();
	}
	private void moveToNextMeldtypeForCurrentTile(){
		remainingMeldTypesToTryForCurrentTile.remove(0);
	}
	
	
	
	
	private boolean currentMeldtypeIsImpossibleForCurrentTile(){
		return ( currentTileMeldType().isChi() && !currentTile().canCompleteChiType(currentTileMeldType()) ) ||
				( currentTileMeldType().isPair() && pairPrivelege.isTaken() ) || 
				partnersForCurrentTileAreGone()
				;
	}
	
	private boolean partnersForCurrentTileAreGone(){return !currentTilePartnersAreStillHere();}
	private boolean currentTilePartnersAreStillHere(){
		if (currentTileMeldType().isChi())
			if (!handTiles.contains(currentTilePartnerIDs()[0]) || !handTiles.contains(currentTilePartnerIDs()[1]))
				return false;
		if (currentTileMeldType().isPair() && handTiles.findHowManyOf(currentTile()) < NUM_PARTNERS_NEEDED_TO_PAIR + 1)
			return false;
		if (currentTileMeldType().isPon() && handTiles.findHowManyOf(currentTile()) < NUM_PARTNERS_NEEDED_TO_PON + 1)
			return false;
		
		return true;
	}
	
	private int[] currentTilePartnerIDs(){
		int id = currentTile().getId();
		switch(currentTileMeldType()){
		case CHI_L: return new int[]{id + 1, id + 2};
		case CHI_M: return new int[]{id - 1, id + 1};
		case CHI_H: return new int[]{id - 2, id - 1};
		
		case KAN: return new int[]{id, id, id};
		case PON: return new int[]{id, id};
		case PAIR: return new int[]{id};
		default: return null;
		}
	}
	
	
	private void takePairPrivelegeIfMeldtypeIsPair(){
		if (currentTileMeldType().isPair())
			pairPrivelege.take();
	}
	private void relinquishPairPrivelegeIfMeldtypeIsPair(){
		if (currentTileMeldType().isPair())
			pairPrivelege.relinquish();
	}
	
	
	
	private NormalCompleteChecker handMinusThisMeld(){
		return new NormalCompleteChecker(handTilesMinusThisMeld(), finishingMelds, pairPrivelege);
	}
	
	private void addThisMeldToFinishingMelds(){
		finishingMelds.add(new Meld(toMeldTiles(), currentTileMeldType()));
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
		if (currentTileMeldType().isChi()){
			partnerIndices.add(handTiles.indexOf(currentTilePartnerIDs()[0]));
			partnerIndices.add(handTiles.indexOf(currentTilePartnerIDs()[1]));
			return partnerIndices;
		}
		//else if pon/pair, make sure you don't count the tile itsef
		partnerIndices = handTiles.findAllIndicesOf(currentTile());
		
		//trim the lists down to size to fit the meld type
		if (currentTileMeldType().isPair()) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PAIR) partnerIndices.remove(partnerIndices.size() - 1);
		if (currentTileMeldType().isPon()) while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_PON) partnerIndices.remove(partnerIndices.size() - 1);
		return partnerIndices;
	}
	
	
	private void resetVariables(){
		finishingMelds.clear();
		pairPrivelege.reset();
		remainingMeldTypesToTryForCurrentTile.clear();
		remainingMeldTypesToTryForCurrentTile.addAll(allMeldTypes());
	}
	
	private boolean invalidHandsize(){return (handTiles.size() % 3) != 2;}
	
	private static List<Meld> emptyMeldList(){return new ArrayList<Meld>();}
	
	//all permutations work 100% of the time, but hands with multiple forms will be assigned a different finishing meld form depending on which permutation was used.
	//example: 1111/2222/3333/CC will have a different form depending on whether chi or pon was checked first (123/123/123/123/CC   vs   111/123/222/333/CC)
	private List<MeldType> allMeldTypes(){
		switch (permutationOrder){
		case DEFAULT_MELD_ORDER:
		default: return new ArrayList<MeldType>(Arrays.asList(PON, CHI_L, CHI_M, CHI_H, PAIR));
		}
	}
	
	
	
	
	
	
	
	
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
	
}

