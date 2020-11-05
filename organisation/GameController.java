package organisation;

//import game_structur.Games;
//import game_structur.GameBoard;

// This Class starts automatically if the server starts
public class GameController{
  
    static GameMaster currentGames;

    public static void main(String[] args) {
        currentGames = new GameMaster();
        System.out.println(currentGames);
    }
    public static GameMaster openNewRoom(){
        currentGames = new GameMaster();
        return null;
    }

    private Boolean addPlayer(Player player, GameMaster gamemaster){
        return false;
    }
    
    private Boolean getInput(Player player,GameMaster gamemaster, String input){
        return false;
    }


    private Boolean notify (Player player){
        return false;
    }


    
}
