package utility;

import java.util.ArrayList;


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
public class MahStack<T> extends ArrayList<T> implements Cloneable{
	private static final long serialVersionUID = -5155657231824569104L;


	protected static final int DEFAULT_CAPACITY = 10;
	
	
//	private final List<T> mList;
	
	
	
	//creates a new stack with the given capacity
	public MahStack(int capacity){super(capacity);}
	public MahStack(){this(DEFAULT_CAPACITY);}
	
	//copy constructor, duplicates another stack
	public MahStack(MahStack<T> other){
		super(other);
//		this(DEFAULT_CAPACITY);
//		addAll(other);
	}
	@Override
	public MahStack<T> clone(){
		return new MahStack<T>(this);
	}
	
	
	
	
	//-----------Stack functions
	public T top(){
		if (isEmpty()) return null;
		return get(size() - 1);
	}
	
	public T pop(){
		if (isEmpty()) return null;
		return remove(size() - 1);
	}
	
	public boolean push(T item){return add(item);}
	
	
	
	
	//removes all items from the stack
//	public void makeEmpty(){mList = new ArrayList<T>(DEFAULT_CAPACITY);}
//	public void makeEmpty(){mList.clear();}
	
	
	
	
	
	
	
	
	
	

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
