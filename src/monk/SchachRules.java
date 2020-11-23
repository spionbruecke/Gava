package src.monk;

import src.Games.PlayingPiece;
import src.Games.Rules;

public class SchachRules implements Rules {

    @Override
    public boolean isFieldOccupied(int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isMoveAllowed(PlayingPiece[][] oldState, PlayingPiece[][] newState) {
        // TODO Auto-generated method stub
        return true;
    }
    

    
}
