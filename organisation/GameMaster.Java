package organisation;

//import monk.GameBoard;
import monk.Games;

public class GameMaster{

    //private GameBoard currentGameBoard;
    private Games currentGame;
    private Player player1;
    private Player player2;
    private int numberOfPlayer;

    public GameMaster(Games game){
        currentGame = game;

    }
    protected int getNumberOfPlayer(){
        return numberOfPlayer;
    }
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

    protected Games getGame(){
        return currentGame;
    }

    protected Player getPlayer1(){
        return player1;
    }

    protected Player getPlayer2(){
        return player2;
    }

    /*
    protected void getStart(){
        
    }

    
    protected Boolean get_Input(GameBoard gameboard, Player player){
        return false;
    }
    protected GameBoard notify(GameBoard gameboard){
        return null;
    }
    protected Games notify(Player player){
        return null;
    }
    */

    public String toString(){
        StringBuilder output = new StringBuilder();

        output.append("Game: ").append(currentGame).append("\n");
        output.append("Player 1: ").append(player1).append("\n");
        output.append("Player 2: ").append(player2).append("\n");
        return output.toString();
    }
}