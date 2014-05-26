package majava.control.testcode;

import java.util.ArrayList;

import majava.Hand;
import majava.MeldType;
import majava.Player;
import majava.Wind;
import majava.tiles.Tile;
import majava.TileList;

import utility.GenSort;
import utility.MahList;



//code to test various functions
public class MajaPlay {
	
	public static final Wind ownerSeat = Wind.SOUTH;
	
	
	public static void main(String[] args) {
		
		
//		testCompleteNormal();
		
//		testCallPartners();
		
		

//		nextTileTest();
		
		//testHots();
		
		//testContains();
		
		//testCalls();
		
		//testPlayerCall();
		
		
		//testMeldMaking();
		
		
		//nextTileTest();
		
		
		//callableIdTest();
		
		
		//chiKamichaTest();
		
		//finishingMovePre();
		
		
		
		//kokushiTenpaiTest();
		
//		chiitoiTenpaiTest();
		
		
		playFinishingMelds();
		
		System.out.println();
	}
	
	
	
	public static void playFinishingMelds(){
		
		Hand h = new Hand(ownerSeat);
		
		
		for (int i = 1; i <= 9; i++){
			h = new Hand(ownerSeat);
			h.DEMOfillChuuren(i);
			
			System.out.println("\n\n\n" + h.toString());
			System.out.println("Complete?: " + h.DEMOgetChecker().isCompleteNormal());
			h.DEMOgetChecker().DEMOprintFinishingMelds();
		}
		
	}
	
	
	
	
	
	
	
	public static void testCompleteNormal(){
		
		Hand h = new Hand(ownerSeat);
		//Tile q = null;
//		TileList waits = null;
		
		h.addTile(new Tile(1));
		h.addTile(new Tile(1));
		h.addTile(new Tile(1));
		h.addTile(new Tile(2));
		h.addTile(new Tile(3));
		h.addTile(new Tile(4));
		h.addTile(new Tile(5));
		h.addTile(new Tile(5));
		h.sortHand();
		

		System.out.println(h.toString());
		

		System.out.println("\nHand is complete normal?: " + h.DEMOgetChecker().isCompleteNormal());
		
	}
	
	
	public static void chiitoiTenpaiTest(){
		
		Hand h = new Hand(ownerSeat);
		TileList waits = new TileList();
		
		TileList handTiles = new TileList(2,2,5,5,7,7,10,10,20,20,21,21,30,30);
		handTiles.remove(13);
		for (Tile t: handTiles) h.addTile(t);
		
		
		System.out.println(h.toString());
		

		System.out.println("\nIn tenpai for chiitoi?: " + h.DEMOgetChecker().DEMOchiitoitsuInTenpai());
		waits = h.DEMOgetChecker().getTenpaiWaits();
		if (waits != null && !waits.isEmpty()) System.out.print("Wait (" + waits.size() + " waits): " + waits.getFirst().toString());
		System.out.println("\n\n\n");
		

		//chiitoi tenpai hands (should show true)
		System.out.println("\n\nGOOD TENPAIS=====================\n");
		ArrayList<TileList> tlists = new ArrayList<TileList>(8);
		tlists.add(new TileList(1,1,3,3,5,5,7,7,9,9,11,11,13));
		tlists.add(new TileList(1,1,3,3,5,5,7,7,9,9,11,13,13));
		tlists.add(new TileList(1,1,3,3,5,5,7,7,9,11,11,13,13));
		tlists.add(new TileList(1,1,3,3,5,5,7,9,9,11,11,13,13));
		tlists.add(new TileList(1,1,3,3,5,7,7,9,9,11,11,13,13));
		tlists.add(new TileList(1,1,3,5,5,7,7,9,9,11,11,13,13));
		tlists.add(new TileList(1,3,3,5,5,7,7,9,9,11,11,13,13));
		
		for (TileList tl: tlists){
			h = new Hand(ownerSeat);
			for (Tile t: tl) h.addTile(t);

			System.out.println(h.toString());

			System.out.println("\nIn tenpai for chiitoi?: " + h.DEMOgetChecker().DEMOchiitoitsuInTenpai());
			waits = h.DEMOgetChecker().getTenpaiWaits();
			if (waits != null && !waits.isEmpty()) System.out.print("Wait (" + waits.size() + " waits): " + waits.getFirst().toString());
			System.out.println("\n\n\n");
		}
		
		
		
		//NOT chiitoi tenpai hands (should show false)
		System.out.println("\n\nBAD TENPAIS=====================\n");
		ArrayList<TileList> badtlists = new ArrayList<TileList>(8);
		badtlists.add(new TileList(1,1,1,1,5,5,7,7,9,9,11,11,13));
		badtlists.add(new TileList(1,1,1,1,5,6,7,7,9,9,11,11,13,13));
		badtlists.add(new TileList(1,1,5,5,7,7,9,9,11,11,13));
		badtlists.add(new TileList(1,1,5));
		badtlists.add(new TileList(new Tile(1)));
		badtlists.add(new TileList(1,1,3,3,5,5,7,7,9,9,11,11,13,13));	//is actually chiitoi complete
		
		for (TileList tl: badtlists){
			h = new Hand(ownerSeat);
			for (Tile t: tl) h.addTile(t);

			System.out.println(h.toString());

			System.out.println("\nIn tenpai for chiitoi?: " + h.DEMOgetChecker().DEMOchiitoitsuInTenpai());
			waits = h.DEMOgetChecker().getTenpaiWaits();
			if (waits != null && !waits.isEmpty()) System.out.print("Wait (" + waits.size() + " waits): " + waits.getFirst().toString());
			System.out.println("\n\n\n");
		}
		
		
		
//		System.out.println("\n\nChiitoi complete?: " + h.mChecker.chiitoitsuIsComplete());
		
	}
	

