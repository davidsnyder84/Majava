package majava.control.testcode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import utility.ConviniList;

import majava.Hand;
import majava.HandChecker;
import majava.enums.MeldType;
import majava.enums.Wind;
import majava.tiles.GameTile;
import majava.tiles.ImmutableTile;
import majava.tiles.TileInterface;
import majava.util.TileInterfaceList;



/*
Class: DemoHandGen
code for testing HandChecker functions
*/
public class DemoHandGen {
	
	public static final Wind OWNER_SEAT = Wind.SOUTH;
	
	public static Random randGen = new Random();
	
	
	
	public static void main(String[] args) {
		
//		runTenpaiSimulation(5000);
//		runSimulationNoDisplay(5000);
//		runSumulationRandom(15000);
//		runSpecificTest();
		
		runSimulation(5000);
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	static method: runSimulation
	
	generates a number of complete hands, and runs them through the HandChecker completeness checker
	keeps track of when HandChecker gives a wrong answer
	*/
	public static void runSimulation(int howManyTimes){
		
		Hand currentHand = null;
		boolean success = true;
		int numFailures = 0;
		int totalNum = 0;
		
		for (int i = 0; i < howManyTimes; i++){
			
			currentHand = generateCompleteHand();

			System.out.println(currentHand.toString() + "\n");
			
			success = currentHand.DEMOgetChecker().DEMOisComplete();
			System.out.println(currentHand.getAsStringMeldsCompact());
			System.out.println("Hand is complete?: " + success);
			
			if (success == false){
				numFailures++;
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			
			System.out.println("\n\n");
			totalNum++;
		}
		
		

		System.out.println("Total number of trials: " + totalNum);
		System.out.println("Total number of failures: " + numFailures);
	}
	
	
	
	public static void runSimulationNoDisplay(int howManyTimes){
		
		Hand currentHand = null;
		int numFailures = 0;
		
		
		for (int i = 0; i < howManyTimes; i++){
			if (!(currentHand = generateCompleteHand()).DEMOgetChecker().isComplete()){
				numFailures++;
				System.out.println(currentHand.toString() + "\n");
				
			}
		}
		

		System.out.println("Total number of trials: " + howManyTimes);
		System.out.println("Total number of failures: " + numFailures);
	}
	
	
	
	/*
	static method: runTenpaiSimulation
	
	generates a number of tenpai hands, and runs them through the HandChecker tenpai checker
	keeps track of when HandChecker gives a wrong answer
	*/
	public static void runTenpaiSimulation(int howManyTimes){
		
		Hand currentHand = null;
		int numFailures = 0;
		List<TileInterface> waits = null;
		String waitString = "";

//		TileList maxWaits = null;
		Hand maxWaitsHand = null;
		int maxNumWaits = 0;
		String maxWaitString = "";
		
		
		
		for (int i = 0; i < howManyTimes; i++){
			
			currentHand = generateTenpaiHand();

			System.out.println(currentHand.toString() + "\n");
			
			waits = currentHand.DEMOgetChecker().DEMOfindTenpaiWaits();
			
			
			System.out.print("Waits: ");
			waitString = "";
			for (TileInterface t: waits) waitString += t.toString() + ", ";
			
			
			if (waits.isEmpty()){
				numFailures++;
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			else{
				System.out.println(waitString);
				if (waits.size() > maxNumWaits){
//					maxWaits = waits;
					maxWaitsHand = currentHand;
					maxWaitString = waitString;
					maxNumWaits = waits.size();
				}
			}
			System.out.println("\n\n");
		}
		
		

		System.out.println("Total number of trials: " + howManyTimes);
		System.out.println("Total number of failures: " + numFailures);
		
		System.out.println("\nMax Waits: ");
		System.out.println(maxWaitsHand.toString() + "\n");
		System.out.println(maxWaitString);
	}
	
	
	
	
	
	//returns a hand that is tenpai
	public static Hand generateTenpaiHand(){
		
		Hand hand = generateCompleteHand();
		
		hand.removeTile(randGen.nextInt(hand.size()));
		return hand; 
	}
	//returns a hand that is complete
	public static Hand generateCompleteHand(){

		Hand hand = new Hand(OWNER_SEAT);
		List<GameTile> listGT = TileInterfaceList.toGameTiles(Majenerator.generateWinningHandTiles());
		
		
		for (GameTile t: listGT){
			t.setOwner(OWNER_SEAT);
			hand.addTile(t);
		}
		
		hand.sortHand();
		return hand;
	}
	
	
	
	
	
	
	/*
	static method: runSumulationRandom
	
	generates a number of random hands, and runs them through the HandChecker completeness checker
	keeps track of when HandChecker identifies a complete hand
	*/
	public static void runSumulationRandom(int howManyTimes){
		
		Hand currentHand = null;
//		boolean hit = true;
		int numHits = 0;
		
		
		
		for (int i = 0; i < howManyTimes; i++){
			
			currentHand = generateRandomHand();
			
//			currentHand = new Hand(OWNER_SEAT);
//			currentHand.fill();
			
//			hit = currentHand.mChecker.DEMOisComplete();
			
			if (currentHand.DEMOgetChecker().DEMOisComplete()){
				numHits++;
				System.out.println(currentHand.toString() + "\n");
			}
		}
		
		

		System.out.println("Total number of trials: " + howManyTimes);
		System.out.println("Total number of hits: " + numHits);
		
	}
	
	public static Hand generateRandomHand(){
		
		Hand hand = new Hand(OWNER_SEAT);
		ConviniList<GameTile> tiles = new ConviniList<GameTile>();
		GameTile currentTile = null;
		int id = 0;
		int numMeldsMade = randGen.nextInt(3);
		
		
		
		while (tiles.size() < 14 - 3*numMeldsMade){
			//generate a random tile
			id = randGen.nextInt(34) + 1;
			
			if (tiles.findHowManyOf(new GameTile(id)) < 4){
				currentTile = new GameTile(id);
				currentTile.setOwner(OWNER_SEAT);
				tiles.add(currentTile);
			}
		}
		
		for (GameTile t: tiles) hand.addTile(t);
		
		hand.sortHand();
//		System.out.println(hand.toString());
		
		return hand;
	}
	
	
	
	
	
	
	public static void runSpecificTest(){
		
		Hand hand = generateSpecificHand();
		
		
		System.out.println("\n" + hand.toString());
		
		HandChecker checker = hand.DEMOgetChecker();
		boolean isComplete = checker.DEMOisComplete();
		System.out.println("Hand is complete normal?: " + isComplete);
		
	}
	
	public static Hand generateSpecificHand(){
		Hand hand = new Hand(OWNER_SEAT);
//		TileList tiles = new TileList(19,19,19,20,21,21,22,22,23,32,32,32,34,34);	//S1 S1 S1 S2 S3 S3 S4 S4 S5 DW DW DW DR DR
//		TileList tiles = new TileList(21,22,23,32,32,32,34,34);
//		TileList tiles = new TileList(2+9,3+9,4+9,1+18,1+18);	//P2 P3 P4 S1 S1
		List<GameTile> tiles = new TileInterfaceList(1,1,2+9,3+9,4+9).toGameTiles();	//P2 P3 P4 S1 S1
		
		for (GameTile t: tiles){
			t.setOwner(OWNER_SEAT);
			
			System.out.println("Adding " + t);
			hand.addTile(t);
//			System.out.println(hand.toString());
		}
		
		
		hand.sortHand();
		return hand;
	}
	
	

}
