package src.chess;

import src.Games.GameBoard;

/**
 * @author Begüm Tosun
 *
 * ChessBoard extends the class Gameboard from src.Games and is sed for constructing an intial chess board.
 */
public class ChessBoard extends GameBoard {

    /**
     * The constructor creates a chess board with an ID and its initial state.
     */
    public ChessBoard(){
        super.setGameBoardID(System.currentTimeMillis());
        super.setState(setUpPlayingPieces());
    }

    /**
     * The method setUpPlayingPieces() returns a chess board with an initial setup.
     * @return ChessPlayingPiece[][]
     */
    @Override
    protected ChessPlayingPiece[][] setUpPlayingPieces(){
        ChessPlayingPiece[][] initialState = new ChessPlayingPiece[8][8];

        for(int i = 0 ; i < 8; i ++) {
            for (int j = 0; j < 8; j ++){
                initialState[i][j] = new ChessPlayingPiece();
            }
        }

        initialState[0][0].setName("rook");
        initialState[0][0].setColour("black");

        initialState[0][1].setName("knight");
        initialState[0][1].setColour("black");

        initialState[0][2].setName("bishop");
        initialState[0][2].setColour("black");

        initialState[0][3].setName("queen");
        initialState[0][3].setColour("black");

        initialState[0][4].setName("king");
        initialState[0][4].setColour("black");

        initialState[0][5].setName("bishop");
        initialState[0][5].setColour("black");

        initialState[0][6].setName("knight");
        initialState[0][6].setColour("black");

        initialState[0][7].setName("rook");
        initialState[0][7].setColour("black");

        for (int x = 0; x < 8; x++) {
            initialState[1][x].setName("pawn");
            initialState[1][x].setColour("black");
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                initialState[j][i].setName(null);
                initialState[j][i].setColour(null);
            }
        }

        for (int x = 0; x < 8; x++) {
            initialState[6][x].setName("pawn");
            initialState[6][x].setColour("white");
        }

        initialState[7][0].setName("rook");
        initialState[7][0].setColour("white");

        initialState[7][1].setName("knight");
        initialState[7][1].setColour("white");

        initialState[7][2].setName("bishop");
        initialState[7][2].setColour("white");

        initialState[7][3].setName("queen");
        initialState[7][3].setColour("white");

        initialState[7][4].setName("king");
        initialState[7][4].setColour("white");

        initialState[7][5].setName("bishop");
        initialState[7][5].setColour("white");

        initialState[7][6].setName("knight");
        initialState[7][6].setColour("white");

        initialState[7][7].setName("rook");
        initialState[7][7].setColour("white");

        return initialState;
    }


}
