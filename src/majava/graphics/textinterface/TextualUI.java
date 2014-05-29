package majava.graphics.textinterface;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import majava.Hand;
import majava.Player;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.Exclamation;
import majava.enums.GameplayEvent;
import majava.graphics.userinterface.GameUI;



public abstract class TextualUI implements GameUI{
	
	protected static final int DEFAULT_SLEEP_TIME_EXCLAMATION = 1500;
	protected static final int NUM_PLAYERS_TO_TRACK = 4;
	
	protected static final Map<Exclamation, String> exclamationToString;
	static {
		Map<Exclamation, String> aMap = new HashMap<Exclamation, String>();
		aMap.put(Exclamation.CHI, "Chi");aMap.put(Exclamation.PON, "Pon");aMap.put(Exclamation.KAN, "Kan");aMap.put(Exclamation.RON, "Ron");
		aMap.put(Exclamation.RIICHI, "Riichi");aMap.put(Exclamation.OWN_KAN, "Kan~");aMap.put(Exclamation.TSUMO, "Tsumo");
		aMap.put(Exclamation.NONE, Exclamation.NONE.toString());aMap.put(Exclamation.UNKNOWN, Exclamation.UNKNOWN.toString());
		exclamationToString = Collections.unmodifiableMap(aMap);
	}
	
	
	protected class PlayerTracker{
		protected Player player;
		protected Hand hand;
		protected Pond pond;
		
		public PlayerTracker(Player p, Hand tH, Pond tP){player = p;hand = tH;pond = tP;}
	}
	
	
	protected final PrintStream mOutStream;
	protected int mSleepTimeExclamation = DEFAULT_SLEEP_TIME_EXCLAMATION;
	
	protected PlayerTracker[] mPTrackers;
	protected Wall mWall;
	protected RoundTracker mRoundTracker;
	
	
	public TextualUI(){
		mOutStream = System.out;
		mRoundTracker = null; mWall = null; mPTrackers = null;
	}
	
	
	
	
	
	

	
	
	public void displayEvent(GameplayEvent event){
		switch(event){
		case DISCARDED_TILE: __displayEventDiscardedTile(); return;
		case MADE_OPEN_MELD: __displayEventMadeOpenMeld(); return;
		case DREW_TILE: __displayEventDrewTile(); return;
		case MADE_OWN_KAN: __displayEventMadeOwnKan(); return;
		case NEW_DORA_INDICATOR: __displayEventNewDoraIndicator(); return;
		
		case HUMAN_PLAYER_TURN_START: __displayEventHumanTurnStart(); return;
		
		case START_OF_ROUND: __displayEventStartOfRound(); return;
		case PLACEHOLDER: break;
		case END_OF_ROUND: __displayEventEndOfRound(); return;
		default: break;
		}
		
		if (event.isExclamation()) __showExclamation(event.getExclamation(), event.getSeat());
	}
	
	protected abstract void __displayEventDiscardedTile();
	protected abstract void __displayEventMadeOpenMeld();
	protected abstract void __displayEventDrewTile();
	protected abstract void __displayEventMadeOwnKan();
	protected abstract void __displayEventNewDoraIndicator();
	protected abstract void __displayEventHumanTurnStart();
	
	protected abstract void __displayEventStartOfRound();
	protected abstract void __displayEventEndOfRound();
	
	protected abstract void __showExclamation(Exclamation exclamation, int seat);
	
	

	
	
	protected abstract void __showHandsOfAllPlayers();
	
	protected abstract void __showWall();
	protected abstract void __showDeadWall();
	protected abstract void __showDoraIndicators();
	
	protected abstract void __showRoundResult();
	
	
	
	
	
	
	
	
	
	public void setSleepTimeExclamation(int sleepTime){mSleepTimeExclamation = sleepTime;};
	
	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, Hand[] pHands, Pond[] pPonds, Wall wall){
		
		mRoundTracker = rTracker;
		
		
		mPTrackers = new PlayerTracker[NUM_PLAYERS_TO_TRACK];
		for (int i = 0; i < NUM_PLAYERS_TO_TRACK; i++) mPTrackers[i] = new PlayerTracker(pPlayers[i], pHands[i], pPonds[i]);
		
		mWall = wall;
	}
	
	
	public void println(String printString){mOutStream.println(printString);}
	public void println(){println("");}
	
	public void printErrorRoundAlreadyOver(){println("----Error: Round is already over, cannot play");}
	
	
	
}