	public static void kokushiTenpaiTest(){
		
		Hand h = new Hand(ownerSeat);
		//Tile q = null;
		TileList waits = null;

		h.addTile(new Tile("M1"));	//1
		h.addTile(new Tile("M9"));	//2
		h.addTile(new Tile("P1"));	//3
		h.addTile(new Tile("P9"));	//4
		h.addTile(new Tile("S1"));	//5
		h.addTile(new Tile("S9"));	//6
		h.addTile(new Tile("WE"));	//7
		h.addTile(new Tile("WS"));	//8
		h.addTile(new Tile("WW"));	//9
		h.addTile(new Tile("WN"));	//10
		h.addTile(new Tile("DW"));	//11
		h.addTile(new Tile("DG"));	//12
		h.addTile(new Tile("DR"));	//13
//		h.addTile(new Tile("m8"));	//14
//		h.addTile(new Tile("DR"));	//extra tile
		h.sortHand();
		
		System.out.println(h.toString());
		

		System.out.println("\nIn tenpai for kokushi musou?: " + h.DEMOgetChecker().isTenpaiKokushi());
		
		waits = h.DEMOgetChecker().DEMOgetKokushiWaits();
		System.out.print(waits.size() + "-sided wait: ");
		for (Tile t: waits)
			System.out.print(t.toString() + ", ");
		
		System.out.println("\n\nKokushi musou complete?: " + h.DEMOgetChecker().isCompleteKokushi());
	}
	
	
	public static void testCallPartners(){
		
		Tile q = null;
		Hand h = new Hand(ownerSeat);

		h.addTile(1);
		h.addTile(2);
		h.addTile(2);
		h.addTile(3);
		h.addTile(4);
		h.addTile(4);
		h.addTile(4);
		h.addTile(5);
		h.addTile(6);
		h.addTile(32);
		h.addTile(32);
		h.addTile(33);
		
		MahList<Integer> discardIDs = new MahList<Integer>(1,2,3,4,5,6,7,8,9,10,11,30,31,32,33,34);
		
		
		for (Integer id: discardIDs)
		{
			q = new Tile(id);
			q.setOwner(ownerSeat.kamichaWind());
			h.checkCallableTile(q);
			
			System.out.println(h.toString());
			System.out.println("\nDiscarded tile: " + q.toString());
			
			System.out.print("\n\tChi-L?: " + h.ableToChiL());
			if (h.ableToChiL())	System.out.print(", Partners: " + h.DEMOpartnerIndicesString(MeldType.CHI_L, true) + ", Ind: " + h.DEMOpartnerIndicesString(MeldType.CHI_L));
			
			System.out.print("\n\tChi-M?: " + h.ableToChiM());
			if (h.ableToChiM())	System.out.print(", Partners: " + h.DEMOpartnerIndicesString(MeldType.CHI_M, true) + ", Ind: " + h.DEMOpartnerIndicesString(MeldType.CHI_M));
			
			System.out.print("\n\tChi-H?: " + h.ableToChiH());
			if (h.ableToChiH())	System.out.print(", Partners: " + h.DEMOpartnerIndicesString(MeldType.CHI_H, true) + ", Ind: " + h.DEMOpartnerIndicesString(MeldType.CHI_H));
			
			System.out.print("\n\tPon?  : " + h.ableToPon());
			if (h.ableToPon())	System.out.print(", Partners: " + h.DEMOpartnerIndicesString(MeldType.PON, true) + ", Ind: " + h.DEMOpartnerIndicesString(MeldType.PON));

			System.out.print("\n\tKan?  : " + h.ableToKan());
			if (h.ableToKan())	System.out.print(", Partners: " + h.DEMOpartnerIndicesString(MeldType.KAN, true) + ", Ind: " + h.DEMOpartnerIndicesString(MeldType.KAN));
			
			System.out.print("\n\tPair? : " + h.ableToPair());
			if (h.ableToPair())	System.out.print(", Partners: " + h.DEMOpartnerIndicesString(MeldType.PAIR, true) + ", Ind: " + h.DEMOpartnerIndicesString(MeldType.PAIR));
			System.out.println("\n\n\n\n");
		}
		
	}
	
	

