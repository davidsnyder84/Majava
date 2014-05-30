package majava.control.testcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import majava.Meld;
import majava.Player;
import majava.RoundResult;
import majava.TileList;
import majava.enums.MeldType;
import majava.tiles.Tile;


public class Majenerator {
	
	private static final int NUM_PLAYERS = 4;
	private static final int NUM_TILES = 34;
	private static final Random randGen = new Random();
	
	
	public static void main(String[] args){
		println(generateRoundResult().toString());
	}
	public static void println(String prints){System.out.println(prints);}public static void println(){System.out.println("");}
	
	
	
	
	
	
	public static RoundResult generateRoundResult(){
		
		RoundResult res = new RoundResult();
		
		Player[] players = {generatePlayer(0), generatePlayer(1), generatePlayer(2), generatePlayer(3)};
		int windex = randGen.nextInt(4), losedex; do{losedex = randGen.nextInt(4);}while(losedex == windex);
		
		Player winner = players[windex];
		Player furi = players[losedex];
		List<Meld> winMelds = null;
		TileList winHandTiles = null;
		
		winHandTiles = new TileList(3+18,4+18,5+18,6+18);
		winMelds = new ArrayList<Meld>();
		winMelds.add(generateMeld());winMelds.add(generateMeld());winMelds.add(generateMeld());
		
		
		if (randGen.nextBoolean()) res.setVictoryRon(winner, furi);
		else res.setVictoryTsumo(winner);
		
		res.setWinningHand(winHandTiles, winMelds, new Tile(7+18));
		
		
		Map<Player, Integer> payments = generatePaymentsMap(players, res);
		res.recordPayments(payments);
		
		return res;
	}
	public static Map<Player,Integer> generatePaymentsMap(Player[] players, RoundResult res){
		Map<Player, Integer> playerPaymentMap = new HashMap<Player, Integer>();
		
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
		
		playerPaymentMap.put(winner, paymentDue);
		
		
		//find who has to pay
		if (res.isVictoryRon()){
			furikonda = res.getFurikondaPlayer();
			for (Player p: losers)
				if (p == furikonda) playerPaymentMap.put(p, -paymentDue);
				else playerPaymentMap.put(p, 0);
		}
		else{//tsumo
			for (Player p: losers){
				if (p.isDealer() || winner.isDealer()) playerPaymentMap.put(p, -tsumoPointsDealer);
				else  playerPaymentMap.put(p, -tsumoPointsNonDealer);
			}
		}
		///////add in riichi sticks here
		return playerPaymentMap;
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
		case PON: meldTiles.add(id); meldTiles.add(id); meldTiles.add(id); break;
		default: break;
		}
		
		Meld m = new Meld(meldTiles, type);
		return m;
	}
	public static Meld generateMeld(){final MeldType[] mts = {MeldType.CHI_L, MeldType.CHI_M, MeldType.CHI_H, MeldType.PON, MeldType.KAN};return generateMeld(mts[randGen.nextInt(mts.length)], true);}
	public static boolean tileCanMeldMeldType(Tile tile, MeldType mt){
		if (tile.getFace() == '0') return false;
		switch (mt){
		case CHI_L: return tile.getFace() != '8' && tile.getFace() != '9';
		case CHI_M: return tile.getFace() != '1' && tile.getFace() != '9';
		case CHI_H: return tile.getFace() != '1' && tile.getFace() != '2';
		case PON: case KAN: return true;
		default: return false;
		}
	}
	public static boolean tileCanMeldMeldType(int tId, MeldType mt){return tileCanMeldMeldType(new Tile(tId), mt);}
	
	
	
	
	
	
	
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
		
		player.DEMOfillHand();
		
		return player;
	}
	public static Player generatePlayer(){return generatePlayer(randGen.nextInt(NUM_PLAYERS));}
}
