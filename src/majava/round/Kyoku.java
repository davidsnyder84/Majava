package majava.round;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import utility.Pauser;

import majava.Pond;
import majava.util.GTL;
import majava.util.PlayerList;
import majava.wall.WallDealer;
import majava.wall.Wall;
import majava.player.Player;
import majava.pond.RiverWalker;
import majava.summary.PaymentMap;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.tiles.PondTile;
import majava.control.testcode.GameSimulation;
import majava.control.testcode.WallDemoer;
import majava.events.GameEventBroadcaster;
import majava.enums.CallType;
import majava.enums.GameEventType;
import majava.enums.KyokuEvent;
import majava.enums.Wind;
import majava.enums.Exclamation;



//represents a single round (ˆê‹Ç) of mahjong
public class Kyoku{
	private static final int NUM_PLAYERS = 4;
	private static final Wind DEFAULT_ROUND_WIND = Wind.EAST;
	private static final int DEFAULT_ROUND_NUM = 1 , DEFAULT_ROUND_BONUS_NUM = 0;
	
	//for debug use
//	private static final boolean DEBUG_LOAD_DEBUG_WALL = true;
	private static final boolean DEBUG_LOAD_DEBUG_WALL = false;
	
//	private static final boolean DEBUG_EXHAUSTED_WALL = true;
	private static final boolean DEBUG_EXHAUSTED_WALL = false;
	
	
	private final Wind roundWind;
	private final int roundNumber, roundBonusNumber;
	
	private final PlayerList players;	
	private final Wall wall;
	
	
//	private final KyokuEvent mostRecentEvent;	//////////////////////////////////////////////this way of doing it is fine, use this (but make it something different)
	private final GameEventType mostRecentEvent;	//////////////////////////////////////////////this way of doing it is fine, use this (but make it something different)
	private final Wind whoseTurnWind = null; //only useful for GUIs
	///^wrap these together?
	
//	private final RoundResult roundResult;
	
	
//	private final Kyoku previousState //??????????????????????????????????????????????????????????????
	
	
	//builder constructor
	private static final boolean BUILDER = true;
	private Kyoku(PlayerList playerArray, Wall w, Wind roundWindToSet, int roundNum, int roundBonusNum, GameEventType lastEvent, boolean BLDR){
		roundWind = roundWindToSet; roundNumber = roundNum; roundBonusNumber = roundBonusNum;
		players = playerArray;
		wall = w;
		
		mostRecentEvent = lastEvent;
	}
	
	//initialization constructors
	public Kyoku(PlayerList playerArray, Wind roundWindToSet, int roundNum, int roundBonusNum){
		roundWind = roundWindToSet; roundNumber = roundNum; roundBonusNumber = roundBonusNum;
		players = playerArray;
		
		wall = new Wall().shuffle();
		mostRecentEvent = GameEventType.START_OF_ROUND;
	}
	public Kyoku(PlayerList playerArray, Wind roundWindToSet, int roundNum){this(playerArray, roundWindToSet, roundNum, DEFAULT_ROUND_BONUS_NUM);}
	public Kyoku(PlayerList playerArray){
		this(playerArray, DEFAULT_ROUND_WIND, DEFAULT_ROUND_NUM);
	}
	public Kyoku(){
		this(new PlayerList(new Player().setSeatWindEast(), new Player().setSeatWindSouth(), new Player().setSeatWindWest(), new Player().setSeatWindNorth()));
		
		for (Player p: players)
			p.setControllerComputer();
//			p.setControllerHuman();
		
	}
	
	//builders
	public Kyoku withWall(Wall newWall){return new Kyoku(players, newWall, roundWind, roundNumber, roundBonusNumber, mostRecentEvent, BUILDER);}
	
	public Kyoku withPlayers(PlayerList newPlayerlist){return new Kyoku(newPlayerlist, wall, roundWind, roundNumber, roundBonusNumber, mostRecentEvent, BUILDER);}
	public Kyoku withUpdatedPlayer(Player newPlayer){
		return this.withPlayers(players.updatePlayer(newPlayer));
	}
	
	public Kyoku withEvent(GameEventType newEvent){return new Kyoku(players, wall, roundWind, roundNumber, roundBonusNumber, newEvent, BUILDER);}
	
	
	
	
	
