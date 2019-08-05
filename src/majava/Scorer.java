package majava;

import java.util.ArrayList;
import java.util.List;

import majava.player.Player;
import majava.summary.PaymentMap;
import majava.util.YakuList;
import majava.yaku.YakuAnalyzer;


//class for calculating point amounts and payments
public class Scorer {
	private static final int NUM_PLAYERS = 4;
	
	private final RoundResult roundResult;
	private final Player[] players;
	
	public Scorer(RoundResult result, Player[] playerArray) {
		roundResult = result;
		players = playerArray;
	}
	
	
	
	
	public PaymentMap getPaymentMap(){
		final int PAYMENT_DUE = 8000;
		int paymentDue = PAYMENT_DUE;
		
		if (roundResult.isRyuukyoku())
			return mapPaymentsForRyuukyoku();
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
		List<Player> losers = playersOtherThan(winner);
		Player furikonda = null;
		
		if (winner.isDealer()) paymentDue *= DEALER_WIN_MULTIPLIER;
		
		///////add in honba here
		
		payments.put(winner, paymentDue);
		
		
		//find who has to pay
		if (roundResult.isVictoryRon()){
			furikonda = roundResult.getFurikondaPlayer();
			for (Player p: losers)
				if (p == furikonda)
					payments.put(p, -paymentDue);
				else
					payments.put(p, 0);
		}
		else{//tsumo
			for (Player p: losers){
				if (p.isDealer() || winner.isDealer())
					payments.put(p, -tsumoPointsDealer);
				else 
					payments.put(p, -tsumoPointsNonDealer);
			}
		}
		///////add in riichi sticks here
		return payments;
	}
	private List<Player> playersOtherThan(Player excludeMe){
		List<Player> others = new ArrayList<Player>();
		for (Player p: players)
			if (p != excludeMe)
				others.add(p);			
		return others;
	}
	
	private PaymentMap mapPaymentsForRyuukyoku(){
		PaymentMap payments = new PaymentMap();
		/////implement no-ten bappu here 		
		
		//map everyone to 0 points
		for (Player p: players)
			payments.put(p, 0);
//		for (int playerNum = 0; playerNum < NUM_PLAYERS; playerNum++)
//			payments.put(roundTracker.getSummaryForPlayer(playerNum), 0);
		
		return payments;
	}
	
	
	public YakuList getYakuOfWinningHand(){
		if (!roundResult.isVictory())
			return new YakuList();
		
		YakuAnalyzer yakuAnalyzer = new YakuAnalyzer(roundResult.getWinningPlayer().getHand(), roundResult);
		return yakuAnalyzer.getAllElligibleYaku();
		
		//could do it this way instead, probably bad idea
//		AgariHand agariHand = new AgariHand(roundResult.getWinningPlayer().getHand(), roundResult.getWinningTile());
//		YakuAnalyzer analyzerFromAgarihand = new YakuAnalyzer(agariHand);
	}
}
