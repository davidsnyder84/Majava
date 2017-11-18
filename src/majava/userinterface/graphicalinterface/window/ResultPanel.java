package majava.userinterface.graphicalinterface.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import majava.hand.Meld;
import majava.control.testcode.Majenerator;
import majava.enums.Wind;
import majava.summary.PaymentMap;
import majava.summary.PlayerSummary;
import majava.summary.RoundResultSummary;
import majava.tiles.GameTile;
import majava.util.GameTileList;
import majava.util.YakuList;
import majava.yaku.Yaku;


public class ResultPanel extends JPanel{
	private static final long serialVersionUID = -5392789088556649589L;
	
	private static final int WIDTH = 600, HEIGHT = 500;
	
	private static final Color COLOR_BASE = Color.GRAY;
	private static final int ALPHA = 245;
	private static final Color COLOR_PANEL = new Color(COLOR_BASE.getRed(), COLOR_BASE.getGreen(), COLOR_BASE.getBlue(), ALPHA);
	
	private static final int NUM_PLAYERS = 4;
	
	private static final int TILE_BIG_WIDTH = 30, TILE_BIG_HEIGHT = 41;
	
	
	
	
	
	
	
	protected WinnerPanel winnerPanel;
	protected TableViewBase.RoundResultLabelPanel resultLabelPanel;
	protected PaymentsPanel paymentsPanel;
	protected YakuPanel yakuPanel;
	
	
	public ResultPanel(){
		super();
		
		setBounds(0,0,WIDTH,HEIGHT);
		setLayout(null);
		setBorder(new LineBorder(new Color(0, 0, 0), 8));
		setBackground(COLOR_PANEL);
		
		resultLabelPanel = new TableViewBase.RoundResultLabelPanel();
		resultLabelPanel.setLocation(200,15);
		resultLabelPanel.setBackground(TableViewBase.COLOR_TRANSPARENT);
		resultLabelPanel.setBorder(new LineBorder(Color.BLACK, 3, true));
		resultLabelPanel.setTextColor(Color.WHITE);
		add(resultLabelPanel);
		
		winnerPanel = new WinnerPanel();
		winnerPanel.setLocation(55-20,88);
		add(winnerPanel);
		
		paymentsPanel = new PaymentsPanel();
		paymentsPanel.setLocation(142, 352);
		add(paymentsPanel);
		
		yakuPanel = new YakuPanel();
		yakuPanel.setLocation(79, 177);
		add(yakuPanel);
	}
	
	
	
	
	
	
	public void showResult(RoundResultSummary resum){
		blankEverything();
		
		//for all
		String resultLabel = null;
		PaymentMap payments = null;
		//for win
		PlayerSummary winner = null, furikon = null;
		GameTileList winnerHandTiles = null; List<Meld> winnerMelds = null; GameTile winningTile = null;
		YakuList yakuList = null; int yakuWorth = 1; int handScore = 0;
		
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
			yakuList = Majenerator.generateYakuList();	/////////////////////YAKU HERE
			yakuWorth = yakuList.totalHan();
			
			//***hand score label
			handScore = payments.get(winner);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//reuslt label
		resultLabelPanel.setVisible(true);
		resultLabelPanel.getLabelResult().setText(resultLabel);
		
		//payments
		paymentsPanel.setVisible(true);
		paymentsPanel.setPayments(payments);
		
		
		
		if (resum.isVictory()){
			winnerPanel.setVisible(true);
			
			//winning hand
			int currentTile = 0, currentMeld = 0;
			for (currentTile = 0; currentTile < winnerHandTiles.size(); currentTile++)
				winnerPanel.panelHandAndMelds.panelH.larryH[currentTile].setIcon(new ImageIcon(getClass().getResource("/res/img/tiles/" + winnerHandTiles.get(currentTile).getId() + ".png")));
			
			//winning melds
			for (currentMeld = 0; currentMeld < winnerMelds.size(); currentMeld++)
				for (currentTile = 0; currentTile < winnerMelds.get(currentMeld).size(); currentTile++)
					winnerPanel.panelHandAndMelds.panelMs.panelHMs[currentMeld].larryHM[currentTile].setIcon(new ImageIcon(getClass().getResource("/res/img/tiles/small/" + winnerMelds.get(currentMeld).getTile(currentTile).getId() + ".png")));
			
			//winning tile
			winnerPanel.panelWinningTile.lblTile.setIcon(new ImageIcon(getClass().getResource("/res/img/tiles/" + winningTile.getId() + ".png")));
			winnerPanel.setNumberOfHandTiles(winnerHandTiles.size());
			
			//yaku list
			yakuPanel.setVisible(true);
			yakuPanel.setYaku(yakuList);
		}
		
	}
	
	
	
