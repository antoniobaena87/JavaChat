package chat_server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class Main {
	public static void main(String[] args) {
		
		ChatServer chatServer = null;
		
		try{
			chatServer = new ChatServer();
		}catch(IOException ex) {
			System.out.println("Error opening server: " + ex);
		}
	}
}
