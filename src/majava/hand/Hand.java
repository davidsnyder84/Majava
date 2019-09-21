package majava.hand;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import majava.hand.handcheckers.AgariChecker;
import majava.hand.handcheckers.CallabilityChecker;
import majava.hand.handcheckers.TurnActionabilityChecker;
import majava.tiles.GameTile;
import majava.util.GTL;
import majava.enums.Wind;
import majava.enums.MeldType;


//represents a player's hand (Žè”v) of tiles
public class Hand implements Iterable<GameTile>, Cloneable{	
	public static final int MAX_HAND_SIZE = 14;
	private static final int MAX_NUM_MELDS = 5;
	private static final int AVG_NUM_TILES_PER_MELD = 3;
	
	
	private final GTL tiles;
	private final List<Meld> melds;
	
	private final Wind ownerWind;
	
	
	
	private Hand(GTL ts, List<Meld> ms, Wind w){
		tiles = ts;
		melds = ms;
		ownerWind = w;
	}
	private Hand withTiles(GTL newTiles){return new Hand(newTiles, melds, ownerWind);}
	private Hand withMelds(List<Meld> newMelds){return new Hand(tiles, newMelds, ownerWind);}
	private Hand withWind(Wind newWind){return new Hand(tiles, melds, newWind);}
	
	
	private List<Meld> meldsClone(){
		List<Meld> meldList = new ArrayList<Meld>();
		for (Meld m: melds)
			meldList.add(m.clone());
		
		return meldList;
	}
	
	
	public Hand(GTL handTiles, Wind w){this(handTiles, new ArrayList<Meld>(MAX_NUM_MELDS), w);}
	public Hand(GTL handTiles){this(handTiles, new ArrayList<Meld>(MAX_NUM_MELDS), Wind.UNKNOWN);}
	public Hand(Wind w){this(new GTL(), new ArrayList<Meld>(MAX_NUM_MELDS), w);}
	public Hand(){this(Wind.UNKNOWN);}
	public Hand (Hand other){
		this(other.tiles, other.melds, other.ownerWind);
//		tiles = other.tiles;
//		melds = new ArrayList<Meld>(MAX_NUM_MELDS);
//		for (Meld m: other.melds) melds.add(m.clone());
//		
//		ownerWind = other.ownerWind;
	}
	public Hand clone(){return new Hand(this);}
	
	
	
	//returns the tile at the given index in the hand, returns null if outside of the hand's range
	public GameTile getTile(int index){
		if (index > size() || index < 0 ) return null;
		return tiles.get(index);
	}
	public GameTile getLastTile(){return getTile(size()-1);}
	public GameTile getFirstTile(){return getTile(0);}
	//GameTileList methods
	public int findHowManyOf(GameTile t){return tiles.findHowManyOf(t);}
	public GTL getTiles(){return tiles;}
	public int indexOf(Integer id){return tiles.indexOf(new GameTile(id));}
	public boolean contains(Integer id){return tiles.contains(new GameTile(id));}
	public List<Integer> findAllIndicesOf(GameTile t){return tiles.findAllIndicesOf(t);}
	
	//returns a list of the melds that have been made (copy of actual melds), returns an empty list if no melds made
	public List<Meld> getMelds(){
		List<Meld> meldList = new ArrayList<Meld>();
		for (Meld m: melds)
			meldList.add(m.clone());
		
		return meldList;
	}
	public List<Meld> getFinishingMelds(){
		List<Meld> finishingMelds = agariChecker().getFinishingMelds();
		System.out.println(finishingMelds.toString());
		
		//this is needed because the ron tile is absorbed into the hand for finished meld form, creating an innacurate "completed Tile" being assigned sometimes (and thus incorrect windofresponsibleplayer)
//		for (Meld m: finishingMelds)
//			m.makeSureResponsibleTileIsCorrectlyAssigned(ownerWind);
		
		List<Meld> finishingMeldsWithCorrectlyAssignedResponsible = new ArrayList<Meld>();
		for (Meld m: finishingMelds)
			finishingMeldsWithCorrectlyAssignedResponsible.add(m.makeSureResponsibleTileIsCorrectlyAssigned(ownerWind));
		
		
		return finishingMeldsWithCorrectlyAssignedResponsible;
	}
	
