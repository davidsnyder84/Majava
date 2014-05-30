package majava.summary;

public class Result {
	
	private static enum ResultType{
		UNDECIDED,
		DRAW_WASHOUT, DRAW_KYUUSHU, DRAW_4KAN, DRAW_4RIICHI, DRAW_4WIND,
		VICTORY_E, VICTORY_S, VICTORY_W, VICTORY_N;
		
		public boolean isDraw(){return (this == DRAW_WASHOUT || this == DRAW_KYUUSHU || this == DRAW_4KAN || this == DRAW_4RIICHI || this == DRAW_4WIND);}
		public boolean isVictory(){return (this == VICTORY_E || this == VICTORY_S || this == VICTORY_W || this == VICTORY_N);}
		
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
	
	
	
	public Result(){
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
	}
}
