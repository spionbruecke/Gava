package src.Games;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    /**
     * Checks whether the player has already a PlayingPiece
     * on this field.
     * @param board GameBoard
     * @param move String
     * @return true if field is occupied else false
     */
    boolean isFieldOccupiedByOwnPlayingP(GameBoard board, String move);

    /**
     * Checks whether the move is allowed according to the game rules.
     * @param old PlayingPiece[][]
     * @param move String
     * @return true if move is allowed else false
     */
    boolean isMoveAllowed(GameBoard gameBoard, String move);

    boolean isFieldOccupied(GameBoard board, String move);
}
