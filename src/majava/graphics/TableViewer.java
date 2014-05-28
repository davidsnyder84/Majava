package majava.graphics;

import majava.Hand;
import majava.Meld;
import majava.Player;
import majava.Pond;
import majava.RoundTracker;
import majava.Wind;
import majava.tiles.Tile;
import majava.TileList;
import majava.tiles.PondTile;
import utility.Pauser;
import utility.ImageRotator;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;


/*
Class: TableViewer
a larger GUI for viewing the full wall
*/
public class TableViewer extends TableGUI{
	private static final long serialVersionUID = 8352771291866988835L;
	
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~BEGIN CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	private static final int WINDOW_WIDTH = 1120;
	private static final int WINDOW_HEIGHT = 726 + 6;
	
	private static final int SIZE_WALL = 34;
	
	private static final int[][] EXCLAMATION_LOCS =  {{161, 688}, {708, 360}, {542, 6}, {3, 360}};
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	
	
	
	
	
	/*......................................BEGIN LABEL ARRAYS......................................*/
		
	private final JLabel[] larryW1 = new JLabel[SIZE_WALL], larryW2 = new JLabel[SIZE_WALL], larryW3 = new JLabel[SIZE_WALL], larryW4 = new JLabel[SIZE_WALL];
	private final JLabel[][] larryWalls = {larryW1, larryW4, larryW3, larryW2};
	private final JLabel[] larryWallAll = new JLabel[SIZE_WALL*4];
	
	
	protected WallPanel panWall1, panWall2, panWall3, panWall4;
	
	/*......................................END LABEL ARRAYS......................................*/
	
	
	
	
	/*+++++++++++++++++++++++++++++++++++++++BEGIN IMAGE ARRAYS+++++++++++++++++++++++++++++++++++++++*/
	/*+++++++++++++++++++++++++++++++++++++++END IMAGE ARRAYS+++++++++++++++++++++++++++++++++++++++*/
	
	
	

	
	/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^BEGIN MEMBER VARIABLES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
	/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^END MEMBER VARIABLES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void updateEverything(){
		
		int currentPlayer, currentTile;
		
		//update wall(s)
		for (currentPlayer = 0; currentPlayer < NUM_PLAYERS; currentPlayer++){
			for (currentTile = 0; currentTile < SIZE_WALL; currentTile++)
				larryWalls[currentPlayer][currentTile].setIcon(__getImageIconWall(tilesW, currentTile + currentPlayer*SIZE_WALL, currentPlayer, mOptionRevealWall));
		}
		//deal wall portion of wall
		for (currentTile = POS_DORA_1; currentTile >= 2*(4 - mRoundTracker.getNumKansMade()); currentTile -= 2){
			larryWallAll[OFFSET_DEAD_WALL + currentTile].setIcon(__getImageIconWall(tilesW, currentTile + OFFSET_DEAD_WALL, SEAT4));
		}
		
		super.updateEverything();
	}
	
	
	
	
	
	//replaces all imageicons with null
	public void blankEverything(){
		
		//walls
		for (JLabel[] lar: larryWalls)	
			for (JLabel l: lar) l.setIcon(null);
		
		super.blankEverything();
	}
	
	
	
	
	
	
	
	
	
	public void showExclamation(String exclamation, int seatNum, int sleepTime){showExclamation(exclamation, EXCLAMATION_LOCS[seatNum][0], EXCLAMATION_LOCS[seatNum][1], sleepTime);}
	public void showExclamation(String exclamation, int seatNum){showExclamation(exclamation, seatNum, DEFAULT_SLEEP_TIME_EXCLAMATION);}
	
	
	
	
	
	
	
