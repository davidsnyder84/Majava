package majava;

import java.util.List;

import majava.enums.Wind;
import majava.summary.PaymentMap;
import majava.summary.PlayerSummary;
import majava.summary.ResultType;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.tiles.TileInterface;
import majava.util.TileList;



/*
Class: RoundResult

data:
	mRoundIsOver - is true if the round is over
	mResult - the result of the round (washout, 4kan, victoryE, etc)
	mWinType - the type of win (tsumo, ron)
	
	mWinningPlayer - the player who won
	mFurikondaPlayer - the player who discarded into the winner's ron, if any
	mWinningTile - the winner's winning tile
	mWinnerHand - a list of tiles in the winner's hand
	mWinnerMelds - a list of the winner's melds
	
methods:
	public:
		mutators:
	 	setVictoryRon, setVictoryTsumo - sets the result to victory for the given player, for the corresponding win type
	 	setWinningHand - used to set the winning hand
	 	
	 	accessors:
	 	isOver - returns true if the round has ended
	 	isDraw, isVictory - returns true if the result is the corresponding result
	 	getWinningHandString - returns string representation of the winning hand
	 	getWinTypeString - returns the win type as a string (tsumo, ron)
*/
public class RoundResult {
	
	
	private boolean mRoundIsOver;
	
	private ResultType mResultType;
	private RoundResultSummary mResultSummary;


	private Player mWinningPlayer;
	private Player mFurikondaPlayer;
	
	private TileInterface mWinningTile;

	
	private TileList mWinnerHand;
	private List<Meld> mWinnerMelds;
	
	private PaymentMap mPayments;
	
	
	
	
	
	
	public RoundResult(){
		mRoundIsOver = false;
		
		mResultType = null;
		mWinningPlayer = mFurikondaPlayer = null;
		mWinningTile = null;
	}
	
	
	
	
	//set round result type
	private void __setRoundOver(ResultType result){
		mResultType = result;
		mRoundIsOver = true;
	}
	
	public void setResultRyuukyokuWashout(){__setRoundOver(ResultType.createResultRyuukyokuWashout());}
	public void setResultRyuukyokuKyuushu(){__setRoundOver(ResultType.createResultRyuukyokuKyuushu());}
	public void setResultRyuukyoku4Kan(){__setRoundOver(ResultType.createResultRyuukyoku4Kan());}
	public void setResultRyuukyoku4Riichi(){__setRoundOver(ResultType.createResultRyuukyoku4Riichi());}
	public void setResultRyuukyoku4Wind(){__setRoundOver(ResultType.createResultRyuukyoku4Wind());}
	
	
	public void setVictoryRon(Player winner, Player discarder){

		mWinningPlayer = winner;
		mFurikondaPlayer = discarder;
		__setRoundOver(ResultType.createResultVictoryRon(mWinningPlayer.getPlayerNumber(), mFurikondaPlayer.getPlayerNumber()));
	}
	public void setVictoryTsumo(Player winner){
		mWinningPlayer = winner;
		__setRoundOver(ResultType.createResultVictoryTsumo(mWinningPlayer.getPlayerNumber()));
	}
	
	
	//set other things
	public void setWinningHand(TileList handTiles, List<Meld> melds, TileInterface winningTile){
		mWinnerHand = handTiles;
		mWinnerMelds = melds;
		setWinningTile(winningTile);
	}
	public void setWinningTile(TileInterface winningTile){mWinningTile = winningTile;}
	
	
	public void recordPayments(PaymentMap payments){
		mPayments = payments;
	}
	
	
	
	
	
	
	
	
	
	//accessors
	
	public boolean isOver(){return mRoundIsOver;}
	public boolean isDraw(){return isOver() && mResultType.isDraw();}
	public boolean isDrawWashout(){return isOver() && mResultType.isDrawWashout();}
	public boolean isVictory(){return isOver() && mResultType.isVictory();}
	public boolean isVictoryRon(){return isOver() && mResultType.isVictoryRon();}
	public boolean isVictoryTsumo(){return isOver() && mResultType.isVictoryTsumo();}
	
	public boolean isDealerVictory(){return (isOver() && isVictory() && mWinningPlayer.isDealer());}
	
	
	
