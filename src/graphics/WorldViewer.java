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
		
		
		
		
		
		
		
		
		
		
		
		
		JPanel panelP1 = new JPanel();
		panelP1.setBounds(454, 222, 162, 140);
		contentPane.add(panelP1);
		panelP1.setLayout(new GridLayout(4, 6, 0, 0));
		
		JLabel lblP1T1 = new JLabel("");
		panelP1.add(lblP1T1);
		lblP1T1.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T2 = new JLabel("");
		panelP1.add(lblP1T2);
		lblP1T2.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T3 = new JLabel("");
		panelP1.add(lblP1T3);
		lblP1T3.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T4 = new JLabel("");
		panelP1.add(lblP1T4);
		lblP1T4.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T5 = new JLabel("");
		panelP1.add(lblP1T5);
		lblP1T5.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T6 = new JLabel("");
		panelP1.add(lblP1T6);
		lblP1T6.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T7 = new JLabel("");
		panelP1.add(lblP1T7);
		lblP1T7.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T8 = new JLabel("");
		panelP1.add(lblP1T8);
		lblP1T8.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T9 = new JLabel("");
		panelP1.add(lblP1T9);
		lblP1T9.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T10 = new JLabel("");
		panelP1.add(lblP1T10);
		lblP1T10.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T11 = new JLabel("");
		panelP1.add(lblP1T11);
		lblP1T11.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T12 = new JLabel("");
		panelP1.add(lblP1T12);
		lblP1T12.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T13 = new JLabel("");
		panelP1.add(lblP1T13);
		lblP1T13.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T14 = new JLabel("");
		panelP1.add(lblP1T14);
		lblP1T14.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T15 = new JLabel("");
		panelP1.add(lblP1T15);
		lblP1T15.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T16 = new JLabel("");
		panelP1.add(lblP1T16);
		lblP1T16.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T17 = new JLabel("");
		panelP1.add(lblP1T17);
		lblP1T17.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T18 = new JLabel("");
		panelP1.add(lblP1T18);
		lblP1T18.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T19 = new JLabel("");
		panelP1.add(lblP1T19);
		lblP1T19.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T20 = new JLabel("");
		panelP1.add(lblP1T20);
		lblP1T20.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T21 = new JLabel("");
		panelP1.add(lblP1T21);
		lblP1T21.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T22 = new JLabel("");
		panelP1.add(lblP1T22);
		lblP1T22.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T23 = new JLabel("");
		panelP1.add(lblP1T23);
		lblP1T23.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		JLabel lblP1T24 = new JLabel("");
		panelP1.add(lblP1T24);
		lblP1T24.setIcon(new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\2.gif"));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		ImageIcon wImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\tileback.gif");
		
		JPanel panelW1 = new JPanel();
		panelW1.setBounds(150, 379, 459, 70);
		contentPane.add(panelW1);
		panelW1.setLayout(new GridLayout(2, 17, 0, 0));
		
		
		JLabel lblW1T1 = new JLabel("");
		panelW1.add(lblW1T1);
		lblW1T1.setIcon(wImg);
		
		JLabel lblW1T2 = new JLabel("");
		panelW1.add(lblW1T2);
		lblW1T2.setIcon(wImg);
		
		JLabel lblW1T3 = new JLabel("");
		panelW1.add(lblW1T3);
		lblW1T3.setIcon(wImg);
		
		JLabel lblW1T4 = new JLabel("");
		panelW1.add(lblW1T4);
		lblW1T4.setIcon(wImg);
		
		JLabel lblW1T5 = new JLabel("");
		panelW1.add(lblW1T5);
		lblW1T5.setIcon(wImg);
		
		JLabel lblW1T6 = new JLabel("");
		panelW1.add(lblW1T6);
		lblW1T6.setIcon(wImg);
		
		JLabel lblW1T7 = new JLabel("");
		panelW1.add(lblW1T7);
		lblW1T7.setIcon(wImg);
		
		JLabel lblW1T8 = new JLabel("");
		panelW1.add(lblW1T8);
		lblW1T8.setIcon(wImg);
		
		JLabel lblW1T9 = new JLabel("");
		panelW1.add(lblW1T9);
		lblW1T9.setIcon(wImg);
		
		JLabel lblW1T10 = new JLabel("");
		panelW1.add(lblW1T10);
		lblW1T10.setIcon(wImg);
		
		JLabel lblW1T11 = new JLabel("");
		panelW1.add(lblW1T11);
		lblW1T11.setIcon(wImg);
		
		JLabel lblW1T12 = new JLabel("");
		panelW1.add(lblW1T12);
		lblW1T12.setIcon(wImg);
		
		JLabel lblW1T13 = new JLabel("");
		panelW1.add(lblW1T13);
		lblW1T13.setIcon(wImg);
		
		JLabel lblW1T14 = new JLabel("");
		panelW1.add(lblW1T14);
		lblW1T14.setIcon(wImg);
		
		JLabel lblW1T15 = new JLabel("");
		panelW1.add(lblW1T15);
		lblW1T15.setIcon(wImg);
		
		JLabel lblW1T16 = new JLabel("");
		panelW1.add(lblW1T16);
		lblW1T16.setIcon(wImg);
		
		JLabel lblW1T17 = new JLabel("");
		panelW1.add(lblW1T17);
		lblW1T17.setIcon(wImg);
		
		JLabel lblW1T18 = new JLabel("");
		panelW1.add(lblW1T18);
		lblW1T18.setIcon(wImg);
		
		JLabel lblW1T19 = new JLabel("");
		panelW1.add(lblW1T19);
		lblW1T19.setIcon(wImg);
		
		JLabel lblW1T20 = new JLabel("");
		panelW1.add(lblW1T20);
		lblW1T20.setIcon(wImg);
		
		JLabel lblW1T21 = new JLabel("");
		panelW1.add(lblW1T21);
		lblW1T21.setIcon(wImg);
		
		JLabel lblW1T22 = new JLabel("");
		panelW1.add(lblW1T22);
		lblW1T22.setIcon(wImg);
		
		JLabel lblW1T23 = new JLabel("");
		panelW1.add(lblW1T23);
		lblW1T23.setIcon(wImg);
		
		JLabel lblW1T24 = new JLabel("");
		panelW1.add(lblW1T24);
		lblW1T24.setIcon(wImg);
		
		JLabel lblW1T25 = new JLabel("");
		panelW1.add(lblW1T25);
		lblW1T25.setIcon(wImg);
		
		JLabel lblW1T26 = new JLabel("");
		panelW1.add(lblW1T26);
		lblW1T26.setIcon(wImg);
		
		JLabel lblW1T27 = new JLabel("");
		panelW1.add(lblW1T27);
		lblW1T27.setIcon(wImg);
		
		JLabel lblW1T28 = new JLabel("");
		panelW1.add(lblW1T28);
		lblW1T28.setIcon(wImg);
		
		JLabel lblW1T29 = new JLabel("");
		panelW1.add(lblW1T29);
		lblW1T29.setIcon(wImg);
		
		JLabel lblW1T30 = new JLabel("");
		panelW1.add(lblW1T30);
		lblW1T30.setIcon(wImg);
		
		JLabel lblW1T31 = new JLabel("");
		panelW1.add(lblW1T31);
		lblW1T31.setIcon(wImg);
		
		JLabel lblW1T32 = new JLabel("");
		panelW1.add(lblW1T32);
		lblW1T32.setIcon(wImg);
		
		JLabel lblW1T33 = new JLabel("");
		panelW1.add(lblW1T33);
		lblW1T33.setIcon(wImg);
		
		JLabel lblW1T34 = new JLabel("");
		panelW1.add(lblW1T34);
		lblW1T34.setIcon(wImg);
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		ImageIcon meldImg = new ImageIcon("C:\\Users\\David\\workspace\\MajavaWorking\\img\\tiles\\normal\\small\\12.gif");
		
		
		JPanel panelH1M1 = new JPanel();
		panelH1M1.setBounds(468, 650, 108, 35);
		contentPane.add(panelH1M1);
		panelH1M1.setLayout(new GridLayout(1, 4, 0, 0));
		
		
		JLabel lblH1M1T1 = new JLabel("");
		panelH1M1.add(lblH1M1T1);
		lblH1M1T1.setIcon(meldImg);
		
		JLabel lblH1M1T2 = new JLabel("");
		panelH1M1.add(lblH1M1T2);
		lblH1M1T2.setIcon(meldImg);
		
		JLabel lblH1M1T3 = new JLabel("");
		panelH1M1.add(lblH1M1T3);
		lblH1M1T3.setIcon(meldImg);
		
		JLabel lblH1M1T4 = new JLabel("");
		panelH1M1.add(lblH1M1T4);
		lblH1M1T4.setIcon(meldImg);
		
		
		
		
		JPanel panelH1M2 = new JPanel();
		panelH1M2.setBounds(703, 650, 108, 35);
		contentPane.add(panelH1M2);
		panelH1M2.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH1M2T1 = new JLabel("");
		panelH1M2.add(lblH1M2T1);
		lblH1M2T1.setIcon(meldImg);
		
		JLabel lblH1M2T2 = new JLabel("");
		panelH1M2.add(lblH1M2T2);
		lblH1M2T2.setIcon(meldImg);
		
		JLabel lblH1M2T3 = new JLabel("");
		panelH1M2.add(lblH1M2T3);
		lblH1M2T3.setIcon(meldImg);
		
		JLabel lblH1M2T4 = new JLabel("");
		panelH1M2.add(lblH1M2T4);
		lblH1M2T4.setIcon(meldImg);
		
		
		
		
		JPanel panelH1M3 = new JPanel();
		panelH1M3.setBounds(886, 650, 108, 35);
		contentPane.add(panelH1M3);
		panelH1M3.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH1M3T1 = new JLabel("");
		panelH1M3.add(lblH1M3T1);
		lblH1M3T1.setIcon(meldImg);
		
		JLabel lblH1M3T2 = new JLabel("");
		panelH1M3.add(lblH1M3T2);
		lblH1M3T2.setIcon(meldImg);
		
		JLabel lblH1M3T3 = new JLabel("");
		panelH1M3.add(lblH1M3T3);
		lblH1M3T3.setIcon(meldImg);
		
		JLabel lblH1M3T4 = new JLabel("");
		panelH1M3.add(lblH1M3T4);
		lblH1M3T4.setIcon(meldImg);
		
		
		
		
		JPanel panelH1M4 = new JPanel();
		panelH1M4.setBounds(1004, 650, 108, 35);
		contentPane.add(panelH1M4);
		panelH1M4.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblH1M4T1 = new JLabel("");
		panelH1M4.add(lblH1M4T1);
		lblH1M4T1.setIcon(meldImg);
		
		JLabel lblH1M4T2 = new JLabel("");
		panelH1M4.add(lblH1M4T2);
		lblH1M4T2.setIcon(meldImg);
		
		JLabel lblH1M4T3 = new JLabel("");
		panelH1M4.add(lblH1M4T3);
		lblH1M4T3.setIcon(meldImg);
		
		JLabel lblH1M4T4 = new JLabel("");
		panelH1M4.add(lblH1M4T4);
		lblH1M4T4.setIcon(meldImg);
		
		JPanel panelH1Ms = new JPanel();
		panelH1Ms.setBounds(110, 32, 456, 35);
		contentPane.add(panelH1Ms);
		panelH1Ms.setLayout(new GridLayout(1, 4, 0, 0));
		
		
		
		
		
		
		
		
		
		
		
		///////////
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
