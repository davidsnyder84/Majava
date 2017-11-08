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
	
	
	
	
	private final int seatNumberOfWinner;
	private final int seatNumberOfFurikonda;
	
	private final ResType resultType;
	private final WinType winType;
	private final DrawType drawType;
	
	
	//win constructor
	private ResultType(WinType receivedWinType, int winnerSeat, int loserSeat){
		resultType = ResType.VICTORY;
		
		seatNumberOfWinner = winnerSeat;
		seatNumberOfFurikonda = loserSeat;
		winType = receivedWinType;
		drawType = null;
	}
	private ResultType(WinType receivedWinType, int winnerSeat){
		this(receivedWinType, winnerSeat, NO_FURIKONDA_SEAT);
	}
	//draw constructor
	private ResultType(DrawType receivedDrawType){
		resultType = ResType.DRAW;
		
		drawType = receivedDrawType;
		winType = null;
		seatNumberOfWinner = NO_WINNING_SEAT;
		seatNumberOfFurikonda = NO_FURIKONDA_SEAT;
	}
	
	
	public boolean isDraw(){return (resultType.isDraw());}
	public boolean isDrawWashout(){return drawType == DrawType.DRAW_WASHOUT;}
	
	public boolean isVictory(){return resultType.isVictory();}
	public boolean isVictoryTsumo(){return isVictory() && winType.isTsumo();}
	public boolean isVictoryRon(){return isVictory() && winType.isRon();}
	
	public int getWinningSeat(){return seatNumberOfWinner;}
	public int getFurikondaSeat(){return seatNumberOfFurikonda;}
	
	
	
	public String getAsStringWinType(){if (!isVictory()) return null; return winType.toString();}
	public String getAsStringResultType(){
		if (resultType.isDraw()) return drawType.toString();
		else if (resultType.isVictory()) return "Player " + (seatNumberOfWinner+1) + " wins!";
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
