package src.chess;

import src.Games.*;

public class ChessBoard extends GameBoard {

    static final String BLACK = "black";
    static final String WHITE = "white";
    static final String ROOK = "rook";
    static final String KNIGHT = "knight";
    static final String BISHOP = "bishop";
    static final String QUEEN = "queen";
    static final String KING = "king";
    static final String PAWN = "pawn";
    private ChessPlayingPiece[] playingpieces = new ChessPlayingPiece[32];

    public static void main(String[] args) {
        ChessBoard chess = new ChessBoard();
        System.out.println(chess.convertBoardtoString());
    }


    public ChessBoard(){
        super.setGameBoardID(System.currentTimeMillis());
        for(int i = 0; i < 32; i++){
            playingpieces[i] = new ChessPlayingPiece();
        }

        playingpieces[0].setName(ROOK);
        playingpieces[0].setColour(BLACK);
        playingpieces[0].setPosition("A8");

        playingpieces[1].setName(KNIGHT);
        playingpieces[1].setColour(BLACK);
        playingpieces[1].setPosition("B8");

        playingpieces[2].setName(BISHOP);
        playingpieces[2].setColour(BLACK);
        playingpieces[2].setPosition("C8");

        playingpieces[3].setName(QUEEN);
        playingpieces[3].setColour(BLACK);
        playingpieces[3].setPosition("D8");

        playingpieces[4].setName(KING);
        playingpieces[4].setColour(BLACK);
        playingpieces[4].setPosition("E8");

        playingpieces[5].setName(BISHOP);
        playingpieces[5].setColour(BLACK);
        playingpieces[5].setPosition("F8");

        playingpieces[6].setName(KNIGHT);
        playingpieces[6].setColour(BLACK);
        playingpieces[6].setPosition("G8");

        playingpieces[7].setName(ROOK);
        playingpieces[7].setColour(BLACK);
        playingpieces[7].setPosition("H8");

        for (int x = 8; x < 16; x++) {
            playingpieces[x].setName(PAWN);
            playingpieces[x].setColour(BLACK);
            playingpieces[x].setPosition(getColumn(x - 8)+"7");
        }

        playingpieces[16].setName(ROOK);
        playingpieces[16].setColour(WHITE);
        playingpieces[16].setPosition("A1");

        playingpieces[17].setName(KNIGHT);
        playingpieces[17].setColour(WHITE);
        playingpieces[17].setPosition("B1");

        playingpieces[18].setName(BISHOP);
        playingpieces[18].setColour(WHITE);
        playingpieces[18].setPosition("C1");

        playingpieces[19].setName(QUEEN);
        playingpieces[19].setColour(WHITE);
        playingpieces[19].setPosition("D1");

        playingpieces[20].setName(KING);
        playingpieces[20].setColour(WHITE);
        playingpieces[20].setPosition("E1");

        playingpieces[21].setName(BISHOP);
        playingpieces[21].setColour(WHITE);
        playingpieces[21].setPosition("F1");

        playingpieces[22].setName(KNIGHT);
        playingpieces[22].setColour(WHITE);
        playingpieces[22].setPosition("G1");

        playingpieces[23].setName(ROOK);
        playingpieces[23].setColour(WHITE);
        playingpieces[23].setPosition("H1");

        for (int x = 24; x < 32; x++) {
            playingpieces[x].setName(PAWN);
            playingpieces[x].setColour(WHITE);
            playingpieces[x].setPosition(getColumn(x - 25)+"2");
        }

        super.setState(setUpPlayingPieces());

    }

    @Override
    protected ChessPlayingPiece[][] setUpPlayingPieces(){
        ChessPlayingPiece[][] initialState = new ChessPlayingPiece[8][8];
        for(int k = 0 ; k < 8; k ++) {
            for (int j = 0; j < 8; j ++){
                initialState[k][j] = new ChessPlayingPiece();
            }
        }

        initialState[0][0] = playingpieces[0];

        initialState[0][1] = playingpieces[1];

        initialState[0][2] = playingpieces[2];

        initialState[0][3] = playingpieces[3];

        initialState[0][4] = playingpieces[4];

        initialState[0][5] = playingpieces[5];

        initialState[0][6] = playingpieces[6];

        initialState[0][7] = playingpieces[7];

        for (int x = 0; x < 8; x++) {
            initialState[1][x] = playingpieces[8 + x];
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                initialState[j][i].setName(null);
                initialState[j][i].setColour(null);
            }
        }

        for (int x = 0; x < 8; x++) {
            initialState[6][x] = playingpieces[24 + x];
        }

        initialState[7][0] = playingpieces[17];

        initialState[7][1] = playingpieces[18];

        initialState[7][2] = playingpieces[19];

        initialState[7][3] = playingpieces[20];

        initialState[7][4] = playingpieces[21];

        initialState[7][5] = playingpieces[22];

        initialState[7][6] = playingpieces[23];

        initialState[7][7] = playingpieces[24];

        return initialState;
    }


    @Override
    public String getNewMove(String newBoard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertBoardtoString() {
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < 32; i++){
            output.append("<");
            output.append(playingpieces[i]);
            output.append(">");
        }

        return output.toString();
    }

    private char getColumn(int row){
        switch (row){
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H';
            default:
                return ' ';
        }
    }


}
