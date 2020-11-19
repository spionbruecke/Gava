import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
	
	private String name;
	private String host = "localhost";
	private int port = 1515;
	
	private DataInputStream dis;
	private DataOutputStream dos;
	
	Boolean isStillPlaying = true;
	
	
	
	public Client(String name) {
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
			isStillPlaying = false;
		}
		System.out.println(text);
		return text;
	}


	public void connectToServer() {
		Socket server = null;
		
		try {
			server = new Socket(host, port);
			
            dis = new DataInputStream(server.getInputStream()); 
            dos = new DataOutputStream(server.getOutputStream());
            
			run();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {		
			if (server != null)
				try {
					server.close();
					System.out.println("Disconnected");
				}
			catch (IOException e) {				
			}
		}
	}
}