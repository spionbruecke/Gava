package src.Games;

/**
 * The client only returns a simple String-description
 * of the move. MoveConverter converts this String into
 * the current state of the gameBoard.
 * @author Begüm Tosun & Alexander Posch
 */

public interface MoveConverter {

    /**
     * This method gets a String description of the move
     * and converts it onto the current game board state
     * which is a two dimensional PlayingPiece array.
     * @param gameBoard GameBoard
     * @param move String
     * @return currentState: PlayingPiece[][]
     */
    PlayingPiece[][] convertStringToState(GameBoard gameBoard, String move);

    /**
     * Converts field name into array coordinate
     * @param c field-description
     * @return array coordinate
     */
    int convertPosIntoArrayCoordinate(char c);

    Field getTargetField(String move);
    
    Field getStartField(String move);
}
