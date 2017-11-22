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
	
	
	protected void displayEventDiscardedTile(GameplayEvent event){tableWindow.updateEverything();}
	protected void displayEventMadeOpenMeld(GameplayEvent event){tableWindow.updateEverything();}
	protected void displayEventDrewTile(GameplayEvent event){tableWindow.updateEverything();}
	protected void displayEventMadeOwnKan(GameplayEvent event){tableWindow.updateEverything();}
	protected void displayEventNewDoraIndicator(GameplayEvent event){/*intentionally blank, don't need to show new indicator because it is shown automatically*/}
	protected void displayEventPlayerTurnStart(GameplayEvent event){tableWindow.updateEverything();}
	
	protected void displayEventStartOfRound(GameplayEvent event){
		tableWindow.giveGameState(gameState);
		tableWindow.updateEverything();
	}
	
	protected void displayEventEndOfRound(GameplayEvent event){
		tableWindow.showResult(gameState.getResultSummary());
		tableWindow.updateEverything();
		
		if (sleepTimeRoundEnd > 0) Pauser.pauseFor(sleepTimeRoundEnd);
	}
	
	
	
	
	protected void showExclamation(Exclamation exclamation, int seat){
		tableWindow.exclamationShow(exclamation, seat);
		if (sleepTimeExclamation > 0) Pauser.pauseFor(sleepTimeExclamation);
		tableWindow.exclamationErase();
	}
	
	
	
	
	
	
	//get user input from window
	public void askUserInputCall(int seatNumber, boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){
		CallType chosenCallType = tableWindow.askUserInputCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);;
		
		controllerOfPlayer(seatNumber).setCallChosenByHuman(chosenCallType);
	}
	
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){
		TurnActionType chosenAction = tableWindow.askUserInputTurnAction(handSize, canRiichi, canAnkan, canMinkan, canTsumo);
		controllerOfCurrentPlayer().setTurnActionChosenByHuman(chosenAction);
		
		if (chosenAction == TurnActionType.DISCARD){
			int chosenIndex = tableWindow.getChosenDiscardIndex() - 1;
			controllerOfCurrentPlayer().setDiscardIndexChosenByHuman(chosenIndex);
		}
	}
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int resultChosenDiscardIndex(){return tableWindow.getChosenDiscardIndex();}
	
	private HumanBrain controllerOfCurrentPlayer(){return controllerOfPlayer(gameState.getRoundTracker().whoseTurn());}
	private HumanBrain controllerOfPlayer(int playerNum){return (HumanBrain) gameState.getControllerForPlayer(playerNum);}
	
	
	
	
	
	public void startUI(){tableWindow.blankEverything(); tableWindow.setVisible(true);}
	public void endUI(){tableWindow.dispose();}
	
	
	public void printErrorRoundAlreadyOver(){System.out.println("----Error: Round is already over, cannot play");}
	
	
	
	
	protected void displayEventHumanTurnStart(GameplayEvent event){
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
	protected void displayEventHumanReactionStart(GameplayEvent event) {
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
