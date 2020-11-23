package src.chess;

import src.Games.GameBoard;
import src.Games.PlayingPiece;
import src.Games.Rules;

public class ChessRules implements Rules {

    @Override
    public boolean isFieldOccupiedByOwnPlayingP(GameBoard board, String move) {
        int x = move.charAt(4);
        int y = move.charAt(3);

        String ownColour = board.getState()[(int)(move.charAt(1))][(int)(move.charAt(0))].getColour();

        if(board.getState()[x][y]==null){
            return false;
        }else if(board.getState()[x][y].getColour().equals(ownColour)){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean isMoveAllowed(xxx) {
        boolean permission;

        switch(playingPiece.getName()){
            case "rook":
                //permission = check moves for rook
                break;

            case "knight":
                //check moves for knight
                break;

            case "bishop":
                //check...
                break;

            case "queen":
                //...
                break;

            case "king":
                //...
                break;

            case "pawn":
                //...
                break;

            default:
                // error
                break;
        }

        return permission;
    }


}
