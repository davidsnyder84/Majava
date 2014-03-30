package graphics;

import java.awt.Color;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class WorldViewer extends JFrame {
	
	
	public static final int WINDOW_WIDTH = 1150;
	public static final int WINDOW_HEIGHT = 850;
	
	public static final int WINDOW_TOP_BORDER_SIZE = 30;
	public static final int WINDOW_SIDE_BORDER_SIZE = 8;
	public static final int WINDOW_BOTTOM_BORDER_SIZE = 8;
	
	
	public static final Color COLOR_TABLE = new Color(0, 140, 0, 100);
	public static final Color COLOR_SIDEBAR = new Color(0, 255, 0, 100);
	public static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);
	
	
	

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorldViewer frame = new WorldViewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//
	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public WorldViewer() throws IOException {
		
		
		final int WINDOW_BOUND_WIDTH = WINDOW_WIDTH + 2*WINDOW_SIDE_BORDER_SIZE;
		final int WINDOW_BOUND_HEIGHT = WINDOW_HEIGHT + WINDOW_TOP_BORDER_SIZE + WINDOW_BOTTOM_BORDER_SIZE;
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\transE.gif"));
		setTitle("The Beaver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, WINDOW_BOUND_WIDTH, WINDOW_BOUND_HEIGHT);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JPanel panelTable = new JPanel();
		panelTable.setBounds(0, 0, 874, 850);
		panelTable.setBackground(COLOR_TABLE);
		contentPane.add(panelTable);
		panelTable.setLayout(null);
		
		
		
		
		
//		ArrayList<JLabel> lblListH1Tiles = new ArrayList<JLabel>();
//		ArrayList<JLabel> labelList = new ArrayList<JLabel>();
//		JLabel[] labelArray = new JLabel[14];
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		ImageIcon pImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\22.gif");
		ImageIcon p2Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat2\\small\\22.gif");
		ImageIcon p3Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat3\\small\\25.gif");
		ImageIcon p4Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat4\\small\\21.gif");
		
		
		ImageIcon wImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\tileback.gif");
		ImageIcon w2Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat2\\small\\tileback.gif");
		ImageIcon w3Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat3\\small\\tileback.gif");
		ImageIcon w4Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat4\\small\\tileback.gif");
		
		
		
		ImageIcon hImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\1.gif");
		ImageIcon h2Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat2\\1.gif");
		ImageIcon h3Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat3\\1.gif");
		ImageIcon h4Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat4\\1.gif");
		
		

		ImageIcon meldImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\34.gif");
		ImageIcon meld2Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat2\\small\\34.gif");
		ImageIcon meld3Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat3\\small\\34.gif");
		ImageIcon meld4Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat4\\small\\34.gif");
		
		
		
//		p2Img = p3Img = p4Img = null;
//		pImg = p2Img = p3Img = p4Img = null;
//		wImg = w2Img = w3Img = w4Img = null;
//		h2Img = h3Img = h4Img = null;
//		hImg = h2Img = h3Img = h4Img = null;
//		meldImg = meld2Img = meld3Img = meld4Img = null;
		
		
		
		
		
//		BufferedImage picture = null;
//		BufferedImage picture1 = ImageIO.read(new File("img\\tiles\\seat1\\1.gif"));
//		BufferedImage picture2 = ImageIO.read(new File("img\\tiles\\seat2\\1.gif"));
//		BufferedImage picture3 = ImageIO.read(new File("img\\tiles\\seat3\\1.gif"));
//		BufferedImage picture4 = ImageIO.read(new File("img\\tiles\\seat4\\1.gif"));
		
		
		
//        hImg = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat1\\1.gif")));
//        h2Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat2\\1.gif")));
//        h3Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat3\\1.gif")));
//        h4Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat4\\1.gif")));
//        
//        pImg = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat1\\small\\22.gif")));
//		p2Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat2\\small\\22.gif")));
//		p3Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat3\\small\\25.gif")));
//		p4Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat4\\small\\21.gif")));
//		
//		wImg = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat1\\small\\tileback.gif")));
//		w2Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat2\\small\\tileback.gif")));
//		w3Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat3\\small\\tileback.gif")));
//		w4Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat4\\small\\tileback.gif")));
//		
//		meldImg = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat1\\small\\34.gif")));
//		meld2Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat2\\small\\34.gif")));
//		meld3Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat3\\small\\34.gif")));
//		meld4Img = new ImageIcon(ImageIO.read(new File("img\\tiles\\seat4\\small\\34.gif")));
		
		
		
		
//		hImg = new ImageIcon("img\\tiles\\seat1\\1.gif");
//		h2Img = new ImageIcon("img\\tiles\\seat2\\1.gif");
//		h3Img = new ImageIcon("img\\tiles\\seat3\\1.gif");
//		h4Img = new ImageIcon("img\\tiles\\seat4\\1.gif");
//			
//		pImg = new ImageIcon("img\\tiles\\seat1\\small\\21.gif");
//		p2Img = new ImageIcon("img\\tiles\\seat2\\small\\21.gif");
//		p3Img = new ImageIcon("img\\tiles\\seat3\\small\\21.gif");
//		p4Img = new ImageIcon("img\\tiles\\seat4\\small\\21.gif");
//			
//		wImg = new ImageIcon("img\\tiles\\seat1\\small\\tileback.gif");
//		w2Img = new ImageIcon("img\\tiles\\seat2\\small\\tileback.gif");
//		w3Img = new ImageIcon("img\\tiles\\seat3\\small\\tileback.gif");
//		w4Img = new ImageIcon("img\\tiles\\seat4\\small\\tileback.gif");
//			
//		meldImg = new ImageIcon("img\\tiles\\seat1\\small\\34.gif");
//		meld2Img = new ImageIcon("img\\tiles\\seat2\\small\\34.gif");
//		meld3Img = new ImageIcon("img\\tiles\\seat3\\small\\34.gif");
//		meld4Img = new ImageIcon("img\\tiles\\seat4\\small\\34.gif");
		
		
		
		
		
		
//		hImg = new ImageIcon("img\\tiles\\seat1\\34.png");
//		h2Img = new ImageIcon("img\\tiles\\seat2\\34.png");
//		h3Img = new ImageIcon("img\\tiles\\seat3\\34.png");
//		h4Img = new ImageIcon("img\\tiles\\seat4\\34.png");
//			
//		pImg = new ImageIcon("img\\tiles\\seat1\\small\\34.png");
//		p2Img = new ImageIcon("img\\tiles\\seat2\\small\\34.png");
//		p3Img = new ImageIcon("img\\tiles\\seat3\\small\\34.png");
//		p4Img = new ImageIcon("img\\tiles\\seat4\\small\\34.png");
//			
//		wImg = new ImageIcon("img\\tiles\\seat1\\small\\34.png");
//		w2Img = new ImageIcon("img\\tiles\\seat2\\small\\34.png");
//		w3Img = new ImageIcon("img\\tiles\\seat3\\small\\34.png");
//		w4Img = new ImageIcon("img\\tiles\\seat4\\small\\34.png");
//			
//		meldImg = new ImageIcon("img\\tiles\\seat1\\small\\34.png");
//		meld2Img = new ImageIcon("img\\tiles\\seat2\\small\\34.png");
//		meld3Img = new ImageIcon("img\\tiles\\seat3\\small\\34.png");
//		meld4Img = new ImageIcon("img\\tiles\\seat4\\small\\34.png");
		
		
//		hImg = new ImageIcon("img\\tiles\\seat1\\34.gif");
//		h2Img = new ImageIcon("img\\tiles\\seat2\\34.gif");
//		h3Img = new ImageIcon("img\\tiles\\seat3\\34.gif");
//		h4Img = new ImageIcon("img\\tiles\\seat4\\34.gif");
//			
//		pImg = new ImageIcon("img\\tiles\\seat1\\small\\34.gif");
//		p2Img = new ImageIcon("img\\tiles\\seat2\\small\\34.gif");
//		p3Img = new ImageIcon("img\\tiles\\seat3\\small\\34.gif");
//		p4Img = new ImageIcon("img\\tiles\\seat4\\small\\34.gif");
//			
//		wImg = new ImageIcon("img\\tiles\\seat1\\small\\34.gif");
//		w2Img = new ImageIcon("img\\tiles\\seat2\\small\\34.gif");
//		w3Img = new ImageIcon("img\\tiles\\seat3\\small\\34.gif");
//		w4Img = new ImageIcon("img\\tiles\\seat4\\small\\34.gif");
//			
//		meldImg = new ImageIcon("img\\tiles\\seat1\\small\\34.gif");
//		meld2Img = new ImageIcon("img\\tiles\\seat2\\small\\34.gif");
//		meld3Img = new ImageIcon("img\\tiles\\seat3\\small\\34.gif");
//		meld4Img = new ImageIcon("img\\tiles\\seat4\\small\\34.gif");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JPanel panelMidTable = new JPanel();
		panelMidTable.setBounds(137, 125, 600, 600);
		panelMidTable.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelMidTable);
		panelMidTable.setLayout(null);
		
		
		
		
		
		JPanel panelP1 = new JPanel();
		panelP1.setBounds(220, 379, 162, 140);
		panelP1.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP1);
		panelP1.setLayout(new GridLayout(4, 6, 0, 0));
		
		JLabel lblP1T1 = new JLabel("");
		panelP1.add(lblP1T1);
		lblP1T1.setIcon(pImg);
		
		JLabel lblP1T2 = new JLabel("");
		panelP1.add(lblP1T2);
		lblP1T2.setIcon(pImg);
		
		JLabel lblP1T3 = new JLabel("");
		panelP1.add(lblP1T3);
		lblP1T3.setIcon(pImg);
		
		JLabel lblP1T4 = new JLabel("");
		panelP1.add(lblP1T4);
		lblP1T4.setIcon(pImg);
		
		JLabel lblP1T5 = new JLabel("");
		panelP1.add(lblP1T5);
		lblP1T5.setIcon(pImg);
		
		JLabel lblP1T6 = new JLabel("");
		panelP1.add(lblP1T6);
		lblP1T6.setIcon(pImg);
		
		JLabel lblP1T7 = new JLabel("");
		panelP1.add(lblP1T7);
		lblP1T7.setIcon(pImg);
		
		JLabel lblP1T8 = new JLabel("");
		panelP1.add(lblP1T8);
		lblP1T8.setIcon(pImg);
		
		JLabel lblP1T9 = new JLabel("");
		panelP1.add(lblP1T9);
		lblP1T9.setIcon(pImg);
		
		JLabel lblP1T10 = new JLabel("");
		panelP1.add(lblP1T10);
		lblP1T10.setIcon(pImg);
		
		JLabel lblP1T11 = new JLabel("");
		panelP1.add(lblP1T11);
		lblP1T11.setIcon(pImg);
		
		JLabel lblP1T12 = new JLabel("");
		panelP1.add(lblP1T12);
		lblP1T12.setIcon(pImg);
		
		JLabel lblP1T13 = new JLabel("");
		panelP1.add(lblP1T13);
		lblP1T13.setIcon(pImg);
		
		JLabel lblP1T14 = new JLabel("");
		panelP1.add(lblP1T14);
		lblP1T14.setIcon(pImg);
		
		JLabel lblP1T15 = new JLabel("");
		panelP1.add(lblP1T15);
		lblP1T15.setIcon(pImg);
		
		JLabel lblP1T16 = new JLabel("");
		panelP1.add(lblP1T16);
		lblP1T16.setIcon(pImg);
		
		JLabel lblP1T17 = new JLabel("");
		panelP1.add(lblP1T17);
		lblP1T17.setIcon(pImg);
		
		JLabel lblP1T18 = new JLabel("");
		panelP1.add(lblP1T18);
		lblP1T18.setIcon(pImg);
		
		JLabel lblP1T19 = new JLabel("");
		panelP1.add(lblP1T19);
		lblP1T19.setIcon(pImg);
		
		JLabel lblP1T20 = new JLabel("");
		panelP1.add(lblP1T20);
		lblP1T20.setIcon(pImg);
		
		JLabel lblP1T21 = new JLabel("");
		panelP1.add(lblP1T21);
		lblP1T21.setIcon(pImg);
		
		JLabel lblP1T22 = new JLabel("");
		panelP1.add(lblP1T22);
		lblP1T22.setIcon(pImg);
		
		JLabel lblP1T23 = new JLabel("");
		panelP1.add(lblP1T23);
		lblP1T23.setIcon(pImg);
		
		JLabel lblP1T24 = new JLabel("");
		panelP1.add(lblP1T24);
		lblP1T24.setIcon(pImg);
		
		
		
		
		
		
		JPanel panelP2 = new JPanel();
		panelP2.setBounds(384, 218, 140, 162);
		panelP2.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP2);
		panelP2.setLayout(new GridLayout(6, 4, 0, 0));
		
		JLabel lblP2T6 = new JLabel("");
		panelP2.add(lblP2T6);
		lblP2T6.setIcon(p2Img);
		
		JLabel lblP2T12 = new JLabel("");
		panelP2.add(lblP2T12);
		lblP2T12.setIcon(p2Img);
		
		JLabel lblP2T18 = new JLabel("");
		panelP2.add(lblP2T18);
		lblP2T18.setIcon(p2Img);
		
		JLabel lblP2T24 = new JLabel("");
		panelP2.add(lblP2T24);
		lblP2T24.setIcon(p2Img);
		
		JLabel lblP2T5 = new JLabel("");
		panelP2.add(lblP2T5);
		lblP2T5.setIcon(p2Img);
		
		JLabel lblP2T11 = new JLabel("");
		panelP2.add(lblP2T11);
		lblP2T11.setIcon(p2Img);
		
		JLabel lblP2T17 = new JLabel("");
		panelP2.add(lblP2T17);
		lblP2T17.setIcon(p2Img);
		
		JLabel lblP2T23 = new JLabel("");
		panelP2.add(lblP2T23);
		lblP2T23.setIcon(p2Img);
		
		JLabel lblP2T4 = new JLabel("");
		panelP2.add(lblP2T4);
		lblP2T4.setIcon(p2Img);
		
		JLabel lblP2T10 = new JLabel("");
		panelP2.add(lblP2T10);
		lblP2T10.setIcon(p2Img);
		
		JLabel lblP2T16 = new JLabel("");
		panelP2.add(lblP2T16);
		lblP2T16.setIcon(p2Img);
		
		JLabel lblP2T22 = new JLabel("");
		panelP2.add(lblP2T22);
		lblP2T22.setIcon(p2Img);
		
		JLabel lblP2T3 = new JLabel("");
		panelP2.add(lblP2T3);
		lblP2T3.setIcon(p2Img);
		
		JLabel lblP2T9 = new JLabel("");
		panelP2.add(lblP2T9);
		lblP2T9.setIcon(p2Img);
		
		JLabel lblP2T15 = new JLabel("");
		panelP2.add(lblP2T15);
		lblP2T15.setIcon(p2Img);
		
		JLabel lblP2T21 = new JLabel("");
		panelP2.add(lblP2T21);
		lblP2T21.setIcon(p2Img);
		
		JLabel lblP2T2 = new JLabel("");
		panelP2.add(lblP2T2);
		lblP2T2.setIcon(p2Img);
		
		JLabel lblP2T8 = new JLabel("");
		panelP2.add(lblP2T8);
		lblP2T8.setIcon(p2Img);
		
		JLabel lblP2T14 = new JLabel("");
		panelP2.add(lblP2T14);
		lblP2T14.setIcon(p2Img);
		
		JLabel lblP2T20 = new JLabel("");
		panelP2.add(lblP2T20);
		lblP2T20.setIcon(p2Img);
		
		JLabel lblP2T1 = new JLabel("");
		panelP2.add(lblP2T1);
		lblP2T1.setIcon(p2Img);
		
		JLabel lblP2T7 = new JLabel("");
		panelP2.add(lblP2T7);
		lblP2T7.setIcon(p2Img);
		
		JLabel lblP2T13 = new JLabel("");
		panelP2.add(lblP2T13);
		lblP2T13.setIcon(p2Img);
		
		JLabel lblP2T19 = new JLabel("");
		panelP2.add(lblP2T19);
		lblP2T19.setIcon(p2Img);
		
		
		
		
		
		
		JPanel panelP3 = new JPanel();
		panelP3.setBounds(220, 79, 162, 140);
		panelP3.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP3);
		panelP3.setLayout(new GridLayout(4, 6, 0, 0));
		
		JLabel lblP3T24 = new JLabel("");
		panelP3.add(lblP3T24);
		lblP3T24.setIcon(p3Img);
		
		JLabel lblP3T23 = new JLabel("");
		panelP3.add(lblP3T23);
		lblP3T23.setIcon(p3Img);
		
		JLabel lblP3T22 = new JLabel("");
		panelP3.add(lblP3T22);
		lblP3T22.setIcon(p3Img);
		
		JLabel lblP3T21 = new JLabel("");
		panelP3.add(lblP3T21);
		lblP3T21.setIcon(p3Img);
		
		JLabel lblP3T20 = new JLabel("");
		panelP3.add(lblP3T20);
		lblP3T20.setIcon(p3Img);
		
		JLabel lblP3T19 = new JLabel("");
		panelP3.add(lblP3T19);
		lblP3T19.setIcon(p3Img);
		
		JLabel lblP3T18 = new JLabel("");
		panelP3.add(lblP3T18);
		lblP3T18.setIcon(p3Img);
		
		JLabel lblP3T17 = new JLabel("");
		panelP3.add(lblP3T17);
		lblP3T17.setIcon(p3Img);
		
		JLabel lblP3T16 = new JLabel("");
		panelP3.add(lblP3T16);
		lblP3T16.setIcon(p3Img);
		
		JLabel lblP3T15 = new JLabel("");
		panelP3.add(lblP3T15);
		lblP3T15.setIcon(p3Img);
		
		JLabel lblP3T14 = new JLabel("");
		panelP3.add(lblP3T14);
		lblP3T14.setIcon(p3Img);
		
		JLabel lblP3T13 = new JLabel("");
		panelP3.add(lblP3T13);
		lblP3T13.setIcon(p3Img);
		
		JLabel lblP3T12 = new JLabel("");
		panelP3.add(lblP3T12);
		lblP3T12.setIcon(p3Img);
		
		JLabel lblP3T11 = new JLabel("");
		panelP3.add(lblP3T11);
		lblP3T11.setIcon(p3Img);
		
		JLabel lblP3T10 = new JLabel("");
		panelP3.add(lblP3T10);
		lblP3T10.setIcon(p3Img);
		
		JLabel lblP3T9 = new JLabel("");
		panelP3.add(lblP3T9);
		lblP3T9.setIcon(p3Img);
		
		JLabel lblP3T8 = new JLabel("");
		panelP3.add(lblP3T8);
		lblP3T8.setIcon(p3Img);
		
		JLabel lblP3T7 = new JLabel("");
		panelP3.add(lblP3T7);
		lblP3T7.setIcon(p3Img);
		
		JLabel lblP3T6 = new JLabel("");
		panelP3.add(lblP3T6);
		lblP3T6.setIcon(p3Img);
		
		JLabel lblP3T5 = new JLabel("");
		panelP3.add(lblP3T5);
		lblP3T5.setIcon(p3Img);
		
		JLabel lblP3T4 = new JLabel("");
		panelP3.add(lblP3T4);
		lblP3T4.setIcon(p3Img);
		
		JLabel lblP3T3 = new JLabel("");
		panelP3.add(lblP3T3);
		lblP3T3.setIcon(p3Img);
		
		JLabel lblP3T2 = new JLabel("");
		panelP3.add(lblP3T2);
		lblP3T2.setIcon(p3Img);
		
		JLabel lblP3T1 = new JLabel("");
		panelP3.add(lblP3T1);
		lblP3T1.setIcon(p3Img);
		
		
		
		
		
		
		JPanel panelP4 = new JPanel();
		panelP4.setBounds(76, 218, 140, 162);
		panelP4.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP4);
		panelP4.setLayout(new GridLayout(6, 4, 0, 0));
		
		JLabel lblP4T19 = new JLabel("");
		panelP4.add(lblP4T19);
		lblP4T19.setIcon(p4Img);
		
		JLabel lblP4T13 = new JLabel("");
		panelP4.add(lblP4T13);
		lblP4T13.setIcon(p4Img);
		
		JLabel lblP4T7 = new JLabel("");
		panelP4.add(lblP4T7);
		lblP4T7.setIcon(p4Img);
		
		JLabel lblP4T1 = new JLabel("");
		panelP4.add(lblP4T1);
		lblP4T1.setIcon(p4Img);
		
		JLabel lblP4T20 = new JLabel("");
		panelP4.add(lblP4T20);
		lblP4T20.setIcon(p4Img);
		
		JLabel lblP4T14 = new JLabel("");
		panelP4.add(lblP4T14);
		lblP4T14.setIcon(p4Img);
		
		JLabel lblP4T8 = new JLabel("");
		panelP4.add(lblP4T8);
		lblP4T8.setIcon(p4Img);
		
		JLabel lblP4T2 = new JLabel("");
		panelP4.add(lblP4T2);
		lblP4T2.setIcon(p4Img);
		
		JLabel lblP4T21 = new JLabel("");
		panelP4.add(lblP4T21);
		lblP4T21.setIcon(p4Img);
		
		JLabel lblP4T15 = new JLabel("");
		panelP4.add(lblP4T15);
		lblP4T15.setIcon(p4Img);
		
		JLabel lblP4T9 = new JLabel("");
		panelP4.add(lblP4T9);
		lblP4T9.setIcon(p4Img);
		
		JLabel lblP4T3 = new JLabel("");
		panelP4.add(lblP4T3);
		lblP4T3.setIcon(p4Img);
		
		JLabel lblP4T22 = new JLabel("");
		panelP4.add(lblP4T22);
		lblP4T22.setIcon(p4Img);
		
		JLabel lblP4T16 = new JLabel("");
		panelP4.add(lblP4T16);
		lblP4T16.setIcon(p4Img);
		
		JLabel lblP4T10 = new JLabel("");
		panelP4.add(lblP4T10);
		lblP4T10.setIcon(p4Img);
		
		JLabel lblP4T4 = new JLabel("");
		panelP4.add(lblP4T4);
		lblP4T4.setIcon(p4Img);
		
		JLabel lblP4T23 = new JLabel("");
		panelP4.add(lblP4T23);
		lblP4T23.setIcon(p4Img);
		
		JLabel lblP4T17 = new JLabel("");
		panelP4.add(lblP4T17);
		lblP4T17.setIcon(p4Img);
		
		JLabel lblP4T11 = new JLabel("");
		panelP4.add(lblP4T11);
		lblP4T11.setIcon(p4Img);
		
		JLabel lblP4T5 = new JLabel("");
		panelP4.add(lblP4T5);
		lblP4T5.setIcon(p4Img);
		
		JLabel lblP4T24 = new JLabel("");
		panelP4.add(lblP4T24);
		lblP4T24.setIcon(p4Img);
		
		JLabel lblP4T18 = new JLabel("");
		panelP4.add(lblP4T18);
		lblP4T18.setIcon(p4Img);
		
		JLabel lblP4T12 = new JLabel("");
		panelP4.add(lblP4T12);
		lblP4T12.setIcon(p4Img);
		
		JLabel lblP4T6 = new JLabel("");
		panelP4.add(lblP4T6);
		lblP4T6.setIcon(p4Img);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JPanel panelW1 = new JPanel();
		panelW1.setBounds(71, 528, 459, 70);
		panelW1.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelW1);
		panelW1.setLayout(new GridLayout(2, 17, 0, 0));
		
		JLabel lblW1T33 = new JLabel("");
		panelW1.add(lblW1T33);
		lblW1T33.setIcon(wImg);
		
		JLabel lblW1T31 = new JLabel("");
		panelW1.add(lblW1T31);
		lblW1T31.setIcon(wImg);
		
		JLabel lblW1T29 = new JLabel("");
		panelW1.add(lblW1T29);
		lblW1T29.setIcon(wImg);
		
		JLabel lblW1T27 = new JLabel("");
		panelW1.add(lblW1T27);
		lblW1T27.setIcon(wImg);
		
		JLabel lblW1T25 = new JLabel("");
		panelW1.add(lblW1T25);
		lblW1T25.setIcon(wImg);
		
		JLabel lblW1T23 = new JLabel("");
		panelW1.add(lblW1T23);
		lblW1T23.setIcon(wImg);
		
		JLabel lblW1T21 = new JLabel("");
		panelW1.add(lblW1T21);
		lblW1T21.setIcon(wImg);
		
		JLabel lblW1T19 = new JLabel("");
		panelW1.add(lblW1T19);
		lblW1T19.setIcon(wImg);
		
		JLabel lblW1T17 = new JLabel("");
		panelW1.add(lblW1T17);
		lblW1T17.setIcon(wImg);
		
		JLabel lblW1T15 = new JLabel("");
		panelW1.add(lblW1T15);
		lblW1T15.setIcon(wImg);
		
		JLabel lblW1T13 = new JLabel("");
		panelW1.add(lblW1T13);
		lblW1T13.setIcon(wImg);
		
		JLabel lblW1T11 = new JLabel("");
		panelW1.add(lblW1T11);
		lblW1T11.setIcon(wImg);
		
		JLabel lblW1T9 = new JLabel("");
		panelW1.add(lblW1T9);
		lblW1T9.setIcon(wImg);
		
		JLabel lblW1T7 = new JLabel("");
		panelW1.add(lblW1T7);
		lblW1T7.setIcon(wImg);
		
		JLabel lblW1T5 = new JLabel("");
		panelW1.add(lblW1T5);
		lblW1T5.setIcon(wImg);
		
		JLabel lblW1T3 = new JLabel("");
		panelW1.add(lblW1T3);
		lblW1T3.setIcon(wImg);
		
		JLabel lblW1T1 = new JLabel("");
		panelW1.add(lblW1T1);
		lblW1T1.setIcon(wImg);
		
		JLabel lblW1T34 = new JLabel("");
		panelW1.add(lblW1T34);
		lblW1T34.setIcon(wImg);
		
		JLabel lblW1T32 = new JLabel("");
		panelW1.add(lblW1T32);
		lblW1T32.setIcon(wImg);
		
		JLabel lblW1T30 = new JLabel("");
		panelW1.add(lblW1T30);
		lblW1T30.setIcon(wImg);
		
		JLabel lblW1T28 = new JLabel("");
		panelW1.add(lblW1T28);
		lblW1T28.setIcon(wImg);
		
		JLabel lblW1T26 = new JLabel("");
		panelW1.add(lblW1T26);
		lblW1T26.setIcon(wImg);
		
		JLabel lblW1T24 = new JLabel("");
		panelW1.add(lblW1T24);
		lblW1T24.setIcon(wImg);
		
		JLabel lblW1T22 = new JLabel("");
		panelW1.add(lblW1T22);
		lblW1T22.setIcon(wImg);
		
		JLabel lblW1T20 = new JLabel("");
		panelW1.add(lblW1T20);
		lblW1T20.setIcon(wImg);
		
		JLabel lblW1T18 = new JLabel("");
		panelW1.add(lblW1T18);
		lblW1T18.setIcon(wImg);
		
		JLabel lblW1T16 = new JLabel("");
		panelW1.add(lblW1T16);
		lblW1T16.setIcon(wImg);
		
		JLabel lblW1T14 = new JLabel("");
		panelW1.add(lblW1T14);
		lblW1T14.setIcon(wImg);
		
		JLabel lblW1T12 = new JLabel("");
		panelW1.add(lblW1T12);
		lblW1T12.setIcon(wImg);
		
		JLabel lblW1T10 = new JLabel("");
		panelW1.add(lblW1T10);
		lblW1T10.setIcon(wImg);
		
		JLabel lblW1T8 = new JLabel("");
		panelW1.add(lblW1T8);
		lblW1T8.setIcon(wImg);
		
		JLabel lblW1T6 = new JLabel("");
		panelW1.add(lblW1T6);
		lblW1T6.setIcon(wImg);
		
		JLabel lblW1T4 = new JLabel("");
		panelW1.add(lblW1T4);
		lblW1T4.setIcon(wImg);
		
		JLabel lblW1T2 = new JLabel("");
		panelW1.add(lblW1T2);
		lblW1T2.setIcon(wImg);
		
		
		
		
		
		JPanel panelW2 = new JPanel();
		panelW2.setBounds(529, 69, 70, 459);
		panelW2.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelW2);
		panelW2.setLayout(new GridLayout(17, 2, 0, 0));
		
		JLabel lblW2T1 = new JLabel("");
		panelW2.add(lblW2T1);
		lblW2T1.setIcon(w2Img);
		
		JLabel lblW2T2 = new JLabel("");
		panelW2.add(lblW2T2);
		lblW2T2.setIcon(w2Img);
		
		JLabel lblW2T3 = new JLabel("");
		panelW2.add(lblW2T3);
		lblW2T3.setIcon(w2Img);
		
		JLabel lblW2T4 = new JLabel("");
		panelW2.add(lblW2T4);
		lblW2T4.setIcon(w2Img);
		
		JLabel lblW2T5 = new JLabel("");
		panelW2.add(lblW2T5);
		lblW2T5.setIcon(w2Img);
		
		JLabel lblW2T6 = new JLabel("");
		panelW2.add(lblW2T6);
		lblW2T6.setIcon(w2Img);
		
		JLabel lblW2T7 = new JLabel("");
		panelW2.add(lblW2T7);
		lblW2T7.setIcon(w2Img);
		
		JLabel lblW2T8 = new JLabel("");
		panelW2.add(lblW2T8);
		lblW2T8.setIcon(w2Img);
		
		JLabel lblW2T9 = new JLabel("");
		panelW2.add(lblW2T9);
		lblW2T9.setIcon(w2Img);
		
		JLabel lblW2T10 = new JLabel("");
		panelW2.add(lblW2T10);
		lblW2T10.setIcon(w2Img);
		
		JLabel lblW2T11 = new JLabel("");
		panelW2.add(lblW2T11);
		lblW2T11.setIcon(w2Img);
		
		JLabel lblW2T12 = new JLabel("");
		panelW2.add(lblW2T12);
		lblW2T12.setIcon(w2Img);
		
		JLabel lblW2T13 = new JLabel("");
		panelW2.add(lblW2T13);
		lblW2T13.setIcon(w2Img);
		
		JLabel lblW2T14 = new JLabel("");
		panelW2.add(lblW2T14);
		lblW2T14.setIcon(w2Img);
		
		JLabel lblW2T15 = new JLabel("");
		panelW2.add(lblW2T15);
		lblW2T15.setIcon(w2Img);
		
		JLabel lblW2T16 = new JLabel("");
		panelW2.add(lblW2T16);
		lblW2T16.setIcon(w2Img);
		
		JLabel lblW2T17 = new JLabel("");
		panelW2.add(lblW2T17);
		lblW2T17.setIcon(w2Img);
		
		JLabel lblW2T18 = new JLabel("");
		panelW2.add(lblW2T18);
		lblW2T18.setIcon(w2Img);
		
		JLabel lblW2T19 = new JLabel("");
		panelW2.add(lblW2T19);
		lblW2T19.setIcon(w2Img);
		
		JLabel lblW2T20 = new JLabel("");
		panelW2.add(lblW2T20);
		lblW2T20.setIcon(w2Img);
		
		JLabel lblW2T21 = new JLabel("");
		panelW2.add(lblW2T21);
		lblW2T21.setIcon(w2Img);
		
		JLabel lblW2T22 = new JLabel("");
		panelW2.add(lblW2T22);
		lblW2T22.setIcon(w2Img);
		
		JLabel lblW2T23 = new JLabel("");
		panelW2.add(lblW2T23);
		lblW2T23.setIcon(w2Img);
		
		JLabel lblW2T24 = new JLabel("");
		panelW2.add(lblW2T24);
		lblW2T24.setIcon(w2Img);
		
		JLabel lblW2T25 = new JLabel("");
		panelW2.add(lblW2T25);
		lblW2T25.setIcon(w2Img);
		
		JLabel lblW2T26 = new JLabel("");
		panelW2.add(lblW2T26);
		lblW2T26.setIcon(w2Img);
		
		JLabel lblW2T27 = new JLabel("");
		panelW2.add(lblW2T27);
		lblW2T27.setIcon(w2Img);
		
		JLabel lblW2T28 = new JLabel("");
		panelW2.add(lblW2T28);
		lblW2T28.setIcon(w2Img);
		
		JLabel lblW2T29 = new JLabel("");
		panelW2.add(lblW2T29);
		lblW2T29.setIcon(w2Img);
		
		JLabel lblW2T30 = new JLabel("");
		panelW2.add(lblW2T30);
		lblW2T30.setIcon(w2Img);
		
		JLabel lblW2T31 = new JLabel("");
		panelW2.add(lblW2T31);
		lblW2T31.setIcon(w2Img);
		
		JLabel lblW2T32 = new JLabel("");
		panelW2.add(lblW2T32);
		lblW2T32.setIcon(w2Img);
		
		JLabel lblW2T33 = new JLabel("");
		panelW2.add(lblW2T33);
		lblW2T33.setIcon(w2Img);
		
		JLabel lblW2T34 = new JLabel("");
		panelW2.add(lblW2T34);
		lblW2T34.setIcon(w2Img);
		
		
		
		
		
		
		JPanel panelW3 = new JPanel();
		panelW3.setBounds(70, 0, 459, 70);
		panelW3.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelW3);
		panelW3.setLayout(new GridLayout(2, 17, 0, 0));
		
		JLabel lblW3T2 = new JLabel("");
		panelW3.add(lblW3T2);
		lblW3T2.setIcon(w3Img);
		
		JLabel lblW3T4 = new JLabel("");
		panelW3.add(lblW3T4);
		lblW3T4.setIcon(w3Img);
		
		JLabel lblW3T6 = new JLabel("");
		panelW3.add(lblW3T6);
		lblW3T6.setIcon(w3Img);
		
		JLabel lblW3T8 = new JLabel("");
		panelW3.add(lblW3T8);
		lblW3T8.setIcon(w3Img);
		
		JLabel lblW3T10 = new JLabel("");
		panelW3.add(lblW3T10);
		lblW3T10.setIcon(w3Img);
		
		JLabel lblW3T12 = new JLabel("");
		panelW3.add(lblW3T12);
		lblW3T12.setIcon(w3Img);
		
		JLabel lblW3T14 = new JLabel("");
		panelW3.add(lblW3T14);
		lblW3T14.setIcon(w3Img);
		
		JLabel lblW3T16 = new JLabel("");
		panelW3.add(lblW3T16);
		lblW3T16.setIcon(w3Img);
		
		JLabel lblW3T18 = new JLabel("");
		panelW3.add(lblW3T18);
		lblW3T18.setIcon(w3Img);
		
		JLabel lblW3T20 = new JLabel("");
		panelW3.add(lblW3T20);
		lblW3T20.setIcon(w3Img);
		
		JLabel lblW3T22 = new JLabel("");
		panelW3.add(lblW3T22);
		lblW3T22.setIcon(w3Img);
		
		JLabel lblW3T24 = new JLabel("");
		panelW3.add(lblW3T24);
		lblW3T24.setIcon(w3Img);
		
		JLabel lblW3T26 = new JLabel("");
		panelW3.add(lblW3T26);
		lblW3T26.setIcon(w3Img);
		
		JLabel lblW3T28 = new JLabel("");
		panelW3.add(lblW3T28);
		lblW3T28.setIcon(w3Img);
		
		JLabel lblW3T30 = new JLabel("");
		panelW3.add(lblW3T30);
		lblW3T30.setIcon(w3Img);
		
		JLabel lblW3T32 = new JLabel("");
		panelW3.add(lblW3T32);
		lblW3T32.setIcon(w3Img);
		
		JLabel lblW3T34 = new JLabel("");
		panelW3.add(lblW3T34);
		lblW3T34.setIcon(w3Img);
		
		JLabel lblW3T1 = new JLabel("");
		panelW3.add(lblW3T1);
		lblW3T1.setIcon(w3Img);
		
		JLabel lblW3T3 = new JLabel("");
		panelW3.add(lblW3T3);
		lblW3T3.setIcon(w3Img);
		
		JLabel lblW3T5 = new JLabel("");
		panelW3.add(lblW3T5);
		lblW3T5.setIcon(w3Img);
		
		JLabel lblW3T7 = new JLabel("");
		panelW3.add(lblW3T7);
		lblW3T7.setIcon(w3Img);
		
		JLabel lblW3T9 = new JLabel("");
		panelW3.add(lblW3T9);
		lblW3T9.setIcon(w3Img);
		
		JLabel lblW3T11 = new JLabel("");
		panelW3.add(lblW3T11);
		lblW3T11.setIcon(w3Img);
		
		JLabel lblW3T13 = new JLabel("");
		panelW3.add(lblW3T13);
		lblW3T13.setIcon(w3Img);
		
		JLabel lblW3T15 = new JLabel("");
		panelW3.add(lblW3T15);
		lblW3T15.setIcon(w3Img);
		
		JLabel lblW3T17 = new JLabel("");
		panelW3.add(lblW3T17);
		lblW3T17.setIcon(w3Img);
		
		JLabel lblW3T19 = new JLabel("");
		panelW3.add(lblW3T19);
		lblW3T19.setIcon(w3Img);
		
		JLabel lblW3T21 = new JLabel("");
		panelW3.add(lblW3T21);
		lblW3T21.setIcon(w3Img);
		
		JLabel lblW3T23 = new JLabel("");
		panelW3.add(lblW3T23);
		lblW3T23.setIcon(w3Img);
		
		JLabel lblW3T25 = new JLabel("");
		panelW3.add(lblW3T25);
		lblW3T25.setIcon(w3Img);
		
		JLabel lblW3T27 = new JLabel("");
		panelW3.add(lblW3T27);
		lblW3T27.setIcon(w3Img);
		
		JLabel lblW3T29 = new JLabel("");
		panelW3.add(lblW3T29);
		lblW3T29.setIcon(w3Img);
		
		JLabel lblW3T31 = new JLabel("");
		panelW3.add(lblW3T31);
		lblW3T31.setIcon(w3Img);
		
		JLabel lblW3T33 = new JLabel("");
		panelW3.add(lblW3T33);
		lblW3T33.setIcon(w3Img);
		
		
		
		
		
		
		JPanel panelW4 = new JPanel();
		panelW4.setBounds(0, 69, 70, 459);
		panelW4.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelW4);
		panelW4.setLayout(new GridLayout(17, 2, 0, 0));
		
		JLabel lblW4T34 = new JLabel("");
		panelW4.add(lblW4T34);
		lblW4T34.setIcon(w4Img);
		
		JLabel lblW4T33 = new JLabel("");
		panelW4.add(lblW4T33);
		lblW4T33.setIcon(w4Img);
		
		JLabel lblW4T32 = new JLabel("");
		panelW4.add(lblW4T32);
		lblW4T32.setIcon(w4Img);
		
		JLabel lblW4T31 = new JLabel("");
		panelW4.add(lblW4T31);
		lblW4T31.setIcon(w4Img);
		
		JLabel lblW4T30 = new JLabel("");
		panelW4.add(lblW4T30);
		lblW4T30.setIcon(w4Img);
		
		JLabel lblW4T29 = new JLabel("");
		panelW4.add(lblW4T29);
		lblW4T29.setIcon(w4Img);
		
		JLabel lblW4T28 = new JLabel("");
		panelW4.add(lblW4T28);
		lblW4T28.setIcon(w4Img);
		
		JLabel lblW4T27 = new JLabel("");
		panelW4.add(lblW4T27);
		lblW4T27.setIcon(w4Img);
		
		JLabel lblW4T26 = new JLabel("");
		panelW4.add(lblW4T26);
		lblW4T26.setIcon(w4Img);
		
		JLabel lblW4T25 = new JLabel("");
		panelW4.add(lblW4T25);
		lblW4T25.setIcon(w4Img);
		
		JLabel lblW4T24 = new JLabel("");
		panelW4.add(lblW4T24);
		lblW4T24.setIcon(w4Img);
		
		JLabel lblW4T23 = new JLabel("");
		panelW4.add(lblW4T23);
		lblW4T23.setIcon(w4Img);
		
		JLabel lblW4T22 = new JLabel("");
		panelW4.add(lblW4T22);
		lblW4T22.setIcon(w4Img);
		
		JLabel lblW4T21 = new JLabel("");
		panelW4.add(lblW4T21);
		lblW4T21.setIcon(w4Img);
		
		JLabel lblW4T20 = new JLabel("");
		panelW4.add(lblW4T20);
		lblW4T20.setIcon(w4Img);
		
		JLabel lblW4T19 = new JLabel("");
		panelW4.add(lblW4T19);
		lblW4T19.setIcon(w4Img);
		
		JLabel lblW4T18 = new JLabel("");
		panelW4.add(lblW4T18);
		lblW4T18.setIcon(w4Img);
		
		JLabel lblW4T17 = new JLabel("");
		panelW4.add(lblW4T17);
		lblW4T17.setIcon(w4Img);
		
		JLabel lblW4T16 = new JLabel("");
		panelW4.add(lblW4T16);
		lblW4T16.setIcon(w4Img);
		
		JLabel lblW4T15 = new JLabel("");
		panelW4.add(lblW4T15);
		lblW4T15.setIcon(w4Img);
		
		JLabel lblW4T14 = new JLabel("");
		panelW4.add(lblW4T14);
		lblW4T14.setIcon(w4Img);
		
		JLabel lblW4T13 = new JLabel("");
		panelW4.add(lblW4T13);
		lblW4T13.setIcon(w4Img);
		
		JLabel lblW4T12 = new JLabel("");
		panelW4.add(lblW4T12);
		lblW4T12.setIcon(w4Img);
		
		JLabel lblW4T11 = new JLabel("");
		panelW4.add(lblW4T11);
		lblW4T11.setIcon(w4Img);
		
		JLabel lblW4T10 = new JLabel("");
		panelW4.add(lblW4T10);
		lblW4T10.setIcon(w4Img);
		
		JLabel lblW4T9 = new JLabel("");
		panelW4.add(lblW4T9);
		lblW4T9.setIcon(w4Img);
		
		JLabel lblW4T8 = new JLabel("");
		panelW4.add(lblW4T8);
		lblW4T8.setIcon(w4Img);
		
		JLabel lblW4T7 = new JLabel("");
		panelW4.add(lblW4T7);
		lblW4T7.setIcon(w4Img);
		
		JLabel lblW4T6 = new JLabel("");
		panelW4.add(lblW4T6);
		lblW4T6.setIcon(w4Img);
		
		JLabel lblW4T5 = new JLabel("");
		panelW4.add(lblW4T5);
		lblW4T5.setIcon(w4Img);
		
		JLabel lblW4T4 = new JLabel("");
		panelW4.add(lblW4T4);
		lblW4T4.setIcon(w4Img);
		
		JLabel lblW4T3 = new JLabel("");
		panelW4.add(lblW4T3);
		lblW4T3.setIcon(w4Img);
		
		JLabel lblW4T2 = new JLabel("");
		panelW4.add(lblW4T2);
		lblW4T2.setIcon(w4Img);
		
		JLabel lblW4T1 = new JLabel("");
		panelW4.add(lblW4T1);
		lblW4T1.setIcon(w4Img);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JPanel panelRoundInfo = new JPanel();
		panelRoundInfo.setBounds(217, 219, 166, 158);
		panelMidTable.add(panelRoundInfo);
		panelRoundInfo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelRoundInfo.setBackground(new Color(0,255,255,35));
		panelRoundInfo.setLayout(null);
		
		
		
		
		
		
		
		
		
		
		JPanel panelRInd = new JPanel();
		panelRInd.setBounds(54, 54, 58, 49);
		panelRoundInfo.add(panelRInd);
		panelRInd.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelRInd.setBackground(new Color(0,155,155,35));
		panelRInd.setLayout(null);
		
		JLabel lblRIndWind = new JLabel("");
		lblRIndWind.setBounds(3, 9, 31, 31);
		panelRInd.add(lblRIndWind);
		lblRIndWind.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\transE.gif"));
		
		JLabel lblRIndNum = new JLabel("4");
		lblRIndNum.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblRIndNum.setHorizontalAlignment(SwingConstants.LEFT);
		lblRIndNum.setVerticalAlignment(SwingConstants.TOP);
		lblRIndNum.setBounds(33, 6, 16, 37);
		panelRInd.add(lblRIndNum);
		
		
		
		
		
		
		JPanel panelInfoP1 = new JPanel();
		panelInfoP1.setBounds(56, 103, 54, 54);
		panelRoundInfo.add(panelInfoP1);
		panelInfoP1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP1.setBackground(new Color(0, 255, 255, 35));
		panelInfoP1.setLayout(null);
		
		JLabel lblInfoP1Wind = new JLabel("");
		lblInfoP1Wind.setBounds(16, 2, 23, 23);
		lblInfoP1Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP1Wind.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transEs.gif"));
		panelInfoP1.add(lblInfoP1Wind);
		
		JLabel lblInfoP1Points = new JLabel("128,000");
		lblInfoP1Points.setBounds(4, 25, 46, 14);
		lblInfoP1Points.setBackground(new Color(0,0,0,0));
		lblInfoP1Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP1.add(lblInfoP1Points);
		
		JLabel lblInfoP1Riichi = new JLabel("");
		lblInfoP1Riichi.setBounds(2, 40, 50, 8);
		lblInfoP1Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP1Riichi.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\other\\riichiStick.gif"));
		panelInfoP1.add(lblInfoP1Riichi);
		
		
		
		
		
		JPanel panelInfoP2 = new JPanel();
		panelInfoP2.setBounds(112, 52, 54, 54);
		panelRoundInfo.add(panelInfoP2);
		panelInfoP2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP2.setBackground(new Color(0, 255, 255, 35));
		panelInfoP2.setLayout(null);
		
		JLabel lblInfoP2Wind = new JLabel("");
		lblInfoP2Wind.setBounds(16, 2, 23, 23);
		lblInfoP2Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP2Wind.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transSs.gif"));
		panelInfoP2.add(lblInfoP2Wind);
		
		JLabel lblInfoP2Points = new JLabel("128,000");
		lblInfoP2Points.setBounds(4, 25, 46, 14);
		lblInfoP2Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP2Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP2.add(lblInfoP2Points);
		
		JLabel lblInfoP2Riichi = new JLabel("");
		lblInfoP2Riichi.setBounds(2, 40, 50, 8);
		lblInfoP2Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP2Riichi.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\other\\riichiStick.gif"));
		panelInfoP2.add(lblInfoP2Riichi);
		
		
		
		
		
		JPanel panelInfoP3 = new JPanel();
		panelInfoP3.setBounds(56, 0, 54, 54);
		panelRoundInfo.add(panelInfoP3);
		panelInfoP3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP3.setBackground(new Color(0, 255, 255, 35));
		panelInfoP3.setLayout(null);
		
		JLabel lblInfoP3Wind = new JLabel("");
		lblInfoP3Wind.setBounds(16, 2, 23, 23);
		lblInfoP3Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP3Wind.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transWs.gif"));
		panelInfoP3.add(lblInfoP3Wind);
		
		JLabel lblInfoP3Points = new JLabel("128,000");
		lblInfoP3Points.setBounds(4, 25, 46, 14);
		lblInfoP3Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP3Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP3.add(lblInfoP3Points);
		
		JLabel lblInfoP3Riichi = new JLabel("");
		lblInfoP3Riichi.setBounds(2, 40, 50, 8);
		lblInfoP3Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP3Riichi.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\other\\riichiStick.gif"));
		panelInfoP3.add(lblInfoP3Riichi);
		
		
		
		
		
		JPanel panelInfoP4 = new JPanel();
		panelInfoP4.setBounds(0, 52, 54, 54);
		panelRoundInfo.add(panelInfoP4);
		panelInfoP4.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP4.setBackground(new Color(0, 255, 255, 35));
		panelInfoP4.setLayout(null);
		
		JLabel lblInfoP4Wind = new JLabel("");
		lblInfoP4Wind.setBounds(16, 2, 23, 23);
		lblInfoP4Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP4Wind.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transNs.gif"));
		panelInfoP4.add(lblInfoP4Wind);
		
		JLabel lblInfoP4Points = new JLabel("128,000");
		lblInfoP4Points.setBounds(4, 25, 46, 14);
		lblInfoP4Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP4Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP4.add(lblInfoP4Points);
		
		JLabel lblInfoP4Riichi = new JLabel("");
		lblInfoP4Riichi.setBounds(2, 40, 50, 8);
		lblInfoP4Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP4Riichi.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\other\\riichiStick.gif"));
		panelInfoP4.add(lblInfoP4Riichi);
		
		//exists only to color the background behind the round info
		JPanel panelRndInfBackground = new JPanel();
		panelRndInfBackground.setBounds(217, 219, 166, 158);
		panelMidTable.add(panelRndInfBackground);
		panelRndInfBackground.setLayout(null);
		
		
		
		
		
		
		
		JPanel panelPlayer1 = new JPanel();
		panelPlayer1.setBounds(185, 753, 667, 82);
		panelPlayer1.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer1);
		panelPlayer1.setLayout(null);
		
		JPanel panelH1 = new JPanel();
		panelH1.setBounds(0, 0, 506, 46);
		panelH1.setBackground(COLOR_TRANSPARENT);
		panelPlayer1.add(panelH1);
		panelH1.setLayout(null);
		
		JLabel lblH1T1 = new JLabel("");
		lblH1T1.setBounds(0, 0, 36, 46);
		lblH1T1.setIcon(hImg);
		panelH1.add(lblH1T1);
		
		JLabel lblH1T2 = new JLabel("");
		lblH1T2.setBounds(36, 0, 36, 46);
		lblH1T2.setIcon(hImg);
		panelH1.add(lblH1T2);
		
		JLabel lblH1T3 = new JLabel("");
		lblH1T3.setBounds(72, 0, 36, 46);
		lblH1T3.setIcon(hImg);
		panelH1.add(lblH1T3);
		
		JLabel lblH1T4 = new JLabel("");
		lblH1T4.setBounds(108, 0, 36, 46);
		lblH1T4.setIcon(hImg);
		panelH1.add(lblH1T4);
		
		JLabel lblH1T5 = new JLabel("");
		lblH1T5.setBounds(144, 0, 36, 46);
		lblH1T5.setIcon(hImg);
		panelH1.add(lblH1T5);
		
		JLabel lblH1T6 = new JLabel("");
		lblH1T6.setBounds(180, 0, 36, 46);
		lblH1T6.setIcon(hImg);
		panelH1.add(lblH1T6);
		
		JLabel lblH1T7 = new JLabel("");
		lblH1T7.setBounds(216, 0, 36, 46);
		lblH1T7.setIcon(hImg);
		panelH1.add(lblH1T7);
		
		JLabel lblH1T8 = new JLabel("");
		lblH1T8.setBounds(252, 0, 36, 46);
		lblH1T8.setIcon(hImg);
		panelH1.add(lblH1T8);
		
		JLabel lblH1T9 = new JLabel("");
		lblH1T9.setBounds(288, 0, 36, 46);
		lblH1T9.setIcon(hImg);
		panelH1.add(lblH1T9);
		
		JLabel lblH1T10 = new JLabel("");
		lblH1T10.setBounds(324, 0, 36, 46);
		lblH1T10.setIcon(hImg);
		panelH1.add(lblH1T10);
		
		JLabel lblH1T11 = new JLabel("");
		lblH1T11.setBounds(360, 0, 36, 46);
		lblH1T11.setIcon(hImg);
		panelH1.add(lblH1T11);
		
		JLabel lblH1T12 = new JLabel("");
		lblH1T12.setBounds(396, 0, 36, 46);
		lblH1T12.setIcon(hImg);
		panelH1.add(lblH1T12);
		
		JLabel lblH1T13 = new JLabel("");
		lblH1T13.setBounds(432, 0, 36, 46);
		lblH1T13.setIcon(hImg);
		panelH1.add(lblH1T13);
		
		JLabel lblH1T14 = new JLabel("");
		lblH1T14.setBounds(468, 0, 36, 46);
		lblH1T14.setIcon(hImg);
		panelH1.add(lblH1T14);
		
		
		
		
		
		
		JPanel panelH1Ms = new JPanel();
		panelH1Ms.setBounds(192, 47, 474, 35);
		panelH1Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer1.add(panelH1Ms);
		panelH1Ms.setLayout(null);
		
		
		
		
		JPanel panelH1M4 = new JPanel();
		panelH1M4.setBounds(0, 0, 114, 35);
		panelH1M4.setBackground(COLOR_TRANSPARENT);
		panelH1M4.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH1M4T1 = new JLabel("");
		panelH1M4.add(lblH1M4T1);
		lblH1M4T1.setIcon(meldImg);
		
		JLabel lblH1M4T2 = new JLabel("");
		panelH1M4.add(lblH1M4T2);
		lblH1M4T2.setIcon(meldImg);
		
		JLabel lblH1M4T3 = new JLabel("");
		panelH1M4.add(lblH1M4T3);
		lblH1M4T3.setIcon(meldImg);
		
		JLabel lblH1M4T4 = new JLabel("");
		panelH1M4.add(lblH1M4T4);
		lblH1M4T4.setIcon(meldImg);
		panelH1Ms.add(panelH1M4);
		
		
		
		JPanel panelH1M3 = new JPanel();
		panelH1M3.setBounds(120, 0, 114, 35);
		panelH1M3.setBackground(COLOR_TRANSPARENT);
		panelH1Ms.add(panelH1M3);
		panelH1M3.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH1M3T1 = new JLabel("");
		panelH1M3.add(lblH1M3T1);
		lblH1M3T1.setIcon(meldImg);
		
		JLabel lblH1M3T2 = new JLabel("");
		panelH1M3.add(lblH1M3T2);
		lblH1M3T2.setIcon(meldImg);
		
		JLabel lblH1M3T3 = new JLabel("");
		panelH1M3.add(lblH1M3T3);
		lblH1M3T3.setIcon(meldImg);
		
		JLabel lblH1M3T4 = new JLabel("");
		panelH1M3.add(lblH1M3T4);
		lblH1M3T4.setIcon(meldImg);
		
		
		
		JPanel panelH1M2 = new JPanel();
		panelH1M2.setBounds(240, 0, 114, 35);
		panelH1M2.setBackground(COLOR_TRANSPARENT);
		panelH1Ms.add(panelH1M2);
		panelH1M2.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH1M2T1 = new JLabel("");
		panelH1M2.add(lblH1M2T1);
		lblH1M2T1.setIcon(meldImg);
		
		JLabel lblH1M2T2 = new JLabel("");
		panelH1M2.add(lblH1M2T2);
		lblH1M2T2.setIcon(meldImg);
		
		JLabel lblH1M2T3 = new JLabel("");
		panelH1M2.add(lblH1M2T3);
		lblH1M2T3.setIcon(meldImg);
		
		JLabel lblH1M2T4 = new JLabel("");
		panelH1M2.add(lblH1M2T4);
		lblH1M2T4.setIcon(meldImg);
		
		
		JPanel panelH1M1 = new JPanel();
		panelH1M1.setBounds(360, 0, 114, 35);
		panelH1M1.setBackground(COLOR_TRANSPARENT);
		panelH1Ms.add(panelH1M1);
		panelH1M1.setLayout(new GridLayout(1, 4, 0, 0));
		
		
		JLabel lblH1M1T1 = new JLabel("");
		panelH1M1.add(lblH1M1T1);
		lblH1M1T1.setIcon(meldImg);
		
		JLabel lblH1M1T2 = new JLabel("");
		panelH1M1.add(lblH1M1T2);
		lblH1M1T2.setIcon(meldImg);
		
		JLabel lblH1M1T3 = new JLabel("");
		panelH1M1.add(lblH1M1T3);
		lblH1M1T3.setIcon(meldImg);
		
		JLabel lblH1M1T4 = new JLabel("");
		panelH1M1.add(lblH1M1T4);
		lblH1M1T4.setIcon(meldImg);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JPanel panelPlayer2 = new JPanel();
		panelPlayer2.setBounds(777, 9, 82, 667);
		panelPlayer2.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer2);
		panelPlayer2.setLayout(null);
		
		JPanel panelH2 = new JPanel();
		panelH2.setBounds(0, 161, 46, 506);
		panelH2.setBackground(COLOR_TRANSPARENT);
		panelPlayer2.add(panelH2);
		panelH2.setLayout(null);
		
		
		JLabel lblH2T1 = new JLabel("");
		lblH2T1.setBounds(0, 468, 46, 36);
		lblH2T1.setIcon(h2Img);
		panelH2.add(lblH2T1);
		
		JLabel lblH2T2 = new JLabel("");
		lblH2T2.setBounds(0, 432, 46, 36);
		lblH2T2.setIcon(h2Img);
		panelH2.add(lblH2T2);
		
		JLabel lblH2T3 = new JLabel("");
		lblH2T3.setBounds(0, 396, 46, 36);
		lblH2T3.setIcon(h2Img);
		panelH2.add(lblH2T3);
		
		JLabel lblH2T4 = new JLabel("");
		lblH2T4.setBounds(0, 360, 46, 36);
		lblH2T4.setIcon(h2Img);
		panelH2.add(lblH2T4);
		
		JLabel lblH2T5 = new JLabel("");
		lblH2T5.setBounds(0, 324, 46, 36);
		lblH2T5.setIcon(h2Img);
		panelH2.add(lblH2T5);
		
		JLabel lblH2T6 = new JLabel("");
		lblH2T6.setBounds(0, 288, 46, 36);
		lblH2T6.setIcon(h2Img);
		panelH2.add(lblH2T6);
		
		JLabel lblH2T7 = new JLabel("");
		lblH2T7.setBounds(0, 252, 46, 36);
		lblH2T7.setIcon(h2Img);
		panelH2.add(lblH2T7);
		
		JLabel lblH2T8 = new JLabel("");
		lblH2T8.setBounds(0, 216, 46, 36);
		lblH2T8.setIcon(h2Img);
		panelH2.add(lblH2T8);
		
		JLabel lblH2T9 = new JLabel("");
		lblH2T9.setBounds(0, 180, 46, 36);
		lblH2T9.setIcon(h2Img);
		panelH2.add(lblH2T9);
		
		JLabel lblH2T10 = new JLabel("");
		lblH2T10.setBounds(0, 144, 46, 36);
		lblH2T10.setIcon(h2Img);
		panelH2.add(lblH2T10);
		
		JLabel lblH2T11 = new JLabel("");
		lblH2T11.setBounds(0, 108, 46, 36);
		lblH2T11.setIcon(h2Img);
		panelH2.add(lblH2T11);
		
		JLabel lblH2T12 = new JLabel("");
		lblH2T12.setBounds(0, 72, 46, 36);
		lblH2T12.setIcon(h2Img);
		panelH2.add(lblH2T12);
		
		JLabel lblH2T13 = new JLabel("");
		lblH2T13.setBounds(0, 36, 46, 36);
		lblH2T13.setIcon(h2Img);
		panelH2.add(lblH2T13);
		
		JLabel lblH2T14 = new JLabel("");
		lblH2T14.setBounds(0, 0, 46, 36);
		lblH2T14.setIcon(h2Img);
		panelH2.add(lblH2T14);
		
		
		JPanel panelH2Ms = new JPanel();
		panelH2Ms.setBounds(46, 0, 35, 474);
		panelH2Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer2.add(panelH2Ms);
		panelH2Ms.setLayout(null);
		
		
		JPanel panelH2M1 = new JPanel();
		panelH2M1.setBounds(0, 0, 35, 114);
		panelH2M1.setBackground(COLOR_TRANSPARENT);
		panelH2Ms.add(panelH2M1);
		panelH2M1.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblH2M1T4 = new JLabel("");
		panelH2M1.add(lblH2M1T4);
		lblH2M1T4.setIcon(meld2Img);
		
		JLabel lblH2M1T3 = new JLabel("");
		panelH2M1.add(lblH2M1T3);
		lblH2M1T3.setIcon(meld2Img);
		
		JLabel lblH2M1T2 = new JLabel("");
		panelH2M1.add(lblH2M1T2);
		lblH2M1T2.setIcon(meld2Img);
		
		JLabel lblH2M1T1 = new JLabel("");
		panelH2M1.add(lblH2M1T1);
		lblH2M1T1.setIcon(meld2Img);
		
		
		
		
		JPanel panelH2M4 = new JPanel();
		panelH2M4.setBounds(0, 360, 35, 114);
		panelH2M4.setBackground(COLOR_TRANSPARENT);
		panelH2M4.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblH2M4T4 = new JLabel("");
		panelH2M4.add(lblH2M4T4);
		lblH2M4T4.setIcon(meld2Img);
		
		JLabel lblH2M4T3 = new JLabel("");
		panelH2M4.add(lblH2M4T3);
		lblH2M4T3.setIcon(meld2Img);
		
		JLabel lblH2M4T2 = new JLabel("");
		panelH2M4.add(lblH2M4T2);
		lblH2M4T2.setIcon(meld2Img);
		
		
		
		JPanel panelH2M2 = new JPanel();
		panelH2M2.setBounds(0, 120, 35, 114);
		panelH2M2.setBackground(COLOR_TRANSPARENT);
		panelH2Ms.add(panelH2M2);
		panelH2M2.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblH2M2T4 = new JLabel("");
		panelH2M2.add(lblH2M2T4);
		lblH2M2T4.setIcon(meld2Img);
		
		JLabel lblH2M2T3 = new JLabel("");
		panelH2M2.add(lblH2M2T3);
		lblH2M2T3.setIcon(meld2Img);
		
		JLabel lblH2M2T2 = new JLabel("");
		panelH2M2.add(lblH2M2T2);
		lblH2M2T2.setIcon(meld2Img);
		
		JLabel lblH2M2T1 = new JLabel("");
		panelH2M2.add(lblH2M2T1);
		lblH2M2T1.setIcon(meld2Img);
		
		
		
		
		JPanel panelH2M3 = new JPanel();
		panelH2M3.setBounds(0, 240, 35, 114);
		panelH2M3.setBackground(COLOR_TRANSPARENT);
		panelH2Ms.add(panelH2M3);
		panelH2M3.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblH2M3T4 = new JLabel("");
		panelH2M3.add(lblH2M3T4);
		lblH2M3T4.setIcon(meld2Img);
		
		JLabel lblH2M3T3 = new JLabel("");
		panelH2M3.add(lblH2M3T3);
		lblH2M3T3.setIcon(meld2Img);
		
		JLabel lblH2M3T2 = new JLabel("");
		panelH2M3.add(lblH2M3T2);
		lblH2M3T2.setIcon(meld2Img);
		
		JLabel lblH2M3T1 = new JLabel("");
		panelH2M3.add(lblH2M3T1);
		lblH2M3T1.setIcon(meld2Img);
		panelH2Ms.add(panelH2M4);
		
		JLabel lblH2M4T1 = new JLabel("");
		panelH2M4.add(lblH2M4T1);
		lblH2M4T1.setIcon(meld2Img);
		
		
		
		
		
		
		
		
		JPanel panelPlayer3 = new JPanel();
		panelPlayer3.setBounds(23, 15, 667, 82);
		panelPlayer3.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer3);
		panelPlayer3.setLayout(null);
		
		
		
		
		
		JPanel panelH3 = new JPanel();
		panelH3.setBounds(161, 36, 506, 46);
		panelH3.setBackground(COLOR_TRANSPARENT);
		panelPlayer3.add(panelH3);
		panelH3.setLayout(null);
		
		JLabel lblH3T1 = new JLabel("");
		lblH3T1.setBounds(468, 0, 36, 46);
		lblH3T1.setIcon(h3Img);
		panelH3.add(lblH3T1);
		
		JLabel lblH3T2 = new JLabel("");
		lblH3T2.setBounds(432, 0, 36, 46);
		lblH3T2.setIcon(h3Img);
		panelH3.add(lblH3T2);
		
		JLabel lblH3T3 = new JLabel("");
		lblH3T3.setBounds(396, 0, 36, 46);
		lblH3T3.setIcon(h3Img);
		panelH3.add(lblH3T3);
		
		JLabel lblH3T4 = new JLabel("");
		lblH3T4.setBounds(360, 0, 36, 46);
		lblH3T4.setIcon(h3Img);
		panelH3.add(lblH3T4);
		
		JLabel lblH3T5 = new JLabel("");
		lblH3T5.setBounds(324, 0, 36, 46);
		lblH3T5.setIcon(h3Img);
		panelH3.add(lblH3T5);
		
		JLabel lblH3T6 = new JLabel("");
		lblH3T6.setBounds(288, 0, 36, 46);
		lblH3T6.setIcon(h3Img);
		panelH3.add(lblH3T6);
		
		JLabel lblH3T7 = new JLabel("");
		lblH3T7.setBounds(252, 0, 36, 46);
		lblH3T7.setIcon(h3Img);
		panelH3.add(lblH3T7);
		
		JLabel lblH3T8 = new JLabel("");
		lblH3T8.setBounds(216, 0, 36, 46);
		lblH3T8.setIcon(h3Img);
		panelH3.add(lblH3T8);
		
		JLabel lblH3T9 = new JLabel("");
		lblH3T9.setBounds(180, 0, 36, 46);
		lblH3T9.setIcon(h3Img);
		panelH3.add(lblH3T9);
		
		JLabel lblH3T10 = new JLabel("");
		lblH3T10.setBounds(144, 0, 36, 46);
		lblH3T10.setIcon(h3Img);
		panelH3.add(lblH3T10);
		
		JLabel lblH3T11 = new JLabel("");
		lblH3T11.setBounds(108, 0, 36, 46);
		lblH3T11.setIcon(h3Img);
		panelH3.add(lblH3T11);
		
		JLabel lblH3T12 = new JLabel("");
		lblH3T12.setBounds(72, 0, 36, 46);
		lblH3T12.setIcon(h3Img);
		panelH3.add(lblH3T12);
		
		JLabel lblH3T13 = new JLabel("");
		lblH3T13.setBounds(36, 0, 36, 46);
		lblH3T13.setIcon(h3Img);
		panelH3.add(lblH3T13);
		
		JLabel lblH3T14 = new JLabel("");
		lblH3T14.setBounds(0, 0, 36, 46);
		lblH3T14.setIcon(h3Img);
		panelH3.add(lblH3T14);
		
		
		
		
		JPanel panelH3Ms = new JPanel();
		panelH3Ms.setBounds(0, 0, 474, 35);
		panelH3Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer3.add(panelH3Ms);
		panelH3Ms.setLayout(null);
		
		
		JPanel panelH3M1 = new JPanel();
		panelH3M1.setBounds(0, 0, 114, 35);
		panelH3M1.setBackground(COLOR_TRANSPARENT);
		panelH3Ms.add(panelH3M1);
		panelH3M1.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH3M1T4 = new JLabel("");
		panelH3M1.add(lblH3M1T4);
		lblH3M1T4.setIcon(meld3Img);
		
		JLabel lblH3M1T3 = new JLabel("");
		panelH3M1.add(lblH3M1T3);
		lblH3M1T3.setIcon(meld3Img);
		
		JLabel lblH3M1T2 = new JLabel("");
		panelH3M1.add(lblH3M1T2);
		lblH3M1T2.setIcon(meld3Img);
		
		
		JLabel lblH3M1T1 = new JLabel("");
		panelH3M1.add(lblH3M1T1);
		lblH3M1T1.setIcon(meld3Img);
		
		
		
		
		JPanel panelH3M4 = new JPanel();
		panelH3M4.setBounds(360, 0, 114, 35);
		panelH3M4.setBackground(COLOR_TRANSPARENT);
		panelH3M4.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH3M4T4 = new JLabel("");
		panelH3M4.add(lblH3M4T4);
		lblH3M4T4.setIcon(meld3Img);
		
		JLabel lblH3M4T3 = new JLabel("");
		panelH3M4.add(lblH3M4T3);
		lblH3M4T3.setIcon(meld3Img);
		
		JLabel lblH3M4T2 = new JLabel("");
		panelH3M4.add(lblH3M4T2);
		lblH3M4T2.setIcon(meld3Img);
		
		
		
		JPanel panelH3M2 = new JPanel();
		panelH3M2.setBounds(120, 0, 114, 35);
		panelH3M2.setBackground(COLOR_TRANSPARENT);
		panelH3Ms.add(panelH3M2);
		panelH3M2.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH3M2T4 = new JLabel("");
		panelH3M2.add(lblH3M2T4);
		lblH3M2T4.setIcon(meld3Img);
		
		JLabel lblH3M2T3 = new JLabel("");
		panelH3M2.add(lblH3M2T3);
		lblH3M2T3.setIcon(meld3Img);
		
		JLabel lblH3M2T2 = new JLabel("");
		panelH3M2.add(lblH3M2T2);
		lblH3M2T2.setIcon(meld3Img);
		
		JLabel lblH3M2T1 = new JLabel("");
		panelH3M2.add(lblH3M2T1);
		lblH3M2T1.setIcon(meld3Img);
		
		
		
		
		JPanel panelH3M3 = new JPanel();
		panelH3M3.setBounds(240, 0, 114, 35);
		panelH3M3.setBackground(COLOR_TRANSPARENT);
		panelH3Ms.add(panelH3M3);
		panelH3M3.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH3M3T4 = new JLabel("");
		panelH3M3.add(lblH3M3T4);
		lblH3M3T4.setIcon(meld3Img);
		
		JLabel lblH3M3T3 = new JLabel("");
		panelH3M3.add(lblH3M3T3);
		lblH3M3T3.setIcon(meld3Img);
		
		JLabel lblH3M3T2 = new JLabel("");
		panelH3M3.add(lblH3M3T2);
		lblH3M3T2.setIcon(meld3Img);
		
		JLabel lblH3M3T1 = new JLabel("");
		panelH3M3.add(lblH3M3T1);
		lblH3M3T1.setIcon(meld3Img);
		panelH3Ms.add(panelH3M4);
		
		JLabel lblH3M4T1 = new JLabel("");
		panelH3M4.add(lblH3M4T1);
		lblH3M4T1.setIcon(meld3Img);
		
		
		
		
		
		
		
		
		
		
		
		JPanel panelPlayer4 = new JPanel();
		panelPlayer4.setBounds(15, 171, 82, 667);
		panelPlayer4.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer4);
		panelPlayer4.setLayout(null);
		
		
		
		
		JPanel panelH4 = new JPanel();
		panelH4.setBounds(36, 0, 46, 506);
		panelH4.setBackground(COLOR_TRANSPARENT);
		panelPlayer4.add(panelH4);
		panelH4.setLayout(null);
		
		JLabel lblH4T1 = new JLabel("");
		lblH4T1.setBounds(0, 0, 46, 36);
		lblH4T1.setIcon(h4Img);
		panelH4.add(lblH4T1);
		
		JLabel lblH4T2 = new JLabel("");
		lblH4T2.setBounds(0, 36, 46, 36);
		lblH4T2.setIcon(h4Img);
		panelH4.add(lblH4T2);
		
		JLabel lblH4T3 = new JLabel("");
		lblH4T3.setBounds(0, 72, 46, 36);
		lblH4T3.setIcon(h4Img);
		panelH4.add(lblH4T3);
		
		JLabel lblH4T4 = new JLabel("");
		lblH4T4.setBounds(0, 108, 46, 36);
		lblH4T4.setIcon(h4Img);
		panelH4.add(lblH4T4);
		
		JLabel lblH4T5 = new JLabel("");
		lblH4T5.setBounds(0, 144, 46, 36);
		lblH4T5.setIcon(h4Img);
		panelH4.add(lblH4T5);
		
		JLabel lblH4T6 = new JLabel("");
		lblH4T6.setBounds(0, 180, 46, 36);
		lblH4T6.setIcon(h4Img);
		panelH4.add(lblH4T6);
		
		JLabel lblH4T7 = new JLabel("");
		lblH4T7.setBounds(0, 216, 46, 36);
		lblH4T7.setIcon(h4Img);
		panelH4.add(lblH4T7);
		
		JLabel lblH4T8 = new JLabel("");
		lblH4T8.setBounds(0, 252, 46, 36);
		lblH4T8.setIcon(h4Img);
		panelH4.add(lblH4T8);
		
		JLabel lblH4T9 = new JLabel("");
		lblH4T9.setBounds(0, 288, 46, 36);
		lblH4T9.setIcon(h4Img);
		panelH4.add(lblH4T9);
		
		JLabel lblH4T10 = new JLabel("");
		lblH4T10.setBounds(0, 324, 46, 36);
		lblH4T10.setIcon(h4Img);
		panelH4.add(lblH4T10);
		
		JLabel lblH4T11 = new JLabel("");
		lblH4T11.setBounds(0, 360, 46, 36);
		lblH4T11.setIcon(h4Img);
		panelH4.add(lblH4T11);
		
		JLabel lblH4T12 = new JLabel("");
		lblH4T12.setBounds(0, 396, 46, 36);
		lblH4T12.setIcon(h4Img);
		panelH4.add(lblH4T12);
		
		JLabel lblH4T13 = new JLabel("");
		lblH4T13.setBounds(0, 432, 46, 36);
		lblH4T13.setIcon(h4Img);
		panelH4.add(lblH4T13);
		
		JLabel lblH4T14 = new JLabel("");
		lblH4T14.setBounds(0, 468, 46, 36);
		lblH4T14.setIcon(h4Img);
		panelH4.add(lblH4T14);
		
		
		
		
		
		JPanel panelH4Ms = new JPanel();
		panelH4Ms.setBounds(0, 192, 35, 474);
		panelH4Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer4.add(panelH4Ms);
		panelH4Ms.setLayout(null);
		
		
		
		
		JPanel panelH4M1 = new JPanel();
		panelH4M1.setBounds(0, 360, 35, 114);
		panelH4M1.setBackground(COLOR_TRANSPARENT);
		panelH4Ms.add(panelH4M1);
		panelH4M1.setLayout(new GridLayout(4, 1, 0, 0));
		
		
		JLabel lblH4M1T1 = new JLabel("");
		panelH4M1.add(lblH4M1T1);
		lblH4M1T1.setIcon(meld4Img);
		
		JLabel lblH4M1T2 = new JLabel("");
		panelH4M1.add(lblH4M1T2);
		lblH4M1T2.setIcon(meld4Img);
		
		JLabel lblH4M1T3 = new JLabel("");
		panelH4M1.add(lblH4M1T3);
		lblH4M1T3.setIcon(meld4Img);
		
		JLabel lblH4M1T4 = new JLabel("");
		panelH4M1.add(lblH4M1T4);
		lblH4M1T4.setIcon(meld4Img);
		
		
		
		
		JPanel panelH4M4 = new JPanel();
		panelH4M4.setBounds(0, 0, 35, 114);
		panelH4M4.setBackground(COLOR_TRANSPARENT);
		panelH4M4.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblH4M4T1 = new JLabel("");
		panelH4M4.add(lblH4M4T1);
		lblH4M4T1.setIcon(meld4Img);
		
		JLabel lblH4M4T2 = new JLabel("");
		panelH4M4.add(lblH4M4T2);
		lblH4M4T2.setIcon(meld4Img);
		
		JLabel lblH4M4T3 = new JLabel("");
		panelH4M4.add(lblH4M4T3);
		lblH4M4T3.setIcon(meld4Img);
		
		JLabel lblH4M4T4 = new JLabel("");
		panelH4M4.add(lblH4M4T4);
		lblH4M4T4.setIcon(meld4Img);
		
		
		
		JPanel panelH4M2 = new JPanel();
		panelH4M2.setBounds(0, 240, 35, 114);
		panelH4M2.setBackground(COLOR_TRANSPARENT);
		panelH4Ms.add(panelH4M2);
		panelH4M2.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblH4M2T1 = new JLabel("");
		panelH4M2.add(lblH4M2T1);
		lblH4M2T1.setIcon(meld4Img);
		
		JLabel lblH4M2T2 = new JLabel("");
		panelH4M2.add(lblH4M2T2);
		lblH4M2T2.setIcon(meld4Img);
		
		JLabel lblH4M2T3 = new JLabel("");
		panelH4M2.add(lblH4M2T3);
		lblH4M2T3.setIcon(meld4Img);
		
		JLabel lblH4M2T4 = new JLabel("");
		panelH4M2.add(lblH4M2T4);
		lblH4M2T4.setIcon(meld4Img);
		
		
		
		
		JPanel panelH4M3 = new JPanel();
		panelH4M3.setBounds(0, 120, 35, 114);
		panelH4M3.setBackground(COLOR_TRANSPARENT);
		panelH4Ms.add(panelH4M3);
		panelH4M3.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblH4M3T1 = new JLabel("");
		panelH4M3.add(lblH4M3T1);
		lblH4M3T1.setIcon(meld4Img);
		
		JLabel lblH4M3T2 = new JLabel("");
		panelH4M3.add(lblH4M3T2);
		lblH4M3T2.setIcon(meld4Img);
		
		JLabel lblH4M3T3 = new JLabel("");
		panelH4M3.add(lblH4M3T3);
		lblH4M3T3.setIcon(meld4Img);
		
		JLabel lblH4M3T4 = new JLabel("");
		panelH4M3.add(lblH4M3T4);
		lblH4M3T4.setIcon(meld4Img);
		panelH4Ms.add(panelH4M4);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JPanel panelSidebar = new JPanel();
		panelSidebar.setBounds(874, 0, 276, 850);
		panelSidebar.setBackground(COLOR_SIDEBAR);
		contentPane.add(panelSidebar);
		panelSidebar.setLayout(null);
		
		JLabel labelWeHaveFun = new JLabel("");
		labelWeHaveFun.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\other\\sheepy2trans.png"));
		labelWeHaveFun.setBounds(4, 5, 270, 345);
		panelSidebar.add(labelWeHaveFun);
		
		
		
		
		
		
		
		
		///////////
		
		
		
		
		
		
		
		
		
		////////////////////////////////////////////////////////////
		
		
		
		
		
		
		
	}
}
