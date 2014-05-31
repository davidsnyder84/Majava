package majava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.userinterface.GameUI;
import majava.summary.entity.PlayerTracker;
import majava.summary.entity.RoundEntities;
import majava.tiles.Tile;
import majava.enums.Wind;
import majava.util.TileList;





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
		__setupPlayerTrackers - sets up player trackers (track players, their hands, and their ponds)
		__syncWithWall - set up association with the wall
		__syncWithViewer - set up association with the GUI
		syncPlayer - called by a player, associates that player with the tracker
		syncHand - called by a hand, associates that hand with the tracker
		syncPond - called by a pond, associates that pond with the tracker
*/
public class RoundTracker {
	
	private static final int NUM_PLAYERS = 4;
	private static final int NUM_PLAYERS_TO_TRACK = NUM_PLAYERS;
	private static final int NUM_MELDS_TO_TRACK = 5;
	
	
	//tracks information for a player
	private final RoundEntities mRoundEntities;
	

	private final Wall mWall;	//duplicate
	private final Tile[] mTilesW;	//duplicate
	
	
	private final Player[] mPlayerArray;
	
	
	
	
	private final Wind mRoundWind;
	private final int mRoundNum;
	private final int mRoundBonusNum;
	
	private final RoundResult mRoundResult;
	
	private int mWhoseTurn;
	private Tile mMostRecentDiscard;
	
	
	
	
	
	
	
	
	
	public RoundTracker(GameUI ui, RoundResult result, Wind roundWind, int roundNum, int roundBonus, Wall wall, Player p1, Player p2, Player p3, Player p4){
		
		mRoundWind = roundWind; mRoundNum = roundNum; mRoundBonusNum = roundBonus;
		
		mWhoseTurn = 0;
		mMostRecentDiscard = null;
		
		mRoundResult = result;
		
		
		mWall = wall;
		__syncWithWall(mWall);
		mTilesW = tempSyncWallTiles;
		
		mPlayerArray = new Player[]{p1,p2,p3,p4};
//		__setupPlayerTrackers();
		mRoundEntities = new RoundEntities(this, __setupPlayerTrackers(), mWall, mTilesW);
		
		__syncWithUI(ui);
	}
	public RoundTracker(RoundResult result, Wind roundWind, int roundNum, int roundBonus, Wall wall, Player p1, Player p2, Player p3, Player p4){this(null, result, roundWind, roundNum, roundBonus, wall, p1, p2, p3, p4);}
	
	
	
	private int numPlayersSynched; private boolean wallSynched;
	private Tile[] tempSyncWallTiles = null;
	private Player tempSyncPlayer = null; private TileList tempSyncHandTiles = null; private TileList tempSyncPondTiles = null; private Hand tempSyncHand = null; private Pond tempSyncPond = null; private List<Meld> tempSyncMelds = null;
	
	private void __syncWithWall(Wall wall){
		mWall.syncWithTracker(this);
	}
	public void syncWall(Tile[] wallTiles){
		if (wallSynched) return;
		tempSyncWallTiles = wallTiles;
	}

