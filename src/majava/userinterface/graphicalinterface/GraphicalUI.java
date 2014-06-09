
package majava.userinterface.graphicalinterface;

import majava.Hand;
import majava.Meld;
import majava.Player;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.Exclamation;
import majava.enums.GameplayEvent;
import majava.userinterface.GameUI;
import majava.userinterface.graphicalinterface.window.TableViewBase;
import majava.summary.RoundResultSummary;
import majava.summary.entity.RoundEntities;
import majava.tiles.Tile;
import majava.util.TileList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Pauser;

/*
Class: GraphicalUI
a GUI for viewing and interacting with the game
	
methods:
	public:
		mutators:
		
		accessors:
		
	other:
	syncWithRoundTracker - associates the viewer with the round tracker
*/
public class GraphicalUI extends GameUI{
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~BEGIN CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	//Control constants
	protected static final boolean DEFAULT_OPTION_REVEAL_WALL = false;
	protected static final boolean DEFAULT_OPTION_REVEAL_HANDS = false;
	
	//debug buttons
	protected static final boolean DEBUG_BUTTONS_VISIBLE = true;
	
	
	


	protected static final int DEAFULT_SLEEPTIME = 400, DEAFULT_SLEEPTIME_EXCLAMATION = 1500, DEAFULT_SLEEPTIME_ROUND_END = 2000;
	
	
	

	
	
	//click action constants
	protected static final int NO_CALL_CHOSEN = -1;
	protected static final int CALL_NONE = 0, CALL_CHI_L = 1, CALL_CHI_M = 2, CALL_CHI_H = 3, CALL_PON = 4, CALL_KAN = 5, CALL_RON = 6, CALL_CHI = 7;
	protected static final int DEFAULT_CALL = CALL_NONE;
	
	protected static final int NO_ACTION_CHOSEN = -1;
	protected static final int TURN_ACTION_DISCARD = -10, TURN_ACTION_ANKAN = -20, TURN_ACTION_MINKAN = -30, TURN_ACTION_RIICHI = -40, TURN_ACTION_TSUMO = -50;

	protected static final int NO_DISCARD_CHOSEN = -1;
	protected static final int DEFAULT_DISCARD = 0;
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~END CONSTANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	
	
	
	
	
	protected static final int SEAT1 = 0, SEAT2 = 1, SEAT3 = 2, SEAT4 = 3;
	protected static final int EAST = 0, SOUTH = 1, WEST = 2, NORTH = 3;
	protected static final int BIG = 0, SMALL = 1, TINY = 2;
	protected final static int X = 0, Y = 1, W = 2, H = 3;
	
	
	
	/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^BEGIN MEMBER VARIABLES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
	
//	protected boolean mOptionRevealWall;
//	protected boolean mOptionRevealHands;
//	protected boolean[] mRevealHands;
	

//	private int mChosenCall;
//	private int mChosenTurnAction;
//	private int mChosenDiscard;
	
	
	
	private TableViewBase mTableWindow;
	
	/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^END MEMBER VARIABLES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
	
	
	
	
	
	
	
	
	
	
	
	
	protected void __displayEventDiscardedTile(){mTableWindow.updateEverything();}
	protected void __displayEventMadeOpenMeld(){mTableWindow.updateEverything();}
	protected void __displayEventDrewTile(){mTableWindow.updateEverything();}
	protected void __displayEventMadeOwnKan(){mTableWindow.updateEverything();}
	protected void __displayEventNewDoraIndicator(){/*blank*/}
	protected void __displayEventHumanTurnStart(){mTableWindow.updateEverything();}
	protected void __displayEventPlaceholder(){mTableWindow.updateEverything();}
	
	protected void __displayEventStartOfRound(){mTableWindow.updateEverything();}
	
	protected void __displayEventEndOfRound(){
		mTableWindow.showResult(mResultSummary);
		mTableWindow.updateEverything();
		
		if (mSleepTimeRoundEnd > 0) Pauser.pauseFor(mSleepTimeRoundEnd);
	}
	
	
	
	
	protected void __showExclamation(Exclamation exclamation, int seat){
		//show the label
		mTableWindow.exclamationShow(exclamation, seat);
		//pause
		if (mSleepTimeExclamation > 0) Pauser.pauseFor(mSleepTimeExclamation);
		//get rid of label
		mTableWindow.exclamationErase();
	}
	
	
	
	
	
	
	//get user input from window
	public boolean askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){
		return mTableWindow.askUserInputCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);
	}
	public boolean resultChosenCallWasNone(){return mTableWindow.resultChosenCallWasNone();}
	public boolean resultChosenCallWasChiL(){return mTableWindow.resultChosenCallWasChiL();}
	public boolean resultChosenCallWasChiM(){return mTableWindow.resultChosenCallWasChiM();}
	public boolean resultChosenCallWasChiH(){return mTableWindow.resultChosenCallWasChiH();}
	public boolean resultChosenCallWasPon(){return mTableWindow.resultChosenCallWasPon();}
	public boolean resultChosenCallWasKan(){return mTableWindow.resultChosenCallWasKan();}
	public boolean resultChosenCallWasRon(){return mTableWindow.resultChosenCallWasRon();}
	
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){
		mTableWindow.askUserInputTurnAction(handSize, canRiichi, canAnkan, canMinkan, canTsumo);
	}
	public boolean resultChosenTurnActionWasDiscard(){return mTableWindow.resultChosenTurnActionWasDiscard();}
	public boolean resultChosenTurnActionWasAnkan(){return mTableWindow.resultChosenTurnActionWasAnkan();}
	public boolean resultChosenTurnActionWasMinkan(){return mTableWindow.resultChosenTurnActionWasMinkan();}
	public boolean resultChosenTurnActionWasRiichi(){return mTableWindow.resultChosenTurnActionWasRiichi();}
	public boolean resultChosenTurnActionWasTsumo(){return mTableWindow.resultChosenTurnActionWasTsumo();}
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int resultChosenDiscardIndex(){return mTableWindow.resultChosenDiscardIndex();}
	
	
	
	
	
	
	
	
	
	
	
	
	public void startUI(){mTableWindow.blankEverything(); mTableWindow.setVisible(true);}
	public void endUI(){mTableWindow.dispose();}
	
	
	
	
	public void syncWithRoundTracker(RoundEntities roundEntities){
		super.syncWithRoundTracker(roundEntities);
		mTableWindow.syncWithRoundTracker(roundEntities);
	}
	
	
	
	
	
	
	//launch the application TODO MAIN
	public static void main(String[] args) {
		
		GraphicalUI viewer = new GraphicalUI();
		
		viewer.startUI();
//		viewer.showResult();
	}
	
	//TODO start of constructor
	public GraphicalUI(){
		
		mTableWindow = new TableViewBase();
		
		
		
	}
	
	

	
	public void printErrorRoundAlreadyOver(){System.out.println("----Error: Round is already over, cannot play");}
}