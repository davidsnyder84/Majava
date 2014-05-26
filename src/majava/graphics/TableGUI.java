
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
Class: TableGUI
a GUI for viewing and interacting with the game
	
methods:
	public:
		mutators:
		updateEverything - updates everything to reflect the current state of the game
		blankEverything - blanks labels and images
		showExclamation - displays the received exclamation and pauses briefly
		
		getClickCall - prompts a player to click a button to make a call
		getClickTurnAction - prompts a player to click a button to choose an action
		
		
		accessors:
		resultClickCallWasNone, etc - returns true if the specified call was clicked
		
		resultClickTurnActionWasDiscard, etc - returns true if the specified action was clicked
		getResultClickedDiscard - returns the discard index the player clicked on
		
		
	other:
	syncWithRoundTracker - associates the viewer with the round tracker
*/
public abstract class TableGUI extends JFrame{
	
	private static final long serialVersionUID = 9210599763112170767L;
	
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~BEGIN CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	//Control constants
	protected static final boolean DEFAULT_OPTION_REVEAL_WALL = false;
	protected static final boolean DEFAULT_OPTION_REVEAL_HANDS = false;
	
	//debug buttons
	protected static final boolean DEBUG_BUTTONS_VISIBLE = true;
	
	private static final String STRING_WINDOW_TITLE = "The Beaver";
	
	private static final int WINDOW_WIDTH = 1120 + (-62*2 - 6);
	private static final int WINDOW_HEIGHT = 726 + 6 + (-62*2 + 25 + 18);
//	private static final int WINDOW_WIDTH = 1150 - 30;
//	private static final int WINDOW_HEIGHT = 850 - 70 - 54 + 6;
	
	protected static final int WINDOW_TOP_BORDER_SIZE_WITH_RESIZE = 30;
	protected static final int WINDOW_TOP_BORDER_SIZE = 26;
	protected static final int WINDOW_SIDE_BORDER_SIZE_WITH_RESIZE = 8;
	protected static final int WINDOW_SIDE_BORDER_SIZE = 2;
	protected static final int WINDOW_BOTTOM_BORDER_SIZE = 8;
	protected static final int WINDOW_MENU_SIZE = 23;
	
	
	

	
	protected static final int TILE_BIG_WIDTH = 30;
	protected static final int TILE_BIG_HEIGHT = 41;
	protected static final int TILE_SMALL_WIDTH = 23;
	protected static final int TILE_SMALL_HEIGHT = 31;
	protected static final int PONDPANEL_NEW_WIDTH = TILE_SMALL_WIDTH*6;
	protected static final int PONDPANEL_NEW_HEIGHT = TILE_SMALL_HEIGHT*4;
	
