package majava.control.testcode;

import majava.enums.Wind;
import majava.hand.AgariHand;
import majava.hand.Hand;
import majava.tiles.GameTile;
import majava.util.GTL;

public class YakuGenerator {
	public static final Wind OWNER_SEAT = Wind.SOUTH;
	
	
	
	public static void main(String[] args){
		println(generateToitoiHandSpecific().toString());
	}
	
	
	
	public static AgariHand generateToitoiHandSpecific(){
		GTL tiles = new GTL(1,1,1,5,5,5,9,9,9,30,30,30,33,33);
		
		Hand hand = new Hand(tiles.withWind(OWNER_SEAT)).setOwnerSeatWind(OWNER_SEAT);
		
		int ponIndex = 2;
		GameTile ponTile = hand.getTile(ponIndex).withOwnerWind(OWNER_SEAT.kamichaWind());
		hand = hand.removeTile(ponIndex).makeMeldPon(ponTile);
		
//		println(hand.toString());
//		println(hand.getAsStringMelds());
		
//		GameTile ronTile = hand.removeTile(hand.size()-1);
		int ronIndex = 6;
		GameTile ronTile = hand.getTile(ronIndex).withOwnerWind(OWNER_SEAT.kamichaWind());
		hand = hand.removeTile(ronIndex);
		
		GameTile agarihai = ronTile;
		AgariHand ah = new AgariHand(hand, agarihai);
//		println("uh");
//		println(ah.toString());
		
		println(ah.size() +"");
		
		return ah;
	}
	
	
	
	
	public static void println(String prints){System.out.println(prints);}public static void println(){println("");}public static void print(String prints){System.out.print(prints);}
}
