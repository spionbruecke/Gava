package src.chess;

import java.util.Arrays;

import src.games.*;

/**
 *   ChessBoard extends the class Gameboard from src.Games and is used for
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
    private ChessPlayingPiece[] playingpieces = new ChessPlayingPiece[32];

    public static void main(String[] args) {
        ChessBoard chessi = new ChessBoard();
        String board = chessi.convertPiecesToString();
        try {
            String old = chessi.convertPiecesToString();
            chessi.setState(chessi.getBoardFromString(board));
            String newOne = chessi.convertPiecesToString();
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
            playingpieces[x].setPosition(getColumn(x - 24)+"2");
        }

        super.setState(setUpPlayingPieces());
    }

    /**
     * The method setUpPlayingPieces() returns a chess board with an initial setup.
     * @return ChessPlayingPiece[][]
     */
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

    // Converts the String list (not the simple String "A1 A2") into a two dim. PlayingPiece Array
    @Override
    public PlayingPiece[][] getBoardFromString(String input) throws WrongFormatException {
        ChessPlayingPiece[][] newBoard = new ChessPlayingPiece[8][8];
        StringBuilder position = new StringBuilder();
        int counter = 0;
        int column;
        int row;
        
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '='){

                position.append(input.charAt(i + 1)).append(input.charAt(i + 2));
                playingpieces[counter].setPosition(position.toString());

                row = Character.getNumericValue((input.charAt(i+2))) - 1;
                column = getColumnNumber(input.charAt(i + 1));
                newBoard[row][column] = playingpieces[counter];
                
                position = new StringBuilder();
                counter = counter + 1;
                i = i + 3;
            }
        }

        return newBoard;
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

        for(int i = 0; i < playingpieces.length; i++){
            if(playingpieces[i].getPosition().equals(start.toString())){
                playingpieces[i].setPosition(end.toString());
                currentGameBoard =  convertBoardtoString();
                break;
            }
        } 
    }
    */

    //constructs the String list
    @Override
    public String convertPiecesToString() {
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

    private int getColumnNumber(char column) throws WrongFormatException {
        switch(column){
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            default:
                System.out.println(column);
                throw new WrongFormatException();
        }
    }


}
