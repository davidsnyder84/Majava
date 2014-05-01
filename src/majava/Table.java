package majava;

import java.util.ArrayList;
import java.util.Scanner;

import majava.graphics.TableViewer;

import utility.GenSort;
import utility.Pauser;




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
		
		//decide seats
		__decideSeats();
		
		//show table window
		mTviewer.setVisible(true);
		
		//play one game
		mCurrentGame = new Game(mTviewer, mPlayerArray);
		mCurrentGame.setOptionFastGameplay(mDoFastGameplay);
		mCurrentGame.play();
		
		//close the window
		Pauser.pauseFor(3000);
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
		boolean keepgoing = true;
		
		if (keepgoing){
			System.out.println("\n\n\n\n");
			
			Table table = new Table();
			
			table.setOptionFastGameplay(true);
			table.setOptionSinglePlayerMode(false);
			table.play();
		}
	}
	

}
