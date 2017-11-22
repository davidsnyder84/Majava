package majava.userinterface;

import majava.RoundTracker;
import majava.enums.CallType;
import majava.enums.Exclamation;
import majava.enums.TurnActionType;
import majava.player.brains.HumanBrain;
import majava.summary.StateOfGame;
import majava.userinterface.graphicalinterface.window.TableViewBase;
import utility.Pauser;

public class MajavaGUI extends GameUI{

	
	private static final int DEAFULT_SLEEPTIME = 400, DEAFULT_SLEEPTIME_EXCLAMATION = 1500, DEAFULT_SLEEPTIME_ROUND_END = 2000;
	
	
	private TableViewBase tableWindow;
	
	
	public MajavaGUI(){		
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
		boolean called = tableWindow.askUserInputCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);
		CallType chosenCallType = CallType.NONE;
		if (called){
			if (tableWindow.resultChosenCallWasChiL()) chosenCallType = CallType.CHI_L;
			else if (tableWindow.resultChosenCallWasChiM()) chosenCallType = CallType.CHI_M;
			else if (tableWindow.resultChosenCallWasChiH()) chosenCallType = CallType.CHI_H;
			else if (tableWindow.resultChosenCallWasPon()) chosenCallType = CallType.PON;
			else if (tableWindow.resultChosenCallWasKan()) chosenCallType = CallType.KAN;
			else if (tableWindow.resultChosenCallWasRon()) chosenCallType = CallType.RON;
		}
		/////////NEED TO KNOW PLAYER NUMBER OF WHO WE'RE ASKING TO CALL
//		((HumanBrain) gameState.getControllerForPlayer((gameState.getRoundTracker().whoseTurn()))).setCallChosenByHuman(chosenCallType);
		((HumanBrain) gameState.getControllerForPlayer(0)).setCallChosenByHuman(chosenCallType);
		return called;
	}
	
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){
		tableWindow.askUserInputTurnAction(handSize, canRiichi, canAnkan, canMinkan, canTsumo);
		
		TurnActionType chosenAction = TurnActionType.DISCARD;
		if (tableWindow.resultChosenTurnActionWasDiscard()) chosenAction = TurnActionType.DISCARD;
		else if (tableWindow.resultChosenTurnActionWasAnkan()) chosenAction = TurnActionType.ANKAN;
		else if (tableWindow.resultChosenTurnActionWasMinkan()) chosenAction = TurnActionType.MINKAN;
		else if (tableWindow.resultChosenTurnActionWasRiichi()) chosenAction = TurnActionType.RIICHI;
		else if (tableWindow.resultChosenTurnActionWasTsumo()) chosenAction = TurnActionType.TSUMO;
		
		((HumanBrain) gameState.getControllerForPlayer((gameState.getRoundTracker().whoseTurn()))).setTurnActionChosenByHuman(chosenAction);
		
		if (chosenAction == TurnActionType.DISCARD)
			((HumanBrain) gameState.getControllerForPlayer((gameState.getRoundTracker().whoseTurn()))).setDiscardIndexChosenByHuman(tableWindow.resultChosenDiscardIndex() - 1);
	}
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int resultChosenDiscardIndex(){return tableWindow.resultChosenDiscardIndex();}
	
	
	
	
	
	public void startUI(){tableWindow.blankEverything(); tableWindow.setVisible(true);}
	public void endUI(){tableWindow.dispose();}
	
	
	
	
	public void syncWithRoundTracker(RoundTracker tracker, StateOfGame stateOfGame){
		super.syncWithRoundTracker(tracker, stateOfGame);
		tableWindow.syncWithRoundTracker(tracker, stateOfGame);
	}
	
	public void printErrorRoundAlreadyOver(){System.out.println("----Error: Round is already over, cannot play");}
	
	
	
	public static void main(String[] args) {		
		MajavaGUI viewer = new MajavaGUI();
		
		viewer.startUI();
//		viewer.showResult();
	}
	
	public void movePromptPanelToSeat(int seat){tableWindow.movePromptPanelToSeat(seat);}
}
