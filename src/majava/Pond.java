package majava;

/*
Class: Pond
represents a player's pond of discarded tiles

data:
	mTiles - list of tiles in the pond
	
methods:
	
	constructors:
	
	
	mutators:
 	
 	
 	accessors:
	
	
	other:
*/
public class Pond {
	
	
	public static final int SIZE_DEFAULT = 30;
	
	
	
	
	private TileList mTiles;
	private RoundTracker mRoundTracker;
	
//	private Tile mRiichiTile;
	private int mRiichiTileIndex;
	
	
	
	
	public Pond(){
		mTiles = new TileList(SIZE_DEFAULT);
	}
	
	
	public void addTile(Tile t){
		mTiles.add(t);
	}
	
	
	
	
	//returns true if the player has made a nagashi mangan in their pond
	public boolean isNagashiMangan(){
		return false;
	}
	
	
	
	//returns which tile the player used for riichi
	public Tile getRiichiTile(){return mTiles.get(mRiichiTileIndex);}
	
	
	
	
	@Override
	public String toString(){
		
		int i, j;
		String pondString = "";
		
		final int TILES_PER_LINE = 6;
		for (i = 0; i < (mTiles.size() / TILES_PER_LINE) + 1; i++)
		{
			pondString += "\t";
			for (j = 0; j < TILES_PER_LINE && (j + TILES_PER_LINE*i < mTiles.size()); j++)
			{
				pondString += mTiles.get(TILES_PER_LINE*i + j).toString() + " ";
			}
			if (TILES_PER_LINE*i < mTiles.size())
				pondString += "\n";
		}
		
		return pondString;
	}
	
	
	
	
	
	//sync pond tilelist with tracker
	public void syncWithRoundTracker(RoundTracker tracker){
		mRoundTracker = tracker;
		mRoundTracker.syncPond(mTiles);
	}
	
	
	
}
