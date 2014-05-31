package majava.graphics.userinterface;

import majava.Hand;
import majava.Player;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.GameplayEvent;
import majava.tiles.Tile;
import majava.util.TileList;

public interface GameUI {
	
	
	
	public void startUI();
	public void endUI();
	
	
	
	
	
	
	
	
	void displayEvent(GameplayEvent e);
	
	
	
	
	
	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, Hand[] pHands, TileList[] pHandTiles, Pond[] pPonds, TileList[] pPondTiles, Wall wall, Tile[] trackerTilesW);
	
	
	
	void setSleepTimeExclamation(int sleepTime);
	
	void printErrorRoundAlreadyOver();
	
	
	
	
	
	
	
	
	
	//user input
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo);
	public boolean resultChosenTurnActionWasDiscard();
	public boolean resultChosenTurnActionWasAnkan();
	public boolean resultChosenTurnActionWasMinkan();
	public boolean resultChosenTurnActionWasRiichi();
	public boolean resultChosenTurnActionWasTsumo();
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int resultChosenDiscardIndex();
	
	public boolean askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon);
	public boolean resultChosenCallWasNone();
	public boolean resultChosenCallWasChiL();
	public boolean resultChosenCallWasChiM();
	public boolean resultChosenCallWasChiH();
	public boolean resultChosenCallWasPon();
	public boolean resultChosenCallWasKan();
	public boolean resultChosenCallWasRon();
//	
	
}
