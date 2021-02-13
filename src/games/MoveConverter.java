package src.games;


/**
 * The client only returns a simple String-description
 * of the move. MoveConverter converts this String into
 * the current state of the gameBoard.
 * @author Begüm Tosun, Alexander Posch
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
     * Converts the new state which is a PlayingPiece[][] into a String which describes the move.
     * @param currentState PlayingPiece[][]
     * @param stateToCheck PlayingPiece[][]
     * @return String
     */
    String stateToString(PlayingPiece[][] currentState, PlayingPiece[][] stateToCheck);

}
