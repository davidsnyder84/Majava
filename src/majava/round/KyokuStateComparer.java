package majava.round;

import java.util.List;

import majava.enums.GameEventType;
import majava.hand.Hand;
import majava.hand.Meld;
import majava.player.Player;
import majava.util.PlayerList;
import majava.wall.Wall;

//I'm pretty sure this is possible to make, but it would be a pain in the ass. Smarter to just have Kyoku create an event before it returns.
public class KyokuStateComparer {
	private static final int NUM_PLAYERS = 4;
	
	private final Kyoku before;
	private final Kyoku after;
	
	public KyokuStateComparer(Kyoku older, Kyoku newer){
		before = older;
		after = newer;
	}
	
	
	
	/*
	START_OF_ROUND,
	DREW_TILE,
	PLAYER_TURN_START,
	
	NEW_DORA_INDICATOR,
	DISCARDED_TILE,
	MADE_OPEN_MELD,
	
	MADE_ANKAN,
	MADE_MINKAN,
	MADE_OWN_KAN,
	
	END_OF_ROUND,
	
	CALLED_TILE,
	DECLARED_RIICHI, DECLARED_OWN_KAN, DECLARED_TSUMO,
//	CALLED_TILE(Exclamation.UNKNOWN),
//	DECLARED_RIICHI(Exclamation.RIICHI), DECLARED_OWN_KAN(Exclamation.OWN_KAN), DECLARED_TSUMO(Exclamation.TSUMO),
	
	HUMAN_PLAYER_TURN_START, HUMAN_PLAYER_REACTION_START,
	END, START,
	UNKNOWN,
	NONE;
	*/
	someonesHandtilesIncreased()
	someonesHandtilesDecreased()
	
	public List<GameEventType> events(){
		if (noDifference()) return null;
		
		
		if (someoneDrew()){
			return null;
		}
		
		
		if (someoneDiscarded()){
			return null;
		}
		
		
		
		if (someoneCalled()){
			return null;
		}
		if (someoneMadeOpenMeld()){
			return null;
		}
		
		
		
		
		
		if (someoneMadeAnkan()){
			return null;
		}
		if (someoneMadeMinkan()){
			return null;
		}
		if (someoneMadeOwnKan()){
			return null;
		}
		
		
		if (someoneDeclaredTsumo()){
			return null;
		}
		
		
		
		
		if (newDoraIndicators()){
			return null;
		}
		
		
		
		if (humanPlayerTurnStarted()){
			return null;
		}
		if (humanPlayerReactionStarted()){
			return null;
		}
		
		
		
		
		if (roundStarted()){
			return null;
		}
		if (roundEnded()){
			return null;
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private Wall wallBefore(){return before.getWall();}
	private Wall wallAfter(){return after.getWall();}
	
	
	public boolean someoneDrew(){
		boolean wallHasLessTiles = wallAfter().numTilesLeft() < wallBefore().numTilesLeft();
		return wallHasLessTiles;
	}
	
	
	public boolean someoneDiscarded(){
		if (someoneMadeOwnKan() || someoneMadeOpenMeld())
			return false;
		
		int oldHandsizes = 0, newHandSizes = 0;
		for (int i=0; i<NUM_PLAYERS; i++){
			oldHandsizes += playersBefore().get(i).handSize();
			newHandSizes += playersAfter().get(i).handSize();
		}
		
		return newHandSizes < oldHandsizes;
	}
	
	
	
	public boolean someoneCalled(){
		return false;
	}
	public boolean someoneMadeOpenMeld(){
		return false;
	}
	
	
	
	
	private PlayerList playersBefore(){return before.getPlayers();}
	private PlayerList playersAfter(){return after.getPlayers();}
	
	
	
	
	public boolean someoneMadeAnkan(){
		for (int i=0; i<NUM_PLAYERS; i++)
			if (playersAfter().get(i).numberOfANKansMade() > playersBefore().get(i).numberOfANKansMade())
				return true;
		return false;
	}
	public boolean someoneMadeMinkan(){
		for (int i=0; i<NUM_PLAYERS; i++)
			if (playersAfter().get(i).numberOfMINKansMade() > playersBefore().get(i).numberOfMINKansMade())
				return true;
		return false;
	}
	public boolean someoneMadeOwnKan(){return (someoneMadeAnkan() || someoneMadeMinkan());}
	
	
	public boolean someoneDeclaredTsumo(){
		return false;
	}
	
	
	
	
	public boolean newDoraIndicators(){
		return false;
	}
	
	
	
	public boolean humanPlayerTurnStarted(){
		return false;
	}
	public boolean humanPlayerReactionStarted(){
		return false;
	}
	
	
	
	
	public boolean roundStarted(){
		return false;
	}
	public boolean roundEnded(){
		return false;
	}
	
//	public boolean startEv(){return false;}
//	public boolean endEv(){return false;}
	
	//unknown event
	
	
	public boolean noDifference(){
//		if (before == after)
//		if (before.equals(after))
		return false;
	}
	

	
	
}
