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
	
	
	protected abstract void displayEventDiscardedTile();
	protected abstract void displayEventMadeOpenMeld();
	protected abstract void displayEventDrewTile();
	protected abstract void displayEventMadeOwnKan();
	protected abstract void displayEventNewDoraIndicator();
	protected abstract void displayEventHumanTurnStart();
	protected abstract void displayEventPlayerTurnStart();
	
	protected abstract void displayEventStartOfRound();
	protected abstract void displayEventEndOfRound();
	
	protected abstract void showExclamation(Exclamation exclamation, int seat);
	
	
	

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
	
	
	
	
	protected void showAllPlayerNames(){
		println();
		for (int i = 0; i < NUM_PLAYERS; i++){
			Player p = gameState.getPlayer(i);
			println("Player" + (i+1) + " (" + p.getSeatWind().toChar() + "): " + p.getPlayerName());
		}
		println();
	}
	
	
	public void startUI(){/*intentionally blank*/}
	public void endUI(){/*intentionally blank*/}
	
	
	
	
	public void println(String printString){outStream.println(printString);}public void println(){println("");}
	public void print(String printString){outStream.print(printString);}public void print(){print("");}
	
	
	public void printErrorRoundAlreadyOver(){println("----Error: Round is already over, cannot play");}
	
}
