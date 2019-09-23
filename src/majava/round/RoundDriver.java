package majava.round;

public class RoundDriver {
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Kyokubu)!");
		
		Kyoku round = new Kyoku();
		int times = 500;
		
		int i = 0;
		while (i < times){
			round = round.next();
		}
		
		System.out.println(round.toString());
	}
}
