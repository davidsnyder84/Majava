package majava.util;

import java.util.Arrays;
import java.util.List;

import utility.ImmuList;
import majava.Pond;
import majava.enums.Wind;
import majava.player.Player;
import majava.tiles.GameTile;
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
	
	public PlayerList rotateSeats(Player newPlayer){
		PlayerList rotatedPlayers = this;
		for (Player p : rotatedPlayers)
			rotatedPlayers = rotatedPlayers.set(p.rotateSeat());
		return rotatedPlayers;
	}
	
	
	
	
	
	
	
	//getters are based on seatwind. seatwind uniquely and consistently idenfifies a player .
	public Player get(Wind seat){
		return get(indexOfPlayer(seat));
	}
	public Player seat(Wind wind){return get(wind);}
	public Player seatE(){return seat(Wind.EAST);}
	public Player seatS(){return seat(Wind.SOUTH);}
	public Player seatW(){return seat(Wind.WEST);}
	public Player seatN(){return seat(Wind.NORTH);}
	public Player neighborNextPlayer(Player p){return neighborShimochaOf(p);}
	public Player neighborShimochaOf(Player p){return get(p.getSeatWind().shimochaWind());}
	public Player neighborToimenOf(Player p){return get(p.getSeatWind().toimenWind());}
	public Player neighborKamichaOf(Player p){return get(p.getSeatWind().kamichaWind());}
	
	public int indexOfPlayer(Wind seat){
		for (int i=0; i<size(); i++)
			if (get(i).getSeatWind() == seat)
				return i;
		
//		return -88; //i want to see this fail fast (figure out a way to avoid this later)
		return 0; ///////////////
	}
	public int indexOfPlayer(Player p){return indexOfPlayer(p.getSeatWind());}
	
	public PlayerList allPlayersExcept(Player p){
		return new PlayerList(neighborShimochaOf(p), neighborToimenOf(p), neighborKamichaOf(p));
	}
	
	
	
	
	public boolean someoneCalled(GameTile t){
		for (Player p: this)
			if (p.called(t))
				return true;
		return false;
	}
	
	public boolean someoneCalledChi(GameTile t){return (callerChi(t) != NOBODY);}
	public boolean someoneCalledPonKan(GameTile t){return (callerPonKan(t) != NOBODY);}
	public boolean someoneCalledRON(GameTile t){return (callerRON(t) != NOBODY);}
	
	public Player callerChi(GameTile t){
		for (Player p : this) if (p.calledChi(t)) return p;
		return NOBODY;
	}
	public Player callerPonKan(GameTile t){
		for (Player p : this) if (p.calledPon(t) || p.calledKan(t)) return p;
		return NOBODY;
	}
	//----NOTE: this doesn't handle atamahane. need to take into account the seatwind of the discarder to decide who gets ron priority
	public Player callerRON(GameTile t){
		for (Player p : this) if (p.calledRon(t)) return p;
		return NOBODY;
	}
	
	
	
	
	
	public Player playerWhoHasFullHand(){
		for (Player p: this) if (p.handIsFull()) return p;
		return NOBODY;
	}
	public boolean someoneHasFullHand(){return playerWhoHasFullHand() != NOBODY;}
	
	
	
	
	public Pond[] getPonds(){return new Pond[]{seatE().getPond(), seatS().getPond(), seatW().getPond(), seatN().getPond()};}
	
	
	
	public String toString(){
		String str = "";
		
		for (int i=0; i<NUM_PLAYERS; i++)
			str += get(i).getSeatWind().toChar() + "->";
		
		str += "   /   ";
		Wind windstepper = Wind.EAST;
		for (int i=0; i<NUM_PLAYERS; i++){
			str += "g" + windstepper.toChar() + ":"+ get(windstepper).getSeatWind().toChar() + ",  ";
			windstepper = windstepper.next();
		}
		
		return str;
	}
	
	
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

