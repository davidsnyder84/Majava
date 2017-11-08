package majava.summary.entity;

import java.util.List;

import majava.hand.Hand;
import majava.hand.Meld;
import majava.Pond;
import majava.player.Player;
import majava.tiles.PondTile;
import majava.util.GameTileList;



public final class PlayerTracker{
	
	public final Player player;
	
	public final Hand hand;
	public final GameTileList tilesH;
	
	public final Pond pond;
	public final List<PondTile> tilesP;
	
	public final List<Meld> melds;
	
	//private Wind seatWind;
	//private int points;
	//private boolean riichiStatus;
	//private String playerName;
	
	//private List<Meld> melds = new ArrayList<Meld>(NUM_MELDS_TO_TRACK);
	
	public PlayerTracker(Player p, Hand ha, GameTileList tH, Pond po, List<PondTile> tP, List<Meld> ms){
		player = p;
		hand = ha; tilesH = tH;
		pond = po; tilesP = tP;
		melds = ms;
	}
}


