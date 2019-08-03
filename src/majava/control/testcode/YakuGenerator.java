package majava.control.testcode;

import majava.enums.Wind;
import majava.hand.AgariHand;
import majava.hand.Hand;
import majava.tiles.GameTile;
import majava.util.GameTileList;

public class YakuGenerator {
	public static final Wind OWNER_SEAT = Wind.SOUTH;
	
	
	
	public static void main(String[] args){
		generateToitoiHandSpecific();
	}
	
	
	
	public static AgariHand generateToitoiHandSpecific(){
		AgariHand ah = null;
		GameTile agarihai = null;
		
		Hand hand = new Hand();
		hand.setOwnerSeatWind(OWNER_SEAT);
		GameTileList tiles = new GameTileList(1,1,1,5,5,5,9,9,9,30,30,30,33,33);
		
		
		for (GameTile t: tiles){
			t.setOwner(OWNER_SEAT);
			hand.addTile(t);
		}
		
		GameTile ponTile = hand.removeTile(2);
		ponTile.setOwner(OWNER_SEAT.kamichaWind());
		hand.makeMeldPon(ponTile);
		
//		println(hand.toString());
//		println(hand.getAsStringMelds());
		
//		GameTile ronTile = hand.removeTile(hand.size()-1);
		GameTile ronTile = hand.removeTile(6);
		ronTile.setOwner(OWNER_SEAT.kamichaWind());
		
		agarihai = ronTile;
		ah = new AgariHand(hand, agarihai);
//		println("uh");
//		println(ah.toString());
		
		
		return ah;
	}
	
	
	
	
	public static void println(String prints){System.out.println(prints);}public static void println(){println("");}public static void print(String prints){System.out.print(prints);}
}
