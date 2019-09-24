package majava.round;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import majava.enums.Wind;
import majava.events.GameEventBroadcaster;
import majava.userinterface.GameUI;
import majava.userinterface.MajavaGUI;
import majava.userinterface.audiointerface.MajavaAudioHandler;
import majava.userinterface.textinterface.SparseTextualUI;

public class RoundDriver {
	
	private GameEventBroadcaster gameEventBroadcaster;
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Majava (Kyokubu)!");
		
		RoundDriver rd = new RoundDriver();
		
		rd.setupUserInterfaces();
		rd.doRound();
	}
	
	
	public RoundDriver(){
		gameEventBroadcaster = new GameEventBroadcaster();
	}
	
	private void doRound(){
		
//		System.out.println(round.getWall().currentPosition());   System.out.println(round.getPlayers().seatE().handSize());
//		System.out.println(round.getWall());
		int times = 5000;
		
		int i = 0;
//		while (i < times){
		String lastRoundString = "";
		
		
		
		for (int j=0; j<times; j++){
			Kyoku round = new Kyoku();
			while (!round.isOver()){
				round = round.next();
	//			waitt();
				
				KyokuState ks = new KyokuState(round);
				gameEventBroadcaster.postNewEvent(ks);
				
//				if (!lastRoundString.equals(round.toString())) System.out.println(round.toString());
			}
			println(j);
		}
		
		
		
		
		
		
//		System.out.println(round.toString());
	}
	
	
	
	
	private void setupUserInterfaces(){		
		GameUI gui = new MajavaGUI();
		setSleepTimesForUI(gui);
//		gui.setSleepTimes(0,0,0); 
//		gui.setSleepTimes(80,80,80); 
		gui.setSleepTimes(200,200,200); 
//		gui.setSleepTimes(10,10,10); 
		
		GameUI textUI = new SparseTextualUI();
		textUI.setSleepTimes(0,0,0);
		
		GameUI audioHandler = new MajavaAudioHandler();
		
		List<GameUI> userInterfaces = new ArrayList<GameUI>();
//		userInterfaces.add(audioHandler);	//add audio observer first, so sounds play before any pauses
		userInterfaces.add(gui);
//		userInterfaces.add(textUI);
				
		for (GameUI ui: userInterfaces)
			gameEventBroadcaster.registerObserver(ui);
		gui.startUI();
	}
	private void setSleepTimesForUI(GameUI ui){
		final int DEAFULT_SLEEPTIME = 400;
		final int DEAFULT_SLEEPTIME_EXCLAMATION = 1500;
		final int DEAFULT_SLEEPTIME_ROUND_END = 18000;
		final int FAST_SLEEPTIME = 0, FAST_SLEEPTIME_EXCLAMATION = 0, FAST_SLEEPTIME_ROUND_END = 0;
		
		int sleepTime = DEAFULT_SLEEPTIME;
		int sleepTimeExclamation = DEAFULT_SLEEPTIME_EXCLAMATION;
		int sleepTimeRoundEnd = DEAFULT_SLEEPTIME_ROUND_END;
		ui.setSleepTimes(sleepTime, sleepTimeExclamation, sleepTimeRoundEnd);
	}
	
	
	public static void println(String prints){System.out.println(prints);}public static void println(){System.out.println("");}public static void println(int prints){System.out.println(prints+"");} public static void waitt(){(new Scanner(System.in)).next();}
}
