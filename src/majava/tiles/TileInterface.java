package majava.tiles;

public interface TileInterface{
	
	//attribute accessors
	public int getId();
	public char getSuit();
	public char getFace();
	public boolean isRedDora();
	
	public boolean isYaochuu();
	public boolean isHonor();
	public boolean isTerminal();
	
	
	
	
	//clone
	public TileInterface clone();
}
