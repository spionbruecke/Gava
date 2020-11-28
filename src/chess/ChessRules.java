package src.chess;

import src.Games.*;

import java.util.ArrayList;

public class ChessRules implements Rules {

    @Override
    public boolean isFieldOccupied(GameBoard board, Field f) {
        int row = f.getRow();
        int column = f.getColumn();

        if(board.getState()[row][column]==null) {
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean isFieldOccupiedByOwnPlayingP(GameBoard board, String move) {
        int row = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(4));
        int column = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(3));

        String ownColour = board.getState()[(int)(move.charAt(1))][(int)(move.charAt(0))].getColour();

        if(board.getState()[row][column]==null){
            return false;
        }else if(board.getState()[row][column].getColour().equals(ownColour)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean isMoveAllowed(GameBoard gameBoard, String move) {
        boolean permission;

        int row = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(1));
        int column = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(0));

        switch(gameBoard.getState()[row][column].getName()){
            case "rook":
                permission = checkRookMoves(move) && isRookPathFree(gameBoard, move);
                break;

            case "knight":
                permission = checkKnightMoves(move) && isKnightTargetFree(gameBoard, move);
                break;

            case "bishop":
                permission = checkBishopMoves(move) && isBishopPathFree(gameBoard, move);
                break;

            case "queen":
                permission = checkQueenMoves(move) && isQueenPathFree(gameBoard, move);
                break;

            case "king":
                permission = checkKingMoves(move) && isKingTargetFree(gameBoard, move);
                break;

            case "pawn":
                permission = checkPawnMoves(gameBoard, move);
                break;

            default:
                permission = false;
                break;
        }

        return permission;
    }
    
    private boolean checkPawnMoves(GameBoard gameBoard, String move){
        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);


        if(target.getRow() == start.getRow()+2){
            if(!gameBoard.getState()[start.getRow()][start.getColumn()].hasMoved() &&
                    !isFieldOccupied(gameBoard, getPawnPathFor2Steps(gameBoard, start)) &&
                    !isFieldOccupiedByOwnPlayingP(gameBoard, move)){
                return true;
            }else{
                return false;
            }
        }else if(target.getRow() == start.getRow()+1){
            return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
        }else{
            return false;
        }
    }

    private Field getPawnPathFor2Steps(GameBoard board, Field start){
        if(start.getRow() == 1){
            return new Field(start.getRow()+1, start.getColumn());
        }else{
            return new Field(start.getRow()-1, start.getColumn());
        }
    }

    private boolean checkKingMoves(String move){
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

        for (int i = 0; i < possibleLocations.size(); i++) {
            if (possibleLocations.get(i) == target) {
                return true;
            }
        }

        return false;
    }

    private boolean isKingTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }

    private boolean checkQueenMoves(String move){
        return checkVerticalAndHorizontalMoves(move) || checkDiagonalMoves(move);
    }

    private boolean isQueenPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move) || areDiagonalPathsFree(gameBoard, move);
    }

    private boolean checkBishopMoves(String move){
        return checkDiagonalMoves(move);
    }

    private boolean isBishopPathFree(GameBoard gameBoard, String move){
        return areDiagonalPathsFree(gameBoard, move);
    }

    private boolean checkKnightMoves(String move){
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
        for (int i = 0; i < possibleLocations.size(); i++) {
            if(possibleLocations.get(i) == target){
                return true;
            }
        }

        return false;
    }

    private boolean isKnightTargetFree(GameBoard gameBoard, String move){
        return !isFieldOccupiedByOwnPlayingP(gameBoard, move);
    }

    private boolean checkRookMoves(String move){
        return checkVerticalAndHorizontalMoves(move);
    }

    private boolean isRookPathFree(GameBoard gameBoard, String move){
        return areVerticalOrHorizontalPathsFree(gameBoard, move);
    }

    private boolean checkDiagonalMoves(String move){
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

        for (int i = 0; i < possibleLocations.size(); i++) {
            if(possibleLocations.get(i) == target){
                return true;
            }
        }

        return false;
    }

    private boolean areDiagonalPathsFree(GameBoard gameBoard, String move){
        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);

        if(isFieldOccupiedByOwnPlayingP(gameBoard, move))
            return false;


        //if target is on the upper-left diagonal
        int row = start.getRow() - 1;
        int column = start.getColumn() - 1;

        if( (target.getRow() < start.getRow()) && (target.getColumn() < start.getColumn()) ){

            while( (row > target.getRow()) && (column > target.getColumn()) ){
                if(gameBoard.getState()[row][column].getName() != null){
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
                if(gameBoard.getState()[row][column].getName() != null){
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
                if(gameBoard.getState()[row][column].getName() != null){
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
                if(gameBoard.getState()[row][column].getName() != null){
                    return false;
                }
                row++;
                column--;
            }
        }

        return true;
    }

    private boolean checkVerticalAndHorizontalMoves(String move){
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
        for (int i = 0; i < possibleLocations.size(); i++) {
            if(possibleLocations.get(i) == target){
                return true;
            }
        }

        return false;
    }

    private boolean areVerticalOrHorizontalPathsFree(GameBoard gameBoard, String move){
        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);

        if(isFieldOccupiedByOwnPlayingP(gameBoard, move))
            return false;

        if(start.getRow()==target.getRow()){

            if(start.getColumn() < target.getColumn()){
                //rook moved to the left side
                for(int i = start.getColumn(); i < target.getColumn(); i++){
                    if(gameBoard.getState()[start.getRow()][i].getName() != null){
                        return false;
                    }
                }
            } else{
                //rook moved to right side
                for(int i = start.getColumn(); i > target.getColumn(); i--) {
                    if (gameBoard.getState()[start.getRow()][i].getName() != null) {
                        return false;
                    }
                }
            }

        }else{

            if(start.getRow()>target.getRow()){
                // rook moved upwards
                for (int i = start.getRow(); i > target.getRow() ; i--) {
                    if(gameBoard.getState()[i][start.getColumn()].getName() != null){
                        return false;
                    }
                }
            }else{
                //rook moved downwards
                for (int i = start.getRow(); i < target.getRow() ; i++) {
                    if(gameBoard.getState()[i][start.getColumn()].getName() != null){
                        return false;
                    }
                }
            }

        }
        return true;
    }

}
