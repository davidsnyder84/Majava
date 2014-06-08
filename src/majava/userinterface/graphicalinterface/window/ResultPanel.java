package majava.userinterface.graphicalinterface.window;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import majava.Meld;
import majava.control.testcode.Majenerator;
import majava.enums.Wind;
import majava.summary.PaymentMap;
import majava.summary.PlayerSummary;
import majava.summary.RoundResultSummary;
import majava.tiles.Tile;
import majava.util.TileList;

public class ResultPanel extends JPanel{
	private static final long serialVersionUID = -5392789088556649589L;
	
	private static final int WIDTH = 600, HEIGHT = 500;
	
	private static final Color COLOR_BASE = Color.GRAY;
	private static final int ALPHA = 245;
	private static final Color COLOR_PANEL = new Color(COLOR_BASE.getRed(), COLOR_BASE.getGreen(), COLOR_BASE.getBlue(), ALPHA);
	
	private static final int NUM_PLAYERS = 4;
	
	
	public ResultPanel(){
		super();
		
		setBounds(0,0,WIDTH,HEIGHT);
		setLayout(null);
		setBorder(new LineBorder(new Color(0, 0, 0), 8));
		
		setBackground(COLOR_PANEL);
		
//		JPanel res = new TableGUI.RoundResultLabelPanel();
//		res.setLocation(120,20);
//		add(res);
		
//		JPanel playp = (new TableViewSmall()).PlayerPanel(TableGUI.SEAT1);
//		JPanel playp = new TableGUI.PlayerPanel(TableGUI.SEAT1);
//		JPanel playp = new (new TableViewer()).PlayerPanel();
		
		
//		JPanel playp = (new TableViewer()).new PlayerPanel(TableGUI.SEAT1);
		TableViewBase.PlayerPanel playp = new TableViewBase.PlayerPanel(TableViewBase.SEAT1);
		playp.setLocation(0,300);
		
		
		for (JLabel l: playp.panelH.larryH) l.setIcon(new ImageIcon(getClass().getResource("/res/img/tiles/5.png")));
		for (TableViewBase.PlayerPanel.MeldsPanel.MeldPanel mp: playp.panelMs.panelHMs) for (JLabel l: mp.larryHM) l.setIcon(new ImageIcon(getClass().getResource("/res/img/tiles/small/15.png")));
//		garryTiles[seat][BIG][RED5M] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/5r.png")));
//		garryTiles[seat][SMALL][RED5M] = rotators[seat].rotateImage(new ImageIcon(getClass().getResource("/res/img/tiles/small/5r.png")));
		
		add(playp);
	}
	
	
	
	/*
	I need this
	
	***result label (Player 1 wins!, Draw!, etc)
	
	***winning hand/melds panel
	dora indicators panel (and ura if necessary)
	
	***panel/list of yaku
	
	***hand score label
	
	***payments per player panel
		*wind
		*name
		*points (before payment)
		*payment amount
	
	
	ok button
	*/
//	RoundResultSummary resum;
//	//for win
//	{
//		//***result label (Player 1 wins!, Draw!, etc)
//		String resultLabel = resum.getAsStringResultType();
//		
//		
//		//***payments per player panel
//		PlayerPointsBlock[] playerPointBlocks = new PlayerPointsBlock[NUM_PLAYERS];
//		
//		Map<PlayerSummary, Integer> payments = resum.getPayments();
//		
//		int i = 0;
//		for (PlayerSummary ps: payments.keySet())
//			playerPointBlocks[++i] = new PlayerPointsBlock(ps.getSeatWind(), ps.getPlayerName(), ps.getPoints(), payments.get(ps));
//		
//		
//		if (resum.isVictory()){
//			//***winning hand/melds panel
//			TileList winnerHandTiles = resum.getWinnerHandTiles();	
//			List<Meld> winnerMelds = resum.getWinnerMelds();
//			Tile winningTile = resum.getWinningTile();
//			
//			//***panel/list of yaku
//			List<String> yakuList = Arrays.asList("Riichi", "Ippatsu", "Tsumo", "Dora 1");
//			
//			//***hand score label
//			String handScore = payments.get(resum.getWinningPlayer()).toString();
//		}
//	}
	
	
	
