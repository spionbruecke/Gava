package src.Games;

/**
 * Memento Design Pattern
 * This class is used for undoing the last move.
 * @author Begüm Tosun
 */
public class Cache {

    private int gameBoardID;

    /**
     * A multidimensional array of type PlayingPiece
     * which represents the state of a game board.
     */
    private PlayingPiece[][] state;

    /**
     * Constructs a Cache-object.
     * @param state a multidimensional PlayingPiece array
     * @param gameBoardID integer value
     */
    public Cache(PlayingPiece[][] state, int gameBoardID){
        this.state = state;
        this.gameBoardID = gameBoardID;
    }

    /**
     * Returns a multidimensional PlayingPiece array.
     * @return state
     */
    public PlayingPiece[][] getState(){
        return state;
    }

    /**
     * Sets state to the given parameter.
     * @param state a multidimensional PlayingPiece array
     */
    public void setState(PlayingPiece[][] state){
        this.state = state;
    }

    /**
     * returns an int value
     * @return gameBoardID
     */
    public int getGameBoardID(){
        return gameBoardID;
    }
}
