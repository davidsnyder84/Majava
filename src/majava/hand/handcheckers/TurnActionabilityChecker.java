package majava.hand.handcheckers;

import majava.hand.Hand;
import majava.hand.Meld;
import majava.tiles.GameTile;
import majava.util.GameTileList;

//checks what turn actions a hand can perform (ankan, tsumo, etc)
public class TurnActionabilityChecker {
	private static final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
	private static final int INDEX_NOT_FOUND = -1;
	
	private final Hand hand;
	
	public TurnActionabilityChecker(Hand handToCheck){
		hand = handToCheck;
	}
	
	
	
	public boolean ableToMinkan(){return getCandidateMinkanIndex() != INDEX_NOT_FOUND;}
	public boolean ableToAnkan(){return getCandidateAnkanIndex() != INDEX_NOT_FOUND;}
	public boolean ableToRiichi(){return false;}	/////////////////////////////////////////////implement riichi check here
	
	//tsumo (only needs to know if the hand is complete)
	public boolean ableToTsumo(){return hand.isComplete();}
	
	
	//minkan
	public int getCandidateMinkanIndex(){
		for (int currentIndex = 0; currentIndex < hand.size(); currentIndex++)
			if (canMinkan(hand.getTile(currentIndex)))
				return currentIndex;
		return INDEX_NOT_FOUND;
	}
	private boolean canMinkan(GameTile candidate){
		for (Meld m: hand.getMelds())
			if (m.canMinkanWith(candidate))
				return true;
		return false;
	}
	
	
	//ankan
	public int getCandidateAnkanIndex(){
		for (int currentIndex = 0; currentIndex < hand.size(); currentIndex++)
			if (canClosedKan(hand.getTile(currentIndex)))
				return currentIndex;
		return INDEX_NOT_FOUND;
	}
	private boolean canClosedKan(GameTile candidate){
		return hand.findHowManyOf(candidate) >= (NUM_PARTNERS_NEEDED_TO_KAN + 1);
	}
	
}
