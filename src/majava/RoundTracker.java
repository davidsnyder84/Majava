package majava;

import java.util.ArrayList;

import majava.graphics.TableViewer;
import majava.tiles.Tile;





/*
Class: RoundTracker

data:
	
	mGameType - length of game being played (single, tonpuusen, or hanchan)
	mRoundWind - the prevailing wind of the current round ('E' or 'S')
	mGameIsOver - will be true if the game is over, false if not
	mGameResult - the specific result of the game (reason for a draw game, or who won), is UNDECIDED if game is not over
	
methods:
	mutators:
	
 	accessors:
	
	other:
*/
public class RoundTracker {
	
	private static final int NUM_PLAYERS = 4;
	private static final int NUM_PLAYERS_TO_TRACK = NUM_PLAYERS;
	private static final int NUM_MELDS_TO_TRACK = 5;
	
	
	public enum Result{
		UNDECIDED,
		DRAW_WASHOUT, DRAW_KYUUSHU, DRAW_4KAN, DRAW_4RIICHI, DRAW_4WIND,
		VICTORY_E, VICTORY_S, VICTORY_W, VICTORY_N;
		
		public boolean isDraw(){return (this == DRAW_WASHOUT || this == DRAW_KYUUSHU || this == DRAW_4KAN || this == DRAW_4RIICHI || this == DRAW_4WIND);}
		public boolean isVictory(){return (this == VICTORY_E || this == VICTORY_S || this == VICTORY_W || this == VICTORY_N);}
		
		@Override
		public String toString(){
			switch (this){
			case DRAW_WASHOUT: return "Draw (Washout)";
			case DRAW_KYUUSHU: return "Draw (Kyuushu)";
			case DRAW_4KAN: return "Draw (4 kans)";
			case DRAW_4RIICHI: return "Draw (4 riichi)";
			case DRAW_4WIND: return "Draw (4 wind)";
			
			case VICTORY_E: return "E wins!";
			case VICTORY_S: return "S wins!";
			case VICTORY_W: return "W wins!";
			case VICTORY_N: return "N wins!";
			default: return "??Undecided??";
			}
		}
	}
	public enum WinType{
		UNDECIDED, TSUMO, RON;
		
		@Override
		public String toString(){
			switch (this){
			case TSUMO: return "TSUMO";
			case RON: return "RON";
			default: return "undecided";
			}
		}
	}
	
	
	
	private class PlayerTracker{
		private Player player;
		
		private char seatWind;
		private int points;
		private boolean riichiStatus;
		private String playerName;
		
		private Hand hand;
		private Pond pond;
		
		private TileList tilesH;
		private TileList tilesP;
		
		private ArrayList<Meld> melds = new ArrayList<Meld>(NUM_MELDS_TO_TRACK);
	}
	
	private PlayerTracker[] mPTrackers;
	private int numPlayersSynched;
	private boolean wallSynched;

	private Wall mWall;
	private Tile[] tilesW;
	
	
	
	
	
	private char mRoundWind;
	private int mRoundNum;
	private int mRoundBonusNum;
	
	
	private int mWhoseTurn;
	private Tile mMostRecentDiscard;
	
	
	private boolean mRoundIsOver;
	private Result mRoundResult;
	private WinType mWinType;

	private Player mWinningPlayer;
	private Player mFurikondaPlayer;
	private Tile mWinningTile;

	
	
	
	
	
	
	
	
	public RoundTracker(TableViewer tviewer, char roundWind, int roundNum, int roundBonus, Wall wall, Player p1, Player p2, Player p3, Player p4){
		
		mRoundWind = roundWind;
		mRoundNum = roundNum;
		mRoundBonusNum = roundBonus;
		
		mWhoseTurn = 1;
		mMostRecentDiscard = null;
		
		mRoundResult = Result.UNDECIDED;
		mWinType = WinType.UNDECIDED;
		mRoundIsOver = false;
		mWinningPlayer = mFurikondaPlayer = null;
		mWinningTile = null;
		
		__syncWithWall(wall);
		__setupPlayerTrackers(p1,p2,p3,p4);
		
		__syncWithViewer(tviewer);
	}
	
	
	private void __syncWithWall(Wall wall){
		mWall = wall;
		mWall.syncWithTracker(this);
	}
	public void syncWall(Tile[] wallTiles){
		if (wallSynched) return;
		tilesW = wallTiles;
	}
	
