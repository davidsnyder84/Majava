package majava.player;


//used to indicate what type of draw a player needs, if any
public class DrawingNeed {
	private static enum DrawType{NONE, NORMAL, RINSHAN;}
	
	private DrawType drawType;
	
	public DrawingNeed(){
		setNone();
	}
	
	//check if the players needs to draw a tile, and what type of draw (normal vs rinshan)
	public boolean needsToDraw(){return (needsNormal() || needsRinshan());}
	public boolean needsNormal(){return (drawType == DrawType.NORMAL);}
	public boolean needsRinshan(){return (drawType == DrawType.RINSHAN);}
	
	private void setDrawNeeded(DrawType dt){drawType = dt;}
	public void setRinshan(){setDrawNeeded(DrawType.RINSHAN);}
	public void setNormal(){setDrawNeeded(DrawType.NORMAL);}
	public void setNone(){setDrawNeeded(DrawType.NONE);}
}
