package majava;

import java.util.ArrayList;

import majava.graphics.TableViewer;



/*
Class: Game

data:
	p1, p2, p3, p4 - four players. p1 is always east, p2 is always south, etc. 
	mWall - wall of tiles, includes the dead wall
	
	mGameType - length of game being played (single, tonpuusen, or hanchan)
	
	mRoundWind - the prevailing wind of the current round ('E' or 'S')
	mWhoseTurn - whose turn it is (1,2,3,4 corresponds to E,S,W,N)
	mReaction - will be NO_REACTION if no calls were made during a turn, will be something else otherwise
	mGameIsOver - will be true if the game is over, false if not
	mGameResult - the specific result of the game (reason for a draw game, or who won), is UNDECIDED if game is not over
	
methods:
	mutators:
 	
 	accessors:
	
	other:
*/
public class Game {
	
	public static final int NUM_PLAYERS = 4;
	public static final char DEFAULT_ROUND_WIND = 'E';
	
	public static final int GAME_TYPE_SINGLE = 0;
	public static final int GAME_TYPE_TONPUUSEN = 1;
	public static final int GAME_TYPE_HANCHAN = 2;
	public static final int GAME_TYPE_DEFAULT = GAME_TYPE_SINGLE;
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	
//	public static TableViewer mTviewer;	//will be private
	private TableViewer mTviewer;
	
	
	private char mRoundWind;
	private int mRoundNum;
	private int mRoundBonusNum;
	
	private int mGameType;
	private boolean mGameIsOver;
//	private int mGameResult;
	
	
	private Round mCurrentRound;	
	
	private ArrayList<String> mWinStrings;
	
	
	
	
	
	
	
	/*
	initializes a game
	*/
	public Game(TableViewer tviewer, Player[] playerArray, int gameType){
		
		mPlayerArray = playerArray;
		p1 = mPlayerArray[0]; p2 = mPlayerArray[1]; p3 = mPlayerArray[2]; p4 = mPlayerArray[3];
		
		
		//initializes round info
		mRoundWind = DEFAULT_ROUND_WIND;mRoundNum = 1;mRoundBonusNum = 0;
		
		mGameIsOver = false;
		
		if (gameType == GAME_TYPE_SINGLE || gameType == GAME_TYPE_TONPUUSEN || gameType == GAME_TYPE_HANCHAN)
			mGameType = gameType;
		else
			mGameType = GAME_TYPE_DEFAULT;
		
		
		mTviewer = tviewer;
		
		mWinStrings = new ArrayList<String>();
		
	}
	//defaults to single round game
	public Game(TableViewer tviewer, Player[] playerArray){this(tviewer, playerArray, GAME_TYPE_DEFAULT);}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	method: play
	plays a new game of mahjong
	
	play round
	*/
	public void play(){
		
		
		final int NUM_ROUNDS_TO_PLAY = 100;
		
		
		//play one round
		for (int i = 0; i < NUM_ROUNDS_TO_PLAY; i++){
			mCurrentRound = new Round(mTviewer, mPlayerArray);
			mCurrentRound.play();
			
			if (mCurrentRound.endedWithVictory())
				mWinStrings.add(mCurrentRound.getWinningHandString());
		}
		
		mGameIsOver = true;
		
		displayGameResult();
		
	}
	
	
	public void displayGameResult(){
		
		System.out.println("\n\n\n\n\n\n~~~~~~~~~~~~~~~~~~~~~~\nGAMEOVER\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~\nPrinting win results\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		for (int i = 0; i < mWinStrings.size(); i++)
			System.out.printf("%3d: %s\n", i+1, mWinStrings.get(i));
	}
	
	
	
	
	
	
	//accessors
	public int getGameType(){return mGameType;}
	public char getRoundWind(){return mRoundWind;}
	public boolean gameIsOver(){return mGameIsOver;}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Game)!");
		
		System.out.println("Launching Table...");
		Table.main(null);
	}
	

}
