package majava.control.testcode;

import java.util.ArrayList;
import java.util.List;

import majava.enums.MeldType;
import majava.hand.AgariHand;
import majava.util.YakuList;
import majava.yaku.KotsuYakuCheck;
import majava.yaku.MenzenTsumoCheck;
import majava.yaku.TerminalHonorYakuCheck;
import majava.yaku.Yaku;
import majava.yaku.YakuAnalyzer;

public class YakuTester {
	
	
	
	public static void main(String[] args){
//		showMeManyAgariHands();
		
		
//		println(toitoiTester()+"");
//		toitoiTesterSpecific();
		
		
		showMeSomeYakuLists();
//		chantaTestSpecific();
		
		
		
		
		
		
		println("done");
	}
	
	
	
	
	public static void showMeSomeYakuLists(){
		List<AgariHand> hands = generateManyAgariHands(10000);
		for (AgariHand h: hands){
			YakuAnalyzer yakuAnalyzer = new YakuAnalyzer(h);
			YakuList yl = yakuAnalyzer.getAllElligibleYaku();
//			yl.remove(Yaku.MENZEN_TSUMO);
			yl.removeAll(easyyaku);
			yl.removeAll(probablyEasyyaku);
			if (!yl.isEmpty())
				println(h.toString() + yl.toString() +"--------------\n\n\n");
//			println(h.toString());
		}
			
	}
	
	static YakuList easyyaku = new YakuList(Yaku.YAKUHAI_DRAGON_CHUN, Yaku.YAKUHAI_DRAGON_HAKU, Yaku.YAKUHAI_DRAGON_HATSU, Yaku.YAKUHAI_WIND_SOUTH, Yaku.TANYAO, Yaku.CHIITOITSU, Yaku.YKM_KOKUSHI);
	static YakuList probablyEasyyaku = new YakuList(Yaku.SANANKOU, Yaku.YKM_SUUANKOU, Yaku.HONITSU, Yaku.CHINITSU, Yaku.TOITOI, Yaku.SANKANTSU, Yaku.YKM_SUUKANTSU, Yaku.YKM_RYUUIISOU, Yaku.SHOUSANGEN, Yaku.YKM_DAISANGEN);
	
	
	
	
	public static void chantaTestSpecific(){
		AgariHand hand = Majenerator.agariHandFromIDs(1,2,3,  7,8,9,  9+1,9+2,9+3,  29,29,29,  34,34);
		
//		println(hand.toString());
		TerminalHonorYakuCheck chantaChecker = new TerminalHonorYakuCheck(hand);
		println(chantaChecker.handIsChanta()+"");
		chantaChecker.handIsChanta();
		println(chantaChecker.getElligibleYaku().toString());
		
		println((new MenzenTsumoCheck(hand)).getElligibleYaku().toString());
	}
	
	
	
	public static void toitoiTesterSpecific(){
		AgariHand ah = YakuGenerator.generateToitoiHandSpecific();
		KotsuYakuCheck checker = new KotsuYakuCheck(ah);
		
		println(ah.toString() + checker.getElligibleYaku().toString());
	}
	
	
	public static boolean toitoiTester(){
		int trials = 50000;
		List<AgariHand> falseNegatives = new ArrayList<AgariHand>();
		List<AgariHand> falsePositives = new ArrayList<AgariHand>();
		
		Majenerator.optionForbidChiitoiAndKokushi();
		
		
		for (int i=0; i<trials; i++){
			AgariHand ah = Majenerator.generateAgariHandOnlyPonkan();
			KotsuYakuCheck checker = new KotsuYakuCheck(ah);
//			println(ah.toString() + checker.calculateElligibleYaku().toString());
			
			if (!checker.handIsToitoi()) falseNegatives.add(ah);
		}
		
		for (int i=0; i<trials; i++){
			AgariHand ah = Majenerator.generateAgariHandOnlyChi();
			KotsuYakuCheck checker = new KotsuYakuCheck(ah);
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
	
	
	
	
	
	public static boolean runAllTests(){
		boolean failureDetected = false;
		failureDetected |= toitoiTester();
		
		
		
		
		
		return failureDetected;
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
