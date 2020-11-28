package src.Games;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    boolean isMoveAllowed(GameBoard gameBoard, String move);
    boolean isFieldOccupied(GameBoard board, Field f);
    boolean isFieldOccupiedByOwnPlayingP(GameBoard board, String move);
}
