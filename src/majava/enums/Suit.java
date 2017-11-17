package majava.enums;

public enum Suit {
	MANZU,
	PINZU,
	SOUZU,
	WIND,
	DRAGON
	;
	
	@Override
	public String toString(){return toChar() + "";}
	public char toChar(){return super.toString().charAt(0);}
}
