package majava.pond;

import majava.Pond;
import majava.enums.Wind;
import majava.tiles.PondTile;


public class RiverWalker {
	private static final int E=0, S=1, W=2, N=3;
	private static final int NUM_PONDS = 4;
	
	
	private final PondTraverser[] traversers;
	private final int indexOfCurrentTraverser;
	
	
	//builder constructor
	private RiverWalker(PondTraverser[] ts, int setCurrentToThis){
		traversers = ts;
		indexOfCurrentTraverser = setCurrentToThis;
	}
	private RiverWalker(PondTraverser pe, PondTraverser ps, PondTraverser pw, PondTraverser pn, int setCurrentToThis){this(new PondTraverser[]{pe, ps, pw, pn}, setCurrentToThis);}
	
	//public constructors
	public RiverWalker(Pond pe, Pond ps, Pond pw, Pond pn){this(new PondTraverser(pe), new PondTraverser(ps), new PondTraverser(pw), new PondTraverser(pn),   E);}
	public RiverWalker(Pond[] ponds){this(ponds[0], ponds[1], ponds[2], ponds[3]);}
	
	//builder
	private RiverWalker withCurrentTraverserIndex(int newCurrent){return new RiverWalker(traversers, newCurrent);}
	private RiverWalker withTraversers(PondTraverser[] newTraversers){return new RiverWalker(newTraversers, indexOfCurrentTraverser);}
	
	
	
	
	//rotate without increment
	private RiverWalker rotateTo(int moveToThis){return this.withCurrentTraverserIndex(moveToThis);}
	
	
	//increment
	private RiverWalker incrementTileInCurrentPond(){
		PondTraverser[] traversersWithCurrentTileIncrementedInCurrentPond = traversers.clone();
		traversersWithCurrentTileIncrementedInCurrentPond[indexOfCurrentTraverser] = currentTraverser().moveToNextTile();
		
		return this.withTraversers(traversersWithCurrentTileIncrementedInCurrentPond);
	}
	
	//rotate and increment
	private RiverWalker moveToNextTileInOtherPond(int moveToThis){
		return this.incrementTileInCurrentPond().rotateTo(moveToThis);	//increment current pond first, to say "I have already seen this tile"
	}
	
	
	
	
	//moves to the next discarded tile
	public RiverWalker moveToNextTile(){
		int moveToThis = nextTraverserIndex();
		
		if (currentTile().wasCalled())
			moveToThis = currentTile().getCaller().toInt();
		
		return moveToNextTileInOtherPond(moveToThis);
	}
	
	
	
	
	//-----------------------------------------------------------------------------accessors
	
	public PondTile lastDiscard(){
//		PondTile lastTile = null; //returns null if all ponds are empty
		PondTile lastTile = PondTile.DUMMY_TILE;
		
		RiverWalker walker = this;
		while (!walker.reachedEnd()){
			lastTile = walker.currentTile();
			walker = walker.moveToNextTile();
		}
		
		return lastTile;
	}
	
	
	
	//NPE here if all ponds are empty
	public PondTile currentTile(){return currentTraverser().getCurrentTile();}
	public Pond currentPond(){return currentTraverser().getPond();}
	
	public boolean reachedEnd(){
		for (PondTraverser pt : traversers)
			if (!pt.reachedEnd())
				return false;
		return true;
	}
	
	//-----------------------------------------------------------------------------accessors
	
	
	
	
	
	
	
	
	private PondTraverser currentTraverser(){return traverserFor(indexOfCurrentTraverser);}
	private PondTraverser nextTraverser(){return traverserFor(nextTraverserIndex());}
	private PondTraverser traverserFor(int which){
		return traversers[which];
	}
	private int nextTraverserIndex(){return (indexOfCurrentTraverser + 1) % NUM_PONDS;}
	
	
	public String toString() {
		String str = "";
		for (int i=0; i < NUM_PONDS; i++){
			String prefix = Wind.values()[i].toChar() + "\n" ;
			str += prefix + traversers[i].getPond() + "\n";
		}
		
		str += "\n,,,,,,, Last Discard: "  + lastDiscard();
		return str;
	}
	
	
	
	
	
	
	
	
	
	public static class PondTraverser{
		private static final int START_POS = 0;
		
		private final Pond pond;
		private final int currentPosition;
		
		private PondTraverser(Pond p, int position){
			pond = p;
			currentPosition = position;
		}
		public PondTraverser(Pond p){this(p, START_POS);}
		
		public PondTraverser moveToNextTile(){
			return new PondTraverser(pond, currentPosition + 1);
		}
		
		
		
		//accessors
		public boolean reachedEnd(){return currentPosition >= pond.size();}
		
		//should I throw an exception here if reachedEnd?
		public PondTile getCurrentTile(){return pond.getTile(currentPosition);}
		public Pond getPond(){return pond;}
		
	}
	
	
	
}
