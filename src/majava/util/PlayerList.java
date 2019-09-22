package majava.util;

import java.util.Arrays;
import java.util.List;

import utility.ImmuList;
import majava.player.Player;


public class PlayerList extends ImmuList<Player>{
	public final static int EAST=0, SOUTH=1, WEST=2, NORTH=3;
	
	
	public PlayerList(){super();}
	
	
	public PlayerList(List<Player> players){super(players);}
	public PlayerList(ImmuList<Player> players){super(players);}
	public PlayerList(Player... players){this(Arrays.asList(players));}
	
	
	public PlayerList clone(){return new PlayerList(this);}
	
	
	public Player eastSeat(){return get(EAST);}
	public Player southSeat(){return get(SOUTH);}
	public Player westSeat(){return get(WEST);}
	public Player northSeat(){return get(NORTH);}
	
	public Player seatE(){return get(EAST);}
	public Player seatS(){return get(SOUTH);}
	public Player seatW(){return get(WEST);}
	public Player seatN(){return get(NORTH);}
	
	
	//--------mutator overrides
	public PlayerList add(Player addThis){return new PlayerList(super.add(addThis));}
	public PlayerList addAll(List<Player> addThese){return new PlayerList(super.addAll(addThese));}
	public PlayerList addAll(ImmuList<Player> addThese){return new PlayerList(super.addAll(addThese));}
	public PlayerList set(int index, Player setToThis){return new PlayerList(super.set(index, setToThis));}
	public PlayerList setLast(Player setToThis){return new PlayerList(super.setLast(setToThis));}
	
	
	//--------sublist overrides
	public PlayerList subList(int fromIndex, int toIndex){return new PlayerList(super.subList(fromIndex, toIndex));}
	public PlayerList getAllExceptLast(){return new PlayerList(super.getAllExceptLast());}
	public PlayerList getMultiple(Integer... indices){return new PlayerList(super.getMultiple(indices));}
	public PlayerList getMultiple(List<Integer> indices){return new PlayerList(super.getMultiple(indices));}
	public PlayerList makeCopyNoDuplicates(){return new PlayerList(super.makeCopyNoDuplicates());}
}

