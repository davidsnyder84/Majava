package majava.graphics;


import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class Face extends JFrame {
	
	public static final int TILE_WIDTH = 36;
	public static final int TILE_HEIGHT = 46;
	
	public static final double SCALER_POND = .7; 
	
	
	
	private JPanel contentPane;
	private JLabel lblTile1;
	private JLabel lblTile2;
	private JLabel lblTile3;
	private JLabel lblTile4;
	private JLabel lblTile5;
	private JLabel lblTileSizing;
	private JLabel label_1;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Face frame = new Face();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public Face() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFaggotsAhoy = new JLabel("Faggots ahoy");
		lblFaggotsAhoy.setBounds(26, 26, 548, 30);
		contentPane.add(lblFaggotsAhoy);
		
		
		
		JLabel lblGreg = new JLabel("");
		lblGreg.setBounds(202, 67, 185, 137);
		contentPane.add(lblGreg);
//		lblGreg.setIcon(new ImageIcon("res/joe/grades.jpg"));
		lblGreg.setIcon(new ImageIcon(getClass().getResource("/res/" + "joe/g" + 1 + "reg.png")));
//		lblFaggotsAhoy.setText((getClass().getResource("/res/joe/grades.jpg")).toString());
//		System.out.println(
//				(getClass().getResource("/res/joe/grades.jpg")).toString()
//		);
		
		
		
		/*
		lblTile1 = new JLabel("");
//		lblTile1.setIcon(new ImageIcon(Face.class.getResource("/tiles/1.gif")));
		lblTile1.setIcon(new ImageIcon(Face.class.getResource("/tiles/1.png")));
		lblTile1.setBounds(20, 90, 36, 46);
		contentPane.add(lblTile1);
		
		lblTile2 = new JLabel("");
//		lblTile2.setIcon(new ImageIcon(Face.class.getResource("/tiles/2.gif")));
		lblTile2.setIcon(new ImageIcon(Face.class.getResource("/tiles/2.png")));
		lblTile2.setBounds(66, 90, 36, 46);
		contentPane.add(lblTile2);
		
		lblTile3 = new JLabel("");
//		lblTile3.setIcon(new ImageIcon(Face.class.getResource("/tiles/3.gif")));
		lblTile3.setIcon(new ImageIcon(Face.class.getResource("/tiles/3.png")));
		lblTile3.setBounds(112, 90, 36, 46);
		contentPane.add(lblTile3);
		
		lblTile4 = new JLabel("");
//		lblTile4.setIcon(new ImageIcon(Face.class.getResource("/tiles/4.gif")));
		lblTile4.setIcon(new ImageIcon(Face.class.getResource("/tiles/4.png")));
		lblTile4.setBounds(158, 90, 36, 46);
		contentPane.add(lblTile4);
		
		lblTile5 = new JLabel("");
//		lblTile5.setIcon(new ImageIcon(Face.class.getResource("/tiles/5.gif")));
		lblTile5.setIcon(new ImageIcon(Face.class.getResource("/tiles/5.png")));
		lblTile5.setBounds(208, 90, 36, 46);
		contentPane.add(lblTile5);
		
		
		
		JButton btnChangeTile = new JButton("Change tile 5");
		btnChangeTile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Ha you're gay");
//				lblTile5.setIcon(new ImageIcon(Face.class.getResource("/tiles/13.gif")));
				lblTile5.setIcon(new ImageIcon(Face.class.getResource("/tiles/13.png")));
				JLabel sub = lblTile5;
//				sub.setIcon(null);
			}
		});
		btnChangeTile.setBounds(78, 190, 150, 23);
		contentPane.add(btnChangeTile);
		
		
		
		
		
//		
//		BufferedImage sizingChun = null;
//        try {sizingChun = ImageIO.read(new File("img\\tiles\\34.gif"));}
//        catch(IOException e){
//        	//if no image file exists for the card, try to open unavailable.jpg
//        	try{sizingChun = ImageIO.read(new File("pics\\unavailable.jpg"));}
//        	catch(IOException e2){
//        		//exit program if unavailable.jpg not found
//                System.out.println("Problem opening image! " + e.getMessage());
//                System.exit(0);        		
//        	}
//        }
//        ImageIcon chunIcon = new ImageIcon(sizingChun.getScaledInstance((int) (TILE_WIDTH*SCALER_POND), (int) (TILE_HEIGHT*SCALER_POND), Image.SCALE_DEFAULT));
		
		
		
		
		
        
        
        
        
        
        
		lblTileSizing = new JLabel("");
//		lblTileSizing.setIcon(new ImageIcon(Face.class.getResource("/tiles/34.gif")));
//		lblTileSizing.setIcon(chunIcon);
		lblTileSizing.setBounds(405, 62, 36, 46);
		contentPane.add(lblTileSizing);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\Gay\\img\\transN.gif"));
		label.setBounds(303, 194, 30, 30);
		contentPane.add(label);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\Gay\\img\\transN23orig.gif"));
		label_2.setBounds(303, 164, 23, 23);
		contentPane.add(label_2);
		
		label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\Gay\\img\\transNorth.gif"));
		label_1.setBounds(338, 193, 31, 31);
		contentPane.add(label_1);
		
		label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\Gay\\img\\transNorth.gif"));
		label_3.setBounds(303, 122, 31, 31);
		contentPane.add(label_3);
		
		label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\Gay\\img\\transEast.gif"));
		label_4.setBounds(338, 122, 31, 31);
		contentPane.add(label_4);
		
		label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\Gay\\img\\transSouth.gif"));
		label_5.setBounds(374, 122, 31, 31);
		contentPane.add(label_5);
		
		label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\Gay\\img\\transWest.gif"));
		label_6.setBounds(410, 122, 31, 31);
		contentPane.add(label_6);
		*/
	}
}
