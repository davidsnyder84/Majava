package majava.enums;


/*
Enum: Wind
represents a wind (East, South, West, North)
	
methods:
	public:
 	next - returns the next wind clockwise
 	prev - returns the next wind counterclockwise
 	kamichaWind - returns the previous wind
 	toString, toChar - returns a string or char represnation of the wind
*/
public enum Wind {
	EAST, SOUTH, WEST, NORTH, UNKNOWN;
	
	
	public Wind next(){
		switch(this){
		case EAST: return SOUTH;
		case SOUTH: return WEST;
		case WEST: return NORTH;
		case NORTH: return EAST;
		default: return UNKNOWN;
		}
	}
	public Wind prev(){return next().next().next();}
	public Wind kamichaWind(){return prev();}
	
	
	public int getNum(){
		if (this == UNKNOWN) return -1;
		return ordinal();
	}
	public String toString(){
		switch (this){
		case EAST: return "East";
		case SOUTH: return "South";
		case WEST: return "West";
		case NORTH: return "North";
		default: return "Unknown";
		}
	}
	public char toChar(){return toString().charAt(0);}
	
	
}
