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
	public static final boolean DEBUG_DO_SINGLE_PLAYER_GAME = true;
	public static final boolean DEBUG_SHUFFLE_SEATS = false;
	
	
	
	
	private Player p1, p2, p3, p4;
	private Player[] mPlayerArray;
	
	
//	public static TableViewer mTviewer;	//will be private
	private TableViewer mTviewer;
	
	
	
//	private int mGameType;
//	private boolean mGameIsOver;
//	private int mGameResult;
	
	
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
		
//		mGameIsOver = false;
		
		//initialize Table Viewer
		mTviewer = new TableViewer();
		mTviewer.setVisible(true);
		mTviewer.blankEverything();
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
		
		//decide seats
		decideSeats();
		
		
		//play one game
		mCurrentGame = new Game(mTviewer, mPlayerArray);
		mCurrentGame.play();
		
	}
	
	
	
	private void __generatePlayers(){
		
		//creates a new player to sit at each seat
		p1 = new Player(Player.SEAT_EAST);
		p2 = new Player(Player.SEAT_SOUTH);
		p3 = new Player(Player.SEAT_WEST);
		p4 = new Player(Player.SEAT_NORTH);
		mPlayerArray = new Player[]{p1, p2, p3, p4};
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
	private void decideSeats(){

		//figure out how many humans are playing
		int numHumans = 0;
		if (DEBUG_DO_SINGLE_PLAYER_GAME)
			numHumans = 1;
		else{
			@SuppressWarnings("resource")
			Scanner keyboard = new Scanner(System.in);
			System.out.println("How many humans will be playing? (Enter 1-4): ");
			numHumans = keyboard.nextInt();
		}
		
		
		//add the requested number of humans to the list of controllers
		ArrayList<Character> controllers = new ArrayList<Character>(NUM_PLAYERS);
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

		//assign the controllers to seats
		p1.setController(controllers.get(0));
		p2.setController(controllers.get(1));
		p3.setController(controllers.get(2));
		p4.setController(controllers.get(3));
		
		//set names
		p1.setPlayerName("Suwado");
		p2.setPlayerName("Albert");
		p3.setPlayerName("Brenda");
		p4.setPlayerName("Carl");
	}
	
	
	
	//accessors
	public int getGameType(){return mCurrentGame.getGameType();}
//	public boolean gameIsOver(){return mCurrentGame.gameIsOver();}
	
	
	
	
	
	
	
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
