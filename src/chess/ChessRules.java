package src.chess;

import src.Games.*;

import java.util.ArrayList;

public class ChessRules implements Rules {

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
                //check moves for knight
                break;

            case "bishop":
                //check...
                break;

            case "queen":
                //...
                break;

            case "king":
                //...
                break;

            case "pawn":
                //...
                break;

            default:
                permission = false;
                break;
        }

        return permission;
    }

    @Override
    public boolean isFieldOccupied(GameBoard board, String move) {
        int row = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(4));
        int column = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(3));

        if(board.getState()[row][column]==null) {
            return false;
        }else {
            return true;
        }
    }

    private boolean checkRookMoves(String move){
        ArrayList<Field> possibleLocations = new ArrayList<Field>();

        int rowStart = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(1));
        int columnStart = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(0));

        int rowTarget = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(4));
        int columnTarget = MoveConverter.convertPosIntoArrayCoordinate(move.charAt(3));


        //determine every theoretically possible move and add the target field into a list
        for (int i = rowStart; i >= 0; i--) {
            possibleLocations.add(new Field(i, columnStart));
        }

        for (int i = rowStart; i <= 7; i++) {
            possibleLocations.add(new Field(i, columnStart));
        }

        for (int i = columnStart; i >= 0; i--) {
            possibleLocations.add(new Field(rowStart, i));
        }

        for (int i = columnStart; i <= 7; i++) {
            possibleLocations.add(new Field(rowStart, i));
        }

        //delete startPos from List
        Field startPos = new Field(rowStart, columnStart);

        for (int i = 0; i < possibleLocations.size(); i++){
            if(possibleLocations.get(i) == startPos) {
                possibleLocations.remove(i);
            }
        }

        //check whether the targetPos is one of the possible locations
        Field targetPos = new Field(rowTarget, columnTarget);

        for (int i = 0; i < possibleLocations.size(); i++) {
            if(possibleLocations.get(i) == targetPos){
                return true;
            }
        }

        return false;
    }

    private boolean isRookPathFree(GameBoard gameBoard, String move){
        Field target = MoveConverter.getTargetField(move);
        Field start = MoveConverter.getStartField(move);

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