	private void blankEverything(){
		resultLabelPanel.setVisible(false);
		paymentsPanel.setVisible(false);
		
		winnerPanel.blankAll();
		winnerPanel.setVisible(false);
		
		yakuPanel.blankAll();
		yakuPanel.setVisible(false);
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
	
	
	
	
	
	
	
	
	//TODO start panel classes
	
	protected static class WinnerPanel extends JPanel{
		private static final long serialVersionUID = -8739912729914325644L;
		
		
		protected static class WinningTilePanel extends JPanel{
			private static final long serialVersionUID = -1175350218382412270L;
			
			private final JLabel lblTile = new JLabel();
			private final JLabel coverall = new JLabel();
			public WinningTilePanel(){
				super();
				setBounds(0,0,30,41);
				setOpaque(false);
				setLayout(null);
				
				lblTile.setSize(30, 41);
				lblTile.setLocation(0,0);
				lblTile.setIcon(new ImageIcon(getClass().getResource("/res/img/tiles/17.png")));
				
				coverall.setBounds(lblTile.getBounds());
				coverall.setBackground(new Color(255,0,150, 70)); coverall.setOpaque(true);
				
				add(coverall);
				add(lblTile);
			}
			public void blankAll(){
				lblTile.setIcon(null);
			}
		}
		
		
		
		public TableViewBase.PlayerPanel panelHandAndMelds = new TableViewBase.PlayerPanel(TableViewBase.SEAT1);
		public WinningTilePanel panelWinningTile = new WinningTilePanel();
		
		public WinnerPanel(){
			super();
			setBounds(0, 0, panelHandAndMelds.getWidth(), panelHandAndMelds.getHeight() + 5);
			setLayout(null);
			setOpaque(false);
			
			panelHandAndMelds.setLocation(0,5);
			panelWinningTile.setLocation(2 + TILE_BIG_WIDTH*14,0);
			
			
			
			add(panelHandAndMelds);
			add(panelWinningTile);
		}
		public void blankAll(){
			panelHandAndMelds.blankAll();
			panelWinningTile.blankAll();
		}
		public void setNumberOfHandTiles(int numHandTiles){
			panelWinningTile.setLocation(2 + TILE_BIG_WIDTH*numHandTiles, 0);
		}
	}
	
	
	protected static class YakuPanel extends JPanel{
		private static final long serialVersionUID = -1129656642544518038L;
		
		private static final int HEIGHT_YP = 138;
		private static final Color COLOR_YAKU_PANEL = Color.GRAY;
		private static final Color COLOR_YAKU_TEXT = Color.WHITE;
		

		//names of the yaku
		protected static class YakuNamePanel extends JPanel{
			private static final long serialVersionUID = 3133779361407012033L;
			
			private final List<JLabel> labelsYakuNames = new ArrayList<JLabel>();
			public YakuNamePanel(){
				super();
				setBounds(0, 0, 120, HEIGHT_YP);
				setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
//				setBackground(Color.YELLOW);
			}
			public void addLabel(JLabel l){
				l.setPreferredSize(new Dimension(getWidth(), 14));
				l.setForeground(COLOR_YAKU_TEXT);
				labelsYakuNames.add((JLabel)add(l));
			}
			public void blankAll(){while (!labelsYakuNames.isEmpty()) remove(labelsYakuNames.remove(0));}
		}
		
		//number values for han
		protected static class YakuWorthPanel extends JPanel{
			private static final long serialVersionUID = 4771454758392390877L;

			private final List<JLabel> labelsYakuWorths = new ArrayList<JLabel>();
			public YakuWorthPanel(){
				super();
				setBounds(0, 0, 20, HEIGHT_YP);
				setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
//				setBackground(Color.GREEN);
			}
			public void addLabel(JLabel l){
				l.setPreferredSize(new Dimension(getWidth(), 14));
				l.setForeground(COLOR_YAKU_TEXT);
				labelsYakuWorths.add((JLabel)add(l));
			}
			public void blankAll(){while (!labelsYakuWorths.isEmpty()) remove(labelsYakuWorths.remove(0));}
		}
		
		
		private final YakuNamePanel panelNames = new YakuNamePanel();
		private final YakuWorthPanel panelWorths = new YakuWorthPanel();
		public YakuPanel(){
			super();
			setLayout(null);
			setBounds(0, 0, panelNames.getWidth() + panelWorths.getWidth(), HEIGHT_YP);
			
			
			panelNames.setLocation(0,0);
			panelWorths.setLocation(panelNames.getWidth(),0);
			panelNames.setBackground(COLOR_YAKU_PANEL);panelWorths.setBackground(COLOR_YAKU_PANEL);
			
			add(panelNames);add(panelWorths);
		}
		
		public void setYaku(YakuList ylist){
			blankAll();
			for (Yaku y: ylist){
				panelNames.addLabel(new JLabel(y.toString()));
				int worth = y.getValueClosed();
				panelWorths.addLabel(new JLabel(Integer.toString(worth)));
			}
		}
		public void blankAll(){
			panelNames.blankAll();
			panelWorths.blankAll();
		}
		
	}
	
	
	
	protected static class PaymentsPanel extends JPanel{
		private static final long serialVersionUID = -3188452904507674217L;
		private static final Color COLOR_PAYMENT_PANEL = Color.LIGHT_GRAY;
		
		public static class PlayerPaymentPanel extends JPanel{
			private static final long serialVersionUID = -1732543659928397387L;
			
			
			private static final Color COLOR_NEGATIVE = Color.RED;
			private static final Color COLOR_POSITIVE = Color.BLUE;
			
			private JPanel panPayment = new JPanel();
			private JLabel lblPoints = new JLabel(), lblPayment = new JLabel();
			private JLabel lblPlayerName = new JLabel(), lblPlayerWind = new JLabel(), lblPlayerNum = new JLabel();
			
			public PlayerPaymentPanel(){
				super();
				
				setBounds(0,0,131,35);
				setBackground(COLOR_PAYMENT_PANEL);
//				setBorder(new LineBorder(new Color(0, 0, 0)));
				setLayout(null);

				
				panPayment.setBounds(30,17,100,14);
				panPayment.setOpaque(false);
				panPayment.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				panPayment.add(lblPoints);
				panPayment.add(lblPayment);
				
				lblPoints.setHorizontalAlignment(SwingConstants.CENTER);
				lblPoints.setSize(48, 14);
				lblPoints.setLocation(0, 0);
				
				lblPayment.setForeground(COLOR_POSITIVE);
				lblPayment.setSize(48, 14);
				lblPayment.setLocation(52, 0);
				
				
				
				lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
				lblPlayerName.setLocation(30, 3);
				lblPlayerName.setSize(89, 14);
				
				lblPlayerWind.setLocation(3, 5);
				lblPlayerWind.setSize(23, 23);
				
				
				//demo vals
				lblPoints.setText("128000");lblPayment.setText("+48000");lblPlayerName.setText("Suwado");lblPlayerWind.setIcon(new ImageIcon(getClass().getResource("/res/img/winds/small/transEs.png")));lblPlayerNum.setText("1");
				
				add(panPayment);
				add(lblPlayerName);
				add(lblPlayerWind);
//				add(lblPlayerNum);
			}
			
			public void setPayment(PlayerSummary player, int payment){
				
				lblPlayerName.setText(player.getPlayerName());
				lblPlayerWind.setIcon(new ImageIcon(getClass().getResource("/res/img/winds/small/trans" + player.getSeatWind().toChar() + "s.png")));
				lblPoints.setText(player.getPoints() + "  ");
				
				if (payment >= 0){
					lblPayment.setForeground(PlayerPaymentPanel.COLOR_POSITIVE);
					lblPayment.setText("+" + payment);
					if (payment == 0) lblPayment.setText(null);
				}
				else{
					lblPayment.setForeground(PlayerPaymentPanel.COLOR_NEGATIVE);
					lblPayment.setText(Integer.toString(payment));
				}
			}
		}
		
		
		private PlayerPaymentPanel panelPlayerPayments[] = new PlayerPaymentPanel[NUM_PLAYERS];
		
		public PaymentsPanel(){
			super();
			
			setBounds(0,0,315,107);
			setLayout(null);
			setOpaque(false);
			
			
			int[][] locs = {{92,72}, {184,36}, {92,0}, {0,36}};
			for (int i = 0; i < panelPlayerPayments.length; i++){
				panelPlayerPayments[i] = new PlayerPaymentPanel();
				panelPlayerPayments[i].setLocation(locs[i][0], locs[i][1]);
				add(panelPlayerPayments[i]);
			}
		}
		
		
		public void setPayments(PaymentMap payments){
			for (int playerNum = 0; playerNum < NUM_PLAYERS; playerNum++)
				panelPlayerPayments[playerNum].setPayment(payments.getPlayer(playerNum), payments.get(playerNum));
		}
	}
	
	
	
	
	public static void main(String[] args) {showDemo(new ResultPanel());}
	public static void showDemo(final ResultPanel resPan){
		
		final int WINDOW_WIDTH = 1120 + (-62*2 - 6) + 2*2;
		final int WINDOW_HEIGHT = 726 + 6 + (-62*2 + 25 + 18) + 26 + 23;
		
		final JFrame frame = new JFrame();
		final JPanel contentPane = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		
		
		resPan.setLocation(57,75);
		contentPane.add(resPan);
		
		
		
		final JButton btnRandAll = new JButton("Rand"); btnRandAll.setBounds(700, 10, 65, 23);
		btnRandAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resPan.showResult(Majenerator.generateRoundResultSummary());
				frame.repaint();
			}
		});
		btnRandAll.setVisible(true);
		contentPane.add(btnRandAll);
		
		frame.setVisible(true);
	}
}
