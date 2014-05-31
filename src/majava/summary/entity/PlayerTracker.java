package majava.summary.entity;

import java.util.List;

import majava.Hand;
import majava.Meld;
import majava.Player;
import majava.Pond;
import majava.util.TileList;



public final class PlayerTracker{
	
	public final Player player;
	
	public final Hand hand;
	public final TileList tilesH;
	
	public final Pond pond;
	public final TileList tilesP;
	
	public final List<Meld> melds;
	
	//private Wind seatWind;
	//private int points;
	//private boolean riichiStatus;
	//private String playerName;
	
	//private List<Meld> melds = new ArrayList<Meld>(NUM_MELDS_TO_TRACK);
	
	public PlayerTracker(Player p, Hand ha, TileList tH, Pond po, TileList tP, List<Meld> ms){
		player = p;
		hand = ha; tilesH = tH;
		pond = po; tilesP = tP;
		melds = ms;
	}
}


