package src.server;

import java.io.*; 
import src.organisation.*;
import java.net.*;

/**
 * @author Alexander Posch
 * @version 1.0
 * 
 * Main Function: Should always run in the Background of the Server, so every Client can connect every time
 */

public class Server {
    
    //This class manage the incoming connections and give them a neh Thread
    public static void main(String[] args) {
        Socket newConnection = null;
        GameController controller = new GameController();
        //TODO(Alex): Think about the Portnumber. There is mayby a better one 
        try (ServerSocket newSocket = new ServerSocket(1515);){      

            while(true){  //it has to run until the server(or the programm) shutdown
                newConnection = null;    
                newConnection = newSocket.accept();

                System.out.println("New Client connectet"); // Message for the Server -> Think about where to save and if we need to safe this.

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
