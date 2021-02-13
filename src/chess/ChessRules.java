package src.chess;

import java.util.ArrayList;
import src.games.*;
import src.organisation.Player;


/**
 * ChessRules implements Rules from src.Games and checks the chess rules.
 *
 * @author Begüm Tosun, Alexander Posch
 */
public class ChessRules implements Rules {

    /*Notlösung
    public static boolean isKingDead(GameBoard gameboard, Player player){

        for(int i = 0; i < gameboard.getPlayingPieces().length; i++ ){
            if(gameboard.getPlayingPieces()[i].getName().equals("king") 
            && gameboard.getPlayingPieces()[i].getColour().equals(player.getColour()) 
            && gameboard.getPlayingPieces()[i].getPosition().equals("null"))
                return true;
        }
        return false;
    }
    */

    /**
     * Checks if the target square/field of the given move is occupied by ones own playing piece.
     * @param board GameBoard
     * @param move String
     * @return boolean
     */
    public static boolean isFieldOccupiedByOwnPlayingP(GameBoard board, String move) {

        int targetRow = ChessMoveConverter.convertPosIntoArrayCoordinate(move.charAt(4));
        int targetColumn = ChessMoveConverter.convertPosIntoArrayCoordinate(move.charAt(3));

        int startRow = ChessMoveConverter.convertPosIntoArrayCoordinate(move.charAt(1));
        int startColumn = ChessMoveConverter.convertPosIntoArrayCoordinate(move.charAt(0));

        String ownColour = board.getState()[startRow][startColumn].getColour();
    
        if(board.getState()[targetRow][targetColumn].getName().equals("null")){
            return false;
        }else
            return board.getState()[targetRow][targetColumn].getColour().equals(ownColour);
    }

