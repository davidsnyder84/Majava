package majava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.userinterface.GameUI;
import majava.util.GameTileList;
import majava.player.Player;
import majava.summary.RoundResultSummary;
import majava.summary.entity.PlayerTracker;
import majava.summary.entity.RoundEntities;
import majava.tiles.GameTile;
import majava.tiles.PondTile;
import majava.enums.Wind;
import majava.hand.Hand;
import majava.hand.Meld;


//other objects can ask a RoundTracker for information about the round
public class RoundTracker {
	private static final int NUM_PLAYERS = 4;
	private static final int NUM_MELDS_TO_TRACK = 5;
	
	
	//tracks information for a player
	private final RoundEntities roundEntities;
	private final Wall wall;	//duplicate
	private final GameTile[] wallTiles;	//duplicate
	
	private final Player[] players;
	
	
	private final Wind roundWind;
	private final int roundNumber;
	private final int roundBonusNumber;
	
	private final RoundResult roundResult;
	
	private int indexOfWhoseTurn;
	private GameTile mostRecentDiscard;
	
	
	public RoundTracker(GameUI ui, RoundResult result, Wind windOfRound, int roundNum, int roundBonusNum, Wall receivedWall, Player[] playerArray){
		roundWind = windOfRound; roundNumber = roundNum; roundBonusNumber = roundBonusNum;
		
		roundResult = result;
		
		wall = receivedWall;
		wall.syncWithTracker(this);
		wallTiles = tempSyncWallTiles;
		
		players = playerArray.clone();
		roundEntities = new RoundEntities(this, __setupPlayerTrackers(), wall, wallTiles);
		tempSyncWallTiles = null;
		
		__syncWithUI(ui);
		
		//the dealer starts first
		indexOfWhoseTurn = getDealerSeatNum();
		mostRecentDiscard = null;
	}
	public RoundTracker(RoundResult result, Wind windOfRound, int roundNum, int roundBonusNum, Wall receivedWall, Player[] playerArray){this(null, result, windOfRound, roundNum, roundBonusNum, receivedWall, playerArray);}
	
	
	private int numPlayersSynched; private boolean wallSynched;
	private GameTile[] tempSyncWallTiles = null;
	private Player tempSyncPlayer = null; private GameTileList tempSyncHandTiles = null; private List<PondTile> tempSyncPondTiles = null; private Hand tempSyncHand = null; private Pond tempSyncPond = null; private List<Meld> tempSyncMelds = null;
	
	public void syncWall(GameTile[] tilesOfWall){
		if (wallSynched) return;
		tempSyncWallTiles = tilesOfWall;
	}

	private PlayerTracker[] __setupPlayerTrackers(){
		if (numPlayersSynched > NUM_PLAYERS) return null;
		
		numPlayersSynched = 0;
		PlayerTracker[] trackers = new PlayerTracker[NUM_PLAYERS];
		
		for (numPlayersSynched = 0; numPlayersSynched < NUM_PLAYERS; numPlayersSynched++){
			
			tempSyncPlayer = players[numPlayersSynched];	//link
			tempSyncPlayer.syncWithRoundTracker(this);
			tempSyncHand.syncWithRoundTracker(this);
			tempSyncPond.syncWithRoundTracker(this);
			
			trackers[numPlayersSynched] = new PlayerTracker(tempSyncPlayer, tempSyncHand, tempSyncHandTiles, tempSyncPond, tempSyncPondTiles, tempSyncMelds);
		}
		tempSyncPlayer = null;tempSyncHandTiles = null;tempSyncPondTiles = null;tempSyncHand = null;tempSyncPond = null;tempSyncMelds = null;
		return trackers;
	}
	
	public void syncPlayer(Hand h, Pond p){
		if (numPlayersSynched > NUM_PLAYERS) return;
		tempSyncHand = h;
		tempSyncPond = p;
	}
	public void syncHand(GameTileList handTiles, List<Meld> handMelds){
		if (numPlayersSynched > NUM_PLAYERS) return;
		tempSyncHandTiles = handTiles;
		tempSyncMelds = handMelds;
	}
	public void syncPond(List<PondTile> pondTiles){
		if (numPlayersSynched > NUM_PLAYERS) return;
		tempSyncPondTiles = pondTiles;
	}
	
