package src.monk;

import src.games.PlayingPiece;
import src.games.Rules;

public class SchachRules implements Rules {

    @Override
    public boolean isFieldOccupied(int field) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isMoveAllowed(PlayingPiece[][] oldState, PlayingPiece[][] newState) {
        // TODO Auto-generated method stub
        return true;
    }
    

    
}
