package majava.hand;


import java.util.Collections;
import java.util.Iterator;

import majava.tiles.GameTile;
import majava.util.GameTileList;
import majava.enums.MeldType;
import majava.enums.Wind;



//represents a meld (–ÊŽq) of tiles (chi, pon, kan, pair)
public class Meld implements Iterable<GameTile>, Comparable<Meld>, Cloneable {
	
	private GameTileList meldTiles;	
	private MeldType meldType;
	
	private GameTile completedTile;
	
	
	
	public Meld(GameTileList tilesFromHand, GameTile completingTile, MeldType typeOfMeld){
		meldTiles = new GameTileList();
		formMeld(tilesFromHand, completingTile, typeOfMeld);
	}
	//2-arg, takes list of tiles and meld type (used when making a meld only from hand tiles, so no "new" tile)
	//passes (handtiles 0 to n-1, handtile n, and meld type)
	public Meld(GameTileList tilesFromHand, MeldType typeOfMeld){
		this(tilesFromHand.subList(0, tilesFromHand.size() - 1), tilesFromHand.get(tilesFromHand.size() - 1), typeOfMeld);
	}
	private Meld(Meld other){		
		meldTiles = other.getAllTiles();
		completedTile = other.completedTile;
	}
	public Meld clone(){return new Meld(this);}
	
	
	private void formMeld(GameTileList tilesFromHand, GameTile completingTile, MeldType typeOfMeld){
		completedTile = completingTile;
		
		setMeldType(typeOfMeld);
		
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
	
	//returns true if the meld can use the candidate to make a minkan
	public boolean canMinkanWith(GameTile candidate){return isPon() && getFirstTile().equals(candidate);}
	
	
	//adds a tile to a meld (needed for upgrading minkou to minkan)
	private void addTile(GameTile t){meldTiles.add(t);}
	private void addTiles(GameTileList tiles){meldTiles.addAll(tiles);}
	
	
	private void setMeldType(MeldType mtype){meldType = mtype;}
	private void sort(){Collections.sort(meldTiles);}
	
	
	
	
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
	
	
	public GameTile getTile(int index){
		if (index >= 0 && index < size()) return meldTiles.get(index);
		return null;
	}
	public GameTile getFirstTile(){return meldTiles.get(0);}
	public GameTileList getAllTiles(){return meldTiles.clone();}
	public int size(){return meldTiles.size();}
	
	
	public boolean isChi(){return meldType.isChi();}
	public boolean isPon(){return meldType.isPon();}
	public boolean isKan(){return meldType.isKan();}
//	public MeldType getMeldType(){return mMeldType;}
	
	
	
	
	
	//toString
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
