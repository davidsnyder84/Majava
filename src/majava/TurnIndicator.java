package majava;

import majava.player.Player;
import majava.tiles.GameTile;


//a class for keeping track of whose turn it is
public class TurnIndicator {
	private static final int NUM_PLAYERS = 4;
	
	private final Player[] players;
	private int indexOfWhoseTurn;
	private GameTile mostRecentDiscard;
	
	public TurnIndicator(Player[] ps) {
		players = ps;
		indexOfWhoseTurn = -1;
		mostRecentDiscard = new GameTile(0);
	}
	
	
	public void nextTurn(){setTurn((indexOfWhoseTurn + 1) % NUM_PLAYERS);}
	public void setTurn(int turn){if (turn < NUM_PLAYERS) indexOfWhoseTurn = turn;}
	public void setTurn(Player p){setTurn(p.getPlayerNumber());}	//overloaded to accept a player
	public Player currentPlayer(){return players[indexOfWhoseTurn];}
	
	
	public void setMostRecentDiscard(GameTile discard){mostRecentDiscard = discard;}
	public GameTile getMostRecentDiscard(){return mostRecentDiscard;}
	

	
//	private Player neighborOffsetOf(Player p, int offset){
//		return players[(p.getPlayerNumber() + offset) % NUM_PLAYERS];
//	}
//	public Player neighborNextPlayer(Player p){return neighborShimochaOf(p);}
//	public Player neighborShimochaOf(Player p){return neighborOffsetOf(p, 1);}
//	public Player neighborToimenOf(Player p){return neighborOffsetOf(p, 2);}
//	public Player neighborKamichaOf(Player p){return neighborOffsetOf(p, 3);}
	
}
