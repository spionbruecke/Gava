package organisation;


//import monk.GameBoard;
import monk.Games;

public class GameRoom{

    //private GameBoard currentGameBoard;
    private Games currentGame;
    private Player player1;
    private Player player2;
    private int numberOfPlayer;

    public GameRoom(Games game){
        currentGame = game;

    }

    //****  Getter ****
    protected int getNumberOfPlayer(){
        return numberOfPlayer;
    }

    public Games getGame(){
        return currentGame;
    }

    protected Player getPlayer1(){
        return player1;
    }

    protected Player getPlayer2(){
        return player2;
    }

    //for test purpose (and mayby later for the gui?)
    public String getTheOtherPlayer( Player player){
        if (player1 == player && player2 != null)
            return player2.getName();
        else if( player2 == player && player1 != null)
            return player1.getName();
        else
            return " with none";
    }

    /*
    protected void getStart(){
        
    }
    
    protected Boolean get_Input(GameBoard gameboard, Player player){
        return false;
    }

    //****  Functions ****
    protected GameBoard notify(GameBoard gameboard){
        return null;
    }
    protected Games notify(Player player){
        return null;
    }
    */

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