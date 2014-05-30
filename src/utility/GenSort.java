package utility;

import java.util.List;
import java.util.Random;



/*
Class: GenSort
contains operations for sorting a List in various ways

methods:
	
	constructors:
	Requires a list, associates this sorter with the list
	
	public:
		mutators:
	 	sort, sortAscending, sortDescending - sorts the list in the specified order
	 	shuffle - shuffles the list in a random order
*/
public class GenSort <T extends Comparable<T> > {
	
	private static final int INVALID_LIST_LENGTH = -1;
	
	
	private List<T> mListToSort;
	private int mListLength;
	
	//takes a list
	public GenSort(List<T> list){
		
		if (list != null){
			mListToSort = list;
			__checkListLength();
		}
		else{
			mListToSort = null;
			mListLength = INVALID_LIST_LENGTH;
			System.out.println("-----Error: unsuitable list received by GenSort");
		}
	}
	
	
	
	
	//sorts mListToSort with a selection sort, ascending order
	public void sort(){sortAscending();}
	
	
	//sorts mListToSort with a selection sort, ascending order
	public void sortAscending(){

		__checkListLength();
		int current, walker, smallest;
		T temp;
		
		for (current = 0; current < mListLength; current++){
			smallest = current;
			for (walker = current + 1; walker < mListLength; walker++)
				if (mListToSort.get(walker).compareTo(mListToSort.get(smallest)) < 0)
					smallest = walker;
			
			//swap the current element and the smallest element
			temp = mListToSort.get(smallest);
			mListToSort.set(smallest, mListToSort.get(current));
			mListToSort.set(current, temp);
		}
		
	}
	
	
	
	//sorts mListToSort with a selection sort, descending order
	public void sortDescending(){
		
		__checkListLength();
		int current, walker, biggest;
		T temp;
		
		for (current = 0; current < mListLength; current++){
			biggest = current;
			for (walker = current + 1; walker < mListLength; walker++)
				if (mListToSort.get(walker).compareTo(mListToSort.get(biggest)) >  0)
					biggest = walker;
			
			//swap the current element and the biggest element
			temp = mListToSort.get(biggest);
			mListToSort.set(biggest, mListToSort.get(current));
			mListToSort.set(current, temp);
		}
		
	}
	
	
	
	
	
	//shuffles the elements of mListToSort in a random order
	public void shuffle(){

		__checkListLength();
		Random random = new Random();
		int swapIndex = -1;
		T temp = null;
		
		int curIndex;
		for (curIndex = 0; curIndex < mListLength; curIndex++){
			//decide the swap index randomly
			swapIndex = random.nextInt(mListLength);
			
			//swap the items at the current index and the swap index
			temp = mListToSort.get(curIndex);
			mListToSort.set(curIndex, mListToSort.get(swapIndex));
			mListToSort.set(swapIndex, temp);
		}
	}
	
	
	//checks the list length (must be done before a sort)
	private int __checkListLength(){return mListLength = mListToSort.size();}
	
}
