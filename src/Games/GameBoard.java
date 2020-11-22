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
     * Int value for identifying the game board.
     */
    private int gameBoardID;

    //private CacheManager cacheList;

    /**
     * Sets state to the current state and saves the current
     * state in a ArrayList of type Cache.
     * @param state PlayingPiece[][]
     */
    public void setState(PlayingPiece[][] state, CacheManager cacheList){
        this.state = state;
        saveStateToMemento(state, cacheList);
    }

    /**
     * Returns a multidimensional PlayingPiece Array.
     * @return state
     */
    public PlayingPiece[][] getState(){
        return state;
    }

    public abstract void setGameBoardID();

    /**
     * Returns int value.
     * @return gameBoardID
     */
    public int getGameBoardID(){
        return gameBoardID;
    }

    /**
     * Returns the previous state from cache for undoing the last move.
     * @param cacheList List where the previous state is saved (if it exists)
     * @return Cache-element or null if there is no entry for the given gameBoardID
     */
    //@Nullable
    public Cache getStateFromMemento(CacheManager cacheList){
        if(cacheList.hasEntry(gameBoardID) > -1) {
            return cacheList.getPreviousStates().get(cacheList.hasEntry(gameBoardID));
        } else {
            System.out.println("GameBoard has no entry in cache!");
            return null;
        }
    }

    /**
     * Saves a given state in a Cache list. This method is used for saving the current state
     * into the CacheManager. If there already exists a previous state for the gameBoardID
     * it will be overwritten otherwise the method will create a new list element and add
     * it to the list.
     * @param state PlayingPiece[]
     * @param cacheList CacheManager
     */
    public void saveStateToMemento(PlayingPiece[][] state, CacheManager cacheList) {

        if(cacheList.hasEntry(gameBoardID) > -1) {
            cacheList.getPreviousStates().get(cacheList.hasEntry(gameBoardID)).setState(state);
        } else {
            Cache memento = new Cache(state, gameBoardID);
            cacheList.getPreviousStates().add(memento);
        }
    }
}
