package src.server;

import java.io.*; 
import src.organisation.*;
import java.net.*;

/**
 * @author Alexander Posch
 * @version 1.0
 * 
 * Main Function: Should always run in the Background of the Server, so every Client can connect every time
 * Allows connection from every Client, who connects to port 1515
 */

public class Server {
    public static void main(String[] args) {
        Socket newConnection = null;
        GameController controller = new GameController();
        
        try (ServerSocket newSocket = new ServerSocket(1515);){      

            while(true){  //it has to run until the server(or the programm) shutdown
                newConnection = null;    
                newConnection = newSocket.accept();

                LogWriter.writeToLog("New Client connectet"); // Message for the Server 

                DataInputStream inputStream = new DataInputStream(newConnection.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(newConnection.getOutputStream());

                Thread newThread = new ClientHandler(newConnection,inputStream, outputStream, controller);

                newThread.start();
            }
        } catch (Exception e) {
            // if there is an error, the connection should be closed, if there is one
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
