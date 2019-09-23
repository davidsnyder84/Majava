package majava.util;

import java.util.Arrays;
import java.util.List;

import utility.ImmuList;
import majava.Pond;
import majava.enums.Wind;
import majava.player.Player;
import static majava.player.Player.NOBODY;


public class PlayerList extends ImmuList<Player>{
	private static final int NUM_PLAYERS = 4;
	
	
	
	
	public PlayerList(){super();}
	
	public PlayerList(List<Player> players){super(players);}
	public PlayerList(ImmuList<Player> players){super(players);}
	public PlayerList(Player... players){this(Arrays.asList(players));}
	
	
	public PlayerList clone(){return new PlayerList(this);}
	
	
	//set
	public PlayerList set(Player newPlayer){
		return this.set(indexOfPlayer(newPlayer), newPlayer);
	}
	public PlayerList updatePlayer(Player newPlayer){return set(newPlayer);}
	
	
	
	
	
	
	//getters are based on seatwind. seatwind uniquely and consistently idenfifies a player .
	public Player get(Wind seat){
		if (indexOfPlayer(seat) == -1)
			this.toString();
		return get(indexOfPlayer(seat));
	}
	public Player seat(Wind wind){return get(wind);}
	public Player seatE(){return seat(Wind.EAST);}
	public Player seatS(){return seat(Wind.SOUTH);}
	public Player seatW(){return seat(Wind.WEST);}
	public Player seatN(){return seat(Wind.NORTH);}
	//i don't think anyone needs these
//	public Player neighborNextPlayer(Player p){return neighborShimochaOf(p);}
//	public Player neighborShimochaOf(Player p){return get(p.getSeatWind().shimochaWind());}
//	public Player neighborToimenOf(Player p){return get(p.getSeatWind().toimenWind());}
//	public Player neighborKamichaOf(Player p){return get(p.getSeatWind().kamichaWind());}
	
	private int indexOfPlayer(Wind seat){
		for (int i=0; i<size(); i++)
			if (get(i).getSeatWind() == seat)
				return i;
		return -1; //i want to see this fail fast
	}
	private int indexOfPlayer(Player p){return indexOfPlayer(p.getSeatWind());}
	
	
	
	
	
	public boolean someoneCalled(){
		for (Player p: this) if (p.called()) return true;
		return false;
	}
	
	public boolean someoneCalledChi(){return (callerChi() != NOBODY);}
	public boolean someoneCalledPonKan(){return (callerPonKan() != NOBODY);}
	public boolean someoneCalledRON(){return (callerRON() != NOBODY);}
	
	public Player callerChi(){
		for (Player p : this) if (p.calledChi()) return p;
		return NOBODY;
	}
	public Player callerPonKan(){
		for (Player p : this) if (p.calledPon() || p.calledKan()) return p;
		return NOBODY;
	}
	//----NOTE: this doesn't handle atamahane. need to take into account the seatwind of the discarder to decide who gets ron priority
	public Player callerRON(){
		for (Player p : this) if (p.calledRon()) return p;
		return NOBODY;
	}
	
	
	
	
	public Player playerWhoHasFullHand(){
		for (Player p: this) if (p.handIsFull()) return p;
		return NOBODY;
	}
	public boolean someoneHasFullHand(){return playerWhoHasFullHand() != NOBODY;}
	
	
	
	
	public Pond[] getPonds(){return new Pond[]{seatE().getPond(), seatS().getPond(), seatW().getPond(), seatN().getPond()};}
	
	
	
	
	
	
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

