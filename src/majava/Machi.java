package majava;

import java.util.ArrayList;

import majava.tiles.Tile;


public class Machi {
	
	
	
	private MeldType mWaitType;
	private Tile mCallCandidate;
	private ArrayList<Integer> mPartnerIndices;
	private ArrayList<Integer> mPartnerIDs;
	
	
	public Machi(ArrayList<Integer> partnerIndices, Tile candidate, MeldType waitType){
		
		mWaitType = waitType;
		mCallCandidate = candidate;
		
		//make a copy of the partner index list
		mPartnerIndices = new ArrayList<Integer>(partnerIndices);
		
		mPartnerIDs = null;
	}
	public Machi(MeldType waitType){this(null, null, waitType);}
	public Machi(){this(MeldType.NONE);}
	
	
	
	
	
	
	
	//accessors
	public MeldType getWaitType(){return mWaitType;}
	public Tile getCallCandidate(){return mCallCandidate;}

	public ArrayList<Integer> getPartnerIndices(){return mPartnerIndices;}
	public ArrayList<Integer> getPartnerIDs(){return mPartnerIDs;}
	
	
	
	
	
	
	
}
