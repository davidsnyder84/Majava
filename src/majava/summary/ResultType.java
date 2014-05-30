package majava.summary;

public class ResultType {
	
	private static enum ResType{
		UNDECIDED,
//		DRAW_WASHOUT, DRAW_KYUUSHU, DRAW_4KAN, DRAW_4RIICHI, DRAW_4WIND,
		DRAW,
		VICTORY;
		
		public boolean isDraw(){return (this == DRAW);}
		public boolean isVictory(){return this == VICTORY;}
		
		public String toString(){
			switch (this){
			case DRAW: return "draws";
			case VICTORY: return "wins!";
			default: return "??Undecided??";
			}
		}
	}
//	private static enum ResType{
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
	private static enum WinType{
		UNDECIDED, TSUMO, RON;
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
	private static enum DrawType{
		UNDECIDED, DRAW_WASHOUT, DRAW_KYUUSHU, DRAW_4KAN, DRAW_4RIICHI, DRAW_4WIND;
		public String toString(){
			switch (this){
			case DRAW_WASHOUT: return "Draw (Washout)";
			case DRAW_KYUUSHU: return "Draw (Kyuushuu)";
			case DRAW_4KAN: return "Draw (4 kans)";
			case DRAW_4RIICHI: return "Draw (4 riichi)";
			case DRAW_4WIND: return "Draw (4 wind)";
			default: return "??Undecided??";
			}
		}
	}
	
	

	private static final int NO_WINNING_SEAT = -2;
	private static final int NO_FURIKONDA_SEAT = -1;
	
	
	
	
	private final int mWinningSeat;
	private final int mFurikondaSeat;
	
	private final ResType mResultType;
	private final WinType mWinType;
	private final DrawType mDrawType;
	
	
	//wins
	private ResultType(WinType winType, int winnerSeat, int loserSeat){
		mResultType = ResType.VICTORY;
		
		mWinningSeat = winnerSeat;
		mFurikondaSeat = loserSeat;
		mWinType = winType;
		mDrawType = null;
	}
	private ResultType(WinType winType, int winnerSeat){
		this(winType, winnerSeat, NO_FURIKONDA_SEAT);
	}
	//draws
	private ResultType(DrawType drawType){
		mResultType = ResType.DRAW;
		
		mDrawType = drawType;
		mWinType = null;
		mWinningSeat = NO_WINNING_SEAT;
		mFurikondaSeat = NO_FURIKONDA_SEAT;
	}
	
	
	public boolean isDraw(){return (mResultType.isDraw());}
	public boolean isVictory(){return mResultType.isVictory();}
	
	public boolean isVictoryTsumo(){return isVictory() && mWinType.isTsumo();}
	public boolean isVictoryRon(){return isVictory() && mWinType.isRon();}
	
	public int getWinningSeat(){return mWinningSeat;}
	public int getFurikondaSeat(){return mFurikondaSeat;}
	
	
	
	public String getAsStringWinType(){return mWinType.toString();}
	public String getAsStringResultType(){
		if (mResultType.isDraw()) return mDrawType.toString();
		else if (mResultType.isVictory()) return "Player " + (mWinningSeat+1) + " wins!";
		return "undecided result";
	}
	
	
//	private void __setResultVictory(Player winner, WinType winType){
//		
//		switch(winner.getSeatWind()){
//		case EAST: __setRoundOver(ResultIC.VICTORY_E); break;
//		case SOUTH: __setRoundOver(ResultIC.VICTORY_S); break;
//		case WEST: __setRoundOver(ResultIC.VICTORY_W); break;
//		case NORTH: __setRoundOver(ResultIC.VICTORY_N); break;
//		default: break;
//		}
//		
//		mWinType = winType;
//		
//		mWinningPlayer = winner;
////		__setWinningHand();
//	}
//	public void setVictoryRon(Player winner, Player discarder){
//		
//		mFurikondaPlayer = discarder;
//		__setResultVictory(winner, WinType.RON);
//	}
//	public void setVictoryTsumo(Player winner){
//		__setResultVictory(winner, WinType.TSUMO);
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	//victory factories
	public static ResultType createResultVictoryTsumo(int winnerSeat){
		return new ResultType(WinType.TSUMO, winnerSeat);
	}
	public static ResultType createResultVictoryRon(int winnerSeat, int loserSeat){
		return new ResultType(WinType.RON, winnerSeat, loserSeat);
	}
	
	//ryuukyoku factories
	public static ResultType createResultRyuukyokuWashout(){return new ResultType(DrawType.DRAW_WASHOUT);}
	public static ResultType createResultRyuukyokuKyuushu(){return new ResultType(DrawType.DRAW_KYUUSHU);}
	public static ResultType createResultRyuukyoku4Kan(){return new ResultType(DrawType.DRAW_4KAN);}
	public static ResultType createResultRyuukyoku4Riichi(){return new ResultType(DrawType.DRAW_4RIICHI);}
	public static ResultType createResultRyuukyoku4Wind(){return new ResultType(DrawType.DRAW_4WIND);}
	
	
}
