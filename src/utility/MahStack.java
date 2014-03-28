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
	Takes initial capacity
	Default - creates empty stack with default capacity
	Copy constructor - Takes another stack, duplicates entries
	
	
	mutators:
	push - adds an item to the top of the stack
	pop - removes and returns the top item on the stack, returns null if the stack is empty
	makeEmpty - removes all items from the stack
	reverseOrder - reverses the order of the stack
 	
 	accessors:
	top - returns the top item on the stack, returns null if the stack is empty
	isEmpty - returns true if the stack is empty
	size - returns the number of items in the stack
	
	
	other:
	iterator - the ArrayList's iterator
	getArrayList - returns the arralist that holds the elements of the stack (this is a good idea)
	
*/
public class MahStack <T extends Comparable<T> > implements Iterable<T>{
	
	protected static final int DEFAULT_CAPACITY = 10;
	
	
	private ArrayList<T> mList;
	
	
	
	//creates a new stack with the given capacity
	public MahStack(int capacity){
		mList = new ArrayList<T>(capacity);
	}
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
	
	/*
	public MahStack<T> createCopy(){
		MahStack<T> newStack;
	}
	*/
	
	
	//reverses the order of the stack
	public void reverseOrder(){GenSort<T> sorter = new GenSort<T>(mList); sorter.reverseOrder();}
	
	//removes all items from the stack
	public void makeEmpty(){mList = new ArrayList<T>(DEFAULT_CAPACITY);}
	
	
	
	
	
	
	//returns the arrayList's iterator
	@Override
	public Iterator<T> iterator(){return mList.iterator();}
	
	//returns a reference to the MahList's arrayList (parentheses this is a good idea)
	public ArrayList<T> getArrayList(){return mList;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

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
