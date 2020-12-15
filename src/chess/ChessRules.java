package src.chess;

import java.util.ArrayList;
import src.games.*;


/**
 * ChessRules implements Rules from src.Games and checks if the given move is valid.
 *
 * @author Begüm Tosun
 */
public class ChessRules implements Rules {
    private static ChessMoveConverter converter = new ChessMoveConverter();

    /**
     * Checks if the target square/field of the given move is occupied by ones own playing piece.
     * @param board GameBoard
     * @param move String
     * @return boolean
     */
    public static boolean isFieldOccupiedByOwnPlayingP(GameBoard board, String move) {
        int row = converter.convertPosIntoArrayCoordinate(move.charAt(4));
        int column = converter.convertPosIntoArrayCoordinate(move.charAt(3));

        String ownColour = board.getState()[converter.convertPosIntoArrayCoordinate(move.charAt(1))][converter.convertPosIntoArrayCoordinate(move.charAt(0))].getColour();
    

        if(board.getState()[row][column].getName()=="null"){
            return false;
        }else
            return board.getState()[row][column].getColour().equals(ownColour);
    }

    /**
     * Checks whether the given move is valid.
     * @param gameBoard GameBoard
     * @param stateToCheck ChessPlayingPiece[][]
     * @return boolean
     */
    public Messages isMoveAllowed(GameBoard gameBoard, PlayingPiece[][] stateToCheck) {
        if(isMoveCastling(gameBoard, stateToCheck))
            return Messages.MOVE_ALLOWED;

        String move = converter.stateToString(gameBoard.getState(), stateToCheck);

        return checkEachPossibleMove(gameBoard, move);
    }

