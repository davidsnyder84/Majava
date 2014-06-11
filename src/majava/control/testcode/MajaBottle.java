package majava.control.testcode;

import java.util.ArrayList;

import utility.Pauser;
import majava.Player;
import majava.Wall;
import majava.tiles.GameTile;



public class MajaBottle {
	
	
	public static void main(String[] args) {
		
//		bottleWall();
		
		bottleReaction();
	}
	
	
	
	//all of the bottleneck is in (p1.addTileToHand(t);p1.takeTurn();)
	public static void bottleReaction(){
		Player p1 = Majenerator.generatePlayer(0);
		Player p2 = Majenerator.generatePlayer(1);
		Player p3 = Majenerator.generatePlayer(2);
		Player p4 = Majenerator.generatePlayer(3);
		GameTile t = null;
		
		p1.DEMOfillHandNoTsumo();
		p2.DEMOfillHandNoTsumo();
		p3.DEMOfillHandNoTsumo();
		p4.DEMOfillHandNoTsumo();
		

		Pauser pauser = new Pauser(0);
		
		for (int i = 1; i <= 50000; i++){
			Player p = new Player();
			p.setSeatWindEast();
			for (int j = 0; j < 13; j++){
				t = new GameTile(j % 34);
				
//				p.takeTurn();
				p.addTileToHand(t);
				p.sortHand();
				
//				p1.addTileToHand(t);p1.takeTurn();
//				p2.addTileToHand(t);p2.takeTurn();
//				p3.addTileToHand(t);p3.takeTurn();
//				p4.addTileToHand(t);p4.takeTurn();
				
//				p1.reactToDiscard(t);
				p2.reactToDiscard(t);
				p3.reactToDiscard(t);
				p4.reactToDiscard(t);
				
//				pauser.pauseWait();
			}
			System.out.println(i);
		}
	}
	
	
	//no bottle
	public static void bottleWall(){
		int num = 0;
		
		for (num = 1; num <= 5000; num++){
			Wall w = new Wall();
//			for (int i = 0; i < 10000; i++)
			w.getStartingHands(new ArrayList<GameTile>(), new ArrayList<GameTile>(), new ArrayList<GameTile>(), new ArrayList<GameTile>());
//			w.getStartingHands(new TileList(), new TileList(), new TileList(), new TileList());
			System.out.println(num);
		}
		
		System.out.println();
	}
}
