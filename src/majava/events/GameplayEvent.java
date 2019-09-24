package majava.events;

import majava.enums.CallType;
import majava.enums.Exclamation;
import majava.enums.GameEventType;
import static majava.enums.GameEventType.*;
import majava.player.Player;
import majava.player.brains.HumanBrain;
import majava.summary.RoundResultSummary;
import majava.summary.StateOfGame;
import majava.tiles.GameTile;

public class GameplayEvent {
	private static final int VICTIM_SEAT_NOT_SET = -58;
	private static final GameTile RELATED_TILE_NOT_SET = null;
	
	private GameEventType eventType;
	private Exclamation exclamation;
	
	private Player relatedPlayer;
	private GameTile relatedTile;
	private int seatVictim;	//no one uses this field yet
	
	
	private GameplayEvent(GameEventType evType){
		eventType = evType;
		exclamation = null;
		
		seatVictim = VICTIM_SEAT_NOT_SET;		
		relatedTile = null;
		relatedPlayer = null;
	}
//	private GameplayEvent(){}
	
	public void setExclamation(Exclamation ex){exclamation = ex;}
	public Exclamation getExclamation(){return exclamation;}
	
	public int getGetVictimSeatNumber(){return seatVictim;}
	public GameTile getRelatedTile(){return relatedTile;}
	public Player getRelatedPlayer(){return relatedPlayer;}
	public int getSeat(){return relatedPlayer.getPlayerNumber();}
	
	public boolean isExclamation(){return eventType.isExclamation();}
	public boolean isForHuman(){return eventType == HUMAN_PLAYER_REACTION_START || eventType == HUMAN_PLAYER_TURN_START;}
	public boolean isStartEnd(){return eventType == START || eventType == END;}
	public GameEventType getEventType(){return eventType;}
	
	
	
	
	
	
	
	//factory methods
	public static final GameplayEvent startOfRoundEvent(){return new GameplayEvent(START_OF_ROUND);}
	public static final GameplayEvent drewTileEvent(){return new GameplayEvent(DREW_TILE);}
	public static final GameplayEvent newDoraIndicatorEvent(){return new GameplayEvent(NEW_DORA_INDICATOR);}
	public static final GameplayEvent discardedTileEvent(){return new GameplayEvent(DISCARDED_TILE);}
	public static final GameplayEvent madeOpenMeldEvent(){return new GameplayEvent(MADE_OPEN_MELD);}
	public static final GameplayEvent madeAnkanEvent(){return new GameplayEvent(MADE_ANKAN);}
	public static final GameplayEvent madeMinkanEvent(){return new GameplayEvent(MADE_MINKAN);}
	public static final GameplayEvent madeOwnKanEvent(){return new GameplayEvent(MADE_OWN_KAN);}
	public static final GameplayEvent endOfRoundEvent(){return new GameplayEvent(END_OF_ROUND);}

	
//	public static final GameplayEvent calledTileEvent(Exclamation exclaim, Player caller, GameTile tile, int whoseTurnNumber){
	public static final GameplayEvent calledTileEvent(){
		GameplayEvent event = new GameplayEvent(CALLED_TILE);
//		event.setExclamation(exclaim);
//		event.packInfo(caller, tile, whoseTurnNumber);
		return event;	
	}
	public static final GameplayEvent declaredRiichiEvent(){return new GameplayEvent(DECLARED_RIICHI);}
	
//	public static final GameplayEvent declaredOwnKanEvent(Player p){
	public static final GameplayEvent declaredOwnKanEvent(){
		GameplayEvent event = new GameplayEvent(DECLARED_OWN_KAN);
//		event.setExclamation(Exclamation.OWN_KAN);
//		event.packInfo(p);
		return event;
	}
	public static final GameplayEvent declaredTsumoEvent(){
		GameplayEvent event = new GameplayEvent(DECLARED_TSUMO);
//		event.setExclamation(Exclamation.TSUMO);
//		event.packInfo(p);
		return event;
	}

	public static final GameplayEvent humanPlayerTurnStartEvent(Player p){
		GameplayEvent event = new GameplayEvent(HUMAN_PLAYER_TURN_START);
		event.packInfo(p);
		return event;
	}
	
//	public static final GameplayEvent humanReactionEvent(Player p, GameTile tile, int whoseTurnNumber){
	public static final GameplayEvent humanReactionEvent(Player p){
		GameplayEvent event = new GameplayEvent(HUMAN_PLAYER_REACTION_START);
//		event.packInfo(p, tile, whoseTurnNumber);
		return event;
	}
	public static final GameplayEvent unknownEvent(){return new GameplayEvent(UNKNOWN);}
	public static final GameplayEvent playerTurnStartEvent(){return new GameplayEvent(PLAYER_TURN_START);}
	public static final GameplayEvent endingEvent(){return new GameplayEvent(END);}
	public static final GameplayEvent startingEvent(){return new GameplayEvent(START);}
	
	
	
	
	private void packInfo(Player p, GameTile tile, int whoseTurnNumber) {
		relatedPlayer = p;
		relatedTile = tile;
		seatVictim = whoseTurnNumber;
	}
	private void packInfo(Player p, GameTile tile) {packInfo(p, tile, VICTIM_SEAT_NOT_SET);}
	private void packInfo(Player p) {packInfo(p, RELATED_TILE_NOT_SET, VICTIM_SEAT_NOT_SET);}

	
//	public static final GameplayEvent newEvent(GameEventType event){return null;}
	
}
