package majava.player;

import majava.enums.TurnActionType;

public class DecisionTurnAction {
	private static final int NO_DISCARD_CHOSEN = -33333;
	public static final DecisionTurnAction DUMMY = new DecisionTurnAction(TurnActionType.UNDECIDED);
	
	
	private final TurnActionType turnAction;
	private final int discardIndex;
	
	
	public DecisionTurnAction(TurnActionType taction, int discardindx){
		turnAction = taction;
		discardIndex = discardindx;
	}
	public DecisionTurnAction(TurnActionType taction){this(taction, NO_DISCARD_CHOSEN);}
	
	
		
	
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
	
	
	
	
	
	public String toString(){return turnAction + " _____ " + discardIndex + "\n";}
	
	
	//factories
	public static DecisionTurnAction Discard(int discard){return new DecisionTurnAction(TurnActionType.DISCARD, discard);}
	
	public static DecisionTurnAction Ankan(){return new DecisionTurnAction(TurnActionType.ANKAN);}
	public static DecisionTurnAction Minkan(){return new DecisionTurnAction(TurnActionType.MINKAN);}	//should kans have an index?
	
	public static DecisionTurnAction Tsumo(){return new DecisionTurnAction(TurnActionType.TSUMO);}
	public static DecisionTurnAction Riichi(){return new DecisionTurnAction(TurnActionType.RIICHI);}
}
