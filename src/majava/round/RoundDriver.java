package majava.round;

import java.util.ArrayList;
import java.util.List;

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
		
		Kyoku round = new Kyoku();
//		System.out.println(round.getWall().currentPosition());   System.out.println(round.getPlayers().seatE().handSize());
//		System.out.println(round.getWall());
		int times = 500;
		
		int i = 0;
//		while (i < times){
		String lastRoundString = "";
		
		
		
		
		while (!round.isOver()){
			round = round.next();
			
			KyokuState ks = new KyokuState(round);
			gameEventBroadcaster.postNewEvent(ks);
			
			if (!lastRoundString.equals(round.toString())) System.out.println(round.toString());
//			lastRoundString = round.toString();
		}
		
		
		
		
		
		
		
//		System.out.println(round.toString());
	}
	
	
	
	
	private void setupUserInterfaces(){		
		GameUI gui = new MajavaGUI();
		setSleepTimesForUI(gui);
		
		GameUI textUI = new SparseTextualUI();
		textUI.setSleepTimes(0,0,0);
		
		GameUI audioHandler = new MajavaAudioHandler();
		
		List<GameUI> userInterfaces = new ArrayList<GameUI>();
//		userInterfaces.add(audioHandler);	//add audio observer first, so sounds play before any pauses
		userInterfaces.add(gui);
//		userInterfaces.add(textU I);
				
		for (GameUI ui: userInterfaces)
			gameEventBroadcaster.registerObserver(ui);
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
	
}
