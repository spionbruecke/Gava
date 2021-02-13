package src.mill;

import src.games.*;

/**
 * MillRules implements Rules from src.Games and checks if the given move is valid.
 * 
 * @author Begüm Tosun, Alexander Posch
 */
public class MillRules implements Rules {

    public static Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck,int roundnumber) {
        final MillMoveConverter CONVERTER = new MillMoveConverter();
        String move = CONVERTER.stateToString(gameBoard.getState(), stateToCheck);

        int totalNumOfPiecesPrevious = MillBoard.getNumOfPieces(gameBoard.getState(), "white")
                                        + MillBoard.getNumOfPieces(gameBoard.getState(), "black");
             
        int totalNumOfPiecesToCheck = MillBoard.getNumOfPieces(stateToCheck, "white")
                                        + MillBoard.getNumOfPieces(stateToCheck, "black");


        int targetRow = MillMoveConverter.convertPosIntoArrayCoordinate(move.charAt(3));
        int targetColumn = MillMoveConverter.convertPosIntoArrayCoordinate(move.charAt(4));
        

        //startingPhase
        if(roundnumber < 18){
            String colour = stateToCheck[targetRow][targetColumn].getColour();
            if((totalNumOfPiecesPrevious < totalNumOfPiecesToCheck) && !Rules.isFieldOccupied(gameBoard.getState(), targetRow, targetColumn)) {
                return removePiece(gameBoard.getState(), stateToCheck, colour);
            }else
                return Messages.ERROR_WRONGMOVEMENT;

        }else {
        //startingPhase over
            int row = MillMoveConverter.convertPosIntoArrayCoordinate(move.charAt(0));
            int column = MillMoveConverter.convertPosIntoArrayCoordinate(move.charAt(1));
            String colour = gameBoard.getState()[row][column].getColour();


            switch (colour) {
                case "white":

                    if (MillBoard.getNumOfPieces(gameBoard.getState(), "white") > 3) {
                        if (isTargetValid(move) && !Rules.isFieldOccupied(gameBoard.getState(), targetRow, targetColumn))
                            return removePiece(gameBoard.getState(), stateToCheck, "white");
                        else
                            return Messages.ERROR_WRONGMOVEMENT;
                    } else {
                        if (!Rules.isFieldOccupied(gameBoard.getState(), targetRow, targetColumn))
                            return removePiece(gameBoard.getState(), stateToCheck, "white");
                        else
                            return Messages.ERROR_WRONGMOVEMENT;
                    }
                case "black":
                    if (MillBoard.getNumOfPieces(gameBoard.getState(), "black") > 3) {
                        if (isTargetValid(move) && !Rules.isFieldOccupied(gameBoard.getState(), targetRow, targetColumn))
                            return removePiece(gameBoard.getState(), stateToCheck, "black");
                        else
                            return Messages.ERROR_WRONGMOVEMENT;
                    } else {
                        if (!Rules.isFieldOccupied(gameBoard.getState(), targetRow, targetColumn))
                            return removePiece(gameBoard.getState(), stateToCheck, "black");
                        else
                            return Messages.ERROR_WRONGMOVEMENT;
                    }
                default:
                    return null;
            }

        }
    }

    //colour of piece which has been removed
    public static Messages checkRemovedPiece(GameBoard board, PlayingPiece[][] stateToCheck, String colour){
        int totalNumOfPiecesPrevious = MillBoard.getNumOfPieces(board.getState(), "white")
                + MillBoard.getNumOfPieces(board.getState(), "black");

        int totalNumOfPiecesToCheck = MillBoard.getNumOfPieces(stateToCheck, "white")
                + MillBoard.getNumOfPieces(stateToCheck, "black");

        if(totalNumOfPiecesPrevious > totalNumOfPiecesToCheck){

            for(int k = 0 ; k < 7; k ++) {
                if(k == 3)
                    for (int j = 0; j < 6; j ++){
                        if(stateToCheck[k][j].getColour().equals("null")
                            && !board.getState()[k][j].getColour().equals("null")
                            && board.getState()[k][j].getColour().equals(colour)
                            && !isPlayingPiecePartOfMill(board, k, j, colour))
                            return Messages.MOVE_ALLOWED;
                    }
                else
                    for (int j = 0; j < 3; j ++){
                        if(stateToCheck[k][j].getColour().equals("null")
                            && !board.getState()[k][j].getColour().equals("null")
                            && board.getState()[k][j].getColour().equals(colour)
                            && !isPlayingPiecePartOfMill(board, k, j, colour))
                            return Messages.MOVE_ALLOWED;
                    }
            }
        }

        return Messages.ERROR_WRONGMOVEMENT;
    }

    //colour of removed piece
    private static boolean isPlayingPiecePartOfMill(GameBoard board, int row, int column, String colour){
        boolean[] mills = threeInARow(board.getState(), colour);

        if(row == 0 && column == 0)
            return mills[0] || mills[8];

        else if(row == 0 && column == 1)
            return mills[0] || mills[11];

        else if(row == 0 && column == 2)
            return mills[0] || mills[15];

        else if(row == 1 && column == 0)
            return mills[1] || mills[9];

        else if(row == 1 && column == 1)
            return mills[1] || mills[11];

        else if(row == 1 && column == 2)
            return mills[1] || mills[14];

        else if(row == 2 && column == 0)
            return mills[2] || mills[10];

        else if(row == 2 && column == 1)
            return mills[2] || mills[11];

        else if(row == 2 && column == 2)
            return mills[2] || mills[13];

        else if(row == 3 && column == 0)
            return mills[3] || mills[8];

        else if(row == 3 && column == 1)
            return mills[3] || mills[9];

        else if(row == 3 && column == 2)
            return mills[3] || mills[10];

        else if(row == 3 && column == 3)
            return mills[4] || mills[13];

        else if(row == 3 && column == 4)
            return mills[4] || mills[14];

        else if(row == 3 && column == 5)
            return mills[4] || mills[15];

        else if(row == 4 && column == 0)
            return mills[5] || mills[10];

        else if(row == 4 && column == 1)
            return mills[5] || mills[12];

        else if(row == 4 && column == 2)
            return mills[5] || mills[13];

        else if(row == 5 && column == 0)
            return mills[6] || mills[9];

        else if(row == 5 && column == 1)
            return mills[6] || mills[12];

        else if(row == 5 && column == 2)
            return mills[6] || mills[14];

        else if(row == 6 && column == 0)
            return mills[7] || mills[8];

        else if(row == 6 && column == 1)
            return mills[7] || mills[12];

        else if(row == 6 && column == 2)
            return mills[7] || mills[15];


        return false;
    }

    //colour of player who made the last move
    public static Messages isGameFinished(PlayingPiece[][] stateToCheck, String colour,int roundnumber) {
        String opponentColour = "";

        if(colour.equals("white"))
            opponentColour = "black";
        else if(colour.equals("black"))
            opponentColour = "white";

        if((MillBoard.getNumOfPieces(stateToCheck, opponentColour) < 3 && roundnumber >= 18)
            || !canPlayingPieceMove(stateToCheck, colour))
            return Messages.VICTORY;

        return Messages.GO_ON;
    }

    public static Messages executeMove(GameBoard board, String colour, PlayingPiece[][] stateToCheck, int roundnumber){
        Messages message = isMoveAllowed(board, stateToCheck,roundnumber);


        if(message == Messages.MOVE_ALLOWED)
            return isGameFinished(stateToCheck, colour,roundnumber);

        return message;
    }

    //method should only be called if the move was checked to be true
    private static Messages removePiece(PlayingPiece[][] currentState, PlayingPiece[][] stateToCheck, String colour){
        boolean[] currentMills = threeInARow(currentState, colour);
        boolean[] millsToBeChecked = threeInARow(stateToCheck, colour);

        int countCurrentMills = 0;
        int countMillsToBeChecked = 0;

        for (int i = 0; i < currentMills.length; i++) {
            if(currentMills[i])
                countCurrentMills++;

            if(millsToBeChecked[i])
                countMillsToBeChecked++;
        }

        if( ( (countCurrentMills == countMillsToBeChecked) && !areArraysIdentical(currentMills, millsToBeChecked) )
            || (countCurrentMills < countMillsToBeChecked) )
            return Messages.MOVE_ALLOWED_REMOVE_PIECE;

        return Messages.MOVE_ALLOWED;
    }

    private static boolean areArraysIdentical(boolean[] a, boolean[] b){
        if(a.length != b.length)
            return false;

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i])
                return false;
        }

        return true;
    }

    private static boolean[] threeInARow(PlayingPiece[][] stateToCheck, String colour){
        boolean[] millsList = new boolean[16];

        //check rows
        if(stateToCheck[0][0].getColour().equals(colour)
                && stateToCheck[0][0].getColour().equals(stateToCheck[0][1].getColour())
                && stateToCheck[0][0].getColour().equals(stateToCheck[0][2].getColour()))
            millsList[0] = true;
        
        if(stateToCheck[1][0].getColour().equals(colour)
                && stateToCheck[1][0].getColour().equals(stateToCheck[1][1].getColour())
                && stateToCheck[1][0].getColour().equals(stateToCheck[1][2].getColour()))
            millsList[1] = true;

        if(stateToCheck[2][0].getColour().equals(colour)
                && stateToCheck[2][0].getColour().equals(stateToCheck[2][1].getColour())
                && stateToCheck[2][0].getColour().equals(stateToCheck[2][2].getColour()))
            millsList[2] = true;
        
        if(stateToCheck[3][0].getColour().equals(colour)
                && stateToCheck[3][0].getColour().equals(stateToCheck[3][1].getColour())
                && stateToCheck[3][0].getColour().equals(stateToCheck[3][2].getColour()))
            millsList[3] = true;

        if(stateToCheck[3][3].getColour().equals(colour)
                && stateToCheck[3][3].getColour().equals(stateToCheck[3][4].getColour())
                && stateToCheck[3][3].getColour().equals(stateToCheck[3][5].getColour()))
            millsList[4] = true;
            
        if(stateToCheck[4][0].getColour().equals(colour)
                && stateToCheck[4][0].getColour().equals(stateToCheck[4][1].getColour())
                && stateToCheck[4][0].getColour().equals(stateToCheck[4][2].getColour()))
            millsList[5] = true;
        
        if(stateToCheck[5][0].getColour().equals(colour)
                && stateToCheck[5][0].getColour().equals(stateToCheck[5][1].getColour())
                && stateToCheck[5][0].getColour().equals(stateToCheck[5][2].getColour()))
            millsList[6] = true;
        
        if(stateToCheck[6][0].getColour().equals(colour)
                && stateToCheck[6][0].getColour().equals(stateToCheck[6][1].getColour())
                && stateToCheck[6][0].getColour().equals(stateToCheck[6][2].getColour()))
            millsList[7] = true;
        

        //check columns
        if(stateToCheck[0][0].getColour().equals(colour)
                && stateToCheck[0][0].getColour().equals(stateToCheck[3][0].getColour())
                && stateToCheck[0][0].getColour().equals(stateToCheck[6][0].getColour()))
            millsList[8] = true;
        
        if(stateToCheck[1][0].getColour().equals(colour)
                && stateToCheck[1][0].getColour().equals(stateToCheck[3][1].getColour())
                && stateToCheck[1][0].getColour().equals(stateToCheck[5][0].getColour()))
            millsList[9] = true;

        if(stateToCheck[2][0].getColour().equals(colour)
                && stateToCheck[2][0].getColour().equals(stateToCheck[3][2].getColour())
                && stateToCheck[2][0].getColour().equals(stateToCheck[4][0].getColour()))
            millsList[10] = true;

        if(stateToCheck[0][1].getColour().equals(colour)
                && stateToCheck[0][1].getColour().equals(stateToCheck[1][1].getColour())
                && stateToCheck[0][1].getColour().equals(stateToCheck[2][1].getColour()))
            millsList[11] = true;

        if(stateToCheck[4][1].getColour().equals(colour)
                && stateToCheck[4][1].getColour().equals(stateToCheck[5][1].getColour())
                && stateToCheck[4][1].getColour().equals(stateToCheck[6][1].getColour()))
            millsList[12] = true;

        if(stateToCheck[2][2].getColour().equals(colour)
                && stateToCheck[2][2].getColour().equals(stateToCheck[3][3].getColour())
                && stateToCheck[2][2].getColour().equals(stateToCheck[4][2].getColour()))
            millsList[13] = true;

        if(stateToCheck[1][2].getColour().equals(colour)
                && stateToCheck[1][2].getColour().equals(stateToCheck[3][4].getColour())
                && stateToCheck[1][2].getColour().equals(stateToCheck[5][2].getColour()))
            millsList[14] = true;

        if(stateToCheck[0][2].getColour().equals(colour)
                && stateToCheck[0][2].getColour().equals(stateToCheck[3][5].getColour())
                && stateToCheck[0][2].getColour().equals(stateToCheck[6][2].getColour()))
            millsList[15] = true;

        return millsList;
    }

    private static boolean isTargetValid(String move){
        String start = move.substring(0, 2);
        String target = move.substring(3, 5);

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

    //opponentColour
    private static boolean canPlayingPieceMove(PlayingPiece[][] stateToCheck ,String colour){
        for(int k = 0 ; k < 7; k ++) {
            if(k == 3)
                for (int j = 0; j < 6; j ++){
                    if(stateToCheck[k][j].getColour().equals(colour) && possibleTargetExists(stateToCheck, k, j))
                        return true;
                }
            else
                for (int j = 0; j < 3; j ++){
                    if(stateToCheck[k][j].getColour().equals(colour) && possibleTargetExists(stateToCheck, k, j))
                        return true;
                }
        }

        return false;
    }

    private static boolean possibleTargetExists(PlayingPiece[][] stateToCheck, int row, int column){
        String location = MillMoveConverter.convertArrayCoordinateIntoPosRow(row) +
                MillMoveConverter.convertArrayCoordinateIntoPosColumn(column);

        switch (location){
            case "1A":
                return !Rules.isFieldOccupied(stateToCheck, 3, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 6, 1);

            case "1B":
                return !Rules.isFieldOccupied(stateToCheck, 6, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 5, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 6, 2);

            case "1C":
                return !Rules.isFieldOccupied(stateToCheck, 6, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 5);

            case "2A":
                return !Rules.isFieldOccupied(stateToCheck, 3, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 5, 1);

            case "2B":
                return !Rules.isFieldOccupied(stateToCheck, 5, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 4, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 5, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 6, 1);

            case "2C":
                return !Rules.isFieldOccupied(stateToCheck, 5, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 4);

            case "3A":
                return !Rules.isFieldOccupied(stateToCheck, 3, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 4, 1);

            case "3B":
                return !Rules.isFieldOccupied(stateToCheck, 4, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 5, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 4, 2);

            case "3C":
                return !Rules.isFieldOccupied(stateToCheck, 4, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 3);

            case "4A":
                return !Rules.isFieldOccupied(stateToCheck, 0, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 6, 0);

            case "4B":
                return !Rules.isFieldOccupied(stateToCheck, 1, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 5, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 0);

            case "4C":
                return !Rules.isFieldOccupied(stateToCheck, 2, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 4, 0);

            case "4D":
                return !Rules.isFieldOccupied(stateToCheck, 2, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 4)
                        || !Rules.isFieldOccupied(stateToCheck, 4, 2);

            case "4E":
                return !Rules.isFieldOccupied(stateToCheck, 1, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 5)
                        || !Rules.isFieldOccupied(stateToCheck, 5, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 3);

            case "4F":
                return !Rules.isFieldOccupied(stateToCheck, 0, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 6, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 4);

            case "5A":
                return !Rules.isFieldOccupied(stateToCheck, 3, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 2, 1);

            case "5B":
                return !Rules.isFieldOccupied(stateToCheck, 2, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 1, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 2, 2);

            case "5C":
                return !Rules.isFieldOccupied(stateToCheck, 2, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 3);

            case "6A":
                return !Rules.isFieldOccupied(stateToCheck, 3, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 1, 1);

            case "6B":
                return !Rules.isFieldOccupied(stateToCheck, 0, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 1, 2)
                        || !Rules.isFieldOccupied(stateToCheck, 2, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 1, 0);

            case "6C":
                return !Rules.isFieldOccupied(stateToCheck, 1, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 4);

            case "7A":
                return !Rules.isFieldOccupied(stateToCheck, 3, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 0, 1);

            case "7B":
                return !Rules.isFieldOccupied(stateToCheck, 0, 0)
                        || !Rules.isFieldOccupied(stateToCheck, 1, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 0, 2);

            case "7C":
                return !Rules.isFieldOccupied(stateToCheck, 0, 1)
                        || !Rules.isFieldOccupied(stateToCheck, 3, 5);

            default:
                return false;
        }
    }
}
