package organisation;

import monk.Games;

import java.util.Random;
import java.util.UUID;


public class Player {

    private String playerID;
    private String name;
    private Games chosedGame;
    //private int points;
    private GameMaster gameMaster;

    public Player(){
        //A random name will be generated until the Player log in
        Random r = new Random();
        name = "anonymus" + r.nextInt(10000);

        // Each Player get a unique ID
        playerID = UUID.randomUUID().toString();
    }

    /* Benötigt Zugang zur Datenbank
    public Boolean logIn(String name, String password) {
        return false;
    }

    public Boolean setName(String name){
            this.name = name;
            return true;
    }
    */

    public String getPlayerID(){
        return playerID;
    }

    public String getName(){
        return name;
    }

    public Games getGame(){
        return chosedGame;
    }

    public void setGameMaster(GameMaster gameMaster){
        this.gameMaster = gameMaster;
    }

    public GameMaster getGameMaster(){
        return gameMaster;
    }

    public Boolean choseGame(Games game){
        this.chosedGame = game;
        return false;
    }

    /*
    protected Boolean setInput(GameBoard gameboard){
        return false;
    }
    protected Boolean notify(GameBoard gameboard){
        return false;
    }
    protected Boolean notify(Games game){
        return false;
    }
    */

    public String toString(){
        return "ID: " + playerID + " | Name: " + name;
    }
}