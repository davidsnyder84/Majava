package majava.tiles;

import java.util.ArrayList;

import majava.util.GTL;
import majava.enums.MeldType;



//a tile with additional information required for hand checking
public class HandCheckerTile extends GameTile {
	
	//stack of possible melds the tile can form
	private final MeldTypeStack meldTypeStack;
	
	
	//copy constructor
	public HandCheckerTile(GameTile other){
		super(other);
		
		if (other instanceof HandCheckerTile)
			meldTypeStack = ((HandCheckerTile) other).meldTypeStack.clone();
		else
			meldTypeStack = new MeldTypeStack();
	}
	public HandCheckerTile(GameTile other, MeldTypeStack mts){
		super(other);
		meldTypeStack = mts;
	}
	public HandCheckerTile(Janpai t){this(new GameTile(t));}
	public HandCheckerTile clone(){return new HandCheckerTile(this);}
	//builder
	private HandCheckerTile withMTS(MeldTypeStack newMTS){return new HandCheckerTile(this, newMTS);}
	
	
	

	//stack functions
	final public HandCheckerTile mstackPush(MeldType meldType){return this.withMTS(meldTypeStack.push(meldType));}
	final public HandCheckerTile mstackPop(){return this.withMTS(meldTypeStack.pop());}
	final public MeldType mstackTop(){return meldTypeStack.top();}
	final public boolean mstackIsEmpty(){return meldTypeStack.isEmpty();}
	
	//returns partner IDs for the top meldType on the stack
	final public int[] mstackTopParterIDs(){
		int id = getId();
		switch(meldTypeStack.top()){
		case CHI_L: return new int[]{id + 1, id + 2};
		case CHI_M: return new int[]{id - 1, id + 1};
		case CHI_H: return new int[]{id - 2, id - 1};
		
		case KAN: return new int[]{id, id, id};
		case PON: return new int[]{id, id};
		case PAIR: return new int[]{id};
		default: return null;
		}
	}
	
	
	
	
	
	
	
	public static final GTL makeCopyOfListWithCheckers(GTL tileList){
		ArrayList<GameTile> hctiles = new ArrayList<GameTile>(14);
		for (GameTile t: tileList)
			hctiles.add(new HandCheckerTile(t));
		
		return new GTL(hctiles);
	}
	public static final GTL populateStacksForEntireList(GTL tileList){
		ArrayList<GameTile> populatedTiles = new ArrayList<GameTile>(14);
		for (GameTile t: tileList)
			populatedTiles.add( (new HandCheckerTile(t)).populateMeldStacks(tileList) );
		
		return new GTL(populatedTiles);
	}
	
	
	
	
	
	
	
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_PAIR = 1;
	private static final int OFFSET_CHI_L1 = 1, OFFSET_CHI_L2 = 2;
	private static final int OFFSET_CHI_M1 = -1,  OFFSET_CHI_M2 = 1;
	private static final int OFFSET_CHI_H1 = -2, OFFSET_CHI_H2 = -1;
	
	//シュンツ
	private static boolean canClosedChiType(GTL checkTiles, GameTile candidate, int offset1, int offset2){
		return (checkTiles.contains(candidate.getId() + offset1) && checkTiles.contains(candidate.getId() + offset2));
	}
	private static boolean canClosedChiL(GTL checkTiles, GameTile candidate){return candidate.canCompleteChiL() && canClosedChiType(checkTiles, candidate, OFFSET_CHI_L1, OFFSET_CHI_L2);}
	private static boolean canClosedChiM(GTL checkTiles, GameTile candidate){return candidate.canCompleteChiM() && canClosedChiType(checkTiles, candidate, OFFSET_CHI_M1, OFFSET_CHI_M2);}
	private static boolean canClosedChiH(GTL checkTiles, GameTile candidate){return candidate.canCompleteChiH() && canClosedChiType(checkTiles, candidate, OFFSET_CHI_H1, OFFSET_CHI_H2);}
	//コーツ
	private static boolean canClosedMultiType(GTL checkTiles, GameTile candidate, int numPartnersNeeded){
		return checkTiles.findHowManyOf(candidate) >= numPartnersNeeded;
	}
	private static boolean canClosedPair(GTL checkTiles, GameTile candidate){return canClosedMultiType(checkTiles, candidate, NUM_PARTNERS_NEEDED_TO_PAIR + 1);}
	private static boolean canClosedPon(GTL checkTiles, GameTile candidate){return canClosedMultiType(checkTiles, candidate, NUM_PARTNERS_NEEDED_TO_PON + 1);}
	
	
	//checks if a tile is meldable, populates the meldStack for candidate. returns true if a meld (any meld) can be made
	private static HandCheckerTile populateMeldStacks(HandCheckerTile candidate, GTL checkTiles){
		MeldTypeStack populatedMTS = candidate.meldTypeStack;
		//changed order of stack on 2019-08-03, tests show that it still works. Just in case, original comment was: "order of stack should be top->L,M,H,Pon,Pair"
		if (canClosedPair(checkTiles, candidate))  populatedMTS = populatedMTS.push(MeldType.PAIR);
		if (canClosedChiH(checkTiles, candidate)) populatedMTS = populatedMTS.push(MeldType.CHI_H);
		if (canClosedChiM(checkTiles, candidate)) populatedMTS = populatedMTS.push(MeldType.CHI_M);
		if (canClosedChiL(checkTiles, candidate)) populatedMTS = populatedMTS.push(MeldType.CHI_L);
		if (canClosedPon(checkTiles, candidate)) populatedMTS = populatedMTS.push(MeldType.PON);
		
		return candidate.withMTS(populatedMTS);
	}
	private HandCheckerTile populateMeldStacks(GTL checkTiles){return populateMeldStacks(this, checkTiles);}
	
	
	
	
	
	
	
	
	
	
	
	//inner class
	private static class MeldTypeStack implements Cloneable{
		private static final int MAX_SIZE = 5;
		private static final int MAX_POS = 4;
		private static final int EMPTY_POS = -1;
		
		private final MeldType[] list;
		private final int pos;
		
		public MeldTypeStack(){this(EMPTY_POS, new MeldType[MAX_SIZE]);}
		private MeldTypeStack(int position, MeldType[] otherlist){
			pos = position;
			
			list = new MeldType[MAX_SIZE];
			list[0] = otherlist[0];
			list[1] = otherlist[1];
			list[2] = otherlist[2];
			list[3] = otherlist[3];
			list[4] = otherlist[4];
		}
		public MeldTypeStack(final MeldTypeStack other){this(other.pos, other.list);}
		public MeldTypeStack clone(){return new MeldTypeStack(this);}
		//builders
		public MeldTypeStack withPosition(int newPos){return new MeldTypeStack(newPos, list);}
		public MeldTypeStack withList(MeldType[] newList){return new MeldTypeStack(pos, newList);}
		
		
		
		public MeldType top(){
			if (pos < 0) return null;
			return list[pos];
		}
		
		public MeldTypeStack pop(){
			if (pos < 0) return null;
			return this.withPosition(pos-1);
		}
		
		public MeldTypeStack push(MeldType pushThis){
			if (pos >= MAX_POS) return this;
			
			int newPos = pos+1;
			MeldType[] newList = list.clone();
			newList[newPos] = pushThis;
			
//			return this.withPosition(newPos).withList(newList); //order doesn't matter
			return new MeldTypeStack(newPos, newList);
		}
		
		public boolean isEmpty(){return pos < 0;}
	}
	
}
