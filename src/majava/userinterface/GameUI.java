package majava.userinterface;

import utility.Pauser;
import majava.enums.Exclamation;
import majava.enums.GameEventType;
import majava.events.JanObserver;
import majava.round.KyokuState;

public abstract class GameUI implements JanObserver{
	protected static final int NUM_PLAYERS = 4;
	protected static final int DEAFULT_SLEEPTIME = 400, DEAFULT_SLEEPTIME_EXCLAMATION = 1500, DEAFULT_SLEEPTIME_ROUND_END = 2000;
	
	
	protected int sleepTime = DEAFULT_SLEEPTIME, sleepTimeExclamation = DEAFULT_SLEEPTIME_EXCLAMATION, sleepTimeRoundEnd = DEAFULT_SLEEPTIME_ROUND_END;
	
	
	protected KyokuState gameState = null;
	
	
	
	public GameUI(){}
	
	
	public abstract void startUI();
	public abstract void endUI();
	
	
	
	@Override
	public void update(KyokuState stateOfGame) {
		gameState = stateOfGame;
		displayEvent(stateOfGame.getEvent());
	}
	
	public void displayEvent(final GameEventType event){
		switch(event){
		case DISCARDED_TILE: displayEventDiscardedTile(); break;
		case MADE_OPEN_MELD: displayEventMadeOpenMeld(); break;
		case DREW_TILE: displayEventDrewTile(); break;
		case MADE_OWN_KAN: displayEventMadeOwnKan(); break;
		case NEW_DORA_INDICATOR: displayEventNewDoraIndicator(); break;
		
		case HUMAN_PLAYER_TURN_START: displayEventHumanTurnStart(); break;
		case HUMAN_PLAYER_REACTION_START: displayEventHumanReactionStart(); break;
		
		case START_OF_ROUND: displayEventStartOfRound(); break;
		case PLAYER_TURN_START: displayEventPlayerTurnStart(); break;
		case END_OF_ROUND: displayEventEndOfRound(); break;
		case END: endUI(); break;
		case START: startUI(); break;
		default: break;
		}
		
		
		//////////////////////////////////////////////////////////
		if (event.isExclamation())
			; //showExclamation(event.getExclamation(), event.getSeat());
		
		//////////////////////////////////////////////////////////
		if (shouldSleepForEvent(event))
			Pauser.pauseFor(sleepTime);
	}
//	private boolean shouldSleepForEvent(GameplayEvent event){
	private boolean shouldSleepForEvent(GameEventType event){
		if (sleepTime < 0) return false;
		
		if (
				event.isExclamation() || 
				event == GameEventType.PLAYER_TURN_START || 
				event.isForHuman() || 
				event.isStartEnd()
			){
			return false;
		}
		
		return true;
	}
	
	protected abstract void displayEventDiscardedTile();
	protected abstract void displayEventMadeOpenMeld();
	protected abstract void displayEventDrewTile();
	protected abstract void displayEventMadeOwnKan();
	protected abstract void displayEventNewDoraIndicator();
	protected abstract void displayEventHumanTurnStart();
	protected abstract void displayEventHumanReactionStart();
	protected abstract void displayEventPlayerTurnStart();
	
	protected abstract void displayEventStartOfRound();
	protected abstract void displayEventEndOfRound();
	
	protected abstract void showExclamation(Exclamation exclamation, int seat);
	
	
	public void setSleepTimes(int newSleepTime, int newSleepTimeExclamation, int newSleepTimeRoundEnd){
		sleepTime = newSleepTime;
		sleepTimeExclamation = newSleepTimeExclamation;
		sleepTimeRoundEnd = newSleepTimeRoundEnd;
	}
	
	
	
	public abstract void printErrorRoundAlreadyOver();
}
