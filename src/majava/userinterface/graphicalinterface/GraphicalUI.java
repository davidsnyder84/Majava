
package majava.userinterface.graphicalinterface;

import majava.hand.Hand;
import majava.hand.Meld;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.Exclamation;
import majava.events.GameplayEvent;
import majava.userinterface.GameUI;
import majava.userinterface.graphicalinterface.window.TableViewBase;
import majava.userinterface.graphicalinterface.window.TableViewLarge;
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
	
	private static final int DEAFULT_SLEEPTIME = 400, DEAFULT_SLEEPTIME_EXCLAMATION = 1500, DEAFULT_SLEEPTIME_ROUND_END = 2000;
	
	
	private TableViewBase tableWindow;
	
	
	public GraphicalUI(){		
		tableWindow = new TableViewBase();
//		tableWindow = new TableViewLarge();
	}
	
	
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
	
	
	
	
	public void syncWithRoundTracker(RoundTracker tracker, RoundEntities roundEntities){
		super.syncWithRoundTracker(tracker, roundEntities);
		tableWindow.syncWithRoundTracker(tracker, roundEntities);
	}
	
	public void printErrorRoundAlreadyOver(){System.out.println("----Error: Round is already over, cannot play");}
	
	
	
	public static void main(String[] args) {		
		GraphicalUI viewer = new GraphicalUI();
		
		viewer.startUI();
//		viewer.showResult();
	}
	
}