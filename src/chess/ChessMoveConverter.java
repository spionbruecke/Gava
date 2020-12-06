package src.chess;

import src.games.*;

public class ChessMoveConverter implements MoveConverter {
    

    /**
     * This method gets a String description of the move
     * and converts it onto the current game board state
     * which is a two dimensional PlayingPiece array.
     * @param gameBoard GameBoard
     * @param move String
     * @return currentState: PlayingPiece[][]
     */
    public PlayingPiece[][] convertStringToState(GameBoard gameBoard, String move){
        PlayingPiece[][] currentState = gameBoard.getState();
        int newPosRow = convertPosIntoArrayCoordinate(move.charAt(4));
        int newPosColumn = convertPosIntoArrayCoordinate(move.charAt(3));
        int oldPosRow = convertPosIntoArrayCoordinate(move.charAt(1));
        int oldPosColumn = convertPosIntoArrayCoordinate(move.charAt(0));

        currentState[newPosRow][newPosColumn] = currentState[oldPosRow][oldPosColumn];

        currentState[oldPosRow][oldPosColumn] = null;

        return currentState;
    }


     /**
     * Converts field name into array coordinate
     * @param c field-description
     * @return array coordinate
     */
    public int convertPosIntoArrayCoordinate(char c) {
        switch(c){
            case 'A':
            case '8':
                return 0;
            case 'B':
            case '7':
                return 1;
            case 'C':
            case '6':
                return 2;
            case 'D':
            case '5':
                return 3;
            case 'E':
            case '4':
                return 4;
            case 'F':
            case '3':
                return 5;
            case 'G':
            case '2':
                return 6;
            case 'H':
            case '1':
                return 7;

            default:
                return -1;
        }
    }

}
