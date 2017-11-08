package utility;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
 

//used to rotate an imageicon in intervals of 90 degrees
public class ImageRotator {
	
	private int angle;
	
	//constructors, require an angle
	public ImageRotator(int receivedAngle){angle = receivedAngle;}
	public ImageRotator(){this(90);}
	
	
	//accessor
	public int getAngle(){return angle;}
	
	
	
	
	//receives an imageicon, rotates it and returns the rotated icon
	public ImageIcon rotateImage(ImageIcon aicon){
		switch(angle){
		case 90: case -90: return rotateImage(aicon, angle);
		case 180: return rotateImage(rotateImage(aicon, 90), 90);
		default: return aicon;
		}
	}
	private ImageIcon rotateImage(ImageIcon aicon, int angleOfRotation){
		
		int w = aicon.getIconWidth();
		int h = aicon.getIconHeight();
		int type = BufferedImage.TYPE_4BYTE_ABGR;
		
		BufferedImage bimg = new BufferedImage(h, w, type);
		Graphics2D grBimg = bimg.createGraphics();
		
		double x = (h - w)/2.0;
		double y = (w - h)/2.0;
		
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.toRadians(angleOfRotation), w/2.0, h/2.0);
		
		grBimg.drawImage(aicon.getImage(), at, null);
		grBimg.dispose();
		
		return new ImageIcon(bimg);
	}
	    
	    
	 
}