	protected static final int POND_PANEL_WIDTH = (TILE_SMALL_WIDTH + 4) * 6;
	protected static final int POND_PANEL_HEIGHT = PONDPANEL_NEW_HEIGHT;
	
	
	
	
	protected static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);
	protected static final Color COLOR_PURPLISH =  new Color(210,0, 210, 30);
	
	protected static final Color COLOR_TABLE = new Color(0, 140, 0, 100);
	protected static final Color COLOR_MID_TABLE = COLOR_TRANSPARENT;
	protected static final Color COLOR_SIDEBAR = new Color(0, 255, 0, 100);
	protected static final Color COLOR_RINF_PANEL = new Color(0,255,255,35);
	protected static final Color COLOR_RIND = new Color(0,155,155,35);
	protected static final Color COLOR_TURN_INDICATOR = Color.YELLOW;
	protected static final Color COLOR_CALL_PANEL = COLOR_PURPLISH;
	protected static final Color COLOR_EXCLAMATION = new Color(210, 100, 210);
	
	protected static final Color COLOR_POND_CALLED_TILE = new Color(250, 0, 0, 250);
	protected static final Color COLOR_POND_RIICHI_TILE = new Color(0, 0, 250, 250);
	protected static final Color COLOR_POND_DISCARD_TILE = Color.YELLOW;

	
	protected static final int DEFAULT_SLEEP_TIME_EXCLAMATION = 1500;
	private static final int[][] EXCLAMATION_LOCS =  {{99, 594}, {570, 298}, {480, 36}, {3, 298}};
	
	
	
	protected static final int NUM_PLAYERS = 4;
	protected static final int SIZE_HAND = 14;
	protected static final int SIZE_MELDPANEL = 4;
	protected static final int SIZE_MELD = 4;
	protected static final int SIZE_WALL = 34;
	protected static final int SIZE_DEAD_WALL = 14;
	protected static final int OFFSET_DEAD_WALL = SIZE_WALL * 4 - SIZE_DEAD_WALL;
	protected static final int POS_DORA_1 = 8;
	protected static final int SIZE_POND = 24;
	protected static final int SIZE_LARRY_INFOPLAYER = 3;
	protected static final int SIZE_LARRY_INFOROUND = 2;
	
	
	private static final int SIZE_GARRY_TILES = 38;
	private static final int SIZE_GARRY_WINDS = 4;
	private static final int SIZE_GARRY_OTHER = 1;
	private static final int SIZE_GARRY_OMAKE = 5;
	private static final int SIZE_NUM_SEATS = 4;
	private static final int GARRYINDEX_RED5M = 35;
	private static final int GARRYINDEX_RED5P = 36;
	private static final int GARRYINDEX_RED5S = 37;
	private static final int GARRYINDEX_TILEBACK = 0;
	
	

	private static final int LARRY_INFOROUND_ROUNDWIND = 0;
	private static final int LARRY_INFOROUND_ROUNDNUM = 1;
	private static final int LARRY_INFOROUND_BONUSNUM = 2;
	
	private static final int LARRY_INFOPLAYER_SEATWIND = 0;
	private static final int LARRY_INFOPLAYER_POINTS = 1;
	private static final int LARRY_INFOPLAYER_RIICHI = 2;
	
	private static final int BARRY_TACTIONS_RIICHI = 0;
	private static final int BARRY_TACTIONS_ANKAN = 1;
	private static final int BARRY_TACTIONS_MINKAN = 2;
	private static final int BARRY_TACTIONS_TSUMO = 3;

	private static final int GARRYINDEX_OTHER_RIICHI = 0;
	

	private static final int NUMBER_OF_DIFFERENT_TILES = 34;
	
	
	//click action constants
	protected static final int NO_CALL_CHOSEN = -1;
	protected static final int CALL_NONE = 0;
	protected static final int CALL_CHI_L = 1;
	protected static final int CALL_CHI_M = 2;
	protected static final int CALL_CHI_H = 3;
	protected static final int CALL_PON = 4;
	protected static final int CALL_KAN = 5;
	protected static final int CALL_RON = 6;
	protected static final int CALL_CHI = 7;
	private static final int DEFAULT_CALL = CALL_NONE;
	
	protected static final int NO_ACTION_CHOSEN = -1;
	protected static final int TURN_ACTION_DISCARD = -10;
	protected static final int TURN_ACTION_ANKAN = -20;
	protected static final int TURN_ACTION_MINKAN = -30;
	protected static final int TURN_ACTION_RIICHI = -40;
	protected static final int TURN_ACTION_TSUMO = -50;

	protected static final int NO_DISCARD_CHOSEN = -1;
	protected static final int DEFAULT_DISCARD = 0;
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	
	
	
	
	
	
	/*......................................BEGIN LABEL ARRAYS......................................*/
	
	protected JLabel[] larryH1 = new JLabel[SIZE_HAND];
	protected JLabel[] larryH2 = new JLabel[SIZE_HAND];
	protected JLabel[] larryH3 = new JLabel[SIZE_HAND];
	protected JLabel[] larryH4 = new JLabel[SIZE_HAND];
	protected JLabel[][] larryHands = {larryH1, larryH2, larryH3, larryH4};
	
	protected JLabel[] larryH1M1 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH1M2 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH1M3 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH1M4 = new JLabel[SIZE_MELD];
	
	protected JLabel[] larryH2M1 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH2M2 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH2M3 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH2M4 = new JLabel[SIZE_MELD];
	
	protected JLabel[] larryH3M1 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH3M2 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH3M3 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH3M4 = new JLabel[SIZE_MELD];
	
	protected JLabel[] larryH4M1 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH4M2 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH4M3 = new JLabel[SIZE_MELD];
	protected JLabel[] larryH4M4 = new JLabel[SIZE_MELD];
	
	protected JLabel[][] larryH1Ms = {larryH1M1, larryH1M2, larryH1M3, larryH1M4};
	protected JLabel[][] larryH2Ms = {larryH2M1, larryH2M2, larryH2M3, larryH2M4};
	protected JLabel[][] larryH3Ms = {larryH3M1, larryH3M2, larryH3M3, larryH3M4};
	protected JLabel[][] larryH4Ms = {larryH4M1, larryH4M2, larryH4M3, larryH4M4};
	protected JLabel[][][] larryHandMelds = {larryH1Ms, larryH2Ms, larryH3Ms, larryH4Ms};
		
	protected JLabel[] larryP1 = new JLabel[SIZE_POND];
	protected JLabel[] larryP2 = new JLabel[SIZE_POND];
	protected JLabel[] larryP3 = new JLabel[SIZE_POND];
	protected JLabel[] larryP4 = new JLabel[SIZE_POND];
	protected JLabel[][] larryPonds = {larryP1, larryP2, larryP3, larryP4};
	
	
	protected JLabel[] larryInfoP1 = new JLabel[SIZE_LARRY_INFOPLAYER];
	protected JLabel[] larryInfoP2 = new JLabel[SIZE_LARRY_INFOPLAYER];
	protected JLabel[] larryInfoP3 = new JLabel[SIZE_LARRY_INFOPLAYER];
	protected JLabel[] larryInfoP4 = new JLabel[SIZE_LARRY_INFOPLAYER];
	
	//larryInfoPlayers[player number][0 = seatwind, 1 = points, 2 = riichiStick]
	protected JLabel[][] larryInfoPlayers = {larryInfoP1, larryInfoP2, larryInfoP3, larryInfoP4};
	
	//0 = roundWind, 1 = roundNumber
	protected JLabel[] larryInfoRound = new JLabel[SIZE_LARRY_INFOROUND];
	
	protected JPanel[] parryTurnInds = new JPanel[NUM_PLAYERS];
	
	

	protected JButton[] barryCalls = new JButton[8];
	protected JButton[] barryTActions = new JButton[4];
	protected JLabel[] larryDW = new JLabel[SIZE_DEAD_WALL];
	
	protected JPanel panResult;
	protected JLabel lblResult;
	
	protected JLabel lblFun;
	protected JLabel lblExclamation;
	
	protected JLabel lblWallTilesLeft;
	
	protected JPanel panTable;
	protected JPanel panSidebar;
	
	
	

	protected JPanel panWallSummary;
	protected JPanel panCalls;
	
	protected JPanel panP1, panP2, panP3, panP4;
	protected JPanel panRoundInfo;
	protected JPanel panRndInfBackground;
	protected JPanel panMidTable;
	
	protected JPanel panPlayer1, panPlayer2, panPlayer3, panPlayer4;
	protected JButton btnBlank, btnRand;
	/*......................................END LABEL ARRAYS......................................*/
	
	
	
	
	
	
	/*+++++++++++++++++++++++++++++++++++++++BEGIN IMAGE ARRAYS+++++++++++++++++++++++++++++++++++++++*/
	
	protected ImageIcon[] garryTileS1big = new ImageIcon[SIZE_GARRY_TILES];
	protected ImageIcon[] garryTileS1small = new ImageIcon[SIZE_GARRY_TILES];
	protected ImageIcon[][] garryTileS1 = {garryTileS1big, garryTileS1small};
	
	protected ImageIcon[] garryTileS2big = new ImageIcon[SIZE_GARRY_TILES];
	protected ImageIcon[] garryTileS2small = new ImageIcon[SIZE_GARRY_TILES];
	protected ImageIcon[][] garryTileS2 = {garryTileS2big, garryTileS2small};
	
	protected ImageIcon[] garryTileS3big = new ImageIcon[SIZE_GARRY_TILES];
	protected ImageIcon[] garryTileS3small = new ImageIcon[SIZE_GARRY_TILES];
	protected ImageIcon[][] garryTileS3 = {garryTileS3big, garryTileS3small};
	
	protected ImageIcon[] garryTileS4big = new ImageIcon[SIZE_GARRY_TILES];
	protected ImageIcon[] garryTileS4small = new ImageIcon[SIZE_GARRY_TILES];
	protected ImageIcon[][] garryTileS4 = {garryTileS4big, garryTileS4small};
	
	//garryTiles[seat number][0=big,1=small][tile number]
	protected ImageIcon[][][] garryTiles = {garryTileS1, garryTileS2, garryTileS3, garryTileS4};
	
	
	
	protected ImageIcon[] garryWindsBig = new ImageIcon[SIZE_GARRY_WINDS];
	protected ImageIcon[] garryWindsSmall = new ImageIcon[SIZE_GARRY_WINDS];
	//garryWinds[0=big,1=small][E,S,W,N]
	protected ImageIcon[][] garryWinds = {garryWindsBig, garryWindsSmall}; 
	
	
	//0 = riichi stick, 1 = sheepy2
	protected ImageIcon[] garryOther = new ImageIcon[SIZE_GARRY_OTHER];
	
	//logos, misc images
	protected ImageIcon[] garryOmake = new ImageIcon[SIZE_GARRY_OMAKE];
	
	
	
	protected static final int SEAT1 = 0;
	protected static final int SEAT2 = 1;
	protected static final int SEAT3 = 2;
	protected static final int SEAT4 = 3;
	protected static final int EAST = 0;
	protected static final int SOUTH = 1;
	protected static final int WEST = 2;
	protected static final int NORTH = 3;
	protected static final int BIG = 0;
	protected static final int SMALL = 1;
	
	/*+++++++++++++++++++++++++++++++++++++++END IMAGE ARRAYS+++++++++++++++++++++++++++++++++++++++*/
	
	
	
	
	
	
	
	
	/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^BEGIN MEMBER VARIABLES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
	
	protected JPanel contentPane;
	
	
	protected boolean mOptionRevealWall;
	protected boolean mOptionRevealHands;
	protected boolean[] mRevealHands;
	
	
	
	

	private static final int NUM_PLAYERS_TO_TRACK = 4;
	private class PlayerTracker{
		private Player player;
		
		private TileList tilesH;
		private TileList tilesP;

		//private Wind seatWind;
		//private int points;
		//private boolean riichiStatus;
		//private String playerName;
		
		//private ArrayList<Meld> melds = new ArrayList<Meld>(NUM_MELDS_TO_TRACK);
		
		public PlayerTracker(Player p, TileList tH, TileList tP){
			player = p;
			tilesH = tH;
			tilesP = tP;
		}
	}

	protected Tile[] tilesW;
	
	
	private PlayerTracker[] mPTrackers;
	protected RoundTracker mRoundTracker;
	
	
	
	private JLabel mMostRecentDiscard;

	private int mChosenCall;
	private int mChosenTurnAction;
	private int mChosenDiscard;
	
	/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^END MEMBER VARIABLES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static final int NULL_TILE_IMAGE_ID = -1;
	
	private ImageIcon __getImageIcon(TileList tList, int index, int seatNum, int tileSize, boolean reveal){
		if (tList.size() <= index) return null;
		
		if (!reveal) return garryTiles[seatNum][tileSize][GARRYINDEX_TILEBACK];
		
		int id =__getImageID(tList.get(index));
		if (id == NULL_TILE_IMAGE_ID) return null;
		
		return garryTiles[seatNum][tileSize][id];
	}
	private ImageIcon __getImageIcon(TileList tList, int index, int seatNum, int tileSize){return __getImageIcon(tList, index, seatNum, tileSize, true);}
	
	
	private ImageIcon __getImageIconPond(TileList tList, int index, int seatNum){
		if (tList.size() <= index) return null;
		
		int id =__getImageID(tList.get(index));
		if (id == NULL_TILE_IMAGE_ID) return null;
		
		
		//mark label if the pond tile has been called
		if ( ((PondTile)(tList.get(index))).wasCalled() ){
			larryPonds[seatNum][index].setOpaque(true); larryPonds[seatNum][index].setBackground(COLOR_POND_CALLED_TILE);
		}
//		mark label if 
//		if ( ((PondTile)(tList.get(index))).isRiichiTile() ){
//			seatNum = (seatNum + 1) % NUM_PLAYERS;
//		}
		
		return garryTiles[seatNum][SMALL][id];
	}
	
	
	//gets icon for wall tile array
	protected ImageIcon __getImageIconWall(Tile[] tList, int index, int seatNum, boolean reveal){
		if (seatNum == SEAT2) seatNum = SEAT4; else if (seatNum == SEAT4) seatNum = SEAT2;
		
		int id = __getImageID(tList[index]);
		if (id == NULL_TILE_IMAGE_ID) return null;
		
		if (!reveal) id = GARRYINDEX_TILEBACK;
		return garryTiles[seatNum][SMALL][id];
	}
	protected ImageIcon __getImageIconWall(Tile[] tList, int index, int seatNum){return __getImageIconWall(tList, index, seatNum, true);}
	
	
	//returns the image ID number for the tile at the given index of tList
	private int __getImageID(Tile t){
		if (t == null) return NULL_TILE_IMAGE_ID;
		if (t.isRedDora())
			switch(t.getId()){
			case 5: return NUMBER_OF_DIFFERENT_TILES + 1;
			case 14: return NUMBER_OF_DIFFERENT_TILES + 2;
			case 23: return NUMBER_OF_DIFFERENT_TILES + 3;
		}
		return t.getId();
	}
	
	//gets icon for the given wind of the given size
	private ImageIcon __getImageIconWind(Wind wind, int windSize){
		int windNum = -1;
		windNum = wind.getNum();
		
		return garryWinds[windSize][windNum];
	}
	
	
	
	private int mNewTurn = -1;
	private int mOldTurn = -1;
	
	private void __updateDiscardMarker(){

//		//erase the old discard marker if the turn has changed
//		mNewTurn = mRoundTracker.whoseTurn();
////		if (mNewTurn != mOldTurn && mOldTurn >= 0 && !mPTrackers[mOldTurn].tilesP.isEmpty()){__getLastLabelInPond(mOldTurn).setOpaque(false); __getLastLabelInPond(mOldTurn).setBackground(COLOR_TRANSPARENT);}
//		if (mOldTurn >= 0 && !mPTrackers[mOldTurn].tilesP.isEmpty()){__getLastLabelInPond(mOldTurn).setOpaque(false); __getLastLabelInPond(mOldTurn).setBackground(COLOR_TRANSPARENT);}
//		//mark the new discard marker
//		if (!mPTrackers[mNewTurn].tilesP.isEmpty() && mPTrackers[mNewTurn].player.needsDraw()){__getLastLabelInPond(mNewTurn).setOpaque(true); __getLastLabelInPond(mNewTurn).setBackground(COLOR_POND_DISCARD_TILE);}
//		mOldTurn = mNewTurn;
	
		
		//always erase the old discard marker
		mNewTurn = mRoundTracker.whoseTurn();
		if (mOldTurn >= 0 && !mPTrackers[mOldTurn].tilesP.isEmpty()){__getLastLabelInPond(mOldTurn).setOpaque(false); __getLastLabelInPond(mOldTurn).setBackground(COLOR_TRANSPARENT);}
		
		if (!mPTrackers[mNewTurn].tilesP.isEmpty() && mPTrackers[mNewTurn].player.needsDraw()){__getLastLabelInPond(mNewTurn).setOpaque(true); __getLastLabelInPond(mNewTurn).setBackground(COLOR_POND_DISCARD_TILE);}
		
//		if (mNewTurn != mOldTurn){
//			if (mOldTurn >= 0 && !mPTrackers[mOldTurn].tilesP.isEmpty()){__getLastLabelInPond(mOldTurn).setOpaque(false); __getLastLabelInPond(mOldTurn).setBackground(COLOR_TRANSPARENT);}
//			//mark the new discard marker
//			if (!mPTrackers[mNewTurn].tilesP.isEmpty() && mPTrackers[mNewTurn].player.needsDraw()){__getLastLabelInPond(mNewTurn).setOpaque(true); __getLastLabelInPond(mNewTurn).setBackground(COLOR_POND_DISCARD_TILE);}
//		}
		mOldTurn = mNewTurn;
		
	}
	
	public void updateEverything(){
		
		__updateDiscardMarker();
		
		int currentPlayer, currentTile;
		int currentMeld;
		
		
		//show end of round result if round is over
		if (mRoundTracker.roundIsOver()){
			lblResult.setText(mRoundTracker.getRoundResultString());
			panResult.setVisible(true);
			
			for (int i = 0; i < mRevealHands.length; i++) mRevealHands[i] = true;
		}
		
		
		
		//update hands
		for (currentPlayer = 0; currentPlayer < NUM_PLAYERS; currentPlayer++){
			for (currentTile = 0; currentTile < SIZE_HAND; currentTile++){
				larryHands[currentPlayer][currentTile].setIcon(__getImageIcon(mPTrackers[currentPlayer].tilesH, currentTile, currentPlayer, BIG, mRevealHands[currentPlayer]));
			}
		}
		
		
		//update ponds
		for (currentPlayer = 0; currentPlayer < NUM_PLAYERS; currentPlayer++){
			for (currentTile = 0; currentTile < SIZE_POND; currentTile++){
				larryPonds[currentPlayer][currentTile].setIcon(__getImageIconPond(mPTrackers[currentPlayer].tilesP, currentTile, currentPlayer));
			}
		}
		
		
		//update dead wall
		for (currentTile = 0; currentTile < SIZE_DEAD_WALL; currentTile++){
			larryDW[currentTile].setIcon(__getImageIconWall(tilesW, currentTile + OFFSET_DEAD_WALL, SEAT1, mOptionRevealWall));
		}
		for (currentTile = POS_DORA_1; currentTile >= 2*(4 - mRoundTracker.getNumKansMade()); currentTile -= 2){
			larryDW[currentTile].setIcon(__getImageIconWall(tilesW, currentTile + OFFSET_DEAD_WALL, SEAT1));
		}
		lblWallTilesLeft.setText(Integer.toString(mRoundTracker.getNumTilesLeftInWall()));
		
		
		//update melds
		ArrayList<Meld> meldList = null;
		TileList tList = null;
		for (currentPlayer = 0; currentPlayer < NUM_PLAYERS; currentPlayer++){
			meldList = mPTrackers[currentPlayer].player.getMelds();
			for (currentMeld = 0; currentMeld < meldList.size(); currentMeld++){
				tList = meldList.get(currentMeld).getAllTiles();
				for (currentTile = 0; currentTile < SIZE_MELD; currentTile++)
					larryHandMelds[currentPlayer][currentMeld][currentTile].setIcon(__getImageIcon(tList, currentTile, currentPlayer, SMALL));
			}
		}
		
		
		//update player info
		for (currentPlayer = 0; currentPlayer < NUM_PLAYERS; currentPlayer++){
			larryInfoPlayers[currentPlayer][LARRY_INFOPLAYER_SEATWIND].setIcon(__getImageIconWind(mPTrackers[currentPlayer].player.getSeatWind(), SMALL));
			larryInfoPlayers[currentPlayer][LARRY_INFOPLAYER_POINTS].setText(Integer.toString(mPTrackers[currentPlayer].player.getPoints()));
			if (mPTrackers[currentPlayer].player.getRiichiStatus())
				larryInfoPlayers[currentPlayer][LARRY_INFOPLAYER_RIICHI].setIcon(garryOther[GARRYINDEX_OTHER_RIICHI]);
		}
		
		
		//update round info
		larryInfoRound[LARRY_INFOROUND_ROUNDWIND].setIcon(__getImageIconWind(mRoundTracker.getRoundWind(), BIG));
		larryInfoRound[LARRY_INFOROUND_ROUNDNUM].setText(Integer.toString(mRoundTracker.getRoundNum()));
		
		//update turn indicators
		for (currentPlayer = 0; currentPlayer < NUM_PLAYERS; currentPlayer++){
			parryTurnInds[currentPlayer].setVisible(mRoundTracker.whoseTurn() == currentPlayer);
		}
		
		
		repaint();
	}
	
	
	
	
	
	//replaces all imageicons with null
	public void blankEverything(){
		
		//exclamation
		lblExclamation.setVisible(false);
		
		for (JLabel[] lar: larryHands)	//hands
			for (JLabel l: lar) l.setIcon(null);
		
		//ponds
		for (JLabel[] lar: larryPonds)
			for (JLabel l: lar) {l.setIcon(null); l.setOpaque(false);}
		
		//melds
		for (JLabel[][] larg: larryHandMelds)
			for (JLabel[] lar: larg)
				for (JLabel l: lar)
					l.setIcon(null);
		
		//round info
		larryInfoRound[0].setIcon(null);
		larryInfoRound[1].setText(null);
		
		//turn indicators
		for (JPanel p: parryTurnInds)
			p.setVisible(false);
		
		//round result
		panResult.setVisible(false);
		
		//player info
		for (JLabel[] player: larryInfoPlayers){
			player[0].setIcon(null);
			player[1].setText("0");
			player[2].setIcon(null);
		}
		
		//call buttons
		for (JButton b: barryCalls)
			b.setVisible(false);
		
		//turn action buttons
		for (JButton b: barryTActions)
			b.setVisible(false);
		
		//dead wall
		for (JLabel l: larryDW)
			l.setIcon(null);
		
		lblWallTilesLeft.setText("0");
		
		repaint();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public boolean getClickCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){
		
		mChosenCall = NO_CALL_CHOSEN;
		boolean onlyOneChiPossible = ((canChiL ^ canChiM ^ canChiH) ^ (canChiL && canChiM && canChiH));
		int chiType = -1;
		
		for (JButton b: barryCalls) b.setVisible(false);
		
		
		barryCalls[CALL_NONE].setVisible(true);
		
		//if only one type of chi is possible, just show a single Chi button
		if (onlyOneChiPossible){
			barryCalls[CALL_CHI].setVisible(true);
			
			if (canChiL) chiType = CALL_CHI_L;
			else if (canChiM) chiType = CALL_CHI_M;
			else if (canChiH) chiType = CALL_CHI_H;
		}
		else{
			//else, show multiple chi buttons
			barryCalls[CALL_CHI_L].setVisible(canChiL);
			barryCalls[CALL_CHI_M].setVisible(canChiM);
			barryCalls[CALL_CHI_H].setVisible(canChiH);
		}
		
		barryCalls[CALL_PON].setVisible(canPon);
		barryCalls[CALL_KAN].setVisible(canKan);
		barryCalls[CALL_RON].setVisible(canRon);
		
		repaint();
		
		
		while (mChosenCall == NO_CALL_CHOSEN);//intentionally blank
		
		if (mChosenCall == CALL_CHI) mChosenCall = chiType;
		
		for (JButton b: barryCalls) b.setVisible(false);
		return (mChosenCall != NO_CALL_CHOSEN);
	}
	public boolean resultClickCallWasNone(){return (mChosenCall == NO_CALL_CHOSEN);}
	public boolean resultClickCallWasChiL(){return (mChosenCall == CALL_CHI_L);}
	public boolean resultClickCallWasChiM(){return (mChosenCall == CALL_CHI_M);}
	public boolean resultClickCallWasChiH(){return (mChosenCall == CALL_CHI_H);}
	public boolean resultClickCallWasPon(){return (mChosenCall == CALL_PON);}
	public boolean resultClickCallWasKan(){return (mChosenCall == CALL_KAN);}
	public boolean resultClickCallWasRon(){return (mChosenCall == CALL_RON);}
	
	
	
	

	
	
	
	
	private class CallListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Chi-L")) mChosenCall = CALL_CHI_L;
			else if (command.equals("Chi-M")) mChosenCall = CALL_CHI_M;
			else if (command.equals("Chi-H")) mChosenCall = CALL_CHI_H;
			else if (command.equals("Pon")) mChosenCall = CALL_PON;
			else if (command.equals("Kan")) mChosenCall = CALL_KAN;
			else if (command.equals("Ron")) mChosenCall = CALL_RON;
			else if (command.equals("None")) mChosenCall = CALL_NONE;
			else if (command.equals("Chi")) mChosenCall = CALL_CHI;
		}		
	}
	
	
	
	
	
	private class TurnActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Ankan")) mChosenTurnAction = TURN_ACTION_ANKAN;
			else if (command.equals("Minkan")) mChosenTurnAction = TURN_ACTION_MINKAN;
			else if (command.equals("Riichi")) mChosenTurnAction = TURN_ACTION_RIICHI;
			else if (command.equals("Tsumo")) mChosenTurnAction = TURN_ACTION_TSUMO;
		}		
	}
	
	

	
	
	private void __setDiscardChosen(int discardIndex){
		mChosenTurnAction = TURN_ACTION_DISCARD;
		mChosenDiscard = discardIndex;
	}
	
	
	public void getClickTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){
		
		mChosenTurnAction = NO_ACTION_CHOSEN;
		mChosenDiscard = NO_DISCARD_CHOSEN;
		
		//add appropriate turn action buttons
		barryTActions[BARRY_TACTIONS_RIICHI].setVisible(canRiichi);
		barryTActions[BARRY_TACTIONS_ANKAN].setVisible(canAnkan);
		barryTActions[BARRY_TACTIONS_MINKAN].setVisible(canMinkan);
		barryTActions[BARRY_TACTIONS_TSUMO].setVisible(canTsumo);
		
		
		mChosenTurnAction = NO_ACTION_CHOSEN;
		while (mChosenTurnAction == NO_ACTION_CHOSEN);//intentionally blank
		
		if (mChosenDiscard > handSize) __setDiscardChosen(DEFAULT_DISCARD);
		if (mChosenDiscard == DEFAULT_DISCARD) mChosenDiscard = handSize;
		
		
		for (JButton b: barryTActions) b.setVisible(false);
	}
	public boolean resultClickTurnActionWasDiscard(){return (mChosenTurnAction == TURN_ACTION_DISCARD);}
	public boolean resultClickTurnActionWasAnkan(){return (mChosenTurnAction == TURN_ACTION_ANKAN);}
	public boolean resultClickTurnActionWasMinkan(){return (mChosenTurnAction == TURN_ACTION_MINKAN);}
	public boolean resultClickTurnActionWasRiichi(){return (mChosenTurnAction == TURN_ACTION_RIICHI);}
	public boolean resultClickTurnActionWasTsumo(){return (mChosenTurnAction == TURN_ACTION_TSUMO);}
	
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int getResultClickedDiscard(){
		if (resultClickTurnActionWasDiscard()) return mChosenDiscard;
		else return NO_DISCARD_CHOSEN;
	}
	
	
	
	
	protected void showExclamation(String exclamation, int x, int y, int sleepTime){
		
		if (mMostRecentDiscard != null) {mMostRecentDiscard.setOpaque(true); mMostRecentDiscard.setBackground(COLOR_POND_DISCARD_TILE);}
		
		//show a label
		lblExclamation.setText(exclamation);
		lblExclamation.setBounds(x, y, lblExclamation.getWidth(), lblExclamation.getHeight());
		lblExclamation.setVisible(true);
		
		//pause
		Pauser.pauseFor(sleepTime);
		
		//get rid of label
		lblExclamation.setVisible(false);
	}
	public void showExclamation(String exclamation, int seatNum, int sleepTime){showExclamation(exclamation, EXCLAMATION_LOCS[seatNum][0], EXCLAMATION_LOCS[seatNum][1], sleepTime);}
	public void showExclamation(String exclamation, int seatNum){showExclamation(exclamation, seatNum, DEFAULT_SLEEP_TIME_EXCLAMATION);}
	
	
	
	
	
	private JLabel __getLastLabelInPond(int seatNum){
		if (mPTrackers[seatNum].tilesP.isEmpty()) return null;
		else return larryPonds[seatNum][mPTrackers[seatNum].tilesP.size() - 1];
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, TileList[] pHandTiles, TileList[] pPondTiles, Tile[] trackerTilesW){
		
		mRoundTracker = rTracker;
		
		mPTrackers = new PlayerTracker[NUM_PLAYERS_TO_TRACK];
		for (int i = 0; i < NUM_PLAYERS_TO_TRACK; i++)
			mPTrackers[i] = new PlayerTracker(pPlayers[i], pHandTiles[i], pPondTiles[i]);
		
		
		mRevealHands = new boolean[NUM_PLAYERS_TO_TRACK];
		for (int i = 0; i < mRevealHands.length; i++) mRevealHands[i] = (mOptionRevealHands || mPTrackers[i].player.controllerIsHuman());
		
		tilesW = trackerTilesW;
		
		blankEverything();
	}
	
	
	
	
	
	
	//launch the application TODO MAIN
	public static void main(String[] args) {
		
		TableViewSmall viewer = new TableViewSmall();
		viewer.setVisible(true);
		
//		boolean canChiL = true;
//		boolean canChiM = true; 
//		boolean canChiH = true; 
//		boolean canPon = true; 
//		boolean canKan = true; 
//		boolean canRon = true;
//		
//		while (true){
//			System.out.println(viewer.getClickCall(canChiL, canChiM, canChiH, canPon, canKan, canRon));
//			viewer.getClickTurnAction();
//			System.out.println(viewer.getResultClickedDiscard());
//			viewer.repaint();
//		}
	}
	
	//TODO start of constructor
	public TableGUI(){
		setResizable(false);
		
		
		mOptionRevealWall = DEFAULT_OPTION_REVEAL_WALL;
		mOptionRevealHands = DEFAULT_OPTION_REVEAL_HANDS;
		
		mMostRecentDiscard = null;


		//load image icons into arrays
		__loadImagesIntoArrays();
		
		
		
		
		final int WINDOW_BOUND_WIDTH = WINDOW_WIDTH + 2*WINDOW_SIDE_BORDER_SIZE;
		final int WINDOW_BOUND_HEIGHT = WINDOW_HEIGHT + WINDOW_TOP_BORDER_SIZE + WINDOW_MENU_SIZE;

		

		ImageIcon windowIconImg = garryWinds[BIG][EAST];
		setIconImage(windowIconImg.getImage());
		
		setTitle(STRING_WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, WINDOW_BOUND_WIDTH, WINDOW_BOUND_HEIGHT);
		
		
		
		
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		/*................................................DEMO PURPOSES.......................................................*/
		
//		ImageIcon pImg = garryTiles[SEAT1][SMALL][22];
//		ImageIcon p2Img = garryTiles[SEAT2][SMALL][22];
//		ImageIcon p3Img = garryTiles[SEAT3][SMALL][25];
//		ImageIcon p4Img = garryTiles[SEAT4][SMALL][21];
//		ImageIcon dwImg = garryTiles[SEAT1][SMALL][0];
		
//		ImageIcon hImg =  garryTiles[SEAT1][BIG][1];
//		ImageIcon h2Img = garryTiles[SEAT2][BIG][1];
//		ImageIcon h3Img = garryTiles[SEAT3][BIG][1];
//		ImageIcon h4Img = garryTiles[SEAT4][BIG][1];
//		
//		ImageIcon meldImg = garryTiles[SEAT1][SMALL][34];
//		ImageIcon meld2Img = garryTiles[SEAT2][SMALL][34];
//		ImageIcon meld3Img = garryTiles[SEAT3][SMALL][34];
//		ImageIcon meld4Img = garryTiles[SEAT4][SMALL][34];
		
		ImageRotator rotators[] = {new ImageRotator(0), new ImageRotator(-90), new ImageRotator(180), new ImageRotator(90)};
		
		ImageIcon pImg = rotators[SEAT1].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/22.png")));
		ImageIcon p2Img = rotators[SEAT2].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/22.png")));
		ImageIcon p3Img = rotators[SEAT3].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/25.png")));
		ImageIcon p4Img = rotators[SEAT4].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/21.png")));
		
		ImageIcon dwImg = rotators[SEAT1].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/0.png")));
		
		ImageIcon hImg = rotators[SEAT1].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/1.png")));
		ImageIcon h2Img = rotators[SEAT2].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/1.png")));
		ImageIcon h3Img = rotators[SEAT3].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/1.png")));
		ImageIcon h4Img = rotators[SEAT4].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/1.png")));
		
		ImageIcon meldImg = rotators[SEAT1].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/34.png")));
		ImageIcon meld2Img = rotators[SEAT2].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/34.png")));
		ImageIcon meld3Img = rotators[SEAT3].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/34.png")));
		ImageIcon meld4Img = rotators[SEAT4].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/34.png")));
		
		

		ImageIcon windRImg = new ImageIcon(getClass().getResource("/res/img/winds/transE.png"));
		ImageIcon wind1Img = new ImageIcon(getClass().getResource("/res/img/winds/small/transEs.png"));
		ImageIcon wind2Img = new ImageIcon(getClass().getResource("/res/img/winds/small/transSs.png"));
		ImageIcon wind3Img = new ImageIcon(getClass().getResource("/res/img/winds/small/transWs.png"));
		ImageIcon wind4Img = new ImageIcon(getClass().getResource("/res/img/winds/small/transNs.png"));
		
		ImageIcon riichiImg = new ImageIcon(getClass().getResource("/res/img/other/riichiStick.png"));
//		ImageIcon sheepImg = new ImageIcon(getClass().getResource("/res/img/omake/sheepy2trans.png"));
//		ImageIcon sheepImg = new ImageIcon(getClass().getResource("/res/img/omake/birdClipart1.png"));
		ImageIcon sheepImg = new ImageIcon(getClass().getResource("/res/img/omake/birdClipart2.png"));
//		ImageIcon sheepImg = new ImageIcon(getClass().getResource("/res/img/omake/birdClipart3.png"));

		windowIconImg = new ImageIcon(getClass().getResource("/res/img/winds/transE.png"));
		setIconImage(windowIconImg.getImage());
		
		/*................................................DEMO PURPOSES.......................................................*/
		
		
		
		
		
		
		
		

		//menus
		JMenuBar menuBar;
		JMenu menuCheats;
		JCheckBoxMenuItem checkboxRevealWalls; JCheckBoxMenuItem checkboxRevealHands;
		
		//panel declarations
		JPanel panelTable;
		JPanel panelSidebar;

		JPanel panelMidTable;
		JPanel panelP1;JPanel panelP2;JPanel panelP3;JPanel panelP4;
		JPanel panelRoundInfo;JPanel panelRInd;JPanel panelInfoP1;JPanel panelInfoP2;JPanel panelInfoP3;JPanel panelInfoP4;JPanel panelRndInfBackground;
		JPanel panelTurnInds;
		JPanel panelTurnInd1; JPanel panelTurnInd2; JPanel panelTurnInd3; JPanel panelTurnInd4;
		
		JPanel panelPlayer1;JPanel panelH1;JPanel panelH1Ms;
		JPanel panelH1M1;JPanel panelH1M2;JPanel panelH1M3;JPanel panelH1M4;
		JPanel panelPlayer2;JPanel panelH2;JPanel panelH2Ms;
		JPanel panelH2M1;JPanel panelH2M2;JPanel panelH2M3;JPanel panelH2M4;
		JPanel panelPlayer3;JPanel panelH3;JPanel panelH3Ms;
		JPanel panelH3M1;JPanel panelH3M2;JPanel panelH3M3;JPanel panelH3M4;
		JPanel panelPlayer4;JPanel panelH4;JPanel panelH4Ms;
		JPanel panelH4M1;JPanel panelH4M2;JPanel panelH4M3;JPanel panelH4M4;
		
		JPanel panelDeadWall;
		JPanel panelWallSummary;JPanel panelWTL;
		
		JPanel panelCalls;
		JPanel panelTActions;
		JPanel panelRoundResult;
		
		
		
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
		
		JLabel lblTurnInd11; JLabel lblTurnInd12; JLabel lblTurnInd21; JLabel lblTurnInd22; JLabel lblTurnInd31; JLabel lblTurnInd32; JLabel lblTurnInd41; JLabel lblTurnInd42;
		
		JLabel lblP1T1;JLabel lblP1T2;JLabel lblP1T3;JLabel lblP1T4;JLabel lblP1T5;JLabel lblP1T6;JLabel lblP1T7;JLabel lblP1T8;JLabel lblP1T9;JLabel lblP1T10;JLabel lblP1T11;JLabel lblP1T12;JLabel lblP1T13;JLabel lblP1T14;JLabel lblP1T15;JLabel lblP1T16;JLabel lblP1T17;JLabel lblP1T18;JLabel lblP1T19;JLabel lblP1T20;JLabel lblP1T21;JLabel lblP1T22;JLabel lblP1T23;JLabel lblP1T24;
		JLabel lblP2T1;JLabel lblP2T2;JLabel lblP2T3;JLabel lblP2T4;JLabel lblP2T5;JLabel lblP2T6;JLabel lblP2T7;JLabel lblP2T8;JLabel lblP2T9;JLabel lblP2T10;JLabel lblP2T11;JLabel lblP2T12;JLabel lblP2T13;JLabel lblP2T14;JLabel lblP2T15;JLabel lblP2T16;JLabel lblP2T17;JLabel lblP2T18;JLabel lblP2T19;JLabel lblP2T20;JLabel lblP2T21;JLabel lblP2T22;JLabel lblP2T23;JLabel lblP2T24;
		JLabel lblP3T1;JLabel lblP3T2;JLabel lblP3T3;JLabel lblP3T4;JLabel lblP3T5;JLabel lblP3T6;JLabel lblP3T7;JLabel lblP3T8;JLabel lblP3T9;JLabel lblP3T10;JLabel lblP3T11;JLabel lblP3T12;JLabel lblP3T13;JLabel lblP3T14;JLabel lblP3T15;JLabel lblP3T16;JLabel lblP3T17;JLabel lblP3T18;JLabel lblP3T19;JLabel lblP3T20;JLabel lblP3T21;JLabel lblP3T22;JLabel lblP3T23;JLabel lblP3T24;
		JLabel lblP4T1;JLabel lblP4T2;JLabel lblP4T3;JLabel lblP4T4;JLabel lblP4T5;JLabel lblP4T6;JLabel lblP4T7;JLabel lblP4T8;JLabel lblP4T9;JLabel lblP4T10;JLabel lblP4T11;JLabel lblP4T12;JLabel lblP4T13;JLabel lblP4T14;JLabel lblP4T15;JLabel lblP4T16;JLabel lblP4T17;JLabel lblP4T18;JLabel lblP4T19;JLabel lblP4T20;JLabel lblP4T21;JLabel lblP4T22;JLabel lblP4T23;JLabel lblP4T24;
		
		
		JLabel lblWeHaveFun;
		
		JLabel lblDW1;JLabel lblDW2;JLabel lblDW3;JLabel lblDW4;JLabel lblDW5;JLabel lblDW6;JLabel lblDW7;JLabel lblDW8;JLabel lblDW9;JLabel lblDW10;JLabel lblDW11;JLabel lblDW12;JLabel lblDW13;JLabel lblDW14;
		
		JLabel lblRoundOver;JLabel lblRoundResult;
		JLabel lblExclamationLabel;
		
		JLabel lblWTLText;JLabel lblWallTilesLeftLabel;
		
		
		//button declarations
		JButton btnCallNone;
		JButton btnCallChiL;JButton btnCallChiM;JButton btnCallChiH;
		JButton btnCallPon;JButton btnCallKan;JButton btnCallRon;
		JButton btnCallChi;
		
		JButton buttonRiichi;JButton buttonAnkan;JButton buttonMinkan;JButton buttonTsumo;
		
		
		
		
		
		
		
		
		
		
		
		

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuCheats = new JMenu("Cheats");
		menuBar.add(menuCheats);
		
		checkboxRevealWalls = new JCheckBoxMenuItem("Reveal Walls");
		checkboxRevealWalls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mOptionRevealWall = !mOptionRevealWall;
				updateEverything();
			}
		});
		checkboxRevealWalls.setSelected(DEFAULT_OPTION_REVEAL_WALL);
		menuCheats.add(checkboxRevealWalls);
		
		checkboxRevealHands = new JCheckBoxMenuItem("Reveal Hands");
		checkboxRevealHands.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mOptionRevealHands = !mOptionRevealHands;
				for (int i = 0; i < mRevealHands.length; i++) mRevealHands[i] = (mOptionRevealHands || mPTrackers[i].player.controllerIsHuman());
				updateEverything();
			}
		});
		checkboxRevealHands.setSelected(DEFAULT_OPTION_REVEAL_HANDS);
		menuCheats.add(checkboxRevealHands);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		panelTable = new JPanel();
		panelTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2){
					mChosenCall = DEFAULT_CALL;
					__setDiscardChosen(DEFAULT_DISCARD);
				}
			}
		});
		panelTable.setBounds(0, 0, 844 - 62*2 - 6, WINDOW_HEIGHT);
		panelTable.setBackground(COLOR_TABLE);
		contentPane.add(panelTable);
		panelTable.setLayout(null);
		
		
		
		lblExclamationLabel = new JLabel("TSUMO!");
		lblExclamationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblExclamationLabel.setFont(new Font("Maiandra GD", Font.BOLD, 28));
