package server;

import java.io.*; 
import organisation.*;
import java.net.*;


public class Server {
    
   public static void main(String[] args) {
        Socket newConnection = null;
        GameController controller = new GameController();
        try (ServerSocket newSocket = new ServerSocket(1515);){      

            while(true){
                newConnection = null;    
                newConnection = newSocket.accept();

                System.out.println("New Client connectet");

                DataInputStream inputStream = new DataInputStream(newConnection.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(newConnection.getOutputStream());

                Thread newThread = new ClientHandler(newConnection,inputStream, outputStream, controller);

                newThread.start();
            }
        } catch (Exception e) {
            try{
                if(newConnection != null)
                            newConnection.close();
            } catch (Exception er) {
                er.printStackTrace();
            } finally {
                e.printStackTrace(); 
            }
        }
    }
}
