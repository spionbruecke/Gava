package src.mill;

import src.games.*;

public class MillRules implements Rules {
    private static MillMoveConverter converter = new MillMoveConverter();

    //TODO BEGÜM: kann Spieler keinen Stein mehr bewegen => DEFEAT

    public static Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck) {
        String move = converter.stateToString(gameBoard.getState(), stateToCheck);

        int totalNumOfPiecesPrevious = MillBoard.getNumOfPieces(gameBoard.getState(), "white")
                                        + MillBoard.getNumOfPieces(gameBoard.getState(), "black");
             
        int totalNumOfPiecesToCheck = MillBoard.getNumOfPieces(stateToCheck, "white")
                                        + MillBoard.getNumOfPieces(stateToCheck, "black");


        int targetRow = converter.convertPosIntoArrayCoordinate(move.charAt(3));
        int targetColumn = converter.convertPosIntoArrayCoordinate(move.charAt(4));
        

        //startingPhase
        if((totalNumOfPiecesPrevious < totalNumOfPiecesToCheck) && !finishedStartingPhase(stateToCheck)){
            
            String colour = stateToCheck[targetRow][targetColumn].getColour();
            if(!Rules.isFieldOccupied(gameBoard, targetRow, targetColumn)) {
            	System.out.print(removePiece(gameBoard.getState(), stateToCheck, colour));
                return removePiece(gameBoard.getState(), stateToCheck, colour);
            }else
                return Messages.ERROR_WRONGMOVEMENT;

        }else {
        //startingPhase over
            int row = converter.convertPosIntoArrayCoordinate(move.charAt(0));
            int column = converter.convertPosIntoArrayCoordinate(move.charAt(1));
            String colour = gameBoard.getState()[row][column].getColour();


            switch (colour) {
                case "white":
                    if (MillBoard.getNumOfPieces(gameBoard.getState(), "white") > 3) {
                        if (isTargetValid(move) && !Rules.isFieldOccupied(gameBoard, row, column))
                            return removePiece(gameBoard.getState(), stateToCheck, "white");
                        else
                            return Messages.ERROR_WRONGMOVEMENT;
                    } else {
                        if (!Rules.isFieldOccupied(gameBoard, row, column))
                            return removePiece(gameBoard.getState(), stateToCheck, "white");
                        else
                            return Messages.ERROR_WRONGMOVEMENT;
                    }
                case "black":
                    if (MillBoard.getNumOfPieces(gameBoard.getState(), "black") > 3) {
                        if (isTargetValid(move) && !Rules.isFieldOccupied(gameBoard, row, column))
                            return removePiece(gameBoard.getState(), stateToCheck, "black");
                        else
                            return Messages.ERROR_WRONGMOVEMENT;
                    } else {
                        if (!Rules.isFieldOccupied(gameBoard, row, column))
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
                        if(stateToCheck[k][j].getColour().equals("null") && !board.getState()[k][j].getColour().equals("null")
                            && board.getState()[k][j].getColour().equals(colour))
                            return Messages.MOVE_ALLOWED;
                    }
                else
                    for (int j = 0; j < 3; j ++){
                        if(stateToCheck[k][j].getColour().equals("null") && !board.getState()[k][j].getColour().equals("null")
                                && board.getState()[k][j].getColour().equals(colour))
                            return Messages.MOVE_ALLOWED;
                    }
            }

        }

        return Messages.ERROR_WRONGMOVEMENT;
    }

    //colour of player who made the last move
    static Messages isGameFinished(PlayingPiece[][] stateToCheck, String colour) {
        String opponentColour = "";

        if(colour.equals("white"))
            opponentColour = "black";
        else if(colour.equals("black"))
            opponentColour = "white";

        if(MillBoard.getNumOfPieces(stateToCheck, opponentColour) < 3 && finishedStartingPhase(stateToCheck))
            return Messages.VICTORY;

        return Messages.GO_ON;
    }

    public static Messages executeMove(GameBoard board, String colour, PlayingPiece[][] stateToCheck){
        Messages message;

        message = isMoveAllowed(board, stateToCheck);

        if(message == Messages.MOVE_ALLOWED)
            return isGameFinished(stateToCheck, colour);

        return message;
    }

    public static Messages setToken(GameBoard board, String colour, PlayingPiece[][] stateToCheck){


       return null;
    }

    public static boolean finishedStartingPhase(PlayingPiece[][] stateToCheck){
        MillBoard newBoard = new MillBoard();

        newBoard.setState(stateToCheck);

        for(int i = 0; i < 18; i++){
            if(!newBoard.getPlayingPieces()[i].hasMoved())
                return false;
        }

        return true;
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
        
        if(countCurrentMills < countMillsToBeChecked) 
            return Messages.MOVE_ALLOWED_REMOVE_PIECE;
        else
            return Messages.MOVE_ALLOWED;
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
}
