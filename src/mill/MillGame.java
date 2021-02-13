package src.mill;

import src.games.Game;

/**
 * MillGame extends the class Game from src.Games and is used for creating a new mill game.
 * 
 * @author Begüm Tosun
 */
public class MillGame extends Game {
    MillBoard board;

    public MillGame() {
        super("mill");
        initialize();
    }

    @Override
    public void initialize() {
        board = new MillBoard();
    }
    
}
