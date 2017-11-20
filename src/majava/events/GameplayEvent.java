package majava.events;

import majava.enums.Exclamation;
import majava.enums.GameEventType;
import static majava.enums.GameEventType.*;
import majava.tiles.GameTile;

public class GameplayEvent {

	private static final int SEAT_NOT_SET = -1;
	
	private GameEventType eventType = null;
	private Exclamation exclamation = null;
	private int seatNumber = SEAT_NOT_SET;
	
	private int seatExclaimer = SEAT_NOT_SET;
	private int seatVictim = SEAT_NOT_SET;
	private GameTile relatedTile = null;
	
	
	private GameplayEvent(GameEventType evType){
		
	}
//	private GameplayEvent(){}
	
	
	
	public void setExclamation(Exclamation ex, int seat){exclamation = ex; seatNumber = seat;}
	public void setExclamation(Exclamation ex){exclamation = ex;}
	public Exclamation getExclamation(){return exclamation;}
	
	public int getSeat(){return seatNumber;}
	
	public boolean isExclamation(){return eventType.isExclamation();}
	
	
	
//	START_OF_ROUND,
//	DREW_TILE,
//	NEW_DORA_INDICATOR,
//	DISCARDED_TILE,
//	MADE_OPEN_MELD,
//	MADE_ANKAN, MADE_MINKAN, MADE_OWN_KAN,
//	END_OF_ROUND,
//	
//	CALLED_TILE,
//	DECLARED_RIICHI, DECLARED_OWN_KAN, DECLARED_TSUMO,
////	CALLED_TILE(Exclamation.UNKNOWN),
////	DECLARED_RIICHI(Exclamation.RIICHI), DECLARED_OWN_KAN(Exclamation.OWN_KAN), DECLARED_TSUMO(Exclamation.TSUMO),
//	
//	HUMAN_PLAYER_TURN_START,		
//	UNKNOWN, PLACEHOLDER;
	
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
	
//	START_OF_ROUND,
//	DREW_TILE,
//	NEW_DORA_INDICATOR,
//	DISCARDED_TILE,
//	MADE_OPEN_MELD,
//	MADE_ANKAN, MADE_MINKAN, MADE_OWN_KAN,
//	END_OF_ROUND,
//	
//	CALLED_TILE(Exclamation.UNKNOWN),
//	DECLARED_RIICHI(Exclamation.RIICHI), DECLARED_OWN_KAN(Exclamation.OWN_KAN), DECLARED_TSUMO(Exclamation.TSUMO),
//	
//	HUMAN_PLAYER_TURN_START,
//	
//	UNKNOWN, PLACEHOLDER;
//	
//	
//	private Exclamation exclamation;
//	private int seatNumber;
//	
//	private GameaplayEvent(Exclamation ex){exclamation = ex;}
//	private GameplayEvent(){this(null);}
//	
//	public static GamaeplayEvent calledTileEvent(){return null;}/////////////////////
//	public static GameaplayEvent CALLED_TILE(){return null;}/////////////////////
//	public static GamaeplayEvent CALLED_TILE_EVENT(){return null;}/////////////////////
//	
//	public void setExclamation(Exclamation ex, int seat){exclamation = ex; seatNumber = seat;}
//	public void setExclamation(Exclamation ex){exclamation = ex;}
//	public Exclamation getExclamation(){return exclamation;}
//	
//	public int getSeat(){return seatNumber;}
//	
//	public boolean isExclamation(){return (this == CALLED_TILE || this == DECLARED_RIICHI || this == DECLARED_OWN_KAN || this == DECLARED_TSUMO);}
}
