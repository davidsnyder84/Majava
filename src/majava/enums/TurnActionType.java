package majava.enums;

public enum TurnActionType {
	DISCARD, ANKAN, MINKAN, RIICHI, TSUMO, UNDECIDED;
	
	public boolean isDiscard(){return this == DISCARD;}
	public boolean isNotDiscard(){return !isDiscard();}
}
