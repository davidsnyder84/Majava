package majava.round;


import majava.player.Player;
import majava.tiles.GameTile;
import majava.util.PlayerList;


//a class for keeping track of which player's turn it is
public class TurnArrow {
	private static final int NUM_PLAYERS = 4;
	
	private final PlayerList players;
	private final Player turnPlayer;
	
	private final GameTile mostRecentDiscard;
	private final Player priorityCaller;
	
//	public TurnArrow(PlayerList ps, Player playerWhoseTurn, int whoIndex, GameTile discardRecent, Player callerPriority){
	public TurnArrow(PlayerList ps, Player playerWhoseTurn, GameTile discardRecent, Player callerPriority){
		players = ps;
		turnPlayer = playerWhoseTurn;
		mostRecentDiscard = discardRecent;
		priorityCaller = callerPriority;
	}
	public TurnArrow(PlayerList ps){this(ps, ps.seatE(), null, null);}
//	public TurnArrow goToNextPlayer(){return new TurnArrow(pl, playerWhoseTurn, whoIndex, mostRecentDiscard, priorityCaller);}
	private TurnArrow withTurnPlayer(Player turner){return new TurnArrow(players, turner, mostRecentDiscard, priorityCaller);}
	private TurnArrow withPriorityCaller(Player caller){return new TurnArrow(players, turnPlayer, mostRecentDiscard, caller);}
	private TurnArrow withLastDiscard(GameTile discard){return new TurnArrow(players, turnPlayer, discard, priorityCaller);}
	
	
	public Player currentPlayer(){return turnPlayer;}
	public Player nextPlayer(){return players.neighborNextPlayer(currentPlayer());}
	
	
	
//	public int whoseTurnNumber(){return indexOfWhoseTurn;}
//	private int getDealerSeatNum(){
//		for (int i = 0; i < NUM_PLAYERS; i++) if (players[i].isDealer()) return i;
//		return -1;
//	}
	
	public GameTile getMostRecentDiscard(){return mostRecentDiscard;}
	public Player getPriorityCaller(){return priorityCaller;}
	
	public boolean callWasMadeOnDiscard(){return players.someoneCalled();}
	
	
	
	
	public TurnArrow setTurnNext(){return setTurn(players.neighborNextPlayer(turnPlayer));}
	public TurnArrow setTurn(Player p){
		return this.withTurnPlayer(p);
	}
	public TurnArrow setTurnToPriorityCaller(){return this.setTurn(priorityCaller);}
	
	public TurnArrow setMostRecentDiscard(GameTile discard){return this.withLastDiscard(discard);}
	public TurnArrow setPriorityCaller(Player caller){return this.withPriorityCaller(caller);}
	
	
	
	
	
//	private Player neighborOffsetOf(Player p, int offset){
//		return players[(p.getPlayerNumber() + offset) % NUM_PLAYERS];
//	}
//	public Player neighborNextPlayer(Player p){return neighborShimochaOf(p);}
//	public Player neighborShimochaOf(Player p){return neighborOffsetOf(p, 1);}
//	public Player neighborToimenOf(Player p){return neighborOffsetOf(p, 2);}
//	public Player neighborKamichaOf(Player p){return neighborOffsetOf(p, 3);}
	
}
