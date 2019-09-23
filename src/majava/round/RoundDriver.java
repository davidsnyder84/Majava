package majava.round;

public class RoundDriver {
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Kyokubu)!");
		
		Kyoku round = new Kyoku();
//		System.out.println(round.getWall().currentPosition());   System.out.println(round.getPlayers().seatE().handSize());
//		System.out.println(round.getWall());
		int times = 500;
		
		int i = 0;
//		while (i < times){
		while (!round.isOver()){
			round = round.next();
			System.out.println(round.toString());
			
		}
		
		System.out.println(round.toString());
	}
}
