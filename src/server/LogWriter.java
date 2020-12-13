package src.server;

import java.io.*;
import java.time.*;
import java.time.format.*;

public class LogWriter {

    private LogWriter(){}


    /**
     * Writes every input in a file named "Log.txt". The input mostly contains informations for the server, like who connected etc.
     * 
     * @author Alexander Posch
     * @param input
     */
    public static void writeToLog(String input) {
        try (BufferedWriter writeLog = new BufferedWriter( new FileWriter("Log.txt",true));) {
            writeLog.write("[ " + getTime() + " ] " + input + "\n");
            writeLog.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    /**
     * @author Alexander Posch
     * @return current time
     */
    public static String getTime(){
        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return dateformat.format(now);  
    }
}
