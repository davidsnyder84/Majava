package utility;

import java.util.ArrayList;
import java.util.Iterator;


/*
Class: MahStack
a wrapper class for ArrayList to simulate stack operations

data:
	mList - an arraylist that holds the elements of the stack
	
methods:
	
	constructors:
	initial capacity
	Copy constructor - Takes another stack, duplicates entries
	
	public
		mutators:
		push - adds an item to the top of the stack
		pop - removes and returns the top item on the stack, returns null if the stack is empty
		makeEmpty - removes all items from the stack
	 	
	 	accessors:
		top - returns the top item on the stack, returns null if the stack is empty
		isEmpty - returns true if the stack is empty
		size - returns the number of items in the stack
		
		
		other:
		iterator - the ArrayList's iterator
*/
public class MahStack <T extends Comparable<T> > implements Iterable<T>{
	
	protected static final int DEFAULT_CAPACITY = 10;
	
	
	private ArrayList<T> mList;
	
	
	
	//creates a new stack with the given capacity
	public MahStack(int capacity){mList = new ArrayList<T>(capacity);}
	public MahStack(){this(DEFAULT_CAPACITY);}
	
	//copy constructor, duplicates another stack
	public MahStack(MahStack<T> other){		
		this(other.size());
		for (T item: other.mList) mList.add(item);
	}
	
	
	
	

	//returns the top item on the stack, returns null if the stack is empty
	public T top(){
		if (mList.isEmpty()) return null;
		return mList.get(mList.size() - 1);
	}

	//removes and returns the top item on the stack, returns null if the stack is empty
	public T pop(){
		if (mList.isEmpty()) return null;
		return mList.remove(mList.size() - 1);
	}
	
	//adds an item to the top of the stack
	public boolean push(T item){return mList.add(item);}
	
	
	
	//returns true if the stack is empty
	public boolean isEmpty(){return mList.isEmpty();}

	//returns the number of items in the stack
	public int size(){return mList.size();}
	
	
	//removes all items from the stack
	public void makeEmpty(){mList = new ArrayList<T>(DEFAULT_CAPACITY);}
	
	
	
	
	
	
	
	@Override
	public Iterator<T> iterator(){return mList.iterator();}	//returns the arrayList's iterator
	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		
		MahStack<Integer> ints = new MahStack<Integer>();
		ints.push(3);
		ints.push(1);
		ints.push(6);
		ints.push(2);
		ints.push(0);
		ints.push(9);
		
		//ints.reverseOrder();
		while (ints.isEmpty() == false)
			System.out.println(ints.pop());
	}
	
	
	
}
