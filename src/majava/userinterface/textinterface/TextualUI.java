package majava.userinterface.textinterface;

import java.io.PrintStream;

import majava.enums.Exclamation;
import majava.player.Player;
import majava.userinterface.GameUI;



public abstract class TextualUI extends GameUI{
	
	protected final PrintStream outStream;
	
	public TextualUI(){
		outStream = System.out;
		gameState = null;/////why?
	}
	
	
	protected abstract void __displayEventDiscardedTile();
	protected abstract void __displayEventMadeOpenMeld();
	protected abstract void __displayEventDrewTile();
	protected abstract void __displayEventMadeOwnKan();
	protected abstract void __displayEventNewDoraIndicator();
	protected abstract void __displayEventHumanTurnStart();
	protected abstract void __displayEventPlaceholder();
	
	protected abstract void __displayEventStartOfRound();
	protected abstract void __displayEventEndOfRound();
	
	protected abstract void __showExclamation(Exclamation exclamation, int seat);
	
	
	

	protected void __showHandsOfAllPlayers(){
		println();
		for (int i = 0; i < NUM_PLAYERS; i++)
			__showPlayerHand(i);
		println();
	}
	protected abstract void __showPlayerHand(int seatNum);
	
	protected abstract void __showWall();
	protected abstract void __showDeadWall();
	protected abstract void __showDoraIndicators();
	
	protected abstract void __showRoundResult();
	
	
	
	
	
	//user interaction (not yet implemented)
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){}
	public boolean resultChosenTurnActionWasDiscard(){return false;}
	public boolean resultChosenTurnActionWasAnkan(){return false;}
	public boolean resultChosenTurnActionWasMinkan(){return false;}
	public boolean resultChosenTurnActionWasRiichi(){return false;}
	public boolean resultChosenTurnActionWasTsumo(){return false;}
	public int resultChosenDiscardIndex(){return -1;}
	public boolean askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){return false;}
	public boolean resultChosenCallWasNone(){return false;}
	public boolean resultChosenCallWasChiL(){return false;}
	public boolean resultChosenCallWasChiM(){return false;}
	public boolean resultChosenCallWasChiH(){return false;}
	public boolean resultChosenCallWasPon(){return false;}
	public boolean resultChosenCallWasKan(){return false;}
	public boolean resultChosenCallWasRon(){return false;}
	
	
	
	
	public void startUI(){/*intentionally blank*/}
	public void endUI(){/*intentionally blank*/}
	
	
	
	
	public void println(String printString){outStream.println(printString);}public void println(){println("");}
	public void print(String printString){outStream.print(printString);}public void print(){print("");}
	
	
	public void printErrorRoundAlreadyOver(){println("----Error: Round is already over, cannot play");}
	
}
