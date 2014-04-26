package majava.control;

import java.util.ArrayList;

import majava.Hand;
import majava.MeldType;
import majava.Player;
import majava.tiles.Tile;
import majava.TileList;
import majava.tiles.PondTile;

import utility.GenSort;
import utility.MahList;


public class MajaPlay {
	

	public static final char ownerSeat = Player.SEAT_SOUTH;
	
	
	public static void main(String[] args) {
		
		
//		testCompleteNormal();
		
//		testCallPartners();
		
		//meldEnumPlay();
		
		
		
		//iterableTest();
		
		//testHots();
		
		//testContains();
		
		//testCalls();
		
		//testPlayerCall();
		
		//subListPlay();
		
		
		//testMeldMaking();
		
		
		//strToIdTest();
		
		//nextTileTest();
		
		//sleepTest();
		
		
		//callableIdTest();
		
		
		//intArrayCopyTest();
		
		
		//chiKamichaTest();
		
		//finishingMovePre();
		
		//finalListTest();
		
		
		//kokushiTenpaiTest();
		
//		chiitoiTenpaiTest();
		
		//mahListPlay();
		
//		listEqualsTest();
		
		
		playTileInheritance();
		
		
		System.out.println();
	}
	
	
	
	
	public static void playTileInheritance(){
		
		Tile t = new Tile(5);

		Tile pt = new PondTile(t);
		((PondTile) pt).setRiichiTile();
		((PondTile) pt).setCalled();
		
		
		
		TileList tlist = new TileList();
		tlist.add(t);
		tlist.add(pt);
		
		
		System.out.println(t.toString());
		
		System.out.println(pt.toString());
		System.out.println(((PondTile) pt).isRiichiTile());
		System.out.println(((PondTile) pt).wasCalled());
		
		
		System.out.println("\n\n\n");
		System.out.println(tlist.toString());
		
	}
	
	
	
	
	
	
	
	
	public static void testCompleteNormal(){
		
		Hand h = new Hand(ownerSeat);
		//Tile q = null;
		TileList waits = null;
		
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
		

		System.out.println("\nHand is complete normal?: " + h.mChecker.isCompleteNormal());
		
	}
	
	
	public static void chiitoiTenpaiTest(){
		
		Hand h = new Hand(ownerSeat);
		TileList waits = new TileList();
		
		TileList handTiles = new TileList(2,2,5,5,7,7,10,10,20,20,21,21,30,30);
		handTiles.remove(13);
		for (Tile t: handTiles) h.addTile(t);
		
		
		System.out.println(h.toString());
		

		System.out.println("\nIn tenpai for chiitoi?: " + h.mChecker.DEMOchiitoitsuInTenpai());
		waits = h.mChecker.getTenpaiWaits();
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

			System.out.println("\nIn tenpai for chiitoi?: " + h.mChecker.DEMOchiitoitsuInTenpai());
			waits = h.mChecker.getTenpaiWaits();
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

			System.out.println("\nIn tenpai for chiitoi?: " + h.mChecker.DEMOchiitoitsuInTenpai());
			waits = h.mChecker.getTenpaiWaits();
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
		h.addTile(new Tile("C1"));	//3
		h.addTile(new Tile("C9"));	//4
		h.addTile(new Tile("B1"));	//5
		h.addTile(new Tile("B9"));	//6
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
		

		System.out.println("\nIn tenpai for kokushi musou?: " + h.mChecker.isTenpaiKokushi());
		
		waits = h.mChecker.getKokushiWaits();
		System.out.print(waits.size() + "-sided wait: ");
		for (Tile t: waits)
			System.out.print(t.toString() + ", ");
		
		System.out.println("\n\nKokushi musou complete?: " + h.mChecker.isCompleteKokushi());
	}
	
	
	public static void testCallPartners(){

		final char ONWER_SEAT = Player.SEAT_SOUTH;
		Tile q = null;
		Hand h = new Hand(ONWER_SEAT);

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
			q.setOwner(Player.findKamichaOf(ONWER_SEAT));
			h.checkCallableTile(q);
			
			System.out.println(h.toString());
			System.out.println("\nDiscarded tile: " + q.toString());
			
			System.out.print("\n\tChi-L?: " + h.ableToChiL());
			if (h.ableToChiL())	System.out.print(", Partners: " + h.partnerIndicesString(MeldType.CHI_L, true) + ", Ind: " + h.partnerIndicesString(MeldType.CHI_L));
			
			System.out.print("\n\tChi-M?: " + h.ableToChiM());
			if (h.ableToChiM())	System.out.print(", Partners: " + h.partnerIndicesString(MeldType.CHI_M, true) + ", Ind: " + h.partnerIndicesString(MeldType.CHI_M));
			
			System.out.print("\n\tChi-H?: " + h.ableToChiH());
			if (h.ableToChiH())	System.out.print(", Partners: " + h.partnerIndicesString(MeldType.CHI_H, true) + ", Ind: " + h.partnerIndicesString(MeldType.CHI_H));
			
			System.out.print("\n\tPon?  : " + h.ableToPon());
			if (h.ableToPon())	System.out.print(", Partners: " + h.partnerIndicesString(MeldType.PON, true) + ", Ind: " + h.partnerIndicesString(MeldType.PON));

			System.out.print("\n\tKan?  : " + h.ableToKan());
			if (h.ableToKan())	System.out.print(", Partners: " + h.partnerIndicesString(MeldType.KAN, true) + ", Ind: " + h.partnerIndicesString(MeldType.KAN));
			
			System.out.print("\n\tPair? : " + h.ableToPair());
			if (h.ableToPair())	System.out.print(", Partners: " + h.partnerIndicesString(MeldType.PAIR, true) + ", Ind: " + h.partnerIndicesString(MeldType.PAIR));
			System.out.println("\n\n\n\n");
		}
		
	}
	
	
	public static void meldEnumPlay(){
		
		MeldType call = MeldType.CHI_H;
		int want = 3;
		MeldType wantCall = MeldType.values()[want];
		
		System.out.println("Call: " + call + ", " + call.ordinal());
		System.out.println("Want: " + wantCall + ", " + wantCall.ordinal());

		System.out.println("Call == Want?: " + (call == wantCall));
		System.out.println("Call > Want?: " + (call.compareTo(wantCall) > 0));
	}
	
	

	public static void finishingMovePre(){
		Hand h = new Hand(ownerSeat);
		Tile q = null;

		h.addTile(2);
		h.addTile(3);
		h.addTile(3);

		System.out.println(h.toString());
		
		
		q = new Tile(1);
		q.setOwner(Player.findKamichaOf(ownerSeat));
		System.out.println("\nDiscarded tile: " + q.toStringAllInfo());
		h.checkCallableTile(q);
		
		System.out.println("Number of calls possible: " + h.numberOfCallsPossible());
		
		
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
		q.setOwner('W');
		

		System.out.println("\nDiscarded tile: " + q.toStringAllInfo());
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
		
		ArrayList<Integer> hots = h.findAllHotTiles();
		ArrayList<Integer> callables = h.findAllCallableTiles();
		
		
		
		//sort the lists
		GenSort<Integer> sorter = new GenSort<Integer>(hots);sorter.sort();
		sorter = new GenSort<Integer>(callables);sorter.sort();
		

		System.out.println("\nHot Tiles: ");
		for (Integer i: hots)
			System.out.print(Tile.repr_stringReprOfId(i) + ", ");
		
		
		System.out.println("\n\nCallable Tiles: ");
		for (Integer i: callables)
			System.out.print(Tile.repr_stringReprOfId(i) + ", ");
		
	}
	
	
	
	
	
	

	public static void nextTileTest(){
		
		ArrayList<Tile> tiles = new ArrayList<Tile>(10);

		tiles.add(new Tile("B2"));
		tiles.add(new Tile("C6"));
		tiles.add(new Tile("C9"));
		tiles.add(new Tile("M1"));
		tiles.add(new Tile("M9"));
		tiles.add(new Tile("DG"));
		tiles.add(new Tile("DR"));
		tiles.add(new Tile("WN"));
		tiles.add(new Tile("WE"));
		tiles.add(new Tile("WW"));
		
		for (Tile t: tiles)
			System.out.println(t.toStringAllInfo() + "\n");
	}
	
	
	
	public static void strToIdTest(){
		System.out.println("ID of M2: " + Tile.repr_idOfStringRepr("M2"));
		System.out.println("ID of M9: " + Tile.repr_idOfStringRepr("M9"));
		System.out.println("ID of M1: " + Tile.repr_idOfStringRepr("M1"));
		System.out.println("ID of C9: " + Tile.repr_idOfStringRepr("C9"));
		System.out.println("ID of WE: " + Tile.repr_idOfStringRepr("WE"));
		System.out.println("ID of WN: " + Tile.repr_idOfStringRepr("WN"));
		System.out.println("ID of DG: " + Tile.repr_idOfStringRepr("DG"));
		System.out.println("ID of DR: " + Tile.repr_idOfStringRepr("DR"));
		System.out.println("ID of BO: " + Tile.repr_idOfStringRepr("BO"));
	}
	
	
	
	
	//probably broke this when clickable discards were implemented
	public static void testMeldMaking(){
		
		Player p = new Player(Player.SEAT_EAST);

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
			

			//probably broke this when clickable discards were implemented
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
			
			
			//probably broke this when clickable discards were implemented
//			p.takeTurn();
			
		}

		System.out.println();
		p.showHand();
		p.showMelds();

		
	}
	

	
	
	
	public static void testPlayerCall(){
		
		Player p = new Player(Player.SEAT_EAST);

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
		

		//probably broke this when clickable discards were implemented
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
		
		/*
		System.out.println("Chi-L?: " + h.canChiL(q));
		System.out.println("Chi-M?: " + h.canChiM(q));
		System.out.println("Chi-H?: " + h.canChiH(q));
		System.out.println("Pon?  : " + h.canPon(q));
		System.out.println("Kan?  : " + h.canKan(q));
		*/
		
	}
	
	
	
	
	
	
	
	public static void testContains(){
		
		ArrayList<Tile> tiles = new ArrayList<Tile>(0);
		
		Tile t2 = new Tile(2);
		
		tiles.add(new Tile(1));
		tiles.add(new Tile(2));
		tiles.add(new Tile(3));
		
		//System.out.println("\nt2 == M2?: ");
		//System.out.println(t2.equals(new Tile(2)));
		
		System.out.println(t2.toStringAllInfo());
		
		System.out.println("\nHand contains t22?: ");
		System.out.println(tiles.contains(t2));
	}
	
	
	
	
	public static void testHots(){
		Hand h = new Hand();

		h.addTile(new Tile(1));
		h.addTile(new Tile(2));
		
		
		ArrayList<Integer> hots = new ArrayList<Integer>(0);
		hots = h.findAllHotTiles();
		
		System.out.println(h.toString());
		
		System.out.println("\nhots:");
		
		for (Integer i: hots)
		{
			System.out.println((new Tile(i)).toString());
		}
	}
	
	
	
	
	
	
	

	public static void subListPlay(){
		
		ArrayList<Integer> list = new ArrayList<Integer>(9);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);

