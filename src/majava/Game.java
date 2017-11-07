package majava;

import java.util.ArrayList;
import java.util.List;

import majava.userinterface.GameUI;
import majava.player.Player;
import majava.summary.RoundResultSummary;
import majava.enums.Wind;



//represents one game (tonpuusen, hanchan, etc) of mahjong
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
		public Wind finalWind(){
			switch (this){
			case TONPUUSEN: return Wind.EAST;
			case HANCHAN: return Wind.SOUTH;
			default: return Wind.UNKNOWN;
			}
		}
	}
//	private static final GameType GAME_TYPE_DEFAULT = GameType.SINGLE;
	private static final GameType GAME_TYPE_DEFAULT = GameType.HANCHAN;
//	private static final GameType GAME_TYPE_DEFAULT = GameType.TONPUUSEN;

	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	private static final int NUM_SIMULATIONS_TO_RUN = 500;
	
	
	
	
	
	private final Player p1, p2, p3, p4;
	private final Player[] mPlayerArray;
	
	private Round currentRound;
	private Wind currentRoundWind;
	private int currentRoundNum, currentRoundBonusNum;
	
	private GameType gameType;
	private boolean gameIsOver;
	private int numRoundsPlayed;
	private final List<String> winStrings;
	private final List<RoundResultSummary> roundResults;
	
	private final GameUI userInterface;
	
	private boolean doFastGameplay;
	
	
	
	
	//initializes a game
	public Game(GameUI ui, Player[] playerArray){

		setGameType(GAME_TYPE_DEFAULT);
		
		mPlayerArray = playerArray;
		p1 = mPlayerArray[0]; p2 = mPlayerArray[1]; p3 = mPlayerArray[2]; p4 = mPlayerArray[3];
		
		//initializes round info
		currentRoundWind = DEFAULT_ROUND_WIND;currentRoundNum = 1;currentRoundBonusNum = 0;
		numRoundsPlayed = 0;
		
		gameIsOver = false;
		winStrings = new ArrayList<String>();
		roundResults = new ArrayList<RoundResultSummary>();
		
		userInterface = ui;
		__setUIs();
		
		doFastGameplay = DEFAULT_DO_FAST_GAMEPLAY;
	}
	
	
	
	//set the game type
	private void setGameType(GameType gametype){
		gameType = gametype;
	}
	public void setGameTypeSingle(){setGameType(GameType.SINGLE);}
	public void setGameTypeTonpuusen(){setGameType(GameType.TONPUUSEN);}
	public void setGameTypeHanchan(){setGameType(GameType.HANCHAN);}
	public void setGameTypeSimulation(){setGameType(GameType.SIMULATION);}
	
	
	
	
	
	
	//plays a full game of mahjong
	public void play(){
		
		checkPlayersAndUI();
		
		while (!gameHasEnded()){
			playNewRound();
			saveRoundResults();
			incrementRound();
		}
		handleEndGame();
	}
	
	private void playNewRound(){
		currentRound = new Round(userInterface, mPlayerArray, currentRoundWind, currentRoundNum, currentRoundBonusNum);
		currentRound.setOptionFastGameplay(doFastGameplay);
		currentRound.play();
	}
	
	private void saveRoundResults(){
		numRoundsPlayed++;
		System.out.println("End of round" + numRoundsPlayed);
		if (currentRound.endedWithVictory()){
			winStrings.add(currentRound.getWinningHandString());
		}
		roundResults.add(currentRound.getResultSummary());
	}
	
	private void incrementRound(){
		//move to the next round, or do a bonus round if the dealer won
		if (currentRound.qualifiesForRenchan()){
			currentRoundBonusNum++;
		}
		else{
			//move to the next round
			currentRoundNum++;
			currentRoundBonusNum = 0;
			
			rotateSeats();
			
			//advance to the next wind if all rounds are finished
			if (currentRoundNum > MAX_NUM_ROUNDS){
				currentRoundNum = 1;
				currentRoundWind = currentRoundWind.next();
			}
		}
	}
	
	private void checkPlayersAndUI(){
		if (userInterface == null)
			for (Player p: mPlayerArray) p.setControllerComputer();
		
		if (doFastGameplay && allPlayersAreComputers())
			setGameTypeSimulation();
	}
	
	
	private boolean allPlayersAreComputers(){
		for (Player p: mPlayerArray) if (p.controllerIsHuman()) return false;
		return true;
	}
	
	
	private void handleEndGame(){
		displayGameResult();
		printReportCard();
	}
	
	
	//rotates seats
	private void rotateSeats(){
		for (Player p: mPlayerArray)
			p.rotateSeat();
	}
	
	private void __setUIs(){
		for (Player p: mPlayerArray){
			p.setUI(userInterface);
		}
	}
	
	
	
	public void displayGameResult(){
		System.out.println("\n\n\n\n\n\n~~~~~~~~~~~~~~~~~~~~~~\nGAMEOVER\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~\nPrinting win results\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		for (int i = 0; i < winStrings.size(); i++)
			System.out.printf("%3d: %s\n", i+1, winStrings.get(i));
		
		RoundResultSummary cr = null;
		for (int i = 0; i < roundResults.size(); i++){
			cr = roundResults.get(i);
			if (cr != null && !cr.isDrawWashout())
				System.out.printf("%3d: %s\n", i+1, cr.getAsStringResultType());
		}
	}
	
	public void printReportCard(){
		int numWins = 0;
		int numWinsByPlayer[] = new int[4];
		
		for (RoundResultSummary cr: roundResults){
			if (cr != null && cr.isVictory()){
				numWins++;
				numWinsByPlayer[cr.getWinningPlayer().getPlayerNumber()]++;
			}
		}
		System.out.println("\n\n");

		System.out.println("Wins by player:");
		int playerNum = 0;
		for (int i: numWinsByPlayer)
			System.out.println("\tPlayer " + (++playerNum) + ": " + i + " wins");
		System.out.printf("Win to draw ratio: %.6f", ((double) numWins) / ((double) roundResults.size()));
		
		System.out.println("\n\n");
	}
	
	
	
	public boolean gameHasEnded(){
		if (gameIsOver) return true;
		
		//game is over is someone runs out of points
		for (Player p: mPlayerArray)
			if (gameType != GameType.SIMULATION && p.getPoints() <= 0)
				return gameIsOver = true;
		
		switch (gameType){
		case SINGLE:
			return gameIsOver = (currentRoundNum > 1 || currentRoundBonusNum > 0);
		case TONPUUSEN: case HANCHAN:
			return gameIsOver = (currentRoundWind == gameType.finalWind().next());
		case SIMULATION:
			return gameIsOver = (numRoundsPlayed >= NUM_SIMULATIONS_TO_RUN);
		default: return false;
		}
	}
	
	
	public void setOptionFastGameplay(boolean doFast){doFastGameplay = doFast;}
	
	
	
	
	
//	public static void main(String[] args) {
//		
//		System.out.println("Welcome to Majava (Game)!");
//		
//		System.out.println("Launching Table...");
//		Table.main(null);
//	}
	

}
