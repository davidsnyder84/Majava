package majava.enums;

import static majava.enums.KET.*;

public class KyokuEvent {
	
	private final KET eventType;
	
	private KyokuEvent(KET et){
		eventType = et;
	}
	
	public KET getEventType(){return eventType;}
	
	public static final KyokuEvent initEvent(){return new KyokuEvent(INIT);}
	public static final KyokuEvent dealtHandsEvent(){return new KyokuEvent(DEALT_HANDS);}
}
