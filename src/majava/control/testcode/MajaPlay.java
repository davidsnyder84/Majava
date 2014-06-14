package majava.control.testcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import majava.Hand;
import majava.RoundResult;
import majava.enums.MeldType;
import majava.Player;
import majava.enums.Wind;
import majava.tiles.GameTile;
import majava.tiles.HandCheckerTile;
import majava.tiles.ImmutableTile;
import majava.tiles.PondTile;
import majava.tiles.TileInterface;
import majava.util.GameTileList;



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
		
		
		
		kokushiTenpaiTest();
		
//		chiitoiTenpaiTest();
		
		
//		playFinishingMelds();
		
		
//		playShowRoundResultInfo();
//		listEqualsTest();
		
//		playTileClone();
		
//		playNewTileInhertance();
		
	}
	public static void println(String prints){System.out.println(prints);}public static void println(){println("");}
	
	
	
	
	

	public static void playNewTileInhertance(){
		
		
		TileInterface ti = ImmutableTile.retrieveTile(34);;
		
		ImmutableTile im = ImmutableTile.retrieveTile(1);
		
		GameTile gt = new GameTile(2);
		
		HandCheckerTile ht = new HandCheckerTile(new GameTile(3));
		PondTile pt = new PondTile(new GameTile(4));
		
		
//		ti = im;
//		ti = gt;
//		ti = ht;
//		ti = pt;
		
//		gt = ht;
//		gt = pt;
		
		
		
		

		
		
		
		List<TileInterface> listTI = new ArrayList<TileInterface>();
		List<ImmutableTile> listIM = new ArrayList<ImmutableTile>();
		List<GameTile> listGT = new ArrayList<GameTile>();
		List<HandCheckerTile> listHT = new ArrayList<HandCheckerTile>();
		List<PondTile> listPT = new ArrayList<PondTile>();
		
		List<? extends TileInterface> listInterface = new ArrayList<TileInterface>();
		listInterface = new ArrayList<GameTile>();
		
		listInterface = listTI;
		listInterface = listIM;
		listInterface = listGT;
		listInterface = listHT;
		listInterface = listPT;
		
		
		

		listTI.add(ti);
		listTI.add(im);
		listTI.add(gt);
		listTI.add(ht);
		listTI.add(pt);
		
		
		println("ti" + ",  " + "im" + ",  " + "gt" + ",  " + "ht" + ",  " + "pt" + ",  " + "");
		println(ti + ",  " + im + ",  " + gt + ",  " + ht + ",  " + pt + ",  " + "");
		
		println("");
		for (TileInterface t: listTI) println(t.toString());
	}
	
	
	
	
	public static void playShowRoundResultInfo(){
		
		RoundResult res;
		res = Majenerator.generateRoundResult();
		
		println(res + "\n");
		
		println(res.getAsStringWinningHand());
		
		println("Winner: " + res.getWinningPlayer());
		if (res.isVictoryRon()){
			println("---Ron!");
			println("---Furikon: " + res.getFurikondaPlayer());
		}
		else if (res.isVictoryTsumo()){
			println("---Tsumo!");
		}
		
		println("\nWinning Tile: " + res.getWinningTile());
		
		println("\n" + res.getAsStringPayments());
	}
	
	
	
	
	
	public static void playFinishingMelds(){
		
		Hand h = new Hand(ownerSeat);
		
		
		for (int i = 1; i <= 9; i++){
			h = new Hand(ownerSeat);
			h.DEMOfillChuuren(i);
			
			println("\n\n\n" + h.toString());
			println("Complete?: " + h.DEMOgetChecker().isCompleteNormal());
			h.DEMOgetChecker().DEMOprintFinishingMelds();
		}
		
	}
	
	
	
	
	
	
	
	public static void testCompleteNormal(){
		
		Hand h = new Hand(ownerSeat);
		//Tile q = null;
//		TileList waits = null;
		
		h.addTile(new GameTile(1));
		h.addTile(new GameTile(1));
		h.addTile(new GameTile(1));
		h.addTile(new GameTile(2));
		h.addTile(new GameTile(3));
		h.addTile(new GameTile(4));
		h.addTile(new GameTile(5));
		h.addTile(new GameTile(5));
		h.sortHand();
		

		println(h.toString());
		

		println("\nHand is complete normal?: " + h.DEMOgetChecker().isCompleteNormal());
		
	}
	
	
	public static void chiitoiTenpaiTest(){
		
		Hand h = new Hand(ownerSeat);
		GameTileList waits = new GameTileList();
		
		List<TileInterface> handTiles = ImmutableTile.retrieveMultipleTiles(2,2,5,5,7,7,10,10,20,20,21,21,30,30);
		handTiles.remove(13);
		for (TileInterface t: handTiles) h.addTile((GameTile) t);
		
		
		println(h.toString());
		

		println("\nIn tenpai for chiitoi?: " + h.DEMOgetChecker().DEMOchiitoitsuInTenpai());
		waits = h.DEMOgetChecker().getTenpaiWaits();
		if (waits != null && !waits.isEmpty()) System.out.print("Wait (" + waits.size() + " waits): " + waits.get(0).toString());
		println("\n\n\n");
		

		//chiitoi tenpai hands (should show true)
		println("\n\nGOOD TENPAIS=====================\n");
		List<List<TileInterface>> tlists = new ArrayList<List<TileInterface>>(8);
		tlists.add(ImmutableTile.retrieveMultipleTiles(1,1,3,3,5,5,7,7,9,9,11,11,13));
		tlists.add(ImmutableTile.retrieveMultipleTiles(1,1,3,3,5,5,7,7,9,9,11,13,13));
		tlists.add(ImmutableTile.retrieveMultipleTiles(1,1,3,3,5,5,7,7,9,11,11,13,13));
		tlists.add(ImmutableTile.retrieveMultipleTiles(1,1,3,3,5,5,7,9,9,11,11,13,13));
		tlists.add(ImmutableTile.retrieveMultipleTiles(1,1,3,3,5,7,7,9,9,11,11,13,13));
		tlists.add(ImmutableTile.retrieveMultipleTiles(1,1,3,5,5,7,7,9,9,11,11,13,13));
		tlists.add(ImmutableTile.retrieveMultipleTiles(1,3,3,5,5,7,7,9,9,11,11,13,13));
		
		for (List<TileInterface> tl: tlists){
			h = new Hand(ownerSeat);
			for (TileInterface t: tl) h.addTile(new GameTile(t.getTileBase()));

			println(h.toString());

			println("\nIn tenpai for chiitoi?: " + h.DEMOgetChecker().DEMOchiitoitsuInTenpai());
			waits = h.DEMOgetChecker().getTenpaiWaits();
			if (waits != null && !waits.isEmpty()) System.out.print("Wait (" + waits.size() + " waits): " + waits.get(0).toString());
			println("\n\n\n");
		}
		
		
		
		//NOT chiitoi tenpai hands (should show false)
		println("\n\nBAD TENPAIS=====================\n");
		List<List<TileInterface>> badtlists = new ArrayList<List<TileInterface>>(8);
		badtlists.add(ImmutableTile.retrieveMultipleTiles(1,1,1,1,5,5,7,7,9,9,11,11,13));
		badtlists.add(ImmutableTile.retrieveMultipleTiles(1,1,1,1,5,6,7,7,9,9,11,11,13,13));
		badtlists.add(ImmutableTile.retrieveMultipleTiles(1,1,5,5,7,7,9,9,11,11,13));
		badtlists.add(ImmutableTile.retrieveMultipleTiles(1,1,5));
		badtlists.add(ImmutableTile.retrieveMultipleTiles(1));
		badtlists.add(ImmutableTile.retrieveMultipleTiles(1,1,3,3,5,5,7,7,9,9,11,11,13,13));	//is actually chiitoi complete
		
		
		
		for (List<TileInterface> tl: badtlists){
			h = new Hand(ownerSeat);
			for (TileInterface t: tl) h.addTile(new GameTile(t.getTileBase()));

			println(h.toString());

			println("\nIn tenpai for chiitoi?: " + h.DEMOgetChecker().DEMOchiitoitsuInTenpai());
			waits = h.DEMOgetChecker().getTenpaiWaits();
			if (waits != null && !waits.isEmpty()) System.out.print("Wait (" + waits.size() + " waits): " + waits.get(0).toString());
			println("\n\n\n");
		}
		
		
		
//		println("\n\nChiitoi complete?: " + h.mChecker.chiitoitsuIsComplete());
		
	}
	

	public static void kokushiTenpaiTest(){
		
		Hand h = new Hand(ownerSeat);
		//Tile q = null;
		GameTileList waits = null;

		h.addTile(new GameTile("M1"));	//1
		h.addTile(new GameTile("M9"));	//2
		h.addTile(new GameTile("P1"));	//3
		h.addTile(new GameTile("P9"));	//4
		h.addTile(new GameTile("S1"));	//5
		h.addTile(new GameTile("S9"));	//6
		h.addTile(new GameTile("WE"));	//7
		h.addTile(new GameTile("WS"));	//8
		h.addTile(new GameTile("WW"));	//9
		h.addTile(new GameTile("WN"));	//10
		h.addTile(new GameTile("DW"));	//11
//		h.addTile(new GameTile("DG"));	//12
		h.addTile(new GameTile("DR"));	//13
//		h.addTile(new GameTile("m8"));	//14
		h.addTile(new GameTile("DR"));	//extra tile
		h.sortHand();
		
		println(h.toString());
		

		println("\nIn tenpai for kokushi musou?: " + h.DEMOgetChecker().isTenpaiKokushi());
		
		waits = h.DEMOgetChecker().DEMOgetKokushiWaits();
		System.out.print(waits.size() + "-sided wait: ");
		for (TileInterface t: waits)
			System.out.print(t.toString() + ", ");
		
		println("\n\nKokushi musou complete?: " + h.DEMOgetChecker().isCompleteKokushi());
	}
	
	
	public static void testCallPartners(){
		
		GameTile q = null;
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
		
		Integer[] dIDs = {1,2,3,4,5,6,7,8,9,10,11,30,31,32,33,34};
		List<Integer> discardIDs = Arrays.asList(dIDs);
		
		
		for (Integer id: discardIDs){
			
			q = new GameTile(id);
			q.setOwner(ownerSeat.kamichaWind());
			h.checkCallableTile(q);
			
			println(h.toString());
			println("\nDiscarded tile: " + q.toString());
			
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
			println("\n\n\n\n");
		}
		
	}
	
	

	public static void finishingMovePre(){
		Hand h = new Hand(ownerSeat);
		GameTile q = null;

		h.addTile(2);
		h.addTile(3);
		h.addTile(3);

		println(h.toString());
		
		
		q = new GameTile(1);
		q.setOwner(ownerSeat.kamichaWind());
//		println("\nDiscarded tile: " + q.toStringAllInfo());
		println("\nDiscarded tile: " + q.toString());
		h.checkCallableTile(q);
		
		
		
		int count = 0;
		if (h.ableToChiL()) count++;
		if (h.ableToChiM()) count++;
		if (h.ableToChiH()) count++;
		if (h.ableToPon()) count++;
		if (h.ableToKan()) count++;
		if (h.ableToRon()) count++;
		
		println("Number of calls possible: " + count);
		
		
	}
	
	

	public static void chiKamichaTest(){
		Hand h = new Hand(ownerSeat);
		GameTile q = null;

		h.addTile(2);
		h.addTile(3);
		h.addTile(3);
		

		println(h.toString());
		
		
		
		q = new GameTile(1);
//		q.setOwner(ownerSeat);
//		q.setOwner(Player.findKamichaOf(ownerSeat));
		q.setOwner(Wind.WEST);
		

//		println("\nDiscarded tile: " + q.toStringAllInfo());
		println("\nDiscarded tile: " + q.toString());
		println("Callable?: " + h.checkCallableTile(q));
		
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
		

		println(h.toString());
		
		List<Integer> hots = h.DEMOfindAllHotTiles();
		List<Integer> callables = h.DEMOfindAllCallableTiles();
		
		
		
		//sort the lists
		Collections.sort(hots);
		Collections.sort(callables);
		

		println("\nHot Tiles: ");
		for (Integer i: hots)
//			System.out.print(Tile.repr_stringReprOfId(i) + ", ");
			System.out.print((new GameTile(i)).toString() + ", ");
		
		
		println("\n\nCallable Tiles: ");
		for (Integer i: callables)
//			System.out.print(Tile.repr_stringReprOfId(i) + ", ");
			System.out.print((new GameTile(i)).toString() + ", ");
		
	}
	
	
	
	
	
	

	public static void nextTileTest(){
		
		List<GameTile> tiles = new ArrayList<GameTile>(10);

		tiles.add(new GameTile("S2"));
		tiles.add(new GameTile("P6"));
		tiles.add(new GameTile("P9"));
		tiles.add(new GameTile("M1"));
		tiles.add(new GameTile("M9"));
		tiles.add(new GameTile("DG"));
		tiles.add(new GameTile("DR"));
		tiles.add(new GameTile("WN"));
		tiles.add(new GameTile("WE"));
		tiles.add(new GameTile("WW"));
		
		for (GameTile t: tiles)
			println(t.toString() + ", next tile: " + t.nextTile().toString());
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
		
		
		GameTile q = null;
		q = new GameTile(4);
		
		List<GameTile> discards = new ArrayList<GameTile>(10);
		discards.add(new GameTile(12));
		discards.add(new GameTile(34));
		discards.add(new GameTile(7));
		discards.add(new GameTile(25));
		discards.add(new GameTile(1));
		discards.add(new GameTile(16));
		
		discards.get(1).setOwner(p.getSeatWind());
		
		
		
		while (discards.isEmpty() == false)
		{
			q = discards.get(0);
			discards.remove(0);

			println();
			println(p.getAsStringHand());
//			p.showMelds();
			println("\nDiscarded tile: " + q.toString() + "\n");
			

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
//			println("Player's call status: " + whatCalled + " (status: " + status + ")");
			
			
			//broke this when clickable discards were implemented
//			p.takeTurn();
			
		}

		println();
		println(p.getAsStringHand());
//		p.showMelds();

		
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
		
		
		GameTile q = null;
		q = new GameTile(4);
		
		println(p.getAsStringHand());
		println("\nDiscarded tile: " + q.toString() + "\n");
		

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
//		println("Player's call status: " + whatCalled + " (status: " + status + ")");
		
	}
	
	
	
	public static void testCalls(){
		
		GameTile q = null;
		Hand h = new Hand();
		
		
		h.addTile(new GameTile(12));
		h.addTile(new GameTile(10));
		
		
		q = new GameTile(11);
		

		println(h.toString());
		println("\nDiscarded tile: " + q.toString() + "\n");
		
		
//		println("Chi-L?: " + h.canChiL(q));
//		println("Chi-M?: " + h.canChiM(q));
//		println("Chi-H?: " + h.canChiH(q));
//		println("Pon?  : " + h.canPon(q));
//		println("Kan?  : " + h.canKan(q));
		
		
	}
	
	
	
	
	
	
	
	public static void testContains(){
		
		List<GameTile> tiles = new ArrayList<GameTile>();
		
		GameTile t2 = new GameTile(2);
		
		tiles.add(new GameTile(1));
		tiles.add(new GameTile(2));
		tiles.add(new GameTile(3));
		
		//println("\nt2 == M2?: ");
		//println(t2.equals(new GameTile(2)));
		
//		println(t2.toStringAllInfo());
		
		println("\nHand contains t22?: ");
		println(tiles.contains(t2) + "");
	}
	
	
	
	
	public static void testHots(){
		Hand h = new Hand();

		h.addTile(new GameTile(1));
		h.addTile(new GameTile(2));
		
		
		List<Integer> hots = new ArrayList<Integer>(0);
		hots = h.DEMOfindAllHotTiles();
		
		println(h.toString());
		
		println("\nhots:");
		
		for (Integer i: hots) println((new GameTile(i)).toString());
	}
	
	
	
	

	public static void playTileClone(){
		GameTile t1, t2;
		t1 = new GameTile(7);
		println("t1: " + t1.toString() + "\n");
		
		t2 = t1.clone();
		println("t1: " + t1.toString() + ", owner: " + t1.getOrignalOwner());
		println("t2: " + t2.toString() + ", owner: " + t2.getOrignalOwner() + "\n");
		
		t1.setOwner(Wind.WEST);
		println("t1: " + t1.toString() + ", owner: " + t1.getOrignalOwner());
		println("t2: " + t2.toString() + ", owner: " + t2.getOrignalOwner() + "\n");
		
		t2.setOwner(Wind.SOUTH);
		println("t1: " + t1.toString() + ", owner: " + t1.getOrignalOwner());
		println("t2: " + t2.toString() + ", owner: " + t2.getOrignalOwner() + "\n");
	}
	
	
	
	

	
//	public static void listEqualsTest(){
//		
//		TileList handTiles = new TileList(2,2,5,5,7,7,10,10,20,20,21,21,30,30);
////		handTiles = new TileList(2,2,5,5,7,7,10,10,20,20,21,21,30,30);
//		
//		TileList evenTiles = handTiles.getMultiple(0,2,4,6,8,10,12);
//		TileList oddTiles = handTiles.getMultiple(1,3,5,7,9,11,13);
//		
////		oddTiles.get(1).setRedDora();
//		((GameTile)oddTiles.get(2)).setOwner(Wind.NORTH);
//		
//		println("List1: " + evenTiles.toString());
//		println("List2: " + oddTiles.toString());
//		println("List1 equals List2?: " + evenTiles.equals(oddTiles));
//		
//
//		TileList dupes = new TileList(2,2,2,3,4,4,5,6,6,6,7);
//		TileList noDupes = dupes.makeCopyNoDuplicates();
//		println("\n\nYedupes: " + dupes.toString());
//		println("Nodupes: " + noDupes.toString());
//	}
	
	
}
