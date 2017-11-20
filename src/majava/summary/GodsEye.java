package majava.summary;

import java.util.List;

import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.Wind;
import majava.hand.Hand;
import majava.hand.Meld;
import majava.player.Player;
import majava.tiles.GameTile;
import majava.tiles.PondTile;
import majava.util.GameTileList;



//the UI needs to be able to look at things that are normally hidden (like the wall and all players' hands)
//this class gives the UI an immutable view of this data
public class GodsEye {	
	private final PlayerTracker[] playerTrackers;
	private final GameTile[] wallTiles;
	private final Wall wall;
	
	private final RoundTracker roundTracker;
	
	
	public GodsEye(RoundTracker rTracker, Player[] playerArray, Wall reveivedWall){
		roundTracker = rTracker;
		playerTrackers = makePlayerTrackers(playerArray);
		wall = reveivedWall;
		wallTiles = wall.DEMOpleaseGiveMeYourTiles();
	}
	private PlayerTracker[] makePlayerTrackers(Player[] playerArray){return new PlayerTracker[]{new PlayerTracker(playerArray[0]), new PlayerTracker(playerArray[1]), new PlayerTracker(playerArray[2]), new PlayerTracker(playerArray[3])};}
	
	
	//player methods
	public final GameTileList getHandTilesForPlayer(int playerNum){return playerTrackers[playerNum].handTiles();}	
	public final List<Meld> getPlayerMelds(int playerNum){return playerTrackers[playerNum].melds();}
	public final String getHandAsString(int playerNum){return playerTrackers[playerNum].getHandAsString();}
	public final String getHandAsStringCompact(int playerNum){return playerTrackers[playerNum].getHandAsStringCompact();}
	public final GameTile getTsumoTileFor(int playerNum){return playerTrackers[playerNum].getTsumoTile();}
	
	public final Pond getPondForPlayer(int playerNum){return playerTrackers[playerNum].pond();}
	public final List<PondTile> getPondTilesForPlayer(int playerNum){return playerTrackers[playerNum].pondTiles();}
	public final String getPondAsString(int playerNum){return playerTrackers[playerNum].getPondAsString();}
	
	public final boolean playerIsHuman(int playerNum){return playerTrackers[playerNum].controllerIsHuman();}
	public final boolean playerIsInRiichi(int playerNum){return playerTrackers[playerNum].isInRiichi();}
	public final boolean playerNeedsDraw(int playerNum){return playerTrackers[playerNum].needsDraw();}
	public final boolean playerNeedsDrawRinshan(int playerNum){return playerTrackers[playerNum].needsDrawRinshan();}
	
	public final int pointsForPlayer(int playerNum){return playerTrackers[playerNum].points();}
	public final Wind seatWindOfPlayer(int playerNum){return playerTrackers[playerNum].seatWind();}
	
	
	//wall methods
	public final GameTile[] getWallTiles(){return wallTiles.clone();}
	public final String wallToString(){return wall.toString();}
	public final String deadWallToString(){return wall.toStringDeadWall();}

	
	
	
	
	
	//inner class
	private static class PlayerTracker{
		
		public final Player player;
		public final Hand hand;
		
		public PlayerTracker(Player p){
			player = p;
			hand = player.DEMOgetHand();/////////
		}
		
		//all of these are clones
		public final GameTileList handTiles(){return hand.getTilesAsList();}
		public final List<Meld> melds(){return player.getMelds();}
		public final String getHandAsString(){return player.getAsStringHand();}
		public final String getHandAsStringCompact(){return player.getAsStringHandCompact();}
		public final GameTile getTsumoTile(){return player.getTsumoTile();}
		
		public final Pond pond(){return player.getPond();}
		public final List<PondTile> pondTiles(){return pond().getTilesAsList();}
		public final String getPondAsString(){return player.getAsStringPond();}
		
		public final boolean controllerIsHuman(){return player.controllerIsHuman();}
		public final boolean isInRiichi(){return player.isInRiichi();}
		public final boolean needsDraw(){return player.needsDraw();}
		public final boolean needsDrawRinshan(){return player.needsDrawRinshan();}
		
		public final int points(){return player.getPoints();}
		public final Wind seatWind(){return player.getSeatWind();}
	}
}
