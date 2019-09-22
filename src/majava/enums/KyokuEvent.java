package majava.enums;

import static majava.enums.KET.*;
import majava.player.Player;
import majava.tiles.GameTile;

public class KyokuEvent {
	private static final Player NOBODY = null;
	private static final GameTile NOTHING = null;
	
	
	private final KET eventType;
	private final Player whoseTurn;
	
	private final Player lastDiscarder;
	private final GameTile lastDiscardedTile;
	
	
	private KyokuEvent(KET et, Player turnPlayer, Player discarder, GameTile discardedTile){
		eventType = et;
		whoseTurn = turnPlayer;
		
		lastDiscardedTile = discardedTile;
		lastDiscarder = discarder;
	}
	private KyokuEvent(KET et, Player turnPlayer){this(et, turnPlayer, NOBODY, NOTHING);}
	private KyokuEvent(KET et){this(et, NOBODY);}
	
	
	
	//getters
	public KET getEventType(){return eventType;}
	public GameTile getLastDiscardedTile(){return lastDiscardedTile;}
	public Player getWhoseTurn(){return whoseTurn;}
	public Player getLastDiscarder(){return lastDiscarder;}
	
	
	
	//factory
	public static final KyokuEvent initEvent(){return new KyokuEvent(INIT);}
	public static final KyokuEvent dealtHandsEvent(Player east){return new KyokuEvent(DEALT_HANDS, east);}
	
	public static final KyokuEvent startOfPlayerTurnEvent(Player thisPlayer){return new KyokuEvent(START_OF_PLAYER_TURN);}
	public static final KyokuEvent drawEvent(Player drawer){return new KyokuEvent(DRAW, drawer);}
	
	public static final KyokuEvent discardEvent(Player discarder, GameTile discardedTile){return new KyokuEvent(DISCARD);}
	public static final KyokuEvent uncalledEvent(Player discarder){return new KyokuEvent(UNCALLED, discarder);}
	
	
	public String toString(){
		return eventType.toString();
	}
}
