package majava.player;

import majava.enums.TurnActionType;

public class DecisionTurnAction {
	private static final int NO_DISCARD_CHOSEN = -33333;
	
	
	private final TurnActionType turnAction;
	private final int discardIndex;
	
	
	private DecisionTurnAction(TurnActionType taction, int discardindx){
		turnAction = taction;
		discardIndex = discardindx;
	}
	private DecisionTurnAction(TurnActionType taction){this(taction, NO_DISCARD_CHOSEN);}
	
	
		
	
	public TurnActionType getTurnAction(){return turnAction;}
	
	public boolean choseKan(){return (choseAnkan() || choseMinkan());}
	public boolean choseAnkan(){return (turnAction == TurnActionType.ANKAN);}
	public boolean choseMinkan(){return (turnAction == TurnActionType.MINKAN);}
	public boolean choseTsumo(){return (turnAction == TurnActionType.TSUMO);}
	public boolean choseDiscard(){return (turnAction == TurnActionType.DISCARD);}
	public boolean choseRiichi(){return (turnAction == TurnActionType.RIICHI);}
	
	
	
	public int getChosenDiscardIndex(){
//		if (!choseDiscard()) throw new Exception();
		return discardIndex;
	}
	
	
	
	//factories
	public static DecisionTurnAction Discard(int discard){return new DecisionTurnAction(TurnActionType.UNDECIDED, discard);}
	
	public static DecisionTurnAction Ankan(){return new DecisionTurnAction(TurnActionType.UNDECIDED);}
	public static DecisionTurnAction Minkan(){return new DecisionTurnAction(TurnActionType.UNDECIDED);}	//should kans have an index?
	
	public static DecisionTurnAction Tsumo(){return new DecisionTurnAction(TurnActionType.UNDECIDED);}
	public static DecisionTurnAction Riichi(){return new DecisionTurnAction(TurnActionType.UNDECIDED);}
}
