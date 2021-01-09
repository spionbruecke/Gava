package src.chess;

import src.games.Field;
import src.games.GameBoard;
import src.games.Messages;
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
                p[row][j].setColour("null");
                p[row][j].setName("null");
            }
        }

        p[0][0].setName("rook");
        p[0][0].setColour("white");

        p[0][1].setName("knight");
        p[0][1].setColour("white");

        p[0][2].setName("bishop");
        p[0][2].setColour("white");

        p[0][3].setName("king");
        p[0][3].setColour("white");

        p[0][4].setName("queen");
        p[0][4].setColour("white");

        p[0][5].setName("bishop");
        p[0][5].setColour("white");

        p[0][6].setName("knight");
        p[0][6].setColour("white");

        p[0][7].setName("rook");
        p[0][7].setColour("white");



        p[1][0].setName("pawn");
        p[1][0].setColour("white");

        p[1][3].setName("pawn");
        p[1][3].setColour("white");

        p[1][4].setName("pawn");
        p[1][4].setColour("white");

        p[1][5].setName("pawn");
        p[1][5].setColour("white");

        p[1][6].setName("pawn");
        p[1][6].setColour("white");

        p[1][7].setName("pawn");
        p[1][7].setColour("white");


        p[2][2].setName("pawn");
        p[2][2].setColour("white");


        p[3][0].setName("queen");
        p[3][0].setColour("black");

        p[3][1].setName("pawn");
        p[3][1].setColour("white");

        p[5][3].setName("pawn");
        p[5][3].setColour("black");

        for (int i = 0; i < 8; i++) {
            p[6][i].setColour("black");
            p[6][i].setName("pawn");
        }

        p[6][3].setColour("null");
        p[6][3].setName("null");


        p[7][0].setName("rook");
        p[7][0].setColour("black");

        p[7][1].setName("knight");
        p[7][1].setColour("black");

        p[7][2].setName("bishop");
        p[7][2].setColour("black");

        p[7][3].setName("king");
        p[7][3].setColour("black");

        p[7][4].setName("null");
        p[7][4].setColour("null");

        p[7][5].setName("bishop");
        p[7][5].setColour("black");

        p[7][6].setName("knight");
        p[7][6].setColour("black");

        p[7][7].setName("rook");
        p[7][7].setColour("black");

        GameBoard board = new ChessBoard();
        board.setState(p);

        System.out.println(ChessRules.checkmate(board, "white")+"checkmate");

    }
}
