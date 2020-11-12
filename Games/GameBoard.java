package Games;

import com.sun.istack.internal.Nullable;

public abstract class GameBoard {

    private String[][] state;
    private int gameBoardID;
    private MementoManager cache;

    public void setState(String[][] state){
        this.state = state;
        saveStateToMemento(state);
    }

    public String[][] getState(){
        return state;
    }

    public abstract void setGameBoardID();

    public int getGameBoardID(){
        return gameBoardID;
    }

    public @Nullable Memento getStateFromMemento(){

        if(cache.hasEntry(gameBoardID) > -1) {
            return cache.getPreviousStates().get(cache.hasEntry(gameBoardID));
        } else {
            System.out.println("GameBoard has no entry in cache!");
            return null;
        }
    }

    public void saveStateToMemento(String[][] state) {

        if(cache.hasEntry(gameBoardID) > -1) {
            cache.getPreviousStates().get(cache.hasEntry(gameBoardID)).setState(state);
        } else {
            Memento memento = new Memento(state, gameBoardID);
            cache.getPreviousStates().add(memento);
        }
    }
}
