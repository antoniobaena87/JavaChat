package chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	
	private boolean runClient = true;
	private Socket socket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		System.out.println("Thread running...");
		in = null;
		out = null;
		String userName = "";
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			userName = in.readLine();
			System.out.println(userName);
		}catch(IOException ex) {
			ex.printStackTrace();
		}

		for(ClientThread clientThread : ChatServer.clientThreads) {
			if(clientThread == this) continue;
			clientThread.getOut().println("#" + userName + " has connected.");
		}
		
		while(runClient) {
			try{
				String message = in.readLine();
				if(message != null) {
					if(!message.contains("0000001 disconnect command")) {
						for(ClientThread clientThread : ChatServer.clientThreads) {
							clientThread.getOut().println(message);
						}
					}else {
						runClient = false;
						for(ClientThread clientThread : ChatServer.clientThreads) {
							clientThread.getOut().println("#" + userName + " disconnected");
						}
					}
				}
				
			}catch(IOException ex) {
				System.out.println("Error reading client stream in CLientThread.java: " + ex);
			}
		}
		
		try{
			System.out.print("Closing client thread...");
			out.close();
			in.close();
			socket.close();
			ChatServer.clientThreads.remove(this);
			System.out.println("successfully closed!");
		}catch(IOException ex) {
			System.out.println("Error closing client thread.");
			ex.printStackTrace();
		}
	}
	
	protected PrintWriter getOut() {
		return out;
	}
}