	private void __syncWithUI(GameUI ui){
		if (ui == null) return;
		ui.syncWithRoundTracker(roundEntities);
	}
	
	
	
	
	
	
	
	
	public void nextTurn(){indexOfWhoseTurn = (indexOfWhoseTurn + 1) % NUM_PLAYERS;}
	public void setTurn(int turn){if (turn < NUM_PLAYERS) indexOfWhoseTurn = turn;}
	public void setTurn(Player p){setTurn(p.getPlayerNumber());}	//overloaded to accept a player
	
	public int whoseTurn(){return indexOfWhoseTurn;}
	
	public int getDealerSeatNum(){
		for (int i = 0; i < NUM_PLAYERS; i++) if (players[i].isDealer()) return i;
		return -1;
	}
	
	public Player currentPlayer(){return players[indexOfWhoseTurn];}
	
	
	private Player neighborOffsetOf(Player p, int offset){
		return players[(p.getPlayerNumber() + offset) % NUM_PLAYERS];
	}
	public Player neighborNextPlayer(Player p){return neighborShimochaOf(p);}
	public Player neighborShimochaOf(Player p){return neighborOffsetOf(p, 1);}
	public Player neighborToimenOf(Player p){return neighborOffsetOf(p, 2);}
	public Player neighborKamichaOf(Player p){return neighborOffsetOf(p, 3);}
	
	
	public boolean callWasMadeOnDiscard(){
		for (int i = 1; i < NUM_PLAYERS; i++) if (players[(indexOfWhoseTurn + i) % NUM_PLAYERS].called()) return true;
		return false;
	}
	
	public void setMostRecentDiscard(GameTile discard){mostRecentDiscard = discard;}
	public GameTile getMostRecentDiscard(){return mostRecentDiscard;}
	
	
	

	public void setResultVictory(Player winner){
		GameTile winningTile = null;
//		GameTileList winningHandTiles = new GameTileList(mRoundEntities.mPTrackers[winner.getPlayerNumber()].tilesH);
		GameTileList winningHandTiles = roundEntities.playerTrackers[winner.getPlayerNumber()].handTiles.clone();
		
		if (winner == currentPlayer()){
			roundResult.setVictoryTsumo(winner);
			
			winningTile = winner.getTsumoTile();
			winningHandTiles.removeLast();
		}
		else{ 
			roundResult.setVictoryRon(winner, currentPlayer());
			
			winningTile = mostRecentDiscard;
		}
		
		roundResult.setWinningHand(winningHandTiles, roundEntities.playerTrackers[winner.getPlayerNumber()].melds, winningTile);
	}
	public void setResultRyuukyokuWashout(){roundResult.setResultRyuukyokuWashout();}
	
//	public Player getWinningPlayer(){return mRoundResult.getWinningPlayer();}
//	public Player getFurikondaPlayer(){return mRoundResult.getFurikondaPlayer();}
	
	public RoundResultSummary getResultSummary(){return roundResult.getSummary();}
	
	public String getRoundResultString(){return roundResult.toString();}
	
	public boolean roundIsOver(){return roundResult.isOver();}
//	public boolean roundEndedWithDraw(){return mRoundResult.isDraw();}
//	public boolean roundEndedWithVictory(){return mRoundResult.isVictory();}
	public boolean roundEndedWithDealerVictory(){return roundResult.isDealerVictory();}
	
