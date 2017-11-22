package majava.events;

import java.util.ArrayList;
import java.util.List;

import utility.Pauser;

public class GameEventListener {
	
	private final List<MyObserver> observers;
	private final List<String> eventLog;
	
	public GameEventListener() {
		observers = new ArrayList<MyObserver>();
		
		eventLog = new ArrayList<String>();
	}
	
	public void postNewEvent(final GameplayEvent event){
//		switch(event.getEventType()){
//		case DISCARDED_TILE: __displayEventDiscardedTile(); break;
//		case MADE_OPEN_MELD: __displayEventMadeOpenMeld(); break;
//		case DREW_TILE: __displayEventDrewTile(); break;
//		case MADE_OWN_KAN: __displayEventMadeOwnKan(); break;
//		case NEW_DORA_INDICATOR: __displayEventNewDoraIndicator(); break;
//		
//		case HUMAN_PLAYER_TURN_START: __displayEventHumanTurnStart(); break;
//		
//		case START_OF_ROUND: __displayEventStartOfRound(); break;
//		case PLACEHOLDER: __displayEventPlaceholder(); break;
//		case END_OF_ROUND: __displayEventEndOfRound(); break;
//		default: break;
//		}
//		
//		if (event.isExclamation()) __showExclamation(event.getExclamation(), event.getSeat());
//		
//		if (mSleepTime > 0 && !event.isExclamation() && !event.isPlaceholder())
//			Pauser.pauseFor(mSleepTime);
	}
	
	
	public void registerObserver(MyObserver newObserver){
		observers.add(newObserver);
	}
	public void removeObserver(MyObserver removeThisObserver){
		observers.remove(removeThisObserver);
	}
	
	private void notifyObservers(){
		for (MyObserver o: observers)
			;
	}
}
