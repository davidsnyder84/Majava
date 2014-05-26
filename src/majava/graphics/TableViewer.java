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
	
	private static final long serialVersionUID = 9210599763112170767L;
	
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~BEGIN CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	private static final int WINDOW_WIDTH = 1120;
	private static final int WINDOW_HEIGHT = 726 + 6;
	
	private static final int SIZE_WALL = 34;
	
	private static final int[][] EXCLAMATION_LOCS =  {{161, 688}, {708, 360}, {542, 6}, {3, 360}};
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	
	
	
	
	
	/*......................................BEGIN LABEL ARRAYS......................................*/
		
	private JLabel[] larryW1 = new JLabel[SIZE_WALL];
	private JLabel[] larryW2 = new JLabel[SIZE_WALL];
	private JLabel[] larryW3 = new JLabel[SIZE_WALL];
	private JLabel[] larryW4 = new JLabel[SIZE_WALL];
	private JLabel[][] larryWalls = {larryW1, larryW4, larryW3, larryW2};
	private JLabel[] larryWallAll = new JLabel[SIZE_WALL*4];
	
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

		
		setBounds(0, 0, WINDOW_BOUND_WIDTH, WINDOW_BOUND_HEIGHT);
		
		
		panTable.setBounds(0, 0, 844, WINDOW_HEIGHT);
		lblExclamation.setBounds(161, 688, 134, 34);
		
		//pond panels
		panP1.setBounds(196, 352, POND_PANEL_WIDTH, POND_PANEL_HEIGHT);
		panP2.setBounds(360, 191, POND_PANEL_HEIGHT, POND_PANEL_WIDTH);
		panP3.setBounds(196, 68, POND_PANEL_WIDTH, POND_PANEL_HEIGHT);
		panP4.setBounds(68, 191, POND_PANEL_HEIGHT, POND_PANEL_WIDTH);
		

		panMidTable.setBounds(143, 91, 552, 544);
		panRoundInfo.setBounds(193, 193, 166, 158);
		panRndInfBackground.setBounds(193, 193, 166, 158);
		
		
		//player panels		
		panPlayer1.setBounds(167, 643, 667, 78);
		panPlayer2.setBounds(730, -53, 78, 667);
		panPlayer3.setBounds(5, 5, 667, 78);
		panPlayer4.setBounds(30, 109, 78, 667);
		
		
		panSidebar.setBounds(panTable.getWidth(), 0, 276, WINDOW_HEIGHT);
		
		lblFun.setBounds(4, 5, 270, 345);
		panResult.setBounds(32, 361, 212, 66);
		panWallSummary.setBounds(10, 446, 161, 85);
		panCalls.setBounds(28, 567, 204, 147);
		
		btnBlank.setBounds(204, 485, 68, 23);
		btnRand.setBounds(204, 460, 65, 23);
		
		
		
		
		
		
		
		
		

		
		
		/*................................................DEMO PURPOSES.......................................................*/
		
