package src.chess;

import src.Games.*;
import java.util.ArrayList;


/**
 * @author Begüm Tosun
 *
 * ChessRules implements Rules from src.Games and checks if the given move is valid.
 */
public class ChessRules implements Rules {

    /**
     * Checks if the target square/field of the given move is occupied by ones own playing piece.
     * @param board GameBoard
     * @param move String
     * @return boolean
     */
    public static boolean isFieldOccupiedByOwnPlayingP(GameBoard board, String move) {
        int row = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(4));
        int column = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(3));

        String ownColour = board.getState()[(int)(move.charAt(1))][(int)(move.charAt(0))].getColour();

        if(board.getState()[row][column]==null){
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
    public static boolean isMoveAllowed(GameBoard gameBoard, ChessPlayingPiece[][] stateToCheck) {
        if(isMoveCastling(gameBoard, stateToCheck))
            return true;

        String move = MoveConverter.stateToString(gameBoard.getState(), stateToCheck);

        return checkEachPossibleMove(gameBoard, stateToCheck, move);
    }

    private static boolean checkEachPossibleMove(GameBoard gameBoard, ChessPlayingPiece[][] stateToCheck, String move){
        int row = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(1));
        int column = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(0));

        switch(gameBoard.getState()[row][column].getName()){
            case "rook":
                return checkRookMoves(move) && isRookPathFree(gameBoard, move);

            case "knight":
                return checkKnightMoves(move) && isKnightTargetFree(gameBoard, move);

            case "bishop":
                return checkBishopMoves(move) && isBishopPathFree(gameBoard, move);

            case "queen":
                return checkQueenMoves(move) && isQueenPathFree(gameBoard, move);

            case "king":
                return checkKingMoves(move) && isKingTargetFree(gameBoard, move);

            case "pawn":
                return checkPawnMoves(gameBoard, stateToCheck);

            default:
                return false;
        }
    }

    private static boolean checkPawnMoves(GameBoard gameBoard, ChessPlayingPiece[][] stateToCheck){
        Field target = MoveConverter.getTargetField(MoveConverter.stateToString(gameBoard.getState(), stateToCheck));
        Field start = MoveConverter.getStartField(MoveConverter.stateToString(gameBoard.getState(), stateToCheck));

        // move two squares forward
        if(target.getRow() == start.getRow()+2
                && !gameBoard.getState()[start.getRow()][start.getColumn()].hasMoved()
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && isPawnPathFree(gameBoard, start)
                && isFieldOccupiedByOwnPlayingP(gameBoard, MoveConverter.stateToString(gameBoard.getState(), stateToCheck))){
            return true;
        }else if(target.getRow() == start.getRow()-2
                && !gameBoard.getState()[start.getRow()][start.getColumn()].hasMoved()
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && isPawnPathFree(gameBoard, start)
                && isFieldOccupiedByOwnPlayingP(gameBoard, MoveConverter.stateToString(gameBoard.getState(), stateToCheck))){
            return true;
        }

        // move one square forward
        if(target.getRow() == start.getRow()+1
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && isFieldOccupiedByOwnPlayingP(gameBoard, MoveConverter.stateToString(gameBoard.getState(), stateToCheck))){
            return true;
        }else if(target.getRow() == start.getRow()-1
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && isFieldOccupiedByOwnPlayingP(gameBoard, MoveConverter.stateToString(gameBoard.getState(), stateToCheck))){
            return true;
        }

        //en passant
        if(target.getRow() == start.getRow()+1
                && (target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1)
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && isEnPassantAllowed(gameBoard, start, target)){
            return true;
        }else if(target.getRow() == start.getRow()-1
                && (target.getColumn() == start.getColumn()+1 || target.getColumn() == start.getColumn()-1)
                && gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && isEnPassantAllowed(gameBoard, start, target)){
            return true;
        }

        //promotion
        if(gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("white")
                && target.getRow() == 0){
            stateToCheck[target.getRow()][target.getColumn()].promotion("TODO");
            return true;
        }else if(gameBoard.getState()[start.getRow()][start.getColumn()].getColour().equals("black")
                && target.getRow() == 7){
            stateToCheck[target.getRow()][target.getColumn()].promotion("TODO");
            return true;
        }

        return false;
    }

    private static boolean isPawnPathFree(GameBoard board, Field start){
        if(board.getState()[start.getRow()][start.getColumn()].getColour().equals("black"))
            return Rules.isFieldOccupied(board, start.getRow()+1, start.getColumn());
        else
            return Rules.isFieldOccupied(board, start.getRow()-1, start.getColumn());
    }

    private static boolean isEnPassantAllowed(GameBoard board, Field start, Field target){
        return board.getStateFromMemento().getState()[start.getRow()][target.getColumn()].getName() == null
                && board.getState()[start.getRow()][target.getColumn()] != null;
    }

    private static boolean checkKingMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<Field>();

        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);

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
            if (possibleLocation == target) {
                return true;
            }
        }

        return false;
    }

    private static boolean isKingTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }


    private static boolean checkQueenMoves(String move){
        return checkVerticalAndHorizontalMoves(move) || checkDiagonalMoves(move);
    }

    private static boolean isQueenPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move) || areDiagonalPathsFree(gameBoard, move);
    }

    private static boolean checkBishopMoves(String move){
        return checkDiagonalMoves(move);
    }

    private static boolean isBishopPathFree(GameBoard gameBoard, String move){
        return areDiagonalPathsFree(gameBoard, move);
    }

    private static boolean checkKnightMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<Field>();

        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);

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
            if (possibleLocation == target) {
                return true;
            }
        }

        return false;
    }

    private static boolean isKnightTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }

    private static boolean checkRookMoves(String move){
        return checkVerticalAndHorizontalMoves(move);
    }

    private static boolean isRookPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move);
    }

    private static boolean isMoveCastling(GameBoard gameBoard, ChessPlayingPiece[][] stateToCheck){
        int counter = 0;
        ArrayList<Field> check = new ArrayList<Field>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(gameBoard.getState()[i][j] != stateToCheck[i][j] && gameBoard.getState()[i][j].getName() != null) {
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
                return isCastlingPathFree(gameBoard, rook, stateToCheck);
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

            return board[f.get(1).getRow()][f.get(1).getColumn()].getColour()
                    .equals(board[f.get(1).getRow()][f.get(1).getColumn()].getColour());
        }

        return false;
    }

    private static boolean isCastlingPathFree(GameBoard board, Field rook, ChessPlayingPiece[][] stateToCheck){
        if(rook.getRow()==0 && rook.getColumn()==0){
            for (int i = 1; i < 4; i++) {
                if(Rules.isFieldOccupied(board, 0, i) || isFieldAttacked(board, 0, i, stateToCheck)){
                    return false;
                }
            }
        }else if(rook.getRow()==0 && rook.getColumn()==7){
            for (int i = 5; i < 7; i++) {
                if(Rules.isFieldOccupied(board, 0, i) || isFieldAttacked(board, 0, i, stateToCheck)){
                    return false;
                }
            }
        }else if(rook.getRow()==7 && rook.getColumn()==0){
            for (int i = 1; i < 4; i++) {
                if(Rules.isFieldOccupied(board, 7, i) || isFieldAttacked(board, 7, i, stateToCheck)){
                    return false;
                }
            }
        }else{
            for (int i = 5; i < 7; i++) {
                if(Rules.isFieldOccupied(board, 7, i) || isFieldAttacked(board, 7, i, stateToCheck)){
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isFieldAttacked(GameBoard board, int row, int column, ChessPlayingPiece[][] stateToCheck){
        String move = "";
        StringBuilder stringB = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                stringB.append(MoveConverter.convertArrayCoordinateIntoPosColumn(j));
                stringB.append(MoveConverter.convertArrayCoordinateIntoPosRow(i));
                stringB.append(" ");
                stringB.append(MoveConverter.convertArrayCoordinateIntoPosColumn(column));
                stringB.append(MoveConverter.convertArrayCoordinateIntoPosRow(row));

                move = stringB.toString();

                if(checkEachPossibleMove(board, stateToCheck, move))
                    return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonalMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<Field>();

        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);

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
            if (possibleLocation == target) {
                return true;
            }
        }

        return false;
    }


    private static boolean areDiagonalPathsFree(GameBoard gameBoard, String move){
        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);

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

        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);

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
            if(possibleLocations.get(i) == startPos) {
                possibleLocations.remove(i);
            }
        }

        //check whether the targetPos is one of the possible locations
        for (Field possibleLocation : possibleLocations) {
            if (possibleLocation == target) {
                return true;
            }
        }

        return false;
    }


    private static boolean areVerticalOrHorizontalPathsFree(GameBoard gameBoard, String move){
        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);

        if(isFieldOccupiedByOwnPlayingP(gameBoard, move))
            return false;

        if(start.getRow()==target.getRow()){

            if(start.getColumn() < target.getColumn()){
                //rook moved to the left side
                for(int i = start.getColumn(); i < target.getColumn(); i++){
                    if(Rules.isFieldOccupied(gameBoard, start.getRow(), i)){
                        return false;
                    }
                }
            } else{
                //rook moved to right side
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

}
