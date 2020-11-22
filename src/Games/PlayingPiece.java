package src.games;

/**
 * @author Begüm Tosun
 */
public abstract class PlayingPiece {

    /**
     * Description of the PlayingPiece
     */
    private String name;


    /**
     * State is 1 if the playing piece is still in the game else 0
     */
    private boolean state;

    /**
     * Color is used to differ the PlayingPieces from each Player.
     */
    private String color;

    public abstract void move();
    public abstract void swap();
}
