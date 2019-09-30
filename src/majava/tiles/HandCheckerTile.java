package majava.tiles;

import majava.enums.MeldType;



//a tile with additional information required for NormalCompleteChecker
public class HandCheckerTile extends GameTile {
	
	//stack of possible melds the tile can form
	private final MeldTypeStack meldTypeStack;
	
	
	public HandCheckerTile(GameTile other){
		this(other, meldTypeStackFor(other));
	}
	private HandCheckerTile(GameTile other, MeldTypeStack mts){
		super(other);
		meldTypeStack = mts;
	}
	//builder
	private HandCheckerTile withMTS(MeldTypeStack newMTS){return new HandCheckerTile(this, newMTS);}
	
	
	

	//stack functions
	public HandCheckerTile mstackPop(){return this.withMTS(meldTypeStack.pop());}
	public MeldType mstackTop(){return meldTypeStack.top();}
	public boolean mstackIsEmpty(){return meldTypeStack.isEmpty();}
	
	//returns partner IDs for the top meldType on the stack
	public int[] mstackTopPartnerIDs(){
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
	
	
	private static MeldTypeStack meldTypeStackFor(GameTile tile){
		MeldTypeStack populatedMTS = new MeldTypeStack();
		
		//changed order of stack on 2019-08-03, tests show that it still works. Just in case, original comment was: "order of stack should be top->L,M,H,Pon,Pair"
		populatedMTS = populatedMTS.push(MeldType.PAIR);
		if (tile.canCompleteChiH()) populatedMTS = populatedMTS.push(MeldType.CHI_H);
		if (tile.canCompleteChiM()) populatedMTS = populatedMTS.push(MeldType.CHI_M);
		if (tile.canCompleteChiL()) populatedMTS = populatedMTS.push(MeldType.CHI_L);
		populatedMTS = populatedMTS.push(MeldType.PON);
		
		return populatedMTS;
	}
	
	
	
	
	
	private static class MeldTypeStack{
		private static final int MAX_SIZE = 5, MAX_POS = 4, EMPTY_POS = -1;
		
		private final MeldType[] list;
		private final int pos;
		
		public MeldTypeStack(){this(EMPTY_POS, new MeldType[MAX_SIZE]);}
		private MeldTypeStack(int position, MeldType[] otherlist){
			pos = position;
			list = otherlist.clone();
		}
		
		
		
		public MeldType top(){
			if (isEmpty()) return null;
			return list[pos];
		}
		
		public MeldTypeStack pop(){
			if (isEmpty()) return null;
			return new MeldTypeStack(pos-1, list);
		}
		
		public MeldTypeStack push(MeldType pushThis){
			if (pos >= MAX_POS) return this;
			
			int newPos = pos+1;
			MeldType[] newList = list.clone();
			newList[newPos] = pushThis;
			
			return new MeldTypeStack(newPos, newList);
		}
		
		public boolean isEmpty(){return pos < 0;}
	}
	
}
