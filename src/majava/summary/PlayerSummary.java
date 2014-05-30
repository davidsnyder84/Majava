package majava.summary;

import majava.enums.Wind;

//represents a summary of a player's information
public class PlayerSummary{
		private final String pPlayerName;
		private final int pPlayerID;
		private final String pControllerString;
		
		private final int mPlayerNum;
		private final Wind pSeatWind;
		private final int pPoints;
		
		public PlayerSummary(String playername, int playerid, String controllerstring, int playernum, Wind seatwind, int points){
			pPlayerName = playername;
			pPlayerID = playerid;
			pControllerString = controllerstring;
			mPlayerNum = playernum;
			pSeatWind = seatwind;
			pPoints = points;
		}
		public String getPlayerName(){return pPlayerName;}
		public int getPlayerID(){return pPlayerID;}
		public String getControllerAsString(){return pControllerString;}
		
		public int getPlayerNumber(){return mPlayerNum;}
		public Wind getSeatWind(){return pSeatWind;}
		public boolean isDealer(){return pSeatWind == Wind.EAST;}
		
		public int getPoints(){return pPoints;}
}