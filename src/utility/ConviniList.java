package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//a wrapper class for an arraylist, adds extra functionalities (I think)
public class ConviniList<T extends Comparable<? super T>> extends ArrayList<T>{
//public class ConviniList<T extends Comparable<? extends T>> extends ArrayList<T>{
	private static final long serialVersionUID = -6296356765155653731L;
	

	//creates a new list with the given capacity
	public ConviniList(int capacity){super(capacity);}
	
	
	//takes a List
	public ConviniList(List<? extends T> items){
		this(items.size());
		addAll(items);
	}
	//can take an array, or a var args
	public ConviniList(@SuppressWarnings("unchecked") T... items){this(Arrays.asList(items));}
	public ConviniList(){this(10);}
	
	@Override
	public ConviniList<T> clone(){return new ConviniList<T>(this);}
	
	
	//return a new ConviniList<T> with a COPY (independent) of each tile in the list
	public ConviniList<T> makeCopyNoDuplicates(){
		ConviniList<T> copy = new ConviniList<T>(size());
		for (T item: this) if (!copy.contains(item)) copy.add(item);
		return copy;
	}
	
	
	
	
	
	
	
	//returns a tile in the list, returns null if the list is empty
	public T getFirst(){if (isEmpty()) return null; return get(0);}
	public T getLast(){if (isEmpty()) return null; return get(size() - 1);}
	
	//removes and returns a tile in the list, returns null if the list is empty
	public T removeFirst(){if (isEmpty()) return null; return remove(0);}
	public T removeLast(){if (isEmpty()) return null; return remove(size() - 1);}
	

	
	@Override
	//returns a sublist, as a ConviniList<T> from fromIndex (inclusive) to toIndex (exclusive)
	public ConviniList<T> subList(int fromIndex, int toIndex){return new ConviniList<T>(super.subList(fromIndex, toIndex));}
	
	//returns a sublist of the entire list, minus the last tile
	public ConviniList<T> getAllExceptLast(){return subList(0, size() - 1);}
	
	
	
	
	
	
	//returns multiple items in the list, at the given indices
	public ConviniList<T> getMultiple(List<Integer> indices){
		ConviniList<T> holder = new ConviniList<T>();
		for (Integer index: indices)
			if (index < size() && index >= 0)
				holder.add(get(index));
		
		return holder;
	}
	public ConviniList<T> getMultiple(Integer... indices){return getMultiple(Arrays.asList(indices));}
	
	
	
	//remove multiple indices from the list
	//returns true if the indices were removed, false if not
	public boolean removeMultiple(final List<Integer> removeIndices){
		
		//disallow more indices than the list has
		if (removeIndices.size() > size()) return false;
		
		List<Integer> seenSoFar = new ArrayList<Integer>();
		for (Integer i: removeIndices){
			//disallow an index outside of the list's size, disallow duplicate indices
			if (i >= size() || i < 0 || seenSoFar.contains(i)) return false;
			else seenSoFar.add(i);
		}
		
		//sort the indices in descending order
		Collections.sort(seenSoFar, Collections.reverseOrder());
		
		//remove the indices
		for (Integer i: seenSoFar) remove((int)i);
		
		return true;
	}
	
	
	
	

	
	
	//finds all indices where a tile occurs in the list, returns the indices as a list of integers
	public List<Integer> findAllIndicesOf(Object item, boolean allowCountingItself){
		List<Integer> indices = new ArrayList<Integer>(2);
		for (int i = 0; i < size(); i++)
			if (get(i).equals(item)){
				if (get(i) != item)
					indices.add(i);
				else if (allowCountingItself)
					indices.add(i);
			}
		return indices;
	}
	//overloaded, omitting allowCountingItself will default to false (do not count itself)
	public List<Integer> findAllIndicesOf(T item){return findAllIndicesOf(item, false);}
	
	//allow counting itself
	public int findHowManyOf(Object item){return findAllIndicesOf(item, true).size();}
//	public int findHowManyOf(T item){return findAllIndicesOf(item, true).size();}
	
	
	
	
	//sort
	public void sort(){Collections.sort(this);}
	
}