	public boolean qualifiesForRenchan(){return roundEndedWithDealerVictory();}	//or if the dealer is in tenpai, or a certain ryuukyoku happens
	
	
	
	
	
	
	
	
	
	
	//returns true if multiple players have made kans, returns false if only one player or no players have made kans
	private boolean multiplePlayersHaveMadeKans(){
		//count the number of players who have made kans
		int count = 0;
		for (Player p: players){
			if (p.hasMadeAKan())
				count++;
		}
		return (count > 1);
	}
	//returns true if a round-ending number of kans have been made
	//returns true if 5 kans have been made, or if 4 kans have been made by multiple players
	public boolean tooManyKans(){
		final int KAN_LIMIT = 4;
		if (getNumKansMade() < KAN_LIMIT) return false;
		if (getNumKansMade() == KAN_LIMIT && !multiplePlayersHaveMadeKans()) return false;		
		return true;
	}
	
	//returns the number of kans made on the table
	public int getNumKansMade(){
		int count = 0;
		for (Player p: players) count += p.getNumKansMade();
		return count;
	}
	
	
	
	
	
	//checks if the wall is empty, and sets the round result if so	
	public boolean checkIfWallIsEmpty(){
		if (wall.isEmpty()){
			setResultRyuukyokuWashout();
			return true;
		}
		return false;
	}
	
	public int getNumTilesLeftInWall(){return wall.numTilesLeftInWall();}
	
	
	public Wind getRoundWind(){return roundWind;}
	public int getRoundNum(){return roundNumber;}
	public int getRoundBonusNum(){return roundBonusNumber;}
	
	
	public Wind getWindOfSeat(int seat){
		if (seat < 0 || seat >= NUM_PLAYERS) return null;
		return players[seat].getSeatWind();
	}
	
}


/*
Class: RoundTracker

data:
	mRoundWind - the round wind
	mRoundNum - the round number
	mRoundBonusNum - the round bonus number
	mRoundResult - the result of the round
	
	mPTrackers - array of tracked info for each player
	mWhoseTurn - indicates whose turn it is (number 0..3)
	mMostRecentDiscard - the most recently discarded tile
	
	checks:
		numPlayersSynched - used to check how many players are synched
		wallSynched - used to check if the wall is synched
	
methods:
	constructors:
	requires: (TableViewer GUI, round wind, round num, round bonus num, a wall, and four players)
	
	
	public:
	
		mutators:
		nextTurn - advances the turn to the next player
		setTurn - advances the turn to the given player
		setMostRecentDiscard - sets the most recently discarded tile to the given tile
		
		setResultVictory - sets the round result to victory for the given player
		setResultRyuukyoku.., etc - sets the round result a certain type of Ryuukyoku (draw)
		
		checkIfTooManyKans - checks if too many kans have been made, and sets the round to over if so
		checkIfWallIsEmpty - checks if the wall is empty, and sets the round to over if so
		
		
	 	accessors:
	 	getRoundWind, getRoundNum, getRoundBonusNum - return the corresponding round info
	 	
	 	currentPlayer - returns the Player whose turn it is (reference to mutable object)
	 	neighborShimochaOf, etc - returns the corresponding neighbor Player of the given player (reference to mutable object)
	 	getSeatNumber - returns the seat number of the given player
	 	
	 	getMostRecentDiscard - returns the most recently discarded tile
	 	callWasMadeOnDiscard - returns true if any player made a call on the most recent discard
	 	
	 	getNumKansMade - returns the number of kans made in the round
	 	getNumTilesLeftInWall - returns the number of tiles left in the wall
	 	
	 	roundIsOver - returns true if the round has ended
	 	roundEndedWithDraw, roundEndedWithVictory - returns true if the round ended with the corresponding result
	 	printRoundResult - prints the round result
	 	getRoundResultString - returns the round result as a string
	 	getWinningHandString - returns a string representation of the winner's winning hand
	 	
 	
	
	setup:
		syncWall
		setupPlayerTrackers - sets up player trackers (track players, their hands, and their ponds)
		syncWithWall - set up association with the wall
		syncWithViewer - set up association with the GUI
		
		syncPlayer - called by a player, associates that player with the tracker
		syncHand - called by a hand, associates that hand with the tracker
		syncPond - called by a pond, associates that pond with the tracker
*/

