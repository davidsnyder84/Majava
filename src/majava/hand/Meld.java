package majava.hand;


import java.util.Iterator;

import majava.tiles.GameTile;
import majava.util.GTL;
import majava.enums.MeldType;
import majava.enums.Wind;



//represents a meld (–ÊŽq) of tiles (chi, pon, kan, pair)
public class Meld implements Iterable<GameTile>, Comparable<Meld>, Cloneable {
	
	private final GTL meldTiles;	
	private final MeldType meldType;
	
	private final GameTile completedTile;
	
	
	
	public Meld(GTL tilesFromHand, GameTile completingTile, MeldType typeOfMeld){
		completedTile = completingTile;
		meldType = typeOfMeld;
		
		meldTiles = new GTL(tilesFromHand).add(completedTile).sort(); //sort if only chi?
	}
	//2-arg, takes list of tiles and meld type (used when making a meld only from hand tiles, so no "new" tile)
	//passes (handtiles 0 to n-1, handtile n, and meld type)
	public Meld(GTL tilesFromHand, MeldType typeOfMeld){
		this(tilesFromHand.getAllExceptLast(), tilesFromHand.getLast(), typeOfMeld);
	}
	private Meld(Meld other){		
		meldTiles = other.meldTiles;
		completedTile = other.completedTile;
		meldType = other.meldType;
	}
	public Meld clone(){return new Meld(this);}
	
	//builder constructor
	private static final boolean BUILDER = true;
	private Meld(GTL allMeldTiles, GameTile completingTile, MeldType typeOfMeld, boolean builder){
		completedTile = completingTile;
		meldType = typeOfMeld;
		meldTiles = allMeldTiles;
	}
	private Meld withTiles(GTL newTiles){return new Meld(newTiles, completedTile, meldType, BUILDER);}
	private Meld withCompletedTile(GameTile newCompletedTile){return new Meld(meldTiles, newCompletedTile, meldType, BUILDER);}
	private Meld withMeldType(MeldType newMeldType){return new Meld(meldTiles, completedTile, newMeldType, BUILDER);}
	
	
	
	
	
	//uses the given tile to upgrade a minkou to a minkan
	public Meld upgradeToMinkan(GameTile handTile){
		if (!isPon()) return this;
		
		return this.withTiles(meldTiles.add(handTile)).withMeldType(MeldType.KAN);
	}
	
	//returns true if the meld can use the candidate to make a minkan
	public boolean canMinkanWith(GameTile candidate){return isPon() && getFirstTile().equals(candidate);}
	
	
//	private Meld sort(){return this.withTiles(meldTiles.sort());}
	
	
	
	
	//returns the amount of fu the meld is worth
	public int calculateFu(){
		int fu = 0;
		//////implement fu calculation here
		return fu;
	}
	
	
	
	
	//accessors
	public boolean isClosed(){
		//if the owner of any tile differs from the responsible player, the meld is not closed
		for (GameTile t: this)
			if (t.getOrignalOwner() != windOfResponsiblePlayer())
				return false;
		return true;
	}
	public boolean isOpen(){return !isClosed();}
	public Wind windOfResponsiblePlayer(){return completedTile.getOrignalOwner();}
	public GameTile getCompletedTile(){return completedTile;}
	public int indexOfCompletedTile(){
		for (int i=0; i<size(); i++) if (getTile(i) == completedTile) return i;
		return -1;
	}
	
