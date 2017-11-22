package majava.userinterface.textinterface;

import java.util.Arrays;
import java.util.List;

import majava.hand.Meld;
import majava.enums.Exclamation;
import majava.player.Player;
import majava.summary.PaymentMap;
import majava.summary.PlayerSummary;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.util.GameTileList;
import majava.util.YakuList;
import majava.yaku.Yaku;
import utility.Pauser;

public class SparseTextualUI extends TextualUI{
	
	
	
	public SparseTextualUI(){
		super();
	}
	
	
	
	protected void __displayEventDiscardedTile(){
		
		//show the discarded tile
		println("\t" + gameState.seatWindOfPlayer(roundTracker.whoseTurn()).toChar() + " discard: " + roundTracker.getMostRecentDiscard());
//		mRoundTracker.currentPlayer().showPond();
	}
	
	
	
	
	
	protected void __displayEventMadeOpenMeld(){}
	protected void __displayEventDrewTile(){
		print("\t" + gameState.seatWindOfPlayer(roundTracker.whoseTurn()).toChar() + " draw: " + gameState.getTsumoTileFor(roundTracker.whoseTurn()));
	}
	protected void __displayEventMadeOwnKan(){}
	
	
	
	protected void __displayEventNewDoraIndicator(){
		__showDeadWall();
	}
	
	protected void __displayEventHumanTurnStart(){
		println();__showPlayerHand(roundTracker.whoseTurn());
	}
	
	protected void __displayEventStartOfRound(){
		__showWall(); __showDoraIndicators();
	}
	
	protected void __displayEventEndOfRound(){
		__showRoundResult();
		__showHandsOfAllPlayers();
		println("\n\n");
		
		if (mSleepTimeRoundEnd > 0) Pauser.pauseFor(mSleepTimeRoundEnd);
	}

	protected void __displayEventPlaceholder(){/*blank*/}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//prints the hands of a player
	protected void __showPlayerHand(int seatNum){
		println(gameState.getHandAsStringCompact(seatNum));
	}
	
	protected void __showWall(){println(gameState.wallToString());}
	protected void __showDoraIndicators(){println(",,,Dora Indicators: " + roundTracker.getDoraIndicators());}
	protected void __showDeadWall(){__showDoraIndicators();}
	
	protected void __showRoundResult(){
		if (!roundTracker.roundIsOver()) return;
		
		println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
		 		"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~Round over!~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
				"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		RoundResultSummary resum = roundTracker.getResultSummary();
		
//		String resultStr = "Result: " + mRoundEntities.mRoundTracker.getRoundResultString();
//		System.out.println(resultStr);
		
//		if (result.isVictory()) System.out.println(mRoundResult.getAsStringWinningHand());
//		mRoundEntities.mRoundTracker.printRoundResult();
		
		
		
		
		
		
		
		
		//for all
		String resultLabel = null;
		PaymentMap payments = null;
		//for win
		PlayerSummary winner = null, furikon = null;
		GameTileList winnerHandTiles = null; List<Meld> winnerMelds = null; GameTile winningTile = null;
		YakuList yakuList = null; int yakuWorth = -1; int handScore = -1; 
		
		
		
		//***result label (Player 1 wins!, Draw!, etc)
		resultLabel = resum.getAsStringResultType();
		
		
		//***payments per player panel
		payments = resum.getPayments();
		
		if (resum.isVictory()){
			winner = resum.getWinningPlayer();
			furikon = resum.getFurikondaPlayer();
			
			//***winning hand/melds panel
			winnerHandTiles = resum.getWinnerHandTiles();	
			winnerMelds = resum.getWinnerMelds();
			winningTile = resum.getWinningTile();
			
			//***panel/list of yaku
			yakuList = new YakuList(Yaku.RIICHI, Yaku.RIICHI_IPPATSU, Yaku.TSUMO, Yaku.DORA);
			yakuWorth = yakuList.totalHan();
			
			//***hand score label
			handScore = payments.get(winner);
		}
		
		
		
		
		System.out.println("Result: " + resultLabel);
		if (resum.isVictory()){
			print(resum.getAsStringWinType() + "!");
			if (resum.isVictoryRon()) print(" (from Player " + (furikon.getPlayerNumber()+1) + ")");
			println();
		}
		
		
		System.out.println("\nPayments:");
		for (PlayerSummary ps: payments){
			print("\tPlayer " + (ps.getPlayerNumber()+1) + " (" + ps.getPlayerName() + ", " + ps.getSeatWind().toChar() + ")... Points:" + ps.getPoints() + " (");
			if (payments.get(ps) > 0) print("+");
			println(payments.get(ps) + ")");
		}
		
		if (resum.isVictory()){
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
	
	
	
	
	
	
	
	
	
	
	
	//info needed: seat wind of caller, most recent discard
	@Override
	protected void __showExclamation(Exclamation exclamation, int seat){
		
		if (exclamation.isCall())
			println("..." + gameState.seatWindOfPlayer(seat) + " Player called " + exclamation.toString().toUpperCase() + " on the tile (" + roundTracker.getMostRecentDiscard() + ")");
//			println("..." + roundTracker.getWindOfSeat(seat) + " Player called " + exclamation.toString().toUpperCase() + " on the tile (" + roundTracker.getMostRecentDiscard() + ")");
		else
			println(",,," + gameState.seatWindOfPlayer(seat) + " Player declared " + exclamation.toString().toUpperCase());
		
		//pause
		if (mSleepTimeExclamation > 0) Pauser.pauseFor(mSleepTimeExclamation);
	}
	
}
