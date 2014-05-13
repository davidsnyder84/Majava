package majava.tiles;

import java.util.ArrayList;

import majava.MeldType;
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
public class HandCheckerTile extends Tile {
	
	
	MahStack<MeldType> mMeldTypeStack;
	
	
	
	public HandCheckerTile(Tile other){
		super(other);
		
		if (other instanceof HandCheckerTile)
			mMeldTypeStack =  new MahStack<MeldType>(((HandCheckerTile) other).mMeldTypeStack);
		else
			mMeldTypeStack = new MahStack<MeldType>();
	}
	
	
	
	
	

	//stack functions
	public boolean mstackPush(MeldType meldType){return mMeldTypeStack.push(meldType);}
	public MeldType mstackPop(){return mMeldTypeStack.pop();}
	public MeldType mstackTop(){return mMeldTypeStack.top();}
	public boolean mstackIsEmpty(){return mMeldTypeStack.isEmpty();}
	
	//returns a list of the partner IDs for the top meldType on the stack
	public ArrayList<Integer> mstackTopParterIDs(){
		
		ArrayList<Integer> partnerIDs = new ArrayList<Integer>(2);
		
		int id = getId();
		switch(mMeldTypeStack.top()){
		case CHI_L: partnerIDs.add(id + 1); partnerIDs.add(id + 2); break;
		case CHI_M: partnerIDs.add(id - 1); partnerIDs.add(id + 1); break;
		case CHI_H: partnerIDs.add(id - 2); partnerIDs.add(id - 1); break;
		case PON: partnerIDs.add(id); partnerIDs.add(id); break;
		case KAN: partnerIDs.add(id); partnerIDs.add(id); partnerIDs.add(id); break;
		case PAIR: partnerIDs.add(id); break;
		default: break;
		}
		return partnerIDs;
	}
	
	
	
	
	
	public String stackString(){
		String stackString = "";
		for (MeldType m: mMeldTypeStack) stackString += m.toString() + ", ";
		return stackString;
	}
	
	
}
