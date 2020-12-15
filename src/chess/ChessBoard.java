package src.chess;

import src.games.*;

/**
 *   ChessBoard extends the class GameBoard from src.Games and is used for
 *   constructing an initial chess board.
 *
 *   @author Begüm Tosun, Alexander Posch
 */
public class ChessBoard extends GameBoard {

    static final String BLACK = "black";
    static final String WHITE = "white";
    static final String ROOK = "rook";
    static final String KNIGHT = "knight";
    static final String BISHOP = "bishop";
    static final String QUEEN = "queen";
    static final String KING = "king";
    static final String PAWN = "pawn";
    private PlayingPiece[] playingPieces = new PlayingPiece[32];

    /**
     * The constructor creates a chess board with an ID and its initial state.
     */
    public ChessBoard(){
        super.setGameBoardID(System.currentTimeMillis());
        for(int i = 0; i < 32; i++){
            playingPieces[i] = new PlayingPiece();
        }

        playingPieces[0].setName(ROOK);
        playingPieces[0].setColour(BLACK);
        playingPieces[0].setPosition("A8");

        playingPieces[1].setName(KNIGHT);
        playingPieces[1].setColour(BLACK);
        playingPieces[1].setPosition("B8");

        playingPieces[2].setName(BISHOP);
        playingPieces[2].setColour(BLACK);
        playingPieces[2].setPosition("C8");

        playingPieces[3].setName(QUEEN);
        playingPieces[3].setColour(BLACK);
        playingPieces[3].setPosition("D8");

        playingPieces[4].setName(KING);
        playingPieces[4].setColour(BLACK);
        playingPieces[4].setPosition("E8");

        playingPieces[5].setName(BISHOP);
        playingPieces[5].setColour(BLACK);
        playingPieces[5].setPosition("F8");

        playingPieces[6].setName(KNIGHT);
        playingPieces[6].setColour(BLACK);
        playingPieces[6].setPosition("G8");

        playingPieces[7].setName(ROOK);
        playingPieces[7].setColour(BLACK);
        playingPieces[7].setPosition("H8");

        for (int x = 8; x < 16; x++) {
            playingPieces[x].setName(PAWN);
            playingPieces[x].setColour(BLACK);
            playingPieces[x].setPosition(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(x - 8)+"7");
        }

        playingPieces[16].setName(ROOK);
        playingPieces[16].setColour(WHITE);
        playingPieces[16].setPosition("A1");

        playingPieces[17].setName(KNIGHT);
        playingPieces[17].setColour(WHITE);
        playingPieces[17].setPosition("B1");

        playingPieces[18].setName(BISHOP);
        playingPieces[18].setColour(WHITE);
        playingPieces[18].setPosition("C1");

        playingPieces[19].setName(QUEEN);
        playingPieces[19].setColour(WHITE);
        playingPieces[19].setPosition("D1");

        playingPieces[20].setName(KING);
        playingPieces[20].setColour(WHITE);
        playingPieces[20].setPosition("E1");

        playingPieces[21].setName(BISHOP);
        playingPieces[21].setColour(WHITE);
        playingPieces[21].setPosition("F1");

        playingPieces[22].setName(KNIGHT);
        playingPieces[22].setColour(WHITE);
        playingPieces[22].setPosition("G1");

        playingPieces[23].setName(ROOK);
        playingPieces[23].setColour(WHITE);
        playingPieces[23].setPosition("H1");

        for (int x = 24; x < 32; x++) {
            playingPieces[x].setName(PAWN);
            playingPieces[x].setColour(WHITE);
            playingPieces[x].setPosition(ChessMoveConverter.convertArrayCoordinateIntoPosColumn(x - 24)+"2");
        }

        super.setState(setUpPlayingPieces());
    }

    /**
     * The method setUpPlayingPieces() returns a chess board with an initial setup.
     * @return ChessPlayingPiece[][]
     */
    @Override
    protected PlayingPiece[][] setUpPlayingPieces(){
        PlayingPiece[][] initialState = new PlayingPiece[8][8];
        for(int k = 0 ; k < 8; k ++) {
            for (int j = 0; j < 8; j ++){
                initialState[k][j] = new PlayingPiece();
                initialState[k][j].setName("null");
                initialState[k][j].setColour("null");
            }
        }

        initialState[0][0] = playingPieces[0];

        initialState[0][1] = playingPieces[1];

        initialState[0][2] = playingPieces[2];

        initialState[0][3] = playingPieces[3];

        initialState[0][4] = playingPieces[4];

        initialState[0][5] = playingPieces[5];

        initialState[0][6] = playingPieces[6];

        initialState[0][7] = playingPieces[7];

        for (int x = 0; x < 8; x++) {
            initialState[1][x] = playingPieces[8 + x];
        }

        for (int x = 0; x < 8; x++) {
            initialState[6][x] = playingPieces[24 + x];
        }

        initialState[7][0] = playingPieces[16];

        initialState[7][1] = playingPieces[17];

        initialState[7][2] = playingPieces[18];

        initialState[7][3] = playingPieces[19];

        initialState[7][4] = playingPieces[20];

        initialState[7][5] = playingPieces[21];

        initialState[7][6] = playingPieces[22];

        initialState[7][7] = playingPieces[23];

        return initialState;
    }
    
    protected PlayingPiece[] getPlayingPieces(){
        return playingPieces;
    }

    @Override
    public void setNewBoard(String input) throws WrongFormatException {
        PlayingPiece[][] newBoard = new PlayingPiece[8][8];
        StringBuilder position = new StringBuilder();
        int counter = 0;
        int column;
        int row;

        for(int k = 0 ; k < 8; k ++) {
            for (int j = 0; j < 8; j ++){
                newBoard[k][j] = new PlayingPiece();
                newBoard[k][j].setName("null");
                newBoard[k][j].setColour("null");
            }
        }
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '='){

                if(input.charAt(i+1) != 'n')
                    position.append(input.charAt(i + 1)).append(input.charAt(i + 2));
                else    
                    position.append("null");

                this.getPlayingPieces()[counter].setPosition(position.toString());

                row = ChessMoveConverter.convertPosIntoArrayCoordinate(input.charAt(i+2));
                column = ChessMoveConverter.convertPosIntoArrayCoordinate(input.charAt(i + 1));
            
                if(row != -2 && column != -2)
                    newBoard[row][column] = this.getPlayingPieces()[counter];

                position = new StringBuilder();
                counter++;
                i = i + 3;
            }
        }

        this.setState(newBoard);
    }
}
