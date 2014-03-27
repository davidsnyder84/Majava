import java.util.Scanner;


public class HelloWorld {


	public static void main(String[] args) {
		
		Scanner inp = new Scanner(System.in);
		int numWorries;
		String biggestWorry = "Murder";
		
		System.out.print("How many worries do you have, cowboy?: ");
		numWorries = inp.nextInt();
		System.out.println("You have " + numWorries + " worries");
		
		System.out.print("\nWhat are you worried about? ");
		biggestWorry = inp.next();
		System.out.println("Your biggest worry is " + biggestWorry);
		
		inp.close();
	}

}
