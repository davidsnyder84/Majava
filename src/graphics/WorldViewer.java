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
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class WorldViewer extends JFrame {
	
	
	private static final int WINDOW_WIDTH = 1150;
	private static final int WINDOW_HEIGHT = 850;
	
	private static final int WINDOW_TOP_BORDER_SIZE = 30;
	private static final int WINDOW_SIDE_BORDER_SIZE = 8;
	private static final int WINDOW_BOTTOM_BORDER_SIZE = 8;
	

	
	private static final int TILE_BIG_WIDTH = 30;
	private static final int TILE_BIG_HEIGHT = 41;
	private static final int TILE_SMALL_WIDTH = 23;
	private static final int TILE_SMALL_HEIGHT = 31;
	private static final int PONDPANEL_NEW_WIDTH = TILE_SMALL_WIDTH*6;
	private static final int PONDPANEL_NEW_HEIGHT = TILE_SMALL_HEIGHT*4;
	
	
	private static final int TILE_OLD_BIG_WIDTH = 36;
	private static final int TILE_OLD_BIG_HEIGHT = 46;
	private static final int TILE_OLD_SMALL_WIDTH = 27;
	private static final int TILE_OLD_SMALL_HEIGHT = 35;
	private static final int PONDPANEL_OLD_WIDTH = TILE_OLD_SMALL_WIDTH*6;
	private static final int PONDPANEL_OLD_HEIGHT = TILE_OLD_SMALL_HEIGHT*4;
	
	public static final int POND_PANEL_WIDTH = PONDPANEL_OLD_WIDTH;
	public static final int POND_PANEL_HEIGHT = PONDPANEL_NEW_HEIGHT;
	
	
	
	
	private static final Color COLOR_TABLE = new Color(0, 140, 0, 100);
	private static final Color COLOR_SIDEBAR = new Color(0, 255, 0, 100);
	private static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);
	
	
	
	private ArrayList<JLabel> Tilelabels = new ArrayList<JLabel>(); 
	
	private JPanel contentPane;
	private WorldViewer thisguy;
	
	private boolean toggle = true;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static final int SIZE_HAND = 14;
	private static final int SIZE_MELDPANEL = 4;
	private static final int SIZE_MELD = 4;
	private static final int SIZE_WALL = 34;
	private static final int SIZE_POND = 24;
	private static final int SIZE_LARRY_INFOPLAYER = 3;
	private static final int SIZE_LARRY_INFOROUND = 2;
	
	
	
	
	//////////////
	JLabel[] larryH1 = new JLabel[SIZE_HAND];
	JLabel[] larryH2 = new JLabel[SIZE_HAND];
	JLabel[] larryH3 = new JLabel[SIZE_HAND];
	JLabel[] larryH4 = new JLabel[SIZE_HAND];
	
	JLabel[] larryH1M1 = new JLabel[SIZE_MELD];
	JLabel[] larryH1M2 = new JLabel[SIZE_MELD];
	JLabel[] larryH1M3 = new JLabel[SIZE_MELD];
	JLabel[] larryH1M4 = new JLabel[SIZE_MELD];
	
	JLabel[] larryH2M1 = new JLabel[SIZE_MELD];
	JLabel[] larryH2M2 = new JLabel[SIZE_MELD];
	JLabel[] larryH2M3 = new JLabel[SIZE_MELD];
	JLabel[] larryH2M4 = new JLabel[SIZE_MELD];
	
	JLabel[] larryH3M1 = new JLabel[SIZE_MELD];
	JLabel[] larryH3M2 = new JLabel[SIZE_MELD];
	JLabel[] larryH3M3 = new JLabel[SIZE_MELD];
	JLabel[] larryH3M4 = new JLabel[SIZE_MELD];
	
	JLabel[] larryH4M1 = new JLabel[SIZE_MELD];
	JLabel[] larryH4M2 = new JLabel[SIZE_MELD];
	JLabel[] larryH4M3 = new JLabel[SIZE_MELD];
	JLabel[] larryH4M4 = new JLabel[SIZE_MELD];
	
	JLabel[][] larryH1Ms = {larryH1M1, larryH1M2, larryH1M3, larryH1M4};
	JLabel[][] larryH2Ms = {larryH2M1, larryH2M2, larryH2M3, larryH2M4};
	JLabel[][] larryH3Ms = {larryH3M1, larryH3M2, larryH3M3, larryH3M4};
	JLabel[][] larryH4Ms = {larryH4M1, larryH4M2, larryH4M3, larryH4M4};
	
	JLabel[] larryW1 = new JLabel[SIZE_WALL];
	JLabel[] larryW2 = new JLabel[SIZE_WALL];
	JLabel[] larryW3 = new JLabel[SIZE_WALL];
	JLabel[] larryW4 = new JLabel[SIZE_WALL];
	
	JLabel[] larryP1 = new JLabel[SIZE_POND];
	JLabel[] larryP2 = new JLabel[SIZE_POND];
	JLabel[] larryP3 = new JLabel[SIZE_POND];
	JLabel[] larryP4 = new JLabel[SIZE_POND];
	
	
	
	
	JLabel[] larryInfoP1 = new JLabel[SIZE_LARRY_INFOPLAYER];
	JLabel[] larryInfoP2 = new JLabel[SIZE_LARRY_INFOPLAYER];
	JLabel[] larryInfoP3 = new JLabel[SIZE_LARRY_INFOPLAYER];
	JLabel[] larryInfoP4 = new JLabel[SIZE_LARRY_INFOPLAYER];
	
	//larryInfoPlayers[player number][0 = seatwind, 1 = points, 2 = riichiStick]
	JLabel[][] larryInfoPlayers = {larryInfoP1, larryInfoP2, larryInfoP3, larryInfoP4};
	
	//0 = roundWind, 1 = roundNumber
	JLabel[] larryInfoRound = new JLabel[SIZE_LARRY_INFOROUND];
	
	
	
	
	
	
	
	
	
	
	private static final int SIZE_GARRY_TILES = 38;
	private static final int SIZE_GARRY_WINDS = 4;
	private static final int SIZE_GARRY_OTHER = 2;
	private static final int SIZE_NUM_SEATS = 4;
	private static final int GARRYINDEX_TILE_BIG = 0;
	private static final int GARRYINDEX_TILE_SMALL = 1;
	private static final int GARRYINDEX_RED5M = 35;
	private static final int GARRYINDEX_RED5P = 36;
	private static final int GARRYINDEX_RED5S = 37;
	
	
	ImageIcon[] garryTileS1big = new ImageIcon[SIZE_GARRY_TILES];
	ImageIcon[] garryTileS1small = new ImageIcon[SIZE_GARRY_TILES];
	ImageIcon[][] garryTileS1 = {garryTileS1big, garryTileS1small};
	
	ImageIcon[] garryTileS2big = new ImageIcon[SIZE_GARRY_TILES];
	ImageIcon[] garryTileS2small = new ImageIcon[SIZE_GARRY_TILES];
	ImageIcon[][] garryTileS2 = {garryTileS2big, garryTileS2small};
	
	ImageIcon[] garryTileS3big = new ImageIcon[SIZE_GARRY_TILES];
	ImageIcon[] garryTileS3small = new ImageIcon[SIZE_GARRY_TILES];
	ImageIcon[][] garryTileS3 = {garryTileS3big, garryTileS3small};
	
	ImageIcon[] garryTileS4big = new ImageIcon[SIZE_GARRY_TILES];
	ImageIcon[] garryTileS4small = new ImageIcon[SIZE_GARRY_TILES];
	ImageIcon[][] garryTileS4 = {garryTileS4big, garryTileS4small};
	
	//garryTiles[seat number][0=big,1=small][tile number]
	ImageIcon[][][] garryTiles = {garryTileS1, garryTileS2, garryTileS3, garryTileS4};
	
	
	
	ImageIcon[] garryWindsBig = new ImageIcon[SIZE_GARRY_WINDS];
	ImageIcon[] garryWindsSmall = new ImageIcon[SIZE_GARRY_WINDS];
	//garryWinds[0=big,1=small]
	ImageIcon[][] garryWinds = {garryWindsBig, garryWindsSmall}; 
	
	
	//0 = riichi stick, 1 = sheepy2
	ImageIcon[] garryOther = new ImageIcon[SIZE_GARRY_OTHER];
	
	
	
	
	
	
	
	
	
	
	
	
	

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
	public WorldViewer(){
		
		thisguy = this;
		
		final int WINDOW_BOUND_WIDTH = WINDOW_WIDTH + 2*WINDOW_SIDE_BORDER_SIZE;
		final int WINDOW_BOUND_HEIGHT = WINDOW_HEIGHT + WINDOW_TOP_BORDER_SIZE + WINDOW_BOTTOM_BORDER_SIZE;

		
		
		String windowIconImgPath = "C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\transE.png";
		ImageIcon windowIconImg = new ImageIcon(windowIconImgPath);
		windowIconImg.equals(windowIconImg);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(windowIconImgPath));
