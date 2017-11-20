package majava.enums;

//not implemented in game yet
public enum Suit {
	MANZU,
	PINZU,
	SOUZU,
	WIND,
	DRAGON
	;
	
	@Override
	public String toString(){return Character.toString(toChar());}
	public char toChar(){return super.toString().charAt(0);}
}
