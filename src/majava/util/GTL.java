package majava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utility.ImmuList;
import majava.enums.Wind;
import majava.hand.Meld;
import majava.tiles.GameTile;


public class GTL extends ImmuList<GameTile> implements ReadOnlyTileList{
	
	
	public GTL(){super();}
	
	
	public GTL(List<GameTile> tiles){super(tiles);}
	public GTL(ImmuList<GameTile> tiles){super(tiles);}
	public GTL(GameTile... tiles){this(Arrays.asList(tiles));}
	public GTL(Integer... ids){this(idsToTiles(ids));}
	
	
	public GTL clone(){return new GTL(this);}
	
	
	//overloaded methods for tile id
	public int indexOf(Integer id){return indexOf(new GameTile(id));}
	public boolean contains(Integer id){return contains(new GameTile(id));}
	public GTL add(int id){return new GTL(add(new GameTile(id)));}
	public GTL addAll(Meld meld){return new GTL(super.addAll(meld.getTiles()));}
	
	public GTL withWind(Wind w){
		GTL windedTiles = new GTL();
		for (GameTile t: this)
			windedTiles = windedTiles.add(t.withOwnerWind(w));
		return windedTiles;
	}
	
	
	
	
	//--------mutator overrides
	public GTL add(GameTile addThis){return new GTL(super.add(addThis));}
	public GTL addAll(List<GameTile> addThese){return new GTL(super.addAll(addThese));}
	public GTL addAll(ImmuList<GameTile> addThese){return new GTL(super.addAll(addThese));}
	public GTL remove(int index){return new GTL(super.remove(index));}
	public GTL removeFirst(){return new GTL(super.removeFirst());}
	public GTL removeLast(){return new GTL(super.removeLast());}
	public GTL removeMultiple(final List<Integer> removeIndices){return new GTL(super.removeMultiple(removeIndices));}
	public GTL sort(){return new GTL(super.sort());}
	public GTL set(int index, GameTile setToThis){return new GTL(super.set(index, setToThis));}
	public GTL setLast(GameTile setToThis){return new GTL(super.setLast(setToThis));}
	
	
	//--------sublist overrides
	public GTL subList(int fromIndex, int toIndex){return new GTL(super.subList(fromIndex, toIndex));}
	public GTL getAllExceptLast(){return new GTL(super.getAllExceptLast());}
	public GTL getMultiple(Integer... indices){return new GTL(super.getMultiple(indices));}
	public GTL getMultiple(List<Integer> indices){return new GTL(super.getMultiple(indices));}
	public GTL makeCopyNoDuplicates(){return new GTL(super.makeCopyNoDuplicates());}
	
	
	
	
	
	
	
	
	private static GTL idsToTiles(Integer... ids){
		ArrayList<GameTile> tileList = new ArrayList<GameTile>();
		for (Integer i: ids)
			tileList.add(new GameTile(i));
		
		return new GTL(tileList);
	}
	
}

