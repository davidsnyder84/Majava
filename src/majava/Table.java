package majava;


import majava.graphics.TableViewer;
import utility.Pauser;




/*
Class: Table

data:
	p1, p2, p3, p4 - the four players who will play the round
	mPlayerArray - an array containing the four players
	
	mCurrentGame - the current game being played at the table
	
	mTviewer - TableViewer GUI to display the game and get input from the human player
	mDoFastGameplay - option, will do fast gameplay if true
	mDoSinglePlayer - option, will do single player if true
	
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
	
	private static final boolean DEFAULT_DO_FAST_GAMEPLAY = false;
	private static final boolean DEFAULT_DO_SINGLE_PLAYER = true;
	
	
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	
	private TableViewer mTviewer;
	
	
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
		
		//initialize Table Viewer
		mTviewer = new TableViewer();
		mTviewer.blankEverything();
		
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
		
		//generate players to sit at the table
		__generatePlayers();
		
		//decide seats
		__decideSeats();
		
		//show table window
		mTviewer.setVisible(true);
		
		//play one game
		mCurrentGame = new Game(mTviewer, mPlayerArray);
		mCurrentGame.setOptionFastGameplay(mDoFastGameplay);
		mCurrentGame.play();
		
		//close the window
		Pauser.pauseFor(8000);
		mTviewer.dispose();
	}
	
	
	
	//sit one human at the table
	public void setOptionSinglePlayerMode(boolean doSinglePlayer){mDoSinglePlayer = doSinglePlayer;}
	public void setOptionFastGameplay(boolean doFastGameplay){mDoFastGameplay = doFastGameplay;}
	
	
	
	
	
	/*
	private method: __generatePlayers
	generates four players to sit at the table
	
	decides the players' names and controllers
	*/
	private void __generatePlayers(){
		
		//creates a new player to sit at each seat
		p1 = new Player();
		p2 = new Player();
		p3 = new Player();
		p4 = new Player();
		mPlayerArray = new Player[]{p1, p2, p3, p4};
		
		
		//figure out how many humans are playing
//		int numHumans = -1;
//		if (mDoSinglePlayer) numHumans = 1;
//		else numHumans = 0;
		

		String[] names = {"Suwado", "Albert", "Brenda", "Carl"};
		boolean[] humanController = {false, false, false, false};
		
		if (mDoSinglePlayer) humanController[0] = true;
		
		//assign the controllers and names to players
		for (int i = 0; i < NUM_PLAYERS; i++){
			if (humanController[i]) mPlayerArray[i].setControllerHuman();
			else mPlayerArray[i].setControllerComputer();
			
			mPlayerArray[i].setPlayerName(names[i]);
		}
	}
	
	
	
	
	/*
	private method: __decideSeats
	assigns a seat to each player
	*/
	private void __decideSeats(){
		p1.setSeatWindEast();
		p2.setSeatWindSouth();
		p3.setSeatWindWest();
		p4.setSeatWindNorth();
	}
	
	
	
	//accessors
//	public int getGameType(){return mCurrentGame.getGameType();}
	public boolean gameIsOver(){return mCurrentGame.gameIsOver();}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Table)!");
		System.out.println("\n\n\n\n");
		
		Table table = new Table();
		
		table.setOptionFastGameplay(true);
		table.setOptionSinglePlayerMode(false);
		table.play();
	}
	

}
