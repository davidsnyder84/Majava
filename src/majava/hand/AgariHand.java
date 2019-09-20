package majava.hand;

import java.util.ArrayList;
import java.util.List;

import majava.enums.Wind;
import majava.tiles.GameTile;
import majava.util.GameTileList;

//NOTE: this class is immutable
public class AgariHand extends Hand {
	
	private final GameTile winningTile;
	
	
	
	public AgariHand (Hand baseHand, GameTile agarihai){
		super(baseHand);
		
		winningTile = agarihai;
		
		//add winning tile to hand
		if (!isFull()){
			addTile(winningTile);
			sort();///////////////////////////////////////////////////////////mutate
		}
	}
	public AgariHand (AgariHand other){
		super(other);
		winningTile = other.winningTile;
	}
	public AgariHand clone(){return new AgariHand(this);}
	
	
	
	
	@Override
	public boolean isClosed(){	//need to override, because the final ronned meld will be open
		for (Meld m: getMeldForm()) if (m.isOpen()) return false;
		return true;
	}
	
	
	
	public List<Meld> getMeldForm(){
		List<Meld> meldForm = new ArrayList<Meld>();
		//add existing melds, then add finishing melds
		meldForm.addAll(getMelds());
		meldForm.addAll(getFinishingMelds());
		
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
		stringRep += "\n=-=-=-AgariHand:\n" + super.toString() + "\n";
		stringRep += "Winning Tile: " + winningTile + "\n";
		stringRep += "-\nFinished Meld Form:\n";
		for (Meld m: getMeldForm())
			stringRep += m.toString() + "\n";
		return stringRep + "\n";
	}
}
