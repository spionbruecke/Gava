package src.organisation;

import java.io.IOException;

import src.chess.*;
import src.games.*;

/**
 * @author Alexander Posch
 * @version 0.2
 * 
 * The GameRoom organize and control the Game.
 * It checks for Rules and decides whos turn it is
 * 
 */
public class GameRoom{

    //private GameBoard currentGameBoard;
    private Game currentGame;
    private Player player1;
    private Player player2;
    private Player turnOfPlayer;
    private int numberOfPlayer;
    private GameBoard gameBoard;
    private Rules rule;
    private String game;

    public GameRoom(Game gamemode){
        currentGame = gamemode;

        if(gamemode instanceof ChessGame){
            game = "Chess";
        }
    }
    
    /**
     * Init the GameBoard and the rules depending on the GameMode, when there a 2 Players in the Room.
     * Randomly choosing a player to start.
     * 
     * @author Alexander Posch
     * @throws IOException
     */
    private void getStart() throws IOException{
        //Decides Randomly who is allowed to start
        if ((int) ( Math.random() * 2 + 1) == 1){
            turnOfPlayer = player1;
            player1.getClientHandler().sendMessage("<Start=1>");
            player2.getClientHandler().sendMessage("<Start=0>");
        } else {
            turnOfPlayer = player2;
            player1.getClientHandler().sendMessage("<Start=0>");
            player2.getClientHandler().sendMessage("<Start=1>");
        }

        if(game.equals("Chess")){
            rule = new ChessRules();
            gameBoard = new ChessBoard();
        }

    }
    

    /**
     * Simply adds a Player an return a boolean, when it was sucessfull.
     * If the second player was added, the methode getStart will be called.
     * 
     * @author Alexander Posch
     * @param player
     * @return was it sucessfull
     */
     protected boolean addPlayer(Player player) throws IOException{
        if(numberOfPlayer == 0) {
            player1 = player;
            numberOfPlayer ++;

        } else if(numberOfPlayer == 1) {
            player2 = player;
            numberOfPlayer ++;
            getStart();
        } else {
            return false;
        }
        return true;
    }
    
    /**
     * Depending on the GameMode the new Board with the new Move will be handeled different
     * 
     * @author Alexander Posch
     * @param information
     * @return Message, if allowed or not
     */
    public String setInput(String information){
        if(this.currentGame instanceof ChessGame){
            return setChessInput(information);
        }
        return "<Error=Error while processing the Movement: Programming Error>";

    }

    /**
     * New GameBoard with new Move will be testet in the Methode isMoveAllowed.
     * This Methode will handle the outcome and set the new Board if the Move is allowed.
     * 
     * @author Alexander Posch
     * @param information
     * @return Message, if allowed or not
     */
    private String setChessInput(String information){
        String pieceintheWay = "<Error=There is some piece in the way>";
        try{
            Messages message;
            message = rule.isMoveAllowed(gameBoard, ChessMoveConverter.getBoardFromString(information));
            switch(message){
                case VICTORY:
                    return "<Gameend=Victory>";
                case DEFEATED:
                    return "<Gameend=Defeated>";
                case MOVE_ALLOWED:
                    gameBoard.setNewBoard(information);
                    this.turnOfPlayer = getTheOtherPlayer(turnOfPlayer);
                    return "<Gameboard=" + ChessMoveConverter.convertPiecesToString((ChessBoard) this.gameBoard) + ">";
                case ERROR_WRONGMOVEMENT_DIRECTION_BISHOP:
                    return "<Error=Bishop is only allowed to move directional>"; //TODO(Alex) Write some good Error Descrition for the player
                case ERROR_WRONGMOVEMENT_DIRECTION_KING:
                    return "<Error=King is only allowed to move one Step>";
                case ERROR_WRONGMOVEMENT_DIRECTION_KNIGHT:
                    return "<Error=Knight is only allowed to move one Step in one Axes and two Steps in another>";
                case ERROR_WRONGMOVEMENT_DIRECTION_PAWN:
                    return "<Error=Pawn is only allowed tom move two Steps forward, when he hasn't moved jet. Otherwise he move one Step forward or directional to strike against an enemy piece>";
                case ERROR_WRONGMOVEMENT_DIRECTION_QUEEN:
                    return "<Error=Queen is only allowed to move straight in every direction>";
                case ERROR_WRONGMOVEMENT_DIRECTION_ROOK:
                    return "<Error=Rook is only allowed to move straight in the x- or y-axis>";
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_BISHOP:
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_KING:
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_KNIGHT:
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_PAWN:
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_QUEEN:
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_ROOK:
                    return pieceintheWay;
                case ERROR_NO_SUCH_PLAYINGPIECE:
                    return "<Error=There is no such playing piece";
                default:
                    System.out.println("Big ERROR: " + message);
            }
    }catch (Exception e){
        System.err.println(e);
    }
        return null;
    }
    /**
     * Returns the other player of the gameroom.
     * 
     * @author Alexander Posch
     * @param player
     * @return The other Player
     */
    public Player getTheOtherPlayer( Player player){
        if (player1 == player && player2 != null)
            return player2;
        else if( player2 == player && player1 != null)
            return player1;
        else
            return null;
    }
    
    public boolean getTurn(Player player){
        return player == turnOfPlayer;
    }

    public int getNumberOfPlayer()      {return numberOfPlayer;}

    public Game getGame()               {return currentGame;}

    public Player getPlayer1()          {return player1;}

    public Player getPlayer2()          {return player2;}

    public GameBoard getGameBoard()     {return gameBoard;}


    public String toString(){

        StringBuilder output = new StringBuilder();

        output.append("Game: ").append(currentGame).append(" |->");
        output.append("Player 1: ").append(player1).append(" | ");
        output.append("Player 2: ").append(player2).append("\n");
        return output.toString();
    }
}