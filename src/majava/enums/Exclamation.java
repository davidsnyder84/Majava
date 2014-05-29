package majava.enums;

public enum Exclamation {
	CHI, PON, KAN, RON, RIICHI, TUSMO, UNKOWN;
	
	@Override
	public String toString(){
		switch (this){
		case CHI: return "Chi";
		case PON: return "Pon";
		case KAN: return "Kan";
		case RON: return "Ron";
		case RIICHI: return "Riichi";
		case TUSMO: return "Tsumo";
		default: return "UnknownExcl";
		}
	}
}
