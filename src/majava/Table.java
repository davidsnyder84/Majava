package majava;

import java.util.ArrayList;
import java.util.Scanner;

import majava.graphics.TableViewer;

import utility.GenSort;




/*
Class: Table

data:
	p1, p2, p3, p4 - four players. p1 is always east, p2 is always south, etc. 
	mWall - wall of tiles, includes the dead wall
	
	mGameType - length of game being played (single, tonpuusen, or hanchan)
	
	mRoundWind - the prevailing wind of the current round ('E' or 'S')
	mWhoseTurn - whose turn it is (1,2,3,4 corresponds to E,S,W,N)
	mReaction - will be NO_REACTION if no calls were made during a turn, will be something else otherwise
	mGameIsOver - will be true if the game is over, false if not
	mGameResult - the specific result of the game (reason for a draw game, or who won), is UNDECIDED if game is not over
	
methods:
	mutators:
 	
 	accessors:
	
	other:
*/
public class Table {
	
	public static final int NUM_PLAYERS = 4;
	public static final char DEFAULT_ROUND_WIND = 'E';
	
	public static final int GAME_TYPE_SINGLE = 0;
	public static final int GAME_TYPE_TONPUUSEN = 1;
	public static final int GAME_TYPE_HANCHAN = 2;
	public static final int GAME_TYPE_DEFAULT = GAME_TYPE_SINGLE;
	
	
	//for debug use
	private static final boolean DEBUG_SHUFFLE_SEATS = false;
	
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
	1-arg Constructor
	initializes a table to make it ready for playing
	
	input: gameType is the length of game that will be played (single, tonpuusen, or hanchan)
	
	creates a player for each seat (4)
	creates the wall
	
	initializes round and game info
	*/
	public Table(int gameType){
		
		//initialize Table Viewer
		mTviewer = new TableViewer();
		mTviewer.setVisible(true);
		mTviewer.blankEverything();
		
		mDoSinglePlayer = DEFAULT_DO_SINGLE_PLAYER;
		mDoFastGameplay = DEFAULT_DO_FAST_GAMEPLAY;
	}
	//no-arg constuctor, defaults to single round game
	public Table(){this(GAME_TYPE_DEFAULT);}
	
	
	
	
	/*
	method: play
	plays a new game of mahjong with the table's four players
	 
	decide seat order
	play round
	*/
	public void play(){
		
		//generate players to sit at the table
		__generatePlayers();
		mPlayerArray = new Player[]{p1, p2, p3, p4};
		
		//decide seats
		__decideSeats();
		
		
		//play one game
		mCurrentGame = new Game(mTviewer, mPlayerArray);
		mCurrentGame.setOptionFastGameplay(mDoFastGameplay);
		mCurrentGame.play();
	}
	
	
	
	//sit one human at the table
	public void setOptionSinglePlayerMode(boolean doSinglePlayer){mDoSinglePlayer = doSinglePlayer;}
	public void setOptionFastGameplay(boolean doFastGameplay){mDoFastGameplay = doFastGameplay;}
	
	
	
	
	
	
	private void __generatePlayers(){
		
		//creates a new player to sit at each seat
		p1 = new Player(Player.SEAT_EAST);
		p2 = new Player(Player.SEAT_SOUTH);
		p3 = new Player(Player.SEAT_WEST);
		p4 = new Player(Player.SEAT_NORTH);
	}
	
	
	
	
	/*
	method: decideSeats
	decides how many humans are playing, and randomly assigns all players to a seat
	
	
	numHumans = ask how many humans will be playing
	make list of controllers, with the desired number of humans
	
	shuffle the list randomly
	assign each of the controllers in the list to a seat
	
	return
	*/
	private void __decideSeats(){

		//figure out how many humans are playing
		int numHumans = -1;
		if (mDoSinglePlayer) numHumans = 1;
		else numHumans = 0;
		
		
		if (numHumans < 0){
			Scanner keyboard = new Scanner(System.in);
			System.out.println("How many humans will be playing? (Enter 1-4): ");
			numHumans = keyboard.nextInt();
			keyboard.close();
		}
		
		
		//add the requested number of humans to the list of controllers
		ArrayList<Character> controllers = new ArrayList<Character>();
		int i;
		for (i = 0; i < NUM_PLAYERS; i++)
			if (i < numHumans)
				controllers.add(Player.CONTROLLER_HUMAN);
			else
				controllers.add(Player.CONTROLLER_COM);
		
		
		if (DEBUG_SHUFFLE_SEATS){
			//shuffle the list controllers
			GenSort<Character> sorter = new GenSort<Character>(controllers);
			sorter.shuffle();
		}
		
		
		String[] names = {"Suwado", "Albert", "Brenda", "Carl"};

		//assign the controllers and names to players
		for (i = 0; i < NUM_PLAYERS; i++){
			mPlayerArray[i].setController(controllers.get(i));
			mPlayerArray[i].setPlayerName(names[i]);
		}
	}
	
	
	
	//accessors
	public int getGameType(){return mCurrentGame.getGameType();}
	public boolean gameIsOver(){return mCurrentGame.gameIsOver();}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Table)!");
		boolean keepgoing = true;
		
		if (keepgoing){
			System.out.println("\n\n\n\n");
			
			Table table = new Table();
			table.play();
		}
	}
	

}
