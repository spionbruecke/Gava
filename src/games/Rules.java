package src.games;

import java.util.ArrayList;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck);

    static boolean isFieldOccupied(GameBoard board, int row, int column){
        return board.getState()[row][column] != null;
    }

    //ArrayList<Messages> isGameFinished(GameBoard board);
}
