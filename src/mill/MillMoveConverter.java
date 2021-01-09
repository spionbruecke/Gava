package src.mill;

import src.games.*;

public class MillMoveConverter implements MoveConverter {

    @Override
    public PlayingPiece[][] convertStringToState(GameBoard gameBoard, String move) {
        return null;
    }

    @Override
    public String stateToString(PlayingPiece[][] currentState, PlayingPiece[][] stateToCheck) {
        return null;
    }

    public static void main(String[] args) {
        MillBoard mill = new MillBoard();
        MillBoard newMill = new MillBoard();

        try {
            newMill.setNewBoard("<token,white,0=A7><token,white,0=C4><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,white,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null><token,black,0=null>");
            mill.setNewBoard(convertPiecesToString(newMill));
        
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }

        System.out.println(convertPiecesToString(mill));
    }
 
    public static String convertPiecesToString(MillBoard board) {
        StringBuilder output = new StringBuilder();
        PlayingPiece[] playingPieces = board.getPlayingPieces();


        for(int i = 0 ; i < 18; i ++) {
            output.append("<").append(playingPieces[i].toString()).append(">");
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
     * Converts a String description into an integer array coordinate.
     * @param i char
     * @return int
     */
    static int convertPosIntoArrayCoordinate(char i){
        switch (i){
            case '7':
            case 'A':
                return 0;

            case '6':
            case 'B':
                return 1;

            case '5':
            case 'C':
                return 2;

            case '4':
            case 'D':
                return 3;

            case '3':
            case 'E':
                return 4;

            case '2':
            case 'F':
                return 5;

            case '1':
                return 6;

            case 'n':
            case 'u':
                return -2;
            default:
                System.out.println("Error convert PosRow into Coordinate: " + i);
                return -1;
        }
    }

     /**
     * Converts the String list into a two dim. PlayingPiece Array
     * @param input String
     * @return PlayingPiece[][]
     * @throws WrongFormatException
     */
    public static PlayingPiece[][] getBoardFromString(String input) throws WrongFormatException {
        MillBoard board = new MillBoard();
        PlayingPiece[][] newBoard = board.getState();
        StringBuilder position = new StringBuilder();
        int row;
        int column;
        int counter = 0;

        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '='){
                if(input.charAt(i + 1) == 'n')
                    position.append("null");
                else
                    position.append(input.charAt(i + 1)).append(input.charAt(i + 2));

                if(input.charAt(i - 1) == '1')
                    board.getPlayingPieces()[counter].setHasMoved();

                board.getPlayingPieces()[counter].setPosition(position.toString());
                row = convertPosIntoArrayCoordinate((input.charAt(i+2)));
                column = convertPosIntoArrayCoordinate(input.charAt(i + 1));
                if (row != -2 && column != -2)
                    newBoard[row][column] = board.getPlayingPieces()[counter];

                
                position = new StringBuilder();
                counter++;
                i = i + 3;
            }
        }
  
        return newBoard;
    }

    
}
