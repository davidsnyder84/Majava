import java.util.ArrayList;
import java.util.Iterator;

import utility.MahStack;


//a list of stacks of meldtypes
public class MeldTypeStackList implements Iterable<MahStack<MeldType>>{
	
	private static final int DEFAULT_LIST_SIZE = 14;
	private static final int DEFAULT_STACK_SIZE = 0;
	
	
	private ArrayList<MahStack<MeldType>> mStackList;
	
	
	
	//constructors, only take size
	public MeldTypeStackList(int size){
		//allocate mem for the list
		mStackList = new ArrayList<MahStack<MeldType>>(size);
		//allocate mem for each stack in the list
		for (int i = 0; i < size; i++) mStackList.add(new MahStack<MeldType>(DEFAULT_STACK_SIZE));
//		for (MahStack<MeldType> s: mStackList) s = new MahStack<MeldType>();
		
	}
	public MeldTypeStackList(){this(DEFAULT_LIST_SIZE);}
	private MeldTypeStackList(int size, boolean dontAddBlanks){mStackList = new ArrayList<MahStack<MeldType>>(size);}
	
	
	
	//returns a new MeldTypeStackList with an independent COPY of each stack in the list
	public MeldTypeStackList makeCopy(){
		MeldTypeStackList copy = new MeldTypeStackList(3, true);
		for (MahStack<MeldType> s: mStackList) copy.add(new MahStack<MeldType>(s));
		return copy;
	}
	
	
	
	//get the first stack in the list
	public MahStack<MeldType> first(){return mStackList.get(0);}
	
	//stack functions for the first stack in the list
	public boolean firstPush(MeldType meldType){return mStackList.get(0).push(meldType);}
	public MeldType firstPop(){return mStackList.get(0).pop();}
	public MeldType firstTop(){return mStackList.get(0).top();}
	public boolean firstIsEmpty(){return mStackList.get(0).isEmpty();}
	//returns a list of the partner IDs for the top meldType on the stack
	
	//returns the partner IDs for the first stack's top meld type, for the given tile ID
	public ArrayList<Integer> firstTopPartnerIDs(int tileID){return partnerIDsOf(tileID, mStackList.get(0).top());}
	
	
	
	//add
	public boolean add(MahStack<MeldType> s){return mStackList.add(s);}
	//remove
	public MahStack<MeldType> remove(int index){return mStackList.remove(index);}
	//get
	public MahStack<MeldType> get(int index){return mStackList.get(index);}
	
	//remove multiple
	public void removeMultiple(ArrayList<Integer> removeIndices){
		
	}
	
	//size
	public int size(){return mStackList.size();}
	
	
	//returns the arrayList's iterator, to iterate over the list of stacks
	@Override
	public Iterator<MahStack<MeldType>> iterator(){return mStackList.iterator();}
	
	
	
	
	
	
	
	
	//returns a list of the partner IDs for the top meldType on the stack
	public static ArrayList<Integer> partnerIDsOf(int tileID, MeldType meldType){
		
		ArrayList<Integer> partnerIDs = new ArrayList<Integer>(2);
		
		switch(meldType){
		case CHI_L: partnerIDs.add(tileID + 1); partnerIDs.add(tileID + 2); break;
		case CHI_M: partnerIDs.add(tileID - 1); partnerIDs.add(tileID + 1); break;
		case CHI_H: partnerIDs.add(tileID - 2); partnerIDs.add(tileID - 1); break;
		case PON: partnerIDs.add(tileID); partnerIDs.add(tileID); partnerIDs.add(tileID); break;
		case KAN: partnerIDs.add(tileID); partnerIDs.add(tileID); partnerIDs.add(tileID); partnerIDs.add(tileID); break;
		case PAIR: partnerIDs.add(tileID); partnerIDs.add(tileID); break;
		default: break;
		}
		return partnerIDs;
	}
	
	
	
	
	
	
	@Override
	public String toString(){
		
		String stackString = "";
		
		for (MahStack<MeldType> m: mStackList){
			for (MeldType t: m)
				stackString += t.toString() + ", ";
			stackString += "\n";
		}
		
		return stackString;
	}
	
	
	
	
	public static void main(String[] args){
		
		MeldTypeStackList list = new MeldTypeStackList(3);
		list.firstPush(MeldType.PAIR);
		list.firstPush(MeldType.PON);
		list.firstPush(MeldType.CHI_L);
		list.get(1).push(MeldType.CHI_H);
		list.get(1).push(MeldType.CHI_L);
		list.get(2).push(MeldType.PAIR);
		list.get(2).push(MeldType.PON);
		

		System.out.println("List 1 (size " + list.size() + "):\n" + list.toString());
		
		MeldTypeStackList list2 = list.makeCopy();
		list.remove(1);
		System.out.println("List 2 (size " + list2.size() + "):\n" + list2.toString());
//		System.out.println("\nHand is complete normal?: " + h.mChecker.isNormalComplete());
	}
	
	
}
