package majava.hand;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import majava.hand.handcheckers.AgariChecker;
import majava.hand.handcheckers.CallabilityChecker;
import majava.hand.handcheckers.TurnActionabilityChecker;
import majava.tiles.GameTile;
import majava.util.GameTileList;
import majava.util.TileKnowledge;
import majava.enums.Wind;
import majava.enums.MeldType;


//represents a player's hand (Žè”v) of tiles
public class Hand implements Iterable<GameTile>, Cloneable{	
	private static final int MAX_HAND_SIZE = 14;
	private static final int MAX_NUM_MELDS = 5;
	private static final int AVG_NUM_TILES_PER_MELD = 3;
	
	
	private final GameTileList tiles;
	private final List<Meld> melds;
	
	private final CallabilityChecker callabilityChecker;
	private final TurnActionabilityChecker turnActionabilityChecker;
	private final AgariChecker agariChecker;
	
	
	
	public Hand(){
		tiles = new GameTileList(MAX_HAND_SIZE);
		melds = new ArrayList<Meld>(MAX_NUM_MELDS);
		
		//make checkers
		callabilityChecker = new CallabilityChecker(this, tiles);
		turnActionabilityChecker = new TurnActionabilityChecker(this);
		agariChecker = new AgariChecker(this, tiles);
	}
	public Hand (Hand other){
		tiles = other.tiles.clone();
		melds = new ArrayList<Meld>(MAX_NUM_MELDS);
		for (Meld m: other.melds) melds.add(m.clone());
		
		callabilityChecker = new CallabilityChecker(this, tiles);
		turnActionabilityChecker = new TurnActionabilityChecker(this);
		agariChecker = new AgariChecker(this, tiles);
	}
	public Hand clone(){return new Hand(this);}
	
	
	
	//returns the tile at the given index in the hand, returns null if outside of the hand's range
	public GameTile getTile(int index){
		if (index > size() || index < 0 ) return null;
		return tiles.get(index);
	}
	public int findHowManyOf(GameTile t){return tiles.findHowManyOf(t);}
	public GameTileList getTilesAsList(){return tiles.clone();}
	
	//returns a list of the melds that have been made (copy of actual melds), returns an empty list if no melds made
	public List<Meld> getMelds(){
		List<Meld> meldList = new ArrayList<Meld>();
		for (Meld m: melds)
			meldList.add(m.clone());
		
		return meldList;
	}

	public List<Meld> getFinishingMelds(){return agariChecker.getFinishingMelds();}
	
	public boolean isFull(){return (size() % 3) == (MAX_HAND_SIZE % 3);}
	
	
	
	public int size(){return tiles.size();}
	public boolean isClosed(){
		for (Meld m: melds) if (m.isOpen()) return false;
		return true;
	}
	public int numberOfMeldsMade(){return melds.size();}
	
	
	//returns the number of kans the player has made
	public int getNumKansMade(){
		int count = 0;
		for (Meld m: melds) if (m.isKan()) count++;
		return count;
	}
	
	public Wind getOwnerSeatWind(){return tiles.getFirst().getOrignalOwner();}
	//returns true if the hand is in tenpai
	public boolean isInTenpai(){return agariChecker.isInTenpai();}
	public GameTileList getTenpaiWaits(){return agariChecker.getTenpaiWaits();}
	
	
	public boolean isComplete(){return agariChecker.isComplete();}
	public boolean isCompleteNormal(){return agariChecker.isCompleteNormal();}	
	public boolean isCompleteChiitoitsu(){return agariChecker.isCompleteChiitoitsu();}
	public boolean isTenpaiChiitoitsu(){return agariChecker.isTenpaiChiitoitsu();}
	public boolean isCompleteKokushi(){return agariChecker.isCompleteKokushi();}
	public boolean isTenpaiKokushi(){return agariChecker.isTenpaiKokushi();}
	
	
	
	
	
	//adds a tile to the hand (cannot add more than max hand size)
	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand
	public boolean addTile(GameTile addThisTile){
		if (size() >= MAX_HAND_SIZE - AVG_NUM_TILES_PER_MELD*numberOfMeldsMade()) return false;
		
		tiles.add(addThisTile);
		return true;
	}
	public boolean addTile(int tileID){return addTile(new GameTile(tileID));}
	
	
	//removes the tile at the given index, returns null if out of range
	public GameTile removeTile(int removeThisIndex){
		if (removeThisIndex < 0 || removeThisIndex > size()) return null;
		GameTile tile = tiles.remove(removeThisIndex);
		sort();
		
		return tile;
	}
	public boolean removeMultiple(List<Integer> removeIndices){
		tiles.removeMultiple(removeIndices);
		sort();
		return true;
	}
	
