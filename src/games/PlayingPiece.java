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
        colour = "null";
        name = "null";
    }

    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

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

    public boolean getHasMoved(){
        return hasMoved;
    }

    public boolean equals(PlayingPiece p){
        return this.getName().equals(p.getName()) && this.getColour().equals(p.getColour());
    }

}
