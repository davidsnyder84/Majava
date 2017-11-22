package majava.userinterface;

import utility.Pauser;
import majava.RoundTracker;
import majava.enums.Exclamation;
import majava.events.GameplayEvent;
import majava.events.JanObserver;
import majava.summary.StateOfGame;
import majava.summary.RoundResultSummary;

public abstract class GameUI implements JanObserver{
	
	
	protected static final int NUM_PLAYERS = 4;
	
	protected static final int DEAFULT_SLEEPTIME = 400, DEAFULT_SLEEPTIME_EXCLAMATION = 1500, DEAFULT_SLEEPTIME_ROUND_END = 2000;
	protected int mSleepTime = DEAFULT_SLEEPTIME, mSleepTimeExclamation = DEAFULT_SLEEPTIME_EXCLAMATION, mSleepTimeRoundEnd = DEAFULT_SLEEPTIME_ROUND_END;
	
	
	protected RoundTracker roundTracker = null;
	protected StateOfGame gameState = null;
	
	
	
	public GameUI(){}
	
	
	public abstract void startUI();
	public abstract void endUI();
	
	
	
	@Override
	public void update(GameplayEvent gameplayEvent, StateOfGame stateOfGame) {
		gameState = stateOfGame;
		roundTracker = gameState.getRoundTracker();
		displayEvent(gameplayEvent);
	}
	
	public void displayEvent(final GameplayEvent event){
		switch(event.getEventType()){
		case DISCARDED_TILE: __displayEventDiscardedTile(event); break;
		case MADE_OPEN_MELD: __displayEventMadeOpenMeld(event); break;
		case DREW_TILE: __displayEventDrewTile(event); break;
		case MADE_OWN_KAN: __displayEventMadeOwnKan(event); break;
		case NEW_DORA_INDICATOR: __displayEventNewDoraIndicator(event); break;
		
		case HUMAN_PLAYER_TURN_START: __displayEventHumanTurnStart(event); break;
		case HUMAN_PLAYER_REACTION_START: __displayEventHumanReactionStart(event); break;
		
		case START_OF_ROUND: __displayEventStartOfRound(event); break;
		case PLACEHOLDER: __displayEventPlaceholder(event); break;
		case END_OF_ROUND: __displayEventEndOfRound(event); break;
		default: break;
		}
		
		if (event.isExclamation()) __showExclamation(event.getExclamation(), event.getSeat());
		
		if (shouldSleepForEvent(event))
			Pauser.pauseFor(mSleepTime);
	}
	private boolean shouldSleepForEvent(GameplayEvent event){
		if (mSleepTime < 0) return false;
		if (event.isExclamation() || event.isPlaceholder() || event.isForHuman())
			return false;
		return true;
	}
	
	protected abstract void __displayEventDiscardedTile(GameplayEvent event);
	protected abstract void __displayEventMadeOpenMeld(GameplayEvent event);
	protected abstract void __displayEventDrewTile(GameplayEvent event);
	protected abstract void __displayEventMadeOwnKan(GameplayEvent event);
	protected abstract void __displayEventNewDoraIndicator(GameplayEvent event);
	protected abstract void __displayEventHumanTurnStart(GameplayEvent event);
	protected abstract void __displayEventHumanReactionStart(GameplayEvent event);
	protected abstract void __displayEventPlaceholder(GameplayEvent event);
	
	protected abstract void __displayEventStartOfRound(GameplayEvent event);
	protected abstract void __displayEventEndOfRound(GameplayEvent event);
	
	protected abstract void __showExclamation(Exclamation exclamation, int seat);
	
	
	public void setSleepTimes(int sleepTime, int sleepTimeExclamation, int sleepTimeRoundEnd){
		mSleepTime = sleepTime;
		mSleepTimeExclamation = sleepTimeExclamation;
		mSleepTimeRoundEnd = sleepTimeRoundEnd;
	}
	
	
	
	public abstract void printErrorRoundAlreadyOver();
}
