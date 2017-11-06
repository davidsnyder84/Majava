package majava.control;

import majava.Table;
import majava.userinterface.graphicalinterface.window.MajavaWelcome;



/*
Class: Majava
main class to drive the program

data:
	mWelcomeMenu - a welcome menu to get the user's choice
	
methods:
	start - start the program
*/
public class Majava {
	
	
	private MajavaWelcome mWelcomeMenu;
	private Table mTable;
	
	
	public Majava(){
		mWelcomeMenu = new MajavaWelcome();
		mTable = null;
	}
	
	
	//start the program
	public void start(){
		
		System.out.println("Welcome to Majava!\n\n\n\n");
		
		boolean doSinglePlayer = true;
		boolean doFastGameplay = false;
		
		
		//get options
		mWelcomeMenu.setVisible(true);
		mWelcomeMenu.waitForChoice();
		doSinglePlayer = mWelcomeMenu.choseSinglePlayer();
		doFastGameplay = mWelcomeMenu.choseFastGameplay();
		
		
		//play the game
		mTable = new Table();
		mTable.setOptionSinglePlayerMode(doSinglePlayer);
		mTable.setOptionFastGameplay(doFastGameplay);
		
		mTable.play();
	}
	
	
	
	
	
	public static void main(String[] args) {
		Majava majavaGame = new Majava();
		majavaGame.start();
	}

}
