package src.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;

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
	
	private Thread runner = null;
	private Socket connection= null;
	protected DataInputStream dis = null;
	protected DataOutputStream dos = null;
	
	protected boolean isPlaying = false;
	
	private String playerName = null;
	private boolean isLoggedIn = false;
	private Object selection;
	
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
	
	protected String color = null;
	protected boolean myTurn = false;
	protected String result = " ";
	
	protected boolean remove = false;
	int tokencounter = 0;
	

	protected String newState;
	protected String oldState;
	protected String defaultSetup;
	
	protected String currentState = "";
	
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
						
			JDialog.setDefaultLookAndFeelDecorated(true);
		    Object[] selectionValues = { "Chess", "Mill"};
		    String initialSelection = "Chess";
		    selection = JOptionPane.showInputDialog(null, "Select a Game",
		        "Select a game", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);

		    setupStates();		
			this.start();
			
		} catch (IOException ieo) {
			JOptionPane.showMessageDialog(null, "Server is not running", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setupStates() {
		if (selection == "Mill") {
			this.newState = " ";
			this.oldState = "<token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null>";
			this.defaultSetup = "<token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null>";
		} else if (selection == "Chess") {
			this.newState = " ";	
			this.oldState = "<rook,black,0=A8><knight,black,0=B8><bishop,black,0=C8><queen,black,0=D8><king,black,0=E8><bishop,black,0=F8><knight,black,0=G8><rook,black,0=H8><pawn,black,0=A7><pawn,black,0=B7><pawn,black,0=C7><pawn,black,0=D7><pawn,black,0=E7><pawn,black,0=F7><pawn,black,0=G7><pawn,black,0=H7><rook,white,0=A1><knight,white,0=B1><bishop,white,0=C1><queen,white,0=D1><king,white,0=E1><bishop,white,0=F1><knight,white,0=G1><rook,white,0=H1><pawn,white,0=A2><pawn,white,0=B2><pawn,white,0=C2><pawn,white,0=D2><pawn,white,0=E2><pawn,white,0=F2><pawn,white,0=G2><pawn,white,0=H2>";
			this.defaultSetup = "<rook,black,0=A8><knight,black,0=B8><bishop,black,0=C8><queen,black,0=D8><king,black,0=E8><bishop,black,0=F8><knight,black,0=G8><rook,black,0=H8><pawn,black,0=A7><pawn,black,0=B7><pawn,black,0=C7><pawn,black,0=D7><pawn,black,0=E7><pawn,black,0=F7><pawn,black,0=G7><pawn,black,0=H7><rook,white,0=A1><knight,white,0=B1><bishop,white,0=C1><queen,white,0=D1><king,white,0=E1><bishop,white,0=F1><knight,white,0=G1><rook,white,0=H1><pawn,white,0=A2><pawn,white,0=B2><pawn,white,0=C2><pawn,white,0=D2><pawn,white,0=E2><pawn,white,0=F2><pawn,white,0=G2><pawn,white,0=H2>";

			
		}
			
	}
	
	
	public void run() {
		
		if (selection == "Mill") {
		String input;
		String information;
		
		try {
			dos.writeUTF("<Gamemode=Mill>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		isPlaying = true;
		
		while(isPlaying) {
			try {
				input = dis.readUTF();
				information = StringConverter.getInformation(input);
				
				switch(StringConverter.getInformationType(input)){
				
				case START:
					if (information.equals("1")) {
						color = "white";
						myTurn = true;		
						setupGameBoard(defaultSetup);			
						JOptionPane.showMessageDialog(null, "Its your turn", "Your Turn", JOptionPane.INFORMATION_MESSAGE);	
						while(newState.equals(" ")) {
							newState = ((MillGui) board).getNewState();
						}					
							currentState = newState;	
							try {
								dos.writeUTF("<Gameboard=" + newState + ">");
							} catch (IOException e) {	}	
		
						newState = " ";
					}
					else {
						color = "black";
						myTurn = false;
						setupGameBoard(defaultSetup);
						JOptionPane.showMessageDialog(null, "You have to wait until the other player has done a move", "Wait", JOptionPane.INFORMATION_MESSAGE);
					}
					break;
					
				case GAMEBOARD:
					if (result.equals("loss")) {
						board.dispose();
						myTurn = false;
						setupGameBoard(information);
						JOptionPane.showMessageDialog(null, "you lost", "Loss", JOptionPane.INFORMATION_MESSAGE);
						isPlaying = false;
						break;
					}
					
					board.dispose();					
					myTurn = true;
					setupGameBoard(information);
					JOptionPane.showMessageDialog(null, "Its your turn", "Your Turn", JOptionPane.INFORMATION_MESSAGE);	
					oldState = information;
					
					while(newState == " ") {
						newState = ((MillGui) board).getNewState();
					}
					currentState = newState;	
					try {
						dos.writeUTF("<Gameboard=" + newState + ">");
					} catch (IOException e) {	}	
					
					newState = " ";
					break;
					
				case ERROR:
					board.dispose();
					myTurn = true;
					setupGameBoard(oldState);
					JOptionPane.showMessageDialog(null, information, "Error", JOptionPane.INFORMATION_MESSAGE);
					
					while(newState == " ") {
						newState = ((MillGui) board).getNewState();
					}
						try {
							dos.writeUTF("<Gameboard=" + newState + ">");
						} catch (IOException e) {	}	
						
					newState = " ";
					break;
				
				case SUCESS:
					oldState = currentState;
					tokencounter++;
					break;
					
				case WIN:
					result = "win";
					JOptionPane.showMessageDialog(null, "you won the game", "Win", JOptionPane.INFORMATION_MESSAGE);
					isPlaying = false;
					break;
				
				case LOSS:
					JOptionPane.showMessageDialog(null, "you lost the game", "Loss", JOptionPane.INFORMATION_MESSAGE);
					isPlaying = false;
					result = "loss";
					break;
					
				case REMOVE:
					board.dispose();
					myTurn = true;
					remove = true;
					setupGameBoard(currentState);
					JOptionPane.showMessageDialog(null, "you can remove a token", "Remove Token", JOptionPane.INFORMATION_MESSAGE);
					
					while(newState == " ") {
						newState = ((MillGui) board).getNewState();
					}

					currentState = newState;	
					try {
						dos.writeUTF("<Remove=" + newState + ">");
					} catch (IOException e) {	}	
					
					remove = false;
					newState = " ";
					break;
					
				default:
					break;			
			}
				
			} catch (Exception e) {
			}
				
		}
		} else if (selection == "Chess") {
			
			String input;
			String information;
			
			try {
				dos.writeUTF("<Gamemode=Chess>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			isPlaying = true;
			
			while(isPlaying) {
				try {
					input = dis.readUTF();
					information = StringConverter.getInformation(input);
					
					switch(StringConverter.getInformationType(input)){
					
					case START:
						if (information.equals("1")) {
							color = "white";
							myTurn = true;		
							setupGameBoard(defaultSetup);			
							JOptionPane.showMessageDialog(null, "Its your turn", "Your Turn", JOptionPane.INFORMATION_MESSAGE);	
							while(newState.equals(" ")) {
								newState = ((ChessBoard) board).getNewState();
							}					
							if (newState.equals("timeout")) {
								try {
									dos.writeUTF("<Timeout>");
								} catch (IOException e) {	}		
							} else {
								currentState = newState;	
								try {
									dos.writeUTF("<Gameboard=" + newState + ">");
								} catch (IOException e) {	}	
							}		
							newState = " ";
						}
						else {
							color = "black";
							myTurn = false;
							setupGameBoard(defaultSetup);
							JOptionPane.showMessageDialog(null, "You have to wait until the other player has done a move", "Wait", JOptionPane.INFORMATION_MESSAGE);
						}
						break;
						
					case GAMEBOARD:
						if (result.equals("loss")) {
							board.dispose();
							myTurn = false;
							setupGameBoard(information);
							JOptionPane.showMessageDialog(null, "you lost", "Loss", JOptionPane.INFORMATION_MESSAGE);
							isPlaying = false;
							break;
						}
						
						board.dispose();					
						myTurn = true;
						setupGameBoard(information);
						JOptionPane.showMessageDialog(null, "Its your turn", "Your Turn", JOptionPane.INFORMATION_MESSAGE);	
						oldState = information;
						while(newState == " ") {
							newState = ((ChessBoard) board).getNewState();
						}
						
						if (newState.equals("timeout")) {
							try {
								dos.writeUTF("<Timeout>");
							} catch (IOException e) {	}		
						} else {
							currentState = newState;	
							try {
								dos.writeUTF("<Gameboard=" + newState + ">");
							} catch (IOException e) {	}	
						}		
						newState = " ";
						break;
						
					case ERROR:
						board.dispose();
						myTurn = true;
						System.out.println("error  " + information);
						setupGameBoard(oldState);
						JOptionPane.showMessageDialog(null, information, "Error", JOptionPane.INFORMATION_MESSAGE); //TODO error meldung
						while(newState == " ") {
							newState = ((ChessBoard) board).getNewState();
						}
						
						if (newState.equals("timeout")) {
							try {
								dos.writeUTF("<Timeout>");
							} catch (IOException e) {	}		
						} else {
							try {
								dos.writeUTF("<Gameboard=" + newState + ">");
							} catch (IOException e) {	}	
						}	
						newState = " ";
						break;
					
					case SUCESS:
						oldState = currentState;
						break;
						
					case WIN:
						result = "win";
						JOptionPane.showMessageDialog(null, "you won the game", "Win", JOptionPane.INFORMATION_MESSAGE);
						isPlaying = false;
						break;
					
					case LOSS:
						JOptionPane.showMessageDialog(null, "you lost the game", "Loss", JOptionPane.INFORMATION_MESSAGE);
						isPlaying = false;
						result = "loss";
						break;
						
					case PROMOTION:
						String s = "null";
						
						String incomingDialog = JOptionPane.showInputDialog("0=Queen ; 1=Rook ; 2=Knight ; 3=Bishop");
						int choice = Integer.parseInt(incomingDialog);
						
						if (choice == 1)
							s = "rook";
						else if (choice == 2)
							s = "knight";
						else if (choice == 3) 
							s = "bishop";
						else
							s = "queen";
						
						try {
							dos.writeUTF("<Promotion=" + s + ">");
						} catch (IOException e) {	}		
										
						break;
						
					default:
						break;			
				}
					
				} catch (Exception e) {
				}
					
			}
		}
	}
	
	public void start()
	{
		runner = new Thread(this);
		runner.start();
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
				information = StringConverter.getInformation(input);
				/*switch(StringConverter.getInformationType(input)){
					case LOGIN:
						receiveData = true;
						break;				
					default:
						break;			
				}*/
			} catch (Exception e) {
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
	 * @param username String
	 * @param password String
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
	
	
	public void setupGameBoard(String s) {
		
		if (selection == "Mill") {
			if (color == null)
				board = new MillGui();
			else
				board = new MillGui(s, color, myTurn, remove, tokencounter);
		} else if (selection == "Chess") {
			if (color == null)
				board = new ChessBoard();
			else
				board = new ChessBoard(color, s, myTurn);
		}
		
		board.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		board.pack();
		board.setResizable(true);
		board.setLocationRelativeTo( null );
		board.setVisible(true);

		board.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					dos.writeUTF("<Connectionstatus=Exit>");
					isPlaying = false;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.getWindow().dispose();
			}
		});	
	}
	
	
	
	
	public abstract void updateBoard();
	
	public abstract void restoreGame();
	
	
	public void makeMove() {
		while(newState == " ") {
			newState = ((ChessBoard) board).getNewState();
		}
		
		try {
			dos.writeUTF("<Gameboard=" + newState + ">");
		} catch (IOException e) {	
		}

		
	}
}

