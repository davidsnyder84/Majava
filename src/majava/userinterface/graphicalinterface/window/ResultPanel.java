package majava.userinterface.graphicalinterface.window;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ResultPanel extends JPanel{
	private static final long serialVersionUID = -5392789088556649589L;
	
	private static final int WIDTH = 600, HEIGHT = 500;
	
	private static final Color COLOR_BASE = Color.GRAY;
	private static final int ALPHA = 245;
	private static final Color COLOR_PANEL = new Color(COLOR_BASE.getRed(), COLOR_BASE.getGreen(), COLOR_BASE.getBlue(), ALPHA);
	
	
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
	
	
	
	
	
	

	
	
	public static void main(String[] args) {
		
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
		
		frame.setVisible(true);
	}

}
