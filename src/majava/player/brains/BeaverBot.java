package majava.player.brains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.enums.TurnActionType;
import majava.enums.CallType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

//a bot that tries to win, without caring what type of hand it makes
public class BeaverBot extends RobotBrain {

	public BeaverBot(){
	}
	
	
	@Override
	protected int selectDiscardIndex(Hand hand){
		return findMostWorthlessIndex(hand);
	}
	
	/*
	 * idea: does forbidding [call chi] cause a higher win rate?
	 */

	private int findMostWorthlessIndex(Hand hand){
		int mostWorthlessIndex = INDEX_NOT_FOUND;
		
		//pairs and pons
		List<Integer> singlesIndices = findSinglesIndicies(hand);
		List<Integer> pairsIndices = findPairsIndices(hand), ponsIndices = findPonsIndices(hand);
		
		//shuntsu
		//might also want to consider how many shuntsu a tile is a member of (ie: 12345, 1 can make only one shuntsu (123), 4 can make two (234/345), 3 can make three (123/234/345))
		List<Integer> almostShuntsuIndices = findAlmostShuntsuIndices(hand), fullShuntsuIndices = findFulllShuntsuIndices(hand);
		almostShuntsuIndices.removeAll(fullShuntsuIndices);
		
		//true orphans: tiles that can't form a pair/pon/shuntsu
		List<Integer> trueOrphans = new ArrayList<Integer>(singlesIndices);
		trueOrphans.removeAll(almostShuntsuIndices); trueOrphans.removeAll(fullShuntsuIndices);
		
		
		//can also use information from the board (number of tiles remaining, etc) to make decisions on which tiles are worth keeping
		
		//if yaku is restricted, also consider easy-to-win yaku (chun, tanyao)
		
		
		//in order of worth: orphan > duos > pair > trios > pons
		mostWorthlessIndex = pickOneFrom(trueOrphans);

		if (mostWorthlessIndex == INDEX_NOT_FOUND)
			mostWorthlessIndex = pickOneFrom(almostShuntsuIndices);
		
		if (mostWorthlessIndex == INDEX_NOT_FOUND)
			mostWorthlessIndex = pickOneFrom(pairsIndices);
		
		//P(56789) S(678) will reach here
		if (mostWorthlessIndex == INDEX_NOT_FOUND)
			mostWorthlessIndex = pickOneFrom(fullShuntsuIndices);
		
		
		//it should be impossible for any hand to reach here
		//^^^^^It's possible if you use an impossible cheat hand, like 22222222222222
		if (mostWorthlessIndex == INDEX_NOT_FOUND)
			mostWorthlessIndex = pickOneFrom(ponsIndices);
		
		if (mostWorthlessIndex == INDEX_NOT_FOUND)
			mostWorthlessIndex = tsumoTileIndex(hand);
		
		return mostWorthlessIndex;
	}
	
	
	
	
	
	//pon methods
	private List<Integer> findSinglesIndicies(Hand hand){return findIndicesThatAppearThisManyTimes(hand, 1);}	
	private List<Integer> findPairsIndices(Hand hand){return findIndicesThatAppearThisManyTimes(hand, 2);}	
	private List<Integer> findPonsIndices(Hand hand){return findIndicesThatAppearThisManyTimes(hand, 3);}
	
	private List<Integer> findIndicesThatAppearThisManyTimes(Hand hand, int howMany){
		List<Integer> indices = new ArrayList<Integer>();
		for (int index = 0; index < hand.size(); index++){
			if (hand.findHowManyOf(hand.getTile(index)) == howMany)
				indices.add(index);
		}
		return indices;
	}
	
	
	
	
	
	//shuntsu methods
	private List<Integer> findFulllShuntsuIndices(Hand hand){
		List<Integer> fullShuntsuIndices = new ArrayList<Integer>();

		for (int index = 0; index < hand.size(); index++){
			GameTile currentTile = hand.getTile(index);
			
			if (tileIsPartOfChiL(hand, currentTile, FULL_CHI))
				fullShuntsuIndices.add(index);
			if (tileIsPartOfChiM(hand, currentTile, FULL_CHI))
				fullShuntsuIndices.add(index);
			if (tileIsPartOfChiH(hand, currentTile, FULL_CHI))
				fullShuntsuIndices.add(index);
			
		}
		return fullShuntsuIndices;
	}
	
	private List<Integer> findAlmostShuntsuIndices(Hand hand){
		List<Integer> almostShuntsuIndices = new ArrayList<Integer>();
		
		for (int index = 0; index < hand.size(); index++){
			GameTile currentTile = hand.getTile(index);
			
			if (tileIsPartOfChiL(hand, currentTile, PARTIAL_CHI))
				almostShuntsuIndices.add(index);
			if (tileIsPartOfChiM(hand, currentTile, PARTIAL_CHI))
				almostShuntsuIndices.add(index);
			if (tileIsPartOfChiH(hand, currentTile, PARTIAL_CHI))
				almostShuntsuIndices.add(index);
			
		}
		return almostShuntsuIndices;
	}
	private static final boolean FULL_CHI = true;
	private static final boolean PARTIAL_CHI = false;
	
	private boolean handContainsTheseIds(Hand hand, boolean requireFull, List<Integer> ids){
		
		boolean id1exists = hand.contains(ids.get(0));
		boolean id2exists = hand.contains(ids.get(1));
		
		if (requireFull)
			return id1exists && id2exists;
		else
			return id1exists || id2exists;
		
	}
	private boolean tileIsPartOfChiL(Hand hand, GameTile t, boolean requireFull){
		if (!t.canCompleteChiL()) return false;
		return handContainsTheseIds(hand, requireFull, partnerIdsForChiL(t));
	}
	private boolean tileIsPartOfChiM(Hand hand, GameTile t, boolean requireFull){
		if (!t.canCompleteChiM()) return false;
		return handContainsTheseIds(hand, requireFull, partnerIdsForChiM(t));
	}
	private boolean tileIsPartOfChiH(Hand hand, GameTile t, boolean requireFull){
		if (!t.canCompleteChiH()) return false;
		return handContainsTheseIds(hand, requireFull, partnerIdsForChiH(t));
	}
	private List<Integer> partnerIdsForChiL(GameTile t){return Arrays.asList(t.getId()+1, t.getId()+2);}
	private List<Integer> partnerIdsForChiM(GameTile t){return Arrays.asList(t.getId()-1, t.getId()+1);}
	private List<Integer> partnerIdsForChiH(GameTile t){return Arrays.asList(t.getId()-2, t.getId()-1);}
	
	
	
	
	
	
	
	private static int pickOneFrom(List<Integer> choices){
//		return pickRandomlyFrom(choices);
		return pickLastFrom(choices);
	}
	
	
	@Override
	protected TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions) {return biggestTurnAction(listOfPossibleTurnActions);}
	
	@Override
	protected CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions){
//		return CallType.NONE;
//		listOfPossibleReactions.removeAll(Arrays.asList(CallType.CHI_L, CallType.CHI_M, CallType.CHI_H, CallType.PON, CallType.KAN));
		return biggestReaction(listOfPossibleReactions);
	}
	
	@Override
	public String toString(){return "BeaverBot";}
}