//		setIconImage(windowIconImg.getImage());
		setTitle("The Beaver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, WINDOW_BOUND_WIDTH, WINDOW_BOUND_HEIGHT);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

//		ImageIcon pImg = null;
		ImageIcon pImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\22.png");
		ImageIcon p2Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat2\\small\\22.png");
		ImageIcon p3Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat3\\small\\25.png");
		ImageIcon p4Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat4\\small\\21.png");
		
		ImageIcon wImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\0.png");
		ImageIcon w2Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat2\\small\\0.png");
		ImageIcon w3Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat3\\small\\0.png");
		ImageIcon w4Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat4\\small\\0.png");
		
		ImageIcon hImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\1.png");
		ImageIcon h2Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat2\\1.png");
		ImageIcon h3Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat3\\1.png");
		ImageIcon h4Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat4\\1.png");

		ImageIcon meldImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\34.png");
		ImageIcon meld2Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat2\\small\\34.png");
		ImageIcon meld3Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat3\\small\\34.png");
		ImageIcon meld4Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat4\\small\\34.png");
		

		ImageIcon windRImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\transE.png");
		ImageIcon wind1Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transEs.png");
		ImageIcon wind2Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transSs.png");
		ImageIcon wind3Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transWs.png");
		ImageIcon wind4Img = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transNs.png");
		
		ImageIcon riichiImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\other\\riichiStick.png");
		
		
		
		
		
		
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
		
		
		
		
		
		
		
		//panel declarations
		JPanel panelTable;
		JPanel panelSidebar;

		JPanel panelMidTable;
		JPanel panelP1;JPanel panelP2;JPanel panelP3;JPanel panelP4;
		JPanel panelW1;JPanel panelW2;JPanel panelW3;JPanel panelW4;
		JPanel panelRoundInfo;JPanel panelRInd;JPanel panelInfoP1;JPanel panelInfoP2;JPanel panelInfoP3;JPanel panelInfoP4;JPanel panelRndInfBackground;
		
		JPanel panelPlayer1;JPanel panelH1;JPanel panelH1Ms;
		JPanel panelH1M1;JPanel panelH1M2;JPanel panelH1M3;JPanel panelH1M4;
		JPanel panelPlayer2;JPanel panelH2;JPanel panelH2Ms;
		JPanel panelH2M1;JPanel panelH2M2;JPanel panelH2M3;JPanel panelH2M4;
		JPanel panelPlayer3;JPanel panelH3;JPanel panelH3Ms;
		JPanel panelH3M1;JPanel panelH3M2;JPanel panelH3M3;JPanel panelH3M4;
		JPanel panelPlayer4;JPanel panelH4;JPanel panelH4Ms;
		JPanel panelH4M1;JPanel panelH4M2;JPanel panelH4M3;JPanel panelH4M4;
		
		
		
		//label declarations
		JLabel lblH1T1;JLabel lblH1T2;JLabel lblH1T3;JLabel lblH1T4;JLabel lblH1T5;JLabel lblH1T6;JLabel lblH1T7;JLabel lblH1T8;JLabel lblH1T9;JLabel lblH1T10;JLabel lblH1T11;JLabel lblH1T12;JLabel lblH1T13;JLabel lblH1T14;
		JLabel lblH1M1T1;JLabel lblH1M1T2;JLabel lblH1M1T3;JLabel lblH1M1T4;JLabel lblH1M2T1;JLabel lblH1M2T2;JLabel lblH1M2T3;JLabel lblH1M2T4;JLabel lblH1M3T1;JLabel lblH1M3T2;JLabel lblH1M3T3;JLabel lblH1M3T4;JLabel lblH1M4T1;JLabel lblH1M4T2;JLabel lblH1M4T3;JLabel lblH1M4T4;
		JLabel lblH2T1;JLabel lblH2T2;JLabel lblH2T3;JLabel lblH2T4;JLabel lblH2T5;JLabel lblH2T6;JLabel lblH2T7;JLabel lblH2T8;JLabel lblH2T9;JLabel lblH2T10;JLabel lblH2T11;JLabel lblH2T12;JLabel lblH2T13;JLabel lblH2T14;
		JLabel lblH2M1T1;JLabel lblH2M1T2;JLabel lblH2M1T3;JLabel lblH2M1T4;JLabel lblH2M2T1;JLabel lblH2M2T2;JLabel lblH2M2T3;JLabel lblH2M2T4;JLabel lblH2M3T1;JLabel lblH2M3T2;JLabel lblH2M3T3;JLabel lblH2M3T4;JLabel lblH2M4T1;JLabel lblH2M4T2;JLabel lblH2M4T3;JLabel lblH2M4T4;
		JLabel lblH3T1;JLabel lblH3T2;JLabel lblH3T3;JLabel lblH3T4;JLabel lblH3T5;JLabel lblH3T6;JLabel lblH3T7;JLabel lblH3T8;JLabel lblH3T9;JLabel lblH3T10;JLabel lblH3T11;JLabel lblH3T12;JLabel lblH3T13;JLabel lblH3T14;
		JLabel lblH3M1T1;JLabel lblH3M1T2;JLabel lblH3M1T3;JLabel lblH3M1T4;JLabel lblH3M2T1;JLabel lblH3M2T2;JLabel lblH3M2T3;JLabel lblH3M2T4;JLabel lblH3M3T1;JLabel lblH3M3T2;JLabel lblH3M3T3;JLabel lblH3M3T4;JLabel lblH3M4T1;JLabel lblH3M4T2;JLabel lblH3M4T3;JLabel lblH3M4T4;
		JLabel lblH4T1;JLabel lblH4T2;JLabel lblH4T3;JLabel lblH4T4;JLabel lblH4T5;JLabel lblH4T6;JLabel lblH4T7;JLabel lblH4T8;JLabel lblH4T9;JLabel lblH4T10;JLabel lblH4T11;JLabel lblH4T12;JLabel lblH4T13;JLabel lblH4T14;
		JLabel lblH4M1T1;JLabel lblH4M1T2;JLabel lblH4M1T3;JLabel lblH4M1T4;JLabel lblH4M2T1;JLabel lblH4M2T2;JLabel lblH4M2T3;JLabel lblH4M2T4;JLabel lblH4M3T1;JLabel lblH4M3T2;JLabel lblH4M3T3;JLabel lblH4M3T4;JLabel lblH4M4T1;JLabel lblH4M4T2;JLabel lblH4M4T3;JLabel lblH4M4T4;
		
		JLabel lblRIndNum;JLabel lblRIndWind;
		JLabel lblInfoP1Points;JLabel lblInfoP1Riichi;JLabel lblInfoP1Wind;
		JLabel lblInfoP2Points;JLabel lblInfoP2Riichi;JLabel lblInfoP2Wind;
		JLabel lblInfoP3Points;JLabel lblInfoP3Riichi;JLabel lblInfoP3Wind;
		JLabel lblInfoP4Points;JLabel lblInfoP4Riichi;JLabel lblInfoP4Wind;
		
		JLabel lblP1T1;JLabel lblP1T2;JLabel lblP1T3;JLabel lblP1T4;JLabel lblP1T5;JLabel lblP1T6;JLabel lblP1T7;JLabel lblP1T8;JLabel lblP1T9;JLabel lblP1T10;JLabel lblP1T11;JLabel lblP1T12;JLabel lblP1T13;JLabel lblP1T14;JLabel lblP1T15;JLabel lblP1T16;JLabel lblP1T17;JLabel lblP1T18;JLabel lblP1T19;JLabel lblP1T20;JLabel lblP1T21;JLabel lblP1T22;JLabel lblP1T23;JLabel lblP1T24;
		JLabel lblP2T1;JLabel lblP2T2;JLabel lblP2T3;JLabel lblP2T4;JLabel lblP2T5;JLabel lblP2T6;JLabel lblP2T7;JLabel lblP2T8;JLabel lblP2T9;JLabel lblP2T10;JLabel lblP2T11;JLabel lblP2T12;JLabel lblP2T13;JLabel lblP2T14;JLabel lblP2T15;JLabel lblP2T16;JLabel lblP2T17;JLabel lblP2T18;JLabel lblP2T19;JLabel lblP2T20;JLabel lblP2T21;JLabel lblP2T22;JLabel lblP2T23;JLabel lblP2T24;
		JLabel lblP3T1;JLabel lblP3T2;JLabel lblP3T3;JLabel lblP3T4;JLabel lblP3T5;JLabel lblP3T6;JLabel lblP3T7;JLabel lblP3T8;JLabel lblP3T9;JLabel lblP3T10;JLabel lblP3T11;JLabel lblP3T12;JLabel lblP3T13;JLabel lblP3T14;JLabel lblP3T15;JLabel lblP3T16;JLabel lblP3T17;JLabel lblP3T18;JLabel lblP3T19;JLabel lblP3T20;JLabel lblP3T21;JLabel lblP3T22;JLabel lblP3T23;JLabel lblP3T24;
		JLabel lblP4T1;JLabel lblP4T2;JLabel lblP4T3;JLabel lblP4T4;JLabel lblP4T5;JLabel lblP4T6;JLabel lblP4T7;JLabel lblP4T8;JLabel lblP4T9;JLabel lblP4T10;JLabel lblP4T11;JLabel lblP4T12;JLabel lblP4T13;JLabel lblP4T14;JLabel lblP4T15;JLabel lblP4T16;JLabel lblP4T17;JLabel lblP4T18;JLabel lblP4T19;JLabel lblP4T20;JLabel lblP4T21;JLabel lblP4T22;JLabel lblP4T23;JLabel lblP4T24;
		
		JLabel lblW1T1;JLabel lblW1T2;JLabel lblW1T3;JLabel lblW1T4;JLabel lblW1T5;JLabel lblW1T6;JLabel lblW1T7;JLabel lblW1T8;JLabel lblW1T9;JLabel lblW1T10;JLabel lblW1T11;JLabel lblW1T12;JLabel lblW1T13;JLabel lblW1T14;JLabel lblW1T15;JLabel lblW1T16;JLabel lblW1T17;JLabel lblW1T18;JLabel lblW1T19;JLabel lblW1T20;JLabel lblW1T21;JLabel lblW1T22;JLabel lblW1T23;JLabel lblW1T24;JLabel lblW1T25;JLabel lblW1T26;JLabel lblW1T27;JLabel lblW1T28;JLabel lblW1T29;JLabel lblW1T30;JLabel lblW1T31;JLabel lblW1T32;JLabel lblW1T33;JLabel lblW1T34;
		JLabel lblW2T1;JLabel lblW2T2;JLabel lblW2T3;JLabel lblW2T4;JLabel lblW2T5;JLabel lblW2T6;JLabel lblW2T7;JLabel lblW2T8;JLabel lblW2T9;JLabel lblW2T10;JLabel lblW2T11;JLabel lblW2T12;JLabel lblW2T13;JLabel lblW2T14;JLabel lblW2T15;JLabel lblW2T16;JLabel lblW2T17;JLabel lblW2T18;JLabel lblW2T19;JLabel lblW2T20;JLabel lblW2T21;JLabel lblW2T22;JLabel lblW2T23;JLabel lblW2T24;JLabel lblW2T25;JLabel lblW2T26;JLabel lblW2T27;JLabel lblW2T28;JLabel lblW2T29;JLabel lblW2T30;JLabel lblW2T31;JLabel lblW2T32;JLabel lblW2T33;JLabel lblW2T34;
		JLabel lblW3T1;JLabel lblW3T2;JLabel lblW3T3;JLabel lblW3T4;JLabel lblW3T5;JLabel lblW3T6;JLabel lblW3T7;JLabel lblW3T8;JLabel lblW3T9;JLabel lblW3T10;JLabel lblW3T11;JLabel lblW3T12;JLabel lblW3T13;JLabel lblW3T14;JLabel lblW3T15;JLabel lblW3T16;JLabel lblW3T17;JLabel lblW3T18;JLabel lblW3T19;JLabel lblW3T20;JLabel lblW3T21;JLabel lblW3T22;JLabel lblW3T23;JLabel lblW3T24;JLabel lblW3T25;JLabel lblW3T26;JLabel lblW3T27;JLabel lblW3T28;JLabel lblW3T29;JLabel lblW3T30;JLabel lblW3T31;JLabel lblW3T32;JLabel lblW3T33;JLabel lblW3T34;
		JLabel lblW4T1;JLabel lblW4T2;JLabel lblW4T3;JLabel lblW4T4;JLabel lblW4T5;JLabel lblW4T6;JLabel lblW4T7;JLabel lblW4T8;JLabel lblW4T9;JLabel lblW4T10;JLabel lblW4T11;JLabel lblW4T12;JLabel lblW4T13;JLabel lblW4T14;JLabel lblW4T15;JLabel lblW4T16;JLabel lblW4T17;JLabel lblW4T18;JLabel lblW4T19;JLabel lblW4T20;JLabel lblW4T21;JLabel lblW4T22;JLabel lblW4T23;JLabel lblW4T24;JLabel lblW4T25;JLabel lblW4T26;JLabel lblW4T27;JLabel lblW4T28;JLabel lblW4T29;JLabel lblW4T30;JLabel lblW4T31;JLabel lblW4T32;JLabel lblW4T33;JLabel lblW4T34;
		
		JLabel labelWeHaveFun;
		JLabel lblBigTileDemo1;
		JLabel lblBigTileDemo2;
		JLabel lblBigTileDemo3;
		JLabel lblSmallTileDemo1;
		JLabel lblSmallTileDemo2;
		JLabel lblSmallTileDemo3;
		
		
		
		
		
		
		
		
		
		
		
		

		
		
		panelTable = new JPanel();
		panelTable.setBounds(0, 0, 874, 850);
		panelTable.setBackground(COLOR_TABLE);
		contentPane.add(panelTable);
		panelTable.setLayout(null);
		
		
		
		
		
		
		panelMidTable = new JPanel();
		panelMidTable.setBounds(161, 153, 552, 544);
		panelMidTable.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelMidTable);
		panelMidTable.setLayout(null);
		
		
		
		
		
		panelP1 = new JPanel();
		panelP1.setBounds(196, 352, POND_PANEL_WIDTH, POND_PANEL_HEIGHT);
		panelP1.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP1);
		panelP1.setLayout(new GridLayout(4, 6, 0, 0));
		
		lblP1T1 = new JLabel("");
		panelP1.add(lblP1T1);
		lblP1T1.setIcon(pImg);
		
		lblP1T2 = new JLabel("");
		panelP1.add(lblP1T2);
		lblP1T2.setIcon(pImg);
		
		lblP1T3 = new JLabel("");
		panelP1.add(lblP1T3);
		lblP1T3.setIcon(pImg);
		
		lblP1T4 = new JLabel("");
		panelP1.add(lblP1T4);
		lblP1T4.setIcon(pImg);
		
		lblP1T5 = new JLabel("");
		panelP1.add(lblP1T5);
		lblP1T5.setIcon(pImg);
		
		lblP1T6 = new JLabel("");
		panelP1.add(lblP1T6);
		lblP1T6.setIcon(pImg);
		
		lblP1T7 = new JLabel("");
		panelP1.add(lblP1T7);
		lblP1T7.setIcon(pImg);
		
		lblP1T8 = new JLabel("");
		panelP1.add(lblP1T8);
		lblP1T8.setIcon(pImg);
		
		lblP1T9 = new JLabel("");
		panelP1.add(lblP1T9);
		lblP1T9.setIcon(pImg);
		
		lblP1T10 = new JLabel("");
		panelP1.add(lblP1T10);
		lblP1T10.setIcon(pImg);
		
		lblP1T11 = new JLabel("");
		panelP1.add(lblP1T11);
		lblP1T11.setIcon(pImg);
		
		lblP1T12 = new JLabel("");
		panelP1.add(lblP1T12);
		lblP1T12.setIcon(pImg);
		
		lblP1T13 = new JLabel("");
		panelP1.add(lblP1T13);
		lblP1T13.setIcon(pImg);
		
		lblP1T14 = new JLabel("");
		panelP1.add(lblP1T14);
		lblP1T14.setIcon(pImg);
		
		lblP1T15 = new JLabel("");
		panelP1.add(lblP1T15);
		lblP1T15.setIcon(pImg);
		
		lblP1T16 = new JLabel("");
		panelP1.add(lblP1T16);
		lblP1T16.setIcon(pImg);
		
		lblP1T17 = new JLabel("");
		panelP1.add(lblP1T17);
		lblP1T17.setIcon(pImg);
		
		lblP1T18 = new JLabel("");
		panelP1.add(lblP1T18);
		lblP1T18.setIcon(pImg);
		
		lblP1T19 = new JLabel("");
		panelP1.add(lblP1T19);
		lblP1T19.setIcon(pImg);
		
		lblP1T20 = new JLabel("");
		panelP1.add(lblP1T20);
		lblP1T20.setIcon(pImg);
		
		lblP1T21 = new JLabel("");
		panelP1.add(lblP1T21);
		lblP1T21.setIcon(pImg);
		
		lblP1T22 = new JLabel("");
		panelP1.add(lblP1T22);
		lblP1T22.setIcon(pImg);
		
		lblP1T23 = new JLabel("");
		panelP1.add(lblP1T23);
		lblP1T23.setIcon(pImg);
		
		lblP1T24 = new JLabel("");
		panelP1.add(lblP1T24);
		lblP1T24.setIcon(pImg);
		
		
		
		
		
		
		panelP2 = new JPanel();
		panelP2.setBounds(360, 191, POND_PANEL_HEIGHT, POND_PANEL_WIDTH);
		panelP2.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP2);
		panelP2.setLayout(new GridLayout(6, 4, 0, 0));
		
		lblP2T6 = new JLabel("");
		panelP2.add(lblP2T6);
		lblP2T6.setIcon(p2Img);
		
		lblP2T12 = new JLabel("");
		panelP2.add(lblP2T12);
		lblP2T12.setIcon(p2Img);
		
		lblP2T18 = new JLabel("");
		panelP2.add(lblP2T18);
		lblP2T18.setIcon(p2Img);
		
		lblP2T24 = new JLabel("");
		panelP2.add(lblP2T24);
		lblP2T24.setIcon(p2Img);
		
		lblP2T5 = new JLabel("");
		panelP2.add(lblP2T5);
		lblP2T5.setIcon(p2Img);
		
		lblP2T11 = new JLabel("");
		panelP2.add(lblP2T11);
		lblP2T11.setIcon(p2Img);
		
		lblP2T17 = new JLabel("");
		panelP2.add(lblP2T17);
		lblP2T17.setIcon(p2Img);
		
		lblP2T23 = new JLabel("");
		panelP2.add(lblP2T23);
		lblP2T23.setIcon(p2Img);
		
		lblP2T4 = new JLabel("");
		panelP2.add(lblP2T4);
		lblP2T4.setIcon(p2Img);
		
		lblP2T10 = new JLabel("");
		panelP2.add(lblP2T10);
		lblP2T10.setIcon(p2Img);
		
		lblP2T16 = new JLabel("");
		panelP2.add(lblP2T16);
		lblP2T16.setIcon(p2Img);
		
		lblP2T22 = new JLabel("");
		panelP2.add(lblP2T22);
		lblP2T22.setIcon(p2Img);
		
		lblP2T3 = new JLabel("");
		panelP2.add(lblP2T3);
		lblP2T3.setIcon(p2Img);
		
		lblP2T9 = new JLabel("");
		panelP2.add(lblP2T9);
		lblP2T9.setIcon(p2Img);
		
		lblP2T15 = new JLabel("");
		panelP2.add(lblP2T15);
		lblP2T15.setIcon(p2Img);
		
		lblP2T21 = new JLabel("");
		panelP2.add(lblP2T21);
		lblP2T21.setIcon(p2Img);
		
		lblP2T2 = new JLabel("");
		panelP2.add(lblP2T2);
		lblP2T2.setIcon(p2Img);
		
		lblP2T8 = new JLabel("");
		panelP2.add(lblP2T8);
		lblP2T8.setIcon(p2Img);
		
		lblP2T14 = new JLabel("");
		panelP2.add(lblP2T14);
		lblP2T14.setIcon(p2Img);
		
		lblP2T20 = new JLabel("");
		panelP2.add(lblP2T20);
		lblP2T20.setIcon(p2Img);
		
		lblP2T1 = new JLabel("");
		panelP2.add(lblP2T1);
		lblP2T1.setIcon(p2Img);
		
		lblP2T7 = new JLabel("");
		panelP2.add(lblP2T7);
		lblP2T7.setIcon(p2Img);
		
		lblP2T13 = new JLabel("");
		panelP2.add(lblP2T13);
		lblP2T13.setIcon(p2Img);
		
		lblP2T19 = new JLabel("");
		panelP2.add(lblP2T19);
		lblP2T19.setIcon(p2Img);
		
		
		
		
		
		
		panelP3 = new JPanel();
		panelP3.setBounds(196, 68, POND_PANEL_WIDTH, POND_PANEL_HEIGHT);
		panelP3.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP3);
		panelP3.setLayout(new GridLayout(4, 6, 0, 0));
		
		lblP3T24 = new JLabel("");
		panelP3.add(lblP3T24);
		lblP3T24.setIcon(p3Img);
		
		lblP3T23 = new JLabel("");
		panelP3.add(lblP3T23);
		lblP3T23.setIcon(p3Img);
		
		lblP3T22 = new JLabel("");
		panelP3.add(lblP3T22);
		lblP3T22.setIcon(p3Img);
		
		lblP3T21 = new JLabel("");
		panelP3.add(lblP3T21);
		lblP3T21.setIcon(p3Img);
		
		lblP3T20 = new JLabel("");
		panelP3.add(lblP3T20);
		lblP3T20.setIcon(p3Img);
		
		lblP3T19 = new JLabel("");
		panelP3.add(lblP3T19);
		lblP3T19.setIcon(p3Img);
		
		lblP3T18 = new JLabel("");
		panelP3.add(lblP3T18);
		lblP3T18.setIcon(p3Img);
		
		lblP3T17 = new JLabel("");
		panelP3.add(lblP3T17);
		lblP3T17.setIcon(p3Img);
		
		lblP3T16 = new JLabel("");
		panelP3.add(lblP3T16);
		lblP3T16.setIcon(p3Img);
		
		lblP3T15 = new JLabel("");
		panelP3.add(lblP3T15);
		lblP3T15.setIcon(p3Img);
		
		lblP3T14 = new JLabel("");
		panelP3.add(lblP3T14);
		lblP3T14.setIcon(p3Img);
		
		lblP3T13 = new JLabel("");
		panelP3.add(lblP3T13);
		lblP3T13.setIcon(p3Img);
		
		lblP3T12 = new JLabel("");
		panelP3.add(lblP3T12);
		lblP3T12.setIcon(p3Img);
		
		lblP3T11 = new JLabel("");
		panelP3.add(lblP3T11);
		lblP3T11.setIcon(p3Img);
		
		lblP3T10 = new JLabel("");
		panelP3.add(lblP3T10);
		lblP3T10.setIcon(p3Img);
		
		lblP3T9 = new JLabel("");
		panelP3.add(lblP3T9);
		lblP3T9.setIcon(p3Img);
		
		lblP3T8 = new JLabel("");
		panelP3.add(lblP3T8);
		lblP3T8.setIcon(p3Img);
		
		lblP3T7 = new JLabel("");
		panelP3.add(lblP3T7);
		lblP3T7.setIcon(p3Img);
		
		lblP3T6 = new JLabel("");
		panelP3.add(lblP3T6);
		lblP3T6.setIcon(p3Img);
		
		lblP3T5 = new JLabel("");
		panelP3.add(lblP3T5);
		lblP3T5.setIcon(p3Img);
		
		lblP3T4 = new JLabel("");
		panelP3.add(lblP3T4);
		lblP3T4.setIcon(p3Img);
		
		lblP3T3 = new JLabel("");
		panelP3.add(lblP3T3);
		lblP3T3.setIcon(p3Img);
		
		lblP3T2 = new JLabel("");
		panelP3.add(lblP3T2);
		lblP3T2.setIcon(p3Img);
		
		lblP3T1 = new JLabel("");
		panelP3.add(lblP3T1);
		lblP3T1.setIcon(p3Img);
		
		
		
		
		
		
		panelP4 = new JPanel();
		panelP4.setBounds(68, 191, POND_PANEL_HEIGHT, POND_PANEL_WIDTH);
		panelP4.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP4);
		panelP4.setLayout(new GridLayout(6, 4, 0, 0));
		
		lblP4T19 = new JLabel("");
		panelP4.add(lblP4T19);
		lblP4T19.setIcon(p4Img);
		
		lblP4T13 = new JLabel("");
		panelP4.add(lblP4T13);
		lblP4T13.setIcon(p4Img);
		
		lblP4T7 = new JLabel("");
		panelP4.add(lblP4T7);
		lblP4T7.setIcon(p4Img);
		
		lblP4T1 = new JLabel("");
		panelP4.add(lblP4T1);
		lblP4T1.setIcon(p4Img);
		
		lblP4T20 = new JLabel("");
		panelP4.add(lblP4T20);
		lblP4T20.setIcon(p4Img);
		
		lblP4T14 = new JLabel("");
		panelP4.add(lblP4T14);
		lblP4T14.setIcon(p4Img);
		
		lblP4T8 = new JLabel("");
		panelP4.add(lblP4T8);
		lblP4T8.setIcon(p4Img);
		
		lblP4T2 = new JLabel("");
		panelP4.add(lblP4T2);
		lblP4T2.setIcon(p4Img);
		
		lblP4T21 = new JLabel("");
		panelP4.add(lblP4T21);
		lblP4T21.setIcon(p4Img);
		
		lblP4T15 = new JLabel("");
		panelP4.add(lblP4T15);
		lblP4T15.setIcon(p4Img);
		
		lblP4T9 = new JLabel("");
		panelP4.add(lblP4T9);
		lblP4T9.setIcon(p4Img);
		
		lblP4T3 = new JLabel("");
		panelP4.add(lblP4T3);
		lblP4T3.setIcon(p4Img);
		
		lblP4T22 = new JLabel("");
		panelP4.add(lblP4T22);
		lblP4T22.setIcon(p4Img);
		
		lblP4T16 = new JLabel("");
		panelP4.add(lblP4T16);
		lblP4T16.setIcon(p4Img);
		
		lblP4T10 = new JLabel("");
		panelP4.add(lblP4T10);
		lblP4T10.setIcon(p4Img);
		
		lblP4T4 = new JLabel("");
		panelP4.add(lblP4T4);
		lblP4T4.setIcon(p4Img);
		
		lblP4T23 = new JLabel("");
		panelP4.add(lblP4T23);
		lblP4T23.setIcon(p4Img);
		
		lblP4T17 = new JLabel("");
		panelP4.add(lblP4T17);
		lblP4T17.setIcon(p4Img);
		
		lblP4T11 = new JLabel("");
		panelP4.add(lblP4T11);
		lblP4T11.setIcon(p4Img);
		
		lblP4T5 = new JLabel("");
		panelP4.add(lblP4T5);
		lblP4T5.setIcon(p4Img);
		
		lblP4T24 = new JLabel("");
		panelP4.add(lblP4T24);
		lblP4T24.setIcon(p4Img);
		
		lblP4T18 = new JLabel("");
		panelP4.add(lblP4T18);
		lblP4T18.setIcon(p4Img);
		
		lblP4T12 = new JLabel("");
		panelP4.add(lblP4T12);
		lblP4T12.setIcon(p4Img);
		
		lblP4T6 = new JLabel("");
		panelP4.add(lblP4T6);
		lblP4T6.setIcon(p4Img);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		panelW1 = new JPanel();
