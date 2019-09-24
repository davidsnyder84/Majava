package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


//public class ImmuList<T> implements Iterable<T>{
public class ImmuList<T extends Comparable<? super T>> implements Iterable<T>{
//public class ImmuList<T extends Comparable<? extends T>> extends ArrayList<T>{
	
	private final ArrayList<T> list;
	
	
	public ImmuList(){list = new ArrayList<T>(15);}
	public ImmuList(List<T> items){
		this();
		list.addAll(items);	//should this be clone?
	}
	public ImmuList(ImmuList<T> other){
		this(other.list);
	}
	//can take an array, or a var args
	public ImmuList(@SuppressWarnings("unchecked") T... items){this(Arrays.asList(items));}
	
	@Override
	public ImmuList<T> clone(){return new ImmuList<T>(this);}
	
	
	public ImmuList<T> withList(ArrayList<T> newList){
		return new ImmuList<T>(newList);
	}
	
	
	//return a new ImmuList<T> with a COPY (independent) of each tile in the list
	public ImmuList<T> makeCopyNoDuplicates(){
		ArrayList<T> listWithNoDuplicates = new ArrayList<T>();
		for (T item: this)
			if (!listWithNoDuplicates.contains(item))
				listWithNoDuplicates.add(item);
		
		return this.withList(listWithNoDuplicates);
	}
	
	private ArrayList<T> listClone(){
		return new ArrayList<T>(list);
	}
	
	
	
	//arraylist methods
	public boolean isEmpty(){return list.isEmpty();}
	public int size(){return list.size();}
	public T get(int index){return list.get(index);}
	public T getFirst(){if (isEmpty()) return null; return get(0);}
	public T getLast(){if (isEmpty()) return null; return get(size() - 1);}
	public boolean contains(T item){return list.contains(item);}
	public int indexOf(T item){return list.indexOf(item);}
	public List<T> toList(){return listClone();}
	@SuppressWarnings("unchecked")public T[] toArray(){return (T[])(listClone().toArray());}
	
	
	
	
	//--------add
	public ImmuList<T> add(T addThis){
		ArrayList<T> listWithAddedItem = listClone();
		listWithAddedItem.add(addThis);
		
		return this.withList(listWithAddedItem);
	}
	public ImmuList<T> addAll(List<T> addThese){
		ArrayList<T> listWithAllAdded = listClone();
		listWithAllAdded.addAll(addThese);
		
		return this.withList(listWithAllAdded);
	}
	public ImmuList<T> addAll(ImmuList<T> addThese){return this.addAll(addThese.listClone());}
	
	
	
	//--------set
	public ImmuList<T> set(int index, T setToThis){
		ArrayList<T> listWithSet = listClone();
		listWithSet.set(index, setToThis);
		
		return this.withList(listWithSet);
	}
	public ImmuList<T> setLast(T setToThis){return set(size()-1, setToThis);}
	
	
	//--------remove
	public ImmuList<T> remove(int index){
		ArrayList<T> listWithRemovedIndex = listClone();
		listWithRemovedIndex.remove(index);
		
		return this.withList(listWithRemovedIndex);
	}
	public ImmuList<T> removeFirst(){if (isEmpty()) return null; return remove(0);}
	public ImmuList<T> removeLast(){if (isEmpty()) return null; return remove(size() - 1);}
	
	public ImmuList<T> removeMultiple(final List<Integer> removeIndices){
		ArrayList<T> listWithMultipleRemoved = listClone();
		
		//disallow more indices than the list has
		if (removeIndices.size() > size()) return null;
		
		List<Integer> seenSoFar = new ArrayList<Integer>();
		for (Integer i: removeIndices){
			//disallow an index outside of the list's size, disallow duplicate indices
			if (i >= size() || i < 0 || seenSoFar.contains(i)) return null;
			else seenSoFar.add(i);
		}
		
		//sort the indices in descending order
		Collections.sort(seenSoFar, Collections.reverseOrder());
		
		//remove the indices
		for (Integer i: seenSoFar)
			listWithMultipleRemoved.remove((int)i);
		
		return this.withList(listWithMultipleRemoved);
	}
	
	
	
	//--------sort
	public ImmuList<T> sort(){
		ArrayList<T> sortedList = listClone();
		Collections.sort(sortedList);
		
		return this.withList(sortedList);
	}
	
	
	
	
	
	//from fromIndex (inclusive) to toIndex (exclusive)
	public ImmuList<T> subList(int fromIndex, int toIndex){return new ImmuList<T>(list.subList(fromIndex, toIndex));}
	public ImmuList<T> getAllExceptLast(){return subList(0, size() - 1);}
	
	
	
	
	public ImmuList<T> getMultiple(List<Integer> indices){
		ArrayList<T> gets = new ArrayList<T>();
		for (Integer index: indices)
			if (index < size() && index >= 0)
				gets.add(this.get(index));
		
		return new ImmuList<T>(gets);
	}
	public ImmuList<T> getMultiple(Integer... indices){return getMultiple(Arrays.asList(indices));}
	
	
	
	
	
	
	
	

	
	
	//finds all indices where a tile occurs in the list, returns the indices as a list of integers
	public List<Integer> findAllIndicesOf(Object item, boolean allowCountingItself){
		List<Integer> indices = new ArrayList<Integer>(3);
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
	
	
	
	
	
	
	
	public String toString(){return list.toString();}
	
	@Override
	public Iterator<T> iterator(){
		return listClone().iterator();
		//return list.iterator();
	}
	
	
	public boolean equals(ImmuList<T> other){
		if (other == null) return false;
		if (this.size() != other.size()) return false;
		for (int i = 0; i < size(); i++)
			if (!this.get(i).equals(other.get(i)))
				return false;
		return true;
	}
}

