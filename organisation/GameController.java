package organisation;

//import game_structur.Games;
//import game_structur.GameBoard;

// This Class starts automatically if the server starts
public class GameController{
  
    GameMaster Current_Games;

    public GameMaster openNewRoom(){
        Current_Games = new GameMaster();
        return null;
    }

    private Boolean addPlayer(Player player, GameMaster gamemaster){
        return false;
    }
    
    private Boolean getInput(Player player,GameMaster gamemaster, Input input){
        return false;
    }


    private Boolean notify (Player player){
        return false;
    }


    
}
