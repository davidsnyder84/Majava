package majava.tiles;

import java.util.Arrays;
import java.util.List;

//immutable representaion of a base tile (one singleton exists for each of the 34 tiles)
public class Janpai implements TileInterface{	
	private static final String FACE_FOR_RED_DORA = "%";
	private static final String[] STR_REPS = {"O0", 
											  "M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9",
											  "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9",
											  "S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9",
											  "WE", "WS", "WW", "WN",
											  "DW", "DG", "DR"};
	
	//singletons (immutable, so making them public is ok)
	public static final Janpai O0 = new Janpai(0),
			M1=new Janpai(1), M2=new Janpai(2), M3=new Janpai(3), M4=new Janpai(4), M5=new Janpai(5), M6=new Janpai(6), M7=new Janpai(7), M8=new Janpai(8), M9=new Janpai(9),
			P1=new Janpai(1+9), P2=new Janpai(2+9), P3=new Janpai(3+9), P4=new Janpai(4+9), P5=new Janpai(5+9), P6=new Janpai(6+9), P7=new Janpai(7+9), P8=new Janpai(8+9), P9=new Janpai(9+9),
			S1=new Janpai(1+18), S2=new Janpai(2+18), S3=new Janpai(3+18), S4=new Janpai(4+18), S5=new Janpai(5+18), S6=new Janpai(6+18), S7=new Janpai(7+18), S8=new Janpai(8+18), S9=new Janpai(9+18),
			WE=new Janpai(28), WS=new Janpai(29), WW=new Janpai(30), WN=new Janpai(31),
			DW=new Janpai(32), DG=new Janpai(33), DR=new Janpai(34),
			REDM5 = new Janpai(5, true), REDP5 = new Janpai(5+9, true), REDS5 = new Janpai(5+18, true);
	
	private static final Janpai[] tiles = {
		O0, 
		M1, M2, M3, M4, M5, M6, M7, M8, M9,
		P1, P2, P3, P4, P5, P6, P7, P8, P9,
		S1, S2, S3, S4, S5, S6, S7, S8, S9,
		WE, WS, WW, WN,
		DW, DG, DR,
		REDM5, REDP5, REDS5
	};
	public static final Janpai DUMMY_TILE = O0; 
	
	
	
	public static final int NUMBER_OF_DIFFERENT_TILES = 34;
	public static final int NUMBER_OF_YAOCHUU_TILES = 13;
	private static final int ID_FIRST_HONOR_TILE = 28;
	private static final int ID_FIRST_WIND_TILE = 28, ID_LAST_WIND_TILE = 31, ID_FIRST_DRAGON_TILE = 32, ID_LAST_DRAGON_TILE = 34;
	
	
	private static final TileInterface[] yaochuuTiles = {tiles[1], tiles[9], tiles[10], tiles[18], tiles[19], tiles[27], tiles[28], tiles[29], tiles[30], tiles[31], tiles[32], tiles[33], tiles[34]};
	
	
	private final int tileID;
	private final char suit;
	private final char face;
	private final String suitfaceString;
	private final boolean isRed;
	
	
	private Janpai(int id, boolean isRedDora){
		tileID = id;
		isRed = isRedDora;
		
		if (isRed) suitfaceString = STR_REPS[tileID].charAt(0) + FACE_FOR_RED_DORA;
		else suitfaceString = STR_REPS[tileID];
		
		suit = suitfaceString.charAt(0);
		face = suitfaceString.charAt(1);
	}
	private Janpai(int id){this(id, false);}
	public Janpai clone(){return this;}
	
	
	
	
	

	final public Janpai getTileBase(){return this;}
	
	final public int getId(){return tileID;}
	final public char getSuit(){return suit;}
	final public char getFace(){return face;}
	final public boolean isRedDora(){return isRed;}
	
	final public boolean isTanyao(){return !isYaochuu();}
	final public boolean isYaochuu(){return (isHonor() || isTerminal());}
	final public boolean isHonor(){return (tileID >= ID_FIRST_HONOR_TILE);}
	final public boolean isWind(){return (tileID >= ID_FIRST_WIND_TILE && tileID <= ID_LAST_WIND_TILE);}
	final public boolean isDragon(){return (tileID >= ID_FIRST_DRAGON_TILE);}
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
	@Override
	final public int compareTo(TileInterface other){
		if (tileID != other.getId())
			return (tileID - other.getId());
		
		if (face == '5')
			if (isRedDora() && !other.isRedDora()) return 1;
			else return -1;
		
		return 0;
	}
	
	//returns true if the tiles have the same ID
	@Override
	final public boolean equals(Object other){
		if (other == null || !(other instanceof TileInterface)) return false;
		return tileID == ((TileInterface)other).getId();
	}
	
	@Override
	public String toString(){return suitfaceString;}
//	public String toString(){/////implementation without using suitfaceString
//		if (isRedDora()) return getSuit() + FACE_FOR_RED_DORA;
//		return getSuit() + getFace() + "";
//	}
	
	
	
	
	//factory methods
	public static final Janpai retrieveTile(int id){
		if (id < 0 || id > NUMBER_OF_DIFFERENT_TILES) return DUMMY_TILE;
		return tiles[id];
	}
	public static final Janpai retrieveTile(String suitface){return valueOf(suitface);}
	public static final Janpai valueOf(String suitface){return retrieveTile(Arrays.asList(STR_REPS).indexOf(suitface.toUpperCase()));}
	
	public static final Janpai retrieveTileRed(int id){
		if (id < 0 || id > NUMBER_OF_DIFFERENT_TILES) return DUMMY_TILE;
		if (id != 5 && id != 14 && id != 23) return retrieveTile(id);
		return tiles[NUMBER_OF_DIFFERENT_TILES + 1 + (id/9)];
	}
	public static final Janpai retrieveTile(int id, boolean wantRed){
		if (wantRed) return retrieveTileRed(id);
		return retrieveTile(id);
	}
	
	
	//retrieve multiple tiles
	public static final List<TileInterface> retrieveMultipleTiles(Integer... ids){
		TileInterface[] list = new TileInterface[ids.length];
		for (int i = 0; i < list.length; i++) list[i] = retrieveTile(ids[i]);
		return Arrays.asList(list);
	}
	public static final List<TileInterface> retrieveMultipleTiles(List<Integer> ids){return retrieveMultipleTiles((Integer[])ids.toArray());}
	
	//retrieve list of yaochuu tiles
	public static final List<TileInterface> retrievelistOfYaochuuTiles(){return Arrays.asList(yaochuuTiles);}
	public static final Integer[] retrieveYaochuuTileIDs(){return new Integer[]{1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34};}
	
}