	//sort the hand in ascending order
	public void sort(){tiles.sort();}
	

	
	
	
	

	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~BEGIN MELD CHECKERS
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/*
	method: checkCallableTile
	runs checks to see if a given tile is callable
	input: candidate is the tile to check if callable
	
	send candidate tile to checker (flags are set and lists are populated here)
	if any call can be made, return true. else, return false
	*/
	public boolean canCallTile(GameTile candidate){
		//~~~~return true if a call (any call) can be made
		return callabilityChecker.tileIsCallable(candidate);
	}
	
	//returns true if a specific call can be made on mCallCandidate
	public boolean ableToChiL(GameTile tileToReactTo){return callabilityChecker.ableToChiL(tileToReactTo);}
	public boolean ableToChiM(GameTile tileToReactTo){return callabilityChecker.ableToChiM(tileToReactTo);}
	public boolean ableToChiH(GameTile tileToReactTo){return callabilityChecker.ableToChiH(tileToReactTo);}
	public boolean ableToPon(GameTile tileToReactTo){return callabilityChecker.ableToPon(tileToReactTo);}
	public boolean ableToKan(GameTile tileToReactTo){return callabilityChecker.ableToKan(tileToReactTo);}
	public boolean ableToRon(GameTile tileToReactTo){return callabilityChecker.ableToRon(tileToReactTo);}
//	public boolean ableToPair(){return handChecker.ableToPair();}
	
	//turn actions
	public boolean ableToAnkan(){return turnActionabilityChecker.ableToAnkan();}
	public boolean ableToMinkan(){return turnActionabilityChecker.ableToMinkan();}
	public boolean ableToRiichi(){return turnActionabilityChecker.ableToRiichi();}
	public boolean ableToTsumo(){return turnActionabilityChecker.ableToTsumo();}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~END MELD CHECKERS
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
	
	//forms a meld of the given type. claimedTile = the tile that will complete the meld
	//partnerIndices = choose which partner list to take indices from, based on meld type
	private void __makeMeld(GameTile claimedTile, MeldType meldType){
		
		//~~~~gather the tiles from the hand that will be in the meld
		//get the list of partner indices, based on the the meld type
		List<Integer> partnerIndices = callabilityChecker.getPartnerIndices(claimedTile, meldType);

		//list of TILES, will hold the tiles coming from the hand that will be in the meld
		GameTileList tilesFromHand = tiles.getMultiple(partnerIndices);
		
		//make the meld
		melds.add(new Meld(tilesFromHand, claimedTile, meldType));
		
		//remove the tiles from the hand 
		removeMultiple(partnerIndices);		
	}
	public void makeMeldChiL(GameTile claimedTile){__makeMeld(claimedTile, MeldType.CHI_L);}
	public void makeMeldChiM(GameTile claimedTile){__makeMeld(claimedTile, MeldType.CHI_M);}
	public void makeMeldChiH(GameTile claimedTile){__makeMeld(claimedTile, MeldType.CHI_H);}
	public void makeMeldPon(GameTile claimedTile){__makeMeld(claimedTile, MeldType.PON);}
	public void makeMeldKan(GameTile claimedTile){__makeMeld(claimedTile, MeldType.KAN);}
	
	
	
	
	
	
	
	
	
	private void __makeClosedMeld(MeldType meldType){
		
		GameTileList handTiles = new GameTileList();
		GameTile candidate;
		int candidateIndex;
		List<Integer> partnerIndices;
		
		final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
		
		if (meldType.isKan()){
			
			candidateIndex = turnActionabilityChecker.getCandidateAnkanIndex();
			candidate = getTile(candidateIndex);			
			
			partnerIndices = tiles.findAllIndicesOf(candidate);
			while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_KAN) partnerIndices.remove(partnerIndices.size() - 1);
			
			
			handTiles = tiles.getMultiple(partnerIndices);
			
			melds.add(new Meld(handTiles, candidate, meldType));
			
			//remove the tiles from the hand
			partnerIndices.add(candidateIndex);
			removeMultiple(partnerIndices);
		}
	}
	private void __makeClosedMeldKan(){__makeClosedMeld(MeldType.KAN);}
