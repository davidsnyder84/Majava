package majava.events;

import majava.enums.Exclamation;
import majava.enums.GameEventType;
import static majava.enums.GameEventType.*;
import majava.player.brains.HumanBrain;
import majava.tiles.GameTile;

public class GameplayEvent {

	private static final int SEAT_NOT_SET = -1;
	
	private GameEventType eventType;
	private Exclamation exclamation;
	private int seatNumber;
	
	/////no one is using these yet
	private int seatExclaimer;
	private int seatVictim;
	private GameTile relatedTile;
	
	private HumanBrain relatedHumanBrain;
	
	
	private GameplayEvent(GameEventType evType){
		eventType = evType;
		exclamation = null;
		
		seatNumber = SEAT_NOT_SET;
		seatExclaimer = SEAT_NOT_SET;
		seatVictim = SEAT_NOT_SET;
		
		relatedTile = null;
		relatedHumanBrain = null;
	}
//	private GameplayEvent(){}
	
	
	
	public void setExclamation(Exclamation ex, int seat){exclamation = ex; seatNumber = seat;}
//	public void setExclamation(Exclamation ex){exclamation = ex;}
	public Exclamation getExclamation(){return exclamation;}
	
	public int getSeat(){return seatNumber;}
	public int getGetVictimSeatNumber(){return seatVictim;}
	public int getGetExclaimerSeatNumber(){return seatExclaimer;}
	public GameTile getRelatedTile(){return relatedTile;}
	
	public boolean isExclamation(){return eventType.isExclamation();}
	public boolean isPlaceholder(){return eventType == PLACEHOLDER;}
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
	public static final GameplayEvent unknownEvent(){return new GameplayEvent(UNKNOWN);}
	public static final GameplayEvent placeholderEvent(){return new GameplayEvent(PLACEHOLDER);}
	
//	public static final GameplayEvent newEvent(GameEventType event){return null;}
	
}
