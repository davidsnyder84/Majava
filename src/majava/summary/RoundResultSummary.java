package majava.summary;

import java.util.List;
import java.util.Map;

import majava.Meld;
import majava.util.TileList;
import majava.enums.Wind;
import majava.summary.PlayerSummary;
import majava.summary.ResultType;
import majava.tiles.Tile;



public class RoundResultSummary {
	
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
		
		pPayments = null;
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
	
	public Wind getWindOfWinner(){if (isVictory()) return pWinningPlayer.getSeatWind(); return null;}
	public Tile getWinningTile(){if (isVictory()) return pWinningTile.clone(); return null;}
}
