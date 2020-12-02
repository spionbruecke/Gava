package src.chess;

import src.Games.Game;

/**
 * @author Begüm Tosun
 *
 * ChessGame extends the class Game from src.Games and is used for creating a new chess game.
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

    /*
    @Override
    public void start() {}
    @Override
    public void end() {}
    @Override
    public void play() {}
     */
}
