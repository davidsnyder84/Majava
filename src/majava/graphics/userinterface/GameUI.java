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
	
	
	
	
	
	
//	public void syncWithRoundTracker(RoundTracker rTracker,
//			  Player[] pPlayers, 
//			  Hand[] pHands, TileList[] pHandTiles, 
//			  Pond[] pPonds, TileList[] pPondTiles,
//			  Wall wall, Tile[] trackerTilesW);
	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, Hand[] pHands, TileList[] pHandTiles, Pond[] pPonds, TileList[] pPondTiles, Wall wall, Tile[] trackerTilesW);
	
	
	
	void setSleepTimeExclamation(int sleepTime);
	
	void printErrorRoundAlreadyOver();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void getClickTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo);
	public boolean resultClickTurnActionWasDiscard();
	public boolean resultClickTurnActionWasAnkan();
	public boolean resultClickTurnActionWasMinkan();
	public boolean resultClickTurnActionWasRiichi();
	public boolean resultClickTurnActionWasTsumo();
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int getResultClickedDiscard();
	
	
	public boolean getClickCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon);
	public boolean resultClickCallWasNone();
	public boolean resultClickCallWasChiL();
	public boolean resultClickCallWasChiM();
	public boolean resultClickCallWasChiH();
	public boolean resultClickCallWasPon();
	public boolean resultClickCallWasKan();
	public boolean resultClickCallWasRon();
	
	
}
