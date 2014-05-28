package majava;

import java.util.ArrayList;

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
	
	
	private enum Result{
		UNDECIDED,
		DRAW_WASHOUT, DRAW_KYUUSHU, DRAW_4KAN, DRAW_4RIICHI, DRAW_4WIND,
		VICTORY_E, VICTORY_S, VICTORY_W, VICTORY_N;
		
		public boolean isDraw(){return (this == DRAW_WASHOUT || this == DRAW_KYUUSHU || this == DRAW_4KAN || this == DRAW_4RIICHI || this == DRAW_4WIND);}
		public boolean isVictory(){return (this == VICTORY_E || this == VICTORY_S || this == VICTORY_W || this == VICTORY_N);}
		
		@Override
		public String toString(){
			switch (this){
			case DRAW_WASHOUT: return "Draw (Washout)";
			case DRAW_KYUUSHU: return "Draw (Kyuushuu)";
			case DRAW_4KAN: return "Draw (4 kans)";
			case DRAW_4RIICHI: return "Draw (4 riichi)";
			case DRAW_4WIND: return "Draw (4 wind)";
			
			case VICTORY_E: return "East wins!";
			case VICTORY_S: return "South wins!";
			case VICTORY_W: return "West wins!";
			case VICTORY_N: return "North wins!";
			default: return "??Undecided??";
			}
		}
	}
	private enum WinType{
		UNDECIDED, TSUMO, RON;
		
		@Override
		public String toString(){
			switch (this){
			case TSUMO: return "TSUMO";
			case RON: return "RON";
			default: return "undecided";
			}
		}
		
		public boolean isTsumo(){return this == TSUMO;}
		public boolean isRon(){return this == RON;}
	}
	
	
	
	
	private boolean mRoundIsOver;
	
	private Result mResult;
	private WinType mWinType;


	private Player mWinningPlayer;
	private Player mFurikondaPlayer;
	
	private Tile mWinningTile;
	

	
	private TileList mWinnerHand;
	private ArrayList<Meld> mWinnerMelds;
	
	
	
	
	
	
	public RoundResult(){
		mResult = Result.UNDECIDED;
		mWinType = WinType.UNDECIDED;
		mWinningPlayer = mFurikondaPlayer = null;
		mWinningTile = null;
		
		mRoundIsOver = false;
	}
	
	
	
	
	
	

	private void __setRoundOver(Result result){
		mResult = result;
		mRoundIsOver = true;
	}
	
	public void setResultRyuukyokuWashout(){__setRoundOver(Result.DRAW_WASHOUT);}
	public void setResultRyuukyokuKyuushu(){__setRoundOver(Result.DRAW_KYUUSHU);}
	public void setResultRyuukyoku4Kan(){__setRoundOver(Result.DRAW_4KAN);}
	public void setResultRyuukyoku4Riichi(){__setRoundOver(Result.DRAW_4RIICHI);}
	public void setResultRyuukyoku4Wind(){__setRoundOver(Result.DRAW_4WIND);}
	
	
	
	
	private void __setResultVictory(Player winner, WinType winType){
		
		switch(winner.getSeatWind()){
		case EAST: __setRoundOver(Result.VICTORY_E); break;
		case SOUTH: __setRoundOver(Result.VICTORY_S); break;
		case WEST: __setRoundOver(Result.VICTORY_W); break;
		case NORTH: __setRoundOver(Result.VICTORY_N); break;
		default: break;
		}
		
		mWinType = winType;
		
		mWinningPlayer = winner;
//		__setWinningHand();
	}
	public void setVictoryRon(Player winner, Player discarder){
		
		mFurikondaPlayer = discarder;
		__setResultVictory(winner, WinType.RON);
	}
	public void setVictoryTsumo(Player winner){
		__setResultVictory(winner, WinType.TSUMO);
	}
	
	
	public void setWinningHand(TileList handTiles, ArrayList<Meld> melds, Tile winningTile){
		mWinnerHand = handTiles;
		mWinnerMelds = melds;
		mWinningTile = winningTile;
	}
	
	
	
	
	public String getWinningHandString(){
		String winString = "";
		if (!isVictory()) return "No winner";
		
		winString += "Winning hand (" + mWinningPlayer.getSeatWind() + "): " + mWinnerHand + ",   agarihai: " + mWinningTile + " (" + getWinTypeString() + ")";
		if (mWinType.isRon()) winString += " [from " + mFurikondaPlayer.getSeatWind() + "]";
		
		winString += "\n";
		for (Meld m: mWinnerMelds)
			winString += m.toString() + "\n";
		
		return winString;
	}
	
	
	
	
	
	
	
	
	
	public boolean isOver(){return mRoundIsOver;}
	public boolean isDraw(){return mResult.isDraw();}
	public boolean isVictory(){return mResult.isVictory();}
	
	
	
	public String getWinTypeString(){return mWinType.toString();}
	public Wind getWindOfWinner(){if (isOver() && isVictory()) return mWinningPlayer.getSeatWind(); return null;}
	
	
	
	//returns the round result as a string
	@Override
	public String toString(){
		
		String resString = "";
		resString += mResult.toString();
		
		if (mResult.isVictory()) resString += " (" + mWinType.toString() + ")";
		return resString;
	}

}
