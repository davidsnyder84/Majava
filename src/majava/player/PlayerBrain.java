package majava.player;

import majava.enums.Exclamation;
import majava.tiles.GameTile;

public abstract class PlayerBrain {
	
	
	
	
	
	//used to indicate what call a player wants to make on another player's discard
	protected static enum CallType{
		NONE, CHI_L, CHI_M, CHI_H, PON, KAN, RON, CHI, UNDECIDED;
		
		@Override
		public String toString(){
			switch (this){
			case CHI_L: case CHI_M: case CHI_H: return "Chi";
			case PON: return "Pon";
			case KAN: return "Kan";
			case RON: return "Ron";
			default: return "None";
			}
		}
		public Exclamation toExclamation(){
			switch (this){
			case CHI_L: case CHI_M: case CHI_H: return Exclamation.CHI;
			case PON: return Exclamation.PON;
			case KAN: return Exclamation.KAN;
			case RON: return Exclamation.RON;
			case NONE: return Exclamation.NONE;
			default: return Exclamation.UNKNOWN;
			}
		}
	}
	
	
	
	
	

	protected CallType callStatus;
	protected Player player;
	
	
	
	
	
	
	public PlayerBrain(Player p){
		player = p;
		if (player == null) player = new Player();
		
		callStatus = CallType.NONE;
	}
	
	
	
	
	
	
	public abstract void chooseTurnAction();
	
	
	public abstract void reactToDiscard(GameTile tileToReactTo);
	
	
	
	
	
	
	//returns call status as an exclamation
	public final Exclamation getCallStatusExclamation(){return callStatus.toExclamation();}
	
	//returns true if the player called a tile
	public final boolean called(){return (callStatus != CallType.NONE);}
	//individual call statuses
	public final boolean calledChi(){return (calledChiL() || calledChiM() || calledChiH());}
	public final boolean calledChiL(){return (callStatus == CallType.CHI_L);}
	public final boolean calledChiM(){return (callStatus == CallType.CHI_M);}
	public final boolean calledChiH(){return (callStatus == CallType.CHI_H);}
	public final boolean calledPon(){return (callStatus == CallType.PON);}
	public final boolean calledKan(){return (callStatus == CallType.KAN);}
	public final boolean calledRon(){return (callStatus == CallType.RON);}
	
	
	public void clearCallStatus(){callStatus = CallType.NONE;}
	
	
	
	
	
	public abstract boolean isHuman(); 
	
	@Override
	public String toString(){return "UnknownnnnnnnnnBrain";}
	
}
