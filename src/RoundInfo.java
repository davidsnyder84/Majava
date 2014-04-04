



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
public class RoundInfo {
	

	private char mRoundWind;
	private int mRoundNum;
	private int mRoundBonusNum;
	
	private int mGameType;
	
	private TileList mDoraIndicators;
	
	private boolean mGameIsOver;
	private int mGameResult;
	
	
	
	
	
	private Player mPlayer1, mPlayer2, mPlayer3, mPlayer4;
	private Pond mPond1, mPond2, mPond3, mPond4;
	private Hand mHand1, mHand2, mHand3, mHand4;
	
	private Wall mWall;
	
	
	private TileList mH1Tiles;
	
	
	
	
	
	
	
	
	
	public RoundInfo(char roundWind, int roundNum, int roundBonus, Player p1, Player p2, Player p3, Player p4, Wall wall){
		
		mRoundWind = roundWind;
		mRoundNum = roundNum;
		mRoundBonusNum = roundBonus;
		
		mPlayer1 = p1;
		mPlayer2 = p2;
		mPlayer3 = p3;
		mPlayer4 = p4;
		
		//will get these later
		mPond1 = mPond2 = mPond3 = mPond4 = null;
		mHand1 = mHand2 = mHand3 = mHand4 = null;
	}
	
	
	
	
	
	
	
	
	//4-arg, takes game type, round wind, dora indicators, and game result
	public RoundInfo(int gameType, char roundWind, TileList doraIndicators, int gameResult){
		
		mGameType = gameType;
		mRoundWind = roundWind;
		
		mGameResult = gameResult;
		//if game is undecided, gameIsOver = false
		if (mGameResult == Table.RESULT_UNDECIDED)
			mGameIsOver = false;
		else
			mGameIsOver = true;
		
		mDoraIndicators = doraIndicators;
	}
	//3-arg, takes game type, round wind, dora indicators, and game result. Assumes game is not over.
	public RoundInfo(int gameType, char roundWind, TileList doraIndicators){
		this(gameType, roundWind, doraIndicators, Table.RESULT_UNDECIDED);
	}
	
	
	
	
	
	
	public void setPond(Pond p){
		;
	}
	
	public void setP1Pond(Pond p){
		;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//accessors
	public int getGameType(){return mGameType;}
	public char getRoundWind(){return mRoundWind;}
	public int getGameResult(){return mGameResult;}
	public boolean gameIsOver(){return mGameIsOver;}
	public TileList getDoraIndicators(){return mDoraIndicators;}
	
	
	
	
	
}
