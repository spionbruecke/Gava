package src.organisation;

import src.chess.*;
import src.games.*;

/**
 * @author Alexander Posch
 * @version 0.2
 * 
 * The GameRoom organize and controll the Game.
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

    public GameRoom(Game game){ //<- Bullshit weil Spieler zwei fehlt : Verschieben nach StartUp
        currentGame = game;
        //Decides Randomly who is allowed to start
        if ((int) ( Math.random() * 2 + 1) == 1){
            turnOfPlayer = player1;
        } else {
            turnOfPlayer = player2;
        }

        if(game instanceof ChessGame)
            rule = new ChessRules();
    }
    //****  Functions ****

    //protected void getStart(){}
    
    // Get the new Move. Checks if it is allowed and send the new Board to the other Player.
    //public Boolean setInput(String move, Player player){
        /*PlayingPiece[][] newState = MoveConverter.convertStringToState(gameBoard, move);
        if(rule.isMoveAllowed(gameBoard, move)){
            this.gameBoard.setState(newState);
            turnOfPlayer = getTheOtherPlayer(player);
            turnOfPlayer.setNewStateAvaible(true);
            turnOfPlayer.setLatestMove(move);
            return true;
        } else
            return false;
    }*/

    //check which position is free and add the player to this position
     protected boolean addPlayer(Player player){
        if(numberOfPlayer == 0) {
            player1 = player;
            numberOfPlayer ++;

        } else if(numberOfPlayer == 1) {
            player2 = player;
            numberOfPlayer ++;
        } else {
            return false;
        }
        return true;
    }
    
    public String setInput(String information){
        if(this.currentGame instanceof ChessGame){
            return setChessInput(information);
        }
        return "<Error=Error while processing the Movement: Programming Error>";

    }

    private String setChessInput(String information){
        try{
            Messages message;
            message = rule.isMoveAllowed(gameBoard, ChessMoveConverter.getBoardFromString(information));
            switch(message){
                case VICTORY_WHITE:
                    return "<Gameend=Victory>";
                case VICTORY_BLACK:
                    return "<Gameend=Victory>";
                case DEFEATED:
                    return "<Gameend=Defeated>";
                case MOVE_ALLOWED:
                    gameBoard.setnewBoard(information);
                    return "<Gameboard=" + ChessMoveConverter.convertPiecesToString((ChessBoard) this.gameBoard) + ">";
                case ERROR_WRONGMOVEMENT_DIRECTION_BISHOP:
                    return "<Error=Bishop is only allowed to move "; //TODO(Alex) Write some good Error Descrition for the player
                case ERROR_WRONGMOVEMENT_DIRECTION_KING:
                    return "<Error=Bishop is only allowed to move ";
                case ERROR_WRONGMOVEMENT_DIRECTION_KNIGHT:

                case ERROR_WRONGMOVEMENT_DIRECTION_PAWN:

                case ERROR_WRONGMOVEMENT_DIRECTION_QUEEN:

                case ERROR_WRONGMOVEMENT_DIRECTION_ROOK:

                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_BISHOP:

                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_KING:

                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_KNIGHT:

                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_PAWN:

                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_QUEEN:

                case ERROR_WRONGMOVEMENT_PIECES_IN_THE_WAY_ROOK:
                case ERROR_NO_SUCH_PLAYINGPIECE:
                    break;
                default:
                    break;

            }
    }catch (Exception e){
        System.err.println(e);
    }
        return null;
    }
    //****  Getter ****
    
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