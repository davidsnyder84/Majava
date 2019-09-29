package majava.round;

import majava.pond.Pond;
import majava.player.Player;
import majava.tiles.PondTile;
import majava.util.PlayerList;

public class RoundEndChecker {
	
	private final Kyoku round;
	
	public RoundEndChecker(Kyoku rnd){
		round = rnd;
	}
	
	

	public boolean isOver(){
		//p.calledRon() and p.calledTsumo() decisions will still be alive here
		return someoneDeclaredVictory() || isRyuukyoku();
	}
	
	
	
	
	//-----victory
	public boolean someoneDeclaredVictory(){
		return someoneDeclaredTsumo() || someoneDeclaredRon();
	}
	public boolean someoneDeclaredTsumo(){
		for (Player p : players()) if (p.turnActionCalledTsumo()) return true;
		return false;
	}
	public boolean someoneDeclaredRon(){
		for (Player p : players()) if (p.calledRon(round.lastDiscard())) return true;
		return false;
	}
	
	public boolean qualifiesForRenchan(){
		return endedWithEastVictory();
		/////or if the dealer is in tenpai, or a certain ryuukyoku happens
	}
	public boolean endedWithEastVictory(){
		return (players().seatE().calledRon(round.lastDiscard()) ||
				players().seatE().turnActionCalledTsumo() );
	}
	
	
	
	
	
	//-----ryuukyou
	public boolean isRyuukyoku(){
		return (isRyuukyokuHowanpai() ||
				isRyuukyoku4Kan() || 
				isRyuukyokuKyuushuu() ||
				isRyuukyoku4Wind()  );
	}
	
	
	
	public boolean isRyuukyokuHowanpai(){
		if (!round.wallIsEmpty())
			return false;
		
		//if we're still here, the wall is empty
		
		if (round.someoneHasFullHand() || 
			round.someoneCalledLastDiscard() || 
			round.someoneNeedsDrawRinshan() )
			return false;
			
		return true;
	}
	
	public boolean isRyuukyoku4Riichi(){
		int count = 0;
		for (Player p : players()) if (p.isInRiichi()) count++;
		return (count == 4);
	}
	
	public boolean isRyuukyokuKyuushuu(){
		return false;//////////this one can't be derived, it has to be set/chosen
	}
	
	public boolean isRyuukyoku4Wind(){
		Pond[] ponds = round.getPonds();
		for (Pond pond : ponds)
			if (pond.size() != 1 || !pond.isUntouched())
				return false;
		
		//here: each pond has exactly 1 tile, and they are all untouched
		
		PondTile allOtherTilesMustEqualThis = ponds[0].getFirst();
		if (!allOtherTilesMustEqualThis.isWind())
			return false;
		
		for (Pond pond : ponds)
			if (!pond.getFirst().equals(allOtherTilesMustEqualThis))
				return false;
		
		return true;
	}
	
	
	
	public boolean isRyuukyoku4Kan(){return tooManyKans();}
	
	public boolean tooManyKans(){
		final int KAN_LIMIT = 4;
		if (round.numKansMade() < KAN_LIMIT) return false;
		if (round.numKansMade() == KAN_LIMIT && !multiplePlayersHaveMadeKans()) return false;		
		return true;
	}
	public boolean multiplePlayersHaveMadeKans(){
		int numberOfPlayersWhoHaveMadeKan = 0;
		for (Player p: players()) if (p.hasMadeAKan()) numberOfPlayersWhoHaveMadeKan++;
		return (numberOfPlayersWhoHaveMadeKan > 1);
	}
	
	
	
	
	
	private PlayerList players(){return round.getPlayers();}
}
