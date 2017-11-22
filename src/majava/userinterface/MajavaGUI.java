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
	protected void __displayEventNewDoraIndicator(){/*intentionally blank, don't need to show new indicator because it is shown automatically*/}
	protected void __displayEventHumanTurnStart(){tableWindow.updateEverything();}
	protected void __displayEventPlaceholder(){tableWindow.updateEverything();}
	
	protected void __displayEventStartOfRound(){tableWindow.updateEverything();}
	
	protected void __displayEventEndOfRound(){
		tableWindow.showResult(resultSummary);
		tableWindow.updateEverything();
		
		if (mSleepTimeRoundEnd > 0) Pauser.pauseFor(mSleepTimeRoundEnd);
	}
	
	
	
	
	protected void __showExclamation(Exclamation exclamation, int seat){
		tableWindow.exclamationShow(exclamation, seat);
		if (mSleepTimeExclamation > 0) Pauser.pauseFor(mSleepTimeExclamation);
		tableWindow.exclamationErase();
	}
	
	
	
	
	
	
	//get user input from window
	public boolean askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){
		boolean called = tableWindow.askUserInputCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);
		CallType chosenCallType = tableWindow.resultChosenCall();
		
		/////////NEED TO KNOW PLAYER NUMBER OF WHO WE'RE ASKING TO CALL
//		((HumanBrain) gameState.getControllerForPlayer((gameState.getRoundTracker().whoseTurn()))).setCallChosenByHuman(chosenCallType);
		((HumanBrain) gameState.getControllerForPlayer(0)).setCallChosenByHuman(chosenCallType);
		return called;
	}
	
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){
		tableWindow.askUserInputTurnAction(handSize, canRiichi, canAnkan, canMinkan, canTsumo);
		
		//chosen action
		TurnActionType chosenAction = tableWindow.resultChosenTurnAction();
		controllerOfCurrentPlayer().setTurnActionChosenByHuman(chosenAction);
		
		//chosen discard index
		if (chosenAction == TurnActionType.DISCARD)
			controllerOfCurrentPlayer().setDiscardIndexChosenByHuman(tableWindow.resultChosenDiscardIndex() - 1);
	}
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int resultChosenDiscardIndex(){return tableWindow.resultChosenDiscardIndex();}
	
	private HumanBrain controllerOfCurrentPlayer(){return controllerOfPlayer(gameState.getRoundTracker().whoseTurn());}
	private HumanBrain controllerOfPlayer(int playerNum){return (HumanBrain) gameState.getControllerForPlayer(playerNum);}
	
	
	
	
	
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
