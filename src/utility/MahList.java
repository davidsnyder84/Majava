package utility;

import java.util.ArrayList;
import java.util.Iterator;


/*
Class: MahList
a wrapper class for ArrayList. adds extra functionalities

data:
	mList - an arraylist that holds the elements of the MahList
	mSorter - used to sort the list
	
methods:
	
	constructors:
	Takes ArrayList
	Takes initial capacity
	Takes Array or Var args
	Takes another MahList, duplicates entries
	Default - creates empty list with default capacity
	
	
	mutators:
	addMultiple - adds multiple items to the end of a list. takes a list, an array, or var arguments
	addMultipleToBeginning -  adds multiple items to the beginning of a list. takes a list, an array, or var arguments
	
	removeFirst - removes and returns the first item in the list, returns null if the list is empty
	removeLast - removes and returns the last item in the list, returns null if the list is empty
	
	removeMultiple... - removes specified number of items from a specified position
	removeMultipleFromEnd - remove multiple items from end
	removeMultipleFromBeginning - remove multiple items from beginning
	removeMultipleFromPosition - remove multiple items from a given position
	
	sort - sort the list in ascending order
	sortAscending - sort ascending
	sortDescending - sort descending
	shuffle - shuffle the elements of the list in a random order
 	
 	
 	accessors:
	getFirst - returns the fist item in the list, returns null if the list is empty
	getLast - returns the last item in the list, returns null if the list is empty
	subList - returns a sublist, as a MahList from fromIndex (inclusive) to toIndex (exclusive)
	findAllIndicesOf - searches the list for all occurences of item, returns a MahList of integer indices of where that item occurred
	
	
	other:
	iterator - the ArrayList's iterator
	
	methods from ArrayList:
	add, remove, size, get, contains, isEmpty, indexOf, lastIndexOf, 
	set, clear, trimToSize, ensureCapacity, iterator
	
*/
public class MahList <T extends Comparable<T> > implements Iterable<T>{
	
	
	protected static final int DEFAULT_CAPACITY = 10;
	public static final int NOT_FOUND = -1;
	
	
	private ArrayList<T> mList;
	private GenSort<T> mSorter;
	
	
	//takes an arrayList
	public MahList(ArrayList<T> newData){
		mList = newData;
		mSorter = new GenSort<T>(mList);
	}
	//creates a new lsit with the given capacity
	public MahList(int capacity){
		this(new ArrayList<T>(capacity));
	}
	@SafeVarargs
	//can take an array, or a variable number or arguments of type T 
	public MahList(T... items){
		this(items.length);
		for (T item: items) mList.add(item);
	}
	//copy constructor, duplicates another MahList
	public MahList(MahList<T> other){		
		this(other.size());
		for (T item: other) mList.add(item);
	}
	public MahList(){
		this(DEFAULT_CAPACITY);
	}
	
	
	
	
	/*
	private method: subList
	returns a sublist, as a MahList<T> type list from fromIndex (inclusive) to toIndex (exclusive)
	Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive. (If fromIndex and toIndex are equal, the returned list is empty)
	
	input: fromIndex, inclusive
		   toIndex, exclusive
	
	creates a new arraylist out of a sublist of mList
	returns a new MahList object with the sublist as its list 
	*/
	public MahList<T> subList(int fromIndex, int toIndex){
		ArrayList<T> alSubList = new ArrayList<T>(mList.subList(fromIndex, toIndex));
		return new MahList<T>(alSubList);
	}
	
	
	
