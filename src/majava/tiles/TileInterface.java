package majava.tiles;

public interface TileInterface extends Comparable<TileInterface>{
//public interface TileInterface extends Comparable<TileInterface>, Cloneable{
	
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
	
	
	
	//clone
	public TileInterface clone();
	
	//compareTo
	public int compareTo(TileInterface other);
	
	//equals
	public boolean equals(Object other);
}
