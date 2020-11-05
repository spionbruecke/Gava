package organisation;

import game_structur.Games;
import game_structur.GameBoard;

public class Player {

    private long ID;
    private String name;
    private Games chosedGame;
    private int points;

    public Boolean logIn(String name, String password) {
        return false;
    }

    public Boolean setName(String name){
            this.name = name;
            return true;
    }
    public String getName(){
        return name;
    }
    public Boolean choseGame(Games game){
        this.chosedGame = game;
        return false;
    }

    protected Boolean setInput(GameBoard gameboard){
        return false;
    }
    protected Boolean notify(GameBoard gameboard){
        return false;
    }
    protected Boolean notify(Games game){
        return false;
    }

}