package majava.enums;

public enum GameplayEvent{
	START_OF_ROUND,
	DREW_TILE,
	NEW_DORA_INDICATOR,
	DISCARDED_TILE,
	MADE_OPEN_MELD,
	MADE_ANKAN, MADE_MINKAN, MADE_OWN_KAN,
	END_OF_ROUND,
	
	CALLED_TILE(Exclamation.UNKNOWN),
	DECLARED_RIICHI(Exclamation.RIICHI), DECLARED_OWN_KAN(Exclamation.OWN_KAN), DECLARED_TSUMO(Exclamation.TSUMO),
	
	UNKNOWN, PLACEHOLDER;
	
	
	private Exclamation mExcl;
	private int mSeat;
	
	private GameplayEvent(Exclamation e){mExcl = e;}
	private GameplayEvent(){this(null);}
	
	
	
	public void setExclamation(Exclamation e, int seat){mExcl = e; mSeat = seat;}
	public void setExclamation(Exclamation e){mExcl = e;}
	public Exclamation getExclamation(){return mExcl;}
	
//	public void setSeat(int seat){mSeat = seat;}
	public int getSeat(){return mSeat;}
	
	public boolean isExclamation(){return (this == CALLED_TILE || this == DECLARED_RIICHI || this == DECLARED_OWN_KAN || this == DECLARED_TSUMO);}
	
}
