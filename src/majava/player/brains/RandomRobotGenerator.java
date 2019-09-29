package majava.player.brains;

import java.util.ArrayList;
import java.util.Random;

import majava.player.Player;

public class RandomRobotGenerator{
	
	public static RobotBrain generateRandomComputerPlayer(){		
		ArrayList<RobotBrain> candidates = new ArrayList<RobotBrain>();
		
		candidates.add(new PonMonsterBot());
		candidates.add(new PonMonsterBot());
		candidates.add(new SevenTwinBot());
		candidates.add(new SevenTwinBot());
		
		candidates.add(new TseIiMenBot());
		candidates.add(new TseIiMenBot());
		candidates.add(new TseIiMenBot());
		candidates.add(new TseIiMenBot());
		
		candidates.add(new SimpleRobot());
//		candidates.add(new OrphanBot());	//not implemented yet
		
		RobotBrain picked = candidates.get((new Random()).nextInt(candidates.size()));
//		p.setPlayerName(picked.toString());
		return picked;
	}
	
}