	//sets up the tracker to track players
	private void __setupPlayerTrackers(Player... players){
		
		numPlayersSynched = 0;
		mPTrackers = new PlayerTracker[NUM_PLAYERS_TO_TRACK];
		
		
		for (numPlayersSynched = 0; numPlayersSynched < NUM_PLAYERS_TO_TRACK; numPlayersSynched++){
			
			mPTrackers[numPlayersSynched] = new PlayerTracker();
			
			mPTrackers[numPlayersSynched].player = players[numPlayersSynched];	//link
			mPTrackers[numPlayersSynched].player.syncWithRoundTracker(this);
			mPTrackers[numPlayersSynched].hand.syncWithRoundTracker(this);
			mPTrackers[numPlayersSynched].pond.syncWithRoundTracker(this);
		}
	}
	public void syncPlayer(Hand h, Pond p){
		if (numPlayersSynched > NUM_PLAYERS_TO_TRACK) return;
		mPTrackers[numPlayersSynched].hand = h;
		mPTrackers[numPlayersSynched].pond = p;
		
		mPTrackers[numPlayersSynched].playerName = mPTrackers[numPlayersSynched].player.getPlayerName();	//link
		mPTrackers[numPlayersSynched].seatWind = mPTrackers[numPlayersSynched].player.getSeatWind();	//NOT LINK, but it won't change
		mPTrackers[numPlayersSynched].points = mPTrackers[numPlayersSynched].player.getPoints();	//NOT LINK
		mPTrackers[numPlayersSynched].riichiStatus = mPTrackers[numPlayersSynched].player.getRiichiStatus();	//NOT LINK
	}
	public void syncHand(TileList handTiles, ArrayList<Meld> handMelds){
		if (numPlayersSynched > NUM_PLAYERS_TO_TRACK) return;
		mPTrackers[numPlayersSynched].tilesH = handTiles;
		mPTrackers[numPlayersSynched].melds = handMelds;
	}
	public void syncPond(TileList pondTiles){
		if (numPlayersSynched > NUM_PLAYERS_TO_TRACK) return;
		mPTrackers[numPlayersSynched].tilesP = pondTiles;
	}
	
