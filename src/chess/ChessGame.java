package src.chess;

import src.Games.Game;

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
