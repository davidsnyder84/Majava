package majava.player.brains;

import java.util.List;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

//(Null object pattern)
//represents a player brain with defined null behavior. (ie, a player with no set controller)
public class NullPlayerBrain extends PlayerBrain {
	
	@Override
	//Null behavior: always choose discard as your action
	protected TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions){return TurnActionType.DISCARD;}
	
	@Override
	//Null behavior: always discard the tsumo tile (tsumogiri/ÉcÉÇêÿÇË)
	protected int selectDiscardIndex(Hand hand){return tsumoTileIndex(hand);}
	
	@Override
	//Null behavior: never react to another player's tile
	protected CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions){return CallType.NONE;}
	
	
	
	
	
	@Override
	//Null behavior: is not considered human
	public boolean isHuman(){return false;}
	
	
	@Override
	public String toString(){return "NullBrain";}
}
