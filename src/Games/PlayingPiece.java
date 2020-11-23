package src.Games;

/**
 * @author Begüm Tosun
 */
public abstract class PlayingPiece {

    /**
     * Description of the PlayingPiece
     */
    private String name;

    /**
     * Colour is used to differ the PlayingPieces from each Player.
     */
    private String colour;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public abstract void move(GameBoard board, String move);
}
