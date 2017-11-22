package majava.enums;

public enum CallType {
	NONE, CHI_L, CHI_M, CHI_H, PON, KAN, RON, NONSPECIFIC_CHI, UNDECIDED;
	
	@Override
	public String toString(){
		switch (this){
		case CHI_L: case CHI_M: case CHI_H: return "Chi";
		case PON: return "Pon";
		case KAN: return "Kan";
		case RON: return "Ron";
		default: return "None";
		}
	}
	public Exclamation toExclamation(){
		switch (this){
		case CHI_L: case CHI_M: case CHI_H: return Exclamation.CHI;
		case PON: return Exclamation.PON;
		case KAN: return Exclamation.KAN;
		case RON: return Exclamation.RON;
		case NONE: return Exclamation.NONE;
		default: return Exclamation.UNKNOWN;
		}
	}
}
