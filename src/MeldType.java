
public enum MeldType {
		NONE, PON, KAN, PAIR, CHI, CHI_L, CHI_M, CHI_H;
		
		@Override
		public String toString(){
			switch (this){
//			case CHI: case CHI_L: case CHI_M: case CHI_H: return "Chi";
			case NONE: return "None";
			case PON: return "Pon";
			case KAN: return "Kan";
			case PAIR: return "Pair";
			case CHI: return "Chi";
			case CHI_L: return "Chi-L";
			case CHI_M: return "Chi-M";
			case CHI_H: return "Chi-H";
			default: return "how...";
			}
		}

		//returns true if the meldtype is a chi
		public boolean isChi(){return (this == CHI || this == CHI_L || this == CHI_M || this == CHI_H);}
		//returns true if the meldtype is a multi
		public boolean isMulti(){return (this == PON || this == KAN || this == PAIR);}
}
