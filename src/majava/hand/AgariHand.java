package majava.hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import majava.tiles.GameTile;
import majava.util.GameTileList;

public class AgariHand extends Hand {
	
	private GameTile winningTile;
	private List<Meld> finishedMeldForm;
	
	
	public AgariHand (Hand baseHand, GameTile agarihai){
		super(baseHand);
		
		winningTile = agarihai;		
		
		finishedMeldForm = new ArrayList<Meld>();
		finishedMeldForm = getMeldForm();
		
		
		//add winning tile to hand
		if (!isFull()){
			addTile(winningTile);
			sort();
		}
	}
	public AgariHand (AgariHand other){
		super(other);
		winningTile = other.winningTile;
		
		finishedMeldForm = new ArrayList<Meld>();
		for (Meld m: other.finishedMeldForm)
			finishedMeldForm.add(m.clone());
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
		stringRep += "Finished Meld Form:\n";
		for (Meld m: getMeldForm())
			stringRep += m.toString() + "\n";
		return stringRep + "\n";
	}
}
