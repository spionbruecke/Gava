package src.Games;

/**
 * @author Begüm Tosun
 */
public abstract class Observable {
    //private ArrayList<Observer> observers;

    public abstract void notifyObservers(GameBoard gameBoard, PlayingPiece[][] state);

}
