package majava;

import java.util.ArrayList;
import java.util.List;

import majava.userinterface.GameUI;
import majava.player.Player;
import majava.summary.GameResultPrinter;
import majava.summary.RoundResultSummary;
import majava.control.Majava;
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
		
		while (!gameIsOver()){
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
	
	
	//just print the game results
	private void handleEndGame(){
		GameResultPrinter resultPrinter = new GameResultPrinter(roundResults, winStrings);
		resultPrinter.printGameResults();
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
	
	public void setOptionFastGameplay(boolean doFast){doFastGameplay = doFast;}
	
	
	//game is over if there are no rounds left, or if someone has run out of points
	public boolean gameIsOver(){
		return noRoundsRemeaining() || hakoShita();
	}
	private boolean noRoundsRemeaining(){
		switch (gameType){
		case SINGLE:
			return (currentRoundNum > 1 || currentRoundBonusNum > 0);
		case TONPUUSEN: case HANCHAN:	//intentionally handled on same case
			return (currentRoundWind == gameType.finalWind().next());
		case SIMULATION:
			return (numRoundsPlayed >= NUM_SIMULATIONS_TO_RUN);
		default: return false;
		}
	}
	//returns true if any player has run out of points (ハコシタ/とび)
	private boolean hakoShita(){
		for (Player p: mPlayerArray)
			if (p.getPoints() <= 0 && gameType != GameType.SIMULATION)
				return true;
		return false;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Game)!");
		
		System.out.println("Launching Majava...");
		Majava.main(null);
	}
	

}
