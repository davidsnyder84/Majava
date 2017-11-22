package majava.events;

import java.util.ArrayList;
import java.util.List;

import majava.summary.StateOfGame;


//Observer pattern (this is the Subject)
public class GameEventListener {
	private final List<JanObserver> observers;
	
	public GameEventListener() {
		observers = new ArrayList<JanObserver>();
	}
	
	public void postNewEvent(GameplayEvent event, StateOfGame gameState){
		notifyObservers(event, gameState);
	}
	
	
	
	
	private void notifyObservers(GameplayEvent event, StateOfGame gameState){
		for (JanObserver o: observers)
			o.update(event, gameState);
	}
	
	public void registerObserver(JanObserver newObserver){
		observers.add(newObserver);
	}
	public void removeObserver(JanObserver removeThisObserver){
		observers.remove(removeThisObserver);
	}
}
