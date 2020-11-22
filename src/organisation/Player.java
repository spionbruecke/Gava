package src.organisation;

import java.util.*;
import src.Games.*;


public class Player {

    private String playerID;
    private StringBuilder name;
    private Game chosedGame;
    //private int points;
    private GameRoom gameRoom;
    private boolean newStateAvaible;
    private String latestMove;
    //private ArrayList<PlayingPiece> palyingPieces;

    public Player(){
        //A random name will be generated until the Player log in
        Random r = new Random();
        name = new StringBuilder();
        name.append("anonymus").append(r.nextInt(10000));

        // Each Player get a unique ID
        playerID = UUID.randomUUID().toString();
    }

    //**** Setter und Getter ****

    public Boolean setName(String name){
            this.name.append(name);
            return true;
    }

    public String getName(){
        return name.toString();
    }

    public void setGameRoom(GameRoom gameRoom){
        this.gameRoom = gameRoom;
    }
    
    public GameRoom getGameRoom(){
        return gameRoom;
    }
    
    public void setGame(Game game){
        this.chosedGame = game;
    }

    public Game getGame(){
        return chosedGame;
    }

    public String getPlayerID(){
        return playerID;
    }

    public void setNewStateAvaible(boolean newStateAvaible){
        this.newStateAvaible = newStateAvaible;
    }

    public boolean getNewStateAvaible(){
        return newStateAvaible;
    }

    public void setLatestMove(String turn){
        latestMove = turn;
    }

    public String getLatestMove(){
        return latestMove;
    }
    //**** Functions ****
         
    /* Benötigt Zugang zur Datenbank
    public Boolean logIn(String name, String password) {
        return false;
    }

    */

    public String toString(){
        return "ID: " + playerID + " | Name: " + name;
    }
}