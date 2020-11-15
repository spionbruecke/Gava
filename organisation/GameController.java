package organisation;

import java.util.*;
import Games.*;
//import game_structur.Games;
//import game_structur.GameBoard;

// This Class starts automatically if the server starts
public class GameController {

    private static List<GameRoom> currentGames = new ArrayList<>();


    public static GameRoom openNewRoom(Game game) {
        currentGames.add(new GameRoom(game));
        return currentGames.get(currentGames.size() - 1);
    }

    public GameRoom addPlayer(Player player) {
        GameRoom currentGame;

        //Check if there is a room already. Is there no GameRoom yet, then create a new one
        if(currentGames.isEmpty()){
            currentGame = openNewRoom(player.getGame());
            player.setGameRoom(currentGame);
            currentGame.addPlayer(player);
            currentGames.add(currentGame);
            return currentGame;
        } else {
            //Go through the list of GameRooms and check if there is a Game with missing Players and the GameMode the Player want
            for(int i = 0 ; i < currentGames.size(); i ++){
                if(currentGames.get(i).getNumberOfPlayer() < 2 && currentGames.get(i).getGame().getName().equals(player.getGame().getName())){
                    currentGames.get(i).addPlayer(player);
                    player.setGameRoom(currentGames.get(i));
                    return  currentGames.get(i);
                }
            }
            //If the programm didn't find an room create a new one
            currentGame = openNewRoom(player.getGame());
            player.setGameRoom(currentGame);
            currentGame.addPlayer(player);
            currentGames.add(currentGame);
            return currentGame;
        }
    }
    
    /*
    private Boolean getInput(Player player,GameRoom gameRoom, String input){
        return false;
    }


    private Boolean notify (Player player){
        return false;
    }

    */
    
}
