package majava.userinterface.graphicalinterface.window;

import java.awt.Color;
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
import javax.swing.border.LineBorder;

import majava.Meld;
import majava.control.testcode.Majenerator;
import majava.enums.Wind;
import majava.summary.PaymentMap;
import majava.summary.PlayerSummary;
import majava.summary.RoundResultSummary;
import majava.tiles.Tile;
import majava.util.TileList;
import majava.util.YakuList;
import majava.yaku.Yaku;

public class ResultPanel extends JPanel{
	private static final long serialVersionUID = -5392789088556649589L;
	
	private static final int WIDTH = 600, HEIGHT = 500;
	
	private static final Color COLOR_BASE = Color.GRAY;
	private static final int ALPHA = 245;
	private static final Color COLOR_PANEL = new Color(COLOR_BASE.getRed(), COLOR_BASE.getGreen(), COLOR_BASE.getBlue(), ALPHA);
	
	private static final int NUM_PLAYERS = 4;
	
	
	
	
	
	
	
	protected TableViewBase.PlayerPanel mWinningPlayerPanel;
	protected TableViewBase.RoundResultLabelPanel mResultLabelPanel;
	protected PaymentsPanel mPaymentsPanel;
	protected YakuPanel mYakuPanel;
	
//	protected List<Yaku> yakuList;
	
	
	public ResultPanel(){
		super();
		
		
		setBounds(0,0,WIDTH,HEIGHT);
		setLayout(null);
		setBorder(new LineBorder(new Color(0, 0, 0), 8));
		setBackground(COLOR_PANEL);
		
		
		
		mResultLabelPanel = new TableViewBase.RoundResultLabelPanel();
		mResultLabelPanel.setLocation(200,15);
		mResultLabelPanel.setBackground(TableViewBase.COLOR_TRANSPARENT);
//		mResultLabelPanel.setBorder(null);
		add(mResultLabelPanel);
		
		
		
		mWinningPlayerPanel = new TableViewBase.PlayerPanel(TableViewBase.SEAT1);
		mWinningPlayerPanel.setLocation(10, 88);
		add(mWinningPlayerPanel);
		
		
		mPaymentsPanel = new PaymentsPanel();
		mPaymentsPanel.setLocation(142, 352);
		add(mPaymentsPanel);
		
		
		mYakuPanel = new YakuPanel();
		add(mYakuPanel);
		
		
		
		showResult(Majenerator.generateRoundResultSummary());
		
	}
	
	
	
	
	
	
	public void showResult(RoundResultSummary resum){
		
		__blankEverything();
		
		
		
		//for all
		String resultLabel = null;
		PaymentMap payments = null;
		//for win
		PlayerSummary winner = null, furikon = null;
		TileList winnerHandTiles = null; List<Meld> winnerMelds = null; Tile winningTile = null;
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
			yakuList = Majenerator.generateYakuList();
			yakuWorth = yakuList.totalHan();
			
			//***hand score label
			handScore = payments.get(winner);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//reuslt label
		mResultLabelPanel.setVisible(true);
		mResultLabelPanel.getLabelResult().setText(resultLabel);
		
		//payments
		mPaymentsPanel.setVisible(true);
		mPaymentsPanel.setPayments(payments);
		
		
		
		if (resum.isVictory()){
			mWinningPlayerPanel.setVisible(true);
			
			//winning hand
			int currentTile = 0, currentMeld = 0;
			for (currentTile = 0; currentTile < winnerHandTiles.size(); currentTile++)
				mWinningPlayerPanel.panelH.larryH[currentTile].setIcon(new ImageIcon(getClass().getResource("/res/img/tiles/" + winnerHandTiles.get(currentTile).getId() + ".png")));
			
			//winning melds
			for (currentMeld = 0; currentMeld < winnerMelds.size(); currentMeld++)
				for (currentTile = 0; currentTile < winnerMelds.get(currentMeld).size(); currentTile++)
					mWinningPlayerPanel.panelMs.panelHMs[currentMeld].larryHM[currentTile].setIcon(new ImageIcon(getClass().getResource("/res/img/tiles/small/" + winnerMelds.get(currentMeld).getTile(currentTile).getId() + ".png")));
			
			//yaku list
			mYakuPanel.setVisible(true);
			mYakuPanel.setYaku(yakuList);
		}
		
//		repaint();
	}
	
	
	
	private void __blankEverything(){
		mResultLabelPanel.setVisible(false);
		mPaymentsPanel.setVisible(false);
		
		mWinningPlayerPanel.blankAll();
		mWinningPlayerPanel.setVisible(false);
		
		mYakuPanel.setVisible(false);
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
	
	protected static class YakuPanel extends JPanel{
		private static final long serialVersionUID = 3133779361407012033L;
		
		
		YakuList yakuList;
		List<JLabel> yakuLabels = new ArrayList<JLabel>();
		
		public YakuPanel(){
			super();
			setBounds(79, 177, 129, 138);
			setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		}
		
		public void setYaku(YakuList ylist){
			blankAll();
			for (Yaku y: ylist){
				JLabel label = new JLabel();
				label.setText(y.toString());
				label.setSize(120,14);
				label.setPreferredSize(new Dimension(120, 14));
				new Dimension(120, 14);
				
				yakuLabels.add(label);
				add(label);
				
//				yakuLabels.add(new JLabel(y.toString()));
//				add(yakuLabels.get(yakuLabels.size()-1));
			}
		}
		public void blankAll(){
//			removeAll();
			while (!yakuLabels.isEmpty()) remove(yakuLabels.remove(0));
		}
	}
	
	
	
	
	
	protected static class PaymentsPanel extends JPanel{
		private static final long serialVersionUID = -3188452904507674217L;
		
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
				setBackground(Color.LIGHT_GRAY);
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
				lblPayment.setHorizontalAlignment(SwingConstants.LEFT);
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
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		showDemo(new ResultPanel());
	}
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
