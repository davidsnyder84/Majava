package majava.events;

import majava.round.KyokuState;

//Observer pattern (observer)
public interface JanObserver {
	public void update(GameplayEvent gameplayEvent, KyokuState gameState);
}
