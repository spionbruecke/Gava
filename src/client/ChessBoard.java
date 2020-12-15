package src.client;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ChessBoard extends JFrame implements MouseListener, MouseMotionListener {
	
	protected JLayeredPane layeredPane;
	protected JPanel chessBoard;
	protected JLabel chessPiece;
	protected Dimension boardSize = new Dimension(600,600);
	
	protected Color dark = new Color(255,235,205);
	protected Color light = new Color(160,82,45);
	
	protected Figure[][] boardMatrix = new Figure[8][8];
	protected ArrayList<Figure> figuresList;
	
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
	
	ImageIcon black_king = new ImageIcon("resources/chess/black_king.png");
	ImageIcon black_queen = new ImageIcon("resources/chess/black_queen.png");
	ImageIcon black_knight_1 = new ImageIcon("resources/chess/black_knight.png");
	ImageIcon black_knight_2 = new ImageIcon("resources/chess/black_knight.png");
	ImageIcon black_rook_1 = new ImageIcon("resources/chess/black_rook.png");
	ImageIcon black_rook_2 = new ImageIcon("resources/chess/black_rook.png");
	ImageIcon black_bishop_1 = new ImageIcon("resources/chess/black_bishop.png");
	ImageIcon black_bishop_2 = new ImageIcon("resources/chess/black_bishop.png");
	ImageIcon black_pawn_1 = new ImageIcon("resources/chess/black_pawn.png");
	ImageIcon black_pawn_2 = new ImageIcon("resources/chess/black_pawn.png");
	ImageIcon black_pawn_3 = new ImageIcon("resources/chess/black_pawn.png");
	ImageIcon black_pawn_4 = new ImageIcon("resources/chess/black_pawn.png");
	ImageIcon black_pawn_5 = new ImageIcon("resources/chess/black_pawn.png");
	ImageIcon black_pawn_6 = new ImageIcon("resources/chess/black_pawn.png");
	ImageIcon black_pawn_7 = new ImageIcon("resources/chess/black_pawn.png");
	ImageIcon black_pawn_8 = new ImageIcon("resources/chess/black_pawn.png");
	
	ImageIcon white_king = new ImageIcon("resources/chess/white_king.png");
	ImageIcon white_queen = new ImageIcon("resources/chess/white_queen.png");
	ImageIcon white_knight_1 = new ImageIcon("resources/chess/white_knight.png");
	ImageIcon white_knight_2 = new ImageIcon("resources/chess/white_knight.png");
	ImageIcon white_rook_1 = new ImageIcon("resources/chess/white_rook.png");
	ImageIcon white_rook_2 = new ImageIcon("resources/chess/white_rook.png");
	ImageIcon white_bishop_1 = new ImageIcon("resources/chess/white_bishop.png");
	ImageIcon white_bishop_2 = new ImageIcon("resources/chess/white_bishop.png");
	ImageIcon white_pawn_1 = new ImageIcon("resources/chess/white_pawn.png");
	ImageIcon white_pawn_2 = new ImageIcon("resources/chess/white_pawn.png");
	ImageIcon white_pawn_3 = new ImageIcon("resources/chess/white_pawn.png");
	ImageIcon white_pawn_4 = new ImageIcon("resources/chess/white_pawn.png");
	ImageIcon white_pawn_5 = new ImageIcon("resources/chess/white_pawn.png");
	ImageIcon white_pawn_6 = new ImageIcon("resources/chess/white_pawn.png");
	ImageIcon white_pawn_7 = new ImageIcon("resources/chess/white_pawn.png");
	ImageIcon white_pawn_8 = new ImageIcon("resources/chess/white_pawn.png");
	
		
	public ChessBoard(String color, String currentState, boolean myTurn) {
		this.currentState = currentState;
		this.oldState = currentState;
		
		this.color = color;
		
		this.myTurn = myTurn;
		
		setupBoard();
		
		initializeBoard();

		updateBoardGUI(this.currentState);
		
	}
	
	
	
	public void updateBoardGUI(String s) {
		int count = 0;
		char row;
		char column;
		
		for (int i = 0; i < s.length() - 2; i++) {
			while(s.charAt(i) != '=') { 
				i++;
			}
			i++;
			
			Figure f = figuresList.get(count);
						
			if (s.charAt(i) == 'n')
				f.setBeaten(true);
			else {
				column = s.charAt(i);
				i++;
				row = s.charAt(i);
			
				f.column = column;
				f.row = row;
			}
			count++;
		}
		
		updateBoard();
		
		for (int i = 0; i < figuresList.size(); i++) {
			if (figuresList.get(i).getBeaten() == false) {
				JPanel panel = (JPanel)chessBoard.getComponent(figuresList.get(i).getPosition());
				panel.add(figuresList.get(i).getJLabel());
			}
		}	
	}
	
	
	
	public void createString() {
		String s;
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < figuresList.size(); i++) {
			Figure f = figuresList.get(i);
			if (f.getBeaten() == true)
				s = "<" + f.name + "," + f.color + "=" + "null" + ">";
			else
				s = "<" + f.name + "," + f.color + "=" + f.column + f.row + ">";
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
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);
		
		chessBoard = new JPanel();
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout(new GridLayout(8,8));
		chessBoard.setPreferredSize(boardSize);
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				JPanel square = new JPanel(new BorderLayout());
				chessBoard.add(square);
				
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
	}
	
	
	public void initializeBoard() {
		Figure f0 = new Figure(black_rook_1, 'A', '8', "rook", "black", true);
		Figure f1 = new Figure(black_knight_1, 'B', '8', "knight", "black", true);
		Figure f2 = new Figure(black_bishop_1, 'C', '8', "bishop", "black", true);
		Figure f3 = new Figure(black_queen, 'D', '8', "queen", "black", true);
		Figure f4 = new Figure(black_king, 'E', '8', "king", "black", true);
		Figure f5 = new Figure(black_bishop_2, 'F', '8', "bishop", "black", true);
		Figure f6 = new Figure(black_knight_2, 'G', '8', "knight", "black", true);
		Figure f7 = new Figure(black_rook_2, 'H', '8', "rook", "black", true);
		Figure f8 = new Figure(black_pawn_1, 'A', '7', "pawn", "black", true);
		Figure f9 = new Figure(black_pawn_2, 'B', '7', "pawn", "black", true);
		Figure f10 = new Figure(black_pawn_3, 'C', '7', "pawn", "black", true);
		Figure f11 = new Figure(black_pawn_4, 'D', '7', "pawn", "black", true);
		Figure f12 = new Figure(black_pawn_5, 'E', '7', "pawn", "black", true);
		Figure f13 = new Figure(black_pawn_6, 'F', '7', "pawn", "black", true);
		Figure f14 = new Figure(black_pawn_7, 'G', '7', "pawn", "black", true);
		Figure f15 = new Figure(black_pawn_8, 'H', '7', "pawn", "black", true);
		Figure f16 = new Figure(white_rook_1, 'A', '1', "rook", "white", true);
		Figure f17 = new Figure(white_knight_1, 'B', '1', "knight", "white", true);
		Figure f18 = new Figure(white_bishop_1, 'C', '1', "bishop", "white", true);
		Figure f19 = new Figure(white_queen, 'D', '1', "queen", "white", true);
		Figure f20 = new Figure(white_king, 'E', '1', "king", "white", true);
		Figure f21 = new Figure(white_bishop_2, 'F', '1', "bishop", "white", true);
		Figure f22 = new Figure(white_knight_2, 'G', '1', "knight", "white", true);
		Figure f23 = new Figure(white_rook_2, 'H', '1', "rook", "white", true);
		Figure f24 = new Figure(white_pawn_1, 'A', '2', "pawn", "white", true);
		Figure f25 = new Figure(white_pawn_2, 'B', '2', "pawn", "white", true);
		Figure f26 = new Figure(white_pawn_3, 'C', '2', "pawn", "white", true);
		Figure f27 = new Figure(white_pawn_4, 'D', '2', "pawn", "white", true);
		Figure f28 = new Figure(white_pawn_5, 'E', '2', "pawn", "white", true);
		Figure f29 = new Figure(white_pawn_6, 'F', '2', "pawn", "white", true);
		Figure f30 = new Figure(white_pawn_7, 'G', '2', "pawn", "white", true);
		Figure f31 = new Figure(white_pawn_8, 'H', '2', "pawn", "white", true);
		
		this.figuresList = new ArrayList<Figure>() {
			{
				add(f0);
				add(f1);
				add(f2);
				add(f3);
				add(f4);
				add(f5);
				add(f6);
				add(f7);
				add(f8);
				add(f9);
				add(f10);
				add(f11);
				add(f12);
				add(f13);
				add(f14);
				add(f15);
				add(f16);
				add(f17);
				add(f18);
				add(f19);
				add(f20);
				add(f21);
				add(f22);
				add(f23);
				add(f24);
				add(f25);
				add(f26);
				add(f27);
				add(f28);
				add(f29);
				add(f30);
				add(f31);			
			}
		};
		
//		for (int i = 0; i < figuresList.size(); i++) {
//			if (figuresList.get(i).getBeaten() == false) {
//				JPanel panel = (JPanel)chessBoard.getComponent(figuresList.get(i).getPosition());
//				panel.add(figuresList.get(i).getJLabel());
//			}
//		}	
//		updateBoard();
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
		
		if (myTurn == false)
			return;
		
		if (movePerformed == true)
			return;
		
		moveFrom[0] = row;
		moveFrom[1] = col;
			
		Point parentLocation = c.getParent().getLocation();
		x = parentLocation.x - me.getX();
		y = parentLocation.y - me.getY();
	
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
		
		moveTo[0] = row;
		moveTo[1] = col;
		 
		if (c instanceof JLabel){
			Container parent = c.getParent();
			parent.remove(0);
			parent.add( chessPiece );
			
			Figure f = boardMatrix[moveFrom[1]][moveFrom[0]];
			Figure f2 = boardMatrix[moveTo[1]][moveTo[0]];
			f2.setBeaten(true);
			f.setColumn(moveTo[1]);
			f.setRow(moveTo[0]);
			updateBoard();		
		} else {
			Container parent = (Container)c;
			parent.add( chessPiece );
			
			Figure f = boardMatrix[moveFrom[1]][moveFrom[0]];
			f.setColumn(moveTo[1]);
			f.setRow(moveTo[0]);
			updateBoard();
		} 
		chessPiece.setVisible(true);
		movePerformed = true;
		createString();
	}
}
