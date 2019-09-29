package majava.userinterface.humaninput;

import java.util.List;

import majava.enums.CallType;
import majava.enums.TurnActionType;
import majava.hand.Hand;
import majava.player.Player;
import majava.player.brains.HumanBrain;
import majava.tiles.GameTile;

public class HumanAskerGUI implements HumanInputAsker{
	
	
	
	/*
	//get user input from window
	public void askUserInputCall(int seatNumber, boolean canChiL, boolean canChiM, boolean canChiH, boolean canPon, boolean canKan, boolean canRon){
		CallType chosenCallType = tableWindow.askUserInputCall(canChiL, canChiM, canChiH, canPon, canKan, canRon);;
		
		controllerOfPlayer(seatNumber).setCallChosenByHuman(chosenCallType);
	}
	
	public void askUserInputTurnAction(int handSize, boolean canRiichi, boolean canAnkan, boolean canMinkan, boolean canTsumo){
		TurnActionType chosenAction = tableWindow.askUserInputTurnAction(handSize, canRiichi, canAnkan, canMinkan, canTsumo);
		controllerOfCurrentPlayer().setTurnActionChosenByHuman(chosenAction);
		
		if (chosenAction == TurnActionType.DISCARD){
			int chosenIndex = tableWindow.getChosenDiscardIndex() - 1;
			controllerOfCurrentPlayer().setDiscardIndexChosenByHuman(chosenIndex);
		}
	}
	//returns the index of the clicked discard. returns negative if no discard chosen.
	public int resultChosenDiscardIndex(){return tableWindow.getChosenDiscardIndex();}
	
	private HumanBrain controllerOfCurrentPlayer(){return controllerOfPlayer(gameState.whoseTurn());}
	private HumanBrain controllerOfPlayer(int playerNum){return (HumanBrain) gameState.getControllerForPlayer(playerNum);}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void displayEventHumanTurnStart(){
		tableWindow.updateEverything();
		
//		Player p = event.getRelatedPlayer();
		Player p = gameState.currentPlayer();
		
		tableWindow.movePromptPanelToSeat(p.getPlayerNumber());
		askUserInputTurnAction(
				p.handSize(),
				p.ableToRiichi(),
				p.ableToAnkan(),
				p.ableToMinkan(),
				p.ableToTsumo()
				);
	}

	@Override
	protected void displayEventHumanReactionStart() {
//		Player p = event.getRelatedPlayer();
//		GameTile tile = event.getRelatedTile();
		
//		Player p = event.getRelatedPlayer();
		Player p = gameState.getPlayer(0); ///////need a way to figure out which human player to get reaction for
		GameTile tile = gameState.getMostRecentDiscard();
		
		tableWindow.movePromptPanelToSeat(p.getPlayerNumber());
		askUserInputCall(
				p.getPlayerNumber(),
				p.ableToCallChiL(tile),
				p.ableToCallChiM(tile),
				p.ableToCallChiH(tile),
				p.ableToCallPon(tile),
				p.ableToCallKan(tile),
				p.ableToCallRon(tile)
				);
	}
	
	
	
	*/
	
	@Override
	public TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions){
		return null;
	}
	
	@Override
	public int selectDiscardIndex(Hand hand){
		return 98;
	}
	
	@Override
	public CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions){
		return null;
	}
	
}
