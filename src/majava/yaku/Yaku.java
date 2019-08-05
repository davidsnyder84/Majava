package majava.yaku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum Yaku {
	DORA("Dora", 1),
	DORA_RED("Dora Aka", 1),
	DORA_URA("Dora Ura", 1),
	
	RIICHI("Riichi", 1, Yaku.value_FORBID_OPEN),
	RIICHI_DOUBLE("Double Riichi", 2, Yaku.value_FORBID_OPEN),
	RIICHI_IPPATSU("Ippatsu", 1, Yaku.value_FORBID_OPEN),
	
	MENZEN_TSUMO("MenzenTsumo", 1, Yaku.value_FORBID_OPEN),
	HAITEI("Haitei Raoyue", 1),
	HOITEI("Houtei Raoyui", 1),
	RINSHAN("Rinshan Kaihou", 1),
	CHANKAN("Chankan", 1),
	
	
	CHIITOITSU("Chiitoitsu", 2, Yaku.value_FORBID_OPEN),
	
					
	PINFU("Pinfu", 1, Yaku.value_FORBID_OPEN),
	IIPEIKOU("Iipeikou", 1, Yaku.value_FORBID_OPEN),
	SANSHOKU("Sanshoku Doujun", 2, 1),
	ITTSUU("Ittsuu", 2, 1),
	RYANPEIKOU("Ryanpeikou", 3, Yaku.value_FORBID_OPEN),
	
	TOITOI("Toitoi", 2),
	SANANKOU("Sanankou", 2),
	SANSHOKU_DOUKOU("Sanshoku Doukou", 2),
	SANKANTSU("Sankantsu", 2),
	
	TANYAO("Tanyao", 1),
	
	YAKUHAI_DRAGON_HAKU("Yakuhai Haku", 1),
	YAKUHAI_DRAGON_HATSU("Yakuhai Hatsu", 1),
	YAKUHAI_DRAGON_CHUN("Yakuhai Chun", 1),
	//bakaze, jikaze
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
	
	YKM_KOKUSHI("KokushiMusou", Yaku.value_YAKUMAN, Yaku.value_FORBID_OPEN),
	YKM_SUUANKOU("Suuankou", Yaku.value_YAKUMAN, Yaku.value_FORBID_OPEN),
	YKM_DAISANGEN("Daisangen", Yaku.value_YAKUMAN),
	YKM_SHOUSHUUSHII("Shousuushii", Yaku.value_YAKUMAN),
	YKM_DAISHUUSHII("Daisuushii", Yaku.value_YAKUMAN),
	YKM_TSUUIISOU("Tsuuiisou", Yaku.value_YAKUMAN),
	YKM_RYUUIISOU("Ryuuiisou", Yaku.value_YAKUMAN),
	YKM_CHUURENPOUTO("ChuurenPoutou", Yaku.value_YAKUMAN, Yaku.value_FORBID_OPEN),
	YKM_SUUKANTSU("Suukantsu", Yaku.value_YAKUMAN),
	YKM_CHINROUTO("Chinrouto", Yaku.value_YAKUMAN),
	
	YKM_TENHOU("Tenhou", Yaku.value_YAKUMAN, Yaku.value_FORBID_OPEN),
	YKM_CHIIHOU("Chiihou", Yaku.value_YAKUMAN, Yaku.value_FORBID_OPEN),
	YKM_RENHOU("Renhou", Yaku.value_YAKUMAN, Yaku.value_FORBID_OPEN),
	
	NAGASHI_MANGAN("NagashiMangan", Yaku.value_MANGAN),
	YAKUNASHI("Yakunashi", 0)
	;
	
	
	private static final int value_YAKUMAN = 13;
	private static final int value_MANGAN = 5;
	//is open is forbidden, han value is 0
	private static final int value_FORBID_OPEN = 0;
	
	
	
	
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
	
	
	public boolean isYakuman(){return closedValue == value_YAKUMAN;}
	public boolean allowedOpen(){return openValue != value_FORBID_OPEN;}
	@Override
	public String toString(){return getName();}
	
	
	
	
	public static List<Yaku> listOfAllYaku(){
		List<Yaku> allYaku = new ArrayList<Yaku>(Arrays.asList(values()));
		allYaku.remove(YAKUNASHI);
		return allYaku;
	}
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
		System.out.println(listOfAllYaku());
	}
	
}
