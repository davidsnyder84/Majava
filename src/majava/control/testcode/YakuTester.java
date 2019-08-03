package majava.control.testcode;

import java.util.ArrayList;
import java.util.List;

import majava.hand.AgariHand;
import majava.yaku.ToitoiCheck;

public class YakuTester {
	
	
	
	public static void main(String[] args){
		showMeManyAgariHands();
		
		
//		toitoiTester();
//		toitoiTesterSpecific();
		
	}
	
	
	
	
	
	public static void toitoiTesterSpecific(){
		AgariHand ah = YakuGenerator.generateToitoiHand();		
		ToitoiCheck checker = new ToitoiCheck(ah);
		
		println(ah.toString() + checker.calculateElligibleYaku().toString());
	}
	
	
	public static void toitoiTester(){
		for (AgariHand ah: generateManyAgariHands(100)){
			ToitoiCheck checker = new ToitoiCheck(ah);
			println(ah.toString() + checker.calculateElligibleYaku().toString());
		}
		
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