//		ImageIcon wImg = garryTiles[SEAT1][SMALL][0];
//		ImageIcon w2Img = garryTiles[SEAT2][SMALL][0];
//		ImageIcon w3Img = garryTiles[SEAT3][SMALL][0];
//		ImageIcon w4Img = garryTiles[SEAT4][SMALL][0];
		
		ImageRotator rotators[] = {new ImageRotator(0), new ImageRotator(-90), new ImageRotator(180), new ImageRotator(90)};
		
		ImageIcon wImg = rotators[SEAT1].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/0.png")));
		ImageIcon w2Img = rotators[SEAT2].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/0.png")));
		ImageIcon w3Img = rotators[SEAT3].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/0.png")));
		ImageIcon w4Img = rotators[SEAT4].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/0.png")));
		
		/*................................................DEMO PURPOSES.......................................................*/

		
		//panel declarations
		JPanel panelW1;JPanel panelW2;JPanel panelW3;JPanel panelW4;
		
		
		
		//label declarations
		JLabel lblW1T1;JLabel lblW1T2;JLabel lblW1T3;JLabel lblW1T4;JLabel lblW1T5;JLabel lblW1T6;JLabel lblW1T7;JLabel lblW1T8;JLabel lblW1T9;JLabel lblW1T10;JLabel lblW1T11;JLabel lblW1T12;JLabel lblW1T13;JLabel lblW1T14;JLabel lblW1T15;JLabel lblW1T16;JLabel lblW1T17;JLabel lblW1T18;JLabel lblW1T19;JLabel lblW1T20;JLabel lblW1T21;JLabel lblW1T22;JLabel lblW1T23;JLabel lblW1T24;JLabel lblW1T25;JLabel lblW1T26;JLabel lblW1T27;JLabel lblW1T28;JLabel lblW1T29;JLabel lblW1T30;JLabel lblW1T31;JLabel lblW1T32;JLabel lblW1T33;JLabel lblW1T34;
		JLabel lblW2T1;JLabel lblW2T2;JLabel lblW2T3;JLabel lblW2T4;JLabel lblW2T5;JLabel lblW2T6;JLabel lblW2T7;JLabel lblW2T8;JLabel lblW2T9;JLabel lblW2T10;JLabel lblW2T11;JLabel lblW2T12;JLabel lblW2T13;JLabel lblW2T14;JLabel lblW2T15;JLabel lblW2T16;JLabel lblW2T17;JLabel lblW2T18;JLabel lblW2T19;JLabel lblW2T20;JLabel lblW2T21;JLabel lblW2T22;JLabel lblW2T23;JLabel lblW2T24;JLabel lblW2T25;JLabel lblW2T26;JLabel lblW2T27;JLabel lblW2T28;JLabel lblW2T29;JLabel lblW2T30;JLabel lblW2T31;JLabel lblW2T32;JLabel lblW2T33;JLabel lblW2T34;
		JLabel lblW3T1;JLabel lblW3T2;JLabel lblW3T3;JLabel lblW3T4;JLabel lblW3T5;JLabel lblW3T6;JLabel lblW3T7;JLabel lblW3T8;JLabel lblW3T9;JLabel lblW3T10;JLabel lblW3T11;JLabel lblW3T12;JLabel lblW3T13;JLabel lblW3T14;JLabel lblW3T15;JLabel lblW3T16;JLabel lblW3T17;JLabel lblW3T18;JLabel lblW3T19;JLabel lblW3T20;JLabel lblW3T21;JLabel lblW3T22;JLabel lblW3T23;JLabel lblW3T24;JLabel lblW3T25;JLabel lblW3T26;JLabel lblW3T27;JLabel lblW3T28;JLabel lblW3T29;JLabel lblW3T30;JLabel lblW3T31;JLabel lblW3T32;JLabel lblW3T33;JLabel lblW3T34;
		JLabel lblW4T1;JLabel lblW4T2;JLabel lblW4T3;JLabel lblW4T4;JLabel lblW4T5;JLabel lblW4T6;JLabel lblW4T7;JLabel lblW4T8;JLabel lblW4T9;JLabel lblW4T10;JLabel lblW4T11;JLabel lblW4T12;JLabel lblW4T13;JLabel lblW4T14;JLabel lblW4T15;JLabel lblW4T16;JLabel lblW4T17;JLabel lblW4T18;JLabel lblW4T19;JLabel lblW4T20;JLabel lblW4T21;JLabel lblW4T22;JLabel lblW4T23;JLabel lblW4T24;JLabel lblW4T25;JLabel lblW4T26;JLabel lblW4T27;JLabel lblW4T28;JLabel lblW4T29;JLabel lblW4T30;JLabel lblW4T31;JLabel lblW4T32;JLabel lblW4T33;JLabel lblW4T34;
		
		
		
		panelW1 = new JPanel();
