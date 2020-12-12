package src.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;

import src.organisation.StringConverter;


/**
 * @author Tobias Mitterreiter
 * @version 1.0
 * this abstract class handles the client side
 */
public abstract class FrameworkClient extends JFrame implements Runnable, ActionListener{
	
	private Socket connection= null;
	protected DataInputStream dis = null;
	protected DataOutputStream dos = null;
	
	protected boolean isPlaying = false;
	
	private String playerName = null;
	private String currentGame = null;
	private boolean isLoggedIn = false;
	
	// GUI elements
	protected JFrame menuFrame = new JFrame();
	protected JTextField usernameTextField;
	protected JPasswordField passwordField;
	protected JLabel currentUserLabel;
	protected JLabel usernameLabel;
	protected JLabel passwordLabel;
	protected JButton loginButton;
	protected JButton createAccountButton;
	protected JLabel createAccountLabel;
	protected JLabel selectGameLabel;
	protected JButton playChessButton;
	protected JButton playMillButton;
	protected JLabel restoreGameLabel;
	protected JButton restoreGameButton;
	protected JSeparator separator_1;
	protected JSeparator separator_2;
	
	
	protected JFrame board;
	
	String newState = " ";
	
	String s1 = "<rook,black=A8><knight,black=B8><bishop,black=C8><queen,black=D8><king,black=E8><bishop,black=F8><knight,black=G8><rook,black=H8><pawn,black=A7><pawn,black=B7><pawn,black=C7><pawn,black=D7><pawn,black=E7><pawn,black=F7><pawn,black=G7><pawn,black=H7><rook,white=A1><knight,white=B1><bishop,white=C1><queen,white=D1><king,white=E1><bishop,white=F1><knight,white=G1><rook,white=H1><pawn,white=A2><pawn,white=B2><pawn,white=C2><pawn,white=D2><pawn,white=E2><pawn,white=F2><pawn,white=G2><pawn,white=H2>";
	
	String s2 = "<rook,black=null><knight,black=null><bishop,black=C8><queen,black=D8><king,black=E8><bishop,black=F8><knight,black=G8><rook,black=H8><pawn,black=A7><pawn,black=B7><pawn,black=C7><pawn,black=D7><pawn,black=E7><pawn,black=F7><pawn,black=G7><pawn,black=H7><rook,white=A1><knight,white=B1><bishop,white=C1><queen,white=D1><king,white=E1><bishop,white=F1><knight,white=G1><rook,white=H1><pawn,white=A2><pawn,white=B2><pawn,white=C2><pawn,white=D2><pawn,white=E2><pawn,white=F2><pawn,white=G3><pawn,white=H5>";
	
    /**
     * This method is called when user starts the application
     * it connects to server
     * @param host String
     * @param port int
     */
	public void connect(String host, int port) {
		
		try {
			connection = new Socket(host, port);
			dis = new DataInputStream(connection.getInputStream()); // get data from sever
			dos = new DataOutputStream(connection.getOutputStream()); // get data to server
			setupGUI();
			String s = dis.readUTF();
			
			run();
			
		} catch (IOException ieo) {
			JOptionPane.showMessageDialog(null, "Server is not running", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	//@Override
	public void run() {
		long t= System.currentTimeMillis();
		long end = t+4000;
		System.out.println("run");
//		setupGameBoard(s1);
//		board.dispose();
//		
//		while(newState == " ") {
//			newState = ((ChessBoard) board).getNewState();
//		}
//		
//		String newS = ((ChessBoard) board).getNewState();
//		System.out.println(newS);
//	
//		board.dispose();
//		setupGameBoard(s2);
//		board.dispose();
		
		
		while(System.currentTimeMillis() < end) {
			
		}
		
		setupGameBoard(s2);
	}	
	
	

	
    /**
     * This method is called when user clicks "login" button
     * it checks if user name and password matches to an user in our database
     * @return Boolean
     * @param username String
     * @param password String
     */
	public boolean login(String username, String password) {
		String input;
		String send;
		String information = "";
		boolean receiveData = false;
		
		send = "<Login=" + username + "," + password + ">";
		
		try {
			dos.writeUTF(send);
		} catch (IOException e) {	
		}
		
		while (receiveData == false) {
			try {
				input = dis.readUTF();
				information = getServerInput(input);
				switch(StringConverter.stringToInformation(input)){
					case LOGIN:
						receiveData = true;
						break;				
					default:
						break;			
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}	
		if (information.equals("True"))
			return true;
		else
			return false;
	}
	
	
    /**
     * This method is called when user clicks "create account" button
     * it sends user name and password to sever ands adds new player to database
     */
	public void createAccount(String username, String password) {
		String send;
		send = "<NewAccount=" + username + "," + password + ">";

		try {
			dos.writeUTF(send);
		} catch (Exception e) {
			//TODO
		}
	}

	
    /**
     * This method setup the menu GUI 
     * it contains 5 ActionListener for "login", "create account", "play chess", "play mill", "restore game"
     */
	public void setupGUI() {	
		
		// Menu Frame
		menuFrame = new JFrame();
		
		// ContentPane
		menuFrame.getContentPane().setEnabled(false);
		menuFrame.setBounds(100, 100, 1000, 680);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.getContentPane().setLayout(null);	
		
		// Label username
		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		usernameLabel.setBounds(58, 80, 117, 35);
		menuFrame.getContentPane().add(usernameLabel);
		
		// Label password
		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		passwordLabel.setBounds(357, 77, 122, 41);
		menuFrame.getContentPane().add(passwordLabel);
		
		// Textfield for username
		usernameTextField = new JTextField();
		usernameTextField.setBounds(159, 88, 169, 19);
		menuFrame.getContentPane().add(usernameTextField);
		usernameTextField.setColumns(10);
		
		// Passwordfield for password
		passwordField = new JPasswordField();
		passwordField.setBounds(447, 88, 169, 19);
		menuFrame.getContentPane().add(passwordField);
		
		// Label currently logged in as
		currentUserLabel = new JLabel("You are currently logged in as an anonymous Player");
		currentUserLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		currentUserLabel.setBounds(12, 12, 580, 33);
		menuFrame.getContentPane().add(currentUserLabel);
		
		// Button login
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				String name = usernameTextField.getText();
				String password = passwordField.getText();	
				
				if(login(name, password)) {
					isLoggedIn = true;
					playerName = name;
					JOptionPane.showMessageDialog(menuFrame, "your login was successful");
					currentUserLabel.setText("You are currently logged in as " + playerName);

					updateSetupGUI();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
					passwordField.setText(null);
					usernameTextField.setText(null);
				}
			}
		});		
		loginButton.setBounds(277, 130, 117, 25);
		menuFrame.getContentPane().add(loginButton);
		
		// Sperator
		separator_2 = new JSeparator();
		separator_2.setBounds(12, 57, 604, 11);
		menuFrame.getContentPane().add(separator_2);
		
		// Button create account
		createAccountButton = new JButton("Create Account");
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String newUser = JOptionPane.showInputDialog("Enter your user name:");
				String newPassword1 = JOptionPane.showInputDialog("Enter your password:");
				String newPassword2 = JOptionPane.showInputDialog("Enter your password again");
				
				if (newPassword1.contentEquals(newPassword2)) {
					createAccount(newUser, newPassword1);
				} else {
					JOptionPane.showMessageDialog(menuFrame, "Your passwords do not match");
				}
			}
		});
		createAccountButton.setBounds(406, 587, 186, 25);
		menuFrame.getContentPane().add(createAccountButton);
		