	//if your hand is full, that means it's your turn and you need to discard something (or declare kan/tsumo)
	public Wind seatToAct(){
		Player fullHandedPlayer = players.playerWhoHasFullHand();
		return fullHandedPlayer.getSeatWind();
	}
	public Player turnPlayer(){return players.get(seatToAct());}
	
	
	public Player priorityCaller(){
		Player priorityCaller = Player.NOBODY;
		GameTile lastDiscard = lastDiscard();
		
		if (players.someoneCalledChi(lastDiscard)) priorityCaller = players.callerChi(lastDiscard);
		if (players.someoneCalledPonKan(lastDiscard)) priorityCaller = players.callerPonKan(lastDiscard);
		if (players.someoneCalledRON(lastDiscard)) priorityCaller = players.callerRON(lastDiscard);
		
		return priorityCaller;
	}
	public Wind seatPriorityCaller(){return priorityCaller().getSeatWind();}
	
	public PondTile lastDiscard(){
		RiverWalker walker = new RiverWalker(getPonds());
		return walker.lastDiscard();
	}
	public Player lastDiscarder(){
		return players.get(lastDiscard().getOrignalOwner());
	}
	
	
	//When is everyone is 13 13 13 13?: right after discard / after call but before meldmake
	public Wind seatToDrawNext(){
		//if last discard was called, jump to the caller
		if (lastDiscard().wasCalled())
			return lastDiscard().getCaller();
		
		//if East makes a kan on the first turn, he will need to draw but lastDiscard wont exist
		if (players.seatE().pondIsEmpty())
			return Wind.EAST;
		
		//otherwise, move to the shimocha of the last person who discarded
		return lastDiscard().getOrignalOwner().shimochaWind();
	}
	
	
	
	public Wind getRoundWind(){return roundWind;}
	public int getRoundNum(){return roundNumber;}
	public int getRoundBonusNum(){return roundBonusNumber;}
	
	public PlayerList getPlayers(){return players;}
	public Wall getWall(){return wall;}
	
	public Pond[] getPonds(){return players.getPonds();}
	
	public boolean someoneHasFullHand(){return players.someoneHasFullHand();}
	public boolean someoneCalledLastDiscard(){return players.someoneCalled(lastDiscard());}
	public boolean someoneNeedsToDraw(){return !someoneHasFullHand();}////////////////
	public boolean someoneNeedsDrawRinshan(){return players.someoneNeedsDrawRinshan();}
	
	
	public boolean hasntStartedYet(){
		return (wall.currentPosition() == 0);
//		for (Player p : players) if (p.handSize() == 0) return true;
//		return false;
	}
	
	
	public PlayerList letPlayersReactToDiscard(){
		PlayerList playersWithReactions = players;
		for (Player p : players.allPlayersExcept(lastDiscarder()))
			if (p.ableToCallTile(lastDiscard())){
				Player reactedPlayer = p.reactToDiscard(lastDiscard());
				playersWithReactions = playersWithReactions.set(reactedPlayer);
			}
		
		return playersWithReactions;
	}
	
	
	
	
	public Kyoku next(){
//		System.out.println("==============================================================================================\n" + this.toString() + "==============================================================================================\n\n");
		
		if (isOver())
			return this;
		
		if (hasntStartedYet())
			return dealStartingHands();
		
		
		
		if (someoneHasFullHand()){
			if (turnPlayer().hasChosenTurnAction())
				return executeTurnAction();
			else
				return askTurnAction();
		}
		
		
		
		if (someoneCalledLastDiscard()){
			return letPriorityCallerMakeMeld();
		}
		
		
		
		PlayerList playersWithReactions = letPlayersReactToDiscard();
		if (playersWithReactions.someoneCalled(lastDiscard()))
			return this.withPlayers(playersWithReactions);
		
		
		
		return letPlayerDraw();
	}
	
	
	
	
	
	
	
	public Kyoku letPriorityCallerMakeMeld(){
		GameTile calledTile = lastDiscard();
		Player priorityCaller = priorityCaller();
		Player discarder = lastDiscarder();
		
		//note: ron will never come here
		
		
		Player discarderWithPondTileRemoved = discarder.removeTileFromPond(priorityCaller.getSeatWind());
		Player priorityCallerWithMeldMade = priorityCaller.makeMeld(calledTile);
		
		return this.withUpdatedPlayer(discarderWithPondTileRemoved).withUpdatedPlayer(priorityCallerWithMeldMade);
	}
	
	
	
