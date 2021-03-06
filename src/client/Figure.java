package src.client;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Figure contains all the information for chess figures
 *
 * @author Tobias Mitterreiter
 */

public class Figure {
	
	protected ImageIcon icon;
	protected char row;
	protected char column;
	protected String name;
	protected String color;
	protected Boolean visible = true;
	protected Boolean beaten = false;
	protected int x;
	protected int y;
	protected String hasMoved;
	
	protected JLabel piece;
	
	String playerColor;
	
	
	public Figure(String name, String color, String hasMoved, char column, char row, boolean beaten, ImageIcon icon, String playerColor) {
		this.name = name;
		this.color = color;
		this.hasMoved = hasMoved;
		this.column = column;
		this.row = row;
		this.beaten = beaten;
		this.icon = icon;
		this.piece = new JLabel(this.icon);
		this.playerColor = playerColor;
	}
	
	public void setBeaten(Boolean b) {
		this.beaten = b;
	}
	
	public ImageIcon getIcon() {
		return this.icon;
	}
	
	public Boolean getBeaten() {
		return this.beaten;
	}
	
	public JLabel getJLabel() {
		return this.piece;
	}
	
	public int getX() {
		int x = convertColumn();
		return x;
	}
	
	public int getY() {
		int y = convertRow();
		return y;
	}
	
	
	public int convertRow() {
		if (playerColor.equals("white")) {
			switch(row){
			case '8':
				return 0;
			case '7':
				return 1;
			case '6':
				return 2;
			case '5':
				return 3;
			case '4':
				return 4;
			case '3':
				return 5;
			case '2':
				return 6;
			case '1':
				return 7;

			default:
				return -1;
			}
		} else {
			switch(row){
			case '8':
				return 7;
			case '7':
				return 6;
			case '6':
				return 5;
			case '5':
				return 4;
			case '4':
				return 3;
			case '3':
				return 2;
			case '2':
				return 1;
			case '1':
				return 0;

			default:
				return -1;
			}	
		}
		
	}
	
	public int convertColumn() {
		
		if (playerColor.equals("white")) {
		
			switch(column){
			case 'A':
				return 0;
			case 'B':
				return 1;
			case 'C':
				return 2;
			case 'D':
				return 3;
			case 'E':
				return 4;
			case 'F':
				return 5;
			case 'G':
				return 6;
			case 'H':
				return 7;

			default:
				return -1;
			}
        } else {
			switch(column){
			case 'A':
				return 7;
			case 'B':
				return 6;
			case 'C':
				return 5;
			case 'D':
				return 4;
			case 'E':
				return 3;
			case 'F':
				return 2;
			case 'G':
				return 1;
			case 'H':
				return 0;

			default:
				return -1;
			}
        }
	}
	
	public int getPosition() {
		int position;
		
		int x = convertColumn();
		int y = convertRow();
		

		position = y * 8 + x;
		
		return position;
	}
	
	public void setRow(int x) {
		if (playerColor.equals("white")) {
			switch(x) {
			case 0:
				this.row = '8';
				break;
			case 1:
				this.row = '7';
				break;
			case 2:
				this.row = '6';
				break;
			case 3:
				this.row = '5';
				break;
			case 4:
				this.row = '4';
				break;
			case 5:
				this.row = '3';
				break;
			case 6:
				this.row = '2';
				break;
			case 7:
				this.row = '1';
				break;
			}
		} else {
			switch(x) {
			case 7:
				this.row = '8';
				break;
			case 6:
				this.row = '7';
				break;
			case 5:
				this.row = '6';
				break;
			case 4:
				this.row = '5';
				break;
			case 3:
				this.row = '4';
				break;
			case 2:
				this.row = '3';
				break;
			case 1:
				this.row = '2';
				break;
			case 0:
				this.row = '1';
				break;
			}
			
		}
	}
	
	public void setColumn(int x) {
		
		if(playerColor.equals("white")) {
			switch(x) {
			case 0:
				this.column = 'A';
				break;
			case 1:
				this.column = 'B';
				break;
			case 2:
				this.column = 'C';
				break;
			case 3:
				this.column = 'D';
				break;
			case 4:
				this.column = 'E';
				break;
			case 5:
				this.column = 'F';
				break;
			case 6:
				this.column = 'G';
				break;
			case 7:
				this.column = 'H';
				break;
			}
		} else {
			switch(x) {
			case 7:
				this.column = 'A';
				break;
			case 6:
				this.column = 'B';
				break;
			case 5:
				this.column = 'C';
				break;
			case 4:
				this.column = 'D';
				break;
			case 3:
				this.column = 'E';
				break;
			case 2:
				this.column = 'F';
				break;
			case 1:
				this.column = 'G';
				break;
			case 0:
				this.column = 'H';
				break;
			}
		}
	}
}