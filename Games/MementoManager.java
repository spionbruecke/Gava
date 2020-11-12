package Games;

import java.util.ArrayList;

public class MementoManager {

    private ArrayList<Memento> previousStates;

    public MementoManager(){
        previousStates = new ArrayList<Memento>();
    }

    public ArrayList<Memento> getPreviousStates(){
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
