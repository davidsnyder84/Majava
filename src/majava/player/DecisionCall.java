package majava.player;

import majava.enums.CallType;
import majava.enums.Exclamation;
import majava.tiles.GameTile;

public class DecisionCall {
	public static final DecisionCall DUMMY = new DecisionCall(GameTile.DUMMY_TILE);
	
	private final CallType callStatus;
	private final GameTile candidate;
	
	
	public DecisionCall(CallType ct, GameTile cand){
		callStatus = ct;
		candidate = cand;
	}
	public DecisionCall(GameTile cand){this(CallType.NONE, cand);}
	
	
	
	public GameTile getCandidate(){return candidate;}
	
	public CallType getCallStatus(){return callStatus;}
	
	
	
	
	public Exclamation getCallStatusExclamation(){return callStatus.toExclamation();}
	
	public boolean called(){return (callStatus != CallType.NONE);}
	public boolean calledChi(){return (calledChiL() || calledChiM() || calledChiH());}
	public boolean calledChiL(){return (callStatus == CallType.CHI_L);}
	public boolean calledChiM(){return (callStatus == CallType.CHI_M);}
	public boolean calledChiH(){return (callStatus == CallType.CHI_H);}
	public boolean calledPon(){return (callStatus == CallType.PON);}
	public boolean calledKan(){return (callStatus == CallType.KAN);}
	public boolean calledRon(){return (callStatus == CallType.RON);}
	
	
	
	
	
	public String toString(){return callStatus + " +++++ " + candidate + "\n";}
	
	
	
	//factories
	public static DecisionCall None(GameTile cand){return new DecisionCall(cand);}
	
	public static DecisionCall ChiL(GameTile cand){return new DecisionCall(CallType.CHI_L, cand);}
	public static DecisionCall ChiM(GameTile cand){return new DecisionCall(CallType.CHI_M, cand);}
	public static DecisionCall ChiH(GameTile cand){return new DecisionCall(CallType.CHI_H, cand);}
	public static DecisionCall Pon(GameTile cand){return new DecisionCall(CallType.PON, cand);}
	public static DecisionCall Kan(GameTile cand){return new DecisionCall(CallType.KAN, cand);}
	public static DecisionCall Ron(GameTile cand){return new DecisionCall(CallType.RON, cand);}
}
