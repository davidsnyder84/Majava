package majava;


import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import majava.tiles.GameTile;
import majava.util.GameTileList;
import majava.enums.MeldType;
import majava.enums.Wind;


/*
Class: Meld
represents a meld of tiles (chi, pon, kan, pair)

data:
	mTiles - list of tiles in the meld
	mMeldType - the type of meld (chi, pon, kan, pair)
	mClosed = if the meld is a closed meld, this will be true, false otherwise
	mFu - the fu value of the meld
	
	mOwnerSeatWind - the seat wind of the owner of the meld (needed for checking fu of wind pairs)
	
	mCompletedTile - the tile that completed the meld
	mPlayerResponsible - the player who contributed the completing tile to the meld
	
methods:
	constructors:
	3-arg: requires list of tiles from hand, the completing tile, and meld type
	2-arg: requires list of tiles from hand and meld type
	copy constructor
	
	public:
		mutators:
		upgradeToMinkan - uses the given tile to upgrade an open pon to a minkan
		
		
	 	accessors:
	 	calculateFu - returns the amount of fu points the meld is worth
	 	isClosed - returns true if the meld is closed
	 	
	 	getOwnerSeatWind - returns the seat wind of the meld's owner
	 	getResponsible - returns the seat wind of the player responsible for completing the meld
	 	
	 	getTile - returns the tile at the given index in the meld
	 	getAllTiles - returns a list of all tiles in the meld
		getSize - returns how many tiles are in the meld
		isChi, isPon, isKan - returns true if the meld is of the corresponding type
*/
public class Meld implements Iterable<GameTile>, Comparable<Meld>, Cloneable {
	
	private static final int FU_DEFAULT = 0;
	
	
	private GameTileList mTiles;	
	private MeldType mMeldType;
	
	private GameTile mCompletedTile;
	private Wind mOwnerSeatWind;
	private Wind mPlayerResponsible;
	
	private int mFu;
	
	
	
	
	
	public Meld(GameTileList tilesFromHand, GameTile completingTile, MeldType meldType){
		mTiles = new GameTileList();
		formMeld(tilesFromHand, completingTile, meldType);
		
		mFu = FU_DEFAULT;
	}
	//2-arg, takes list of tiles and meld type (used when making a meld only from hand tiles, so no "new" tile)
	//passes (handtiles 0 to n-1, handtile n, and meld type)
	public Meld(GameTileList handTiles, MeldType meldType){
		this(handTiles.subList(0, handTiles.size() - 1), handTiles.get(handTiles.size() - 1), meldType);
	}
	
	
	private Meld(Meld other){
		
		mTiles = other.getAllTiles();
		
		mOwnerSeatWind = other.mOwnerSeatWind;
		
		mFu = other.mFu;
		
		mCompletedTile = other.mCompletedTile;
		mPlayerResponsible = other.mPlayerResponsible;
	}
	public Meld clone(){return new Meld(this);}
	
	
	
	
	private void formMeld(GameTileList tilesFromHand, GameTile completingTile, MeldType meldType){
		
		mCompletedTile = completingTile;
		
		mOwnerSeatWind = tilesFromHand.get(0).getOrignalOwner();
		mPlayerResponsible = completingTile.getOrignalOwner();
		
		setMeldType(meldType);
		
		addTiles(tilesFromHand);
		addTile(completingTile);
		
		if (isChi()) sort();
	}
	
	
	
	
	//uses the given tile to upgrade a minkou to a minkan
	public void upgradeToMinkan(GameTile handTile){
		if (!isPon()) return;
		
		addTile(handTile);
		setMeldType(MeldType.KAN); 
	}
	
	//adds a tile to a meld (needed for upgrading minkou to minkan)
	private void addTile(GameTile t){mTiles.add(t);}
	private void addTiles(GameTileList tiles){mTiles.addAll(tiles);}
	
	
	private void setMeldType(MeldType mtype){mMeldType = mtype;}
	private void sort(){Collections.sort(mTiles);}
	
	
	
	
	
	//returns the amount of fu the meld is worth
	public int calculateFu(){
		int fu = 0;
		
		//////implement fu calculation here
		
		mFu = fu;
		return mFu;
	}
	
	
	
	
	//accessors
	public boolean isClosed(){return mPlayerResponsible == mOwnerSeatWind;}
	public Wind getOwnerSeatWind(){return mOwnerSeatWind;}
	public Wind getResponsible(){return mPlayerResponsible;}
	
	public GameTile getTile(int index){
		if (index >= 0 && index < size()) return mTiles.get(index);
		return null;
	}
	public GameTile getFirstTile(){return mTiles.get(0);}
	
	//returns a copy of the entire list of tiles
	public GameTileList getAllTiles(){return mTiles.clone();}
	
	public int size(){return mTiles.size();}
	
	
	public boolean isChi(){return mMeldType.isChi();}
	public boolean isPon(){return mMeldType.isPon();}
	public boolean isKan(){return mMeldType.isKan();}
//	public MeldType getMeldType(){return mMeldType;}
	
	
	
	//toString
	@Override
	public String toString(){
		String meldString = "";
		
		for (GameTile t: this) meldString += t.toString() + " ";
		
		//show closed or open
		if (isClosed()) meldString += "  [Closed]";
		else meldString += "  [Open, called from: " + mPlayerResponsible + "'s " + mCompletedTile + "]";
		
		return meldString;
	}
	public String toStringCompact(){
		String meldString = "";
		for (GameTile t: this) meldString += t + " ";
		
		if (meldString != "") meldString = meldString.substring(0, meldString.length() - 1);
		return meldString;
	}
	

	//iterator, returns mTile's iterator
	@Override
	public Iterator<GameTile> iterator() {return mTiles.iterator();}
	
	
	
	@Override
	public int compareTo(Meld other) {
		
		//if the first tiles are different, return the comparison of the first tiles
		int tileCompare = getFirstTile().compareTo(other.getFirstTile());
		if (tileCompare != 0) return tileCompare;
		
		//if the first tiles are the same, compare by meld type
		return (mMeldType.compareTo(other.mMeldType));
	}
}
