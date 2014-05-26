package majava.graphics;

import majava.Player;
import majava.RoundTracker;
import majava.TileList;
import majava.tiles.Tile;

public interface TableGUI {
	
	
	//updates everything to reflect the current state of the game
	public void updateEverything();
	
	//blanks labels and images
	public void blankEverything();
	
	//displays the received exclamation and pauses briefly
	public void showExclamation(String exclamation, int seatNum, int sleepTime);
	
	
	
	
	//prompts a player to click a button to make a call
	public boolean getClickCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon);
	
	public boolean resultClickCallWasNone();
	public boolean resultClickCallWasChiL();
	public boolean resultClickCallWasChiM();
	public boolean resultClickCallWasChiH();
	public boolean resultClickCallWasPon();
	public boolean resultClickCallWasKan();
	public boolean resultClickCallWasRon();
	
	//prompts a player to click a button to choose an action
	public void getClickTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo);
	
	//returns true if the specified call was clicked
	public boolean resultClickTurnActionWasDiscard();
	public boolean resultClickTurnActionWasAnkan();
	public boolean resultClickTurnActionWasMinkan();
	public boolean resultClickTurnActionWasRiichi();
	public boolean resultClickTurnActionWasTsumo();
	
	//returns the discard index the player clicked on
	public int getResultClickedDiscard();
	
	
	
	
	
	//associates the GUI with the round tracker
	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, TileList[] pHandTiles, TileList[] pPondTiles, Tile[] trackerTilesW);
	
	
	
	
	
	
	//JFrame methods
	public void setVisible(boolean b);
	public void dispose();
	
	
	
	
	
	
}