//		lblExclamationLabel.setBounds(161, 688, 134, 34);
		lblExclamationLabel.setBounds(EXCLAMATION_LOCS[SEAT1][0], EXCLAMATION_LOCS[SEAT1][1], 134, 34);
		lblExclamationLabel.setBorder(new LineBorder(new Color(0, 0, 200), 3, true));
		panelTable.add(lblExclamationLabel);
		lblExclamationLabel.setOpaque(true);
		lblExclamationLabel.setBackground(COLOR_EXCLAMATION);
		
		
		
		
		
		
		panelMidTable = new JPanel();
		panelMidTable.setBounds(143, 116, 428, 420);
//		panelMidTable.setBackground(COLOR_TRANSPARENT);
		panelMidTable.setBackground(COLOR_MID_TABLE);
		panelTable.add(panelMidTable);
		panelMidTable.setLayout(null);
		
		
		
		
		
		panelP1 = new JPanel();
		panelP1.setBounds(134, 290, POND_PANEL_WIDTH, POND_PANEL_HEIGHT);
		panelP1.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP1);
		panelP1.setLayout(new GridLayout(4, 6, 0, 0));
		
		lblP1T1 = new JLabel();
		panelP1.add(lblP1T1);
		lblP1T1.setIcon(pImg);
		
		lblP1T2 = new JLabel();
		panelP1.add(lblP1T2);
		lblP1T2.setIcon(pImg);
		
		lblP1T3 = new JLabel();
		panelP1.add(lblP1T3);
		lblP1T3.setIcon(pImg);
		
		lblP1T4 = new JLabel();
		panelP1.add(lblP1T4);
		lblP1T4.setIcon(pImg);
		
		lblP1T5 = new JLabel();
		panelP1.add(lblP1T5);
		lblP1T5.setIcon(pImg);
		
		lblP1T6 = new JLabel();
		panelP1.add(lblP1T6);
		lblP1T6.setIcon(pImg);
		
		lblP1T7 = new JLabel();
		panelP1.add(lblP1T7);
		lblP1T7.setIcon(pImg);
		
		lblP1T8 = new JLabel();
		panelP1.add(lblP1T8);
		lblP1T8.setIcon(pImg);
		
		lblP1T9 = new JLabel();
		panelP1.add(lblP1T9);
		lblP1T9.setIcon(pImg);
		
		lblP1T10 = new JLabel();
		panelP1.add(lblP1T10);
		lblP1T10.setIcon(pImg);
		
		lblP1T11 = new JLabel();
		panelP1.add(lblP1T11);
		lblP1T11.setIcon(pImg);
		
		lblP1T12 = new JLabel();
		panelP1.add(lblP1T12);
		lblP1T12.setIcon(pImg);
		
		lblP1T13 = new JLabel();
		panelP1.add(lblP1T13);
		lblP1T13.setIcon(pImg);
		
		lblP1T14 = new JLabel();
		panelP1.add(lblP1T14);
		lblP1T14.setIcon(pImg);
		
		lblP1T15 = new JLabel();
		panelP1.add(lblP1T15);
		lblP1T15.setIcon(pImg);
		
		lblP1T16 = new JLabel();
		panelP1.add(lblP1T16);
		lblP1T16.setIcon(pImg);
		
		lblP1T17 = new JLabel();
		panelP1.add(lblP1T17);
		lblP1T17.setIcon(pImg);
		
		lblP1T18 = new JLabel();
		panelP1.add(lblP1T18);
		lblP1T18.setIcon(pImg);
		
		lblP1T19 = new JLabel();
		panelP1.add(lblP1T19);
		lblP1T19.setIcon(pImg);
		
		lblP1T20 = new JLabel();
		panelP1.add(lblP1T20);
		lblP1T20.setIcon(pImg);
		
		lblP1T21 = new JLabel();
		panelP1.add(lblP1T21);
		lblP1T21.setIcon(pImg);
		
		lblP1T22 = new JLabel();
		panelP1.add(lblP1T22);
		lblP1T22.setIcon(pImg);
		
		lblP1T23 = new JLabel();
		panelP1.add(lblP1T23);
		lblP1T23.setIcon(pImg);
		
		lblP1T24 = new JLabel();
		panelP1.add(lblP1T24);
		lblP1T24.setIcon(pImg);
		
		
		
		
		
		
		panelP2 = new JPanel();
		panelP2.setBounds(298, 129, POND_PANEL_HEIGHT, POND_PANEL_WIDTH);
		panelP2.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP2);
		panelP2.setLayout(new GridLayout(6, 4, 0, 0));
		
		lblP2T6 = new JLabel();
		panelP2.add(lblP2T6);
		lblP2T6.setIcon(p2Img);
		
		lblP2T12 = new JLabel();
		panelP2.add(lblP2T12);
		lblP2T12.setIcon(p2Img);
		
		lblP2T18 = new JLabel();
		panelP2.add(lblP2T18);
		lblP2T18.setIcon(p2Img);
		
		lblP2T24 = new JLabel();
		panelP2.add(lblP2T24);
		lblP2T24.setIcon(p2Img);
		
		lblP2T5 = new JLabel();
		panelP2.add(lblP2T5);
		lblP2T5.setIcon(p2Img);
		
		lblP2T11 = new JLabel();
		panelP2.add(lblP2T11);
		lblP2T11.setIcon(p2Img);
		
		lblP2T17 = new JLabel();
		panelP2.add(lblP2T17);
		lblP2T17.setIcon(p2Img);
		
		lblP2T23 = new JLabel();
		panelP2.add(lblP2T23);
		lblP2T23.setIcon(p2Img);
		
		lblP2T4 = new JLabel();
		panelP2.add(lblP2T4);
		lblP2T4.setIcon(p2Img);
		
		lblP2T10 = new JLabel();
		panelP2.add(lblP2T10);
		lblP2T10.setIcon(p2Img);
		
		lblP2T16 = new JLabel();
		panelP2.add(lblP2T16);
		lblP2T16.setIcon(p2Img);
		
		lblP2T22 = new JLabel();
		panelP2.add(lblP2T22);
		lblP2T22.setIcon(p2Img);
		
		lblP2T3 = new JLabel();
		panelP2.add(lblP2T3);
		lblP2T3.setIcon(p2Img);
		
		lblP2T9 = new JLabel();
		panelP2.add(lblP2T9);
		lblP2T9.setIcon(p2Img);
		
		lblP2T15 = new JLabel();
		panelP2.add(lblP2T15);
		lblP2T15.setIcon(p2Img);
		
		lblP2T21 = new JLabel();
		panelP2.add(lblP2T21);
		lblP2T21.setIcon(p2Img);
		
		lblP2T2 = new JLabel();
		panelP2.add(lblP2T2);
		lblP2T2.setIcon(p2Img);
		
		lblP2T8 = new JLabel();
		panelP2.add(lblP2T8);
		lblP2T8.setIcon(p2Img);
		
		lblP2T14 = new JLabel();
		panelP2.add(lblP2T14);
		lblP2T14.setIcon(p2Img);
		
		lblP2T20 = new JLabel();
		panelP2.add(lblP2T20);
		lblP2T20.setIcon(p2Img);
		
		lblP2T1 = new JLabel();
		panelP2.add(lblP2T1);
		lblP2T1.setIcon(p2Img);
		
		lblP2T7 = new JLabel();
		panelP2.add(lblP2T7);
		lblP2T7.setIcon(p2Img);
		
		lblP2T13 = new JLabel();
		panelP2.add(lblP2T13);
		lblP2T13.setIcon(p2Img);
		
		lblP2T19 = new JLabel();
		panelP2.add(lblP2T19);
		lblP2T19.setIcon(p2Img);
		
		
		
		
		
		
		panelP3 = new JPanel();
		panelP3.setBounds(134, 6, POND_PANEL_WIDTH, POND_PANEL_HEIGHT);
		panelP3.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP3);
		panelP3.setLayout(new GridLayout(4, 6, 0, 0));
		
		lblP3T24 = new JLabel();
		panelP3.add(lblP3T24);
		lblP3T24.setIcon(p3Img);
		
		lblP3T23 = new JLabel();
		panelP3.add(lblP3T23);
		lblP3T23.setIcon(p3Img);
		
		lblP3T22 = new JLabel();
		panelP3.add(lblP3T22);
		lblP3T22.setIcon(p3Img);
		
		lblP3T21 = new JLabel();
		panelP3.add(lblP3T21);
		lblP3T21.setIcon(p3Img);
		
		lblP3T20 = new JLabel();
		panelP3.add(lblP3T20);
		lblP3T20.setIcon(p3Img);
		
		lblP3T19 = new JLabel();
		panelP3.add(lblP3T19);
		lblP3T19.setIcon(p3Img);
		
		lblP3T18 = new JLabel();
		panelP3.add(lblP3T18);
		lblP3T18.setIcon(p3Img);
		
		lblP3T17 = new JLabel();
		panelP3.add(lblP3T17);
		lblP3T17.setIcon(p3Img);
		
		lblP3T16 = new JLabel();
		panelP3.add(lblP3T16);
		lblP3T16.setIcon(p3Img);
		
		lblP3T15 = new JLabel();
		panelP3.add(lblP3T15);
		lblP3T15.setIcon(p3Img);
		
		lblP3T14 = new JLabel();
		panelP3.add(lblP3T14);
		lblP3T14.setIcon(p3Img);
		
		lblP3T13 = new JLabel();
		panelP3.add(lblP3T13);
		lblP3T13.setIcon(p3Img);
		
		lblP3T12 = new JLabel();
		panelP3.add(lblP3T12);
		lblP3T12.setIcon(p3Img);
		
		lblP3T11 = new JLabel();
		panelP3.add(lblP3T11);
		lblP3T11.setIcon(p3Img);
		
		lblP3T10 = new JLabel();
		panelP3.add(lblP3T10);
		lblP3T10.setIcon(p3Img);
		
		lblP3T9 = new JLabel();
		panelP3.add(lblP3T9);
		lblP3T9.setIcon(p3Img);
		
		lblP3T8 = new JLabel();
		panelP3.add(lblP3T8);
		lblP3T8.setIcon(p3Img);
		
		lblP3T7 = new JLabel();
		panelP3.add(lblP3T7);
		lblP3T7.setIcon(p3Img);
		
		lblP3T6 = new JLabel();
		panelP3.add(lblP3T6);
		lblP3T6.setIcon(p3Img);
		
		lblP3T5 = new JLabel();
		panelP3.add(lblP3T5);
		lblP3T5.setIcon(p3Img);
		
		lblP3T4 = new JLabel();
		panelP3.add(lblP3T4);
		lblP3T4.setIcon(p3Img);
		
		lblP3T3 = new JLabel();
		panelP3.add(lblP3T3);
		lblP3T3.setIcon(p3Img);
		
		lblP3T2 = new JLabel();
		panelP3.add(lblP3T2);
		lblP3T2.setIcon(p3Img);
		
		lblP3T1 = new JLabel();
		panelP3.add(lblP3T1);
		lblP3T1.setIcon(p3Img);
		
		
		
		
		
		
		panelP4 = new JPanel();
		panelP4.setBounds(6, 129, POND_PANEL_HEIGHT, POND_PANEL_WIDTH);
		panelP4.setBackground(COLOR_TRANSPARENT);
		panelMidTable.add(panelP4);
		panelP4.setLayout(new GridLayout(6, 4, 0, 0));
		
		lblP4T19 = new JLabel();
		panelP4.add(lblP4T19);
		lblP4T19.setIcon(p4Img);
		
		lblP4T13 = new JLabel();
		panelP4.add(lblP4T13);
		lblP4T13.setIcon(p4Img);
		
		lblP4T7 = new JLabel();
		panelP4.add(lblP4T7);
		lblP4T7.setIcon(p4Img);
		
		lblP4T1 = new JLabel();
		panelP4.add(lblP4T1);
		lblP4T1.setIcon(p4Img);
		
		lblP4T20 = new JLabel();
		panelP4.add(lblP4T20);
		lblP4T20.setIcon(p4Img);
		
		lblP4T14 = new JLabel();
		panelP4.add(lblP4T14);
		lblP4T14.setIcon(p4Img);
		
		lblP4T8 = new JLabel();
		panelP4.add(lblP4T8);
		lblP4T8.setIcon(p4Img);
		
		lblP4T2 = new JLabel();
		panelP4.add(lblP4T2);
		lblP4T2.setIcon(p4Img);
		
		lblP4T21 = new JLabel();
		panelP4.add(lblP4T21);
		lblP4T21.setIcon(p4Img);
		
		lblP4T15 = new JLabel();
		panelP4.add(lblP4T15);
		lblP4T15.setIcon(p4Img);
		
		lblP4T9 = new JLabel();
		panelP4.add(lblP4T9);
		lblP4T9.setIcon(p4Img);
		
		lblP4T3 = new JLabel();
		panelP4.add(lblP4T3);
		lblP4T3.setIcon(p4Img);
		
		lblP4T22 = new JLabel();
		panelP4.add(lblP4T22);
		lblP4T22.setIcon(p4Img);
		
		lblP4T16 = new JLabel();
		panelP4.add(lblP4T16);
		lblP4T16.setIcon(p4Img);
		
		lblP4T10 = new JLabel();
		panelP4.add(lblP4T10);
		lblP4T10.setIcon(p4Img);
		
		lblP4T4 = new JLabel();
		panelP4.add(lblP4T4);
		lblP4T4.setIcon(p4Img);
		
		lblP4T23 = new JLabel();
		panelP4.add(lblP4T23);
		lblP4T23.setIcon(p4Img);
		
		lblP4T17 = new JLabel();
		panelP4.add(lblP4T17);
		lblP4T17.setIcon(p4Img);
		
		lblP4T11 = new JLabel();
		panelP4.add(lblP4T11);
		lblP4T11.setIcon(p4Img);
		
		lblP4T5 = new JLabel();
		panelP4.add(lblP4T5);
		lblP4T5.setIcon(p4Img);
		
		lblP4T24 = new JLabel();
		panelP4.add(lblP4T24);
		lblP4T24.setIcon(p4Img);
		
		lblP4T18 = new JLabel();
		panelP4.add(lblP4T18);
		lblP4T18.setIcon(p4Img);
		
		lblP4T12 = new JLabel();
		panelP4.add(lblP4T12);
		lblP4T12.setIcon(p4Img);
		
		lblP4T6 = new JLabel();
		panelP4.add(lblP4T6);
		lblP4T6.setIcon(p4Img);
		
		
		
		
		
		
		panelRoundInfo = new JPanel();
		panelRoundInfo.setBounds(131, 131, 166, 158);
		panelMidTable.add(panelRoundInfo);
		panelRoundInfo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelRoundInfo.setBackground(COLOR_RINF_PANEL);
		panelRoundInfo.setLayout(null);
		
		
		
		panelRInd = new JPanel();
		panelRInd.setBounds(54, 54, 58, 49);
		panelRoundInfo.add(panelRInd);
		panelRInd.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelRInd.setBackground(COLOR_RIND);
		panelRInd.setLayout(null);
		
		lblRIndWind = new JLabel();
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
		panelInfoP1.setBackground(COLOR_RINF_PANEL);
		panelInfoP1.setLayout(null);
		
		lblInfoP1Wind = new JLabel();
		lblInfoP1Wind.setBounds(16, 2, 23, 23);
		lblInfoP1Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP1Wind.setIcon(wind1Img);
		panelInfoP1.add(lblInfoP1Wind);
		
		lblInfoP1Points = new JLabel("128,000");
		lblInfoP1Points.setBounds(4, 25, 46, 14);
		lblInfoP1Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP1Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP1.add(lblInfoP1Points);
		
		lblInfoP1Riichi = new JLabel();
		lblInfoP1Riichi.setBounds(2, 40, 50, 8);
		lblInfoP1Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP1Riichi.setIcon(riichiImg);
		panelInfoP1.add(lblInfoP1Riichi);
		
		
		
		
		
		panelInfoP2 = new JPanel();
		panelInfoP2.setBounds(112, 52, 54, 54);
		panelRoundInfo.add(panelInfoP2);
		panelInfoP2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP2.setBackground(COLOR_RINF_PANEL);
		panelInfoP2.setLayout(null);
		
		lblInfoP2Wind = new JLabel();
		lblInfoP2Wind.setBounds(16, 2, 23, 23);
		lblInfoP2Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP2Wind.setIcon(wind2Img);
		panelInfoP2.add(lblInfoP2Wind);
		
		lblInfoP2Points = new JLabel("128,000");
		lblInfoP2Points.setBounds(4, 25, 46, 14);
		lblInfoP2Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP2Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP2.add(lblInfoP2Points);
		
		lblInfoP2Riichi = new JLabel();
		lblInfoP2Riichi.setBounds(2, 40, 50, 8);
		lblInfoP2Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP2Riichi.setIcon(riichiImg);
		panelInfoP2.add(lblInfoP2Riichi);
		
		
		
		
		
		panelInfoP3 = new JPanel();
		panelInfoP3.setBounds(56, 0, 54, 54);
		panelRoundInfo.add(panelInfoP3);
		panelInfoP3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP3.setBackground(COLOR_RINF_PANEL);
		panelInfoP3.setLayout(null);
		
		lblInfoP3Wind = new JLabel();
		lblInfoP3Wind.setBounds(16, 2, 23, 23);
		lblInfoP3Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP3Wind.setIcon(wind3Img);
		panelInfoP3.add(lblInfoP3Wind);
		
		lblInfoP3Points = new JLabel("128,000");
		lblInfoP3Points.setBounds(4, 25, 46, 14);
		lblInfoP3Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP3Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP3.add(lblInfoP3Points);
		
		lblInfoP3Riichi = new JLabel();
		lblInfoP3Riichi.setBounds(2, 40, 50, 8);
		lblInfoP3Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP3Riichi.setIcon(riichiImg);
		panelInfoP3.add(lblInfoP3Riichi);
		
		
		
		
		
		panelInfoP4 = new JPanel();
		panelInfoP4.setBounds(0, 52, 54, 54);
		panelRoundInfo.add(panelInfoP4);
		panelInfoP4.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoP4.setBackground(COLOR_RINF_PANEL);
		panelInfoP4.setLayout(null);
		
		lblInfoP4Wind = new JLabel();
		lblInfoP4Wind.setBounds(16, 2, 23, 23);
		lblInfoP4Wind.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP4Wind.setIcon(wind4Img);
		panelInfoP4.add(lblInfoP4Wind);
		
		lblInfoP4Points = new JLabel("128,000");
		lblInfoP4Points.setBounds(4, 25, 46, 14);
		lblInfoP4Points.setBackground(COLOR_TRANSPARENT);
		lblInfoP4Points.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoP4.add(lblInfoP4Points);
		
		lblInfoP4Riichi = new JLabel();
		lblInfoP4Riichi.setBounds(2, 40, 50, 8);
		lblInfoP4Riichi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoP4Riichi.setIcon(riichiImg);
		panelInfoP4.add(lblInfoP4Riichi);
		
		//exists only to color the background behind the round info
		panelRndInfBackground = new JPanel();
		panelRndInfBackground.setBounds(131, 131, 166, 158);
		panelMidTable.add(panelRndInfBackground);
		panelRndInfBackground.setLayout(null);
		
		
		
		
		
		
		
		panelPlayer1 = new JPanel();
		panelPlayer1.setBounds(105, 544, 667, 78);
		panelPlayer1.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer1);
		panelPlayer1.setLayout(null);
		
		panelH1 = new JPanel();
		panelH1.setBounds(42, 0, TILE_BIG_WIDTH*SIZE_HAND, TILE_BIG_HEIGHT);
		panelH1.setBackground(COLOR_TRANSPARENT);
		panelPlayer1.add(panelH1);
		panelH1.setLayout(new GridLayout(1, 14, 0, 0));
		
		lblH1T1 = new JLabel();
		lblH1T1.setIcon(hImg);
		panelH1.add(lblH1T1);
		
		lblH1T2 = new JLabel();
		lblH1T2.setIcon(hImg);
		panelH1.add(lblH1T2);
		
		lblH1T3 = new JLabel();
		lblH1T3.setIcon(hImg);
		panelH1.add(lblH1T3);
		
		lblH1T4 = new JLabel();
		lblH1T4.setIcon(hImg);
		panelH1.add(lblH1T4);
		
		lblH1T5 = new JLabel();
		lblH1T5.setIcon(hImg);
		panelH1.add(lblH1T5);
		
		lblH1T6 = new JLabel();
		lblH1T6.setIcon(hImg);
		panelH1.add(lblH1T6);
		
		lblH1T7 = new JLabel();
		lblH1T7.setIcon(hImg);
		panelH1.add(lblH1T7);
		
		lblH1T8 = new JLabel();
		lblH1T8.setIcon(hImg);
		panelH1.add(lblH1T8);
		
		lblH1T9 = new JLabel();
		lblH1T9.setIcon(hImg);
		panelH1.add(lblH1T9);
		
		lblH1T10 = new JLabel();
		lblH1T10.setIcon(hImg);
		panelH1.add(lblH1T10);
		
		lblH1T11 = new JLabel();
		lblH1T11.setIcon(hImg);
		panelH1.add(lblH1T11);
		
		lblH1T12 = new JLabel();
		lblH1T12.setIcon(hImg);
		panelH1.add(lblH1T12);
		
		lblH1T13 = new JLabel();
		lblH1T13.setIcon(hImg);
		panelH1.add(lblH1T13);
		
		lblH1T14 = new JLabel();
		lblH1T14.setIcon(hImg);
		panelH1.add(lblH1T14);
		
		
		
		lblH1T1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {__setDiscardChosen(1);}
		});
		lblH1T2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(2);}
		});
		lblH1T3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(3);}
		});
		lblH1T4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(4);}
		});
		lblH1T5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(5);}
		});
		lblH1T6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(6);}
		});
		lblH1T7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(7);}
		});
		lblH1T8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(8);}
		});
		lblH1T9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(9);}
		});
		lblH1T10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(10);}
		});
		lblH1T11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(11);}
		});
		lblH1T12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(12);}
		});
		lblH1T13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(13);}
		});
		lblH1T14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){__setDiscardChosen(14);}
		});
		
		
		
		
		
		
		
		
		
		panelH1Ms = new JPanel();
		panelH1Ms.setBounds(100, 47, 474, 31);
		panelH1Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer1.add(panelH1Ms);
		panelH1Ms.setLayout(null);
		
		
		
		
		panelH1M4 = new JPanel();
		panelH1M4.setBounds(0, 0, 108, 31);
		panelH1M4.setBackground(COLOR_TRANSPARENT);
		panelH1M4.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH1M4T1 = new JLabel();
		panelH1M4.add(lblH1M4T1);
		lblH1M4T1.setIcon(meldImg);
		
		lblH1M4T2 = new JLabel();
		panelH1M4.add(lblH1M4T2);
		lblH1M4T2.setIcon(meldImg);
		
		lblH1M4T3 = new JLabel();
		panelH1M4.add(lblH1M4T3);
		lblH1M4T3.setIcon(meldImg);
		
		lblH1M4T4 = new JLabel();
		panelH1M4.add(lblH1M4T4);
		lblH1M4T4.setIcon(meldImg);
		panelH1Ms.add(panelH1M4);
		
		
		
		panelH1M3 = new JPanel();
		panelH1M3.setBounds(120, 0, 108, 31);
		panelH1M3.setBackground(COLOR_TRANSPARENT);
		panelH1Ms.add(panelH1M3);
		panelH1M3.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH1M3T1 = new JLabel();
		panelH1M3.add(lblH1M3T1);
		lblH1M3T1.setIcon(meldImg);
		
		lblH1M3T2 = new JLabel();
		panelH1M3.add(lblH1M3T2);
		lblH1M3T2.setIcon(meldImg);
		
		lblH1M3T3 = new JLabel();
		panelH1M3.add(lblH1M3T3);
		lblH1M3T3.setIcon(meldImg);
		
		lblH1M3T4 = new JLabel();
		panelH1M3.add(lblH1M3T4);
		lblH1M3T4.setIcon(meldImg);
		
		
		
		panelH1M2 = new JPanel();
		panelH1M2.setBounds(240, 0, 108, 31);
		panelH1M2.setBackground(COLOR_TRANSPARENT);
		panelH1Ms.add(panelH1M2);
		panelH1M2.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH1M2T1 = new JLabel();
		panelH1M2.add(lblH1M2T1);
		lblH1M2T1.setIcon(meldImg);
		
		lblH1M2T2 = new JLabel();
		panelH1M2.add(lblH1M2T2);
		lblH1M2T2.setIcon(meldImg);
		
		lblH1M2T3 = new JLabel();
		panelH1M2.add(lblH1M2T3);
		lblH1M2T3.setIcon(meldImg);
		
		lblH1M2T4 = new JLabel();
		panelH1M2.add(lblH1M2T4);
		lblH1M2T4.setIcon(meldImg);
		
		
		panelH1M1 = new JPanel();
		panelH1M1.setBounds(360, 0, 108, 31);
		panelH1M1.setBackground(COLOR_TRANSPARENT);
		panelH1Ms.add(panelH1M1);
		panelH1M1.setLayout(new GridLayout(1, 4, 0, 0));
		
		
		lblH1M1T1 = new JLabel();
		panelH1M1.add(lblH1M1T1);
		lblH1M1T1.setIcon(meldImg);
		
		lblH1M1T2 = new JLabel();
		panelH1M1.add(lblH1M1T2);
		lblH1M1T2.setIcon(meldImg);
		
		lblH1M1T3 = new JLabel();
		panelH1M1.add(lblH1M1T3);
		lblH1M1T3.setIcon(meldImg);
		
		lblH1M1T4 = new JLabel();
		panelH1M1.add(lblH1M1T4);
		lblH1M1T4.setIcon(meldImg);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		panelPlayer2 = new JPanel();
		panelPlayer2.setBounds(606, -90, 78, 667);
		panelPlayer2.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer2);
		panelPlayer2.setLayout(null);
		
		panelH2 = new JPanel();
		panelH2.setBounds(0, 203, TILE_BIG_HEIGHT, TILE_BIG_WIDTH*SIZE_HAND);
		panelH2.setBackground(COLOR_TRANSPARENT);
		panelPlayer2.add(panelH2);
		panelH2.setLayout(new GridLayout(14, 1, 0, 0));
		
		lblH2T14 = new JLabel();
		lblH2T14.setIcon(h2Img);
		panelH2.add(lblH2T14);
		larryH2[13] = lblH2T14;
		
		lblH2T13 = new JLabel();
		lblH2T13.setIcon(h2Img);
		panelH2.add(lblH2T13);
		larryH2[12] = lblH2T13;
		
		lblH2T12 = new JLabel();
		lblH2T12.setIcon(h2Img);
		panelH2.add(lblH2T12);
		larryH2[11] = lblH2T12;
		
		lblH2T11 = new JLabel();
		lblH2T11.setIcon(h2Img);
		panelH2.add(lblH2T11);
		larryH2[10] = lblH2T11;
		
		lblH2T10 = new JLabel();
		lblH2T10.setIcon(h2Img);
		panelH2.add(lblH2T10);
		larryH2[9] = lblH2T10;
		
		lblH2T9 = new JLabel();
		lblH2T9.setIcon(h2Img);
		panelH2.add(lblH2T9);
		larryH2[8] = lblH2T9;
		
		lblH2T8 = new JLabel();
		lblH2T8.setIcon(h2Img);
		panelH2.add(lblH2T8);
		larryH2[7] = lblH2T8;
		
		lblH2T7 = new JLabel();
		lblH2T7.setIcon(h2Img);
		panelH2.add(lblH2T7);
		larryH2[6] = lblH2T7;
		
		lblH2T6 = new JLabel();
		lblH2T6.setIcon(h2Img);
		panelH2.add(lblH2T6);
		larryH2[5] = lblH2T6;
		
		lblH2T5 = new JLabel();
		lblH2T5.setIcon(h2Img);
		panelH2.add(lblH2T5);
		larryH2[4] = lblH2T5;
		
		lblH2T4 = new JLabel();
		lblH2T4.setIcon(h2Img);
		panelH2.add(lblH2T4);
		larryH2[3] = lblH2T4;
		
		lblH2T3 = new JLabel();
		lblH2T3.setIcon(h2Img);
		panelH2.add(lblH2T3);
		larryH2[2] = lblH2T3;
		
		lblH2T2 = new JLabel();
		lblH2T2.setIcon(h2Img);
		panelH2.add(lblH2T2);
		larryH2[1] = lblH2T2;
		
		
		lblH2T1 = new JLabel();
		lblH2T1.setIcon(h2Img);
		panelH2.add(lblH2T1);
		
		
		panelH2Ms = new JPanel();
		panelH2Ms.setBounds(46, 104, 35, 474);
		panelH2Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer2.add(panelH2Ms);
		panelH2Ms.setLayout(null);
		
		
		panelH2M1 = new JPanel();
		panelH2M1.setBounds(0, 0, 35, 114);
		panelH2M1.setBackground(COLOR_TRANSPARENT);
		panelH2Ms.add(panelH2M1);
		panelH2M1.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH2M1T4 = new JLabel();
		panelH2M1.add(lblH2M1T4);
		lblH2M1T4.setIcon(meld2Img);
		
		lblH2M1T3 = new JLabel();
		panelH2M1.add(lblH2M1T3);
		lblH2M1T3.setIcon(meld2Img);
		
		lblH2M1T2 = new JLabel();
		panelH2M1.add(lblH2M1T2);
		lblH2M1T2.setIcon(meld2Img);
		
		lblH2M1T1 = new JLabel();
		panelH2M1.add(lblH2M1T1);
		lblH2M1T1.setIcon(meld2Img);
		
		
		
		
		panelH2M4 = new JPanel();
		panelH2M4.setBounds(0, 360, 35, 114);
		panelH2M4.setBackground(COLOR_TRANSPARENT);
		panelH2M4.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH2M4T4 = new JLabel();
		panelH2M4.add(lblH2M4T4);
		lblH2M4T4.setIcon(meld2Img);
		
		lblH2M4T3 = new JLabel();
		panelH2M4.add(lblH2M4T3);
		lblH2M4T3.setIcon(meld2Img);
		
		lblH2M4T2 = new JLabel();
		panelH2M4.add(lblH2M4T2);
		lblH2M4T2.setIcon(meld2Img);
		
		
		
		panelH2M2 = new JPanel();
		panelH2M2.setBounds(0, 120, 35, 114);
		panelH2M2.setBackground(COLOR_TRANSPARENT);
		panelH2Ms.add(panelH2M2);
		panelH2M2.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH2M2T4 = new JLabel();
		panelH2M2.add(lblH2M2T4);
		lblH2M2T4.setIcon(meld2Img);
		
		lblH2M2T3 = new JLabel();
		panelH2M2.add(lblH2M2T3);
		lblH2M2T3.setIcon(meld2Img);
		
		lblH2M2T2 = new JLabel();
		panelH2M2.add(lblH2M2T2);
		lblH2M2T2.setIcon(meld2Img);
		
		lblH2M2T1 = new JLabel();
		panelH2M2.add(lblH2M2T1);
		lblH2M2T1.setIcon(meld2Img);
		
		
		
		
		panelH2M3 = new JPanel();
		panelH2M3.setBounds(0, 240, 35, 114);
		panelH2M3.setBackground(COLOR_TRANSPARENT);
		panelH2Ms.add(panelH2M3);
		panelH2M3.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH2M3T4 = new JLabel();
		panelH2M3.add(lblH2M3T4);
		lblH2M3T4.setIcon(meld2Img);
		
		lblH2M3T3 = new JLabel();
		panelH2M3.add(lblH2M3T3);
		lblH2M3T3.setIcon(meld2Img);
		
		lblH2M3T2 = new JLabel();
		panelH2M3.add(lblH2M3T2);
		lblH2M3T2.setIcon(meld2Img);
		
		lblH2M3T1 = new JLabel();
		panelH2M3.add(lblH2M3T1);
		lblH2M3T1.setIcon(meld2Img);
		panelH2Ms.add(panelH2M4);
		
		lblH2M4T1 = new JLabel();
		panelH2M4.add(lblH2M4T1);
		lblH2M4T1.setIcon(meld2Img);
		
		
		
		
		
		
		
		
		panelPlayer3 = new JPanel();
		panelPlayer3.setBounds(-32, 30, 667, 78);
		panelPlayer3.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer3);
		panelPlayer3.setLayout(null);
		
		
		
		
		
		panelH3 = new JPanel();
		panelH3.setBounds(203, 36, TILE_BIG_WIDTH*SIZE_HAND, TILE_BIG_HEIGHT);
		panelH3.setBackground(COLOR_TRANSPARENT);
		panelPlayer3.add(panelH3);
		panelH3.setLayout(new GridLayout(1, 14, 0, 0));
		
		lblH3T14 = new JLabel();
		lblH3T14.setBounds(0, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T14.setIcon(h3Img);
		panelH3.add(lblH3T14);
		
		lblH3T13 = new JLabel();
		lblH3T13.setBounds(36, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T13.setIcon(h3Img);
		panelH3.add(lblH3T13);
		
		lblH3T12 = new JLabel();
		lblH3T12.setBounds(72, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T12.setIcon(h3Img);
		panelH3.add(lblH3T12);
		
		lblH3T11 = new JLabel();
		lblH3T11.setBounds(108, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T11.setIcon(h3Img);
		panelH3.add(lblH3T11);
		
		lblH3T10 = new JLabel();
		lblH3T10.setBounds(144, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T10.setIcon(h3Img);
		panelH3.add(lblH3T10);
		
		lblH3T9 = new JLabel();
		lblH3T9.setBounds(180, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T9.setIcon(h3Img);
		panelH3.add(lblH3T9);
		
		lblH3T8 = new JLabel();
		lblH3T8.setBounds(216, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T8.setIcon(h3Img);
		panelH3.add(lblH3T8);
		
		lblH3T7 = new JLabel();
		lblH3T7.setBounds(252, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T7.setIcon(h3Img);
		panelH3.add(lblH3T7);
		
		lblH3T6 = new JLabel();
		lblH3T6.setBounds(288, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T6.setIcon(h3Img);
		panelH3.add(lblH3T6);
		
		lblH3T5 = new JLabel();
		lblH3T5.setBounds(324, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T5.setIcon(h3Img);
		panelH3.add(lblH3T5);
		
		lblH3T4 = new JLabel();
		lblH3T4.setBounds(360, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T4.setIcon(h3Img);
		panelH3.add(lblH3T4);
		
		lblH3T3 = new JLabel();
		lblH3T3.setBounds(396, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T3.setIcon(h3Img);
		panelH3.add(lblH3T3);
		
		lblH3T2 = new JLabel();
		lblH3T2.setBounds(432, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T2.setIcon(h3Img);
		panelH3.add(lblH3T2);
		
		lblH3T1 = new JLabel();
		lblH3T1.setBounds(468, 0, TILE_BIG_WIDTH, TILE_BIG_HEIGHT);
		lblH3T1.setIcon(h3Img);
		panelH3.add(lblH3T1);
		
		
		
		
		panelH3Ms = new JPanel();
		panelH3Ms.setBounds(104, 0, 474, 35);
		panelH3Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer3.add(panelH3Ms);
		panelH3Ms.setLayout(null);
		
		
		panelH3M1 = new JPanel();
		panelH3M1.setBounds(0, 0, 114, 35);
		panelH3M1.setBackground(COLOR_TRANSPARENT);
		panelH3Ms.add(panelH3M1);
		panelH3M1.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH3M1T4 = new JLabel();
		panelH3M1.add(lblH3M1T4);
		lblH3M1T4.setIcon(meld3Img);
		
		lblH3M1T3 = new JLabel();
		panelH3M1.add(lblH3M1T3);
		lblH3M1T3.setIcon(meld3Img);
		
		lblH3M1T2 = new JLabel();
		panelH3M1.add(lblH3M1T2);
		lblH3M1T2.setIcon(meld3Img);
		
		lblH3M1T1 = new JLabel();
		panelH3M1.add(lblH3M1T1);
		lblH3M1T1.setIcon(meld3Img);
		
		
		
		
		panelH3M4 = new JPanel();
		panelH3M4.setBounds(360, 0, 114, 35);
		panelH3M4.setBackground(COLOR_TRANSPARENT);
		panelH3M4.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH3M4T4 = new JLabel();
		panelH3M4.add(lblH3M4T4);
		lblH3M4T4.setIcon(meld3Img);
		
		lblH3M4T3 = new JLabel();
		panelH3M4.add(lblH3M4T3);
		lblH3M4T3.setIcon(meld3Img);
		
		lblH3M4T2 = new JLabel();
		panelH3M4.add(lblH3M4T2);
		lblH3M4T2.setIcon(meld3Img);
		
		
		
		panelH3M2 = new JPanel();
		panelH3M2.setBounds(120, 0, 114, 35);
		panelH3M2.setBackground(COLOR_TRANSPARENT);
		panelH3Ms.add(panelH3M2);
		panelH3M2.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH3M2T4 = new JLabel();
		panelH3M2.add(lblH3M2T4);
		lblH3M2T4.setIcon(meld3Img);
		
		lblH3M2T3 = new JLabel();
		panelH3M2.add(lblH3M2T3);
		lblH3M2T3.setIcon(meld3Img);
		
		lblH3M2T2 = new JLabel();
		panelH3M2.add(lblH3M2T2);
		lblH3M2T2.setIcon(meld3Img);
		
		lblH3M2T1 = new JLabel();
		panelH3M2.add(lblH3M2T1);
		lblH3M2T1.setIcon(meld3Img);
		
		
		
		
		panelH3M3 = new JPanel();
		panelH3M3.setBounds(240, 0, 114, 35);
		panelH3M3.setBackground(COLOR_TRANSPARENT);
		panelH3Ms.add(panelH3M3);
		panelH3M3.setLayout(new GridLayout(1, 4, 0, 0));
		
		lblH3M3T4 = new JLabel();
		panelH3M3.add(lblH3M3T4);
		lblH3M3T4.setIcon(meld3Img);
		
		lblH3M3T3 = new JLabel();
		panelH3M3.add(lblH3M3T3);
		lblH3M3T3.setIcon(meld3Img);
		
		lblH3M3T2 = new JLabel();
		panelH3M3.add(lblH3M3T2);
		lblH3M3T2.setIcon(meld3Img);
		
		lblH3M3T1 = new JLabel();
		panelH3M3.add(lblH3M3T1);
		lblH3M3T1.setIcon(meld3Img);
		panelH3Ms.add(panelH3M4);
		
		lblH3M4T1 = new JLabel();
		panelH3M4.add(lblH3M4T1);
		lblH3M4T1.setIcon(meld3Img);
		
		
		
		
		
		
		
		
		
		
		
		panelPlayer4 = new JPanel();
		panelPlayer4.setBounds(30, 72, 78, 667);
		panelPlayer4.setBackground(COLOR_TRANSPARENT);
		panelTable.add(panelPlayer4);
		panelPlayer4.setLayout(null);
		
		
		
		
		panelH4 = new JPanel();
		panelH4.setBounds(36, 42, TILE_BIG_HEIGHT, TILE_BIG_WIDTH*SIZE_HAND);
		panelH4.setBackground(COLOR_TRANSPARENT);
		panelPlayer4.add(panelH4);
		panelH4.setLayout(new GridLayout(14, 1, 0, 0));
		
		lblH4T1 = new JLabel();
		lblH4T1.setBounds(0, 0, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T1.setIcon(h4Img);
		panelH4.add(lblH4T1);
		
		lblH4T2 = new JLabel();
		lblH4T2.setBounds(0, 36, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T2.setIcon(h4Img);
		panelH4.add(lblH4T2);
		
		lblH4T3 = new JLabel();
		lblH4T3.setBounds(0, 72, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T3.setIcon(h4Img);
		panelH4.add(lblH4T3);
		
		lblH4T4 = new JLabel();
		lblH4T4.setBounds(0, 108, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T4.setIcon(h4Img);
		panelH4.add(lblH4T4);
		
		lblH4T5 = new JLabel();
		lblH4T5.setBounds(0, 144, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T5.setIcon(h4Img);
		panelH4.add(lblH4T5);
		
		lblH4T6 = new JLabel();
		lblH4T6.setBounds(0, 180, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T6.setIcon(h4Img);
		panelH4.add(lblH4T6);
		
		lblH4T7 = new JLabel();
		lblH4T7.setBounds(0, 216, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T7.setIcon(h4Img);
		panelH4.add(lblH4T7);
		
		lblH4T8 = new JLabel();
		lblH4T8.setBounds(0, 252, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T8.setIcon(h4Img);
		panelH4.add(lblH4T8);
		
		lblH4T9 = new JLabel();
		lblH4T9.setBounds(0, 288, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T9.setIcon(h4Img);
		panelH4.add(lblH4T9);
		
		lblH4T10 = new JLabel();
		lblH4T10.setBounds(0, 324, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T10.setIcon(h4Img);
		panelH4.add(lblH4T10);
		
		lblH4T11 = new JLabel();
		lblH4T11.setBounds(0, 360, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T11.setIcon(h4Img);
		panelH4.add(lblH4T11);
		
		lblH4T12 = new JLabel();
		lblH4T12.setBounds(0, 396, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T12.setIcon(h4Img);
		panelH4.add(lblH4T12);
		
		lblH4T13 = new JLabel();
		lblH4T13.setBounds(0, 432, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T13.setIcon(h4Img);
		panelH4.add(lblH4T13);
		
		lblH4T14 = new JLabel();
		lblH4T14.setBounds(0, 468, TILE_BIG_HEIGHT, TILE_BIG_WIDTH);
		lblH4T14.setIcon(h4Img);
		panelH4.add(lblH4T14);
		
		
		
		
		
		panelH4Ms = new JPanel();
		panelH4Ms.setBounds(0, 100, 35, 474);
		panelH4Ms.setBackground(COLOR_TRANSPARENT);
		panelPlayer4.add(panelH4Ms);
		panelH4Ms.setLayout(null);
		
		
		
		
		panelH4M1 = new JPanel();
		panelH4M1.setBounds(0, 360, 35, 114);
		panelH4M1.setBackground(COLOR_TRANSPARENT);
		panelH4Ms.add(panelH4M1);
		panelH4M1.setLayout(new GridLayout(4, 1, 0, 0));
		
		
		lblH4M1T1 = new JLabel();
		panelH4M1.add(lblH4M1T1);
		lblH4M1T1.setIcon(meld4Img);
		
		lblH4M1T2 = new JLabel();
		panelH4M1.add(lblH4M1T2);
		lblH4M1T2.setIcon(meld4Img);
		
		lblH4M1T3 = new JLabel();
		panelH4M1.add(lblH4M1T3);
		lblH4M1T3.setIcon(meld4Img);
		
		lblH4M1T4 = new JLabel();
		panelH4M1.add(lblH4M1T4);
		lblH4M1T4.setIcon(meld4Img);
		
		
		
		
		panelH4M4 = new JPanel();
		panelH4M4.setBounds(0, 0, 35, 114);
		panelH4M4.setBackground(COLOR_TRANSPARENT);
		panelH4M4.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH4M4T1 = new JLabel();
		panelH4M4.add(lblH4M4T1);
		lblH4M4T1.setIcon(meld4Img);
		
		lblH4M4T2 = new JLabel();
		panelH4M4.add(lblH4M4T2);
		lblH4M4T2.setIcon(meld4Img);
		
		lblH4M4T3 = new JLabel();
		panelH4M4.add(lblH4M4T3);
		lblH4M4T3.setIcon(meld4Img);
		
		lblH4M4T4 = new JLabel();
		panelH4M4.add(lblH4M4T4);
		lblH4M4T4.setIcon(meld4Img);
		
		
		
		panelH4M2 = new JPanel();
		panelH4M2.setBounds(0, 240, 35, 114);
		panelH4M2.setBackground(COLOR_TRANSPARENT);
		panelH4Ms.add(panelH4M2);
		panelH4M2.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH4M2T1 = new JLabel();
		panelH4M2.add(lblH4M2T1);
		lblH4M2T1.setIcon(meld4Img);
		
		lblH4M2T2 = new JLabel();
		panelH4M2.add(lblH4M2T2);
		lblH4M2T2.setIcon(meld4Img);
		
		lblH4M2T3 = new JLabel();
		panelH4M2.add(lblH4M2T3);
		lblH4M2T3.setIcon(meld4Img);
		
		lblH4M2T4 = new JLabel();
		panelH4M2.add(lblH4M2T4);
		lblH4M2T4.setIcon(meld4Img);
		
		
		
		
		panelH4M3 = new JPanel();
		panelH4M3.setBounds(0, 120, 35, 114);
		panelH4M3.setBackground(COLOR_TRANSPARENT);
		panelH4Ms.add(panelH4M3);
		panelH4M3.setLayout(new GridLayout(4, 1, 0, 0));
		
		lblH4M3T1 = new JLabel();
		panelH4M3.add(lblH4M3T1);
		lblH4M3T1.setIcon(meld4Img);
		
		lblH4M3T2 = new JLabel();
		panelH4M3.add(lblH4M3T2);
		lblH4M3T2.setIcon(meld4Img);
		
		lblH4M3T3 = new JLabel();
		panelH4M3.add(lblH4M3T3);
		lblH4M3T3.setIcon(meld4Img);
		
		lblH4M3T4 = new JLabel();
		panelH4M3.add(lblH4M3T4);
		lblH4M3T4.setIcon(meld4Img);
		panelH4Ms.add(panelH4M4);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		panelSidebar = new JPanel();
//		panelSidebar.setBounds(874, 0, 276, WINDOW_HEIGHT);
		panelSidebar.setBounds(panelTable.getWidth(), 0, 276, WINDOW_HEIGHT);
		panelSidebar.setBackground(COLOR_SIDEBAR);
		contentPane.add(panelSidebar);
		panelSidebar.setLayout(null);
		
		lblWeHaveFun = new JLabel();
		lblWeHaveFun.setVerticalAlignment(SwingConstants.TOP);
		lblWeHaveFun.setIcon(sheepImg);
		lblWeHaveFun.setBounds(4, 5, 270, 218);
//		lblWeHaveFun.setBounds(-500, -500, 270, 345);
		panelSidebar.add(lblWeHaveFun);
		
		
		
		
		
		

		JButton btnBlankAll = new JButton("Blank");
		btnBlankAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				blankEverything();
			}
		});
		btnBlankAll.setBounds(204, 358, 68, 23);
		btnBlankAll.setVisible(DEBUG_BUTTONS_VISIBLE);
		panelSidebar.add(btnBlankAll);
		
		
		
		
		
		
		
		final TableGUI thisguy = this;
		JButton btnRandAll = new JButton("Rand");
		btnRandAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Random randGen = new Random();
				final int RANDLIMIT = 38;
				
				//randomize tiles
				for (JLabel l: larryH1)
					l.setIcon(garryTiles[SEAT1][BIG][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryP1)
					l.setIcon(garryTiles[SEAT1][SMALL][randGen.nextInt(RANDLIMIT)]);
				for (JLabel[] lar: larryH1Ms)
					for (JLabel l: lar)
						if (randGen.nextInt(8) == 1) l.setIcon(garryTiles[1][1][randGen.nextInt(RANDLIMIT)]);
						else l.setIcon(garryTiles[SEAT1][SMALL][randGen.nextInt(RANDLIMIT)]);
				
				
				for (JLabel l: larryH2)
					if (!DEFAULT_OPTION_REVEAL_HANDS) l.setIcon(garryTiles[SEAT2][BIG][0]);
					else l.setIcon(garryTiles[SEAT2][BIG][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryP2)
					if (randGen.nextInt(34) != 1) l.setIcon(garryTiles[1][1][randGen.nextInt(RANDLIMIT)]);
					else l.setIcon(garryTiles[SEAT2][SMALL][randGen.nextInt(RANDLIMIT)]);
				for (JLabel[] lar: larryH2Ms)
					for (JLabel l: lar)
						l.setIcon(garryTiles[SEAT2][SMALL][randGen.nextInt(RANDLIMIT)]);
				
				
				for (JLabel l: larryH3)
					if (!DEFAULT_OPTION_REVEAL_HANDS) l.setIcon(garryTiles[SEAT3][BIG][0]);
					else l.setIcon(garryTiles[SEAT3][BIG][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryP3)
					l.setIcon(garryTiles[SEAT3][SMALL][randGen.nextInt(RANDLIMIT)]);
				for (JLabel[] lar: larryH3Ms)
					for (JLabel l: lar)
						l.setIcon(garryTiles[SEAT3][SMALL][randGen.nextInt(RANDLIMIT)]);
				
				
				for (JLabel l: larryH4)
					if (!DEFAULT_OPTION_REVEAL_HANDS) l.setIcon(garryTiles[SEAT4][BIG][0]);
					else l.setIcon(garryTiles[SEAT4][BIG][randGen.nextInt(RANDLIMIT)]);
				for (JLabel l: larryP4)
					l.setIcon(garryTiles[SEAT4][SMALL][randGen.nextInt(RANDLIMIT)]);
				for (JLabel[] lar: larryH4Ms)
					for (JLabel l: lar)
						l.setIcon(garryTiles[SEAT4][SMALL][randGen.nextInt(RANDLIMIT)]);
				
				
				//randomize round info
				larryInfoRound[0].setIcon(garryWinds[BIG][randGen.nextInt(SIZE_GARRY_WINDS)]);
				larryInfoRound[1].setText(Integer.toString(1+randGen.nextInt(SIZE_GARRY_WINDS)));
				
				//randomize player info
				for (JLabel[] infoP: larryInfoPlayers){
					infoP[0].setIcon(garryWinds[SMALL][randGen.nextInt(SIZE_GARRY_WINDS)]);
					infoP[1].setText(Integer.toString(100*randGen.nextInt(1280)));
					if (randGen.nextBoolean()) infoP[2].setIcon(null);
					else infoP[2].setIcon(garryOther[0]);
				}
				
				
				for (JLabel l: larryDW)
					l.setIcon(garryTiles[SEAT1][SMALL][randGen.nextInt(RANDLIMIT)]);

				lblWallTilesLeft.setText(Integer.toString(1+randGen.nextInt(122)));
				
				
				int exclLoc = randGen.nextInt(EXCLAMATION_LOCS.length);
				lblExclamation.setVisible(true);
				lblExclamation.setBounds(EXCLAMATION_LOCS[exclLoc][0], EXCLAMATION_LOCS[exclLoc][1], lblExclamation.getWidth(), lblExclamation.getHeight());
				
				String[] results = {"Draw (Washout)", "Draw (Kyuushuu)", "Draw (4 kans)", "Draw (4 riichi)", "Draw (4 wind)",
									"East wins! (TSUMO)", "East wins! (RON)", 
									"South wins! (TSUMO)", "South wins! (RON)", 
									"West wins! (TSUMO)", "West wins! (RON)", 
									"North wins! (TSUMO)", "North wins! (RON)" };
				panResult.setVisible(true);
				lblResult.setText(results[randGen.nextInt(results.length)]);
				
				
				//randomize turn indicator
				for (JPanel p: parryTurnInds) p.setVisible(false);
				parryTurnInds[randGen.nextInt(parryTurnInds.length)].setVisible(true);
				
				lblFun.setIcon(garryOmake[randGen.nextInt(garryOmake.length)]);
				
				thisguy.repaint();
				
			}
		});
		btnRandAll.setBounds(204, 333, 65, 23);
		btnRandAll.setVisible(DEBUG_BUTTONS_VISIBLE);
		panelSidebar.add(btnRandAll);
		
		
		
		
		
		
		panelCalls = new JPanel();
		panelCalls.setBounds(28, 440, 204, 147);
		panelCalls.setBackground(COLOR_CALL_PANEL);
		panelSidebar.add(panelCalls);
		panelCalls.setLayout(null);
		
		
		btnCallNone = new JButton("No call");
		btnCallNone.setActionCommand("None");
		btnCallNone.setBounds(0, 0, 89, 23);
		panelCalls.add(btnCallNone);
		
		btnCallChi = new JButton("Chi");
		btnCallChi.setActionCommand("Chi");
		btnCallChi.setBounds(1, 24, 60, 23);
		panelCalls.add(btnCallChi);
		
		btnCallChiL = new JButton("Chi-L");
		btnCallChiL.setActionCommand("Chi-L");
		btnCallChiL.setBounds(0, 48, 67, 23);
		panelCalls.add(btnCallChiL);
		
		btnCallChiM = new JButton("Chi-M");
		btnCallChiM.setActionCommand("Chi-M");
		btnCallChiM.setBounds(67, 48, 67, 23);
		panelCalls.add(btnCallChiM);
		
		btnCallChiH = new JButton("Chi-H");
		btnCallChiH.setActionCommand("Chi-H");
		btnCallChiH.setBounds(135, 48, 67, 23);
		panelCalls.add(btnCallChiH);
		
		btnCallPon = new JButton("Pon");
		btnCallPon.setActionCommand("Pon");
		btnCallPon.setBounds(1, 72, 64, 23);
		panelCalls.add(btnCallPon);
		
		btnCallKan = new JButton("Kan");
		btnCallKan.setActionCommand("Kan");
		btnCallKan.setBounds(65, 72, 64, 23);
		panelCalls.add(btnCallKan);
		
		btnCallRon = new JButton("Ron!!!");
		btnCallRon.setActionCommand("Ron");
		btnCallRon.setBounds(2, 96, 103, 51);
		panelCalls.add(btnCallRon);
		
		
		
		
		
		
		
		
		panelTActions = new JPanel();
		panelTActions.setBounds(104, 0, 100, 147);
		panelTActions.setOpaque(false);
		panelCalls.add(panelTActions);
		panelTActions.setLayout(null);

		
		buttonRiichi = new JButton("Riichi?");
		buttonRiichi.setBounds(11, 0, 89, 23);
		panelTActions.add(buttonRiichi);
		buttonRiichi.setActionCommand("Riichi");
		
		buttonAnkan = new JButton("Ankan?");
		buttonAnkan.setBounds(11, 24, 89, 23);
		panelTActions.add(buttonAnkan);
		buttonAnkan.setActionCommand("Ankan");
		
		buttonMinkan = new JButton("Minkan?");
		buttonMinkan.setBounds(11, 48, 89, 23);
		panelTActions.add(buttonMinkan);
		buttonMinkan.setActionCommand("Minkan");
		
		buttonTsumo = new JButton("Tsumo!!!");
		buttonTsumo.setBounds(0, 96, 100, 51);
		panelTActions.add(buttonTsumo);
		buttonTsumo.setActionCommand("Tsumo");
		
		
		
		
		
		
		
		
		
		panelWallSummary = new JPanel();
		panelSidebar.add(panelWallSummary);
		panelWallSummary.setLayout(null);
		panelWallSummary.setBounds(10, 319, 161, 85);
		panelWallSummary.setOpaque(false);
		

		
		panelWTL = new JPanel();
		panelWTL.setBounds(1, 62, 78, 21);
		panelWallSummary.add(panelWTL);
		panelWTL.setOpaque(false);
		panelWTL.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblWTLText = new JLabel("Left: ");
		lblWTLText.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelWTL.add(lblWTLText);
		
		lblWallTilesLeftLabel = new JLabel("122");
		lblWallTilesLeftLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelWTL.add(lblWallTilesLeftLabel);
		
		
		
		
		
		
		panelDeadWall = new JPanel();
		panelDeadWall.setBounds(0, 0, 161, 62);
		panelWallSummary.add(panelDeadWall);
//		panelDeadWall.setBackground(COLOR_TRANSPARENT);
		panelDeadWall.setBackground(COLOR_CALL_PANEL);
		panelDeadWall.setLayout(new GridLayout(2, 7, 0, 0));

		
		lblDW1 = new JLabel();
		lblDW1.setIcon(dwImg);
		lblDW1.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW1);
		
		lblDW3 = new JLabel();
		lblDW3.setIcon(dwImg);
		lblDW3.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW3);
		
		lblDW5 = new JLabel();
		lblDW5.setIcon(dwImg);
		lblDW5.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW5);
		
		lblDW7 = new JLabel();
		lblDW7.setIcon(dwImg);
		lblDW7.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW7);
		
		lblDW9 = new JLabel();
		lblDW9.setIcon(dwImg);
		lblDW9.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW9);
		
		lblDW11 = new JLabel();
		lblDW11.setIcon(dwImg);
		lblDW11.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW11);
		
		lblDW13 = new JLabel();
		lblDW13.setIcon(dwImg);
		lblDW13.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW13);
		
		lblDW2 = new JLabel();
		lblDW2.setIcon(dwImg);
		lblDW2.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW2);
		
		lblDW4 = new JLabel();
		lblDW4.setIcon(dwImg);
		lblDW4.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW4);
		
		lblDW6 = new JLabel();
		lblDW6.setIcon(dwImg);
		lblDW6.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW6);
		
		lblDW8 = new JLabel();
		lblDW8.setIcon(dwImg);
		lblDW8.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW8);
		
		lblDW10 = new JLabel();
		lblDW10.setIcon(dwImg);
		lblDW10.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW10);
		
		lblDW12 = new JLabel();
		lblDW12.setIcon(dwImg);
		lblDW12.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW12);
		
		lblDW14 = new JLabel();
		lblDW14.setIcon(dwImg);
		lblDW14.setBounds(72, 470, 23, 31);
		panelDeadWall.add(lblDW14);
		
		
		
		
		
		panelTurnInds = new JPanel();
		panelTurnInds.setBounds(0, 0, 166, 158);
		panelRoundInfo.add(panelTurnInds);
		panelTurnInds.setLayout(null);
		panelTurnInds.setOpaque(false);
		
		panelTurnInd1 = new JPanel();
		panelTurnInd1.setBounds(0, 144, 166, 13);
		panelTurnInds.add(panelTurnInd1);
		panelTurnInd1.setOpaque(false);
		panelTurnInd1.setLayout(null);
		
		lblTurnInd11 = new JLabel();
		lblTurnInd11.setBounds(1, 0, 55, 13);
		panelTurnInd1.add(lblTurnInd11);
		lblTurnInd11.setOpaque(true);
		lblTurnInd11.setBackground(COLOR_TURN_INDICATOR);
		
		lblTurnInd12 = new JLabel();
		lblTurnInd12.setBounds(110, 0, 55, 13);
		panelTurnInd1.add(lblTurnInd12);
		lblTurnInd12.setOpaque(true);
		lblTurnInd12.setBackground(COLOR_TURN_INDICATOR);
		
		

		panelTurnInd2 = new JPanel();
		panelTurnInd2.setBounds(152, 0, 13, 158);
		panelTurnInds.add(panelTurnInd2);
		panelTurnInd2.setOpaque(false);
		panelTurnInd2.setLayout(null);
		
		lblTurnInd21 = new JLabel();
		lblTurnInd21.setBounds(1, 1, 13, 52);
		panelTurnInd2.add(lblTurnInd21);
		lblTurnInd21.setOpaque(true);
		lblTurnInd21.setBackground(COLOR_TURN_INDICATOR);
		
		lblTurnInd22 = new JLabel();
		lblTurnInd22.setBounds(0, 105, 13, 52);
		panelTurnInd2.add(lblTurnInd22);
		lblTurnInd22.setOpaque(true);
		lblTurnInd22.setBackground(COLOR_TURN_INDICATOR);
		
		
		panelTurnInd3 = new JPanel();
		panelTurnInd3.setBounds(0, 1, 166, 13);
		panelTurnInds.add(panelTurnInd3);
		panelTurnInd3.setOpaque(false);
		panelTurnInd3.setLayout(null);
		
		lblTurnInd31 = new JLabel();
		lblTurnInd31.setBounds(1, 0, 55, 13);
		panelTurnInd3.add(lblTurnInd31);
		lblTurnInd31.setOpaque(true);
		lblTurnInd31.setBackground(COLOR_TURN_INDICATOR);
		
		lblTurnInd32 = new JLabel();
		lblTurnInd32.setBounds(110, 0, 55, 13);
		panelTurnInd3.add(lblTurnInd32);
		lblTurnInd32.setOpaque(true);
		lblTurnInd32.setBackground(COLOR_TURN_INDICATOR);
		
		
		panelTurnInd4 = new JPanel();
		panelTurnInd4.setBounds(1, 1, 13, 158);
		panelTurnInds.add(panelTurnInd4);
		panelTurnInd4.setOpaque(false);
		panelTurnInd4.setLayout(null);
		
		lblTurnInd41 = new JLabel();
		lblTurnInd41.setBounds(1, 1, 13, 51);
		panelTurnInd4.add(lblTurnInd41);
		lblTurnInd41.setOpaque(true);
		lblTurnInd41.setBackground(COLOR_TURN_INDICATOR);
		
		lblTurnInd42 = new JLabel();
		lblTurnInd42.setBounds(0, 105, 13, 51);
		panelTurnInd4.add(lblTurnInd42);
		lblTurnInd42.setOpaque(true);
		lblTurnInd42.setBackground(COLOR_TURN_INDICATOR);
		
		
		
		
		
		
		

		
		
		
		
		panelRoundResult = new JPanel();
		panelRoundResult.setBounds(32, 234, 212, 66);
		panelSidebar.add(panelRoundResult);
		panelRoundResult.setLayout(null);
		panelRoundResult.setBackground(COLOR_CALL_PANEL);
		panelRoundResult.setBorder(new LineBorder(new Color(0, 200, 0), 3, true));
		
		lblRoundOver = new JLabel("===ROUND OVER===");
		lblRoundOver.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRoundOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoundOver.setBounds(0, 0, 212, 33);
		panelRoundResult.add(lblRoundOver);
		
		lblRoundResult = new JLabel("KATAOKA-SAN WINS");
		lblRoundResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoundResult.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRoundResult.setBounds(0, 33, 212, 33);
		panelRoundResult.add(lblRoundResult);
		
		
		
		
		
		
		
		
		
		///////////
		
		
		
		
		////////////////////////////////////////////////////////////
		
		
		
		//load Tile labels into arrays
		larryH1[0] = lblH1T1;larryH1[1] = lblH1T2;larryH1[2] = lblH1T3;larryH1[3] = lblH1T4;larryH1[4] = lblH1T5;larryH1[5] = lblH1T6;larryH1[6] = lblH1T7;larryH1[7] = lblH1T8;larryH1[8] = lblH1T9;larryH1[9] = lblH1T10;larryH1[10] = lblH1T11;larryH1[11] = lblH1T12;larryH1[12] = lblH1T13;larryH1[13] = lblH1T14;
		larryH1M1[0] = lblH1M1T1;larryH1M1[1] = lblH1M1T2;larryH1M1[2] = lblH1M1T3;larryH1M1[3] = lblH1M1T4;larryH1M2[0] = lblH1M2T1;larryH1M2[1] = lblH1M2T2;larryH1M2[2] = lblH1M2T3;larryH1M2[3] = lblH1M2T4;larryH1M3[0] = lblH1M3T1;larryH1M3[1] = lblH1M3T2;larryH1M3[2] = lblH1M3T3;larryH1M3[3] = lblH1M3T4;larryH1M4[0] = lblH1M4T1;larryH1M4[1] = lblH1M4T2;larryH1M4[2] = lblH1M4T3;larryH1M4[3] = lblH1M4T4;
		larryP1[0] = lblP1T1;larryP1[1] = lblP1T2;larryP1[2] = lblP1T3;larryP1[3] = lblP1T4;larryP1[4] = lblP1T5;larryP1[5] = lblP1T6;larryP1[6] = lblP1T7;larryP1[7] = lblP1T8;larryP1[8] = lblP1T9;larryP1[9] = lblP1T10;larryP1[10] = lblP1T11;larryP1[11] = lblP1T12;larryP1[12] = lblP1T13;larryP1[13] = lblP1T14;larryP1[14] = lblP1T15;larryP1[15] = lblP1T16;larryP1[16] = lblP1T17;larryP1[17] = lblP1T18;larryP1[18] = lblP1T19;larryP1[19] = lblP1T20;larryP1[20] = lblP1T21;larryP1[21] = lblP1T22;larryP1[22] = lblP1T23;larryP1[23] = lblP1T24;
		
		larryH2[0] = lblH2T1;
		larryH2M1[0] = lblH2M1T1;larryH2M1[1] = lblH2M1T2;larryH2M1[2] = lblH2M1T3;larryH2M1[3] = lblH2M1T4;larryH2M2[0] = lblH2M2T1;larryH2M2[1] = lblH2M2T2;larryH2M2[2] = lblH2M2T3;larryH2M2[3] = lblH2M2T4;larryH2M3[0] = lblH2M3T1;larryH2M3[1] = lblH2M3T2;larryH2M3[2] = lblH2M3T3;larryH2M3[3] = lblH2M3T4;larryH2M4[0] = lblH2M4T1;larryH2M4[1] = lblH2M4T2;larryH2M4[2] = lblH2M4T3;larryH2M4[3] = lblH2M4T4;
		larryP2[0] = lblP2T1;larryP2[1] = lblP2T2;larryP2[2] = lblP2T3;larryP2[3] = lblP2T4;larryP2[4] = lblP2T5;larryP2[5] = lblP2T6;larryP2[6] = lblP2T7;larryP2[7] = lblP2T8;larryP2[8] = lblP2T9;larryP2[9] = lblP2T10;larryP2[10] = lblP2T11;larryP2[11] = lblP2T12;larryP2[12] = lblP2T13;larryP2[13] = lblP2T14;larryP2[14] = lblP2T15;larryP2[15] = lblP2T16;larryP2[16] = lblP2T17;larryP2[17] = lblP2T18;larryP2[18] = lblP2T19;larryP2[19] = lblP2T20;larryP2[20] = lblP2T21;larryP2[21] = lblP2T22;larryP2[22] = lblP2T23;larryP2[23] = lblP2T24;
		
		larryH3[0] = lblH3T1;larryH3[1] = lblH3T2;larryH3[2] = lblH3T3;larryH3[3] = lblH3T4;larryH3[4] = lblH3T5;larryH3[5] = lblH3T6;larryH3[6] = lblH3T7;larryH3[7] = lblH3T8;larryH3[8] = lblH3T9;larryH3[9] = lblH3T10;larryH3[10] = lblH3T11;larryH3[11] = lblH3T12;larryH3[12] = lblH3T13;larryH3[13] = lblH3T14;
		larryH3M1[0] = lblH3M1T1;larryH3M1[1] = lblH3M1T2;larryH3M1[2] = lblH3M1T3;larryH3M1[3] = lblH3M1T4;larryH3M2[0] = lblH3M2T1;larryH3M2[1] = lblH3M2T2;larryH3M2[2] = lblH3M2T3;larryH3M2[3] = lblH3M2T4;larryH3M3[0] = lblH3M3T1;larryH3M3[1] = lblH3M3T2;larryH3M3[2] = lblH3M3T3;larryH3M3[3] = lblH3M3T4;larryH3M4[0] = lblH3M4T1;larryH3M4[1] = lblH3M4T2;larryH3M4[2] = lblH3M4T3;larryH3M4[3] = lblH3M4T4;
		larryP3[0] = lblP3T1;larryP3[1] = lblP3T2;larryP3[2] = lblP3T3;larryP3[3] = lblP3T4;larryP3[4] = lblP3T5;larryP3[5] = lblP3T6;larryP3[6] = lblP3T7;larryP3[7] = lblP3T8;larryP3[8] = lblP3T9;larryP3[9] = lblP3T10;larryP3[10] = lblP3T11;larryP3[11] = lblP3T12;larryP3[12] = lblP3T13;larryP3[13] = lblP3T14;larryP3[14] = lblP3T15;larryP3[15] = lblP3T16;larryP3[16] = lblP3T17;larryP3[17] = lblP3T18;larryP3[18] = lblP3T19;larryP3[19] = lblP3T20;larryP3[20] = lblP3T21;larryP3[21] = lblP3T22;larryP3[22] = lblP3T23;larryP3[23] = lblP3T24;
		
		larryH4[0] = lblH4T1;larryH4[1] = lblH4T2;larryH4[2] = lblH4T3;larryH4[3] = lblH4T4;larryH4[4] = lblH4T5;larryH4[5] = lblH4T6;larryH4[6] = lblH4T7;larryH4[7] = lblH4T8;larryH4[8] = lblH4T9;larryH4[9] = lblH4T10;larryH4[10] = lblH4T11;larryH4[11] = lblH4T12;larryH4[12] = lblH4T13;larryH4[13] = lblH4T14;
		larryH4M1[0] = lblH4M1T1;larryH4M1[1] = lblH4M1T2;larryH4M1[2] = lblH4M1T3;larryH4M1[3] = lblH4M1T4;larryH4M2[0] = lblH4M2T1;larryH4M2[1] = lblH4M2T2;larryH4M2[2] = lblH4M2T3;larryH4M2[3] = lblH4M2T4;larryH4M3[0] = lblH4M3T1;larryH4M3[1] = lblH4M3T2;larryH4M3[2] = lblH4M3T3;larryH4M3[3] = lblH4M3T4;larryH4M4[0] = lblH4M4T1;larryH4M4[1] = lblH4M4T2;larryH4M4[2] = lblH4M4T3;larryH4M4[3] = lblH4M4T4;
		larryP4[0] = lblP4T1;larryP4[1] = lblP4T2;larryP4[2] = lblP4T3;larryP4[3] = lblP4T4;larryP4[4] = lblP4T5;larryP4[5] = lblP4T6;larryP4[6] = lblP4T7;larryP4[7] = lblP4T8;larryP4[8] = lblP4T9;larryP4[9] = lblP4T10;larryP4[10] = lblP4T11;larryP4[11] = lblP4T12;larryP4[12] = lblP4T13;larryP4[13] = lblP4T14;larryP4[14] = lblP4T15;larryP4[15] = lblP4T16;larryP4[16] = lblP4T17;larryP4[17] = lblP4T18;larryP4[18] = lblP4T19;larryP4[19] = lblP4T20;larryP4[20] = lblP4T21;larryP4[21] = lblP4T22;larryP4[22] = lblP4T23;larryP4[23] = lblP4T24;		
		
		
		//load Round Info labels into arrays
		larryInfoRound[0] = lblRIndWind;larryInfoRound[1] = lblRIndNum;
		larryInfoP1[0] = lblInfoP1Wind;larryInfoP1[1] = lblInfoP1Points;larryInfoP1[2] = lblInfoP1Riichi;
		larryInfoP2[0] = lblInfoP2Wind;larryInfoP2[1] = lblInfoP2Points;larryInfoP2[2] = lblInfoP2Riichi;
		larryInfoP3[0] = lblInfoP3Wind;larryInfoP3[1] = lblInfoP3Points;larryInfoP3[2] = lblInfoP3Riichi;
		larryInfoP4[0] = lblInfoP4Wind;larryInfoP4[1] = lblInfoP4Points;larryInfoP4[2] = lblInfoP4Riichi;
		
		//load turn indicator panels into arrays
		parryTurnInds[0] = panelTurnInd1;parryTurnInds[1] = panelTurnInd2;parryTurnInds[2] = panelTurnInd3;parryTurnInds[3] = panelTurnInd4;
		
		//round results
		panResult = panelRoundResult; lblResult = lblRoundResult;
		
		lblExclamation = lblExclamationLabel;
		lblFun = lblWeHaveFun;
		
		
		//load deadwall labels into arrays
		larryDW[0] = lblDW1;larryDW[1] = lblDW2;larryDW[2] = lblDW3;larryDW[3] = lblDW4;larryDW[4] = lblDW5;larryDW[5] = lblDW6;larryDW[6] = lblDW7;larryDW[7] = lblDW8;larryDW[8] = lblDW9;larryDW[9] = lblDW10;larryDW[10] = lblDW11;larryDW[11] = lblDW12;larryDW[12] = lblDW13;larryDW[13] = lblDW14;
		
		lblWallTilesLeft = lblWallTilesLeftLabel; 
		
		
		//load call buttons into array
		barryCalls[0] = btnCallNone;barryCalls[1] = btnCallChiL;barryCalls[2] = btnCallChiM;barryCalls[3] = btnCallChiH;barryCalls[4] = btnCallPon;barryCalls[5] = btnCallKan;barryCalls[6] = btnCallRon;
		barryCalls[7] = btnCallChi;
		
		barryTActions[0] = buttonRiichi;barryTActions[1] = buttonAnkan;barryTActions[2] = buttonMinkan;barryTActions[3] = buttonTsumo;
		
		panTable = panelTable;
		panSidebar = panelSidebar;
		
		
		panP1 = panelP1;panP2 = panelP2;panP3 = panelP3;panP4 = panelP4;
		panPlayer1 = panelPlayer1;panPlayer2 = panelPlayer2;panPlayer3 = panelPlayer3;panPlayer4 = panelPlayer4;
		panCalls = panelCalls;
		panRoundInfo = panelRoundInfo; panRndInfBackground = panelRndInfBackground;
		panMidTable = panelMidTable;
		panWallSummary = panelWallSummary;
		btnBlank = btnBlankAll; btnRand = btnRandAll;
		

		
		//set call button attributes
		CallListener callListener = new CallListener();
		for (JButton b: barryCalls){
			b.addActionListener(callListener);
			
			b.setContentAreaFilled(false);
			b.setRolloverEnabled(false);
			b.setFocusPainted(false);
			b.setOpaque(false);
		}
		TurnActionListener taListener = new TurnActionListener();
		for (JButton b: barryTActions){
			b.addActionListener(taListener);
			
			b.setContentAreaFilled(false);
			b.setRolloverEnabled(false);
			b.setFocusPainted(false);
			b.setOpaque(false);
		}
		
		
		
		
		for (JLabel l: larryP1) {l.setHorizontalAlignment(SwingConstants.CENTER);}
		for (JLabel l: larryP3) {l.setHorizontalAlignment(SwingConstants.CENTER);}
		
		
	}
	
	
	
	
	//put image icons into arrays
	private void __loadImagesIntoArrays(){
		
		final int NUM_TILES_PLUS_TILEBACK = 35;
		
		ImageRotator rotators[] = {new ImageRotator(0), new ImageRotator(-90), new ImageRotator(180), new ImageRotator(90)};
		
		
		//load Tile images into array
		for(int seat = 0; seat < SIZE_NUM_SEATS; seat++){
			//get tileback and each of the 34 tiles
			//garryTiles[seat number][0=big,1=small][tile number]
			for (int tile = 0; tile < NUM_TILES_PLUS_TILEBACK; tile++){
				garryTiles[seat][BIG][tile] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/" + tile + ".png")));
				garryTiles[seat][SMALL][tile] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/" + tile + ".png")));
			}
			
			//get red fives
			garryTiles[seat][BIG][GARRYINDEX_RED5M] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/5r.png")));
			garryTiles[seat][SMALL][GARRYINDEX_RED5M] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/5r.png")));
			
			garryTiles[seat][BIG][GARRYINDEX_RED5P] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/14r.png")));
			garryTiles[seat][SMALL][GARRYINDEX_RED5P] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/14r.png")));

			garryTiles[seat][BIG][GARRYINDEX_RED5S] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/23r.png")));
			garryTiles[seat][SMALL][GARRYINDEX_RED5S] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/23r.png")));
		}
		
		
		//load Wind images into array
		garryWinds[BIG][EAST] = new ImageIcon(getClass().getResource("/res/img/winds/transE.png"));
		garryWinds[BIG][SOUTH] = new ImageIcon(getClass().getResource("/res/img/winds/transS.png"));
		garryWinds[BIG][WEST] = new ImageIcon(getClass().getResource("/res/img/winds/transW.png"));
		garryWinds[BIG][NORTH] = new ImageIcon(getClass().getResource("/res/img/winds/transN.png"));
		garryWinds[SMALL][EAST] = new ImageIcon(getClass().getResource("/res/img/winds/small/transEs.png"));
		garryWinds[SMALL][SOUTH] = new ImageIcon(getClass().getResource("/res/img/winds/small/transSs.png"));
		garryWinds[SMALL][WEST] = new ImageIcon(getClass().getResource("/res/img/winds/small/transWs.png"));
		garryWinds[SMALL][NORTH] = new ImageIcon(getClass().getResource("/res/img/winds/small/transNs.png"));
		
		//load Other (riichi stick) into array
		garryOther[0] = new ImageIcon(getClass().getResource("/res/img/other/riichiStick.png"));
		
		//load omake imgaes into array
		garryOmake[0] = new ImageIcon(getClass().getResource("/res/img/omake/sheepy2trans.png"));
		garryOmake[1] = new ImageIcon(getClass().getResource("/res/img/omake/birdClipart1.png"));
		garryOmake[2] = new ImageIcon(getClass().getResource("/res/img/omake/birdClipart2.png"));
		garryOmake[3] = new ImageIcon(getClass().getResource("/res/img/omake/birdClipart3.png"));
		garryOmake[4] = new ImageIcon(getClass().getResource("/res/img/omake/sounotori.png"));
	}
	
}