	public GTL getTilesAsListIncludingMeldTiles(){
		GTL allTiles = tiles;
		
		for (Meld m: melds)
			allTiles = allTiles.addAll(m);
		
		allTiles = allTiles.sort();
		return allTiles;
	}
	
	
	
	
	public int size(){return tiles.size();}
	public boolean isClosed(){
		for (Meld m: melds) if (m.isOpen()) return false;
		return true;
	}
	public int numberOfMeldsMade(){return melds.size();}
	public boolean isFull(){return (size() % 3) == (MAX_HAND_SIZE % 3);}
	
	public int numberOfKansMade(){
		int numKans = 0;
		for (Meld m: melds) if (m.isKan()) numKans++;
		return numKans;
	}
	
	public Wind getOwnerSeatWind(){return ownerWind;}
	
	public Hand setOwnerSeatWind(Wind newOwnerWind){
		GTL tilesWithNewWind = new GTL();
		for (GameTile t: tiles)
			tilesWithNewWind = tilesWithNewWind.add(t.withOwnerWind(newOwnerWind));
		
		return this.withTiles(tilesWithNewWind).withWind(newOwnerWind);
	}
	
	public boolean isInTenpai(){return agariChecker().isInTenpai();}
	public GTL getTenpaiWaits(){return agariChecker().getTenpaiWaits();}
	
	public boolean isComplete(){return agariChecker().isComplete();}
	public boolean isCompleteNormal(){return agariChecker().isCompleteNormal();}	
	public boolean isCompleteChiitoitsu(){return agariChecker().isCompleteChiitoitsu();}
	public boolean isTenpaiChiitoitsu(){return agariChecker().isTenpaiChiitoitsu();}
	public boolean isCompleteKokushi(){return agariChecker().isCompleteKokushi();}
	public boolean isTenpaiKokushi(){return agariChecker().isTenpaiKokushi();}
	
	
	private AgariChecker agariChecker(){return new AgariChecker(this);}
	private CallabilityChecker callabilityChecker(){return new CallabilityChecker(this);}
	private TurnActionabilityChecker turnActionabilityChecker(){return new TurnActionabilityChecker(this);}
	
	
	//adds a tile to the hand (cannot add more than max hand size)
	//overloaded for tileID, accepts integer tileID and adds a new tile with that ID to the hand
	public Hand addTile(GameTile addThisTile){
		if (size() >= MAX_HAND_SIZE - AVG_NUM_TILES_PER_MELD*numberOfMeldsMade())
			return this; //no change
		
		return this.withTiles(tiles.add(addThisTile));
	}
	public Hand addTile(int tileID){return addTile(new GameTile(tileID));}
	
	//removes the tile at the given index, returns no change out of range
	public Hand removeTile(int removeThisIndex){
		if (removeThisIndex < 0 || removeThisIndex > size())
			return this;
		
		return this.withTiles(tiles.remove(removeThisIndex).sort());
	}
	public Hand removeLastTile(){return removeTile(size()-1);}
	public Hand removeFirstTile(){return removeTile(0);}
	
	public Hand removeMultiple(List<Integer> removeIndices){
		return this.withTiles(tiles.removeMultiple(removeIndices).sort());
	}
	
	public Hand sort(){return this.withTiles(tiles.sort());}
	

	
	
	

	
	
	//able to make calls?
	public boolean canCallTile(GameTile candidate){
		return callabilityChecker().tileIsCallable(candidate);
	}	
	public boolean ableToChiL(GameTile tileToReactTo){return callabilityChecker().ableToChiL(tileToReactTo);}
	public boolean ableToChiM(GameTile tileToReactTo){return callabilityChecker().ableToChiM(tileToReactTo);}
	public boolean ableToChiH(GameTile tileToReactTo){return callabilityChecker().ableToChiH(tileToReactTo);}
	public boolean ableToPon(GameTile tileToReactTo){return callabilityChecker().ableToPon(tileToReactTo);}
	public boolean ableToKan(GameTile tileToReactTo){return callabilityChecker().ableToKan(tileToReactTo);}
	public boolean ableToRon(GameTile tileToReactTo){return callabilityChecker().ableToRon(tileToReactTo);}
	
