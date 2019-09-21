package majava.hand;

import java.util.ArrayList;
import java.util.List;

import majava.enums.Wind;
import majava.tiles.GameTile;
import majava.util.GTL;

//NOTE: this class is immutable
public class AgariHand{
	
	private final GameTile winningTile;
	private final Hand hand;
	
	
	
	public AgariHand (Hand baseHand, GameTile agarihai){
		winningTile = agarihai;
		
		//add winning tile to hand if not full
		if (baseHand.isFull())
			hand = baseHand;
		else
			hand = baseHand.addTile(winningTile).sort();
	}
	public AgariHand (AgariHand other){this(other.hand, other.winningTile);}
	public AgariHand clone(){return new AgariHand(this);}
	
	
	
	
	
	public boolean isClosed(){	//need to override, because the final ronned meld will be open
		for (Meld m: getMeldForm()) if (m.isOpen()) return false;
		return true;
	}
	public int size(){return hand.size();}
	
	
	
	public List<Meld> getMeldForm(){
		List<Meld> meldForm = new ArrayList<Meld>();
		//add existing melds, then add finishing melds
		meldForm.addAll(hand.getMelds());
		meldForm.addAll(hand.getFinishingMelds());
		
		return meldForm;
	}
	public GameTile getAgariHai(){return winningTile;}
	public Meld getPair(){
		for (Meld m: getMeldForm())
			if (m.isPair()) return m.clone();
		return null;
	}
	
	
	@Override
	public String toString(){
		String stringRep = "";
		stringRep += "\n=-=-=-AgariHand:\n" + hand.toString() + "\n";
		stringRep += "Winning Tile: " + winningTile + "\n";
		stringRep += "-\nFinished Meld Form:\n";
		for (Meld m: getMeldForm())
			stringRep += m.toString() + "\n";
		return stringRep + "\n";
	}
}
