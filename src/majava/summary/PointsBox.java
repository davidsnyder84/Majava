package majava.summary;

//Tenbako“_” 
public class PointsBox {
	
	/////for implementing point sticks later
//	private static final int[] STICK_VALUES = {100, 500, 1000, 5000, 10000};
//	private static final int[] STICK_FACE_DOTS = {8, 0, 1, 5, 9};
	
	private static final int DEFAULT_STARTING_POINTS = 25000;
	
	
	int pointTotal;
	
	
	
	public PointsBox(int startingPoints){
		pointTotal = startingPoints;
	}
	public PointsBox(){this(DEFAULT_STARTING_POINTS);}
	
	
	
	//accessors for points
	public int getPoints(){return pointTotal;}
	//mutators for points, increase or decrease
	public void add(int amount){pointTotal += amount;}
	public void subtract(int amount){pointTotal -= amount;}
	public boolean isHakoshita(){return pointTotal <= 0;}
	
	
	@Override
	public String toString(){return Integer.toString(pointTotal);}
}
