package majava.events;

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
	
	
	private int seatVictim;
	private Player relatedPlayer;
	private GameTile relatedTile;
	
	private RoundResultSummary resultSummary;
	
	
	private GameplayEvent(GameEventType evType){
		eventType = evType;
		exclamation = null;
		
		seatVictim = VICTIM_SEAT_NOT_SET;		
		relatedTile = null;
		relatedPlayer = null;
		
		resultSummary = null;
	}
//	private GameplayEvent(){}
	
	public void setExclamation(Exclamation ex){exclamation = ex;}
	public Exclamation getExclamation(){return exclamation;}
	
	public int getGetVictimSeatNumber(){return seatVictim;}
	public GameTile getRelatedTile(){return relatedTile;}
	public Player getRelatedPlayer(){return relatedPlayer;}
	public int getSeat(){return relatedPlayer.getPlayerNumber();}
	public RoundResultSummary getResultSummary(){return resultSummary;}
	
	public boolean isExclamation(){return eventType.isExclamation();}
	public boolean isPlaceholder(){return eventType == PLACEHOLDER;}
	public boolean isForHuman(){return eventType == HUMAN_PLAYER_REACTION_START || eventType == HUMAN_PLAYER_TURN_START;}
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

	public static final GameplayEvent calledTileEvent(){return new GameplayEvent(CALLED_TILE);}
	public static final GameplayEvent declaredRiichiEvent(){return new GameplayEvent(DECLARED_RIICHI);}
	public static final GameplayEvent declaredOwnKanEvent(){return new GameplayEvent(DECLARED_OWN_KAN);}
	public static final GameplayEvent declaredTsumoEvent(){return new GameplayEvent(DECLARED_TSUMO);}

	public static final GameplayEvent humanPlayerTurnStartEvent(){return new GameplayEvent(HUMAN_PLAYER_TURN_START);}
	public static final GameplayEvent humanReactionEvent(){return new GameplayEvent(HUMAN_PLAYER_REACTION_START);}
	public static final GameplayEvent unknownEvent(){return new GameplayEvent(UNKNOWN);}
	public static final GameplayEvent placeholderEvent(){return new GameplayEvent(PLACEHOLDER);}


//	public void packInfo(Player p, GameTile tile, int eventRaisedByplayerNumber, int whoseTurnNumber) {
	public void packInfo(Player p, GameTile tile, int whoseTurnNumber) {
		relatedPlayer = p;
		relatedTile = tile;
		seatVictim = whoseTurnNumber;
	}
	public void packInfo(Player p, GameTile tile) {packInfo(p, tile, VICTIM_SEAT_NOT_SET);}
	public void packInfo(Player p) {packInfo(p, RELATED_TILE_NOT_SET, VICTIM_SEAT_NOT_SET);}

	public void packInfo(RoundResultSummary summary) {
		resultSummary = summary;
	}
	
//	public static final GameplayEvent newEvent(GameEventType event){return null;}
	
}
