package majava.userinterface.textinterface;

import java.util.Arrays;
import java.util.List;

import majava.hand.Meld;
import majava.enums.Exclamation;
import majava.events.GameplayEvent;
import majava.player.Player;
import majava.summary.PaymentMap;
import majava.summary.PlayerSummary;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.util.GTL;
import majava.util.YakuList;
import majava.yaku.Yaku;
import utility.Pauser;

public class SparseTextualUI extends TextualUI{
	
	
	
	public SparseTextualUI(){
		super();
	}
	
	
	
	protected void displayEventDiscardedTile(){
		
		//show the discarded tile
		println("\t" + gameState.seatWindOfPlayer(gameState.whoseTurn()).toChar() + " discard: " + gameState.getMostRecentDiscard());
//		mRoundTracker.currentPlayer().showPond();
	}
	
	
	
	
	
	protected void displayEventMadeOpenMeld(){}
	protected void displayEventDrewTile(){
		print("\t" + gameState.seatWindOfPlayer(gameState.whoseTurn()).toChar() + " draw: " + gameState.getTsumoTileFor(gameState.whoseTurn()));
	}
	protected void displayEventMadeOwnKan(){}
	protected void displayEventHumanReactionStart() {}
	
	
	
	protected void displayEventNewDoraIndicator(){
		__showDeadWall();
	}
	
	protected void displayEventHumanTurnStart(){
		println();__showPlayerHand(gameState.whoseTurn());
	}
	
	protected void displayEventStartOfRound(){
		showAllPlayerNames();
		__showWall();
		__showDoraIndicators();
	}
	
	protected void displayEventEndOfRound(){
		__showRoundResult();
		__showHandsOfAllPlayers();
		println("\n\n");
		
		if (sleepTimeRoundEnd > 0) Pauser.pauseFor(sleepTimeRoundEnd);
	}

	protected void displayEventPlayerTurnStart(){/*blank*/}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//prints the hands of a player
	protected void __showPlayerHand(int seatNum){
		println(gameState.getHandAsStringCompact(seatNum));
	}
	
	protected void __showWall(){println(gameState.wallToString());}
	protected void __showDoraIndicators(){println(",,,Dora Indicators: " + gameState.getDoraIndicators());}
	protected void __showDeadWall(){__showDoraIndicators();}
	
	protected void __showRoundResult(){
		if (!gameState.roundIsOver()) return;
		
		println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
		 		"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~Round over!~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + 
				"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		RoundResultSummary roundResultSummary = gameState.getResultSummary();
		
//		String resultStr = "Result: " + mRoundEntities.mRoundTracker.getRoundResultString();
//		System.out.println(resultStr);
		
//		if (result.isVictory()) System.out.println(mRoundResult.getAsStringWinningHand());
//		mRoundEntities.mRoundTracker.printRoundResult();
		
		
		
		
		
		
		
		
		//for all
		String resultLabel = null;
		PaymentMap payments = null;
		//for win
		PlayerSummary winner = null, furikon = null;
		GTL winnerHandTiles = null; List<Meld> winnerMelds = null; GameTile winningTile = null;
		YakuList yakuList = null; int yakuWorth = -1; int handScore = -1; 
		
		
		
		//***result label (Player 1 wins!, Draw!, etc)
		resultLabel = roundResultSummary.getAsStringResultType();
		
		
		//***payments per player panel
		payments = roundResultSummary.getPayments();
		
		if (roundResultSummary.isVictory()){
			winner = roundResultSummary.getWinningPlayer();
			furikon = roundResultSummary.getFurikondaPlayer();
			
			//***winning hand/melds panel
			winnerHandTiles = roundResultSummary.getWinnerHandTiles();	
			winnerMelds = roundResultSummary.getWinnerMelds();
			winningTile = roundResultSummary.getWinningTile();
			
			//***panel/list of yaku
			yakuList = roundResultSummary.getYakuOfWinner();
			yakuWorth = yakuList.totalHan();
			
			//***hand score label
			handScore = payments.get(winner);
		}
		
		
		
		
		System.out.println("Result: " + resultLabel);
		if (roundResultSummary.isVictory()){
			print(roundResultSummary.getAsStringWinType() + "!");
			if (roundResultSummary.isVictoryRon()) print(" (from Player " + (furikon.getPlayerNumber()+1) + ")");
			println();
		}
		
		
		System.out.println("\nPayments:");
		for (PlayerSummary ps: payments){
			print("\tPlayer " + (ps.getPlayerNumber()+1) + " (" + ps.getPlayerName() + ", " + ps.getSeatWind().toChar() + ")... Points:" + ps.getPoints() + " (");
			if (payments.get(ps) > 0) print("+");
			println(payments.get(ps) + ")");
		}
		
		if (roundResultSummary.isVictory()){
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
	protected void showExclamation(Exclamation exclamation, int seat){
		
		if (exclamation.isCall())
			println("..." + gameState.seatWindOfPlayer(seat) + " Player called " + exclamation.toString().toUpperCase() + " on the tile (" + gameState.getMostRecentDiscard() + ")");
//			println("..." + roundTracker.getWindOfSeat(seat) + " Player called " + exclamation.toString().toUpperCase() + " on the tile (" + roundTracker.getMostRecentDiscard() + ")");
		else
			println(",,," + gameState.seatWindOfPlayer(seat) + " Player declared " + exclamation.toString().toUpperCase());
		
		//pause
		if (sleepTimeExclamation > 0) Pauser.pauseFor(sleepTimeExclamation);
	}
	
}
