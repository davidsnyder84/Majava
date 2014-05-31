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
		mTextualUI.setSleepTimeExclamation(sleepTime);
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
	public void getClickTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){mGraphicalUI.getClickTurnAction(handSize, canRiichi, canAnkan, canMinkan, canTsumo);}
	public boolean resultClickTurnActionWasDiscard(){return mGraphicalUI.resultClickTurnActionWasDiscard();}
	public boolean resultClickTurnActionWasAnkan(){return mGraphicalUI.resultClickTurnActionWasAnkan();}
	public boolean resultClickTurnActionWasMinkan(){return mGraphicalUI.resultClickTurnActionWasMinkan();}
	public boolean resultClickTurnActionWasRiichi(){return mGraphicalUI.resultClickTurnActionWasRiichi();}
	public boolean resultClickTurnActionWasTsumo(){return mGraphicalUI.resultClickTurnActionWasTsumo();}
	public int getResultClickedDiscard(){return mGraphicalUI.getResultClickedDiscard();}
	public boolean getClickCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){return mGraphicalUI.getClickCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);}
	public boolean resultClickCallWasNone(){return mGraphicalUI.resultClickCallWasNone();}
	public boolean resultClickCallWasChiL(){return mGraphicalUI.resultClickCallWasChiL();}
	public boolean resultClickCallWasChiM(){return mGraphicalUI.resultClickCallWasChiM();}
	public boolean resultClickCallWasChiH(){return mGraphicalUI.resultClickCallWasChiH();}
	public boolean resultClickCallWasPon(){return mGraphicalUI.resultClickCallWasPon();}
	public boolean resultClickCallWasKan(){return mGraphicalUI.resultClickCallWasKan();}
	public boolean resultClickCallWasRon(){return mGraphicalUI.resultClickCallWasRon();}
	
	
	
	public void startUI(){
		mTextualUI.startUI();
		mGraphicalUI.startUI();
	}
	public void endUI(){
		mTextualUI.endUI();
		mGraphicalUI.endUI();
	}
	
}
