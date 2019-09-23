package majava.enums;



//represents a wind (East, South, West, North)
public enum Wind {
	EAST, SOUTH, WEST, NORTH, UNKNOWN;
	
	private static final Wind DEALER_WIND = EAST;
	
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
	public Wind kamichaWind(){return toimenWind().next();}
	public Wind toimenWind(){return shimochaWind().next();}
	public Wind shimochaWind(){return next();}
	
	public boolean isDealerWind(){return this == DEALER_WIND;}
	
	
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
