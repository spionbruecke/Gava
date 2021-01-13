package src.organisation;

import java.util.*;

import src.chess.*;
import src.games.*;
import src.server.ClientHandler;
import src.mill.*;


/**
 * @author Alexander Posch
 * @version 0.2
 * 
 * Every Client get his own Player class with every nessacery information
 */


public class Player {

    private String playerID;
    private String playerName;
    private Game chosedGame;  
    private GameRoom gameRoom;
    private boolean newStateAvaible;
    private String latestMove;
    private String colour;
    private ClientHandler clienthandler;
    //private ArrayList<PlayingPiece> palyingPieces;
    //private int points;


    public Player(){
        //A random name will be generated until the Player log in
        Random r = new Random();
        StringBuilder name = new StringBuilder();
        name.append("anonymus").append(r.nextInt(10000));
        playerName = name.toString();
        // Each Player get a unique ID
        playerID = UUID.randomUUID().toString();
    }

    //**** Functions ****
         
    /* Benötigt Zugang zur Datenbank
    public Boolean logIn(String name, String password) {
        return false;
    }

    */

    
    //**** Getter ****
    public String getName()                                 {return playerName;}

    public GameRoom getGameRoom()                           {return gameRoom;}

    public Game getGame()                                   {return chosedGame;}

    public String getPlayerID()                             {return playerID;}

    public boolean getNewStateAvaible()                     { return newStateAvaible;}

    public String getLatestMove()                           { return latestMove; }

    public String getColour()                               { return colour; }

    public ClientHandler getClientHandler()                 {return clienthandler; }

    /***********Setter********/

    public void setName(String name)                        {this.playerName = name;}
    
    public void setGameRoom(GameRoom gameRoom)              {this.gameRoom = gameRoom;}
    
    public void setNewStateAvaible(boolean newStateAvaible) {this.newStateAvaible = newStateAvaible;}

    public void setLatestMove(String turn)                  {this.latestMove = turn; }

    public void setColour(String colour)                    {this.colour = colour;}

    public void setClientHandler(ClientHandler client)      {this.clienthandler = client;}

    public void setGame(String input) throws UnsupportedGameMode {
        switch(input){
            case "Chess":
                this.chosedGame = new ChessGame();
                break;
            case "Mill":
                this.chosedGame = new MillGame(); 
                break;
            default:
                System.out.println(input);
                throw new UnsupportedGameMode();
        }
    }

    public String toString(){
        return "ID: " + playerID + " | Name: " + playerName;
    }
}