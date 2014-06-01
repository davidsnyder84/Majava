package majava.summary;

public class ResultType {
	
	private static enum ResType{
		UNDECIDED,
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
	
	
	//win constructor
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
	//draw constructor
	private ResultType(DrawType drawType){
		mResultType = ResType.DRAW;
		
		mDrawType = drawType;
		mWinType = null;
		mWinningSeat = NO_WINNING_SEAT;
		mFurikondaSeat = NO_FURIKONDA_SEAT;
	}
	
	
	public boolean isDraw(){return (mResultType.isDraw());}
	public boolean isDrawWashout(){return mDrawType == DrawType.DRAW_WASHOUT;}
	
	public boolean isVictory(){return mResultType.isVictory();}
	public boolean isVictoryTsumo(){return isVictory() && mWinType.isTsumo();}
	public boolean isVictoryRon(){return isVictory() && mWinType.isRon();}
	
	public int getWinningSeat(){return mWinningSeat;}
	public int getFurikondaSeat(){return mFurikondaSeat;}
	
	
	
	public String getAsStringWinType(){if (!isVictory()) return null; return mWinType.toString();}
	public String getAsStringResultType(){
		if (mResultType.isDraw()) return mDrawType.toString();
		else if (mResultType.isVictory()) return "Player " + (mWinningSeat+1) + " wins!";
		return "undecided result";
	}
	
	
	
	
	
	
	
	
	
	
	
	
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
