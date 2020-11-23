package src.chess;

import src.Games.GameBoard;
import src.Games.MoveConverter;
import src.Games.PlayingPiece;

public class ChessPlayingPiece extends PlayingPiece {
    private boolean hasMoved = false;

    @Override
    public void move(GameBoard board, String move) {
        MoveConverter m = new MoveConverter();
        board.setState(m.convertStringToState(board, move));
        hasMoved = true;
    }

    public void promotion(String playingPieceChoice) {
        this.setName(playingPieceChoice);
    }

    public boolean hasMoved(){
        return hasMoved;
    }
}
