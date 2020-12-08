package src.chess;

import java.util.Arrays;

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

    public static void main(String[] args) {
        ChessBoard chessi = new ChessBoard();
        String board = ChessMoveConverter.convertPiecesToString(chessi);
        try {
            String old = ChessMoveConverter.convertPiecesToString(chessi);
            chessi.setState(ChessMoveConverter.getBoardFromString(board, chessi));
            String newOne = ChessMoveConverter.convertPiecesToString(chessi);
            System.out.println(newOne.equals(old));
        } catch (WrongFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
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

        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                initialState[j][i].setName(null);
                initialState[j][i].setColour(null);
            }
        }

        for (int x = 0; x < 8; x++) {
            initialState[6][x] = playingPieces[24 + x];
        }

        initialState[7][0] = playingPieces[17];

        initialState[7][1] = playingPieces[18];

        initialState[7][2] = playingPieces[19];

        initialState[7][3] = playingPieces[20];

        initialState[7][4] = playingPieces[21];

        initialState[7][5] = playingPieces[22];

        initialState[7][6] = playingPieces[23];

        initialState[7][7] = playingPieces[24];

        return initialState;
    }

    /*
    * We dont use this anymore
    @Override
    public String getNewMove(String newBoard) {
        StringBuilder newMove = new StringBuilder();
        int tmp = 0;
        for(int i = 0; i < newBoard.length(); i ++){
            if (newBoard.charAt(i) == '=')
                tmp = i;
            if(newBoard.charAt(i) != currentGameBoard.charAt(i)){
                newMove.append(currentGameBoard.charAt(tmp + 1)).append(currentGameBoard.charAt(tmp + 2)).append(" ");
                newMove.append(newBoard.charAt(tmp + 1)).append(newBoard.charAt(tmp + 2));
                return newMove.toString();
            }
        }
        return "No Movement";
    }

    @Override
    public void setNewMove(String move) {
        StringBuilder start = new StringBuilder();
        start.append(move.charAt(0)).append(move.charAt(1));

        StringBuilder end = new StringBuilder();
        end.append(move.charAt(3)).append(move.charAt(4));

        for(int i = 0; i < playingPieces.length; i++){
            if(playingPieces[i].getPosition().equals(start.toString())){
                playingPieces[i].setPosition(end.toString());
                currentGameBoard =  convertBoardtoString();
                break;
            }
        } 
    }
    */
    
    protected PlayingPiece[] getPlayingPieces(){
        return playingPieces;
    }
}
