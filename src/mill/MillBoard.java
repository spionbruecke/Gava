package src.mill;

import src.games.GameBoard;
import src.games.PlayingPiece;
import src.games.WrongFormatException;

public class MillBoard extends GameBoard {

    private int counterBlack = 9;
    private int counterWhite = 9;

    public MillBoard(){
        super.setState(setUpBoard());
    }

    @Override
    public PlayingPiece[] getPlayingPieces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setNewBoard(String input) throws WrongFormatException {
        // TODO Auto-generated method stub

    }

    @Override
    protected PlayingPiece[][] setUpBoard() {
        PlayingPiece[][] initialState = new PlayingPiece[7][];

        for(int k = 0 ; k < 7; k ++) {
            if(k < 3 || k >3)
                initialState[k] = new PlayingPiece[3];
            else
                initialState[k] = new PlayingPiece[6];
        }

        for(int k = 0 ; k < 7; k ++) {
            if(k == 3)
                for (int j = 0; j < 6; j ++){
                    initialState[k][j] = new PlayingPiece();
                    initialState[k][j].setName("null");
                    initialState[k][j].setColour("null");
                    initialState[k][j].setPosition(MillMoveConverter.convertArrayCoordinateIntoPosRow(k)+ MillMoveConverter.convertArrayCoordinateIntoPosColumn(j));
                }
            else
                for (int j = 0; j < 3; j ++){
                    initialState[k][j] = new PlayingPiece();
                    initialState[k][j].setName("null");
                    initialState[k][j].setColour("null");
                    initialState[k][j].setPosition(MillMoveConverter.convertArrayCoordinateIntoPosRow(k)+ MillMoveConverter.convertArrayCoordinateIntoPosColumn(j));
                }
        }

        return initialState;
    }
    
}
