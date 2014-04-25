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
	
//	public static void pauseWait(){
//		int time = 0;
//		if (DEBUG_WAIT_AFTER_COMPUTER) time = TIME_TO_SLEEP;
//		
//		try {Thread.sleep(time);} catch (InterruptedException e){}
//	}
}
