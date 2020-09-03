package chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer {
	
	private final int PORT = 2524;
	private ServerSocket serverSocket;
	protected static ArrayList<ClientThread> clientThreads = new ArrayList<>();
	
	private int chatters = 0;
	
	private boolean runServer = true;
	
	public ChatServer() throws IOException{
		System.out.print("Opening server socket...");
		serverSocket = new ServerSocket(PORT);
		System.out.print("Server socket successfully opened.\n");
		
		while(runServer) {
			ClientThread clientThread = new ClientThread(serverSocket.accept());
			clientThreads.add(clientThread);
			clientThread.start();
			System.out.println("New client accepted");
		}
	}
	
	public boolean isRunning() {
		return runServer;
	}
}
