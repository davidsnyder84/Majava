package majava.userinterface.textinterface;


import java.util.Arrays;
import java.util.List;

import utility.Pauser;
import majava.Meld;
import majava.Player;
import majava.util.TileList;
import majava.util.YakuList;
import majava.yaku.Yaku;
import majava.enums.Exclamation;
import majava.summary.PaymentMap;
import majava.summary.PlayerSummary;
import majava.summary.RoundResultSummary;
import majava.tiles.Tile;

public class DetailedTextualUI extends TextualUI{
	
	
	public DetailedTextualUI(){
		super();
	}
	
	
	
	
	
	
	

	protected void __displayEventDiscardedTile(){
		
		//show the human player their hand
		__showHandsOfAllPlayers();
		
		//show the discarded tile and the discarder's pond
		println("\n\n\tTiles left: " + mRoundEntities.mRoundTracker.getNumTilesLeftInWall());
		println("\t" + mRoundEntities.mRoundTracker.currentPlayer().getSeatWind() + " Player's discard: ^^^^^" + mRoundEntities.mRoundTracker.getMostRecentDiscard().toString() + "^^^^^");
		println("\t" + mRoundEntities.mRoundTracker.currentPlayer().getAsStringPond());
	}
	
	
	
	
	
	protected void __displayEventMadeOpenMeld(){}
	protected void __displayEventDrewTile(){}
	protected void __displayEventMadeOwnKan(){}
	
	
	
	protected void __displayEventNewDoraIndicator(){
		__showDeadWall();
	}

	protected void __displayEventHumanTurnStart(){
		__showPlayerHand(mRoundEntities.mRoundTracker.currentPlayer());
	}
	
	protected void __displayEventStartOfRound(){
		__showWall();__showDoraIndicators();
	}
	
	protected void __displayEventEndOfRound(){
		__showRoundResult();
		__showHandsOfAllPlayers();
		println("\n\n");
		
		if (mSleepTimeRoundEnd > 0) Pauser.pauseFor(mSleepTimeRoundEnd);
	}
	
	protected void __displayEventPlaceholder(){/*blank*/}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//prints the hand of a player
	protected void __showPlayerHand(Player p){println(p.getAsStringHand());}
	
	protected void __showWall(){println(mRoundEntities.mWall.toString());}
	
	protected void __showDoraIndicators(){
		TileList t = mRoundEntities.mWall.getDoraIndicators();
		println("Dora Indicators: " + t.toString() + "\n\n");
	}
	
	protected void __showDeadWall(){
		__showDoraIndicators();
		println(mRoundEntities.mWall.toStringDeadWall());
	}
	
	protected void __showRoundResultOLD(){
		if (!mRoundEntities.mRoundTracker.roundIsOver()) return;
		
		System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
		 					"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~Round over!~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
				 			"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		RoundResultSummary result = mRoundEntities.mRoundTracker.getResultSummary();
		
		String resultStr = "Result: " + mRoundEntities.mRoundTracker.getRoundResultString();
		System.out.println(resultStr);
		
		if (result.isVictory());
//			System.out.println(mRoundResult.getAsStringWinningHand());
	}
	
	protected void __showRoundResult(){
		if (!mRoundEntities.mRoundTracker.roundIsOver()) return;
		
		println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
		 		"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~Round over!~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
				"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		RoundResultSummary result = mRoundEntities.mRoundTracker.getResultSummary();
		
//		String resultStr = "Result: " + mRoundEntities.mRoundTracker.getRoundResultString();
//		System.out.println(resultStr);
		
//		if (result.isVictory()) System.out.println(mRoundResult.getAsStringWinningHand());
//		mRoundEntities.mRoundTracker.printRoundResult();
		
		
		
		
		
		
		
		
		//for all
		String resultLabel = null;
		PaymentMap payments = null;
		//for win
		PlayerSummary winner = null, furikon = null;
		TileList winnerHandTiles = null; List<Meld> winnerMelds = null; Tile winningTile = null;
		YakuList yakuList = null; int yakuWorth = -1; int handScore = -1; 
		
		
		
		//***result label (Player 1 wins!, Draw!, etc)
		resultLabel = result.getAsStringResultType();
		
		
		//***payments per player panel
		payments = result.getPayments();
		
		if (result.isVictory()){
			winner = result.getWinningPlayer();
			furikon = result.getFurikondaPlayer();
			
			//***winning hand/melds panel
			winnerHandTiles = result.getWinnerHandTiles();	
			winnerMelds = result.getWinnerMelds();
			winningTile = result.getWinningTile();
			
			//***panel/list of yaku
			yakuList = new YakuList(Yaku.RIICHI, Yaku.RIICHI_IPPATSU, Yaku.TSUMO, Yaku.DORA);
			yakuWorth = yakuList.totalHan();
			
			//***hand score label
			handScore = payments.get(winner);
		}
		
		
		
		
		System.out.println("Result: " + resultLabel);
		if (result.isVictory()){
			print(result.getAsStringWinType() + "!");
			if (result.isVictoryRon()) print(" (from Player " + (furikon.getPlayerNumber()+1) + ")");
			println();
		}
		
		
		System.out.println("\nPayments:");
		for (PlayerSummary ps: payments){
			print("\tPlayer " + (ps.getPlayerNumber()+1) + " (" + ps.getPlayerName() + ", " + ps.getSeatWind().toChar() + ")... Points:" + ps.getPoints() + " (");
			if (payments.get(ps) > 0) print("+");
			println(payments.get(ps) + ")");
		}
		
		if (result.isVictory()){
			//***winning hand/melds panel
			println("\nWinner's hand: " + winnerHandTiles);
			println("Winner's melds:");
			for (Meld m: winnerMelds) System.out.println("\t" + m);
			println("Winning tile: " + winningTile);
			
			//***panel/list of yaku
			System.out.println("\nList of Yaku:");
			for (Yaku y: yakuList) println("\t" + y + " (" + yakuWorth + ")");
			
			//***hand score label
			println("Hand score: " + handScore);
		}
		
		
		println("\n\n\n");
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void __showExclamation(Exclamation exclamation, int seat){
		
		if (exclamation.isCall())
			println("\n*********************************************************" + 
					"\n**********" + mRoundEntities.mPTrackers[seat].player.getSeatWind() + " Player called the tile (" + mRoundEntities.mRoundTracker.getMostRecentDiscard().toString() + ")! " + exclamationToString.get(exclamation) + "!!!**********" + 
					"\n*********************************************************");
		else
			println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
					"\n~~~~~~~~~~~" + mRoundEntities.mPTrackers[seat].player.getSeatWind() + " Player declared " + exclamationToString.get(exclamation) + "!!!~~~~~~~~~~~" + 
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
