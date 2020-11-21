package src.games;

public class MoveConverter {

    
    //public MoveConverter(){}

    /*
       This method gets a String description of the move
       and converts it onto the current game board state
       which is a two dimensional String array.
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

    // converts field name into array coordinate
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
