package majava.graphics;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ResultPanel extends JPanel{
	private static final long serialVersionUID = -5392789088556649589L;
	
	private static final int WIDTH = 500, HEIGHT = 500;
	
	private static final Color COLOR_BASE = Color.GRAY;
	private static final int ALPHA = 245;
	private static final Color COLOR_PANEL = new Color(COLOR_BASE.getRed(), COLOR_BASE.getGreen(), COLOR_BASE.getBlue(), ALPHA);
	
	
	public ResultPanel(){
		super();
		
		setBounds(0,0,WIDTH,HEIGHT);
		setLayout(null);
		setBorder(new LineBorder(new Color(0, 0, 0), 8));
		
		setBackground(COLOR_PANEL);
		
		JPanel res = new TableGUI.RoundResultLabelPanel();
		res.setLocation(120,20);
		add(res);
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		JPanel contentPane = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 600, 600);
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		
		
		ResultPanel resPan = new ResultPanel();
		contentPane.add(resPan);
		
		frame.setVisible(true);
	}

}
