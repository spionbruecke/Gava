package src.mill;

import src.games.GameBoard;
import src.games.PlayingPiece;
import src.games.WrongFormatException;

public class MillBoard extends GameBoard {

    private PlayingPiece[] playingPieces = new PlayingPiece[18];

    public MillBoard(){
        super.setState(setUpBoard());

        for(int i = 0; i < 18; i ++){
            playingPieces[i] = new PlayingPiece();
            playingPieces[i].setName("token");
            if(i < 9)
                playingPieces[i].setColour("white");
            else
                playingPieces[i].setColour("black");
            playingPieces[i].setPosition("null");
        }
    }

    @Override
    public PlayingPiece[] getPlayingPieces() {
        return playingPieces;
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
                }
            else
                for (int j = 0; j < 3; j ++){
                    initialState[k][j] = new PlayingPiece();
                    initialState[k][j].setName("null");
                    initialState[k][j].setColour("null");
                }
        }

        return initialState;
    }
    
    @Override
    public void setNewBoard(String input) throws WrongFormatException {
        MillBoard board = new MillBoard();
        PlayingPiece[][] newBoard = board.getState();
        StringBuilder position = new StringBuilder();
        int counter = 0;
        int column;
        int row;

        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '='){

                if(input.charAt(i+1) != 'n')
                    position.append(input.charAt(i + 1)).append(input.charAt(i + 2));
                else   
                    position.append("null");

                if(!this.getPlayingPieces()[counter].getPosition().equals(position.toString()))
                    this.getPlayingPieces()[counter].setHasMoved();

                this.getPlayingPieces()[counter].setPosition(position.toString());
                
                if(input.charAt(i - 1) == '1')
                    this.getPlayingPieces()[counter].setHasMoved();
                    
                row = MillMoveConverter.convertPosIntoArrayCoordinate(input.charAt(i+2));
                column = MillMoveConverter.convertPosIntoArrayCoordinate(input.charAt(i + 1));
            
                if(row != -2 && column != -2)
                    newBoard[row][column] = this.getPlayingPieces()[counter];

                position = new StringBuilder();
                counter++;
                i = i + 3;
            }
        }

        this.setState(newBoard);
    }

    public static int getNumOfPieces(PlayingPiece[][] state, String colour){
        int counter = 0;

        for(int k = 0 ; k < 7; k ++) {
            if(k == 3)
                for (int j = 0; j < 6; j ++){
                    if(state[k][j].getColour().equals(colour))
                        counter++;
                }
            else
                for (int j = 0; j < 3; j ++){
                    if(state[k][j].getColour().equals(colour))
                        counter++;
                }
        }

        return counter;
    }

}
