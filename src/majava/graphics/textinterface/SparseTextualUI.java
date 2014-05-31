package majava.graphics.textinterface;

import java.util.Arrays;

import majava.Player;
import majava.enums.Exclamation;
import majava.tiles.Tile;
import utility.Pauser;

public class SparseTextualUI extends TextualUI{
	
	
	
	public SparseTextualUI(){
		super();
	}
	
	
	
	protected void __displayEventDiscardedTile(){
		
		//show the discarded tile
		println("\t" + mRoundTracker.currentPlayer().getSeatWind().toChar() + " discard: " + mRoundTracker.getMostRecentDiscard().toString());
//		mRoundTracker.currentPlayer().showPond();
	}
	
	
	
	
	
	protected void __displayEventMadeOpenMeld(){}
	protected void __displayEventDrewTile(){
		print("\t" + mRoundTracker.currentPlayer().getSeatWind().toChar() + " draw: " + mRoundTracker.currentPlayer().getTsumoTile());
	}
	protected void __displayEventMadeOwnKan(){}
	
	
	
	protected void __displayEventNewDoraIndicator(){
		__showDeadWall();
	}
	
	protected void __displayEventHumanTurnStart(){
		println();__showPlayerHand(mRoundTracker.whoseTurn());
	}
	
	protected void __displayEventStartOfRound(){
		__showWall(); __showDoraIndicators();
	}
	
	protected void __displayEventEndOfRound(){
		__showRoundResult();__showHandsOfAllPlayers();
		if (mSleepTimeExclamation > 0) Pauser.pauseFor(mSleepTimeRoundEnd);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//prints the hands of a player
	protected void __showPlayerHand(int seatNum){__showPlayerHand(mPTrackers[seatNum].player);}
	protected void __showPlayerHand(Player p){println(p.getAsStringHandCompact());}
	
	protected void __showWall(){println(mWall.toString());}
	protected void __showDoraIndicators(){println(",,,New Dora Indicator: " + mWall.getDoraIndicators().getLast());}
	protected void __showDeadWall(){__showDoraIndicators();}
	
	protected void __showRoundResult(){
		mRoundTracker.printRoundResult();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void __showExclamation(Exclamation exclamation, int seat){
		
		if (exclamation.isCall())
			println("..." + mPTrackers[seat].player.getSeatWind() + " Player called " + exclamationToString.get(exclamation).toUpperCase() + " on the tile (" + mRoundTracker.getMostRecentDiscard().toString() + ")");
		else
			println(",,," + mPTrackers[seat].player.getSeatWind() + " Player declared " + exclamationToString.get(exclamation).toUpperCase());
		
		//pause
		if (mSleepTimeExclamation > 0) Pauser.pauseFor(mSleepTimeExclamation);
	}
	
}
