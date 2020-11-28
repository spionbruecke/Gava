package src.Games;

/**
 * @author Begüm Tosun
 */
public interface Rules {

    boolean isFieldOccupied(GameBoard board, Field f);
    boolean isFieldOccupiedByOwnPlayingP(GameBoard board, String move);
}
