package src.chess;

import java.util.ArrayList;
import src.games.*;

/**
 * ChessRules implements Rules from src.games and checks the chess rules.
 *
 * @author Begüm Tosun, Alexander Posch
 */
public class ChessRules implements Rules {

    /**
     * Checks if the target square/field of the given move is occupied by ones own playing piece.
     * @param board GameBoard
     * @param move String
     * @return boolean
     * @author Begüm Tosun
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
     * @author Begüm Tosun
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
     * @author Begüm Tosun
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
     * @author Begüm Tosun
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
     * @author Begüm Tosun
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

    /**
     * Updates the promoted pawn in the String list.
     * @param gameBoard GameBoard
     * @param information String
     * @param position String
     * @throws WrongFormatException Exception
     * @author Alexander Posch
     */
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
     * @author Begüm Tosun
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
     * @author Begüm Tosun
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
     * @author Begüm Tosun
     */
    private static Messages checkKingMoves(GameBoard board, String move, String mode){
        ArrayList<Field> possibleLocations = new ArrayList<>();

        Field target = ChessMoveConverter.getChessTargetField(move);
        Field start = ChessMoveConverter.getChessStartField(move);


        if(mode.equals("move") && isMoveCastling(board, move))
            return Messages.MOVE_ALLOWED;

        if(mode.equals("move") && isFieldAttacked(board, target.getRow(), target.getColumn(), start))
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
     * @author Begüm Tosun
     */
    private static boolean isKingTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }

