package majava.tiles;

import majava.enums.MeldType;
import majava.util.GameTileList;



//a tile with additional information required for hand checking
public class HandCheckerTile extends GameTile {
	
	//stack of possible melds the tile can form
	private final MeldTypeStack meldTypeStack;
	
	
	//copy constructor
	public HandCheckerTile(GameTile other){
		super(other);
		
		if (other instanceof HandCheckerTile)
			meldTypeStack =  ((HandCheckerTile) other).meldTypeStack.clone();
		else
			meldTypeStack = new MeldTypeStack();
	}
	public HandCheckerTile(Janpai t){this(new GameTile(t));}
	public HandCheckerTile clone(){return new HandCheckerTile(this);}
	
	
	
	
	

	//stack functions
//	final public boolean mstackPush(MeldType meldType){return mMeldTypeStack.push(meldType);}
	final public void mstackPush(MeldType meldType){meldTypeStack.push(meldType);}
	final public MeldType mstackPop(){return meldTypeStack.pop();}
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
	
	
	public static final GameTileList makeCopyOfListWithCheckers(GameTileList tileList){
		GameTileList copy = new GameTileList(tileList.size());
		for (GameTile t: tileList) copy.add(new HandCheckerTile(t));
		return copy;
	}
	
	
	
	
	
	//inner class
	private static class MeldTypeStack implements Cloneable{
		private static final int MAX_SIZE = 5;
		private static final int MAX_POS = 4;
		
		private final MeldType[] list = new MeldType[MAX_SIZE];
		private int pos = -1;
		
		public MeldTypeStack(){}
		public MeldTypeStack(final MeldTypeStack other){
			pos = other.pos;
			list[0] = other.list[0];
			list[1] = other.list[1];
			list[2] = other.list[2];
			list[3] = other.list[3];
			list[4] = other.list[4];
		}
		public MeldTypeStack clone(){return new MeldTypeStack(this);}
		
		public MeldType top(){
			if (pos < 0) return null;
			return list[pos];
		}
		
		public MeldType pop(){
			if (pos < 0) return null;
			return list[pos--];
		}
		
		public void push(MeldType item){
			if (pos < MAX_POS)
				list[++pos] = item;
		}
		
		public boolean isEmpty(){return pos < 0;}
	}
	
}
