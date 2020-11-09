package organisation;

import monk.Games;

import java.util.Random;
import java.util.UUID;


public class Player {

    private String playerID;
    private String name;
    private Games chosedGame;
    //private int points;
    private GameRoom gameRoom;

    public Player(){
        //A random name will be generated until the Player log in
        Random r = new Random();
        name = "anonymus" + r.nextInt(10000);

        // Each Player get a unique ID
        playerID = UUID.randomUUID().toString();
    }

    //**** Setter und Getter ****

    public Boolean setName(String name){
            this.name = name;
            return true;
    }

    public String getName(){
        return name;
    }

    public void setGameRoom(GameRoom gameRoom){
        this.gameRoom = gameRoom;
    }
    
    public GameRoom getGameRoom(){
        return gameRoom;
    }
    
    public void setGame(Games game){
        this.chosedGame = game;
    }

    public Games getGame(){
        return chosedGame;
    }

    public String getPlayerID(){
        return playerID;
    }

    /**** Functions ****
     

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
    
    /* Benötigt Zugang zur Datenbank
    public Boolean logIn(String name, String password) {
        return false;
    }

    */

    public String toString(){
        return "ID: " + playerID + " | Name: " + name;
    }
}