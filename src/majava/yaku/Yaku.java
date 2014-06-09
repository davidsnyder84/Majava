package majava.yaku;


public enum Yaku {
	
	DORA("Dora", 1),
	DORA_RED("Red Dora", 1),
	DORA_URA("Ura Dora", 1),
	
	RIICHI("Riichi", 1, Yaku.DISALLOW_OPEN),
	RIICHI_DOUBLE("Double Riichi", 2, Yaku.DISALLOW_OPEN),
	RIICHI_IPPATSU("Ippatsu", 1, Yaku.DISALLOW_OPEN),
	
	TSUMO("Tsumo", 1, Yaku.DISALLOW_OPEN),
	HAITEI("Haitei", 1),
	HOITEI("Houtei", 1),
	RINSHAN("Rinshan Kaihou", 1),
	CHANKAN("Chankan", 1),
	
	
	CHIITOITSU("Chiitoitsu", 2, Yaku.DISALLOW_OPEN),
	
					
	PINFU("Pinfu", 1, 0),
	IIPEIKOU("Iipeikou", 1, Yaku.DISALLOW_OPEN),
	SANSHOKU("Sanshoku Doujun", 2, 1),
	ITTSUU("Ittsuu", 2, 1),
	RYANPEIKOU("Ryanpeikou", 3, Yaku.DISALLOW_OPEN),
	
	TOITOI("Toitoi", 2),
	SANANKOU("Sanankou", 2),
	SANSHOKU_DOUKOU("Sanshoku Doukou", 2),
	SANKANTSU("Sankantsu", 2),
	
	TANYAO("Tanyao", 1),
	
	YAKUHAI_HAKU("Yakuhai Haku", 1),
	YAKUHAI_HATSU("Yakuhai Hatsu", 1),
	YAKUHAI_CHUN("Yakuhai Chun", 1),
	YAKUHAI_EAST("Yakuhai East", 1),
	YAKUHAI_SOUTH("Yakuhai South", 1),
	YAKUHAI_WEST("Yakuhai West", 1),
	YAKUHAI_NORTH("Yakuhai North", 1),
	
	CHANTA("Chanta", 2, 1),
	JUNCHAN("Junchan", 2, 1),
	HONROUTO("Honrouto", 2, 1),
	SHOUSANGEN("Shousangen", 4),
	
	HONITSU("Honitsu", 3, 2),
	CHINITSU("Chinitsu", 5, 4),
	
	YKM_KOKUSHI("Kokushi Musou", Yaku.YAKUMAN),
	YKM_SUUANKOU("Suuankou", Yaku.YAKUMAN),
	YKM_DAISANGEN("Daisangen", Yaku.YAKUMAN),
	YKM_SHOUSHUUSHII("Shousuushii", Yaku.YAKUMAN),
	YKM_DAISHUUSHII("Daisuushii", Yaku.YAKUMAN),
	YKM_TSUUIISOU("Tsuuiisou", Yaku.YAKUMAN),
	YKM_RYUUIISOU("Ryuuiisou", Yaku.YAKUMAN),
	YKM_CHUURENPOUTO("Chuuren Poutou", Yaku.YAKUMAN),
	YKM_SUUKANTSU("Suukantsu", Yaku.YAKUMAN),
	
	YKM_TENHOU("Tenhou", Yaku.YAKUMAN),
	YKM_CHIIHOU("Chiihou", Yaku.YAKUMAN),
	YKM_RENHOU("Renhou", Yaku.YAKUMAN),
	
	
	NAGASHI_MANGAN("Nagashi mangan", Yaku.MANGAN);
	
	
	
	
	
	
	private static final int YAKUMAN = 13;
	private static final int MANGAN = 5;
	private static final int DISALLOW_OPEN = 0;
	
	
	
	
	
	
	
	
	
	
	
	private final String yakuName;
	private final int openVal, closedVal;
	
	

	//for yaku whose open/closed values differ
	private Yaku(String name, int closed, int open){
		yakuName = name;
		openVal = open; closedVal = closed;
	}
	private Yaku(String name, int val){this(name, val, val);}
	
	
	//accessors
	public String getName(){return yakuName;}
	public int getValueOpen(){return openVal;}
	public int getValueClosed(){return closedVal;}
	
	
	public boolean isYakuman(){return openVal == YAKUMAN;}
	public boolean allowOpen(){return openVal != DISALLOW_OPEN;}
	
	public String toString(){return yakuName;}
	
	
}
