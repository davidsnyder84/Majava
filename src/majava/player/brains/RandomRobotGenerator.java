package majava.player.brains;

import java.util.ArrayList;
import java.util.Random;

import majava.player.Player;

public class RandomRobotGenerator{
	
	public static RobotBrain generateRandomComputerPlayer(Player p){		
		ArrayList<RobotBrain> candidates = new ArrayList<RobotBrain>();
		
		candidates.add(new PonMonsterBot(p));
		candidates.add(new PonMonsterBot(p));
		candidates.add(new SevenTwinBot(p));
		candidates.add(new SevenTwinBot(p));
		
		candidates.add(new TseIiMenBot(p));
		candidates.add(new TseIiMenBot(p));
		candidates.add(new TseIiMenBot(p));
		candidates.add(new TseIiMenBot(p));
		
		candidates.add(new SimpleRobot(p));
//		candidates.add(new OrphanBot(p));	//not implemented yet
		
		RobotBrain picked = candidates.get((new Random()).nextInt(candidates.size()));
		p.setPlayerName(picked.toString());
		return picked;
	}
	
}
