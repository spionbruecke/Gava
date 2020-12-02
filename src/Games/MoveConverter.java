package src.Games;

import java.lang.Character;

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
     * Converts the new state which is a PlayingPiece[][] into a String which describes the move.
     * Warning!! It is provided that the move is not castling.
     * @param currentState PlayingPiece[][]
     * @param stateToCheck PlayingPiece[][]
     * @return String
     */
    public static String stateToString(PlayingPiece[][] currentState, PlayingPiece[][] stateToCheck){
        // Voraussetzung move != castling
        StringBuilder move = new StringBuilder();
        Field start = new Field();
        Field target = new Field();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentState[i][j].getName() != stateToCheck[i][j].getName() && stateToCheck[i][j].getName() == null){
                    start = new Field(i, j);
                }else if(currentState[i][j].getName() != stateToCheck[i][j].getName() && stateToCheck[i][j].getName() != null){
                    target = new Field(i, j);
                }
            }
        }

        move.append(convertArrayCoordinateIntoPosRow(start.getColumn()));
        move.append(convertArrayCoordinateIntoPosColumn(start.getRow()));
        move.append(" ");
        move.append(convertArrayCoordinateIntoPosRow(target.getColumn()));
        move.append(convertArrayCoordinateIntoPosColumn(target.getRow()));


        return move.toString();
    }

    /**
     * Converts an integer array coordinate into a String description.
     * @param i int
     * @return String
     */
    public static String convertArrayCoordinateIntoPosRow(int i){

        switch (i){
            case 0:
                return "8";

            case 1:
                return "7";

            case 2:
                return "6";

            case 3:
                return "5";

            case 4:
                return "4";

            case 5:
                return "3";

            case 6:
                return "2";

            case 7:
                return "1";

            default:
                return "";
        }
    }

    /**
     * Converts an integer array coordinate into a String description.
     * @param i int
     * @return String
     */
    public static String convertArrayCoordinateIntoPosColumn(int i){
        switch (i){
            case 0:
                return "a";

            case 1:
                return "b";

            case 2:
                return "c";

            case 3:
                return "d";

            case 4:
                return "e";

            case 5:
                return "f";

            case 6:
                return "g";

            case 7:
                return "h";

            default:
                return "";
        }
    }

    /**
     * Converts a String description of a chess square into an array coordinate
     * @param c field-description
     * @return array coordinate
     */
    public static int convertPosIntoArrayCoordinate(char c) {
        if (Character.isLetter(c)){
            c = Character.toLowerCase(c);
        }

        switch(c){
            case 'a':
            case '8':
                return 0;
            case 'b':
            case '7':
                return 1;
            case 'c':
            case '6':
                return 2;
            case 'd':
            case '5':
                return 3;
            case 'e':
            case '4':
                return 4;
            case 'f':
            case '3':
                return 5;
            case 'g':
            case '2':
                return 6;
            case 'h':
            case '1':
                return 7;

            default:
                return -1;
        }
    }

    /**
     * Returns the target square from a given move.
     * @param move String
     * @return Field
     */
    public static Field getTargetField(String move){
        return new Field(convertPosIntoArrayCoordinate(move.charAt(4)),
                convertPosIntoArrayCoordinate(move.charAt(3)));
    }

    /**
     * Returns the current square of a laying piece from a given move.
     * @param move String
     * @return Field
     */
    public static Field getStartField(String move){
        return new Field(convertPosIntoArrayCoordinate(move.charAt(1)),
                convertPosIntoArrayCoordinate(move.charAt(0)));
    }
}