	public static void finishingMovePre(){
		Hand h = new Hand(ownerSeat);
		Tile q = null;

		h.addTile(2);
		h.addTile(3);
		h.addTile(3);

		System.out.println(h.toString());
		
		
		q = new Tile(1);
		q.setOwner(ownerSeat.kamichaWind());
//		System.out.println("\nDiscarded tile: " + q.toStringAllInfo());
		System.out.println("\nDiscarded tile: " + q.toString());
		h.checkCallableTile(q);
		
		
		
		int count = 0;
		if (h.ableToChiL()) count++;
		if (h.ableToChiM()) count++;
		if (h.ableToChiH()) count++;
		if (h.ableToPon()) count++;
		if (h.ableToKan()) count++;
		if (h.ableToRon()) count++;
		
		System.out.println("Number of calls possible: " + count);
		
		
	}
	
	

	public static void chiKamichaTest(){
		Hand h = new Hand(ownerSeat);
		Tile q = null;

		h.addTile(2);
		h.addTile(3);
		h.addTile(3);
		

		System.out.println(h.toString());
		
		
		
		q = new Tile(1);
//		q.setOwner(ownerSeat);
//		q.setOwner(Player.findKamichaOf(ownerSeat));
		q.setOwner(Wind.WEST);
		

//		System.out.println("\nDiscarded tile: " + q.toStringAllInfo());
		System.out.println("\nDiscarded tile: " + q.toString());
		System.out.println("Callable?: " + h.checkCallableTile(q));
		
	}
	
	
	
	
	
	


