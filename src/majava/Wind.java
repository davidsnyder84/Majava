package majava;

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
	
	
	
	@Override
	public String toString(){
		switch (this){
		case EAST: return "East";
		case SOUTH: return "South";
		case WEST: return "West";
		case NORTH: return "North";
		default: return "Unknown";
		}
	}
	public char toChar(){
		return this.toString().charAt(0);
//		switch (this){
//		case EAST: return 'E';
//		case SOUTH: return 'S';
//		case WEST: return 'W';
//		case NORTH: return 'N';
//		default: return 'U';
//		}
	}
	public int getNum(){
		switch(this){
		case EAST: return 0;
		case SOUTH: return 1;
		case WEST: return 2;
		case NORTH: return 3;
		default: return -1;
		}
	}
	
	
	
	
	public static void main(String[] args) {
		Wind w = Wind.EAST;
		System.out.println(w.toString());
		System.out.println(w.toChar());

		w = w.next();
		System.out.println(w.toString());
		System.out.println(w.toChar());
		
		w = w.prev();
		System.out.println(w.toString());
		System.out.println(w.toChar());
	}

}
