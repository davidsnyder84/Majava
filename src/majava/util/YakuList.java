package majava.util;

import java.util.ArrayList;
import java.util.Arrays;

import majava.yaku.Yaku;

public class YakuList extends ArrayList<Yaku>{
	private static final long serialVersionUID = 2608398840570577517L;


	public YakuList(Yaku... yakus){super(Arrays.asList(yakus));}
	
	
	public int totalHan(){
		int total = 0;
		for (Yaku y: this){
			total += y.getValueClosed();
		}
		return total;
	}
	
}
