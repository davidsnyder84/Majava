package majava.userinterface.humaninput;

import java.util.List;

import majava.enums.CallType;
import majava.enums.TurnActionType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;
import majava.userinterface.graphicalinterface.window.TableViewBase;

public class HumanAskerGUI implements HumanInputAsker{
	
	
	private final TableViewBase tableWindow;
	
	
	public HumanAskerGUI(TableViewBase tblwndw){
		tableWindow = tblwndw;
	}
	
	
	
	
	
	//-------------turn actions
	@Override
	public TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions){
		tableWindow.updateEverything();
		
//		tableWindow.movePromptPanelToSeat(player.getPlayerNumber()); //doesn't appear in the right place yet, because of playernum (can tableWindow figure it out on its own?)
		
		TurnActionType chosenAction = tableWindow.askUserInputTurnAction(
										player.handSize(),
										player.ableToRiichi(),
										player.ableToAnkan(),
										player.ableToMinkan(),
										player.ableToTsumo()
									);
		return chosenAction;
	}
	
	@Override
	public int selectDiscardIndex(Hand hand){
		//table window sets discard index during selectTurnAction
		return tableWindow.getChosenDiscardIndex() - 1;
	}
	
	
	
	
	
	
	//-------------calls
	@Override
	public CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions){
		
//		tableWindow.movePromptPanelToSeat(player.getPlayerNumber());
		
		CallType chosenCalltype = tableWindow.askUserInputCall(
				player.ableToCallChiL(tileToReactTo),
				player.ableToCallChiM(tileToReactTo),
				player.ableToCallChiH(tileToReactTo),
				player.ableToCallPon(tileToReactTo),
				player.ableToCallKan(tileToReactTo),
				player.ableToCallRon(tileToReactTo)
				);
		
		return chosenCalltype;
	}
	
	
}
