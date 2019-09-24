package majava.userinterface;

import utility.Pauser;
import majava.RoundTracker;
import majava.enums.Exclamation;
import majava.enums.GameEventType;
import majava.events.GameplayEvent;
import majava.events.JanObserver;
import majava.round.KyokuState;
import majava.summary.StateOfGame;

public abstract class GameUI implements JanObserver{
	protected static final int NUM_PLAYERS = 4;
	
	protected static final int DEAFULT_SLEEPTIME = 400, DEAFULT_SLEEPTIME_EXCLAMATION = 1500, DEAFULT_SLEEPTIME_ROUND_END = 2000;
	protected int sleepTime = DEAFULT_SLEEPTIME, sleepTimeExclamation = DEAFULT_SLEEPTIME_EXCLAMATION, sleepTimeRoundEnd = DEAFULT_SLEEPTIME_ROUND_END;
	
	
	protected RoundTracker roundTracker = null;
	protected KyokuState gameState = null;
	
	
	
	public GameUI(){}
	
	
	public abstract void startUI();
	public abstract void endUI();
	
	
	
	@Override
	public void update(GameplayEvent gameplayEvent, KyokuState stateOfGame) {
		gameState = stateOfGame;
		if (gameState != null) roundTracker = gameState.getRoundTracker();
		displayEvent(gameplayEvent);
	}
	
	public void displayEvent(final GameplayEvent event){
		switch(event.getEventType()){
		case DISCARDED_TILE: displayEventDiscardedTile(event); break;
		case MADE_OPEN_MELD: displayEventMadeOpenMeld(event); break;
		case DREW_TILE: displayEventDrewTile(event); break;
		case MADE_OWN_KAN: displayEventMadeOwnKan(event); break;
		case NEW_DORA_INDICATOR: displayEventNewDoraIndicator(event); break;
		
		case HUMAN_PLAYER_TURN_START: displayEventHumanTurnStart(event); break;
		case HUMAN_PLAYER_REACTION_START: displayEventHumanReactionStart(event); break;
		
		case START_OF_ROUND: displayEventStartOfRound(event); break;
		case PLAYER_TURN_START: displayEventPlayerTurnStart(event); break;
		case END_OF_ROUND: displayEventEndOfRound(event); break;
		case END: endUI(); break;
		case START: startUI(); break;
		default: break;
		}
		
		if (event.isExclamation()) showExclamation(event.getExclamation(), event.getSeat());
		
		if (shouldSleepForEvent(event))
			Pauser.pauseFor(sleepTime);
	}
	private boolean shouldSleepForEvent(GameplayEvent event){
		if (sleepTime < 0) return false;
		if (event.isExclamation() || event.getEventType() == GameEventType.PLAYER_TURN_START || event.isForHuman() || event.isStartEnd())
			return false;
		return true;
	}
	
	protected abstract void displayEventDiscardedTile(GameplayEvent event);
	protected abstract void displayEventMadeOpenMeld(GameplayEvent event);
	protected abstract void displayEventDrewTile(GameplayEvent event);
	protected abstract void displayEventMadeOwnKan(GameplayEvent event);
	protected abstract void displayEventNewDoraIndicator(GameplayEvent event);
	protected abstract void displayEventHumanTurnStart(GameplayEvent event);
	protected abstract void displayEventHumanReactionStart(GameplayEvent event);
	protected abstract void displayEventPlayerTurnStart(GameplayEvent event);
	
	protected abstract void displayEventStartOfRound(GameplayEvent event);
	protected abstract void displayEventEndOfRound(GameplayEvent event);
	
	protected abstract void showExclamation(Exclamation exclamation, int seat);
	
	
	public void setSleepTimes(int newSleepTime, int newSleepTimeExclamation, int newSleepTimeRoundEnd){
		sleepTime = newSleepTime;
		sleepTimeExclamation = newSleepTimeExclamation;
		sleepTimeRoundEnd = newSleepTimeRoundEnd;
	}
	
	
	
	public abstract void printErrorRoundAlreadyOver();
}