	private void __syncWithViewer(TableViewer viewer){
		
		Player[] pPlayers = {mPTrackers[0].player, mPTrackers[1].player, mPTrackers[2].player, mPTrackers[3].player};
		TileList[] pHandTiles = {mPTrackers[0].tilesH, mPTrackers[1].tilesH, mPTrackers[2].tilesH, mPTrackers[3].tilesH};
		TileList[] pPondTiles = {mPTrackers[0].tilesP, mPTrackers[1].tilesP, mPTrackers[2].tilesP, mPTrackers[3].tilesP};
//		Pond[] pPonds = {mPTrackers[0].pond, mPTrackers[1].pond, mPTrackers[2].pond, mPTrackers[3].pond};
		
		viewer.syncWithRoundTracker(this, pPlayers, pHandTiles, pPondTiles, tilesW);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void __setRoundOver(Result result){
		mRoundResult = result;
		mRoundIsOver = true;
	}
	public void setResultWashout(){__setRoundOver(Result.DRAW_WASHOUT);}
	public void setResultKyuushu(){__setRoundOver(Result.DRAW_KYUUSHU);}
	public void setResult4Kan(){__setRoundOver(Result.DRAW_4KAN);}
	public void setResult4Riichi(){__setRoundOver(Result.DRAW_4RIICHI);}
	public void setResult4Wind(){__setRoundOver(Result.DRAW_4WIND);}
	private void __setResultVictory(char winningSeat){
		switch(winningSeat){
		case 'E': __setRoundOver(Result.VICTORY_E); break;
		case 'S': __setRoundOver(Result.VICTORY_S); break;
		case 'W': __setRoundOver(Result.VICTORY_W); break;
		case 'N': __setRoundOver(Result.VICTORY_N); break;
		default: break;
		}
	}
	
	
	
	private void __setVictoryRon(Player discarder){
		mFurikondaPlayer = discarder;
		mWinningTile = mMostRecentDiscard;
		mWinType = WinType.RON;
	}
	private void __setVictoryTsumo(){
		mWinningTile = mPTrackers[getSeatNumber(mWinningPlayer)].tilesH.getLast();
		mWinType = WinType.TSUMO;
	}
	
	
	public void setResultVictory(Player winner){
		__setResultVictory(winner.getSeatWind());
		mWinningPlayer = winner;
		
		if (winner == currentPlayer()) __setVictoryTsumo();
		else __setVictoryRon(currentPlayer());
		
		__setWinningHand();
	}
	
	
	private void __setWinningHand(){
		TileList winHand = new TileList();
		winHand = mPTrackers[getSeatNumber(mWinningPlayer)].tilesH.makeCopy();
		
//		if (mWinType == WinType.RON) winHand.add(mMostRecentDiscard);
		
		mWinningHand = winHand;
	}
	
	public String getWinningHandString(){
		String winString = "";
		winString += "Winning hand: " + mWinningHand.toString() + ", agarihai: " + mWinningTile.toString() + " (" + __getWinTypeString() + ")";
		String mWinningString = winString;
		return mWinningString;
	}
	
	
	
	private TileList mWinningHand;
	
	
	
	public void printRoundResult(){
		if (roundIsOver())
			 System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
			 					"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~Round over!~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
					 			"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		 String resultStr = "Result: " + getRoundResultString();
		 System.out.println(resultStr);
	}
	
	//returns the round result as a string
	public String getRoundResultString(){
		
		String resString = "";
		resString += mRoundResult.toString();
		
		if (mRoundResult.isVictory()) resString += " (" + __getWinTypeString() + ")";
		return resString;
	}
	private String __getWinTypeString(){return mWinType.toString();}
	
	public boolean roundIsOver(){return mRoundIsOver;}
	public boolean roundEndedWithDraw(){return mRoundResult.isDraw();}
	public boolean roundEndedWithVictory(){return mRoundResult.isVictory();}
	
	
	
	
	
	
	
	
	public void nextTurn(){
		mWhoseTurn++;
		if (mWhoseTurn > NUM_PLAYERS) mWhoseTurn = 1;
	}
	public void setTurn(int turn){if (turn <= NUM_PLAYERS) mWhoseTurn = turn;}
	public int whoseTurn(){return mWhoseTurn;}
	
	public Player currentPlayer(){return mPTrackers[mWhoseTurn - 1].player;}
	
	
	
	
	
	
	private Player __neighborOffsetOf(Player p, int offset){
		int positionOfP = -1;
		for (int i = 0; i < mPTrackers.length; i++)
			if (mPTrackers[i].player == p) positionOfP = i;
		
		return mPTrackers[(positionOfP + offset) % 4].player;
	}
	public Player neighborShimochaOf(Player p){return __neighborOffsetOf(p, 1);}
	public Player neighborToimenOf(Player p){return __neighborOffsetOf(p, 2);}
	public Player neighborKamichaOf(Player p){return __neighborOffsetOf(p, 3);}
	public Player neighborNextPlayer(Player p){return neighborShimochaOf(p);}
	
	
	
	public boolean callWasMadeOnDiscard(){
		for (int i = 0; i < 3; i++)
			if (mPTrackers[(mWhoseTurn + i) % 4].player.called())
				return true;
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	//returns true if multiple players have made kans, returns false if only one player or no players have made kans
	private boolean __multiplePlayersHaveMadeKans(){
		//count the number of players who have made kans
		int count = 0;
		for (PlayerTracker pt: mPTrackers){
			if (pt.player.hasMadeAKan())
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
		for (PlayerTracker pt: mPTrackers) count += pt.player.getNumKansMade();
		return count;
	}
	
	
	
	
	
	
	//checks if too many kans have been made, and sets the round result if so
	public boolean checkIfTooManyKans(){
		if (__tooManyKans()){
			setResult4Kan();
			return true;
		}
		return false;
	}
	
	
	
	
	//checks if the wall is empty, and sets the round result if so	
	public boolean checkIfWallIsEmpty(){
		if (mWall.isEmpty()){
			setResultWashout();
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	public void setMostRecentDiscard(Tile discard){mMostRecentDiscard = discard;}
	public Tile getMostRecentDiscard(){return mMostRecentDiscard;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public char getRoundWind(){return mRoundWind;}
	public int getRoundNum(){return mRoundNum;}
	
	
	
	public int getSeatNumber(Player p) {
		for (int i = 0; i < mPTrackers.length; i++)
			if (p == mPTrackers[i].player)
				return i;
		
		return -1;
	}
	
	
	
	
	
	
	
	
	
	
	
//	//accessors
//	public int getGameType(){return mGameType;}
//	public TileList getDoraIndicators(){return mDoraIndicators;}
	
	
	
	
	
	
	
	
	
	
}