	public String getAsStringWinType(){return mResultType.getAsStringWinType();}
	public String getAsStringWinningHand(){
		String winString = "";
		if (!isVictory()) return "No winner";
		
		winString += "Winning hand (" + mWinningPlayer.getSeatWind() + "): " + mWinnerHand + ",   agarihai: " + mWinningTile + " (" + getAsStringWinType() + ")";
		if (mResultType.isVictoryRon()) winString += " [from " + mFurikondaPlayer.getSeatWind() + "]";
		
		winString += "\n";
		for (Meld m: mWinnerMelds)
			winString += m.toString() + "\n";
		
		return winString;
	}
	public String getAsStringPayments(){
		String pstring = "";
		
		pstring += "Winner: " + mWinningPlayer + ": +" + mPayments.get(mWinningPlayer.getPlayerNumber());
		for (PlayerSummary ps: mPayments) if (ps.getPlayerNumber() != mWinningPlayer.getPlayerNumber()) pstring += "\n" + ps + ": " + mPayments.get(ps.getPlayerNumber());
//		ps += "Winner: " + mWinningPlayer + ": +" + mPayments.get(mWinningPlayer);
//		for (Player p: mPayments.keySet()) if (p != mWinningPlayer) ps += "\n" + p + ": " + mPayments.get(p);
		
		return pstring;
	}
	
	
	
	public Wind getWindOfWinner(){if (isOver() && isVictory()) return mWinningPlayer.getSeatWind(); return null;}
	
	public TileInterface getWinningTile(){return mWinningTile;}
	public Player getWinningPlayer(){return mWinningPlayer;}
	public Player getFurikondaPlayer(){return mFurikondaPlayer;}
	
	
	
	//returns the round result as a string
	@Override
	public String toString(){
		String resString = "";
		resString += mResultType.getAsStringResultType();
		
		if (isVictory()) resString += " (" + getAsStringWinType() + ")";
		return resString;
	}
	
	
	
	public RoundResultSummary getSummary(){
		if (!mRoundIsOver) return null;
		if (mResultSummary != null) return mResultSummary;
		
		RoundResultSummary sum = null;
		PlayerSummary winnerSummary = null, furikonSummary = null;
		PaymentMap payments = null;
		TileInterface winningTile = null;
		TileList winnerHand = null;
		List<Meld> winnerMelds = null;
		
		//get winning, losing player summaries
		if (mResultType.isVictory()){
			winnerSummary = mWinningPlayer.getPlayerSummary();
			if (mResultType.isVictoryRon()) furikonSummary = mFurikondaPlayer.getPlayerSummary();
			winningTile = mWinningTile;
			winnerHand = mWinnerHand;
			winnerMelds = mWinnerMelds;
		}
		
		//get payments
		payments = new PaymentMap(mPayments);
		
		sum = new RoundResultSummary(mResultType, winnerSummary, furikonSummary, winningTile, winnerHand, winnerMelds, payments);
//		sum = new RoundResultSummary(mResultType, payments);
		mResultSummary = sum;
		return mResultSummary;
	}
//	public RoundResultSummary getSummary(){
//		if (!mRoundIsOver) return null;
//		if (mResultSummary != null) return mResultSummary;
//		
//		RoundResultSummary sum = null;
//		PlayerSummary winnerSummary = null, furikonSummary = null;
//		Map<PlayerSummary,Integer> payments = null;
//		Tile winningTile = null;
//		TileList winnerHand = null;
//		List<Meld> winnerMelds = null;
//		
//		//get winning, losing player summaries
//		if (mResultType.isVictory()){
//			winnerSummary = mWinningPlayer.getPlayerSummary();
//			if (mResultType.isVictoryRon()) furikonSummary = mFurikondaPlayer.getPlayerSummary();
//			winningTile = mWinningTile;
//			winnerHand = mWinnerHand;
//			winnerMelds = mWinnerMelds;
//		}
//		
//		//get payments
//		payments = new HashMap<PlayerSummary,Integer>();
//		for (Player p: mPayments.keySet()) payments.put(p.getPlayerSummary(), mPayments.get(p));
//		
//		sum = new RoundResultSummary(mResultType, winnerSummary, furikonSummary, winningTile, winnerHand, winnerMelds, payments);
////		sum = new RoundResultSummary(mResultType, payments);
//		mResultSummary = sum;
//		return mResultSummary;
//	}

}
