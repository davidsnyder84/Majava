package majava.summary;

import java.util.List;

import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
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
	
	
//	public GodsEye(RoundTracker rTracker, PlayerTracker[] ptrackers, Wall reveivedWall, GameTile[] tilesW){
	public GodsEye(RoundTracker rTracker, Player[] playerArray, Wall reveivedWall, GameTile[] tilesW){
		roundTracker = rTracker;
		playerTrackers = makePlayerTrackers(playerArray);
		wall = reveivedWall;
		wallTiles = tilesW;
	}
	private PlayerTracker[] makePlayerTrackers(Player[] playerArray){return new PlayerTracker[]{new PlayerTracker(playerArray[0]), new PlayerTracker(playerArray[1]), new PlayerTracker(playerArray[2]), new PlayerTracker(playerArray[3])};}
	
	
	
	public final GameTileList getPlayerHandTiles(int playerNum){return playerTrackers[playerNum].handTiles();}	
	public final List<Meld> melds(int playerNum){return playerTrackers[playerNum].melds();}
	public final Pond getPlayerPond(int playerNum){return playerTrackers[playerNum].pond();}
	public final List<PondTile> getPlayerPondTiles(int playerNum){return playerTrackers[playerNum].pondTiles();}
	
	
	
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
		public final Pond pond(){return player.getPond();}
		public final List<PondTile> pondTiles(){return pond().getTilesAsList();}
	}
}
