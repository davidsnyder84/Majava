package majava.tiles;

public interface TileInterface extends Comparable<TileInterface>{
	
	//attribute accessors
	public int getId();
	public char getSuit();
	public char getFace();
	public boolean isRedDora();
	
	public boolean isYaochuu();
	public boolean isHonor();
	public boolean isTerminal();
	
	public ImmutableTile getTileBase();
	
	
//	public boolean canChiL();
	
	
	
	public TileInterface clone();
	public int compareTo(TileInterface other);
	public boolean equals(Object other);
}
