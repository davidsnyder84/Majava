package majava.userinterface.textinterface;

import java.io.PrintStream;

import majava.enums.Exclamation;
import majava.events.GameplayEvent;
import majava.player.Player;
import majava.userinterface.GameUI;



public abstract class TextualUI extends GameUI{
	
	protected final PrintStream outStream;
	
	public TextualUI(){
		outStream = System.out;
		gameState = null;/////why?
	}
	
	
	protected abstract void __displayEventDiscardedTile(GameplayEvent event);
	protected abstract void __displayEventMadeOpenMeld(GameplayEvent event);
	protected abstract void __displayEventDrewTile(GameplayEvent event);
	protected abstract void __displayEventMadeOwnKan(GameplayEvent event);
	protected abstract void __displayEventNewDoraIndicator(GameplayEvent event);
	protected abstract void __displayEventHumanTurnStart(GameplayEvent event);
	protected abstract void __displayEventPlaceholder(GameplayEvent event);
	
	protected abstract void __displayEventStartOfRound(GameplayEvent event);
	protected abstract void __displayEventEndOfRound(GameplayEvent event);
	
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
	
	
	
	
	public void startUI(){/*intentionally blank*/}
	public void endUI(){/*intentionally blank*/}
	
	
	
	
	public void println(String printString){outStream.println(printString);}public void println(){println("");}
	public void print(String printString){outStream.print(printString);}public void print(){print("");}
	
	
	public void printErrorRoundAlreadyOver(){println("----Error: Round is already over, cannot play");}
	
}
