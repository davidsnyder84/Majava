package majava;

public enum GameType {
	SINGLE_ROUND, TONPUUSEN, HANCHAN;
	
	@Override
	public String toString(){
		switch (this){
		case SINGLE_ROUND: return "Single";
		case TONPUUSEN: return "Tonpuusen";
		case HANCHAN: return "Hanchan";
		default: return "what";
		}
	}
}