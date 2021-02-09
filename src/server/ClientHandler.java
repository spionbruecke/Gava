package src.server;

import java.io.*;
import java.net.*;
import src.chess.*;
import src.games.*;
import src.mill.*;
import src.organisation.*;

/**
 * @author Alexander Posch
 * @version 0.2
 * 
 * For each connection there is own ClientHandler,who sends and recieve informations
 */


public class ClientHandler extends Thread {

    private Socket newConnection;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private GameController controller;
    private Player player;
    private GameRoom gameRoom;
    private boolean connected;

    protected ClientHandler(Socket newConnection, DataInputStream inputStream, DataOutputStream outputStream,
            GameController controller) {
        this.newConnection = newConnection;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.controller = controller;
    }


    @Override
    public void run() {
        String input;
        String information;
        

        connected = true;
            try {
                if(newConnection != null){
                    LogWriter.writeToLog("Client " + this.newConnection + " connected");
                    outputStream.writeUTF("<Connectionstatus=Connected>");
                    player = new Player();
                    player.setClientHandler(this);
                    gameRoom = null;

                    while(connected){
                        input = inputStream.readUTF();
                        information = StringConverter.getInformation(input);
                        switch(StringConverter.getInformationType(input)){
                            case GAMEMODE:
                                player.setGame(information);
                                LogWriter.writeToLog("The Player " +  player.getName() + "choosed the Gamemode: " + information);
                                gameRoom = controller.addPlayer(player);
                                LogWriter.writeToLog("Player " + player.getName() + " is in a Room with " + gameRoom.getTheOtherPlayer(player) + " with the mode " + gameRoom.getGame());
                                break;
                            case GAMEBOARD:
                                if(gameRoom != null){
                                        handleGame(information);
                                }
                                break;
                            case LOGIN:
                            	if (information.equals("tobi,123")){
                                    outputStream.writeUTF("<Login=True>");
                                    player.setName("tobi");
                                }
                            	else
                            		outputStream.writeUTF("<Login=False>");
                                break;
                            case CREATEACCOUNT:
                                //dummyfunctionLogin(Name, pW)
                                break;                           
                            case PROMOTION:
                                gameRoom.setPromotion(information);
                                gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Gameboard=" + ChessMoveConverter.convertPiecesToString((ChessBoard)gameRoom.getGameBoard()) + ">");
                                gameRoom.setTurn(gameRoom.getTheOtherPlayer(player));
                                gameRoom.incrementRoundnumber();
                                break;
                            case REMOVE:
                                if(MillRules.checkRemovedPiece(gameRoom.getGameBoard(),  MillMoveConverter.getBoardFromString(information), gameRoom.getTheOtherPlayer(player).getColour()).equals(Messages.MOVE_ALLOWED)){
                                    gameRoom.getGameBoard().setNewBoard(information);
                                    gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Gameboard=" + MillMoveConverter.convertPiecesToString((MillBoard) gameRoom.getGameBoard()) + ">");
                                    gameRoom.setTurn(gameRoom.getTheOtherPlayer(player));
                                    gameRoom.incrementRoundnumber();
                                    outputStream.writeUTF("<Sucess>");
                                } else {
                                    outputStream.writeUTF("<Error=You aren't allowed to remove from a mill>");
                                }

                                break;
                            case CONNECTIONSTATUS:
                                if(information.equals("Exit"))
                                    connected = false;
                                break;
                            case TIMEOUT:
                                gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Win>");
                                outputStream.writeUTF("<Loss>");
                                connected = false; 
                                break;
                            default:

                                break;
                        }
                    }
                    
                    outputStream.writeUTF("You disconnect now"); //for test purpose
                    newConnection.close();
                    inputStream.close();
                    outputStream.close();
                    newConnection = null;
                    LogWriter.writeToLog("Player " + player.getName() + " disconnectet.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) throws IOException {
            outputStream.writeUTF(message);
        }

        private void handleGame(String information) throws WrongInformationFormatException, IOException {
            String tmp;
            InformationsTypes typ;

            tmp = gameRoom.setInput(information);
            typ = StringConverter.getInformationType(tmp);
            if(typ.equals(InformationsTypes.ERROR)){
                outputStream.writeUTF(tmp);
            } else if( typ.equals(InformationsTypes.WIN)){
                outputStream.writeUTF("<Win>"); 
                if(this.gameRoom.getGame() instanceof ChessGame){
                    gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Gameboard=" + ChessMoveConverter.convertPiecesToString((ChessBoard)gameRoom.getGameBoard()) + ">");
                }
                connected = false; 
            } else if(typ.equals(InformationsTypes.LOSS)){
                outputStream.writeUTF("<Loss>"); 
                if(this.gameRoom.getGame() instanceof ChessGame){
                    gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Gameboard=" + ChessMoveConverter.convertPiecesToString((ChessBoard)gameRoom.getGameBoard()) + ">");
                }
                connected = false; 
            } else if(typ.equals(InformationsTypes.PROMOTION)){
                outputStream.writeUTF("<Promotion>"); 
            } else if(typ.equals(InformationsTypes.REMOVE)){
                outputStream.writeUTF("<Remove>"); 
            } else {
                outputStream.writeUTF("<Sucess>"); 
                if(this.gameRoom.getGame() instanceof ChessGame){ 
                    gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Gameboard=" + ChessMoveConverter.convertPiecesToString((ChessBoard)gameRoom.getGameBoard()) + ">");
                } else if (this.gameRoom.getGame() instanceof MillGame){ 
                    gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Gameboard=" + MillMoveConverter.convertPiecesToString((MillBoard)gameRoom.getGameBoard()) + ">");
                }
            }
        }
}
