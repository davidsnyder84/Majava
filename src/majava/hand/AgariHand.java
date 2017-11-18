package majava.hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import majava.enums.Wind;
import majava.tiles.GameTile;
import majava.util.GameTileList;

public class AgariHand extends Hand {
	
	private GameTile winningTile;
	private List<Meld> finishedMeldForm;
	
	private final Wind ownerWind;
	
	public AgariHand (Hand baseHand, GameTile agarihai){
		super(baseHand);
		
		winningTile = agarihai;		
		
		finishedMeldForm = new ArrayList<Meld>();
		finishedMeldForm = getMeldForm();
		
		ownerWind = baseHand.getOwnerSeatWind();
		
		//add winning tile to hand
		if (!isFull()){
			addTile(winningTile);
			sort();
		}
	}
	public AgariHand (AgariHand other){
		super(other);
		winningTile = other.winningTile;
		ownerWind = other.ownerWind;
		
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
	
	
	
	//need to override because adding WinningTile screws up Hand's getOwnerSeatWind() logic.
	//AgariHand's OwnerWind could also be derived with a calculation, but a final member variable is more intuitive
	@Override
	public Wind getOwnerSeatWind(){return ownerWind;}
	
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
