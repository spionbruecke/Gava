package src.mill;

import src.games.Game;

public class MillGame extends Game {
    MillBoard board;

    public MillGame(String gameName) {
        super(gameName);
        initialize();
    }

    @Override
    public void initialize() {
        board = new MillBoard();
    }
    
}
