package src.chess;

import src.games.*;
//import sun.tools.jar.resources.jar;

/**
 * @author Alexander Posch, Begüm Tosun
 */

public class ChessMoveConverter implements MoveConverter {

    /**
     * Converts the new state which is a PlayingPiece[][] into a String which describes the move.
     * Warning!! It is provided that the move is not castling.
     * @param currentState PlayingPiece[][]
     * @param stateToCheck PlayingPiece[][]
     * @return String
     */
    public String stateToString(PlayingPiece[][] currentState, PlayingPiece[][] stateToCheck){
        // Voraussetzung move != castling
        StringBuilder move = new StringBuilder();
        Field start = new Field();
        Field target = new Field();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!currentState[i][j].getName().equals("null") && stateToCheck[i][j].getName().equals("null")){
                    start = new Field(i, j);
                }else if(!currentState[i][j].getColour().equals(stateToCheck[i][j].getColour()) && !stateToCheck[i][j].getName().equals("null")){
                    target = new Field(i, j);
                }
            }
        }

        
        move.append(convertArrayCoordinateIntoPosColumn(start.getColumn()));
        move.append(convertArrayCoordinateIntoPosRow(start.getRow()));
        move.append(" ");
       
        move.append(convertArrayCoordinateIntoPosColumn(target.getColumn()));
        move.append(convertArrayCoordinateIntoPosRow(target.getRow()));

        return move.toString();
    }

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

        if(newPosColumn != -2 && newPosRow != -2)
            currentState[newPosRow][newPosColumn] = currentState[oldPosRow][oldPosColumn];
        currentState[oldPosRow][oldPosColumn] = new PlayingPiece();
        currentState[oldPosRow][oldPosColumn].setName("null");
        currentState[oldPosRow][oldPosColumn].setColour("null");

        return currentState;
    }


     /**
     * Converts field name into array coordinate
     * @param c field-description
     * @return array coordinate
     */
    public static int convertPosIntoArrayCoordinate(char c){
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

            case 'n':
            case 'u':
                return -2;
            default:
                System.out.println("WrongFormatException: -1 returned");
                return -1;
        }
    }

    /**
     * Converts an integer array coordinate into a String description.
     * @param i int
     * @return String
     */
    static String convertArrayCoordinateIntoPosRow(int i){
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

            case 7:
                return "H";

            default:
                return "";
        }
    }

    /**
     * Returns the target square from a given move.
     * @param move String
     * @return Field
     */
    static Field getChessTargetField(String move){
        
        return new Field(convertPosIntoArrayCoordinate(move.charAt(4)),
                convertPosIntoArrayCoordinate(move.charAt(3)));
    }

    /**
     * Returns the current square of a laying piece from a given move.
     * @param move String
     * @return Field
     */
    static Field getChessStartField(String move){
        return new Field(convertPosIntoArrayCoordinate(move.charAt(1)),
                convertPosIntoArrayCoordinate(move.charAt(0)));
    }

    /**
     * Converts the String list (not the simple String "A1 A2") into a two dim. PlayingPiece Array
     * @param input String
     * @return PlayingPiece[][]
     * @throws WrongFormatException
     */
    public static PlayingPiece[][] getBoardFromString(String input) throws WrongFormatException {
        ChessBoard board = new ChessBoard();
        PlayingPiece[][] newBoard = new PlayingPiece[8][8];
        StringBuilder position = new StringBuilder();
        int counter = 0;
        int column;
        int row;

        for(int i = 0; i < 8;i++){
            for(int j = 0; j < 8; j++){
                newBoard[j][i] = new PlayingPiece();
                newBoard[j][i].setName("null");
                newBoard[j][i].setColour("null");
            }
        }


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


    //constructs the String list
    public static String convertPiecesToString(ChessBoard board) {
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < 32; i++){
            output.append("<");
            output.append(board.getPlayingPieces()[i]);
            output.append(">");
        }

        return output.toString();
    }

}
