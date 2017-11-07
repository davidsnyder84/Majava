package majava;


import java.util.Collections;
import java.util.Iterator;

import majava.tiles.GameTile;
import majava.util.GameTileList;
import majava.enums.MeldType;
import majava.enums.Wind;



//represents a meld (–ÊŽq) of tiles (chi, pon, kan, pair)
public class Meld implements Iterable<GameTile>, Comparable<Meld>, Cloneable {
	
	private GameTileList mTiles;	
	private MeldType mMeldType;
	
	private GameTile mCompletedTile;
	private Wind mOwnerSeatWind;
	private Wind mPlayerResponsible;
	
	
	
	public Meld(GameTileList tilesFromHand, GameTile completingTile, MeldType meldType){
		mTiles = new GameTileList();
		formMeld(tilesFromHand, completingTile, meldType);
	}
	//2-arg, takes list of tiles and meld type (used when making a meld only from hand tiles, so no "new" tile)
	//passes (handtiles 0 to n-1, handtile n, and meld type)
	public Meld(GameTileList handTiles, MeldType meldType){
		this(handTiles.subList(0, handTiles.size() - 1), handTiles.get(handTiles.size() - 1), meldType);
	}
	
	
	private Meld(Meld other){		
		mTiles = other.getAllTiles();
		
		mCompletedTile = other.mCompletedTile;
		
		mOwnerSeatWind = other.mOwnerSeatWind;
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
		return fu;
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
