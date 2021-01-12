package src.games;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    static Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck){return null;}

    static boolean isFieldOccupied(GameBoard board, int row, int column){
        return !board.getState()[row][column].getColour().equals("null");
    }

    static Messages isGameFinished(GameBoard board, String colour){return null;}
}
