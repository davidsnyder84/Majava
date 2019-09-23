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
import majava.summary.StateOfGame;
import majava.tiles.GameTile;
import majava.tiles.PondTile;
import majava.control.testcode.GameSimulation;
import majava.control.testcode.WallDemoer;
import majava.events.GameEventBroadcaster;
import majava.events.GameplayEvent;
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
	
	
	private final KyokuEvent mostRecentEvent;
	
//	private final StateOfGame gameState;
//	private final GameEventBroadcaster gameEventBroadcaster;
	
//	private final RoundTracker roundTracker;
//	private final RoundResult roundResult;
	
	
//	private final Kyoku previousState //??????????????????????????????????????????????????????????????
//	RoundDriver + KyokuState
	
	
	//builder constructor
	private static final boolean BUILDER = true;
	private Kyoku(PlayerList playerArray, Wall w, Wind roundWindToSet, int roundNum, int roundBonusNum, KyokuEvent lastEvent, boolean BLDR){
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
		mostRecentEvent = KyokuEvent.initEvent();
	}
	public Kyoku(PlayerList playerArray, Wind roundWindToSet, int roundNum){this(playerArray, roundWindToSet, roundNum, DEFAULT_ROUND_BONUS_NUM);}
	public Kyoku(PlayerList playerArray){
		this(playerArray, DEFAULT_ROUND_WIND, DEFAULT_ROUND_NUM);
	}
	public Kyoku(){
//		this(new PlayerList(new Player(), new Player(), new Player(), new Player()));
		this(new PlayerList(new Player().setSeatWindEast(), new Player().setSeatWindSouth(), new Player().setSeatWindWest(), new Player().setSeatWindNorth()));
		
		for (Player p: players)
			p.setControllerHuman();
		
	}
	
	//builders
	public Kyoku withWall(Wall newWall){return new Kyoku(players, newWall, roundWind, roundNumber, roundBonusNumber, mostRecentEvent, BUILDER);}
	
	public Kyoku withPlayers(PlayerList newPlayerlist){return new Kyoku(newPlayerlist, wall, roundWind, roundNumber, roundBonusNumber, mostRecentEvent, BUILDER);}
	public Kyoku withSetPlayer(Player oldPlayer, Player newPlayer){return this.withPlayers(players.set(oldPlayer, newPlayer));}
	
	public Kyoku withEvent(KyokuEvent newEvent){return new Kyoku(players, wall, roundWind, roundNumber, roundBonusNumber, newEvent, BUILDER);}
	
	
	
	
	
	
	
	
	//if your hand is full, that means it's your turn and you need to discard something (or declare kan/tsumo)
	public Wind seatToAct(){
		Player fullHandedPlayer = players.playerWhoHasFullHand();
		return fullHandedPlayer.getSeatWind();
	}
	
	//nobody has a full hand -> either: discard / call happened
	//read lastDiscard.wind to find who discarded, prioritycaller calc field
	public Wind seatPriorityCaller(){
		Player priorityCaller = Player.NOBODY;

		if (players.someoneCalledChi()) priorityCaller = players.callerChi();
		if (players.someoneCalledPonKan()) priorityCaller = players.callerPonKan();
		if (players.someoneCalledRON()) priorityCaller = players.callerRON();
		
		return priorityCaller.getSeatWind();
	}
	
	public GameTile lastDiscard(){
		RiverWalker walker = new RiverWalker(getPonds());
		return walker.lastDiscard();
	}
	
	
	public Pond[] getPonds(){return players.getPonds();}
	
	
	
	
	
	
	
	public Kyoku next(){
		return whatDo();
//		return doRoundStart();
	}
	
	public Kyoku whatDo(){
		System.out.println("==============================================================================================\n" + this.toString() + "==============================================================================================\n\n");
		
		
		switch(mostRecentEvent.getEventType()){
//		case INIT: return doRoundStart();
//		case DEALT_HANDS: return doPlayerTurnAction(players.seatE());
//		
//		case START_OF_PLAYER_TURN:
//		case DRAW: return doCurrentPlayerTurnAction();
//		
//		case DISCARD: return moveTurnToNextPlayer(); //would get reactions here
////		case UNCALLED: return doNextPlayerTurnAction();
////		case CALLED: return //let the caller make the meld
//		case UNCALLED: return letPlayerDraw(nextPlayer());
//		
//		case MADE_OPEN_MELD: letPlayerDraw(currentPlayer());
		
		
		
		
		
		
		
		
		
		
		
		
		
//		case DISCARDED_TILE: displayEventDiscardedTile(event); break;
//		case MADE_OPEN_MELD: displayEventMadeOpenMeld(event); break;
//		case DREW_TILE: displayEventDrewTile(event); break;
//		case MADE_OWN_KAN: displayEventMadeOwnKan(event); break;
//		case NEW_DORA_INDICATOR: displayEventNewDoraIndicator(event); break;
//		
//		case HUMAN_PLAYER_TURN_START: displayEventHumanTurnStart(event); break;
//		case HUMAN_PLAYER_REACTION_START: displayEventHumanReactionStart(event); break;
//		
		
//		case PLAYER_TURN_START: displayEventPlayerTurnStart(event); break;
//		case END_OF_ROUND: displayEventEndOfRound(event); break;
//		case END: endUI(); break;
//		case START: startUI(); break;
		default: break;
		}
		
//		if (event.isExclamation()) showExclamation(event.getExclamation(), event.getSeat());
//		
//		if (shouldSleepForEvent(event))
//			Pauser.pauseFor(sleepTime);
		return null;
	}
	

