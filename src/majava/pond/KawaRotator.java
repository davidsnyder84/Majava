package majava.pond;

import majava.Pond;
import majava.enums.Wind;
import majava.tiles.PondTile;
import majava.util.PlayerList;


public class KawaRotator {
	private static final int E=0, S=1, W=2, N=3;
	
	private final PondTraverser e, s, w, n;
	private final PondTraverser currentPond;
	
	
	private KawaRotator(PondTraverser pe, PondTraverser ps, PondTraverser pw, PondTraverser pn, PondTraverser setCurrentToThis){
//		traversers = new PondTraverser[]{pe, ps, pw, pn};
		e = pe; s = ps; w = pw; n = pn;
		currentPond = setCurrentToThis;
	}
	private KawaRotator(PondTraverser pe, PondTraverser ps, PondTraverser pw, PondTraverser pn){this(pe, ps, pw, pn,    pe);}
	
	public KawaRotator(Pond pe, Pond ps, Pond pw, Pond pn){this(new PondTraverser(pe), new PondTraverser(ps), new PondTraverser(pw), new PondTraverser(pn));}
	public KawaRotator(PlayerList plyrs){this(plyrs.seat(Wind.EAST).getPond(), plyrs.seat(Wind.SOUTH).getPond(), plyrs.seat(Wind.WEST).getPond(), plyrs.seat(Wind.NORTH).getPond());}
	
	//builder
	private KawaRotator withCurrent(PondTraverser newCurrent){return new KawaRotator(e, s, w, n, newCurrent);}
	
	//mutators
	public KawaRotator moveToNext(){return this.withCurrent(nextTraverser());}
	public KawaRotator moveTo(Wind moveToThis){
		switch(moveToThis){
		case EAST: return this.withCurrent(e);
		case SOUTH: return this.withCurrent(s);
		case WEST: return this.withCurrent(w);
		case NORTH: return this.withCurrent(n);
		default: return null;
		}
	}
	
	
	
	
	
	
	
	
	public Pond getCurrentPond(){return currentPond.getPond();}
	
	public PondTraverser currentTraverser(){return currentPond;}
	
	public PondTraverser nextTraverser(){
		if (currentPond == e) return s;
		if (currentPond == s) return w;
		if (currentPond == w) return n;
		if (currentPond == n) return e;
		return null;
	}
	
	

//	private Pond nextPond(Wind seat){
//	}
//	private Pond pondOf(Wind seat){
//		return players.seat(seat).getPond();
//	}
	
	
	
	
	private static class PondTraverser{
		private static final int START_POS = 0;
		
		private final Pond pond;
		private final int currentPosition;
		
		private PondTraverser(Pond p, int position){
			pond = p;
			currentPosition = position;
		}
		public PondTraverser(Pond p){this(p, START_POS);}
		
		public PondTraverser moveToNextTile(){
			return new PondTraverser(pond, currentPosition+1);
		}
		
		
		
		
		public boolean reachedEnd(){return currentPosition >= pond.size();}
		
		public PondTile getCurrentTile(){return pond.getTile(currentPosition);}
		public Pond getPond(){return pond;}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
