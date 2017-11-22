package majava.events;

import java.util.ArrayList;
import java.util.List;

import majava.summary.StateOfGame;

public class JanEventLogger implements JanObserver{
	private final List<String> eventLog;
	
	public JanEventLogger() {
		eventLog = new ArrayList<String>();
	}

	@Override
	public void update(GameplayEvent gameplayEvent, StateOfGame gameState) {
		eventLog.add(gameplayEvent.toString());
	}
	
	public void printEventLog(){
		for (String eventString : eventLog) System.out.println(eventString);
	}
}