    /**
     * Checks whether the given move is valid.
     * @param gameBoard GameBoard
     * @param stateToCheck ChessPlayingPiece[][]
     * @return Messages
     */
    public static Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck) {
        ChessMoveConverter converter = new ChessMoveConverter();
        String move = converter.stateToString(gameBoard.getState(), stateToCheck);

        return checkEachPossibleMove(gameBoard, move, "move");
    }

    /**
     * Checks whether the given move is valid. The method returns Messages.MOVE_ALLOWED
     * or the corresponding error message.
     * @param gameBoard GameBoard
     * @param move String
     * @param mode String: There are two different modes. When a normal move is checked the mode is "move".
     *             When a possible attack is checked the mode is "attack".
     * @return Messages
     */
    private static Messages checkEachPossibleMove(GameBoard gameBoard, String move, String mode){

        int row = ChessMoveConverter.convertPosIntoArrayCoordinate(move.charAt(1));
        int column = ChessMoveConverter.convertPosIntoArrayCoordinate(move.charAt(0));

        switch(gameBoard.getState()[row][column].getName()){
            case "rook":
                if((checkRookMoves(move) == Messages.MOVE_ALLOWED) && isRookPathFree(gameBoard, move) )
                    return Messages.MOVE_ALLOWED;
                else
                    return Messages.ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_ROOK;

            case "knight":
                if((checkKnightMoves(move) == Messages.MOVE_ALLOWED) && isKnightTargetFree(gameBoard, move))
                    return Messages.MOVE_ALLOWED;
                else
                    return Messages.ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_KNIGHT;

            case "bishop":
                if((checkBishopMoves(move) == Messages.MOVE_ALLOWED) && isBishopPathFree(gameBoard, move))
                    return Messages.MOVE_ALLOWED;
                else
                    return Messages.ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_BISHOP;

            case "queen":
                if((checkQueenMoves(move) == Messages.MOVE_ALLOWED) && isQueenPathFree(gameBoard, move))
                    return Messages.MOVE_ALLOWED;
                else
                    return Messages.ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_QUEEN;

            case "king":
                if((checkKingMoves(gameBoard, move, mode) == Messages.MOVE_ALLOWED) && isKingTargetFree(gameBoard, move))
                    return Messages.MOVE_ALLOWED;
                else
                    return Messages.ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_KING;

            case "pawn":
                return checkPawnMoves(gameBoard, move);

            default:
                return Messages.ERROR_NO_SUCH_PLAYINGPIECE;
        }
        
    }

    /**
     * Checks pawn moves. The method returns Messages.MOVE_ALLOWED or Messages.ERROR_WRONGMOVEMENT_DIRECTION_PAWN.
     * @param gameBoard GameBoard
     * @param move String
     * @return Messages
     */
    private static Messages checkPawnMoves(GameBoard gameBoard, String move){

        Field target = ChessMoveConverter.getChessTargetField(move);
        Field start = ChessMoveConverter.getChessStartField(move);
        // move two squares forward
        if(target.getRow() == start.getRow()+2
                && !gameBoard.getState()[start.getRow()][start.getColumn()].hasMoved()
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && isPawnPathFree(gameBoard, start)
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)){
            return Messages.MOVE_ALLOWED;
        }else if(target.getRow() == start.getRow()-2
                && !gameBoard.getState()[start.getRow()][start.getColumn()].hasMoved()
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && isPawnPathFree(gameBoard, start)
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)){
            return Messages.MOVE_ALLOWED;
        }

        // move one square forward
        if(target.getRow() == start.getRow()+1
                && target.getColumn() == start.getColumn()
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)){
            return Messages.MOVE_ALLOWED;
        }else if(target.getRow() == start.getRow()-1
                && target.getColumn() == start.getColumn()
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)){
            return Messages.MOVE_ALLOWED;
        }

        //en passant
        if(target.getRow() == start.getRow()+1
                && (target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1)
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && isEnPassantAllowed(gameBoard, start, target)){
            return Messages.MOVE_ALLOWED;
        }else if(target.getRow() == start.getRow()-1
                && (target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1)
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && isEnPassantAllowed(gameBoard, start, target)){
            return Messages.MOVE_ALLOWED;
        }

        //move one square diagonally forward (only if there is a playing piece from the opponent)
        if(target.getRow() == start.getRow()+1
                && (target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1)
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)
                && Rules.isFieldOccupied(gameBoard.getState(), target.getRow(), target.getColumn())){
            return Messages.MOVE_ALLOWED;
        }else if(target.getRow() == start.getRow()-1
                && (target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1)
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)
                && Rules.isFieldOccupied(gameBoard.getState(), target.getRow(), target.getColumn())){
            return Messages.MOVE_ALLOWED;
        }

        return Messages.ERROR_WRONGMOVEMENT_DIRECTION_PAWN;
    }

    /**
     * Checks whether the last move was a promotion. In case of a promotion the method returns
     * a String description of the target field otherwise it returns the String "false".
     * @param gameBoard GameBoard
     * @param stateToCheck PlayingPiece[][]
     * @return String
     */
    public static String isPromotion(GameBoard gameBoard, PlayingPiece[][] stateToCheck){
        ChessMoveConverter converter = new ChessMoveConverter();
        Field target = ChessMoveConverter.getChessTargetField(converter.stateToString(gameBoard.getState(), stateToCheck));
        Field start = ChessMoveConverter.getChessStartField(converter.stateToString(gameBoard.getState(), stateToCheck));

        String stringTarget = ChessMoveConverter.convertArrayCoordinateIntoPosColumn(target.getColumn())
                                + ChessMoveConverter.convertArrayCoordinateIntoPosRow(target.getRow());

        if(!gameBoard.getState()[start.getRow()][start.getColumn()].getName().equals("pawn") ||
            checkPawnMoves(gameBoard, converter.stateToString(gameBoard.getState(), stateToCheck)) != Messages.MOVE_ALLOWED)
            return "false";

        //promotion
        if(gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && target.getRow() == 0){
            return stringTarget;
        }else if(gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && target.getRow() == 7){
            return stringTarget;
        }

        return "false";
    }


    public static void setPromotion(GameBoard gameBoard, String information, String position)
            throws WrongFormatException {
        PlayingPiece[] list = gameBoard.getPlayingPieces();
        StringBuilder output = new StringBuilder();
        for (PlayingPiece piece : list) {
            if (piece.getPosition().equals(position)) {
                piece.setName(information);
                break;
            }
        }

        for(int i = 0; i < 32; i++){
            output.append("<");
            output.append(list[i]);
            output.append(">");
        }
        

        gameBoard.setNewBoard(output.toString());

    }

    /**
     * Checks whether the path of the pawns move is free.
     * @param board GameBoard
     * @param start Field
     * @return boolean
     */
    private static boolean isPawnPathFree(GameBoard board, Field start){
        if(board.getState()[start.getRow()][start.getColumn()].getColour().equals("black"))
            return !Rules.isFieldOccupied(board.getState(), start.getRow()+1, start.getColumn());
        else
            return !Rules.isFieldOccupied(board.getState(), start.getRow()-1, start.getColumn());
    }

    /**
     * Checks the rule en passant.
     * @param board GameBoard
     * @param start Field
     * @param target Field
     * @return boolean
     */
    private static boolean isEnPassantAllowed(GameBoard board, Field start, Field target){
        return !board.getStateFromMemento().getState()[start.getRow()][target.getColumn()]
                    .equals(board.getState()[start.getRow()][target.getColumn()])
                && board.getState()[start.getRow()][target.getColumn()].getName().equals("null")
                && !board.getState()[start.getRow()][target.getColumn()].getColour()
                        .equals(board.getState()[start.getRow()][start.getColumn()].getColour());
    }

    /**
     * Checks whether the given move of the king is valid. If the move is valid the method will
     * return Messages.MOVE_ALLOWED otherwise Messages.ERROR_WRONGMOVEMENT_DIRECTION_KING.
     * @param board GameBard
     * @param move String
     * @param mode String: If the move which is to be checked is just a normal king move the mode
     *             should be "move". If a possible attack from the opponent king is checked then
     *             the mode should be "attack".
     * @return Messages
     */
    private static Messages checkKingMoves(GameBoard board, String move, String mode){
        ArrayList<Field> possibleLocations = new ArrayList<>();

        Field target = ChessMoveConverter.getChessTargetField(move);
        Field start = ChessMoveConverter.getChessStartField(move);

        if(mode.equals("move") && isMoveCastling(board, move))
            return Messages.MOVE_ALLOWED;

        if(mode.equals("move") && isFieldAttacked(board, target.getRow(), target.getColumn()))
            return Messages.ERROR_WRONGMOVEMENT_DIRECTION_KING;

        //determine every theoretically possible move and add the target field into a list
        if( (start.getRow()-1 >= 0) && (start.getColumn()-1 >= 0) ){
            possibleLocations.add(new Field(start.getRow()-1, start.getColumn()-1));
        }

        if(start.getRow()-1 >= 0){
            possibleLocations.add(new Field(start.getRow()-1, start.getColumn()));
        }

        if( (start.getRow()-1 >= 0) && (start.getColumn()+1 <= 7) ){
            possibleLocations.add(new Field(start.getRow()-1, start.getColumn()+1));
        }

        if(start.getColumn()+1 <= 7){
            possibleLocations.add(new Field(start.getRow(), start.getColumn()+1));
        }

        if( (start.getRow()+1 <= 7) && (start.getColumn()+1 <= 7) ){
            possibleLocations.add(new Field(start.getRow()+1, start.getColumn()+1));
        }

        if(start.getRow()+1 <= 7){
            possibleLocations.add(new Field(start.getRow()+1, start.getColumn()));
        }

        if( (start.getRow()+1 <= 7) && (start.getColumn()-1 >= 0) ){
            possibleLocations.add(new Field(start.getRow()+1, start.getColumn()-1));
        }

        if(start.getColumn()-1 >= 0){
            possibleLocations.add(new Field(start.getRow(), start.getColumn()-1));
        }

        //check whether the target is one of the possible locations
        for (Field possibleLocation : possibleLocations) {
            if (possibleLocation.equals(target)) {
                return Messages.MOVE_ALLOWED;
            }
        }

        return Messages.ERROR_WRONGMOVEMENT_DIRECTION_KING;
    }

    /**
     * Checks whether the target field of a king move is occupied by a playing piece of its own colour.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     */
    private static boolean isKingTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }

    /**
     * Checks the move of a queen. Depending on whether the move was valid or not
     * the method will return Messages.MOVE_ALLOWED or Messages.ERROR_WRONGMOVEMENT_DIRECTION_QUEEN.
     * @param move String
     * @return Messages
     */
    private static Messages checkQueenMoves(String move){
        if (checkVerticalAndHorizontalMoves(move) || checkDiagonalMoves(move))
            return Messages.MOVE_ALLOWED;
        else
            return Messages.ERROR_WRONGMOVEMENT_DIRECTION_QUEEN;
    }

    /**
     * Checks whether the path of the move done by a queen is free.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     */
    private static boolean isQueenPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move) || areDiagonalPathsFree(gameBoard, move);
    }

    /**
     * Checks the move of a bishop. Depending on whether the move was valid or not
     * the method will return Messages.MOVE_ALLOWED or Messages.ERROR_WRONGMOVEMENT_DIRECTION_BISHOP.
     * @param move String
     * @return Messages
     */
    private static Messages checkBishopMoves(String move){
        if(checkDiagonalMoves(move))
            return Messages.MOVE_ALLOWED;
        else
            return Messages.ERROR_WRONGMOVEMENT_DIRECTION_BISHOP;
    }

    /**
     * Checks whether the path of the move done by a bishop is free.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     */
    private static boolean isBishopPathFree(GameBoard gameBoard, String move){
        return areDiagonalPathsFree(gameBoard, move);
    }

    /**
     * Checks the move of a knight. Depending on whether the move was valid or not
     * the method will return Messages.MOVE_ALLOWED or Messages.ERROR_WRONGMOVEMENT_DIRECTION_KNIGHT.
     * @param move String
     * @return Messages
     */
    private static Messages checkKnightMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<>();

        Field target = ChessMoveConverter.getChessTargetField(move);
        Field start = ChessMoveConverter.getChessStartField(move);

        //determine every theoretically possible move and add the target field into a list

        //up-left
        if( (start.getRow()-2 >= 0) && (start.getColumn()-1 >= 0) ){
            possibleLocations.add(new Field(start.getRow()-2, start.getColumn()-1));
        }

        //up-right
        if( (start.getRow()-2 >= 0) && (start.getColumn()+1 <= 7) ){
            possibleLocations.add(new Field(start.getRow()-2, start.getColumn()+1));
        }

        //down-left
        if( (start.getRow()+2 <= 7) && (start.getColumn()-1 >= 0) ){
            possibleLocations.add(new Field(start.getRow()+2, start.getColumn()-1));
        }

        //down-right
        if( (start.getRow()+2 <= 7) && (start.getColumn()+1 <= 7) ){
            possibleLocations.add(new Field(start.getRow()+2, start.getColumn()+1));
        }

        //left-up
        if( (start.getColumn()-2 >= 0) && (start.getRow()-1 >= 0) ){
            possibleLocations.add(new Field(start.getRow()-1, start.getColumn()-2));
        }

        //left-down
        if( (start.getColumn()-2 >= 0) && (start.getRow()-1 <= 7) ){
            possibleLocations.add(new Field(start.getRow()-1, start.getColumn()-2));
        }

        //right-up
        if( (start.getColumn()+2 <= 7) && (start.getRow()-1 <= 0) ){
            possibleLocations.add(new Field(start.getRow()-1, start.getColumn()+2));
        }

        //right-down
        if( (start.getColumn()+2 <= 7) && (start.getRow()+1 <= 7) ){
            possibleLocations.add(new Field(start.getRow()+1, start.getColumn()+2));
        }

        //check whether the target is one of the possible locations
        for (Field possibleLocation : possibleLocations) {
            if (possibleLocation.equals(target)) {
                return Messages.MOVE_ALLOWED;
            }
        }

        return Messages.ERROR_WRONGMOVEMENT_DIRECTION_KNIGHT;
    }

    /**
     * Checks whether the target field of a knight move is occupied by a playing piece of its own colour.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     */
    private static boolean isKnightTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }

    /**
     * Checks the move of a rook. Depending on whether the move was valid or not
     * the method will return Messages.MOVE_ALLOWED or Messages.ERROR_WRONGMOVEMENT_DIRECTION_ROOK.
     * @param move String
     * @return Messages
     */
    private static Messages checkRookMoves(String move){
        if(checkVerticalAndHorizontalMoves(move))
            return Messages.MOVE_ALLOWED;
        else
            return Messages.ERROR_WRONGMOVEMENT_DIRECTION_ROOK;
    }

    /**
     * Checks whether the path of the move done by a rook is free.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     */
    private static boolean isRookPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move);
    }

    /**
     * Checks whether the move done by a king was castling.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     */
    private static boolean isMoveCastling(GameBoard gameBoard, String move){
        int startRow = ChessMoveConverter.getChessStartField(move).getRow();
        int startColumn = ChessMoveConverter.getChessStartField(move).getColumn();

        if(gameBoard.getState()[startRow][startColumn].hasMoved())
            return false;

        final Field MOVE_TARGET = ChessMoveConverter.getChessTargetField(move);

        final Field G8 = new Field(0,6);
        final Field C8 = new Field(0, 2);
        final Field G1 = new Field(7, 6);
        final Field C1 = new Field(7, 2);

        if(gameBoard.getState()[startRow][startColumn].getColour().equals("black")){

            if(MOVE_TARGET.equals(G8) && !gameBoard.getState()[0][6].hasMoved())
                return isCastlingPathFree(gameBoard, G8);
            else if(MOVE_TARGET.equals(C8) && !gameBoard.getState()[0][2].hasMoved())
                return isCastlingPathFree(gameBoard, C8);

        }else {

            if(MOVE_TARGET.equals(G1) && !gameBoard.getState()[7][6].hasMoved())
                return isCastlingPathFree(gameBoard, G1);
            else if(MOVE_TARGET.equals(C1) && !gameBoard.getState()[7][2].hasMoved())
                return isCastlingPathFree(gameBoard, C1);

        }

        return false;
    }

    /**
     * Checks whether the path of the move castling is free.
     * @param board GameBoard
     * @param rook Field
     * @return boolean
     */
    private static boolean isCastlingPathFree(GameBoard board, Field rook){
        if(rook.getRow()==0 && rook.getColumn()==0){
            for (int i = 1; i < 4; i++) {
                if(Rules.isFieldOccupied(board.getState(), 0, i) || isFieldAttacked(board, 0, i)){
                    return false;
                }
            }
        }else if(rook.getRow()==0 && rook.getColumn()==7){
            for (int i = 5; i < 7; i++) {
                if(Rules.isFieldOccupied(board.getState(), 0, i) || isFieldAttacked(board, 0, i)){
                    return false;
                }
            }
        }else if(rook.getRow()==7 && rook.getColumn()==0){
            for (int i = 1; i < 4; i++) {
                if(Rules.isFieldOccupied(board.getState(), 7, i) || isFieldAttacked(board, 7, i)){
                    return false;
                }
            }
        }else{
            for (int i = 5; i < 7; i++) {
                if(Rules.isFieldOccupied(board.getState(), 7, i) || isFieldAttacked(board, 7, i)){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks whether the given field is attacked or not.
     * @param board GameBoard
     * @param row int
     * @param column int
     * @return boolean
     */
    private static boolean isFieldAttacked(GameBoard board, int row, int column){
        String move = "";
        StringBuilder stringB = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(j));
                stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(i));
                stringB.append(" ");
                stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column));
                stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row));

                move = stringB.toString();

                if(checkEachPossibleMove(board, move, "attack") == Messages.MOVE_ALLOWED)
                    return true;

                stringB = new StringBuilder();
            }
        }
        return false;
    }

    /**
     * Checks whether the king can escape or not.
     * @param board GameBoard
     * @param row int
     * @param column int
     * @return boolean
     */
    private static boolean kingCannotEscape(GameBoard board, int row, int column){
        StringBuilder stBuilder = new StringBuilder();
        stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column));
        stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row)).append(" ");

        String move = "";

        //check whether there is a field where king could escape
        if( (row-1 >= 0) && (column-1 >= 0) ){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column-1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row-1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row-1, column-1) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if(row-1 >= 0){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row-1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row-1, column) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if( (row-1 >= 0) && (column+1 <= 7) ){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column+1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row-1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row-1, column+1) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if(column+1 <= 7){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column+1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row, column+1) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if( (row+1 <= 7) && (column+1 <= 7) ){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column+1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row+1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row+1, column+1) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if(row+1 <= 7){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row+1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row+1, column) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if( (row+1 <= 7) && (column-1 >= 0) ){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column-1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row+1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row+1, column-1) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if(column-1 >= 0){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column-1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row, column-1) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        String colour = board.getState()[row][column].getColour();

        //check if castling is possible
        if(board.getState()[row][0].getName().equals("rook")
            && board.getState()[row][0].getColour().equals(colour)
            && !board.getState()[row][0].hasMoved()
            && !board.getState()[row][column].hasMoved()
            && isCastlingPathFree(board, new Field(row, 0)) ){
                return false;
        }else if(board.getState()[row][7].getName().equals("rook")
                && board.getState()[row][7].getColour().equals(colour)
                && !board.getState()[row][7].hasMoved()
                && !board.getState()[row][column].hasMoved()
                && isCastlingPathFree(board, new Field(row, 7)) ){
                    return false;
        }

        return true;
    }

    /**
     * Checks whether the diagonal move was valid.
     * @param move String
     * @return boolean
     */
    private static boolean checkDiagonalMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<>();

        Field target = ChessMoveConverter.getChessTargetField(move);
        Field start = ChessMoveConverter.getChessStartField(move);

        //determine every theoretically possible move and add the target field into a list

        //left-up diagonal
        int row = start.getRow() - 1;
        int column = start.getColumn() - 1;

        while( (row >= 0) && (column >= 0) ){
            possibleLocations.add(new Field(row, column));
            row--;
            column--;
        }

        //left-down diagonal
        row = start.getRow() + 1;
        column = start.getColumn() - 1;

        while ( (row <= 7) && (column >= 0) ){
            possibleLocations.add(new Field(row, column));
            row++;
            column--;
        }

        //right-up diagonal
        row = start.getRow() - 1;
        column = start.getColumn() + 1;

        while ( (row >= 0) && (column <= 7) ){
            possibleLocations.add(new Field(row, column));
            row--;
            column++;
        }

        //right-down diagonal
        row = start.getRow() + 1;
        column = start.getColumn() + 1;

        while ( (row <= 7) && (column <= 7) ){
            possibleLocations.add(new Field(row, column));
            row++;
            column++;
        }
        for (Field possibleLocation : possibleLocations) {
            if (possibleLocation.equals(target)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the path of a diagonal move is free.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     */
    private static boolean areDiagonalPathsFree(GameBoard gameBoard, String move){
        Field target = ChessMoveConverter.getChessTargetField(move);
        Field start = ChessMoveConverter.getChessStartField(move);

        if(isFieldOccupiedByOwnPlayingP(gameBoard, move))
            return false;


        //if target is on the upper-left diagonal
        int row = start.getRow() - 1;
        int column = start.getColumn() - 1;

        if( (target.getRow() < start.getRow()) && (target.getColumn() < start.getColumn()) ){

            while( (row > target.getRow()) && (column > target.getColumn()) ){
                if(Rules.isFieldOccupied(gameBoard.getState(), row, column)){
                    return false;
                }
                row--;
                column--;
            }
        } else if( (target.getRow() < start.getRow()) && (target.getColumn() > start.getColumn()) ){
            //if target is on the upper-right diagonal

            row = start.getRow() - 1;
            column = start.getColumn() + 1;

            while( (row > target.getRow()) && (column < target.getColumn()) ){
                if(Rules.isFieldOccupied(gameBoard.getState(), row, column)){
                    return false;
                }
                row--;
                column++;
            }
        } else if( (target.getRow() > start.getRow()) && (target.getColumn() > start.getColumn()) ){
            //if target is on the down-right diagonal

            row = start.getRow() + 1;
            column = start.getColumn() + 1;

            while( (row < target.getRow()) && (column < target.getColumn()) ){
                if(Rules.isFieldOccupied(gameBoard.getState(), row, column)){
                    return false;
                }
                row++;
                column++;
            }
        } else if( (target.getRow() > start.getRow()) && (target.getColumn() < start.getColumn()) ){
            //if target is on down-left diagonal

            row = start.getRow() + 1;
            column = start.getColumn() - 1;

            while ( (row < target.getRow()) && (column > target.getColumn()) ){
                if(Rules.isFieldOccupied(gameBoard.getState(), row, column)){
                    return false;
                }
                row++;
                column--;
            }
        }

        return true;
    }

    /**
     * Checks vertical and horizontal moves.
     * @param move String
     * @return boolean
     */
    private static boolean checkVerticalAndHorizontalMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<>();

        Field target = ChessMoveConverter.getChessTargetField(move);
        Field start = ChessMoveConverter.getChessStartField(move);

        //determine every theoretically possible move and add the target field into a list
        for (int i = start.getRow(); i >= 0; i--) {
            possibleLocations.add(new Field(i, start.getColumn()));
        }

        for (int i = start.getRow(); i <= 7; i++) {
            possibleLocations.add(new Field(i, start.getColumn()));
        }

        for (int i = start.getColumn(); i >= 0; i--) {
            possibleLocations.add(new Field(start.getRow(), i));
        }

        for (int i = start.getColumn(); i <= 7; i++) {
            possibleLocations.add(new Field(start.getRow(), i));
        }

        //delete startPos from List
        Field startPos = new Field(start.getRow(), start.getColumn());

        for (int i = 0; i < possibleLocations.size(); i++){
            if(possibleLocations.get(i).equals(startPos)) {
                possibleLocations.remove(i);
            }
        }

        //check whether the targetPos is one of the possible locations
        for (Field possibleLocation : possibleLocations) {
            if (possibleLocation.equals(target)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the path of a vertical/horizontal move is free.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     */
    private static boolean areVerticalOrHorizontalPathsFree(GameBoard gameBoard, String move){
        Field target = ChessMoveConverter.getChessTargetField(move);
        Field start = ChessMoveConverter.getChessStartField(move);

        if(isFieldOccupiedByOwnPlayingP(gameBoard, move))
            return false;


        if(start.getRow()==target.getRow()){

            if(start.getColumn() < target.getColumn()){
                //moved to the right side
                for(int i = start.getColumn()+1; i < target.getColumn(); i++){
                    if(Rules.isFieldOccupied(gameBoard.getState(), start.getRow(), i)){
                        return false;
                    }
                }
            } else{
                //moved to the left side
                for(int i = start.getColumn()-1; i > target.getColumn(); i--) {
                    if (Rules.isFieldOccupied(gameBoard.getState(), start.getRow(), i)) {
                        return false;
                    }
                }
            }

        }else{

            if(start.getRow()>target.getRow()){
                //moved upwards
                for (int i = start.getRow()-1; i > target.getRow() ; i--) {
                    if(Rules.isFieldOccupied(gameBoard.getState(), i, start.getColumn())){
                        return false;
                    }
                }
            }else{
                //moved downwards
                for (int i = start.getRow()+1; i < target.getRow() ; i++) {
                    if(Rules.isFieldOccupied(gameBoard.getState(), i, start.getColumn())){
                        return false;
                    }
                }
            }

        }
        return true;
    }

    /**
     * Checks the rule checkmate.
     * @param gameBoard GameBoard
     * @param colour String
     * @return boolean
     */
    private static boolean checkmate(GameBoard gameBoard, String colour){
        int row = -1;
        int column = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(gameBoard.getState()[i][j].getColour().equals(colour)
                        && gameBoard.getState()[i][j].getName().equals("king")) {
                    row = i;
                    column = j;
                }
            }
        }

        //king is not in the game anymore or can not escape
        return (row == -1) || (kingCannotEscape(gameBoard, row, column) && isFieldAttacked(gameBoard, row, column));
    }

    /**
     * Checks the rule stalemate.
     * @param board GameBoard
     * @param colour String
     * @return boolean
     */
    private static boolean isStalemate(GameBoard board, String colour){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board.getState()[i][j].getColour().equals(colour)
                    && possibleMoveExists(board, new Field(i, j))){
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if a possible move for a playing piece which is described through his start field exists.
     * @param board GameBoard
     * @param start Field
     * @return
     */
    private static boolean possibleMoveExists(GameBoard board, Field start){
        StringBuilder move = new StringBuilder();
        move.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(start.getColumn()));
        move.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(start.getRow())).append(" ");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                move.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(j));
                move.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(i));

                if(checkEachPossibleMove(board, move.toString(), "move") == Messages.MOVE_ALLOWED){
                    return true;
                }
                move.deleteCharAt(4);
                move.deleteCharAt(3);
            }
        }

        return false;
    }

    //TODO BEGÜM
    private static boolean deadPosition(GameBoard board){
        int black_counter = 0;
        int white_counter = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(possibleMoveExists(board, new Field(i, j)))
                    if(board.getState()[i][j].getColour().equals("black"))
                        black_counter++;
                    else
                        white_counter++;
            }
        }

        return black_counter <= 1 && white_counter <= 1;
    }

    /**
     * After the execution of a move the method checks if the game is finished. Messages.DEFEATED,
     * Messages.VICTORY, Messages.DRAW and Messages.GO_ON are possible return values.
     * @param board GameBoard
     * @param colour String: colour of player whose turn it is to move
     * @return Messages
     */
    public static Messages isGameFinished(GameBoard board, String colour){
        String othercolour = "white";

        if(colour.equals("white"))
            othercolour = "black";

        if(checkmate(board, colour))
            return Messages.DEFEATED;
        else if (checkmate(board, othercolour))
            return Messages.VICTORY;
        else if(isStalemate(board, colour) || deadPosition(board))
            return Messages.DRAW;
        else
            return Messages.GO_ON;            
    }

    /**
     * Checks whether the last move was allowed and if after the last move the game is finished.
     * @param board GameBoard
     * @param colour String
     * @param stateToCheck PlayingPiece[][]
     * @return Messages
     */
    public static Messages executeMove(GameBoard board, String colour, PlayingPiece[][] stateToCheck){
        Messages message;

        message = isMoveAllowed(board, stateToCheck);
        if(message == Messages.MOVE_ALLOWED)
            return isGameFinished(board, colour);
        return message;
    }
}