		// Seperator
		separator_1 = new JSeparator();
		separator_1.setBounds(12, 518, 965, 11);
		menuFrame.getContentPane().add(separator_1);
		
		// Label create account
		createAccountLabel = new JLabel("if you dont have an account yet, feel free to create one, to save your current Game");
		createAccountLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		createAccountLabel.setBounds(203, 549, 685, 15);
		menuFrame.getContentPane().add(createAccountLabel);
		
		// Label for all games
		selectGameLabel = new JLabel("You can play one of the following games");
		selectGameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		selectGameLabel.setBounds(12, 242, 348, 15);
		menuFrame.getContentPane().add(selectGameLabel);
		
		// Button play chess
		playChessButton = new JButton("Play Chess");
		playChessButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentGame = "Chess";
				try {
					dos.writeUTF("<Gamemode=Chess>");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		playChessButton.setBounds(413, 232, 117, 41);
		menuFrame.getContentPane().add(playChessButton);
		
		// Button play mill
		playMillButton = new JButton("Play Mill");
		playMillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentGame = "Mill";
				try {
					dos.writeUTF("<Gamemode=Mill>");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		playMillButton.setBounds(646, 229, 117, 44);
		menuFrame.getContentPane().add(playMillButton);
		
		// Label restore Game
		restoreGameLabel = new JLabel("Or you can restore your latest game");
		restoreGameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		restoreGameLabel.setBounds(12, 350, 316, 15);
		menuFrame.getContentPane().add(restoreGameLabel);
		
		// Button restore Game
		restoreGameButton = new JButton("Restore Game");
		restoreGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO restore latest game from database
			}
		});
		restoreGameButton.setBounds(413, 337, 155, 41);
		menuFrame.getContentPane().add(restoreGameButton);
		
		// Window Listener for Closing
		menuFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					dos.writeUTF("<Connectionstatus=Exit>");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.getWindow().dispose();
			}
		});
	
		updateSetupGUI();
		menuFrame.setVisible(true);
	}
	
    /**
     * This method updates the menuGUI 
     * if a user logs in, some GUI elements are set to invisible
     */
	private void updateSetupGUI() {
		if (isLoggedIn == false) {
			restoreGameLabel.setVisible(false);
			restoreGameButton.setVisible(false);
			createAccountButton.setVisible(true);
			createAccountLabel.setVisible(true);
			separator_1.setVisible(true);
		} else {
			createAccountButton.setVisible(false);
			createAccountLabel.setVisible(false);
			separator_1.setVisible(false);
			restoreGameLabel.setVisible(true);
			restoreGameButton.setVisible(true);
		}
	}
	
	private String getServerInput(String input) {
        StringBuilder information = new StringBuilder();
        int charNumber = 0;

        for(int j = 0 ; j < input.length(); j ++){
            if(input.charAt(j) == '='){
                charNumber = j + 1;
                break;
            }
        }

        for(int i = charNumber; i < input.length(); i ++){
            if(input.charAt(i) == '>')
                break;
            information.append(input.charAt(i));
        }
        return information.toString();
	}


	public void setupGameBoard(String s) {
		
		board = new ChessBoard("black", s);
		board.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		board.pack();
		board.setResizable(true);
		board.setLocationRelativeTo( null );
		board.setVisible(true);
	}
	
	public abstract void updateBoard();
	
	public abstract void restoreGame();
	
	public abstract void makeMove();
	
	public abstract void undoMove();
}