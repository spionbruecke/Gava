package src.games;

import java.util.ArrayList;

public class CacheManager {

    private ArrayList<Cache> previousStates;

    public CacheManager(){
        previousStates = new ArrayList<Cache>();
    }

    public ArrayList<Cache> getPreviousStates(){
        return previousStates;
    }

    public int hasEntry(int gameboardID){

        for(int i = 0; i < previousStates.size(); i++){
            if(previousStates.get(i).getGameBoardID() == gameboardID){
                return i;
            }
        }

        return -1;
    }
}
