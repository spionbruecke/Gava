package src.games;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    static Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck){return null;}

    static boolean isFieldOccupied(PlayingPiece[][] state, int row, int column){
        return !state[row][column].getColour().equals("null");
    }

    static Messages isGameFinished(GameBoard board, String colour){return null;}
}
