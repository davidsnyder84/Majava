package majava.summary;

import java.util.Arrays;
import java.util.Iterator;

public class PaymentSummary{
	
	private static final int NUM_PLAYERS = 4;
	
	
	private final PlayerSummary[] mPlayers;
	private final int[] mPayments;
	
	public PaymentSummary(){
		mPlayers = new PlayerSummary[NUM_PLAYERS];
		mPayments = new int[NUM_PLAYERS];
	}
	
	
	
	
	public void put(PlayerSummary player, int paymt){
		if (player == null) return;
		mPlayers[player.getPlayerNumber()] = player;
		mPayments[player.getPlayerNumber()] = paymt;
	}
	
	

	public int get(int playerNum){return mPayments[playerNum];}
	public int get(PlayerSummary player){if (player == null) return -1; return mPayments[player.getPlayerNumber()];}
	
	
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
