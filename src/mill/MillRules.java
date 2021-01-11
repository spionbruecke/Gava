package src.mill;

import src.games.*;

public class MillRules implements Rules {

    public static Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck) {
        return null;
    }

    static Messages isGameFinished(GameBoard board, String colour) {
        
        return Messages.DEFEATED;
    }

    public static Messages executeMove(GameBoard board, String colour, PlayingPiece[][] stateToCheck){

        return null;
    }
    
}
