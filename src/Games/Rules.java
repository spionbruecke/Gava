package src.Games;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    /**
     * Checks whether the player has already PlayingPiece
     * on this field.
     * @param board GameBoard
     * @param move String
     * @return true if field is occupied else false
     */
    boolean isFieldOccupiedByOwnPlayingP(GameBoard board, String move);

    /**
     * Checks whether the move is allowed according to the game rules.
     * @param playingPiece PlayingPiece
     * @param move String
     * @return true if move is allowed else false
     */
    boolean isMoveAllowed(PlayingPiece playingPiece, String move);
    boolean isMoveAllowed(PlayingPiece[][] old, PlayingPiece[][] current);
}
