
package majava.userinterface.graphicalinterface.window;

import majava.hand.Hand;
import majava.hand.Meld;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.CallType;
import majava.enums.Exclamation;
import majava.enums.TurnActionType;
import majava.enums.Wind;
import majava.player.Player;
import majava.summary.StateOfGame;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.tiles.TileInterface;
import majava.tiles.PondTile;
import majava.util.GameTileList;
import utility.ImageResizer;
import utility.ImageRotator;
import utility.Pauser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;

/*
a GUI for viewing and interacting with the game
	
methods:
	public:
		mutators:
		updateEverything - updates everything to reflect the current state of the game
		blankEverything - blanks labels and images
		showExclamation - displays the received exclamation and pauses briefly
		
		getClickCall - prompts a player to click a button to make a call
		getClickTurnAction - prompts a player to click a button to choose an action
*/
public class TableViewBase extends JFrame{
	private static final long serialVersionUID = -4041211467460747162L;
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~BEGIN CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	//debgug flags
	protected static final boolean DEFAULT_OPTION_REVEAL_WALL = true;
	protected static final boolean DEFAULT_OPTION_REVEAL_HANDS = true;
	protected static final boolean DEBUG_BUTTONS_VISIBLE = false;
	
	
	//window constants
	private static final String STRING_WINDOW_TITLE = "The Beaver";	
	private static final int WINDOW_WIDTH = 1120 + (-62*2 - 6);
	private static final int WINDOW_HEIGHT = 726 + 6 + (-62*2 + 25 + 18);	
	protected static final int WINDOW_TOP_BORDER_SIZE_WITH_RESIZE = 30;
	protected static final int WINDOW_TOP_BORDER_SIZE = 26;
	protected static final int WINDOW_SIDE_BORDER_SIZE_WITH_RESIZE = 8;
	protected static final int WINDOW_SIDE_BORDER_SIZE = 2;
	protected static final int WINDOW_BOTTOM_BORDER_SIZE = 8;
	protected static final int WINDOW_MENU_SIZE = 23;
	

	
	protected static final int TILE_BIG_WIDTH = 30, TILE_BIG_HEIGHT = 41;
	protected static final int TILE_SMALL_WIDTH = 23, TILE_SMALL_HEIGHT = 31;
	protected static final int PONDPANEL_NEW_WIDTH = TILE_SMALL_WIDTH*6;
	protected static final int PONDPANEL_NEW_HEIGHT = TILE_SMALL_HEIGHT*4;
	
	protected static final int POND_PANEL_WIDTH = (TILE_SMALL_WIDTH + 4) * 6;
	protected static final int POND_PANEL_HEIGHT = PONDPANEL_NEW_HEIGHT;
	
	
	
	//color constants
	protected static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);
	protected static final Color COLOR_PURPLISH =  new Color(210,0, 210, 30);
	
	protected static final Color COLOR_TABLE = new Color(0, 140, 0, 100);
	protected static final Color COLOR_MID_TABLE = COLOR_TRANSPARENT;
	protected static final Color COLOR_SIDEBAR = new Color(0, 255, 0, 100);
	protected static final Color COLOR_RINF_PANEL = new Color(0,255,255,35);
	protected static final Color COLOR_RIND = new Color(0,155,155,35);
	protected static final Color COLOR_TURN_INDICATOR = Color.YELLOW;
	protected static final Color COLOR_CALL_PANEL = COLOR_PURPLISH;
	protected static final Color COLOR_RESULT_PANEL = COLOR_PURPLISH;
	protected static final Color COLOR_WALL_SUMMARY_PANEL = COLOR_PURPLISH;
	protected static final Color COLOR_EXCLAMATION = new Color(210, 100, 210);
	
	protected static final Color COLOR_POND_CALLED_TILE = new Color(250, 0, 0, 250);
	protected static final Color COLOR_POND_RIICHI_TILE = new Color(0, 0, 250, 250);
	protected static final Color COLOR_POND_DISCARD_TILE = Color.YELLOW;
	
	
	private static final int[][] EXCLAMATION_LOCS =  {{99, 594}, {570, 298}, {480, 36}, {3, 298}};
	
	private static final double SCALER_TINY = .3;
	protected static final ImageRotator[] rotators = {new ImageRotator(0), new ImageRotator(-90), new ImageRotator(180), new ImageRotator(90)};
	protected static final ImageResizer tinyResizer = new ImageResizer(SCALER_TINY);
	
	
	protected static final int NUM_PLAYERS = 4, NUM_SEATS = NUM_PLAYERS;
	protected static final int NUM_WINDS = 4;
	protected static final int SIZE_HAND = 14;
	protected static final int SIZE_MELDPANEL = 4;
	protected static final int SIZE_MELD = 4;
	protected static final int SIZE_WALL = 34;
	protected static final int SIZE_NORMAL_WALL = 122;
	protected static final int SIZE_DEAD_WALL = 14;
	protected static final int SIZE_TINY_WALL = 69;
	protected static final int OFFSET_DEAD_WALL = SIZE_WALL * 4 - SIZE_DEAD_WALL;
	protected static final int POS_DORA_1 = 8;
	protected static final int SIZE_POND = 24;
	protected static final int SIZE_LARRY_INFOPLAYER = 3;
	protected static final int SIZE_LARRY_INFOROUND = 3;
	
	
	private static final int SIZE_GARRY_TILES = 38, SIZE_GARRY_OTHER = 1, SIZE_GARRY_OMAKE = 5;
	private static final int RED5M = 35,  RED5P = 36, RED5S = 37, TILEBACK = 0, NULL_TILE_IMAGE_ID = -1;
	private static final int GARRYINDEX_OTHER_RIICHI = 0;
	

	private static final int LARRY_INFOROUND_ROUNDWIND = 0, LARRY_INFOROUND_ROUNDNUM = 1, LARRY_INFOROUND_BONUSNUM = 2;
	private static final int LARRY_INFOPLAYER_SEATWIND = 0, LARRY_INFOPLAYER_POINTS = 1, LARRY_INFOPLAYER_RIICHI = 2;
	
	private static final int BARRY_TACTIONS_RIICHI = 0, BARRY_TACTIONS_ANKAN = 1, BARRY_TACTIONS_MINKAN = 2, BARRY_TACTIONS_TSUMO = 3;
	private static final int BARRY_CALLS_NONE = 0, BARRY_CALLS_CHI_L = 1, BARRY_CALLS_CHI_M = 2, BARRY_CALLS_CHI_H = 3, BARRY_CALLS_PON = 4, BARRY_CALLS_KAN = 5, BARRY_CALLS_RON = 6, BARRY_CALLS_CHI = 7;
	
	
	//call/action constants
	private static final CallType DEFAULT_CALL = CallType.NONE;
	protected static final int DEFAULT_DISCARD = 0;
	protected static final int NO_DISCARD_CHOSEN = -99778;
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	
	
	
	
	
	/*......................................BEGIN LABEL ARRAYS......................................*/
	
	protected final JLabel[] larryH1 = new JLabel[SIZE_HAND], larryH2 = new JLabel[SIZE_HAND], larryH3 = new JLabel[SIZE_HAND], larryH4 = new JLabel[SIZE_HAND];
	protected final JLabel[][] larryHands = {larryH1, larryH2, larryH3, larryH4};
	
	protected final JLabel[] larryH1M1 = new JLabel[SIZE_MELD], larryH1M2 = new JLabel[SIZE_MELD], larryH1M3 = new JLabel[SIZE_MELD], larryH1M4 = new JLabel[SIZE_MELD];
	protected final JLabel[] larryH2M1 = new JLabel[SIZE_MELD], larryH2M2 = new JLabel[SIZE_MELD], larryH2M3 = new JLabel[SIZE_MELD], larryH2M4 = new JLabel[SIZE_MELD];
	protected final JLabel[] larryH3M1 = new JLabel[SIZE_MELD], larryH3M2 = new JLabel[SIZE_MELD], larryH3M3 = new JLabel[SIZE_MELD], larryH3M4 = new JLabel[SIZE_MELD];
	protected final JLabel[] larryH4M1 = new JLabel[SIZE_MELD], larryH4M2 = new JLabel[SIZE_MELD], larryH4M3 = new JLabel[SIZE_MELD], larryH4M4 = new JLabel[SIZE_MELD];
	
	protected final JLabel[][] larryH1Ms = {larryH1M1, larryH1M2, larryH1M3, larryH1M4}, larryH2Ms = {larryH2M1, larryH2M2, larryH2M3, larryH2M4}, larryH3Ms = {larryH3M1, larryH3M2, larryH3M3, larryH3M4}, larryH4Ms = {larryH4M1, larryH4M2, larryH4M3, larryH4M4};
	protected final JLabel[][][] larryHandMelds = {larryH1Ms, larryH2Ms, larryH3Ms, larryH4Ms};
		
	protected final JLabel[] larryP1 = new JLabel[SIZE_POND], larryP2 = new JLabel[SIZE_POND], larryP3 = new JLabel[SIZE_POND], larryP4 = new JLabel[SIZE_POND];
	protected final JLabel[][] larryPonds = {larryP1, larryP2, larryP3, larryP4};
	
	
	//larryInfoPlayers[player number][0 = seatwind, 1 = points, 2 = riichiStick]
	protected final JLabel[] larryInfoP1 = new JLabel[SIZE_LARRY_INFOPLAYER], larryInfoP2 = new JLabel[SIZE_LARRY_INFOPLAYER], larryInfoP3 = new JLabel[SIZE_LARRY_INFOPLAYER], larryInfoP4 = new JLabel[SIZE_LARRY_INFOPLAYER];
	protected final JLabel[][] larryInfoPlayers = {larryInfoP1, larryInfoP2, larryInfoP3, larryInfoP4};
	
	//0 = roundWind, 1 = roundNumber, 2 = roundBonusNumber
	protected final JLabel[] larryInfoRound = new JLabel[SIZE_LARRY_INFOROUND];
	protected final JPanel[] parryTurnInds = new JPanel[NUM_PLAYERS];
	
	

	protected final JButton[] barryCalls = new JButton[8], barryTActions = new JButton[4];
	
	protected final JLabel[] larryDW = new JLabel[SIZE_DEAD_WALL];
	protected final JLabel[] larryTinyW = new JLabel[SIZE_TINY_WALL];
	protected final JLabel lblWallTilesLeft;
	
	
	protected final ResultPanel panResult;
	protected final RoundResultLabelPanel panRoundResultLabel;
	protected final JLabel lblResult;
	
	protected final JLabel lblFun;
	protected final ExclamationLabel lblExclamation;
	
	protected final JPanel panTable;
	protected final JPanel panSidebar;
	protected final JMenuBar menuBar; 
	
	

	protected WallSummaryPanel panWallSummary;
	protected PromptPanel panCalls;

	protected JPanel panMidTable;
	
	protected RoundInfoSquarePanel panRoundInfoSquare;
	
	protected PlayerPanel panPlayer1, panPlayer2, panPlayer3, panPlayer4;
	protected PondPanel panP1, panP2, panP3, panP4;
	
	protected DebugButtonsPanel panDebugButtons;
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
	protected ImageIcon[] garryTilesTiny = new ImageIcon[SIZE_GARRY_TILES];
	
	
	
	protected ImageIcon[] garryWindsBig = new ImageIcon[NUM_WINDS];
	protected ImageIcon[] garryWindsSmall = new ImageIcon[NUM_WINDS];
	//garryWinds[0=big,1=small][E,S,W,N]
	protected ImageIcon[][] garryWinds = {garryWindsBig, garryWindsSmall}; 
	
	
	//0 = riichi stick, 1 = sheepy2
	protected ImageIcon[] garryOther = new ImageIcon[SIZE_GARRY_OTHER];
	
	//logos, misc images
	protected ImageIcon[] garryOmake = new ImageIcon[SIZE_GARRY_OMAKE];
	
	
	protected static final int SEAT1 = 0, SEAT2 = 1, SEAT3 = 2, SEAT4 = 3;
	protected static final int EAST = 0, SOUTH = 1, WEST = 2, NORTH = 3;
	protected static final int BIG = 0, SMALL = 1;
	protected final static int X = 0, Y = 1, W = 2, H = 3;
	
	/*+++++++++++++++++++++++++++++++++++++++END IMAGE ARRAYS+++++++++++++++++++++++++++++++++++++++*/
	
	
	
	
	
	
	/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^BEGIN MEMBER VARIABLES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
	
	protected JPanel contentPane;
	
	protected boolean cheatRevealAllWall;
	protected boolean cheatRevealAllHands;
	protected boolean[] whichHandsToReveal;
	
	
	protected RoundTracker roundTracker;
	protected StateOfGame gameState;
	
	
	private int newTurn = -1, oldTurn = -1;
	
