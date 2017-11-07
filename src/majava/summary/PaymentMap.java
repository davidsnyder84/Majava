package majava.summary;

import java.util.Arrays;
import java.util.Iterator;

import majava.player.Player;


public class PaymentMap implements Iterable<PlayerSummary>{
	
	private static final int NUM_PLAYERS = 4;
	
	
	private final PlayerSummary[] mPlayers;
	private final int[] mPayments;
	
	public PaymentMap(){
		mPlayers = new PlayerSummary[NUM_PLAYERS];
		mPayments = new int[NUM_PLAYERS];
	}

	public PaymentMap(PaymentMap other){
		this();
		putAll(other);
	}
	
	
	
	
	public void put(PlayerSummary playerSummary, int paymt){
		if (playerSummary == null) return;
		mPlayers[playerSummary.getPlayerNumber()] = playerSummary;
		mPayments[playerSummary.getPlayerNumber()] = paymt;
	}
	public void putAll(PaymentMap other){
		for (int i = 0; i < NUM_PLAYERS; i++){
			mPlayers[i] = other.mPlayers[i];
			mPayments[i] = other.mPayments[i];
		}
	}
	//overloaded for Player
	public void put(Player player, int paymt){
		put(PlayerSummary.getSummaryFor(player), paymt);
	}
	
	
	
	//gets the payments the player is mapped to
	public int get(int playerNum){return mPayments[playerNum];}
	public int get(PlayerSummary playerSummary){if (playerSummary == null) return -1; return mPayments[playerSummary.getPlayerNumber()];}
	public int get(Player p){return get(PlayerSummary.getSummaryFor(p));}
	
	//gets the player
	public PlayerSummary getPlayer(int playerNum){return mPlayers[playerNum];}
	
	
	
	public Iterator<PlayerSummary> iterator(){return Arrays.asList(mPlayers).iterator();}
	public PlayerSummary[] keySet(){
		PlayerSummary[] copy = new PlayerSummary[NUM_PLAYERS];
		for (int i = 0; i < NUM_PLAYERS; i++) copy[i] = mPlayers[i];
		return copy;
	}
	
	
	

//	public void putAll(Map<? extends PlayerSummary, ? extends Integer> m) {
//		// TODO Auto-generated method stub
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public Integer get(Object key){return mPaymentMap.get(key);}
//	
//	
//	public Set<PlayerSummary> keySet() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Integer put(PlayerSummary key, Integer value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void putAll(Map<? extends PlayerSummary, ? extends Integer> m) {
//		// TODO Auto-generated method stub
//		
//	}
}
