package src.server;

import java.io.*;
import java.net.*;

import src.games.*;
import src.chess.*;
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
                                    if(this.gameRoom.getGame() instanceof ChessGame){
                                        handleChessgame(information);
                                    }
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

        public void sendMessage(String message) throws IOException {
            outputStream.writeUTF(message);
        }

        private void handleChessgame(String information) throws WrongInformationFormatException, IOException {
            String tmp;
            InformationsTypes typ;
            Messages finished;

            tmp = gameRoom.setInput(information);
            typ = StringConverter.getInformationType(tmp);
            if(typ.equals(InformationsTypes.ERROR)){
                outputStream.writeUTF(tmp);
            } else if (typ.equals(InformationsTypes.PROMOTION)){

            } else {  //TODO(Alex) Informationstype Win or Lose
                outputStream.writeUTF("<Sucess>");  
                gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Gameboard=" + ChessMoveConverter.convertPiecesToString((ChessBoard)gameRoom.getGameBoard()) + ">");
                /*finished = gameRoom.getRules().isGameFinished(gameRoom.getGameBoard(),gameRoom.getTheOtherPlayer(player).getColour());
                switch (finished){
                    case DEFEATED:
                        gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Loss>");
                        outputStream.writeUTF("<Win>");
                        break;
                    case DRAW:
                        gameRoom.getTheOtherPlayer(player).getClientHandler().sendMessage("<Draw>");
                        outputStream.writeUTF("<Draw>");
                        break;
                    default:
                        System.out.println("Some mistake");

                }*/
            }
        }
}