	//this method is needed because the ron tile is absorbed into the hand for meld form, creating an innacurate "completed Tile" being assigned sometimes (and thus incorrect windofresponsibleplayer)
	public Meld makeSureResponsibleTileIsCorrectlyAssigned(Wind ownerWind){
		for (GameTile t: meldTiles)
			if (t.getOrignalOwner() != ownerWind)
				return this.withCompletedTile(t);
		return this;
	}
	
	
	public GameTile getTile(int index){
		if (index >= 0 && index < size()) return meldTiles.get(index);
		return null;
	}
	public GameTile getFirstTile(){return meldTiles.get(0);}
	public GTL getAllTiles(){return getTiles();}
	public GTL getTiles(){return meldTiles;}
	public int size(){return meldTiles.size();}
	public boolean contains(GameTile tile){return meldTiles.contains(tile);}
	public boolean contains(int tileID){return meldTiles.contains(tileID);}
	
	
	public boolean isChi(){return meldType.isChi();}
	public boolean isPon(){return meldType.isPon();}
	public boolean isKan(){return meldType.isKan();}
	public boolean isPair(){return meldType.isPair();}
	public boolean isMulti(){return meldType.isMulti();}	//pair/pon/kan
	public boolean isPonKan(){return (isPon() || isKan());}	//pon/kan only
	public MeldType getMeldType(){return meldType;}
	
	//returns true if the meld contains a 1 or 9
	public boolean containsTerminal(){
		for (GameTile t: this)
			if (t.isTerminal())
				return true;
		return false;
	}
	public boolean isHonorMeld(){return getFirstTile().isHonor();}
	public boolean isDragonMeld(){return getFirstTile().isDragon();}
	public boolean isWindMeld(){return getFirstTile().isWind();}
	
	
	//returns a re-ordered form of the meld, such that the completed tile points towards the player responsible for it
	public Meld tiltedOrderForm(){
		if (isClosed()) return this;
		
		Meld tiltedMeld = this.clone();
		int indexOfCompleted = tiltedMeld.indexOfCompletedTile();
		
		int moveToThisIndex = -1;
		if (tiltedMeld.windOfResponsiblePlayer() == tiltedMeld.originalOwnerOfThisMeld().shimochaWind())
			moveToThisIndex = size()-1;
		if (tiltedMeld.windOfResponsiblePlayer() == tiltedMeld.originalOwnerOfThisMeld().toimenWind())
			moveToThisIndex = 1;
		if (tiltedMeld.windOfResponsiblePlayer() == tiltedMeld.originalOwnerOfThisMeld().kamichaWind())
			moveToThisIndex = 0;
		
		//swap
		GameTile temp = tiltedMeld.meldTiles.get(moveToThisIndex);
		tiltedMeld.meldTiles.set(moveToThisIndex, tiltedMeld.meldTiles.get(indexOfCompleted));
		tiltedMeld.meldTiles.set(indexOfCompleted, temp);
		
		return tiltedMeld;
	}
	private Wind originalOwnerOfThisMeld(){
		for (GameTile t: this)
			if (t.getOrignalOwner() != windOfResponsiblePlayer())
				return t.getOrignalOwner();
		return getFirstTile().getOrignalOwner();
	}
	
	
	
	
	
	
	public Meld DEMO_setWind(Wind newWind){
//		return this.withTiles(meldTiles.withWind(newWind)).withCompletedTile(completedTile.withOwnerWind(newWind)).withMeldType(meldType);
		return new Meld(meldTiles.withWind(newWind), completedTile.withOwnerWind(newWind), meldType);
	}
	
	
	
	
	
	
	
	
	@Override
	public String toString(){return toStringTilesOnly() + toStringOpenClosedInfo();}
	public String toStringTilesOnly(){
		String meldString = "";
		for (GameTile t: this) meldString += t + " ";
		
		if (meldString != "") meldString = meldString.substring(0, meldString.length() - 1);
		return meldString;
	}
	private String toStringOpenClosedInfo(){
		if (isClosed()) return "   [Closed]";
		else return "   [Open, called from: " + windOfResponsiblePlayer() + "'s " + completedTile + "]";
	}
	
	//iterator, returns mTile's iterator
	@Override
	public Iterator<GameTile> iterator() {return meldTiles.iterator();}
	
	
	@Override
	public int compareTo(Meld other) {
		//if the first tiles are different, return the comparison of the first tiles
		int tileCompare = getFirstTile().compareTo(other.getFirstTile());
		if (tileCompare != 0) return tileCompare;
		//if the first tiles are the same, compare by meld type
		return (meldType.compareTo(other.meldType));
	}
}