	private Kyoku askTurnAction(){
		Player p = turnPlayer();
		Player pWithTurnActionDecided = p.decideTurnAction();
		Kyoku thisWithAskedTurnAction = this.withUpdatedPlayer(pWithTurnActionDecided);
		
		if (p.turnActionMadeKan())
			; //attach some kind of event
		
		if (p.turnActionCalledTsumo())
			;
		
		return thisWithAskedTurnAction;
	}
	private Kyoku executeTurnAction(){
		Player p = turnPlayer();
		Player pAfterExecutingTurnAction = p.doTurnAction();
		Kyoku thisAfterExecutingTurnAction = this.withUpdatedPlayer(pAfterExecutingTurnAction);
		
		
		return thisAfterExecutingTurnAction;
	}
	
	
	
	
	private Kyoku letPlayerDraw(){
		Player p = players.get(seatToDrawNext());
//		if (!p.needsDraw()) return this; //if we're here, p definitely needs to draw, so this check shouldn't be necessary
		
		Wall wallAfterDraw = wall;
		
		GameTile drawnTile = null;
		if (p.needsDrawRinshan()){
			drawnTile = wall.nextDeadWallTile();
			wallAfterDraw = wall.removeNextDeadWallTile();
			//with with new dora indicators event? or does wall know how to figure that out on its own?
		}
		else{
			drawnTile = wall.nextTile();
			wallAfterDraw = wall.removeNextTile();
		}
		
		
		Player pWithDrawnTile = p.addTileToHand(drawnTile);
		
		return this.withUpdatedPlayer(pWithDrawnTile).withWall(wallAfterDraw);
	}
	
	
	
	
	
	
	private Kyoku dealStartingHands(){
		Wall wallToDealFrom = wall;
		
		//DEBUG
//		if (DEBUG_EXHAUSTED_WALL) wallToDealFrom = WallDemoer.ExhaustedWall();
		if (DEBUG_EXHAUSTED_WALL) wallToDealFrom = WallDemoer.ExhaustedWall(wallToDealFrom);
		if (DEBUG_LOAD_DEBUG_WALL) wallToDealFrom = WallDemoer.SpecialDebugWall(wallToDealFrom);
		
		WallDealer dealer = new WallDealer(wallToDealFrom);
		Wall dealtWall = dealer.wallWithStartingHandsRemoved();
		
		Player peWithStartingHand = players.seatE().giveStartingHand(dealer.startingHandEast());
		Player psWithStartingHand = players.seatS().giveStartingHand(dealer.startingHandSouth());
		Player pwWithStartingHand = players.seatW().giveStartingHand(dealer.startingHandWest());
		Player pnWithStartingHand = players.seatN().giveStartingHand(dealer.startingHandNorth());
		
		
//		PlayerList playersWithStartingHands = new PlayerList(peWithStartingHand, psWithStartingHand, pwWithStartingHand, pnWithStartingHand);
		PlayerList playersWithStartingHands = players.set(peWithStartingHand).set(psWithStartingHand).set(pwWithStartingHand).set(pnWithStartingHand);
		return this.withWall(dealtWall).withPlayers(playersWithStartingHands);
//		return this.withWall(dealtWall).withPlayers(playersWithStartingHands).postEvent(something);
	}
	
	
	
	
	
	
	
	public boolean wallIsEmpty(){return wall.isEmpty();}
	
	
	
	//round end methods
	public boolean isOver(){
		return roundEndChecker().isOver();
	}
	public RoundEndChecker roundEndChecker(){return new RoundEndChecker(this);}
	
	public boolean someoneDeclaredVictory(){return roundEndChecker().someoneDeclaredVictory();}
	public boolean someoneDeclaredTsumo(){return roundEndChecker().someoneDeclaredTsumo();}
	public boolean someoneDeclaredRon(){return roundEndChecker().someoneDeclaredRon();}
	
	public boolean isRyuukyoku(){return roundEndChecker().isRyuukyoku();}
	public boolean isRyuukyokuHowanpai(){return roundEndChecker().isRyuukyokuHowanpai();}
	public boolean isRyuukyoku4Riichi(){return roundEndChecker().isRyuukyoku4Riichi();}
	public boolean isRyuukyokuKyuushuu(){return roundEndChecker().isRyuukyokuKyuushuu();}
	public boolean isRyuukyoku4Wind(){return roundEndChecker().isRyuukyoku4Wind();}
	public boolean isRyuukyoku4Kan(){return roundEndChecker().isRyuukyoku4Kan();}
	
