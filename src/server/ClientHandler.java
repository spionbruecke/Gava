package src.server;

import java.io.*;
import java.net.*;
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

    protected ClientHandler(Socket newConnection, DataInputStream inputStream, DataOutputStream outputStream,
            GameController controller) {
        this.newConnection = newConnection;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.controller = controller;
    }

    // The class have some Features who are temporary because, we need them to test the Code, after the implementation of some Rules we delete most of them
    @Override
    public void run() {
        Player player;
        GameRoom gameRoom;
        String input;
        String information;
        boolean connected;

        connected = true;
            try {
                if(newConnection != null){
                    LogWriter.writeToLog("Client " + this.newConnection + " connected");
                    outputStream.writeUTF("<Connectionstatus=Connected>");
                    player = new Player();
                    player.setClientHandler(this);
                    
                    while(connected){
                        input = inputStream.readUTF();
                        information = StringConverter.getInformation(input);
                        switch(StringConverter.stringToInformation(input)){
                            case GAMEMODE:
                                player.setGame(information);
                                LogWriter.writeToLog("The Player " +  player.getName() + "choosed the Gamemode: " + information);
                                gameRoom = controller.addPlayer(player);
                                LogWriter.writeToLog("Player " + player.getName() + " is in a Room with " + gameRoom.getTheOtherPlayer(player) + " with the mode " + gameRoom.getGame()) ;
                                break;
                            case GAMEBOARD:
                            
                                break;
                            case LOGIN:
                            	if (information.equals("tobi,123"))
                            		outputStream.writeUTF("<Login=True>");
                            	else
                            		outputStream.writeUTF("<Login=False>");
                                break;
                            case CREATEACCOUNT:
                            
                            	break;
                            case CONNECTIONSTATUS:
                                if(information.equals("Exit"))
                                    connected = false;
                                break;
                            case MESSAGE:

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
}
