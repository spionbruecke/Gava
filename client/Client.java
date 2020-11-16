package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import com.sun.jdi.connect.spi.Connection;


public class Client {
	
	private String name;
	private String host = "localhost";
	private int port = 1515;
	
	private DataInputStream dis;
	private DataOutputStream dos;
	
	Boolean isStillPlaying = true;
	
	
	
	public User(String name) {
		this.name = name;
	}
	
	
	public void login() {
		
	}
	
	public void createAccount() {
		
	}
	
	public void run(){
		
		String incommingData;
		String outcommingData;
		login();
		
		
		while(isStillPlaying) {
			
			incommingData = getServerOutput();

			
			if (incommingData.equals("1. Schach")) {
				outcommingData = getUserInput();
				try {
					dos.writeUTF(outcommingData);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	
	private String getUserInput() {
		Scanner scn = new Scanner(System.in);
		String text = scn.nextLine();
		scn.close();
		
		return text;
	}


	private String getServerOutput() {
		String text = "";
		
		try {
			text = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(text);
		return text;
	}


	public void connectToServer() {
		
		try {
			Socket connection = new Socket(host, port);
			
            dis = new DataInputStream(connection.getInputStream()); 
            dos = new DataOutputStream(connection.getOutputStream());
            
			run();
			
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

