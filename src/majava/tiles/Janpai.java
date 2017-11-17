package majava.tiles;

import java.util.Arrays;
import java.util.List;

import majava.control.Majava;



//problems:
//--Can't override equals (this is unacceptable for red fives)
//--Can't override compareTo (this is ok)
public enum Janpai{
	O0(0), 
	M1(1), M2(2), M3(3), M4(4), M5(5), RED_M5(5, true), M6(6), M7(7), M8(8), M9(9),
	P1(1+9), P2(2+9), P3(3+9), P4(4+9), P5(5+9), RED_P5(5+9, true), P6(6+9), P7(7+9), P8(8+9), P9(9+9),
	S1(1+18), S2(2+18), S3(3+18), S4(4+18), S5(5+18), RED_S5(5+18, true), S6(6+18), S7(7+18), S8(8+18), S9(9+18),
	WE(28), WS(29), WW(30), WN(31),
	DW(32), DG(33), DR(34)
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
	
	
	private Janpai(int id, boolean isRedDora){
		isRed = isRedDora;
		int tempID = id;
		String tempSuitface= toString();
		
		if (isRedDora){
			if (toString().equals("RED_M5")){
				tempSuitface = "M%";
				tempID = 5;
			}
			if (toString().equals("RED_P5")){
				tempSuitface = "P%";
				tempID = 5+9;
			}
			if (toString().equals("RED_S5")){
				tempSuitface = "S%";
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
	private Janpai(int id){this(id, false);}
	
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
	final public boolean isRedDora(){return (this == RED_M5 || this == RED_P5 || this == RED_S5);}
	
	final public boolean isTanyao(){return !isYaochuu();}
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
		return !isHonor() && face != badface1 && face != badface2;
	}
	//Txx
	final public boolean canCompleteChiL(){return canCompleteChiType('8', '9');}
	//xTx
	final public boolean canCompleteChiM(){return canCompleteChiType('1', '9');}
	//xxT
	final public boolean canCompleteChiH(){return canCompleteChiType('1', '2');}
	
	
	
	
	
	//returns true if the tiles have the same ID
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
	
	
	public static void main(String[] args) {
		for (Janpai j : values())
			System.out.println(j.toString());
//		System.out.println(j.suitfaceString);
			
			System.out.println(Janpai.values().toString());
		System.out.println("gay");
	}
}