//		ArrayList<Integer> sublist = (ArrayList<Integer>)list.subList(0, list.size());
		ArrayList<Integer> sublist = new ArrayList<Integer>(list.subList(1, 4));
		
		for (Integer i: sublist)
			System.out.println(i);
	}
	
	public static void intArrayCopyTest(){

		ArrayList<Integer> list1 = new ArrayList<Integer>(5);
		list1.add(5);
		list1.add(6);
		list1.add(7);
		list1.add(8);
		list1.add(9);

		System.out.print("List1: "); for (Integer i: list1) System.out.print(i.toString() + ", ");
		
		
		ArrayList<Integer> list2 = list1;
		System.out.print("\nList2: "); for (Integer i: list2) System.out.print(i.toString() + ", ");
		
		
		
		/*
		//after list2 = list1
		//BAD - XXXX - Removing an object from either list removes it from both lists
		list2 = list1;
		list2.remove(2);
		System.out.print("\nList1: "); for (Integer i: list1) System.out.print(i.toString() + ", ");
		System.out.print("\nList2: "); for (Integer i: list2) System.out.print(i.toString() + ", ");
		*/
		
		
		/*
		//after list2 = list1.clone()
		//GOOD - Removing an object from either list does NOT affect the other list
		list2 = (ArrayList<Integer>)list1.clone();
		list1.remove(3);
		System.out.print("\n\nList1: "); for (Integer i: list1) System.out.print(i.toString() + ", ");
		System.out.print("\nList2: "); for (Integer i: list2) System.out.print(i.toString() + ", ");
		*/
		
		
		/*
		//after list2 = new arraylist(list1) copy constructor
		//GOOD - Removing an object from either list does NOT affect the other list
		list2 = new ArrayList<Integer>(list1);
		list2.remove(4);
		System.out.print("\n\nList1: "); for (Integer i: list1) System.out.print(i.toString() + ", ");
		System.out.print("\nList2: "); for (Integer i: list2) System.out.print(i.toString() + ", ");
		*/
		

		//after list2 = add 1 by 1 from list 1
		//GOOD - Removing an object from either list does NOT affect the other list
		list2 = new ArrayList<Integer>(0);for (int i = 0; i < list1.size(); i++) list2.add(list1.get(i));
		list1.remove(2);
		System.out.print("\n\nList1: "); for (Integer i: list1) System.out.print(i.toString() + ", ");
		System.out.print("\nList2: "); for (Integer i: list2) System.out.print(i.toString() + ", ");
	}
	public static void sleepTest(){
		
		final int SLEEP_AMOUNT = 1000;
		
		System.out.println("Hello sir!");
		try {Thread.sleep(SLEEP_AMOUNT);} 
		catch (InterruptedException e) {}
		
		System.out.println("I am");
		try {Thread.sleep(SLEEP_AMOUNT);}
		catch (InterruptedException e) {}
		
		System.out.println("very");
		try {Thread.sleep(SLEEP_AMOUNT);}
		catch (InterruptedException e) {}
		
		System.out.println("very...");
		try {Thread.sleep(SLEEP_AMOUNT);}
		catch (InterruptedException e) {}
		
		System.out.println("sleepy!");	
	}
	
	
	
	/*
	public static List<Integer> unlist;
	static{
		List<Integer> temp = new List<Integer>(3);
		temp.add(4);
		temp.add(6);
		temp.add(8);
		unlist = temp;
		unlist = Collections.unmodifiableList(temp);
	}
	*/
	public static void finalListTest(){
		
		ArrayList<Integer> prelist = new ArrayList<Integer>(5);
		prelist.add(5);
		prelist.add(6);
		prelist.add(7);
		prelist.add(8);
		prelist.add(9);
		final ArrayList<Integer> list1 = new ArrayList<Integer>(prelist);
		
		
		System.out.print("list1: "); for (Integer i: list1) System.out.print(i.toString() + ", ");
		
		list1.remove(2);
		System.out.print("\n\nList1: "); for (Integer i: list1) System.out.print(i.toString() + ", ");
	}
	
	
	public static void mahListPlay(){
		MahList<Integer> list = new MahList<Integer>();
		list.add(3);
		list.add(5);
		list.add(7);
		list.add(9);
		list.add(11);
		

		Integer[] intArray = new Integer[5];
		intArray[0] = 2;
		intArray[1] = 4;
		intArray[2] = 6;
		intArray[3] = 8;
		intArray[4] = 10;
		
		MahList<Integer> sublist = new MahList<Integer>(4, 7, 3, 1, 5);
		sublist.sort();
		
		
		
		System.out.print("list: ");
		for (Integer i: list)
			System.out.print(i.toString() + ", ");

		System.out.print("\nsub : ");
		for (Integer i: sublist)
			System.out.print(i.toString() + ", ");
		
		
		
		sublist.add(6);
		sublist.add(2);
		sublist.sort();
		System.out.print("\nsub : ");
		for (Integer i: sublist)
			System.out.print(i.toString() + ", ");
		
	}
	
	
	public static void iterableTest(){
		Hand h = new Hand(ownerSeat);
		h.addTile(new Tile("M1"));	//1
		h.addTile(new Tile("M9"));	//2
		h.addTile(new Tile("C1"));	//3
		h.addTile(new Tile("C9"));	//4
		h.addTile(new Tile("B1"));	//5
		
		for (Tile t: h) System.out.println(t.toString());
	}

	
	public static void listEqualsTest(){
		
		TileList handTiles = new TileList(2,2,5,5,7,7,10,10,20,20,21,21,30,30);
//		handTiles = new TileList(2,2,5,5,7,7,10,10,20,20,21,21,30,30);
		
		TileList evenTiles = handTiles.getMultiple(0,2,4,6,8,10,12);
		TileList oddTiles = handTiles.getMultiple(1,3,5,7,9,11,13);
		
		oddTiles.get(1).setRedDora();
		oddTiles.get(2).setOwner('N');
		
		System.out.println("List1: " + evenTiles.toString());
		System.out.println("List2: " + oddTiles.toString());
		System.out.println("List1 equals List2?: " + evenTiles.equals(oddTiles));
		

		TileList dupes = new TileList(2,2,2,3,4,4,5,6,6,6,7);
		TileList noDupes = dupes.makeCopyNoDuplicates();
		System.out.println("\n\nYedupes: " + dupes.toString());
		System.out.println("Nodupes: " + noDupes.toString());
	}
	
	
}
