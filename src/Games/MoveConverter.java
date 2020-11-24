package src.Games;

/**
 * The client only returns a simple String-description
 * of the move. MoveConverter converts this String into
 * the current state of the gameBoard.
 * @author Begüm Tosun
 */

public class MoveConverter {

    /**
     * This method gets a String description of the move
     * and converts it onto the current game board state
     * which is a two dimensional PlayingPiece array.
     * @param gameBoard GameBoard
     * @param move String
     * @return currentState: PlayingPiece[][]
     */
    public static PlayingPiece[][] convertStringToState(GameBoard gameBoard, String move){
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
    public static int convertPosIntoArrayCoordinate(char c) {
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

    public static Field getTargetField(String move){
        return new Field(convertPosIntoArrayCoordinate(move.charAt(4)),
                convertPosIntoArrayCoordinate(move.charAt(3)));
    }

    public static Field getStartField(String move){
        return new Field(convertPosIntoArrayCoordinate(move.charAt(1)),
                convertPosIntoArrayCoordinate(move.charAt(0)));
    }
}
