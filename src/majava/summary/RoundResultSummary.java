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
	
	private final boolean pRoundIsOver;
	
	private final ResultType pResultType;

	private final PlayerSummary pWinningPlayer;
	private final PlayerSummary pFurikondaPlayer;
	
	private final Tile pWinningTile;
	
	
	private final TileList pWinnerHand;
	private final List<Meld> pWinnerMelds;
	
	private final Map<PlayerSummary,Integer> pPayments;
	
	
	
	public RoundResultSummary(boolean roundIsOver, ResultType resType, PlayerSummary winningPlayer, PlayerSummary furikondaPlayer, Tile winningTile, TileList winnerHand, List<Meld> winnerMelds, Map<PlayerSummary,Integer> payments){
		pRoundIsOver = roundIsOver;
		
		pResultType = resType;
		
		pWinningPlayer = winningPlayer;
		pFurikondaPlayer = furikondaPlayer;
		pWinningTile = winningTile.clone();
		
		pWinnerHand = winnerHand.makeCopy();
		pWinnerMelds = winnerMelds;
		
		pPayments = null;
	}
	
	
 	public boolean isOver(){return pRoundIsOver;}
	public boolean isDraw(){return isOver() && pResultType.isDraw();}
	public boolean isVictory(){return isOver() && pResultType.isVictory();}
	public boolean isVictoryRon(){return isOver() && pResultType.isVictoryRon();}
	public boolean isVictoryTsumo(){return isOver() && pResultType.isVictoryTsumo();}
	
	public boolean isDealerVictory(){return (isOver() && isVictory() && pWinningPlayer.isDealer());}
	public Wind getWindOfWinner(){if (isOver() && isVictory()) return pWinningPlayer.getSeatWind(); return null;}
	
	public String getWinTypeString(){return pResultType.getAsStringWinType();}
	public String getAsStringResultType(){return pResultType.getAsStringResultType();}
	
	public Tile getWinningTile(){return pWinningTile.clone();}
	public PlayerSummary getWinningPlayer(){return pWinningPlayer;}
	public PlayerSummary getFurikondaPlayer(){return pFurikondaPlayer;}
}
