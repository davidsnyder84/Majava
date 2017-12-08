package majava;


import java.util.ArrayList;
import java.util.List;

import majava.events.GameEventBroadcaster;
import majava.events.GameplayEvent;
import majava.events.JanObserver;
import majava.player.Player;
import majava.userinterface.GameUI;
import majava.userinterface.MajavaGUI;
import majava.userinterface.textinterface.DetailedTextualUI;
import majava.userinterface.textinterface.SparseTextualUI;
import majava.userinterface.textinterface.TextualUI;
import utility.Pauser;



//a table for four players to sit at and play various types of mahjong games
public class Table {
	private static final int NUM_PLAYERS = 4;
	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	private static final boolean DEFAULT_DO_SINGLE_PLAYER = true;
	
	
	private Player[] players;
	private Game currentGame;
	
	//options
	private boolean optionDoSinglePlayer = DEFAULT_DO_SINGLE_PLAYER;
	private boolean optionDoFastGameplay = DEFAULT_DO_FAST_GAMEPLAY;
	private int sleepTime, sleepTimeExclamation, sleepTimeRoundEnd;
	
	private GameEventBroadcaster gameEventBroadcaster;
	
	
	
	
	public Table(){
		gameEventBroadcaster = new GameEventBroadcaster();
	}
	private void setupUserInterfaces(){		
		GameUI gui = new MajavaGUI();
		setSleepTimesForUI(gui);
		GameUI textUI = new SparseTextualUI();
		textUI.setSleepTimes(0,0,0);
		
		List<GameUI> userInterfaces = new ArrayList<GameUI>();
		userInterfaces.add(gui);
		userInterfaces.add(textUI);
				
		for (GameUI ui: userInterfaces)
			gameEventBroadcaster.registerObserver(ui);
	}
	
	
	
	//plays a new game of mahjong with the table's four players
	public void play(){
		
		generatePlayers();
		
		decideSeats();
		
		setupUserInterfaces();
		
		playNewGame();
		
		//close the window
		end();
	}
	
	//play one game
	private void playNewGame(){
		final long time = System.currentTimeMillis();
		
		currentGame = new Game(players, gameEventBroadcaster);
		if (optionDoFastGameplay && allPlayersAreComputers()) currentGame.setGameTypeSimulation();
		
		//start a game
		gameEventBroadcaster.postNewEvent(GameplayEvent.startingEvent());
		currentGame.play();
		
		System.out.println("Time elapsed: " + (System.currentTimeMillis() - time));
		System.out.println("Games played: " + currentGame.getNumberOfRoundsPlayed());
	}

	
	private boolean allPlayersAreComputers(){
		for (Player p: players) if (p.controllerIsHuman()) return false;
		return true;
	}
	
	public void setOptionSinglePlayerMode(boolean doSinglePlayer){optionDoSinglePlayer = doSinglePlayer;}
	public void setOptionFastGameplay(boolean doFastGameplay){optionDoFastGameplay = doFastGameplay;}
	
	private void setSleepTimesForUI(GameUI ui){
		final int DEAFULT_SLEEPTIME = 400;
		final int DEAFULT_SLEEPTIME_EXCLAMATION = 1500;
		final int DEAFULT_SLEEPTIME_ROUND_END = 18000;
		final int FAST_SLEEPTIME = 0, FAST_SLEEPTIME_EXCLAMATION = 0, FAST_SLEEPTIME_ROUND_END = 0;
		
		if (optionDoFastGameplay){
			sleepTime = FAST_SLEEPTIME;
			sleepTimeExclamation = FAST_SLEEPTIME_EXCLAMATION;
			sleepTimeRoundEnd = FAST_SLEEPTIME_ROUND_END;
		}
		else{
			sleepTime = DEAFULT_SLEEPTIME;
			sleepTimeExclamation = DEAFULT_SLEEPTIME_EXCLAMATION;
			sleepTimeRoundEnd = DEAFULT_SLEEPTIME_ROUND_END;
		}
		ui.setSleepTimes(sleepTime, sleepTimeExclamation, sleepTimeRoundEnd);
	}
	
	
	private void generatePlayers(){
		String[] names = {"HughMan", "Albert", "Brenda", "Carl"};
//		boolean[] humanController = {true,true,true,true};
//		boolean[] humanController = {true,true,false,true};
		boolean[] humanController = {false, false, false, false};
		if (optionDoSinglePlayer) humanController[0] = true;
		
		//creates a new player to sit at each seat
		players = new Player[]{new Player(), new Player(), new Player(), new Player()};		
		
		//assign controllers and names to players
		for (int i = 0; i < NUM_PLAYERS; i++){
			if (humanController[i]) players[i].setControllerHuman();
			else players[i].setControllerComputer();
			
			players[i].setPlayerName(names[i]);
		}
	}
	
	
	//assigns a seat to each player
	private void decideSeats(){
		players[0].setSeatWindEast();
		players[1].setSeatWindSouth();
		players[2].setSeatWindWest();
		players[3].setSeatWindNorth();
		
		for (int playerNum = 0; playerNum < players.length; playerNum++)
			players[playerNum].setPlayerNumber(playerNum);
	}
	
	//accessors
	public boolean gameIsOver(){return currentGame.gameIsOver();}
	
	private void end(){
		Pauser.pauseFor(5000);
		gameEventBroadcaster.postNewEvent(GameplayEvent.endingEvent(), null);
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("Welcome to Majava (Table)!");
		System.out.println("\n\n\n\n");
		
		Table table = new Table();
		
		table.setOptionFastGameplay(DEFAULT_DO_FAST_GAMEPLAY);
		table.setOptionSinglePlayerMode(DEFAULT_DO_SINGLE_PLAYER);
		table.play();
	}
	
}
