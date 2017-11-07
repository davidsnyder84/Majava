package majava.userinterface.graphicalinterface.window;



import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

import utility.Pauser;


//a welcome menu GUI to start the game 
public class MajavaWelcome extends JFrame implements ActionListener {
	private static final long serialVersionUID = 3226158067427833672L;
	
	private static final String LOGO_IMAGE_PATH = "/res/img/omake/sounotori.png";
	private static final String LOGO_TEXT = "MAJAVA";
	private static final String WINDOW_ICON_PATH = "/res/img/winds/transE.png";

	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 300;
	
	private static final int WINDOW_TOP_BORDER_SIZE = 26;
	private static final int WINDOW_SIDE_BORDER_SIZE = 2;
	private static final int WINDOW_BOTTOM_BORDER_SIZE = 2;
//	private static final int WINDOW_MENU_SIZE = 23;
	
	private static final Color COLOR_CONTENT_PANE = new Color(210,0, 210, 30);
	
	private static final String COMMAND_SINGLE_PLAYER = "Single Player Game";
	private static final String COMMAND_COMPUTERS_PLAYERS_ONLY = "Computer Players Only";
	private static final String COMMAND_QUIT = "Quit";
	private static final String COMMAND_FAST_GAMEPLAY = "Fast gameplay";
	
	
	
	private enum Option {UNDECIDED, SINGLE_PLAYER, COMPUTER_ONLY}
	
	private Option optionChosen;
	private boolean doFastGameplay;
	
	
	public MajavaWelcome(){
		final int WINDOW_BOUND_WIDTH = WINDOW_WIDTH + 2*WINDOW_SIDE_BORDER_SIZE;
		final int WINDOW_BOUND_HEIGHT = WINDOW_HEIGHT + WINDOW_TOP_BORDER_SIZE + WINDOW_BOTTOM_BORDER_SIZE;
		
		
		ImageIcon windowIconImg = new ImageIcon(getClass().getResource(WINDOW_ICON_PATH));
		setIconImage(windowIconImg.getImage());
		
		setTitle("Majava - Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, WINDOW_BOUND_WIDTH, WINDOW_BOUND_HEIGHT);
		setResizable(false);
		
		
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(COLOR_CONTENT_PANE);
		
		JLabel lblMajava = new JLabel(LOGO_TEXT);
		lblMajava.setFont(new Font("High Tower Text", Font.PLAIN, 52));
		lblMajava.setHorizontalAlignment(SwingConstants.CENTER);
		lblMajava.setBounds(0, 0, 500, 97);
		contentPane.add(lblMajava);
		
		
		JButton btnSinglePlayerGame = new JButton();
		setupText(btnSinglePlayerGame, COMMAND_SINGLE_PLAYER);
		btnSinglePlayerGame.setBounds(156, 97, 174, 40);
		contentPane.add(btnSinglePlayerGame);
		
		JButton btnComputerPlayersOnly = new JButton();
		setupText(btnComputerPlayersOnly, COMMAND_COMPUTERS_PLAYERS_ONLY);
		btnComputerPlayersOnly.setBounds(156, 144, 174, 40);
		contentPane.add(btnComputerPlayersOnly);
		
		JButton btnQuit = new JButton();
		setupText(btnQuit, COMMAND_QUIT);
		btnQuit.setBounds(156, 236, 174, 40);
		contentPane.add(btnQuit);
		
		JLabel lblTori = new JLabel("");
		lblTori.setBounds(351, 91, 137, 190);
		lblTori.setIcon(new ImageIcon(getClass().getResource(LOGO_IMAGE_PATH)));
		contentPane.add(lblTori);
		
		JCheckBox checkboxFastGameplay = new JCheckBox();
		setupText(checkboxFastGameplay, COMMAND_FAST_GAMEPLAY);
		checkboxFastGameplay.setBounds(156, 191, 146, 23);
		checkboxFastGameplay.setOpaque(true);
		checkboxFastGameplay.setSelected(false);
		contentPane.add(checkboxFastGameplay);
		
		
		optionChosen = Option.UNDECIDED;
		doFastGameplay = checkboxFastGameplay.isSelected();
	}
	private void setupText(AbstractButton button, String text){
		button.setText(text);
		button.setActionCommand(text);
		button.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (arg0.getActionCommand().equals(COMMAND_SINGLE_PLAYER)) optionChosen = Option.SINGLE_PLAYER;
		if (arg0.getActionCommand().equals(COMMAND_COMPUTERS_PLAYERS_ONLY)) optionChosen = Option.COMPUTER_ONLY;
		
		if (arg0.getActionCommand().equals(COMMAND_FAST_GAMEPLAY)) doFastGameplay = !doFastGameplay;
		
		if (arg0.getActionCommand().equals(COMMAND_QUIT)) System.exit(0);
	}
	
	
	
	
	public void waitForChoice(){
		while (optionChosen == Option.UNDECIDED)
			Pauser.pauseFor(500);
		this.dispose();
	}
	
	public boolean choseSinglePlayer(){return (optionChosen == Option.SINGLE_PLAYER);}
	public boolean choseFastGameplay(){return doFastGameplay;}
	
	
	
	public static void main(String[] args) {
		MajavaWelcome welcomer = new MajavaWelcome();
		welcomer.setVisible(true);
		
		welcomer.waitForChoice();
		
		System.out.println(welcomer.choseSinglePlayer());
	}
	
}