	//able to perform turn actions?
	public boolean ableToAnkan(){return turnActionabilityChecker().ableToAnkan();}
	public boolean ableToMinkan(){return turnActionabilityChecker().ableToMinkan();}
	public boolean ableToRiichi(){return turnActionabilityChecker().ableToRiichi();}
	public boolean ableToTsumo(){return turnActionabilityChecker().ableToTsumo();}
	
	
	
	
	//forms a meld of the given type. claimedTile = the tile that will complete the meld
	private Hand makeMeld(GameTile claimedTile, MeldType meldType){
		
		//~~~~gather the tiles from the hand that will be in the meld
		//get the list of partner indices, based on the the meld type
		List<Integer> partnerIndices = callabilityChecker().getPartnerIndices(claimedTile, meldType);

		//list of TILES, will hold the tiles coming from the hand that will be in the meld
		GTL tilesFromHand = tiles.getMultiple(partnerIndices);
		
		//make the meld
		List<Meld> meldsWithNewMeld = meldsClone();
		Meld meld = new Meld(tilesFromHand, claimedTile, meldType);
		meldsWithNewMeld.add(meld);
		
		//remove the tiles from the hand 
		GTL tilesMinusMeldtiles = tiles.removeMultiple(partnerIndices);
		
		return this.withTiles(tilesMinusMeldtiles).withMelds(meldsWithNewMeld);
	}
	
	public Hand makeMeldChiL(GameTile claimedTile){return makeMeld(claimedTile, MeldType.CHI_L);}
	public Hand makeMeldChiM(GameTile claimedTile){return makeMeld(claimedTile, MeldType.CHI_M);}
	public Hand makeMeldChiH(GameTile claimedTile){return makeMeld(claimedTile, MeldType.CHI_H);}
	public Hand makeMeldPon(GameTile claimedTile){return makeMeld(claimedTile, MeldType.PON);}
	public Hand makeMeldKan(GameTile claimedTile){return makeMeld(claimedTile, MeldType.KAN);}
	
	
	
	
	
	public Hand makeMeldTurnAnkan(){
		final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
		
		int candidateIndex = turnActionabilityChecker().getCandidateAnkanIndex();
		GameTile candidate = getTile(candidateIndex);
		
		List<Integer> partnerIndices = tiles.findAllIndicesOf(candidate);
		while(partnerIndices.size() > NUM_PARTNERS_NEEDED_TO_KAN) partnerIndices.remove(partnerIndices.size() - 1);
		
		
		GTL tilesForAnkanFromHand = tiles.getMultiple(partnerIndices);
		
		
		//make the meld
		List<Meld> meldsWithNewAnkan = meldsClone();
		meldsWithNewAnkan.add(new Meld(tilesForAnkanFromHand, candidate, MeldType.KAN));
		
		//remove the tiles from the hand 
		partnerIndices.add(candidateIndex);
		
		return this.removeMultiple(partnerIndices).withMelds(meldsWithNewAnkan);
	}
	
/////////////////////////////////////////////////////////////////////////////////mutate
	public Hand makeMeldTurnMinkan(){
		int candidateIndex = turnActionabilityChecker().getCandidateMinkanIndex();
		GameTile candidate = getTile(candidateIndex);
		
		Meld meldToUpgrade = null;
		
		//find the pon
		for (Meld m: melds)
			if (m.canMinkanWith(candidate))
				meldToUpgrade = m;
		
		meldToUpgrade.upgradeToMinkan(candidate);
		
		///////////m is already upgraded before cloning, fix this
		List<Meld> meldsWithMinkan = meldsClone();
		
		return this.removeTile(candidateIndex).withMelds(meldsWithMinkan);
	}
	
	
	
	

	
	//xxxxBEGIN DEMO METHODS
	public void DEMOfillScattered(){int[] ids = {2,4,6,8,10,12,14,16,18,20,22,24,26,28}; for (int i: ids) addTile(i);}
	public void DEMOfillChuuren(int lastTile){int[] ids = {1,1,1,2,3,4,5,6,7,8,9,9,9,lastTile}; for (int i: ids) addTile(i);}
	public void DEMOfillChuuren(){DEMOfillChuuren(8);}
	public void DEMOaddMeld(Meld meld){melds.add(meld);}
	//xxxxEND DEMO METHODS
	
	
	
	
	
	
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
	
}
