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
	
	HUMAN_PLAYER_TURN_START,
	
	UNKNOWN, PLACEHOLDER;
	
	
	private Exclamation exclamation;
	private int seatNumber;
	
	private GameplayEvent(Exclamation ex){exclamation = ex;}
	private GameplayEvent(){this(null);}
	
	public static GameplayEvent calledTileEvent(){return null;}/////////////////////
	public static GameplayEvent CALLED_TILE(){return null;}/////////////////////
	public static GameplayEvent CALLED_TILE_EVENT(){return null;}/////////////////////
	
	public void setExclamation(Exclamation ex, int seat){exclamation = ex; seatNumber = seat;}
	public void setExclamation(Exclamation ex){exclamation = ex;}
	public Exclamation getExclamation(){return exclamation;}
	
	public int getSeat(){return seatNumber;}
	
	public boolean isExclamation(){return (this == CALLED_TILE || this == DECLARED_RIICHI || this == DECLARED_OWN_KAN || this == DECLARED_TSUMO);}
}
