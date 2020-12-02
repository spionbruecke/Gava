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

    private String position;

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

    public void outOfGame(){
        colour = null;
        name = null;
    }

    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public String toString(){
        StringBuilder output = new StringBuilder();

        output.append(name).append(",").append(colour).append("=").append(position);

        return output.toString();
    }

    public abstract void move(GameBoard board, String move);

    public abstract boolean hasMoved();
}
