package majava.control.testcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import majava.Hand;
import majava.HandChecker;
import majava.Meld;
import majava.Player;
import majava.RoundResult;
import majava.util.TileList;
import majava.util.YakuList;
import majava.yaku.Yaku;
import majava.enums.MeldType;
import majava.summary.PaymentMap;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;


public class Majenerator {
	
	private static final int NUM_PLAYERS = 4;
	private static final int NUM_TILES = 34;
	private static final Random randGen = new Random();
	
	
	public static void main(String[] args){
		
		
//		println(generateRoundResult().toString());
		for (Yaku y: generateYakuList()) println(y.toString());
	}
	

	
	
	
	
	
	public static YakuList generateYakuList(){
		
		final int MAX_HOW_MANY = 10;
		
		YakuList yakuList = new YakuList();
		int howMany = 1+randGen.nextInt(MAX_HOW_MANY);
		
		Yaku[] allYaku = Yaku.values();
		for (int i = 0; i < howMany; i++){
			yakuList.add(allYaku[randGen.nextInt(allYaku.length)]);
		}
		
		return yakuList;
	}
	
	
	
	
	
	public static RoundResultSummary generateRoundResultSummary(){
		return generateRoundResult().getSummary();
	}
	public static RoundResult generateRoundResult(){
		
		RoundResult res = new RoundResult();
		
		Player[] players = {generatePlayer(0), generatePlayer(1), generatePlayer(2), generatePlayer(3)};
		int windex = randGen.nextInt(NUM_PLAYERS), losedex; do{losedex = randGen.nextInt(NUM_PLAYERS);}while(losedex == windex);
		
		Player winner = players[windex];
		Player furi = players[losedex];
		List<Meld> winMelds = null;
		TileList winHandTiles = null;
		GameTile winningTile = null;
		
		
		winHandTiles = new TileList();
		winMelds = new ArrayList<Meld>();
		generateWinningHandAndMelds(winHandTiles, winMelds);
//		generateWinningHandAndMelds(winHandTiles, winMelds, 0);
		winningTile = winHandTiles.remove(randGen.nextInt(winHandTiles.size()));
		
		
		if (randGen.nextBoolean()) res.setVictoryRon(winner, furi);
		else res.setVictoryTsumo(winner);
		
		res.setWinningHand(winHandTiles, winMelds, winningTile);
		
		
		PaymentMap payments = generatePaymentsMap(players, res);
		res.recordPayments(payments);
		
		return res;
	}
	public static PaymentMap generatePaymentsMap(Player[] players, RoundResult res){
		PaymentMap playerPaymentMap = new PaymentMap();
		
		final double DEALER_WIN_MULTIPLIER = 1.5, DEALER_TSUMO_MULTIPLIER = 2;
		
		int paymentDue = 8000;
		int tsumoPointsNonDealer = paymentDue / 4;
		int tsumoPointsDealer = (int) (DEALER_TSUMO_MULTIPLIER * tsumoPointsNonDealer);
		
		//find who the winner is
		Player winner = res.getWinningPlayer(); int windex = Arrays.asList(players).indexOf(winner);
		Player[] losers = {players[(windex+1)%4], players[(windex+2)%4], players[(windex+3)%4]};
		Player furikonda = null;
		
		if (winner.isDealer()) paymentDue *= DEALER_WIN_MULTIPLIER;
		
		///////add in honba here
		
		playerPaymentMap.put(winner.getPlayerSummary(), paymentDue);
		
		
		//find who has to pay
		if (res.isVictoryRon()){
			furikonda = res.getFurikondaPlayer();
			for (Player p: losers)
				if (p == furikonda) playerPaymentMap.put(p.getPlayerSummary(), -paymentDue);
				else playerPaymentMap.put(p.getPlayerSummary(), 0);
		}
		else{//tsumo
			for (Player p: losers){
				if (p.isDealer() || winner.isDealer()) playerPaymentMap.put(p.getPlayerSummary(), -tsumoPointsDealer);
				else  playerPaymentMap.put(p.getPlayerSummary(), -tsumoPointsNonDealer);
			}
		}
		///////add in riichi sticks here
		return playerPaymentMap;
	}
	
	
	
	
	
	
	public static TileList generateWinningHandTiles(){
		final TileList winHand = new TileList();
		final List<Meld> winMelds = new ArrayList<Meld>();
		generateWinningHandAndMelds(winHand, winMelds);
		
		return winHand;
	}
	
