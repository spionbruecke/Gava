package src.games;

/**
 * @author Begüm Tosun
 */
public class PlayingPiece {

    public PlayingPiece(){}
    /**
     * Description of the PlayingPiece
     */
    private String name;

    /**
     * Colour is used to differ the PlayingPieces from each Player.
     */
    private String colour;

    /**
     * Describes Position on the GameBoard
     */
    private String position;

    /**
     * Tells whether the PlayingPiece has been moved or not. (initial value = false)
     */
    private boolean hasMoved = false;

    /**
     * Returns the name of a PlayingPiece object.
     * @return String: name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a PlayingPiece object.
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the colour of a PlayingPiece object.
     * @return String: colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * Sets the colour of a PlayingPiece.
     * @param colour String: colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * Returns the position of a PlayingPiece object.
     * @return String
     */
    public String getPosition(){
        return position;
    }

    /**
     * Sets the position of a PlayingPiece object.
     * @param position String
     */
    public void setPosition(String position){
        this.position = position;
    }

    /**
     * Creates a String representation of a PlayingPiece object.
     * @return String
     */
    public String toString(){
        StringBuilder output = new StringBuilder();
        String moved;
        if(hasMoved)
            moved = "1";
        else
            moved = "0";
        output.append(name).append(",").append(colour).append(",").append(moved).append("=").append(position);

        return output.toString();
    }

    /**
     * True if chess playing piece has moved else false.
     * @return boolean
     */
    public boolean hasMoved(){return hasMoved;}

    public void setHasMoved(){
        hasMoved = true;
    }

    /**
     * Checks whether two PlayingPiece objects have the same value.
     * @param p PlayingPiece
     * @return boolean
     */
    public boolean equals(PlayingPiece p){
        return this.getName().equals(p.getName()) && this.getColour().equals(p.getColour());
    }

}
