package majava.hand.handcheckers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import majava.enums.MeldType;
import majava.hand.Hand;
import majava.tiles.GameTile;
import majava.util.TileKnowledge;


//checks what calls a hand can make on a given tile (chi, pon, ron, etc)
public class CallabilityChecker {
	private static final int NUM_PARTNERS_NEEDED_TO_PON = 2;
	private static final int NUM_PARTNERS_NEEDED_TO_KAN = 3;
	private static final int OFFSET_CHI_L1 = 1, OFFSET_CHI_L2 = 2;
	private static final int OFFSET_CHI_M1 = -1,  OFFSET_CHI_M2 = 1;
	private static final int OFFSET_CHI_H1 = -2, OFFSET_CHI_H2 = -1;
	
	
	private final Hand hand;
	
	public CallabilityChecker(Hand handToCheck){
		hand = handToCheck;
	}
	
	
	
	//ableToCall methods
	public boolean tileIsCallable(GameTile candidate){
		return ableToChi(candidate) || ableToPon(candidate) || ableToRon(candidate);	//revert to [flagCanRon = handIsInTenpai && ableToRon(candidate)] if problems happen (they shouldn't happen) 
	}
	public boolean ableToChiL(GameTile candidate){return candidate.canCompleteChiL() && !getPartnerIndicesChiL(candidate).isEmpty();}
	public boolean ableToChiM(GameTile candidate){return candidate.canCompleteChiM() && !getPartnerIndicesChiM(candidate).isEmpty();}
	public boolean ableToChiH(GameTile candidate){return candidate.canCompleteChiH() && !getPartnerIndicesChiH(candidate).isEmpty();}
	public boolean ableToChi(GameTile candidate){return ableToChiL(candidate) || ableToChiM(candidate) || ableToChiH(candidate);}
	public boolean ableToPon(GameTile candidate){return !getPartnerIndicesPon(candidate).isEmpty();}
	public boolean ableToKan(GameTile candidate){return !getPartnerIndicesKan(candidate).isEmpty();}
	public boolean ableToRon(GameTile candidate){return hand.getTenpaiWaits().contains(candidate);}
	
	
	private boolean tileCameFromChiablePlayer(GameTile candidate){
		return (candidate.getOrignalOwner() == hand.getOwnerSeatWind()) || 
				(candidate.getOrignalOwner() == hand.getOwnerSeatWind().kamichaWind());
	}
	
	
	//returns the partner indices list for a given meld type
	public List<Integer> getPartnerIndices(GameTile candidate, MeldType meldType){
		switch (meldType){
		case CHI_L: return getPartnerIndicesChiL(candidate);
		case CHI_M: return getPartnerIndicesChiM(candidate);
		case CHI_H: return getPartnerIndicesChiH(candidate);
		case PON: return getPartnerIndicesPon(candidate);
		case KAN: return getPartnerIndicesKan(candidate);
		default: return null;
		}
	}
	
	//シュンツ
	private List<Integer> getPartnerIndicesChiType(GameTile candidate, int offset1, int offset2){
		if (!tileCameFromChiablePlayer(candidate)) return emptyIndicesList();
		if (hand.contains(candidate.getId() + offset1) && hand.contains(candidate.getId() + offset2))
			return Arrays.asList(hand.indexOf(candidate.getId() + offset1), hand.indexOf(candidate.getId() + offset2));
		return emptyIndicesList();
	}
	private List<Integer> getPartnerIndicesChiL(GameTile candidate){return getPartnerIndicesChiType(candidate, OFFSET_CHI_L1, OFFSET_CHI_L2);}
	private List<Integer> getPartnerIndicesChiM(GameTile candidate){return getPartnerIndicesChiType(candidate, OFFSET_CHI_M1, OFFSET_CHI_M2);}
	private List<Integer> getPartnerIndicesChiH(GameTile candidate){return getPartnerIndicesChiType(candidate, OFFSET_CHI_H1, OFFSET_CHI_H2);}
	
	//コーツ
	private List<Integer> getPartnerIndicesMulti(GameTile candidate, int numPartnersNeeded){		
		//pon/kan is possible if there are enough partners in the hannd to form the meld
		List<Integer> partnerIndices = hand.findAllIndicesOf(candidate);
		if (partnerIndices.size() >= numPartnersNeeded)
			return partnerIndices.subList(0, numPartnersNeeded);
		return emptyIndicesList();
	}
	public List<Integer> getPartnerIndicesPon(GameTile candidate){return getPartnerIndicesMulti(candidate, NUM_PARTNERS_NEEDED_TO_PON);}
	public List<Integer> getPartnerIndicesKan(GameTile candidate){return getPartnerIndicesMulti(candidate, NUM_PARTNERS_NEEDED_TO_KAN);}
	private static List<Integer> emptyIndicesList(){return new ArrayList<Integer>();}
}