	public static void generateWinningHandAndMelds(final TileList winHand, final List<Meld> winMelds, final int howManyMelds){
		if (winHand == null || winMelds == null) return;
		while (!winHand.isEmpty()) winHand.removeFirst();
		while (!winMelds.isEmpty()) winMelds.remove(0);
		
		TileList handtiles = null;
		
		
		//sometimes do chiitoi and kokushi
		if (randGen.nextInt(15) == 14){
			if (randGen.nextBoolean()) handtiles = generateHandTilesKokushi();
			else handtiles = generateHandTilesChiitoi();
			
			for (GameTile t: handtiles) winHand.add(t);
			return;
		}
		

		List<Meld> handMelds = new ArrayList<Meld>();
		List<Meld> recipient = null;
		Meld candidateMeld = null;
		
		//form melds
		int meldsFormed = 0;
		while (meldsFormed < 4){
			if (meldsFormed < howManyMelds){
				recipient = winMelds;
				candidateMeld = generateMeld();
			}
			else{
				recipient = handMelds;
				candidateMeld = generateMeld(randomMeldTypeNoKan());
			}
			
			
			if (!__meldWouldViolateTileLimit(candidateMeld, handMelds, winMelds)){
				recipient.add(candidateMeld);
				meldsFormed++;
			}
		}
		
		//form pair
		while (__meldWouldViolateTileLimit((candidateMeld = generateMeld(MeldType.PAIR)), handMelds, winMelds));
		handMelds.add(candidateMeld);
		
		//add hand meld tiles to hand
		for (Meld m: handMelds) for (GameTile t: m) winHand.add(t);
		winHand.sort();
	}
	public static void generateWinningHandAndMelds(final TileList winHand, final List<Meld> winMelds){generateWinningHandAndMelds(winHand, winMelds, randGen.nextInt(5));}
	private static boolean __meldWouldViolateTileLimit(Meld candidateMeld, TileList existingTiles){
		//chis
		if (candidateMeld.isChi()) for (GameTile t: candidateMeld) if (existingTiles.findHowManyOf(t) >= 4) return true;
		//pon, kan, pair
		else if ((existingTiles.findHowManyOf(candidateMeld.getFirstTile()) + candidateMeld.size()) > 4) return true;
		
		return false;
	}
	private static boolean __meldWouldViolateTileLimit(Meld candidateMeld, List<Meld> handMelds, List<Meld> melds){	
		TileList existingTiles = new TileList();
		for (Meld m: handMelds) for (GameTile t: m) existingTiles.add(t);
		for (Meld m: melds) for (GameTile t: m) existingTiles.add(t);
		return __meldWouldViolateTileLimit(candidateMeld, existingTiles);
	}
	
	
	
	
	
	public static TileList generateHandTilesChiitoi(){
		int id;
		TileList tlist = new TileList();
		while (tlist.size() != 14){
			id = 1+randGen.nextInt(NUM_TILES);
			if (!tlist.contains(id)){
				tlist.add(id);tlist.add(id);
			}
		}
		
		tlist.sort();
		return tlist;
	}
	public static TileList generateHandTilesKokushi(){
		TileList tlist = HandChecker.listOfYaochuuTiles();
		tlist.add(tlist.get(randGen.nextInt(tlist.size())));
		
		tlist.sort();
		return tlist;
	}
	
	
	
	public static Meld generateMeld(MeldType type, boolean closed){
		
		TileList meldTiles = new TileList();
		int id = 0;
		
		while(!tileCanMeldMeldType((id = 1+randGen.nextInt(NUM_TILES)), type));
		
		switch (type){
		case CHI_L: meldTiles.add(id); meldTiles.add(id + 1); meldTiles.add(id + 2); break;
		case CHI_M: meldTiles.add(id - 1); meldTiles.add(id); meldTiles.add(id + 1); break;
		case CHI_H: meldTiles.add(id - 2); meldTiles.add(id - 1); meldTiles.add(id); break;
		case KAN: meldTiles.add(id);
		case PON: meldTiles.add(id);
		case PAIR: meldTiles.add(id); meldTiles.add(id); break;
		default: break;
		}
		
		Meld m = new Meld(meldTiles, type);
		return m;
	}
	public static Meld generateMeld(MeldType mt){return generateMeld(mt, true);}
	public static Meld generateMeld(){return generateMeld(randomMeldType());}
	
	public static boolean tileCanMeldMeldType(GameTile tile, MeldType mt){
		if (tile.getId() == 0) return false;
		
		//pon/kan
		if (mt.isMulti()) return true;
		
		//chis
		if (tile.isHonor()) return false;
		
		switch (mt){
		case CHI_L: return tile.getFace() != '8' && tile.getFace() != '9';
		case CHI_M: return tile.getFace() != '1' && tile.getFace() != '9';
		case CHI_H: return tile.getFace() != '1' && tile.getFace() != '2';
		default: return false;
		}
	}
	public static boolean tileCanMeldMeldType(int tId, MeldType mt){return tileCanMeldMeldType(new GameTile(tId), mt);}
	
	
	public static MeldType randomMeldType(){final MeldType[] mts = {MeldType.CHI_L, MeldType.CHI_M, MeldType.CHI_H, MeldType.PON, MeldType.KAN}; return mts[randGen.nextInt(mts.length)];}
	public static MeldType randomMeldTypeNoKan(){final MeldType[] mts = {MeldType.CHI_L, MeldType.CHI_M, MeldType.CHI_H, MeldType.PON}; return mts[randGen.nextInt(mts.length)];}
	
	
	
	
	
	
	
	public static Player generatePlayer(int playernum){
		
		if (playernum < 0 || playernum >= 4) playernum = 0;
		Player player = new Player();

		String[] names = {"Suwado", "Albert", "Brenda", "Carl", "Dick", "Eddie", "Geromy", "Halbert", "Little King John"};
		
		player.setControllerComputer();
		player.setPlayerName(names[randGen.nextInt(names.length)]);
		
		switch(playernum){
		case 0: player.setSeatWindEast(); break;
		case 1: player.setSeatWindSouth(); break;
		case 2: player.setSeatWindWest(); break;
		case 3: player.setSeatWindNorth(); break;
		default: break;
		}
		
		player.setPlayerNumber(playernum);
		
		player.DEMOfillHandNoTsumo();
		
		return player;
	}
	public static Player generatePlayer(){return generatePlayer(randGen.nextInt(NUM_PLAYERS));}
	
	
	

	public static void println(String prints){System.out.println(prints);}public static void println(){System.out.println("");}
}





