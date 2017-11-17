package majava.tiles;

import java.util.Arrays;
import java.util.List;

import majava.control.Majava;

public enum Janpai{
	O0, 
	M1, M2, M3, M4, M5, M6, M7, M8, M9,
	P1, P2, P3, P4, P5, P6, P7, P8, P9,
	S1, S2, S3, S4, S5, S6, S7, S8, S9,
	WE, WS, WW, WN,
	DW, DG, DR,
	RED_M5(true), RED_P5(true), RED_S5(true)
	;
	private static final int INDEX_DUMMY = 0; 
	private static final int INDEX_RED_M5 = 35, INDEX_RED_P5 = 36, INDEX_RED_S5 = 37; 
	
	
	
	public static final int NUMBER_OF_DIFFERENT_TILES = 34;
	public static final int NUMBER_OF_YAOCHUU_TILES = 13;
	private static final int ID_FIRST_HONOR_TILE = 28;
	
	
	private static final Janpai[] yaochuuTiles = {M1, M9, P1, P9, S1, S9, WE, WS, WW, WN, DW, DG, DR};
	private static final Janpai[] tiles = values();
	
	
	
	private final int tileID;
	private final char suit;
	private final char face;
	private final String suitfaceString;
	private final boolean isRed;
	
	
	private Janpai(boolean isRedDora){
		isRed = isRedDora;
		int tempID = ordinal();
		String tempSuitface= toString();
		
		
		if (isRed){
			if (toString().equals("RED_M5")){
				tempSuitface = "M%";
				tempID = 5;
			}
			if (toString().equals("RED_P5")){
				tempSuitface = "P%";
				tempID = 5+9;
			}
			if (toString().equals("RED_M5")){
				tempSuitface = "M%";
				tempID = 5+18;
			}
//			initializeRedDora();
		}
		
		suitfaceString = tempSuitface;
		tileID = tempID;
		suit = suitfaceString.charAt(0);
		face = suitfaceString.charAt(1);
	}
//	private Janpai(int id){this(id, false);}
	private Janpai(){this(false);}
	
//	private void initializeRedDora(){
//		if (toString().equals("RED_M5")){
//			suitfaceString = "M%";
//			tileID = M5.ordinal();
//		}
//		if (toString().equals("RED_P5")){
//			suitfaceString = "P%";
//			tileID = P5.ordinal();
//		}
//		if (toString().equals("RED_M5")){
//			suitfaceString = "M%";
//			tileID = S5.ordinal();
//		}
//	}
	
	
//	public Janpai clone(){return this;}
	
	
	
	
	final public int getId(){return tileID;}
	final public char getSuit(){return suit;}
	final public char getFace(){return face;}
	final public boolean isRedDora(){return isRed;}
	
	final public boolean isYaochuu(){return (isHonor() || isTerminal());}
	final public boolean isHonor(){return (tileID >= ID_FIRST_HONOR_TILE);}
	final public boolean isTerminal(){return (face == '1' || face == '9');}
	
	
	
	
	//returns the tile that follows this one (used to find a dora from a dora indicator)
	final public Janpai nextTile(){
		if (face == '9') return tiles[tileID - 8];
		else if (face == 'N') return tiles[tileID - 3];
		else if (face == 'R') return tiles[tileID - 2];
		else return tiles[tileID + 1];
	}
	//returns true if it's possible for Tile T to finish a Chi of the given type
	final private boolean canCompleteChiType(char badface1, char badface2){
		return !isHonor() && getFace() != badface1 && getFace() != badface2;
	}
	//Txx
	final public boolean canCompleteChiL(){return canCompleteChiType('8', '9');}
	//xTx
	final public boolean canCompleteChiM(){return canCompleteChiType('1', '9');}
	//xxT
	final public boolean canCompleteChiH(){return canCompleteChiType('1', '2');}
	
	
	
	//compares the IDs of two tiles. if they are both 5's, and one is a red dora, the red dora will "come after" the non-red tile
//	@Override
//	final public int compareTo(Janpai other){
//		if (tileID != other.getId())
//			return (tileID - other.getId());
//		
//		if (face == '5')
//			if (isRedDora() && !other.isRedDora()) return 1;
//			else return -1;
//		
//		return 0;
//	}
//	
//	//returns true if the tiles have the same ID
//	@Override
//	final public boolean equals(Object other){
//		if (other == null || !(other instanceof TileInterface)) return false;
//		return tileID == ((TileInterface)other).getId();
//	}
//	
//	@Override
//	public String toString(){return suitfaceString;}
//	
//	
//	
//	
//	
//	//factory methods
//	public static final ImmutableTile retrieveTile(int id){
//		if (id < 0 || id > NUMBER_OF_DIFFERENT_TILES) return dummyTile();
//		return tiles[id];
//	}
//	public static final ImmutableTile retrieveTile(String suitfaceString){return retrieveTile(Arrays.asList(STR_REPS).indexOf(suitfaceString.toUpperCase()));}
//	
//	public static final ImmutableTile retrieveTileRed(int id){
//		if (id < 0 || id > NUMBER_OF_DIFFERENT_TILES) return dummyTile();
//		if (id != 5 && id != 14 && id != 23) return retrieveTile(id);
//		return tiles[NUMBER_OF_DIFFERENT_TILES + 1 + (id/9)];
//	}
//	private static final ImmutableTile dummyTile(){return tiles[INDEX_DUMMY];}
//	
//	
//	//retrieve multiple tiles
//	public static final List<TileInterface> retrieveMultipleTiles(Integer... ids){
//		TileInterface[] list = new TileInterface[ids.length];
//		for (int i = 0; i < list.length; i++) list[i] = retrieveTile(ids[i]);
//		return Arrays.asList(list);
//	}
//	public static final List<TileInterface> retrieveMultipleTiles(List<Integer> ids){return retrieveMultipleTiles((Integer[])ids.toArray());}
//	
//	//retrieve list of yaochuu tiles
//	public static final List<TileInterface> retrievelistOfYaochuuTiles(){return Arrays.asList(yaochuuTiles);}
//	public static final Integer[] retrieveYaochuuTileIDs(){return new Integer[]{1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34};}
	
	
	
	
	
	
	
//	@Override
//	public int getId() {
//		return 0;
//	}
//
//	@Override
//	public char getSuit() {
//		return 0;
//	}
//
//	@Override
//	public char getFace() {
//		return 0;
//	}
//
//	@Override
//	public boolean isRedDora() {
//		return false;
//	}
//
//	@Override
//	public boolean isYaochuu() {
//		return false;
//	}
//
//	@Override
//	public boolean isHonor() {
//		return false;
//	}
//
//	@Override
//	public boolean isTerminal() {
//		return false;
//	}
//
//	@Override
//	public ImmutableTile getTileBase() {
//		return null;
//	}
//
//	@Override
//	public int compareTo(TileInterface other) {
//		return 0;
//	}
	
	
	public static void main(String[] args) {
		for (Janpai j : values())
			System.out.println(j);
			
			System.out.println(Janpai.values().toString());
		System.out.println("gay");
	}
}
