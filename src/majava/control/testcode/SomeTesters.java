package majava.control.testcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.jws.soap.SOAPBinding;

import majava.hand.AgariHand;
import majava.hand.Hand;
import majava.hand.Meld;
import majava.Pond;
import majava.RoundResult;
import majava.util.GTL;
import majava.util.YakuList;
import majava.wall.Wall;
import majava.yaku.Yaku;
import majava.enums.MeldType;
import majava.enums.Wind;
import majava.player.Player;
import majava.pond.RiverWalker;
import majava.summary.PaymentMap;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.tiles.Janpai;
import majava.tiles.TileInterface;

public class SomeTesters {
	
	
	public static void main(String[] args){
		
		
//		testPond();
		
		testWall();
	}
	
	
	
	
	
	
	
	public static void testWall(){
		Wall wall = new Wall();
		showWall(wall);
		
		for (int i = 0; i < 5; i++){
			wall = wall.removeNextTile();
			showWall(wall);
		}
		
		wall = wall.removeNextMultiple(10);
		showWall(wall);
	}
	public static void showWall(Wall wall){
		println(wall.toString() + "\n\n" + wallIdsToString(wall));
		println("\n\n\n\n\n");
		waitt();
	}
	public static String wallIdsToString(Wall wall){
		String str = "";
		for (GameTile t : wall.getTiles()) if (t!=null) str += t.getWallID() + "  ";
		return str;
	}
	
	
	
	
	public static void testPond(){
		
		testPonds(Majenerator.generatePonds());
		testPonds(Majenerator.generatePondsEmpty());
		testPonds(Majenerator.generatePondsFew());
		testPonds(Majenerator.generatePondsOneTile());
		testPonds(Majenerator.generatePondsWithCallsFew());
		testPonds(Majenerator.generatePondsWithCallsFewAfterCallButBeforeDiscard());
		
		
		testPonds(Majenerator.generatePondsWithCalls());
		
		
	}
	
//	public static void testPond(Pond[][] pondtests){
	public static void testPonds(Pond[]... pondtests){
		for (Pond[] pt : pondtests){
			RiverWalker rw = new RiverWalker(pt);
			println(rw.toString() + "\n-----------------------------------------\n\n\n\n\n\n\n\n\n\n\n\n\n");
		}
	}
	
	
	
	
	public static void println(String prints){System.out.println(prints);}public static void println(){System.out.println("");}public static void println(int prints){System.out.println(prints+"");} public static void waitt(){(new Scanner(System.in)).next();}
}
