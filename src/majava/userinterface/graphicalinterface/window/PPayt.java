package majava.userinterface.graphicalinterface.window;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;

public class PPayt extends JPanel{
	private static final long serialVersionUID = -1732543659928397387L;
	
	
	private JPanel panPayment = new JPanel();
	
	private JLabel lblPoints = new JLabel();
	private JLabel lblPayment = new JLabel();
	private JLabel lblPlayerName = new JLabel();
	private JLabel lblPlayerWind = new JLabel();
	private JLabel lblPlayerNum = new JLabel();
	
	
	public PPayt(){
		super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		
		setBounds(0,0,131,35);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
//		lblPoints.setBounds("25000");
//		lblPayment.setBounds("+8000");
//		lblPlayerName.setBounds("Suwado");
//		lblPlayerWind.setBounds(new ImageIcon(getClass().getResource("/res/img/winds/small/transEs.png")));
//		lblPlayerNum.setBounds("1");
		
		
		panPayment.setBounds(30,17,100,14);
//		panPayment.setLayout(null);
		panPayment.setOpaque(false);
		panPayment.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panPayment.add(lblPoints);
		panPayment.add(lblPayment);

		lblPayment.setLocation(52, 0);
		lblPoints.setLocation(0, 0);
		
		
		lblPoints.setText("128000  ");
		lblPoints.setHorizontalAlignment(SwingConstants.CENTER);
//		lblPoints.setLocation(30, 17);
		lblPoints.setSize(48, 14);
		
		lblPayment.setForeground(Color.BLUE);
		lblPayment.setHorizontalAlignment(SwingConstants.LEFT);
//		lblPayment.setLocation(82, 17);
		lblPayment.setSize(48, 14);
		lblPayment.setText("+48000");
		
		lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerName.setLocation(30, 3);
		lblPlayerName.setSize(89, 14);
		lblPlayerName.setText("Suwado");
		
		lblPlayerWind.setLocation(3, 5);
		lblPlayerWind.setSize(23, 23);
		lblPlayerWind.setIcon(new ImageIcon(getClass().getResource("/res/img/winds/small/transEs.png")));
		lblPlayerNum.setText("1");
		
		
		add(panPayment);
//		add(lblPoints);
//		add(lblPayment);
		add(lblPlayerName);
		add(lblPlayerWind);
//		add(lblPlayerNum);
	}
}
