package majava.summary;

import java.util.List;

import majava.Pond;
import majava.RoundTracker;
import majava.wall.Wall;
import majava.enums.Wind;
import majava.hand.Hand;
import majava.hand.Meld;
import majava.player.Player;
import majava.player.brains.PlayerBrain;
import majava.tiles.GameTile;
import majava.tiles.PondTile;
import majava.util.GTL;



//the UI needs to be able to look at things that are normally hidden (like the wall and all players' hands)
//this class gives the UI an immutable view of this data
public final class StateOfGame {	
	private final PlayerTracker[] playerTrackers;
	private final Wall wall;
	
	private final RoundTracker roundTracker;
	
	
	public StateOfGame(RoundTracker rTracker, Player[] playerArray, Wall reveivedWall){
		roundTracker = rTracker;
		playerTrackers = makePlayerTrackers(playerArray);
		wall = reveivedWall;
	}
	private PlayerTracker[] makePlayerTrackers(Player[] playerArray){return new PlayerTracker[]{new PlayerTracker(playerArray[0]), new PlayerTracker(playerArray[1]), new PlayerTracker(playerArray[2]), new PlayerTracker(playerArray[3])};}
	
	
	//player methods
	public GTL getHandTilesForPlayer(int playerNum){return playerTrackers[playerNum].handTiles();}	
	public String getHandAsString(int playerNum){return playerTrackers[playerNum].getHandAsString();}
	public String getHandAsStringCompact(int playerNum){return playerTrackers[playerNum].getHandAsStringCompact();}
	public GameTile getTsumoTileFor(int playerNum){return playerTrackers[playerNum].getTsumoTile();}
	
	//below is all public and could (shoud?) be learned from asking RoundTracker
	public List<Meld> getPlayerMelds(int playerNum){return playerTrackers[playerNum].melds();}
	public Pond getPondForPlayer(int playerNum){return playerTrackers[playerNum].pond();}
	public List<PondTile> getPondTilesForPlayer(int playerNum){return playerTrackers[playerNum].pondTiles();}
	public String getPondAsString(int playerNum){return playerTrackers[playerNum].getPondAsString();}
	
	public boolean playerIsHuman(int playerNum){return playerTrackers[playerNum].controllerIsHuman();}
	public boolean playerIsInRiichi(int playerNum){return playerTrackers[playerNum].isInRiichi();}
	public boolean playerNeedsDraw(int playerNum){return playerTrackers[playerNum].needsDraw();}
	public boolean playerNeedsDrawRinshan(int playerNum){return playerTrackers[playerNum].needsDrawRinshan();}
	public PlayerSummary getSummary(int playerNum){return playerTrackers[playerNum].getSummary();}
	
	public int pointsForPlayer(int playerNum){return playerTrackers[playerNum].points();}
	public Wind seatWindOfPlayer(int playerNum){return playerTrackers[playerNum].seatWind();}
	
	
	//wall methods
	public GameTile[] getWallTiles(){return wall.getTiles();}
	public String wallToString(){return wall.toString();}
	public String deadWallToString(){return wall.toStringDeadWall();}

	
	
	public RoundTracker getRoundTracker(){return roundTracker;}
	public RoundResultSummary getResultSummary(){return roundTracker.getResultSummary();}
	
	public PlayerBrain getControllerForPlayer(int playerNum){return playerTrackers[playerNum].getController();}
	public Player getPlayer(int playerNum){return playerTrackers[playerNum].getPlayer();}
	public Player currentPlayer(){return getPlayer(roundTracker.whoseTurn());}
	
	
	
	
	//inner class
	private static final class PlayerTracker{
		
		public final Player player;
		
		public PlayerTracker(Player p){
			player = p;
		}
		
		//this is normally hidden information
		public GTL handTiles(){return player.getHand().getTiles();}
		public String getHandAsString(){return player.getAsStringHand();}
		public String getHandAsStringCompact(){return player.getAsStringHandCompact();}
		public GameTile getTsumoTile(){return player.getTsumoTile();}
		
		public PlayerBrain getController() {return player.getController();}
		public Player getPlayer(){return player;}
		
		//below is all public and could (shoud?) be learned from asking RoundTracker
		public List<Meld> melds(){return player.getMelds();}
		public Pond pond(){return player.getPond();}
		public List<PondTile> pondTiles(){return pond().getTilesAsList();}
		public String getPondAsString(){return player.getAsStringPond();}
		
		public boolean controllerIsHuman(){return player.controllerIsHuman();}
		public boolean isInRiichi(){return player.isInRiichi();}
		public boolean needsDraw(){return player.needsDraw();}
		public boolean needsDrawRinshan(){return player.needsDrawRinshan();}
		public PlayerSummary getSummary(){return PlayerSummary.getSummaryFor(player);}
		
		public int points(){return player.getPoints();}
		public Wind seatWind(){return player.getSeatWind();}
	}
}
