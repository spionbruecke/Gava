package src.organisation;

import java.io.IOException;
import java.util.*;

import src.games.*;

/**
 * @author Alexander Posch
 * @version 1.0
 * 
 * Organize every GameRoom.
 * Add Players to the Room with the right Game-Mode or opens a new if needed
 * 
 */

public class GameController {

    private static List<GameRoom> currentGames = new ArrayList<>(); //Every Room with minimum one Player


    /**
     * Opens a new Room, add it to the List and return the new Room
     * @param game Game
     */
    private static GameRoom openNewRoom(Game game) {
        currentGames.add(new GameRoom(game));
        return currentGames.get(currentGames.size() - 1);
    }

    /**
     * Add the Player player to Room depending on the GameMode the Player choosed. 
     * If the room has two players and is full then the game starts.
     * 
     * @param player Player
     * @return the gameroom, that was selected for the player
     * @throws IOException Exception
     */
    public GameRoom addPlayer(Player player) throws IOException, UnsupportedGameMode {
        GameRoom currentGame;
        if(player.getGameRoom() == null ){
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
                    if(currentGames.get(i).getNumberOfPlayer() < 2 && currentGames.get(i).getGame().getName().equals(player.getGame().getName())) {
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
        return player.getGameRoom();
    }   
}