//		panelW1.setBounds(71, 528, TILE_SMALL_WIDTH*(SIZE_WALL/2)+17, TILE_SMALL_HEIGHT*2+2);
		panelW1.setBounds(81, 482, TILE_SMALL_WIDTH*(SIZE_WALL/2), TILE_SMALL_HEIGHT*2);
		panelW1.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelW1);
		panelW1.setLayout(new GridLayout(2, 17, 0, 0));
		
		lblW1T33 = new JLabel("");
		panelW1.add(lblW1T33);
		lblW1T33.setIcon(wImg);
		
		lblW1T31 = new JLabel("");
		panelW1.add(lblW1T31);
		lblW1T31.setIcon(wImg);
		
		lblW1T29 = new JLabel("");
		panelW1.add(lblW1T29);
		lblW1T29.setIcon(wImg);
		
		lblW1T27 = new JLabel("");
		panelW1.add(lblW1T27);
		lblW1T27.setIcon(wImg);
		
		lblW1T25 = new JLabel("");
		panelW1.add(lblW1T25);
		lblW1T25.setIcon(wImg);
		
		lblW1T23 = new JLabel("");
		panelW1.add(lblW1T23);
		lblW1T23.setIcon(wImg);
		
		lblW1T21 = new JLabel("");
		panelW1.add(lblW1T21);
		lblW1T21.setIcon(wImg);
		
		lblW1T19 = new JLabel("");
		panelW1.add(lblW1T19);
		lblW1T19.setIcon(wImg);
		
		lblW1T17 = new JLabel("");
		panelW1.add(lblW1T17);
		lblW1T17.setIcon(wImg);
		
		lblW1T15 = new JLabel("");
		panelW1.add(lblW1T15);
		lblW1T15.setIcon(wImg);
		
		lblW1T13 = new JLabel("");
		panelW1.add(lblW1T13);
		lblW1T13.setIcon(wImg);
		
		lblW1T11 = new JLabel("");
		panelW1.add(lblW1T11);
		lblW1T11.setIcon(wImg);
		
		lblW1T9 = new JLabel("");
		panelW1.add(lblW1T9);
		lblW1T9.setIcon(wImg);
		
		lblW1T7 = new JLabel("");
		panelW1.add(lblW1T7);
		lblW1T7.setIcon(wImg);
		
		lblW1T5 = new JLabel("");
		panelW1.add(lblW1T5);
		lblW1T5.setIcon(wImg);
		
		lblW1T3 = new JLabel("");
		panelW1.add(lblW1T3);
		lblW1T3.setIcon(wImg);
		
		lblW1T1 = new JLabel("");
		panelW1.add(lblW1T1);
		lblW1T1.setIcon(wImg);
		
		lblW1T34 = new JLabel("");
		panelW1.add(lblW1T34);
		lblW1T34.setIcon(wImg);
		
		lblW1T32 = new JLabel("");
		panelW1.add(lblW1T32);
		lblW1T32.setIcon(wImg);
		
		lblW1T30 = new JLabel("");
		panelW1.add(lblW1T30);
		lblW1T30.setIcon(wImg);
		
		lblW1T28 = new JLabel("");
		panelW1.add(lblW1T28);
		lblW1T28.setIcon(wImg);
		
		lblW1T26 = new JLabel("");
		panelW1.add(lblW1T26);
		lblW1T26.setIcon(wImg);
		
		lblW1T24 = new JLabel("");
		panelW1.add(lblW1T24);
		lblW1T24.setIcon(wImg);
		
		lblW1T22 = new JLabel("");
		panelW1.add(lblW1T22);
		lblW1T22.setIcon(wImg);
		
		lblW1T20 = new JLabel("");
		panelW1.add(lblW1T20);
		lblW1T20.setIcon(wImg);
		
		lblW1T18 = new JLabel("");
		panelW1.add(lblW1T18);
		lblW1T18.setIcon(wImg);
		
		lblW1T16 = new JLabel("");
		panelW1.add(lblW1T16);
		lblW1T16.setIcon(wImg);
		
		lblW1T14 = new JLabel("");
		panelW1.add(lblW1T14);
		lblW1T14.setIcon(wImg);
		
		lblW1T12 = new JLabel("");
		panelW1.add(lblW1T12);
		lblW1T12.setIcon(wImg);
		
		lblW1T10 = new JLabel("");
		panelW1.add(lblW1T10);
		lblW1T10.setIcon(wImg);
		
		lblW1T8 = new JLabel("");
		panelW1.add(lblW1T8);
		lblW1T8.setIcon(wImg);
		
		lblW1T6 = new JLabel("");
		panelW1.add(lblW1T6);
		lblW1T6.setIcon(wImg);
		
		lblW1T4 = new JLabel("");
		panelW1.add(lblW1T4);
		lblW1T4.setIcon(wImg);
		
		lblW1T2 = new JLabel("");
		panelW1.add(lblW1T2);
		lblW1T2.setIcon(wImg);
		
		
		
		
		
		panelW2 = new JPanel();
		panelW2.setBounds(490, 76, TILE_SMALL_HEIGHT*2, TILE_SMALL_WIDTH*(SIZE_WALL/2));
		panelW2.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelW2);
		panelW2.setLayout(new GridLayout(17, 2, 0, 0));
		
		lblW2T1 = new JLabel("");
		panelW2.add(lblW2T1);
		lblW2T1.setIcon(w2Img);
		
		lblW2T2 = new JLabel("");
		panelW2.add(lblW2T2);
		lblW2T2.setIcon(w2Img);
		
		lblW2T3 = new JLabel("");
		panelW2.add(lblW2T3);
		lblW2T3.setIcon(w2Img);
		
		lblW2T4 = new JLabel("");
		panelW2.add(lblW2T4);
		lblW2T4.setIcon(w2Img);
		
		lblW2T5 = new JLabel("");
		panelW2.add(lblW2T5);
		lblW2T5.setIcon(w2Img);
		
		lblW2T6 = new JLabel("");
		panelW2.add(lblW2T6);
		lblW2T6.setIcon(w2Img);
		
		lblW2T7 = new JLabel("");
		panelW2.add(lblW2T7);
		lblW2T7.setIcon(w2Img);
		
		lblW2T8 = new JLabel("");
		panelW2.add(lblW2T8);
		lblW2T8.setIcon(w2Img);
		
		lblW2T9 = new JLabel("");
		panelW2.add(lblW2T9);
		lblW2T9.setIcon(w2Img);
		
		lblW2T10 = new JLabel("");
		panelW2.add(lblW2T10);
		lblW2T10.setIcon(w2Img);
		
		lblW2T11 = new JLabel("");
		panelW2.add(lblW2T11);
		lblW2T11.setIcon(w2Img);
		
		lblW2T12 = new JLabel("");
		panelW2.add(lblW2T12);
		lblW2T12.setIcon(w2Img);
		
		lblW2T13 = new JLabel("");
		panelW2.add(lblW2T13);
		lblW2T13.setIcon(w2Img);
		
		lblW2T14 = new JLabel("");
		panelW2.add(lblW2T14);
		lblW2T14.setIcon(w2Img);
		
		lblW2T15 = new JLabel("");
		panelW2.add(lblW2T15);
		lblW2T15.setIcon(w2Img);
		
		lblW2T16 = new JLabel("");
		panelW2.add(lblW2T16);
		lblW2T16.setIcon(w2Img);
		
		lblW2T17 = new JLabel("");
		panelW2.add(lblW2T17);
		lblW2T17.setIcon(w2Img);
		
		lblW2T18 = new JLabel("");
		panelW2.add(lblW2T18);
		lblW2T18.setIcon(w2Img);
		
		lblW2T19 = new JLabel("");
		panelW2.add(lblW2T19);
		lblW2T19.setIcon(w2Img);
		
		lblW2T20 = new JLabel("");
		panelW2.add(lblW2T20);
		lblW2T20.setIcon(w2Img);
		
		lblW2T21 = new JLabel("");
		panelW2.add(lblW2T21);
		lblW2T21.setIcon(w2Img);
		
		lblW2T22 = new JLabel("");
		panelW2.add(lblW2T22);
		lblW2T22.setIcon(w2Img);
		
		lblW2T23 = new JLabel("");
		panelW2.add(lblW2T23);
		lblW2T23.setIcon(w2Img);
		
		lblW2T24 = new JLabel("");
		panelW2.add(lblW2T24);
		lblW2T24.setIcon(w2Img);
		
		lblW2T25 = new JLabel("");
		panelW2.add(lblW2T25);
		lblW2T25.setIcon(w2Img);
		
		lblW2T26 = new JLabel("");
		panelW2.add(lblW2T26);
		lblW2T26.setIcon(w2Img);
		
		lblW2T27 = new JLabel("");
		panelW2.add(lblW2T27);
		lblW2T27.setIcon(w2Img);
		
		lblW2T28 = new JLabel("");
		panelW2.add(lblW2T28);
		lblW2T28.setIcon(w2Img);
		
		lblW2T29 = new JLabel("");
		panelW2.add(lblW2T29);
		lblW2T29.setIcon(w2Img);
		
		lblW2T30 = new JLabel("");
		panelW2.add(lblW2T30);
		lblW2T30.setIcon(w2Img);
		
		lblW2T31 = new JLabel("");
		panelW2.add(lblW2T31);
		lblW2T31.setIcon(w2Img);
		
		lblW2T32 = new JLabel("");
		panelW2.add(lblW2T32);
		lblW2T32.setIcon(w2Img);
		
		lblW2T33 = new JLabel("");
		panelW2.add(lblW2T33);
		lblW2T33.setIcon(w2Img);
		
		lblW2T34 = new JLabel("");
		panelW2.add(lblW2T34);
		lblW2T34.setIcon(w2Img);
		
		
		
		
		
		
		panelW3 = new JPanel();
		panelW3.setBounds(81, 0, TILE_SMALL_WIDTH*(SIZE_WALL/2), TILE_SMALL_HEIGHT*2);
		panelW3.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelW3);
		panelW3.setLayout(new GridLayout(2, 17, 0, 0));
		
		lblW3T2 = new JLabel("");
		panelW3.add(lblW3T2);
		lblW3T2.setIcon(w3Img);
		
		lblW3T4 = new JLabel("");
		panelW3.add(lblW3T4);
		lblW3T4.setIcon(w3Img);
		
		lblW3T6 = new JLabel("");
		panelW3.add(lblW3T6);
		lblW3T6.setIcon(w3Img);
		
		lblW3T8 = new JLabel("");
		panelW3.add(lblW3T8);
		lblW3T8.setIcon(w3Img);
		
		lblW3T10 = new JLabel("");
		panelW3.add(lblW3T10);
		lblW3T10.setIcon(w3Img);
		
		lblW3T12 = new JLabel("");
		panelW3.add(lblW3T12);
		lblW3T12.setIcon(w3Img);
		
		lblW3T14 = new JLabel("");
		panelW3.add(lblW3T14);
		lblW3T14.setIcon(w3Img);
		
		lblW3T16 = new JLabel("");
		panelW3.add(lblW3T16);
		lblW3T16.setIcon(w3Img);
		
		lblW3T18 = new JLabel("");
		panelW3.add(lblW3T18);
		lblW3T18.setIcon(w3Img);
		
		lblW3T20 = new JLabel("");
		panelW3.add(lblW3T20);
		lblW3T20.setIcon(w3Img);
		
		lblW3T22 = new JLabel("");
		panelW3.add(lblW3T22);
		lblW3T22.setIcon(w3Img);
		
		lblW3T24 = new JLabel("");
		panelW3.add(lblW3T24);
		lblW3T24.setIcon(w3Img);
		
		lblW3T26 = new JLabel("");
		panelW3.add(lblW3T26);
		lblW3T26.setIcon(w3Img);
		
		lblW3T28 = new JLabel("");
		panelW3.add(lblW3T28);
		lblW3T28.setIcon(w3Img);
		
		lblW3T30 = new JLabel("");
		panelW3.add(lblW3T30);
		lblW3T30.setIcon(w3Img);
		
		lblW3T32 = new JLabel("");
		panelW3.add(lblW3T32);
		lblW3T32.setIcon(w3Img);
		
		lblW3T34 = new JLabel("");
		panelW3.add(lblW3T34);
		lblW3T34.setIcon(w3Img);
		
		lblW3T1 = new JLabel("");
		panelW3.add(lblW3T1);
		lblW3T1.setIcon(w3Img);
		
		lblW3T3 = new JLabel("");
		panelW3.add(lblW3T3);
		lblW3T3.setIcon(w3Img);
		
		lblW3T5 = new JLabel("");
		panelW3.add(lblW3T5);
		lblW3T5.setIcon(w3Img);
		
		lblW3T7 = new JLabel("");
		panelW3.add(lblW3T7);
		lblW3T7.setIcon(w3Img);
		
		lblW3T9 = new JLabel("");
		panelW3.add(lblW3T9);
		lblW3T9.setIcon(w3Img);
		
		lblW3T11 = new JLabel("");
		panelW3.add(lblW3T11);
		lblW3T11.setIcon(w3Img);
		
		lblW3T13 = new JLabel("");
		panelW3.add(lblW3T13);
		lblW3T13.setIcon(w3Img);
		
		lblW3T15 = new JLabel("");
		panelW3.add(lblW3T15);
		lblW3T15.setIcon(w3Img);
		
		lblW3T17 = new JLabel("");
		panelW3.add(lblW3T17);
		lblW3T17.setIcon(w3Img);
		
		lblW3T19 = new JLabel("");
		panelW3.add(lblW3T19);
		lblW3T19.setIcon(w3Img);
		
		lblW3T21 = new JLabel("");
		panelW3.add(lblW3T21);
		lblW3T21.setIcon(w3Img);
		
		lblW3T23 = new JLabel("");
		panelW3.add(lblW3T23);
		lblW3T23.setIcon(w3Img);
		
		lblW3T25 = new JLabel("");
		panelW3.add(lblW3T25);
		lblW3T25.setIcon(w3Img);
		
		lblW3T27 = new JLabel("");
		panelW3.add(lblW3T27);
		lblW3T27.setIcon(w3Img);
		
		lblW3T29 = new JLabel("");
		panelW3.add(lblW3T29);
		lblW3T29.setIcon(w3Img);
		
		lblW3T31 = new JLabel("");
		panelW3.add(lblW3T31);
		lblW3T31.setIcon(w3Img);
		
		lblW3T33 = new JLabel("");
		panelW3.add(lblW3T33);
		lblW3T33.setIcon(w3Img);
		
		
		
		
		
		
		panelW4 = new JPanel();
		panelW4.setBounds(0, 76, TILE_SMALL_HEIGHT*2, TILE_SMALL_WIDTH*(SIZE_WALL/2));
		panelW4.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelW4);
		panelW4.setLayout(new GridLayout(17, 2, 0, 0));
		
		lblW4T34 = new JLabel("");
		panelW4.add(lblW4T34);
		lblW4T34.setIcon(w4Img);
		
		lblW4T33 = new JLabel("");
		panelW4.add(lblW4T33);
		lblW4T33.setIcon(w4Img);
		
		lblW4T32 = new JLabel("");
		panelW4.add(lblW4T32);
		lblW4T32.setIcon(w4Img);
		
		lblW4T31 = new JLabel("");
		panelW4.add(lblW4T31);
		lblW4T31.setIcon(w4Img);
		
		lblW4T30 = new JLabel("");
		panelW4.add(lblW4T30);
		lblW4T30.setIcon(w4Img);
		
		lblW4T29 = new JLabel("");
		panelW4.add(lblW4T29);
		lblW4T29.setIcon(w4Img);
		
		lblW4T28 = new JLabel("");
		panelW4.add(lblW4T28);
		lblW4T28.setIcon(w4Img);
		
		lblW4T27 = new JLabel("");
		panelW4.add(lblW4T27);
		lblW4T27.setIcon(w4Img);
		
		lblW4T26 = new JLabel("");
		panelW4.add(lblW4T26);
		lblW4T26.setIcon(w4Img);
		
		lblW4T25 = new JLabel("");
		panelW4.add(lblW4T25);
		lblW4T25.setIcon(w4Img);
		
		lblW4T24 = new JLabel("");
		panelW4.add(lblW4T24);
		lblW4T24.setIcon(w4Img);
		
		lblW4T23 = new JLabel("");
		panelW4.add(lblW4T23);
		lblW4T23.setIcon(w4Img);
		
		lblW4T22 = new JLabel("");
		panelW4.add(lblW4T22);
		lblW4T22.setIcon(w4Img);
		
		lblW4T21 = new JLabel("");
		panelW4.add(lblW4T21);
		lblW4T21.setIcon(w4Img);
		
		lblW4T20 = new JLabel("");
		panelW4.add(lblW4T20);
		lblW4T20.setIcon(w4Img);
		
		lblW4T19 = new JLabel("");
		panelW4.add(lblW4T19);
		lblW4T19.setIcon(w4Img);
		
		lblW4T18 = new JLabel("");
		panelW4.add(lblW4T18);
		lblW4T18.setIcon(w4Img);
		
		lblW4T17 = new JLabel("");
		panelW4.add(lblW4T17);
		lblW4T17.setIcon(w4Img);
		
		lblW4T16 = new JLabel("");
		panelW4.add(lblW4T16);
		lblW4T16.setIcon(w4Img);
		
		lblW4T15 = new JLabel("");
		panelW4.add(lblW4T15);
		lblW4T15.setIcon(w4Img);
		
		lblW4T14 = new JLabel("");
		panelW4.add(lblW4T14);
		lblW4T14.setIcon(w4Img);
		
		lblW4T13 = new JLabel("");
		panelW4.add(lblW4T13);
		lblW4T13.setIcon(w4Img);
		
		lblW4T12 = new JLabel("");
		panelW4.add(lblW4T12);
		lblW4T12.setIcon(w4Img);
		
		lblW4T11 = new JLabel("");
		panelW4.add(lblW4T11);
		lblW4T11.setIcon(w4Img);
		
		lblW4T10 = new JLabel("");
		panelW4.add(lblW4T10);
		lblW4T10.setIcon(w4Img);
		
		lblW4T9 = new JLabel("");
		panelW4.add(lblW4T9);
		lblW4T9.setIcon(w4Img);
		
		lblW4T8 = new JLabel("");
		panelW4.add(lblW4T8);
		lblW4T8.setIcon(w4Img);
		
		lblW4T7 = new JLabel("");
		panelW4.add(lblW4T7);
		lblW4T7.setIcon(w4Img);
		
		lblW4T6 = new JLabel("");
		panelW4.add(lblW4T6);
		lblW4T6.setIcon(w4Img);
		
		lblW4T5 = new JLabel("");
		panelW4.add(lblW4T5);
		lblW4T5.setIcon(w4Img);
		
		lblW4T4 = new JLabel("");
		panelW4.add(lblW4T4);
		lblW4T4.setIcon(w4Img);
		
		lblW4T3 = new JLabel("");
		panelW4.add(lblW4T3);
		lblW4T3.setIcon(w4Img);
		
		lblW4T2 = new JLabel("");
		panelW4.add(lblW4T2);
		lblW4T2.setIcon(w4Img);
		
		lblW4T1 = new JLabel("");
		panelW4.add(lblW4T1);
		lblW4T1.setIcon(w4Img);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		panelRoundInfo = new JPanel();
		panelRoundInfo.setBounds(193, 193, 166, 158);
		panelMidTable.add(panelRoundInfo);
		panelRoundInfo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelRoundInfo.setBackground(new Color(0,255,255,35));
		panelRoundInfo.setLayout(null);
		
		
		
		
		panelRInd = new JPanel();
		panelRInd.setBounds(54, 54, 58, 49);
		panelRoundInfo.add(panelRInd);
		panelRInd.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelRInd.setBackground(new Color(0,155,155,35));
		panelRInd.setLayout(null);
		
		lblRIndWind = new JLabel("");
		lblRIndWind.setBounds(3, 9, 31, 31);
		panelRInd.add(lblRIndWind);
		lblRIndWind.setIcon(windRImg);
		
		lblRIndNum = new JLabel("4");
		lblRIndNum.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblRIndNum.setHorizontalAlignment(SwingConstants.LEFT);
		lblRIndNum.setVerticalAlignment(SwingConstants.TOP);
		lblRIndNum.setBounds(33, 6, 16, 37);
		panelRInd.add(lblRIndNum);
		
		
		
		
		
		
		panelInfoP1 = new JPanel();
		panelInfoP1.setBounds(56, 103, 54, 54);
		panelRoundInfo.add(panelInfoP1);
		panelInfoP1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP1.setBackground(new Color(0, 255, 255, 35));
		panelInfoP1.setLayout(null);
		
		lblInfoP1Wind = new JLabel("");
		lblInfoP1Wind.setBounds(16, 2, 23, 23);
		lblInfoP1Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP1Wind.setIcon(wind1Img);
		panelInfoP1.add(lblInfoP1Wind);
		
		lblInfoP1Points = new JLabel("128,000");
		lblInfoP1Points.setBounds(4, 25, 46, 14);
		lblInfoP1Points.setBackground(new Color(0,0,0,0));
		lblInfoP1Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP1.add(lblInfoP1Points);
		
		lblInfoP1Riichi = new JLabel("");
		lblInfoP1Riichi.setBounds(2, 40, 50, 8);
		lblInfoP1Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP1Riichi.setIcon(riichiImg);
		panelInfoP1.add(lblInfoP1Riichi);
		
		
		
		
		
		panelInfoP2 = new JPanel();
		panelInfoP2.setBounds(112, 52, 54, 54);
		panelRoundInfo.add(panelInfoP2);
		panelInfoP2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP2.setBackground(new Color(0, 255, 255, 35));
		panelInfoP2.setLayout(null);
		
		lblInfoP2Wind = new JLabel("");
		lblInfoP2Wind.setBounds(16, 2, 23, 23);
		lblInfoP2Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP2Wind.setIcon(wind2Img);
		panelInfoP2.add(lblInfoP2Wind);
		
		lblInfoP2Points = new JLabel("128,000");
		lblInfoP2Points.setBounds(4, 25, 46, 14);
		lblInfoP2Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP2Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP2.add(lblInfoP2Points);
		
		lblInfoP2Riichi = new JLabel("");
		lblInfoP2Riichi.setBounds(2, 40, 50, 8);
		lblInfoP2Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP2Riichi.setIcon(riichiImg);
		panelInfoP2.add(lblInfoP2Riichi);
		
		
		
		
		
		panelInfoP3 = new JPanel();
		panelInfoP3.setBounds(56, 0, 54, 54);
		panelRoundInfo.add(panelInfoP3);
		panelInfoP3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP3.setBackground(new Color(0, 255, 255, 35));
		panelInfoP3.setLayout(null);
		
		lblInfoP3Wind = new JLabel("");
		lblInfoP3Wind.setBounds(16, 2, 23, 23);
		lblInfoP3Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP3Wind.setIcon(wind3Img);
		panelInfoP3.add(lblInfoP3Wind);
		
		lblInfoP3Points = new JLabel("128,000");
		lblInfoP3Points.setBounds(4, 25, 46, 14);
		lblInfoP3Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP3Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP3.add(lblInfoP3Points);
		
		lblInfoP3Riichi = new JLabel("");
		lblInfoP3Riichi.setBounds(2, 40, 50, 8);
		lblInfoP3Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP3Riichi.setIcon(riichiImg);
		panelInfoP3.add(lblInfoP3Riichi);
		
		
		
		
		
		panelInfoP4 = new JPanel();
		panelInfoP4.setBounds(0, 52, 54, 54);
		panelRoundInfo.add(panelInfoP4);
		panelInfoP4.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP4.setBackground(new Color(0, 255, 255, 35));
		panelInfoP4.setLayout(null);
		
		lblInfoP4Wind = new JLabel("");
		lblInfoP4Wind.setBounds(16, 2, 23, 23);
		lblInfoP4Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP4Wind.setIcon(wind4Img);
		panelInfoP4.add(lblInfoP4Wind);
		
		lblInfoP4Points = new JLabel("128,000");
		lblInfoP4Points.setBounds(4, 25, 46, 14);
		lblInfoP4Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP4Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP4.add(lblInfoP4Points);
		
		lblInfoP4Riichi = new JLabel("");
		lblInfoP4Riichi.setBounds(2, 40, 50, 8);
		lblInfoP4Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP4Riichi.setIcon(riichiImg);
		panelInfoP4.add(lblInfoP4Riichi);
		
		//exists only to color the background behind the round info
		panelRndInfBackground = new JPanel();
		panelRndInfBackground.setBounds(193, 193, 166, 158);
		panelMidTable.add(panelRndInfBackground);
		panelRndInfBackground.setLayout(null);
		
		
		
		
		
		
		
		panelPlayer1 = new JPanel();
		panelPlayer1.setBounds(185, 732, 667, 78);
		panelPlayer1.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer1);
		panelPlayer1.setLayout(null);
		
		panelH1 = new JPanel();
		panelH1.setBounds(42, 0, TILE_BIG_WIDTH*SIZE_HAND, TILE_BIG_HEIGHT);
		panelH1.setBackground(COLOR_TRANSPARENT);
		panelPlayer1.add(panelH1);
		panelH1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		lblH1T1 = new JLabel("");
		lblH1T1.setIcon(hImg);
		panelH1.add(lblH1T1);
		
		lblH1T2 = new JLabel("");
		lblH1T2.setIcon(hImg);
		panelH1.add(lblH1T2);
		
		lblH1T3 = new JLabel("");
		lblH1T3.setIcon(hImg);
		panelH1.add(lblH1T3);
		
		lblH1T4 = new JLabel("");
		lblH1T4.setIcon(hImg);
		panelH1.add(lblH1T4);
		
		lblH1T5 = new JLabel("");
		lblH1T5.setIcon(hImg);
		panelH1.add(lblH1T5);
		
		lblH1T6 = new JLabel("");
		lblH1T6.setIcon(hImg);
		panelH1.add(lblH1T6);
		
		lblH1T7 = new JLabel("");
		lblH1T7.setIcon(hImg);
		panelH1.add(lblH1T7);
		
		lblH1T8 = new JLabel("");
		lblH1T8.setIcon(hImg);
		panelH1.add(lblH1T8);
		
		lblH1T9 = new JLabel("");
		lblH1T9.setIcon(hImg);
		panelH1.add(lblH1T9);
		
		lblH1T10 = new JLabel("");
		lblH1T10.setIcon(hImg);
		panelH1.add(lblH1T10);
		
		lblH1T11 = new JLabel("");
		lblH1T11.setIcon(hImg);
		panelH1.add(lblH1T11);
		
		lblH1T12 = new JLabel("");
		lblH1T12.setIcon(hImg);
		panelH1.add(lblH1T12);
		
		lblH1T13 = new JLabel("");
		lblH1T13.setIcon(hImg);
		panelH1.add(lblH1T13);
		
		lblH1T14 = new JLabel("");
		lblH1T14.setIcon(hImg);
		panelH1.add(lblH1T14);
		
		
		
		
		
		
		panelH1Ms = new JPanel();
		panelH1Ms.setBounds(192, 47, 474, 31);
		panelH1Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer1.add(panelH1Ms);
		panelH1Ms.setLayout(null);
		
		
		
		
		panelH1M4 = new JPanel();
		panelH1M4.setBounds(0, 0, 108, 31);
		panelH1M4.setBackground(COLOR_TRANSPARENT);
		panelH1M4.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH1M4T1 = new JLabel("");
		panelH1M4.add(lblH1M4T1);
		lblH1M4T1.setIcon(meldImg);
		
		lblH1M4T2 = new JLabel("");
		panelH1M4.add(lblH1M4T2);
		lblH1M4T2.setIcon(meldImg);
		
		lblH1M4T3 = new JLabel("");
		panelH1M4.add(lblH1M4T3);
		lblH1M4T3.setIcon(meldImg);
		
		lblH1M4T4 = new JLabel("");
		panelH1M4.add(lblH1M4T4);
		lblH1M4T4.setIcon(meldImg);
		panelH1Ms.add(panelH1M4);
		
		
		
		panelH1M3 = new JPanel();
		panelH1M3.setBounds(120, 0, 108, 31);
		panelH1M3.setBackground(COLOR_TRANSPARENT);
		panelH1Ms.add(panelH1M3);
		panelH1M3.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH1M3T1 = new JLabel("");
		panelH1M3.add(lblH1M3T1);
		lblH1M3T1.setIcon(meldImg);
		
		lblH1M3T2 = new JLabel("");
		panelH1M3.add(lblH1M3T2);
		lblH1M3T2.setIcon(meldImg);
		
		lblH1M3T3 = new JLabel("");
		panelH1M3.add(lblH1M3T3);
		lblH1M3T3.setIcon(meldImg);
		
		lblH1M3T4 = new JLabel("");
		panelH1M3.add(lblH1M3T4);
		lblH1M3T4.setIcon(meldImg);
		
		
		
		panelH1M2 = new JPanel();
		panelH1M2.setBounds(240, 0, 108, 31);
		panelH1M2.setBackground(COLOR_TRANSPARENT);
		panelH1Ms.add(panelH1M2);
		panelH1M2.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH1M2T1 = new JLabel("");
		panelH1M2.add(lblH1M2T1);
		lblH1M2T1.setIcon(meldImg);
		
		lblH1M2T2 = new JLabel("");
		panelH1M2.add(lblH1M2T2);
		lblH1M2T2.setIcon(meldImg);
		
		lblH1M2T3 = new JLabel("");
		panelH1M2.add(lblH1M2T3);
		lblH1M2T3.setIcon(meldImg);
		
		lblH1M2T4 = new JLabel("");
		panelH1M2.add(lblH1M2T4);
		lblH1M2T4.setIcon(meldImg);
		
		
		panelH1M1 = new JPanel();
		panelH1M1.setBounds(360, 0, 108, 31);
		panelH1M1.setBackground(COLOR_TRANSPARENT);
		panelH1Ms.add(panelH1M1);
		panelH1M1.setLayout(new GridLayout(1, 4, 0, 0));
		
		
		lblH1M1T1 = new JLabel("");
		panelH1M1.add(lblH1M1T1);
		lblH1M1T1.setIcon(meldImg);
		
		lblH1M1T2 = new JLabel("");
		panelH1M1.add(lblH1M1T2);
		lblH1M1T2.setIcon(meldImg);
		
		lblH1M1T3 = new JLabel("");
		panelH1M1.add(lblH1M1T3);
		lblH1M1T3.setIcon(meldImg);
		
		lblH1M1T4 = new JLabel("");
		panelH1M1.add(lblH1M1T4);
		lblH1M1T4.setIcon(meldImg);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		panelPlayer2 = new JPanel();
		panelPlayer2.setBounds(748, 9, 78, 667);
		panelPlayer2.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer2);
		panelPlayer2.setLayout(null);
		
		panelH2 = new JPanel();
		panelH2.setBounds(0, 203, TILE_BIG_HEIGHT, TILE_BIG_WIDTH*SIZE_HAND);
		panelH2.setBackground(COLOR_TRANSPARENT);
		panelPlayer2.add(panelH2);
		panelH2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		lblH2T14 = new JLabel("");
		lblH2T14.setIcon(h2Img);
		panelH2.add(lblH2T14);
		larryH2[13] = lblH2T14;
		
		lblH2T13 = new JLabel("");
		lblH2T13.setIcon(h2Img);
		panelH2.add(lblH2T13);
		larryH2[12] = lblH2T13;
		
		lblH2T12 = new JLabel("");
		lblH2T12.setIcon(h2Img);
		panelH2.add(lblH2T12);
		larryH2[11] = lblH2T12;
		
		lblH2T11 = new JLabel("");
		lblH2T11.setIcon(h2Img);
		panelH2.add(lblH2T11);
		larryH2[10] = lblH2T11;
		
		lblH2T10 = new JLabel("");
		lblH2T10.setIcon(h2Img);
		panelH2.add(lblH2T10);
		larryH2[9] = lblH2T10;
		
		lblH2T9 = new JLabel("");
		lblH2T9.setIcon(h2Img);
		panelH2.add(lblH2T9);
		larryH2[8] = lblH2T9;
		
		lblH2T8 = new JLabel("");
		lblH2T8.setIcon(h2Img);
		panelH2.add(lblH2T8);
		larryH2[7] = lblH2T8;
		
		lblH2T7 = new JLabel("");
		lblH2T7.setIcon(h2Img);
		panelH2.add(lblH2T7);
		larryH2[6] = lblH2T7;
		
		lblH2T6 = new JLabel("");
		lblH2T6.setIcon(h2Img);
		panelH2.add(lblH2T6);
		larryH2[5] = lblH2T6;
		
		lblH2T5 = new JLabel("");
		lblH2T5.setIcon(h2Img);
		panelH2.add(lblH2T5);
		larryH2[4] = lblH2T5;
		
		lblH2T4 = new JLabel("");
		lblH2T4.setIcon(h2Img);
		panelH2.add(lblH2T4);
		larryH2[3] = lblH2T4;
		
		lblH2T3 = new JLabel("");
		lblH2T3.setIcon(h2Img);
		panelH2.add(lblH2T3);
		larryH2[2] = lblH2T3;
		
		lblH2T2 = new JLabel("");
		lblH2T2.setIcon(h2Img);
		panelH2.add(lblH2T2);
		larryH2[1] = lblH2T2;
		
		
		lblH2T1 = new JLabel("");
		lblH2T1.setIcon(h2Img);
		panelH2.add(lblH2T1);
		
		
		panelH2Ms = new JPanel();
		panelH2Ms.setBounds(46, 0, 35, 474);
		panelH2Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer2.add(panelH2Ms);
		panelH2Ms.setLayout(null);
		
		
		panelH2M1 = new JPanel();
		panelH2M1.setBounds(0, 0, 35, 114);
		panelH2M1.setBackground(COLOR_TRANSPARENT);
		panelH2Ms.add(panelH2M1);
		panelH2M1.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH2M1T4 = new JLabel("");
		panelH2M1.add(lblH2M1T4);
		lblH2M1T4.setIcon(meld2Img);
		
		lblH2M1T3 = new JLabel("");
		panelH2M1.add(lblH2M1T3);
		lblH2M1T3.setIcon(meld2Img);
		
		lblH2M1T2 = new JLabel("");
		panelH2M1.add(lblH2M1T2);
		lblH2M1T2.setIcon(meld2Img);
		
		lblH2M1T1 = new JLabel("");
		panelH2M1.add(lblH2M1T1);
		lblH2M1T1.setIcon(meld2Img);
		
		
		
		
		panelH2M4 = new JPanel();
		panelH2M4.setBounds(0, 360, 35, 114);
		panelH2M4.setBackground(COLOR_TRANSPARENT);
		panelH2M4.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH2M4T4 = new JLabel("");
		panelH2M4.add(lblH2M4T4);
		lblH2M4T4.setIcon(meld2Img);
		
		lblH2M4T3 = new JLabel("");
		panelH2M4.add(lblH2M4T3);
		lblH2M4T3.setIcon(meld2Img);
		
		lblH2M4T2 = new JLabel("");
		panelH2M4.add(lblH2M4T2);
		lblH2M4T2.setIcon(meld2Img);
		
		
		
		panelH2M2 = new JPanel();
		panelH2M2.setBounds(0, 120, 35, 114);
		panelH2M2.setBackground(COLOR_TRANSPARENT);
		panelH2Ms.add(panelH2M2);
		panelH2M2.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH2M2T4 = new JLabel("");
		panelH2M2.add(lblH2M2T4);
		lblH2M2T4.setIcon(meld2Img);
		
		lblH2M2T3 = new JLabel("");
		panelH2M2.add(lblH2M2T3);
		lblH2M2T3.setIcon(meld2Img);
		
		lblH2M2T2 = new JLabel("");
		panelH2M2.add(lblH2M2T2);
		lblH2M2T2.setIcon(meld2Img);
		
		lblH2M2T1 = new JLabel("");
		panelH2M2.add(lblH2M2T1);
		lblH2M2T1.setIcon(meld2Img);
		
		
		
		
		panelH2M3 = new JPanel();
		panelH2M3.setBounds(0, 240, 35, 114);
		panelH2M3.setBackground(COLOR_TRANSPARENT);
		panelH2Ms.add(panelH2M3);
		panelH2M3.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH2M3T4 = new JLabel("");
		panelH2M3.add(lblH2M3T4);
		lblH2M3T4.setIcon(meld2Img);
		
		lblH2M3T3 = new JLabel("");
		panelH2M3.add(lblH2M3T3);
		lblH2M3T3.setIcon(meld2Img);
		
		lblH2M3T2 = new JLabel("");
		panelH2M3.add(lblH2M3T2);
		lblH2M3T2.setIcon(meld2Img);
		
		lblH2M3T1 = new JLabel("");
		panelH2M3.add(lblH2M3T1);
		lblH2M3T1.setIcon(meld2Img);
		panelH2Ms.add(panelH2M4);
		
		lblH2M4T1 = new JLabel("");
		panelH2M4.add(lblH2M4T1);
		lblH2M4T1.setIcon(meld2Img);
		
		
		
		
		
		
		
		
		panelPlayer3 = new JPanel();
		panelPlayer3.setBounds(23, 40, 667, 78);
		panelPlayer3.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer3);
		panelPlayer3.setLayout(null);
		
		
		
		
		
		panelH3 = new JPanel();
		panelH3.setBounds(203, 36, TILE_BIG_WIDTH*SIZE_HAND, TILE_BIG_HEIGHT);
		panelH3.setBackground(COLOR_TRANSPARENT);
		panelPlayer3.add(panelH3);
		panelH3.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		lblH3T1 = new JLabel("");
		lblH3T1.setBounds(468, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T1.setIcon(h3Img);
		panelH3.add(lblH3T1);
		
		lblH3T2 = new JLabel("");
		lblH3T2.setBounds(432, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T2.setIcon(h3Img);
		panelH3.add(lblH3T2);
		
		lblH3T3 = new JLabel("");
		lblH3T3.setBounds(396, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T3.setIcon(h3Img);
		panelH3.add(lblH3T3);
		
		lblH3T4 = new JLabel("");
		lblH3T4.setBounds(360, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T4.setIcon(h3Img);
		panelH3.add(lblH3T4);
		
		lblH3T5 = new JLabel("");
		lblH3T5.setBounds(324, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T5.setIcon(h3Img);
		panelH3.add(lblH3T5);
		
		lblH3T6 = new JLabel("");
		lblH3T6.setBounds(288, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T6.setIcon(h3Img);
		panelH3.add(lblH3T6);
		
		lblH3T7 = new JLabel("");
		lblH3T7.setBounds(252, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T7.setIcon(h3Img);
		panelH3.add(lblH3T7);
		
		lblH3T8 = new JLabel("");
		lblH3T8.setBounds(216, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T8.setIcon(h3Img);
		panelH3.add(lblH3T8);
		
		lblH3T9 = new JLabel("");
		lblH3T9.setBounds(180, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T9.setIcon(h3Img);
		panelH3.add(lblH3T9);
		
		lblH3T10 = new JLabel("");
		lblH3T10.setBounds(144, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T10.setIcon(h3Img);
		panelH3.add(lblH3T10);
		
		lblH3T11 = new JLabel("");
		lblH3T11.setBounds(108, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T11.setIcon(h3Img);
		panelH3.add(lblH3T11);
		
		lblH3T12 = new JLabel("");
		lblH3T12.setBounds(72, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T12.setIcon(h3Img);
		panelH3.add(lblH3T12);
		
		lblH3T13 = new JLabel("");
		lblH3T13.setBounds(36, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T13.setIcon(h3Img);
		panelH3.add(lblH3T13);
		
		lblH3T14 = new JLabel("");
		lblH3T14.setBounds(0, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T14.setIcon(h3Img);
		panelH3.add(lblH3T14);
		
		
		
		
		panelH3Ms = new JPanel();
		panelH3Ms.setBounds(0, 0, 474, 35);
		panelH3Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer3.add(panelH3Ms);
		panelH3Ms.setLayout(null);
		
		
		panelH3M1 = new JPanel();
		panelH3M1.setBounds(0, 0, 114, 35);
		panelH3M1.setBackground(COLOR_TRANSPARENT);
		panelH3Ms.add(panelH3M1);
		panelH3M1.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH3M1T4 = new JLabel("");
		panelH3M1.add(lblH3M1T4);
		lblH3M1T4.setIcon(meld3Img);
		
		lblH3M1T3 = new JLabel("");
		panelH3M1.add(lblH3M1T3);
		lblH3M1T3.setIcon(meld3Img);
		
		lblH3M1T2 = new JLabel("");
		panelH3M1.add(lblH3M1T2);
		lblH3M1T2.setIcon(meld3Img);
		
		
		lblH3M1T1 = new JLabel("");
		panelH3M1.add(lblH3M1T1);
		lblH3M1T1.setIcon(meld3Img);
		
		
		
		
		panelH3M4 = new JPanel();
		panelH3M4.setBounds(360, 0, 114, 35);
		panelH3M4.setBackground(COLOR_TRANSPARENT);
		panelH3M4.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH3M4T4 = new JLabel("");
		panelH3M4.add(lblH3M4T4);
		lblH3M4T4.setIcon(meld3Img);
		
		lblH3M4T3 = new JLabel("");
		panelH3M4.add(lblH3M4T3);
		lblH3M4T3.setIcon(meld3Img);
		
		lblH3M4T2 = new JLabel("");
		panelH3M4.add(lblH3M4T2);
		lblH3M4T2.setIcon(meld3Img);
		
		
		
		panelH3M2 = new JPanel();
		panelH3M2.setBounds(120, 0, 114, 35);
		panelH3M2.setBackground(COLOR_TRANSPARENT);
		panelH3Ms.add(panelH3M2);
		panelH3M2.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH3M2T4 = new JLabel("");
		panelH3M2.add(lblH3M2T4);
		lblH3M2T4.setIcon(meld3Img);
		
		lblH3M2T3 = new JLabel("");
		panelH3M2.add(lblH3M2T3);
		lblH3M2T3.setIcon(meld3Img);
		
		lblH3M2T2 = new JLabel("");
		panelH3M2.add(lblH3M2T2);
		lblH3M2T2.setIcon(meld3Img);
		
		lblH3M2T1 = new JLabel("");
		panelH3M2.add(lblH3M2T1);
		lblH3M2T1.setIcon(meld3Img);
		
		
		
		
		panelH3M3 = new JPanel();
		panelH3M3.setBounds(240, 0, 114, 35);
		panelH3M3.setBackground(COLOR_TRANSPARENT);
		panelH3Ms.add(panelH3M3);
		panelH3M3.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH3M3T4 = new JLabel("");
		panelH3M3.add(lblH3M3T4);
		lblH3M3T4.setIcon(meld3Img);
		
		lblH3M3T3 = new JLabel("");
		panelH3M3.add(lblH3M3T3);
		lblH3M3T3.setIcon(meld3Img);
		
		lblH3M3T2 = new JLabel("");
		panelH3M3.add(lblH3M3T2);
		lblH3M3T2.setIcon(meld3Img);
		
		lblH3M3T1 = new JLabel("");
		panelH3M3.add(lblH3M3T1);
		lblH3M3T1.setIcon(meld3Img);
		panelH3Ms.add(panelH3M4);
		
		lblH3M4T1 = new JLabel("");
		panelH3M4.add(lblH3M4T1);
		lblH3M4T1.setIcon(meld3Img);
		
		
		
		
		
		
		
		
		
		
		
		panelPlayer4 = new JPanel();
		panelPlayer4.setBounds(48, 171, 78, 667);
		panelPlayer4.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer4);
		panelPlayer4.setLayout(null);
		
		
		
		
		panelH4 = new JPanel();
		panelH4.setBounds(36, 42, TILE_BIG_HEIGHT, TILE_BIG_WIDTH*SIZE_HAND);
		panelH4.setBackground(COLOR_TRANSPARENT);
		panelPlayer4.add(panelH4);
		panelH4.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		lblH4T1 = new JLabel("");
		lblH4T1.setBounds(0, 0, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T1.setIcon(h4Img);
		panelH4.add(lblH4T1);
		
		lblH4T2 = new JLabel("");
		lblH4T2.setBounds(0, 36, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T2.setIcon(h4Img);
		panelH4.add(lblH4T2);
		
		lblH4T3 = new JLabel("");
		lblH4T3.setBounds(0, 72, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T3.setIcon(h4Img);
		panelH4.add(lblH4T3);
		
		lblH4T4 = new JLabel("");
		lblH4T4.setBounds(0, 108, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T4.setIcon(h4Img);
		panelH4.add(lblH4T4);
		
		lblH4T5 = new JLabel("");
		lblH4T5.setBounds(0, 144, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T5.setIcon(h4Img);
		panelH4.add(lblH4T5);
		
		lblH4T6 = new JLabel("");
		lblH4T6.setBounds(0, 180, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T6.setIcon(h4Img);
		panelH4.add(lblH4T6);
		
		lblH4T7 = new JLabel("");
		lblH4T7.setBounds(0, 216, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T7.setIcon(h4Img);
		panelH4.add(lblH4T7);
		
		lblH4T8 = new JLabel("");
		lblH4T8.setBounds(0, 252, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T8.setIcon(h4Img);
		panelH4.add(lblH4T8);
		
		lblH4T9 = new JLabel("");
		lblH4T9.setBounds(0, 288, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T9.setIcon(h4Img);
		panelH4.add(lblH4T9);
		
		lblH4T10 = new JLabel("");
		lblH4T10.setBounds(0, 324, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T10.setIcon(h4Img);
		panelH4.add(lblH4T10);
		
		lblH4T11 = new JLabel("");
		lblH4T11.setBounds(0, 360, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T11.setIcon(h4Img);
		panelH4.add(lblH4T11);
		
		lblH4T12 = new JLabel("");
		lblH4T12.setBounds(0, 396, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T12.setIcon(h4Img);
		panelH4.add(lblH4T12);
		
		lblH4T13 = new JLabel("");
		lblH4T13.setBounds(0, 432, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T13.setIcon(h4Img);
		panelH4.add(lblH4T13);
		
		lblH4T14 = new JLabel("");
		lblH4T14.setBounds(0, 468, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T14.setIcon(h4Img);
		panelH4.add(lblH4T14);
		
		
		
		
		
		panelH4Ms = new JPanel();
		panelH4Ms.setBounds(0, 192, 35, 474);
		panelH4Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer4.add(panelH4Ms);
		panelH4Ms.setLayout(null);
		
		
		
		
		panelH4M1 = new JPanel();
		panelH4M1.setBounds(0, 360, 35, 114);
		panelH4M1.setBackground(COLOR_TRANSPARENT);
		panelH4Ms.add(panelH4M1);
		panelH4M1.setLayout(new GridLayout(4, 1, 0, 0));
		
		
		lblH4M1T1 = new JLabel("");
		panelH4M1.add(lblH4M1T1);
		lblH4M1T1.setIcon(meld4Img);
		
		lblH4M1T2 = new JLabel("");
		panelH4M1.add(lblH4M1T2);
		lblH4M1T2.setIcon(meld4Img);
		
		lblH4M1T3 = new JLabel("");
		panelH4M1.add(lblH4M1T3);
		lblH4M1T3.setIcon(meld4Img);
		
		lblH4M1T4 = new JLabel("");
		panelH4M1.add(lblH4M1T4);
		lblH4M1T4.setIcon(meld4Img);
		
		
		
		
		panelH4M4 = new JPanel();
		panelH4M4.setBounds(0, 0, 35, 114);
		panelH4M4.setBackground(COLOR_TRANSPARENT);
		panelH4M4.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH4M4T1 = new JLabel("");
		panelH4M4.add(lblH4M4T1);
		lblH4M4T1.setIcon(meld4Img);
		
		lblH4M4T2 = new JLabel("");
		panelH4M4.add(lblH4M4T2);
		lblH4M4T2.setIcon(meld4Img);
		
		lblH4M4T3 = new JLabel("");
		panelH4M4.add(lblH4M4T3);
		lblH4M4T3.setIcon(meld4Img);
		
		lblH4M4T4 = new JLabel("");
		panelH4M4.add(lblH4M4T4);
		lblH4M4T4.setIcon(meld4Img);
		
		
		
		panelH4M2 = new JPanel();
		panelH4M2.setBounds(0, 240, 35, 114);
		panelH4M2.setBackground(COLOR_TRANSPARENT);
		panelH4Ms.add(panelH4M2);
		panelH4M2.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH4M2T1 = new JLabel("");
		panelH4M2.add(lblH4M2T1);
		lblH4M2T1.setIcon(meld4Img);
		
		lblH4M2T2 = new JLabel("");
		panelH4M2.add(lblH4M2T2);
		lblH4M2T2.setIcon(meld4Img);
		
		lblH4M2T3 = new JLabel("");
		panelH4M2.add(lblH4M2T3);
		lblH4M2T3.setIcon(meld4Img);
		
		lblH4M2T4 = new JLabel("");
		panelH4M2.add(lblH4M2T4);
		lblH4M2T4.setIcon(meld4Img);
		
		
		
		
		panelH4M3 = new JPanel();
		panelH4M3.setBounds(0, 120, 35, 114);
		panelH4M3.setBackground(COLOR_TRANSPARENT);
		panelH4Ms.add(panelH4M3);
		panelH4M3.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH4M3T1 = new JLabel("");
		panelH4M3.add(lblH4M3T1);
		lblH4M3T1.setIcon(meld4Img);
		
		lblH4M3T2 = new JLabel("");
		panelH4M3.add(lblH4M3T2);
		lblH4M3T2.setIcon(meld4Img);
		
		lblH4M3T3 = new JLabel("");
		panelH4M3.add(lblH4M3T3);
		lblH4M3T3.setIcon(meld4Img);
		
		lblH4M3T4 = new JLabel("");
		panelH4M3.add(lblH4M3T4);
		lblH4M3T4.setIcon(meld4Img);
		panelH4Ms.add(panelH4M4);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		panelSidebar = new JPanel();
		panelSidebar.setBounds(874, 0, 276, 850);
		panelSidebar.setBackground(COLOR_SIDEBAR);
		contentPane.add(panelSidebar);
		panelSidebar.setLayout(null);
		
		labelWeHaveFun = new JLabel("");
		labelWeHaveFun.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\other\\sheepy2trans.png"));
		labelWeHaveFun.setBounds(4, 5, 270, 345);
		panelSidebar.add(labelWeHaveFun);
		
		
		
		
		
		//all pond tiles put in an arraylist, for a toggle test
		Tilelabels.add(lblP1T1);Tilelabels.add(lblP1T2);Tilelabels.add(lblP1T3);Tilelabels.add(lblP1T4);Tilelabels.add(lblP1T5);Tilelabels.add(lblP1T6);Tilelabels.add(lblP1T7);Tilelabels.add(lblP1T8);Tilelabels.add(lblP1T9);Tilelabels.add(lblP1T10);Tilelabels.add(lblP1T11);Tilelabels.add(lblP1T12);Tilelabels.add(lblP1T13);Tilelabels.add(lblP1T14);Tilelabels.add(lblP1T15);Tilelabels.add(lblP1T16);Tilelabels.add(lblP1T17);Tilelabels.add(lblP1T18);Tilelabels.add(lblP1T19);Tilelabels.add(lblP1T20);Tilelabels.add(lblP1T21);Tilelabels.add(lblP1T22);Tilelabels.add(lblP1T23);Tilelabels.add(lblP1T24);
		Tilelabels.add(lblP3T1);Tilelabels.add(lblP3T2);Tilelabels.add(lblP3T3);Tilelabels.add(lblP3T4);Tilelabels.add(lblP3T5);Tilelabels.add(lblP3T6);Tilelabels.add(lblP3T7);Tilelabels.add(lblP3T8);Tilelabels.add(lblP3T9);Tilelabels.add(lblP3T10);Tilelabels.add(lblP3T11);Tilelabels.add(lblP3T12);Tilelabels.add(lblP3T13);Tilelabels.add(lblP3T14);Tilelabels.add(lblP3T15);Tilelabels.add(lblP3T16);Tilelabels.add(lblP3T17);Tilelabels.add(lblP3T18);Tilelabels.add(lblP3T19);Tilelabels.add(lblP3T20);Tilelabels.add(lblP3T21);Tilelabels.add(lblP3T22);Tilelabels.add(lblP3T23);Tilelabels.add(lblP3T24);
		Tilelabels.add(lblP2T1);Tilelabels.add(lblP2T2);Tilelabels.add(lblP2T3);Tilelabels.add(lblP2T4);Tilelabels.add(lblP2T5);Tilelabels.add(lblP2T6);Tilelabels.add(lblP2T7);Tilelabels.add(lblP2T8);Tilelabels.add(lblP2T9);Tilelabels.add(lblP2T10);Tilelabels.add(lblP2T11);Tilelabels.add(lblP2T12);Tilelabels.add(lblP2T13);Tilelabels.add(lblP2T14);Tilelabels.add(lblP2T15);Tilelabels.add(lblP2T16);Tilelabels.add(lblP2T17);Tilelabels.add(lblP2T18);Tilelabels.add(lblP2T19);Tilelabels.add(lblP2T20);Tilelabels.add(lblP2T21);Tilelabels.add(lblP2T22);Tilelabels.add(lblP2T23);Tilelabels.add(lblP2T24);
		Tilelabels.add(lblP4T1);Tilelabels.add(lblP4T2);Tilelabels.add(lblP4T3);Tilelabels.add(lblP4T4);Tilelabels.add(lblP4T5);Tilelabels.add(lblP4T6);Tilelabels.add(lblP4T7);Tilelabels.add(lblP4T8);Tilelabels.add(lblP4T9);Tilelabels.add(lblP4T10);Tilelabels.add(lblP4T11);Tilelabels.add(lblP4T12);Tilelabels.add(lblP4T13);Tilelabels.add(lblP4T14);Tilelabels.add(lblP4T15);Tilelabels.add(lblP4T16);Tilelabels.add(lblP4T17);Tilelabels.add(lblP4T18);Tilelabels.add(lblP4T19);Tilelabels.add(lblP4T20);Tilelabels.add(lblP4T21);Tilelabels.add(lblP4T22);Tilelabels.add(lblP4T23);Tilelabels.add(lblP4T24);
		JButton btnToggleOnOff = new JButton("Toggle OnOff");
		btnToggleOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageIcon toggleIcon = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\5.png");
				toggle = !toggle;
				for (JLabel l: Tilelabels){
					if (toggle) l.setIcon(toggleIcon);
					else l.setIcon(null);
					thisguy.repaint();
				}
			}
		});
		btnToggleOnOff.setBounds(27, 559, 215, 35);
		panelSidebar.add(btnToggleOnOff);
		
		lblBigTileDemo1 = new JLabel("");
		lblBigTileDemo1.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\1.png"));
		lblBigTileDemo1.setBounds(52, 403, 30, 41);
		panelSidebar.add(lblBigTileDemo1);
		
		lblBigTileDemo2 = new JLabel("");
		lblBigTileDemo2.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\1.png"));
		lblBigTileDemo2.setBounds(82, 403, 30, 41);
		panelSidebar.add(lblBigTileDemo2);
		
		lblBigTileDemo3 = new JLabel("");
		lblBigTileDemo3.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\1.png"));
		lblBigTileDemo3.setBounds(112, 403, 30, 41);
		panelSidebar.add(lblBigTileDemo3);
		
		
		
		
		
		
		
		
		
		
		JButton btnRandhand = new JButton("Rand All");
		//TODO: Random button
		btnRandhand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Random randGen = new Random();
				final int RANDLIMIT = 38;
				final boolean HIDEWALL = true;
				final boolean HIDEHANDS = true;
				
				//randomize tiles
				for (JLabel l: larryH1)
					l.setIcon(garryTiles[0][0][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryP1)
					l.setIcon(garryTiles[0][1][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryW1)
					if (HIDEWALL) l.setIcon(garryTiles[0][1][0]); 
					else l.setIcon(garryTiles[0][1][randGen.nextInt(RANDLIMIT)]);
				for (JLabel[] lar: larryH1Ms)
					for (JLabel l: lar)
						if (randGen.nextInt(4) == 1) l.setIcon(garryTiles[1][1][randGen.nextInt(RANDLIMIT)]);
						else l.setIcon(garryTiles[0][1][randGen.nextInt(RANDLIMIT)]);
				
				
				for (JLabel l: larryH2)
					if (HIDEHANDS) l.setIcon(garryTiles[1][0][0]);
					else l.setIcon(garryTiles[1][0][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryP2)
					if (randGen.nextInt(34) != 1) l.setIcon(garryTiles[1][1][randGen.nextInt(RANDLIMIT)]);
					else l.setIcon(garryTiles[0][1][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryW2)
					if (HIDEWALL) l.setIcon(garryTiles[1][1][0]); 
					else l.setIcon(garryTiles[1][1][randGen.nextInt(RANDLIMIT)]);
				for (JLabel[] lar: larryH2Ms)
					for (JLabel l: lar)
						l.setIcon(garryTiles[1][1][randGen.nextInt(RANDLIMIT)]);
				
				
				for (JLabel l: larryH3)
					if (HIDEHANDS) l.setIcon(garryTiles[2][0][0]);
					else l.setIcon(garryTiles[2][0][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryP3)
					l.setIcon(garryTiles[2][1][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryW3)
					if (HIDEWALL) l.setIcon(garryTiles[2][1][0]); 
					else l.setIcon(garryTiles[2][1][randGen.nextInt(RANDLIMIT)]);
				for (JLabel[] lar: larryH3Ms)
					for (JLabel l: lar)
						l.setIcon(garryTiles[2][1][randGen.nextInt(RANDLIMIT)]);
				
				
				for (JLabel l: larryH4)
					if (HIDEHANDS) l.setIcon(garryTiles[3][0][0]);
					else l.setIcon(garryTiles[3][0][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryP4)
					l.setIcon(garryTiles[3][1][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryW4)
					if (HIDEWALL) l.setIcon(garryTiles[3][1][0]); 
					else l.setIcon(garryTiles[3][1][randGen.nextInt(RANDLIMIT)]);
				for (JLabel[] lar: larryH4Ms)
					for (JLabel l: lar)
						l.setIcon(garryTiles[3][1][randGen.nextInt(RANDLIMIT)]);
				
				
				//randomize round info
				larryInfoRound[0].setIcon(garryWindsBig[randGen.nextInt(SIZE_GARRY_WINDS)]);
				larryInfoRound[1].setText(Integer.toString(1+randGen.nextInt(SIZE_GARRY_WINDS)));
				
				//ranodmize player info
				for (JLabel[] player: larryInfoPlayers){
					player[0].setIcon(garryWindsSmall[randGen.nextInt(SIZE_GARRY_WINDS)]);
					player[1].setText(Integer.toString(100*randGen.nextInt(1280)));
					if (randGen.nextInt(2) == 0) player[2].setIcon(null);
					else player[2].setIcon(garryOther[0]);
				}
				
				thisguy.repaint();
				
			
			}
		});
		btnRandhand.setBounds(49, 621, 172, 35);
		panelSidebar.add(btnRandhand);
		
		lblSmallTileDemo1 = new JLabel("");
		lblSmallTileDemo1.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\14.png"));
		lblSmallTileDemo1.setBounds(49, 470, 23, 31);
		panelSidebar.add(lblSmallTileDemo1);
		
		lblSmallTileDemo2 = new JLabel("");
		lblSmallTileDemo2.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\14.png"));
		lblSmallTileDemo2.setBounds(72, 470, 23, 31);
		panelSidebar.add(lblSmallTileDemo2);
		
		lblSmallTileDemo3 = new JLabel("");
		lblSmallTileDemo3.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat1\\small\\14.png"));
		lblSmallTileDemo3.setBounds(95, 470, 23, 31);
		panelSidebar.add(lblSmallTileDemo3);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		///////////
		
		
		
		
		////////////////////////////////////////////////////////////
		
		
		
		//load Tile labels into arrays
		larryH1[0] = lblH1T1;larryH1[1] = lblH1T2;larryH1[2] = lblH1T3;larryH1[3] = lblH1T4;larryH1[4] = lblH1T5;larryH1[5] = lblH1T6;larryH1[6] = lblH1T7;larryH1[7] = lblH1T8;larryH1[8] = lblH1T9;larryH1[9] = lblH1T10;larryH1[10] = lblH1T11;larryH1[11] = lblH1T12;larryH1[12] = lblH1T13;larryH1[13] = lblH1T14;
		larryH1M1[0] = lblH1M1T1;larryH1M1[1] = lblH1M1T2;larryH1M1[2] = lblH1M1T3;larryH1M1[3] = lblH1M1T4;larryH1M2[0] = lblH1M2T1;larryH1M2[1] = lblH1M2T2;larryH1M2[2] = lblH1M2T3;larryH1M2[3] = lblH1M2T4;larryH1M3[0] = lblH1M3T1;larryH1M3[1] = lblH1M3T2;larryH1M3[2] = lblH1M3T3;larryH1M3[3] = lblH1M3T4;larryH1M4[0] = lblH1M4T1;larryH1M4[1] = lblH1M4T2;larryH1M4[2] = lblH1M4T3;larryH1M4[3] = lblH1M4T4;
		larryP1[0] = lblP1T1;larryP1[1] = lblP1T2;larryP1[2] = lblP1T3;larryP1[3] = lblP1T4;larryP1[4] = lblP1T5;larryP1[5] = lblP1T6;larryP1[6] = lblP1T7;larryP1[7] = lblP1T8;larryP1[8] = lblP1T9;larryP1[9] = lblP1T10;larryP1[10] = lblP1T11;larryP1[11] = lblP1T12;larryP1[12] = lblP1T13;larryP1[13] = lblP1T14;larryP1[14] = lblP1T15;larryP1[15] = lblP1T16;larryP1[16] = lblP1T17;larryP1[17] = lblP1T18;larryP1[18] = lblP1T19;larryP1[19] = lblP1T20;larryP1[20] = lblP1T21;larryP1[21] = lblP1T22;larryP1[22] = lblP1T23;larryP1[23] = lblP1T24;
		larryW1[0] = lblW1T1;larryW1[1] = lblW1T2;larryW1[2] = lblW1T3;larryW1[3] = lblW1T4;larryW1[4] = lblW1T5;larryW1[5] = lblW1T6;larryW1[6] = lblW1T7;larryW1[7] = lblW1T8;larryW1[8] = lblW1T9;larryW1[9] = lblW1T10;larryW1[10] = lblW1T11;larryW1[11] = lblW1T12;larryW1[12] = lblW1T13;larryW1[13] = lblW1T14;larryW1[14] = lblW1T15;larryW1[15] = lblW1T16;larryW1[16] = lblW1T17;larryW1[17] = lblW1T18;larryW1[18] = lblW1T19;larryW1[19] = lblW1T20;larryW1[20] = lblW1T21;larryW1[21] = lblW1T22;larryW1[22] = lblW1T23;larryW1[23] = lblW1T24;larryW1[24] = lblW1T25;larryW1[25] = lblW1T26;larryW1[26] = lblW1T27;larryW1[27] = lblW1T28;larryW1[28] = lblW1T29;larryW1[29] = lblW1T30;larryW1[30] = lblW1T31;larryW1[31] = lblW1T32;larryW1[32] = lblW1T33;larryW1[33] = lblW1T34;
		
		larryH2[0] = lblH2T1;
		larryH2M1[0] = lblH2M1T1;larryH2M1[1] = lblH2M1T2;larryH2M1[2] = lblH2M1T3;larryH2M1[3] = lblH2M1T4;larryH2M2[0] = lblH2M2T1;larryH2M2[1] = lblH2M2T2;larryH2M2[2] = lblH2M2T3;larryH2M2[3] = lblH2M2T4;larryH2M3[0] = lblH2M3T1;larryH2M3[1] = lblH2M3T2;larryH2M3[2] = lblH2M3T3;larryH2M3[3] = lblH2M3T4;larryH2M4[0] = lblH2M4T1;larryH2M4[1] = lblH2M4T2;larryH2M4[2] = lblH2M4T3;larryH2M4[3] = lblH2M4T4;
		larryP2[0] = lblP2T1;larryP2[1] = lblP2T2;larryP2[2] = lblP2T3;larryP2[3] = lblP2T4;larryP2[4] = lblP2T5;larryP2[5] = lblP2T6;larryP2[6] = lblP2T7;larryP2[7] = lblP2T8;larryP2[8] = lblP2T9;larryP2[9] = lblP2T10;larryP2[10] = lblP2T11;larryP2[11] = lblP2T12;larryP2[12] = lblP2T13;larryP2[13] = lblP2T14;larryP2[14] = lblP2T15;larryP2[15] = lblP2T16;larryP2[16] = lblP2T17;larryP2[17] = lblP2T18;larryP2[18] = lblP2T19;larryP2[19] = lblP2T20;larryP2[20] = lblP2T21;larryP2[21] = lblP2T22;larryP2[22] = lblP2T23;larryP2[23] = lblP2T24;
		larryW2[0] = lblW2T1;larryW2[1] = lblW2T2;larryW2[2] = lblW2T3;larryW2[3] = lblW2T4;larryW2[4] = lblW2T5;larryW2[5] = lblW2T6;larryW2[6] = lblW2T7;larryW2[7] = lblW2T8;larryW2[8] = lblW2T9;larryW2[9] = lblW2T10;larryW2[10] = lblW2T11;larryW2[11] = lblW2T12;larryW2[12] = lblW2T13;larryW2[13] = lblW2T14;larryW2[14] = lblW2T15;larryW2[15] = lblW2T16;larryW2[16] = lblW2T17;larryW2[17] = lblW2T18;larryW2[18] = lblW2T19;larryW2[19] = lblW2T20;larryW2[20] = lblW2T21;larryW2[21] = lblW2T22;larryW2[22] = lblW2T23;larryW2[23] = lblW2T24;larryW2[24] = lblW2T25;larryW2[25] = lblW2T26;larryW2[26] = lblW2T27;larryW2[27] = lblW2T28;larryW2[28] = lblW2T29;larryW2[29] = lblW2T30;larryW2[30] = lblW2T31;larryW2[31] = lblW2T32;larryW2[32] = lblW2T33;larryW2[33] = lblW2T34;
		
		larryH3[0] = lblH3T1;larryH3[1] = lblH3T2;larryH3[2] = lblH3T3;larryH3[3] = lblH3T4;larryH3[4] = lblH3T5;larryH3[5] = lblH3T6;larryH3[6] = lblH3T7;larryH3[7] = lblH3T8;larryH3[8] = lblH3T9;larryH3[9] = lblH3T10;larryH3[10] = lblH3T11;larryH3[11] = lblH3T12;larryH3[12] = lblH3T13;larryH3[13] = lblH3T14;
		larryH3M1[0] = lblH3M1T1;larryH3M1[1] = lblH3M1T2;larryH3M1[2] = lblH3M1T3;larryH3M1[3] = lblH3M1T4;larryH3M2[0] = lblH3M2T1;larryH3M2[1] = lblH3M2T2;larryH3M2[2] = lblH3M2T3;larryH3M2[3] = lblH3M2T4;larryH3M3[0] = lblH3M3T1;larryH3M3[1] = lblH3M3T2;larryH3M3[2] = lblH3M3T3;larryH3M3[3] = lblH3M3T4;larryH3M4[0] = lblH3M4T1;larryH3M4[1] = lblH3M4T2;larryH3M4[2] = lblH3M4T3;larryH3M4[3] = lblH3M4T4;
		larryP3[0] = lblP3T1;larryP3[1] = lblP3T2;larryP3[2] = lblP3T3;larryP3[3] = lblP3T4;larryP3[4] = lblP3T5;larryP3[5] = lblP3T6;larryP3[6] = lblP3T7;larryP3[7] = lblP3T8;larryP3[8] = lblP3T9;larryP3[9] = lblP3T10;larryP3[10] = lblP3T11;larryP3[11] = lblP3T12;larryP3[12] = lblP3T13;larryP3[13] = lblP3T14;larryP3[14] = lblP3T15;larryP3[15] = lblP3T16;larryP3[16] = lblP3T17;larryP3[17] = lblP3T18;larryP3[18] = lblP3T19;larryP3[19] = lblP3T20;larryP3[20] = lblP3T21;larryP3[21] = lblP3T22;larryP3[22] = lblP3T23;larryP3[23] = lblP3T24;
		larryW3[0] = lblW3T1;larryW3[1] = lblW3T2;larryW3[2] = lblW3T3;larryW3[3] = lblW3T4;larryW3[4] = lblW3T5;larryW3[5] = lblW3T6;larryW3[6] = lblW3T7;larryW3[7] = lblW3T8;larryW3[8] = lblW3T9;larryW3[9] = lblW3T10;larryW3[10] = lblW3T11;larryW3[11] = lblW3T12;larryW3[12] = lblW3T13;larryW3[13] = lblW3T14;larryW3[14] = lblW3T15;larryW3[15] = lblW3T16;larryW3[16] = lblW3T17;larryW3[17] = lblW3T18;larryW3[18] = lblW3T19;larryW3[19] = lblW3T20;larryW3[20] = lblW3T21;larryW3[21] = lblW3T22;larryW3[22] = lblW3T23;larryW3[23] = lblW3T24;larryW3[24] = lblW3T25;larryW3[25] = lblW3T26;larryW3[26] = lblW3T27;larryW3[27] = lblW3T28;larryW3[28] = lblW3T29;larryW3[29] = lblW3T30;larryW3[30] = lblW3T31;larryW3[31] = lblW3T32;larryW3[32] = lblW3T33;larryW3[33] = lblW3T34;
		
		larryH4[0] = lblH4T1;larryH4[1] = lblH4T2;larryH4[2] = lblH4T3;larryH4[3] = lblH4T4;larryH4[4] = lblH4T5;larryH4[5] = lblH4T6;larryH4[6] = lblH4T7;larryH4[7] = lblH4T8;larryH4[8] = lblH4T9;larryH4[9] = lblH4T10;larryH4[10] = lblH4T11;larryH4[11] = lblH4T12;larryH4[12] = lblH4T13;larryH4[13] = lblH4T14;
		larryH4M1[0] = lblH4M1T1;larryH4M1[1] = lblH4M1T2;larryH4M1[2] = lblH4M1T3;larryH4M1[3] = lblH4M1T4;larryH4M2[0] = lblH4M2T1;larryH4M2[1] = lblH4M2T2;larryH4M2[2] = lblH4M2T3;larryH4M2[3] = lblH4M2T4;larryH4M3[0] = lblH4M3T1;larryH4M3[1] = lblH4M3T2;larryH4M3[2] = lblH4M3T3;larryH4M3[3] = lblH4M3T4;larryH4M4[0] = lblH4M4T1;larryH4M4[1] = lblH4M4T2;larryH4M4[2] = lblH4M4T3;larryH4M4[3] = lblH4M4T4;
		larryP4[0] = lblP4T1;larryP4[1] = lblP4T2;larryP4[2] = lblP4T3;larryP4[3] = lblP4T4;larryP4[4] = lblP4T5;larryP4[5] = lblP4T6;larryP4[6] = lblP4T7;larryP4[7] = lblP4T8;larryP4[8] = lblP4T9;larryP4[9] = lblP4T10;larryP4[10] = lblP4T11;larryP4[11] = lblP4T12;larryP4[12] = lblP4T13;larryP4[13] = lblP4T14;larryP4[14] = lblP4T15;larryP4[15] = lblP4T16;larryP4[16] = lblP4T17;larryP4[17] = lblP4T18;larryP4[18] = lblP4T19;larryP4[19] = lblP4T20;larryP4[20] = lblP4T21;larryP4[21] = lblP4T22;larryP4[22] = lblP4T23;larryP4[23] = lblP4T24;		
		larryW4[0] = lblW4T1;larryW4[1] = lblW4T2;larryW4[2] = lblW4T3;larryW4[3] = lblW4T4;larryW4[4] = lblW4T5;larryW4[5] = lblW4T6;larryW4[6] = lblW4T7;larryW4[7] = lblW4T8;larryW4[8] = lblW4T9;larryW4[9] = lblW4T10;larryW4[10] = lblW4T11;larryW4[11] = lblW4T12;larryW4[12] = lblW4T13;larryW4[13] = lblW4T14;larryW4[14] = lblW4T15;larryW4[15] = lblW4T16;larryW4[16] = lblW4T17;larryW4[17] = lblW4T18;larryW4[18] = lblW4T19;larryW4[19] = lblW4T20;larryW4[20] = lblW4T21;larryW4[21] = lblW4T22;larryW4[22] = lblW4T23;larryW4[23] = lblW4T24;larryW4[24] = lblW4T25;larryW4[25] = lblW4T26;larryW4[26] = lblW4T27;larryW4[27] = lblW4T28;larryW4[28] = lblW4T29;larryW4[29] = lblW4T30;larryW4[30] = lblW4T31;larryW4[31] = lblW4T32;larryW4[32] = lblW4T33;larryW4[33] = lblW4T34;
		
		
		
		//load Round Info labels into arrays
		larryInfoRound[0] = lblRIndWind;larryInfoRound[1] = lblRIndNum;
		larryInfoP1[0] = lblInfoP1Wind;larryInfoP1[1] = lblInfoP1Points;larryInfoP1[2] = lblInfoP1Riichi;
		larryInfoP2[0] = lblInfoP2Wind;larryInfoP2[1] = lblInfoP2Points;larryInfoP2[2] = lblInfoP2Riichi;
		larryInfoP3[0] = lblInfoP3Wind;larryInfoP3[1] = lblInfoP3Points;larryInfoP3[2] = lblInfoP3Riichi;
		larryInfoP4[0] = lblInfoP4Wind;larryInfoP4[1] = lblInfoP4Points;larryInfoP4[2] = lblInfoP4Riichi;
		
		
		
		
		//put image icons into arrays
		//load Tile images into array
		for(int seat = 0; seat < SIZE_NUM_SEATS; seat++){
			//get tileback and each of the 34 tiles
			//garryTiles[seat number][0=big,1=small][tile number]
			for (int tile = 0; tile < SIZE_GARRY_TILES; tile++){
				garryTiles[seat][GARRYINDEX_TILE_BIG][tile] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat" + (seat+1) + "\\" + tile + ".png");
				garryTiles[seat][GARRYINDEX_TILE_SMALL][tile] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat" + (seat+1) + "\\small\\" + tile + ".png");
			}
			
			//get red fives
			garryTiles[seat][GARRYINDEX_TILE_BIG][GARRYINDEX_RED5M] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat" + (seat+1) + "\\5r.png");
			garryTiles[seat][GARRYINDEX_TILE_SMALL][GARRYINDEX_RED5M] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat" + (seat+1) + "\\small\\5r.png");
			
			garryTiles[seat][GARRYINDEX_TILE_BIG][GARRYINDEX_RED5P] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat" + (seat+1) + "\\14r.png");
			garryTiles[seat][GARRYINDEX_TILE_SMALL][GARRYINDEX_RED5P] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat" + (seat+1) + "\\small\\14r.png");

			garryTiles[seat][GARRYINDEX_TILE_BIG][GARRYINDEX_RED5S] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat" + (seat+1) + "\\23r.png");
			garryTiles[seat][GARRYINDEX_TILE_SMALL][GARRYINDEX_RED5S] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\seat" + (seat+1) + "\\small\\23r.png");
		}
		
		
		//load Wind images into array
		garryWinds[0][0] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\transE.png");
		garryWinds[0][1] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\transS.png");
		garryWinds[0][2] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\transW.png");
		garryWinds[0][3] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\transN.png");
		garryWinds[1][0] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transEs.png");
		garryWinds[1][1] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transSs.png");
		garryWinds[1][2] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transWs.png");
		garryWinds[1][3] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\winds\\small\\transNs.png");
		
		
		
		
		//load Other (riichi stick, sheepy2) into array
		garryOther[0] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\other\\riichiStick.png");
		garryOther[1] = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\other\\sheepy2trans.png");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
