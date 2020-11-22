package src.games;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    /**
     * Checks whether the player has already PlayingPiece
     * on this field.
     * @param x coordinate of field
     * @param y coordinate of field
     * @return 1 if field is occupied else 0
     */
    public  boolean isFieldOccupied(int x, int y);

    /**
     * Checks whether the move is allowed according to the game rules.
     * @param oldState current state of PlayingPiece
     * @param newState state where the Player wants to move the PlayingPiece
     * @return 1 if move is allowed else 0
     */
    public  boolean isMoveAllowed(PlayingPiece[][] oldState, PlayingPiece[][] newState);
}
