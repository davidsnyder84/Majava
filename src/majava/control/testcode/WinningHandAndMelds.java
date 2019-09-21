package majava.control.testcode;

import java.util.List;

import majava.hand.Hand;
import majava.hand.Meld;
import majava.util.GTL;

public class WinningHandAndMelds{
	
	private final Hand hand;
	private final List<Meld> melds;
	
	public WinningHandAndMelds(Hand h, List<Meld> ms){
		hand = h;
		melds = ms;
	}
	public WinningHandAndMelds(GTL tiles, List<Meld> ms){this (new Hand(tiles), ms);}
	
	
	public Hand getHand(){
		return hand;
	}
	
	public List<Meld> getMelds(){
		return melds;
	}
}
