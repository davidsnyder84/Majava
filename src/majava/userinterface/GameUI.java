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
	protected RoundResultSummary resultSummary = null;
	
	
	
	public GameUI(){}
	
	
	public abstract void startUI();
	public abstract void endUI();
	
	
	
	@Override
	public void update(GameplayEvent gameplayEvent, StateOfGame stateOfGame) {
		gameState = stateOfGame;
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
		
		if (mSleepTime > 0 && !event.isExclamation() && !event.isPlaceholder())
			Pauser.pauseFor(mSleepTime);
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
	
	
	
	
	
	public void syncWithRoundTracker(RoundTracker tracker, StateOfGame stateOfGame){
		roundTracker = tracker;
		gameState = stateOfGame;
	}
	
	
	public void setRoundResult(RoundResultSummary resum){
		resultSummary = resum;
	}
	
	
	public void setSleepTimes(int sleepTime, int sleepTimeExclamation, int sleepTimeRoundEnd){
		mSleepTime = sleepTime;
		mSleepTimeExclamation = sleepTimeExclamation;
		mSleepTimeRoundEnd = sleepTimeRoundEnd;
	}
	
	
	
	
	//user input
	public abstract void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo);
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public abstract int resultChosenDiscardIndex();
	public abstract boolean askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon);
	
	
	
	public abstract void printErrorRoundAlreadyOver();
	
	public void movePromptPanelToSeat(int seat){}
}
