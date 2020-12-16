package src.chess;

import src.games.Field;
import src.games.GameBoard;
import src.games.Messages;
import src.games.PlayingPiece;
import src.chess.ChessRules;

public class test1 {

    public static void main(String[] args){

        //..................Bauer schlägt Bauer.................//
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
        p[0][0].setColour("black");

        p[1][1].setName("pawn");
        p[1][1].setColour("white");

        PlayingPiece[][] p_copy = p.clone();
        p_copy[0][0].setName("pawn");
        p_copy[0][0].setColour("white");

        p_copy[1][1].setName("null");
        p_copy[1][1].setColour("null");

        GameBoard b = new ChessBoard();
        b.setState(p);
        ChessRules rule = new ChessRules();

        if(rule.isMoveAllowed(b, p_copy) == Messages.MOVE_ALLOWED)
            System.out.println("Bauer schlaegt Bauer");

        System.out.println(rule.isMoveAllowed(b, p_copy));

//.......................Läufer schlägt Läufer..............//
        PlayingPiece[][] p2 = new PlayingPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                p2[i][j] = new PlayingPiece();
            }
        }

        for (int row = 0; row < 8; row++) {
            for (int j = 0; j < 8; j++) {
                if( ((row == 0) && (j == 0)) || ((row == 1) && (j == 1))){
                    // do nothing
                }else{
                    p2[row][j].setName("null");
                    p2[row][j].setColour("null");
                }
            }
        }

        p2[0][0].setName("bishop");
        p2[0][0].setColour("black");

        p[1][1].setName("bishop");
        p[1][1].setColour("white");

        GameBoard board2 = new ChessBoard();
        board2.setState(p);

        if(rule.isMoveAllowed(b, p_copy) == Messages.MOVE_ALLOWED)
            System.out.println("Läufer schlaegt Läufer");
    }
}
