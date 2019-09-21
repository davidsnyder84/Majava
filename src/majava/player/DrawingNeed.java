package majava.player;


//used to indicate what type of draw a player needs, if any
public class DrawingNeed {
	private static enum DrawType{NONE, NORMAL, RINSHAN;}
	
	private final DrawType drawType;
	
	DrawingNeed(DrawType dt){
		drawType = dt;
	}
	public DrawingNeed(){this(DrawType.NONE);}
	
	
	//check if the players needs to draw a tile, and what type of draw (normal vs rinshan)
	public boolean needsToDraw(){return (needsNormal() || needsRinshan());}
	public boolean needsNormal(){return (drawType == DrawType.NORMAL);}
	public boolean needsRinshan(){return (drawType == DrawType.RINSHAN);}
	
	
	public DrawingNeed rinshan(){return new DrawingNeed(DrawType.RINSHAN);}
	public DrawingNeed normal(){return new DrawingNeed(DrawType.NORMAL);}
	public DrawingNeed none(){return new DrawingNeed(DrawType.NONE);}
	
	public DrawingNeed setRinshan(){return new DrawingNeed(DrawType.RINSHAN);}
	public DrawingNeed setNormal(){return new DrawingNeed(DrawType.NORMAL);}
	public DrawingNeed setNone(){return new DrawingNeed(DrawType.NONE);}
}
