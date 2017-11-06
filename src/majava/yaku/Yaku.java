package majava.yaku;

import java.util.Arrays;
import java.util.List;


public enum Yaku {
	
	DORA("Dora", 1),
	DORA_RED("Red Dora", 1),
	DORA_URA("Ura Dora", 1),
	
	RIICHI("Riichi", 1, Yaku.DISALLOW_OPEN),
	RIICHI_DOUBLE("Double Riichi", 2, Yaku.DISALLOW_OPEN),
	RIICHI_IPPATSU("Ippatsu", 1, Yaku.DISALLOW_OPEN),
	
	TSUMO("Tsumo", 1, Yaku.DISALLOW_OPEN),
	HAITEI("Haitei Raoyue", 1),
	HOITEI("Houtei Raoyui", 1),
	RINSHAN("Rinshan Kaihou", 1),
	CHANKAN("Chankan", 1),
	
	
	CHIITOITSU("Chiitoitsu", 2, Yaku.DISALLOW_OPEN),
	
					
	PINFU("Pinfu", 1, Yaku.DISALLOW_OPEN),
	IIPEIKOU("Iipeikou", 1, Yaku.DISALLOW_OPEN),
	SANSHOKU("Sanshoku Doujun", 2, 1),
	ITTSUU("Ittsuu", 2, 1),
	RYANPEIKOU("Ryanpeikou", 3, Yaku.DISALLOW_OPEN),
	
	TOITOI("Toitoi", 2),
	SANANKOU("Sanankou", 2),
	SANSHOKU_DOUKOU("Sanshoku Doukou", 2),
	SANKANTSU("Sankantsu", 2),
	
	TANYAO("Tanyao", 1),
	
	YAKUHAI_DRAGON_HAKU("Yakuhai Haku", 1),
	YAKUHAI_DRAGON_HATSU("Yakuhai Hatsu", 1),
	YAKUHAI_DRAGON_CHUN("Yakuhai Chun", 1),
	YAKUHAI_WIND_EAST("Yakuhai East", 1),
	YAKUHAI_WIND_SOUTH("Yakuhai South", 1),
	YAKUHAI_WIND_WEST("Yakuhai West", 1),
	YAKUHAI_WIND_NORTH("Yakuhai North", 1),
	
	CHANTA("Chanta", 2, 1),
	JUNCHAN("Junchan", 3, 2),
	HONROUTO("Honrouto", 4),
	SHOUSANGEN("Shousangen", 4),
	
	HONITSU("Honitsu", 3, 2),
	CHINITSU("Chinitsu", 6, 5),
	
	YKM_KOKUSHI("Kokushi Musou", Yaku.YAKUMAN, Yaku.DISALLOW_OPEN),
	YKM_SUUANKOU("Suuankou", Yaku.YAKUMAN, Yaku.DISALLOW_OPEN),
	YKM_DAISANGEN("Daisangen", Yaku.YAKUMAN),
	YKM_SHOUSHUUSHII("Shousuushii", Yaku.YAKUMAN),
	YKM_DAISHUUSHII("Daisuushii", Yaku.YAKUMAN),
	YKM_TSUUIISOU("Tsuuiisou", Yaku.YAKUMAN),
	YKM_RYUUIISOU("Ryuuiisou", Yaku.YAKUMAN),
	YKM_CHUURENPOUTO("Chuuren Poutou", Yaku.YAKUMAN, Yaku.DISALLOW_OPEN),
	YKM_SUUKANTSU("Suukantsu", Yaku.YAKUMAN),
	YKM_CHINROUTO("Chinrouto", Yaku.YAKUMAN),
	
	YKM_TENHOU("Tenhou", Yaku.YAKUMAN, Yaku.DISALLOW_OPEN),
	YKM_CHIIHOU("Chiihou", Yaku.YAKUMAN, Yaku.DISALLOW_OPEN),
	YKM_RENHOU("Renhou", Yaku.YAKUMAN, Yaku.DISALLOW_OPEN),
	
	
	NAGASHI_MANGAN("Nagashi mangan", Yaku.MANGAN);
	
	
	
	
	
	
	private static final int YAKUMAN = 13;
	private static final int MANGAN = 5;
	//is open is disallowed, han value is 0
	private static final int DISALLOW_OPEN = 0;
	
	
	
	
	
	
	
	
	
	
	
	private final String yakuName;
	private final int openValue, closedValue;
	
	

	//for yaku whose open/closed values differ
	private Yaku(String name, int closedVal, int openVal){
		yakuName = name;
		openValue = openVal; closedValue = closedVal;
	}
	private Yaku(String name, int hanValue){this(name, hanValue, hanValue);}
	
	
	//accessors
	public String getName(){return yakuName;}
	public int getValueOpen(){return openValue;}
	public int getValueClosed(){return closedValue;}
	
	
	public boolean isYakuman(){return closedValue == YAKUMAN;}
	public boolean allowedOpen(){return openValue != DISALLOW_OPEN;}
	
	public String toString(){return yakuName;}
	
	
	
	
	
	
	
	
	
	public static List<Yaku> listOfAllYaku(){return Arrays.asList(values());}
	public static List<Yaku> listOfAllYakuman(){
		return Arrays.asList(
							YKM_KOKUSHI, 
							YKM_SUUANKOU, 
							YKM_DAISANGEN, 
							YKM_SHOUSHUUSHII, 
							YKM_DAISHUUSHII,
							YKM_TSUUIISOU,
							YKM_RYUUIISOU,
							YKM_CHUURENPOUTO, 
							YKM_SUUKANTSU, 							
							YKM_CHINROUTO,
							
							YKM_TENHOU,
							YKM_CHIIHOU,
							YKM_RENHOU
							);
	}
	
	
	public static void main(String[]s){
		listOfAllYaku();
	}
	
}
