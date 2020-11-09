package server;

import java.io.*;
import java.text.*;
import java.util.*;
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

    @Override
    public void run() {
        Player player;
        Games schach = new Games("Schach");
        Games mill = new Games("Mühle");
        GameMaster gameMaster;
        while(true){
            try {
                if(newConnection != null){
                    System.out.println("Client " + this.newConnection + " connected");
                    outputStream.writeUTF("You're now connected");
                    player = new Player();
                    
                    outputStream.writeUTF("You're Player: " + player.getName() + " with ID: " + player.getPlayerID());

                    outputStream.writeUTF("Please choose a Game");
                    
                    switch (inputStream.readUTF()){
                        case "1":
                            player.choseGame(schach);
                            break;
                        case "2":
                            player.choseGame(mill);
                            break;
                        default:
                            break;
                    }

                    gameMaster = controller.addPlayer(player);



                    outputStream.writeUTF("You're connectet with Player: ");

                    outputStream.writeUTF("You disconnect now");
                    newConnection.close();
                    inputStream.close();
                    outputStream.close();
                    newConnection = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
