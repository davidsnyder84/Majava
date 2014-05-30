package majava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import majava.enums.Wind;
import majava.summary.PlayerSummary;
import majava.summary.ResultType;
import majava.tiles.Tile;



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
	
	
//	public class RoundResultSummary {
//		
//		private final boolean pRoundIsOver;
//		
//		private final ResultIC pResult;
//		private final WinType pWinType;
//
//
//		private final PlayerSummary pWinningPlayer;
//		private final PlayerSummary pFurikondaPlayer;
//		
//		private final Tile pWinningTile;
//		
//		
//		private final TileList pWinnerHand;
//		private final List<Meld> pWinnerMelds;
//		
//		private final Map<PlayerSummary,Integer> pPayments;
//		
//		
//		
//		public RoundResultSummary(boolean roundIsOver, ResultIC result, WinType winType, PlayerSummary winningPlayer, PlayerSummary furikondaPlayer, Tile winningTile, TileList winnerHand, List<Meld> winnerMelds, Map<PlayerSummary,Integer> payments){
//			pRoundIsOver = roundIsOver;
//			
//			pResult = result;
//			pWinType = winType;
//			
//			pWinningPlayer = winningPlayer;
//			pFurikondaPlayer = furikondaPlayer;
//			pWinningTile = winningTile.clone();
//			
//			pWinnerHand = winnerHand.makeCopy();
//			pWinnerMelds = winnerMelds;
//			
//			pPayments = null;
//		}
//		
//		
//	 	public boolean isOver(){return pRoundIsOver;}
//		public boolean isDraw(){return pResult.isDraw();}
//		public boolean isVictory(){return pResult.isVictory();}
//		public boolean isVictoryRon(){return pWinType.isRon();}
//		public boolean isVictoryTsumo(){return pWinType.isTsumo();}
//		
////		public boolean isDealerVictory(){return (isOver() && isVictory() && pWinningPlayer.isDealer());}
////		public Wind getWindOfWinner(){if (isOver() && isVictory()) return mWinningPlayer.getSeatWind(); return null;}
//		
//		public String getWinTypeString(){return pWinType.toString();}
//		
//		public Tile getWinningTile(){return pWinningTile.clone();}
//		public PlayerSummary getWinningPlayer(){return pWinningPlayer;}
//		public PlayerSummary getFurikondaPlayer(){return pFurikondaPlayer;}
//	}
//	
//	public RoundResultSummary getSummary(){
//		RoundResultSummary sum = null;
//		
//		PlayerSummary winnerSummary = mWinningPlayer.getPlayerSummary();
//		PlayerSummary furikonSummary = mFurikondaPlayer.getPlayerSummary();
//		
//		Map<PlayerSummary,Integer> payments = new HashMap<PlayerSummary,Integer>();
//		for (Player p: mPayments.keySet()) payments.put(p.getPlayerSummary(), mPayments.get(p));
//		
//		
//		sum = new RoundResultSummary(mRoundIsOver, mResult, mWinType, winnerSummary, furikonSummary, mWinningTile, mWinnerHand, mWinnerMelds, payments);
//		return sum;
//	}
	
	
	
	
	
	
	
//	private static enum ResultIC{
//		UNDECIDED,
//		DRAW_WASHOUT, DRAW_KYUUSHU, DRAW_4KAN, DRAW_4RIICHI, DRAW_4WIND,
//		VICTORY_E, VICTORY_S, VICTORY_W, VICTORY_N;
//		
//		public boolean isDraw(){return (this == DRAW_WASHOUT || this == DRAW_KYUUSHU || this == DRAW_4KAN || this == DRAW_4RIICHI || this == DRAW_4WIND);}
//		public boolean isVictory(){return (this == VICTORY_E || this == VICTORY_S || this == VICTORY_W || this == VICTORY_N);}
//		
//		public String toString(){
//			switch (this){
//			case DRAW_WASHOUT: return "Draw (Washout)";
//			case DRAW_KYUUSHU: return "Draw (Kyuushuu)";
//			case DRAW_4KAN: return "Draw (4 kans)";
//			case DRAW_4RIICHI: return "Draw (4 riichi)";
//			case DRAW_4WIND: return "Draw (4 wind)";
//			
//			case VICTORY_E: return "East wins!";
//			case VICTORY_S: return "South wins!";
//			case VICTORY_W: return "West wins!";
//			case VICTORY_N: return "North wins!";
//			default: return "??Undecided??";
//			}
//		}
//	}
//	private static enum WinType{
//		UNDECIDED, TSUMO, RON;
//		public String toString(){
//			switch (this){
//			case TSUMO: return "TSUMO";
//			case RON: return "RON";
//			default: return "undecided";
//			}
//		}
//		public boolean isTsumo(){return this == TSUMO;}
//		public boolean isRon(){return this == RON;}
//	}
	
	
	
	
	private boolean mRoundIsOver;
	
	private ResultType mResultType;
	