//	public void makeClosedMeldPon(){__makeClosedMeld(MeldType.PON);}
//	public void makeClosedMeldChiL(){__makeClosedMeld(MeldType.CHI_L);}
//	public void makeClosedMeldChiM(){__makeClosedMeld(MeldType.CHI_M);}
//	public void makeClosedMeldChiH(){__makeClosedMeld(MeldType.CHI_H);}
	
	
	
	
	
	
	public void makeMeldTurnAnkan(){
		__makeClosedMeldKan();
	}
	
	public void makeMeldTurnMinkan(){
		int candidateIndex = turnActionabilityChecker.getCandidateMinkanIndex();
		GameTile candidate = getTile(candidateIndex);
		
		Meld meldToUpgrade = null;
		
		//find the pon
		for (Meld m: melds)
			if (m.canMinkanWith(candidate))
				meldToUpgrade = m;
		
		meldToUpgrade.upgradeToMinkan(candidate);
		removeTile(candidateIndex);
	}
	
	
	
	
	
	
	

	
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//xxxxBEGIN DEMO METHODS
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//demo values
	public void DEMOfillHelter(){
		int[] ids = {2,4,6,8,10,12,14,16,18,20,22,24,26,28};
		for (int i: ids) addTile(i);
	}
	public void DEMOfillChuuren(int lastTile){
		int[] ids = {1,1,1,2,3,4,5,6,7,8,9,9,9,lastTile};
		for (int i: ids) addTile(i);
	}
	public void DEMOfillChuuren(){DEMOfillChuuren(8);}	
	public void DEMOsetOwner(Wind owner){for (GameTile t: tiles) t.setOwner(owner);}
	
//	//true returns a string of indices (indices are +1 to match display)
//	//false returns a string of actual tile values
//	public String DEMOpartnerIndicesString(MeldType meldType, boolean wantActualTiles){
//		
//		String partnersString = "";
//		List<Integer> wantedIndices = null;
//		wantedIndices = handChecker.getPartnerIndices(meldType);
//		
//		for (Integer i: wantedIndices)
//			if (wantActualTiles) partnersString += getTile(i).toString() + ", ";
//			else partnersString += Integer.toString(i+1) + ", ";
//		
//		if (partnersString != "") partnersString = partnersString.substring(0, partnersString.length() - 2);
//		return partnersString;
//	}
//	public String DEMOpartnerIndicesString(MeldType meldType){return DEMOpartnerIndicesString(meldType, false);}
	
	//returns a list of hot tile IDs for ALL tiles in the hand
	public List<Integer> DEMOfindAllHotTiles(){return TileKnowledge.findAllHotTiles(tiles);}
	
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//xxxxEND DEMO METHODS
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	
	
	
	
	
	
	
	
	//string representation of all melds
	public String getAsStringMelds(){
		String meldsString = ""; int mnum = 0;
		for (Meld m: melds) meldsString += "+++Meld " + (++mnum) + ": " + m;
		return meldsString;
	}
	//compact string representation of all melds
	public String getAsStringMeldsCompact(){
		String meldsString = "";
		for (Meld m: melds) meldsString += "[" + m.toStringTilesOnly() + "] ";
		return meldsString;
	}
	
	
	
	
	@Override
	public String toString(){
		String handString = "";
		final boolean DEBUG_SHOW_MELDS_ALONG_WITH_HAND = false;
		
		//show indices above the hand
		for (int i = 0; i < size(); i++)
			if (i+1 < 10) handString += (i+1) + "  ";
			else handString += (i+1) + " ";
		handString += "\n";
		
		//show the tiles, separated by spaces
		for (int i = 0; i < size(); i++)
			handString += getTile(i) + " ";
		
		//show the completed melds
		if (DEBUG_SHOW_MELDS_ALONG_WITH_HAND)
			for (int i = 0; i < melds.size(); i++)
				handString+= "\n+++Meld " + (i+1) + ": " + melds.get(i).toString();
		
		return handString;
	}
	
	@Override
	public Iterator<GameTile> iterator() {return tiles.iterator();}
	
	public static int maxHandSize(){return Hand.MAX_HAND_SIZE;}
}
