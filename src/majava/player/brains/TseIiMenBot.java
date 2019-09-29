package majava.player.brains;

import java.util.List;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.enums.Suit;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;


//Tse Ii Men (ツェーイーメン /絶一門) algorithm
public class TseIiMenBot extends RobotBrain {
	private static final char SUIT_MAN = Suit.MANZU.toChar(), SUIT_PIN = Suit.PINZU.toChar(), SUIT_SOU = Suit.SOUZU.toChar(), SUIT_WIND = Suit.WIND.toChar(), SUIT_DRAGON = Suit.DRAGON.toChar();
	private static final char DEFAULT_SUIT_TO_CUT = SUIT_SOU;
	private static final int NOT_FOUND = -1;
	
	
	private char suitToCut;
	
	public TseIiMenBot(char cutSuit){
		suitToCut = cutSuit;
	}
	public TseIiMenBot(){this(DEFAULT_SUIT_TO_CUT);}
	
	
	
	

	@Override
	protected TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions){return biggestTurnAction(listOfPossibleTurnActions);}
	@Override
	protected int selectDiscardIndex(Hand hand){
		if (findCutSuitIndex(hand) != NOT_FOUND)
			return findCutSuitIndex(hand);
		
		if (findHonorIndex(hand) != NOT_FOUND)
			return findHonorIndex(hand);
		
		return tsumoTileIndex(hand);
	}
	
	private int findCutSuitIndex(Hand hand){
		for (int index = tsumoTileIndex(hand); index >= 0; index--)
			if (hand.getTile(index).getSuit() == suitToCut) return index;
		return NOT_FOUND;
	}
	private int findHonorIndex(Hand hand){		
		for (int index = tsumoTileIndex(hand); index >= 0; index--)
			if (hand.getTile(index).isHonor()) return index;
		return NOT_FOUND;
	}
	
	@Override
	protected CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions) {return biggestReaction(listOfPossibleReactions);}

	@Override
	public String toString(){return "TseIiiMenBot";}
}
