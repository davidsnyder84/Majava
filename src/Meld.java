
import java.util.Iterator;


/*
Class: Meld
represents a meld of tiles (chi, pon, kan, pair)

data:
	mTiles - list of tiles in the meld
	mMeldType - the type of meld (chi, pon, kan, pair)
	mClosed = if the meld is a closed meld, this will be true, false otherwise
	mFu - the fu value of the meld
	
	mPlayerSeatWind - the seat wind of the owner of the meld (needed for checking fu of wind pairs)
	
methods:
	constructors:

	mutators:
 	
 	accessors:
	
	other:
	
	private:
*/
public class Meld implements Iterable<Tile>, Comparable<Meld> {
	
	/*
	public static final int MELD_TYPE_UNKNOWN = 0;
	public static final int MELD_TYPE_CHI_L = 1;
	public static final int MELD_TYPE_CHI_M = 2;
	public static final int MELD_TYPE_CHI_H = 3;
	public static final int MELD_TYPE_PON = 4;
	public static final int MELD_TYPE_KAN = 5;
	public static final int MELD_TYPE_PAIR = 8;
	public static final int MELD_TYPE_DEFAULT = MELD_TYPE_UNKNOWN;
	public static final int MELD_TYPE_CHI = 123;
	*/
	
	public static final char OWNER_UNKOWN = '?';
	public static final char OWNER_DEFAULT = OWNER_UNKOWN;

	public static final boolean CLOSED_STATUS_CLOSED = true;
	public static final boolean CLOSED_STATUS_OPEN = false;
	public static final boolean CLOSED_STATUS_DEFAULT = CLOSED_STATUS_CLOSED;

	public static final int AVG_NUM_TILES_PER_MELD = 3;
	
	private static final int FU_DEFAULT = 0;
	
	
	
	//list of tiles in the meld
	private TileList mTiles;
	
	private MeldType mMeldType;
	private boolean mClosed;

	private char mOwnerSeatWind;
	
	private Tile mCompletedTile;
	private char mPlayerResponsible;
	
	private int mFu;
	
	
	
	
	/*
	3-arg Constructor
	forms a meld from the given tiles, of the given meld type
	
	input: handTiles are the tiles that are coming from the player's hand
		   newTile is the "new" tile that is being added to form the meld
		   meldType is the type of meld that is being formed
	
	form the meld
	fu = 0
	*/
	public Meld(TileList handTiles, Tile newTile, MeldType meldType){		
		
		__formMeld(handTiles, newTile, meldType);
		
		mFu = FU_DEFAULT;
	}
	//2-arg, takes list of tiles and meld type (used when making a meld only from hand tiles, so no "new" tile)
	//passes (handtiles 0 to n-1, handtile n, and meld type)
	public Meld(TileList handTiles, MeldType meldType){
		this(handTiles.getAllExceptLast(), handTiles.getLast(), meldType);
	}
	public Meld(Meld other){
		
		mTiles = new TileList();
		
		for (Tile t: other.mTiles) mTiles.add(t);
		mOwnerSeatWind = other.mOwnerSeatWind;
		
		mClosed = other.mClosed;
		mFu = other.mFu;
		
		mCompletedTile = other.mCompletedTile;
		mPlayerResponsible = other.mPlayerResponsible;
		
	}
	
	
	
	
	
	/*
	private method: __formMeld
	forms a meld of the given type with the given tiles
	
	input: handTiles are the tiles from the player's hand
		   newTile is the "new" tile that is added to form the meld
		   meldType is the type of meld that is being formed
	
	
	set onwer's seat wind = owner of the hand tiles
	set player responsible = player who discarded the newTile
	set tile that completed the meld = newTile
	set meld type
	
	if (owner's wind == responsible's wind)
		set closed = true (tiles all came from owner's hand)
	else (owner's wind is different from responsible's wind)
		set closed = false (tile was called)
	end if
	
	add the hand tiles to the meld
	add the new tile to the meld
	sort the meld if it is a chi
	*/
	public void __formMeld(TileList handTiles, Tile newTile, MeldType meldType){
		
		//set the owner's seat wind
		mOwnerSeatWind = handTiles.getFirst().getOrignalOwner();
		
		//set the new tile as the tile that completed the meld
		mCompletedTile = newTile;
		//check who is responsible for discarding the new tile
		mPlayerResponsible = newTile.getOrignalOwner();
		
		//check if the new tile came from someone other than the owner
		//closed = false if the tile came from someone else
		mClosed = (mPlayerResponsible == mOwnerSeatWind);
		
		
		//set meld type
		mMeldType = meldType;
		

		//add the hand tiles to the meld
		mTiles = handTiles;
		//add the new tile to the meld
		mTiles.add(newTile);
		
		//sort the meld if it is a chi
		if (mMeldType.isChi()) mTiles.sort();
	}
	
	
	
	
	
	
	//returns the amount of fu the meld is worth
	public int calculateFu(){
		int fu = 0;
		
		mFu = fu;
		return mFu;
	}
	
	
	
	
	//accessors
	public MeldType getMeldType(){return mMeldType;}
	public boolean isClosed(){return mClosed;}
	public char getOwnerSeatWind(){return mOwnerSeatWind;}
	public char getResponsible(){return mPlayerResponsible;}
	

	//returns the tile at the given index in the meld, returns null if index is outside of the meld's range
	public Tile getTile(int index){
		if (index >= 0 && index < mTiles.size()) return mTiles.get(index);
		return null;
	}
	//returns the first tile in the meld
	public Tile getFirstTile(){return mTiles.getFirst();}
	
	
	//returns the entire list of tiles (this is a good idea)
	public TileList getAllTiles(){return mTiles;}
	
	
	
	//returns how many tiles are in the meld
	public int getSize(){return mTiles.size();}
	
	
	
	
	
	
	
	
	//toString
	@Override
	public String toString(){
		
		String meldString = "";
		
		//add the tiles to the string
		for (Tile t: mTiles)
			meldString += t.toString() + " ";
		
		//show closed or open
		if (mClosed == true)
			meldString += "  [Closed]";
		else
			meldString += "  [Open, called from: " + mPlayerResponsible + "'s " + mCompletedTile.toString() + "]";
		
		return meldString;
	}
	public String toStringCompact(){
		
		String meldString = "";
		//add the tiles to the string
		for (Tile t: mTiles) meldString += t.toString() + " ";
		if (meldString != "") meldString = meldString.substring(0, meldString.length() - 1);
		
		return meldString;
	}
	

	//iterator, returns mTile's iterator
	@Override
	public Iterator<Tile> iterator() {return mTiles.iterator();}
	
	
	@Override
	public int compareTo(Meld other) {
		
		//if the first tiles are different, return the comparison of the first tiles
		int tileCompare = getFirstTile().compareTo(other.getFirstTile());
		if (tileCompare != 0) return tileCompare;
		
		//if the first tiles are the same, compare by meld type
		return (mMeldType.compareTo(other.mMeldType));
	}
}