//	private Kyoku doCurrentPlayerTurnAction(){return doPlayerTurnAction(currentPlayer());}
//	private Kyoku doNextPlayerTurnAction(){return doPlayerTurnAction(nextPlayer());}
	private Kyoku doPlayerTurnAction(final Player p){
//		if (roundIsOver()) return;	//return early if (4kan or ryuukyoku)
		//loop until the player has chosen a discard (loop until the player stops making kans) (kans and riichi are handled inside here)
		
		GameTile discardedTile = null;
		int discardIndex = 0;
		System.out.println(p.getHand().toString());
		
//		if (p.controllerIsHuman()) announceHumanTurnStartEvent(p);
//		if (p.controllerIsHuman()){
//			System.out.print("Discard what?: ");
//			Scanner keyboard = new Scanner(System.in);
//			discardIndex = keyboard.nextInt();
//		}
		
//		discardedTile = p.takeTurn();
		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhan" +p.handSize()); 
		discardedTile = p.getHand().getTile(discardIndex);
		Player pWithActionTaken = p.takeTurn();
		
		
//		if (madeKan(p)){
//			announceSelfKanEvent(p);
//			letPlayerDraw(p);	//give player a rinshan draw
//		}
//		
//		if (p.turnActionCalledTsumo()){
//			announceTsumoEvent(p);
//			setResultVictory(p);
//		}
		
//		if (roundIsOver()) return;	//return early if (tsumo or 4kan or 4riichi or kyuushu)
		
//		announceEvent(GameplayEvent.discardedTileEvent());
		return this.withSetPlayer(p, pWithActionTaken).withEvent(KyokuEvent.discardEvent(pWithActionTaken, discardedTile));
	}
	