	public static void DEMOthis(){
		
		RoundResultSummary resum = Majenerator.generateRoundResultSummary();
		
		//for all
		String resultLabel = null; PlayerPointsBlock[] pointsBlocks = null; PaymentMap payments = null;
		//for win
		PlayerSummary winner = null, furikon = null;
		TileList winnerHandTiles = null; List<Meld> winnerMelds = null; Tile winningTile = null;
		List<String> yakuList = null; int yakuWorth = 1; int handScore = 0; 
		
		
		
		//***result label (Player 1 wins!, Draw!, etc)
		resultLabel = resum.getAsStringResultType();
		
		
		//***payments per player panel
		pointsBlocks = new PlayerPointsBlock[NUM_PLAYERS];
		
		payments = resum.getPayments();
		
		int i = 0;
		for (PlayerSummary player: payments)
			pointsBlocks[i++] = new PlayerPointsBlock(player, payments.get(player));
//			pointsBlocks[i++] = new PlayerPointsBlock(ps.getPlayerNumber(), ps.getSeatWind(), ps.getPlayerName(), ps.getPoints(), payments.get(ps));
		
		
		if (resum.isVictory()){
			winner = resum.getWinningPlayer();
			furikon = resum.getFurikondaPlayer();
			
			//***winning hand/melds panel
			winnerHandTiles = resum.getWinnerHandTiles();	
			winnerMelds = resum.getWinnerMelds();
			winningTile = resum.getWinningTile();
			
			//***panel/list of yaku
			yakuList = Arrays.asList("Riichi", "Ippatsu", "Tsumo", "Dora 1");
			
			//***hand score label
			handScore = payments.get(winner);
		}
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("Result: " + resultLabel);
		if (resum.isVictory()){
			System.out.print(resum.getAsStringWinType() + "!");
			if (resum.isVictoryRon()) System.out.print(" (from Player " + (furikon.getPlayerNumber()+1) + ")");
			System.out.println();
		}
		
		
		System.out.println("\nPayments:");
		for (PlayerPointsBlock pb: pointsBlocks){
			System.out.print("\tPlayer " + (pb.playerNum+1) + " (" + pb.playerName + ", " + pb.playerWind.toChar() + ")... Points:" + pb.origPoints + " (");
			if (pb.paymentAmt > 0) System.out.print("+");
			System.out.println(pb.paymentAmt + ")");
		}
		
		if (resum.isVictory()){
			//***winning hand/melds panel
			System.out.println("\nWinner's hand: " + winnerHandTiles);
			System.out.println("Winner's melds:");
			for (Meld m: winnerMelds) System.out.println("\t" + m);
			System.out.println("Winning tile: " + winningTile);
			
			//***panel/list of yaku
			System.out.println("\nList of Yaku:");
			for (String s: yakuList) System.out.println("\t" + s + " (" + yakuWorth + ")");
			
			//***hand score label
			System.out.println("Hand score: " + handScore);
		}
		
	}
	
	
	
	public static void DEMOthisNoPBS(){
		
		RoundResultSummary resum = Majenerator.generateRoundResultSummary();
		
		//for all
		String resultLabel = null;
		PaymentMap payments = null;
		//for win
		PlayerSummary winner = null, furikon = null;
		TileList winnerHandTiles = null; List<Meld> winnerMelds = null; Tile winningTile = null;
		List<String> yakuList = null; int yakuWorth = 1; int handScore = 0; 
		
		
		
		//***result label (Player 1 wins!, Draw!, etc)
		resultLabel = resum.getAsStringResultType();
		
		
		//***payments per player panel
		payments = resum.getPayments();
		
		if (resum.isVictory()){
			winner = resum.getWinningPlayer();
			furikon = resum.getFurikondaPlayer();
			
			//***winning hand/melds panel
			winnerHandTiles = resum.getWinnerHandTiles();	
			winnerMelds = resum.getWinnerMelds();
			winningTile = resum.getWinningTile();
			
			//***panel/list of yaku
			yakuList = Arrays.asList("Riichi", "Ippatsu", "Tsumo", "Dora 1");
			
			//***hand score label
			handScore = payments.get(winner);
		}
		
		
		
		
		
		
		
		
		
		System.out.println("Result: " + resultLabel);
		if (resum.isVictory()){
			System.out.print(resum.getAsStringWinType() + "!");
			if (resum.isVictoryRon()) System.out.print(" (from Player " + (furikon.getPlayerNumber()+1) + ")");
			System.out.println();
		}
		
		
		System.out.println("\nPayments:");
		for (PlayerSummary ps: payments){
			System.out.print("\tPlayer " + (ps.getPlayerNumber()+1) + " (" + ps.getPlayerName() + ", " + ps.getSeatWind().toChar() + ")... Points:" + ps.getPoints() + " (");
			if (payments.get(ps) > 0) System.out.print("+");
			System.out.println(payments.get(ps) + ")");
		}
		
		if (resum.isVictory()){
			//***winning hand/melds panel
			System.out.println("\nWinner's hand: " + winnerHandTiles);
			System.out.println("Winner's melds:");
			for (Meld m: winnerMelds) System.out.println("\t" + m);
			System.out.println("Winning tile: " + winningTile);
			
			//***panel/list of yaku
			System.out.println("\nList of Yaku:");
			for (String s: yakuList) System.out.println("\t" + s + " (" + yakuWorth + ")");
			
			//***hand score label
			System.out.println("Hand score: " + handScore);
		}
		
	}
	
	
	
	
	
	
	private static class PlayerPointsBlock{
		private final int playerNum;
		private final Wind playerWind;
		private final String playerName;
		private final int origPoints;
		private final int paymentAmt;
		public PlayerPointsBlock(int playernum, Wind wind, String name, int pointsBeforePayment, int payment){
			playerNum = playernum;
			playerWind = wind;
			playerName = name;
//			origPoints = pointsBeforePayment - payment;
			origPoints = pointsBeforePayment;
			paymentAmt = payment;
		}
		public PlayerPointsBlock(PlayerSummary ps, int payment){
			this(ps.getPlayerNumber(), ps.getSeatWind(), ps.getPlayerName(), ps.getPoints(), payment);
		}
	}
	
	

	
	
	public static void main(String[] args) {
		
//		for (int i = 0; i < 200; i++) DEMOthis();
		DEMOthisNoPBS();
		
		final int WINDOW_WIDTH = 1120 + (-62*2 - 6) + 2*2;
		final int WINDOW_HEIGHT = 726 + 6 + (-62*2 + 25 + 18) + 26 + 23;
		
		JFrame frame = new JFrame();
		JPanel contentPane = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		
		
		ResultPanel resPan = new ResultPanel();
		resPan.setLocation(57,75);
		contentPane.add(resPan);
		
//		frame.setVisible(true);
	}

}
