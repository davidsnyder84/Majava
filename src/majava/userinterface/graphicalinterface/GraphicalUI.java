
package majava.userinterface.graphicalinterface;

import majava.hand.Hand;
import majava.hand.Meld;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.Exclamation;
import majava.enums.GameplayEvent;
import majava.userinterface.GameUI;
import majava.userinterface.graphicalinterface.window.TableViewBase;
import majava.player.Player;
import majava.summary.RoundResultSummary;
import majava.summary.entity.RoundEntities;
import majava.tiles.GameTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Pauser;


//a GUI for viewing and interacting with the game
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
	
	
	
	private TableViewBase tableWindow;
	
	/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^END MEMBER VARIABLES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
	
	
	
	
	
	
	
	
	
	
	
	
	protected void __displayEventDiscardedTile(){tableWindow.updateEverything();}
	protected void __displayEventMadeOpenMeld(){tableWindow.updateEverything();}
	protected void __displayEventDrewTile(){tableWindow.updateEverything();}
	protected void __displayEventMadeOwnKan(){tableWindow.updateEverything();}
	protected void __displayEventNewDoraIndicator(){/*blank*/}
	protected void __displayEventHumanTurnStart(){tableWindow.updateEverything();}
	protected void __displayEventPlaceholder(){tableWindow.updateEverything();}
	
	protected void __displayEventStartOfRound(){tableWindow.updateEverything();}
	
	protected void __displayEventEndOfRound(){
		tableWindow.showResult(resultSummary);
		tableWindow.updateEverything();
		
		if (mSleepTimeRoundEnd > 0) Pauser.pauseFor(mSleepTimeRoundEnd);
	}
	
	
	
	
	protected void __showExclamation(Exclamation exclamation, int seat){
		//show the label
		tableWindow.exclamationShow(exclamation, seat);
		//pause
		if (mSleepTimeExclamation > 0) Pauser.pauseFor(mSleepTimeExclamation);
		//get rid of label
		tableWindow.exclamationErase();
	}
	
	
	
	
	
	
	//get user input from window
	public boolean askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){
		return tableWindow.askUserInputCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);
	}
	public boolean resultChosenCallWasNone(){return tableWindow.resultChosenCallWasNone();}
	public boolean resultChosenCallWasChiL(){return tableWindow.resultChosenCallWasChiL();}
	public boolean resultChosenCallWasChiM(){return tableWindow.resultChosenCallWasChiM();}
	public boolean resultChosenCallWasChiH(){return tableWindow.resultChosenCallWasChiH();}
	public boolean resultChosenCallWasPon(){return tableWindow.resultChosenCallWasPon();}
	public boolean resultChosenCallWasKan(){return tableWindow.resultChosenCallWasKan();}
	public boolean resultChosenCallWasRon(){return tableWindow.resultChosenCallWasRon();}
	
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){
		tableWindow.askUserInputTurnAction(handSize, canRiichi, canAnkan, canMinkan, canTsumo);
	}
	public boolean resultChosenTurnActionWasDiscard(){return tableWindow.resultChosenTurnActionWasDiscard();}
	public boolean resultChosenTurnActionWasAnkan(){return tableWindow.resultChosenTurnActionWasAnkan();}
	public boolean resultChosenTurnActionWasMinkan(){return tableWindow.resultChosenTurnActionWasMinkan();}
	public boolean resultChosenTurnActionWasRiichi(){return tableWindow.resultChosenTurnActionWasRiichi();}
	public boolean resultChosenTurnActionWasTsumo(){return tableWindow.resultChosenTurnActionWasTsumo();}
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int resultChosenDiscardIndex(){return tableWindow.resultChosenDiscardIndex();}
	
	
	
	
	
	
	
	
	
	
	
	
	public void startUI(){tableWindow.blankEverything(); tableWindow.setVisible(true);}
	public void endUI(){tableWindow.dispose();}
	
	
	
	
	public void syncWithRoundTracker(RoundEntities roundEntities){
		super.syncWithRoundTracker(roundEntities);
		tableWindow.syncWithRoundTracker(roundEntities);
	}
	
	
	
	
	
	
	//launch the application TODO MAIN
	public static void main(String[] args) {
		
		GraphicalUI viewer = new GraphicalUI();
		
		viewer.startUI();
//		viewer.showResult();
	}
	
	//TODO start of constructor
	public GraphicalUI(){
		
		tableWindow = new TableViewBase();
		
		
		
	}
	
	

	
	public void printErrorRoundAlreadyOver(){System.out.println("----Error: Round is already over, cannot play");}
}