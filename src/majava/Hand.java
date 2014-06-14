package majava;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utility.ConviniList;

import majava.tiles.GameTile;
import majava.tiles.TileInterface;
import majava.util.GameTileList;
import majava.enums.Wind;
import majava.enums.MeldType;


/*
Class: Hand
represents a player's hand of tiles

data:
	mTiles - list of tiles in the hand
	mMelds - list of melds made from the hand
	mChecker - runs checks on the hand
	
	mNumMeldsMade - the number of melds made
	mOwnerSeatWind - the seat wind of the player who owns the hand
	
	mRoundTracker - used to look at round info
	
methods:
	
	constructors:
	Requires player's seat wind - creates list of tiles and melds, initializes hand info, creates a checker
	
	
	public:
		mutators:
		addTile - adds a tile to the hand  (and updates possible calls/actions)
		removeTile - removes the tile at the given index (and updates possible calls/actions)
		sortHand - sorts the hand in ascending order
		
		checkCallableTile - runs checks to see if a given tile is callable. returns true if the tile is callable. (modifies the checker)
		makeMeldChiL, etc - forms a meld of the corresponding type with the most recent discard (mCallCandidate)
		makeMeldTurnAnkan/makeMeldTurnMinkan - forms the corresponding turn meld
	 	
	 	
	 	accessors:
		getSize - returns the number of tiles in the hand
		getTile - returns the tile at the given index in the hand
		
		getNumMeldsMade - returns the number of melds made
		getMelds - returns a list of the melds that have been made (copy of actual melds)
		
		isClosed - returns true if the hand is fully concealed, false if an open meld has been made
		getOwnerSeatWind - returns the hand owner's seat wind
		getTenpaiStatus - returns true if the hand is in tenpai
		getTenpaiWaits - returns the hand's waits, if it is in tenpai
		
		numberOfCallsPossible - returns the number of different calls that can be made on a tile
		ableToChiL, etc - returns true if the corresponding call can be made
		ableToAnkan, etc - returns true if the corresponding action is possible
		
		showMelds, showMeldsCompact - prints all melds to the screen
		toString - returns a string of all tiles in the hand, and their indices
		
		
		other:
		syncWithRoundTracker - associates the hand with the tracker
*/
public class Hand implements Iterable<GameTile>{
	
	private static final int MAX_HAND_SIZE = 14;
	private static final int MAX_NUM_MELDS = 5;
	private static final int AVG_NUM_TILES_PER_MELD = 3;
	
	//for debug use
	private static final boolean DEBUG_SHOW_MELDS_ALONG_WITH_HAND = false;
	
	
	
	
	private final GameTileList mTiles;
	private final List<Meld> mMelds;
	private final HandChecker mChecker;
	
	private final Wind mOwnerSeatWind;
	
	private int mNumMeldsMade;
	
	
	private RoundTracker mRoundTracker;
	
	
	
	//1-arg constructor, takes player's seat wind
	public Hand(Wind playerWind){
		mTiles = new GameTileList(MAX_HAND_SIZE);
		mMelds = new ArrayList<Meld>(MAX_NUM_MELDS);
		
		mNumMeldsMade = 0;
		mOwnerSeatWind = playerWind;
		
		//make a checker for the hand
		mChecker = new HandChecker(this, mTiles, mMelds);
		mRoundTracker = null;
	}
	public Hand(){this(Wind.UNKNOWN);}
	
	
	
	//returns the tile at the given index in the hand, returns null if outside of the hand's range
	public GameTile getTile(int index){
		if (index > mTiles.size() || index < 0 ) return null;
		return (GameTile)mTiles.get(index);
	}
	
	//returns a list of the melds that have been made (copy of actual melds), returns an empty list if no melds made
	public List<Meld> getMelds(){
		List<Meld> meldList = new ArrayList<Meld>(mNumMeldsMade); 
		for (int i = 0; i < mNumMeldsMade; i++)
			meldList.add(mMelds.get(i).clone());
		
		return meldList;
	}
	
	
	
	public int size(){return mTiles.size();}
	public boolean isClosed(){return mChecker.getClosedStatus();}
	public int getNumMeldsMade(){return mNumMeldsMade;}
	
	
	//returns the number of kans the player has made
	public int getNumKansMade(){
		int count = 0;
		for (Meld m: mMelds) if (m.isKan()) count++;
		return count;
	}
	
	//returns the hand owner's seat wind
	public Wind getOwnerSeatWind(){return mOwnerSeatWind;}
	//returns true if the hand is in tenpai
	public boolean getTenpaiStatus(){return mChecker.getTenpaiStatus();}
	
