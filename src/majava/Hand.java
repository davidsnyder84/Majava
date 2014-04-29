package majava;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import majava.tiles.Tile;

import utility.MahList;


/*
Class: Hand
represents a player's hand of tiles

data:
	mTiles - list of tiles in the hand
	mMelds - list of melds made from the hand
	mChecker - runs checks on the hand. is final.
	
	mNumMeldsMade - the number of melds made
	mOwnerSeatWind - holds the wind of the player who owns the hand
	
methods:
	
	constructors:
	1-arg, takes player's seat wind - creates list of tiles and melds, initializes hand info, creates a checker
	
	mutators:
	addTile - adds a tile to the hand (overloaded for actual tile, or an integer tile ID)
	removeTile - removes the tile at the given index, and checks if removing the tile puts the hand in tenpai
	sortHand - sorts the hand in ascending order
 	
 	
 	accessors:
	getSize - returns the number of tiles in the hand
	getTile - returns the tile at the given index in the hand
 	contains - returns true if the hand contains a copy of the given tile
	
	getNumMeldsMade - returns the number of melds made
	howManyOfThisTileInHand - returns how many copies of a given tile are in the hand
	
	getTenpaiStatus - returns true if the hand is in tenpai
	isClosed - returns true if the hand is fully concealed, false if an open meld has been made
	ableToChiL, ableToChiM, ableToChiH, ableToPon, ableToKan, ableToRon - return true if a call can be made
	
	
	other:
	checkCallableTile - runs checks to see if a given tile is callable. returns true if the tile is callable.
	numberOfCallsPossible - returns the number of different calls that can be made on a tile
	makeMeld - forms a meld of the given type with mCallCandidate
	
	showMelds - prints all melds to the screen
	toString - returns string of all tiles in the hand, and their indices
*/
public class Hand implements Iterable<Tile>{
	
	public static final int MAX_HAND_SIZE = 14;
	public static final int MAX_NUM_MELDS = 5;
	public static final int AVG_NUM_TILES_PER_MELD = 3;
	
	private enum ModifyAction {ADD, REMOVE;}
	
	//for debug use
	public static final boolean DEBUG_SHOW_MELDS_ALONG_WITH_HAND = false;
	
	
	
	
	private final TileList mTiles;
	private final ArrayList<Meld> mMelds;
	//is public right now for debug purposes, but it shouldn't be
	public final HandChecker mChecker;
	
	
	private int mNumMeldsMade;
	private char mOwnerSeatWind;
	
	
	private RoundTracker mRoundTracker;
	
	
	
	//1-arg constructor, takes player's seat wind
	public Hand(char playerWind){
		mTiles = new TileList(MAX_HAND_SIZE);
		mMelds = new ArrayList<Meld>(MAX_NUM_MELDS);
		
		mNumMeldsMade = 0;
		mOwnerSeatWind = playerWind;
		
		//make a checker for the hand
		mChecker = new HandChecker(this, mTiles, mMelds);
		mRoundTracker = null;
	}
	public Hand(){this(Player.SEAT_UNDECIDED);}
	
	
	
	//returns the tile at the given index in the hand, returns null if outside of the hand's range
	public Tile getTile(int index){
		if (index > mTiles.size() || index < 0 ) return null;
		return mTiles.get(index);
	}
	
	//returns a list of the melds that have been made (copy of actual melds), returns an empty list if no melds made
	public ArrayList<Meld> getMelds(){
		ArrayList<Meld> meldList = new ArrayList<Meld>(0); 
		for (int i = 0; i < mNumMeldsMade; i++)
			meldList.add(new Meld(mMelds.get(i)));
		
		return meldList;
	}
	
	
	
	//returns the number of tiles in the hand
	public int getSize(){return mTiles.size();}
	//returns true if the hand is fully concealed, false if an open meld has been made
	public boolean isClosed(){return mChecker.getClosedStatus();}
	//returns the number of melds made
	public int getNumMeldsMade(){return mNumMeldsMade;}
	
	//returns the number of kans the player has made
	public int getNumKansMade(){
		int count = 0;
		for (Meld m: mMelds) if (m.isKan()) count++;
		return count;
	}
	
