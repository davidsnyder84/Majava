package majava.control.testcode;

import java.util.ArrayList;
import java.util.List;

import majava.enums.MeldType;
import majava.hand.AgariHand;
import majava.yaku.ToitoiCheck;

public class YakuTester {
	
	
	
	public static void main(String[] args){
//		showMeManyAgariHands();
		
		
		println(toitoiTester()+"");
//		toitoiTesterSpecific();
		
	}
	
	
	
	
	
	public static void toitoiTesterSpecific(){
		AgariHand ah = YakuGenerator.generateToitoiHand();
		ToitoiCheck checker = new ToitoiCheck(ah);
		
		println(ah.toString() + checker.calculateElligibleYaku().toString());
	}
	
	
	public static boolean toitoiTester(){
		int trials = 500;
		List<AgariHand> falseNegatives = new ArrayList<AgariHand>();
		List<AgariHand> falsePositives = new ArrayList<AgariHand>();
		
		Majenerator.optionForbidChiitoiAndKokushi();
		
		
		for (int i=0; i<trials; i++){
			AgariHand ah = Majenerator.generateAgariHandOnlyPonkan();
			ToitoiCheck checker = new ToitoiCheck(ah);
//			println(ah.toString() + checker.calculateElligibleYaku().toString());
			
			if (!checker.handIsToitoi()) falseNegatives.add(ah);
		}
		
		for (int i=0; i<trials; i++){
			AgariHand ah = Majenerator.generateAgariHandOnlyChi();
			ToitoiCheck checker = new ToitoiCheck(ah);
//			println(ah.toString() + checker.calculateElligibleYaku().toString());
			
			if (checker.handIsToitoi()) falsePositives.add(ah);
		}
		
		return failuresDetected(falseNegatives, falsePositives);
		
		
//		for (AgariHand ah: generateManyAgariHands(100)){
//			ToitoiCheck checker = new ToitoiCheck(ah);
//			println(ah.toString() + checker.calculateElligibleYaku().toString());
//		}
		
	}
	
	
	private static boolean failuresDetected(List<AgariHand> falseNegatives, List<AgariHand> falsePositives){
		
		if (!falseNegatives.isEmpty()){
			println("---------FALSE NEGATIVE DETECTED--------");
			for (AgariHand ah: falseNegatives) println(ah.toString());
		}
		
		if (!falsePositives.isEmpty()){
			println("\n\n\n\n+++++++++++FALSE POSITIVE DETECTED+++++++++");
			for (AgariHand ah: falsePositives) println(ah.toString());
		}
		
		return (!falseNegatives.isEmpty() || !falsePositives.isEmpty());
	}
	
	
	
	
	
	
	
	public static List<AgariHand> generateManyAgariHands(int howMany){
		List<AgariHand> agariHands = new ArrayList<AgariHand>();
		for (int i=0; i<howMany; i++) agariHands.add(Majenerator.generateAgariHand());
		
		return agariHands;
	}
	public static List<AgariHand> generateManyAgariHands(){return generateManyAgariHands(100);}
	
	
	public static void showMeManyAgariHands(){
		for (AgariHand ah: generateManyAgariHands(100))
			println(ah.toString());
	}
	
	
	public static void println(String prints){System.out.println(prints);}public static void println(){println("");}public static void print(String prints){System.out.print(prints);}	
}
