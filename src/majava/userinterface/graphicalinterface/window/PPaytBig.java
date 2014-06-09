package majava.userinterface.graphicalinterface.window;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class PPaytBig extends JPanel{
	private static final long serialVersionUID = -3188452904507674217L;
	
	
	
	
//	private PPayt panelPlayerPayments[] = new PPayt[4];
	
	private PPayt pp1 = new PPayt();
	private PPayt pp2 = new PPayt();
	private PPayt pp3 = new PPayt();
	private PPayt pp4 = new PPayt();
	
	public PPaytBig(){
		super();
		
		setBounds(0,0,315,107);
		
		setLayout(null);
		
		
		pp1.setLocation(92, 72);
		pp2.setLocation(184, 36);
		pp3.setLocation(92, 0);
		pp4.setLocation(0, 36);
		
		
		
		add(pp1);
		add(pp2);
		add(pp3);
		add(pp4);
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
		
		
		PPaytBig resPan = new PPaytBig();
		resPan.setLocation(0,0);
		contentPane.add(resPan);
		
		frame.setVisible(true);
	}
}
