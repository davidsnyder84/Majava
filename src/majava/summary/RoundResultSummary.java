package majava.summary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import majava.Meld;
import majava.util.TileList;
import majava.enums.Wind;
import majava.summary.PlayerSummary;
import majava.summary.ResultType;
import majava.tiles.Tile;



public class RoundResultSummary {
	
	private static final int NUM_PLAYERS = 4;
	
	
	private final ResultType pResultType;

	private final PlayerSummary pWinningPlayer;
	private final PlayerSummary pFurikondaPlayer;
	
	private final Tile pWinningTile;
	
	
	private final TileList pWinnerHand;
	private final List<Meld> pWinnerMelds;
	
	private final Map<PlayerSummary,Integer> pPayments;
	
	
	//win constructor
	public RoundResultSummary(ResultType resType, PlayerSummary winningPlayer, PlayerSummary furikondaPlayer, Tile winningTile, TileList winnerHand, List<Meld> winnerMelds, Map<PlayerSummary,Integer> payments){
		
		pResultType = resType;
		
		if (pResultType.isVictory()){
			pWinningPlayer = winningPlayer;
			pFurikondaPlayer = furikondaPlayer;
			pWinningTile = winningTile.clone();
			
			pWinnerHand = winnerHand.makeCopy();
			pWinnerMelds = winnerMelds;
		}
		else{
			pWinningPlayer = null;
			pFurikondaPlayer = null;
			pWinningTile = null;
			
			pWinnerHand = null;
			pWinnerMelds = null;
		}
		
		pPayments = new HashMap<PlayerSummary,Integer>(NUM_PLAYERS);
		pPayments.putAll(payments);
	}
	//draw constructor
	public RoundResultSummary(ResultType resType, Map<PlayerSummary,Integer> payments){
		this(resType, null, null, null, null, null, payments);
	}
	
	
	public boolean isDraw(){return pResultType.isDraw();}
	public boolean isDrawWashout(){return pResultType.isDrawWashout();}
	
	public boolean isVictory(){return pResultType.isVictory();}
	public boolean isVictoryRon(){return pResultType.isVictoryRon();}
	public boolean isVictoryTsumo(){return pResultType.isVictoryTsumo();}
	
	public boolean isDealerVictory(){return (isVictory() && pWinningPlayer.isDealer());}
	
	
	
	public String getAsStringWinType(){return pResultType.getAsStringWinType();}
	public String getAsStringResultType(){return pResultType.getAsStringResultType();}
	
	
	public PlayerSummary getWinningPlayer(){return pWinningPlayer;}
	public PlayerSummary getFurikondaPlayer(){return pFurikondaPlayer;}
	
	public Wind getWindOfWinner(){if (!isVictory()) return null; return pWinningPlayer.getSeatWind();}
	public Tile getWinningTile(){if (!isVictory()) return null; return pWinningTile.clone();}
	
	
	
	public TileList getWinnerHandTiles(){if (!isVictory()) return null; return pWinnerHand.makeCopy();}
	public List<Meld> getWinnerMelds(){
		if (!isVictory()) return null; 
		List<Meld> meldsCopy = new ArrayList<Meld>();
		for (Meld m: pWinnerMelds) meldsCopy.add(m);
		return meldsCopy;
	}
	
	public Map<PlayerSummary,Integer> getPayments(){
		Map<PlayerSummary,Integer> paymentsCopy = new HashMap<PlayerSummary,Integer>(NUM_PLAYERS);
		paymentsCopy.putAll(pPayments);
		
		return paymentsCopy;
	}
}