	//returns the first item in the list, returns null if the list is empty
	public T getFirst(){
		if (mList.isEmpty()) return null;
		return mList.get(0);
	}
	//returns the last item in the list, returns null if the list is empty
	public T getLast(){
		if (mList.isEmpty()) return null;
		return mList.get(mList.size() - 1);
	}
	
	
	//removes and returns the first item in the list, returns null if the list is empty
	public T removeFirst(){
		if (mList.isEmpty()) return null;
		return mList.remove(0);
	}
	//removes and returns the last item in the list, returns null if the list is empty
	public T removeLast(){
		if (mList.isEmpty()) return null;
		return mList.remove(mList.size() - 1);
	}
	
	
	//remove multiple items
	//returns true if exactly the desired number of items were removed
	//returns false if the list was emptied before removing the desired number of items
	public boolean removeMultipleFromEnd(int howMany){
		int i = 0;
		while(i < howMany && !mList.isEmpty()){
			mList.remove(mList.size() - 1);
			i++;
		}
		return (i == howMany);
	}
	public boolean removeMultipleFromBeginning(int howMany){
		int i = 0;
		while(i < howMany && !mList.isEmpty()){
			mList.remove(0);
			i++;
		}
		return (i == howMany);
	}
	public boolean removeMultipleFromPosition(int howMany, int position){
		int i = 0;
		while(i < howMany && (position + i) < mList.size()){
			mList.remove(position);
			i++;
		}
		return (i == howMany);
	}
	
	
	//add multiple items to the end of a list
	//takes a list, an array, or var arguments
	public void addMultiple(MahList<T> items){
		for (T item: items) mList.add(item);
	}
	@SuppressWarnings("unchecked")
	public void addMultiple(T... items){addMultiple(new MahList<T>(items));}
	public void addMultiple(ArrayList<T> items){addMultiple(new MahList<T>(items));}
	
	
	
	//add multiple items to the beginning of a list
	//takes a list, an array, or var arguments
	public void addMultipleToBeginning(MahList<T> items){
		for (T item: items) mList.add(0, item);
	}
	@SuppressWarnings("unchecked")
	public void addMultipleToBeginning(T... items){addMultiple(new MahList<T>(items));}
	public void addMultipleToBeginning(ArrayList<T> items){addMultiple(new MahList<T>(items));}
	
	
	
	
	//finds all indices where an item occurs in a list
	//returns the indices in a MahList
	public MahList<Integer> findAllIndicesOf(T item){
		MahList<Integer> indices = new MahList<Integer>(2);
		for (int i = 0; i < mList.size(); i++)
			if (mList.get(i).equals(item))	
				indices.add(i);
		
		return indices;
	}
	
	
	
	//sorts
	public void sort(){sortAscending();}
	public void sortAscending(){mSorter.sort();}
	public void sortDescending(){mSorter.sortDescending();}
	public void shuffle(){mSorter.shuffle();}
	
	
	
	
	
	
	
	
	
	//***************************************************************************************************
	//****BEGIN ARRAYLIST FUNCS
	//***************************************************************************************************
	//add
	public boolean add(T item){return mList.add(item);}
	public void add(int index, T item){mList.add(index, item);}
	
	//remove
	public boolean remove(T item){return mList.remove(item);}
	public T remove(int index){return mList.remove(index);}
	
	//size
	public int size(){return mList.size();}
	
	//get
	public T get(int index){return mList.get(index);}
	public boolean contains(T item){return mList.contains(item);}
	public boolean isEmpty(){return mList.isEmpty();}
	public int indexOf(T item){return mList.indexOf(item);}
	public int lastIndexOf(T item){return mList.lastIndexOf(item);}
	public T set(int index, T item){return mList.set(index, item);}
	

	//public List<T> subList(int fromIndex, int toIndex){return mList.subList(fromIndex, toIndex);}
	public void clear(){mList.clear();}
	public void trimToSize(){mList.trimToSize();}
	public void ensureCapacity(int minCapacity){mList.ensureCapacity(minCapacity);}
	
	
	//returns the arrayList's iterator
	@Override
	public Iterator<T> iterator(){return mList.iterator();}
	//***************************************************************************************************
	//****END ARRAYLIST FUNCS
	//***************************************************************************************************
	
	//returns a reference to the MahList's arrayList (parentheses this is a good idea)
	public ArrayList<T> getArrayList(){return mList;}
	
	
	
	
}
