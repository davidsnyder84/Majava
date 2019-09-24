package majava.events;

import majava.round.KyokuState;
import majava.summary.StateOfGame;

//Observer pattern (observer)
public interface JanObserver {
	public void update(GameplayEvent gameplayEvent, KyokuState gameState);
}
