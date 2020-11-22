package src.games;

/**
 * The client only returns a simple String-description
 * of the move. MoveConverter converts this String into
 * the current state of the gameBoard.
 * @author Begüm Tosun
 */

public class MoveConverter {

    /**
     * Default constructor
     */
    public MoveConverter(){}

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
        int newPosX = convertPosIntoArrayCoordinate(move.charAt(4));
        int newPosY = convertPosIntoArrayCoordinate(move.charAt(3));
        int oldPosX = convertPosIntoArrayCoordinate(move.charAt(1));
        int oldPosY = convertPosIntoArrayCoordinate(move.charAt(0));

        currentState[newPosX][newPosY] = currentState[oldPosX][oldPosY];

        currentState[oldPosX][oldPosY] = null;

        return currentState;
    }

    /**
     * Converts field name into array coordinate
     * @param c field-description
     * @return array coordinate
     */
    private static int convertPosIntoArrayCoordinate(char c) {
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
