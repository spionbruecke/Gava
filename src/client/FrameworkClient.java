package src.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;

public abstract class FrameworkClient extends JFrame {
	
	private Socket connection = null;
	protected DataInputStream dis = null;
	protected DataOutputStream dos = null;
	protected Boolean isStillPlaying = true;
	
	private String playerName = null;
	private String currentGame = null;
	private boolean isLoggedIn = false;
	
	protected JFrame menueFrame = new JFrame();
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
	

	public void connect(String host, int port) {
		
		try {
			connection = new Socket(host, port);
			dis = new DataInputStream(connection.getInputStream()); // get data from sever
			dos = new DataOutputStream(connection.getOutputStream()); // get data to server
			run();
		} catch (IOException ieo) {
			JOptionPane.showMessageDialog(null, "Server is not running", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private String getUserInput() {
		Scanner scn = new Scanner(System.in);
		String text = scn.nextLine();
		scn.close();
		
		return text;
	}
	
	private String getServerOutput() {
		String text = "";
		
		try {
			text = dis.readUTF();
		} catch (IOException e) {
			isStillPlaying = false;
		}
		System.out.println(text);
		return text;
	}
	
	public void run() {
		String incommingData;
		String outcommingData;
		
		setupGUI();
		
			
		while(isStillPlaying) {
			
			incommingData = getServerOutput();
			
			if (incommingData.equals("1. Schach")) {
				outcommingData = getUserInput();
				try {
					dos.writeUTF(outcommingData);
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		}
	}
	
	
	public void sendDataToServer() {
		
	}
	
	public void getDataFromServer() {
		
	}
	
	public boolean login(String username, String password) {
		// TODO
		// check if username and password are correct
		
		return true;
		
	}
	
	public void createAccount(String username, String password) {
		// TODO
		// create new account	
	}
	
	public void setupGUI() {	
		/*
		 * JTextField usernameTextField; JPasswordField passwordField; JLabel
		 * currentUserLabel; JLabel usernameLabel; JLabel passwordLabel; JButton
		 * loginButton; JButton createAccountButton; JLabel createAccountLabel; JLabel
		 * selectGameLabel; JButton playChessButton; JButton playMillButton; JLabel
		 * restoreGameLabel; JButton restoreGameButton;
		 */
		
		menueFrame = new JFrame();
		
		menueFrame.getContentPane().setEnabled(false);
		menueFrame.setBounds(100, 100, 1000, 680);
		menueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menueFrame.getContentPane().setLayout(null);	
		
		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		usernameLabel.setBounds(58, 80, 117, 35);
		menueFrame.getContentPane().add(usernameLabel);
		
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		passwordLabel.setBounds(357, 77, 122, 41);
		menueFrame.getContentPane().add(passwordLabel);
		
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(159, 88, 169, 19);
		menueFrame.getContentPane().add(usernameTextField);
		usernameTextField.setColumns(10);
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(447, 88, 169, 19);
		menueFrame.getContentPane().add(passwordField);
		
		
		currentUserLabel = new JLabel("You are currently logged in as an anonymous Player");
		currentUserLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		currentUserLabel.setBounds(12, 12, 580, 33);
		menueFrame.getContentPane().add(currentUserLabel);
		
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String loginName = usernameTextField.getText();
				String userPassword = passwordField.getText();
				
				Boolean validUser = login(loginName, userPassword);
				
				if(validUser) {
					JOptionPane.showMessageDialog(menueFrame, "your login was successful");
					currentUserLabel.setText("You are currently logged in as " + loginName);
					isLoggedIn = true;
					updateSetupGUI();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
					passwordField.setText(null);
					usernameTextField.setText(null);
				}
			}
		});	
		
		loginButton.setBounds(277, 130, 117, 25);
		menueFrame.getContentPane().add(loginButton);
		
		
		separator_2 = new JSeparator();
		separator_2.setBounds(12, 57, 604, 11);
		menueFrame.getContentPane().add(separator_2);
		
		
		createAccountButton = new JButton("Create Account");
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String newUser = JOptionPane.showInputDialog("Enter your user name:");
				String newPassword1 = JOptionPane.showInputDialog("Enter your password:");
				String newPassword2 = JOptionPane.showInputDialog("Enter your password again");
				
				if (newPassword1.contentEquals(newPassword2)) {
					createAccount(newUser, newPassword1);
				} else {
					JOptionPane.showMessageDialog(menueFrame, "Your passwords do not match");
				}
			}
		});
		createAccountButton.setBounds(406, 587, 186, 25);
		menueFrame.getContentPane().add(createAccountButton);
		
		
		separator_1 = new JSeparator();
		separator_1.setBounds(12, 518, 965, 11);
		menueFrame.getContentPane().add(separator_1);
		
		
		createAccountLabel = new JLabel("if you dont have an account yet, feel free to create one, to save your current Game");
		createAccountLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		createAccountLabel.setBounds(203, 549, 685, 15);
		menueFrame.getContentPane().add(createAccountLabel);
		
		
		selectGameLabel = new JLabel("You can play one of the following games");
		selectGameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		selectGameLabel.setBounds(12, 242, 348, 15);
		menueFrame.getContentPane().add(selectGameLabel);
		
		
		playChessButton = new JButton("Play Chess");
		playChessButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentGame = "Chess";
				// TODO start game
			}
		});
		playChessButton.setBounds(413, 232, 117, 41);
		menueFrame.getContentPane().add(playChessButton);
		
		
		playMillButton = new JButton("Play Mill");
		playMillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentGame = "Mill";
				// TODO start game
			}
		});
		playMillButton.setBounds(646, 229, 117, 44);
		menueFrame.getContentPane().add(playMillButton);
		
		
		restoreGameLabel = new JLabel("Or you can restore your latest game");
		restoreGameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		restoreGameLabel.setBounds(12, 350, 316, 15);
		menueFrame.getContentPane().add(restoreGameLabel);
		
		
		restoreGameButton = new JButton("Restore Game");
		restoreGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO restore latest game from database
			}
		});
		restoreGameButton.setBounds(413, 337, 155, 41);
		menueFrame.getContentPane().add(restoreGameButton);
	
		updateSetupGUI();
		menueFrame.setVisible(true);
	}
	
	
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


	public abstract void setupGameBoard();
	
	public abstract void updateBoard();
	
	public abstract void selectGame();
	
	public abstract void restoreGame();
	
	public abstract void makeMove();
	
	public abstract void undoMove();
}
