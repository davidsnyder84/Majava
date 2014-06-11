package majava.tiles;

import java.util.Arrays;
import java.util.List;

import majava.enums.MeldType;
import utility.MahStack;


/*
Class: HandCheckerTile
a tile with additional information required for hand checking

data:
	mMeldTypeStack - stack of possible melds the tile can form
	
methods:
	
	constructors:
	Requires another tile (copy constructor) 
	
	public:
		mutators:
	 	mstackPush, mstackPop - stack functions for stack of melds
	 	
	 	accessors:
		mstackTop, mstackIsEmpty - stack functions for stack of melds
		mstackTopParterIDs - returns partner ids for the meld on top of the stack
*/
public class HandCheckerTile extends GameTile {
	
	
	private final MahStack<MeldType> mMeldTypeStack;
	
	
	
	public HandCheckerTile(GameTile other){
		super(other);
		
		if (other instanceof HandCheckerTile)
			mMeldTypeStack =  new MahStack<MeldType>(((HandCheckerTile) other).mMeldTypeStack);
		else
			mMeldTypeStack = new MahStack<MeldType>();
	}
	public HandCheckerTile(TileInterface t){this(new GameTile(t));}
	public HandCheckerTile clone(){return new HandCheckerTile(this);}
	
	
	
	
	

	//stack functions
	final public boolean mstackPush(MeldType meldType){return mMeldTypeStack.push(meldType);}
	final public MeldType mstackPop(){return mMeldTypeStack.pop();}
	final public MeldType mstackTop(){return mMeldTypeStack.top();}
	final public boolean mstackIsEmpty(){return mMeldTypeStack.isEmpty();}
	
	//returns a list of the partner IDs for the top meldType on the stack
	final public List<Integer> mstackTopParterIDs(){
		int id = getId();
		switch(mMeldTypeStack.top()){
		case CHI_L: return Arrays.asList(id + 1, id + 2);
		case CHI_M: return Arrays.asList(id - 1, id + 1);
		case CHI_H: return Arrays.asList(id - 2, id - 1);
		
		case KAN: return Arrays.asList(id, id, id);
		case PON: return Arrays.asList(id, id);
		case PAIR: return Arrays.asList(id);
		default: return null;
		}
	}
	
	
	
	
	
//	public String stackString(){
//		String stackString = "";
//		for (MeldType m: mMeldTypeStack) stackString += m.toString() + ", ";
//		return stackString;
//	}
	
	
}
