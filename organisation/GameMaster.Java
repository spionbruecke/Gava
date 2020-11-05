package organisation;

import monk.GameBoard;
import monk.Games;

public class GameMaster{

    private GameBoard currentGameBoard;
    private Games currentGame;
    private Player Player_1;
    private Player Player_2;

    public GameMaster(){

    }
    
    protected void get_start(){
        
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
}