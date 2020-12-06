package src.chess;

import src.games.Game;

/**
 * ChessGame extends the class Game from src.Games and is used for creating a new chess game.
 *
 * @author Begüm Tosun
 */
public class ChessGame extends Game {

    ChessBoard board;

    public ChessGame(){
        super("Chess");
        initialize();
    }

    @Override
    public void initialize() {
        board = new ChessBoard();
    }

}
