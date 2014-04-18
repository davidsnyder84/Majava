package majava;

import java.util.ArrayList;

import majava.graphics.TableViewer;





/*
Class: RoundInfo

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
	
	private static final int NUM_PLAYERS_TO_TRACK = 4;
	private static final int NUM_MELDS_TO_TRACK = 5;
	
	
	
	
	private char mRoundWind;
	private int mRoundNum;
	private int mRoundBonusNum;
	
	private Wall mWall;
	private Tile[] tilesW;
	
	private PlayerTracker[] mPTrackers;
	private int numPlayersSynched;
	private boolean wallSynched;
	
	
	
	
//	private int mGameType;
//	
//	private TileList mDoraIndicators;
//	
//	private boolean mGameIsOver;
//	private int mGameResult;
	
	
	
	
	
	
	
	
	
	
	
	public RoundTracker(char roundWind, int roundNum, int roundBonus, Wall wall, Player p1, Player p2, Player p3, Player p4){
		
		mRoundWind = roundWind;
		mRoundNum = roundNum;
		mRoundBonusNum = roundBonus;
		
		__syncWithWall(wall);
		__setupPlayerTrackers(p1,p2,p3,p4);
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
	
	public void syncWithViewer(TableViewer viewer){
		
		Player[] pPlayers = {mPTrackers[0].player, mPTrackers[1].player, mPTrackers[2].player, mPTrackers[3].player};
		TileList[] pHandTiles = {mPTrackers[0].tilesH, mPTrackers[1].tilesH, mPTrackers[2].tilesH, mPTrackers[3].tilesH};
		TileList[] pPondTiles = {mPTrackers[0].tilesP, mPTrackers[1].tilesP, mPTrackers[2].tilesP, mPTrackers[3].tilesP};
		
		viewer.syncWithRoundTracker(pPlayers, pHandTiles, pPondTiles, tilesW);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	//accessors
//	public int getGameType(){return mGameType;}
//	public char getRoundWind(){return mRoundWind;}
//	public int getGameResult(){return mGameResult;}
//	public boolean gameIsOver(){return mGameIsOver;}
//	public TileList getDoraIndicators(){return mDoraIndicators;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
