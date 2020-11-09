package organisation;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.*;
import java.util.*;
import monk.*;
//import game_structur.Games;
//import game_structur.GameBoard;

// This Class starts automatically if the server starts
public class GameController {

    private static List<GameMaster> currentGames;
    private static List<Player> unemployedPlayer;
    private static int numberOfGames;

   public static void main(String[] args) {

        Games game = new Games("Schach");

        currentGames = new ArrayList<>();
        unemployedPlayer = new ArrayList<>();
        numberOfGames = -1;
        /* 
        // unemployedPlayer.add(new Player());
        // unemployedPlayer.add(new Player());

        for (int i = 0; i < unemployedPlayer.size(); i++) {
            unemployedPlayer.get(i).choseGame(game);
            addPlayer(unemployedPlayer.get(i));
            unemployedPlayer.remove(i);
            i--;
        }

        for (GameMaster masters : currentGames) {
            System.out.println(masters);
        }*/
    }

    public static GameMaster openNewRoom(Games game) {
        currentGames.add(new GameMaster(game));
        numberOfGames++;
        return currentGames.get(numberOfGames);
    }

    // Hier muss man auf Abhängigkeit vom gewählten Spiel schauen[Also nochmal
    // überarbeiten]
    public GameMaster addPlayer(Player player) {
        GameMaster currentGame;

        if(numberOfGames == -1){
            currentGame = openNewRoom(player.getGame());
            player.setGameMaster(currentGame);
            return currentGame;
            // im else ist noch ein Fehler vorhanden!
        } else {
            for(int i = 0 ; i < currentGames.size(); i ++){
                if(currentGames.get(i).getNumberOfPlayer() < 2 && currentGames.get(i).getGame() == player.getGame()){
                    currentGames.get(i).addPlayer(player);
                    player.setGameMaster(currentGames.get(i));
                    return  currentGames.get(i);
                }
            }
            currentGame = openNewRoom(player.getGame());
            player.setGameMaster(currentGame);
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
