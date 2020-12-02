
package src.client;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Client extends FrameworkClient{
	
	private JPanel boardPanel = new JPanel();
	private JButton[][] boardButtons = new JButton[8][8];

	
	public Client(String host, int port) {
		connect(host, port);
	}



	@Override
	public void setupGameBoard() {

		
	}

	@Override
	public void updateBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undoMove() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
