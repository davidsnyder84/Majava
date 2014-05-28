package majava;

import java.util.ArrayList;

import majava.graphics.TableGUI;



/*
Class: Game

data:
	p1, p2, p3, p4 - the game's four players
	mPlayerArray - an array containing the four players
	
	mGameType - the type of game being played (single, tonpuusen, hanchan. or simulation)
	
	mCurrentRound - the current round being played
	mCurrentRoundWind, mCurrentRoundNum, mCurrentRoundBonusNum - information for the current round
	
	mGameIsOver - will be true if the game is over, false if not
	mWinStrings - a string containing the winning hands of each round in the game
	
	mTviewer - TableGUI window for the game
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
	private static final int MAX_NUM_ROUNDS = NUM_PLAYERS;
	private static final Wind DEFAULT_ROUND_WIND = Wind.EAST;
	
	private enum GameType{
		SINGLE, TONPUUSEN, HANCHAN, SIMULATION;
		@Override
		public String toString(){
			switch (this){
			case SINGLE: return "Single";
			case TONPUUSEN: return "Tonpuusen";
			case HANCHAN: return "Hanchan";
			case SIMULATION: return "Simulation";
			default: return "unknown";
			}
		}
		public Wind lastWind(){
			switch (this){
			case TONPUUSEN: return Wind.EAST;
			case HANCHAN: return Wind.SOUTH;
			default: return Wind.UNKNOWN;
			}
		}
	}
//	public static final GameType GAME_TYPE_DEFAULT = GameType.SINGLE;
//	public static final GameType GAME_TYPE_DEFAULT = GameType.HANCHAN;
	public static final GameType GAME_TYPE_DEFAULT = GameType.TONPUUSEN;

	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	private static final int NUM_SIMULATIONS_TO_RUN = 500;
	
	
	
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	private Round mCurrentRound;
	private Wind mCurrentRoundWind;
	private int mCurrentRoundNum;
	private int mCurrentRoundBonusNum;
	
	private GameType mGameType;
	private boolean mGameIsOver;
	private int mNumRoundsPlayed;
	private ArrayList<String> mWinStrings;

	private TableGUI mTviewer;
	
	private boolean mDoFastGameplay;
	
	
	
	
	
	/*
	initializes a game
	*/
	public Game(TableGUI tviewer, Player[] playerArray){

		__setGameType(GAME_TYPE_DEFAULT);
		
		mPlayerArray = playerArray;
		p1 = mPlayerArray[0]; p2 = mPlayerArray[1]; p3 = mPlayerArray[2]; p4 = mPlayerArray[3];
		
		
		//initializes round info
		mCurrentRoundWind = DEFAULT_ROUND_WIND;mCurrentRoundNum = 1;mCurrentRoundBonusNum = 0;

		mGameIsOver = false;
		mWinStrings = new ArrayList<String>();
		
		mTviewer = tviewer;
		mDoFastGameplay = DEFAULT_DO_FAST_GAMEPLAY;
		mNumRoundsPlayed = 0;
	}
	
	
	
	//set the game type
	private void __setGameType(GameType gametype){
		mGameType = gametype;
	}
	public void setGameTypeSingle(){__setGameType(GameType.SINGLE);}
	public void setGameTypeTonpuusen(){__setGameType(GameType.TONPUUSEN);}
	public void setGameTypeHanchan(){__setGameType(GameType.HANCHAN);}
	public void setGameTypeSimulation(){__setGameType(GameType.SIMULATION);}
	
	
	
	
	
	
	
	
	
	/*
	method: play
	plays a full game of mahjong
	*/
	public void play(){
		
		if (mDoFastGameplay && p1.controllerIsComputer() && p2.controllerIsComputer() && p3.controllerIsComputer() && p4.controllerIsComputer()) setGameTypeSimulation();
		
		
		//play rounds until the game is over
		while (!gameIsOver()){
			
			//play a round
			mCurrentRound = new Round(mTviewer, mPlayerArray, mCurrentRoundWind, mCurrentRoundNum, mCurrentRoundBonusNum);
			mCurrentRound.setOptionFastGameplay(mDoFastGameplay);
			mCurrentRound.play();
			
			mNumRoundsPlayed++;
			if (mCurrentRound.endedWithVictory()){
				mWinStrings.add(mCurrentRound.getWinningHandString());
			}
			
			
			//move to the next round, or do a bonus round if the dealer won
			if (mCurrentRound.qualifiesForRenchan()){
				mCurrentRoundBonusNum++;
			}
			else{
				
				//move to the next round
				mCurrentRoundNum++;
				mCurrentRoundBonusNum = 0;
				
				//rotate seats
				__rotateSeats();
				
				//advance to the next wind if all rounds are finished
				if (mCurrentRoundNum > MAX_NUM_ROUNDS){
					mCurrentRoundNum = 1;
					mCurrentRoundWind = mCurrentRoundWind.next();
				}
			}
		}
		
		displayGameResult();
	}
	
	
	
	
	//rotates seats
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
	public boolean gameIsOver(){
		if (mGameIsOver) return true;
		
		switch (mGameType){
		case SINGLE: return mGameIsOver = (mCurrentRoundNum > 1 || mCurrentRoundBonusNum > 0);
		case TONPUUSEN: case HANCHAN: return mGameIsOver = (mCurrentRoundWind == mGameType.lastWind().next());
		case SIMULATION: return mGameIsOver = (mNumRoundsPlayed > NUM_SIMULATIONS_TO_RUN);
		default: return false;
		}
	}
	
	
	
	
	

	public void setOptionFastGameplay(boolean doFastGameplay){mDoFastGameplay = doFastGameplay;}
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Game)!");
		
		System.out.println("Launching Table...");
		Table.main(null);
	}
	

}
