package majava.control.testcode;


import java.util.ArrayList;
import java.util.Random;

import majava.Hand;
import majava.MeldType;
import majava.Wind;
import majava.tiles.Tile;
import majava.TileList;



/*
Class: DemoHandGen
code for testing HandChecker functions
*/
public class DemoHandGen {
	
	public static final Wind OWNER_SEAT = Wind.SOUTH;
	
	public static Random random;
	
	
	
	public static void main(String[] args) {

		random = new Random();
//		runTenpaiSimulation(5000);
//		runSimulationNoDisplay(25000);
//		runSumulationRandom(90000);
//		runSpecificTest();
		
		runSimulation(20000);
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
			currentHand.showMeldsCompact();
			System.out.println("Hand is complete normal?: " + success);
			
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
			if (!(currentHand = generateCompleteHand()).DEMOgetChecker().isCompleteNormal()){
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
		TileList waits = null;
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
			for (Tile t: waits) waitString += t.toString() + ", ";
			
			
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

		int removeIndex = random.nextInt(hand.getSize());
		hand.removeTile(removeIndex);
		
		return hand; 
	}
		
	
	//returns a hand that is complete
	public static Hand generateCompleteHand(){
		
		Hand hand = new Hand(OWNER_SEAT);
		
		
		TileList tiles = new TileList();
		Tile currentTile = null;
		
		
		int id = 0;
		
		int meldWhich = -1;
		MeldType chosenMeld;
		ArrayList<MeldType> validMelds = null;
		
		int howManyOfThisInHand = -1;
		
		TileList currentMeldTiles = null;
		
		
		int numSuccessfulMelds = 0;
		boolean tooMany = false;
		
		
		int numAlreadyCompletedMelds = 0;
		numAlreadyCompletedMelds = random.nextInt(5);
		
		//add 4 melds
		while (numSuccessfulMelds < numAlreadyCompletedMelds){
			
			//generate a random tile
			id = random.nextInt(34) + 1;
			currentTile = new Tile(id);
			currentTile.setOwner(OWNER_SEAT);
			
			howManyOfThisInHand = tiles.findHowManyOf(currentTile);
			if (howManyOfThisInHand < 4){
				
				validMelds = new ArrayList<MeldType>(5);
				if (!currentTile.isHonor()){
					if (currentTile.getFace() != '8' && currentTile.getFace() != '9') validMelds.add(MeldType.CHI_L);
					if (currentTile.getFace() != '1' && currentTile.getFace() != '9') validMelds.add(MeldType.CHI_M);
					if (currentTile.getFace() != '1' && currentTile.getFace() != '2') validMelds.add(MeldType.CHI_H);
				}
				if (howManyOfThisInHand <= 1) validMelds.add(MeldType.PON);
				
				meldWhich = random.nextInt(validMelds.size());
				chosenMeld = validMelds.get(meldWhich);
				
				
				//add the tiles to the meld list
				currentMeldTiles = new TileList(3);
				switch (chosenMeld){
				case CHI_L: currentMeldTiles.add(id); currentMeldTiles.add(id + 1); currentMeldTiles.add(id + 2); break;
				case CHI_M: currentMeldTiles.add(id - 1); currentMeldTiles.add(id); currentMeldTiles.add(id + 1); break;
				case CHI_H: currentMeldTiles.add(id - 2); currentMeldTiles.add(id - 1); currentMeldTiles.add(id); break;
				case PON: currentMeldTiles.add(id); currentMeldTiles.add(id); currentMeldTiles.add(id); break;
				default: break;
				}
				
				
				
				tooMany = false;
				for (Tile t: currentMeldTiles) if (tiles.findHowManyOf(t) + 1 > 4) tooMany = true;
				
				if (tooMany == false){
					//add the tiles to the hand
					for (Tile t: currentMeldTiles){
						t.setOwner(OWNER_SEAT);
						hand.addTile(t);
					}
					numSuccessfulMelds++;
				}
				
				
				
			}
		}
		
		//add a pair
		
		do{
			//generate a random tile
			id = random.nextInt(34) + 1;
			currentTile = new Tile(id);
			currentTile.setOwner(OWNER_SEAT);
			howManyOfThisInHand = tiles.findHowManyOf(currentTile);
		}
		while(howManyOfThisInHand > 2);

		currentMeldTiles = new TileList(2);
		currentMeldTiles.add(id); currentMeldTiles.add(id);
		
		//add the tiles to the hand
		for (Tile t: currentMeldTiles){
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
		TileList tiles = new TileList();
		Tile currentTile = null;
		int id = 0;
		int numMeldsMade = random.nextInt(3);
		
		
		
		while (tiles.size() < 14 - 3*numMeldsMade){
			//generate a random tile
			id = random.nextInt(34) + 1;
			
			if (tiles.findHowManyOf(id) < 4){
				currentTile = new Tile(id);
				currentTile.setOwner(OWNER_SEAT);
				tiles.add(currentTile);
			}
		}
		
		for (Tile t: tiles) hand.addTile(t);
		
		hand.sortHand();
//		System.out.println(hand.toString());
		
		return hand;
	}
	
	
	
	
	
	
	public static void runSpecificTest(){
		
		Hand hand = generateSpecificHand();
		
		
		System.out.println(hand.toString());
		System.out.println("Hand is complete normal?: " + hand.DEMOgetChecker().isCompleteNormal());
		
	}
	
	public static Hand generateSpecificHand(){
		Hand hand = new Hand(OWNER_SEAT);
		TileList tiles = new TileList(19,19,19,20,21,21,22,22,23,32,32,32,34,34);
//		TileList tiles = new TileList(21,22,23,32,32,32,34,34);
		
		for (Tile t: tiles){
			t.setOwner(OWNER_SEAT);
			hand.addTile(t);
		}
		//S1 S1 S1 S2 S3 S3 S4 S4 S5 DW DW DW DR DR
		
		hand.sortHand();
		return hand;
	}
	
	

}
