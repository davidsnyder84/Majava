package majava;

import majava.player.Player;
import majava.tiles.GameTile;


//a class for keeping track of which player's turn it is
public class TurnIndicator {
	private static final int NUM_PLAYERS = 4;
	
	private final Player[] players;
	private int indexOfWhoseTurn;
	
	private GameTile mostRecentDiscard;
	private Player priorityCaller;
	
	public TurnIndicator(Player[] ps) {
		players = ps;
		
		indexOfWhoseTurn = getDealerSeatNum();
		mostRecentDiscard = null;
		priorityCaller = null;
	}
	
	
	public void nextTurn(){setTurn((indexOfWhoseTurn + 1) % NUM_PLAYERS);}
	public void setTurn(int turn){if (turn < NUM_PLAYERS) indexOfWhoseTurn = turn;}
	public void setTurn(Player p){setTurn(p.getPlayerNumber());}	//overloaded to accept a player
	public Player currentPlayer(){return players[indexOfWhoseTurn];}
	
	public int whoseTurnNumber(){return indexOfWhoseTurn;}
	private int getDealerSeatNum(){
		for (int i = 0; i < NUM_PLAYERS; i++) if (players[i].isDealer()) return i;
		return -1;
	}
	
	public void setMostRecentDiscard(GameTile discard){mostRecentDiscard = discard;}
	public GameTile getMostRecentDiscard(){return mostRecentDiscard;}
	
	public void setTurnToPriorityCaller(){setTurn(priorityCaller);}
	public void setPriorityCaller(Player caller){priorityCaller = caller;}
	public Player getPriorityCaller(){return priorityCaller;}
	
	
	public boolean callWasMadeOnDiscard(){
		for (int i = 1; i < NUM_PLAYERS; i++) if (players[(indexOfWhoseTurn + i) % NUM_PLAYERS].called()) return true;
		return false;
	}
	
//	private Player neighborOffsetOf(Player p, int offset){
//		return players[(p.getPlayerNumber() + offset) % NUM_PLAYERS];
//	}
//	public Player neighborNextPlayer(Player p){return neighborShimochaOf(p);}
//	public Player neighborShimochaOf(Player p){return neighborOffsetOf(p, 1);}
//	public Player neighborToimenOf(Player p){return neighborOffsetOf(p, 2);}
//	public Player neighborKamichaOf(Player p){return neighborOffsetOf(p, 3);}
	
}
