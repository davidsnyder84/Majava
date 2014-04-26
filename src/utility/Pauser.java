package utility;

public class Pauser {
	
	
	public static final int DEFAULT_SLEEP_TIME = 10;
	
	
	private int mSleepTime;
	
	
	public Pauser(int sleepTime){
		mSleepTime = sleepTime;
	}
	public Pauser(){this(DEFAULT_SLEEP_TIME);}
	
	
	//pauses for dramatic effect
	public void pauseWait(){pauseFor(mSleepTime);}
	
	public static void pauseFor(int sleepTime){
		try {Thread.sleep(sleepTime);} catch (InterruptedException e){}
	}
}
