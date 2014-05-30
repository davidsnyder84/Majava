package majava.summary;

import majava.enums.Wind;

//represents a summary of a player's information
public class PlayerSummary{
		private final String pPlayerName;
		private final int pPlayerID;
		private final String pControllerString;
		private final Wind pSeatWind;
		private final int pPoints;
		
		public PlayerSummary(String playername, int playerid, String controllerstring, Wind seatwind, int points){
			pPlayerName = playername;
			pPlayerID = playerid;
			pControllerString = controllerstring;
			pSeatWind = seatwind;
			pPoints = points;
		}
		public String getPlayerName(){return pPlayerName;}
		public int getPlayerID(){return pPlayerID;}
		public String getControllerAsString(){return pControllerString;}
		public Wind getSeatWind(){return pSeatWind;}
		public boolean isDealer(){return pSeatWind == Wind.EAST;}
		public int getPoints(){return pPoints;}
}