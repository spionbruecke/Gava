package Games;

/*
  Memento Design Pattern
  This class is used for undoing the last move.
 */

public class Cache {

    private int gameBoardID;
    private String[][] state;

    public Cache(String[][] state, int gameBoardID){
        this.state = state;
        this.gameBoardID = gameBoardID;
    }

    public String[][] getState(){
        return state;
    }

    public void setState(String[][] state){
        this.state = state;
    }

    public int getGameBoardID(){
        return gameBoardID;
    }
}