	public static void callableIdTest(){
		
		Hand h = new Hand();
		

		h.addTile(1);
		h.addTile(2);
		h.addTile(3);
		h.addTile(4);
		h.addTile(5);
		h.addTile(6);
		h.addTile(7);
		h.addTile(8);
		h.addTile(9);
		/*
		h.addTile(2);
		h.addTile(2);
		h.addTile(3);
		h.addTile(4);
		h.addTile(4);
		h.addTile(4);
		h.addTile(5);
		h.addTile(6);
		*/
		

		System.out.println(h.toString());
		
		ArrayList<Integer> hots = h.DEMOfindAllHotTiles();
		ArrayList<Integer> callables = h.DEMOfindAllCallableTiles();
		
		
		
		//sort the lists
		GenSort<Integer> sorter = new GenSort<Integer>(hots);sorter.sort();
		sorter = new GenSort<Integer>(callables);sorter.sort();
		

		System.out.println("\nHot Tiles: ");
		for (Integer i: hots)
//			System.out.print(Tile.repr_stringReprOfId(i) + ", ");
			System.out.print((new Tile(i)).toString() + ", ");
		
		
		System.out.println("\n\nCallable Tiles: ");
		for (Integer i: callables)
//			System.out.print(Tile.repr_stringReprOfId(i) + ", ");
			System.out.print((new Tile(i)).toString() + ", ");
		
	}
	
	
	
	
	
	

	public static void nextTileTest(){
		
		ArrayList<Tile> tiles = new ArrayList<Tile>(10);

		tiles.add(new Tile("S2"));
		tiles.add(new Tile("P6"));
		tiles.add(new Tile("P9"));
		tiles.add(new Tile("M1"));
		tiles.add(new Tile("M9"));
		tiles.add(new Tile("DG"));
		tiles.add(new Tile("DR"));
		tiles.add(new Tile("WN"));
		tiles.add(new Tile("WE"));
		tiles.add(new Tile("WW"));
		
		for (Tile t: tiles)
			System.out.println(t.toString() + ", next tile: " + t.nextTile().toString());
	}
	
	
	
	
	
	//broke this when clickable discards were implemented
	public static void testMeldMaking(){
		
		Player p = new Player();
		p.setSeatWindEast();

		p.addTileToHand(2);
		p.addTileToHand(3);
		p.addTileToHand(7);
		p.addTileToHand(7);
		p.addTileToHand(10);
		p.addTileToHand(11);
		p.addTileToHand(15);
		p.addTileToHand(17);
		p.addTileToHand(25);
		p.addTileToHand(25);
		p.addTileToHand(33);
		p.addTileToHand(34);
		p.addTileToHand(34);
		
		
		Tile q = null;
		q = new Tile(4);
		
		ArrayList<Tile> discards = new ArrayList<Tile>(10);
		discards.add(new Tile(12));
		discards.add(new Tile(34));
		discards.add(new Tile(7));
		discards.add(new Tile(25));
		discards.add(new Tile(1));
		discards.add(new Tile(16));
		
		discards.get(1).setOwner(p.getSeatWind());
		
		
		
		while (discards.isEmpty() == false)
		{
			q = discards.get(0);
			discards.remove(0);

			System.out.println();
			p.showHand();
			p.showMelds();
			System.out.println("\nDiscarded tile: " + q.toString() + "\n");
			

			//broke this when clickable discards were implemented
//			p.reactToDiscard(q);
			p.makeMeld(q);
			
			
			//broke this when reduced coupling in Player
//			String whatCalled = "nooooo";
//			int status = p.checkCallStatus();
//			if (status == Player.CALLED_NONE)
//				whatCalled = "None";
//			if (status == Player.CALLED_CHI_L)
//				whatCalled = "Chi-L";
//			else if (status == Player.CALLED_CHI_M)
//				whatCalled = "Chi-M";
//			else if (status == Player.CALLED_CHI_H)
//				whatCalled = "Chi-H";
//			else if (status == Player.CALLED_PON)
//				whatCalled = "Pon";
//			else if (status == Player.CALLED_KAN)
//				whatCalled = "Kan";
//			else if (status == Player.CALLED_RON)
//				whatCalled = "Ron";
//			System.out.println("Player's call status: " + whatCalled + " (status: " + status + ")");
			
			
			//broke this when clickable discards were implemented
//			p.takeTurn();
			
		}

		System.out.println();
		p.showHand();
		p.showMelds();

		
	}
	

	
	

