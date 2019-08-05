package majava.summary;

public class ResultType {
	private static final int NO_WINNING_SEAT = -2;
	private static final int NO_FURIKONDA_SEAT = -1;
	
	
	private final int seatNumberOfWinner;
	private final int seatNumberOfFurikonda;
	
	private final ResType resultType;
	private final WinType winType;
	private final RyuukyokuType ryuukyokuType;
	
	
	//win constructor
	private ResultType(WinType receivedWinType, int winnerSeat, int loserSeat){
		resultType = ResType.VICTORY;
		
		seatNumberOfWinner = winnerSeat;
		seatNumberOfFurikonda = loserSeat;
		winType = receivedWinType;
		ryuukyokuType = null;
	}
	private ResultType(WinType receivedWinType, int winnerSeat){
		this(receivedWinType, winnerSeat, NO_FURIKONDA_SEAT);
	}
	//ryuukyoku constructor
	private ResultType(RyuukyokuType receivedRyuukyokuType){
		resultType = ResType.RYUUKYOKU;
		
		ryuukyokuType = receivedRyuukyokuType;
		winType = null;
		seatNumberOfWinner = NO_WINNING_SEAT;
		seatNumberOfFurikonda = NO_FURIKONDA_SEAT;
	}
	
	
	public boolean isRyuukyoku(){return (resultType.isRyuukyoku());}
	public boolean isRyuukyokuHowanpai(){return ryuukyokuType == RyuukyokuType.RYUUKYOKU_HOWANPAI;}
	
	public boolean isVictory(){return resultType.isVictory();}
	public boolean isVictoryTsumo(){return isVictory() && winType.isTsumo();}
	public boolean isVictoryRon(){return isVictory() && winType.isRon();}
	
	public int getWinningSeat(){return seatNumberOfWinner;}
	public int getFurikondaSeat(){return seatNumberOfFurikonda;}
	
	
	
	public String getAsStringWinType(){if (!isVictory()) return null; return winType.toString();}
	public String getAsStringResultType(){
		if (resultType.isRyuukyoku()) return ryuukyokuType.toString();
		else if (resultType.isVictory()) return "Player" + (seatNumberOfWinner+1) + " wins!";
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
	public static ResultType createResultRyuukyokuHowanpai(){return new ResultType(RyuukyokuType.RYUUKYOKU_HOWANPAI);}
	public static ResultType createResultRyuukyokuKyuushu(){return new ResultType(RyuukyokuType.RYUUKYOKU_KYUUSHU);}
	public static ResultType createResultRyuukyoku4Kan(){return new ResultType(RyuukyokuType.RYUUKYOKU_KAN);}
	public static ResultType createResultRyuukyoku4Riichi(){return new ResultType(RyuukyokuType.RYUUKYOKU_RIICHI);}
	public static ResultType createResultRyuukyoku4Wind(){return new ResultType(RyuukyokuType.RYUUKYOKU_WIND);}
	
	
	
	//enums
	private static enum ResType{
		UNDECIDED,
		RYUUKYOKU,
		VICTORY;
		
		public boolean isRyuukyoku(){return (this == RYUUKYOKU);}
		public boolean isVictory(){return this == VICTORY;}
		
		public String toString(){
			switch (this){
			case RYUUKYOKU: return "ryuukyoku...";
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
	private static enum RyuukyokuType{
		UNDECIDED, RYUUKYOKU_HOWANPAI, RYUUKYOKU_KYUUSHU, RYUUKYOKU_KAN, RYUUKYOKU_RIICHI, RYUUKYOKU_WIND;
		public String toString(){
			switch (this){
			case RYUUKYOKU_HOWANPAI: return "Ryuukyoku (Howanpai)";
			case RYUUKYOKU_KYUUSHU: return "Ryuukyoku (Kyuushuu)";
			case RYUUKYOKU_KAN: return "Ryuukyoku (4 kans)";
			case RYUUKYOKU_RIICHI: return "Ryuukyoku (4 riichi)";
			case RYUUKYOKU_WIND: return "Ryuukyoku (4 wind)";
			default: return "??Undecided??";
			}
		}
	}
}
