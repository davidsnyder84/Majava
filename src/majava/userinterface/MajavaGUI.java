package majava.userinterface;

import majava.RoundTracker;
import majava.enums.CallType;
import majava.enums.Exclamation;
import majava.enums.TurnActionType;
import majava.events.GameplayEvent;
import majava.player.Player;
import majava.player.brains.HumanBrain;
import majava.summary.StateOfGame;
import majava.tiles.GameTile;
import majava.userinterface.graphicalinterface.window.TableViewBase;
import utility.Pauser;

public class MajavaGUI extends GameUI{
	private static final int DEAFULT_SLEEPTIME = 400, DEAFULT_SLEEPTIME_EXCLAMATION = 1500, DEAFULT_SLEEPTIME_ROUND_END = 2000;
	
	
	private TableViewBase tableWindow;
	
	
	public MajavaGUI(){		
		tableWindow = new TableViewBase();
//		tableWindow = new TableViewLarge();
	}
	
	
	protected void __displayEventDiscardedTile(GameplayEvent event){tableWindow.updateEverything();}
	protected void __displayEventMadeOpenMeld(GameplayEvent event){tableWindow.updateEverything();}
	protected void __displayEventDrewTile(GameplayEvent event){tableWindow.updateEverything();}
	protected void __displayEventMadeOwnKan(GameplayEvent event){tableWindow.updateEverything();}
	protected void __displayEventNewDoraIndicator(GameplayEvent event){/*intentionally blank, don't need to show new indicator because it is shown automatically*/}
	protected void __displayEventPlaceholder(GameplayEvent event){tableWindow.updateEverything();}
	
	protected void __displayEventStartOfRound(GameplayEvent event){tableWindow.updateEverything();}
	
	protected void __displayEventEndOfRound(GameplayEvent event){
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
	public boolean askUserInputCall(int seatNumber, boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){
		boolean called = tableWindow.askUserInputCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);
		CallType chosenCallType = tableWindow.resultChosenCall();
		
		controllerOfPlayer(seatNumber).setCallChosenByHuman(chosenCallType);
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
	
	
	
	
	protected void __displayEventHumanTurnStart(GameplayEvent event){
		tableWindow.updateEverything();
		
		Player p = event.getRelatedPlayer();
		
		tableWindow.movePromptPanelToSeat(p.getPlayerNumber());
		askUserInputTurnAction(
				p.handSize(),
				p.ableToRiichi(),
				p.ableToAnkan(),
				p.ableToMinkan(),
				p.ableToTsumo()
				);
	}

	@Override
	protected void __displayEventHumanReactionStart(GameplayEvent event) {
		Player p = event.getRelatedPlayer();
		GameTile tile = event.getRelatedTile();
		
		tableWindow.movePromptPanelToSeat(p.getPlayerNumber());
		askUserInputCall(
				p.getPlayerNumber(),
				p.ableToCallChiL(tile),
				p.ableToCallChiM(tile),
				p.ableToCallChiH(tile),
				p.ableToCallPon(tile),
				p.ableToCallKan(tile),
				p.ableToCallRon(tile)
				);
	}
}
