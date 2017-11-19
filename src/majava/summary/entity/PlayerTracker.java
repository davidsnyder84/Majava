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
	public final GameTileList handTiles;
	
	public PlayerTracker(Player p, Hand ha, GameTileList tH){
		player = p;
		hand = ha; handTiles = tH;
	}

	public final List<Meld> melds(){return player.getMelds();}
	public final Pond pond(){return player.getPond();}
	public final List<PondTile> pondTiles(){return pond().getTilesAsList();}
}


