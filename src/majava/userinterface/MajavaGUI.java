package majava.userinterface;

import majava.enums.CallType;
import majava.enums.Exclamation;
import majava.enums.TurnActionType;
import majava.player.Player;
import majava.player.brains.HumanBrain;
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
	
	
	protected void displayEventDiscardedTile(){tableWindow.updateEverything();}
	protected void displayEventMadeOpenMeld(){tableWindow.updateEverything();}
	protected void displayEventDrewTile(){tableWindow.updateEverything();}
	protected void displayEventMadeOwnKan(){tableWindow.updateEverything();}
	protected void displayEventNewDoraIndicator(){/*intentionally blank, don't need to show new indicator because it is shown automatically*/}
	protected void displayEventPlayerTurnStart(){tableWindow.updateEverything();}
	
	protected void displayEventStartOfRound(){
		tableWindow.giveGameState(gameState);
		tableWindow.updateEverything();
	}
	
	protected void displayEventEndOfRound(){
		tableWindow.showResult(gameState.getResultSummary());
		tableWindow.updateEverything();
		
		if (sleepTimeRoundEnd > 0) Pauser.pauseFor(sleepTimeRoundEnd);
	}
	
	
	
	
	protected void showExclamation(Exclamation exclamation, int seat){
		tableWindow.exclamationShow(exclamation, seat);
		if (sleepTimeExclamation > 0) Pauser.pauseFor(sleepTimeExclamation);
		tableWindow.exclamationErase();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void startUI(){tableWindow.blankEverything(); tableWindow.setVisible(true);}
	public void endUI(){tableWindow.dispose();}
	
	
	public void printErrorRoundAlreadyOver(){System.out.println("----Error: Round is already over, cannot play");}
	
	
	
	
	protected void displayEventHumanTurnStart(){
		tableWindow.updateEverything();
		//////////////////////////
	}
	
	@Override
	protected void displayEventHumanReactionStart() {
		//////////////////////////
	}
	
	public TableViewBase getTableWindow(){return tableWindow;}
}
