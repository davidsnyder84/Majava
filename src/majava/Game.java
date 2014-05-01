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
	public static final Wind DEFAULT_ROUND_WIND = Wind.EAST;
	
	
	
	private enum GameType{
		SINGLE, TONPUUSEN, HANCHAN;
	}
	public static final GameType GAME_TYPE_DEFAULT = GameType.SINGLE;

	public static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	
//	public static TableViewer mTviewer;	//will be private
	private TableViewer mTviewer;
	
	
	private Wind mRoundWind;
	private int mRoundNum;
	private int mRoundBonusNum;
	
	private GameType mGameType;
	private boolean mGameIsOver;
	
	
	private Round mCurrentRound;
	private ArrayList<String> mWinStrings;
	
	private boolean mDoFastGameplay;
	
	
	
	
	
	/*
	initializes a game
	*/
	public Game(TableViewer tviewer, Player[] playerArray){

		__setGameType(GAME_TYPE_DEFAULT);
		
		mPlayerArray = playerArray;
		p1 = mPlayerArray[0]; p2 = mPlayerArray[1]; p3 = mPlayerArray[2]; p4 = mPlayerArray[3];
		
		
		//initializes round info
		mRoundWind = DEFAULT_ROUND_WIND;mRoundNum = 1;mRoundBonusNum = 0;

		mGameIsOver = false;
		mWinStrings = new ArrayList<String>();
		
		mTviewer = tviewer;
		mDoFastGameplay = DEFAULT_DO_FAST_GAMEPLAY;
	}
	
	
	
	//set the game type
	private void __setGameType(GameType gametype){
		mGameType = gametype;
	}
	public void setGameTypeSingle(){__setGameType(GameType.SINGLE);}
	public void setGameTypeTonpuusen(){__setGameType(GameType.TONPUUSEN);}
	public void setGameTypeHanchan(){__setGameType(GameType.HANCHAN);}
	
	
	
	
	
	
	
	
	
	/*
	method: play
	plays a new game of mahjong
	
	play round
	*/
	public void play(){
		
//		runSimulation();
		if (mDoFastGameplay && p1.controllerIsComputer() && p2.controllerIsComputer() && p3.controllerIsComputer() && p4.controllerIsComputer()) runSimulation();
		
		
		//play one round
		mCurrentRound = new Round(mTviewer, mPlayerArray);
		mCurrentRound.setOptionFastGameplay(mDoFastGameplay);
		mCurrentRound.play();
		
		if (mCurrentRound.endedWithVictory()){
			mWinStrings.add(mCurrentRound.getWinningHandString());
		}
		
		mGameIsOver = true;
		
		displayGameResult();
	}
	
	
	/*
	method: runSimulation
	runs a simulation
	*/
	public void runSimulation(){
		
		final int NUM_ROUNDS_TO_PLAY = 200;

		
		
		//play a bunch of rounds
		for (int i = 0; i < NUM_ROUNDS_TO_PLAY; i++){
			mCurrentRound = new Round(mTviewer, mPlayerArray);
			mCurrentRound.setOptionFastGameplay(true);
			mCurrentRound.play();
			
			if (mCurrentRound.endedWithVictory()){
				if (mCurrentRound.getWinningHandString().charAt(16) != ',')
				mWinStrings.add(mCurrentRound.getWinningHandString());
			}
			
			__rotateSeats();
		}
		
		mGameIsOver = true;
		
		displayGameResult();
	}
	
	
	
	private void __rotateSeats(){
		for (Player p: mPlayerArray)
			p.rotateSeat();
	}
	
	
	
	
	
	public void displayGameResult(){
		
		System.out.println("\n\n\n\n\n\n~~~~~~~~~~~~~~~~~~~~~~\nGAMEOVER\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~\nPrinting win results\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		for (int i = 0; i < mWinStrings.size(); i++)
			System.out.printf("%3d: %s\n", i+1, mWinStrings.get(i));
	}
	
	
	
	
	
	
	//accessors
//	public int getGameType(){return mGameType;}
	public Wind getRoundWind(){return mRoundWind;}
	public boolean gameIsOver(){return mGameIsOver;}
	
	
	
	
	

	public void setOptionFastGameplay(boolean doFastGameplay){mDoFastGameplay = doFastGameplay;}
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Game)!");
		
		System.out.println("Launching Table...");
		Table.main(null);
	}
	

}
