package src.chess;

import src.games.*;

/**
 * ChessPlayingPiece extends the class PlayingPiece and contains an additional attribute hasMoved.
 *
 * @author Begüm Tosun
 */
public class ChessPlayingPiece extends PlayingPiece {
    private boolean hasMoved = false;
    private ChessMoveConverter converter = new ChessMoveConverter();

    /**
     * The method move(GameBoard gameBoard, String move) executes the move and updates the current state of the game board.
     * @param board GameBoard
     * @param move String
     */
    @Override
    public void move(GameBoard board, String move) {
        board.setState(converter.convertStringToState(board, move));
        hasMoved = true;
    }

    /**
     * Executes the move "promotion" from pawn.
     * @param playingPieceChoice String
     */
    public void promotion(String playingPieceChoice) {
        this.setName(playingPieceChoice);
    }

    /**
     * True if chess playing piece has moved else false.
     * @return boolean
     */
    public boolean hasMoved(){
        return hasMoved;
    }
}
