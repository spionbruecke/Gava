package src.games;

import src.chess.ChessMessages;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    //boolean isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck);

    static boolean isFieldOccupied(GameBoard board, int row, int column){
        return board.getState()[row][column] != null;
    }

    //ChessMessages isGameFinished(GameBoard board);
}
