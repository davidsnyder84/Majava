package majava.tiles;


public class HandCheckerTile extends Tile {
	
	
	
//	public HandCheckerTile(int id) {super(id);}
	public HandCheckerTile(Tile other){
		super(other);
//		mMeldTypeStack = new MahStack<MeldType>(other.mMeldTypeStack);
	}
	
	
	
	
	

	
	/*
	public boolean mstackPush(MeldType meldType){return mMeldTypeStack.push(meldType);}
	public MeldType mstackPop(){return mMeldTypeStack.pop();}
	public MeldType mstackTop(){return mMeldTypeStack.top();}
	public boolean mstackIsEmpty(){return mMeldTypeStack.isEmpty();}
	
	//returns a list of the partner IDs for the top meldType on the stack
	public ArrayList<Integer> mstackTopParterIDs(){
		
		ArrayList<Integer> partnerIDs = new ArrayList<Integer>(2);
		
		switch(mMeldTypeStack.top()){
		case CHI_L: partnerIDs.add(mID + 1); partnerIDs.add(mID + 2); break;
		case CHI_M: partnerIDs.add(mID - 1); partnerIDs.add(mID + 1); break;
		case CHI_H: partnerIDs.add(mID - 2); partnerIDs.add(mID - 1); break;
		case PON: partnerIDs.add(mID); partnerIDs.add(mID); break;
		case KAN: partnerIDs.add(mID); partnerIDs.add(mID); partnerIDs.add(mID); break;
		case PAIR: partnerIDs.add(mID); break;
		default: break;
		}
		return partnerIDs;
	}
	
	*/
	
	
	
	
	
}