	public boolean qualifiesForRenchan(){return roundEndChecker().qualifiesForRenchan();}
	
	
	public int numKansMade(){
		int numKans = 0;
		for (Player p: players) numKans += p.getNumKansMade();
		return numKans;
	}
	
	
	//only valid immediately after making / before rinshan drawing
	public Player playerWhoMadeTheMostRecentKan(){
		for (Player p : players) if (p.needsDrawRinshan()) return p;
		return Player.NOBODY;
	}
	
	
	
	
	//===================================================================================================================================
	//===================================================================================================================================
	//===================================================================================================================================
	//===================================================================================================================================
	//===================================================================================================================================
	//===================================================================================================================================
//	
//	
//	//round result methods
//	public String getWinningHandString(){return roundResult.getAsStringWinningHand();}
//	public RoundResultSummary getResultSummary(){return roundResult.getSummary();}
//	public String getRoundResultString(){return roundResult.toString();}
//	
//	
//	
//	private void handleRoundEnd(){
//		doPointPayments();
//		
//		announceEndOfRoundEvent();
//	}
//	private void doPointPayments(){
//		Scorer scorer = new Scorer(roundResult, players);
//		PaymentMap payments = scorer.getPaymentMap();
//		
//		//carry out payments
//		for (Player p: players)
//			p.pointsIncrease(payments.get(p));
//		
//		roundResult.recordPayments(payments);
//		roundResult.recordYaku(scorer.getYakuOfWinningHand());
//		
//		System.out.println(scorer.getYakuOfWinningHand());
//	}
//	
//	
//	
//	
//	private void setResultVictory(Player winner){
//		GameTile winningTile = null;
//		GTL winningHandTiles = winner.getHand().getTiles();
//		
//		if (winner == currentPlayer()){
//			roundResult.setVictoryTsumo(winner);			
//			winningTile = winner.getTsumoTile();
//			winningHandTiles.removeLast();
//		}
//		else{ 
//			roundResult.setVictoryRon(winner, currentPlayer());			
//			winningTile = mostRecentDiscard();
//		}
//		roundResult.setWinningHand(winningHandTiles, winner.getHand().getMelds(), winningTile);
//	}
//	
//	
//	
	
	
	
////	//event announce methods
////	private void announceEvent(GameplayEvent event){
////		gameEventBroadcaster.postNewEvent(event, gameState);
////	}
////	private void announceCallEventFrom(Player caller){
////		announceEvent(GameplayEvent.calledTileEvent(caller.getCallStatusExclamation(), caller, mostRecentDiscard(), whoseTurnNumber()));
////	}
////	private void announceSelfKanEvent(Player fromPlayer){
////		announceEvent(GameplayEvent.declaredOwnKanEvent(fromPlayer));
////		announceEvent(GameplayEvent.madeOwnKanEvent());
////	}
////	private void announceTsumoEvent(Player fromPlayer){announceEvent(GameplayEvent.declaredTsumoEvent(fromPlayer));}
////	private void announceHumanTurnStartEvent(Player p){announceEvent(GameplayEvent.humanPlayerTurnStartEvent(p));}
////	private void announceHumanReactionChanceEvent(Player p){announceEvent(GameplayEvent.humanReactionEvent(p, mostRecentDiscard(), whoseTurnNumber()));}
////	private void announceEndOfRoundEvent(){announceEvent(GameplayEvent.endOfRoundEvent());}
//	
//	
//	
//	//event announce methods
////	private void announceEvent(GameplayEvent event){
////		gameEventBroadcaster.postNewEvent(event, gameState);
////	}
//	private void announceCallEventFrom(Player caller){
//		GameplayEvent.calledTileEvent(); //GameplayEvent.calledTileEvent(caller.getCallStatusExclamation(), caller, mostRecentDiscard(), whoseTurnNumber());
//	}
//	private void announceSelfKanEvent(Player fromPlayer){
//		GameplayEvent.declaredOwnKanEvent(); //GameplayEvent.declaredOwnKanEvent(fromPlayer); ///!!!!!!!!!! <- this is DECLARE slef kan. Declare is done by someone with a full hand, so you can infer who it is
//		GameplayEvent.madeOwnKanEvent();
//		//if I could figure out how to determine WHO made the most recent kan, I could make 100% of these be enums
//	}
//	private void announceTsumoEvent(Player fromPlayer){
//		GameplayEvent.declaredTsumoEvent();  //GameplayEvent.declaredTsumoEvent(fromPlayer); //can infer tsumo player from roundstate (who has full hand?)
//	}
////	private void announceHumanTurnStartEvent(Player p){///////////////what does this mean?
////		GameplayEvent.humanPlayerTurnStartEvent(p);
////	}
////	private void announceHumanReactionChanceEvent(Player p){////////////this might not be necessary
////		GameplayEvent.humanReactionEvent(p);
////	}	
//	//**********************************************enum
//	private void announceEndOfRoundEvent(){
//		GameplayEvent.endOfRoundEvent();
//	}
//	//**********************************************enum
//	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^use Wind instead of Player
//	
//				/*
//				//(from GameplayEvent//////////////////) 
//				//factory methods
//				public static final GameplayEvent startOfRoundEvent(){return new GameplayEvent(START_OF_ROUND);}
//				public static final GameplayEvent drewTileEvent(){return new GameplayEvent(DREW_TILE);}
//				public static final GameplayEvent newDoraIndicatorEvent(){return new GameplayEvent(NEW_DORA_INDICATOR);}
//				public static final GameplayEvent discardedTileEvent(){return new GameplayEvent(DISCARDED_TILE);}
//				public static final GameplayEvent madeOpenMeldEvent(){return new GameplayEvent(MADE_OPEN_MELD);}
//				public static final GameplayEvent madeAnkanEvent(){return new GameplayEvent(MADE_ANKAN);}
//				public static final GameplayEvent madeMinkanEvent(){return new GameplayEvent(MADE_MINKAN);}
//				public static final GameplayEvent madeOwnKanEvent(){return new GameplayEvent(MADE_OWN_KAN);}
//				public static final GameplayEvent endOfRoundEvent(){return new GameplayEvent(END_OF_ROUND);}
//			
//				
//			//		public static final GameplayEvent calledTileEvent(Exclamation exclaim, Player caller, GameTile tile, int whoseTurnNumber){
//				public static final GameplayEvent calledTileEvent(){
//					GameplayEvent event = new GameplayEvent(CALLED_TILE);
//			//			event.setExclamation(exclaim);
//			//			event.packInfo(caller, tile, whoseTurnNumber);
//					return event;	
//				}
//				public static final GameplayEvent declaredRiichiEvent(){return new GameplayEvent(DECLARED_RIICHI);}
//				
//			//		public static final GameplayEvent declaredOwnKanEvent(Player p){
//				public static final GameplayEvent declaredOwnKanEvent(){
//					GameplayEvent event = new GameplayEvent(DECLARED_OWN_KAN);
//			//			event.setExclamation(Exclamation.OWN_KAN);
//			//			event.packInfo(p);
//					return event;
//				}
//				public static final GameplayEvent declaredTsumoEvent(){
//					GameplayEvent event = new GameplayEvent(DECLARED_TSUMO);
//			//			event.setExclamation(Exclamation.TSUMO);
//			//			event.packInfo(p);
//					return event;
//				}
//			
//				public static final GameplayEvent humanPlayerTurnStartEvent(Player p){
//					GameplayEvent event = new GameplayEvent(HUMAN_PLAYER_TURN_START);
//					event.packInfo(p);
//					return event;
//				}
//				
//			//		public static final GameplayEvent humanReactionEvent(Player p, GameTile tile, int whoseTurnNumber){
//				public static final GameplayEvent humanReactionEvent(Player p){
//					GameplayEvent event = new GameplayEvent(HUMAN_PLAYER_REACTION_START);
//			//			event.packInfo(p, tile, whoseTurnNumber);
//					return event;
//				}
//				public static final GameplayEvent unknownEvent(){return new GameplayEvent(UNKNOWN);}
//				public static final GameplayEvent playerTurnStartEvent(){return new GameplayEvent(PLAYER_TURN_START);}
//				public static final GameplayEvent endingEvent(){return new GameplayEvent(END);}
//				public static final GameplayEvent startingEvent(){return new GameplayEvent(START);}
//				*/
	
	
	
	
	
	
	
	
	
	
	public GameEventType getMostRecentEvent(){
		return mostRecentEvent;
	}
	
	
	
	public String getRoundResultString(){return "roundResult.toString()";}///////////////////////////
	public RoundResultSummary getResultSummary(){return null;}//return "roundResult.getSummary()"///////////////////////
	
	
	public String toString(){
		String str = "";
		str += wall.toString() + "\n";
		str += "------------------------------------------\n";
		
		for (Player p: players){
			str += p.toString() + "\n";
			str += p.getHand().toString() + "\n\n";
			str += p.getPond().toString(); 
			str += "\n\n";
		}
		
//		str += diag();
		
		return str;
	}
	private String diag(){
		String str = "\n*****\n";
		str += mostRecentEvent.toString();
//		str += players.size();
//		str += players.size();
		return str;
	}
	
	public static void println(String prints){System.out.println(prints);}public static void println(){System.out.println("");}public static void println(int prints){System.out.println(prints+"");} public static void waitt(){(new Scanner(System.in)).next();}
}
