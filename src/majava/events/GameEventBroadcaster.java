package majava.events;

import java.util.ArrayList;
import java.util.List;

import majava.round.KyokuState;
import majava.summary.StateOfGame;


//Observer pattern (this is the Subject)
public class GameEventBroadcaster {
	private final List<JanObserver> observers;
	
	public GameEventBroadcaster() {
		observers = new ArrayList<JanObserver>();
	}
	
	public void registerObserver(JanObserver newObserver){
		observers.add(newObserver);
	}
	public void removeObserver(JanObserver removeThisObserver){
		observers.remove(removeThisObserver);
	}
	
	
	//notify observers when a new event is posted (in other words, when the game's state has changed)
	public void postNewEvent(GameplayEvent event, KyokuState gameState){
		notifyObservers(event, gameState);
	}
	public void postNewEvent(GameplayEvent event){notifyObservers(event, null);}
	
	
	private void notifyObservers(GameplayEvent event, KyokuState gameState){
		for (JanObserver o: observers)
			o.update(event, gameState);
	}
}
