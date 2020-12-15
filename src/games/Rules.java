package src.games;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck);

    static boolean isFieldOccupied(GameBoard board, int row, int column){
        return board.getState()[row][column].getName() != "null";
    }

    Messages isGameFinished(GameBoard board, String colour);
}
