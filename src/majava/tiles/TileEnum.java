package majava.tiles;

public enum TileEnum {
	O0(),
	M1(),
	M2(),
	M3(),
	M4(),
	M5(),
	M6(),
	M7(),
	M8(),
	M9(),

	P1(),
	P2(),
	P3(),
	P4(),
	P5(),
	P6(),
	P7(),
	P8(),
	P9(),
	
	S1(),
	S2(),
	S3(),
	S4(),
	S5(),
	S6(),
	S7(),
	S8(),
	S9(),
	
	WE(),
	WS(),
	WW(),
	WN(),
	
	DW(),
	DG(),
	DR(),
	
	RED_M5(5),
	RED_P5(14),
	RED_S5(23);
	
	
	
	
	
	
	
	
	
	
	private static final int NUMBER_OF_DIFFERENT_TILES = 34;
	private static final int ID_FIRST_HONOR_TILE = 28;
	
	
	
	
	
	private final int mID;
	private final char mSuit;
	private final char mFace;
	private final String mSuitfaceString;
	
	
	private TileEnum(final int id){

		final String FACE_FOR_RED_DORA = "%";
		final String[] STR_REPS = {"O0", 
								   "M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9",
								   "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9",
								   "S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9",
								   "WE", "WS", "WW", "WN",
								   "DW", "DG", "DR",
								   "M%", "P%", "S%",};
		
		if (id != 0){
			mID = id;
			mSuitfaceString = STR_REPS[mID].charAt(0) + FACE_FOR_RED_DORA;
		}
		else{
			mID = ordinal();
			mSuitfaceString = STR_REPS[mID];
		}
		
		mSuit = mSuitfaceString.charAt(0);
		mFace = mSuitfaceString.charAt(1);
	}
	private TileEnum(){this(0);}
	
	
	
	
	

	final public int getId(){return mID;}
	final public char getSuit(){return mSuit;}
	final public char getFace(){return mFace;}
	final public boolean isRedDora(){return (this == RED_M5 || this == RED_P5 || this == RED_S5);}
	
	final public boolean isYaochuu(){return (isHonor() || isTerminal());}
	final public boolean isHonor(){return (mID >= ID_FIRST_HONOR_TILE);}
	final public boolean isTerminal(){return (mFace == '1' || mFace == '9');}
	
	
	
	
	/*
	method: nextGameTile
	returns the tile that follows this one (used to find a dora from a dora indicator)
	*/
	final public TileEnum nextTile(){
		switch(this){
		case M9: return M1;
		case P9: return P1;
		case S9: return S1;
		case WN: return WE;
		case DR: return DW;
		default: break;
		}
		return values()[mID + 1];
	}
	
	
	
	
	
	
	
	
	
	//compares the IDs of two tiles
	//if they are both 5's, and one is a red dora, the red dora will "come after" the non-red tile
//	@Override
//	final public int compareTo(TileEnum other){
//		
//		//if the tiles have different id's, return the difference
//		if (mID != other.mID) return (mID - other.mID);
//		
//		//at this point, both tiles have the same ID
//		//if both 5's, check if one is red dora
//		if (mFace == '5')
//			if (isRedDora() && !other.isRedDora()) return 1;
//			else return -1;
//		
//		//if the tiles are not 5's, or if both or neither are red doras, return 0
//		return 0;
//	}
//	
//	//returns true if the tiles have the same ID
//	@Override
//	final public boolean equals(Object other){
//		if (other == null || !(other instanceof GameTile)) return false;
//		return (mID == ((GameTile)other).mID);
//	}
	
	//string representaiton of tile's suit/face
	@Override
	public String toString(){return mSuitfaceString;}
	
	
	
	
	
	
	
	
	
	public static final TileEnum createInstanceOfTile(int id){
		return values()[id];
	}
	
	
	
	
	public static void main(String[] h){
		System.out.println("\n\n");
	}
	
	
	
}
