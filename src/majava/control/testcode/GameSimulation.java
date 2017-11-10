package majava.control.testcode;

import majava.Table;

public class GameSimulation {
	
	public static void main(String[] args) {

		System.out.println("Welcome to Simulation!\n\n\n\n");
		boolean doSinglePlayer = false;
		boolean doFastGameplay = true;
		
		Table table = new Table();
		table.setOptionSinglePlayerMode(doSinglePlayer);
		table.setOptionFastGameplay(doFastGameplay);
		table.play();
	}

}