	//returns the hand owner's seat wind
	public char getOwnerSeatWind(){return mOwnerSeatWind;}
	//returns true if the hand is in tenpai
	public boolean getTenpaiStatus(){return mChecker.getTenpaiStatus();}
	
	//returns a list of the hand's tenpai waits
	public TileList getTenpaiWaits(){
		if (getTenpaiStatus()) return mChecker.getTenpaiWaits();
		else return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//adds a tile to the hand (cannot add more than max hand size)
	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand
	public boolean addTile(Tile t){return __modifyHand(ModifyAction.ADD, t);}
	public boolean addTile(int tileID){return addTile(new Tile(tileID));}
	
	
	//removes the tile at the given index
	public boolean removeTile(int index){return __modifyHand(ModifyAction.REMOVE, index);}
	
	
	/*
	method: __modifyHand
	removes a tile from or adds a tile to a hand
	
	if (add): add the tile to the hand
	if (remove): remove the tile, sort the hand
	
	check if modifying the hand put the hand in tenpai
	update what turn actions are possible after modifying the hand
	*/
	private boolean __modifyHand(ModifyAction modType, Tile addThisTile, int removeThisIndex){
		
		if (modType == ModifyAction.ADD && addThisTile != null){
			
			if (mTiles.size() < MAX_HAND_SIZE - AVG_NUM_TILES_PER_MELD*mNumMeldsMade){
				addThisTile.setOwner(mOwnerSeatWind);
				mTiles.add(addThisTile);
			}
			else return false;
		}
		else if (modType == ModifyAction.REMOVE){
			
			if (removeThisIndex >= 0 && removeThisIndex < mTiles.size()){
				mTiles.remove(removeThisIndex);
				sortHand();
			}
			else return false;
		}
		else return false;
		

		//check if modifying the hand put the hand in tenpai
		mChecker.updateTenpaiStatus();
		//update what turn actions are possible after modifying the hand
		mChecker.updateTurnActions();
		
		return true;
	}
	private boolean __modifyHand(ModifyAction modType, Tile addThisTile){return __modifyHand(ModifyAction.ADD, addThisTile, -1);}
	private boolean __modifyHand(ModifyAction modType, int removeThisIndex){return __modifyHand(ModifyAction.REMOVE, null, removeThisIndex);}
	
	
	
	
	
	
	
	
	//returns true if the hand contains an occurence of tile t
	public boolean contains(Tile t){
		return mTiles.contains(t);
	}
	//overloaded to accept tile id instead of actual tile
	public boolean contains(int id){return contains(new Tile(id));}
	
	
	
	//sort the hand in ascending order
	public void sortHand(){
		mTiles.sort();
		
		//check if modifying the hand put the hand in tenpai
		mChecker.updateTenpaiStatus();
		//update what turn actions are possible after modifying the hand
		mChecker.updateTurnActions();
	}
	

	
	
	
	
	
	
	
	

	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~BEGIN MELD CHEKCERS
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/*
	method: checkCallableTile
	runs checks to see if a given tile is callable
	input: candidate is the tile to check if callable
	
	send candidate tile to checker (flags are set and lists are populated here)
	if any call can be made, return true. else, return false
	*/
	public boolean checkCallableTile(Tile candidate){
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
	public int numberOfCallsPossible(){return mChecker.numberOfCallsPossible();}
	
	//returns how many copies of tile t are in the hand
	public int howManyOfThisTileInHand(Tile t){return mChecker.howManyOfThisTileInHand(t);}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~END MELD CHEKCERS
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
	*///TODO coupling
	private void __makeMeld(MeldType meldType){
		
		//~~~~gather the tiles from the hand that will be in the meld
		//get the list of partner indices, based on the the meld type
		ArrayList<Integer> partnerIndices = mChecker.getPartnerIndices(meldType);

		//list of TILES, will hold the tiles coming from the hand that will be in the meld
		TileList tilesFromHand = mTiles.getMultiple(partnerIndices);
		
		//candidateTile = the tile that will complete the meld
		Tile candidateTile = mChecker.getCallCandidate();
		
		
		//~~~~make the meld and remove the tiles from the hand
		//make the meld, add to the list of melds
		mMelds.add(new Meld(tilesFromHand, candidateTile, meldType));
		
		//remove the tiles from the hand
		while (partnerIndices.isEmpty() == false){
			removeTile(partnerIndices.get(partnerIndices.size() - 1).intValue());
			partnerIndices.remove(partnerIndices.size() - 1);
		}
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
		
		TileList handTiles = new TileList();
		Tile candidate;
		int candidateIndex;
		MahList<Integer> partnerIndices;
		
		if (meldType.isKan()){

			candidateIndex = mChecker.getCandidateAnkanIndex();
			candidate = mTiles.get(candidateIndex);
			
			partnerIndices = mTiles.findAllIndicesOf(candidate);
			while(partnerIndices.size() > HandChecker.NUM_PARTNERS_NEEDED_TO_KAN) partnerIndices.removeLast();
			
			handTiles = mTiles.getMultiple(partnerIndices);
			
			mMelds.add(new Meld(handTiles, candidate, meldType));
			
			//remove the tiles from the hand
			partnerIndices.add(candidateIndex);
			partnerIndices.sortDescending();
			for (Integer i: partnerIndices) removeTile(i);
			

			mNumMeldsMade++;
		}
	}
	public void makeClosedMeldKan(){__makeClosedMeld(MeldType.KAN);}
//	public void makeClosedMeldPon(){__makeClosedMeld(MeldType.PON);}
//	public void makeClosedMeldChiL(){__makeClosedMeld(MeldType.CHI_L);}
//	public void makeClosedMeldChiM(){__makeClosedMeld(MeldType.CHI_M);}
//	public void makeClosedMeldChiH(){__makeClosedMeld(MeldType.CHI_H);}
	
	
	
	
	
	
	
	public void makeMeldTurnAnkan(){
		
		makeClosedMeldKan();
	}
	
	
	
	public void makeMeldTurnMinkan(){
		Tile candidate = mChecker.getCandidateMinkan();
		
		int candidateIndex = mChecker.getCandidateMinkanIndex();
		candidate = mTiles.get(candidateIndex);
		
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
	public void fillChuuren(int lastTile){
		int[] ids = {1,1,1,2,3,4,5,6,7,8,9,9,9,lastTile};
		for (int i: ids) addTile(i);
	}
	public void fillChuuren(){fillChuuren(8);}
	
	//true returns a string of indices (indices are +1 to match display)
	//false returns a string of actual tile values
	public String partnerIndicesString(MeldType meldType, boolean wantActualTiles){
		
		String partnersString = "";
		ArrayList<Integer> wantedIndices = null;
		wantedIndices = mChecker.getPartnerIndices(meldType);
		
		for (Integer i: wantedIndices)
			if (wantActualTiles) partnersString += mTiles.get(i).toString() + ", ";
			else partnersString += Integer.toString(i+1) + ", ";
		
		if (partnersString != "") partnersString = partnersString.substring(0, partnersString.length() - 2);
		return partnersString;
	}
	public String partnerIndicesString(MeldType meldType){return partnerIndicesString(meldType, false);}
	
	//returns a list of hot tile IDs for ALL tiles in the hand
	public ArrayList<Integer> findAllHotTiles(){return mChecker.findAllHotTiles();}
	//returns a list of callable tile IDs for ALL tiles in the hand
	public ArrayList<Integer> findAllCallableTiles(){return mChecker.findAllCallableTiles();}
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//xxxxEND DEMO METHODS
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	
	public HandChecker DEMOgetChecker(){return mChecker;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//prints all melds to the screen
	public void showMelds(){
		for (int i = 0; i < mMelds.size(); i++)
			System.out.println("+++Meld " + (i+1) + ": " + mMelds.get(i).toString());
	}
	
	//prints all melds to the screen
	public void showMeldsCompact(){
		
		String meldsString = "";
		for (Meld m: mMelds)
			meldsString += "[" + m.toStringCompact() + "] ";
		
		System.out.println(meldsString);
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
	public Iterator<Tile> iterator() {return mTiles.iterator();}
	
	
	
	
	
	
	
	
	
	
	//sync hand tilelist and meldlist with tracker
	public void syncWithRoundTracker(RoundTracker tracker){
		mRoundTracker = tracker;
		mRoundTracker.syncHand(mTiles, mMelds);
	}
	
	

}
