package majava.userinterface;

import majava.hand.Hand;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.Exclamation;
import majava.events.GameplayEvent;
import majava.userinterface.graphicalinterface.GraphicalUI;
import majava.userinterface.graphicalinterface.window.TableViewBase;
import majava.userinterface.textinterface.DetailedTextualUI;
import majava.userinterface.textinterface.SparseTextualUI;
import majava.userinterface.textinterface.TextualUI;
import majava.player.Player;
import majava.summary.RoundResultSummary;
import majava.summary.entity.RoundEntities;


public class ComboTextGraphicalUI extends GameUI{

	private TextualUI textualUI;
	private GraphicalUI graphicalUI;
	
	
	public ComboTextGraphicalUI(){
		textualUI = __generateTextualUI();
		graphicalUI = __generateGraphicalUI();
	}

	private GraphicalUI __generateGraphicalUI(){
		return new GraphicalUI();
//		return new TableViewSmall();
//		return new TableViewer();
	}
	private TextualUI __generateTextualUI(){
		return new SparseTextualUI();
//		return new DetailedTextualUI();
	}
	
	
	
	
	
	public void displayEvent(GameplayEvent e){
		textualUI.displayEvent(e);
		graphicalUI.displayEvent(e);
	}
	
	public void setRoundResult(RoundResultSummary resum){
		textualUI.setRoundResult(resum);
		graphicalUI.setRoundResult(resum);
	}
	
	
	public final void setSleepTimes(int sleepTime, int sleepTimeExclamation, int sleepTimeRoundEnd){
		textualUI.setSleepTimes(0,0,0);
		graphicalUI.setSleepTimes(sleepTime, sleepTimeExclamation, sleepTimeRoundEnd);
	}
	
	public void printErrorRoundAlreadyOver(){
		textualUI.printErrorRoundAlreadyOver();
		graphicalUI.printErrorRoundAlreadyOver();
	}
	
//	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, Hand[] pHands, TileList[] pHandTiles, Pond[] pPonds, TileList[] pPondTiles, Wall wall, Tile[] tilesW){
	public void syncWithRoundTracker(RoundEntities roundEntities){
		textualUI.syncWithRoundTracker(roundEntities);
		graphicalUI.syncWithRoundTracker(roundEntities);
	}
	
	
	
	//user interaction (sends to the GUI)
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){graphicalUI.askUserInputTurnAction(handSize, canRiichi, canAnkan, canMinkan, canTsumo);}
	public boolean resultChosenTurnActionWasDiscard(){return graphicalUI.resultChosenTurnActionWasDiscard();}
	public boolean resultChosenTurnActionWasAnkan(){return graphicalUI.resultChosenTurnActionWasAnkan();}
	public boolean resultChosenTurnActionWasMinkan(){return graphicalUI.resultChosenTurnActionWasMinkan();}
	public boolean resultChosenTurnActionWasRiichi(){return graphicalUI.resultChosenTurnActionWasRiichi();}
	public boolean resultChosenTurnActionWasTsumo(){return graphicalUI.resultChosenTurnActionWasTsumo();}
	public int resultChosenDiscardIndex(){return graphicalUI.resultChosenDiscardIndex();}
	public boolean askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){return graphicalUI.askUserInputCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);}
	public boolean resultChosenCallWasNone(){return graphicalUI.resultChosenCallWasNone();}
	public boolean resultChosenCallWasChiL(){return graphicalUI.resultChosenCallWasChiL();}
	public boolean resultChosenCallWasChiM(){return graphicalUI.resultChosenCallWasChiM();}
	public boolean resultChosenCallWasChiH(){return graphicalUI.resultChosenCallWasChiH();}
	public boolean resultChosenCallWasPon(){return graphicalUI.resultChosenCallWasPon();}
	public boolean resultChosenCallWasKan(){return graphicalUI.resultChosenCallWasKan();}
	public boolean resultChosenCallWasRon(){return graphicalUI.resultChosenCallWasRon();}
	
	
	
	public void startUI(){
		textualUI.startUI();
		graphicalUI.startUI();
	}
	public void endUI(){
		textualUI.endUI();
		graphicalUI.endUI();
	}
	
	
	

	
	/*intentionally blank*/
	protected void __displayEventDiscardedTile(){}protected void __displayEventMadeOpenMeld(){}protected void __displayEventDrewTile(){}protected void __displayEventMadeOwnKan(){}protected void __displayEventNewDoraIndicator(){}protected void __displayEventHumanTurnStart(){}
	protected void __displayEventStartOfRound(){}protected void __displayEventEndOfRound(){}protected void __displayEventPlaceholder(){}protected void __showExclamation(Exclamation exclamation, int seat){}
}
