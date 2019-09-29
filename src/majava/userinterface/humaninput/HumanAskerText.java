package majava.userinterface.humaninput;

import java.util.List;
import java.util.Scanner;

import majava.enums.CallType;
import majava.enums.TurnActionType;
import majava.hand.Hand;
import majava.player.Player;
import majava.tiles.GameTile;

public class HumanAskerText implements HumanInputAsker{
	private static final int NONE = -1;
	
	
	
	@Override
	public TurnActionType selectTurnAction(Player player, Hand hand, List<TurnActionType> listOfPossibleTurnActions){
		if (listOfPossibleTurnActions.isEmpty())
			return TurnActionType.DISCARD;
		
		println(player.toString());
		println("Possible options: " + listOfPossibleTurnActions.toString());
		print("\tWhat do? (enter number): ");
		
		int numberInput = getNumberInput();
		if (numberInput == NONE)
			return TurnActionType.DISCARD;
		
		
		TurnActionType chosenTaction = listOfPossibleTurnActions.get(numberInput);
		return chosenTaction;
	}
	
	@Override
	public int selectDiscardIndex(Hand hand){
		println("-----Now it is time to discard for player " + hand.getOwnerSeatWind() + ":::");
		println(hand.toString());
		print("\tEnter discard index (enter number): ");
		
		int numberInput = getNumberInput();
		return numberInput;
	}
	
	@Override
	public CallType chooseReaction(Player player, Hand hand, GameTile tileToReactTo, List<CallType> listOfPossibleReactions){
		println("******ASKING reaction" + player.getSeatWind().toChar() + ": ");
		println("Possible options: " + callnames(listOfPossibleReactions));
		print("\tWhat do? (enter number): ");
		
		int numberInput = getNumberInput();
		if (numberInput == NONE)
			return CallType.NONE;
		
		CallType chosenCall = listOfPossibleReactions.get(numberInput);
		return chosenCall;
	}
	private static String callnames(List<CallType> listOfPossibleReactions){
		String string = "[";
		for (CallType ct: listOfPossibleReactions)
			string += ct.name() + ", ";
		return string + "]";
	}
	
	
	
	@SuppressWarnings("resource") private static int getNumberInput(){
		return (new Scanner(System.in)).nextInt() - 1;
	}
	
	public static void println(String prints){System.out.println(prints);}public static void println(){System.out.println("");}public static void println(int prints){System.out.println(prints+"");}public static void print(String prints){System.out.print(prints);} public static void waitt(){(new Scanner(System.in)).next();}
}
