package majava.round;

import java.util.List;

import majava.Pond;
import majava.RoundTracker;
import majava.wall.Wall;
import majava.enums.GameEventType;
import majava.enums.Wind;
import majava.hand.Hand;
import majava.hand.Meld;
import majava.player.Player;
import majava.player.brains.PlayerBrain;
import majava.summary.PlayerSummary;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.tiles.PondTile;
import majava.util.GTL;
import majava.util.PlayerList;



public final class KyokuState{
	
	private final Kyoku kyoku;
//	private final GameEventType event;
	
	
	private final PlayerTracker[] playerTrackers;
	
	private final RoundTracker roundTracker;
	
	
	public KyokuState(Kyoku round, RoundTracker rTracker, Wall reveivedWall){
		kyoku = round;
//		event = kyoku.getMostRecentEvent();
		
		roundTracker = rTracker;
		playerTrackers = makePlayerTrackers(players());
	}
	private PlayerTracker[] makePlayerTrackers(PlayerList players){
		return new PlayerTracker[]{new PlayerTracker(players.get(0)), new PlayerTracker(players.get(1)), new PlayerTracker(players.get(2)), new PlayerTracker(players.get(3))};
//		return new PlayerTracker[]{new PlayerTracker(playerArray[0]), new PlayerTracker(playerArray[1]), new PlayerTracker(playerArray[2]), new PlayerTracker(playerArray[3])};
	}
	
	
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
	public GameTile[] getWallTiles(){return wall().getTiles();}
	public String wallToString(){return wall().toString();}
	public String deadWallToString(){return wall().toStringDeadWall();}
	
	
	public Kyoku getKyoku(){return kyoku;}
	public GameEventType getEvent(){return kyoku.getMostRecentEvent();}
	
	public Wall wall(){return kyoku.getWall();}
	public PlayerList players(){return kyoku.getPlayers();}
	
	public Wind getRoundWind(){return kyoku.getRoundWind();}
	public int getRoundNum(){return kyoku.getRoundNum();}
	public int getRoundBonusNum(){return kyoku.getRoundBonusNum();}
	
	
	
	/////////////////////////////////////////////////////////////////////////////
	public RoundTracker getRoundTracker(){
		return roundTracker;
	}
	public RoundResultSummary getResultSummary(){
		return roundTracker.getResultSummary();
	}
	
	public Player currentPlayer(){
		return getPlayer(roundTracker.whoseTurn());
	}
	/////////////////////////////////////////////////////////////////
	
	public PlayerBrain getControllerForPlayer(int playerNum){return playerTrackers[playerNum].getController();}
	public Player getPlayer(int playerNum){return playerTrackers[playerNum].getPlayer();}
	
	
	
	
	
	
	//this is dumb, but I don't want to mess with it
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
