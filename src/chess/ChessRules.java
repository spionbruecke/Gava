package src.chess;

import src.Games.PlayingPiece;
import src.Games.Rules;

public class ChessRules implements Rules {
    @Override
    public boolean isFieldOccupied(int x, int y) {
        return false;
    }

    @Override
    public boolean isMoveAllowed(PlayingPiece[][] oldState, PlayingPiece[][] newState) {
        return false;
    }
}
