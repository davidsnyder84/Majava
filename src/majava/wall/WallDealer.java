package majava.wall;

import java.util.ArrayList;
import java.util.List;

import majava.wall.Wall;
import majava.tiles.GameTile;
import majava.util.GTL;

public class WallDealer{
	private static final int E_HAND = 0, S_HAND = 1, W_HAND = 2, N_HAND = 3;
	
	private final Wall wall;
	
	public WallDealer(Wall w){
		wall = w;
	}
	
	
	
	public GTL startingHandEast(){return startingHands().get(E_HAND);}
	public GTL startingHandSouth(){return startingHands().get(S_HAND);}
	public GTL startingHandWest(){return startingHands().get(W_HAND);}
	public GTL startingHandNorth(){return startingHands().get(N_HAND);}
	
	public Wall wallWithStartingHandsRemoved(){
		return wallWithStartingHandsRemoved(null, null, null, null);
	}
	
	
	
	
	private List<GTL> startingHands(){
		List<GameTile> tilesE = new ArrayList<GameTile>(), tilesS = new ArrayList<GameTile>(), tilesW = new ArrayList<GameTile>(), tilesN = new ArrayList<GameTile>();
		wallWithStartingHandsRemoved(tilesE, tilesS, tilesW, tilesN);
		
		List<GTL> startingHands = new ArrayList<GTL>();
		startingHands.add(new GTL(tilesE));
		startingHands.add(new GTL(tilesS));
		startingHands.add(new GTL(tilesW));
		startingHands.add(new GTL(tilesN));
		
		return startingHands;
	}
	
	
	//fills the received TileLists with each player's starting hands
	private Wall wallWithStartingHandsRemoved(List<GameTile> tilesE, List<GameTile> tilesS, List<GameTile> tilesW, List<GameTile> tilesN){
		Wall wallWithRemoved = wall;
		
		//[East takes 4, South takes 4, West takes 4, North takes 4] x3
		for (int i = 0; i < 3; i++){
			wallWithRemoved = take(4, wallWithRemoved, tilesE);
			wallWithRemoved = take(4, wallWithRemoved, tilesS);
			wallWithRemoved = take(4, wallWithRemoved, tilesW);
			wallWithRemoved = take(4, wallWithRemoved, tilesN);
		}
		//East takes 2, South takes 1, West takes 1, North takes 1 x1
		wallWithRemoved = take(2, wallWithRemoved, tilesE);
		wallWithRemoved = take(1, wallWithRemoved, tilesS);
		wallWithRemoved = take(1, wallWithRemoved, tilesN);
		wallWithRemoved = take(1, wallWithRemoved, tilesW);
		
		return wallWithRemoved;
	}
	
	//removes X tiles from a wall, puts them in the received list
	private static Wall take(int howMany, Wall w, List<GameTile> putTakenTilesHere){
		if (putTakenTilesHere != null)
			putTakenTilesHere.addAll(w.getNextMultiple(howMany));
		return w.removeNextMultiple(howMany);
	}
	
	
	
	public String toString(){
		String wdStr = "";
		
		wdStr += wall.toString() + "\n";
		wdStr += "------------------------------------------\n\n";
		wdStr += wallWithStartingHandsRemoved().toString() + "\n\n\n";
		wdStr += "------------------------------------------\n";
		
		for (GTL shand : startingHands())
			wdStr += shand + "\n";
		
		return wdStr;
	}
	
}
