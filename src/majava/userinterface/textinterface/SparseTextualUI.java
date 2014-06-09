package majava.userinterface.textinterface;

import java.util.Arrays;
import java.util.List;

import majava.Meld;
import majava.Player;
import majava.enums.Exclamation;
import majava.summary.PaymentMap;
import majava.summary.PlayerSummary;
import majava.summary.RoundResultSummary;
import majava.tiles.Tile;
import majava.util.TileList;
import majava.util.YakuList;
import majava.yaku.Yaku;
import utility.Pauser;

public class SparseTextualUI extends TextualUI{
	
	
	
	public SparseTextualUI(){
		super();
	}
	
	
	
	protected void __displayEventDiscardedTile(){
		
		//show the discarded tile
		println("\t" + mRoundEntities.mRoundTracker.currentPlayer().getSeatWind().toChar() + " discard: " + mRoundEntities.mRoundTracker.getMostRecentDiscard().toString());
//		mRoundTracker.currentPlayer().showPond();
	}
	
	
	
	
	
	protected void __displayEventMadeOpenMeld(){}
	protected void __displayEventDrewTile(){
		print("\t" + mRoundEntities.mRoundTracker.currentPlayer().getSeatWind().toChar() + " draw: " + mRoundEntities.mRoundTracker.currentPlayer().getTsumoTile());
	}
	protected void __displayEventMadeOwnKan(){}
	
	
	
	protected void __displayEventNewDoraIndicator(){
		__showDeadWall();
	}
	
	protected void __displayEventHumanTurnStart(){
		println();__showPlayerHand(mRoundEntities.mRoundTracker.whoseTurn());
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
	protected void __showPlayerHand(int seatNum){__showPlayerHand(mRoundEntities.mPTrackers[seatNum].player);}
	protected void __showPlayerHand(Player p){println(p.getAsStringHandCompact());}
	
	protected void __showWall(){println(mRoundEntities.mWall.toString());}
	protected void __showDoraIndicators(){println(",,,New Dora Indicator: " + mRoundEntities.mWall.getDoraIndicators().getLast());}
	protected void __showDeadWall(){__showDoraIndicators();}
	
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
			println("..." + mRoundEntities.mPTrackers[seat].player.getSeatWind() + " Player called " + exclamationToString.get(exclamation).toUpperCase() + " on the tile (" + mRoundEntities.mRoundTracker.getMostRecentDiscard().toString() + ")");
		else
			println(",,," + mRoundEntities.mPTrackers[seat].player.getSeatWind() + " Player declared " + exclamationToString.get(exclamation).toUpperCase());
		
		//pause
		if (mSleepTimeExclamation > 0) Pauser.pauseFor(mSleepTimeExclamation);
	}
	
}
