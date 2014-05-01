package utility;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
 


public class ImageRotator {
	
	int mAngle;
	
	
	public ImageRotator(int angle){mAngle = angle;}
	public ImageRotator(){this(90);}
	
	

	public void setAngle(int angle){mAngle = angle;}
	public int getAngle(){return mAngle;}
	
	
	
	private ImageIcon __rotateImage(ImageIcon aicon, int angle){
		
		int w = aicon.getIconWidth();
		int h = aicon.getIconHeight();
		int type = BufferedImage.TYPE_4BYTE_ABGR;  // other options, see api
		
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
	public ImageIcon rotateImage(ImageIcon aicon){
		switch(mAngle){
		case 90: case -90: return __rotateImage(aicon, mAngle);
		case 180: return __rotateImage(__rotateImage(aicon, 90), 90);
		default: return aicon;
		}
	}
	
	
	
	//	public static BufferedImage rotate(BufferedImage image, double angle) {
	//	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	//	    int w = image.getWidth(), h = image.getHeight();
	//	    int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
	//	    GraphicsConfiguration gc = getDefaultConfiguration();
	//	    BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
	//	    Graphics2D g = result.createGraphics();
	//	    g.translate((neww-w)/2, (newh-h)/2);
	//	    g.rotate(angle, w/2, h/2);
	//	    g.drawRenderedImage(image, null);
	//	    g.dispose();
	//	    return result;
	//	}
	    
	    
	    
	 
}