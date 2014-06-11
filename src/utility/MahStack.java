package utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import majava.tiles.GameTile;


/*
Class: MahStack
a wrapper class for a List to simulate stack operations

data:
	mList - a list that holds the elements of the stack
	
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
		iterator - the List's iterator
*/
public class MahStack <T> implements Cloneable{
	
	protected static final int DEFAULT_CAPACITY = 10;
	
	
	private final List<T> mList;
	
	
	
	//creates a new stack with the given capacity
	public MahStack(int capacity){mList = new ArrayList<T>(capacity);}
	public MahStack(){this(DEFAULT_CAPACITY);}
	
	//copy constructor, duplicates another stack
	public MahStack(MahStack<T> other){
		this(other.size());
		for (T item: other.mList) mList.add(item);
		
	}
	public MahStack<T> clone(){
		return new MahStack<T>(this);
	}
	
	
	
	
	//-----------Stack functions
	public T top(){
		if (mList.isEmpty()) return null;
		return mList.get(mList.size() - 1);
	}
	
	public T pop(){
		if (mList.isEmpty()) return null;
		return mList.remove(mList.size() - 1);
	}
	
	public boolean push(T item){return mList.add(item);}
	
	public boolean isEmpty(){return mList.isEmpty();}
	
	public int size(){return mList.size();}
	
	
	
	
	//removes all items from the stack
//	public void makeEmpty(){mList = new ArrayList<T>(DEFAULT_CAPACITY);}
//	public void makeEmpty(){mList.clear();}
	
	
	
	
	
	
	
//	@Override
//	public Iterator<T> iterator(){return mList.iterator();}	//returns the List's iterator
	
	
	
	
	
	
	
	
	
	

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
