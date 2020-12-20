package src.client;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ChessBoard extends JFrame implements MouseListener, MouseMotionListener {
	
	protected JLayeredPane layeredPane;
	protected JPanel chessBoard;
	protected JLabel chessPiece;
	protected Dimension boardSize = new Dimension(670,670);
	
	protected Color dark = new Color(255,235,205);
	protected Color light = new Color(160,82,45);
	
	protected Figure[][] boardMatrix = new Figure[8][8];
	protected ArrayList<Figure> figuresList = new ArrayList<Figure>();
	
	protected int[] moveFrom = new int[2];
	protected int[] moveTo = new int[2];
	
	protected String color;
	protected String currentState;
	protected String oldState;
	protected String newState = " ";
	
	protected int x;
	protected int y;
	
	
	protected boolean myTurn;
	protected boolean movePerformed = false;
	
	
	private JLabel l;
	
	
	public ChessBoard(String color, String currentState, boolean myTurn) {	
		this.currentState = currentState;
		this.oldState = currentState;
		this.color = color;
		this.myTurn = myTurn;
		
		setupBoard();
		
		initialize(currentState);
	}
	
	public ChessBoard() {
		setupBoard();
	}
	
	public void initialize(String input) {
		
		for (int i = 0; i < input.length(); i++) {
			String name = "";
			String color = "";
			String hasMoved = "";
			char column = 'A';
			char row = '0';
			boolean beaten = false;
			
			if (input.charAt(i) == '<') {
				i++; // <
				while (input.charAt(i) != ',') {
					name += input.charAt(i);
					i++;
				}
				i++; // ,
				while (input.charAt(i) != ',') {
					color += input.charAt(i);
					i++;
				}
				i++; // hasMoved
				hasMoved += input.charAt(i);
				i++; // =
				i++; //n or column
				if (input.charAt(i) == 'n') {
					beaten = true;
					while (input.charAt(i) != '>') {
						i++;
					}
				} else {
					column = input.charAt(i);
					i++; // row
					row = input.charAt(i);
					i++; // >	
				}
			}
			ImageIcon icon = getImageIcon(name, color);
		
			Figure f = new Figure(name, color, hasMoved, column, row, beaten, icon, this.color);
			figuresList.add(f);
		}
		
		updateBoard();
				
		for (int i = 0; i < figuresList.size(); i++) {
			if (figuresList.get(i).getBeaten() == false) {
				JPanel panel = (JPanel)chessBoard.getComponent(figuresList.get(i).getPosition());
				panel.add(figuresList.get(i).getJLabel());
			}
		}
	}
	
	
public ImageIcon getImageIcon(String name, String color) {
		
		ImageIcon icon = null;
		
		if (color.equals("black")) {
			
			switch (name) {
			
			case "pawn":
				icon = new ImageIcon("resources/chess/black_pawn.png");
				break;
			case "king":
				icon = new ImageIcon("resources/chess/black_king.png");
				break;
			case "queen":
				icon = new ImageIcon("resources/chess/black_queen.png");
				break;
			case "knight":
				icon = new ImageIcon("resources/chess/black_knight.png");
				break;
			case "rook":
				icon = new ImageIcon("resources/chess/black_rook.png");
				break;
			case "bishop":
				icon = new ImageIcon("resources/chess/black_bishop.png");
				break;
			}
		} else {
			switch (name) {		
			case "pawn":
				icon = new ImageIcon("resources/chess/white_pawn.png");
				break;
			case "king":
				icon = new ImageIcon("resources/chess/white_king.png");
				break;
			case "queen":
				icon = new ImageIcon("resources/chess/white_queen.png");
				break;
			case "knight":
				icon = new ImageIcon("resources/chess/white_knight.png");
				break;
			case "rook":
				icon = new ImageIcon("resources/chess/white_rook.png");
				break;
			case "bishop":
				icon = new ImageIcon("resources/chess/white_bishop.png");
				break;
			}		
		}	
		return icon;
	}

	
	
	public void createString() {
		String s;
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < figuresList.size(); i++) {
			Figure f = figuresList.get(i);
			if (f.getBeaten() == true)
				s = "<" + f.name + "," + f.color + "," + f.hasMoved + "=" + "null" + ">";
			else
				s = "<" + f.name + "," + f.color + "," + f.hasMoved + "=" + f.column + f.row + ">";
			builder.append(s);
		}
		String helper = builder.toString();
		
		if(helper.equals(oldState))
			movePerformed = false;
		else
			newState =  builder.toString();
	}
	
	
	public String getNewState() {
		return this.newState;
	}

	
	public void setupBoard() {
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane, BorderLayout.NORTH);
		layeredPane.setPreferredSize(boardSize);

		
		chessBoard = new JPanel();
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout(new GridLayout(8,8));
		chessBoard.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		chessBoard.setPreferredSize(boardSize);
		chessBoard.setBounds(35, 35, 600, 600);
		chessBoard.addMouseListener(this);
		chessBoard.addMouseMotionListener(this);
		
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				JPanel square = new JPanel(new BorderLayout());
				square.setBorder(new LineBorder(new Color(0, 0, 0), 1));
				chessBoard.add(square); //TODO
				
				if (i%2 == 0) {
					if (j%2 == 0)
						square.setBackground(dark);
					else
						square.setBackground(light);
				} else {
					if (j%2 == 0)
						square.setBackground(light);
					else
						square.setBackground(dark);
				}
			}	
		}
		setUpFrameLetters(35,640,75,35);
		setUpFrameLetters(35,5,75,35);		  
		  
		setUpFrameNumbers(635,35,35,75);
		setUpFrameNumbers(0, 35, 35, 75);
	}
	
	public void setUpFrameNumbers(int x, int y, int w, int h) {
		String[] lettersW = {"A", "B", "C", "D", "E", "F", "G", "H"};
		String[] lettersB = {"H", "G", "F", "E", "D", "C", "B", "A"};
		String[] tmp;
		
		if (this.color.equals("white"))
			tmp = lettersW;
		else
			tmp = lettersB;
		
		for (int i = 7; i >= 0; i--) {
			l = new JLabel(tmp[i]);
			l.setHorizontalTextPosition(SwingConstants.CENTER);
			l.setHorizontalAlignment(SwingConstants.CENTER);
			l.setFont(new Font("C059", Font.PLAIN, 25));
			l.setBounds(x, y, w, h);
			layeredPane.add(l);
			y += 75;
		  }			
	}
	
	public void setUpFrameLetters(int x, int y, int w, int h) {
		String[] numbersW = {"1", "2", "3", "4", "5", "6", "7", "8"};
		String[] numbersB = {"8", "7", "6", "5", "4", "3", "2", "1"};
		String[] tmp;
		
		if (this.color.equals("white"))
			tmp = numbersW;
		else
			tmp = numbersB;
		
		for (int i = 0; i < 8; i++) {
			l = new JLabel(tmp[i]);
			l.setHorizontalTextPosition(SwingConstants.CENTER);
			l.setHorizontalAlignment(SwingConstants.CENTER);
			l.setFont(new Font("C059", Font.PLAIN, 25));
			l.setBounds(x, y, w, h);
			layeredPane.add(l);
			x += 75;
		  }			
	}
	
	
	public void printFigures() {	
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if (boardMatrix[i][j] instanceof Figure)
					System.out.println(boardMatrix[i][j].name + " " + boardMatrix[i][j].column + " " +  boardMatrix[i][j].row);
			}
		}
	}

	
	public void updateBoard() {
		this.boardMatrix = new Figure[8][8];
		for (int i = 0; i < figuresList.size(); i++) {
			if (figuresList.get(i).getBeaten() == false) {
				int x = figuresList.get(i).getX();
				int y = figuresList.get(i).getY();
				boardMatrix[x][y] = figuresList.get(i);
			}
		}
	}
	
	
	@Override
	public void mouseDragged(MouseEvent me) {
		if (chessPiece == null) 
			return;
		chessPiece.setLocation(me.getX() + x, me.getY() + y);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		chessPiece = null;
		Component c = chessBoard.findComponentAt(me.getX(), me.getY());
		
		if (c instanceof JPanel)
			return;
				
		int row = me.getY() / c.getWidth();
		int col = me.getX() / c.getHeight();
		
		moveFrom[0] = row;
		moveFrom[1] = col;
		
		Figure f = boardMatrix[col][row];
		if (f.color.equals(this.color) == false)
			return;
		
		if (myTurn == false) //TODO brauch ich beide if Abfragen?
			return;
		
		if (movePerformed == true) //TODO
			return;
		
//		moveFrom[0] = row;
//		moveFrom[1] = col;
			
		Point parentLocation = c.getParent().getLocation();
		x = parentLocation.x - me.getX() +35;
		y = parentLocation.y - me.getY() +35; //+35 wegen frame
	
		chessPiece = (JLabel)c;
		
		chessPiece.setLocation(me.getX() + x, me.getY() + y);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		
		if(chessPiece == null) return;
		  
		chessPiece.setVisible(false);
		Component c =  chessBoard.findComponentAt(me.getX(), me.getY());

		int row = me.getY() / c.getWidth();
		int col = me.getX() / c.getHeight();
		
		moveTo[0] = row; //TODO evtl löschen
		moveTo[1] = col; //TODO evtl löschen
		 
		if (c instanceof JLabel){
			Container parent = c.getParent();
			parent.remove(0);
			parent.add( chessPiece );
			
			Figure f = boardMatrix[moveFrom[1]][moveFrom[0]];
			Figure f2 = boardMatrix[moveTo[1]][moveTo[0]];
			f2.setBeaten(true);
			f.setColumn(moveTo[1]);
			f.setRow(moveTo[0]);
			updateBoard(); //TODO brauch ich das?
		} else {
			Container parent = (Container)c;
			parent.add( chessPiece );
			
			Figure f = boardMatrix[moveFrom[1]][moveFrom[0]];
			f.setColumn(moveTo[1]);
			f.setRow(moveTo[0]);
			updateBoard(); //TODO brauch ich das?
		} 
		chessPiece.setVisible(true);
		movePerformed = true;
		createString();
	}
}
