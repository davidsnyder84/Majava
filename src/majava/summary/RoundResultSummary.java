package majava.summary;

import java.util.ArrayList;
import java.util.List;

import majava.Meld;
import majava.enums.Wind;
import majava.summary.PlayerSummary;
import majava.summary.ResultType;
import majava.tiles.TileInterface;



public class RoundResultSummary {
	
	private static final int NUM_PLAYERS = 4;
	
	
	private final ResultType pResultType;

	private final PlayerSummary pWinningPlayer;
	private final PlayerSummary pFurikondaPlayer;
	
	private final TileInterface pWinningTile;
	
	
	private final List<TileInterface> pWinnerHand;
	private final List<Meld> pWinnerMelds;
	
	private final PaymentMap pPayments;
//	private final Map<PlayerSummary,Integer> pPayments;
	
	
	//win constructor
	public RoundResultSummary(ResultType resType, PlayerSummary winningPlayer, PlayerSummary furikondaPlayer, TileInterface winningTile, List<? extends TileInterface> winnerHand, List<Meld> winnerMelds, PaymentMap payments){
		
		pResultType = resType;
		
		if (pResultType.isVictory()){
			pWinningPlayer = winningPlayer;
			pFurikondaPlayer = furikondaPlayer;
			pWinningTile = winningTile.clone();
			
			pWinnerHand = new ArrayList<TileInterface>();
			for (TileInterface t: winnerHand) pWinnerHand.add(t.getTileBase());
			
			pWinnerMelds = winnerMelds;
		}
		else{
			pWinningPlayer = null;
			pFurikondaPlayer = null;
			pWinningTile = null;
			
			pWinnerHand = null;
			pWinnerMelds = null;
		}
		
		pPayments = new PaymentMap();
		pPayments.putAll(payments);
	}
	//draw constructor
	public RoundResultSummary(ResultType resType, PaymentMap payments){
		this(resType, null, null, null, null, null, payments);
	}
	
	
	public boolean isDraw(){return pResultType.isDraw();}
	public boolean isDrawWashout(){return pResultType.isDrawWashout();}
	
	public boolean isVictory(){return pResultType.isVictory();}
	public boolean isVictoryRon(){return pResultType.isVictoryRon();}
	public boolean isVictoryTsumo(){return pResultType.isVictoryTsumo();}
	
	public boolean isDealerVictory(){return (isVictory() && pWinningPlayer.isDealer());}
	
	
	
	public String getAsStringWinType(){return pResultType.getAsStringWinType();}
	public String getAsStringResultType(){
		String resString = "";
		resString += pResultType.getAsStringResultType();
		
		if (isVictory()) resString += " (" + getAsStringWinType() + ")";
		return resString;
	}
	
	
	public PlayerSummary getWinningPlayer(){return pWinningPlayer;}
	public PlayerSummary getFurikondaPlayer(){return pFurikondaPlayer;}
	
	public Wind getWindOfWinner(){if (!isVictory()) return null; return pWinningPlayer.getSeatWind();}
	public TileInterface getWinningTile(){if (!isVictory()) return null; return pWinningTile.clone();}
	
	
	
	public List<TileInterface> getWinnerHandTiles(){
		if (!isVictory()) return null;
		List <TileInterface> copy = new ArrayList<TileInterface>();
		copy.addAll(pWinnerHand);
		return copy;
	}
	public List<Meld> getWinnerMelds(){
		if (!isVictory()) return null; 
		List<Meld> meldsCopy = new ArrayList<Meld>();
		for (Meld m: pWinnerMelds) meldsCopy.add(m);
		return meldsCopy;
	}
	
	public PaymentMap getPayments(){return new PaymentMap(pPayments);}
}
