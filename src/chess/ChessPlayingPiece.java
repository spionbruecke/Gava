package src.chess;

import src.Games.*;

public class ChessPlayingPiece extends PlayingPiece {
    private boolean hasMoved = false;
    private ChessMoveKonverter konverter = new ChessMoveKonverter();

    @Override
    public void move(GameBoard board, String move) {
        board.setState(konverter.convertStringToState(board, move));
        hasMoved = true;
    }

    public void promotion(String playingPieceChoice) {
        this.setName(playingPieceChoice);
    }

    public boolean hasMoved(){
        return hasMoved;
    }
}