//	private int chosenCall;
//	private int chosenTurnAction;
	private CallType chosenCall;
	private TurnActionType chosenTurnAction;
	private int chosenDiscard;
	
	/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^END MEMBER VARIABLES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
	
	
	
	
	
	
	
	
	
	
	private ImageIcon getImageIconForTile(List<? extends TileInterface> tList, int index, int seatNum, int tileSize, boolean reveal){
		if (tList.size() <= index) return null;
		
		if (!reveal) return garryTiles[seatNum][tileSize][TILEBACK];
		
		int id = getImageIdForTile(tList.get(index));
		if (id == NULL_TILE_IMAGE_ID) return null;
		
		return garryTiles[seatNum][tileSize][id];
	}
	private ImageIcon getImageIconForTile(List<? extends TileInterface> tList, int index, int seatNum, int tileSize){return getImageIconForTile(tList, index, seatNum, tileSize, true);}
	
	
	private ImageIcon getImageIconPond(List<PondTile> tList, int index, int seatNum){
		if (tList.size() <= index) return null;
		
		int id = getImageIdForTile(tList.get(index));
		if (id == NULL_TILE_IMAGE_ID) return null;
		
		
		//mark label if the pond tile has been called
		if (tList.get(index).wasCalled()){
			larryPonds[seatNum][index].setOpaque(true);
			larryPonds[seatNum][index].setBackground(COLOR_POND_CALLED_TILE);
		}
/////	riichi not implemented yet
//		if (tList.get(index).isRiichiTile()){
//			seatNum = (seatNum + 1) % NUM_PLAYERS;
//		}
		
		return garryTiles[seatNum][SMALL][id];
	}
	
	
	//gets icon for wall tile array
	protected ImageIcon getImageIconWall(TileInterface[] tList, int index, int seatNum, boolean reveal){
		if (seatNum == SEAT2) seatNum = SEAT4; else if (seatNum == SEAT4) seatNum = SEAT2;
		
		int id = getImageIdForTile(tList[index]);
		if (id == NULL_TILE_IMAGE_ID) return null;
		
		if (!reveal) id = TILEBACK;
		return garryTiles[seatNum][SMALL][id];
	}
	protected ImageIcon getImageIconWall(TileInterface[] tList, int index, int seatNum){return getImageIconWall(tList, index, seatNum, true);}
	
	
	//returns the image ID number for the tile at the given index of tList
	private static int getImageIdForTile(TileInterface t){
		if (t == null) return NULL_TILE_IMAGE_ID;
		if (t.isRedDora())
			switch(t.getId()){
			case 5: return RED5M;
			case 14: return RED5P;
			case 23: return RED5S;
		}
		return t.getId();
	}
	
	//gets icon for the given wind of the given size
	private ImageIcon getImageIconWind(Wind wind, int windSize){
		int windNum = -1;
		windNum = wind.getNum();
		
		return garryWinds[windSize][windNum];
	}
	
	
	
	
	
	public void exclamationShow(Exclamation exclamation, int seat){
		lblExclamation.showExclamation(exclamation, seat);
		repaint();
	}
	public void exclamationErase(){
		lblExclamation.erase();
		repaint();/////
	}
	
	
	
	
	
	
	public void updateEverything(){
		
//		Pauser.pauseFor(1000);/////////////////////////////////////////////////////////////////////////////
		
		
		updateDiscardMarker();
		
		//show end of round result if round is over
		if (roundTracker.roundIsOver()){
			lblResult.setText(roundTracker.getRoundResultString());
			panRoundResultLabel.setVisible(true);
			
			for (int i = 0; i < whichHandsToReveal.length; i++) whichHandsToReveal[i] = true;
		}
		
		//update hands
		for (int playerIndex = SEAT1; playerIndex <= SEAT4; playerIndex++){
			GameTileList handTiles = gameState.getHandTilesForPlayer(playerIndex);
			for (int tile = 0; tile < SIZE_HAND; tile++){
				larryHands[playerIndex][tile].setIcon(getImageIconForTile(handTiles, tile, playerIndex, BIG, whichHandsToReveal[playerIndex]));
			}
		}
		
		//update ponds
		for (int playerIndex = SEAT1; playerIndex <= SEAT4; playerIndex++){
			List<PondTile> pondTiles = pondTilesFor(playerIndex);
			for (int tileIndex = 0; tileIndex < SIZE_POND; tileIndex++){
				larryPonds[playerIndex][tileIndex].setIcon(getImageIconPond(pondTiles, tileIndex, playerIndex));
			}
		}
		
		GameTile[] wallTiles = gameState.getWallTiles();
		//update wall summary
		for (int tileIndex = 0; tileIndex < SIZE_DEAD_WALL; tileIndex++){
			larryDW[tileIndex].setIcon(getImageIconWall(wallTiles, tileIndex + OFFSET_DEAD_WALL, SEAT1, cheatRevealAllWall));
		}
		for (int tileIndex = POS_DORA_1; tileIndex >= 2*(4 - roundTracker.getNumKansMade()); tileIndex -= 2){
			if (tileIndex >= 0)/////////////////////For 5 kans to avoid -2 array index
				larryDW[tileIndex].setIcon(getImageIconWall(wallTiles, tileIndex + OFFSET_DEAD_WALL, SEAT1));
		}
		lblWallTilesLeft.setText(Integer.toString(roundTracker.getNumTilesLeftInWall()));		
		int walltileIndex = 0;
		while (wallTiles[SIZE_NORMAL_WALL-1-walltileIndex] != null){
			if (cheatRevealAllWall) larryTinyW[walltileIndex].setIcon(garryTilesTiny[getImageIdForTile(wallTiles[SIZE_NORMAL_WALL-1-walltileIndex])]);
			else larryTinyW[walltileIndex].setIcon(garryTilesTiny[TILEBACK]);
			walltileIndex++;
		}
		if (walltileIndex < SIZE_TINY_WALL) larryTinyW[walltileIndex].setIcon(null);
		
		//update melds
		for (int playerIndex = SEAT1; playerIndex <= SEAT4; playerIndex++){
			List<Meld> meldList = gameState.getPlayerMelds(playerIndex);
			for (int meldIndex = 0; meldIndex < meldList.size(); meldIndex++){
				List<GameTile> tList = meldList.get(meldIndex).getAllTiles();
				for (int tileIndex = 0; tileIndex < SIZE_MELD; tileIndex++)
					larryHandMelds[playerIndex][meldIndex][tileIndex].setIcon(getImageIconForTile(tList, tileIndex, playerIndex, SMALL));
			}
		}
		
		
		//update player info
		for (int playerIndex = SEAT1; playerIndex <= SEAT4; playerIndex++){
			larryInfoPlayers[playerIndex][LARRY_INFOPLAYER_SEATWIND].setIcon(getImageIconWind(gameState.seatWindOfPlayer(playerIndex), SMALL));
			larryInfoPlayers[playerIndex][LARRY_INFOPLAYER_POINTS].setText(Integer.toString(gameState.pointsForPlayer(playerIndex)));
			if (gameState.playerIsInRiichi(playerIndex))
				larryInfoPlayers[playerIndex][LARRY_INFOPLAYER_RIICHI].setIcon(garryOther[GARRYINDEX_OTHER_RIICHI]);
		}
		
		
		//update round info
		larryInfoRound[LARRY_INFOROUND_ROUNDWIND].setIcon(getImageIconWind(roundTracker.getRoundWind(), BIG));
		larryInfoRound[LARRY_INFOROUND_ROUNDNUM].setText(Integer.toString(roundTracker.getRoundNum()));
		larryInfoRound[LARRY_INFOROUND_BONUSNUM].setText(Integer.toString(roundTracker.getRoundBonusNum()));
		
		//update turn indicators
		for (int playerIndex = SEAT1; playerIndex <= SEAT4; playerIndex++){
			parryTurnInds[playerIndex].setVisible(roundTracker.whoseTurn() == playerIndex);
		}
		
		repaint();
	}
	
	//highlights the most recent discard
	private void updateDiscardMarker(){
		
		newTurn = roundTracker.whoseTurn();
		discardMarkerErase();
		discardMarkerSet();
		oldTurn = newTurn;
	}
	private void discardMarkerSet(){
		if (!pondTilesFor(newTurn).isEmpty() && gameState.playerNeedsDraw(newTurn)){
			if (newTurn == oldTurn && gameState.playerNeedsDrawRinshan(newTurn)) return;
			getLastLabelInPond(newTurn).setOpaque(true);
			getLastLabelInPond(newTurn).setBackground(COLOR_POND_DISCARD_TILE);
		}
	}
	private void discardMarkerErase(){
		if (oldTurn >= 0 && !pondTilesFor(oldTurn).isEmpty()){
			getLastLabelInPond(oldTurn).setOpaque(false);
			getLastLabelInPond(oldTurn).setBackground(COLOR_TRANSPARENT);
		}
	}
	private JLabel getLastLabelInPond(int seatNum){
		if (pondTilesFor(seatNum).isEmpty()) return null;
		else return larryPonds[seatNum][pondTilesFor(seatNum).size() - 1];
	}
	
	private List<PondTile> pondTilesFor(int seat){
		return gameState.getPondTilesForPlayer(seat);
	}
	
	
	
	
	//replaces all imageicons with null
	public void blankEverything(){
		//exclamation
		lblExclamation.setVisible(false);
		
		//hands
		for (JLabel[] lar: larryHands)
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
		larryInfoRound[LARRY_INFOROUND_ROUNDWIND].setIcon(null);larryInfoRound[LARRY_INFOROUND_ROUNDNUM].setText(null);larryInfoRound[LARRY_INFOROUND_BONUSNUM].setText(null);
		
		//turn indicators
		hideAll(parryTurnInds);
		
		//round result
		panRoundResultLabel.setVisible(false);
		panResult.setVisible(false);
		
		//player info
		for (JLabel[] player: larryInfoPlayers){
			player[0].setIcon(null);
			player[1].setText("0");
			player[2].setIcon(null);
		}
		
		//call buttons
		hideAll(barryCalls);
		//turn action buttons
		hideAll(barryTActions);
		
		//dead wall and tiny wall
		for (JLabel l: larryDW)  l.setIcon(null);
		for (JLabel l: larryTinyW)  l.setIcon(null);
		lblWallTilesLeft.setText("0");
		
		repaint();
	}
	
	private void hideAll(JComponent[] components){for (JComponent c: components) c.setVisible(false);}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void movePromptPanelToSeat(int seat){
		switch(seat){
		case SEAT1: panCalls.setLocation(475, 420); break;
		case SEAT2: panCalls.setLocation(455, 85); break;
		case SEAT3: panCalls.setLocation(90, 100); break;
		case SEAT4: panCalls.setLocation(100, 470); break;
		default: break;
		}
	}
	

	public CallType askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){
		boolean onlyOneChiPossible = ((canChiL ^ canChiM ^ canChiH) ^ (canChiL && canChiM && canChiH));
		CallType chiType = CallType.NONE;
		
		hideAll(barryCalls);
		barryCalls[BARRY_CALLS_NONE].setVisible(true);
		
		//if only one type of chi is possible, just show a single Chi button
		if (onlyOneChiPossible){
			barryCalls[BARRY_CALLS_CHI].setVisible(true);
			
			if (canChiL) chiType = CallType.CHI_L;
			else if (canChiM) chiType = CallType.CHI_M;
			else if (canChiH) chiType = CallType.CHI_H;
		}
		else{
			//else, show multiple chi buttons
			barryCalls[BARRY_CALLS_CHI_L].setVisible(canChiL);
			barryCalls[BARRY_CALLS_CHI_M].setVisible(canChiM);
			barryCalls[BARRY_CALLS_CHI_H].setVisible(canChiH);
		}
		
		barryCalls[BARRY_CALLS_PON].setVisible(canPon);
		barryCalls[BARRY_CALLS_KAN].setVisible(canKan);
		barryCalls[BARRY_CALLS_RON].setVisible(canRon);
		
		repaint();
		
		chosenCall = CallType.UNDECIDED;
		while (chosenCall == CallType.UNDECIDED)
			waitAroundForClick();
		
		if (chosenCall == CallType.NONSPECIFIC_CHI) chosenCall = chiType;
		
		hideAll(barryCalls);
		repaint();	/////questionable if needed
		return chosenCall;
	}
	
	
	
	
	private void setDiscardChosen(int seatNumberWhoClicked, int discardIndex){
		if (seatNumberWhoClicked != roundTracker.whoseTurn()) return;
		chosenTurnAction = TurnActionType.DISCARD;
		chosenDiscard = discardIndex;
	}
	private void setDiscardChosen(int discardIndex){setDiscardChosen(roundTracker.whoseTurn(), discardIndex);}
	
	
	public TurnActionType askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){
		canRiichi = false;	/////no riichi implemented yet
		
		chosenTurnAction = TurnActionType.UNDECIDED;
		chosenDiscard = NO_DISCARD_CHOSEN;
		
		//add appropriate turn action buttons
		barryTActions[BARRY_TACTIONS_RIICHI].setVisible(canRiichi);
		barryTActions[BARRY_TACTIONS_ANKAN].setVisible(canAnkan);
		barryTActions[BARRY_TACTIONS_MINKAN].setVisible(canMinkan);
		barryTActions[BARRY_TACTIONS_TSUMO].setVisible(canTsumo);
		this.repaint();
		
		chosenTurnAction = TurnActionType.UNDECIDED;
		while (chosenTurnAction == TurnActionType.UNDECIDED)
			waitAroundForClick();
		
		if (chosenDiscard > handSize) setDiscardChosen(DEFAULT_DISCARD);
		if (chosenDiscard == DEFAULT_DISCARD) chosenDiscard = handSize;
		
		hideAll(barryTActions);
		repaint();	/////questionable if needed
		
		return chosenTurnAction;
	}
	
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int getChosenDiscardIndex(){
		if (chosenTurnAction == TurnActionType.DISCARD) return chosenDiscard;
		else return NO_DISCARD_CHOSEN;
	}
	
	//avoids buggy behavior that can happen if busy waits are used while waiting for mouseclick input
	private void waitAroundForClick(){
		int sleepTime = 100;
		Pauser.pauseFor(sleepTime);
	}
	
	
	
	
	public void showResult(RoundResultSummary resum){
		panResult.setVisible(true);
		panResult.showResult(resum);
	}
	
	
	
	
	
	
	
	
	
	public void giveGameState(StateOfGame stateOfGame){		
		gameState = stateOfGame;
		roundTracker = gameState.getRoundTracker();
		
		//hand revealing options
		whichHandsToReveal = new boolean[NUM_PLAYERS];
		for (int i = 0; i < NUM_PLAYERS; i++)
			whichHandsToReveal[i] = (cheatRevealAllHands || gameState.playerIsHuman(i));
		
		blankEverything();
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
//		TableViewSmall viewer = new TableViewSmall();
		TableViewBase viewer = new TableViewBase();
		viewer.setVisible(true);
		viewer.panDebugButtons.setVisible(true);
		
//		viewer.showResult();
	}
	
	//TODO start of constructor
	public TableViewBase(){
		cheatRevealAllWall = DEFAULT_OPTION_REVEAL_WALL;
		cheatRevealAllHands = DEFAULT_OPTION_REVEAL_HANDS;
		
		loadImagesIntoArrays();
		
		final int WINDOW_BOUND_WIDTH = WINDOW_WIDTH + 2*WINDOW_SIDE_BORDER_SIZE;
		final int WINDOW_BOUND_HEIGHT = WINDOW_HEIGHT + WINDOW_TOP_BORDER_SIZE + WINDOW_MENU_SIZE;
		
		setIconImage(garryWinds[BIG][EAST].getImage());
		
		setResizable(false);
		setTitle(STRING_WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, WINDOW_BOUND_WIDTH, WINDOW_BOUND_HEIGHT);
		
		menuBar = new TopMenuBar();
		setJMenuBar(menuBar);
		
		
		//panel declarations
		JPanel panelTable = new JPanel();
		JPanel panelSidebar = new JPanel();
		JPanel panelMidTable = new JPanel();
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		panResult = new ResultPanel();
		
		panPlayer1 = new PlayerPanelClickable(SEAT1);
		panPlayer2 = new PlayerPanelClickable(SEAT2);
		panPlayer3 = new PlayerPanelClickable(SEAT3);
		panPlayer4 = new PlayerPanelClickable(SEAT4);
		
		panP1 = new PondPanel(SEAT1);
		panP2 = new PondPanel(SEAT2);
		panP3 = new PondPanel(SEAT3);
		panP4 = new PondPanel(SEAT4);
		
		panRoundInfoSquare = new RoundInfoSquarePanel();
		panWallSummary = new WallSummaryPanel();
		
		lblExclamation = new ExclamationLabel();
		
		panRoundResultLabel = new RoundResultLabelPanel();
		panDebugButtons = new DebugButtonsPanel(this);
		lblFun = new FunLabel();
		
		panCalls = new PromptPanel();
		
		
		
		contentPane.add(panResult);
		
		contentPane.add(panelTable);
		contentPane.add(panelSidebar);
		
		
		//listener for double click
		panelTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2){
					chosenCall = DEFAULT_CALL;
					setDiscardChosen(DEFAULT_DISCARD);
				}
			}
		});
		panelTable.setBounds(0, 0, 844 - 62*2 - 6, WINDOW_HEIGHT);
		panelTable.setBackground(COLOR_TABLE);
		panelTable.setLayout(null);
		
		panelTable.add(lblExclamation);
		panCalls.setLocation(475, 420);/////////////////////
		panelTable.add(panCalls);//////////////////////////
		panelTable.add(panelMidTable);
		panelTable.add(panPlayer1);panelTable.add(panPlayer2);panelTable.add(panPlayer3);panelTable.add(panPlayer4);
		
		
		
		
		panPlayer1.setLocation(147, 544);
		panPlayer2.setLocation(606, -90);
		panPlayer3.setLocation(-32, 30);
		panPlayer4.setLocation(30, 72);
		
		panP1.setLocation(134, 290);
		panP2.setLocation(298, 129);
		panP3.setLocation(134, 6);
		panP4.setLocation(6, 129);
		
		panRoundInfoSquare.setLocation(131, 131);
		
		panWallSummary.setLocation(10, 319);
		panRoundResultLabel.setLocation(32, 234);
		panResult.setLocation(57,75); panResult.setVisible(false);
		
		lblExclamation.setSeatCoordinates(EXCLAMATION_LOCS);
		lblExclamation.setLocation(SEAT1);
		
