package src.games;

/*
  Memento Design Pattern
  This class is used for undoing the last move.
 */

public class Cache {

    private int gameBoardID;
    private PlayingPiece[][] state;

    public Cache(PlayingPiece[][] state, int gameBoardID){
        this.state = state;
        this.gameBoardID = gameBoardID;
    }

    public PlayingPiece[][] getState(){
        return state;
    }

    public void setState(PlayingPiece[][] state){
        this.state = state;
    }

    public int getGameBoardID(){
        return gameBoardID;
    }
}
