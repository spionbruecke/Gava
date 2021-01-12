package src.mill;

import src.games.Game;

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
