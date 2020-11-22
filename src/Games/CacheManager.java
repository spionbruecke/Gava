package src.games;

import java.util.ArrayList;

/**
 * This class manages the previous states of currently existing
 * game boards (one previous state for each game board if such a state exists).
 * @author Begüm Tosun
 */
public class CacheManager {

    /**
     * A list with Cache-elements which contains previous states.
     */
    private ArrayList<Cache> previousStates;

    /**
     * Constructor creates a new ArrayList of type Cache
     */
    public CacheManager(){
        previousStates = new ArrayList<Cache>();
    }

    /**
     * Returns the ArrayList with the previous states.
     * @return previousStates
     */
    public ArrayList<Cache> getPreviousStates(){
        return previousStates;
    }

    /**
     * The method hasEntry checks whether there is an entry for the given
     * game board ID. If there is an entry it returns the corresponding
     * index else -1.
     * @param gameboardID int value
     * @return index of the Cache-element for the given game board ID
     */
    public int hasEntry(int gameboardID){

        for(int i = 0; i < previousStates.size(); i++){
            if(previousStates.get(i).getGameBoardID() == gameboardID){
                return i;
            }
        }

        return -1;
    }
}
