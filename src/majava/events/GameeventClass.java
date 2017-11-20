package majava.events;

import majava.enums.Exclamation;
import majava.enums.GameEventType;
import static majava.enums.GameEventType.*;
import majava.tiles.GameTile;

public class GameeventClass {

	private static final int SEAT_NOT_SET = -1;
	
	private GameEventType eventType = null;
	private Exclamation exclamation = null;
	private int seatNumber = SEAT_NOT_SET;
	
	private int seatExclaimer = SEAT_NOT_SET;
	private int seatVictim = SEAT_NOT_SET;
	private GameTile relatedTile = null;
	
	
	private GameeventClass(GameEventType evType){
		
	}
//	private GameeventClass(){}
	
	
	
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
	public static final GameeventClass startOfRoundEvent(){return new GameeventClass(START_OF_ROUND);}
	public static final GameeventClass drewTileEvent(){return new GameeventClass(DREW_TILE);}
	public static final GameeventClass newDoraIndicatorEvent(){return new GameeventClass(NEW_DORA_INDICATOR);}
	public static final GameeventClass discardedTileEvent(){return new GameeventClass(DISCARDED_TILE);}
	public static final GameeventClass madeOpenMeldEvent(){return new GameeventClass(MADE_OPEN_MELD);}
	public static final GameeventClass madeAnkanEvent(){return new GameeventClass(MADE_ANKAN);}
	public static final GameeventClass madeMinkanEvent(){return new GameeventClass(MADE_MINKAN);}
	public static final GameeventClass madeOwnKanEvent(){return new GameeventClass(MADE_OWN_KAN);}
	public static final GameeventClass endOfRoundEvent(){return new GameeventClass(END_OF_ROUND);}

	public static final GameeventClass calledTileEvent(){return new GameeventClass(CALLED_TILE);}
	public static final GameeventClass declaredRiichiEvent(){return new GameeventClass(DECLARED_RIICHI);}
	public static final GameeventClass declaredOwnKanEvent(){return new GameeventClass(DECLARED_OWN_KAN);}
	public static final GameeventClass declaredTsumoEvent(){return new GameeventClass(DECLARED_TSUMO);}

	public static final GameeventClass humanPlayerTurnStartEvent(){return new GameeventClass(HUMAN_PLAYER_TURN_START);}
	public static final GameeventClass unknownEvent(){return new GameeventClass(UNKNOWN);}
	public static final GameeventClass placeholderEvent(){return new GameeventClass(PLACEHOLDER);}
	
	
//	public static final GameeventClass newEvent(GameEventType event){return null;}
}
