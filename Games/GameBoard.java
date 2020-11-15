package Games;

public abstract class GameBoard {

    private PlayingPiece[][] state;
    private int gameBoardID;
    private CacheManager cacheList;

    public void setState(PlayingPiece[][] state){
        this.state = state;
        saveStateToMemento(state);
    }

    public PlayingPiece[][] getState(){
        return state;
    }

    public abstract void setGameBoardID();

    public int getGameBoardID(){
        return gameBoardID;
    }
    
    //@Nullable
    public Cache getStateFromMemento(){
        if(cacheList.hasEntry(gameBoardID) > -1) {
            return cacheList.getPreviousStates().get(cacheList.hasEntry(gameBoardID));
        } else {
            System.out.println("GameBoard has no entry in cache!");
            return null;
        }
    }

    public void saveStateToMemento(PlayingPiece[][] state) {

        if(cacheList.hasEntry(gameBoardID) > -1) {
            cacheList.getPreviousStates().get(cacheList.hasEntry(gameBoardID)).setState(state);
        } else {
            Cache memento = new Cache(state, gameBoardID);
            cacheList.getPreviousStates().add(memento);
        }
    }
}
