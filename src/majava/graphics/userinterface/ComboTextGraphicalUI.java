package majava.graphics.userinterface;

import majava.Hand;
import majava.Player;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.GameplayEvent;
import majava.graphics.TableGUI;
import majava.graphics.TableViewSmall;
import majava.graphics.TableViewer;
import majava.graphics.textinterface.DetailedTextualUI;
import majava.graphics.textinterface.SparseTextualUI;
import majava.graphics.textinterface.TextualUI;
import majava.tiles.Tile;
import majava.util.TileList;


public class ComboTextGraphicalUI implements GameUI{

	private TextualUI mTextualUI;
	private TableGUI mGraphicalUI;
	
	
	public ComboTextGraphicalUI(){
		mTextualUI = __generateTextualUI();
		mGraphicalUI = __generateGraphicalUI();
	}

	private TableGUI __generateGraphicalUI(){
		return new TableViewSmall();
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
	
	public void setSleepTimeExclamation(int sleepTime){
		mTextualUI.setSleepTimeExclamation(0);
		mGraphicalUI.setSleepTimeExclamation(sleepTime);
	}
	
	public void printErrorRoundAlreadyOver(){
		mTextualUI.printErrorRoundAlreadyOver();
		mGraphicalUI.printErrorRoundAlreadyOver();
	}
	
	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, Hand[] pHands, TileList[] pHandTiles, Pond[] pPonds, TileList[] pPondTiles, Wall wall, Tile[] trackerTilesW){
		mTextualUI.syncWithRoundTracker(rTracker, pPlayers, pHands, pHandTiles, pPonds, pPondTiles, wall, trackerTilesW);
		mGraphicalUI.syncWithRoundTracker(rTracker, pPlayers, pHands, pHandTiles, pPonds, pPondTiles, wall, trackerTilesW);
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
	
}
