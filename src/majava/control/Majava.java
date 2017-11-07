package majava.control;

import majava.Table;
import majava.userinterface.graphicalinterface.window.MajavaWelcome;



//main class to drive the program
public class Majava {
	
	
	private MajavaWelcome welcomeMenu;
	private Table table;
	
	
	public Majava(){
		welcomeMenu = new MajavaWelcome();
		table = null;
	}
	
	
	//start the program
	public void start(){
		System.out.println("Welcome to Majava!\n\n\n\n");
		boolean doSinglePlayer = true;
		boolean doFastGameplay = false;
		
		//get options
		welcomeMenu.setVisible(true);
		welcomeMenu.waitForChoice();
		doSinglePlayer = welcomeMenu.choseSinglePlayer();
		doFastGameplay = welcomeMenu.choseFastGameplay();
		
		table = new Table();
		table.setOptionSinglePlayerMode(doSinglePlayer);
		table.setOptionFastGameplay(doFastGameplay);
		table.play();
	}
	
	
	
	public static void main(String[] args) {
		Majava majavaGame = new Majava();
		majavaGame.start();
	}

}
