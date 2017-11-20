package majava.userinterface;

import utility.Pauser;
import majava.hand.Hand;
import majava.Pond;
import majava.RoundTracker;
import majava.Wall;
import majava.enums.Exclamation;
import majava.events.GameplayEvent;
import majava.player.Player;
import majava.summary.RoundResultSummary;
import majava.summary.entity.RoundEntities;

public abstract class GameUI {

	
	protected static final int NUM_PLAYERS = 4;
	
	protected static final int DEAFULT_SLEEPTIME = 400, DEAFULT_SLEEPTIME_EXCLAMATION = 1500, DEAFULT_SLEEPTIME_ROUND_END = 2000;
	protected int mSleepTime = DEAFULT_SLEEPTIME, mSleepTimeExclamation = DEAFULT_SLEEPTIME_EXCLAMATION, mSleepTimeRoundEnd = DEAFULT_SLEEPTIME_ROUND_END;
	
	
//	protected RoundTracker mRoundTracker;
	protected RoundEntities roundEntities = null;
	protected RoundResultSummary resultSummary = null;
	
	
	
	public GameUI(){
		
	}
	
	
	public abstract void startUI();
	public abstract void endUI();
	
	
	
	
	public void displayEvent(final GameplayEvent event){
		switch(event.getEventType()){
		case DISCARDED_TILE: __displayEventDiscardedTile(); break;
		case MADE_OPEN_MELD: __displayEventMadeOpenMeld(); break;
		case DREW_TILE: __displayEventDrewTile(); break;
		case MADE_OWN_KAN: __displayEventMadeOwnKan(); break;
		case NEW_DORA_INDICATOR: __displayEventNewDoraIndicator(); break;
		
		case HUMAN_PLAYER_TURN_START: __displayEventHumanTurnStart(); break;
		
		case START_OF_ROUND: __displayEventStartOfRound(); break;
		case PLACEHOLDER: __displayEventPlaceholder(); break;
		case END_OF_ROUND: __displayEventEndOfRound(); break;
		default: break;
		}
		
		if (event.isExclamation()) __showExclamation(event.getExclamation(), event.getSeat());
		
		if (mSleepTime > 0 && !event.isExclamation() && !event.isPlaceholder())
			Pauser.pauseFor(mSleepTime);
	}
	
	protected abstract void __displayEventDiscardedTile();
	protected abstract void __displayEventMadeOpenMeld();
	protected abstract void __displayEventDrewTile();
	protected abstract void __displayEventMadeOwnKan();
	protected abstract void __displayEventNewDoraIndicator();
	protected abstract void __displayEventHumanTurnStart();
	protected abstract void __displayEventPlaceholder();
	
	protected abstract void __displayEventStartOfRound();
	protected abstract void __displayEventEndOfRound();
	
	protected abstract void __showExclamation(Exclamation exclamation, int seat);
	
	
	
	
	
	public void syncWithRoundTracker(RoundEntities receivedRoundEntities){
		roundEntities = receivedRoundEntities;
	}
	
//	public void syncWithRoundTracker(RoundTracker rTracker, Player[] pPlayers, Hand[] pHands, TileList[] pHandTiles, Pond[] pPonds, TileList[] pPondTiles, Wall wall, Tile[] tilesW){
//		
//		mRoundTracker = rTracker;
//
//		mPTrackers = new PlayerTracker[NUM_PLAYERS];
//		for (int i = 0; i < NUM_PLAYERS; i++) mPTrackers[i] = new PlayerTracker(pPlayers[i], pHands[i], pHandTiles[i], pPonds[i], pPondTiles[i]);
//		
//		mWall = wall;mTilesW = tilesW;
//	}
	
	
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
	public abstract boolean resultChosenTurnActionWasDiscard();
	public abstract boolean resultChosenTurnActionWasAnkan();
	public abstract boolean resultChosenTurnActionWasMinkan();
	public abstract boolean resultChosenTurnActionWasRiichi();
	public abstract boolean resultChosenTurnActionWasTsumo();
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public abstract int resultChosenDiscardIndex();
	
	public abstract boolean askUserInputCall(boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon);
	public abstract boolean resultChosenCallWasNone();
	public abstract boolean resultChosenCallWasChiL();
	public abstract boolean resultChosenCallWasChiM();
	public abstract boolean resultChosenCallWasChiH();
	public abstract boolean resultChosenCallWasPon();
	public abstract boolean resultChosenCallWasKan();
	public abstract boolean resultChosenCallWasRon();
	
	
	
	public abstract void printErrorRoundAlreadyOver();
	
}
