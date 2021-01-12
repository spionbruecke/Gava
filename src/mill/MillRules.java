package src.mill;

import src.games.*;

public class MillRules implements Rules {
    private static MillMoveConverter converter = new MillMoveConverter();

    public static Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck) {
        String move = converter.stateToString(gameBoard.getState(), stateToCheck);
        int row = converter.convertPosIntoArrayCoordinate(move.charAt(0));
        int column = converter.convertPosIntoArrayCoordinate(move.charAt(1));
        String colour = gameBoard.getState()[row][column].getColour();

        switch (colour){
            case "white":
                if(MillBoard.getNumOfPieces(gameBoard.getState(), "white") > 3){
                    if(isTargetValid(move) && !Rules.isFieldOccupied(gameBoard, row, column))
                        return Messages.MOVE_ALLOWED;
                    else
                        return Messages.INVALID_TARGET;
                }else {
                    if(!Rules.isFieldOccupied(gameBoard, row, column))
                        return Messages.MOVE_ALLOWED;
                    else
                        return Messages.INVALID_TARGET;
                }
            case "black":
                if(MillBoard.getNumOfPieces(gameBoard.getState(), "black") > 3){
                    if(isTargetValid(move) && !Rules.isFieldOccupied(gameBoard, row, column))
                        return Messages.MOVE_ALLOWED;
                    else
                        return Messages.INVALID_TARGET;
                }else {
                    if(!Rules.isFieldOccupied(gameBoard, row, column))
                        return Messages.MOVE_ALLOWED;
                    else
                        return Messages.INVALID_TARGET;
                }
            default:
                return null;
        }
    }

    //colour of player who made the last move
    static Messages isGameFinished(GameBoard board, String colour) {
        String opponentColour = "";

        if(colour.equals("white"))
            opponentColour = "black";
        else if(colour.equals("black"))
            opponentColour = "white";

        if(MillBoard.getNumOfPieces(board.getState(), opponentColour) < 3)
            return Messages.VICTORY;

        return Messages.GO_ON;
    }

    public static Messages executeMove(GameBoard board, String colour, PlayingPiece[][] stateToCheck){
        Messages message;

        message = isMoveAllowed(board, stateToCheck);
        if(message == Messages.MOVE_ALLOWED)
            return isGameFinished(board, colour);

        return message;
    }

    public static boolean isTargetValid(String move){
        String start = move.substring(0, 2);
        String target = move.substring(3, 5);
        System.out.println(target);

        switch (start){
            case "1A":
                return target.equals("1B") || target.equals("4A");

            case "1B":
                return target.equals("1A") || target.equals("1C") || target.equals("2B");

            case "1C":
                return target.equals("1B") || target.equals("4F");

            case "2A":
                return target.equals("4B") || target.equals("2B");

            case "2B":
                return target.equals("2A") || target.equals("3B") || target.equals("2C") || target.equals("1B");

            case "2C":
                return target.equals("2B") || target.equals("4E");

            case "3A":
                return target.equals("4C") || target.equals("3B");

            case "3B":
                return target.equals("3A") || target.equals("2B") || target.equals("3C");

            case "3C":
                return target.equals("3B") || target.equals("4D");

            case "4A":
                return target.equals("7A") || target.equals("4B") || target.equals("1A");

            case "4B":
                return target.equals("4A") || target.equals("6A") || target.equals("4C") || target.equals("2A");

            case "4C":
                return target.equals("4B") || target.equals("5A") || target.equals("3A");

            case "4D":
                return target.equals("5C") || target.equals("4E") || target.equals("3C");

            case "4E":
                return target.equals("4D") || target.equals("6C") || target.equals("4F") || target.equals("2C");

            case "4F":
                return target.equals("7C") || target.equals("4E") || target.equals("1C");

            case "5A":
                return target.equals("4C") || target.equals("5B");

            case "5B":
                return target.equals("5A") || target.equals("6B") || target.equals("5C");

            case "5C":
                return target.equals("5B") || target.equals("4D");

            case "6A":
                return target.equals("4B") || target.equals("6B");

            case "6B":
                return target.equals("6A") || target.equals("7B") || target.equals("6C") || target.equals("5B");

            case "6C":
                return target.equals("6B") || target.equals("4E");

            case "7A":
                return target.equals("4A") || target.equals("7B");

            case "7B":
                return target.equals("7A") || target.equals("6B") || target.equals("7C");

            case "7C":
                return target.equals("7B") || target.equals("4F");

            default:
                return false;
        }

    }
}
