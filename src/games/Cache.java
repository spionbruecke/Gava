package src.games;

/**
 * Memento Design Pattern
 * This class is used for undoing the last move.
 * @author Begüm Tosun
 */
public class Cache {
    private GameBoard board;

    /**
     * Constructs a Cache-object.
     * @param board GameBoard-object
     */
    public Cache(GameBoard board){
        this.board = board;
    }

    /**
     * Returns a multidimensional PlayingPiece array.
     * @return state
     */
    public PlayingPiece[][] getState(){
        return board.getState();
    }

    /**
     * Sets state to the given parameter.
     * @param state a multidimensional PlayingPiece array
     */
    public void setState(PlayingPiece[][] state){
        board.setState(state);
    }

    /**
     * returns an long value
     * @return gameBoardID
     */
    public long getGameBoardID(){
        return board.getGameBoardID();
    }
}
