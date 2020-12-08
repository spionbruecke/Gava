package src.chess;

import src.games.*;

/**
 * ChessPlayingPiece extends the class PlayingPiece and contains an additional attribute hasMoved.
 *
 * @author Begüm Tosun
 */
public class ChessPlayingPiece {
    private static ChessMoveConverter converter = new ChessMoveConverter();

    /**
     * Executes the move "promotion" from pawn.
     * @param playingPieceChoice String
     */
    public void promotion(String playingPieceChoice, PlayingPiece pawn) {
        pawn.setName(playingPieceChoice);
    }

}