//		panCalls.setLocation(28, 475);
		
		
		
		
		panelMidTable.setBounds(143, 116, 428, 420);
		panelMidTable.setBackground(COLOR_MID_TABLE);
		panelMidTable.setLayout(null);
		
		//mid table holds round info square and ponds
		panelMidTable.add(panP1);panelMidTable.add(panP2);panelMidTable.add(panP3);panelMidTable.add(panP4);
		panelMidTable.add(panRoundInfoSquare);
		
		
		
		
		panelSidebar.setBounds(panelTable.getWidth(), 0, 276, WINDOW_HEIGHT);
		panelSidebar.setBackground(COLOR_SIDEBAR);
		panelSidebar.setLayout(null);
		
		panelSidebar.add(lblFun);
		panelSidebar.add(panDebugButtons);
//		panelSidebar.add(panCalls);
		
		panelSidebar.add(panWallSummary);
		panelSidebar.add(panRoundResultLabel);
		
		lblFun.setLocation(4, 5);
		panDebugButtons.setLocation(204, 333);
		
		
		panDebugButtons.setVisible(DEBUG_BUTTONS_VISIBLE);
		
		
		panTable = panelTable;
		panSidebar = panelSidebar;
		panMidTable = panelMidTable;
		
		
		//get labels
		panPlayer1.getLabelsHand(larryH1);panPlayer1.getLabelsMelds(larryH1Ms);
		panPlayer2.getLabelsHand(larryH2);panPlayer2.getLabelsMelds(larryH2Ms);
		panPlayer3.getLabelsHand(larryH3);panPlayer3.getLabelsMelds(larryH3Ms);
		panPlayer4.getLabelsHand(larryH4);panPlayer4.getLabelsMelds(larryH4Ms);

		panP1.getLabels(larryP1);
		panP2.getLabels(larryP2);
		panP3.getLabels(larryP3);
		panP4.getLabels(larryP4);
		
		panRoundInfoSquare.getLabelsTurnIndicators(parryTurnInds);
		panRoundInfoSquare.getLabelsPlayerInfo(larryInfoPlayers);
		panRoundInfoSquare.getLabelsRoundInfo(larryInfoRound);
		
		panWallSummary.getLabelsDeadWall(larryDW);
		panWallSummary.getLabelsTinyWall(larryTinyW);		
		lblWallTilesLeft = panWallSummary.getLabelWallTilesLeft();
		
		lblResult = panRoundResultLabel.getLabelResult();
		
		panCalls.getButtonsCalls(barryCalls);
		panCalls.getButtonsTurnActions(barryTActions);
	}
	public Container getContentPane(){return null;}
	
	
	
	
	//put image icons into arrays
	private void loadImagesIntoArrays(){
		final int NUM_TILES_PLUS_TILEBACK = 35;
		
		//load Tile images into array
		for(int seat = SEAT1; seat <= SEAT4; seat++){
			//get tileback and each of the 34 tiles, garryTiles[seat number][0=big,1=small][tile number]
			for (int tile = 0; tile < NUM_TILES_PLUS_TILEBACK; tile++){
				garryTiles[seat][BIG][tile] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/" + tile + ".png")));
				garryTiles[seat][SMALL][tile] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/" + tile + ".png")));
			}
			
			//get red fives
			garryTiles[seat][BIG][RED5M] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/5r.png")));
			garryTiles[seat][SMALL][RED5M] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/5r.png")));
			
			garryTiles[seat][BIG][RED5P] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/14r.png")));
			garryTiles[seat][SMALL][RED5P] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/14r.png")));

			garryTiles[seat][BIG][RED5S] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/23r.png")));
			garryTiles[seat][SMALL][RED5S] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/23r.png")));
		}
		
		//load tiny tile images (for tiny wall)
		for (int i = 0; i < garryTiles[BIG][SEAT1].length; i++) garryTilesTiny[i] = tinyResizer.resizeImage(garryTiles[BIG][SEAT1][i]);
		
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
		garryOmake[0] = new ImageIcon(getClass().getResource("/res/img/omake/sheep2toumei.png"));
		garryOmake[1] = new ImageIcon(getClass().getResource("/res/img/omake/birdClipart1.png"));
		garryOmake[2] = new ImageIcon(getClass().getResource("/res/img/omake/birdClipart2.png"));
		garryOmake[3] = new ImageIcon(getClass().getResource("/res/img/omake/birdClipart3.png"));
		garryOmake[4] = new ImageIcon(getClass().getResource("/res/img/omake/sounotori.png"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private class CallListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Chi-L")) chosenCall = CallType.CHI_L;
			else if (command.equals("Chi-M")) chosenCall = CallType.CHI_M;
			else if (command.equals("Chi-H")) chosenCall = CallType.CHI_H;
			else if (command.equals("Pon")) chosenCall = CallType.PON;
			else if (command.equals("Kan")) chosenCall = CallType.KAN;
			else if (command.equals("Ron")) chosenCall = CallType.RON;
			else if (command.equals("None")) chosenCall = CallType.NONE;
			else if (command.equals("Chi")) chosenCall = CallType.NONSPECIFIC_CHI;
		}		
	}
	
	private class TurnActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Ankan")) chosenTurnAction = TurnActionType.ANKAN;
			else if (command.equals("Minkan")) chosenTurnAction = TurnActionType.MINKAN;
			else if (command.equals("Riichi")) chosenTurnAction = TurnActionType.RIICHI;
			else if (command.equals("Tsumo")) chosenTurnAction = TurnActionType.TSUMO;
		}		
	}
	
	
	
	//TODO start of panel classes
	protected static class PlayerPanel extends JPanel{
		private static final long serialVersionUID = 2890177422476419820L;

		protected static class HandPanel extends JPanel{
			private static final long serialVersionUID = -1503172723884599289L;
			
			private static final int WIDTH = TILE_BIG_WIDTH*SIZE_HAND, HEIGHT = TILE_BIG_HEIGHT;
			
			protected JLabel[] larryH = new JLabel[SIZE_HAND];
			
			public HandPanel(int seat){
				super();
				for (int i = 0; i < larryH.length; i++) larryH[i] = new JLabel();

				setBounds(0, 0, WIDTH, HEIGHT);
				setLayout(new GridLayout(1, 14, 0, 0));
				if (seat == SEAT2 || seat == SEAT4){
					setSize(HEIGHT, WIDTH);
					setLayout(new GridLayout(14, 1, 0, 0));
				}
				
				setBackground(COLOR_TRANSPARENT);
				
				if (seat == SEAT2 || seat == SEAT3) for (int i = larryH.length-1; i >= 0; i--) add(larryH[i]);
				else for (int i = 0; i < larryH.length; i++) add(larryH[i]);
			}
			public void getLabels(JLabel[] hLarry){for (int i = 0; i < larryH.length; i++) hLarry[i] = larryH[i];}
		}
		
		protected static class MeldsPanel extends JPanel{
			private static final long serialVersionUID = -830267613586455835L;

			protected static class MeldPanel extends JPanel{
				private static final long serialVersionUID = 2970579345629950616L;
				private static final int WIDTH = 108, HEIGHT = 31;
				
				protected final JLabel[] larryHM = new JLabel[SIZE_MELD];
				
				public MeldPanel(int seat){
					super();
					for (int i = 0; i < larryHM.length; i++) larryHM[i] = new JLabel();
					
					setBounds(0, 0, WIDTH, HEIGHT);
					setLayout(new GridLayout(1, 4, 0, 0));
					if (seat == SEAT2 || seat == SEAT4){
						setSize(HEIGHT, WIDTH);
						setLayout(new GridLayout(4, 1, 0, 0));
					}
					
					setBackground(COLOR_TRANSPARENT);
					
					if (seat == SEAT2 || seat == SEAT3) for (int i = larryHM.length-1; i >= 0; i--) add(larryHM[i]);
					else for (int i = 0; i < larryHM.length; i++) add(larryHM[i]);
				}
				public void getLabels(JLabel[] mLarry){for (int i = 0; i < larryHM.length; i++) mLarry[i] = larryHM[i];}
			}
			
			private static final int WIDTH = 474, HEIGHT = 31;
			protected final MeldPanel[] panelHMs = new MeldPanel[SIZE_MELDPANEL];
			
			public MeldsPanel(int seat){
				super();
				
				if (seat == SEAT2 || seat == SEAT4) setBounds(0, 0, HEIGHT, WIDTH); 
				else setBounds(0, 0, WIDTH, HEIGHT);
				
				setBackground(COLOR_TRANSPARENT);
				setLayout(null);

				final int[][][] LOCS_MELDS = {{{360,0},{240,0},{120,0},{0,0}}, {{0,0},{0,120},{0,240},{0,360}}, {{0,0},{120,0},{240,0},{360,0}}, {{0,360},{0,240},{0,120},{0,0}}};
				for (int mnum = 0; mnum < panelHMs.length; mnum++){
					panelHMs[mnum] = new MeldPanel(seat);
					panelHMs[mnum].setLocation(LOCS_MELDS[seat][mnum][X], LOCS_MELDS[seat][mnum][Y]);
				}
				for (MeldPanel m: panelHMs) add(m);
			}
			public void getLabels(JLabel[][] msLarry){for (int mnum = 0; mnum < panelHMs.length; mnum++) panelHMs[mnum].getLabels(msLarry[mnum]);}
		}

		private static final int WIDTH = 667, HEIGHT = 78;
		
		protected HandPanel panelH;
		protected MeldsPanel panelMs;
		
		public PlayerPanel(int seat){
			super();
			
			if (seat == SEAT2 || seat == SEAT4) setBounds(0, 0, HEIGHT, WIDTH);
			else setBounds(0, 0, WIDTH, HEIGHT);
			
			setBackground(COLOR_TRANSPARENT);
			setLayout(null);
			
			final int[][] LOCS_H = {{0,0}, {0,203}, {177,36}, {36,40}};
			final int[][] LOCS_M = {{58,47}, {46,104}, {78,0}, {0,96}};
			
			panelH = new HandPanel(seat);
			panelH.setLocation(LOCS_H[seat][X], LOCS_H[seat][Y]);
			
			panelMs = new MeldsPanel(seat);
			panelMs.setLocation(LOCS_M[seat][X], LOCS_M[seat][Y]);
			
			add(panelH);
			add(panelMs);
		}
		public void getLabelsHand(JLabel[] hLarry){panelH.getLabels(hLarry);}
		public void getLabelsMelds(JLabel[][] msLarry){panelMs.getLabels(msLarry);}
		
		public void blankAll(){
			for (JLabel l: panelH.larryH) l.setIcon(null);
			for (MeldsPanel.MeldPanel mp: panelMs.panelHMs) for (JLabel l: mp.larryHM) l.setIcon(null);
		}
		
	}
	
	
	
	protected class PlayerPanelClickable extends PlayerPanel{
		private static final long serialVersionUID = -8026283033946280711L;
		
		public PlayerPanelClickable(final int seat){
			super(seat);
			
			//add mouse listeners to hand tile labels
			for (int i = 0; i < this.panelH.larryH.length; i++){
				final int discChoice = i + 1;
				panelH.larryH[i].addMouseListener(new MouseAdapter() {public void mouseClicked(MouseEvent arg0){
					setDiscardChosen(seat, discChoice);}}
				);
			}
			
			for (JLabel l: panelH.larryH) l.setIcon(garryTiles[seat][BIG][1]);
			for (PlayerPanel.MeldsPanel.MeldPanel mp: panelMs.panelHMs) for (JLabel l: mp.larryHM) l.setIcon(garryTiles[seat][SMALL][34]);
		}
	}
	
	
	
	
	
	
	protected class PondPanel extends JPanel{
		private static final long serialVersionUID = -6135344462326116557L;
		
		private static final int WIDTH = POND_PANEL_WIDTH,  HEIGHT = POND_PANEL_HEIGHT;
		
		protected final JLabel[] larryP = new JLabel[SIZE_POND];
		
		public PondPanel(int seat){
			super();
			for (int i = 0; i < larryP.length; i++) larryP[i] = new JLabel();
			
			for (JLabel l: larryP) l.setIcon(garryTiles[seat][SMALL][22+seat*3]);
			
			if (seat == SEAT2 || seat == SEAT4){
				setBounds(0, 0, HEIGHT, WIDTH);
				setLayout(new GridLayout(6, 4, 0, 0));
				for (JLabel l: larryP) {l.setVerticalAlignment(SwingConstants.CENTER);}
			}
			else{
				setBounds(0, 0, WIDTH, HEIGHT);
				setLayout(new GridLayout(4, 6, 0, 0));
				for (JLabel l: larryP) {l.setHorizontalAlignment(SwingConstants.CENTER);}
			}
			setBackground(COLOR_TRANSPARENT);
			
			int addOrders[][] = {{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, {6,12,18,24,5,11,17,23,4,10,16,22,3,9,15,21,2,8,14,20,1,7,13,19}, {24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1}, {19,13,7,1,20,14,8,2,21,15,9,3,22,16,10,4,23,17,11,5,24,18,12,6}};
			for (int i: addOrders[seat]) add(larryP[i-1]);
		}
		public void getLabels(JLabel[] pLarry){for (int i = 0; i < larryP.length; i++) pLarry[i] = larryP[i];}
	}
	
	
	
	
	
	
	protected class RoundInfoSquarePanel extends JPanel{
		private static final long serialVersionUID = -3845929576576899579L;
		
		protected class RoundInfoPanel extends JPanel{
			private static final long serialVersionUID = -3126252307107694820L;
			
			protected class RoundIndicatorPanel extends JPanel{
				private static final long serialVersionUID = -8999599492268781636L;
				
				protected JLabel lblRIndWind = new JLabel(), lblRIndNum = new JLabel(), lblRIndBonusNum = new JLabel();
				
				public RoundIndicatorPanel(){
					super();
					lblRIndWind.setIcon(garryWinds[BIG][EAST]);
					lblRIndNum.setText("4");
					
					setBounds(0, 0, 58, 49);
					setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
					setBackground(COLOR_RIND);
					setLayout(null);
					
					lblRIndWind.setBounds(3, 9, 31, 31);

					lblRIndNum.setBounds(33, 6, 16, 37);
					lblRIndNum.setFont(new Font("Tahoma", Font.PLAIN, 30));
					lblRIndNum.setHorizontalAlignment(SwingConstants.LEFT);
					lblRIndNum.setVerticalAlignment(SwingConstants.TOP);

//					lblRIndBonusNum.setBounds(40, 35, 16, 37);
					lblRIndBonusNum.setBounds(50, 36, 16, 37);
					lblRIndBonusNum.setText("1");
					lblRIndBonusNum.setFont(new Font("Tahoma", Font.PLAIN, 10));
					lblRIndBonusNum.setHorizontalAlignment(SwingConstants.LEFT);
					lblRIndBonusNum.setVerticalAlignment(SwingConstants.TOP);
					
					add(lblRIndWind);add(lblRIndNum);add(lblRIndBonusNum);
				}
				public void getLabels(JLabel[] rinfoParry){
					rinfoParry[LARRY_INFOROUND_ROUNDWIND] = lblRIndWind; rinfoParry[LARRY_INFOROUND_ROUNDNUM] = lblRIndNum; rinfoParry[LARRY_INFOROUND_BONUSNUM] = lblRIndBonusNum;
				}
			}
			
			protected class PlayerInfoPanel extends JPanel{
				private static final long serialVersionUID = -8220269097110427156L;
				
				protected final JLabel lblInfoPWind = new JLabel(), lblInfoPPoints = new JLabel(), lblInfoPRiichi = new JLabel();
				
				public PlayerInfoPanel(){
					super();
					lblInfoPWind.setIcon(garryWinds[SMALL][EAST]);lblInfoPPoints.setText("128,000");lblInfoPRiichi.setIcon(garryOther[GARRYINDEX_OTHER_RIICHI]);

					setBounds(0, 0, 54, 54);
					setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
					setBackground(COLOR_RINF_PANEL);
					setLayout(null);
					
					lblInfoPWind.setBounds(16, 2, 23, 23);
					lblInfoPWind.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblInfoPPoints.setBounds(4, 25, 46, 14);
					lblInfoPPoints.setBackground(COLOR_TRANSPARENT);
					lblInfoPPoints.setHorizontalAlignment(SwingConstants.CENTER);
					
					lblInfoPRiichi.setBounds(2, 40, 50, 8);
					lblInfoPRiichi.setHorizontalAlignment(SwingConstants.CENTER);
					
					add(lblInfoPWind);add(lblInfoPPoints);add(lblInfoPRiichi);
				}
				public void getLabels(JLabel[] pinfoLarry){
					pinfoLarry[LARRY_INFOPLAYER_SEATWIND] = lblInfoPWind; pinfoLarry[LARRY_INFOPLAYER_POINTS] = lblInfoPPoints; pinfoLarry[LARRY_INFOPLAYER_RIICHI] = lblInfoPRiichi;
				}
			}
			
			protected class TurnIndicatorPanel extends JPanel{
				private static final long serialVersionUID = -3814763309281234952L;

				protected class TurnIndicatorSingle extends JPanel{
					private static final long serialVersionUID = 4876759595860219518L;
					
					public TurnIndicatorSingle(int seat){
						super();
						
						setBounds(0, 0, 166, 13);
						if (seat == SEAT2 || seat == SEAT4) setSize(13, 158);
						setOpaque(false);
						setLayout(null);
						
						final int[][][] BOUNDS_LABELS = {{{1, 0, 55, 13}, {1, 1, 13, 52}, {1, 0, 55, 13}, {1, 1, 13, 51}},
														{{110, 0, 55, 13}, {0, 105, 13, 52}, {110, 0, 55, 13}, {0, 105, 13, 51}}};
						
						final JLabel[] turnIndLabels = {new JLabel(), new JLabel()};
						for (int label = 0; label < turnIndLabels.length; label++){
							turnIndLabels[label].setBounds(BOUNDS_LABELS[label][seat][X], BOUNDS_LABELS[label][seat][Y], BOUNDS_LABELS[label][seat][W], BOUNDS_LABELS[label][seat][H]);
							turnIndLabels[label].setOpaque(true);turnIndLabels[label].setBackground(COLOR_TURN_INDICATOR);
						}
						for (JLabel l: turnIndLabels) add(l);
					}
				}
				
				protected final TurnIndicatorSingle[] panelTurnIndSingles = new TurnIndicatorSingle[NUM_PLAYERS];
				
				public TurnIndicatorPanel(){
					super();
					
					setBounds(0, 0, 166, 158);
					setLayout(null);
					setOpaque(false);

					final int[][] LOCS_SINGLES = {{0, 144}, {152, 0}, {0, 1}, {1, 1}};
					for (int seat = SEAT1; seat <= SEAT4; seat++){
						panelTurnIndSingles[seat] = new TurnIndicatorSingle(seat);
						panelTurnIndSingles[seat].setLocation(LOCS_SINGLES[seat][X], LOCS_SINGLES[seat][Y]);
					}
					for (TurnIndicatorSingle pn: panelTurnIndSingles) add(pn);
				}
				public void getLabels(JPanel[] tindParry){
					for (int i = 0; i < panelTurnIndSingles.length; i++) tindParry[i] = panelTurnIndSingles[i];
				}
			}
			
			protected final RoundIndicatorPanel panelRInd = new RoundIndicatorPanel();
			protected final PlayerInfoPanel[] panelInfoPs = new PlayerInfoPanel[NUM_PLAYERS];
			protected final TurnIndicatorPanel panelTurnInds = new TurnIndicatorPanel();
			
			public RoundInfoPanel(){
				super();
				
				setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
				setBackground(COLOR_RINF_PANEL);
				setLayout(null);
				
				panelRInd.setLocation(54, 54);
				panelTurnInds.setLocation(0,0);
				
				final int[][] LOCS_PLAYER_PANELS = {{56, 103}, {112, 52}, {56, 0}, {0, 52}};
				for (int seat = SEAT1; seat <= SEAT4; seat++){
					panelInfoPs[seat] = new PlayerInfoPanel();
					panelInfoPs[seat].setLocation(LOCS_PLAYER_PANELS[seat][X], LOCS_PLAYER_PANELS[seat][Y]);
				}
				
				add(panelRInd);
				for (PlayerInfoPanel pn: panelInfoPs) add(pn);
				add(panelTurnInds);
			}
			public void getLabelsRoundInfo(JLabel[] rinfoLarry){panelRInd.getLabels(rinfoLarry);}
			public void getLabelsTurnIndicators(JPanel[] tindParry){panelTurnInds.getLabels(tindParry);}
			public void getLabelsPlayerInfo(JLabel[][] pinfoLarrys){
				for (int i = 0; i < pinfoLarrys.length; i++) panelInfoPs[i].getLabels(pinfoLarrys[i]);
			}
		}
		

		private static final int WIDTH = 166;
		private static final int HEIGHT = 158;
		
		protected final JPanel panelRndInfBackground = new JPanel();
		protected final RoundInfoPanel panelRoundInfo = new RoundInfoPanel();
		
		public RoundInfoSquarePanel(){
			super();
			
			setBounds(0, 0, WIDTH, HEIGHT);
			setLayout(null);
			setBackground(COLOR_TRANSPARENT);
			
			panelRoundInfo.setBounds(0, 0, WIDTH, HEIGHT);
			panelRndInfBackground.setBounds(panelRoundInfo.getBounds());
			
			add(panelRoundInfo);
			add(panelRndInfBackground);
		}
		public void getLabelsPlayerInfo(JLabel[][] pinfoLarrys){panelRoundInfo.getLabelsPlayerInfo(pinfoLarrys);}
		public void getLabelsRoundInfo(JLabel[] rinfoLarry){panelRoundInfo.getLabelsRoundInfo(rinfoLarry);}
		public void getLabelsTurnIndicators(JPanel[] tindParry){panelRoundInfo.getLabelsTurnIndicators(tindParry);}
	}
	
	
	
	
	protected class WallSummaryPanel extends JPanel{
		private static final long serialVersionUID = -9084400911185524132L;
		
		protected class TinyWallPanel extends JPanel{
			private static final long serialVersionUID = -4122759925808650141L;
			
			protected final JLabel[] larryTWtiles = new JLabel[SIZE_TINY_WALL];
			
			public TinyWallPanel(){
				super();
				
				setBounds(0, 0, 162, 50);
				setLayout(new GridLayout(4,18,0,0));
				setBackground(COLOR_TRANSPARENT);
				
				for (int i = 0; i < larryTWtiles.length; i++){
					larryTWtiles[i] = new JLabel();
					larryTWtiles[i].setIcon(garryTilesTiny[TILEBACK]);
					add(larryTWtiles[i]);
				}
			}
			public void getLabels(JLabel[] twLarry){for (int i = 0; i < larryTWtiles.length; i++)twLarry[i] = larryTWtiles[i];}
		}
		
		
		
		protected class DeadWallPanel extends JPanel{
			private static final long serialVersionUID = 8920630209311855667L;
			
			protected final JLabel[] larryDWtiles = new JLabel[SIZE_DEAD_WALL];
			
			public DeadWallPanel(){
				super();
				for (int i = 0; i < larryDWtiles.length; i++) larryDWtiles[i] = new JLabel();
				
				for (JLabel l: larryDWtiles) l.setIcon(garryTiles[SEAT1][SMALL][0]);
				
				setBounds(0, 0, 161, 62);
				setBackground(COLOR_WALL_SUMMARY_PANEL);
				setLayout(new GridLayout(2, 7, 0, 0));
				
				final int[] addOrder = {1,3,5,7,9,11,13,2,4,6,8,10,12,14};
				for (int i: addOrder) add(larryDWtiles[i-1]);
			}
			public void getLabels(JLabel[] dwLarry){for (int i = 0; i < larryDWtiles.length; i++) dwLarry[i] = larryDWtiles[i];}
		}
		
		protected class WallTilesLeftPanel extends JPanel{
			private static final long serialVersionUID = 2296100666342215397L;
			
			protected final JLabel lblWallTilesLeftLabel = new JLabel();
			
			public WallTilesLeftPanel(){
				super();
				lblWallTilesLeftLabel.setText("122");

				setBounds(0, 0, 78, 21);
				setOpaque(false);
				setLayout(new GridLayout(0, 2, 0, 0));
				
				JLabel lblWTLText = new JLabel("Left: "); lblWTLText.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblWallTilesLeftLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
				
				add(lblWTLText);add(lblWallTilesLeftLabel);
			}
			public JLabel getLabel(){return lblWallTilesLeftLabel;}
		}
		
		protected WallTilesLeftPanel panelWTL = new WallTilesLeftPanel();
		protected DeadWallPanel panelDeadWall = new DeadWallPanel();
		protected TinyWallPanel panelTinyWall = new TinyWallPanel();
		
		public WallSummaryPanel(){
			super();
			
			setLayout(null);
			setBounds(0, 0, 161, 83 + 13*4);
			setOpaque(false);
			
			panelWTL.setLocation(1, 62);
			panelDeadWall.setLocation(0, 0);
			panelTinyWall.setLocation(0, 83);
			
			add(panelWTL);add(panelDeadWall);add(panelTinyWall);
		}
		public void setTinyWallVisible(boolean b){panelTinyWall.setVisible(b);}
		public void getLabelsDeadWall(JLabel[] dwLarry){panelDeadWall.getLabels(dwLarry);}
		public void getLabelsTinyWall(JLabel[] twLarry){panelTinyWall.getLabels(twLarry);}
		public JLabel getLabelWallTilesLeft(){return panelWTL.getLabel();}
	}
	
	
	
	
	protected static class ExclamationLabel extends JLabel{
		private static final long serialVersionUID = 1642040936441297909L;
		
		private final int[][] LOCS =  {{0,0}, {0,0}, {0,0}, {0,0}};
		public ExclamationLabel(){
			super();
			setText("TSUMO!");setLocation(EXCLAMATION_LOCS[SEAT1][0], EXCLAMATION_LOCS[SEAT1][1]);

			setSize(134, 34);
			setHorizontalAlignment(SwingConstants.CENTER);
			setFont(new Font("Maiandra GD", Font.BOLD, 28));
			setBorder(new LineBorder(new Color(0, 0, 200), 3, true));
			setOpaque(true); setBackground(COLOR_EXCLAMATION);
		}
		public void setLocation(int seat){setLocation(LOCS[seat][X], LOCS[seat][Y]);}
		public void setSeatCoordinates(int[][] seatLocs){
			if (seatLocs == null || seatLocs.length != NUM_SEATS) return; for (int iarr[]: seatLocs) if (iarr == null || iarr.length != 2) return;
			for (int seat = 0; seat < 4; seat++) for (int XorY = 0; XorY < 2; XorY++) LOCS[seat][XorY] = seatLocs[seat][XorY];
		}
		
		public void showExclamation(Exclamation exclamation, int seat){
			//show the label
			setText(exclamation.toString());
			setLocation(seat);
			setVisible(true);
		}
		public void erase(){setVisible(false);}
	}
	
	
	protected class FunLabel extends JLabel{
		private static final long serialVersionUID = -4613254346704042607L;

		public FunLabel(){
			super();
			setIcon(garryOmake[2]);
			setBounds(0, 0, 270, 218);
			setVerticalAlignment(SwingConstants.TOP);
		}
	}
	
	
	
	protected static class RoundResultLabelPanel extends JPanel{
		private static final long serialVersionUID = 1415387615431328822L;
		
		protected final JLabel lblRoundResult = new JLabel();
		protected final JLabel lblRoundOver = new JLabel();
		
		public RoundResultLabelPanel(){
			super();
			lblRoundResult.setText("KATAOKA-SAN WINS");
			
			setBounds(0, 0, 212, 66);
			setLayout(null);
			setBackground(COLOR_RESULT_PANEL);
			setBorder(new LineBorder(new Color(0, 200, 0), 3, true));
			
			lblRoundOver.setText("===ROUND OVER===");
			lblRoundOver.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblRoundOver.setHorizontalAlignment(SwingConstants.CENTER);
			lblRoundOver.setBounds(0, 0, 212, 33);
			
			lblRoundResult.setHorizontalAlignment(SwingConstants.CENTER);
			lblRoundResult.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblRoundResult.setBounds(0, 33, 212, 33);
			
			add(lblRoundOver);add(lblRoundResult);
		}
		public void setTextColor(Color newTextColor){lblRoundOver.setForeground(newTextColor);lblRoundResult.setForeground(newTextColor);}
		public JLabel getLabelResult(){return lblRoundResult;}
	}
	
	

	protected class DebugButtonsPanel extends JPanel{
		private static final long serialVersionUID = 1859926261989819869L;
		protected final JButton btnBlankAll = new JButton("Blank"), btnRandAll = new JButton("Rand");
		private final JFrame frame;
		public DebugButtonsPanel(JFrame f){
			super();
			frame = f;
			setBounds(0, 0, 68, 23*2);setVisible(DEBUG_BUTTONS_VISIBLE);setLayout(null);setBackground(COLOR_TRANSPARENT);
			btnBlankAll.setBounds(0, 23, 68, 23);btnBlankAll.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {blankEverything();}});
			btnRandAll.setBounds(0, 0, 65, 23);
			btnRandAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					final Random randGen = new Random();final int RANDLIMIT = 38;
					for (JLabel l: larryH1) l.setIcon(garryTiles[SEAT1][BIG][randGen.nextInt(RANDLIMIT)]);
					for (JLabel l: larryP1) if (randGen.nextInt(24) != 1) l.setIcon(garryTiles[SEAT1][SMALL][randGen.nextInt(RANDLIMIT)]); else l.setIcon(garryTiles[SEAT1+1][SMALL][randGen.nextInt(RANDLIMIT)]);
					for (JLabel[] lar: larryH1Ms) for (JLabel l: lar) if (randGen.nextInt(8) == 1) l.setIcon(garryTiles[1][1][randGen.nextInt(RANDLIMIT)]); else l.setIcon(garryTiles[SEAT1][SMALL][randGen.nextInt(RANDLIMIT)]);
					
					for (JLabel l: larryH2) l.setIcon(garryTiles[SEAT2][BIG][randGen.nextInt(RANDLIMIT)]);for (JLabel l: larryP2) l.setIcon(garryTiles[SEAT2][SMALL][randGen.nextInt(RANDLIMIT)]);for (JLabel[] lar: larryH2Ms) for (JLabel l: lar) l.setIcon(garryTiles[SEAT2][SMALL][randGen.nextInt(RANDLIMIT)]);
					for (JLabel l: larryH3) l.setIcon(garryTiles[SEAT3][BIG][randGen.nextInt(RANDLIMIT)]);for (JLabel l: larryP3) l.setIcon(garryTiles[SEAT3][SMALL][randGen.nextInt(RANDLIMIT)]);for (JLabel[] lar: larryH3Ms) for (JLabel l: lar) l.setIcon(garryTiles[SEAT3][SMALL][randGen.nextInt(RANDLIMIT)]);
					for (JLabel l: larryH4) l.setIcon(garryTiles[SEAT4][BIG][randGen.nextInt(RANDLIMIT)]);for (JLabel l: larryP4) l.setIcon(garryTiles[SEAT4][SMALL][randGen.nextInt(RANDLIMIT)]);for (JLabel[] lar: larryH4Ms) for (JLabel l: lar) l.setIcon(garryTiles[SEAT4][SMALL][randGen.nextInt(RANDLIMIT)]);
					for (JLabel l: larryDW) l.setIcon(garryTiles[SEAT1][SMALL][randGen.nextInt(RANDLIMIT)]); lblWallTilesLeft.setText(Integer.toString(1+randGen.nextInt(122))); for (JLabel l: larryTinyW) l.setIcon(garryTilesTiny[randGen.nextInt(RANDLIMIT)]);
					larryInfoRound[LARRY_INFOROUND_ROUNDWIND].setIcon(garryWinds[BIG][randGen.nextInt(garryWinds[BIG].length)]);larryInfoRound[LARRY_INFOROUND_ROUNDNUM].setText(Integer.toString(1+randGen.nextInt(4))); larryInfoRound[LARRY_INFOROUND_BONUSNUM].setText(Integer.toString(1+randGen.nextInt(4)));	//randomize round info
					for (JLabel[] infoP: larryInfoPlayers)	//randomize player info
						{infoP[LARRY_INFOPLAYER_SEATWIND].setIcon(garryWinds[SMALL][randGen.nextInt(garryWinds[SMALL].length)]); infoP[LARRY_INFOPLAYER_POINTS].setText(Integer.toString(100*randGen.nextInt(1280)));
						if (randGen.nextBoolean()) infoP[LARRY_INFOPLAYER_RIICHI].setIcon(null); else infoP[LARRY_INFOPLAYER_RIICHI].setIcon(garryOther[GARRYINDEX_OTHER_RIICHI]);}
					lblExclamation.setVisible(true);lblExclamation.setLocation(randGen.nextInt(NUM_SEATS));
					String[] results = {"Draw (Washout)", "Draw (Kyuushuu)", "Draw (4 kans)", "Draw (4 riichi)", "Draw (4 wind)", "East wins! (TSUMO)", "East wins! (RON)", "South wins! (TSUMO)", "South wins! (RON)", "West wins! (TSUMO)", "West wins! (RON)", "North wins! (TSUMO)", "North wins! (RON)" };panRoundResultLabel.setVisible(true);lblResult.setText(results[randGen.nextInt(results.length)]);
					hideAll(parryTurnInds);	parryTurnInds[randGen.nextInt(parryTurnInds.length)].setVisible(true); //randomize turn indicator
					lblFun.setIcon(garryOmake[randGen.nextInt(garryOmake.length)]);
					hideAll(barryCalls);hideAll(barryTActions);
					if (randGen.nextBoolean()) for (JButton b: barryCalls) b.setVisible(randGen.nextBoolean());
					else for (JButton b: barryTActions) b.setVisible(randGen.nextBoolean());
					frame.repaint();
				}
			});
			add(btnBlankAll);add(btnRandAll);
		}
	}
	
	
	private static final Color COLOR_COOL_BUTTON = new Color(0, 50, 250);
