package majava.summary;

import java.util.ArrayList;
import java.util.List;

import majava.hand.Meld;
import majava.enums.Wind;
import majava.summary.PlayerSummary;
import majava.summary.ResultType;
import majava.tiles.GameTile;
import majava.util.GameTileList;



public class RoundResultSummary {
	private static final int NUM_PLAYERS = 4;
	
	
	private final ResultType pResultType;

	private final PlayerSummary winningPlayer;
	private final PlayerSummary furikondaPlayer;
	
	private final GameTile winningTile;
	
	
	private final GameTileList winnerHand;
	private final List<Meld> winnerMelds;
	
	private final PaymentMap payments;
	
	
	//win constructor
	public RoundResultSummary(ResultType resType, PlayerSummary winner, PlayerSummary discarder, GameTile winningtile, GameTileList winnerhand, List<Meld> winnermelds, PaymentMap paymentsMap){
		
		pResultType = resType;
		
		if (pResultType.isVictory()){
			winningPlayer = winner;
			furikondaPlayer = discarder;
			winningTile = winningtile.clone();
			
			winnerHand = new GameTileList(winnerhand);
			
			winnerMelds = winnermelds;
		}
		else{
			winningPlayer = null;
			furikondaPlayer = null;
			winningTile = null;
			
			winnerHand = null;
			winnerMelds = null;
		}
		
		payments = new PaymentMap();
		payments.putAll(paymentsMap);
	}
	//draw constructor
	public RoundResultSummary(ResultType resType, PaymentMap paymentsMap){
		this(resType, null, null, null, null, null, paymentsMap);
	}
	
	
	public boolean isDraw(){return pResultType.isDraw();}
	public boolean isDrawWashout(){return pResultType.isDrawWashout();}
	
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
	
	
	
	public GameTileList getWinnerHandTiles(){
		if (!isVictory()) return null;
		return winnerHand.clone();
	}
	public List<Meld> getWinnerMelds(){
		if (!isVictory()) return null; 
		List<Meld> meldsCopy = new ArrayList<Meld>();
		for (Meld m: winnerMelds) meldsCopy.add(m);
		return meldsCopy;
	}
	
	public PaymentMap getPayments(){return new PaymentMap(payments);}
}
