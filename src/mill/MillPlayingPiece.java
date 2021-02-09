package src.mill;

import src.games.Messages;
import src.games.PlayingPiece;

public class MillPlayingPiece extends PlayingPiece {

    /*colour from player who set the playing piece
    public Messages setPlayingPiece(MillBoard board, String location, String colour){
        int row = MillMoveConverter.convertPosIntoArrayCoordinate(location.charAt(0));
        int column = MillMoveConverter.convertPosIntoArrayCoordinate(location.charAt(1));

        if(board.getState()[row][column].getColour().equals("null")){
            board.getState()[row][column].setColour(colour);
            return Messages.MOVE_ALLOWED;
        }else {
            return Messages.INVALID_TARGET;
        }
    }*/
}
