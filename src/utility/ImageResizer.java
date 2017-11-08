package utility;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

//for demo
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



//used to resize an imageicon to a given scaled value
public class ImageResizer {
	private static final double DEFAULT_SCALER = 1;
	
	
	private double scaler;
	
	
	//constructors, require a scale value
	public ImageResizer(double deisredScaler){
		setScaler(deisredScaler);
	}
	public ImageResizer(){this(DEFAULT_SCALER);}
	
	private void setScaler(double deisredScaler){
		scaler = deisredScaler;
		if (deisredScaler <= 0) scaler = DEFAULT_SCALER;
	}
	
	
	public double getScaler(){return scaler;}
	
	
	public ImageIcon resizeImage(ImageIcon aicon){
		return resizeImage(aicon, scaler);
	}
	private ImageIcon resizeImage(ImageIcon aicon, double scale){
		
		int oldW = aicon.getIconWidth();
		int oldH = aicon.getIconHeight();
		int type = BufferedImage.TYPE_4BYTE_ABGR;
		
		BufferedImage oldImg = new BufferedImage(oldW, oldH, type);
		Graphics2D grOldImg = oldImg.createGraphics();
		grOldImg.drawImage(aicon.getImage(), 0, 0, null);
		grOldImg.dispose();
		
		
		int newH = (int) (oldH * scale);
		int newW = (int) (oldW * scale);
		int scaleType = Image.SCALE_SMOOTH;
		
		if (newH <= 0) newH = 1;
		if (newW <= 0) newH = 1;
		
		Image newImg = oldImg.getScaledInstance(newW, newH, scaleType);
		return new ImageIcon(newImg);
	}
	
	
	
	
	
	
	
	
	//TODO demo methods
	public static void main(String[] args) {
		
//		final double DEMO_SCALER_1 = .270588;
		final double DEMO_SCALER_1 = .54117647;
		final double DEMO_SCALER_2 = 2.8;
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 600, 600);
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
		
		contentPane.add(wallz(.54117647));
		contentPane.add(wallz(.270588));
		contentPane.add(wallsmallz(.35));
		contentPane.add(wallsmallz(.3));
		
		frame.setVisible(true);
	}
	
	public static JPanel wallsmallz(double scale){
		
		JPanel panel = new JPanel();
		ImageResizer resizer1 = new ImageResizer(scale);
		
		panel.setLayout(new GridLayout(4,34,0,0));
		
		
		for (int i = 1; i <= 69; i++){
//			for (int j = 0; j < 4; j++)
			panel.add(new JLabel(resizer1.resizeImage(new ImageIcon(ImageResizer.class.getResource("/res/img/tiles/" + (i%35) + ".png")))));
//			panel.add(new JLabel(resizer1.resizeImage(new ImageIcon(ImageResizer.class.getResource("/res/img/tiles/0.png")))));
		}
		
		return panel;
	}
	
	
	public static JPanel wallz(double scale){
		
		JPanel panel = new JPanel();
		ImageResizer resizer1 = new ImageResizer(scale);
		
		panel.setLayout(new GridLayout(4,34,0,0));
		
		
		for (int i = 1; i <= 34; i++){
			for (int j = 0; j < 4; j++)
			panel.add(new JLabel(resizer1.resizeImage(new ImageIcon(ImageResizer.class.getResource("/res/img/tiles/" + i + ".png")))));
		}
		
		return panel;
	}
}
