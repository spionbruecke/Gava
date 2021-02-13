package src.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MillGui extends JFrame{
	
	protected Dimension boardSize = new Dimension(810,810);
	protected JLabel l;
	protected Field f;
	
	protected ArrayList<JLabel> labelList = new ArrayList<JLabel>();
	protected ArrayList<Field> fieldList = new ArrayList<Field>();
	protected ArrayList<YourMouseListener> msList = new ArrayList<YourMouseListener>();
	
	protected ImageIcon blue = new ImageIcon("resources/mill/black.png");
	protected ImageIcon orange = new ImageIcon("resources/mill/white.png");
	
	protected String currentState;
	protected String color;
	
	protected boolean movePerformed = false;
	protected String newState = " ";
	protected boolean myTurn;
	protected boolean remove;
	protected boolean newTokenSet = false;
	
	protected int tokencounter;
	
	protected boolean fly = false;
	

	public MillGui (String currentState, String color, boolean myTurn, boolean remove, int counter) {
		this.currentState = currentState;
		this.color = color;
		this.myTurn = myTurn;
		this.remove = remove;
		this.tokencounter = counter;
		
		if (counter > 8)
			fly = true;
		
		this.setPreferredSize(boardSize);
		this.getContentPane().setLayout(null);
		
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("resources/mill/spielbrett.png")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setupFields();
		
		setupLabels();
		
		parseInput();
	}
	
	public MillGui () {
		this.setPreferredSize(boardSize);
		this.getContentPane().setLayout(null);
		
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("resources/mill/spielbrett.png")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String getNewState() {
		return this.newState;
	}	
	
	
	
	public void setupBoard(char row, char column, String color, String moved) {
		int x;
		YourMouseListener ms;
			
		x = locationToIndex(row,  column);
		JLabel l = labelList.get(x);
		if (color.equals("white"))
			l.setIcon(orange);
		else
			l.setIcon(blue);
		
		ms = msList.get(x);
		ms.color = color;
		ms.column = column;
		ms.row = row;
		ms.moved = moved;
		ms.iconset = true;
	}
	
	public void createString() {
		String s = "";
		StringBuilder builder = new StringBuilder();
		String output;
		String[] blackA = new String[9];
		String[] whiteA = new String[9];
		
		int b = 0;
		int w = 0;
		
		for (int i = 0; i < msList.size(); i++) {
			
			YourMouseListener ms = msList.get(i);
			if (ms.color == "white") {
				s = "<" + "token" + "," + ms.color + "," + ms.moved + "=" + ms.row + ms.column + ">";
				whiteA[w] = s;
				w++;
			} else if (ms.color == "black") {
				s = "<" + "token" + "," + ms.color + "," + ms.moved + "=" + ms.row + ms.column + ">";
				blackA[b] = s;
				b++;
			}
		}
		
		for (int j = w; j < whiteA.length; j++) {
			whiteA[j] = "<" + "token" + "," + "white" + "," + "0" + "=" + "null" + ">";
		}
		
		for (int j = b; j < blackA.length; j++) {
			blackA[j] = "<" + "token" + "," + "black" + "," + "0" + "=" + "null" + ">";
		}

		String black = "";
		String white = "";
		
		for (int i = 0; i < whiteA.length; i++) {
			white+= whiteA[i];
		}
		
		for (int i = 0; i < blackA.length; i++) {
			black+= blackA[i];
		}
		
		newState = white + black;
	}
	
	
	public void parseInput() {
		String name = "token";
		String color = null;
		String hasMoved = null;
		char row = '0';
		char column = '0';
		
		for (int i = 0; i < currentState.length(); i++) {
			if (currentState.charAt(i) == '<') {
				i++; // <
				
				while (currentState.charAt(i) != ',') {
					i++;
				}
				i++;

				if (currentState.charAt(i) == 'w')
					color = "white";
				else
					color = "black";
				
				while (currentState.charAt(i) != ',') {
					i++;
				}
				i++;
				
				if (currentState.charAt(i) == '1') 
					hasMoved = "1";
				else
					hasMoved = "0";
		
				i++; // =
				i++; 
				
				if (currentState.charAt(i) == 'n') {
					row = '0';
					column = '0';
				} else {
					row = currentState.charAt(i);
					i++;
					column = currentState.charAt(i);					
				}
				while (currentState.charAt(i) != '>') {
					i++;
				}
			}
			if (row != '0' && column != '0')
				setupBoard(row, column, color, hasMoved);
		}
	}
	
	
	public int locationToIndex(char row, char col) {
		
		int index = 0;
		int p1 = 0; //row
		int p2 = 0; //col
		int p3 = 0; // if col == 1 2 3 
		
		switch (row) {
		case 'A': 
			p1 = 1;
			break;
		case 'B':
			p1 = 2;
			break;
		case 'C':
			p1 = 3;
			break;
		case 'D':
			p1 = 4;
			break;
		case 'E':
			p1 = 5;
			break;
		case 'F':
			p1 = 6;
			break;
		}
		
		switch (col) {
		case '7':
			p2 = 0;
			break;
		case '6':
			p2 = 1;
			break;
		case '5':
			p2 = 2;
			break;
		case '4':
			p2 = 3;
			break;
		case '3':
			p2 = 4;
			p3 = 3;
			break;
		case '2':
			p2 = 5;
			p3 = 3;
			break;
		case '1':
			p2 = 6;
			p3 = 3;
			break;
		}
		
		
		return (p1 + (p2*3) + p3) - 1;
		
	}
	
	public void setupFields() {
		char[] r1 = new char[] {'A', 'B', 'C', 'D', 'E', 'F'};
		char[] r2 = new char[] {'A', 'B', 'C'};
		char[] c = new char[] {'7', '6', '5', '4', '3', '2', '1'};
		
		for (int i = 0; i < c.length; i++) {
			if (i == 3) {
				for (int j = 0; j < r1.length; j++) {
					f = new Field();
					f.row = r1[j];
					f.column = c[i];
					fieldList.add(f);
				}
			} else {
				for (int j = 0; j < r2.length; j++) {
					f = new Field();
					f.row = r2[j];
					f.column = c[i];
					fieldList.add(f);
				}
			}
		}
	}
	
	
	public class Field {
		String color;
		char row;
		char column;
		
		public String getLocation() {
			return String.valueOf(row) + String.valueOf(column);	
		}
	}
	
	
	public void setupLabels() {
		int x = 55;
		
		l = new JLabel();
		l.setBounds(10, 1, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(375, 1, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(740, 1, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(135, 120, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(375, 120, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(615, 120, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(255, 240, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(375, 240, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(495, 240, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(10, 365, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(135, 365, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(255, 365, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
			
		l = new JLabel();
		l.setBounds(495, 365, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(615, 365, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(740, 365, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(255, 485, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(375, 485, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(495, 485, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(135, 605, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(375, 605, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(615, 605, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(10, 725, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(375, 725, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		l = new JLabel();
		l.setBounds(740, 725, x, x);
		this.getContentPane().add(l);
		labelList.add(l);
		
		
		
		for (int i = 0; i < labelList.size(); i++) {
			l = labelList.get(i);
			f = fieldList.get(i);
			YourMouseListener ms = new YourMouseListener(l, null, color, f.row, f.column);
			msList.add(ms);
			l.addMouseListener(ms);
		}
	}
	
	class YourMouseListener extends MouseAdapter {
	    JLabel label;
	    String color = null;
	    char row;
	    char column;
	    String moved;
	    boolean iconset = false;
	    String playercolor;
	    
	    YourMouseListener(JLabel label, String color, String playercolor, char row, char column) {
	        this.label = label;
	        this.color = color;
	        this.playercolor = playercolor;
	        this.row = row;
	        this.column = column;
	    }
	    

	    public void mouseClicked(MouseEvent entered) {
	    	
	    	if (fly == true && movePerformed == false && myTurn == true) {
	    		if (iconset == false)
	    			return;
	    	}
	    	
	    	if (fly == true && movePerformed == false && myTurn == true) {
	    		if (color.equals(playercolor)) {
	    			label.setIcon(null);
	    			remove = false;
	    			movePerformed = true;
	    			this.iconset = false;
	    			this.color = null;
	    			
	    			movePerformed = true;
	    			newTokenSet = true;
	    			
	    			return;
	    		}
	    	}
	    	if (newTokenSet == true) {
	    		if (iconset == false) {
	    			if (playercolor == "black")
	    				label.setIcon(blue);
	    			else
	    				label.setIcon(orange);
	    		
	    			iconset = true;
	    			this.color = playercolor;
	    			this.moved = "1";
	    			movePerformed = true;
	    			newTokenSet = false;
	    			createString();
	    		}
	    		return;
	    	}
	    		    	
	    	if (remove == true && iconset == true && movePerformed == false && myTurn == true) {
	    		if (color.equals(playercolor) == false && color != null) {
	    			label.setIcon(null);
	    			remove = false;
	    			movePerformed = true;
	    			this.iconset = false;
	    			this.color = null;
	    			createString();
	    		}
	    	}
	    	if (movePerformed == false && myTurn == true && fly == false) {
	    		setToken();
	    	}
	    }
	    
	    public void setToken() {
	    	if (iconset == false) {
	    		if (playercolor == "black")
	    			label.setIcon(blue);
	    		else
	    			label.setIcon(orange);
	    		
	    		iconset = true;
	    		color = playercolor;
	    		moved = "1";
	    		movePerformed = true;
	    		createString();
	    	}
	    }
	}	

}

