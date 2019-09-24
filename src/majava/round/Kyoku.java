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
	
	
	private final KyokuEvent mostRecentEvent;	//////////////////////////////////////////////this way of doing it is fine, use this (but make it something different)
	
//	private final StateOfGame gameState;
//	private final GameEventBroadcaster gameEventBroadcaster;
	
//	private final RoundTracker roundTracker;
//	private final RoundResult roundResult;
	
	
//	private final Kyoku previousState //??????????????????????????????????????????????????????????????
	
	
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
	public Kyoku withUpdatedPlayer(Player newPlayer){
		return this.withPlayers(players.updatePlayer(newPlayer));
	}
	
	public Kyoku withEvent(KyokuEvent newEvent){return new Kyoku(players, wall, roundWind, roundNumber, roundBonusNumber, newEvent, BUILDER);}
	
	
	
	
	
	
	
	
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
	public Player lastDiscarder(){return players.get(lastDiscard().getOrignalOwner());}
	
	
	//When is everyone is 13 13 13 13?: right after discard / after call but before meldmake
	public Wind seatToDrawNext(){
		if (lastDiscard().wasCalled())
			return lastDiscard().getCaller();
		else
			return lastDiscard().getOrignalOwner().shimochaWind();
	}
	
	public boolean isOver(){
		if (wallIsEmpty()){
			if (someoneHasFullHand() || players.someoneCalled(lastDiscard()))
				return false;
			else
				return true;
		}
		return false;
		
//		return wallIsEmpty() && !someoneHasFullHand() && !someoneCalled();
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
	
	
	public boolean needToDealStartingHands(){
		return (wall.currentPosition() == 0);
//		for (Player p : players) if (p.handSize() == 0) return true;
//		return false;
	}
	
	
//	public boolean someoneWantsToCallTheLastDiscard(){return letPlayersReactToDiscard().someoneCalled();}
	public PlayerList letPlayersReactToDiscard(){
		PlayerList playersWithReactions = players;
		for (Player p : players.allPlayersExcept(lastDiscarder()))
			if (p.ableToCallTile(lastDiscard()))
				playersWithReactions.set(p.reactToDiscard(lastDiscard()));
		
		return playersWithReactions;
	}
	
	
	
	
	public Kyoku next(){
//		System.out.println("==============================================================================================\n" + this.toString() + "==============================================================================================\n\n");
		
		if (isOver())
			return this;
		
		if (needToDealStartingHands())
			return dealHands();
		
		
		
		
		
		if (someoneHasFullHand()){
			if (turnPlayer().hasChosenTurnAction())
				return executeTurnAction();
			else
				return askTurnAction();
			
//			return doPlayerTurnAction();
		}
		
		
		
		
		if (someoneCalledLastDiscard()){
			if (priorityCaller().calledRon(lastDiscard()))
				return null; ///////return some kind of roundend
			else
				return letPriorityCallerMakeMeld();
		}
		
		
		
		
		PlayerList playersWithReactions = letPlayersReactToDiscard();
		if (playersWithReactions.someoneCalled(lastDiscard()))
			return this.withPlayers(playersWithReactions);
		
		
		
		return letPlayerDraw();
		
	}
	
	
	
	
	
	
	
	public Kyoku letPriorityCallerMakeMeld(){
		GameTile calledTile = lastDiscard();
		Player priorityCaller = players.get(seatPriorityCaller());
		Player discarder = players.get(calledTile.getOrignalOwner());
		
		///////////////ron falls here
//		if (priorityCaller.calledRon()){
//			setResultVictory(priorityCaller);
//			return;
//		}
		
		
		//remove tile from discarder's pond and make meld
		Player discarderWithPondTileRemoved = discarder.removeTileFromPond(priorityCaller.getSeatWind());
		Player priorityCallerWithMeldMade = priorityCaller.makeMeld(calledTile);
		
		return this.withUpdatedPlayer(discarderWithPondTileRemoved).withUpdatedPlayer(priorityCallerWithMeldMade);
	}
	
	
	
	private Kyoku askTurnAction(){
		Player p = turnPlayer();
		
		Player pWithTurnActionDecided = p.decideTurnAction();
//		p.takeTurn();
		
		return this.withUpdatedPlayer(pWithTurnActionDecided);
	}
	private Kyoku executeTurnAction(){
		Player p = turnPlayer();
		
		Player pAfterExecutingTurnAction = p.doTurnAction();
//		Player pAfterExecutingTurnAction = p.takeTurn();
		
		return this.withUpdatedPlayer(pAfterExecutingTurnAction);
	}
	
	
//	private Kyoku doCurrentPlayerTurnAction(){return doPlayerTurnAction(currentPlayer());}
//	private Kyoku doNextPlayerTurnAction(){return doPlayerTurnAction(nextPlayer());}
	private Kyoku doPlayerTurnAction(){return doPlayerTurnAction(turnPlayer());}
//	private Kyoku doPlayerTurnAction(Wind seat){return doPlayerTurnAction(turnPlayer());}
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
		System.out.println("pppppppp" + p.getSeatWind()); 
		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhan" +p.handSize()); 
		discardedTile = p.getHand().getTile(discardIndex);
		Player pWithActionTaken = p.takeTurn();
		
		
		return this.withUpdatedPlayer(pWithActionTaken);
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
	
	
	private Kyoku letPlayerDraw(){return letPlayerDraw(players.get(seatToDrawNext()));}
	private Kyoku letPlayerDraw(final Player p){
//		if (!p.needsDraw()) return this; //with some kind of event   //if we're here, p definitely needs to draw, so this check shouldn't be necessary
		
		Wall wallAfterDraw = wall;
		
		GameTile drawnTile = null;
		if (p.needsDrawRinshan()){
//			if (tooManyKans()) setResultRyuukyoku4Kan();
			
			drawnTile = wall.nextDeadWallTile();
			wallAfterDraw = wall.removeNextDeadWallTile();
			//with with new dora indicators? or does wall know how to figure that out on its own?
		}
		else{
//			if (wallIsEmpty()) setResultRyuukyokuHowanpai();
			
			drawnTile = wall.nextTile();
			wallAfterDraw = wall.removeNextTile();
		}
		
		
		Player pWithDrawnTile = p.addTileToHand(drawnTile);
		
		return this.withUpdatedPlayer(pWithDrawnTile).withWall(wallAfterDraw);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private Kyoku doRoundStart(){
//		return dealHands();
////		announceEvent(GameplayEvent.startOfRoundEvent());
//	}
	//deals players their starting hands
	private Kyoku dealHands(){
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
		
		
		PlayerList playersWithStartingHands = new PlayerList(peWithStartingHand, psWithStartingHand, pwWithStartingHand, pnWithStartingHand);
		return this.withWall(dealtWall).withPlayers(playersWithStartingHands);
//		return this.withWall(dealtWall).withPlayers(playersWithStartingHands).postEvent(something);
	}
	
	
	
	
	
	
	
	public boolean wallIsEmpty(){return wall.isEmpty();}
	
	
	
	
	
	
	
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
	
	
	
	//event announce methods
//	private void announceEvent(GameplayEvent event){
//		gameEventBroadcaster.postNewEvent(event, gameState);
//	}
	private void announceCallEventFrom(Player caller){
//		GameplayEvent.calledTileEvent(caller.getCallStatusExclamation(), caller, mostRecentDiscard(), whoseTurnNumber());
		GameplayEvent.calledTileEvent();
	}
	private void announceSelfKanEvent(Player fromPlayer){
//		GameplayEvent.declaredOwnKanEvent(fromPlayer); ///!!!!!!!!!! <- this is DECLARE slef kan. Declare is done by someone with a full hand, so you can infer who it is
		GameplayEvent.declaredOwnKanEvent();
		GameplayEvent.madeOwnKanEvent();
		//if I could figure out how to determine WHO made the most recent kan, I could make 100% of these be enums
	}
	private void announceTsumoEvent(Player fromPlayer){
//		GameplayEvent.declaredTsumoEvent(fromPlayer); //can infer tsumo player from roundstate (who has full hand?)
		GameplayEvent.declaredTsumoEvent();
	}
//	private void announceHumanTurnStartEvent(Player p){///////////////what does this mean?
//		GameplayEvent.humanPlayerTurnStartEvent(p);
//	}
//	private void announceHumanReactionChanceEvent(Player p){////////////this might not be necessary
//		GameplayEvent.humanReactionEvent(p);
//	}	
	//**********************************************enum
	private void announceEndOfRoundEvent(){
		GameplayEvent.endOfRoundEvent();
	}
	//**********************************************enum
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^use Wind instead of Player
	
	public GameEventType getMostRecentEvent(){
//		return mostRecentEvent;
		return null;
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
