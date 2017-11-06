package majava;



import majava.player.Player;
import majava.userinterface.ComboTextGraphicalUI;
import majava.userinterface.GameUI;
import majava.userinterface.graphicalinterface.GraphicalUI;
import majava.userinterface.graphicalinterface.window.TableViewBase;
import majava.userinterface.graphicalinterface.window.TableViewSmall;
import majava.userinterface.textinterface.DetailedTextualUI;
import majava.userinterface.textinterface.SparseTextualUI;
import majava.userinterface.textinterface.TextualUI;
import utility.Pauser;



/*
Class: Table	
methods:
	public:
		mutators:
		play - plays a game of mahjong with the four players seated at the table
	 	
	 	accessors:
	 	gameIsOver - returns true if the current game is over
*/
public class Table {
	
	private static final int NUM_PLAYERS = 4;
	
	
	//for debug use
//	private static final boolean DEBUG_SHUFFLE_SEATS = false;
//	private static final boolean DEBUG_USE_SMALL_VIEWER = false;
	private static final boolean DEBUG_USE_SMALL_VIEWER = true;
	private static final boolean DEBUG_USE_SPARSE_TEXT = true;
	
	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	private static final boolean DEFAULT_DO_SINGLE_PLAYER = true;
	
	
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	private GameUI mGameUI;
	
	
	//options
	private boolean mDoSinglePlayer;
	private boolean mDoFastGameplay;
	
	
	private Game mCurrentGame;
	
	
	/*
	no-arg Constructor
	initializes a table, creates a GUI to view the table
	initializes options for games that will be played at the table
	*/
	public Table(){
		
		//initialize UI
		mGameUI = null;
		mGameUI = __generateGameUI();
		
		mDoSinglePlayer = DEFAULT_DO_SINGLE_PLAYER;
		mDoFastGameplay = DEFAULT_DO_FAST_GAMEPLAY;
	}
	
	
	
	/*
	method: play
	plays a new game of mahjong with the table's four players
	 
	decide seat order
	play game
	*/
	public void play(){
		long time = 0;
		
		//generate players to sit at the table
		__generatePlayers();
		
		//decide seats
		__decideSeats();
		

		if (mGameUI != null) mGameUI.startUI();
		
		time = System.currentTimeMillis();
		
		//play one game
		mCurrentGame = new Game(mGameUI, mPlayerArray);
		mCurrentGame.setOptionFastGameplay(mDoFastGameplay);
		
		
		mCurrentGame.play();
		

		
		
		time = System.currentTimeMillis() - time;
		System.out.println("Time elapsed: " + time);
		
		//close the window
		Pauser.pauseFor(5000);
		if (mGameUI != null) mGameUI.endUI();
	}
	
	private GameUI __generateGameUI(){
		GameUI ui = null;
		
//		ui = new TableViewSmall();
//		ui = new TableViewer();
//		ui = new SparseTextualUI();
//		ui = new DetailedTextualUI();
		ui = new ComboTextGraphicalUI();
//		ui = new GraphicalUI();
		return ui;
	}
//	private TableGUI __generateTableGUI(){
//		TableGUI g;
//		if (DEBUG_USE_SMALL_VIEWER) g = new TableViewSmall();
//		else g = new TableViewer();
//		return g;
//	}
//	private TextualUI __generateTextInterface(){
//		TextualUI u;
//		if (DEBUG_USE_SPARSE_TEXT) u = new SparseTextualUI();
//		else u = new DetailedTextualUI();
//		return u;
//	}
	
	
	
	//sit one human at the table
	public void setOptionSinglePlayerMode(boolean doSinglePlayer){mDoSinglePlayer = doSinglePlayer;}
	public void setOptionFastGameplay(boolean doFastGameplay){mDoFastGameplay = doFastGameplay;}
	
	
	
	
	
	private void __generatePlayers(){
		
		//creates a new player to sit at each seat
		p1 = new Player();
		p2 = new Player();
		p3 = new Player();
		p4 = new Player();
		mPlayerArray = new Player[]{p1, p2, p3, p4};
		
		String[] names = {"HughMan", "Albert", "Brenda", "Carl"};
		boolean[] humanController = {false, false, false, false};
		
		if (mDoSinglePlayer) humanController[0] = true;
		
		//assign controllers and names to players
		for (int i = 0; i < NUM_PLAYERS; i++){
			if (humanController[i]) mPlayerArray[i].setControllerHuman();
			else mPlayerArray[i].setControllerComputer();
			
			mPlayerArray[i].setPlayerName(names[i]);
		}
	}
	
	
	
	
	//assigns a seat to each player
	private void __decideSeats(){
		p1.setSeatWindEast();
		p2.setSeatWindSouth();
		p3.setSeatWindWest();
		p4.setSeatWindNorth();
		
		for (int playerNum = 0; playerNum < mPlayerArray.length; playerNum++) mPlayerArray[playerNum].setPlayerNumber(playerNum);
	}
	
	
	
	//accessors
//	public int getGameType(){return mCurrentGame.getGameType();}
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
