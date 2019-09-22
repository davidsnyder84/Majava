package majava.summary;

//Tenbako“_” 
public class PointsBox {
//	private static final int[] STICK_VALUES = {100, 500, 1000, 5000, 10000};
//	private static final int[] STICK_FACE_DOTS = {8, 0, 1, 5, 9};
	
	private static final int DEFAULT_STARTING_POINTS = 25000;
	
	
	final int pointTotal;
	
	
	public PointsBox(int pts){
		pointTotal = pts;
	}
	public PointsBox(){this(DEFAULT_STARTING_POINTS);}
	
	
	
	//accessors for points
	public int getPoints(){return pointTotal;}
	public boolean isHakoshita(){return pointTotal <= 0;}
	
	
	//mutators for points, increase or decrease
	public PointsBox add(int amount){return new PointsBox(pointTotal + amount);}
	public PointsBox subtract(int amount){return this.add(0 - amount);}
	
	
	@Override
	public String toString(){return Integer.toString(pointTotal);}
}
