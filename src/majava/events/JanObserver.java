package majava.events;

import majava.summary.StateOfGame;

//Observer pattern (observer)
public interface JanObserver {
	public void update(GameplayEvent gameplayEvent, StateOfGame gameState);
}
