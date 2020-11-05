package organisation;

import monk.Games;
import monk.GameBoard;

public class Player {

    private long ID;
    private String name;
    private Games chosedGame;
    private int points;

    public Player(){
        ID ++;
        name = "anonymus" + (int) (Math.random() * 100000);
    }
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