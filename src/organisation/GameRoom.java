package src.organisation;

import src.Games.*;
import src.monk.SchachRules;

public class GameRoom{

    //private GameBoard currentGameBoard;
    private Game currentGame;
    private Player player1;
    private Player player2;
    private Player turnOfPlayer;
    private int numberOfPlayer;
    private GameBoard gameBoard;
    private Rules rule;

    public GameRoom(Game game){
        currentGame = game;
        //if ((int) ( Math.random() * 2 + 1) == 1)
            turnOfPlayer = player1;
        //else
           // turnOfPlayer = player2;
        if(game instanceof Schach)
            rule = new SchachRules();
    }

    //****  Getter ****
    protected int getNumberOfPlayer(){
        return numberOfPlayer;
    }

    public Game getGame(){
        return currentGame;
    }

    protected Player getPlayer1(){
        return player1;
    }

    protected Player getPlayer2(){
        return player2;
    }

    public GameBoard getGameBoard(){
        return gameBoard;
    }

    public boolean getTurn(Player player){
        if(player == turnOfPlayer)
            return true;
        else    
            return false;
    }

    //for test purpose (and mayby later for the gui?)
    public Player getTheOtherPlayer( Player player){
        if (player1 == player && player2 != null)
            return player2;
        else if( player2 == player && player1 != null)
            return player1;
        else
            return null;
    }

    //protected void getStart(){}
    
    public Boolean setInput(String move, Player player){
        PlayingPiece[][] newState = MoveConverter.convertStringToState(gameBoard, move);
        if(rule.isMoveAllowed(gameBoard, move)){
            this.gameBoard.setState(newState);
            turnOfPlayer = getTheOtherPlayer(player);
            turnOfPlayer.setNewStateAvaible(true);
            turnOfPlayer.setLatestMove(move);
            return true;
        } else
            return false;
    }

    //****  Functions ****

    //check which position is free and add the player to this position
     protected boolean addPlayer(Player player){
        if(numberOfPlayer == 0) {
            player1 = player;
            numberOfPlayer ++;

        } else if(numberOfPlayer == 1) {
            player2 = player;
            numberOfPlayer ++;
        } else {
            return false;
        }
        return true;
    }

    public String toString(){
        StringBuilder output = new StringBuilder();

        output.append("Game: ").append(currentGame).append("\n");
        output.append("Player 1: ").append(player1).append("\n");
        output.append("Player 2: ").append(player2).append("\n");
        return output.toString();
    }
}