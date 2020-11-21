package src.monk;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.*;
/*
*   This is just a Test Class for Testing the Server
*
*/
public class Client {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in); 

        try (Socket verbindung = new Socket("localhost", 1515);
             Socket verbindung2 = new Socket("localhost", 1515);) {
            
            DataInputStream dis = new DataInputStream(verbindung.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(verbindung.getOutputStream()); 
            
            DataInputStream dis2 = new DataInputStream(verbindung2.getInputStream()); 
            DataOutputStream dos2 = new DataOutputStream(verbindung2.getOutputStream()); 

            String text;
            String text2;
            String tosend;

            while(true){
                text = dis.readUTF();
                System.out.println(text);
                text2 = dis2.readUTF();
                System.out.println(text2);

                if(text.equals("1. Schach \n2. Mühle")){
                    //System.out.println("1. Schach \n2. Mühle");
                    tosend = scn.nextLine(); 
                    dos.writeUTF(tosend); 
                }


                if(text2.equals("1. Schach \n2. Mühle")){
                   // System.out.println("1. Schach \n2. Mühle");
                    tosend = scn.nextLine(); 
                    dos2.writeUTF(tosend); 
                }

                if (text.equals("You disconnect now"))
                    break;
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        scn.close();
    }
}