    /**
     * Checks the move of a queen. Depending on whether the move was valid or not
     * the method will return Messages.MOVE_ALLOWED or Messages.ERROR_WRONGMOVEMENT_DIRECTION_QUEEN.
     * @param move String
     * @return Messages
     * @author Begüm Tosun
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
     * @author Begüm Tosun
     */
    private static boolean isQueenPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move) || areDiagonalPathsFree(gameBoard, move);
    }

    /**
     * Checks the move of a bishop. Depending on whether the move was valid or not
     * the method will return Messages.MOVE_ALLOWED or Messages.ERROR_WRONGMOVEMENT_DIRECTION_BISHOP.
     * @param move String
     * @return Messages
     * @author Begüm Tosun
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
     * @author Begüm Tosun
     */
    private static boolean isBishopPathFree(GameBoard gameBoard, String move){
        return areDiagonalPathsFree(gameBoard, move);
    }

    /**
     * Checks the move of a knight. Depending on whether the move was valid or not
     * the method will return Messages.MOVE_ALLOWED or Messages.ERROR_WRONGMOVEMENT_DIRECTION_KNIGHT.
     * @param move String
     * @return Messages
     * @author Begüm Tosun
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
        if( (start.getColumn()-2 >= 0) && (start.getRow()+1 <= 7) ){
            possibleLocations.add(new Field(start.getRow()+1, start.getColumn()-2));
        }

        //right-up
        if( (start.getColumn()+2 <= 7) && (start.getRow()-1 >= 0) ){
            possibleLocations.add(new Field(start.getRow()-1, start.getColumn()+2));
        }

        //right-down
        if( (start.getColumn()+2 <= 7) && (start.getRow()+1 <= 7) ){
            possibleLocations.add(new Field(start.getRow()+1, start.getColumn()+2));
        }

        //check whether the target is one of the possible locations
        for (Field possibleLocation : possibleLocations) {
            if (possibleLocation.equals(target))
                return Messages.MOVE_ALLOWED;
        }

        return Messages.ERROR_WRONGMOVEMENT_DIRECTION_KNIGHT;
    }

    /**
     * Checks whether the target field of a knight move is occupied by a playing piece of its own colour.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     * @author Begüm Tosun
     */
    private static boolean isKnightTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }

    /**
     * Checks the move of a rook. Depending on whether the move was valid or not
     * the method will return Messages.MOVE_ALLOWED or Messages.ERROR_WRONGMOVEMENT_DIRECTION_ROOK.
     * @param move String
     * @return Messages
     * @author Begüm Tosun
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
     * @author Begüm Tosun
     */
    private static boolean isRookPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move);
    }

    /**
     * Checks whether the move done by a king was castling.
     * @param gameBoard GameBoard
     * @param move String
     * @return boolean
     * @author Begüm Tosun
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
     * @author Begüm Tosun
     */
    private static boolean isCastlingPathFree(GameBoard board, Field rook){
        if(rook.getRow()==0 && rook.getColumn()==0){
            for (int i = 1; i < 4; i++) {
                if(Rules.isFieldOccupied(board.getState(), 0, i) || isFieldAttacked(board, 0, i, rook)){
                    return false;
                }
            }
        }else if(rook.getRow()==0 && rook.getColumn()==7){
            for (int i = 5; i < 7; i++) {
                if(Rules.isFieldOccupied(board.getState(), 0, i) || isFieldAttacked(board, 0, i, rook)){
                    return false;
                }
            }
        }else if(rook.getRow()==7 && rook.getColumn()==0){
            for (int i = 1; i < 4; i++) {
                if(Rules.isFieldOccupied(board.getState(), 7, i) || isFieldAttacked(board, 7, i, rook)){
                    return false;
                }
            }
        }else{
            for (int i = 5; i < 7; i++) {
                if(Rules.isFieldOccupied(board.getState(), 7, i) || isFieldAttacked(board, 7, i, rook)){
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
     * @author Begüm Tosun
     */
    private static boolean isFieldAttacked(GameBoard board, int row, int column, Field pieceThatMoves){
        String move = "";
        StringBuilder stringB = new StringBuilder();

        String ownColour = board.getState()[pieceThatMoves.getRow()][pieceThatMoves.getColumn()].getColour();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if( !pieceThatMoves.equals(new Field(i, j))
                    && !board.getState()[i][j].getColour().equals(ownColour)
                    && !board.getState()[i][j].getColour().equals("null")) {

                    stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(j));
                    stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(i));
                    stringB.append(" ");
                    stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column));
                    stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row));

                    move = stringB.toString();

                    if (checkEachPossibleMove(board, move, "attack") == Messages.MOVE_ALLOWED)
                        return true;


                    stringB = new StringBuilder();
                }

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
     * @author Begüm Tosun
     */
    private static boolean kingCannotEscape(GameBoard board, int row, int column){
        Field king = new Field(row, column);
        StringBuilder stBuilder = new StringBuilder();
        stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column));
        stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row)).append(" ");

        String move = "";

        //check whether there is a field where king could escape
        if( (row-1 >= 0) && (column-1 >= 0) ){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column-1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row-1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row-1, column-1, king) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if(row-1 >= 0){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row-1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row-1, column, king) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if( (row-1 >= 0) && (column+1 <= 7) ){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column+1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row-1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row-1, column+1, king) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if(column+1 <= 7){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column+1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row, column+1, king) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if( (row+1 <= 7) && (column+1 <= 7) ){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column+1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row+1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row+1, column+1, king) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if(row+1 <= 7){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row+1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row+1, column, king) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if( (row+1 <= 7) && (column-1 >= 0) ){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column-1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row+1));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row+1, column-1, king) && !isFieldOccupiedByOwnPlayingP(board, move))
                return false;

            stBuilder.deleteCharAt(4);
            stBuilder.deleteCharAt(3);
        }


        if(column-1 >= 0){
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column-1));
            stBuilder.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row));
            move = stBuilder.toString();

            if(!isFieldAttacked(board, row, column-1, king) && !isFieldOccupiedByOwnPlayingP(board, move))
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
     * Checks whether the king can't be protected in case of check (and kingCannotEscape).
     * @param stateToCheck PlayingPiece[][]
     * @param row int: array coordinate of king
     * @param column int: array coordinate of king
     * @return boolean
     * @author Begüm Tosun
     */
    private static boolean kingCannotBeProtected(PlayingPiece[][] stateToCheck, int row, int column){
        Field king = new Field(row, column);
        GameBoard board = new ChessBoard();
        board.setState(stateToCheck);

        ArrayList<Field> enemies = detectAttack(board, row, column, new Field(row, column));

        //check if enemy can be defeated by another playing piece
        for (int i = 0; i < enemies.size(); i++) {
            if(isFieldAttacked(board, enemies.get(i).getRow(), enemies.get(i).getColumn(), king))
                enemies.remove(i);
        }

        //check if it's possible to put a piece between king and enemy
        for (int i = 0; i < enemies.size(); i++) {

            //if the enemy which caused check is a knight, pawn or king it does not make a
            //difference whether it is possible to put a piece between king and the enemy
            if(stateToCheck[enemies.get(i).getRow()][enemies.get(i).getColumn()].getName().equals("queen")
                || stateToCheck[enemies.get(i).getRow()][enemies.get(i).getColumn()].getName().equals("rook")
                || stateToCheck[enemies.get(i).getRow()][enemies.get(i).getColumn()].getName().equals("bishop")){

                ArrayList<Field> path = getPath(new Field(row, column), enemies.get(i));

                for (int j = 0; j < path.size(); j++) {
                    if(isFieldAttacked(board, path.get(j).getRow(), path.get(j).getColumn(), king)) {
                        enemies.remove(i);
                        break;
                    }
                }
            }
        }

        return enemies.size() > 0;
    }

    /**
     * Returns the path between two pieces.
     * @param piece Field
     * @param enemy Field
     * @return ArrayList: list of fields
     * @author Begüm Tosun
     */
    private static ArrayList<Field> getPath(Field piece, Field enemy){
        ArrayList<Field> path = new ArrayList<>();

        if(enemy.getColumn() == piece.getColumn() && enemy.getRow() < piece.getRow()){
            for(int i = enemy.getRow()+1; i < piece.getRow(); i++){
                path.add(new Field(i, enemy.getColumn()));
            }

        }else if(enemy.getColumn() == piece.getColumn() && enemy.getRow() > piece.getRow()){
            for(int i = enemy.getRow()-1; i > piece.getRow(); i--){
                path.add(new Field(i, enemy.getColumn()));
            }

        }else if(enemy.getColumn() > piece.getColumn() && enemy.getRow() == piece.getRow()){
            for(int i = enemy.getColumn()-1; i > piece.getColumn(); i--){
                path.add(new Field(enemy.getRow(), i));
            }

        }else if(enemy.getColumn() < piece.getColumn() && enemy.getRow() == piece.getRow()){
            for(int i = enemy.getColumn()+1; i < piece.getColumn(); i++){
                path.add(new Field(enemy.getRow(), i));
            }

        }else if(enemy.getColumn() > piece.getColumn() && enemy.getRow() < piece.getRow()){
            for(int i = enemy.getColumn()-1; i > piece.getColumn(); i--){
                for (int j = enemy.getRow()+1; j < piece.getRow(); j++)
                    path.add(new Field(j, i));
            }

        }else if(enemy.getColumn() > piece.getColumn() && enemy.getRow() > piece.getRow()){
            for(int i = enemy.getColumn()-1; i > piece.getColumn(); i--){
                for (int j = enemy.getRow()-1; j > piece.getRow(); j--)
                    path.add(new Field(j, i));
            }

        }else if(enemy.getColumn() < piece.getColumn() && enemy.getRow() > piece.getRow()){
            for(int i = enemy.getColumn()+1; i < piece.getColumn(); i++){
                for (int j = enemy.getRow()-1; j > piece.getRow(); j--)
                    path.add(new Field(j, i));
            }

        }else if(enemy.getColumn() < piece.getColumn() && enemy.getRow() < piece.getRow()){
            for(int i = enemy.getColumn()+1; i < piece.getColumn(); i++){
                for (int j = enemy.getRow()+1; j < piece.getRow(); j++)
                    path.add(new Field(j, i));
            }

        }

        return path;
    }

    /**
     * Detects all attacks against a given field.
     * @param board GameBoard
     * @param row int
     * @param column int
     * @return boolean
     * @author Begüm Tosun
     */
    private static ArrayList<Field> detectAttack(GameBoard board, int row, int column, Field pieceThatMoves){
        String ownColour = board.getState()[row][column].getColour();
        String move = "";
        StringBuilder stringB = new StringBuilder();
        ArrayList<Field> enemies= new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if( !pieceThatMoves.equals(new Field(i, j))
                    && !board.getState()[i][j].getColour().equals(ownColour)
                    && !board.getState()[i][j].getColour().equals("null")) {
                    
                    stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(j));
                    stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(i));
                    stringB.append(" ");
                    stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(column));
                    stringB.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(row));

                    move = stringB.toString();

                    if (checkEachPossibleMove(board, move, "attack") == Messages.MOVE_ALLOWED)
                        enemies.add(new Field(i, j));

                    stringB = new StringBuilder();
                }
            }
        }
        return enemies;
    }

    /**
     * Checks whether the diagonal move was valid.
     * @param move String
     * @return boolean
     * @author Begüm Tosun
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
     * @author Begüm Tosun
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
     * @author Begüm Tosun
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
     * @author Begüm Tosun
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
     * @param stateToCheck PlayingPiece[][]
     * @param colour String
     * @return boolean
     * @author Begüm Tosun
     */
    private static boolean checkmate(PlayingPiece[][] stateToCheck, String colour){
        int row = -1;
        int column = -1;

        GameBoard board = new ChessBoard();
        board.setState(stateToCheck);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(stateToCheck[i][j].getColour().equals(colour)
                        && stateToCheck[i][j].getName().equals("king")) {
                    row = i;
                    column = j;
                }
            }
        }

        //king is not in the game anymore or can not escape
        return (row == -1) || (kingCannotEscape(board, row, column)
                && isFieldAttacked(board, row, column, new Field(row, column))
                && kingCannotBeProtected(stateToCheck, row, column));
    }

    /**
     * Checks the rule stalemate.
     * @param stateToCheck PlayingPiece
     * @param colour String
     * @return boolean
     * @author Begüm Tosun
     */
    private static boolean isStalemate(PlayingPiece[][] stateToCheck, String colour){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(stateToCheck[i][j].getColour().equals(colour)
                    && possibleMoveExists(stateToCheck, new Field(i, j))){
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if a possible move for a playing piece which is described through his start field exists.
     * @param stateToCheck PlayingPiece
     * @param start Field
     * @return boolean
     * @author Begüm Tosun
     */
    private static boolean possibleMoveExists(PlayingPiece[][] stateToCheck, Field start){
        StringBuilder move = new StringBuilder();
        move.append(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(start.getColumn()));
        move.append(ChessMoveConverter.convertArrayCoordinateIntoPosRow(start.getRow())).append(" ");

        GameBoard board = new ChessBoard();
        board.setState(stateToCheck);

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

    /**
     * Checks rule dead position.
     * @param stateToCheck PlayingPiece[][]
     * @return boolean
     * @author Begüm Tosun
     */
    private static boolean deadPosition(PlayingPiece[][] stateToCheck){
        int black_counter = 0;
        int white_counter = 0;
        ArrayList<PlayingPiece> pieces = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(stateToCheck[i][j].getColour().equals("black")) {
                    pieces.add(stateToCheck[i][j]);
                    black_counter++;
                }else if(stateToCheck[i][j].getColour().equals("white")) {
                    pieces.add(stateToCheck[i][j]);
                    white_counter++;
                }
            }
        }

        if(black_counter + white_counter == 2){
            return true;
        }else if(black_counter + white_counter > 2){
            int king = 0;
            int bishop = 0;
            int knight = 0;

            for (int i = 0; i < pieces.size(); i++) {
                if(pieces.get(i).getName().equals("king"))
                    king++;
                else if(pieces.get(i).getName().equals("bishop"))
                    bishop++;
                else if(pieces.get(i).getName().equals("knight"))
                    knight++;
            }

            if(king == 2) {
                if (bishop == 1)
                    return true;

                if(knight == 1)
                    return true;

                if(bishop == 2){
                    String colour = "";
                    int helper = 0;

                    for(int i = 0; i < pieces.size(); i++){

                        if(pieces.get(i).getName().equals("bishop") && helper == 0) {
                            colour = pieces.get(i).getColour();
                            helper++;
                        }else if(pieces.get(i).getName().equals("bishop") && helper > 0) {
                            return pieces.get(i).getColour().equals(colour);
                        }

                    }
                }
            }

        }

        return false;
    }

    /**
     * After the execution of a move the method checks if the game is finished. Messages.DEFEATED,
     * Messages.VICTORY, Messages.DRAW and Messages.GO_ON are possible return values.
     * @param stateToCheck PlayingPiece[][]
     * @param colour String: colour of player whose turn it is to move
     * @return Messages
     * @author Begüm Tosun
     */
    public static Messages isGameFinished(PlayingPiece[][] stateToCheck, String colour){
        String othercolour = "white";

        if(colour.equals("white"))
            othercolour = "black";

        if(checkmate(stateToCheck, colour))
            return Messages.DEFEATED;
        else if (checkmate(stateToCheck, othercolour))
            return Messages.VICTORY;
        else if(isStalemate(stateToCheck, colour) || deadPosition(stateToCheck))
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
     * @author Begüm Tosun
     */
    public static Messages executeMove(GameBoard board, String colour, PlayingPiece[][] stateToCheck){
        Messages message;

        message = isMoveAllowed(board, stateToCheck);
        if(message == Messages.MOVE_ALLOWED)
            return isGameFinished(stateToCheck, colour);
        return message;
    }
}
