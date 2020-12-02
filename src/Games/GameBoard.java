package src.Games;

/**
 * @author Begüm Tosun
 */
public abstract class GameBoard {

    /**
     * The multidimensional PlayingPiece array represents
     * the game board.
     */
    private PlayingPiece[][] state;

    /**
     * long value for identifying the game board.
     */
    protected long gameBoardID;

    /**
     *
     */
    private Cache prevState;

    /**
     * Sets state to the current state and saves the current
     * state in prevState.
     * @param state PlayingPiece[][]
     */
    public void setState(PlayingPiece[][] state){
        this.state = state;
        saveStateToMemento();
    }

    /**
     * Returns a multidimensional PlayingPiece Array.
     * @return state
     */
    public PlayingPiece[][] getState(){
        return state;
    }

    /**
     * Sets the gameBoard ID to the given value.
     * @param id long
     */
    public void setGameBoardID(long id){
        gameBoardID = id;
    }

    /**
     * Returns a long value.
     * @return gameBoardID
     */
    public long getGameBoardID(){
        return gameBoardID;
    }

    /**
     * Returns the previous state from cache for undoing the last move.
     * @return Cache-element or null if there is no entry
     */
    //@Nullable
    public Cache getStateFromMemento(){
        return prevState;
    }

    /**
     * Saves a given state in a Cache list. This method is used for saving the current state
     * into the CacheManager. If there already exists a previous state for the gameBoardID
     * it will be overwritten otherwise the method will create a new list element and add
     * it to the list.
     */
    public void saveStateToMemento() {
        prevState = new Cache(this);
    }

    public abstract String getNewMove(String newBoard);

    public abstract void setNewMove(String move);

    public abstract String convertBoardtoString();
    
    protected abstract PlayingPiece[][] setUpPlayingPieces();
}
