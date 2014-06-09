package majava.userinterface.graphicalinterface.window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Joker extends ResultPanel{
	private static final long serialVersionUID = -7920542375306312978L;














	public Joker(){
		super();
		mYakuPanel.setBounds(79, 177, 437, 138);
		mYakuPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){
//		for (int i = 0; i < 200; i++) DEMOthis();
//		DEMOthisNoPBS();
		
		final int WINDOW_WIDTH = 1120 + (-62*2 - 6) + 2*2;
		final int WINDOW_HEIGHT = 726 + 6 + (-62*2 + 25 + 18) + 26 + 23;
		
		JFrame frame = new JFrame();
		JPanel contentPane = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		
		
		Joker resPan = new Joker();
		resPan.setLocation(57,75);
		contentPane.add(resPan);
		
		frame.setVisible(true);
	}

}