//		panelW1.setBounds(71, 528, TILE_SMALL_WIDTH*(SIZE_WALL/2)+17, TILE_SMALL_HEIGHT*2+2);
		panelW1.setBounds(81, 482, TILE_SMALL_WIDTH*(SIZE_WALL/2), TILE_SMALL_HEIGHT*2);
		panelW1.setBackground(COLOR_TRANSPARENT);
		panMidTable.add(panelW1);
		panelW1.setLayout(new GridLayout(2, 17, 0, 0));
		
		lblW1T33 = new JLabel();
		panelW1.add(lblW1T33);
		lblW1T33.setIcon(wImg);
		
		lblW1T31 = new JLabel();
		panelW1.add(lblW1T31);
		lblW1T31.setIcon(wImg);
		
		lblW1T29 = new JLabel();
		panelW1.add(lblW1T29);
		lblW1T29.setIcon(wImg);
		
		lblW1T27 = new JLabel();
		panelW1.add(lblW1T27);
		lblW1T27.setIcon(wImg);
		
		lblW1T25 = new JLabel();
		panelW1.add(lblW1T25);
		lblW1T25.setIcon(wImg);
		
		lblW1T23 = new JLabel();
		panelW1.add(lblW1T23);
		lblW1T23.setIcon(wImg);
		
		lblW1T21 = new JLabel();
		panelW1.add(lblW1T21);
		lblW1T21.setIcon(wImg);
		
		lblW1T19 = new JLabel();
		panelW1.add(lblW1T19);
		lblW1T19.setIcon(wImg);
		
		lblW1T17 = new JLabel();
		panelW1.add(lblW1T17);
		lblW1T17.setIcon(wImg);
		
		lblW1T15 = new JLabel();
		panelW1.add(lblW1T15);
		lblW1T15.setIcon(wImg);
		
		lblW1T13 = new JLabel();
		panelW1.add(lblW1T13);
		lblW1T13.setIcon(wImg);
		
		lblW1T11 = new JLabel();
		panelW1.add(lblW1T11);
		lblW1T11.setIcon(wImg);
		
		lblW1T9 = new JLabel();
		panelW1.add(lblW1T9);
		lblW1T9.setIcon(wImg);
		
		lblW1T7 = new JLabel();
		panelW1.add(lblW1T7);
		lblW1T7.setIcon(wImg);
		
		lblW1T5 = new JLabel();
		panelW1.add(lblW1T5);
		lblW1T5.setIcon(wImg);
		
		lblW1T3 = new JLabel();
		panelW1.add(lblW1T3);
		lblW1T3.setIcon(wImg);
		
		lblW1T1 = new JLabel();
		panelW1.add(lblW1T1);
		lblW1T1.setIcon(wImg);
		
		lblW1T34 = new JLabel();
		panelW1.add(lblW1T34);
		lblW1T34.setIcon(wImg);
		
		lblW1T32 = new JLabel();
		panelW1.add(lblW1T32);
		lblW1T32.setIcon(wImg);
		
		lblW1T30 = new JLabel();
		panelW1.add(lblW1T30);
		lblW1T30.setIcon(wImg);
		
		lblW1T28 = new JLabel();
		panelW1.add(lblW1T28);
		lblW1T28.setIcon(wImg);
		
		lblW1T26 = new JLabel();
		panelW1.add(lblW1T26);
		lblW1T26.setIcon(wImg);
		
		lblW1T24 = new JLabel();
		panelW1.add(lblW1T24);
		lblW1T24.setIcon(wImg);
		
		lblW1T22 = new JLabel();
		panelW1.add(lblW1T22);
		lblW1T22.setIcon(wImg);
		
		lblW1T20 = new JLabel();
		panelW1.add(lblW1T20);
		lblW1T20.setIcon(wImg);
		
		lblW1T18 = new JLabel();
		panelW1.add(lblW1T18);
		lblW1T18.setIcon(wImg);
		
		lblW1T16 = new JLabel();
		panelW1.add(lblW1T16);
		lblW1T16.setIcon(wImg);
		
		lblW1T14 = new JLabel();
		panelW1.add(lblW1T14);
		lblW1T14.setIcon(wImg);
		
		lblW1T12 = new JLabel();
		panelW1.add(lblW1T12);
		lblW1T12.setIcon(wImg);
		
		lblW1T10 = new JLabel();
		panelW1.add(lblW1T10);
		lblW1T10.setIcon(wImg);
		
		lblW1T8 = new JLabel();
		panelW1.add(lblW1T8);
		lblW1T8.setIcon(wImg);
		
		lblW1T6 = new JLabel();
		panelW1.add(lblW1T6);
		lblW1T6.setIcon(wImg);
		
		lblW1T4 = new JLabel();
		panelW1.add(lblW1T4);
		lblW1T4.setIcon(wImg);
		
		lblW1T2 = new JLabel();
		panelW1.add(lblW1T2);
		lblW1T2.setIcon(wImg);
		
		
		
		
		
		panelW2 = new JPanel();
		panelW2.setBounds(490, 76, TILE_SMALL_HEIGHT*2, TILE_SMALL_WIDTH*(SIZE_WALL/2));
		panelW2.setBackground(COLOR_TRANSPARENT);
		panMidTable.add(panelW2);
		panelW2.setLayout(new GridLayout(17, 2, 0, 0));
		
		lblW2T1 = new JLabel();
		panelW2.add(lblW2T1);
		lblW2T1.setIcon(w2Img);
		
		lblW2T2 = new JLabel();
		panelW2.add(lblW2T2);
		lblW2T2.setIcon(w2Img);
		
		lblW2T3 = new JLabel();
		panelW2.add(lblW2T3);
		lblW2T3.setIcon(w2Img);
		
		lblW2T4 = new JLabel();
		panelW2.add(lblW2T4);
		lblW2T4.setIcon(w2Img);
		
		lblW2T5 = new JLabel();
		panelW2.add(lblW2T5);
		lblW2T5.setIcon(w2Img);
		
		lblW2T6 = new JLabel();
		panelW2.add(lblW2T6);
		lblW2T6.setIcon(w2Img);
		
		lblW2T7 = new JLabel();
		panelW2.add(lblW2T7);
		lblW2T7.setIcon(w2Img);
		
		lblW2T8 = new JLabel();
		panelW2.add(lblW2T8);
		lblW2T8.setIcon(w2Img);
		
		lblW2T9 = new JLabel();
		panelW2.add(lblW2T9);
		lblW2T9.setIcon(w2Img);
		
		lblW2T10 = new JLabel();
		panelW2.add(lblW2T10);
		lblW2T10.setIcon(w2Img);
		
		lblW2T11 = new JLabel();
		panelW2.add(lblW2T11);
		lblW2T11.setIcon(w2Img);
		
		lblW2T12 = new JLabel();
		panelW2.add(lblW2T12);
		lblW2T12.setIcon(w2Img);
		
		lblW2T13 = new JLabel();
		panelW2.add(lblW2T13);
		lblW2T13.setIcon(w2Img);
		
		lblW2T14 = new JLabel();
		panelW2.add(lblW2T14);
		lblW2T14.setIcon(w2Img);
		
		lblW2T15 = new JLabel();
		panelW2.add(lblW2T15);
		lblW2T15.setIcon(w2Img);
		
		lblW2T16 = new JLabel();
		panelW2.add(lblW2T16);
		lblW2T16.setIcon(w2Img);
		
		lblW2T17 = new JLabel();
		panelW2.add(lblW2T17);
		lblW2T17.setIcon(w2Img);
		
		lblW2T18 = new JLabel();
		panelW2.add(lblW2T18);
		lblW2T18.setIcon(w2Img);
		
		lblW2T19 = new JLabel();
		panelW2.add(lblW2T19);
		lblW2T19.setIcon(w2Img);
		
		lblW2T20 = new JLabel();
		panelW2.add(lblW2T20);
		lblW2T20.setIcon(w2Img);
		
		lblW2T21 = new JLabel();
		panelW2.add(lblW2T21);
		lblW2T21.setIcon(w2Img);
		
		lblW2T22 = new JLabel();
		panelW2.add(lblW2T22);
		lblW2T22.setIcon(w2Img);
		
		lblW2T23 = new JLabel();
		panelW2.add(lblW2T23);
		lblW2T23.setIcon(w2Img);
		
		lblW2T24 = new JLabel();
		panelW2.add(lblW2T24);
		lblW2T24.setIcon(w2Img);
		
		lblW2T25 = new JLabel();
		panelW2.add(lblW2T25);
		lblW2T25.setIcon(w2Img);
		
		lblW2T26 = new JLabel();
		panelW2.add(lblW2T26);
		lblW2T26.setIcon(w2Img);
		
		lblW2T27 = new JLabel();
		panelW2.add(lblW2T27);
		lblW2T27.setIcon(w2Img);
		
		lblW2T28 = new JLabel();
		panelW2.add(lblW2T28);
		lblW2T28.setIcon(w2Img);
		
		lblW2T29 = new JLabel();
		panelW2.add(lblW2T29);
		lblW2T29.setIcon(w2Img);
		
		lblW2T30 = new JLabel();
		panelW2.add(lblW2T30);
		lblW2T30.setIcon(w2Img);
		
		lblW2T31 = new JLabel();
		panelW2.add(lblW2T31);
		lblW2T31.setIcon(w2Img);
		
		lblW2T32 = new JLabel();
		panelW2.add(lblW2T32);
		lblW2T32.setIcon(w2Img);
		
		lblW2T33 = new JLabel();
		panelW2.add(lblW2T33);
		lblW2T33.setIcon(w2Img);
		
		lblW2T34 = new JLabel();
		panelW2.add(lblW2T34);
		lblW2T34.setIcon(w2Img);
		
		
		
		
		
		
		panelW3 = new JPanel();
		panelW3.setBounds(81, 0, TILE_SMALL_WIDTH*(SIZE_WALL/2), TILE_SMALL_HEIGHT*2);
		panelW3.setBackground(COLOR_TRANSPARENT);
		panMidTable.add(panelW3);
		panelW3.setLayout(new GridLayout(2, 17, 0, 0));
		
		lblW3T2 = new JLabel();
		panelW3.add(lblW3T2);
		lblW3T2.setIcon(w3Img);
		
		lblW3T4 = new JLabel();
		panelW3.add(lblW3T4);
		lblW3T4.setIcon(w3Img);
		
		lblW3T6 = new JLabel();
		panelW3.add(lblW3T6);
		lblW3T6.setIcon(w3Img);
		
		lblW3T8 = new JLabel();
		panelW3.add(lblW3T8);
		lblW3T8.setIcon(w3Img);
		
		lblW3T10 = new JLabel();
		panelW3.add(lblW3T10);
		lblW3T10.setIcon(w3Img);
		
		lblW3T12 = new JLabel();
		panelW3.add(lblW3T12);
		lblW3T12.setIcon(w3Img);
		
		lblW3T14 = new JLabel();
		panelW3.add(lblW3T14);
		lblW3T14.setIcon(w3Img);
		
		lblW3T16 = new JLabel();
		panelW3.add(lblW3T16);
		lblW3T16.setIcon(w3Img);
		
		lblW3T18 = new JLabel();
		panelW3.add(lblW3T18);
		lblW3T18.setIcon(w3Img);
		
		lblW3T20 = new JLabel();
		panelW3.add(lblW3T20);
		lblW3T20.setIcon(w3Img);
		
		lblW3T22 = new JLabel();
		panelW3.add(lblW3T22);
		lblW3T22.setIcon(w3Img);
		
		lblW3T24 = new JLabel();
		panelW3.add(lblW3T24);
		lblW3T24.setIcon(w3Img);
		
		lblW3T26 = new JLabel();
		panelW3.add(lblW3T26);
		lblW3T26.setIcon(w3Img);
		
		lblW3T28 = new JLabel();
		panelW3.add(lblW3T28);
		lblW3T28.setIcon(w3Img);
		
		lblW3T30 = new JLabel();
		panelW3.add(lblW3T30);
		lblW3T30.setIcon(w3Img);
		
		lblW3T32 = new JLabel();
		panelW3.add(lblW3T32);
		lblW3T32.setIcon(w3Img);
		
		lblW3T34 = new JLabel();
		panelW3.add(lblW3T34);
		lblW3T34.setIcon(w3Img);
		
		lblW3T1 = new JLabel();
		panelW3.add(lblW3T1);
		lblW3T1.setIcon(w3Img);
		
		lblW3T3 = new JLabel();
		panelW3.add(lblW3T3);
		lblW3T3.setIcon(w3Img);
		
		lblW3T5 = new JLabel();
		panelW3.add(lblW3T5);
		lblW3T5.setIcon(w3Img);
		
		lblW3T7 = new JLabel();
		panelW3.add(lblW3T7);
		lblW3T7.setIcon(w3Img);
		
		lblW3T9 = new JLabel();
		panelW3.add(lblW3T9);
		lblW3T9.setIcon(w3Img);
		
		lblW3T11 = new JLabel();
		panelW3.add(lblW3T11);
		lblW3T11.setIcon(w3Img);
		
		lblW3T13 = new JLabel();
		panelW3.add(lblW3T13);
		lblW3T13.setIcon(w3Img);
		
		lblW3T15 = new JLabel();
		panelW3.add(lblW3T15);
		lblW3T15.setIcon(w3Img);
		
		lblW3T17 = new JLabel();
		panelW3.add(lblW3T17);
		lblW3T17.setIcon(w3Img);
		
		lblW3T19 = new JLabel();
		panelW3.add(lblW3T19);
		lblW3T19.setIcon(w3Img);
		
		lblW3T21 = new JLabel();
		panelW3.add(lblW3T21);
		lblW3T21.setIcon(w3Img);
		
		lblW3T23 = new JLabel();
		panelW3.add(lblW3T23);
		lblW3T23.setIcon(w3Img);
		
		lblW3T25 = new JLabel();
		panelW3.add(lblW3T25);
		lblW3T25.setIcon(w3Img);
		
		lblW3T27 = new JLabel();
		panelW3.add(lblW3T27);
		lblW3T27.setIcon(w3Img);
		
		lblW3T29 = new JLabel();
		panelW3.add(lblW3T29);
		lblW3T29.setIcon(w3Img);
		
		lblW3T31 = new JLabel();
		panelW3.add(lblW3T31);
		lblW3T31.setIcon(w3Img);
		
		lblW3T33 = new JLabel();
		panelW3.add(lblW3T33);
		lblW3T33.setIcon(w3Img);
		
		
		
		
		
		
		panelW4 = new JPanel();
		panelW4.setBounds(0, 76, TILE_SMALL_HEIGHT*2, TILE_SMALL_WIDTH*(SIZE_WALL/2));
		panelW4.setBackground(COLOR_TRANSPARENT);
		panMidTable.add(panelW4);
		panelW4.setLayout(new GridLayout(17, 2, 0, 0));
		
		lblW4T34 = new JLabel();
		panelW4.add(lblW4T34);
		lblW4T34.setIcon(w4Img);
		
		lblW4T33 = new JLabel();
		panelW4.add(lblW4T33);
		lblW4T33.setIcon(w4Img);
		
		lblW4T32 = new JLabel();
		panelW4.add(lblW4T32);
		lblW4T32.setIcon(w4Img);
		
		lblW4T31 = new JLabel();
		panelW4.add(lblW4T31);
		lblW4T31.setIcon(w4Img);
		
		lblW4T30 = new JLabel();
		panelW4.add(lblW4T30);
		lblW4T30.setIcon(w4Img);
		
		lblW4T29 = new JLabel();
		panelW4.add(lblW4T29);
		lblW4T29.setIcon(w4Img);
		
		lblW4T28 = new JLabel();
		panelW4.add(lblW4T28);
		lblW4T28.setIcon(w4Img);
		
		lblW4T27 = new JLabel();
		panelW4.add(lblW4T27);
		lblW4T27.setIcon(w4Img);
		
		lblW4T26 = new JLabel();
		panelW4.add(lblW4T26);
		lblW4T26.setIcon(w4Img);
		
		lblW4T25 = new JLabel();
		panelW4.add(lblW4T25);
		lblW4T25.setIcon(w4Img);
		
		lblW4T24 = new JLabel();
		panelW4.add(lblW4T24);
		lblW4T24.setIcon(w4Img);
		
		lblW4T23 = new JLabel();
		panelW4.add(lblW4T23);
		lblW4T23.setIcon(w4Img);
		
		lblW4T22 = new JLabel();
		panelW4.add(lblW4T22);
		lblW4T22.setIcon(w4Img);
		
		lblW4T21 = new JLabel();
		panelW4.add(lblW4T21);
		lblW4T21.setIcon(w4Img);
		
		lblW4T20 = new JLabel();
		panelW4.add(lblW4T20);
		lblW4T20.setIcon(w4Img);
		
		lblW4T19 = new JLabel();
		panelW4.add(lblW4T19);
		lblW4T19.setIcon(w4Img);
		
		lblW4T18 = new JLabel();
		panelW4.add(lblW4T18);
		lblW4T18.setIcon(w4Img);
		
		lblW4T17 = new JLabel();
		panelW4.add(lblW4T17);
		lblW4T17.setIcon(w4Img);
		
		lblW4T16 = new JLabel();
		panelW4.add(lblW4T16);
		lblW4T16.setIcon(w4Img);
		
		lblW4T15 = new JLabel();
		panelW4.add(lblW4T15);
		lblW4T15.setIcon(w4Img);
		
		lblW4T14 = new JLabel();
		panelW4.add(lblW4T14);
		lblW4T14.setIcon(w4Img);
		
		lblW4T13 = new JLabel();
		panelW4.add(lblW4T13);
		lblW4T13.setIcon(w4Img);
		
		lblW4T12 = new JLabel();
		panelW4.add(lblW4T12);
		lblW4T12.setIcon(w4Img);
		
		lblW4T11 = new JLabel();
		panelW4.add(lblW4T11);
		lblW4T11.setIcon(w4Img);
		
		lblW4T10 = new JLabel();
		panelW4.add(lblW4T10);
		lblW4T10.setIcon(w4Img);
		
		lblW4T9 = new JLabel();
		panelW4.add(lblW4T9);
		lblW4T9.setIcon(w4Img);
		
		lblW4T8 = new JLabel();
		panelW4.add(lblW4T8);
		lblW4T8.setIcon(w4Img);
		
		lblW4T7 = new JLabel();
		panelW4.add(lblW4T7);
		lblW4T7.setIcon(w4Img);
		
		lblW4T6 = new JLabel();
		panelW4.add(lblW4T6);
		lblW4T6.setIcon(w4Img);
		
		lblW4T5 = new JLabel();
		panelW4.add(lblW4T5);
		lblW4T5.setIcon(w4Img);
		
		lblW4T4 = new JLabel();
		panelW4.add(lblW4T4);
		lblW4T4.setIcon(w4Img);
		
		lblW4T3 = new JLabel();
		panelW4.add(lblW4T3);
		lblW4T3.setIcon(w4Img);
		
		lblW4T2 = new JLabel();
		panelW4.add(lblW4T2);
		lblW4T2.setIcon(w4Img);
		
		lblW4T1 = new JLabel();
		panelW4.add(lblW4T1);
		lblW4T1.setIcon(w4Img);
		
		
		
		btnRand.addActionListener(new ActionListener(){
			Random randGen = new Random();
			final int RANDLIMIT = 38;
			public void actionPerformed(ActionEvent arg0){
				for (JLabel l: larryW1) l.setIcon(garryTiles[SEAT1][SMALL][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryW2) l.setIcon(garryTiles[SEAT2][SMALL][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryW3) l.setIcon(garryTiles[SEAT3][SMALL][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryW4) l.setIcon(garryTiles[SEAT4][SMALL][randGen.nextInt(RANDLIMIT)]);
			}
		});
		
		
		
		///////////
		
		
		
		
		////////////////////////////////////////////////////////////
		
		
		
		//load Tile labels into arrays
		larryW1[0] = lblW1T1;larryW1[1] = lblW1T2;larryW1[2] = lblW1T3;larryW1[3] = lblW1T4;larryW1[4] = lblW1T5;larryW1[5] = lblW1T6;larryW1[6] = lblW1T7;larryW1[7] = lblW1T8;larryW1[8] = lblW1T9;larryW1[9] = lblW1T10;larryW1[10] = lblW1T11;larryW1[11] = lblW1T12;larryW1[12] = lblW1T13;larryW1[13] = lblW1T14;larryW1[14] = lblW1T15;larryW1[15] = lblW1T16;larryW1[16] = lblW1T17;larryW1[17] = lblW1T18;larryW1[18] = lblW1T19;larryW1[19] = lblW1T20;larryW1[20] = lblW1T21;larryW1[21] = lblW1T22;larryW1[22] = lblW1T23;larryW1[23] = lblW1T24;larryW1[24] = lblW1T25;larryW1[25] = lblW1T26;larryW1[26] = lblW1T27;larryW1[27] = lblW1T28;larryW1[28] = lblW1T29;larryW1[29] = lblW1T30;larryW1[30] = lblW1T31;larryW1[31] = lblW1T32;larryW1[32] = lblW1T33;larryW1[33] = lblW1T34;
		larryW2[0] = lblW2T1;larryW2[1] = lblW2T2;larryW2[2] = lblW2T3;larryW2[3] = lblW2T4;larryW2[4] = lblW2T5;larryW2[5] = lblW2T6;larryW2[6] = lblW2T7;larryW2[7] = lblW2T8;larryW2[8] = lblW2T9;larryW2[9] = lblW2T10;larryW2[10] = lblW2T11;larryW2[11] = lblW2T12;larryW2[12] = lblW2T13;larryW2[13] = lblW2T14;larryW2[14] = lblW2T15;larryW2[15] = lblW2T16;larryW2[16] = lblW2T17;larryW2[17] = lblW2T18;larryW2[18] = lblW2T19;larryW2[19] = lblW2T20;larryW2[20] = lblW2T21;larryW2[21] = lblW2T22;larryW2[22] = lblW2T23;larryW2[23] = lblW2T24;larryW2[24] = lblW2T25;larryW2[25] = lblW2T26;larryW2[26] = lblW2T27;larryW2[27] = lblW2T28;larryW2[28] = lblW2T29;larryW2[29] = lblW2T30;larryW2[30] = lblW2T31;larryW2[31] = lblW2T32;larryW2[32] = lblW2T33;larryW2[33] = lblW2T34;
		larryW3[0] = lblW3T1;larryW3[1] = lblW3T2;larryW3[2] = lblW3T3;larryW3[3] = lblW3T4;larryW3[4] = lblW3T5;larryW3[5] = lblW3T6;larryW3[6] = lblW3T7;larryW3[7] = lblW3T8;larryW3[8] = lblW3T9;larryW3[9] = lblW3T10;larryW3[10] = lblW3T11;larryW3[11] = lblW3T12;larryW3[12] = lblW3T13;larryW3[13] = lblW3T14;larryW3[14] = lblW3T15;larryW3[15] = lblW3T16;larryW3[16] = lblW3T17;larryW3[17] = lblW3T18;larryW3[18] = lblW3T19;larryW3[19] = lblW3T20;larryW3[20] = lblW3T21;larryW3[21] = lblW3T22;larryW3[22] = lblW3T23;larryW3[23] = lblW3T24;larryW3[24] = lblW3T25;larryW3[25] = lblW3T26;larryW3[26] = lblW3T27;larryW3[27] = lblW3T28;larryW3[28] = lblW3T29;larryW3[29] = lblW3T30;larryW3[30] = lblW3T31;larryW3[31] = lblW3T32;larryW3[32] = lblW3T33;larryW3[33] = lblW3T34;
		larryW4[0] = lblW4T1;larryW4[1] = lblW4T2;larryW4[2] = lblW4T3;larryW4[3] = lblW4T4;larryW4[4] = lblW4T5;larryW4[5] = lblW4T6;larryW4[6] = lblW4T7;larryW4[7] = lblW4T8;larryW4[8] = lblW4T9;larryW4[9] = lblW4T10;larryW4[10] = lblW4T11;larryW4[11] = lblW4T12;larryW4[12] = lblW4T13;larryW4[13] = lblW4T14;larryW4[14] = lblW4T15;larryW4[15] = lblW4T16;larryW4[16] = lblW4T17;larryW4[17] = lblW4T18;larryW4[18] = lblW4T19;larryW4[19] = lblW4T20;larryW4[20] = lblW4T21;larryW4[21] = lblW4T22;larryW4[22] = lblW4T23;larryW4[23] = lblW4T24;larryW4[24] = lblW4T25;larryW4[25] = lblW4T26;larryW4[26] = lblW4T27;larryW4[27] = lblW4T28;larryW4[28] = lblW4T29;larryW4[29] = lblW4T30;larryW4[30] = lblW4T31;larryW4[31] = lblW4T32;larryW4[32] = lblW4T33;larryW4[33] = lblW4T34;
		
		//put wall labels in a single array of 136 tiles
		int i = 0;
		for (JLabel[] lar: larryWalls) 
			for (JLabel l: lar)
				larryWallAll[i++] = l;
		
		
	}
	
	
	
}
