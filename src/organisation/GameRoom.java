package src.organisation;

import java.io.IOException;

import src.chess.*;
import src.games.*;
import src.mill.*;

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
    private String promotionPosition;
    private boolean startingphase;

    public GameRoom(Game gamemode){
        currentGame = gamemode;
        if(gamemode instanceof ChessGame){
            game = "Chess";
        } else if(gamemode instanceof MillGame){
            game = "Mill";
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
        startingphase = true;
        //Decides Randomly who is allowed to start
        if ((int) ( Math.random() * 2 + 1) == 1){
            turnOfPlayer = player1;
            player1.setColour("white");
            player2.setColour("black");
            player1.getClientHandler().sendMessage("<Start=1>");
            player2.getClientHandler().sendMessage("<Start=0>");
        } else {
            turnOfPlayer = player2;
            player2.setColour("white");
            player1.setColour("black");
            player1.getClientHandler().sendMessage("<Start=0>");
            player2.getClientHandler().sendMessage("<Start=1>");
        }

        if(game.equals("Chess")){
            gameBoard = new ChessBoard();
        } else if(game.equals("Mill")){
            gameBoard = new MillBoard();
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
        } else if (this.currentGame instanceof MillGame){
            return setMillInput(information);
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
            String promotion;

            message = ChessRules.executeMove(gameBoard, turnOfPlayer.getColour(), ChessMoveConverter.getBoardFromString(information));
            promotion = ChessRules.isPromotion(gameBoard, ChessMoveConverter.getBoardFromString(information));
            
            if(message == Messages.GO_ON){
                gameBoard.setNewBoard(information);
                if(ChessRules.isKingDead(gameBoard,getTheOtherPlayer(turnOfPlayer))){
                    getTheOtherPlayer(turnOfPlayer).getClientHandler().sendMessage("<Loss>");
                    return "<Win>";
                }

                if(!promotion.equals("false")){
                    promotionPosition = promotion;
                    return "<Promotion>";
                }
            }
            switch(message){
                case VICTORY:
                    getTheOtherPlayer(turnOfPlayer).getClientHandler().sendMessage("<Loss>");
                    return "<Win>";
                case DEFEATED:
                    getTheOtherPlayer(turnOfPlayer).getClientHandler().sendMessage("<Win>");
                    return "<Loss>";
                case DRAW:
                    return "<Gameend=Draw>";
                case MOVE_ALLOWED:
                case GO_ON:
                    this.turnOfPlayer = getTheOtherPlayer(turnOfPlayer);
                    return "<Gameboard=" + ChessMoveConverter.convertPiecesToString((ChessBoard) this.gameBoard) + ">";
                case ERROR_WRONGMOVEMENT_DIRECTION_BISHOP:
                    return "<Error=Bishop is only allowed to move directional>"; //TODO(Alex) Write some good Error Description for the player
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
                    return pieceintheWay;
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_KING:
                    return pieceintheWay;
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_KNIGHT:
                    return pieceintheWay;
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_PAWN:
                    return pieceintheWay;
                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_QUEEN:
                    return pieceintheWay;
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

    private String setMillInput(String information){
        try{
            Messages message;

            message = MillRules.executeMove(gameBoard, turnOfPlayer.getColour(), MillMoveConverter.getBoardFromString(information));

            switch(message){
                case VICTORY:
                    getTheOtherPlayer(turnOfPlayer).getClientHandler().sendMessage("<Loss>");
                    return "<Win>";
                case DEFEATED:
                    getTheOtherPlayer(turnOfPlayer).getClientHandler().sendMessage("<Win>");
                    return "<Loss>";
                case DRAW:
                    getTheOtherPlayer(turnOfPlayer).getClientHandler().sendMessage("<Draw>");
                    return "<Draw>";
                case MOVE_ALLOWED:
                case GO_ON:
                    gameBoard.setNewBoard(information);
                    this.turnOfPlayer = getTheOtherPlayer(turnOfPlayer);
                    return "<Gameboard=" + MillMoveConverter.convertPiecesToString((MillBoard) this.gameBoard) + ">";
                case ERROR_WRONGMOVEMENT:
                    return "<Error=Wrong Movement>";
                case MOVE_ALLOWED_REMOVE_PIECE:
                    gameBoard.setNewBoard(information);
                    return "<Remove>";
                default:
                    System.out.println("Set Input for Mill Error");
                    return null;
            }
        }catch (Exception e){
            System.err.println(e);
        }
        return null;
    }

    public void setPromotion(String information) throws WrongFormatException {
        ChessRules.setPromotion(gameBoard, information, promotionPosition);
    }

    public void setTurn(Player player){
        this.turnOfPlayer = player;
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

    public Rules getRules()              {return rule;}


    public String toString(){

        StringBuilder output = new StringBuilder();

        output.append("Game: ").append(currentGame).append(" |->");
        output.append("Player 1: ").append(player1).append(" | ");
        output.append("Player 2: ").append(player2).append("\n");
        return output.toString();
    }
}