//	//handles player p's turn, and gets the other players' reactions to the p's turn
//	private void doPlayerTurn(Player p){
//		
//		//loop until the player has chosen a discard (loop until the player stops making kans) (kans and riichi are handled inside here)
//		GameTile discardedTile = null;
//		do{
//			if (p.controllerIsHuman()) announceHumanTurnStartEvent(p);
//			
//			discardedTile = p.takeTurn();
//			turnIndicator.setMostRecentDiscard(discardedTile);	//discardedTile will be null if the player made a kan/tsumo, but that's ok
//			
//			if (madeKan(p)){
//				announceSelfKanEvent(p);
//				letPlayerDraw(p);	//give player a rinshan draw
//			}
//			
//			if (p.turnActionCalledTsumo()){
//				announceTsumoEvent(p);
//				setResultVictory(p);
//			}
//			
//			if (roundIsOver()) return;	//return early if (tsumo or 4kan or 4riichi or kyuushu)
//		}
//		while (!p.turnActionChoseDiscard());
//		
//		announceEvent(GameplayEvent.discardedTileEvent());
//	}
//	private boolean madeKan(Player p){return p.needsDrawRinshan();}
////	private List<Player> playersOtherThan(Player p){return Arrays.asList(roundTracker.neighborShimochaOf(p), roundTracker.neighborToimenOf(p), roundTracker.neighborKamichaOf(p));}
//	
//	
	
	
	
	private Kyoku letPlayerDraw(final Player p){
		if (!p.needsDraw())
			return this.withEvent(KyokuEvent.startOfPlayerTurnEvent(p)); //with some kind of event
		
		Wall wallAfterDraw = wall;
		
		
		
		GameTile drawnTile = null;
		if (p.needsDrawNormal()){
//			if (wallIsEmpty()){
//				setResultRyuukyokuHowanpai();
//				return;
//			}
			drawnTile = wall.nextTile();
			wallAfterDraw = wall.removeNextTile();
		}
		else if (p.needsDrawRinshan()){
//			if (tooManyKans()){
//				setResultRyuukyoku4Kan();
//				return;
//			}
			drawnTile = wall.nextDeadWallTile();
			wallAfterDraw = wall.removeNextDeadWallTile();
			
//			announceEvent(GameplayEvent.newDoraIndicatorEvent());
		}
		
		Player pWithDrawnTile = p.addTileToHand(drawnTile);
		
//		announceEvent(GameplayEvent.drewTileEvent());
		return this.withSetPlayer(p, pWithDrawnTile).withWall(wallAfterDraw).withEvent(KyokuEvent.drawEvent(pWithDrawnTile));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private Kyoku doRoundStart(){
		return dealHands();
//		announceEvent(GameplayEvent.startOfRoundEvent());
	}
	//deals players their starting hands
	private Kyoku dealHands(){
		Wall wallToDealFrom = wall;
		
		//DEBUG
//			if (DEBUG_EXHAUSTED_WALL) wallToDealFrom = WallDemoer.ExhaustedWall();
		if (DEBUG_EXHAUSTED_WALL) wallToDealFrom = WallDemoer.ExhaustedWall(wallToDealFrom);
		if (DEBUG_LOAD_DEBUG_WALL) wallToDealFrom = WallDemoer.SpecialDebugWall(wallToDealFrom);
		
		
		//get starting hands from the wall
		WallDealer wallDealer = new WallDealer(wallToDealFrom);
		Wall dealtWall = wallDealer.wallWithStartingHandsRemoved();
		
		Player peWithStartingHand = players.seatE().giveStartingHand(wallDealer.startingHandEast());
		Player psWithStartingHand = players.seatS().giveStartingHand(wallDealer.startingHandSouth());
		Player pwWithStartingHand = players.seatW().giveStartingHand(wallDealer.startingHandWest());
		Player pnWithStartingHand = players.seatN().giveStartingHand(wallDealer.startingHandNorth());
		
		
		PlayerList playersWithStartingHands = new PlayerList(peWithStartingHand, psWithStartingHand, pwWithStartingHand, pnWithStartingHand);
		return this.withWall(dealtWall).withPlayers(playersWithStartingHands).withEvent(KyokuEvent.dealtHandsEvent(players.seatE()));
	}
	
	
	
	
	
	
	
	public boolean wallIsEmpty(){return wall.isEmpty();}
	

	public Wind getRoundWind(){return roundWind;}
	public int getRoundNum(){return roundNumber;}
	public int getRoundBonusNum(){return roundBonusNumber;}
	
	
	
	
	
	
	//===================================================================================================================================
	//===================================================================================================================================
	//===================================================================================================================================
	//===================================================================================================================================
	//===================================================================================================================================
	//===================================================================================================================================
	
//	
//	public boolean callWasMadeOnDiscard(){return turnIndicator.callWasMadeOnDiscard();}
//	public GameTile mostRecentDiscard(){return turnIndicator.getMostRecentDiscard();}
//	public Player currentPlayer(){return turnIndicator.currentPlayer();}
//	public int whoseTurnNumber(){return turnIndicator.whoseTurnNumber();}
//	
//	private boolean tooManyKans(){return roundTracker.tooManyKans();}
//	
//	
//	
//	
//	//accessors
//	
//	//round result methods
//	public boolean roundIsOver(){return roundResult.isOver();}
//	public boolean endedWithVictory(){return roundResult.isVictory();}
//	public String getWinningHandString(){return roundResult.getAsStringWinningHand();}
//	public RoundResultSummary getResultSummary(){return roundResult.getSummary();}
//	public String getRoundResultString(){return roundResult.toString();}
//	
//	public boolean qualifiesForRenchan(){
//		return roundEndedWithDealerVictory();
//		/////or if the dealer is in tenpai, or a certain ryuukyoku happens
//	}
//	public boolean roundEndedWithDealerVictory(){return roundResult.isDealerVictory();}	
//	
//	
//	
//	
//	
//	
//	//-----------------------------------------------------------------------------------------------------------------mutators
//	
//	//plays a single round of mahjong with the round's players
//	public void play(){
////		if (roundIsOver()) return;	//don't allow the same round to be played twice
//		
////		dealHands();
////		announceEvent(GameplayEvent.startOfRoundEvent());
//		
//		while (!roundIsOver()){
//			doPlayerTurn(currentPlayer());
//			
//			if (roundIsOver())
//				break;
//			
//			letOtherPlayersReactToDiscard();
//			if (callWasMadeOnDiscard()){
//				handleReaction();
//				setTurnToPriorityCaller();
//			}
//			else
//				goToNextTurn();
//		}
//		handleRoundEnd();
//	}
//	
//	
//	private void goToNextTurn(){		
//		if (wallIsEmpty())
//			return;
//		turnIndicator.nextTurn();
//	}
//	private void setTurnToPriorityCaller(){turnIndicator.setTurnToPriorityCaller();}
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
//	
//	
//	
//	//handles player p's turn, and gets the other players' reactions to the p's turn
//	private void doPlayerTurn(Player p){
//		if (p.needsDraw())
//			letPlayerDraw(p);
//		announceEvent(GameplayEvent.playerTurnStartEvent());
//		
//		if (roundIsOver()) return;	//return early if (4kan or ryuukyoku)
//		
//		//loop until the player has chosen a discard (loop until the player stops making kans) (kans and riichi are handled inside here)
//		GameTile discardedTile = null;
//		do{
//			if (p.controllerIsHuman()) announceHumanTurnStartEvent(p);
//			
//			discardedTile = p.takeTurn();
//			turnIndicator.setMostRecentDiscard(discardedTile);	//discardedTile will be null if the player made a kan/tsumo, but that's ok
//			
//			if (madeKan(p)){
//				announceSelfKanEvent(p);
//				letPlayerDraw(p);	//give player a rinshan draw
//			}
//			
//			if (p.turnActionCalledTsumo()){
//				announceTsumoEvent(p);
//				setResultVictory(p);
//			}
//			
//			if (roundIsOver()) return;	//return early if (tsumo or 4kan or 4riichi or kyuushu)
//		}
//		while (!p.turnActionChoseDiscard());
//		
//		announceEvent(GameplayEvent.discardedTileEvent());
//	}
//	private boolean madeKan(Player p){return p.needsDrawRinshan();}
////	private List<Player> playersOtherThan(Player p){return Arrays.asList(roundTracker.neighborShimochaOf(p), roundTracker.neighborToimenOf(p), roundTracker.neighborKamichaOf(p));}
//	
//	
//	//gives a player a tile from the wall or dead wall
//	private void letPlayerDraw(Player p){
//		if (!p.needsDraw()) return;
//		Wall wallAfterDraw = wall;
//		
//		GameTile drawnTile = null;
//		if (p.needsDrawNormal()){
//			if (wallIsEmpty()){
//				setResultRyuukyokuHowanpai();
//				return;
//			}
//			drawnTile = wall.nextTile();
//			wallAfterDraw = wall.removeNextTile();
//		}
//		else if (p.needsDrawRinshan()){
//			if (tooManyKans()){
//				setResultRyuukyoku4Kan();
//				return;
//			}
//			drawnTile = wall.nextDeadWallTile();
//			wallAfterDraw = wall.removeNextDeadWallTile();
//			
//			announceEvent(GameplayEvent.newDoraIndicatorEvent());
//		}
//		
//		p.addTileToHand(drawnTile);
//		announceEvent(GameplayEvent.drewTileEvent());
//		
//		this.withWall(wallAfterDraw);
//	}
//	
//	private void setResultRyuukyoku4Kan(){roundResult.setResultRyuukyoku4Kan();}
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
//	private void setResultRyuukyokuHowanpai(){roundResult.setResultRyuukyokuHowanpai();}
//	
//	/////these aren't implemented yet in gameplay
//	private void setResultRyuukyokuKyuushu(){roundResult.setResultRyuukyokuKyuushu();}
//	private void setResultRyuukyoku4Riichi(){roundResult.setResultRyuukyoku4Riichi();}
//	private void setResultRyuukyoku4Wind(){roundResult.setResultRyuukyoku4Wind();}
//	
//	
//	
//	
//	
//	
//	
//	//////////////////is this mutate?????????????????
//	private void letOtherPlayersReactToDiscard(){
//		letReact(turnIndicator.neighborShimochaOf(currentPlayer()));
//		letReact(turnIndicator.neighborToimenOf(currentPlayer()));
//		letReact(turnIndicator.neighborKamichaOf(currentPlayer()));
//	}
//	private void letReact(Player p){
//		if (!p.ableToCallTile(mostRecentDiscard())) return;
//		if (p.controllerIsHuman())
//			announceHumanReactionChanceEvent(p);
//		p.reactToDiscard(mostRecentDiscard());
//	}
//	
//	
//	
//	//handles a call made on a discarded tile
//	private void handleReaction(){
//		Player priorityCaller = whoCalled();
//		turnIndicator.setPriorityCaller(priorityCaller);
//		
//		announceCallEventFrom(priorityCaller);
//		
//		if (priorityCaller.calledRon()){
//			setResultVictory(priorityCaller);
//			return;
//		}
//		
//		//remove tile from discarder's pond and make meld
//		GameTile calledTile = currentPlayer().getLastDiscard();
//		currentPlayer().removeTileFromPond();//////////////////////////////////////////////////////////////////////////////
//		priorityCaller.makeMeld(calledTile);
//		
//		announceEvent(GameplayEvent.madeOpenMeldEvent());
//	
//	
//	
//	
//	//event announce methods
//	private void announceEvent(GameplayEvent event){
//		gameEventBroadcaster.postNewEvent(event, gameState);
//	}
//	private void announceCallEventFrom(Player caller){
//		announceEvent(GameplayEvent.calledTileEvent(caller.getCallStatusExclamation(), caller, mostRecentDiscard(), whoseTurnNumber()));
//	}
//	private void announceSelfKanEvent(Player fromPlayer){
//		announceEvent(GameplayEvent.declaredOwnKanEvent(fromPlayer));
//		announceEvent(GameplayEvent.madeOwnKanEvent());
//	}
//	private void announceTsumoEvent(Player fromPlayer){announceEvent(GameplayEvent.declaredTsumoEvent(fromPlayer));}
//	private void announceHumanTurnStartEvent(Player p){announceEvent(GameplayEvent.humanPlayerTurnStartEvent(p));}
//	private void announceHumanReactionChanceEvent(Player p){announceEvent(GameplayEvent.humanReactionEvent(p, mostRecentDiscard(), whoseTurnNumber()));}
//	private void announceEndOfRoundEvent(){announceEvent(GameplayEvent.endOfRoundEvent());}
	
	public String toString(){
		String str = "";
		str += wall.toString() + "\n";
		str += "------------------------------------------\n";
		
		for (Player p: players){
			str += p.toString() + "\n";
			str += p.getHand().toString() + "\n\n";
		}
		
		str += diag();
		
		return str;
	}
	private String diag(){
		String str = "\n*****\n";
		str += mostRecentEvent.toString();
//		str += players.size();
//		str += players.size();
		return str;
	}
	

}
