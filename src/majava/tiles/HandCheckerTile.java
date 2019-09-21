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