	//returns a list of the hand's tenpai waits
//	public GameTileList getTenpaiWaits(){
//		if (getTenpaiStatus()) return mChecker.getTenpaiWaits();
//		else return null;
//	}
	
	
	
	
	
	
	//adds a tile to the hand (cannot add more than max hand size)
	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand
	public boolean addTile(GameTile addThisTile){
		
		if (mTiles.size() >= MAX_HAND_SIZE - AVG_NUM_TILES_PER_MELD*mNumMeldsMade) return false;
		
		addThisTile.setOwner(mOwnerSeatWind);
		mTiles.add(addThisTile);
		
		__updateChecker();
		return true;
	}
	public boolean addTile(int tileID){return addTile(new GameTile(tileID));}
	
	
	//removes the tile at the given index
	public boolean removeTile(int removeThisIndex){
		
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~HELP I'M BEING RAPED~~~~~~~~~~~~~~~~~~~~~~~");
		
		if (removeThisIndex < 0 || removeThisIndex >= mTiles.size()) return false;

		mTiles.remove(removeThisIndex);
		
		sortHand();
		return true;
	}
	public boolean removeMultiple(List<Integer> removeIndices){
		
		mTiles.removeMultiple(removeIndices);
		
		sortHand();
		return true;
	}
	
	
	
	private void __updateChecker(){
		
		//check if modifying the hand put the hand in tenpai
		mChecker.updateTenpaiStatus();
		
		//update what turn actions are possible after modifying the hand
		mChecker.updateTurnActions();
	}
	
	
	
	
	
	
	//sort the hand in ascending order
	public void sortHand(){
		mTiles.sort();
		
		__updateChecker();
	}
	

	
	
	
	
	
	
	
	

	
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
	public boolean checkCallableTile(GameTile candidate){
		//~~~~return true if a call (any call) can be made
		return mChecker.checkCallableTile(candidate);
	}
	
	//returns true if a specific call can be made on mCallCandidate
	public boolean ableToChiL(){return mChecker.ableToChiL();}
	public boolean ableToChiM(){return mChecker.ableToChiM();}
	public boolean ableToChiH(){return mChecker.ableToChiH();}
	public boolean ableToPon(){return mChecker.ableToPon();}
	public boolean ableToKan(){return mChecker.ableToKan();}
	public boolean ableToRon(){return mChecker.ableToRon();}
	public boolean ableToPair(){return mChecker.ableToPair();}
	
	//turn actions
	public boolean ableToAnkan(){return mChecker.ableToAnkan();}
	public boolean ableToMinkan(){return mChecker.ableToMinkan();}
	public boolean ableToRiichi(){return mChecker.ableToRiichi();}
	public boolean ableToTsumo(){return mChecker.ableToTsumo();}
	
	//returns the number of different calls possible for callCandidate
//	public int numberOfCallsPossible(){return mChecker.numberOfCallsPossible();}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~END MELD CHECKERS
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
	
	/*
	method: makeMeld
	forms a meld of the given type with mCallCandidate
	
	input: meldType is the type of meld to form
	
	
	partnerIndices = choose which partner list to take indices from, based on meld type
	for (each index in partnerIndices)
		add hand(index) to tilesFromHand
	end for	
	
	form a new meld with the tiles
	remove the tiles from the hand
	numMeldsMade++
	update hand's closed status after making the meld
	*/
	private void __makeMeld(MeldType meldType){
		
		//~~~~gather the tiles from the hand that will be in the meld
		//get the list of partner indices, based on the the meld type
		List<Integer> partnerIndices = mChecker.getPartnerIndices(meldType);

		//list of TILES, will hold the tiles coming from the hand that will be in the meld
		GameTileList tilesFromHand = mTiles.getMultiple(partnerIndices);
		
		//candidateTile = the tile that will complete the meld
		GameTile candidateTile = mChecker.getCallCandidate();
		
		
		//~~~~make the meld and remove the tiles from the hand
		//make the meld, add to the list of melds
		mMelds.add(new Meld(tilesFromHand, candidateTile, meldType));
		
		//remove the tiles from the hand
		removeMultiple(partnerIndices);
		mNumMeldsMade++;
		
		//update the hand's closed status after making the meld
		mChecker.updateClosedStatus();
		
	}
	public void makeMeldChiL(){__makeMeld(MeldType.CHI_L);}
	public void makeMeldChiM(){__makeMeld(MeldType.CHI_M);}
	public void makeMeldChiH(){__makeMeld(MeldType.CHI_H);}
	public void makeMeldPon(){__makeMeld(MeldType.PON);}
	public void makeMeldKan(){__makeMeld(MeldType.KAN);}
	
	
	
	
	
	
	
	
	
