package majava;


import majava.player.Player;
import majava.userinterface.ComboTextGraphicalUI;
import majava.userinterface.GameUI;
import utility.Pauser;



//a table for four players to sit at and play various types of mahjong games
public class Table {
	
	private static final int NUM_PLAYERS = 4;
	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	private static final boolean DEFAULT_DO_SINGLE_PLAYER = true;
	
	
	private Player[] players;
	private Game currentGame;
	
	private GameUI userInterface;	
	//options
	private boolean optionDoSinglePlayer = DEFAULT_DO_SINGLE_PLAYER;
	private boolean optionDoFastGameplay = DEFAULT_DO_FAST_GAMEPLAY;
	
	
	
	
	public Table(){
		userInterface = null;
		userInterface = generateGameUI();
	}
	
	
	//plays a new game of mahjong with the table's four players
	public void play(){
		
		generatePlayers();
		
		decideSeats();
		
		if (userInterface != null)
			userInterface.startUI();
		
		playNewGame();
		
		//close the window
		Pauser.pauseFor(5000);
		if (userInterface != null) userInterface.endUI();
	}
	
	//play one game
	private void playNewGame(){
		final long time = System.currentTimeMillis();
		currentGame = new Game(userInterface, players);
		currentGame.setOptionFastGameplay(optionDoFastGameplay);
		currentGame.play();
		
		System.out.println("Time elapsed: " + (System.currentTimeMillis() - time));
		System.out.println("Games played: " + currentGame.getNumberOfRoundsPlayed());
	}
	
	private GameUI generateGameUI(){
		GameUI ui = null;
		
		ui = new ComboTextGraphicalUI();
//		ui = new TableViewSmall();
//		ui = new TableViewerLarge();
//		ui = new SparseTextualUI();
//		ui = new DetailedTextualUI();
//		ui = new GraphicalUI();
		return ui;
	}
		
	
	public void setOptionSinglePlayerMode(boolean doSinglePlayer){optionDoSinglePlayer = doSinglePlayer;}
	public void setOptionFastGameplay(boolean doFastGameplay){optionDoFastGameplay = doFastGameplay;}
	
	
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
	
	
	
	public static void main(String[] args) {
		System.out.println("Welcome to Majava (Table)!");
		System.out.println("\n\n\n\n");
		
		Table table = new Table();
		
		table.setOptionFastGameplay(DEFAULT_DO_FAST_GAMEPLAY);
		table.setOptionSinglePlayerMode(DEFAULT_DO_SINGLE_PLAYER);
		table.play();
	}
	
}
