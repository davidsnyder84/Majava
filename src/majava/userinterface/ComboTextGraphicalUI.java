package majava.userinterface;

import majava.Hand;
import majava.Player;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.Exclamation;
import majava.enums.GameplayEvent;
import majava.userinterface.graphicalinterface.GraphicalUI;
import majava.userinterface.graphicalinterface.window.TableViewBase;
import majava.userinterface.graphicalinterface.window.TableViewSmall;
import majava.userinterface.graphicalinterface.window.TableViewer;
import majava.userinterface.textinterface.DetailedTextualUI;
import majava.userinterface.textinterface.SparseTextualUI;
import majava.userinterface.textinterface.TextualUI;
import majava.summary.entity.RoundEntities;
import majava.tiles.Tile;
import majava.util.TileList;


public class ComboTextGraphicalUI extends GameUI{

	private TextualUI mTextualUI;
	private GraphicalUI mGraphicalUI;
	
	
	public ComboTextGraphicalUI(){
		mTextualUI = __generateTextualUI();
		mGraphicalUI = __generateGraphicalUI();
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
		mTextualUI.displayEvent(e);
		mGraphicalUI.displayEvent(e);
	}
	
	
	
	public final void setSleepTimes(int sleepTime, int sleepTimeExclamation, int sleepTimeRoundEnd){
		mTextualUI.setSleepTimes(0,0,0);
		mGraphicalUI.setSleepTimes(sleepTime, sleepTimeExclamation, sleepTimeRoundEnd);
	}
	
	public void printErrorRoundAlreadyOver(){
		mTextualUI.printErrorRoundAlreadyOver();
		mGraphicalUI.printErrorRoundAlreadyOver();
	}
	
//	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, Hand[] pHands, TileList[] pHandTiles, Pond[] pPonds, TileList[] pPondTiles, Wall wall, Tile[] tilesW){
	public void syncWithRoundTracker(RoundEntities roundEntities){
		mTextualUI.syncWithRoundTracker(roundEntities);
		mGraphicalUI.syncWithRoundTracker(roundEntities);
	}
	
	
	
	//user interaction (sends to the GUI)
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){mGraphicalUI.askUserInputTurnAction(handSize, canRiichi, canAnkan, canMinkan, canTsumo);}
	public boolean resultChosenTurnActionWasDiscard(){return mGraphicalUI.resultChosenTurnActionWasDiscard();}
	public boolean resultChosenTurnActionWasAnkan(){return mGraphicalUI.resultChosenTurnActionWasAnkan();}
	public boolean resultChosenTurnActionWasMinkan(){return mGraphicalUI.resultChosenTurnActionWasMinkan();}
	public boolean resultChosenTurnActionWasRiichi(){return mGraphicalUI.resultChosenTurnActionWasRiichi();}
	public boolean resultChosenTurnActionWasTsumo(){return mGraphicalUI.resultChosenTurnActionWasTsumo();}
	public int resultChosenDiscardIndex(){return mGraphicalUI.resultChosenDiscardIndex();}
	public boolean askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){return mGraphicalUI.askUserInputCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);}
	public boolean resultChosenCallWasNone(){return mGraphicalUI.resultChosenCallWasNone();}
	public boolean resultChosenCallWasChiL(){return mGraphicalUI.resultChosenCallWasChiL();}
	public boolean resultChosenCallWasChiM(){return mGraphicalUI.resultChosenCallWasChiM();}
	public boolean resultChosenCallWasChiH(){return mGraphicalUI.resultChosenCallWasChiH();}
	public boolean resultChosenCallWasPon(){return mGraphicalUI.resultChosenCallWasPon();}
	public boolean resultChosenCallWasKan(){return mGraphicalUI.resultChosenCallWasKan();}
	public boolean resultChosenCallWasRon(){return mGraphicalUI.resultChosenCallWasRon();}
	
	
	
	public void startUI(){
		mTextualUI.startUI();
		mGraphicalUI.startUI();
	}
	public void endUI(){
		mTextualUI.endUI();
		mGraphicalUI.endUI();
	}
	
	
	

	
	/*intentionally blank*/
	protected void __displayEventDiscardedTile(){}protected void __displayEventMadeOpenMeld(){}protected void __displayEventDrewTile(){}protected void __displayEventMadeOwnKan(){}protected void __displayEventNewDoraIndicator(){}protected void __displayEventHumanTurnStart(){}
	protected void __displayEventStartOfRound(){}protected void __displayEventEndOfRound(){}protected void __displayEventPlaceholder(){}protected void __showExclamation(Exclamation exclamation, int seat){}
}
