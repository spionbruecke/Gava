package src.games;

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
     * A Cache object which saves the previous state of a GameBoard for undoing the last move.
     * Initial value is null
     */
    private Cache prevState = null;

    /**
     * Sets the new state to the current state and saves the current
     * state in prevState.
     * @param state PlayingPiece[][]
     */
    public void setState(PlayingPiece[][] state){
        this.state = state;
        saveStateToMemento(state);
    }

    /**
     * Returns the current state.
     * @return PlayingPiece[][]
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
     * Returns the gameBoardID.
     * @return long
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
    private void saveStateToMemento(PlayingPiece[][] state) {
        prevState = new Cache(state);
    }
    
    //public abstract String getNewMove(String newBoard);

    //public abstract void setNewMove(String move);
    
    protected abstract PlayingPiece[][] setUpPlayingPieces();
}