	//launch the application TODO MAIN
	public static void main(String[] args) {
		
		TableViewer viewer = new TableViewer();
		viewer.setVisible(true);
		
	}
	
	
	//TODO start of constructor
	public TableViewer(){
		
		super();
		
		final int WINDOW_BOUND_WIDTH = WINDOW_WIDTH + 2*WINDOW_SIDE_BORDER_SIZE;
		final int WINDOW_BOUND_HEIGHT = WINDOW_HEIGHT + WINDOW_TOP_BORDER_SIZE + WINDOW_MENU_SIZE;
		
		setSize(WINDOW_BOUND_WIDTH, WINDOW_BOUND_HEIGHT);
		
		
		
		panTable.setBounds(0, 0, 844, WINDOW_HEIGHT);
		panSidebar.setBounds(panTable.getWidth(), 0, 276, WINDOW_HEIGHT);

		panMidTable.setBounds(143, 91, 552, 544);
		lblExclamation.setLocation(161, 688);
		
		//pond panels
		panP1.setLocation(196, 352);
		panP2.setLocation(360, 191);
		panP3.setLocation(196, 68);
		panP4.setLocation(68, 191);
		
		panRoundInfoSquare.setLocation(193, 193);
		
		
		//player panels		
		panPlayer1.setLocation(167, 643);
		panPlayer2.setLocation(730, -53);
		panPlayer3.setLocation(5, 5);
		panPlayer4.setLocation(30, 109);
		
		
		
		lblFun.setLocation(4, 5);lblFun.setSize(270, 345);
		panResult.setLocation(32, 361);
		panWallSummary.setLocation(10, 446);
		panCalls.setLocation(28, 567);
		
		panDebugButtons.setLocation(204, 460);
		
		
		
		

		
		/*................................................DEMO PURPOSES.......................................................*/
		/*................................................DEMO PURPOSES.......................................................*/
		
		
		panWall1 = new WallPanel(SEAT1);
		panWall2 = new WallPanel(SEAT2);
		panWall3 = new WallPanel(SEAT3);
		panWall4 = new WallPanel(SEAT4);
		
		
		//add wall panels to mid table
		panMidTable.add(panWall1);panMidTable.add(panWall2);panMidTable.add(panWall3);panMidTable.add(panWall4);
		
		
		//wall panels
		panWall1.setLocation(81, 482);
		panWall2.setLocation(490, 76);
		panWall3.setLocation(81, 0);
		panWall4.setLocation(0, 76);
		
		
		
		//add random walls to random button
		panDebugButtons.btnBlankAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Random randGen = new Random();final int RANDLIMIT = 38;
				int[] s = {0,3,2,1}; for (int seat = SEAT1; seat <= SEAT4; seat++) for (int j = 0; j < larryWalls[SEAT1].length; j++) larryWalls[seat][j].setIcon(garryTiles[s[seat]][SMALL][randGen.nextInt(RANDLIMIT)]);
			}
		});
		
		
		
		///////////
		
		
		
		////////////////////////////////////////////////////////////
		
		
		//load Tile labels into arrays
		panWall1.getLabels(larryW1);
		panWall2.getLabels(larryW2);
		panWall3.getLabels(larryW3);
		panWall4.getLabels(larryW4);
		
		//put wall labels in a single array of 136 tiles
		int i = 0; for (JLabel[] lar: larryWalls) for (JLabel l: lar) larryWallAll[i++] = l;
		
	}
	
	
	
	
	//TODO start of panel classes
	protected class WallPanel extends JPanel{
		private static final long serialVersionUID = 4795848182513272852L;
		
		private static final int WIDTH = TILE_SMALL_WIDTH*(SIZE_WALL/2), HEIGHT = TILE_SMALL_HEIGHT*2;
		protected final JLabel[] larryW = new JLabel[SIZE_WALL];
		
		public WallPanel(int seat){
			super();
			for (int i = 0; i < larryW.length; i++) larryW[i] = new JLabel();
			
			int pic = seat; if (seat == SEAT2) seat = SEAT4; else if (seat == SEAT4) seat = SEAT2; for (JLabel l: larryW) l.setIcon(garryTiles[pic][SMALL][0]);
			
			
			setBounds(0, 0, WIDTH, HEIGHT);
			setLayout(new GridLayout(2, 17, 0, 0));
			if (seat == SEAT2 || seat == SEAT4){
				setSize(HEIGHT, WIDTH);
				setLayout(new GridLayout(17, 2, 0, 0));
			}
			setBackground(COLOR_TRANSPARENT);
			
			
			int[][] addOrders = {{33,31,29,27,25,23,21,19,17,15,13,11,9,7,5,3,1,34,32,30,28,26,24,22,20,18,16,14,12,10,8,6,4,2},
								{34,33,32,31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1},
								{2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33},
								{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34}};
			for (int i: addOrders[seat]) add(larryW[i-1]);
		}
		public void getLabels(JLabel[] wLarry){for (int i = 0; i < larryW.length; i++) wLarry[i] = larryW[i];}
	}
	
	
	
}
