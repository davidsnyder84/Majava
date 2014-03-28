package utility;

import java.util.ArrayList;
import java.util.Random;


public class GenSort <T extends Comparable<T> > {
	
	public static final int SORT_ASCENDING = 1;
	public static final int SORT_DESCENDING = 2;
	public static final int SORT_RANDOM = 3;
	public static final int SORT_DEFAULT = SORT_ASCENDING;
	
	public static final int INVALID_LIST_LENGTH = -1;
	
	
	private ArrayList<T> mListToSort;
	private int mListLength = INVALID_LIST_LENGTH;
	
	//1-arg constructor
	//takes an arraylist
	public GenSort(ArrayList<T> list){
		
		if (list != null)
		{
			mListToSort = list;
			checkListLength();
		}
		else
		{
			mListToSort = null;
			mListLength = INVALID_LIST_LENGTH;
			System.out.println("-----Error: unsuitable list received by GenSort");
		}
	}
	
	
	
	
	//sorts mListToSort with a shitty selection sort, ascending order
	public void sort(){
		sortAscending();
	}
	

	//sorts mListToSort with a shitty selection sort, ascending order
	public void sortAscending(){

		checkListLength();
		int current, walker, smallest;
		T temp;
		
		for (current = 0; current < mListLength; current++)
		{
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
	
	
	
	//sorts mListToSort with a shitty selection sort, descending order
	public void sortDescending(){
		
		checkListLength();
		int current, walker, biggest;
		T temp;
		
		for (current = 0; current < mListLength; current++)
		{
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
	/*
	//sorts mListToSort with a shitty selection sort, descending order
	public void sortDescending(){
			
		int current, walker, biggest;
		T temp;
		checkListLength();
		
		for (current = mListLength - 1; current >= 0; current--)
		{
			biggest = current;
			for (walker = current - 1; walker >= 0; walker--)
				if (mListToSort.get(walker).compareTo(mListToSort.get(biggest)) <  0)
					biggest = walker;
			
			//swap the current element and the biggest element
			temp = mListToSort.get(biggest);
			mListToSort.set(biggest, mListToSort.get(current));
			mListToSort.set(current, temp);
		}
		
	}
	*/
	
	
	
	//shuffles the elements of mListToSort in a random order
	public void shuffle(){

		checkListLength();
		Random random = new Random();
		int swapIndex = -1;
		T temp = null;
		
		int curIndex;
		for (curIndex = 0; curIndex < mListLength; curIndex++)
		{
			//decide the swap index randomly
			swapIndex = random.nextInt(mListLength);
			
			//swap the items at the current index and the swap index
			temp = mListToSort.get(curIndex);
			mListToSort.set(curIndex, mListToSort.get(swapIndex));
			mListToSort.set(swapIndex, temp);
		}
		
	}
	
	
	
	//reverses the order of the items in a list
	public void reverseOrder(){
		
		checkListLength();
		
		//templist is a copy of the list
		ArrayList<T> tempList = new ArrayList<T>(mListLength);
		for (T item: mListToSort) tempList.add(item);
		
		//empty ListToSort
		mListToSort.clear();
		
		//add the items back to ListToSort in reverse order 
		while (tempList.isEmpty() == false) mListToSort.add(tempList.remove(tempList.size() - 1));
	}
	
	
	
	
	
	//checks the list length (must be done before a sort)
	public int checkListLength(){
		return mListLength = mListToSort.size();
	}
	
	
	
}
