package src.games;

import java.util.ArrayList;

public abstract class Observable {
    //private ArrayList<Observer> observers;

    public abstract void notifyObservers(GameBoard gameBoard, PlayingPiece[][] state);

}
