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
		
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		while(runClient) {
			try{
				String message = in.readLine();
				if(message != null) {
					for(ClientThread clientThread : ChatServer.clientThreads) {
						clientThread.getOut().println(message);
					}
				}
				
			}catch(IOException ex) {
				System.out.println(ex);
			}
		}
		
		try{
			out.close();
			in.close();
			socket.close();
			ChatServer.clientThreads.remove(this);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	protected PrintWriter getOut() {
		return out;
	}
}
