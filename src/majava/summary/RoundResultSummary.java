package majava.summary;

import java.util.ArrayList;
import java.util.List;

import majava.hand.Meld;
import majava.enums.Wind;
import majava.summary.PlayerSummary;
import majava.summary.ResultType;
import majava.tiles.GameTile;
import majava.util.GTL;
import majava.util.YakuList;


//immutable
public class RoundResultSummary {
	private static final int NUM_PLAYERS = 4;
	
	
	private final ResultType pResultType;

	private final PlayerSummary winningPlayer;
	private final PlayerSummary furikondaPlayer;
	
	private final GameTile winningTile;
	
	
	private final GTL winnerHand;
	private final List<Meld> winnerMelds;
	
	private final PaymentMap payments;
	private final YakuList yakuOfWinner;
	
	
	//win constructor
	public RoundResultSummary(ResultType resType, PlayerSummary winner, PlayerSummary discarder, GameTile winningtile, GTL winnerhand, List<Meld> winnermelds, PaymentMap paymentsMap, YakuList yakus){
		
		pResultType = resType;
		
		if (pResultType.isVictory()){
			winningPlayer = winner;
			furikondaPlayer = discarder;
			winningTile = winningtile.clone();
			
			winnerHand = winnerhand;			
			winnerMelds = winnermelds;
			
			yakuOfWinner = yakus;
		}
		else{
			winningPlayer = null;
			furikondaPlayer = null;
			winningTile = null;
			
			winnerHand = null;
			winnerMelds = null;
			
			yakuOfWinner = new YakuList();
		}
		
		payments = new PaymentMap();
		payments.putAll(paymentsMap);
	}
	//ryuukyoku constructor
	public RoundResultSummary(ResultType resType, PaymentMap paymentsMap){
		this(resType, null, null, null, null, null, paymentsMap, null);
	}
	
	
	public boolean isRyuukyoku(){return pResultType.isRyuukyoku();}
	public boolean isRyuukyokuHowanpai(){return pResultType.isRyuukyokuHowanpai();}
	
	public boolean isVictory(){return pResultType.isVictory();}
	public boolean isVictoryRon(){return pResultType.isVictoryRon();}
	public boolean isVictoryTsumo(){return pResultType.isVictoryTsumo();}
	
	public boolean isDealerVictory(){return (isVictory() && winningPlayer.isDealer());}
	
	
	
	public String getAsStringWinType(){return pResultType.getAsStringWinType();}
	public String getAsStringResultType(){
		String resString = "";
		resString += pResultType.getAsStringResultType();
		
		if (isVictory()) resString += " (" + getAsStringWinType() + ")";
		return resString;
	}
	
	
	public PlayerSummary getWinningPlayer(){return winningPlayer;}
	public PlayerSummary getFurikondaPlayer(){return furikondaPlayer;}
	
	public Wind getWindOfWinner(){if (!isVictory()) return null; return winningPlayer.getSeatWind();}
	public GameTile getWinningTile(){if (!isVictory()) return null; return winningTile.clone();}
	
	
	
	public GTL getWinnerHandTiles(){
		if (!isVictory()) return null;
		return winnerHand;
	}
	public List<Meld> getWinnerMelds(){
		if (!isVictory()) return null; 
		List<Meld> meldsCopy = new ArrayList<Meld>();
		for (Meld m: winnerMelds) meldsCopy.add(m.clone());
		return meldsCopy;
	}
	
	public PaymentMap getPayments(){return payments.clone();}
	public YakuList getYakuOfWinner(){return yakuOfWinner.clone();}
	
}
