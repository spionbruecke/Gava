package server;

import java.io.*;

import java.net.*;
import organisation.*;
import monk.*;

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
        Games schach = new Games("Schach"); //these are the two games we want to implement fully
        Games mill = new Games("Mühle");
        GameRoom gameRoom;

            try {
                if(newConnection != null){
                    System.out.println("Client " + this.newConnection + " connected");
                    outputStream.writeUTF("You're now connected"); //for test purpose
                    player = new Player();
                    
                    outputStream.writeUTF("You're Player: " + player.getName() + " with ID: " + player.getPlayerID()); //for test purpose

                    outputStream.writeUTF("Please choose a Game"); //for test purpose
                    outputStream.writeUTF("1. Schach \n2. Mühle"); //for test purpose
                    switch (inputStream.readUTF()){
                        case "1":
                            player.setGame(schach);
                            break;
                        case "2":
                            player.setGame(mill);
                            break;
                        default:
                            break;
                    }

                    gameRoom = controller.addPlayer(player);

                    outputStream.writeUTF("You're connectet with Player: " + gameRoom.getTheOtherPlayer(player) +" with the Game: " + gameRoom.getGame()); //for test purpose 

                    
                    outputStream.writeUTF("You disconnect now"); //for test purpose
                    newConnection.close();
                    inputStream.close();
                    outputStream.close();
                    newConnection = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
