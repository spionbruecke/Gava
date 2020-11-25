package src.chess;

import src.Games.GameBoard;
import src.Games.MoveConverter;
import src.Games.PlayingPiece;

public class ChessPlayingPiece extends PlayingPiece {
    private boolean hasMoved = false;

    @Override
    public void move(GameBoard board, String move) {
        board.setState(MoveConverter.convertStringToState(board, move));
        hasMoved = true;
    }

    public void promotion(String playingPieceChoice) {
        this.setName(playingPieceChoice);
    }

    public boolean hasMoved(){
        return hasMoved;
    }
}
