package majava.graphics.textinterface;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import utility.Pauser;

import majava.enums.Exclamation;
import majava.enums.Wind;
import majava.graphics.textinterface.TextualUI.PlayerTracker;

public class DetailedTextualUI extends TextualUI{
	
	protected static final Map<Exclamation, String> exclamationToString;
	static {
		Map<Exclamation, String> aMap = new HashMap<Exclamation, String>();
		aMap.put(Exclamation.CHI, "Chi");aMap.put(Exclamation.PON, "Pon");aMap.put(Exclamation.KAN, "Kan");aMap.put(Exclamation.RON, "Ron");
		aMap.put(Exclamation.RIICHI, "Riichi");aMap.put(Exclamation.OWN_KAN, "Kan~");aMap.put(Exclamation.TSUMO, "Tsumo");
		aMap.put(Exclamation.NONE, Exclamation.NONE.toString());aMap.put(Exclamation.UNKNOWN, Exclamation.UNKNOWN.toString());
		exclamationToString = Collections.unmodifiableMap(aMap);
	}
	
	
	
	
	public DetailedTextualUI(){
		super();
	}
	
	
	
	
	
	
	

	public void showDiscardedTile(){
		
		//show the human player their hand
		showHandsOfAllPlayers();
		
		//show the discarded tile and the discarder's pond
		System.out.println("\n\n\tTiles left: " + mRoundTracker.getNumTilesLeftInWall());
		System.out.println("\t" + mRoundTracker.currentPlayer().getSeatWind() + " Player's discard: ^^^^^" + mRoundTracker.getMostRecentDiscard().toString() + "^^^^^");
		mRoundTracker.currentPlayer().showPond();
	}
	
	
	
	
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void showExclamation(Exclamation exclamation, int seat){
		
		if (exclamation.isCall())
			mOutStream.println("\n*********************************************************" + 
							"\n**********" + mRoundTracker.getWindOfSeat(seat) + " Player called the tile (" + mRoundTracker.getMostRecentDiscard().toString() + ")! " + exclamationToString.get(exclamation) + "!!!**********" + 
							"\n*********************************************************");
		else
			mOutStream.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
							"\n~~~~~~~~~~~" + mRoundTracker.getWindOfSeat(seat) + " Player declared " + exclamationToString.get(exclamation) + "!!!~~~~~~~~~~~" + 
							"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		//pause
		Pauser.pauseFor(mSleepTimeExclamation);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
//		TextualUI ui = new DetailedTextualUI();
//		ui.showExclamation(Exclamation.PON, Wind.EAST);
	}

}
