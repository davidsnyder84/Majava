package majava.graphics.textinterface;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import majava.Hand;
import majava.Meld;
import majava.Player;
import majava.Pond;
import majava.RoundTracker;
import majava.TileList;
import majava.Wall;
import majava.enums.Exclamation;
import majava.enums.Wind;
import majava.enums.GameplayEvent;

public class TextualUI {
	
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
	
	protected final PrintStream mOutStream;
	
	
	
	protected class PlayerTracker{
		protected Player player;
		
//		private Wind seatWind;
//		private int points;
//		private boolean riichiStatus;
//		private String playerName;
		
		protected Hand hand;
		protected Pond pond;
		
//		private ArrayList<Meld> melds = new ArrayList<Meld>();
		
		public PlayerTracker(Player p, Hand tH, Pond tP){player = p;hand = tH;pond = tP;}
	}
	protected PlayerTracker[] mPTrackers;
	protected Wall mWall;
	
	
	
	
	protected RoundTracker mRoundTracker;
	
	
	public TextualUI(){
		mOutStream = System.out;
		mRoundTracker = null; mWall = null; mPTrackers = null;
	}
	
	
	
	
	
	
	
	public void showDiscardedTile(){}
	public void showMadeOpenMeld(){}
	public void showDrewTile(){}
	public void showMadeOwnKan(){}
	
	public void showDeadWall(){
		printDoraIndicators();
		mWall.printDeadWall();
	}
	
	public void showDrewTileRinshan(){
		mWall.printDeadWall();
		mWall.printDoraIndicators();	//debug
	}
	
	
	public void showHandsOfAllPlayers(){
		//prints the hands of each player
		for (PlayerTracker pt: mPTrackers) pt.player.showHand();
	}
	
	public void printWall(){
		mWall.printWall();
	}
	public void printDoraIndicators(){
		mWall.printDoraIndicators();
	}
	
	
	public void showRoundResult(){
		mRoundTracker.printRoundResult();
	}
	
	
	public void displayEvent(GameplayEvent event){
		
		switch(event){
		case DISCARDED_TILE: showDiscardedTile(); break;
		case MADE_OPEN_MELD: showMadeOpenMeld(); break;
		case DREW_TILE: showDrewTile(); break;
		case MADE_OWN_KAN: showMadeOwnKan(); break;
		case NEW_DORA_INDICATOR: showDeadWall(); break;
		
		
		case START_OF_ROUND: printWall(); printDoraIndicators(); break;
		case PLACEHOLDER: break;
		case END_OF_ROUND: showHandsOfAllPlayers(); showRoundResult(); break;
		default: break;
		}
	}
	
	
	
	
	public void showExclamation(Exclamation exclamation, Wind playerWind, int sleepTime){
		mOutStream.println("Hi bob.");
//		//if multiple players called, show if someone got bumped by priority 
//		for (Player p: mPlayerArray)
//			if (p.called() && p != priorityCaller)
//				System.out.println("~~~~~~~~~~" + p.getSeatWind() + " Player tried to call " + p.getCallStatusString() + ", but got bumped by " + priorityCaller.getSeatWind() + "!");
//		System.out.println();
	}
	public void showExclamation(Exclamation exclamation, Wind playerWind){showExclamation(exclamation, playerWind, DEFAULT_SLEEP_TIME_EXCLAMATION);}
	
	
	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, Hand[] pHands, Pond[] pPonds, Wall wall){
		
		mRoundTracker = rTracker;
		
		
		mPTrackers = new PlayerTracker[NUM_PLAYERS_TO_TRACK];
		for (int i = 0; i < NUM_PLAYERS_TO_TRACK; i++) mPTrackers[i] = new PlayerTracker(pPlayers[i], pHands[i], pPonds[i]);
		
		mWall = wall;
	}

	
	
	public void printErrorRoundAlreadyOver(){mOutStream.println("----Error: Round is already over, cannot play");}
	
	
	
	
	public static void main(String[] args) {
		
		
	}



}
