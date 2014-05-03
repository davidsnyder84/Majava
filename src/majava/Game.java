package majava;

import java.util.ArrayList;

import majava.graphics.TableViewer;



/*
Class: Game

data:
	p1, p2, p3, p4 - the game's four players
	mPlayerArray - an array containing the four players
	
	mGameType - the type of game being played (single, tonpuusen, or hanchan)
	
	mCurrentRound - the current round being played
	mCurrentRoundWind, mCurrentRoundNum, mCurrentRoundBonusNum - information for the current round
	
	mGameIsOver - will be true if the game is over, false if not
	mWinStrings - a string containing the winning hands of each round in the game
	
	mTviewer - TableViewer GUI window for the game
	mDoFastGameplay - option, will do fast gameplay if true
	
methods:
	constructors:
	2-arg, requires a TableViewer GUI and an array of four players
	
	public:
		play - plays a game of mahjong with the four players
		runSimulation - plays a large number of fast games with computer players only
	
		mutators:
		setOptionFastGameplay - turns fast gameplay on or off
 	
 		accessors:
 		gameIsOver - returns true if the game is over
 		displayGameResult - displays the result of a game that has finished
*/
public class Game {
	
	private static final int NUM_PLAYERS = 4;
	private static final Wind DEFAULT_ROUND_WIND = Wind.EAST;
	
	private enum GameType{
		SINGLE, TONPUUSEN, HANCHAN;
		@Override
		public String toString(){
			switch (this){
			case SINGLE: return "Single";
			case TONPUUSEN: return "Tonpuusen";
			case HANCHAN: return "Hanchan";
			default: return "unknown";
			}
		}
	}
	public static final GameType GAME_TYPE_DEFAULT = GameType.SINGLE;

	public static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	
	
	
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	private Round mCurrentRound;
	private Wind mCurrentRoundWind;
	private int mCurrentRoundNum;
	private int mCurrentRoundBonusNum;
	
	private GameType mGameType;
	private boolean mGameIsOver;
	private ArrayList<String> mWinStrings;

	private TableViewer mTviewer;
	
	private boolean mDoFastGameplay;
	
	
	
	
	
	/*
	initializes a game
	*/
	public Game(TableViewer tviewer, Player[] playerArray){

		__setGameType(GAME_TYPE_DEFAULT);
		
		mPlayerArray = playerArray;
		p1 = mPlayerArray[0]; p2 = mPlayerArray[1]; p3 = mPlayerArray[2]; p4 = mPlayerArray[3];
		
		
		//initializes round info
		mCurrentRoundWind = DEFAULT_ROUND_WIND;mCurrentRoundNum = 1;mCurrentRoundBonusNum = 0;

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
		
		final int NUM_ROUNDS_TO_PLAY = 100;

		
		
		//play a bunch of rounds
		for (mCurrentRoundNum = 1; mCurrentRoundNum <= NUM_ROUNDS_TO_PLAY; mCurrentRoundNum++){
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
	public boolean gameIsOver(){return mGameIsOver;}
	
	
	
	
	

	public void setOptionFastGameplay(boolean doFastGameplay){mDoFastGameplay = doFastGameplay;}
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Game)!");
		
		System.out.println("Launching Table...");
		Table.main(null);
	}
	

}
