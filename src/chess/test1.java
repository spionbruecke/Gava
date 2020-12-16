package src.chess;

import src.games.Field;
import src.games.GameBoard;
import src.games.PlayingPiece;
import src.chess.ChessRules;

public class test1 {

    public static void main(String[] args){
        PlayingPiece[][] p = new PlayingPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                p[i][j] = new PlayingPiece();
            }
        }

        for (int row = 0; row < 8; row++) {
            for (int j = 0; j < 8; j++) {
                if( ((row == 0) && (j == 0)) || ((row == 1) && (j == 1))){
                    // do nothing
                }else{
                    p[row][j].setName("null");
                    p[row][j].setColour("null");
                }
            }
        }

        p[0][0].setName("pawn");
        p[0][0].setColour("white");

        p[1][1].setName("pawn");
        p[1][1].setColour("black");

        GameBoard b = new ChessBoard();
        b.setState(p);
        ChessRules rule = new ChessRules();

        if(rule.areVerticalOrHorizontalPathsFree(b, "B7 A8"))
            System.out.println("Turm laeuft einwandfrei");

    }
}
