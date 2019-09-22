package majava.player;
import java.util.Random;

//holds profile information such as name and playerID
public class PlayerProfile {
	private static final String DEFAULT_PLAYERNAME = "Miyanaga";
	
	private final String playerName;
	private final int playerID;
	
	
	public PlayerProfile(String pname, int pid){
		if (pname == null)
			playerName = DEFAULT_PLAYERNAME;
		else
			playerName = pname;
		
		
		playerID = pid;
	}
	public PlayerProfile(String pname){this(pname, generateRandomID());}
	
	public PlayerProfile withName(String newName){return new PlayerProfile(newName, playerID);}
	
	
	
	//getters
	public String getPlayerName(){return playerName;}
	public int getPlayerID(){return playerID;}
	
	
	//setter
	public PlayerProfile setPlayerName(String newName){return this.withName(newName);}
	
	
	private static int generateRandomID(){return (new Random()).nextInt();}
}
