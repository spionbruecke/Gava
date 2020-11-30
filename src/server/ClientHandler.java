package src.server;

import java.io.*;
import java.net.*;
import src.organisation.*;
import src.chess.*;


/**
 * @author Alexander Posch
 * @version 0.2
 * 
 * For each connection there is own ClientHandler,who sends and recieve informations
 * TODO(Alex): Refactor the whole Class to get a nice structure 
 */


public class ClientHandler extends Thread {

    private Socket newConnection;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private GameController controller;
    

    protected ClientHandler(Socket newConnection, DataInputStream inputStream, DataOutputStream outputStream, GameController controller) {
        this.newConnection = newConnection;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.controller = controller;
    }

    // The class have some Features who are temporary because, we need them to test the Code, after the implementation of some Rules we delete most of them
    @Override
    public void run() {
        Player player;
        ChessGame schach = new ChessGame(); //these one game we want to implement later
        GameRoom gameRoom;
        String input;
        String information;

            try {
                if(newConnection != null){
                    System.out.println("Client " + this.newConnection + " connected");
                    outputStream.writeUTF("< Connectionstatus = Connected >");
                    player = new Player();
                    
                    informationCheck: //TODO: (Alex) find a better Solution
                    while(true){
                        input = inputStream.readUTF();
                        information = getInformation(input);
                        switch(StringConverter.stringToInformation(input)){
                            case GAMEMODE:
                                player.setGame(information);
                                System.out.println("The Player choosed the Gamemode: " + information);
                                break;
                            case GAMEBOARD:
                            
                                break;
                            case LOGIN:

                                break;
                            case CONNECTIONSTATUS:
                                if(information.equals("Exit"))
                                    break informationCheck;
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private String getInformation(String input){
        StringBuilder information = new StringBuilder();
        int charNumber = 0;

        for(int j = 0 ; j < input.length(); j ++){
            if(input.charAt(j) == '='){
                charNumber = j + 1;
                break;
            }
        }

        for(int i = charNumber; i < input.length(); i ++){
            if(input.charAt(i) == '>')
                break;
            information.append(input.charAt(i));
        }
        return information.toString();
    }
}
