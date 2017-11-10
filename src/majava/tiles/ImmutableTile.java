package majava.tiles;

import java.util.Arrays;
import java.util.List;

public final class ImmutableTile implements TileInterface{
	
	private static final String FACE_FOR_RED_DORA = "%";
	private static final String[] STR_REPS = {"O0", 
											  "M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9",
											  "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9",
											  "S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9",
											  "WE", "WS", "WW", "WN",
											  "DW", "DG", "DR"};
	
	private static final ImmutableTile[] tiles = {
		new ImmutableTile(0),
		new ImmutableTile(1),
		new ImmutableTile(2),
		new ImmutableTile(3),
		new ImmutableTile(4),
		new ImmutableTile(5),
		new ImmutableTile(6),
		new ImmutableTile(7),
		new ImmutableTile(8),
		new ImmutableTile(9),

		new ImmutableTile(10),
		new ImmutableTile(11),
		new ImmutableTile(12),
		new ImmutableTile(13),
		new ImmutableTile(14),
		new ImmutableTile(15),
		new ImmutableTile(16),
		new ImmutableTile(17),
		new ImmutableTile(18),
		
		new ImmutableTile(19),
		new ImmutableTile(20),
		new ImmutableTile(21),
		new ImmutableTile(22),
		new ImmutableTile(23),
		new ImmutableTile(24),
		new ImmutableTile(25),
		new ImmutableTile(26),
		new ImmutableTile(27),
		
		new ImmutableTile(28),
		new ImmutableTile(29),
		new ImmutableTile(30),
		new ImmutableTile(31),
		
		new ImmutableTile(32),
		new ImmutableTile(33),
		new ImmutableTile(34),
		
		
		//red 5's
		new ImmutableTile(5, true),
		new ImmutableTile(14, true),
		new ImmutableTile(23, true)
	};
	
	private static final int INDEX_DUMMY = 0; 
	private static final int INDEX_RED_M5 = 35, INDEX_RED_P5 = 36, INDEX_RED_S5 = 37; 
	
	
	
	public static final int NUMBER_OF_DIFFERENT_TILES = 34;
	public static final int NUMBER_OF_YAOCHUU_TILES = 13;
	private static final int ID_FIRST_HONOR_TILE = 28;
	
	
	private static final TileInterface[] yaochuuTiles = {tiles[1], tiles[9], tiles[10], tiles[18], tiles[19], tiles[27], tiles[28], tiles[29], tiles[30], tiles[31], tiles[32], tiles[33], tiles[34]};
	
	
	private final int tileID;
	private final char suit;
	private final char face;
	private final String suitfaceString;
	private final boolean isRed;
	
	
	private ImmutableTile(int id, boolean isRedDora){
		tileID = id;
		isRed = isRedDora;
		
		if (isRed) suitfaceString = STR_REPS[tileID].charAt(0) + FACE_FOR_RED_DORA;
		else suitfaceString = STR_REPS[tileID];
		
		suit = suitfaceString.charAt(0);
		face = suitfaceString.charAt(1);
	}
	private ImmutableTile(int id){this(id, false);}
	public ImmutableTile clone(){return this;}
	
	
	
	
	

	final public ImmutableTile getTileBase(){return this;}
	
	final public int getId(){return tileID;}
	final public char getSuit(){return suit;}
	final public char getFace(){return face;}
	final public boolean isRedDora(){return isRed;}
	
	final public boolean isYaochuu(){return (isHonor() || isTerminal());}
	final public boolean isHonor(){return (tileID >= ID_FIRST_HONOR_TILE);}
	final public boolean isTerminal(){return (face == '1' || face == '9');}
	
	
	
	
	//returns the tile that follows this one (used to find a dora from a dora indicator)
	final public ImmutableTile nextTile(){
		if (face == '9') return tiles[tileID - 8];
		else if (face == 'N') return tiles[tileID - 3];
		else if (face == 'R') return tiles[tileID - 2];
		else return tiles[tileID + 1];
	}
	
	
	
	
	
	//compares the IDs of two tiles
	//if they are both 5's, and one is a red dora, the red dora will "come after" the non-red tile
	@Override
	final public int compareTo(TileInterface other){
		
		//if the tiles have different id's, return the difference
		if (tileID != other.getId()) return (tileID - other.getId());
		
		//at this point, both tiles have the same ID
		//if both 5's, check if one is red dora
		if (face == '5')
			if (isRedDora() && !other.isRedDora()) return 1;
			else return -1;
		
		//if the tiles are not 5's, or if both or neither are red doras, return 0
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
	
	
	
	
	
	//factory methods
	public static final ImmutableTile retrieveTile(int id){
		if (id < 0 || id > NUMBER_OF_DIFFERENT_TILES) return dummyTile();
		return tiles[id];
	}
	public static final ImmutableTile retrieveTile(String suitfaceString){return retrieveTile(Arrays.asList(STR_REPS).indexOf(suitfaceString.toUpperCase()));}
	
	public static final ImmutableTile retrieveTileRed(int id){
		if (id < 0 || id > NUMBER_OF_DIFFERENT_TILES) return dummyTile();
		if (id != 5 && id != 14 && id != 23) return retrieveTile(id);
		return tiles[NUMBER_OF_DIFFERENT_TILES + 1 + (id/9)];
	}
	private static final ImmutableTile dummyTile(){return tiles[INDEX_DUMMY];}
	
	
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
	
	
	
	
	
	public static void main(String[] s){
		
		System.out.println();
//		List<TileInterface> il = retrieveMultipleTiles(5,6,7,8,9);
//		for (TileInterface t: il) System.out.println(t.toString());
		for (TileInterface t: retrieveMultipleTiles(5,6,7,8,9)) System.out.println(t.toString());
//		for (int i = 0; i < retrieveMultipleTiles(5,6,7,8,9).size(); i++)System.out.println(i);
		System.out.println("\n\n"); 
		
		
//		List<ImmutableTile> list = new ArrayList<ImmutableTile>();
//		for (int i = 0; i <= NUMBER_OF_DIFFERENT_TILES; i++) list.add(ImmutableTile.retrieveTile(i));
//		for (ImmutableTile t: list) System.out.println(t.toString());
		
		
		ImmutableTile it = tiles[3];
		System.out.println(it.toString());
		
		GameTile gt= new GameTile(3);
		System.out.println(gt.toString());
		
		System.out.println(it.equals(gt));
		System.out.println(gt.equals(it));
		
		System.out.println();
		System.out.println(new Integer(3).equals(it));
		System.out.println(it.equals(new Integer(3)));

		System.out.println();
		List<Integer> a = Arrays.asList(new Integer(2),new Integer(3),new Integer(4));
		List<TileInterface> ls = retrieveMultipleTiles(2,3,4);
		System.out.println(a.toString());
		System.out.println(a.contains(it));
		System.out.println(ls.toString());
		System.out.println(ls.contains(3));
	}

}
