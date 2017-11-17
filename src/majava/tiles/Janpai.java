package majava.tiles;

import java.util.Arrays;
import java.util.List;

import majava.control.testcode.TestingJanpai;



public enum Janpai{	
	O0, 
	M1, M2, M3, M4, M5, M6, M7, M8, M9,
	P1, P2, P3, P4, P5, P6, P7, P8, P9,
	S1, S2, S3, S4, S5, S6, S7, S8, S9,
	WE, WS, WW, WN,
	DW, DG, DR,
	;
	
	
	public static final int NUMBER_OF_DIFFERENT_TILES = 34;
	public static final int NUMBER_OF_YAOCHUU_TILES = 13;
	private static final int ID_FIRST_HONOR_TILE = 28;
	private static final Janpai DUMMY_TILE = O0;
	
	
	private static final Janpai[] yaochuuTiles = {M1, M9, P1, P9, S1, S9, WE, WS, WW, WN, DW, DG, DR};
	private static final Janpai[] tiles = values();
	
	
	
	final public int tileID(){return ordinal();}
	final public char suit(){return toString().charAt(0);}
	final public char face(){return toString().charAt(1);}
	
	final public boolean isTanyao(){return !isYaochuu();}
	final public boolean isYaochuu(){return (isHonor() || isTerminal());}
	final public boolean isHonor(){return (tileID() >= ID_FIRST_HONOR_TILE);}
	final public boolean isTerminal(){return (face() == '1' || face() == '9');}
	
	
	//returns the tile that follows this one (used to find a dora from a dora indicator)
	final public Janpai nextTile(){
		if (face() == '9') return tiles[tileID() - 8];
		else if (face() == 'N') return tiles[tileID() - 3];
		else if (face() == 'R') return tiles[tileID() - 2];
		else return tiles[tileID() + 1];
	}
	//returns true if it's possible for Tile T to finish a Chi of the given type
	final private boolean canCompleteChiType(char badface1, char badface2){
		return !isHonor() && face() != badface1 && face() != badface2;
	}
	//Txx
	final public boolean canCompleteChiL(){return canCompleteChiType('8', '9');}
	//xTx
	final public boolean canCompleteChiM(){return canCompleteChiType('1', '9');}
	//xxT
	final public boolean canCompleteChiH(){return canCompleteChiType('1', '2');}
	
	
	
	
	public static final Janpai retrieveTile(int id){
		if (id < 0 || id > NUMBER_OF_DIFFERENT_TILES) return DUMMY_TILE;
		return tiles[id];
	}
//	public static final ImmutableTile retrieveTile(String suitfaceString){return retrieveTile(Arrays.asList(STR_REPS).indexOf(suitfaceString.toUpperCase()));}
	
	
	//retrieve multiple tiles
	public static final List<Janpai> retrieveMultipleTiles(Integer... ids){
		Janpai[] list = new Janpai[ids.length];
		for (int i = 0; i < list.length; i++) list[i] = retrieveTile(ids[i]);
		return Arrays.asList(list);
	}
	public static final List<Janpai> retrieveMultipleTiles(List<Integer> ids){return retrieveMultipleTiles((Integer[])ids.toArray());}
	
	public static final List<Janpai> retrievelistOfYaochuuTiles(){return Arrays.asList(yaochuuTiles);}
	public static final Integer[] retrieveYaochuuTileIDs(){return new Integer[]{1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34};}
	
	
	
	
	
	public static void main(String[] args) {TestingJanpai.main(args);}
}
