package majava.hand;

import java.util.ArrayList;
import java.util.List;

import majava.enums.Wind;
import majava.tiles.GameTile;
import majava.util.GameTileList;

public class AgariHand extends Hand {
	
	private GameTile winningTile;
	
	
	
	public AgariHand (Hand baseHand, GameTile agarihai){
		super(baseHand);
		
		winningTile = agarihai;
		
		//add winning tile to hand
		if (!isFull()){
			addTile(winningTile);
			sort();
		}
	}
	public AgariHand (AgariHand other){
		super(other);
		winningTile = other.winningTile;
	}
	public AgariHand clone(){return new AgariHand(this);}
	
	
	
	private List<Meld> getMeldForm(){
		List<Meld> meldForm = new ArrayList<Meld>();
		//add existing melds, then add finishing melds
		meldForm.addAll(getMelds());
		meldForm.addAll(getFinishingMelds());
		
		return meldForm;
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
