package utility;


//contains operations for pausing (sleeping/waiting)
public class Pauser {
	private static final int DEFAULT_SLEEP_TIME = 10;
	
	private final int sleepTime;
	
	//constructors
	public Pauser(int timeToSleep){sleepTime = timeToSleep;}
	public Pauser(){this(DEFAULT_SLEEP_TIME);}
	
	
	//pause
	public void pauseWait(){if (sleepTime > 0) pauseFor(sleepTime);}
	
	public static void pauseFor(int timeToSleep){
		try {Thread.sleep(timeToSleep);} catch (InterruptedException e){}
	}
}
