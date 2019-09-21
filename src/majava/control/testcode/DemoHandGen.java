package majava.control.testcode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import majava.hand.AgariHand;
import majava.hand.Hand;
import majava.hand.Meld;
import majava.enums.MeldType;
import majava.enums.Wind;
import majava.tiles.GameTile;
import majava.tiles.TileInterface;
import majava.util.GTL;



//code for testing HandChecker functions
public class DemoHandGen {
	
	public static final Wind OWNER_SEAT = Wind.SOUTH;
	
	public static Random randGen = new Random();
	
	
	
	public static void main(String[] args) {
		
//		runTenpaiSimulation(5000);
//		runSingleTenpaiTest(generateSpecificHand());
		runSimulation(5000);
		
		
		
//		runSimulationNoDisplay(50000);
//		runSumulationRandom(300000);
//		runSpecificTest();
		
//		runTenpaiSimulation(500);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	static method: runSimulation
	
	generates a number of complete hands, and runs them through the HandChecker completeness checker
	keeps track of when HandChecker gives a wrong answer
	*/
	public static void runSimulation(int howManyTimes){
		int numFailures = 0;
		int totalNum = 0;
		
		for (int i = 0; i < howManyTimes; i++){
			
			Hand currentHand = generateCompleteHand().sort();
			
			println(currentHand.toString());
			
			boolean success = currentHand.isComplete();
			System.out.println(currentHand.getAsStringMeldsCompact());
			System.out.println("Hand is complete?: " + success);
			
			if (success == false){
				numFailures++;
//				System.out.println(currentHand.toString());
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			else{
				printFinishingMeldsFor(currentHand);
				;
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
			if (!(currentHand = generateCompleteHand()).isComplete()){
				numFailures++;
				System.out.println(currentHand.toString() + "\n");
				
			}
		}
		

		System.out.println("Total number of trials: " + howManyTimes);
		System.out.println("Total number of failures: " + numFailures);
	}
	

	
	
	
	
	
	public static void runSingleTenpaiTest(Hand hand){
		System.out.println("\n" + hand + "\n");
		
		GTL waits = hand.getTenpaiWaits();
		
		String waitString = "";
		for (GameTile t: waits) waitString += t + ", ";
		System.out.println("Waits: " + waitString);
		System.out.println(waits.toString());

		printFinishingMeldsFor(hand);
	}
	
	/*
	static method: runTenpaiSimulation
	
	generates a number of tenpai hands, and runs them through the HandChecker tenpai checker
	keeps track of when HandChecker gives a wrong answer
	*/
	public static void runTenpaiSimulation(int howManyTimes){
		int numFailures = 0;
		
		GTL maxWaits = new GTL();
		Hand maxWaitsHand = null;
		int maxNumWaits = 0;
		String maxWaitString = "";
		
		
		
		for (int i = 0; i < howManyTimes; i++){
			
			Hand currentHand = generateTenpaiHand();

			println(currentHand.toString() + "\n");
			
			GTL waits = currentHand.getTenpaiWaits();
			
			
			print("Waits: ");
			String waitString = "";
			for (GameTile t: waits) waitString += t.toString() + ", ";
			
			
			if (waits.isEmpty()){
				println(currentHand.toString() + "\n");
				numFailures++;
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			else{
				System.out.println(waitString); 
				if (waits.size() > maxNumWaits && waits.size() != 13){
					maxWaits = waits;
					maxWaitsHand = currentHand;
					maxWaitString = waitString;
					maxNumWaits = waits.size();
				}
				printFinishingMeldsFor(currentHand);
			}
			System.out.println("\n\n");
		}
		
		

		System.out.println("Total number of trials: " + howManyTimes);
		System.out.println("Total number of failures: " + numFailures);
		
		System.out.println("\nMax Waits: (" + maxWaits.size() + ")");
		System.out.println(maxWaitsHand.toString() + "\n");
		System.out.println(maxWaitString);
	}
	
	
	
	
	
	//returns a hand that is tenpai
	public static Hand generateTenpaiHand(){
		
		Hand hand = generateCompleteHand();
		hand = hand.removeTile(randGen.nextInt(hand.size()));
		return hand;
		
//		return generateCompleteHand().removeTile(randGen.nextInt(hand.size()));
	}
	//returns a hand that is complete
	public static Hand generateCompleteHand(){
		
		GTL listGT = Majenerator.generateWinningHandTiles().withWind(OWNER_SEAT);
		Hand hand = new Hand(listGT).setOwnerSeatWind(OWNER_SEAT).sort();
		
		return hand;
	}
	
	
	
	
	
	/*
	static method: runSumulationRandom
	
	generates a number of random hands, and runs them through the HandChecker completeness checker
	keeps track of when HandChecker identifies a complete hand
	*/
	public static void runSumulationRandom(int howManyTimes){
		
		Hand currentHand = null;
		int numHits = 0;
		
		
		
		for (int i = 0; i < howManyTimes; i++){
			
			currentHand = generateRandomHand();
			currentHand.sort();
			
			if (currentHand.isComplete()){
				numHits++;
				System.out.println("\n\n" + currentHand.toString());
				printFinishingMeldsFor(currentHand);
				currentHand.getFinishingMelds();
			}
		}
		
		

		System.out.println("\n\nTotal number of trials: " + howManyTimes);
		System.out.println("Total number of hits: " + numHits);
		
	}
	
	public static Hand generateRandomHand(){
		GTL tiles = new GTL();
		final int numMeldsMade = randGen.nextInt(3);
		
		while (tiles.size() < 14 - 3*numMeldsMade){
			//generate a random tile
			int id = randGen.nextInt(34) + 1;
			
			if (tiles.findHowManyOf(new GameTile(id)) < 4)
				tiles = tiles.add(new GameTile(id).withOwnerWind(OWNER_SEAT));
		}
		
		
		Hand hand = new Hand(tiles).setOwnerSeatWind(OWNER_SEAT).sort();
//		System.out.println(hand.toString());
		
		return hand;
	}
	
	public static void printFinishingMeldsFor(Hand h){
//		List<Meld> fMelds = hc.getFinishingMelds();
		for (Meld m: h.getFinishingMelds())
			System.out.println(m.toString());
	}
	
	
	
	public static void runSpecificTest(){
		Hand hand = generateSpecificHand();
		System.out.println("\n" + hand.toString());
		
		System.out.println("Hand is complete normal?: " + hand.isCompleteNormal());
		printFinishingMeldsFor(hand);
		
		System.out.println("\n\n\n");
//		System.out.println("Hand is tenpai:? " + "\n" + checker.DEMOfindNormalTenpaiWaits());
		System.out.println("Hand is tenpai:? " + "\n" + hand.getTenpaiWaits());
		
	}
	
	public static Hand generateSpecificHand(){
		Hand hand = new Hand();
//		GTL tiles = new GTL(19,19,19,20,21,21,22,22,23,32,32,32,34,34);	//S1 S1 S1 S2 S3 S3 S4 S4 S5 DW DW DW DR DR
//		GTL tiles = new GTL(21,22,23,32,32,32,34,34);
		GTL tiles = new GTL(2+9,3+9,4+9,1+18,1+18);	//P2 P3 P4 S1 S1
//		GTL tiles = new GTL(1,1,2+9,3+9,4+9);	//P2 P3 P4 S1 S1
//		GTL tiles = new GTL(3,7,8,9,4+18,5+18,6+18);	//M3 M7 M8 M9 S4 S5 S6
//		GTL tiles = new GTL(1,2,2,3,3,4,7+9,9+9,18+5,18+5);	//
//		GTL tiles = new GTL(1,1,1,2,3,4,5,6,7,8,9,9,9);	//tenpai chuuren
//		GTL tiles = new GTL(2,2,8+9,8+9,6+18,6+18,33,33);	//error case
		
		
		for (GameTile t: tiles){
			GameTile addThis = t.withOwnerWind(OWNER_SEAT);
			System.out.println("Adding " + addThis);
			
			hand = hand.addTile(addThis);
//			System.out.println(hand.toString());
		}
		
		return hand.sort();
	}
	
	public static void println(String prints){System.out.println(prints);}public static void println(){println("");}public static void print(String prints){System.out.print(prints);}

}
