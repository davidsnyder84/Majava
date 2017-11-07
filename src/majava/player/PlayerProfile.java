package majava.player;
import java.util.Random;

//holds profile information such as name and playerID
public class PlayerProfile {
	private static final String DEFAULT_PLAYERNAME = "Saki";
	
	private String playerName;
	private int playerID;
	
	
	public PlayerProfile(String pname, int pid){
		setPlayerName(pname);
		setID(pid);
	}
	public PlayerProfile(String pname){this(pname, generateRandomID());}
	public PlayerProfile(){}
	
	
	//getters
	public String getPlayerName(){return playerName;}
	public int getPlayerID(){return playerID;}
	
	
	//setters
	public void setPlayerName(String newName){
		if (newName == null)
			if (playerName == null) playerName = DEFAULT_PLAYERNAME;
			else return;
		playerName = newName;
	}
	private void setID(int newID){playerID = newID;}
	
	private static int generateRandomID(){return (new Random()).nextInt();}
}
