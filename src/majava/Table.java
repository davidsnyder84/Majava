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
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	private GameUI mGameUI;
	
	
	//options
	private boolean mDoSinglePlayer;
	private boolean mDoFastGameplay;
	
	
	private Game mCurrentGame;
	
	
	public Table(){
		mGameUI = null;
		mGameUI = generateGameUI();
		
		mDoSinglePlayer = DEFAULT_DO_SINGLE_PLAYER;
		mDoFastGameplay = DEFAULT_DO_FAST_GAMEPLAY;
	}
	
	
	//plays a new game of mahjong with the table's four players
	public void play(){
		
		generatePlayers();
		
		decideSeats();
		
		if (mGameUI != null) mGameUI.startUI();
		
		
		long startTime = System.currentTimeMillis();
		//play one game
		mCurrentGame = new Game(mGameUI, mPlayerArray);
		mCurrentGame.setOptionFastGameplay(mDoFastGameplay);
		mCurrentGame.play();
		
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("Time elapsed: " + endTime);
		
		//close the window
		Pauser.pauseFor(5000);
		if (mGameUI != null) mGameUI.endUI();
	}
	
	private GameUI generateGameUI(){
		
		//for debug use
//		final boolean DEBUG_USE_SMALL_VIEWER = true;
//		final boolean DEBUG_USE_SPARSE_TEXT = true;
		
		GameUI ui = null;
		
//		ui = new TableViewSmall();
//		ui = new TableViewerLarge();
//		ui = new SparseTextualUI();
//		ui = new DetailedTextualUI();
//		ui = new GraphicalUI();
		ui = new ComboTextGraphicalUI();
		return ui;
	}
		
	
	public void setOptionSinglePlayerMode(boolean doSinglePlayer){mDoSinglePlayer = doSinglePlayer;}
	public void setOptionFastGameplay(boolean doFastGameplay){mDoFastGameplay = doFastGameplay;}
	
	
	private void generatePlayers(){
		String[] names = {"HughMan", "Albert", "Brenda", "Carl"};
		boolean[] humanController = {false, false, false, false};
		if (mDoSinglePlayer) humanController[0] = true;
		
		//creates a new player to sit at each seat
		p1 = new Player();
		p2 = new Player();
		p3 = new Player();
		p4 = new Player();
		mPlayerArray = new Player[]{p1, p2, p3, p4};		
		
		//assign controllers and names to players
		for (int i = 0; i < NUM_PLAYERS; i++){
			if (humanController[i]) mPlayerArray[i].setControllerHuman();
			else mPlayerArray[i].setControllerComputer();
			
			mPlayerArray[i].setPlayerName(names[i]);
		}
	}
	
	
	
	//assigns a seat to each player
	private void decideSeats(){
		p1.setSeatWindEast();
		p2.setSeatWindSouth();
		p3.setSeatWindWest();
		p4.setSeatWindNorth();
		
		for (int playerNum = 0; playerNum < mPlayerArray.length; playerNum++)
			mPlayerArray[playerNum].setPlayerNumber(playerNum);
	}
	
	
	//accessors
	public boolean gameIsOver(){return mCurrentGame.gameIsOver();}
	
	
	
	
	public static void main(String[] args) {
		System.out.println("Welcome to Majava (Table)!");
		System.out.println("\n\n\n\n");
		
		Table table = new Table();
		
		table.setOptionFastGameplay(DEFAULT_DO_FAST_GAMEPLAY);
		table.setOptionSinglePlayerMode(DEFAULT_DO_SINGLE_PLAYER);
		table.play();
	}
	
}
