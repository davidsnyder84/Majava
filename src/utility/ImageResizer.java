package utility;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

//for demo
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



/*
Class: ImageResizer
used to resize an imageicon to a given scaled value

data:
	mScaler - the scale at which to resize an imageicon
	
methods:
	constructors:
	Requires a scaler
	
	public:
		rotateImage - resizes an icon and returns the resized form
*/
public class ImageResizer {
	
	
	private static final double DEFAULT_SCALER = 1;
	
	
	
	private double mScaler;
	
	
	//constructors, require an angle
	public ImageResizer(double scaler){__setScaler(scaler);}
	public ImageResizer(){this(DEFAULT_SCALER);}
	
	private boolean __setScaler(double scaler){
		if (scaler <= 0){
			mScaler = DEFAULT_SCALER;
			return false;
		}
		
		mScaler = scaler;
		return true;
	}
	
	
	
	
	//accessor
	public double getScaler(){return mScaler;}
	
	
	
	
	
	public ImageIcon resizeImage(ImageIcon aicon){
		return __resizeImage(aicon, mScaler);
	}
	private ImageIcon __resizeImage(ImageIcon aicon, double scaler){
		
		int oldW = aicon.getIconWidth();
		int oldH = aicon.getIconHeight();
		int type = BufferedImage.TYPE_4BYTE_ABGR;
		
		BufferedImage oldImg = new BufferedImage(oldW, oldH, type);
		Graphics2D grOldImg = oldImg.createGraphics();
		grOldImg.drawImage(aicon.getImage(), 0, 0, null);
		grOldImg.dispose();
		
		
		int newH = (int) (oldH * scaler);
		int newW = (int) (oldW * scaler);
		int scaleType = Image.SCALE_SMOOTH;
		
		if (newH <= 0) newH = 1;
		if (newW <= 0) newH = 1;
		
		Image newImg = oldImg.getScaledInstance(newW, newH, scaleType);
		return new ImageIcon(newImg);
	}
	
	
	public static void main(String[] args) {
		
		final double DEMO_SCALER_1 = .8;
		final double DEMO_SCALER_2 = 3.8;
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 600, 300);
		JPanel contentPane = new JPanel();
		frame.setContentPane(contentPane);
		
		ImageIcon icon = new ImageIcon(ImageResizer.class.getResource("/res/img/tiles/23r.png"));
		JLabel label = new JLabel(icon);
		
		ImageResizer resizer1 = new ImageResizer(DEMO_SCALER_1);
		JLabel addLabel1 = new JLabel(resizer1.resizeImage(icon));
		
		ImageResizer resizer2 = new ImageResizer(DEMO_SCALER_2);
		JLabel addLabel2 = new JLabel(resizer2.resizeImage(icon));
		
		contentPane.add(label);
		contentPane.add(addLabel1);
		contentPane.add(addLabel2);
		
		frame.setVisible(true);
	}

}
