package src.server;

import java.io.*;
import java.time.*;
import java.time.format.*;

public class LogWriter {

    private LogWriter(){}


    public static void writeToLog(String input) {
        try (FileWriter writeLog = new FileWriter("filename.txt");) {
            writeLog.write("[ " + getTime() + " ] " + input + "\n");
            writeLog.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public static String getTime(){
        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return dateformat.format(now);  
    }
}