	private PlayerTracker[] __setupPlayerTrackers(){
		if (numPlayersSynched > NUM_PLAYERS) return null;
		
		numPlayersSynched = 0;
		PlayerTracker[] trackers = new PlayerTracker[NUM_PLAYERS];
		
		for (numPlayersSynched = 0; numPlayersSynched < NUM_PLAYERS; numPlayersSynched++){
			
			tempSyncPlayer = mPlayerArray[numPlayersSynched];	//link
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
		
//		mPTrackers[numPlayersSynched].playerName = mPTrackers[numPlayersSynched].player.getPlayerName();	//NOT LINK
//		mPTrackers[numPlayersSynched].seatWind = mPTrackers[numPlayersSynched].player.getSeatWind();	//NOT LINK, but it won't change
//		mPTrackers[numPlayersSynched].points = mPTrackers[numPlayersSynched].player.getPoints();	//NOT LINK
//		mPTrackers[numPlayersSynched].riichiStatus = mPTrackers[numPlayersSynched].player.getRiichiStatus();	//NOT LINK
	}
	public void syncHand(TileList handTiles, List<Meld> handMelds){
		if (numPlayersSynched > NUM_PLAYERS) return;
		tempSyncHandTiles = handTiles;
		tempSyncMelds = handMelds;
	}
	public void syncPond(TileList pondTiles){
		if (numPlayersSynched > NUM_PLAYERS) return;
		tempSyncPondTiles = pondTiles;
	}
	
	
	private void __syncWithUI(GameUI ui){
		if (ui == null) return;
		ui.syncWithRoundTracker(mRoundEntities);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void nextTurn(){mWhoseTurn = (mWhoseTurn + 1) % 4;}
	public void setTurn(int turn){if (turn < NUM_PLAYERS) mWhoseTurn = turn;}
	public void setTurn(Player p){setTurn(p.getPlayerNumber());}	//overloaded to accept a player
	
	public int whoseTurn(){return mWhoseTurn;}
	
	public Player currentPlayer(){return mPlayerArray[mWhoseTurn];}
	
	
	
	
	
	
	private Player __neighborOffsetOf(Player p, int offset){
		return mPlayerArray[(p.getPlayerNumber() + offset) % NUM_PLAYERS];
	}
	public Player neighborShimochaOf(Player p){return __neighborOffsetOf(p, 1);}
	public Player neighborToimenOf(Player p){return __neighborOffsetOf(p, 2);}
	public Player neighborKamichaOf(Player p){return __neighborOffsetOf(p, 3);}
	public Player neighborNextPlayer(Player p){return neighborShimochaOf(p);}
	
	
	
	public boolean callWasMadeOnDiscard(){
		for (int i = 1; i < 4; i++) if (mPlayerArray[(mWhoseTurn + i) % NUM_PLAYERS].called()) return true;
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void setResultVictory(Player winner){
		
		Tile winningTile = null;
		
		if (winner == currentPlayer()){
			mRoundResult.setVictoryTsumo(winner);
			winningTile = winner.getTsumoTile();
		}
		else{ 
			mRoundResult.setVictoryRon(winner, currentPlayer());
			winningTile = mMostRecentDiscard;
		}
		
		mRoundResult.setWinningHand(mRoundEntities.mPTrackers[winner.getPlayerNumber()].tilesH, mRoundEntities.mPTrackers[winner.getPlayerNumber()].melds, winningTile);
	}
	public void setResultRyuukyokuWashout(){mRoundResult.setResultRyuukyokuWashout();}
	public void setResultRyuukyokuKyuushu(){mRoundResult.setResultRyuukyokuKyuushu();}
	public void setResultRyuukyoku4Kan(){mRoundResult.setResultRyuukyoku4Kan();}
	public void setResultRyuukyoku4Riichi(){mRoundResult.setResultRyuukyoku4Riichi();}
	public void setResultRyuukyoku4Wind(){mRoundResult.setResultRyuukyoku4Wind();}
	
	
	
//	public Player getWinningPlayer(){return mRoundResult.getWinningPlayer();}
//	public Player getFurikondaPlayer(){return mRoundResult.getFurikondaPlayer();}
	
	public void printRoundResult(){
		if (roundIsOver())
			 System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
			 					"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~Round over!~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
					 			"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		
		 String resultStr = "Result: " + getRoundResultString();
		 System.out.println(resultStr);
		 
		 if (mRoundResult.isVictory()) System.out.println(mRoundResult.getAsStringWinningHand());
	}
	//returns the round result as a string
	public String getRoundResultString(){return mRoundResult.toString();}
	
	
	public boolean roundIsOver(){return mRoundResult.isOver();}
//	public boolean roundEndedWithDraw(){return mRoundResult.isDraw();}
//	public boolean roundEndedWithVictory(){return mRoundResult.isVictory();}
	public boolean roundEndedWithDealerVictory(){return mRoundResult.isDealerVictory();}
	
	public boolean qualifiesForRenchan(){return roundEndedWithDealerVictory();}	//or if the dealer is in tenpai, or a certain ryuukyoku happens
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns true if multiple players have made kans, returns false if only one player or no players have made kans
	private boolean __multiplePlayersHaveMadeKans(){
		//count the number of players who have made kans
		int count = 0;
		for (Player p: mPlayerArray){
			if (p.hasMadeAKan())
				count++;
		}
		return (count > 1);
	}
	//returns true if a round-ending number of kans have been made
	//returns true if 5 kans have been made, or if 4 kans have been made by multiple players
	private boolean __tooManyKans(){
		
		final int KAN_LIMIT = 4;
		if (getNumKansMade() < KAN_LIMIT) return false;
		if (getNumKansMade() == KAN_LIMIT && !__multiplePlayersHaveMadeKans()) return false;
		
		return true;
	}
	
	
	//returns the number of kans made on the table
	public int getNumKansMade(){
		int count = 0;
		for (Player p: mPlayerArray) count += p.getNumKansMade();
		return count;
	}
	
	
	
	
	
	
	//checks if too many kans have been made, and sets the round result if so
	public boolean checkIfTooManyKans(){
		if (__tooManyKans()){
			setResultRyuukyoku4Kan();
			return true;
		}
		return false;
	}
	
	
	
	
	//checks if the wall is empty, and sets the round result if so	
	public boolean checkIfWallIsEmpty(){
		if (mWall.isEmpty()){
			setResultRyuukyokuWashout();
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	public void setMostRecentDiscard(Tile discard){mMostRecentDiscard = discard;}
	public Tile getMostRecentDiscard(){return mMostRecentDiscard;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Wind getRoundWind(){return mRoundWind;}
	public int getRoundNum(){return mRoundNum;}
	public int getRoundBonusNum(){return mRoundBonusNum;}
	
	
	
	public Wind getWindOfSeat(int seat){
		if (seat < 0 || seat >= NUM_PLAYERS) return null;
		return mPlayerArray[seat].getSeatWind();
	}
	
	
	
	
	
	public int getNumTilesLeftInWall(){return mWall.getNumTilesLeftInWall();}
	
	
	
	
	
	
	
	
	
	
	
	
}
