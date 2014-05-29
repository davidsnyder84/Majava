package majava.enums;

public enum Exclamation{
	CHI, PON, KAN, RON, RIICHI, OWN_KAN, TSUMO, NONE, UNKNOWN;
	
	@Override
	public String toString(){
		switch (this){
		case CHI: return "Chi";
		case PON: return "Pon";
		case KAN: return "Kan";
		case RON: return "Ron";
		case RIICHI: return "Riichi";
		case OWN_KAN: return "Kan";
		case TSUMO: return "Tsumo";
		case NONE: return "None";
		default: return "UnknownExcl";
		}
	}
	public boolean isCall(){return (this == CHI || this == PON || this == KAN || this == RON);}
	public boolean isTurnAction(){return (this == RIICHI || this == OWN_KAN || this == TSUMO);}
}
