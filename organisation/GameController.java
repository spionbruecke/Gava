package organisation;

import java.util.*;
import monk.*;
//import game_structur.Games;
//import game_structur.GameBoard;

// This Class starts automatically if the server starts
public class GameController {

    private static List<GameMaster> currentGames = new ArrayList<>();


    public static GameMaster openNewRoom(Games game) {
        currentGames.add(new GameMaster(game));
        return currentGames.get(currentGames.size() - 1);
    }

    public GameMaster addPlayer(Player player) {
        GameMaster currentGame;

        if(currentGames.isEmpty()){
            currentGame = openNewRoom(player.getGame());
            player.setGameMaster(currentGame);
            currentGame.addPlayer(player);
            currentGames.add(currentGame);
            return currentGame;
        } else {
            for(int i = 0 ; i < currentGames.size(); i ++){
                if(currentGames.get(i).getNumberOfPlayer() < 2 && currentGames.get(i).getGame().getName().equals(player.getGame().getName())){
                    currentGames.get(i).addPlayer(player);
                    player.setGameMaster(currentGames.get(i));
                    return  currentGames.get(i);
                }
            }
            currentGame = openNewRoom(player.getGame());
            player.setGameMaster(currentGame);
            currentGame.addPlayer(player);
            currentGames.add(currentGame);
            return currentGame;
        }
    }
    
    /*
    private Boolean getInput(Player player,GameMaster gamemaster, String input){
        return false;
    }


    private Boolean notify (Player player){
        return false;
    }

    */
    
}
