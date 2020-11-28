package src.server;

import java.io.*;

import java.net.*;
import src.organisation.*;
import src.chess.ChessGame;

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

            try {
                if(newConnection != null){
                    System.out.println("Client " + this.newConnection + " connected");
                    outputStream.writeUTF("You're now connected"); //for test purpose
                    player = new Player();
                    
                    outputStream.writeUTF("You're Player: " + player.getName() + " with ID: " + player.getPlayerID()); //for test purpose

                    outputStream.writeUTF("Please choose a Game"); //for test purpose
                    outputStream.writeUTF("1. Schach"); //for test purpose

                    

                    input = inputStream.readUTF();

                    switch (input){
                        case "1":
                            player.setGame(schach);
                            break;
                        default:
                            break;
                    }

                    gameRoom = controller.addPlayer(player);

                    if(gameRoom.getTheOtherPlayer(player) != null)
                        outputStream.writeUTF("You're connectet with Player: " + gameRoom.getTheOtherPlayer(player).getName() +" in the Game: " + gameRoom.getGame().getName()); //for test purpose 
                    else   
                        outputStream.writeUTF("You're connectet with no one in the Game: " + gameRoom.getGame().getName()); //for test purpose 
                    

                    //Game
                    while(true){
                        input = inputStream.readUTF();
                        if(input.equals("Exit"))
                            break;
                        
                        if(input.length() == 5) { //every move of a Playing Piece is describt as start field and end field (example: "A2 A3") <- its always 5 chars long
                            gameRoom.setInput(input, player);
                        }

                        if(player.getNewStateAvaible()){
                            outputStream.writeUTF(player.getLatestMove());
                            player.setNewStateAvaible(false);
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
}