//	private static final Color BORDER_COOL_BUTTON = new ;
	//TODO promtpanel
	protected class PromptPanel extends JPanel{
		private static final long serialVersionUID = -2942085357712026377L;
		
		private static final int BUTON_HEIGHT = 23, BIG_BUTON_HEIGHT = 51;
		private static final int BUTON_WIDTH = 52, BIG_BUTON_WIDTH = 103;
		
		protected class TurnActionPanel extends JPanel{
			private static final long serialVersionUID = -4659522187187470904L;
			
			private static final int WIDTH = 100, HEIGHT = 147;
			
			protected final JButton[] barryTB = new JButton[4];
			
			public TurnActionPanel(){
				super();
				setBounds(0, 0, WIDTH, HEIGHT);
				setOpaque(false);//setLayout(null);
				
				String texts[] = {"Riichi?", "Ankan?", "Minkan?", "Tsumo!!!"};
				String commands[] = {"Riichi", "Ankan", "Minkan", "Tsumo"};
				int[][] BOUNDS_B = {{11, 0, 89, BUTON_HEIGHT}, {11, 24, 89, BUTON_HEIGHT}, {11, 48, 89, BUTON_HEIGHT}, {0, 96, 100, BIG_BUTON_HEIGHT}};
				TurnActionListener taListener = new TurnActionListener();
				for (int i = 0; i < barryTB.length; i++){
					barryTB[i] = new JButton(texts[i]);
					barryTB[i].setActionCommand(commands[i]);
					barryTB[i].setBounds(BOUNDS_B[i][X], BOUNDS_B[i][Y], BOUNDS_B[i][W], BOUNDS_B[i][H]);
					
					barryTB[i].addActionListener(taListener);
//					barryTB[i].setContentAreaFilled(false);
					barryTB[i].setRolloverEnabled(false);
					barryTB[i].setFocusPainted(false);
					
					barryTB[i].setOpaque(true);
					barryTB[i].setBackground(COLOR_COOL_BUTTON);
					barryTB[i].setForeground(Color.WHITE);
//					barryTB[i].setBorder(BorderFactory.createEtchedBorder());
					
//					add(barryTB[i]);
				}
			}
			public void getButtons(JButton[] taBarry){for (int i = 0; i < barryTB.length; i++) taBarry[i] = barryTB[i];}
		}
		
//		private static final int WIDTH = 204, HEIGHT = 147;
		private static final int WIDTH = 150, HEIGHT = 147;
		
		protected final JButton[] barryCB = new JButton[8];
		protected final TurnActionPanel panelTB = new TurnActionPanel();
		
		public PromptPanel(){
			super();
			setBounds(0, 0, WIDTH, HEIGHT);
			setBackground(COLOR_CALL_PANEL);
			setOpaque(false);
//			setLayout(null);
			setLayout(new FlowLayout(FlowLayout.LEADING));
			
			String texts[] = {"No call", "Chi-L", "Chi-M", "Chi-H", "Pon", "Kan", "Ron!!!", "Chi"};
			String commands[] = {"None", "Chi-L", "Chi-M", "Chi-H", "Pon", "Kan", "Ron", "Chi"};
			int[][] BOUNDS_B = {{0, 0, 89, BUTON_HEIGHT}, {0, 48, BUTON_WIDTH, BUTON_HEIGHT}, {67, 48, BUTON_WIDTH, BUTON_HEIGHT}, {135, 48, BUTON_WIDTH, BUTON_HEIGHT}, {1, 72, BUTON_WIDTH, BUTON_HEIGHT}, {65, 72, BUTON_WIDTH, BUTON_HEIGHT}, {2, 96, BIG_BUTON_WIDTH, BIG_BUTON_HEIGHT}, {1, 24, BUTON_WIDTH, BUTON_HEIGHT}};
			CallListener callListener = new CallListener();
			for (int i = 0; i < barryCB.length; i++){
				barryCB[i] = new JButton(texts[i]);
				barryCB[i].setActionCommand(commands[i]);
				barryCB[i].setBounds(BOUNDS_B[i][X], BOUNDS_B[i][Y], BOUNDS_B[i][W], BOUNDS_B[i][H]);

				barryCB[i].addActionListener(callListener);
//				barryCB[i].setContentAreaFilled(false);
				barryCB[i].setRolloverEnabled(false);
				barryCB[i].setFocusPainted(false);
				
				barryCB[i].setOpaque(true);
				barryCB[i].setBackground(COLOR_COOL_BUTTON);
				barryCB[i].setForeground(Color.WHITE);
				barryCB[i].setHorizontalAlignment(SwingConstants.LEFT);
				
				
				add(barryCB[i]);
			}
			for (JButton b: panelTB.barryTB) add(b);
//			panelTB.setLocation(104, 0);
			panelTB.setLocation(0, 0);
//			add(panelTB);
		}
		public void getButtonsCalls(JButton[] cbBarry){for (int i = 0; i < barryCB.length; i++) cbBarry[i] = barryCB[i];}
		public void getButtonsTurnActions(JButton[] taBarry){panelTB.getButtons(taBarry);}
		
	}
	
	
	protected class TopMenuBar extends JMenuBar{
		private static final long serialVersionUID = -2474321339954066458L;
		
		protected class CheatsMenu extends JMenu{
			private static final long serialVersionUID = 5173870286829300289L;
			
			public CheatsMenu(){
				super("Cheats");
				JCheckBoxMenuItem checkboxRevealWalls = new JCheckBoxMenuItem("Reveal Walls"), checkboxRevealHands = new JCheckBoxMenuItem("Reveal Hands");
				checkboxRevealWalls.setSelected(DEFAULT_OPTION_REVEAL_WALL);checkboxRevealHands.setSelected(DEFAULT_OPTION_REVEAL_HANDS);
				
				checkboxRevealWalls.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cheatRevealAllWall = !cheatRevealAllWall;
						updateEverything();
					}
				});
				checkboxRevealHands.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cheatRevealAllHands = !cheatRevealAllHands;
						for (int i = 0; i < whichHandsToReveal.length; i++) whichHandsToReveal[i] = (cheatRevealAllHands || gameState.playerIsHuman(i));
						updateEverything();
					}
				});
				add(checkboxRevealWalls);add(checkboxRevealHands);
			}
		}
		
		protected final CheatsMenu menuCheats = new CheatsMenu();
		
		public TopMenuBar(){
			super();
			add(menuCheats);
		}
	}
	
	
}

