package majava.control.testcode;

import majava.enums.Wind;
import majava.hand.AgariHand;
import majava.hand.Hand;
import majava.tiles.GameTile;

public class AgariHandTester {
	
	public static final Wind OWNER_WIND = Wind.SOUTH;

	public static void main(String[] s){
		Hand h = DemoHandGen.generateRandomHand();
		h.removeTile(0);
		GameTile winningTile = new GameTile(3);
		winningTile.setOwner(OWNER_WIND.kamichaWind());
		
		Hand ah = new AgariHand(h, winningTile);
//		AgariHand ah = new AgariHand(h, winningTile);
		
		h.addTile(winningTile);h.sort();
		
		System.out.println("Sizes:" + h.size() + " " + ah.size());
		System.out.println(h + "\n\n" + ah);
		
		
		System.out.println(h.getOwnerSeatWind());
		System.out.println(ah.getOwnerSeatWind());
	}
	
}
