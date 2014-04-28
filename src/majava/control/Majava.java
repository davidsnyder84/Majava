package majava.control;

import majava.Table;
import majava.graphics.MajavaWelcome;




public class Majava {
	
	
	
	//disallow instantiation of this class
	private Majava(){}
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava!");
		
		boolean doSinglePlayer = true;
		boolean doFastGameplay = false;
		
		MajavaWelcome welcomeMenu = new MajavaWelcome();
		
		
		//get options
		welcomeMenu.setVisible(true);
		welcomeMenu.waitForChoice();
		doSinglePlayer = welcomeMenu.choseSinglePlayer();
		doFastGameplay = welcomeMenu.choseFastGameplay();
		
		
		System.out.println("\n\n\n\n");
		
		
		
		//play the game
		Table table = new Table();
		table.setOptionSinglePlayerMode(doSinglePlayer);
		table.setOptionFastGameplay(doFastGameplay);
		
		table.play();
	}

}
