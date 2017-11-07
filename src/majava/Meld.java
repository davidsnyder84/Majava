package majava;


import java.util.Collections;
import java.util.Iterator;

import majava.tiles.GameTile;
import majava.util.GameTileList;
import majava.enums.MeldType;
import majava.enums.Wind;



//represents a meld (–ÊŽq) of tiles (chi, pon, kan, pair)
public class Meld implements Iterable<GameTile>, Comparable<Meld>, Cloneable {
	
	private GameTileList meldTiles;	
	private MeldType mMeldType;
	
	private GameTile completedTile;
	private Wind ownerWind;
	
	
	
	public Meld(GameTileList tilesFromHand, GameTile completingTile, MeldType meldType){
		meldTiles = new GameTileList();
		formMeld(tilesFromHand, completingTile, meldType);
	}
	//2-arg, takes list of tiles and meld type (used when making a meld only from hand tiles, so no "new" tile)
	//passes (handtiles 0 to n-1, handtile n, and meld type)
	public Meld(GameTileList handTiles, MeldType meldType){
		this(handTiles.subList(0, handTiles.size() - 1), handTiles.get(handTiles.size() - 1), meldType);
	}
	
	
	private Meld(Meld other){		
		meldTiles = other.getAllTiles();
		
		completedTile = other.completedTile;
		ownerWind = other.ownerWind;
	}
	public Meld clone(){return new Meld(this);}
	
	
	
	private void formMeld(GameTileList tilesFromHand, GameTile completingTile, MeldType meldType){
		completedTile = completingTile;
		ownerWind = tilesFromHand.get(0).getOrignalOwner();
		
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
	private void addTile(GameTile t){meldTiles.add(t);}
	private void addTiles(GameTileList tiles){meldTiles.addAll(tiles);}
	
	
	private void setMeldType(MeldType mtype){mMeldType = mtype;}
	private void sort(){Collections.sort(meldTiles);}
	
	
	
	
	//returns the amount of fu the meld is worth
	public int calculateFu(){
		int fu = 0;
		//////implement fu calculation here
		return fu;
	}
	
	
	
	
	//accessors
	public boolean isClosed(){return windOfOwner() == windOfResponsiblePlayer();}
	public Wind windOfOwner(){return ownerWind;}
	public Wind windOfResponsiblePlayer(){return completedTile.getOrignalOwner();}
	
	public GameTile getTile(int index){
		if (index >= 0 && index < size()) return meldTiles.get(index);
		return null;
	}
	public GameTile getFirstTile(){return meldTiles.get(0);}
	
	//returns a copy of the entire list of tiles
	public GameTileList getAllTiles(){return meldTiles.clone();}
	
	public int size(){return meldTiles.size();}
	
	
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
		else meldString += "  [Open, called from: " + windOfResponsiblePlayer() + "'s " + completedTile + "]";
		
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
	public Iterator<GameTile> iterator() {return meldTiles.iterator();}
	
	
	
	@Override
	public int compareTo(Meld other) {
		
		//if the first tiles are different, return the comparison of the first tiles
		int tileCompare = getFirstTile().compareTo(other.getFirstTile());
		if (tileCompare != 0) return tileCompare;
		
		//if the first tiles are the same, compare by meld type
		return (mMeldType.compareTo(other.mMeldType));
	}
}