	//broke this when clickable discards were implemented
	public static void testPlayerCall(){
		
		Player p = new Player();
		p.setSeatWindEast();

		p.addTileToHand(2);
		p.addTileToHand(2);
		p.addTileToHand(3);
		p.addTileToHand(4);
		p.addTileToHand(4);
		p.addTileToHand(4);
		p.addTileToHand(5);
		p.addTileToHand(6);
		
		
		Tile q = null;
		q = new Tile(4);
		
		p.showHand();
		System.out.println("\nDiscarded tile: " + q.toString() + "\n");
		

		//broke this when clickable discards were implemented
//		p.reactToDiscard(q);
		
		//broke this when reduced coupling in Player
//		String whatCalled = "nooooo";
//		int status = p.checkCallStatus();
//
//		if (status == Player.CALLED_NONE)
//			whatCalled = "None";
//		if (status == Player.CALLED_CHI_L)
//			whatCalled = "Chi-L";
//		else if (status == Player.CALLED_CHI_M)
//			whatCalled = "Chi-M";
//		else if (status == Player.CALLED_CHI_H)
//			whatCalled = "Chi-H";
//		else if (status == Player.CALLED_PON)
//			whatCalled = "Pon";
//		else if (status == Player.CALLED_KAN)
//			whatCalled = "Kan";
//		else if (status == Player.CALLED_RON)
//			whatCalled = "Ron";
//		
//		System.out.println("Player's call status: " + whatCalled + " (status: " + status + ")");
		
	}
	
	
	
	public static void testCalls(){
		
		Tile q = null;
		Hand h = new Hand();
		
		
		h.addTile(new Tile(12));
		h.addTile(new Tile(10));
		
		
		q = new Tile(11);
		

		System.out.println(h.toString());
		System.out.println("\nDiscarded tile: " + q.toString() + "\n");
		
		
//		System.out.println("Chi-L?: " + h.canChiL(q));
//		System.out.println("Chi-M?: " + h.canChiM(q));
//		System.out.println("Chi-H?: " + h.canChiH(q));
//		System.out.println("Pon?  : " + h.canPon(q));
//		System.out.println("Kan?  : " + h.canKan(q));
		
		
	}
	
	
	
	
	
	
	
	public static void testContains(){
		
		ArrayList<Tile> tiles = new ArrayList<Tile>(0);
		
		Tile t2 = new Tile(2);
		
		tiles.add(new Tile(1));
		tiles.add(new Tile(2));
		tiles.add(new Tile(3));
		
		//System.out.println("\nt2 == M2?: ");
		//System.out.println(t2.equals(new Tile(2)));
		
//		System.out.println(t2.toStringAllInfo());
		
		System.out.println("\nHand contains t22?: ");
		System.out.println(tiles.contains(t2));
	}
	
	
	
	
	public static void testHots(){
		Hand h = new Hand();

		h.addTile(new Tile(1));
		h.addTile(new Tile(2));
		
		
		ArrayList<Integer> hots = new ArrayList<Integer>(0);
		hots = h.DEMOfindAllHotTiles();
		
		System.out.println(h.toString());
		
		System.out.println("\nhots:");
		
		for (Integer i: hots)
		{
			System.out.println((new Tile(i)).toString());
		}
	}
	
	
	
	
	
	
	
	

	
	public static void listEqualsTest(){
		
		TileList handTiles = new TileList(2,2,5,5,7,7,10,10,20,20,21,21,30,30);
//		handTiles = new TileList(2,2,5,5,7,7,10,10,20,20,21,21,30,30);
		
		TileList evenTiles = handTiles.getMultiple(0,2,4,6,8,10,12);
		TileList oddTiles = handTiles.getMultiple(1,3,5,7,9,11,13);
		
//		oddTiles.get(1).setRedDora();
		oddTiles.get(2).setOwner(Wind.NORTH);
		
		System.out.println("List1: " + evenTiles.toString());
		System.out.println("List2: " + oddTiles.toString());
		System.out.println("List1 equals List2?: " + evenTiles.equals(oddTiles));
		

		TileList dupes = new TileList(2,2,2,3,4,4,5,6,6,6,7);
		TileList noDupes = dupes.makeCopyNoDuplicates();
		System.out.println("\n\nYedupes: " + dupes.toString());
		System.out.println("Nodupes: " + noDupes.toString());
	}
	
	
}