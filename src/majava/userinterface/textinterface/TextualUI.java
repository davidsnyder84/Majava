package majava.userinterface.textinterface;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import majava.enums.Exclamation;
import majava.enums.GameplayEvent;
import majava.player.Player;
import majava.summary.entity.PlayerTracker;
import majava.summary.entity.RoundEntities;
import majava.userinterface.GameUI;



public abstract class TextualUI extends GameUI{
	
	protected static final Map<Exclamation, String> exclamationToString;
	static {
		Map<Exclamation, String> aMap = new HashMap<Exclamation, String>();
		aMap.put(Exclamation.CHI, "Chi");aMap.put(Exclamation.PON, "Pon");aMap.put(Exclamation.KAN, "Kan");aMap.put(Exclamation.RON, "Ron");
		aMap.put(Exclamation.RIICHI, "Riichi");aMap.put(Exclamation.OWN_KAN, "Kan~");aMap.put(Exclamation.TSUMO, "Tsumo");
		aMap.put(Exclamation.NONE, Exclamation.NONE.toString());aMap.put(Exclamation.UNKNOWN, Exclamation.UNKNOWN.toString());
		exclamationToString = Collections.unmodifiableMap(aMap);
	}
	
	
	protected final PrintStream mOutStream;
	
	
	
	public TextualUI(){
		mOutStream = System.out;
		mRoundEntities = null;
//		mRoundTracker = null; mWall = null; mPTrackers = null;
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
	
	
	

	protected void __showHandsOfAllPlayers(){println(); for (PlayerTracker pt: mRoundEntities.mPTrackers) __showPlayerHand(pt.player); println();}
	protected void __showPlayerHand(int seatNum){__showPlayerHand(mRoundEntities.mPTrackers[seatNum].player);}
	protected abstract void __showPlayerHand(Player p);
	
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
	
	
	
	
	
	
	
	public void println(String printString){mOutStream.println(printString);}public void println(){println("");}
	public void print(String printString){mOutStream.print(printString);}public void print(){print("");}
	
	
	public void printErrorRoundAlreadyOver(){println("----Error: Round is already over, cannot play");}
	
	
	
}
