package majava.util;

import java.util.Arrays;
import java.util.List;

import utility.ImmuList;
import majava.hand.Meld;


public class MeldList extends ImmuList<Meld>{
	
	
	public MeldList(){super();}
	
	
	public MeldList(List<Meld> melds){super(melds);}
	public MeldList(ImmuList<Meld> melds){super(melds);}
	public MeldList(Meld... melds){this(Arrays.asList(melds));}
	
	
	public MeldList clone(){return new MeldList(this);}
	
	
	
	
	//--------mutator overrides
	public MeldList add(Meld addThis){return new MeldList(super.add(addThis));}
	public MeldList addAll(List<Meld> addThese){return new MeldList(super.addAll(addThese));}
	public MeldList addAll(ImmuList<Meld> addThese){return new MeldList(super.addAll(addThese));}
	public MeldList remove(int index){return new MeldList(super.remove(index));}
	public MeldList removeFirst(){return new MeldList(super.removeFirst());}
	public MeldList removeLast(){return new MeldList(super.removeLast());}
	public MeldList removeMultiple(final List<Integer> removeIndices){return new MeldList(super.removeMultiple(removeIndices));}
	public MeldList sort(){return new MeldList(super.sort());}
	public MeldList set(int index, Meld setToThis){return new MeldList(super.set(index, setToThis));}
	public MeldList setLast(Meld setToThis){return new MeldList(super.setLast(setToThis));}
	
	
	//--------sublist overrides
	public MeldList subList(int fromIndex, int toIndex){return new MeldList(super.subList(fromIndex, toIndex));}
	public MeldList getAllExceptLast(){return new MeldList(super.getAllExceptLast());}
	public MeldList getMultiple(Integer... indices){return new MeldList(super.getMultiple(indices));}
	public MeldList getMultiple(List<Integer> indices){return new MeldList(super.getMultiple(indices));}
	public MeldList makeCopyNoDuplicates(){return new MeldList(super.makeCopyNoDuplicates());}
}

