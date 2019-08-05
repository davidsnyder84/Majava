package majava.summary;

import majava.enums.Wind;
import majava.player.Player;

//represents an immutable summary of a player's information
public class PlayerSummary{
		private final String playerName;
		private final int playerID;
		private final String controllerString;
		
		private final int playerNumber;
		private final Wind seatWind;
		private final int points;
		
		private PlayerSummary(String name, int id, String controllerstring, int playernum, Wind seatwind, int pointsAmount){
			playerName = name;
			playerID = id;
			controllerString = controllerstring;
			playerNumber = playernum;
			seatWind = seatwind;
			points = pointsAmount;
		}
		public PlayerSummary(Player p){
			this(p.getPlayerName(), p.getPlayerID(), p.getControllerAsString(), p.getPlayerNumber(), p.getSeatWind(), p.getPoints());
		}
		
		
		
		public String getPlayerName(){return playerName;}
		public int getPlayerID(){return playerID;}
		public String getControllerAsString(){return controllerString;}
		
		public int getPlayerNumber(){return playerNumber;}
		public Wind getSeatWind(){return seatWind;}
		public boolean isDealer(){return seatWind == Wind.EAST;}
		
		public int getPoints(){return points;}
		
		@Override
		public String toString(){return "Player " + playerNumber + " (" + seatWind.toChar() + ")";}
		@Override
		public boolean equals(Object other){
			return ((PlayerSummary)other).playerNumber == playerNumber;
		}
		@Override
		public int hashCode(){
			return playerID + playerNumber + points;
		}
		
		
		public static PlayerSummary getSummaryFor(Player p){
			if (p == null) return null;
			return new PlayerSummary(p);
		}
}