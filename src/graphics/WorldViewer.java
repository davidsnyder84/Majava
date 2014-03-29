package graphics;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class WorldViewer extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorldViewer frame = new WorldViewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//
	/**
	 * Create the frame.
	 */
	public WorldViewer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1150, 850);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		
		
		
		
		
		ArrayList<JLabel> lblListH1Tiles = new ArrayList<JLabel>();
		ArrayList<JLabel> labelList = new ArrayList<JLabel>();
		JLabel[] labelArray = new JLabel[14];
		
		
		
		
		JPanel panelH1 = new JPanel();
		panelH1.setBounds(305, 520, 506, 48);
		contentPane.add(panelH1);
		panelH1.setLayout(null);
		
		JLabel lblH1T1 = new JLabel("");
		lblH1T1.setBounds(0, 0, 36, 46);
		lblH1T1.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T1);
		
		JLabel lblH1T2 = new JLabel("");
		lblH1T2.setBounds(36, 0, 36, 46);
		lblH1T2.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T2);
		
		JLabel lblH1T3 = new JLabel("");
		lblH1T3.setBounds(72, 0, 36, 46);
		lblH1T3.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T3);
		
		JLabel lblH1T4 = new JLabel("");
		lblH1T4.setBounds(108, 0, 36, 46);
		lblH1T4.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T4);
		
		JLabel lblH1T5 = new JLabel("");
		lblH1T5.setBounds(144, 0, 36, 46);
		lblH1T5.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T5);
		
		JLabel lblH1T6 = new JLabel("");
		lblH1T6.setBounds(180, 0, 36, 46);
		lblH1T6.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T6);
		
		JLabel lblH1T7 = new JLabel("");
		lblH1T7.setBounds(216, 0, 36, 46);
		lblH1T7.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T7);
		
		JLabel lblH1T8 = new JLabel("");
		lblH1T8.setBounds(252, 0, 36, 46);
		lblH1T8.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T8);
		
		JLabel lblH1T9 = new JLabel("");
		lblH1T9.setBounds(288, 0, 36, 46);
		lblH1T9.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T9);
		
		JLabel lblH1T10 = new JLabel("");
		lblH1T10.setBounds(324, 0, 36, 46);
		lblH1T10.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T10);
		
		JLabel lblH1T11 = new JLabel("");
		lblH1T11.setBounds(360, 0, 36, 46);
		lblH1T11.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T11);
		
		JLabel lblH1T12 = new JLabel("");
		lblH1T12.setBounds(396, 0, 36, 46);
		lblH1T12.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T12);
		
		JLabel lblH1T13 = new JLabel("");
		lblH1T13.setBounds(432, 0, 36, 46);
		lblH1T13.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T13);
		
		JLabel lblH1T14 = new JLabel("");
		lblH1T14.setBounds(468, 0, 36, 46);
		lblH1T14.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\1.gif"));
		panelH1.add(lblH1T14);
		
		
		
		
		
	}
}