	private void __makeClosedMeld(MeldType meldType){
		
		GameTileList handTiles = new GameTileList();
		GameTile candidate;
		int candidateIndex;
		List<Integer> partnerIndices;
		
		final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
		
		if (meldType.isKan()){
			
			candidateIndex = mChecker.getCandidateAnkanIndex();
			candidate = mTiles.get(candidateIndex);
			
			partnerIndices = mTiles.findAllIndicesOf(candidate);
			while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_KAN) partnerIndices.remove(partnerIndices.size() - 1);
			
			
			handTiles = mTiles.getMultiple(partnerIndices);
			
			mMelds.add(new Meld(handTiles, candidate, meldType));
			
			//remove the tiles from the hand
			partnerIndices.add(candidateIndex);
			removeMultiple(partnerIndices);
			
			
			mNumMeldsMade++;
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
		
		int candidateIndex = mChecker.getCandidateMinkanIndex();
		GameTile candidate = mTiles.get(candidateIndex);
		
		Meld meldToUpgrade = null;
		
		//find the pon
		for (Meld m: mMelds)
			if (m.isPon() && m.getFirstTile().equals(candidate))
				meldToUpgrade = m;
		
		//upgrade the pon to a kan
		meldToUpgrade.upgradeToMinkan(candidate);
		
		//remove the tile from the hand
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
	
	//true returns a string of indices (indices are +1 to match display)
	//false returns a string of actual tile values
	public String DEMOpartnerIndicesString(MeldType meldType, boolean wantActualTiles){
		
		String partnersString = "";
		List<Integer> wantedIndices = null;
		wantedIndices = mChecker.getPartnerIndices(meldType);
		
		for (Integer i: wantedIndices)
			if (wantActualTiles) partnersString += mTiles.get(i).toString() + ", ";
			else partnersString += Integer.toString(i+1) + ", ";
		
		if (partnersString != "") partnersString = partnersString.substring(0, partnersString.length() - 2);
		return partnersString;
	}
	public String DEMOpartnerIndicesString(MeldType meldType){return DEMOpartnerIndicesString(meldType, false);}
	
	//returns a list of hot tile IDs for ALL tiles in the hand
	public List<Integer> DEMOfindAllHotTiles(){return mChecker.DEMOfindAllHotTiles();}
	//returns a list of callable tile IDs for ALL tiles in the hand
	public List<Integer> DEMOfindAllCallableTiles(){return mChecker.DEMOfindAllCallableTiles();}
	
	public HandChecker DEMOgetChecker(){return mChecker;}
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//xxxxEND DEMO METHODS
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//string representation of all melds
	public String getAsStringMelds(){
		String ms = ""; int mnum = 0;
		for (Meld m: mMelds) ms += "+++Meld " + (++mnum) + ": " + m;
		return ms;
	}
	//compact string representation of all melds
	public String getAsStringMeldsCompact(){
		String ms = "";
		for (Meld m: mMelds) ms += "[" + m.toStringCompact() + "] ";
		return ms;
	}
	
	
	
	
	
	
	//returns string of all tiles in the hand, and their indices
	@Override
	public String toString(){
		
		String handString = "";
		int i;
		
		//show indices above the hand
		for (i = 0; i < mTiles.size(); i++)
			if (i+1 < 10) handString += (i+1) + "  ";
			else handString += (i+1) + " ";
		handString += "\n";
		
		//show the tiles, separated by spaces
		for (i = 0; i < mTiles.size(); i++)
			handString += mTiles.get(i).toString() + " ";
		
		//show the completed melds
		if (DEBUG_SHOW_MELDS_ALONG_WITH_HAND)
			for (i = 0; i < mMelds.size(); i++)
				handString+= "\n+++Meld " + (i+1) + ": " + mMelds.get(i).toString();
		
		return handString;
	}
	
	//iterator, returns mTile's iterator
	@Override
	public Iterator<GameTile> iterator() {return mTiles.iterator();}
	
	
	
	
	
	
	//sync hand tilelist and meldlist with tracker
	public void syncWithRoundTracker(RoundTracker tracker){
		if (mRoundTracker != null) return;
		mRoundTracker = tracker;
		mRoundTracker.syncHand(mTiles, mMelds);
	}
	
	

}
