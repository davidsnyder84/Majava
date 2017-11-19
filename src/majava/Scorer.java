package majava;

import majava.hand.AgariHand;
import majava.player.Player;
import majava.summary.PaymentMap;
import majava.yaku.YakuAnalyzer;


//class for calculating point amounts and payments
public class Scorer {
	
	private final RoundResult roundResult;
	private final RoundTracker roundTracker;
	
	public Scorer(RoundResult result, RoundTracker tracker) {
		roundResult = result;
		roundTracker = tracker;
	}
	
	
	
	
	public PaymentMap getPaymentMap(){
		final int PAYMENT_DUE = 8000;
		int paymentDue = PAYMENT_DUE;
		
		if (roundResult.isDraw())
			return mapPaymentsForDraw();
		else
			return mapPaymentsForWin(paymentDue);
	}
	
	
	private PaymentMap mapPaymentsForWin(int handValue){
		PaymentMap payments = new PaymentMap();
		
		final double DEALER_WIN_MULTIPLIER = 1.5, DEALER_TSUMO_MULTIPLIER = 2;
		
		int paymentDue = handValue;
		int tsumoPointsNonDealer = paymentDue / 4;
		int tsumoPointsDealer = (int) (DEALER_TSUMO_MULTIPLIER * tsumoPointsNonDealer);
		
		
		//find who the winner is
		Player winner = roundResult.getWinningPlayer();
		Player[] losers = {roundTracker.neighborShimochaOf(winner), roundTracker.neighborToimenOf(winner), roundTracker.neighborKamichaOf(winner)};
		Player furikonda = null;
		
		if (winner.isDealer()) paymentDue *= DEALER_WIN_MULTIPLIER;
		
		///////add in honba here
		
		payments.put(winner, paymentDue);
		
		
		//find who has to pay
		if (roundResult.isVictoryRon()){
			furikonda = roundResult.getFurikondaPlayer();
			for (Player p: losers)
				if (p == furikonda) payments.put(p, -paymentDue);
				else payments.put(p, 0);
		}
		else{//tsumo
			for (Player p: losers){
				if (p.isDealer() || winner.isDealer()) payments.put(p, -tsumoPointsDealer);
				else  payments.put(p, -tsumoPointsNonDealer);
			}
		}
		///////add in riichi sticks here
		return payments;
	}
	
	private PaymentMap mapPaymentsForDraw(){
		PaymentMap payments = new PaymentMap();
		/////implement no-ten bappu here 
		
		payments.put(roundTracker.currentPlayer(), 0);
		payments.put(roundTracker.neighborShimochaOf(roundTracker.currentPlayer()), 0);
		payments.put(roundTracker.neighborToimenOf(roundTracker.currentPlayer()), 0);
		payments.put(roundTracker.neighborKamichaOf(roundTracker.currentPlayer()), 0);
		
		return payments;
	}
	
	
	
	
	/////driveby/demo
	public void printWinningYaku() {
		if (!roundResult.isVictory()) return;
		
		AgariHand agariHand = new AgariHand(roundResult.getWinningPlayer().DEMOgetHand(), roundResult.getWinningTile());
		System.out.println(agariHand.toString());
		
		YakuAnalyzer yakuana = new YakuAnalyzer(roundResult.getWinningPlayer().DEMOgetHand(), roundResult);
		System.out.println("YAKUANALYZERSAYS: "  + yakuana.getYakuList());
	}
}