    private static Messages checkEachPossibleMove(GameBoard gameBoard, String move){
        int row = converter.convertPosIntoArrayCoordinate(move.charAt(1));
        int column = converter.convertPosIntoArrayCoordinate(move.charAt(0));

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
                if((checkKingMoves(move) == Messages.MOVE_ALLOWED) && isKingTargetFree(gameBoard, move))
                    return Messages.MOVE_ALLOWED;
                else
                    return Messages.ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_KING;

            case "pawn":
                return checkPawnMoves(gameBoard, move);

            default:
                return Messages.ERROR_NO_SUCH_PLAYINGPIECE;
        }
    }

    private static Messages checkPawnMoves(GameBoard gameBoard, String move){
        Field target = converter.getChessTargetField(move);
        Field start = converter.getChessStartField(move);
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
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)){
            return Messages.MOVE_ALLOWED;
        }else if(target.getRow() == start.getRow()-1
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)){
            return Messages.MOVE_ALLOWED;
        }

        //en passant
        if(target.getRow() == start.getRow()+1
                && ((target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1))
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && isEnPassantAllowed(gameBoard, start, target)){
            return Messages.MOVE_ALLOWED;
        }else if(target.getRow() == start.getRow()-1
                && ((target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1))
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && isEnPassantAllowed(gameBoard, start, target)){
            return Messages.MOVE_ALLOWED;
        }

        //move one square diagonally forward (only if there is a playing piece from the opponent)
        if(target.getRow() == start.getRow()+1
                && ((target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1))
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)
                && Rules.isFieldOccupied(gameBoard, target.getRow(), target.getColumn())){
            return Messages.MOVE_ALLOWED;
        }else if(target.getRow() == start.getRow()-1
                && ((target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1))
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && !isFieldOccupiedByOwnPlayingP(gameBoard, move)
                && Rules.isFieldOccupied(gameBoard, target.getRow(), target.getColumn())){
            return Messages.MOVE_ALLOWED;
        }

        return Messages.ERROR_WRONGMOVEMENT_DIRECTION_PAWN;
    }

    public boolean isPromotion(GameBoard gameBoard, PlayingPiece[][] stateToCheck){
        Field target = converter.getChessTargetField(converter.stateToString(gameBoard.getState(), stateToCheck));
        Field start = converter.getChessStartField(converter.stateToString(gameBoard.getState(), stateToCheck));

        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(!gameBoard.getState()[i][j].equals(stateToCheck[i][j]))
                    counter++;
            }
        }

        //more than one piece has been moved
        if(counter > 2){
            return false;
        }

        //promotion
        if(gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && target.getRow() == 0){
            return true;
        }else if(gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && target.getRow() == 7){
            return true;
        }

        return false;
    }

    private static boolean isPawnPathFree(GameBoard board, Field start){
        if(board.getState()[start.getRow()][start.getColumn()].getColour().equals("black"))
            return !Rules.isFieldOccupied(board, start.getRow()+1, start.getColumn());
        else
            return !Rules.isFieldOccupied(board, start.getRow()-1, start.getColumn());
    }

    private static boolean isEnPassantAllowed(GameBoard board, Field start, Field target){
        return !board.getStateFromMemento().getState()[start.getRow()][target.getColumn()]
                    .equals(board.getState()[start.getRow()][target.getColumn()])
                && board.getState()[start.getRow()][target.getColumn()].getName() != "null"
                && !board.getState()[start.getRow()][target.getColumn()].getColour()
                        .equals(board.getState()[start.getRow()][start.getColumn()].getColour());
    }

    private static Messages checkKingMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<Field>();

        Field target = converter.getChessTargetField(move);
        Field start = converter.getChessStartField(move);

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

        for (Field possibleLocation : possibleLocations) {
            if (possibleLocation.equals(target)) {
                return Messages.MOVE_ALLOWED;
            }
        }

        return Messages.ERROR_WRONGMOVEMENT_DIRECTION_KING;
    }

    private static boolean isKingTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }


    private static Messages checkQueenMoves(String move){
        if (checkVerticalAndHorizontalMoves(move) || checkDiagonalMoves(move))
            return Messages.MOVE_ALLOWED;
        else
            return Messages.ERROR_WRONGMOVEMENT_DIRECTION_QUEEN;
    }

    private static boolean isQueenPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move) || areDiagonalPathsFree(gameBoard, move);
    }

    private static Messages checkBishopMoves(String move){
        if(checkDiagonalMoves(move))
            return Messages.MOVE_ALLOWED;
        else
            return Messages.ERROR_WRONGMOVEMENT_DIRECTION_BISHOP;
    }

    private static boolean isBishopPathFree(GameBoard gameBoard, String move){
        return areDiagonalPathsFree(gameBoard, move);
    }

    private static Messages checkKnightMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<Field>();

        Field target = converter.getChessTargetField(move);
        Field start = converter.getChessStartField(move);

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

        //check whether the target is one of the possible locations and
        for (Field possibleLocation : possibleLocations) {
            if (possibleLocation.equals(target)) {
                return Messages.MOVE_ALLOWED;
            }
        }

        return Messages.ERROR_WRONGMOVEMENT_DIRECTION_KNIGHT;
    }

    private static boolean isKnightTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }

    private static Messages checkRookMoves(String move){
        if(checkVerticalAndHorizontalMoves(move))
            return Messages.MOVE_ALLOWED;
        else
            return Messages.ERROR_WRONGMOVEMENT_DIRECTION_ROOK;
    }

    private static boolean isRookPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move);
    }

    private static boolean isMoveCastling(GameBoard gameBoard, PlayingPiece[][] stateToCheck){
        int counter = 0;
        ArrayList<Field> check = new ArrayList<Field>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(!gameBoard.getState()[i][j].equals(stateToCheck[i][j]) && gameBoard.getState()[i][j].getName() != "null") {
                    counter++;
                    check.add(new Field(i, j));
                }
            }
        }

        Field rook;
        Field king;

        // if two playing piece have changed location
        if(counter == 2 && arePieceRookAndKingFromSameColour(check, gameBoard.getState())){

            if(gameBoard.getState()[check.get(0).getRow()][check.get(0).getColumn()].getName().equals("rook")){
                rook = new Field(check.get(0).getRow(), check.get(0).getColumn());
                king = new Field(check.get(1).getRow(), check.get(1).getColumn());
            }else {
                rook = new Field(check.get(1).getRow(), check.get(1).getColumn());
                king = new Field(check.get(0).getRow(), check.get(0).getColumn());
            }

            if(gameBoard.getState()[rook.getRow()][rook.getColumn()].hasMoved()
                    || gameBoard.getState()[king.getRow()][king.getColumn()].hasMoved()){
                return false;
            }else {
                return isCastlingPathFree(gameBoard, rook);
            }
        }

        return false;
    }

    private static boolean arePieceRookAndKingFromSameColour(ArrayList<Field> f, PlayingPiece[][] board){

        if( (board[f.get(0).getRow()][f.get(0).getColumn()].getName().equals("rook")
                && board[f.get(1).getRow()][f.get(1).getColumn()].getName().equals("king"))
                ||
                (board[f.get(0).getRow()][f.get(0).getColumn()].getName().equals("king")
                && board[f.get(1).getRow()][f.get(1).getColumn()].getName().equals("rook")) ){

            return board[f.get(0).getRow()][f.get(0).getColumn()].getColour()
                    .equals(board[f.get(1).getRow()][f.get(1).getColumn()].getColour());
        }

        return false;
    }

    private static boolean isCastlingPathFree(GameBoard board, Field rook){
        if(rook.getRow()==0 && rook.getColumn()==0){
            for (int i = 1; i < 4; i++) {
                if(Rules.isFieldOccupied(board, 0, i) || isFieldAttacked(board, 0, i)){
                    return false;
                }
            }
        }else if(rook.getRow()==0 && rook.getColumn()==7){
            for (int i = 5; i < 7; i++) {
                if(Rules.isFieldOccupied(board, 0, i) || isFieldAttacked(board, 0, i)){
                    return false;
                }
            }
        }else if(rook.getRow()==7 && rook.getColumn()==0){
            for (int i = 1; i < 4; i++) {
                if(Rules.isFieldOccupied(board, 7, i) || isFieldAttacked(board, 7, i)){
                    return false;
                }
            }
        }else{
            for (int i = 5; i < 7; i++) {
                if(Rules.isFieldOccupied(board, 7, i) || isFieldAttacked(board, 7, i)){
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isFieldAttacked(GameBoard board, int row, int column){
        String move = "";
        StringBuilder stringB = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                stringB.append(converter.convertArrayCoordinateIntoPosColumn(j));
                stringB.append(converter.convertArrayCoordinateIntoPosRow(i));
                stringB.append(" ");
                stringB.append(converter.convertArrayCoordinateIntoPosColumn(column));
                stringB.append(converter.convertArrayCoordinateIntoPosRow(row));

                move = stringB.toString();

                if(checkEachPossibleMove(board, move) == Messages.MOVE_ALLOWED)
                    return true;
            }
        }
        return false;
    }

    private boolean kingCannotEscape(GameBoard board, int row, int column){
        //check whether there is a field where king could escape
        if( (row-1 >= 0) && (column-1 >= 0) ){
            if(!isFieldAttacked(board, row-1, column-1))
                return false;
        }

        if(row-1 >= 0){
            if(!isFieldAttacked(board, row-1, column))
                return false;
        }

        if( (row-1 >= 0) && (column+1 <= 7) ){
            if(!isFieldAttacked(board, row-1, column+1))
                return false;
        }

        if(column+1 <= 7){
            if(!isFieldAttacked(board, row, column+1))
                return false;
        }

        if( (row+1 <= 7) && (column+1 <= 7) ){
            if(!isFieldAttacked(board, row+1, column+1))
                return false;
        }

        if(row+1 <= 7){
            if(!isFieldAttacked(board, row+1, column))
                return false;
        }

        if( (row+1 <= 7) && (column-1 >= 0) ){
            if(!isFieldAttacked(board, row+1, column-1))
                return false;
        }

        if(column-1 >= 0){
            if(!isFieldAttacked(board, row, column-1))
                return false;
        }

        String colour = board.getState()[row][column].getColour();

        //check if castling possible
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

    private static boolean checkDiagonalMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<Field>();

        Field target = converter.getChessTargetField(move);
        Field start = converter.getChessStartField(move);

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


    private static boolean areDiagonalPathsFree(GameBoard gameBoard, String move){
        Field target = converter.getChessTargetField(move);
        Field start = converter.getChessStartField(move);

        if(isFieldOccupiedByOwnPlayingP(gameBoard, move))
            return false;


        //if target is on the upper-left diagonal
        int row = start.getRow() - 1;
        int column = start.getColumn() - 1;

        if( (target.getRow() < start.getRow()) && (target.getColumn() < start.getColumn()) ){

            while( (row > target.getRow()) && (column > target.getColumn()) ){
                if(Rules.isFieldOccupied(gameBoard, row, column)){
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
                if(Rules.isFieldOccupied(gameBoard, row, column)){
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
                if(Rules.isFieldOccupied(gameBoard, row, column)){
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
                if(Rules.isFieldOccupied(gameBoard, row, column)){
                    return false;
                }
                row++;
                column--;
            }
        }

        return true;
    }

    private static boolean checkVerticalAndHorizontalMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<Field>();

        Field target = converter.getChessTargetField(move);
        Field start = converter.getChessStartField(move);

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


    private static boolean areVerticalOrHorizontalPathsFree(GameBoard gameBoard, String move){
        Field target = converter.getChessTargetField(move);
        Field start = converter.getChessStartField(move);

        if(isFieldOccupiedByOwnPlayingP(gameBoard, move))
            return false;

        if(start.getRow()==target.getRow()){

            if(start.getColumn() < target.getColumn()){
                //rook moved to the right side
                for(int i = start.getColumn(); i < target.getColumn(); i++){
                    if(Rules.isFieldOccupied(gameBoard, start.getRow(), i)){
                        return false;
                    }
                }
            } else{
                //rook moved to the left side
                for(int i = start.getColumn(); i > target.getColumn(); i--) {
                    if (Rules.isFieldOccupied(gameBoard, start.getRow(), i)) {
                        return false;
                    }
                }
            }

        }else{

            if(start.getRow()>target.getRow()){
                // rook moved upwards
                for (int i = start.getRow(); i > target.getRow() ; i--) {
                    if(Rules.isFieldOccupied(gameBoard, i, start.getColumn())){
                        return false;
                    }
                }
            }else{
                //rook moved downwards
                for (int i = start.getRow(); i < target.getRow() ; i++) {
                    if(Rules.isFieldOccupied(gameBoard, i, start.getColumn())){
                        return false;
                    }
                }
            }

        }
        return true;
    }

    private boolean checkmate(GameBoard gameBoard, String colour){
        int row = -1;
        int column = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(gameBoard.getState()[i][j].getColour().equals(colour)
                        && gameBoard.getState()[i][j].getName().equals("king")) {
                    row = i;
                    column = i;
                }
            }
        }

        //king is not in the game anymore or can not escape
        if((row == -1) || kingCannotEscape(gameBoard, row, column))
            return true;

        return false;
    }

    private boolean isStalemate(GameBoard board, String colour){
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

    private boolean possibleMoveExists(GameBoard board, Field start){
        StringBuilder move = new StringBuilder();
        move.append(start.getColumn());
        move.append(start.getRow() + " ");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                move.append(converter.convertArrayCoordinateIntoPosColumn(j));
                move.append(converter.convertArrayCoordinateIntoPosRow(i));

                if(checkEachPossibleMove(board, move.toString()) == Messages.MOVE_ALLOWED){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean deadPosition(GameBoard board){
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

        if(black_counter > 1 || white_counter > 1)
            return false;

        return true;
    }


    //String colour: colour of player whose turn it is to move
    public Messages isGameFinished(GameBoard board, String colour){
        if(checkmate(board, colour))
            return Messages.DEFEATED;
        else if(isStalemate(board, colour) || deadPosition(board))
            return Messages.DRAW;
        else
            return Messages.GO_ON;
    }
}
