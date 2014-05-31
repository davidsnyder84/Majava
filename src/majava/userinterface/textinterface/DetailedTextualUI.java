package majava.userinterface.textinterface;


import utility.Pauser;
import majava.Player;
import majava.util.TileList;
import majava.enums.Exclamation;
import majava.tiles.Tile;

public class DetailedTextualUI extends TextualUI{
	
	
	public DetailedTextualUI(){
		super();
	}
	
	
	
	
	
	
	

	protected void __displayEventDiscardedTile(){
		
		//show the human player their hand
		__showHandsOfAllPlayers();
		
		//show the discarded tile and the discarder's pond
		println("\n\n\tTiles left: " + mRoundTracker.getNumTilesLeftInWall());
		println("\t" + mRoundTracker.currentPlayer().getSeatWind() + " Player's discard: ^^^^^" + mRoundTracker.getMostRecentDiscard().toString() + "^^^^^");
		println("\t" + mRoundTracker.currentPlayer().getAsStringPond());
	}
	
	
	
	
	
	protected void __displayEventMadeOpenMeld(){}
	protected void __displayEventDrewTile(){}
	protected void __displayEventMadeOwnKan(){}
	
	
	
	protected void __displayEventNewDoraIndicator(){
		__showDeadWall();
	}

	protected void __displayEventHumanTurnStart(){
		__showPlayerHand(mRoundTracker.currentPlayer());
	}
	
	protected void __displayEventStartOfRound(){
		__showWall();__showDoraIndicators();
	}
	
	protected void __displayEventEndOfRound(){
		__showRoundResult();__showHandsOfAllPlayers();
		if (mSleepTimeExclamation > 0) Pauser.pauseFor(mSleepTimeRoundEnd);
	}
	
	protected void __displayEventPlaceholder(){/*blank*/}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//prints the hand of a player
	protected void __showPlayerHand(Player p){println(p.getAsStringHand());}
	
	protected void __showWall(){println(mWall.toString());}
	
	protected void __showDoraIndicators(){
		TileList t = mWall.getDoraIndicators();
		println("Dora Indicators: " + t.toString() + "\n\n");
	}
	
	protected void __showDeadWall(){
		__showDoraIndicators();
		println(mWall.toStringDeadWall());
	}
	
	protected void __showRoundResult(){
		mRoundTracker.printRoundResult();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void __showExclamation(Exclamation exclamation, int seat){
		
		if (exclamation.isCall())
			println("\n*********************************************************" + 
					"\n**********" + mPTrackers[seat].player.getSeatWind() + " Player called the tile (" + mRoundTracker.getMostRecentDiscard().toString() + ")! " + exclamationToString.get(exclamation) + "!!!**********" + 
					"\n*********************************************************");
		else
			println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
					"\n~~~~~~~~~~~" + mPTrackers[seat].player.getSeatWind() + " Player declared " + exclamationToString.get(exclamation) + "!!!~~~~~~~~~~~" + 
					"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		

//		//if multiple players called, show if someone got bumped by priority 
//		for (Player p: mPlayerArray)
//			if (p.called() && p != priorityCaller)
//				System.out.println("~~~~~~~~~~" + p.getSeatWind() + " Player tried to call " + p.getCallStatusString() + ", but got bumped by " + priorityCaller.getSeatWind() + "!");
//		System.out.println();
		
		//pause
		if (mSleepTimeExclamation > 0) Pauser.pauseFor(mSleepTimeExclamation);
	}
	
	
	
	
	
}
