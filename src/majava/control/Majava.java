package majava.control;

import majava.Table;




public class Majava {
	
	//disallow instantiation of this class
	private Majava(){}
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava!");
		
		
		boolean keepgoing = true;
		
		
		if (keepgoing)
		{
			System.out.println("\n\n\n\n");
			
			Table table = new Table();
			table.play();
		}
		
	}

}
