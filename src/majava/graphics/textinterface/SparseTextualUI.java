package majava.graphics.textinterface;

import majava.TileList;
import majava.enums.Exclamation;
import majava.tiles.Tile;
import utility.Pauser;

public class SparseTextualUI extends TextualUI{
	
	
	
	public SparseTextualUI(){
		super();
	}
	
	
	protected void __displayEventDiscardedTile(){
		
		//show the discarded tile
		println("\t" + mRoundTracker.currentPlayer().getSeatWind().toChar() + " Player discard: " + mRoundTracker.getMostRecentDiscard().toString());
//		mRoundTracker.currentPlayer().showPond();
	}
	
	
	
	
	
	protected void __displayEventMadeOpenMeld(){}
	protected void __displayEventDrewTile(){}
	protected void __displayEventMadeOwnKan(){}
	
	
	
	protected void __displayEventNewDoraIndicator(){
		__showDeadWall();
	}
	
	protected void __displayEventHumanTurnStart(){
		__showPlayerHand(mRoundTracker.whoseTurn());
	}
	
	protected void __displayEventStartOfRound(){
		__showWall(); __showDoraIndicators();
	}
	
	protected void __displayEventEndOfRound(){
		__showRoundResult();__showHandsOfAllPlayers();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//prints the hands of each player
	protected void __showHandsOfAllPlayers(){println(); for (int seatNum = 0; seatNum < 4; seatNum++) __showPlayerHand(seatNum); println();}
	protected void __showPlayerHand(int seatNum){
		String handString = mPTrackers[seatNum].player.getSeatWind() + " hand: ";
		for (Tile t: mPTrackers[seatNum].hand) handString += t + " ";
		println(handString);
	}
	
	protected void __showWall(){println(mWall.toString());}
	
	protected void __showDoraIndicators(){
		println(",,,New Dora Indicator: " + mWall.getDoraIndicators().getLast());
	}
	
	protected void __showDeadWall(){
		__showDoraIndicators();
//		println(mWall.toStringDeadWall() + "\n");
	}
	
	protected void __showRoundResult(){
		mRoundTracker.printRoundResult();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void __showExclamation(Exclamation exclamation, int seat){
		
		if (exclamation.isCall())
			println("..." + mRoundTracker.getWindOfSeat(seat) + " Player called " + exclamationToString.get(exclamation).toUpperCase() + " on the tile (" + mRoundTracker.getMostRecentDiscard().toString() + ")");
		else
			println(",,," + mRoundTracker.getWindOfSeat(seat) + " Player declared " + exclamationToString.get(exclamation).toUpperCase());
		
//		//if multiple players called, show if someone got bumped by priority 
//		for (Player p: mPlayerArray)
//			if (p.called() && p != priorityCaller)
//				System.out.println("~~~~~~~~~~" + p.getSeatWind() + " Player tried to call " + p.getCallStatusString() + ", but got bumped by " + priorityCaller.getSeatWind() + "!");
//		System.out.println();
		
		//pause
		Pauser.pauseFor(mSleepTimeExclamation);
	}
	
}