//	private ResultIC mResult;
//	private WinType mWinType;


	private Player mWinningPlayer;
	private Player mFurikondaPlayer;
	
	private Tile mWinningTile;
	

	
	private TileList mWinnerHand;
	private List<Meld> mWinnerMelds;
	
	private Map<Player,Integer> mPayments;
	
	
	
	
	
	
	public RoundResult(){
//		mResult = ResultIC.UNDECIDED;
//		mWinType = WinType.UNDECIDED;
		mResultType = null;
		mWinningPlayer = mFurikondaPlayer = null;
		mWinningTile = null;
		
		mRoundIsOver = false;
	}
	

	private void __setRoundOver(ResultType result){
		mResultType = result;
//		mResult = result;
		mRoundIsOver = true;
	}
	
	public void setResultRyuukyokuWashout(){__setRoundOver(ResultType.createResultRyuukyokuWashout());}
	public void setResultRyuukyokuKyuushu(){__setRoundOver(ResultType.createResultRyuukyokuKyuushu());}
	public void setResultRyuukyoku4Kan(){__setRoundOver(ResultType.createResultRyuukyoku4Kan());}
	public void setResultRyuukyoku4Riichi(){__setRoundOver(ResultType.createResultRyuukyoku4Riichi());}
	public void setResultRyuukyoku4Wind(){__setRoundOver(ResultType.createResultRyuukyoku4Wind());}
	
	
	
	
//	private void __setResultVictory(Player winner, WinType winType){
//		
////		switch(winner.getSeatWind()){
////		case EAST: __setRoundOver(ResultIC.VICTORY_E); break;
////		case SOUTH: __setRoundOver(ResultIC.VICTORY_S); break;
////		case WEST: __setRoundOver(ResultIC.VICTORY_W); break;
////		case NORTH: __setRoundOver(ResultIC.VICTORY_N); break;
////		default: break;
////		}
////		
////		mWinType = winType;
//		
//		mWinningPlayer = winner;
//	}
	public void setVictoryRon(Player winner, Player discarder){

		mWinningPlayer = winner;
		mFurikondaPlayer = discarder;
//		__setResultVictory(winner, WinType.RON);
		__setRoundOver(ResultType.createResultVictoryRon(mWinningPlayer.getPlayerNumber(), mFurikondaPlayer.getPlayerNumber()));
	}
	public void setVictoryTsumo(Player winner){
//		__setResultVictory(winner, WinType.TSUMO);
		mWinningPlayer = winner;
		__setRoundOver(ResultType.createResultVictoryTsumo(mWinningPlayer.getPlayerNumber()));
	}
	
	
	
	
	
	public void setWinningHand(TileList handTiles, List<Meld> melds, Tile winningTile){
		mWinnerHand = handTiles;
		mWinnerMelds = melds;
		setWinningTile(winningTile);
	}
	public void setWinningTile(Tile winningTile){mWinningTile = winningTile;}
	
	
	public void recordPayments(Map<Player,Integer> playerPaymentsMap){
		mPayments = playerPaymentsMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	public String getWinningHandString(){
		String winString = "";
		if (!isVictory()) return "No winner";
		
		winString += "Winning hand (" + mWinningPlayer.getSeatWind() + "): " + mWinnerHand + ",   agarihai: " + mWinningTile + " (" + getAsStringWinType() + ")";
		if (mResultType.isVictoryRon()) winString += " [from " + mFurikondaPlayer.getSeatWind() + "]";
		
		winString += "\n";
		for (Meld m: mWinnerMelds)
			winString += m.toString() + "\n";
		
		return winString;
	}
	
	public String getPaymentsString(){
		String ps = "";
		
		ps += "Winner: " + mWinningPlayer + ": +" + mPayments.get(mWinningPlayer);
		for (Player p: mPayments.keySet()) if (p != mWinningPlayer) ps += "\n" + p + ": " + mPayments.get(p);
		
		return ps;
	}
	
	
	
	public boolean isOver(){return mRoundIsOver;}
	public boolean isDraw(){return isOver() && mResultType.isDraw();}
	public boolean isVictory(){return isOver() && mResultType.isVictory();}
	public boolean isVictoryRon(){return isOver() && mResultType.isVictoryRon();}
	public boolean isVictoryTsumo(){return isOver() && mResultType.isVictoryTsumo();}
	
	public boolean isDealerVictory(){return (isOver() && isVictory() && mWinningPlayer.isDealer());}
	
	
	
	public String getAsStringWinType(){return mResultType.getAsStringWinType();}
	
	
	public Wind getWindOfWinner(){if (isOver() && isVictory()) return mWinningPlayer.getSeatWind(); return null;}
	
	public Tile getWinningTile(){return mWinningTile;}
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

}
