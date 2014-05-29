package majava.graphics.userinterface;

import majava.enums.Exclamation;
import majava.enums.GameplayEvent;

public interface GameUI {
	
	void displayEvent(GameplayEvent e);
	
	void setSleepTimeExclamation(int sleepTime);
	
}
