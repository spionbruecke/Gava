package src.mill;

import src.games.*;

public class MillMoveConverter implements MoveConverter {

    @Override
    public PlayingPiece[][] convertStringToState(GameBoard gameBoard, String move) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String stateToString(PlayingPiece[][] currentState, PlayingPiece[][] stateToCheck) {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {
        MillBoard mill = new MillBoard();
        System.out.println(convertPiecesToString(mill));
    }

    //TODO(Alex) Umwandlung des Spielbretts in einen String 
    public static String convertPiecesToString(MillBoard board) {
        StringBuilder output = new StringBuilder();
        PlayingPiece[][] state = board.getState();


        for(int k = 0 ; k < 7; k ++) {
            if(k == 3)
                for (int j = 0; j < 6; j ++){
                    output.append("<").append(state[k][j].toString()).append(">");
                }
            else
                for (int j = 0; j < 3; j ++){
                    output.append("<").append(state[k][j].toString()).append(">");
                }
        }


        return output.toString() ;
    }

     /**
     * Converts an integer array coordinate into a String description.
     * @param i int
     * @return String
     */
    static String convertArrayCoordinateIntoPosRow(int i){
        switch (i){
            case 0:
                return "7";

            case 1:
                return "6";

            case 2:
                return "5";

            case 3:
                return "4";

            case 4:
                return "3";

            case 5:
                return "2";

            case 6:
                return "1";

            default:
                System.out.println("Error convert into PosRow: " + i);
                return "";
        }
    }

    /**
     * Converts an integer array coordinate into a String description.
     * @param i int
     * @return String
     */
    static String convertArrayCoordinateIntoPosColumn(int i){
        switch (i){
            case 0:
                return "A";

            case 1:
                return "B";

            case 2:
                return "C";

            case 3:
                return "D";

            case 4:
                return "E";

            case 5:
                return "F";

            case 6:
                return "G";

            default:
                return "";
        }
    }

     /**
     * Converts the String list into a two dim. PlayingPiece Array
     * @param input String
     * @return PlayingPiece[][]
     * @throws WrongFormatException
     */
    public static PlayingPiece[][] getBoardFromString(String input) throws WrongFormatException {
       
  
        return null;
    }

    
}
