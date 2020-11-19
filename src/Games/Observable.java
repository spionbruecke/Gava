package Games;

import java.util.ArrayList;

public abstract class Observable {
    //private ArrayList<Observer> observers;

    public abstract void notifyObservers(GameBoard gameBoard, String[][] state);

}
