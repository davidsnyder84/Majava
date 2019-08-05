package majava;

import java.util.List;

import majava.enums.Wind;
import majava.hand.Meld;
import majava.player.Player;
import majava.summary.PaymentMap;
import majava.summary.PlayerSummary;
import majava.summary.ResultType;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.util.GameTileList;
import majava.util.YakuList;



//used to record information about how the round ended (who won, the winner's hand, poing payments, etc)
public class RoundResult {
	private boolean flagRoundIsOver;
	
	private ResultType resultType;
	private RoundResultSummary resultSummary;
	
	private Player winningPlayer;
	private Player furikondaPlayer;
	
	private GameTile winningTile;
	
	private GameTileList winnerHand;
	private List<Meld> winnerMelds;
	
	private PaymentMap payments;
	private YakuList yakuOfWinner;
	
	
	public RoundResult(){
		flagRoundIsOver = false;
		
		resultType = null;
		winningPlayer = furikondaPlayer = null;
		winningTile = null;
	}
	
	
	
	//set round result type
	private void __setRoundOver(ResultType result){
		resultType = result;
		flagRoundIsOver = true;
	}
	
	/////荒牌平局（ホワンパイピンチュー、こうはいへいきょく）、または荒牌（ホワンパイ、こうはい）と呼ぶ
	public void setResultRyuukyokuHowanpai(){__setRoundOver(ResultType.createResultRyuukyokuHowanpai());}
	public void setResultRyuukyokuKyuushu(){__setRoundOver(ResultType.createResultRyuukyokuKyuushu());}
	public void setResultRyuukyoku4Kan(){__setRoundOver(ResultType.createResultRyuukyoku4Kan());}
	public void setResultRyuukyoku4Riichi(){__setRoundOver(ResultType.createResultRyuukyoku4Riichi());}
	public void setResultRyuukyoku4Wind(){__setRoundOver(ResultType.createResultRyuukyoku4Wind());}
	
	
	public void setVictoryRon(Player winner, Player discarder){

		winningPlayer = winner;
		furikondaPlayer = discarder;
		__setRoundOver(ResultType.createResultVictoryRon(winningPlayer.getPlayerNumber(), furikondaPlayer.getPlayerNumber()));
	}
	public void setVictoryTsumo(Player winner){
		winningPlayer = winner;
		__setRoundOver(ResultType.createResultVictoryTsumo(winningPlayer.getPlayerNumber()));
	}
	
	
	//set other things
	public void setWinningHand(GameTileList handTiles, List<Meld> melds, GameTile receivedWinningTile){
		winnerHand = handTiles;
		winnerMelds = melds;
		setWinningTile(receivedWinningTile);
	}
	public void setWinningTile(GameTile receivedWinningTile){winningTile = receivedWinningTile;}
	
	
	public void recordPayments(PaymentMap receivedPayments){payments = receivedPayments;}
	public void recordYaku(YakuList yakus){yakuOfWinner = yakus;}
	
	public PaymentMap getPayments(){return new PaymentMap(payments);}
	public YakuList getYakuOfWinner(){return new YakuList(yakuOfWinner);}
	
	
	
	//accessors
	public boolean isOver(){return flagRoundIsOver;}
	public boolean isRyuukyoku(){return isOver() && resultType.isRyuukyoku();}
	public boolean isRyuukyokuHowanpai(){return isOver() && resultType.isRyuukyokuHowanpai();}
	public boolean isVictory(){return isOver() && resultType.isVictory();}
	public boolean isVictoryRon(){return isOver() && resultType.isVictoryRon();}
	public boolean isVictoryTsumo(){return isOver() && resultType.isVictoryTsumo();}
	
	public boolean isDealerVictory(){return (isOver() && isVictory() && winningPlayer.isDealer());}
	
	
	
	public String getAsStringWinType(){return resultType.getAsStringWinType();}
	public String getAsStringWinningHand(){
		String winString = "";
		if (!isVictory()) return "No winner";
		
		winString += "Winner: Player" + (winningPlayer.getPlayerNumber()+1) + " (" + winningPlayer.getControllerAsString() + ") (" + winningPlayer.getSeatWind() + ")\n";
		winString += winnerHand + ",   agarihai: " + winningTile + " (" + getAsStringWinType() + ")";
		if (resultType.isVictoryRon()) winString += " [from Player" + (furikondaPlayer.getPlayerNumber()+1) + " (" + furikondaPlayer.getSeatWind() + ")]\n";
		
		for (Meld m: winnerMelds)
			winString += m.toString() + "\n";
		
		if (resultSummary.getYakuOfWinner().isEmpty()) winString += "Yakunashi\n";
		else winString += "&&&Yaku:" + resultSummary.getYakuOfWinner() + "\n";
		
		return winString;
	}
	public String getAsStringPayments(){
		String pstring = "";
		
		pstring += "Winner: " + winningPlayer + ": +" + payments.get(winningPlayer.getPlayerNumber());
		for (PlayerSummary ps: payments) if (ps.getPlayerNumber() != winningPlayer.getPlayerNumber()) pstring += "\n" + ps + ": " + payments.get(ps.getPlayerNumber());
		
		return pstring;
	}
	
	
	
	public Wind getWindOfWinner(){if (isOver() && isVictory()) return winningPlayer.getSeatWind(); return null;}
	
	public GameTile getWinningTile(){return winningTile;}
	public Player getWinningPlayer(){return winningPlayer;}
	public Player getFurikondaPlayer(){return furikondaPlayer;}
	
	
	
	@Override
	public String toString(){
		String resString = "";
		resString += resultType.getAsStringResultType();
		
		if (isVictory()) resString += " (" + getAsStringWinType() + ")";
		return resString;
	}
	
	
	
	public RoundResultSummary getSummary(){
		if (!flagRoundIsOver) return null;
		if (resultSummary != null) return resultSummary;
		
		PlayerSummary winnerSummary = null, furikonSummary = null;
		GameTile winningTileCopy = null;
		GameTileList winnerHandCopy = null;
		List<Meld> winnerMeldsCopy = null;
		
		//get winning, losing player summaries
		if (resultType.isVictory()){
			winnerSummary = PlayerSummary.getSummaryFor(winningPlayer);
			if (resultType.isVictoryRon()) furikonSummary = PlayerSummary.getSummaryFor(furikondaPlayer);
			winningTileCopy = winningTile.clone();
			winnerHandCopy = winnerHand.clone();
			winnerMeldsCopy = winnerMelds;
		}
		
		resultSummary = new RoundResultSummary(resultType, winnerSummary, furikonSummary, winningTileCopy, winnerHandCopy, winnerMeldsCopy, payments.clone(), yakuOfWinner.clone());
		return resultSummary;
	}
	
}
