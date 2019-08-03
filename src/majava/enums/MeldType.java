package majava.enums;


//represents a type of meld (chi, pon, kan, pair)
public enum MeldType {
		NONE, PON, KAN, PAIR, CHI, CHI_L, CHI_M, CHI_H;
		//note: CHI enum is currently used by no one
		
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
			default: return "melttypesomethingwentwrong...";
			}
		}

		public boolean isChi(){return (this == CHI_L || this == CHI_M || this == CHI_H || this == CHI);}
		public boolean isMulti(){return (this == PON || this == KAN || this == PAIR);}
		
		public boolean isPon(){return (this == PON);}
		public boolean isKan(){return (this == KAN);}
		public boolean isPair(){return (this == PAIR);}
		
		public static MeldType[] listOfMultiTypes(){return new MeldType[]{PON, KAN};}
		public static MeldType[] listOfChiTypes(){return new MeldType[]{CHI_L, CHI_M, CHI_H};}
}
