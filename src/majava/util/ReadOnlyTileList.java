package majava.util;

import majava.tiles.GameTile;

public interface ReadOnlyTileList{
	
	public GameTile get(int index);
	public GameTile getFirst();
	public GameTile getLast();
	
	public boolean contains(GameTile t);
	public boolean isEmpty();
	public int size();
	public int indexOf(GameTile t);
	
	public ReadOnlyTileList subList(int fromIndex, int toIndex);
	public ReadOnlyTileList getAllExceptLast();
	
	
	
	//tile id overloads
	public int indexOf(Integer id);
	public boolean contains(Integer id);
}
