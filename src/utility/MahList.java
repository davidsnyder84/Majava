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
	ArrayList / initial capacity / Array or Var args / copy constructor
	Default - creates empty list with default capacity
	
	public:
		mutators:
		removeFirst, removeLast - removes and returns an item in the list, returns null if the list is empty
		sort, sortAscending, sortDescending, shuffle - sorts the list
	 	
	 	accessors:
		getFirst, getLast - returns an item in the list, returns null if the list is empty
		findAllIndicesOf - searches the list for all occurences of item, returns a MahList of integer indices of where that item occurred
		
		methods from ArrayList:
		add, remove, size, get, contains, isEmpty, indexOf, lastIndexOf, set, clear, trimToSize, ensureCapacity, iterator, subList, iterator
*/
public class MahList <T extends Comparable<T> > implements Iterable<T>{
	
	
	private static final int DEFAULT_CAPACITY = 10;
	
	
	private ArrayList<T> mList;
	private GenSort<T> mSorter;
	
	
	//takes an arrayList
	public MahList(ArrayList<T> newData){
		mList = newData;
		mSorter = new GenSort<T>(mList);
	}
	//creates a new list with the given capacity
	public MahList(int capacity){this(new ArrayList<T>(capacity));}
	public MahList(){this(DEFAULT_CAPACITY);}
	
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
	
	
	
	
	//returns an item
	public T getFirst(){if (mList.isEmpty()) return null; return mList.get(0);}
	public T getLast(){if (mList.isEmpty()) return null; return mList.get(mList.size() - 1);}
	
	
	//removes and returns an item
	public T removeFirst(){if (mList.isEmpty()) return null; return mList.remove(0);}
	public T removeLast(){if (mList.isEmpty()) return null; return mList.remove(mList.size() - 1);}
	
	
	
	
	//finds all indices where an item occurs in a list
	//returns the indices in a MahList
	public MahList<Integer> findAllIndicesOf(T item){
		MahList<Integer> indices = new MahList<Integer>();
		for (int i = 0; i < mList.size(); i++) if (mList.get(i).equals(item)) indices.add(i);
		
		return indices;
	}
	
	
	
	
	//sorts
	public void sort(){sortAscending();}
	public void sortAscending(){mSorter.sortAscending();}
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
	
	//returns a sublist, as a MahList<T>
	public MahList<T> subList(int fromIndex, int toIndex){ArrayList<T> alSubList = new ArrayList<T>(mList.subList(fromIndex, toIndex)); return new MahList<T>(alSubList);}

	//public List<T> subList(int fromIndex, int toIndex){return mList.subList(fromIndex, toIndex);}
	public void clear(){mList.clear();}
	public void trimToSize(){mList.trimToSize();}
	public void ensureCapacity(int minCapacity){mList.ensureCapacity(minCapacity);}
	
	@Override
	public Iterator<T> iterator(){return mList.iterator();}	//returns the arrayList's iterator
	//***************************************************************************************************
	//****END ARRAYLIST FUNCS
	//***************************************************************************************************
	
	
	//returns a reference to the MahList's arrayList (parentheses this is a good idea)
	public ArrayList<T> getArrayList(){return mList;}
	
	
	
	
}
