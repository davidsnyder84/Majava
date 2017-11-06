package majava;

import java.util.ArrayList;
import java.util.List;

import majava.userinterface.GameUI;
import majava.player.Player;
import majava.summary.RoundResultSummary;
import majava.enums.Wind;



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
	
	private static enum GameType{
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
	public static final GameType GAME_TYPE_DEFAULT = GameType.HANCHAN;
//	public static final GameType GAME_TYPE_DEFAULT = GameType.TONPUUSEN;

	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	private static final boolean DEFAULT_FAST_COMS_MEAN_SIMULATION = true;
//	private static final boolean DEFAULT_FAST_COMS_MEAN_SIMULATION = false;
	private static final int NUM_SIMULATIONS_TO_RUN = 500;
//	private static final int NUM_SIMULATIONS_TO_RUN = 5000;
//	private static final int NUM_SIMULATIONS_TO_RUN = 100;
	
	
	
	
	
	private final Player p1, p2, p3, p4;
	private final Player[] mPlayerArray;
	
	private Round mCurrentRound;
	private Wind mCurrentRoundWind;
	private int mCurrentRoundNum, mCurrentRoundBonusNum;
	
	private GameType mGameType;
	private boolean mGameIsOver;
	private int mNumRoundsPlayed;
	private final List<String> mWinStrings;
	private final List<RoundResultSummary> mRoundResults;
	
	private final GameUI mUI;
	
	private boolean mDoFastGameplay;
	
	
	
	
	
	/*
	initializes a game
	*/
	public Game(GameUI ui, Player[] playerArray){

		__setGameType(GAME_TYPE_DEFAULT);
		
		mPlayerArray = playerArray;
		p1 = mPlayerArray[0]; p2 = mPlayerArray[1]; p3 = mPlayerArray[2]; p4 = mPlayerArray[3];
		
		//initializes round info
		mCurrentRoundWind = DEFAULT_ROUND_WIND;mCurrentRoundNum = 1;mCurrentRoundBonusNum = 0;
		mNumRoundsPlayed = 0;
		
		mGameIsOver = false;
		mWinStrings = new ArrayList<String>();
		mRoundResults = new ArrayList<RoundResultSummary>();
		
		mUI = ui;
		__setUIs();
		
		mDoFastGameplay = DEFAULT_DO_FAST_GAMEPLAY;
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
		
		if (DEFAULT_FAST_COMS_MEAN_SIMULATION)
			if (mDoFastGameplay && p1.controllerIsComputer() && p2.controllerIsComputer() && p3.controllerIsComputer() && p4.controllerIsComputer()){
				setGameTypeSimulation();
				for (Player p: mPlayerArray) p.setControllerComputer();
			}
		if (mUI == null) for (Player p: mPlayerArray) p.setControllerComputer();
		
		
		//play rounds until the game is over
		while (!gameIsOver()){
			
			//play a round
			mCurrentRound = new Round(mUI, mPlayerArray, mCurrentRoundWind, mCurrentRoundNum, mCurrentRoundBonusNum);
			mCurrentRound.setOptionFastGameplay(mDoFastGameplay);
			mCurrentRound.play();
			
			mNumRoundsPlayed++;
			System.out.println(mNumRoundsPlayed);
			if (mCurrentRound.endedWithVictory()){
				mWinStrings.add(mCurrentRound.getWinningHandString());
			}
			mRoundResults.add(mCurrentRound.getResultSummary());
			
			
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
		
		__handleEndGame();
	}
	
	
	
	private void __handleEndGame(){
		displayGameResult();
		printReportCard();
	}
	
	
	
	
	//rotates seats
	private void __rotateSeats(){
		for (Player p: mPlayerArray)
			p.rotateSeat();
	}
	
	private void __setUIs(){
		for (Player p: mPlayerArray) p.setUI(mUI);
	}
	
	
	
	
	
	public void displayGameResult(){
		
		System.out.println("\n\n\n\n\n\n~~~~~~~~~~~~~~~~~~~~~~\nGAMEOVER\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~\nPrinting win results\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		for (int i = 0; i < mWinStrings.size(); i++)
			System.out.printf("%3d: %s\n", i+1, mWinStrings.get(i));
		
		RoundResultSummary cr = null;
		for (int i = 0; i < mRoundResults.size(); i++){
			cr = mRoundResults.get(i);
			if (cr != null && !cr.isDrawWashout())
				System.out.printf("%3d: %s\n", i+1, cr.getAsStringResultType());
		}
	}
	
	public void printReportCard(){
		int numWins = 0;
		int numWinsByPlayer[] = new int[4];
		
		for (RoundResultSummary cr: mRoundResults){
			if (cr != null && cr.isVictory()){
				numWins++;
				numWinsByPlayer[cr.getWinningPlayer().getPlayerNumber()]++;
			}
		}
		
		System.out.println("\n\n");
		

		System.out.println("Wins by player:");
		int j = 0;
		for (int i: numWinsByPlayer) System.out.println("\tPlayer " + (++j) + ": " + i + " wins");
		System.out.printf("Win to draw ratio: %.6f", ((double) numWins) / ((double) mRoundResults.size()));
		
		System.out.println("\n\n");
	}
	
	
	
	
	
	
	//accessors
	public boolean gameIsOver(){
		if (mGameIsOver) return true;
		
		for (Player p: mPlayerArray) if (mGameType != GameType.SIMULATION && p.getPoints() < 0) return mGameIsOver = true;
		
		switch (mGameType){
		case SINGLE: return mGameIsOver = (mCurrentRoundNum > 1 || mCurrentRoundBonusNum > 0);
		case TONPUUSEN: case HANCHAN: return mGameIsOver = (mCurrentRoundWind == mGameType.lastWind().next());
		case SIMULATION: return mGameIsOver = (mNumRoundsPlayed >= NUM_SIMULATIONS_TO_RUN);
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
