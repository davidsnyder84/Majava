package utility;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
 

/*
Class: ImageRotator
used to rotate an imageicon in intervals of 90 degrees

data:
	mAngle - the angle at which to rotate an imageicon
	
methods:
	constructors:
	Requires an angle
	
	public:
		rotateImage - rotates an icon and returns the rotated form
*/
public class ImageRotator {
	
	private int mAngle;
	
	//constructors, require an angle
	public ImageRotator(int angle){mAngle = angle;}
	public ImageRotator(){this(90);}
	
	
	//accessor
	public int getAngle(){return mAngle;}
	
	
	
	
	//receives an imageicon, rotates it and returns the rotated icon
	public ImageIcon rotateImage(ImageIcon aicon){
		switch(mAngle){
		case 90: case -90: return __rotateImage(aicon, mAngle);
		case 180: return __rotateImage(__rotateImage(aicon, 90), 90);
		default: return aicon;
		}
	}
	private ImageIcon __rotateImage(ImageIcon aicon, int angle){
		
		int w = aicon.getIconWidth();
		int h = aicon.getIconHeight();
		int type = BufferedImage.TYPE_4BYTE_ABGR;
		
		BufferedImage bimg = new BufferedImage(h, w, type);
		Graphics2D grBimg = bimg.createGraphics();
		
		double x = (h - w)/2.0;
		double y = (w - h)/2.0;
		
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.toRadians(angle), w/2.0, h/2.0);
		
		grBimg.drawImage(aicon.getImage(), at, null);
		grBimg.dispose();
		
		return new ImageIcon(bimg);
	}
	    
	    
	 
}