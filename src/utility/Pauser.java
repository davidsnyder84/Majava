package utility;


/*
Class: Pauser
contains operations for pausing

data:
	mSleepTime - the amount of time to sleep
	
methods:
	
	constructors:
	Requires sleep time
	
	public:
	 	accessors:
		pauseWait - pauses for this object's amount of sleeptime
		
		static:
		pauseFor - pauses for sleepTime amout of time
*/
public class Pauser {
	
	
//	private class PauseBehavior{public void pauseWait(){try {Thread.sleep(mSleepTime);} catch (InterruptedException e){}}}
//	private class NoPauseBehavior extends PauseBehavior{public void pauseWait(){}}
//	private final PauseBehavior mPauseBehavior;
//	public Pauser(int sleepTime){mSleepTime = sleepTime; if (mSleepTime >0) mPauseBehavior = new PauseBehavior(); else mPauseBehavior = new NoPauseBehavior();}
//	public void pauseWait(){mPauseBehavior.pauseWait();}
	
	
	private static final int DEFAULT_SLEEP_TIME = 10;
	
	private final int mSleepTime;
	
	
	//constructors
	public Pauser(int sleepTime){mSleepTime = sleepTime;}
	public Pauser(){this(DEFAULT_SLEEP_TIME);}
	
	
	//pause for dramatic effect
	public void pauseWait(){if (mSleepTime > 0) pauseFor(mSleepTime);}
	
	public static void pauseFor(int sleepTime){
		try {Thread.sleep(sleepTime);} catch (InterruptedException e){}
	}
}
