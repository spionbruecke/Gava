package src.server;

import java.io.*;
import java.net.*;
import src.chess.*;
import src.organisation.*;

/**
 * @author Alexander Posch
 * @version 2.5
 * 
 * For each connection there is own ClientHandler,who sends and recieve informations
 */


public class ClientHandler extends Thread {

    private Socket newConnection;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private GameController controller;

    protected ClientHandler(Socket newConnection, DataInputStream inputStream, DataOutputStream outputStream,
            GameController controller) {
        this.newConnection = newConnection;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.controller = controller;
    }

    /**
     * 
     *  Handls every incoming message and send the output of the gameroom to the client
     */
    @Override
    public void run() {
        String input;
        String information;
        Player player;
        GameRoom gameRoom;
        boolean connected;
    
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
                                    connected = gameRoom.handleGame(information);
                                }
                                break;                    
                            case PROMOTION:
                                gameRoom.setPromotion(information);
                                gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Gameboard=" + ChessMoveConverter.convertPiecesToString((ChessBoard)gameRoom.getGameBoard()) + ">");
                                gameRoom.setTurn(gameRoom.getTheOtherPlayer(player));
                                gameRoom.incrementRoundnumber();
                                break;
                            case REMOVE:
                                outputStream.writeUTF(gameRoom.handleRemove(information));
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
                                throw new WrongInformationFormatException();
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
}
